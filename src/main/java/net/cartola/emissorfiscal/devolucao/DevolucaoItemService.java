package net.cartola.emissorfiscal.devolucao;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.loja.Loja;

/**
 * @date 17 de set. de 2021
 * @author robson.costa
 */
@Service
public class DevolucaoItemService {

	@Autowired
	private DevolucaoItemRepository devolucaoItemRepository;

	
	
	
	public Optional<DocumentoFiscal> save(Devolucao devolucao) {
		// TODO Auto-generated method stub
		
		return null;
	}




	public void setLojaOrigem(DevolucaoItem item, Map<String, Loja> mapLojaPorCnpj, Map<String, Boolean> mapErros) {
		String cnpjLojaOrigem = item.getLojaOrigem().getCnpj();
		if (mapLojaPorCnpj != null && mapLojaPorCnpj.containsKey(cnpjLojaOrigem)) {
			item.setLojaOrigem(mapLojaPorCnpj.get(cnpjLojaOrigem));
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("Loja de origem: ").append(cnpjLojaOrigem).append(" NÃO encontrada no emissor-fiscal, para o item: ")
				.append(item.getItem()).append(" | X/s: ").append(item.getCodigoX()).append(" ").append(item.getCodigoSequencia());
			mapErros.put(sb.toString(), true);
		}
	}
	
	
	
	
}
