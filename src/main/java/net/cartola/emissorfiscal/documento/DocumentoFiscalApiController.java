package net.cartola.emissorfiscal.documento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *	22 de nov de 2019
 *	@author robson.costa
 */

@RequestMapping("api/v1/documento-fiscal/")
@RestController
public class DocumentoFiscalApiController {
	
	@Autowired
	private DocumentoFiscalService docFiscalService;
	
//	@GetMapping("cnpjEmitente/{cnpjEmitente}/tipoDocumento/{tipoDocumento}/serie/{serie}/numero/{numero}")
//	public List<DocumentoFiscal> list(@PathVariable String cnpjEmitente, @PathVariable String tipoDocumento, @PathVariable String serie, @PathVariable String numero ) {
//		return docFiscalService.findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(cnpjEmitente, tipoDocumento, serie, numero);
//	}
//	
//	@GetMapping("id{id}")
//	public List<DocumentoFiscal> findDocumentoById(@PathVariable Long id) {
////		return docFiscalService.findOne(id)
//		return null;
//	}
}


