# emissor-fiscal

Projeto criado para um analista fiscal, ser o responsável por manter as tributações estaduais e federais que serão usadas no cálculo, 
de documentos fiscais recebidos. 

### 1. Criando login

 **Antes de tudo**. Para o ERP, ter "comunicação", ou seja, funcionar junto com o emissor-fiscal. É necessário ter as 2 propriedades abaixo configuradas. Para isso abra o arquivo **dbf.properties**, que provavelmente esteja em: **C:\DBF\dist**. Caso tenha dúvida, peça a ajuda para alguém de T.I

```
emissor-fiscal.ativo=true
emissor-fiscal.server=http://localhost:8080          (TROCAR essa URL(link), para a dá página inicial do EMISSOR-FISCAL)  
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

### 1.1 Fazendo Login

**Vá para** o endereço configurado nas properties acima, no nosso exemplo: **http://localhost:8080**

![Tela de login](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/01.2%20-%20Tela%20de%20Login.png?raw=true)

Informe os dados solicitados, e clique no botão **ENTRAR**

### 2. Tela de simulação

Ao efetuar o login no sistema a imagem abaixo, será a tela inicial. Na parte superior temos os seguintes menus:
* **Cadastrar** ([CFOP](https://github.com/cartola-erp/emissor-fiscal/#3-cadastrando-uma-cfop), 
[NCM](https://github.com/cartola-erp/emissor-fiscal/#4-cadastrando-um-ncm), 
[Estado](https://github.com/cartola-erp/emissor-fiscal/#5-cadastrando-um-estado), 
[Operação](https://github.com/cartola-erp/emissor-fiscal/#6-cadastrando-uma-opera%C3%A7%C3%A3o), 
[Tributação Estadual](https://github.com/cartola-erp/emissor-fiscal#7-cadastrando-uma-tributa%C3%A7%C3%A3o-estadual) e 
[Federal](https://github.com/cartola-erp/emissor-fiscal#7-cadastrando-uma-tributa%C3%A7%C3%A3o-federal))
* **Consultar** ([CFOP](https://github.com/cartola-erp/emissor-fiscal/blob/master/README.md#7-consultando-uma-cfop),
[NCM](https://github.com/cartola-erp/emissor-fiscal/blob/master/README.md#8-consultando-um-ncm),
Estado, Operação, Tributação Estadual e Federal)
* **Simulação** ([Essa tela](https://github.com/cartola-erp/emissor-fiscal/#2-tela-de-simula%C3%A7%C3%A3o))
* **Sair** (Irá deslogar do sistema, voltando assim para a página de login)

Já essa página inicial, servirá apenas para: Fazer uma simulação de como as informações de um item deverá sair num documento fiscal. E para isso ser feito será preciso, "alimentar/parametrizar o sistema nas telas seguintes".

![Simulação de calculo](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/02%20-%20Simula%C3%A7%C3%A3o.png)
**Figura 02** - Simulação de cálculo 

### 3. Cadastrando uma CFOP

Informe o número de 4 dígitos da CFOP e sua descrição. 

![Cadastrar CFOP](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/03%20-%20Cadastrar%20CFOP.png)
**Figura 03** - Cadastrar CFOP 

Após preencher o formulário acima, ao clicar no botão **ALTERAR/CADASTRAR**, a msg abaixo deverá ser mostrada, na parte superior do formulário.

![Mensagem de Alterado/Cadastrado com sucesso](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/12%20-%20Mensagem%20Alterado-Cadastrado%20Sucesso.png?raw=true)

Caso ocorra algum **erro ou falte preencher algum campo**, será mostrada alguma mensagem semelhante a essa embaixo

![Mensagem de erro](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/13%20-%20Mensagem%20Erro.png?raw=true)

### 4. Cadastrando um NCM

Semelhante a tela anterior, nessa deverá ser informado:

1. NCM("classe fiscal"), que deverá ter no máximo 8 digítos
2. Exceção com até 2 números 
3. Descrição do NCM que está sendo cadastrado.
4. Click em "ALTERAR/CADASTRAR"

![Cadastrar NCM](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/04%20-%20Cadastrar%20NCM.png?raw=true)
**Figura 04** - Cadastrar um NCM 

### 5. Cadastrando um ESTADO

OBS: O estado será usado, para fazer o cadastro das **tributações estaduais** para isso na tela abaixo:

1. Informe a **sigla** do estado
2. E o nome

![Cadastrar ESTADO](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/05%20-%20Cadastrar%20Estado.png
)
**Figura 05** - Cadastrar um ESTADO 

### 6. Cadastrando uma OPERAÇÃO

Nessa tela tem que informar apenas a descrição da OPERAÇÃO, que é a mesma que será usado no documento fiscal. (EX.: VENDA, VENDA INTERESTADUAL, DEVOLUÇÃO etc)

![Cadastrar uma operação](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/06%20-%20Cadastrar%20Opera%C3%A7%C3%A3o.png?raw=true)
**Figura 06** - Cadastrar uma OPERAÇÃO 

### 7. Cadastrando uma TRIBUTAÇÃO ESTADUAL
OBS IMPORTANTE: Para inserir uma tributação, é OBRIGATÓRIO, realizar os cadastros nas telas anteriores, caso ainda **NÃO EXISTA** essas informações, que serão necessárias para a tributação que será cadastrada.

1. **UF. Origem** - **Estado** de onde a mercadoria está **saindo**.
2. **UF. Destino** - **Estado destino** da "mercadoria".
3. **Operação** - Venda, Venda Interestadual, devolução etc.
4. **NCM** - Ncm do "item" que será cadastrado a **tributação estadual**
5. **Finalidade** - Qual é a finalidade do item? Revenda, Brinde, Doação, Patrimônio ou Consumo ?
6. **Regime tributário do emitente** - O regime tribuário da PESSOA que está emitindo o documento fiscal. SIMPLES, PRESUMIDO ou REAL ?

**OBS:** A COMBINAÇÃO dessas SEIS INFORMAÇÕES, será o que o ERP, usará como base para procurar as ALÍQUOTAS, CST etc (que serão usadas nos cálculos), quando estiver cadastrando um DOCUMENTO FISCAL. Ou seja, mesmo que ja tenha o cadastro para algum NCM, porém na hora do preenchimento do Documento qualquer um dos outros parametros acima for diferente, NÃO FUNCIONARÁ.

Ex.: (Tenho a seguinte tributação cadastrada):
```
UF ORIGEM = SP
UF DESTINO = SP
OPERAÇÃO = VENDA
NCM = 7031011
FINALIDADE = CONSUMO
REGIME DO EMITENTE = REAL
```
Porém estamos preenchendo um documento fiscal, cujo o mesmo terá apenas uma informação diferente do de cima (no nosso exemplo a finalidade):

```
FINALIDADE = REVENDA
```
Logo no preenchimento desse Documento Fiscal (no ERP), receberá uma mensagem semelhante a: "Não existe tributação cadastrada para esse item".

Para corrigir isso deverá realizar o cadastro de uma nova tributação, que se encaixe nessa finalidade.

Já os campos abaixo do formulário, é o que de fato será usado no calculo, para o item, que se encaixe nas combinações acima.

7. **ICMS CST** - Código da substitução tributária do icms.
8. **ICMS BASE RED.** - Porcentagem de redução na base de calculo. Ex.: Se informar 20% O sistema irá reduzir a base do produto em 80%, ou seja, apenas será considerado 20% do valor do produto, para realizar os calculos dos impostos estaduais. (
9. **ICMS Aliq.** - **Alíquota** do estado de **origem**
10. **ICMS IVA/MVA** - Porcentagem(referente ao estado de origem) de IVA/MVA do item que está sendo cadastrado. Ex.: Se informar 70%, nesse campo, o sistema irá considerar 70% do valor do produto. (Mesmo funcionamento que o campo:  **ICMS BASE RED.**). 
11. **Aliq. Interna Destino** - **Alíquota** do estado de **destino**
12. **FCP Aliq.** Alíquota do Fundo de combate a pobreza, do estado de destino.
13. **ICMS ST Aliq.** Alíquota do icms de substituição tributária.
14. **CEST** - Código especificador da substituição tributária. (Relacionado ao NCM)
15. **CFOP** - Qual a CFOP, deverá sair para o item/produto, conforme as 6 primeiras opções preenchidas? (uf origem, destino, operação, ncm, finalidade, e regime tributário do emitente).
16. **Mensagem** Uma observação/mensagem, para essa tributação. (não há interferência no cálculo)

![Cadastrar uma tributação estadual](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/07%20-%20Cadastrar%20Tributacao%20Estadual%20-%20(ICMS).png?raw=true)
**Figura 06** - Cadastrar uma TRIBUTAÇÃO ESTADUAL 

### 7. Cadastrando uma TRIBUTAÇÃO FEDERAL

Na tela de cadastro de **tributação federal**, vale a mesma regra das **combinações** da [TRIBUTAÇÃO ESTADUAL](https://github.com/cartola-erp/emissor-fiscal#7-cadastrando-uma-tributa%C3%A7%C3%A3o-estadual) que o (ERP usará para encontrar as CST, ALIQ. etc, da tributação), com a diferença, que como é uma **TRIBUTAÇÃO FEDERAL**, não precisará dos **UF origem** e nem de **destino**, ou seja, serão as QUATROS combinações abaixo:

1. **Operação** - Venda, Venda Interestadual, devolução etc.
2. **NCM** - Ncm do "item" que será cadastrado a **tributação estadual**
3. **Finalidade** - Qual é a finalidade do item? Revenda, Brinde, Doação, Patrimônio ou Consumo ?
4. **Regime tributário do emitente** - O regime tribuário da PESSOA que está emitindo o documento fiscal. SIMPLES, PRESUMIDO ou REAL ?

![Cadastrar uma tributação federal](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/08%20-%20Cadastrar%20Tributacao%20Federal%20-%20(PIS%20&%20COFINS).png?raw=true)
**Figura 07** - Cadastrar uma TRIBUTAÇÃO FEDERAL 

Já nesses abaixo, são os valores que serão usados nos cálculos:

5. **PIS CST** - Código da substitução tributária do PIS.
6. **PIS Base Red.** - Porcentagem de redução na base de calculo do PIS. Funciona da mesma forma que o campo **ICMS BASE RED.**, no cadastro de tributação estadual.  Ex.: Se informar 20% O sistema irá reduzir a base do produto em 80%, ou seja, apenas será considerado 20% do valor do produto, para realizar os calculos dos impostos federais.
7. **PIS Alíquota** - A alíquota do PIS. (GERALMENTE **1,65%**)
8. **COFINS CST** - Código da substitução tributária do COFINS. (GERALMENTE, é o mesmo que o do PIS)
9. **COFINS Base Red.** - Porcentagem de redução na base de calculo do COFINS. (GERALMENTE, é o mesmo que o do PIS). Mesmo funcionamento que o campo: **PIS Base Red.**
10. **COFINS Alíquota** - A alíquota do COFINS. (GERALMENTE **7,60%**)
11. **IPI CST** - Código da substitução tributária do IPI.
12. **IPI Base** - Porcentagem de redução na base de calculo do IPI.
13. **IPI Alíquota** - A alíquota do IPI.
14. **Mensagem** - Uma observação/mensagem, para essa tributação. (não há interferência no cálculo)

### 7. Consultando uma CFOP

No menu superior vá para **Consultar**>**CFOP**. Será aberta a tela abaixo.
  
![Consultar uma CFOP](https://github.com/cartola-erp/emissor-fiscal/blob/master/doc/Telas%20do%20Sistema/09%20-%20Consultar%20CFOP.png?raw=true)
**Figura 08** - Consultar uma CFOP 

As telas de consulta sempre serão no mesmo padrão:
1. Na parte superior -> Uma **caixa de texto** para informar o parâmetro de pesquisa(**nº da cfop**) de um registro especifico
e ao lado um **botão com icone de lupa**, para procurar.
2. Na parte inferior (tabela) é onde os registros são mostrados. E na mesma sempre terá pelo menos 2 colunas:
  * 1. **[Editar](https://github.com/cartola-erp/emissor-fiscal/blob/master/README.md#9-editandodeletando-uma-cfop)** - Irá carregar a tela de cadastro (do registro daquela linha) com todas as informações cadastradas preenchidas.
  * 2. **[Deletar](https://github.com/cartola-erp/emissor-fiscal/blob/master/README.md#9-editandodeletando-uma-cfop)** - Será deletado o registro daquela linha.

### 8. Consultando um NCM

### 9. Editando/deletando uma CFOP

~~Tudo que for relacionado a "alteração e o delete" de algum registro, sempre irá seguir o **mesmo fluxo**. No menu superior clique, em **Consultar** e no que pretende editar, no nosso exemplo a **CFOP**.
Então a tela abaixo será aberta.~~

~~2. Na tabela embaixo é onde os registros são mostrados. E na mesma sempre terá pelo menos 2 colunas:
  * 1. Editar - Irá carregar a tela de cadastro (do registro daquela linha) com todas as informações cadastradas preenchidas.
  * 2. Deletar - Será deltetado o registro daquela linha.~~
  
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
