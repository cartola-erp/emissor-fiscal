package net.cartola.emissorfiscal.documento;

import static java.util.stream.Collectors.toMap;
import static net.cartola.emissorfiscal.util.ValidationHelper.collectionIsEmptyOrNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
	List<DocumentoFiscalItem> prepareDocumentoFiscalToUpdate(List<DocumentoFiscalItem> listOldItens, List<DocumentoFiscalItem> listNewItens) {
		LOG.log(Level.INFO, "Preparando as informações dos itens para serem atualizadas ");
		Map<Integer, DocumentoFiscalItem> mapNewItemPorNumItem = listNewItens.stream().collect(toMap(DocumentoFiscalItem::getItem, newDocFiscItem -> newDocFiscItem));

		boolean isItensEquals = listNewItens.containsAll(listOldItens);
		if (isItensEquals) {
			listOldItens.forEach(oldItem -> {
				DocumentoFiscalItem newItem = mapNewItemPorNumItem.get(oldItem.getItem());
				newItem.setId(oldItem.getId());
			});
		} else {
			this.deleteByListItens(listOldItens);
		}
		List<DocumentoFiscalItem> newListItens = new ArrayList<>();
		newListItens.addAll(mapNewItemPorNumItem.values());
		LOG.log(Level.INFO, "Informações dos itens preparada ");
		return newListItens;
	}

	public boolean verificaSeEhImportado(DocumentoFiscalItem docItem) {
		return listCodProdutoImportado.contains(docItem.getOrigem().ordinal());
	}
	
}
