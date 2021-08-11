package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 11/09/2020
 * @author robson.costa
 * 
 * 5.4 - Tabela de Códigos das Obrigações de ICMS a Recolher
 */
public enum ObrigacaoIcmsARecolher implements EnumCodificado {

	/**
	 * 000
	 */
	ICMS_RECOLHER("000"),
    
	/**
	 * 001
	 */
	ICMS_SUBSTITUICAO_ENTRADAS("001"),
    
	/**
	 * 002
	 */
	ICMS_SUBSTITUICAO_SAIDAS_ESTADO("002"),
    
	/**
	 * 003
	 */
	ANTECIPACAO_DIFERENCIAL_ALIQUOTAS("003"),
	
	/**
	 * 004
	 */
    ANTECIPACAO_ICMS_IMPORTACAO("004"),
    
    /**
	 * 005
	 */
    ANTECIPACAO_TRIBUTARIA("005"),
    
    /**
	 * 006
	 */
    ALIQUOTA_ADICIONAL_FUNDO_POBREZA("006"),
    
    /**
	 * 090
	 */
    OUTRAS_OBRIGACOES_ICMS("090"),
    
    /**
	 * 999
	 */
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
