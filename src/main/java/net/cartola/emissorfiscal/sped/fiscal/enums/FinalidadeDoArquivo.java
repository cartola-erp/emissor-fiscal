package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 19/08/2020
 * @author robson.costa
 */
public enum FinalidadeDoArquivo {
	
	REMESSA_ARQUIVO_ORIGINAL(0),
	REMESSA_ARQUIVO_SUBSTITUTO(1);
	
	private int codFin;
	 
	FinalidadeDoArquivo(int codFin) {
        this.codFin = codFin;
    }
 
    public int getCodFin() {
        return codFin;
    }
    
}
