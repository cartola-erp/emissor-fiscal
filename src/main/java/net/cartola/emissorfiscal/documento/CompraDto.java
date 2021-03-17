package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.tributacao.CalculoGareCompra;

/**
  * @date 10 de mar. de 2021
  * @author robson.costa
  */
@Getter
@Setter
@ToString
public class CompraDto implements Serializable {

	private static final long serialVersionUID = -3060590428793051270L;

	
	private DocumentoFiscal docFiscal;

	private Map<DocumentoFiscalItem, CalculoGareCompra> mapCalcGarePorItem;
	private CalculoGareCompra totalCalcGareCompra;
	private boolean foiCalculadoIcmsSt = false;
	
	
	
}
