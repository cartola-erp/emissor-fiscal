package net.cartola.emissorfiscal.documento;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import net.cartola.emissorfiscal.util.ValidationHelper;


/**
 *	22 de nov de 2019
 *	@author robson.costa
 */
@RestController
@RequestMapping("api/v1/documento-fiscal")
public class DocumentoFiscalApiController {

	private static final Logger LOG = Logger.getLogger(DocumentoFiscalApiController.class.getName());
	
	@Autowired
	private DocumentoFiscalService docFiscalService;
	
	@Autowired
	private DeOlhoNoImpostoService olhoNoImpostoService;
	
	@PostMapping(value = "/buscar")
	public ResponseEntity<Response<DocumentoFiscal>> findDocumentoFiscalByCnpjTipoDocumentoSerieNumero(@RequestBody DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		if(docFiscal == null || docFiscal.getEmitente() == null || docFiscal.getTipoOperacao() == null || docFiscal.getSerie() == null || docFiscal.getNumeroNota() == null) {
			return ResponseEntity.badRequest().build();
		} 
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipoOperacao(), docFiscal.getSerie(), docFiscal.getNumeroNota());
		
		if(!opDocFiscal.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		response.setData(opDocFiscal.get());
		return ResponseEntity.ok(response);
	}
	 
	@PostMapping()
	public ResponseEntity<Response<DocumentoFiscal>> save(@Valid @RequestBody DocumentoFiscal newDocFiscal) {
		LOG.log(Level.INFO, "Salvando o DocumentoFiscal {0} " ,newDocFiscal);
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(newDocFiscal.getEmitente().getCnpj(), newDocFiscal.getTipoOperacao(), newDocFiscal.getSerie(), newDocFiscal.getNumeroNota());
//
		if(opDocFiscal.isPresent()) {
			docFiscalService.prepareDocumentoFiscalToUpdate(opDocFiscal, newDocFiscal);
		} 
		return saveOrEditDocumentoFiscal(newDocFiscal);
	}

	@PutMapping()
	public ResponseEntity<Response<DocumentoFiscal>> update(@Valid @RequestBody DocumentoFiscal newDocFiscal) {
		LOG.log(Level.INFO, "Atualizando o DocumentoFiscal {0} " ,newDocFiscal);
		Optional<DocumentoFiscal> opOldDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(newDocFiscal.getEmitente().getCnpj(), newDocFiscal.getTipoOperacao(), newDocFiscal.getSerie(), newDocFiscal.getNumeroNota());

		if (!opOldDocFiscal.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		docFiscalService.prepareDocumentoFiscalToUpdate(opOldDocFiscal, newDocFiscal);
		return saveOrEditDocumentoFiscal(newDocFiscal);
	}
	
	private ResponseEntity<Response<DocumentoFiscal>> saveOrEditDocumentoFiscal(DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, true, true);
		if (!ValidationHelper.collectionEmpty(erros)) {
			LOG.log(Level.WARNING, "ERROS na validação do DocumentoFiscal: {0} ", erros);
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
	
	// ========================================================== REFERENTE as "COMPRAS_DBF" ================================================================================
	
	@PostMapping(value = "/salvar-compra") 
	public ResponseEntity<Response<CompraDto>> saveCompra(@RequestBody DocumentoFiscal docFiscal) {
		LOG.log(Level.INFO, "Salvando a Compra {0} " ,docFiscal);
		Response<DocumentoFiscal> response = new Response<>();
		// Nessa linha abaixo busco se o DocumentoFiscal existe;
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipoOperacao(), docFiscal.getSerie(), docFiscal.getNumeroNota());
		boolean isNewCompra = true;
		if(opDocFiscal.isPresent()) {
			docFiscal.setId(opDocFiscal.get().getId());
			docFiscalService.deleteById(opDocFiscal.get().getId());
			isNewCompra = false;
		}
		return saveOrEditDocumentoFiscalEntrada(docFiscal, isNewCompra);
	}

	private ResponseEntity<Response<CompraDto>> saveOrEditDocumentoFiscalEntrada(DocumentoFiscal docFiscal, boolean isNewCompra) {
		Response<CompraDto> response = new Response<>();
		
		List<String> erros = docFiscalService.setaValoresNecessariosCompra(docFiscal);
		if(!ValidationHelper.collectionEmpty(erros)) {
			LOG.log(Level.WARNING, "ERROS ao tentar salvar a compra -> {0} " ,erros);
			return ResponseEntity.noContent().build();
		}
		
		Optional<CompraDto> opCompraDto = docFiscalService.saveCompra(docFiscal, isNewCompra);
		if(opCompraDto.isPresent()) {
			response.setData(opCompraDto.get());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

}


