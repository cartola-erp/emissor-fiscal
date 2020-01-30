package net.cartola.emissorfiscal.documento;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *	22 de nov de 2019
 *	@author robson.costa
 */

@RestController
@RequestMapping("api/v1/documento-fiscal")
public class DocumentoFiscalApiController {
	
	@Autowired
	private DocumentoFiscalService docFiscalService;
	
//	@GetMapping("id{id}")
//	public List<DocumentoFiscal> findDocumentoById(@PathVariable Long id) {
////		return docFiscalService.findOne(id)
//		return null;
//	}
	
//	public ResponseEntity<List<DocumentoFiscalDto>> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@PathVariable Long cnpj, @PathVariable String tipo, @PathVariable Long serie,  @PathVariable Long numero) {
	@PostMapping(value = "/buscar")
	public ResponseEntity<DocumentoFiscalDto> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@RequestBody DocumentoFiscal docFiscal) {
		if(docFiscal == null || docFiscal.getEmitente() == null || docFiscal.getTipo() == null || docFiscal.getSerie() == null || docFiscal.getNumero() == null) {
			return ResponseEntity.badRequest().build();
		} 
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipo(), docFiscal.getSerie(), docFiscal.getNumero());
		
		return ResponseEntity.ok(docFiscalService.convertToDto(opDocFiscal.get()));
	}
	
	
	@PostMapping()
	public ResponseEntity<DocumentoFiscalDto> save(@Valid @RequestBody DocumentoFiscal docFiscal, BindingResult result) {
		if (result.hasErrors() || docFiscal == null) {
			return ResponseEntity.badRequest().build();
		}
		DocumentoFiscal docuFiscal;
		try {
			docuFiscal = docFiscalService.save(docFiscal).get();
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
		// DEVOLVER AS INFORMAÇÔES NECESSÁRIAS PARA REALIZAR O CALCULO/MONTAR a nota corretamente
		
		return ResponseEntity.ok(docFiscalService.convertToDto(docuFiscal));
	}
	
}


