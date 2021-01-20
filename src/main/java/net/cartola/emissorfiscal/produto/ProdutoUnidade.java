package net.cartola.emissorfiscal.produto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 25/09/2020
 * @author robson.costa
 */
/** Mesma classe do ERP, irei precisar pois no SPED é preciso informar as unidades de produto (KG, GR, Peça), que tiveram movimentação no mês **/
@Getter
@Setter
@ToString
@Entity
@Table(name = "prod_unid")
public class ProdutoUnidade implements Serializable {

	private static final long serialVersionUID = -3304619981347931301L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao = "";
	private String sigla = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime cadastro;
	private String criadoPor;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime alterado;
	private String alteradoPor;

}