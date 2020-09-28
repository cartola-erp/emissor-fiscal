package net.cartola.emissorfiscal.cadastro;

/**
 * 18/06/2011 23:51:53
 * @author Murilo
 */
public enum ModoPagamentoTipo {
	
    VISTA("À vista"), CHEQUE("Cheque"), FATURADO("Faturado");
	
    private final String descricao;
    
    ModoPagamentoTipo(String desc) {
        this.descricao = desc;
    }

    public String getDescricao() {
        return descricao;
    }
    
}
