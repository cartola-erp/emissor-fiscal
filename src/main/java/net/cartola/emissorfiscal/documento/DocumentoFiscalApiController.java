package net.cartola.emissorfiscal.documento;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.ibpt.DeOlhoNoImpostoService;
import net.cartola.emissorfiscal.response.Response;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;
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
	
	@Autowired
	private CalculoFiscalFederal calcFiscalFederal;
	
	@Autowired
	private CalculoFiscalEstadual calFiscalEstadual;
	
	@Autowired
	private DeOlhoNoImpostoService olhoNoImpostoService;
	
//	@GetMapping("id{id}")
//	public List<DocumentoFiscal> findDocumentoById(@PathVariable Long id) {
////		return docFiscalService.findOne(id)
//		return null;
//	}
	
//	public ResponseEntity<List<DocumentoFiscal>> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@PathVariable Long cnpj, @PathVariable String tipo, @PathVariable Long serie,  @PathVariable Long numero) {
	@PostMapping(value = "/buscar")
	public ResponseEntity<Response<DocumentoFiscal>> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@RequestBody DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		if(docFiscal == null || docFiscal.getEmitente() == null || docFiscal.getTipo() == null || docFiscal.getSerie() == null || docFiscal.getNumero() == null) {
			return ResponseEntity.badRequest().build();
		} 
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipo(), docFiscal.getSerie(), docFiscal.getNumero());
		
		if(!opDocFiscal.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		response.setData(opDocFiscal.get());
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping()
	public ResponseEntity<Response<DocumentoFiscal>> save(@Valid @RequestBody DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoDocumentoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipo(), docFiscal.getSerie(), docFiscal.getNumero());

		if(opDocFiscal.isPresent()) {
			response.setData(opDocFiscal.get());
			return ResponseEntity.ok(response);
		}
		return saveOrEditDocumentoFiscal(docFiscal);
	}
	

	@PostMapping("/busca-calculo-federal")
	public ResponseEntity<Response<DocumentoFiscal>> findCalculoFederal(@Valid @RequestBody DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		// 1 - VALIDAR
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, false, true);
		if (!ValidationHelper.collectionEmpty(erros)) {
			response.setErrors(erros);
			return ResponseEntity.badRequest().body(response);
		}
		
		// 2 - CALCULAR
		calcFiscalFederal.calculaImposto(docFiscal);
		olhoNoImpostoService.setDeOlhoNoImposto(Optional.of(docFiscal));
		response.setData(docFiscal);
		return ResponseEntity.ok(response);
	}
	
	
	@PutMapping()
	public ResponseEntity<Response<DocumentoFiscal>> edit (@Valid @RequestBody DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findOne(docFiscal.getId());

		if(!opDocFiscal.isPresent()) {
			response.setErrors(Arrays.asList("O documento fiscal que você está tentando editar, NÃO existe!"));
			return ResponseEntity.badRequest().body(response);
		}
		return saveOrEditDocumentoFiscal(docFiscal);
	}
	
	private ResponseEntity<Response<DocumentoFiscal>> saveOrEditDocumentoFiscal(DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, true);
		if (!ValidationHelper.collectionEmpty(erros)) {
			response.setErrors(erros);
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.save(docFiscal);
		if (opDocFiscal.isPresent()) {
			response.setData(opDocFiscal.get());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}


