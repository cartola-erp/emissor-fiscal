package net.cartola.emissorfiscal.amqp.documentosfiscais.model;

/**
 * Define os destinos AMQP para o servico de solcitacao de emissao de docs fiscais (produtores de solicitacao de emissao,
 * consumidores de respostas de emissao e produtores de notificaces dinamicas para produtores).
 *
 * As contantes aqui definidas tambem sao utilizadas no EmissorFiscal e em sistemas solicitantes de emissao de documentos fiscais
 */
public class AmqpDocumentosFiscaisDestinos {

    /**
     * Exchange principal (Topic Exchange) para todos os documentos fiscais
     */
    public static final String EXCHANGE_DOCUMENTOS_FISCAIS_PRINCIPAL = "documentos-fiscais.exchange";

    public static final class NFE {

        /**
         * Fila para o EmissorFiscal consumir requisicoes de emissão de NFe.
         */
        public static final String QUEUE_NFE_EMISSAO_REQUISICAO_NAME = "nfe.emissao.requisicao.queue";

        /**
         * Routing Keys para Envio de requisicoes de emissao na exchange principal
         */
        public static final String ROUTING_KEY_NFE_EMISSAO_REQUSICAO = "nfe.emissao.requisicao.routing-key";

        /**
         * Fila de resposta de Emissão do EmissorFiscal para consumo no WS
         */
        public static final String QUEUE_NFE_EMISSAO_RESPOSTA_NAME = "nfe.emissao.resposta.queue";

        /**
         * Routing Key que o EmissorFiscal usa para enviar respostas de emissão de NFe
         * para a queue de resposta (o WS escuta).
         */
        public static final String ROUTING_KEY_NFE_EMISSAO_RESPOSTA = "nfe.emissao.resposta.routing-key";
    }

    public static final class NFCE {
        /**
         * Fila para o EmissorFiscal consumir requisicoes de emissao de NFCe.
         */
        public static final String QUEUE_NFCE_EMISSAO_REQUISICAO_NAME = "nfce.emissao.requisicao.queue";

        /**
         * Routing Keys para envio de requisicoes de emissao na exchange principal
         */
        public static final String ROUTING_KEY_NFCE_EMISSAO_REQUISICAO = "nfce.emissao.requisicao.routing-key";

        /**
         * Fila de resposta de emissao do EmissorFiscal para consulmo no WS
         */
        public static final String QUEUE_NFCE_EMISSAO_RESPOSTA_NAME = "nfce.emissao.resposta.queue";

        /**
         * Routing Key que o EmissorFiscal usa para enviar respostas de emissão de NFCe
         * para a queue de resposta (o WS escuta).
         */
        public static final String ROUTING_KEY_NFCE_EMISSAO_RESPOSTA = "nfce.emissao.resposta.routing-key";
    }

}
