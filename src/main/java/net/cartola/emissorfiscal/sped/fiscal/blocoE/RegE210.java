package net.cartola.emissorfiscal.sped.fiscal.blocoE;

import java.math.BigDecimal;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.sped.fiscal.enums.EnumCodificado;

/**
 * 11/09/2020
 * @author robson.costa
 */
@Record(fields = { 
	@Field(name = "reg", maxLength = 4),
	@Field(name = "indMovSt"),
	@Field(name = "vlSldCredAntSt"),
	@Field(name = "vlDevolSt"),
	@Field(name = "vlRessarcSt"),
	@Field(name = "vlOutCredSt"),
	@Field(name = "vlAjCreditosSt"),
	@Field(name = "vlRetencaoSt"),
	@Field(name = "vlOutDebSt"),
	@Field(name = "vlAjDebitosSt"),
	@Field(name = "vlSldDevAntSt"),
    // ==================== FILHOS ====================
	@Field(name = "regE220"),
	@Field(name = "regE240"),
})
@Getter
@Setter
public class RegE210 {
	
	private final String reg = "E210";
	private IndicadorDeMovimentoSt indMovSt;
	private BigDecimal vlSldCredAntSt;
	private BigDecimal vlDevolSt;
	private BigDecimal vlRessarcSt;
	private BigDecimal vlOutCredSt;
	private BigDecimal vlAjCreditosSt;
	private BigDecimal vlRetencaoSt;
	private BigDecimal vlOutDebSt;
	private BigDecimal vlAjDebitosSt;	
	private BigDecimal vlSldDevAntSt;
	// ==================== FILHOS ====================
	private List<RegE220> regE220;
	private List<RegE240> regE240;
	
}

enum IndicadorDeMovimentoSt implements EnumCodificado {

	SEM_OPERACOES_COM_ST("0"), 
	COM_OPERACOES_ST("1");

	private String indMovSt;

	IndicadorDeMovimentoSt(String indMovSt) {
		this.indMovSt = indMovSt;
	}

	@Override
	public String getCodigo() {
		return indMovSt;
	}

}



 