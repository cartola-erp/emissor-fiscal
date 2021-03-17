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

	private static final Logger LOG = Logger.getLogger(DocumentoFiscalApiController.class.getName());
	
	@Autowired
	private DocumentoFiscalService docFiscalService;
	
	@Autowired
	private CalculoFiscalFederal calcFiscalFederal;
	
	@Autowired
	private CalculoFiscalEstadual calFiscalEstadual;
	
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
	public ResponseEntity<Response<DocumentoFiscal>> save(@Valid @RequestBody DocumentoFiscal docFiscal) {
//		Response<DocumentoFiscal> response = new Response<>();
		LOG.log(Level.INFO, "Salvando o DocumentoFiscal {0} " ,docFiscal);
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipoOperacao(), docFiscal.getSerie(), docFiscal.getNumeroNota());
//
		if(opDocFiscal.isPresent()) {
//			response.setData(opDocFiscal.get());
//			return ResponseEntity.ok(response);
			docFiscal.setId(opDocFiscal.get().getId());
			docFiscalService.deleteById(opDocFiscal.get().getId());
		} 
		return saveOrEditDocumentoFiscal(docFiscal);
	}
	
	@PutMapping()
	public ResponseEntity<Response<DocumentoFiscal>> update(@Valid @RequestBody DocumentoFiscal docFiscal) {
		LOG.log(Level.INFO, "Atualizando o DocumentoFiscal {0} " ,docFiscal);
		Response<DocumentoFiscal> response = new Response<>();
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipoOperacao(), docFiscal.getSerie(), docFiscal.getNumeroNota());

		if (!opDocFiscal.isPresent()) {
			return ResponseEntity.noContent().build();
		}
//		docFiscal.setId(opDocFiscal.get().getId());
		DocumentoFiscal docFiscInDb = opDocFiscal.get();
		docFiscInDb.setNfeChaveAcesso(docFiscal.getNfeChaveAcesso());
		docFiscInDb.setStatus(docFiscal.getStatus());
		docFiscalService.updateStatusAndChaveAcesso(docFiscInDb).ifPresent(updatedDocFiscal -> response.setData(updatedDocFiscal));
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping(value = "/salvar-compra") 
	public ResponseEntity<Response<DocumentoFiscal>> saveCompra(@RequestBody DocumentoFiscal docFiscal) {
		LOG.log(Level.INFO, "Salvando a Compra {0} " ,docFiscal);
		Response<DocumentoFiscal> response = new Response<>();
		// Nessa linha abaixo busco se o DocumentoFiscal existe;
		Optional<DocumentoFiscal> opDocFiscal = docFiscalService.findDocumentoFiscalByCnpjTipoOperacaoSerieENumero(docFiscal.getEmitente().getCnpj(), docFiscal.getTipoOperacao(), docFiscal.getSerie(), docFiscal.getNumeroNota());
		if(opDocFiscal.isPresent()) {
			docFiscal.setId(opDocFiscal.get().getId());
			docFiscalService.deleteById(opDocFiscal.get().getId());
		}
		return saveOrEditDocumentoFiscalEntrada(docFiscal);
	}
	
	/**
	 * DEPOIS QUE TERMINAR DE IMPLANTAR O ICMS (save(...) ), APAGAR ESSE MÉTODO
	 * @param docFiscal
	 * @return
	 */
	@PostMapping("/busca-calculo-federal")
	public ResponseEntity<Response<DocumentoFiscal>> findCalculoFederal(@Valid @RequestBody DocumentoFiscal docFiscal) {
		LOG.log(Level.INFO, "Buscando calculo de PIS/COFINS, para o DocumentoFiscal {0}", docFiscal);
		Response<DocumentoFiscal> response = new Response<>();
		// 1 - VALIDAR
		List<String> erros = docFiscalService.validaDadosESetaValoresNecessarios(docFiscal, false, true);
		if (!ValidationHelper.collectionEmpty(erros)) {
			LOG.log(Level.WARNING, "Houve algum erro na validação do DocumentoFiscal: {0} ", erros);
			response.setErrors(erros);
			return ResponseEntity.badRequest().body(response);
		}
		
		// 2 - CALCULAR
		calcFiscalFederal.calculaImposto(docFiscal);
		olhoNoImpostoService.setDeOlhoNoImposto(Optional.of(docFiscal));
		response.setData(docFiscal);
		return ResponseEntity.ok(response);
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
	
	private ResponseEntity<Response<DocumentoFiscal>> saveOrEditDocumentoFiscalEntrada(DocumentoFiscal docFiscal) {
		Response<DocumentoFiscal> response = new Response<>();
		
		List<String> erros = docFiscalService.setaValoresNecessariosCompra(docFiscal);
		if(!ValidationHelper.collectionEmpty(erros)) {
			LOG.log(Level.WARNING, "ERROS ao tentar salvar a compra -> {0} " ,erros);
			return ResponseEntity.noContent().build();
		}
		
		Optional<DocumentoFiscal> opDocFiscalEntrada = docFiscalService.saveCompra(docFiscal);
		if(opDocFiscalEntrada.isPresent()) {
			response.setData(opDocFiscalEntrada.get());
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
		
	}

}


