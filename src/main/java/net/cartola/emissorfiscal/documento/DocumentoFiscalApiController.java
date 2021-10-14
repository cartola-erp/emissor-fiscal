package net.cartola.emissorfiscal.documento;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.cartola.emissorfiscal.devolucao.Devolucao;
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
	
	
	/**
	 * TODO Verifiar se isso será mantido aqui
	 * NO momento esse método NÃO é usado (e provavelmente não será)
	 * @param docFiscal
	 * @return
	 */
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
		Optional<DocumentoFiscal> opOldDocFiscal = docFiscalService.findDocumentoFiscal(newDocFiscal);

		if(opOldDocFiscal.isPresent()) {
			this.docFiscalService.prepareDocumentoFiscalToUpdate(opOldDocFiscal, newDocFiscal);
			return saveOrEditDocumentoFiscal(opOldDocFiscal.get(), true, true);
		} 
		return saveOrEditDocumentoFiscal(newDocFiscal, true, true);
	}
	
	/**
	 * TODO 
	 * 
	 * COLOQUEI o retorno como HTTP 500, para forçarem  a atualizarem o SISTEMA ERP;
	 * Pois tirei alguns campos de DocumentoFiscalItem (na atualização do dia 06/09) - E pretendo tirar mais (tirar a FK com NCM, e colocar direto no item)
	 * 
	 * TODO MAS o ideal é retornar HTTP 400 - BAD REQUEST, com a MSG de ERRO
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) 
	public String handleException(final HttpMessageNotReadableException ex) {
		LOG.log(Level.WARNING, "ERRO HttpMessageNotReadableException: {0} " ,ex.getMessage());
		return ex.getMessage();
	}
	

	@PutMapping()
	public ResponseEntity<Response<DocumentoFiscal>> update(@Valid @RequestBody DocumentoFiscal newDocFiscal) {
		LOG.log(Level.INFO, "Atualizando o DocumentoFiscal {0} " ,newDocFiscal);
		Optional<DocumentoFiscal> opOldDocFiscal = docFiscalService.findDocumentoFiscal(newDocFiscal);
		
		if (!opOldDocFiscal.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		docFiscalService.prepareDocumentoFiscalToUpdate(opOldDocFiscal, newDocFiscal);
		return saveOrEditDocumentoFiscal(opOldDocFiscal.get(), false, false);
	}
	
	@PostMapping("/salvar-devolucao")
	public ResponseEntity<Response<DocumentoFiscal>> salvarDevolucao(@RequestBody Devolucao devolucao) {
		LOG.log(Level.INFO, "Salvando/Gerando a Devolucao {0} " ,devolucao);
		Response<DocumentoFiscal> response = new Response<>();
		Optional<DocumentoFiscal> opDocFiscalDevolucao = docFiscalService.save(devolucao);
		if (opDocFiscalDevolucao.isPresent()) {
			response.setData(opDocFiscalDevolucao.get());
			LOG.log(Level.INFO, "Devolucao Salva com SUCESSO ! ");
			return ResponseEntity.ok(response);
		} 
		return ResponseEntity.noContent().build();
	}
	
	private ResponseEntity<Response<DocumentoFiscal>> saveOrEditDocumentoFiscal(DocumentoFiscal docFiscal, boolean validaTribuEsta, boolean validaTribuFede) {
		Response<DocumentoFiscal> response = new Response<>();
		
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, validaTribuEsta, validaTribuFede);
		if (!ValidationHelper.collectionIsEmptyOrNull(erros)) {
			LOG.log(Level.WARNING, "ERROS na validação do DocumentoFiscal: {0} ", erros);
			response.setErrors(erros);
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.save(docFiscal, validaTribuEsta, validaTribuFede);
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
		if(!ValidationHelper.collectionIsEmptyOrNull(erros)) {
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


