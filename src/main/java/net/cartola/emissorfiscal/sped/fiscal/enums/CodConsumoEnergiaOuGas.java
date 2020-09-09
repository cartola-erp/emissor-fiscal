package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 09/09/2020
 * @author robson.costa
 * 
 * USADO no Registro C500
 */
public enum CodConsumoEnergiaOuGas implements EnumCodificado{
	
	COMERCIAL("01"), 
	CONSUMO_PROPRIO("02"), 
	ILUMINACAO_PUBLICA("03"), 
	INDUSTRIAL("04"), 
	PODER_PUBLICO("05"), 
	RESIDENCIAL("06"), 
	RURAL("07"), 
	SERVICO_PUBLICO("08");

	private String codConsEnergiaOuGas;
	 
	CodConsumoEnergiaOuGas(String codConsEnergiaOuGas) {
        this.codConsEnergiaOuGas = codConsEnergiaOuGas;
    }
	
	@Override
    public String getCodigo() {
        return codConsEnergiaOuGas;
    }
    
}
