package net.cartola.emissorfiscal.documento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
//	@GetMapping("id{id}")
//	public List<DocumentoFiscal> findDocumentoById(@PathVariable Long id) {
////		return docFiscalService.findOne(id)
//		return null;
//	}
	
//	@GetMapping("cnpjEmitente/{cnpjEmitente}/tipoDocumento/{tipoDocumento}/serie/{serie}/numero/{numero}")
	@GetMapping("cnpj-emitente/{cnpj}/tipo-documento/{tipo}/serie/{serie}/numero/{numero}") 
	public ResponseEntity<List<DocumentoFiscalDto>> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@PathVariable Long cnpj, @PathVariable String tipo, @PathVariable Long serie,  @PathVariable Long numero) {
		List<DocumentoFiscal> listDoc = docFiscalService.findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(cnpj, tipo, serie, numero);
		if(listDoc.isEmpty()) {
			return ResponseEntity.notFound().build();
		} 
		return ResponseEntity.ok(listDoc.stream().map(docFiscal -> docFiscalService.convertToDto(docFiscal))
				.collect(Collectors.toList()));
//		return ResponseEntity.ok(listDoc);
	}
	
	
}


