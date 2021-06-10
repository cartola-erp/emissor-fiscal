# emissor-fiscal

Projeto criado para um analista fiscal, ser o responsável por manter as tributações estaduais e federais que serão usadas no cálculo, 
de documentos fiscais de saídas (emitidos), assim como a geração do arquivo SPED FISCAL (EFD ICMS IPI). Até o Momento funciona basicamente da seguinte forma:  

BREVE RESUMO
---- 
- Ao receber um **DocumentoFiscal (de emissão própria, que geralmente é de saída)**, com as devidas tributações cadastradas, será calculado os impostos (que é retornado num JSON), caso não tenha a tributação para algum item do DocumentoFiscal, não será calculado NADA, e apenas retornando que falta X tributação para o NCM do item, que está sem. (A intenção no futuro é fazer com que esse projeto também faça toda a parte de comunicação com a SEFAZ (geração do xml, envio, cancelamento, consulta etc....)

- Ao receber um **DocumentoFiscal (de emissão de terceiros, que é entradas)**, apenas será salvo na tabela docu_fisc. Exceto se for alguma NFE que **seja de SC, ES, MG** (nesses casos, será verificado na tabela **trib_esta_guia**, se algum item dessa nota de entrada, teremos que recolher o ICMS ST pela Guia gare, caso sim será enviado um email para o setor fiscal, utilizando API do sendgrid com os devidos calculos e retornado um JSON com os valores desse calculo). 
  * PS¹: Atualmente, toda a parte de calculo de impostos na entrada que teremos crédito é feita pelo ERPJ.
  * PS²: Não é feita nenhuma emissão de guia gare (das entrada de SC, ES e MG), pois não encontrei nenhuma forma de integração para fazer isso

- **SPED FISCAL** -> Parte que está atualmente em desenvolvimento. Antes de começarmos a gerar os arquivos, é necessário que de fato todos os DocumentoFiscais sejam salvos nesse projeto (hoje em dia é a maioria). Ao menos nesse primeiro momento, a preocupação é fazer com que gere o arquivo corretamente igual é gerado hoje em dia utilizando o software de terceiros. Após isso terá a parte de **Assinatura** e **Envio** etc...

- **TODO** -> Integração para ser emitida as guias GNRE (Que é necessário quando vendemos para outro estado e a pessoa seja PF ou PJ não seja contribuinte de icms, ou seja, é quando tem o calculo de DIFAL na nota que emitimos) 

### 2. Criando login

 **Antes de tudo**. Para o ERP, ter "comunicação", ou seja, funcionar junto com o emissor-fiscal. É necessário ter as 3 propriedades abaixo configuradas. Para isso abra o arquivo **dbf.properties**, que provavelmente esteja em: **C:\DBF\dist**. Caso tenha dúvida, peça a ajuda para alguém de T.I

```
emissor-fiscal.ativo=true
emissor-fiscal.server=http://localhost:8080          (TROCAR essa URL(link), para a dá página inicial do EMISSOR-FISCAL) 
emissor-fiscal.compra.ativo=true                     (Até o momento somente é salvo, a compra no emissor-fiscal, não fazendo nenhum calculo (exceto as de SC que é feito da guia Gare))
```

<p align="left">
  <img src="./doc/Telas do Sistema/01 - Caminho (ERP) para criar usuario.png" width="190" height="400" />
  <img src="./doc/Telas do Sistema/01.1 - Tela (ERP) Cadastrar usuario.png" width="670" height="400" />
</p>

Para criar o login, no ERP vá no menu: **CONTABILIDADE**>**CRIAR USUARIO EMISSOR-FISCAL**. (Somente será mostrada, caso as properties acima estejam configuradas).
Na tela aberta teremos os seguintes botões:

1. **Cancelar** - Fecha a tela aberta.
2. **Atualizar Usuário** - Caso tenha feito, alguma alteração no usuário do ERP (EX.: de Senha) e queira que essas alterações tenham efeito no emissor-fiscal, clique nesse botão.
3. **Efetuar Cadastro** - Irá criar um usuário no emissor-fiscal, com as mesmas informações do ERP.

### 2.1. Criando Login através do envio/exportação de um documento fiscal
Quando o usuário enviar/exportar uma NFE e estiver com as properties acima ativadas, também será criado um usuário (com o Perfil de ***API_ACESS***), veja os outros perfis no enum: 
```
net.cartola.emissorfiscal.usuario.Perfil
```

### 3. Arquivos de configurações (application.properties e bootstrap.properties)
Temos quatro arquivos aplication.properties, sendo eles:

|nome|Usado em|
|----|---------|
|application.properties|Arquivo principal, todos os application dos perfis abaixo, herdarão o que estiver nesse|
|application-dev.properties|Para rodar a aplicação no localhost (etapa de desenvolvimento)|
|application-homologacao.properties|Para fazer o deploy no [GAE](https://cloud.google.com/appengine). E conseguirmos testar a aplicação no mesmo ambiente do usuário |
|application-producao.properties|Para fazer o deploy em produção no GAE|
|application-test.properties|Para rodar os testes no localhost, usando o DB (emissorfiscal_teste) |
|bootstrap.properties|Para utilizarmos o serviço [Secret Manager do GCP](https://cloud.google.com/secret-manager). Quando é feito o deploy esse arquivo é carregado antes do application|
|bootstrap-homologacao.properties|Deploy no projeto do GCP de homologacao|
|bootstrap-producao.properties|Deploy em produção|

PS: No aplication.properties, temos algumas propriedades, que são referente a "regras de negócios". Exemplos: codigos das origens dos produtos que são importados, email para
quem é enviado os calculos das GUIA GARE (entradas de SC, MS e ES) etc...

### 4. "Parametrização/Inserção", das tributações federais e estadual (PIS/COFINS e ICMS) (nos DocumentosFiscais emitidos por nóis)
Dentro da pasta **./doc/scripts**, temos as duas pastas a seguir, que serviram para cadastrar as tributações, em três tabelas (Tais informações foram passados pela Contabilidade/Fiscal): 
  * trib_fede
  * trib_esta
  * trib_esta_guia 

![image](https://user-images.githubusercontent.com/29218270/121573885-1b2fef80-c9fc-11eb-92c8-f32691b1015b.png)

Dentro das tributações federais temos os scripts abaixo (Devem ser rodados na ordem que estão no print)
PS: O último script, contém todos os inserts das duas procedures acima. (Sim, o melhor era ter feito INSERT com subquery, igual foi feito nas trib_esta)
![image](https://user-images.githubusercontent.com/29218270/121576332-a7dbad00-c9fe-11eb-8f45-30b61c7cc4e9.png)

As tributações federais (PIS/COFINS), funcionam basicamente da seguinte forma (estamos considerando a operação 1 - VENDA), Se o NCM é:

```
 Monofásico - CST 04  (Sem tributação, Base de calculo, aliq, e valor imposto ZERADO)
 Se não é monofásico - CST 01 - Pis Aliq = 1,65% | Cofins Aliq = 7,60%
``` 
  OBS: ***Dependendo da operação a CST poderá ser diferente*** (conforme está nos scripts), assim como não ocorrer a incidência de impostos. PORÉM, sempre que um NCM for monofásico essa será a regra que tem prevalência;

### 4.1. trib_esta (inserindo informações referente a Aliq de ICMS, CFOP, CEST COD ANP etc)

- Na primeira pasta do print abaixo, temos o script para inserir a tributação na tabela: **trib_esta_guia** (Todos os ncms que tiverem nessa tabela serão calculados, 
o valor da guia gare quando derem entrada em um DocumentoFiscal)
PS: Ao invés de usar um campo do tipo enum para considerar a Origem do produto (o ideal é trocar, para um boolean para saber se a tributação é ou não para um produto importado)

- **Interestadual** Script com todas as tributações em VENDAS interestaduais de SP x Qualquer outra UF. No caso das operações foi feito o seguinte para saber se tem que calcular ou não difal/fcp. Equivalência de operações: 


|Operação|Equivalente a|
|---|----|
|2 - VENDA INTERESTADUAL (JURIDICA)|Pessoa contribuinte de icms, ou seja, quando usar as tributações dessa operação para fazer o calculo NUNCA será calculado o DIFAL e FCP|
|3 - VENDA INTERESTADUAL (FISICA) |Pessoa não contribuinte, sempre será calculado o DIFAL, e o FCP para os estados que tiverem|

Problemas que possamos ter ao utilizar essa abordagem: Nem todos os PJ, são contribuintes, ou seja, caso calcule, um DocumentoFiscal, cujo o destinatário seja Pessoa Juridica, 
NÃO contribuinte, deverá ser calculado o DIFAL, mas não será. 

PS: Isso será corrigido, no futuro da seguinte forma: Será enviado junto com o **DocumentoFiscal**, se a pessoa é ou não contribuinte de icms, para assim ser buscada a tributação correta.

![image](https://user-images.githubusercontent.com/29218270/121577555-0190a700-ca00-11eb-9597-24be7f46b3c7.png)

- Os dois últimos, scripts do print: Pode se dizer que são "repetidos", já que a tributação dentro do estado não muda quando o produto é ou não importado (porém tem os dois para ser calculados em ambos os casos). A informação do produto ser ou não importado, tem grande importancia nas VENDAS interestaduais, pois é essa aliquota (4%) que tem prevalência
(conforme está nos scripts mencionados acima) caso o produto seja importado.

- Esses dois scripts funcionam com as tributações de vendas, que já foram inseridas previamente, ao iniciar o projeto utilizando o **flyway**

![image](https://user-images.githubusercontent.com/29218270/121580538-39e5b480-ca03-11eb-8562-0ae71ce307e0.png)


## Começando

Clone esse projeto em um diretório de sua máquina

```
git clone https://github.com/cartola-erp/emissor-fiscal.git
```

### Pré requisitos


* 1 - [MySql - v5.7 ou maior](https://www.youtube.com/watch?v=WuBcTJnIuzo)
* 2 - [Java 8](https://www.youtube.com/watch?v=rzto4yY3pVw)
* 3 - [STS - Spring Tool Suite ](https://spring.io/tools#suite-three) (IDE - recomendada)

### Instalando o projeto no ambiente de desenvolvimento

* 1 - [Importe o projeto maven no STS](https://www.lagomframework.com/documentation/1.6.x/java/EclipseMavenInt.html)
* 2 - Crie os seguinte bancos de dados:

```
create database emissorfiscal;
create database emissorfiscal_teste;      (para ser usado em ambiente de teste)
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
