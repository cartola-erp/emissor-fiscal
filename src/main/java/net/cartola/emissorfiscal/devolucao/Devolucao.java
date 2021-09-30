package net.cartola.emissorfiscal.devolucao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.ToString;
import net.cartola.emissorfiscal.documento.Documento;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.enums.FreteConta;
import net.cartola.emissorfiscal.util.LocalDateTimeDeserializer;

/**
 * @date 15 de set. de 2021
 * @author robson.costa
 */
@ToString
@Entity
@Table(name = "devo")
public class Devolucao extends Documento<DevolucaoItem> implements Serializable {
	
	private static final long serialVersionUID = -3260584299611764410L;

	private Long id;
    private DevolucaoTipo devolucaoTipo = DevolucaoTipo.DE_CLIENTE;    
    private FreteConta freteConta = FreteConta.SEM_FRETE;    
    private VendaTipo origemTipo = VendaTipo.NFE; 
    
    /**
     * Provavelmente aqui no emissorfiscal em tempo de execucao terei que montar uma mapa dessas origens;
     * E para cada item que eu estiver calculando tenho que obter a origem desse mapa; 
     * POIS, será esses os valores que terei que usar para fazer o calculo das devolucoes
     */
    @Transient
	private List<DocumentoFiscal> origens;	// origens vendas e compras
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oper_id", referencedColumnName = "oper_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_devo_oper_id"))
	public Operacao getOperacao() {
		return operacao;
	}

	@Override
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	
	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "devo_loja_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_devo_loja_id"))
	public Loja getLoja() {
		return loja;
	}

	@Override
	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	
	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emit_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_devo_emitente_id"))
	public Pessoa getEmitente() {
		return emitente;
	}
	
	@Override
	public void setEmitente(Pessoa emitente) {
		this.emitente = emitente;
	}
	
	@Override
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dest_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fnk_devo_destinatario_id"))
	public Pessoa getDestinatario() {
		return destinatario;
	}

	@Override
	public void setDestinatario(Pessoa destinatario) {
		this.destinatario = destinatario;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="devo_tipo", columnDefinition="enum('DE_CLIENTE','PARA_FORNECEDOR','TRANSFERENCIA','DE_FORNECEDOR') ")
	public DevolucaoTipo getDevolucaoTipo() {
		return devolucaoTipo;
	}

	public void setDevolucaoTipo(DevolucaoTipo devolucaoTipo) {
		this.devolucaoTipo = devolucaoTipo;
	}

//	@Column(name = "indi_fret", columnDefinition ="enum('0', '1', '2', '3', '4', '5') DEFAULT '5' ")
	@Enumerated(EnumType.ORDINAL)
//	@Column(name = "indi_fret")
	public FreteConta getFreteConta() {
		return freteConta;
	}

	public void setFreteConta(FreteConta freteConta) {
		this.freteConta = freteConta;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="origem_tipo", columnDefinition="enum('NOTA','NFE','PEDIDO','CUPOM','COMPRA','SAT')  NOT NULL")
	public VendaTipo getOrigemTipo() {
		return origemTipo;
	}

	public void setOrigemTipo(VendaTipo origemTipo) {
		this.origemTipo = origemTipo;
	}

    @Transient
	public List<DocumentoFiscal> getOrigens() {
		return origens;
	}

	public void setOrigens(List<DocumentoFiscal> origens) {
		this.origens = origens;
	}

	@Override
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "devolucao")
	public List<DevolucaoItem> getItens() {
		return itens;
	}

	public void setItens(List<DevolucaoItem> itens) {
		this.itens = itens;
	}
	
	
	
}
