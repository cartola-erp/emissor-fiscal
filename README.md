# emissor-fiscal

Projeto criado para um analista fiscal, ser o responsável por manter as tributações estaduais e federais que serão usadas no cálculo, 
de documentos fiscais de saídas (emitidos), assim como a geração do arquivo SPED FISCAL (EFD ICMS IPI). Até o Momento funciona basicamente da seguinte forma:  



### 1. BREVE RESUMO
---- 
- Ao receber um **DocumentoFiscal (de emissão própria, que geralmente é de saída)**, com as devidas tributações cadastradas, será calculado os impostos (que é retornado num JSON), caso não tenha a tributação para algum item do DocumentoFiscal, não será calculado NADA, e será apenas retornado uma mensagem avisando que falta X tributação para o NCM do item. (A intenção no futuro é fazer com que esse projeto também faça toda a parte de comunicação com a SEFAZ (geração do xml, envio, cancelamento, consulta etc....)

- Ao receber um **DocumentoFiscal (de devolução/remessa em garantia, que podem ser de entrada ou saída, mas que são sempre emitido pela autogeral)**, com a devida parametrização na tabela **(trib_esta_devo)**, e o cadastro correto da operação **(oper)**, indicando que a operação é de **devolução** OU **remessa em garantia**, será realizado o calculo do ICMS. **Caso não** esteja parametrizado para alguma operação provavelmente **irá ocorrer algum NullPointerException**.

- Ao receber um **DocumentoFiscal (de emissão de terceiros, que é de entrada)**, esse **"documento"** apenas será salvo na tabela **docu_fisc**. Exceto se for alguma NFE que **seja de SC, ES, MG** (nesses casos, será verificado na tabela **trib_esta_guia**, se para algum item dessa nota de entrada, teremos que recolher o **ICMS ST** pela Guia gare, caso sim será enviado um email para o setor fiscal, utilizando API do sendgrid com os devidos cálculos e retornado um JSON com os valores desse cálculo). 
  * PS¹: Atualmente, toda a parte do cálculo de impostos na entrada que teremos crédito é feita pelo ERPJ.
  * PS²: Não é feita nenhuma emissão de guia gare (das entrada de SC, ES e MG), pois não encontrei nenhuma forma de integração para fazer isso. Por isso, é enviado apenas um email para o grupo **@fiscal**, com os valores a **recolher de ICMS ST**.
  * PS³: O cálculo não é salvo em nenhuma tabela desse projeto, portanto é apenas retornado em um JSON, que será salvo na tabela: **calc_gare_compra_item**, do **ERPJ**;

- **SPED FISCAL** -> Parte que está atualmente em **TESTE**. Antes de começarmos a gerar os arquivos, é necessário que de fato todos os DocumentoFiscais sejam salvos nesse projeto (hoje em dia é a maioria). Ao menos nesse primeiro momento, a preocupação é fazer com que gere o arquivo corretamente igual é gerado hoje em dia utilizando o software de terceiros. 
	<br/> A estrutura em si do Layout eu estava conseguindo gerar corretamente e iria começar a verificar se as partes referentes a valores estão sendo feita(calculadas) corretamente.(a melhor forma seria validar com todos os documentos), Porém dá para fazer isso com alguns registros especificos, Ex.: REGs: C197, D197, Bloco E  etc... Pois são referentes a uma única NFE (basta verificar algumas de formas "amostral" e verificar se os valores desses registros estão iguais ao que estão no arquivo gerado pelo sistema de terceiros);

~~**TODO**-> Integração para ser emitida as guias GNRE (Que é necessário quando vendemos para outro estado e a pessoa seja PF ou PJ não seja contribuinte de icms, ou seja, é quando tem o calculo de DIFAL na nota que emitimos)~~

### 2. Criando login

#### 2.1. Properties necessárias (no ERP)

 **Antes de tudo**. Para o ERP, ter "comunicação", ou seja, funcionar junto com o emissor-fiscal. É necessário ter as propriedades abaixo configuradas. Para isso abra o arquivo **dbf.properties**, que provavelmente esteja em: **C:\DBF\dist**. Caso tenha dúvida, peça a ajuda para alguém de T.I.

```
emissor-fiscal.ativo=true
emissor-fiscal.compra.ativo=true					(Até o momento somente é salvo, a compra no emissor-fiscal, não fazendo nenhum calculo (exceto as de SC que é feito da guia Gare))
operacoes.devolucao.pelo.emissor-fiscal=6,7,23,28,39,40,10,11,29,30,21,84,8,9		(As operações que tiverem nessa propertie são as de devoluções que serão calculadas pelo emissorfiscal )

emissor-fiscal.server=http://localhost:8080				(TROCAR essa URL(link), para a dá página inicial do EMISSOR-FISCAL) 
emissor-fiscal-homologacao.server=http://localhost:8080			(Caso a propertie de envio de NFE no ERP seja para homologação (**nfe.ambiente=2**), será usado a URL, que estiver nessa propertie para fazer requisições para o emissorfiscal)
```

#### 2.2. Criando login atráves da tela de criação

<p align="left">
  <img src="./doc/Telas do Sistema/01 - Caminho (ERP) para criar usuario.png" width="190" height="400" />
  <img src="./doc/Telas do Sistema/01.1 - Tela (ERP) Cadastrar usuario.png" width="670" height="400" />
</p>

Para criar o login, no ERP vá no menu: **CONTABILIDADE**>**CRIAR USUARIO EMISSOR-FISCAL**. (Somente será mostrado esse menu, caso as properties acima estejam configuradas).
Na tela aberta teremos os seguintes botões:

1. **Cancelar** - Fecha a tela aberta.
2. **Atualizar Usuário** - Caso tenha feito, alguma alteração no usuário do ERP (EX.: de Senha) e queira que essas alterações tenham efeito no emissor-fiscal, clique nesse botão.
3. **Efetuar Cadastro** - Irá criar um usuário no emissor-fiscal, com as mesmas informações do ERP.

#### 2.3. Criando Login através do envio/exportação de um documento fiscal
Quando o usuário enviar/exportar uma NFE e estiver com as properties acima ativadas, também será criado um usuário (com o Perfil de ***API_ACESS***),

#### 2.4. Perfis
|    Perfil	|			Permissões					|
|---------------|-----------------------------------------------------------------------|
|ADMIN		|Acesso a tudo								|
|WEB_ACESS	|Somente consulta nas páginas WEB					|
|CONTADOR	|Consegue fazer alterações em tributações, operações etc.Nas telas WEB.	|
|ESCRITURADOR	|Consegue gerar o arquivo do SPED FISCAL ICMS IPI			|
|API_ACESS	|Acesso somente para consumir a parte de API desse projeto		|

veja os perfis no enum: 
```
net.cartola.emissorfiscal.usuario.Perfil
```

### 3. Arquivos de configurações (application.properties)
Temos cincum ~~(sim, sou flamenguista, como adivinhou!?)~~ arquivos "application.properties", sendo eles:

|		nome			|							 Usado em								  |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
|application.properties			|Arquivo principal, todos os application dos perfis abaixo, herdarão o que estiver nesse					  |
|application-dev.properties		|Para rodar a aplicação no localhost (etapa de desenvolvimento)									  |
|application-homologacao.properties	|Para fazer o deploy no [GAE](https://cloud.google.com/appengine). E conseguirmos testar a aplicação no mesmo ambiente do usuário |
|application-producao.properties	|Para fazer o deploy em produção no GAE												  |
|application-test.properties		|Para rodar os testes no localhost, usando o DB (emissorfiscal_teste) 								  |

PS: No application.properties, temos algumas propriedades, que são referente as "regras de negócios". Exemplos: codigos das origens dos produtos que são importados, email para
quem é enviado os calculos das GUIA GARE (entradas de SC, MS e ES) etc...

#### 3.1. pom.xml (usando maven profile para fazer deploy)

No trecho abaixo está o perfil, que é usado pela linha de comando (maven) para gerar o .WAR e fazer o deploy no GAE (Google App Engine)
```
	<profiles>
		<profile>
			<id>producao</id>
				<dependencies>
					.... 
					Dependências que irão entrar somente no perfil de produção (ou seja, quando estiverem fazendo o deploy no GAE 
					....
				</dependencies>
		</profile>
	</profiles>
```

#### 3.2. appengine-web.xml 

Nesse arquivo estão as configurações referentes ao [GAE](https://cloud.google.com/appengine). Além disso, indica qual o perfil do spring ("application.properties") que estará ativo para fazer deploy. Basicamente será um dos dois abaixo:
```
<property name="spring.profiles.active" value="producao"/>				-> Usado para fazer deploy em produção no projeto: **erpj-br**, do GCP.
<property name="spring.profiles.active" value="homologacao"/>				-> Para fazer deploy em homologação (testes no GCP), projeto: **erpj-dev**
```

### 4. Estrutura de pastas

<details>
  <summary>De forma resumida temos basicamente essa estrutura de pastas/package no projeto (clique aqui para expandir)</summary>

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂net
 ┃ ┃ ┃ ┗ 📂cartola
 ┃ ┃ ┃ ┃ ┗ 📂emissorfiscal -> Aqui estará todas as classes do projeto e os packages de cada "módulo"
 ┃ ┣ 📂resources
 ┃ ┃ ┣ 📂db
 ┃ ┃ ┃ ┗ 📂migration		-> E aqui todos os scripts das "migrations", no nosso caso estamos usando o flyway para isso
 ┃ ┃ ┃ ┃ ┗ 📂mysql
 ┃ ┃ ┃ ┃ ┃ ┣ 📂cadastro-tributacao-clientes-outras-ufs
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜OBS.txt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00019__1_VENDA_PRODUTO_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00020__1_VENDA_PRODUTO_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00021__45_VENDA_FUTURA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00022__45_VENDA_FUTURA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00023__46_REMESSA_FUTURA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00024__46_REMESSA_FUTURA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00025__63_VENDA_DE_SUCATA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00026__63_VENDA_DE_SUCATA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00027__73_COMPRA_DE_SUCATA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00028__73_COMPRA_DE_SUCATA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00029__83_DISTRIBUICAO_GRATUITA_ITEM_ESTOQUE_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜V00030__83_DISTRIBUICAO_GRATUITA_ITEM_ESTOQUE_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📂cadastro-tributacao-interestadual
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00013__3_VENDA_INTERESTADUAL_FISICA_PRODUTO_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00014__3_VENDA_INTERESTADUAL_FISICA_PRODUTO_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00015__2_VENDA_INTERESTADUAL_JURIDICA_PRODUTO_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00016__2_VENDA_INTERESTADUAL_JURIDICA_PRODUTO_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜V00032__AquisicoesInterestaduaisEmitidasPelaAg.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜NCMs - Validado pela Consulcamp.csv
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00002__inserindoNcmsValidadosPelaConsulcamp.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00007__createNcmsMonofasicosEmissorFiscal.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00008__insertIntoTribEstaGuiaGareCompraParaComercia.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00009__insertTribEstaSaidaDentroEstado.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00010__insertTribEstaSaidaDentroEstadoProdutoImportado.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00011__correcaoIcms.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00012__copiandoIcmsParaOsNcmsComVariasExcecoes.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00017__insertTribFede.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00018__insertTribFedeMonofasico.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V0001__Init.sql
 ┃ ┃ ┃ ┃ ┃ ┗ 📜V00031__insertTribEstaDevo.sql
 ┃ ┃ ┣ 📂public
 ┃ ┃ ┃ ┗ 📂error
 ┃ ┃ ┃ ┃ ┣ 📜400.html
 ┃ ┃ ┣ 📂src
 ┃ ┃ ┃ ┗ 📂assets
 ┃ ┃ ┃ ┃ ┗ 📂scripts
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📂assets
 ┃ ┃ ┃ ┃ ┗ 📂static
 ┃ ┃ ┃ ┃ ┃ ┣ 📂fonts
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂icons
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂fontawesome
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂themify
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┣ 📂fonts
 ┃ ┃ ┃ ┣ 📂img
 ┃ ┃ ┃ ┣ 📂js
 ┃ ┃ ┃ ┃ ┣ 📂ncm
 ┃ ┃ ┃ ┃ ┣ 📂tilt
 ┃ ┃ ┃ ┣ 📂src
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┣ 📜Htmls do projeto
 ┃ ┃ ┣ 📜application-dev.properties
 ┃ ┃ ┣ 📜application-homologacao.properties
 ┃ ┃ ┣ 📜application-producao.properties
 ┃ ┃ ┣ 📜application-test.properties
 ┃ ┃ ┗ 📜application.properties
 ┃ ┗ 📂webapp
 ┃ ┃ ┗ 📂WEB-INF
 ┃ ┃ ┃ ┣ 📜appengine-web.xml
 ┃ ┃ ┃ ┗ 📜logging.properties
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂net
 ┃ ┃ ┃ ┗ 📂cartola
 ┃ ┃ ┃ ┃ ┗ 📂emissorfiscal

```
</details>

#### 4.1 Cadastrando as tributações federais (PIS/COFINS) e estaduais (ICMS), usando os scripts pelo flyway
Um ponto de extrema importância é a parte abaixo onde estão os scripts, que servem para cadastrar as tributações estaduais (venda, transferência, devolução e outras operações que emitimos NFEs ou até mesmo para calcular o ICMS ST para as entradas de SC, ES e MS (temos que pagar a guia gare em alguns casos dessas UFs). E além disso a parametrização para o PIS/COFINS)

- Tabelas com as regras que serão aplicadas nos cálculos

|	Tabela	      |			Model			|				Responsável Por						|
|---------------------|-----------------------------------------|---------------------------------------------------------------------------------------|
| **trib_esta**       |		TributacaoEstadual.java		| **ICMS**, nas operações de vendas, transferências entre outras movimentações	     	|
| **trib_esta_guia**  |	TributacaoEstadualGuia.java		| **ICMS ST**, na operação de Compra Para Comercialização interestadual (SC, ES e MG)	|
| **trib_esta_devo**  |	TributacaoEstadualDevolucao.java	| **ICMS**, nas operações de devoluções e remessas em garantias				|	
| **trib_fede**       |		TributacaoFederal.java		| **PIS/COFINS**, nas operações de vendas, transferências, devoluções, remessas em garantias etc...|

- Scripts pelo flyway, para parametrizações nas tabelas: 

```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂net
 ┃ ┃ ┃ ┗ 📂cartola
 ┃ ┣ 📂resources
 ┃ ┃ ┣ 📂db
 ┃ ┃ ┃ ┗ 📂migration
 ┃ ┃ ┃ ┃ ┗ 📂mysql
 ┃ ┃ ┃ ┃ ┃ ┣ 📂cadastro-tributacao-clientes-outras-ufs			->  tabela: **(trib_esta)**: Aqui está sendo parametrizado algumas operações que são usadas no "Balcão", mas o cliente possa ser de outro estado (Ex.: Estamos VENDENDO para um cliente na loja física, mas ele não é de SP, a parametrização estará dentro dessa pasta, CASO NÃO, esteja basta acrescentar outro script para a operação em especifíco, no mesmo padrão das já existentes)
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜OBS.txt
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00019__1_VENDA_PRODUTO_IMPORTADO.sql							
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00020__1_VENDA_PRODUTO_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00021__45_VENDA_FUTURA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00022__45_VENDA_FUTURA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00023__46_REMESSA_FUTURA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00024__46_REMESSA_FUTURA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00025__63_VENDA_DE_SUCATA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00026__63_VENDA_DE_SUCATA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00027__73_COMPRA_DE_SUCATA_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00028__73_COMPRA_DE_SUCATA_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00029__83_DISTRIBUICAO_GRATUITA_ITEM_ESTOQUE_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜V00030__83_DISTRIBUICAO_GRATUITA_ITEM_ESTOQUE_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📂cadastro-tributacao-interestadual			-> tabela: **(trib_esta)**: Aqui está sendo parametrizado, de fato as operações que são interestaduais, inclusive no caso das VENDAS INTERESTADUAIS é onde tem diferenças de aliquotas caso o produto SEJA IMPORTADO, diferente das outras situações que geralmente tem a tributação apenas para que o sistema encontre para quando o produto for NACIONAL ou IMPORTADO, já que é usado a mesma query.
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00013__3_VENDA_INTERESTADUAL_FISICA_PRODUTO_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00014__3_VENDA_INTERESTADUAL_FISICA_PRODUTO_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00015__2_VENDA_INTERESTADUAL_JURIDICA_PRODUTO_IMPORTADO.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜V00016__2_VENDA_INTERESTADUAL_JURIDICA_PRODUTO_NACIONAL.sql
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜V00032__AquisicoesInterestaduaisEmitidasPelaAg.sql
 ┃ ┃ ┃ ┃ ┃ ┣ 📜NCMs - Validado pela Consulcamp.csv			->.CSV, que criei para deixar os ncms validados pela consulcamp. Usei para inserir aqueles que ainda não estavam cadastrados no emissorfiscal, e sempre davam b.o para enviar o SAT.
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00001__Init.sql						-> Tem a estrutura básica do banco de dados. E a população de algumas tabelas no banco. Ex.: estado, trib_esta, oper...
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00002__inserindoNcmsValidadosPelaConsulcamp.sql		-> Script que lê o .csv acima, pelo flyway não deu certo, portanto os ncms desse .csv estão nesse script, e insere na tabela: **(trib_esta)**, o icms de VENDA e TRANSFERÊNCIA. Foi importante colocar esse script aqui nessa ordem, para que o script **V00012__copiandoIcmsParaOsNcmsComVariasExcecoes.sql**, copie o icms desses ncms para as outras execeções deles caso eles tenham mais de uma. (Para o ICMS a tributação é a mesma para o ncm em todas as suas exceções)
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00007__createNcmsMonofasicosEmissorFiscal.sql		-> Cria a tabela de **ncms_monofasicos**, e insere nela todos os ncms monofásicos (aqui a exceção do ncm é de extrema importância). Essa tabela é para facilitar nos inserts das tributações federais (trib_fede), que estão um pouco mais abaixo. 
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00008__insertIntoTribEstaGuiaGareCompraParaComercia.sql		-> tabela: **(trib_esta_guia)**: Aqui está a parametrização dos calculos das "Guias Gare". São calculadas toda vez que dão entrada no ERPJ. Quando salvam uma compra cujo a UF seja diferente de SP, ou seja compra interestadual: (SC, MS, ES), será buscado a parametrização nessa tabela caso tenha para algum item, será calculado e enviado no email (grupo @fiscal) os calculos! PS: Os ncms nesse script a Gabi/fiscal foi me passando ao longo do tempo.
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00009__insertTribEstaSaidaDentroEstado.sql			->  tabela: **(trib_esta)**: Insert de icms, para outras operações que são de saídas. (Ou que ao menos a autogeral faça a emissão de NFe)
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00010__insertTribEstaSaidaDentroEstadoProdutoImportado.sql	->  tabela: **(trib_esta)**: Mesma coisa do script acima. PORÉM para os produtos que SÃO IMPORTADOS. Com o acréscimo que aqui tem o insert para VENDA e TRANSFERENCIA. Para essas duas operações quando é nacional eles estão no script **V0001__Init.sql**, ou no **V00002__inserindoNcmsValidadosPelaConsulcamp.sql**
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00011__correcaoIcms.sql					-> tabela: **(trib_esta)**:  É o script para corrigir a tributação de ICMS em (vendas/transferências/entrega futura/distribuicao de brindes, a maioria dos ncms que estão nesse script estavam cadastrados errado no emissorfiscal)
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00012__copiandoIcmsParaOsNcmsComVariasExcecoes.sql	-> tabela: **(trib_esta)**: Script que servirá para "copiar", a tributação do ICMS para as outras exceções do NCM. (Caso ele tenha mais de uma) 
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00017__insertTribFede.sql				-> tabela: **(trib_fede)**: Esse script depende do: **V00007__createNcmsMonofasicosEmissorFiscal.sql**, pois será inserido o PIS/COFINS para todos os NCMS que NÃO são monofásicos.
 ┃ ┃ ┃ ┃ ┃ ┣ 📜V00018__insertTribFedeMonofasico.sql			-> tabela: **(trib_fede)**: Já esse script será inserido para todos os NCMS MONOFÁSICOS, ou seja, para todos que estão no script: **V00007__createNcmsMonofasicosEmissorFiscal.sql**.
 ┃ ┃ ┃ ┃ ┃ ┗ 📜V00031__insertTribEstaDevo.sql				-> tabela: **(trib_esta_devo)**: Será inserido todas as parametrizações para as operações de: "Devoluções e Remessas em Garantias".
```

As tributações federais (PIS/COFINS), funcionam basicamente da seguinte forma (estamos considerando a operação 1 - VENDA), Se o NCM é:

```
 Monofásico - CST 04  (Sem tributação, Base de calculo, aliq, e valor imposto ZERADO)
 Se não é monofásico - CST 01 - Pis Aliq = 1,65% | Cofins Aliq = 7,60%
``` 

  OBS: ***Dependendo da operação a CST poderá ser diferente*** (conforme está nos scripts), assim como não ocorrer a incidência dos impostos. PORÉM, sempre que um NCM for monofásico essa será a regra que tem prevalência;

#### 4.2. Funcionamento das VENDAS interestaduais (inserindo informações referente a Aliq de ICMS, CFOP, CEST COD ANP etc)

- **Interestadual** Script com todas as tributações em VENDAS interestaduais de SP x Qualquer outra UF. No caso dessas operações foi feito o seguinte para saber se tem que calcular ou não difal/fcp. Equivalência de operações: 

|		Operação		|							Equivalente a									|
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
|2 - VENDA INTERESTADUAL (JURIDICA)	|Pessoa contribuinte de icms, ou seja, quando usar as tributações dessa operação para fazer o calculo NUNCA será calculado o DIFAL e FCP|
|3 - VENDA INTERESTADUAL (FISICA) 	|Pessoa não contribuinte, sempre será calculado o DIFAL, e o FCP para os estados que tiverem						|

Problemas que possamos ter ao utilizar essa abordagem: Nem todos os PJ, são contribuintes, ou seja, caso calcule, um DocumentoFiscal, cujo o destinatário seja Pessoa Jurídica, 
NÃO contribuinte, deverá ser calculado o DIFAL, mas não será (não será pois o usuário, provavelmente selecionou a operação **"...JURIDICA..."**). 

PS: Isso será corrigido, no futuro da seguinte forma: Será enviado junto com o **DocumentoFiscal**, se a pessoa é ou não contribuinte de icms, para assim ser buscada a tributação correta.


### 5. breve explicação sobre algumas classes do projeto

#### 5.1 DocumentoFiscal (package extremamente importante), 
* 1 - O projeto é feito utilizando:
	* 1.0 - JAVA 8 
	* 1.1 - JPA/Hibernate
	* 1.2 - Spring boot, data, e security 
	* 1.3 - Thymeleaf (Template engine, responsável por renderizar os .html)
* 2 - Os pacotes do projeto, são separados por "módulos", exemplo na imagem cfop, contador e documento.
   * Podendo ter dentro do pacote todas as camadas (ApiController, Controller, Service, Repository, Model)
 
![image](https://user-images.githubusercontent.com/29218270/121585029-7f58b080-ca08-11eb-9c83-e4c0055b6fdb.png)

**DocumentoFiscalApiController**, é a classe utilizada para fazer integração com o ERP. Através dela terá mapeamentos de recursos para atualizar e ou salvar um DocumentoFiscal, seja de entrada ou de saída. Nela também é que está "integrado", a chamada dos métodos na service para calcular os impostos (que é necessários em todos os DocumentoFiscais, que emitimos); Além disso nela também está mapeado a rota que irá salvar uma Devolucao, e retornar um **DocumentoFiscal**, com os calculos dos tributos.

**DocumentoFiscalService**, classe onde tem as regras de negócio referente ao **DocumentoFiscal**;

**CalculoFiscalEstadual**, classe reponsável por fazer todo o cálculo de **ICMS**, para um **DocumentoFiscal** e todos os seus itens;

**CalculoFiscalFederal**, responsável por fazer todo o cálculo de **PIS/COFINS**, e **IPI** (esse último exclusivamente para devoluções/remessas em garantias);

**CalculoGuiaEstadualService**, responsável por fazer o cálculo das guia gare **(ICMS ST)**, das entradas de comercialização que são de **SC, ES e MS**;

**TributacaoEstadualApiController**, controller criada para o **e-commerce**, buscar as alíquotas dos calculos das GNRE (VENDAS INTERESTADUAIS);

#### 5.2 Operacao
- Essa classe é a model da tabela de operações do emissorfiscal tabela (**oper**). Um ponto **importante** nela, é que o emissorfiscal entende quando é uma **operação** de **devolução ou remessa em garantia**, pelas propriedades booleanas, que tem na classe, sendo elas respectivamentes **isDevolucao** ou **isRemessaParaFornecedor**;


### 6. EFD ICMS IPI (SpedFiscal ICMS IPI)
É um dos módulos que faz parte do projeto: [SPED - Sistema Público De Escrituração Digital](http://sped.rfb.gov.br/projeto/show/274), assim como os documentos fiscais NF-e, CT-e, NFS-e.

* Essa parte ainda está em desenvolvimento (fase de testes), porém o layout do arquivo foram criados da seguinte forma: cada bloco tem seu pacote com sua modelagem e outro exclusivamente para as services(regras de preenchimento de cada registro):

![image](https://user-images.githubusercontent.com/29218270/121586857-8a144500-ca0a-11eb-86b1-3001ca3faeb6.png)

- 1 - Cada registro tem sua classe (isso equivale a uma linha no arquivo txt);
- 2 - Um registro de Nivel DOIS tem como um (objeto) "registro filho" o que é de nível 3, E o de nível três terá o registro de Nível QUATRO, como registro filho (essa informação de registros filhos etc é consultado na documentação do governo)...
- 3 - Exemplo Registro C100 (RegC100.java), que além de seus campos, tem os registros filhos que podem ou não serem preenchidos, porém somente poderão caso tenha preenchido a linha do REG C100. 

Cada pacote da imagem acima basicamente é referente a um Bloco do [Sped Fiscal ICMS IPI](http://sped.rfb.gov.br/estatico/8D/519392B83F160FA92AF2A21532ADDC16703E1B/Guia%20Pr%c3%a1tico%20EFD%20-%20Vers%c3%a3o%203.0.8.pdf);

Ex.: 
```
net.cartola.emissorfiscal.sped.fiscal.bloco0						-> Todas Models/Registros (pelo menos as que usamos), referentes ao Bloco 0 
net.cartola.emissorfiscal.sped.fiscal.bloco0.service					-> Services Referentes aos Registros do Bloco 0 (Na maioria das vezes são services referentes a "registros de nível 2" (conforme a documentação da EFD ICMS IPI). Nelas é que estaram de fato o preenchimento dos Registros de Cada Bloco.
```

<details>
  <summary>Atualmente temos esses Blocos abaixo no SPED ICMS IPI (clique aqui para expandir)</strong></summary>


| Bloco	|			Descrição				| 	Foi implementado ?	 |			
|-------|---------------------------------------------------------------|--------------------------------|
|0	| Abertura, Identificação e Referências				|   	**Sim** 		 |
|B***	| Escrituração e Apuração do ISS				|	Não precisamos		 | 
|C 	| Documentos Fiscais I – Mercadorias (ICMS/IPI)      		|	**Sim**			 |	
|D 	| Documentos Fiscais II – Serviços (ICMS)            		|	**Sim**   		 |	
|E 	| Apuração do ICMS e do IPI                          		|       **Sim**        		 |
|G*	| Controle do Crédito de ICMS do Ativo Permanente – CIAP	|        Não        		 |
|H 	| Inventário Físico                                        	|       **Sim**       		 |
|K**	| Controle da Produção e do Estoque                      	|       Não precisamos		 |
|1 	| Outras Informações                                       	|       **Sim**       		 |
|9 	| Controle e Encerramento do Arquivo Digital               	|       **Sim**        		 |
</details>

![image](https://user-images.githubusercontent.com/29218270/121588139-f3e11e80-ca0b-11eb-8a80-17c36a10b1f8.png)

- **SpedFiscalArquivoController** -> Classe que irá carregar a tela, para a geração do arquivo SPED e receber os parametros (data inicio, fim, loja, contador, se exportará ou não o inventário) para processar o arquivo.
- **SpedFiscalArquivoService** -> As informações acima serão passadas para o método **gerarAquivoSpedFiscal(...)**, da Service, que será responsável por buscar todas as informações fiscais do período, e popular um objeto do tipo **MovimentoMensalIcmsIpi**
- **SpedFiscalService** -> Recebe um objeto do tipo **MovimentoMensalIcmsIpi**, e chama as services de cada bloco, para preencher os registros do SpedFiscal, que precisamos escriturar e devolve um objeto do tipo **SpedFiscal** que nada mais é do que a modelagem que citamos anteriormente (SpedFiscal -> Blocos do Sped -> Registros dos Blocos)
- **SpedFiscalArquivoService** -> Com todos os registros necessários preenchidos/escriturados, ainda dentro do método **gerarArquivoSpedFiscal(...)**, será chamado o método 
**gerarArquivoSped(....)**, dentro dessa service, será "transformado" o objeto preenchido anteriormente, no arquivo **.txt** (utilzando a **lib: coffeepot-bean-wr**). E por fim  o arquivo será salvo na tabela **sped_fisc_aqu** (SpedFiscalArquivo). 
- **OBS: No futuro isso será mudado, para salvar somente o link do arquivo que está no bucket do GCP.**

## 7. Começando

Clone esse projeto em um diretório de sua máquina

```
git clone https://github.com/cartola-erp/emissor-fiscal.git
```

### 7.1 - Pré requisitos


* 1 - [MySql - v5.7 ou maior](https://www.youtube.com/watch?v=WuBcTJnIuzo)
* 2 - [Java 8](https://www.youtube.com/watch?v=rzto4yY3pVw)
* 3 - [STS - Spring Tool Suite ](https://spring.io/tools#suite-three) (IDE - recomendada)
* 4 - [Maven 3.6.3 (ou que tenha suporte para a JDK 8)](https://maven.apache.org/docs/3.6.3/release-notes.html)
* 5 - [SDK/CLI do GCP - Google Cloud Platform](https://cloud.google.com/sdk/docs/install) - Para realizar o deploy da aplicação na "nuvem"

É Necessário estar configurado corretamente as variáveis de ambientes ! (JAVA e MAVEN)

### 7.2 - Rodando o projeto no ambiente de desenvolvimento

* 1 - [Importe o projeto maven no STS](https://www.lagomframework.com/documentation/1.6.x/java/EclipseMavenInt.html)
* 2 - Crie os seguinte bancos de dados:

```
create database emissorfiscal;
create database emissorfiscal_teste;      (para ser usado em ambiente de teste)
```

PS: Necessário ter o usuário root com a senha root (ou alterar no **application.properties**, para a que você deseja)

## 8. Deployment -- ATUALIZADO NO DIA 05/09/2024 ---- 

<!-- VERSÃO ANTIGA DA REALIZAÇÃO DO DEPLOYMENT 

### 8.a Deployment no GAE (Google App Engine)
- 1. Necessário deixar o perfil correto definido no arquivo **appengine-web.xml** (homologacao ou producao
![image](https://user-images.githubusercontent.com/29218270/155608033-37b247f7-ce57-4e92-87de-a10ce13d697e.png)
 
- 2. Colocar a chave do Sendgrid, no **application.properties**. Propriedade: "**spring.sendgrid.api-key=**"   (senão, não será enviado os emails das guias gare (entradas de SC, ES e MS)
- 3. Ter instalado na máquina o [SDK do GCP](https://cloud.google.com/sdk/docs/install)
- 4. Pelo CMD: 
	- 4.1 Se autentique no GCP, para isso digite ->  **gcloud auth list**
	- 4.2 Verifique os projetos que você tem permissão -> **gcloud projects list**
	- 4.3 Defina um desses projetos (o que será feito o deploy da aplicação) -> **gcloud config set project NOME__DO__PROJETO** | (Atualmente erpj-dev é o de homologacao e o erpj-br o de producao).
	- 4.4 Rode o **deploy.bat**, que está na raiz da pasta do projeto! Ou os comandos abaixo:
		- 4.4.1 ```mvn clean install -Pproducao -DskipTests ``` 
		- 4.4.2 ```mvn package -Pproducao -DskipTests```
		- 4.4.3 ```mvn appengine:deploy -Pproducao -DskipTests```

PS: [Clique aqui para ver sobre o maven profile "-Pproducao"](https://github.com/cartola-erp/emissor-fiscal#31-pomxml-usando-maven-profile-para-fazer-deploy)

### 8.a Deployment no compute engine
- 1 Compile e empacote o arquivo na sua máquina : ```mvn clean package -Pproducao -DskipTests```
- 2 Copie o arquivo jar gerado a máquina de destino, na linha abaixo a máquina é o a emissorfiscal do projeto erpj-br.
```
gcloud compute scp target\emissor-fiscal-0.1.jar muril@emissorfiscal:/home/muril --project=erpj-br
```

--> 

### Deployment na VM do google cloud 

<p>
Para iniciar o <b>Deployment</b> na vm do google é necessario antes criar um serviço para rodar o emissor all time <br> 
Fazeremos isso acessando a vm, e utilizando o seguinte comando: 
</p>

```
cd lib/etc/systemd/system/ 
vim emissor-fiscal 
```
<p>
O arquivo de configuração do serviço ficara assim: 
</p>

```
 [Install]
WantedBy=multi-user.target

[Unit]
Description=Web Service fiscal interno autogeral
Requires=cloud-sql-proxy.service
After=cloud-sql-proxy.service

[Service]
Type=simple
WorkingDirectory=/opt/emissor-fiscal
ExecStart=/usr/bin/java -jar -Xmx3G /opt/emissor-fiscal/emissor-fiscal.jar
Restart=always
StandardOutput=journap[Install]
WantedBy=multi-user.target
```
<p>
Apos a configuração do arquivo, iremos criar uma pasta que receberá o aquivo JAR do projeto do emissor fiscal, <br> 
o <b>arquivo jar</b> pode ser gerado atraves do comando <b>MVN PACKAGE</b>, apos gerar o arquivo jar usaremos o comando para transferir o arquivo local 
para dentro da vm 
</p>

```
gcloud compute scp C:\Users\wesley.cristian\Documents\intelijj-projetos\emissor-fiscal\target\emissor-fiscal-0.1.jar fiscal:/home/wesley.cristian --zone=southamerica-east1-c --project=erpj-br --recurse
```
<p>
E por ultimo iremos transferir o arquivo da pasta do usuario na vm, para a pasta <b>OPT</b>, que será onde o arquivo do serviço ira buscar para começar a inicialização
e por o deploy no ar 
</p>

```
mv emissor-fiscal-0.1.jar /opt/emissor-fiscal/emissor-fiscal.jar
```

## permissões 
* Para dar acesso do systemd com o usuario root acesso o cloud:
```
sudo semanage fcontext -a -t bin_t "/usr/local/bin/cloud-sql-proxy"
```
```
sudo restorecon -v /usr/local/bin/cloud-sql-proxy
```
* Para o nginx porder acessar localhost:8080:
```
sudo setsebool -P httpd_can_network_connect 1
```

## Autores

* **[Robson Henrique Ramalho Costa](https://github.com/robsonhenriq)** - *Trabalho inicial (Desenvovimento dos calculos das operações das NFEs, Assim como todo o desenvolvimento da lógica do preenchimento dos registros referentes ao SPED FISCAL ICMS IPI)* - [robsonhenriq](https://github.com/robsonhenriq)

Veja também a lista de [contribuidores](https://github.com/cartola-erp/emissor-fiscal/graphs/contributors) que participaram deste projeto.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

