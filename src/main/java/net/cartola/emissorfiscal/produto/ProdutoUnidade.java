package net.cartola.emissorfiscal.produto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 25/09/2020
 * @author robson.costa
 */
/** Mesma classe do ERP, irei precisar pois no SPED é preciso informar as unidades de produto (KG, GR, Peça), que tiveram movimentação no mês **/
@Getter
@Setter
public class ProdutoUnidade implements Serializable {

	private static final long serialVersionUID = -3304619981347931301L;

	private int codigo;
	private String descricao = "";
	private String sigla = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime cadastro;
	private String criadoPor;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime alterado;
	private String alteradoPor;

}