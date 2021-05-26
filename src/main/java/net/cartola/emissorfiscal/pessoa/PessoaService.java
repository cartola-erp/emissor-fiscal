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
//		setCnpjCpf(pessoa);
		Optional<PessoaEndereco> opEndereco = pessoaEnderecoService.save(pessoa.getEndereco());			
		pessoa.setEndereco(opEndereco.get());
		return Optional.ofNullable(pessoaRepository.saveAndFlush(pessoa));
	}
		
//	private void setCnpjCpf(Pessoa pessoa) {
//		boolean isCnpj = StringUtil.isCnpj(pessoa.getCnpj());
//		if (!isCnpj) {
//			pessoa.setCpf(pessoa.getCnpj());
//			pessoa.setCnpj("");
//		}
//	}
	
	public Optional<Pessoa> findOne(Long id) {
		return pessoaRepository.findById(id);
	}
	
	public Optional<Pessoa> findByCnpj(String cnpj) {
		return pessoaRepository.findPessoaByCnpj(cnpj);
	}
	
	public Optional<Pessoa> findPessoaByCpf(String cpf) {
		return pessoaRepository.findPessoaByCpf(cpf);
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
		Optional<Pessoa> opPessoaBuscada = Optional.empty(); 
		if (pessoa.getCnpj() != null && !pessoa.getCnpj().isEmpty()) {
			opPessoaBuscada = findByCnpj(pessoa.getCnpj());
		} else if (pessoa.getCpf() != null && !pessoa.getCpf().isEmpty()) {
			opPessoaBuscada = findPessoaByCpf(pessoa.getCpf());
		}
		
		if (!opPessoaBuscada.isPresent() || mudouAlgumaInfo(pessoa, opPessoaBuscada)) {
			return save(pessoa);
		}
		return opPessoaBuscada;
	}

	private boolean mudouAlgumaInfo(Pessoa pessoa, Optional<Pessoa> opPessoaBuscada) {
		if(!opPessoaBuscada.isPresent()) {
			return false;
		} else {
			Pessoa pessoaNoBancoDeDados = opPessoaBuscada.get();
			pessoa.setId(pessoaNoBancoDeDados.getId());
			
			boolean isPessoaEqualPessoaInDb = !pessoa.equals(pessoaNoBancoDeDados);
			return isPessoaEqualPessoaInDb;
		}
		
	}

}

