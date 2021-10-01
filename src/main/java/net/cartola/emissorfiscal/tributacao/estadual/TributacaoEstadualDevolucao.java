package net.cartola.emissorfiscal.tributacao.estadual;

import java.io.Serializable;

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
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

/**
 * Model que irá Representar as parametrizações referentes ao ICMS das devoluções 
 * 
 * 28/09/2021 
 * @author robson.costa
 */
@Entity
@Table(name = "trib_esta_devo", uniqueConstraints = @UniqueConstraint(name = "unk_trib_esta_devo_oper_fina_regi_trib_cfop_venda", columnNames = {
		"oper_id", "finalidade", "regime_tributario", "cfop_venda" }))
public class TributacaoEstadualDevolucao implements Serializable {

    private static final long serialVersionUID = 970384982433L;
    
	private static final String FINALIDADE_OBRIGATORIA = "Atenção! A finalidade é obrigatória!!";

    private Long id;
    private Operacao operacao;
	private Finalidade finalidade = Finalidade.CONSUMO;
	private RegimeTributario regimeTributario;
    private int icmsCst;
    private int cfopVenda;				// CFOP -> Que saiu na nota de VENDA (Seja ela do fornecedor ou dá própria AG)
    private int cfopEntrada;			// CFOP -> Que foi dado entrada na AG
    private int cfopNotaDevolucao;		// CFOP -> Que saiu/sairá na NF do emitida por contribuinte. PS (Nos caso que emitimos pelo cliente essa CFOP será igual a CFOP de ENTRADA)
    
//    icmsCest
//	private int codigoAnp;		// --> Deverá ser preenchido pelo OBJ -> TributacaoEstadual
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
    @JoinColumn(name = "oper_id", referencedColumnName = "oper_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_devo_oper_id"))
    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

	@NotNull(message = FINALIDADE_OBRIGATORIA)
	@Enumerated(EnumType.STRING)
	@Column(name="finalidade", columnDefinition="enum('COMERCIALIZACAO', 'BRINDE', 'DOACAO', 'CONSUMO') default 'CONSUMO' ")
    public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="regime_tributario", columnDefinition="enum('SIMPLES','SIMPLES_EXCESSO','NORMAL') ")
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}
	
//	@Column(name = "is_prod_impor", columnDefinition = " TINYINT NOT NULL DEFAULT FALSE")
//	public boolean isProdutoImportado() {
//		return produtoImportado;
//	}
//
//	public void setProdutoImportado(boolean produtoImportado) {
//		this.produtoImportado = produtoImportado;
//	}

	@Column(name = "icms_cst", scale = 4, nullable = false)
    public int getIcmsCst() {
        return icmsCst;
    }

    public void setIcmsCst(int icmsCst) {
        this.icmsCst = icmsCst;
    }

    @Column(name = "cfop_venda", scale = 4, nullable = false)
    public int getCfopVenda() {
		return cfopVenda;
	}
    
	public void setCfopVenda(int cfopVenda) {
		this.cfopVenda = cfopVenda;
	}
	
    @Column(name = "cfop_entr", scale = 4, nullable = false)
	public int getCfopEntrada() {
		return cfopEntrada;
	}

	public void setCfopEntrada(int cfopEntrada) {
		this.cfopEntrada = cfopEntrada;
	}

    @Column(name = "cfop_nota_devo", scale = 4, nullable = false)
	public int getCfopNotaDevolucao() {
		return cfopNotaDevolucao;
	}
	
	public void setCfopNotaDevolucao(int cfopNotaDevolucao) {
		this.cfopNotaDevolucao = cfopNotaDevolucao;
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
        if (!(object instanceof TributacaoEstadualDevolucao)) {
            return false;
        }
        TributacaoEstadualDevolucao other = (TributacaoEstadualDevolucao) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

	@Override
	public String toString() {
		return "TributacaoEstadualDevolucao [id=" + id + ", operacao=" + operacao + ", finalidade=" + finalidade
				+ ", regimeTributario=" + regimeTributario + ", icmsCst=" + icmsCst + ", cfopVenda=" + cfopVenda
				+ ", cfopEntrada=" + cfopEntrada + ", cfopNotaDevolucao=" + cfopNotaDevolucao + ", mensagem=" + mensagem
				+ "]";
	}

}

