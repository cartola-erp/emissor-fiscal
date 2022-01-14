package net.cartola.emissorfiscal.sped.fiscal.blocoH;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "indMov")
})
public class RegH001AberturaDoBlocoH {
	
	private final String reg = "H001";
	private IndicadorDeMovimento indMov = IndicadorDeMovimento.BLOCO_SEM_DADOS_INFORMADOS;

	public RegH001AberturaDoBlocoH(MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		if (movimentoMensalIcmsIpi.getInventario() != null) {
			this.indMov = IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS;
		}
	}
	
	public RegH001AberturaDoBlocoH(IndicadorDeMovimento indMov) {
		this.indMov = indMov;
	}
	
	public String getReg() {
		return reg;
	}
	
	public IndicadorDeMovimento getIndMov() {
		return indMov;
	}
	
	public void setIndMov(IndicadorDeMovimento indMov) {
		this.indMov = indMov;
	}
}
