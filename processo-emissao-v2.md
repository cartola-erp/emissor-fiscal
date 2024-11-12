# Processo de emissão de documentos fiscais na versão 2.0

* Receber o pedido de emissão de documento fiscal e obter os dados relativos ao emitente (Certificado e Ambiente)
* Preencher os dados fiscais -> CLASSE RESPONSAVEL EmissaoPrenchimentoDadosFiscais, 
* Criar o XML a partir dos dados fiscais preenchidos -> EmissaoCriacaoXml, 
* Enviar o XML para a SEFAZ
* Imprimir o DANFE (Opcional)

# AMQP

O emissor em sua versão 2.0 continuará repondento a API 1.0 e também responderá as requisições AMQP.
Na string abaixo altere {USUARIO} pelo seu usuário do Windows, também considere que estamos colocando em uma estrutura de diretório tmp\amqp.

```shell
docker run -d --name rabbitmq312 -p 5672:5672 -p 15672:15672 -v "C:\Users\{USUARIO}\tmp\ampq:/var/lib/rabbitmq/mnesia" rabbitmq:3.12-management
```

Para o Mac, o comando fica como abaixo

```shell
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -v /Users/{USUARIO}/ampq:/var/lib/rabbitmq/mnesia rabbitmq:3.12-management
```

## Acesso ao RabbitMQ

[Acesso ao painel administrativo](http://localhost:15672)

O usuário administrador da instância é guest com senha guest.