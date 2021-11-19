package net.cartola.emissorfiscal.inventario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 19 de nov. de 2021
 * @author robson.costa
 */
@Service
public class InventarioItemService {

	@Autowired
	private InventarioItemRepository inventarioItemItemRepository;
	
	
//	public List<InventarioItem> findAll() {
//		return inventarioItemItemRepository.findAll();
//	}
//	
//	public Optional<InventarioItem> save(InventarioItem inventarioItem) {
//		return Optional.ofNullable(inventarioItemItemRepository.saveAndFlush(inventarioItem));
//	}
//		
//	public Optional<InventarioItem> findOne(Long id) {
//		return inventarioItemItemRepository.findById(id);
//	}
	
	
}
