# Processo de emissão de documentos fiscais na versão 2.0

* Receber o pedido de emissão de documento fiscal e obter os dados relativos ao emitente (Certificado e Ambiente)
* Preencher os dados fiscais -> CLASSE RESPONSAVEL EmissaoPrenchimentoDadosFiscais, 
* Criar o XML a partir dos dados fiscais preenchidos -> EmissaoCriacaoXml, 
* Enviar o XML para a SEFAZ
* Imprimir o DANFE (Opcional)