package net.cartola.emissorfiscal.documento;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 11 de jan. de 2021
 * @author robson.costa
 */
@Service
public class DocumentoFiscalReferenciaService {


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

	public Set<DocumentoFiscalReferencia> prepareDocumentoFiscalToUpdate(Set<DocumentoFiscalReferencia> listOldReferencias, Set<DocumentoFiscalReferencia> listNewReferencias) {
		this.deleteByListReferencias(listOldReferencias);
		return listNewReferencias;
	}
	
}
