package net.cartola.emissorfiscal.documento;

import static net.cartola.emissorfiscal.util.ValidationHelper.collectionIsEmptyOrNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentoFiscalItemService {

	private static final Logger LOG = Logger.getLogger(DocumentoFiscalItemService.class.getName());
	
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
	
	/**
	 * Método que irá preparar a lista dos Novos itens, para salvar no Banco de dados.
	 * 
	 * @param listOldItens
	 * @param listNewItens
	 * @return
	 */
	void prepareDocumentoFiscalItemToUpdate(List<DocumentoFiscalItem> listOldItens, List<DocumentoFiscalItem> listNewItens) {
		LOG.log(Level.INFO, "Preparando as informações dos itens para serem atualizadas ");
		List<DocumentoFiscalItem> listNewItensNaoConstamListaAntiga = new ArrayList<>();
		
		for (DocumentoFiscalItem newItem : listNewItens) {
			int idxOldItem = listOldItens.indexOf(newItem);
			if (idxOldItem >= 0) {
				DocumentoFiscalItem oldItem = listOldItens.get(idxOldItem);
				oldItem.copyValuesToUpdate(newItem);
			} else {
				LOG.log(Level.WARNING, "O new item {0}, não consta na lista antiga " ,newItem);
				listNewItensNaoConstamListaAntiga.add(newItem);
			}
		}
		
		LOG.log(Level.INFO, "Informações dos itens preparada ");
	}

	public boolean verificaSeEhImportado(DocumentoFiscalItem docItem) {
		return listCodProdutoImportado.contains(docItem.getOrigem().ordinal());
	}
	
}
