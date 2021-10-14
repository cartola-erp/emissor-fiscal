package net.cartola.emissorfiscal.documento;

import static java.util.logging.Logger.getLogger;
import static java.util.stream.Collectors.toMap;
import static net.cartola.emissorfiscal.util.ValidationHelper.collectionNotEmptyOrNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @date 11 de jan. de 2021
 * @author robson.costa
 */
@Service
public class DocumentoFiscalReferenciaService {

	private static final Logger LOG = getLogger(DocumentoFiscalReferencia.class.getName());

	@Autowired
	private DocumentoFiscalReferenciaRepository docFiscalReferenciaRepository;

	public List<DocumentoFiscalReferencia> findAll() {
		return docFiscalReferenciaRepository.findAll();
	}

	public Optional<DocumentoFiscalReferencia> save(DocumentoFiscalReferencia documentoFiscalItem) {
		return Optional.ofNullable(docFiscalReferenciaRepository.saveAndFlush(documentoFiscalItem));
	}

//	public List<DocumentoFiscalReferencia> findDocumentoFiscalByOperacao(Ncm ncm) {
//		return docFiscalReferenciaRepository.findByNcm(ncm);
//	}
//
//	public List<DocumentoFiscalReferencia> findDocumentoFiscalByVariasOperacoes(Collection<Ncm> ncms) {
//		return docFiscalReferenciaRepository.findByNcmIn(ncms);
//	}

	public Optional<DocumentoFiscalReferencia> findOne(Long id) {
		return docFiscalReferenciaRepository.findById(id);
	}

	public void deleteById(Long id) {
		docFiscalReferenciaRepository.deleteById(id);
	}	
	
	public void deleteByListReferencias(Collection<DocumentoFiscalReferencia> listReferencias) {
		docFiscalReferenciaRepository.deleteInBatch(listReferencias);
	}

	void prepareDocumentoFiscalReferenciaToUpdate(Set<DocumentoFiscalReferencia> listOldReferencias, Set<DocumentoFiscalReferencia> listNewReferencias) {
		if (collectionNotEmptyOrNull(listOldReferencias) && collectionNotEmptyOrNull(listNewReferencias)) {
			LOG.log(Level.INFO, "Preparando as informações das referências para serem atualizadas ");
			
			Map<String, DocumentoFiscalReferencia> mapOldReferenciaPorChaveAcesso = listOldReferencias.stream()
					.collect(toMap(DocumentoFiscalReferencia::getChave, (DocumentoFiscalReferencia docFiscRef) -> docFiscRef));
			
			for (DocumentoFiscalReferencia newReferencia : listNewReferencias) {
				String newRefChaveAcesso = newReferencia.getChave();
				if (mapOldReferenciaPorChaveAcesso.containsKey(newRefChaveAcesso)) {
					DocumentoFiscalReferencia oldReferencia = mapOldReferenciaPorChaveAcesso.get(newRefChaveAcesso);
					oldReferencia.copyValuesToUpdate(newReferencia);
				}
			}
			
			LOG.log(Level.INFO, "Informações das referências preparada ");
		}
	}
	
}
