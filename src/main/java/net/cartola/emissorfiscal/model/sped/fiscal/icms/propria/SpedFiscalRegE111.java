package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.io.Serializable;
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

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @date 5 de jul. de 2021
 * @author robson.costa
 */
@Getter
@Setter
@Entity	
@Table(name = "sped_fisc_reg_e111")
@JsonIgnoreProperties(value = {"spedFiscRegE110"})
public class SpedFiscalRegE111 implements Serializable {

	private static final long serialVersionUID = -4382957192352267750L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@ManyToOne
//	private Loja loja;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
//	private final String reg = "E111";
	// São Códigos da tabela 5.1.1 (@see AjusteApuracaoIcmsSp )	
	// Quando for o Sistema que gerar esse registro, o campo abaixo será preenchido HARCODED
	// Quando for, abro um MODAL, para ele pesquisar pelos códigos da tabela acima, e o que ele selecionar eu pego a String do códigoAjuste, e passo para essa tabela
	private String codAjApur;		
	@Length(max = 1000)
	private String descrComplAj;
	private BigDecimal vlAjApur;		// Referente aos campos: 4, 5, 8,9  do REG E110 |  (o reg E110 é somado com base no valor que estiver aqui
	// e o "codAjApur", que é o que irá definir em qual campo irá o valor
  
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reg_e110_id", unique = false, foreignKey = @ForeignKey(name = "fnk_sped_fisc_reg_e110"))
	private SpedFiscalRegE110 spedFiscRegE110;
	// ==================== FILHOS ====================
//	private List<RegE112> regE112;
//	private List<RegE113> regE113;
	
	
}
