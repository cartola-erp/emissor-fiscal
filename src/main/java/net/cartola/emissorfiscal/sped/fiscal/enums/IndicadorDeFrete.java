package net.cartola.emissorfiscal.sped.fiscal.enums;

/**
 * 02/09/2020
 * @author robson.costa
 */
public enum IndicadorDeFrete {
	
	CONTRATACAO_DO_FRETE_POR_CONTA_DO_REMETENTE_CIF(0),
	CONTRATACAO_DO_FRETE_POR_CONTA_DO_DESTINATARIO_FOB(1),
	CONTRATACAO_DO_FRETE_POR_CONTA_DE_TERCEIROS(2),
	TRANSPORTE_PROPRIO_POR_CONTA_DO_REMETENTE(3),
	TRANSPORTE_PROPRIO_POR_CONTA_DO_DESTINATARIO(4),
	SEM_OCORRENCIA_DE_TRANSPORTE(9);
	
	private int indFrt;
	 
	IndicadorDeFrete(int indFrt) {
        this.indFrt = indFrt;
    }
 
    public int getIndFrt() {
        return indFrt;
    }
}
