package net.cartola.emissorfiscal.model.sped.fiscal.difal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
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
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Getter
@Setter
@Entity
@Table(name = "sped_fisc_reg_e316")
@JsonIgnoreProperties(value = {"spedFiscRegE310"})
public class SpedFiscalRegE316 implements Serializable {

	private static final long serialVersionUID = -1565864291125936277L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
	private ObrigacaoIcmsARecolher codOr;
	private BigDecimal vlOr;
	private LocalDate dtVcto;
	private String codRec;
	private Long numProc;
	private OrigemDoProcesso indProc;
	private String proc;
	private String txtCompl;
	private LocalDate mesRef;	//Informe o mês de referência no formato “mmaaaa | 6 caracteres”
	
	@ManyToOne
	@JoinColumn(name = "reg_e310_id", unique = false, foreignKey = @ForeignKey(name = "fnk_sped_fisc_reg_e310"))
	private SpedFiscalRegE310 spedFiscRegE310;
	
}
