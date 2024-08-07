package net.cartola.emissorfiscal.model.sped.fiscal.tabelas;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Conteúdo da tabela: 5.1.1- Tabela de Códigos de Ajustes da Apuração do ICMS - SP
 * 
 * 
 * Classe Reponsavel, por salvar a tabela 5.1, cujo os códigos são usados em alguns registros do SPED
 * 
 * @see http://www.sped.fazenda.gov.br/spedtabelas/AppConsulta/publico/aspx/ConsultaTabelasExternas.aspx?CodSistema=SpedFiscal
 * 
 * @autor robson.costa
 * @data 19 de abr. de 2021
 */

// tabela_5_1_1_ajust_apura_icms_sp 
// tbl_5_1_1_ajust_apura_icms_sp
//@Entity(name = "ajust_apura_icms_sp")
@Entity(name = "tbl_5_1_1_ajust_apura_icms_sp")
@ToString
@Getter
@Setter
public class Tabela511AjusteApuracaoIcmsSp {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "cod_ajust", length = 30)
	private String codigoAjuste;
	@Column(name = "dscr")
	private String descricao;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
}
