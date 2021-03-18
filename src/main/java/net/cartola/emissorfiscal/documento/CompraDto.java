package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.tributacao.CalculoGareCompra;

/**	
 * Classe usada para retornar um DocumentoFiscal de entrada ("Compra"). Para o ERP.
 * Com, o resultado do Calculo da guia gare de ICMS ST (Somente para itens com NCM de ST, nas entradas vindas de SC, MS e ES)
 * 
 * @date 10 de mar. de 2021
 * @author robson.costa
 */
@Getter
@Setter
@ToString
public class CompraDto implements Serializable {

	private static final long serialVersionUID = -3060590428793051270L;

	
	private DocumentoFiscal docFiscal;

	private List<CalculoGareCompra> listCalcGareItem;	
	private CalculoGareCompra totalCalcGareCompra;
	private boolean foiCalculadoIcmsSt = false;
	
	
}
