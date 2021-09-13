package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

/**
 * Classe para retornar os valores de calculo, da GNRE (Guia nacional de recolhimento estadual), para ecommerce acrescentar no valor total do produto,
 * quando for Venda Interestadual para NÃO contribuinte;
 * 
 * PS: A lista é o que será preenchido pelo emissorfiscal;
 * Os outros 3 atributos é o que será usado para fazer a consulta
 * 
 * @date 10 de set. de 2021
 * @author robson.costa
 */
@ToString
@Getter
@Setter
public class GnreAliquotaDto {

	private String ufOrigem;
	private String ufDestino;
	private RegimeTributario regimeTributarioEmitente;
	private Set<GnreAliquota> listGnreAliquota;
	
}
