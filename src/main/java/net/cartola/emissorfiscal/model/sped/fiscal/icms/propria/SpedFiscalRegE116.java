package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.sped.fiscal.enums.ObrigacaoIcmsARecolher;
import net.cartola.emissorfiscal.sped.fiscal.enums.OrigemDoProcesso;

/**
 * 
 * Lançamento Manual referente ao valor de ICMS a Pagar - Valor + Vencimento + Cod. Recolhimento da Receita
 *
 * @date 5 de jul. de 2021
 * @author robson.costa
 */
@Getter
@Setter
@Entity	
@Table(name = "sped_fisc_reg_e116")
@JsonIgnoreProperties(value = {"spedFiscRegE110"} )
public class SpedFiscalRegE116 {
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dataInicioSped;
	private LocalDate dataFimSped;
	
//	private final String reg = "E116";
	private ObrigacaoIcmsARecolher codOr;
	private BigDecimal vlOr;
	private LocalDate dtVcto;
	private String codRec;
	private String numProc;
	private OrigemDoProcesso indProc;
	private String proc;
	private String txtCompl;
	private String mesRef;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reg_e110_id", unique = false, foreignKey = @ForeignKey(name = "fnk_sped_fisc_reg_e116_reg_e110"))
	private SpedFiscalRegE110 spedFiscRegE110;

	
}

