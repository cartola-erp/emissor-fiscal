package net.cartola.emissorfiscal.documento;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.response.Response;
import net.cartola.emissorfiscal.util.ValidationHelper;


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
	public ResponseEntity<Response<DocumentoFiscalDto>> save(@Valid @RequestBody DocumentoFiscal docFiscal) {
		Response<DocumentoFiscalDto> response = new Response<>();
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipo(), docFiscal.getSerie(), docFiscal.getNumero());

		if(opDocFiscal.isPresent()) {
			response.setData(docFiscalService.convertToDto(opDocFiscal.get()));
			return ResponseEntity.ok(response);
		}
		
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal);
		if (!ValidationHelper.collectionEmpty(erros)) {
			response.setErrors(erros);
			return ResponseEntity.badRequest().body(response);
		}
		
		opDocFiscal = docFiscalService.save(docFiscal);
		if (opDocFiscal.isPresent()) {
			response.setData(docFiscalService.convertToDto(opDocFiscal.get()));
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
}


