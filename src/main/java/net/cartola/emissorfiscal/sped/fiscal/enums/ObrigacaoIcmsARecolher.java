package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 11/09/2020
 * @author robson.costa
 * 
 * 5.4 - Tabela de Códigos das Obrigações de ICMS a Recolher
 */
public enum ObrigacaoIcmsARecolher implements EnumCodificado {

	ICMS_RECOLHER("000"),
    ICMS_SUBSTITUICAO_ENTRADAS("001"),
    ICMS_SUBSTITUICAO_SAIDAS_ESTADO("002"),
    ANTECIPACAO_DIFERENCIAL_ALIQUOTAS("003"),
    ANTECIPACAO_ICMS_IMPORTACAO("004"),
    ANTECIPACAO_TRIBUTARIA("005"),
    ALIQUOTA_ADICIONAL_FUNDO_POBREZA("006"),
    OUTRAS_OBRIGACOES_ICMS("090"),
    ICMS_SUBSTITUICAO_SAIDAS_OUTROS_ESTADOS("999");

    private final String codigo;

    private ObrigacaoIcmsARecolher(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }
 }
