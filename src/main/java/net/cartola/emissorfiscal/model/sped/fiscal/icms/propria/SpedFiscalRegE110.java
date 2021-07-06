package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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

/**
 * Essa classe é basicamente um "espelho", da que é usada no na escrituração do SPED FISCAL ICMS IPI;
 * Porém essa é usado para salvar informações no banco de dados referente aos registros do BLOCO E;
 * 
 * A motivação de montar essa "estrutura de tabelas", é porque NEM tudo o sistema irá conseguir "prever": Ex.: Valor de guia GNRE, pago incorretamente;
 * E caso aconteça em algum mês algo "imprevisto", o analista fiscal, terá a opção de informar tais dados manualmente (PS: atualmente todos os Registro filhos de E110, 
 * é informado manualmente no  sistema terceirizado que usamos)
 * 
 * Em resumo, ao fechar o mês, o sistema irá gerar automaticamente o SPED FISCAL, e salvar as informações do BLOCO E que ele conseguiu "prever", caso o sistema não 
 * tenha conseguido prever/criar todos os registros necessários do bloco E, deverá ser preenchido manualmente. E gerar novamente o SpedFiscal com as informações do bloco E 
 * que foram informadas manualmente para o periodo
 * 
 * @date 5 de jul. de 2021
 * @author robson.costa
 */
@Getter
@Setter
@Entity
@Table(name = "sped_fisc_reg_e110")
public class SpedFiscalRegE110 implements Serializable {

	private static final long serialVersionUID = 5707254165751693964L;

	/**
	 * OBS: Alguns campos do REG E110, é somado com base com o que está no REG E111 (atualmente esse registro é lançado de forma manual)
	 * 
	 * Outros no entanto NÃO. (Pois é somado com base nos documentos fiscais)
	 * 
	 * O importante é: Que o REG E110, é calculado pelo SISTEMA, ou seja, o usuário não edita ele, e 
	 * sim os registros filhos dele
	 * 
	 */
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne()
	@JoinColumn(foreignKey = @ForeignKey(name = "fnk_reg_e110_loja_id"))
	private Loja loja;
	private LocalDate dataInicioApuracao;
	private LocalDate dataFimApuracao;
	
	// Registro E110 (Registro "PAI"), basicamente é o TOTAL dos registro filhos
//	private final String reg = "E110";
	/***
	 * "....O valor neste campo deve ser igual à soma dos VL_ICMS de todos os registros C190, C320, C390, C490, C590, C690, C790,
	 * 		C850, C890, D190, D300, D390, D410, D590, D690, D696..."
	 */
	private BigDecimal vlTotDebitos;
	private BigDecimal vlAjDebitos;
	private BigDecimal vlTotAjDebitos;
	private BigDecimal vlEstornosCred;
	private BigDecimal vlTotCreditos;
	private BigDecimal vlAjCreditos;
	private BigDecimal vlTotAjCreditos;
	private BigDecimal vlEstornosDeb;
	private BigDecimal vlSldCredorAnt;
	private BigDecimal vlSldApurado;
	private BigDecimal vlTotDed;
	private BigDecimal vlIcmsRecolher;
	private BigDecimal vlSldCredorTransportar;
	private BigDecimal debEsp;
    // ==================== FILHOS ==================== (basicamente é a discriminação de cada valor)
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "spedFiscRegE110")
	private Set<SpedFiscalRegE111> spedFiscRegE111;
	
	
//	private List<RegE115> regE115;
	 
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "spedFiscRegE110")
	private Set<SpedFiscalRegE116> spedFiscRegE116;
	
	
}