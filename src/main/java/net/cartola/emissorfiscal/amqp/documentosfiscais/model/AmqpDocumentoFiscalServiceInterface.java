package net.cartola.emissorfiscal.amqp.documentosfiscais.model;

import br.com.autogeral.emissorfiscal.response.vo.InvoiceResponse;

import java.io.Serializable;

/**
 * Interface para servicos AMQP relacionados ao processamento de emissao de documentos fiscais.
 *
 */
public interface AmqpDocumentoFiscalServiceInterface {

    /**
     * Processa a requisicao de emissao de um documento fiscal.
     *
     * @param jsonRequest - Documento fiscal que contem os dados da requisicao de emissao
     */
    public void processarRequisicaoEmissao(String jsonRequest);


    /**
     * Envia a resposta da requisicao de emissao de um documento fiscal.
     *
     * @param invoiceModel - Resposta da emissao do documento fiscal, contendo os dados do resultado da emissao
     */
    public void enviarRespostaRequisicaoEmissao(InvoiceResponse invoiceModel);

}
