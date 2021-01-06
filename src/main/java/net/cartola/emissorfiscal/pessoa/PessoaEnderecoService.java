package net.cartola.emissorfiscal.pessoa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 6 de jan. de 2021
 * @author robson.costa
 */
@Service
public class PessoaEnderecoService {
	
	@Autowired
	private PessoaEnderecoRepository pessoaEndRepository;
	
	public List<PessoaEndereco> findAll() {
		return pessoaEndRepository.findAll();
	}
	
	public Optional<PessoaEndereco> save(PessoaEndereco endereco) {
		return Optional.ofNullable(pessoaEndRepository.saveAndFlush(endereco));
	}
		
	public Optional<PessoaEndereco> findOne(Long id) {
		return pessoaEndRepository.findById(id);
	}
	
}
