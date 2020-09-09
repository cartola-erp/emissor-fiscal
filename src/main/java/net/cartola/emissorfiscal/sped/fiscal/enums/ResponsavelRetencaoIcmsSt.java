package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 09/09/2020
 * @author robson.costa
 */
public enum ResponsavelRetencaoIcmsSt implements EnumCodificado {
	
	REMETENTE_DIRETO("1"),
	REMETENTE_INDIRETO("2"),
	PROPRIO_DECLARANTE("3");
	
	private String codReponsavelRet;
	 
	ResponsavelRetencaoIcmsSt(String codReponsavelRet) {
        this.codReponsavelRet = codReponsavelRet;
    }
	
	@Override
    public String getCodigo() {
        return codReponsavelRet;
    }

}
