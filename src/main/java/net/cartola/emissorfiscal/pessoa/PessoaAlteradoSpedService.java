package net.cartola.emissorfiscal.pessoa;

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
public class PessoaAlteradoSpedService {
	
	private static final Logger LOG = Logger.getLogger(PessoaAlteradoSpedService.class.getName());

	@Autowired
	private PessoaAlteradoSpedRepository pessAlterSpedRepository;
	
	
	public List<PessoaAlteradoSped> findAll() {
		return pessAlterSpedRepository.findAll();
	}
	
	public Optional<PessoaAlteradoSped> save(PessoaAlteradoSped pessoaAlteradoSped) {
		return Optional.ofNullable(pessAlterSpedRepository.saveAndFlush(pessoaAlteradoSped));
	}
		
	public Optional<PessoaAlteradoSped> findOne(Long id) {
		return pessAlterSpedRepository.findById(id);
	}
	
}
