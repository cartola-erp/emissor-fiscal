package net.cartola.emissorfiscal.documento;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *	22 de nov de 2019
 *	@author robson.costa
 */

@RestController
@RequestMapping("api/v1/documento-fiscal/")
public class DocumentoFiscalApiController {
	
	@Autowired
	private DocumentoFiscalService docFiscalService;
	
//	@GetMapping("id{id}")
//	public List<DocumentoFiscal> findDocumentoById(@PathVariable Long id) {
////		return docFiscalService.findOne(id)
//		return null;
//	}
	
//	public ResponseEntity<List<DocumentoFiscalDto>> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@PathVariable Long cnpj, @PathVariable String tipo, @PathVariable Long serie,  @PathVariable Long numero) {
	@PostMapping(value = "buscar-por-cnpjEmitente-tipoDocumento-serie-numero")
	public ResponseEntity<List<DocumentoFiscalDto>> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@RequestBody DocumentoFiscal docFiscal) {
		if(docFiscal == null || docFiscal.getEmitente() == null || docFiscal.getTipo() == null || docFiscal.getSerie() == null || docFiscal.getNumero() == null) {
			return ResponseEntity.notFound().build();
		} 
		List<DocumentoFiscal> listDoc = docFiscalService.findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipo(), docFiscal.getSerie(), docFiscal.getNumero());
		
		return ResponseEntity.ok(listDoc.stream().map(docuFiscal -> docFiscalService.convertToDto(docuFiscal))
				.collect(Collectors.toList()));
	}
	
	
	@PostMapping()
	public ResponseEntity<DocumentoFiscalDto> save(@Valid @RequestBody DocumentoFiscal docFiscal) {
		DocumentoFiscal documentoFiscal  = docFiscalService.save(docFiscal).get();
		if (documentoFiscal == null) {
			return ResponseEntity.notFound().build();
		}
		// DEVOLVER AS INFORMAÇÔES NECESSÁRIAS PARA REALIZAR O CALCULO/MONTAR a nota corretamente
		
		return ResponseEntity.ok(docFiscalService.convertToDto(documentoFiscal));
	}
	
}


