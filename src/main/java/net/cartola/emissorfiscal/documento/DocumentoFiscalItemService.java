package net.cartola.emissorfiscal.documento;

import static net.cartola.emissorfiscal.util.ValidationHelper.collectionIsEmptyOrNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentoFiscalItemService {

	@Autowired
	private DocumentoFiscalItemRepository documentoFiscalItemRepository;

	@Value("${cod.origem.produto.importado}")
	private List<Integer> listCodProdutoImportado;
	
	public List<DocumentoFiscalItem> findAll() {
		return documentoFiscalItemRepository.findAll();
	}

	public Optional<DocumentoFiscalItem> save(DocumentoFiscalItem documentoFiscalItem) {
		return Optional.ofNullable(documentoFiscalItemRepository.saveAndFlush(documentoFiscalItem));
	}

	public List<DocumentoFiscalItem> findItensByVariosDocumentoFiscal(Set<Long> listDocFiscIds) {
		if (collectionIsEmptyOrNull(listDocFiscIds)) {
			return new ArrayList<>();
		}
		return documentoFiscalItemRepository.findByDocumentoFiscalIdIn(listDocFiscIds);
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

	public boolean verificaSeEhImportado(DocumentoFiscalItem docItem) {
		return listCodProdutoImportado.contains(docItem.getOrigem().ordinal());
	}
	
}
