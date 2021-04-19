package net.cartola.emissorfiscal.sped.fiscal.tabelas;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Conteúdo da tabela: 5.3 - Tabela de Ajustes e Informações de Valores Provenientes de Documento Fiscal SP
 * 
 * 
 * Classe Reponsavel, por salvar a tabela 5.3, cujo os códigos são usados em alguns registros do SPED
 * 
 * @see http://www.sped.fazenda.gov.br/spedtabelas/AppConsulta/publico/aspx/ConsultaTabelasExternas.aspx?CodSistema=SpedFiscal
 * 
 * @autor robson.costa
 * @data 19 de abr. de 2021
 */
@Entity(name = "ajust_info_vlr_doc_fisc")
@Getter
@Setter
public class AjusteEInfoDeValoresProvenientesDeDocFiscal {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "cod_ajust_bene_ince", length = 30)
	private String codigoAjusteBeneficioIncentivo;
	@Column(name = "dscr")
	private String descricao;
	private LocalDate dataInicio;
	private LocalDate dataFim;
//    @ManyToOne
//    @JoinColumn(name = "uf", referencedColumnName = "esta_id", nullable = false, foreignKey = @ForeignKey(name = "fnk_ajust_info_vlr_doc_fisc_uf"))
//	private Estado uf;
	
}
