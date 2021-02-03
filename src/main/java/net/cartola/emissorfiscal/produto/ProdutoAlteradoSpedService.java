package net.cartola.emissorfiscal.produto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 27 de jan. de 2021
 * @author robson.costa
 */
@Service
public class ProdutoAlteradoSpedService {
	
	private static final Logger LOG = Logger.getLogger(ProdutoAlteradoSpedService.class.getName());
	
	@Autowired
	private ProdutoAlteradoSpedRepository prodAlterSpedRepository;

	public List<ProdutoAlteradoSped> findAll() {
		return prodAlterSpedRepository.findAll();
	}
	
	public Optional<ProdutoAlteradoSped> save(ProdutoAlteradoSped prodAlteSped) {
		prodAlteSped.setDtFinalUtilizacaoDescAnterior(LocalDate.now());
		return Optional.ofNullable(prodAlterSpedRepository.saveAndFlush(prodAlteSped));
	}
		
	public Optional<ProdutoAlteradoSped> findOne(Long id) {
		return prodAlterSpedRepository.findById(id);
	}
	
	
}
