package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 31/08/2020
 * @author robson.costa
 */
public enum GrupoDeConta {
	
	CONTAS_DE_ATIVO("01"),
	CONTAS_DE_PASSIVO("02"),
	PATRIMONIO_LIQUIDO("03"),
	CONTAS_DE_RESULTADO("04"),
	CONTAS_DE_COMPENSACAO("05"),
	OUTRAS("09");

	private String codNaturezaDaConta;
	 
	GrupoDeConta(String codNaturezaDaConta) {
        this.codNaturezaDaConta = codNaturezaDaConta;
    }
 
    public String getCodNaturezaDaConta() {
        return codNaturezaDaConta;
    }
    
}
