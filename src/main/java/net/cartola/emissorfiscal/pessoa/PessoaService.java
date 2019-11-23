package net.cartola.emissorfiscal.pessoa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	23 de nov de 2019
 *	@author robson.costa
 */

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	public Optional<Pessoa> save(Pessoa documentoFiscal) {
		return Optional.ofNullable(pessoaRepository.saveAndFlush(documentoFiscal));
	}
		
	public Optional<Pessoa> findOne(Long id) {
		return pessoaRepository.findById(id);
	}

	public void deleteById(Long id) {
		pessoaRepository.deleteById(id);
	}
		
}

