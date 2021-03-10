package net.cartola.emissorfiscal.documento;

/**
 * 09/10/2013 16:40:35
 * @author murilo
 */
public enum ProdutoOrigem {

    NACIONAL,
    ESTRANGEIRA_IMPORTACAO_DIRETA,
    ESTRANGEIRA_ADQUIRIDO_MERCADO_INTERNO,
    NACIONAL_CONTEUDO_IMPORTADO_MAIOR_40,
    NACIONAL_CONFORMIDADE_PROCESSOS,
    NACIONAL_CONTEUDO_IMPORTADO_MENOR_40,
    ESTRANGEIRA_IMPORTACAO_DIRETA_CAMEX,
    ESTRANGEIRA_ADQUIRIDO_MERCADO_INTERNO_CAMEX,
    NACIONAL_CONTEUDO_IMPORTADO_MAIOR_70;
    
    
    public String getDescricaoComOrdinal() {
    	return String.valueOf(this.ordinal()) +" - "+ this.name();
    }
}