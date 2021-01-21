package net.cartola.emissorfiscal.loja;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Service
public class LojaService {

	@Autowired
	private LojaRepository lojaRepository;
	
	public List<Loja> findAll() {
		return lojaRepository.findAll();
	}
	
	public Optional<Loja> save(Loja loja) {
		return Optional.ofNullable(lojaRepository.saveAndFlush(loja));
	}
		
	public Optional<Loja> findOne(Long id) {
		return lojaRepository.findById(id);
	}
	
}
