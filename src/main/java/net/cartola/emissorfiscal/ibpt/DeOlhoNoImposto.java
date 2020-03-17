package net.cartola.emissorfiscal.ibpt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 17 de mar de 2020
 * @author robson.costa
 */
@Entity
@Table(name = "trib_olho_imposto", uniqueConstraints = {
		@UniqueConstraint(name = "unk_olho_imposto_id_ex", columnNames = { "ncm", "exce" }) })
public class DeOlhoNoImposto implements Serializable{

	private static final long serialVersionUID = 3206582449678382010L;
	
	private Long id;
	private int ncm;
	private String exce;
	private int tabela;
	private String descricaoIbpt;
	private BigDecimal aliqFederalNacional;
	private BigDecimal aliqFederalImportado;
	private BigDecimal aliqEstadual;
	private BigDecimal aliqMunicipal;
	private LocalDate vigenciaInicio;
	private LocalDate vigenciaTermino;
	private String chave;
	private String versao;
	private String fonte;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getNcm() {
		return ncm;
	}

	public void setNcm(int ncm) {
		this.ncm = ncm;
	}
	
	@Column(length = 2)
	public String getExce() {
		return exce;
	}

	public void setExce(String exce) {
		this.exce = exce;
	}

	public int getTabela() {
		return tabela;
	}

	public void setTabela(int tabela) {
		this.tabela = tabela;
	}

	@Column(length = 1020)
	public String getDescricaoIbpt() {
		return descricaoIbpt;
	}

	public void setDescricaoIbpt(String descricaoIbpt) {
		this.descricaoIbpt = descricaoIbpt;
	}

    @Column(name = "aliq_fede_nacional", precision = 9, scale = 4, columnDefinition = "Numeric(9,4) default '0.00'")
	public BigDecimal getAliqFederalNacional() {
		return aliqFederalNacional;
	}

	public void setAliqFederalNacional(BigDecimal aliqFederalNacional) {
		this.aliqFederalNacional = aliqFederalNacional;
	}

    @Column(name = "aliq_fede_importado", precision = 9, scale = 4, columnDefinition = "Numeric(9,4) default '0.00'")
	public BigDecimal getAliqFederalImportado() {
		return aliqFederalImportado;
	}

	public void setAliqFederalImportado(BigDecimal aliqFederalImportado) {
		this.aliqFederalImportado = aliqFederalImportado;
	}

    @Column(name = "aliq_esta", precision = 9, scale = 4, columnDefinition = "Numeric(9,4) default '0.00'")
	public BigDecimal getAliqEstadual() {
		return aliqEstadual;
	}

	public void setAliqEstadual(BigDecimal aliqEstadual) {
		this.aliqEstadual = aliqEstadual;
	}

    @Column(name = "aliq_municipal", precision = 9, scale = 4, columnDefinition = "Numeric(9,4) default '0.00'")
	public BigDecimal getAliqMunicipal() {
		return aliqMunicipal;
	}

	public void setAliqMunicipal(BigDecimal aliqMunicipal) {
		this.aliqMunicipal = aliqMunicipal;
	}

	public LocalDate  getVigenciaInicio() {
		return vigenciaInicio;
	}

	public void setVigenciaInicio(LocalDate  vigenciaInicio) {
		this.vigenciaInicio = vigenciaInicio;
	}

	public LocalDate  getVigenciaTermino() {
		return vigenciaTermino;
	}

	public void setVigenciaTermino(LocalDate  vigenciaTermino) {
		this.vigenciaTermino = vigenciaTermino;
	}

	@Column(length = 6)
	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	@Column(length = 10)
	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	@Column(length = 6)
	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	
	
}

