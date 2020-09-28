package net.cartola.emissorfiscal.cadastro;


/**
 * 18/06/2011 22:33:57
 * @author Murilo
 */
public enum CobrancaTipo {
	
    INDEFINIDO("", false), ACUMULO("Acúmulo", false), DIRETO("Direta", true), 
    FIXO_DIRETO("Dia fixo no mês, com o boleto emitido na hora", true),
    FIXO_ACUMULO("Dia fixo no mês, com o boleto por acumulo", false);
    
    private String descricao;
    private boolean geraCobrancaNaVenda;
    
    CobrancaTipo(String desc, boolean gcv) {
        this.descricao = desc;
        this.geraCobrancaNaVenda = gcv;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isGeraCobrancaNaVenda() {
        return geraCobrancaNaVenda;
    }
}
