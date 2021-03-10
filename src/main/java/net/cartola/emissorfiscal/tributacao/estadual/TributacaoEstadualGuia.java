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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;

/**
 * Nessa tabela tem as tributações para calcular as guias Estaduais:
 * 
 * No momento como iremos apenas INSERIR o GARE ICMS - COD 063-2, iremos inserir
 * apenas para ela Ex.: de ICMS ST - Aquisições VINDA de SC, MG e ES (que algum
 * NCM se encaixe)
 * 
 * OBS: Antigamente para os NCMS que tem ICMS ST, o REMETENTE recolhia o ICMS
 * ST. Porém a partir de 2020, os ESTADOS REMENTENTES ACIMA (SC, MG e ES), não
 * tem mais essa obrigação. Ficando então assim a cargo do DESTINATARIO (No
 * momento que dão entrada na NFE é obrigado a GERAR a GUIA e pagar no mesmo
 * dia)
 * 
 * 
 * @date 8 de mar. de 2021
 * @author robson.costa
 */
//@Table(name = "trib_esta", uniqueConstraints = @UniqueConstraint(name = "unk_trib_esta_guia_oper_ncm", columnNames = {
//		"oper_id", "ncm_id", "finalidade", "regime_tributario", "esta_orig_id", "esta_dest_id" }))
//@Table(name = "trib_esta_guia", uniqueConstraints = @UniqueConstraint(name = "unk_trib_esta_guia_oper_ncm", columnNames = {
//		"oper_id", "ncm_id", "finalidade", "regime_tributario", "esta_orig_id", "esta_dest_id" }))

@ToString
@Getter
@Setter
@Entity
@Table(name = "trib_esta_guia")
public class TributacaoEstadualGuia implements Serializable {

	private static final long serialVersionUID = 8190985020309716307L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "enum('GARE_ICMS', 'GNRE')")
	private TipoGuia tipoGuia;
	
	@ManyToOne
	@JoinColumn(name = "esta_orig_id", referencedColumnName = "esta_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_guia_orig_id"))
	private Estado estadoOrigem;
    
	@ManyToOne
    @JoinColumn(name = "esta_dest_id", referencedColumnName = "esta_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_guia_dest_id"))
	private Estado estadoDestino;
	
	@Enumerated(EnumType.ORDINAL)
	private ProdutoOrigem produtoOrigem;
	
	@ManyToOne
    @JoinColumn(name = "oper_id", referencedColumnName = "oper_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_guia_oper_id"))
	private Operacao operacao;
	
    @ManyToOne
    @JoinColumn(name = "ncm_id", referencedColumnName = "ncm_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_trib_esta_guia_ncm_id"))
	private Ncm ncm = new Ncm();
	
//	private int icmsCst;
    @Column(name = "icms_aliq_interna_dest", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
    private BigDecimal icmsAliqInternaDestino;

	@Column(name = "icms_iva", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	private BigDecimal icmsIva = BigDecimal.ZERO;
	
    @Column(name = "icms_aliq", precision = 7, scale = 6, nullable = false, columnDefinition = "Numeric(7,6) default '0.00'")
	private BigDecimal icmsAliquota; // Aliquota para o "Crédito de ICMS. Para as guias gare é 4% ou 12% pois é Conforme a Origem do Produto"

    @Column(name="mens")
	private String mensagem;

}
