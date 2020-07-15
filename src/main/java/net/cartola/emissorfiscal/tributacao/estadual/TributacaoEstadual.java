package net.cartola.emissorfiscal.tributacao.estadual;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

/**
 * 08/08/2017 18:39:51
 * @author murilo
 */
@Entity
@Table(name = "trib_esta", uniqueConstraints = @UniqueConstraint(name = "unk_trib_esta_oper_ncm", columnNames = {
		"oper_id", "ncm_id", "finalidade", "regime_tributario", "esta_orig_id", "esta_dest_id" }))
public class TributacaoEstadual implements Serializable {

    private static final long serialVersionUID = 970384982433L;
    
	private static final String FINALIDADE_OBRIGATORIA = "Atenção! A finalidade é obrigatória!!";

    private Long id;
    private Estado estadoOrigem;
    private Estado estadoDestino;
    private Operacao operacao;
    private Ncm ncm = new Ncm();
	private Finalidade finalidade = Finalidade.CONSUMO;
	private RegimeTributario regimeTributario;
    private int icmsCst;
    private BigDecimal icmsBase;
    private BigDecimal icmsAliquota;
    private BigDecimal icmsIva = BigDecimal.ZERO;
    private BigDecimal icmsAliquotaDestino;
    private BigDecimal fcpAliquota = BigDecimal.ZERO;		// se a pFCPST ("fcpStAliquota") for != dessa terei q add mais um campo
    private BigDecimal icmsStAliquota = BigDecimal.ZERO;	// usado em:  ICMS10, ICMS30, ICMS70, ICMS90 etc
    private Integer cest;
    private String mensagem;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trib_esta_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "esta_orig_id", referencedColumnName = "esta_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_orig_id"))
    public Estado getEstadoOrigem() {
        return estadoOrigem;
    }

    public void setEstadoOrigem(Estado estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }

    @ManyToOne
    @JoinColumn(name = "esta_dest_id", referencedColumnName = "esta_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_dest_id"))
    public Estado getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(Estado estadoDestino) {
        this.estadoDestino = estadoDestino;
    }
    
    @ManyToOne
    @JoinColumn(name = "oper_id", referencedColumnName = "oper_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_oper_id"))
    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }
    
    @ManyToOne
    @JoinColumn(name = "ncm_id", referencedColumnName = "ncm_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_ncm_id"))
    public Ncm getNcm() {
        return ncm;
    }

    public void setNcm(Ncm ncm) {
        this.ncm = ncm;
    }

	@NotNull(message = FINALIDADE_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	@Column(name="finalidade", columnDefinition="enum('CONSUMO', 'REVENDA') default 'CONSUMO' ")
    public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="regime_tributario", columnDefinition="enum('SIMPLES', 'PRESUMIDO', 'REAL') ")
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	@Column(name = "icms_cst", scale = 4, nullable = false)
    public int getIcmsCst() {
        return icmsCst;
    }

    public void setIcmsCst(int icmsCst) {
        this.icmsCst = icmsCst;
    }

    @Column(name = "icms_base", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    public BigDecimal getIcmsBase() {
        return icmsBase;
    }

    public void setIcmsBase(BigDecimal icmsBase) {
        this.icmsBase = icmsBase;
    }

    @Column(name = "icms_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    public BigDecimal getIcmsAliquota() {
        return icmsAliquota;
    }

    public void setIcmsAliquota(BigDecimal icmsAliquota) {
        this.icmsAliquota = icmsAliquota;
    }

    @Column(name = "icms_iva", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    public BigDecimal getIcmsIva() {
        return icmsIva;
    }

    public void setIcmsIva(BigDecimal icmsIva) {
        this.icmsIva = icmsIva;
    }

    @Column(name = "icms_aliq_dest", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    public BigDecimal getIcmsAliquotaDestino() {
        return icmsAliquotaDestino;
    }

    public void setIcmsAliquotaDestino(BigDecimal icmsAliquotaDestino) {
        this.icmsAliquotaDestino = icmsAliquotaDestino;
    }

    @Column(name = "fcp_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    public BigDecimal getFcpAliquota() {
		return fcpAliquota;
	}

	public void setFcpAliquota(BigDecimal fcpAliquota) {
		this.fcpAliquota = fcpAliquota;
	}

    @Column(name = "icms_st_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	public BigDecimal getIcmsStAliquota() {
		return icmsStAliquota;
	}

	public void setIcmsStAliquota(BigDecimal icmsStAliquota) {
		this.icmsStAliquota = icmsStAliquota;
	}

	//	@Column(name = "cest", scale = 7)
    public Integer getCest() {
        return cest;
    }

    public void setCest(Integer cest) {
        this.cest = cest;
    }

    @Column(name="mens")
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TributacaoEstadual)) {
            return false;
        }
        TributacaoEstadual other = (TributacaoEstadual) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "TributacaoEstadual[id=" + id + " ]";
    }

}

