package net.cartola.emissorfiscal.sped.fiscal.enums;


/**
 * 19/08/2020
 * @author robson.costa
 */
public enum FinalidadeDoArquivo implements EnumCodificado {
	
	REMESSA_ARQUIVO_ORIGINAL("0"),
	REMESSA_ARQUIVO_SUBSTITUTO("1");
	
	private String codFin;
	 
	FinalidadeDoArquivo(String codFin) {
        this.codFin = codFin;
    }
	
	@Override
    public String getCodigo() {
        return codFin;
    }
    
}
