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
@Entity(name = "ajust_apura_icms_sp")
@Getter
@Setter
public class AjusteApuracaoIcmsSp {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "cod_ajust", length = 30)
	private String codigoAjuste;
	@Column(name = "dscr")
	private String descricao;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	
}
