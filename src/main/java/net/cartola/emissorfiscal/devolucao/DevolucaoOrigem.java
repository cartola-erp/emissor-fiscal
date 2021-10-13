package net.cartola.emissorfiscal.devolucao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.util.LocalDateTimeDeserializer;

/**
 * @date 5 de out. de 2021
 * @author robson.costa
 */
//@ToString
@Entity
@Table(name = "devo_orig", uniqueConstraints = @UniqueConstraint(name = "unk_devo_orig_chave_acesso_devo_id", 
								columnNames = {"orig_chave_acesso", "devo_id" }) )
@JsonIgnoreProperties(value = {"devolucao"})
public class DevolucaoOrigem implements Serializable {

	private static final long serialVersionUID = 9011020864489646853L;

	private Long id;
	private int origemDocumento;
    private Loja origemLoja;
    private VendaTipo origemTipo = VendaTipo.NFE;
    private String origemChaveAcesso;
    private LocalDateTime cadastro;
    private LocalDateTime alterado;

    private Devolucao devolucao;

	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
		return id;
	}
	
    public void setId(Long id) {
		this.id = id;
	}
	
    @Column(name = "docu")
	public int getOrigemDocumento() {
		return origemDocumento;
	}

	public void setOrigemDocumento(int origemDocumento) {
		this.origemDocumento = origemDocumento;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "orig_loja_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_devo_orig_origem_loja_id"))
	public Loja getOrigemLoja() {
		return origemLoja;
	}

	public void setOrigemLoja(Loja loja) {
		this.origemLoja = loja;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="origem_tipo", columnDefinition="enum('NOTA','NFE','PEDIDO','CUPOM','COMPRA','SAT')  NOT NULL")
	public VendaTipo getOrigemTipo() {
		return origemTipo;
	}

	public void setOrigemTipo(VendaTipo origemTipo) {
		this.origemTipo = origemTipo;
	}

	@Column(name = "orig_chave_acesso", length = 44)
	public String getOrigemChaveAcesso() {
		return origemChaveAcesso;
	}

	public void setOrigemChaveAcesso(String origemChaveAcesso) {
		this.origemChaveAcesso = origemChaveAcesso;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public LocalDateTime getCadastro() {
		return cadastro;
	}

	public void setCadastro(LocalDateTime cadastro) {
		this.cadastro = cadastro;
	}

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public LocalDateTime getAlterado() {
		return alterado;
	}

	public void setAlterado(LocalDateTime alterado) {
		this.alterado = alterado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "devo_id", unique = false, foreignKey = @ForeignKey(name = "fnk_devo_orig_devo_id"))
	public Devolucao getDevolucao() {
		return devolucao;
	}

	public void setDevolucao(Devolucao devolucao) {
		this.devolucao = devolucao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(origemChaveAcesso);
	}

	/**
	 * Se a "origemChaveAcesso", dos dois objetos forem iguais, então é a msm origem; <\br>
	 * PS: Uma devolução não pode ter a origem repetida! <bold> PORÉM <\bold>, 
	 * uma "Origem" (leia-se NFE/ Origem Chave Acesso), pode estar em mais de uma devolução
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevolucaoOrigem other = (DevolucaoOrigem) obj;
		return Objects.equals(origemChaveAcesso, other.origemChaveAcesso);
	}
	
	
	
	
}
