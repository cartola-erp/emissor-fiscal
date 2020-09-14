package net.cartola.emissorfiscal.sped.fiscal.blocoH;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import coffeepot.bean.wr.annotation.Field;
import coffeepot.bean.wr.annotation.Record;
import net.cartola.emissorfiscal.sped.fiscal.enums.MotivoDoInventario;

/**
 * 14/09/2020
 * @author robson.costa
 */
@Record(fields = { 
		@Field(name = "reg", maxLength = 4),
		@Field(name = "dtInv"),
		@Field(name = "vlInv"),
		@Field(name = "motInv"),
		// ==================== FILHOS ====================
		@Field(name = "regH010")
})
public class RegH005 {
	
	private final String reg = "H005";
    private LocalDate dtInv;
    private BigDecimal vlInv;
    private MotivoDoInventario motInv;
	 // ==================== FILHOS ====================
    private List<RegH010> regH010;
	
    public String getReg() {
		return reg;
	}

	public LocalDate getDtInv() {
		return dtInv;
	}

	public void setDtInv(LocalDate dtInv) {
		this.dtInv = dtInv;
	}

	public BigDecimal getVlInv() {
		return vlInv;
	}

	public void setVlInv(BigDecimal vlInv) {
		this.vlInv = vlInv;
	}

	public MotivoDoInventario getMotInv() {
		return motInv;
	}

	public void setMotInv(MotivoDoInventario motInv) {
		this.motInv = motInv;
	}

	public List<RegH010> getRegH010() {
		return regH010;
	}

	public void setRegH010(List<RegH010> regH010) {
		this.regH010 = regH010;
	}
    
}
