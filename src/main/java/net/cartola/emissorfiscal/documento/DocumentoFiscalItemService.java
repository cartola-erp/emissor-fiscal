package net.cartola.emissorfiscal.documento;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.ncm.Ncm;

@Service
public class DocumentoFiscalItemService {

	@Autowired
	private DocumentoFiscalItemRepository documentoFiscalItemRepository;

	public List<DocumentoFiscalItem> findAll() {
		return documentoFiscalItemRepository.findAll();
	}

	public Optional<DocumentoFiscalItem> save(DocumentoFiscalItem documentoFiscalItem) {
		return Optional.ofNullable(documentoFiscalItemRepository.saveAndFlush(documentoFiscalItem));
	}

	public List<DocumentoFiscalItem> findDocuFiscItemByNcm(Ncm ncm) {
		return documentoFiscalItemRepository.findByNcm(ncm);
	}

	public List<DocumentoFiscalItem> findDocuFiscItemByVariosNcms(Collection<Ncm> ncms) {
		return documentoFiscalItemRepository.findByNcmIn(ncms);
	}

	public List<DocumentoFiscalItem> findItensByVariosDocumentoFiscal(Collection<DocumentoFiscal> listDocumentosFiscais) {
		return documentoFiscalItemRepository.findByDocumentoFiscalIn(listDocumentosFiscais);
	}
	
	public Optional<DocumentoFiscalItem> findOne(Long id) {
		return documentoFiscalItemRepository.findById(id);
	}

	public void deleteById(Long id) {
		documentoFiscalItemRepository.deleteById(id);
	}
	
	public void deleteByListItens(Collection<DocumentoFiscalItem> listItens) {
		documentoFiscalItemRepository.deleteInBatch(listItens);
	}
	
}
