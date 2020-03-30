# emissor-fiscal

Projeto criado para um analista fiscal, ser o responsável por manter as tributações estaduais e federais que serão usadas no cálculo, 
de documentos fiscais recebidos. 

### 2. Tela de simulação

Ao efetuar o login no sistema a imagem abaixo, será a tela inicial. Na parte superior temos os seguintes menus:
* **Cadastrar** (CFOP, NCM, Estado, Operação, Tributação Estadual e Federal)
* **Consultar** (CFOP, NCM, Estado, Operação, Tributação Estadual e Federal)
* **Simulação** (Essa tela)
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
