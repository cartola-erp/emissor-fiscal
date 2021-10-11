package net.cartola.emissorfiscal.devolucao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @date 17 de set. de 2021
 * @author robson.costa
 */
@Service
public class DevolucaoOrigemService {
	
	@Autowired
	private DevolucaoOrigemRepository devolucaoOrigemRepository;
	
	
	public void deleteByListDevolucaoOrigem(Collection<DevolucaoOrigem> listDevolucaoOrigem) {
		devolucaoOrigemRepository.deleteInBatch(listDevolucaoOrigem);
	}
	
	
}