package net.cartola.emissorfiscal.model.sped.fiscal.difal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimentoFcp;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Getter
@Setter
@Entity
@Table(name = "sped_fisc_reg_e310")
public class SpedFiscalRegE310 implements Serializable {

	
	private static final long serialVersionUID = -7648677153521399809L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne()
	@JoinColumn(foreignKey = @ForeignKey(name = "fnk_reg_e310_loja_id"))
	private Loja loja;
	private LocalDate dataInicioSped;
	private LocalDate dataFimSped;

	private IndicadorDeMovimentoFcp indMovFcpDifal;
	private BigDecimal vlSldCredAntDifal;
	private BigDecimal vlTotDebitosDifal;
	private BigDecimal vlOutDebDifal;
	private BigDecimal vlTotCreditosDifal;
	private BigDecimal vlOutCredDifal;
	private BigDecimal vlSldDevAntDifal;
	private BigDecimal vlDeducoesDifal;
	private BigDecimal vlRecolDifal;
	private BigDecimal vlSldCredTransportarDifal;
	private BigDecimal debEspDifal;
	private BigDecimal vlSldCredAntFcp;
	private BigDecimal vlTotDebFcpSaida;
	private BigDecimal vlOutDebFcp;
	private BigDecimal vlTotCredFcpEntr;
	private BigDecimal vlOutCredFcp;
	private BigDecimal vlSldDevAntFcp;
	private BigDecimal vlDeducoesFcp;
	private BigDecimal vlRecolFcp;
	private BigDecimal vlSldCredTransportarFcp;
	private BigDecimal debEspFcp;
	// ==================== FILHOS ====================
//	private List<RegE311> regE311;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "spedFiscRegE310")
	private List<SpedFiscalRegE316> spedFiscREgE316;		// Esse é Usado
}
