package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 09/09/2020
 * @author robson.costa
 */
public enum CodTipoDeLigacao implements EnumCodificado{
	
	MONOFASICO('1'), 
	BIFASICO('2'), 
	TRIFASICO('3');
	
	private char codTipoDeLigacao;
	 
	CodTipoDeLigacao(char  codTipoDeLigacao) {
        this.codTipoDeLigacao = codTipoDeLigacao;
    }
	
	@Override
    public String getCodigo() {
        return String.valueOf(codTipoDeLigacao);
    }
    
}
