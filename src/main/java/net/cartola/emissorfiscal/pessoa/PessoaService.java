package net.cartola.emissorfiscal.pessoa;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.util.StringUtil;

/**
 *	23 de nov de 2019
 *	@author robson.costa
 */

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaEnderecoService pessoaEnderecoService;
	
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
	
	public List<Pessoa> findByIdsIn(Set<Long> pessoasId) {
		return pessoaRepository.findPessoaByIdIn(pessoasId);
	}
	
	public Optional<Pessoa> save(Pessoa pessoa) {
		boolean isCnpj = StringUtil.isCnpj(pessoa.getCnpj());
		if (!isCnpj) {
			pessoa.setCpf(pessoa.getCnpj());
			pessoa.setCnpj("");
		}
		Optional<PessoaEndereco> opEndereco = pessoaEnderecoService.save(pessoa.getEndereco());			
		pessoa.setEndereco(opEndereco.get());
		return Optional.ofNullable(pessoaRepository.saveAndFlush(pessoa));
	}
		
	public Optional<Pessoa> findOne(Long id) {
		return pessoaRepository.findById(id);
	}
	
	public Optional<Pessoa> findByCnpj(String cnpj) {
		return pessoaRepository.findPessoaByCnpj(cnpj);
	}
	
	public Optional<Pessoa> findPessoaByCnpjOrCpf(String cnpj, String cpf) {
		return pessoaRepository.findPessoaByCnpjOrCpf(cnpj, cpf);
	}
	
	public void deleteById(Long id) {
		pessoaRepository.deleteById(id);
	}
	
	/**
	 * Verifica se a pessoa Existe </br>
	 * <b> Caso NÃO</b>, ela será <strong> salva </strong>
	 * 
	 * @return {@link Optional} <{@link Pessoa}>
	 */
	public Optional<Pessoa> verificaSePessoaExiste(Pessoa pessoa) {
		Optional<Pessoa> opPessoa = findPessoaByCnpjOrCpf(pessoa.getCnpj(), pessoa.getCpf());
		if (!opPessoa.isPresent()) {
			return save(pessoa);
		}
		return opPessoa;
	}

}

