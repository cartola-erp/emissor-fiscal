package net.cartola.emissorfiscal.pessoa;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

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
	
	public List<PessoaAlteradoSped> findPessoaAlteradoPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return pessAlterSpedRepository.findByDtAlteracaoCadastroBetween(dataInicio, dataFim);
	}
	
	
	public List<PessoaAlteradoSped> findPessoaAlteradoPorPeriodoSped(LocalDate dataInicio, LocalDate dataFim, Set<String> listCpfCnpj) {
		return pessAlterSpedRepository.findByDtAlteracaoCadastroBetweenAndCnpjAntInOrCnpjNovoInOrCpfAntInOrCpfNovoIn(
				dataInicio, 
				dataFim, 
				listCpfCnpj, 
				listCpfCnpj, 
				listCpfCnpj, 
				listCpfCnpj);
	}
	
	public Optional<PessoaAlteradoSped> save(PessoaAlteradoSped pessAlterSped) {
		pessAlterSped.setDtAlteracaoCadastro(LocalDate.now());
		return Optional.ofNullable(pessAlterSpedRepository.saveAndFlush(pessAlterSped));
	}
		
	public Optional<PessoaAlteradoSped> findOne(Long id) {
		return pessAlterSpedRepository.findById(id);
	}

	public PessoaAlteradoSped validaPessoaAlteradoSped(PessoaAlteradoSped newPessAlterSped) {
		LOG.log(Level.INFO, "Validando o PessoaAlteradoSped ");
		Optional<PessoaAlteradoSped> opPessoaAlterSped = buscaPessoaAlteradoNoDia(newPessAlterSped);
	
		if (opPessoaAlterSped.isPresent()) {
			PessoaAlteradoSped oldPessAlterSped = opPessoaAlterSped.get();
			
			if (isPessoaFisica(oldPessAlterSped)) {
				return setValoresPessoaFisicaJaExistente(oldPessAlterSped, newPessAlterSped);
			} 
			return setValoresPessoaJuridicaJaExistente(oldPessAlterSped, newPessAlterSped);
		}
		LOG.log(Level.INFO, "Validação de PessoaAlteradoSped, terminada ");
		return newPessAlterSped;
	}


	public PessoaAlteradoSped validaEnderecoPessoaAlteradaSped(PessoaAlteradoSped newPessAlterSped) {
		LOG.log(Level.INFO, "Validando o ENDERECO de PessoaAlteradoSped ");
		Optional<PessoaAlteradoSped> opPessoaAlterSped = buscaPessoaAlteradoNoDia(newPessAlterSped);
		if (opPessoaAlterSped.isPresent()) {
			PessoaAlteradoSped oldPessAlterSped = opPessoaAlterSped.get();
			return setEnderecoPessoaJaExistente(oldPessAlterSped, newPessAlterSped);
		}
		LOG.log(Level.INFO, "Validação do ENDERECO de PessoaAlteradoSped, terminada ");
		return newPessAlterSped;
	}
	

	/** 
	 * Talvez o ideal seja, buscar se aquela pessoa foi alterado NAQUELE MES
	 * 
	 * @param pessAlterSped
	 * @return
	 */
	private Optional<PessoaAlteradoSped> buscaPessoaAlteradoNoDia(PessoaAlteradoSped pessAlterSped) {
		Optional<PessoaAlteradoSped> opPessoaAlterSped = Optional.empty();
		if (isPessoaFisica(pessAlterSped)) {
			List<String> listCpf = Arrays.asList(pessAlterSped.getCpfAnt(), pessAlterSped.getCpfNovo());
			opPessoaAlterSped = pessAlterSpedRepository.findByCpfAntInOrCpfNovoInAndDtAlteracaoCadastro(listCpf, listCpf, LocalDate.now());
		} else {
			List<String> listCnpj = Arrays.asList(pessAlterSped.getCnpjAnt(), pessAlterSped.getCnpjNovo());
			opPessoaAlterSped = pessAlterSpedRepository.findByCnpjAntInOrCnpjNovoInAndDtAlteracaoCadastro(listCnpj, listCnpj, LocalDate.now());
		}
		return opPessoaAlterSped;
	}
	
	private boolean isPessoaFisica(PessoaAlteradoSped pessAlterSped) {
		boolean isPessoaFisica = false;
		if (pessAlterSped.getCnpjAnt() == null && pessAlterSped.getCnpjNovo() == null && pessAlterSped.getCpfNovo() != null) {
			isPessoaFisica = true;
		} else if (pessAlterSped.getCpfNovo() == null && pessAlterSped.getCnpjNovo() != null) {
			isPessoaFisica = false;
		}
		return isPessoaFisica;
	}
	
	
	/**
	 * Irá setar no oldPessAlterSped, APENAS os valores novos que foram recebidos no obj newPessAlterSped
	 * 
	 * @param oldPessAlterSped
	 * @param newPessAlterSped
	 * @return
	 */
	private PessoaAlteradoSped setValoresPessoaJuridicaJaExistente(PessoaAlteradoSped oldPessAlterSped, PessoaAlteradoSped newPessAlterSped) {
		oldPessAlterSped.setCnpjNovo(newPessAlterSped.getCnpjNovo());
		return setValoresPessoaJaExistente(oldPessAlterSped, newPessAlterSped);
	}

	private PessoaAlteradoSped setValoresPessoaFisicaJaExistente(PessoaAlteradoSped oldPessAlterSped, PessoaAlteradoSped newPessAlterSped) {
		oldPessAlterSped.setCpfNovo(newPessAlterSped.getCpfNovo());
		return setValoresPessoaJaExistente(oldPessAlterSped, newPessAlterSped);
	}

	private PessoaAlteradoSped setValoresPessoaJaExistente(PessoaAlteradoSped oldPessAlterSped, PessoaAlteradoSped newPessAlterSped) {
		oldPessAlterSped.setNomeNovo(newPessAlterSped.getNomeNovo());
		oldPessAlterSped.setCodPaisNovo(newPessAlterSped.getCodPaisNovo());
		oldPessAlterSped.setCodMunNovo(newPessAlterSped.getCodMunNovo());
		oldPessAlterSped.setSuframaNovo(newPessAlterSped.getSuframaNovo());
		return oldPessAlterSped;
	}
	
	/**
	 * Setando o endereco da PessoaAlterada, já existente
	 * 
	 * @param oldPessAlterSped
	 * @param newPessAlterSped
	 * @return
	 */
	private PessoaAlteradoSped setEnderecoPessoaJaExistente(PessoaAlteradoSped oldPessAlterSped, @Valid PessoaAlteradoSped newPessAlterSped) {
		oldPessAlterSped.setCodMunNovo(newPessAlterSped.getCodMunNovo());
		oldPessAlterSped.setEnderecoNovo(newPessAlterSped.getEnderecoNovo());
		oldPessAlterSped.setNumeroNovo(newPessAlterSped.getNumeroNovo());
		oldPessAlterSped.setComplementoNovo(newPessAlterSped.getComplementoNovo());
		oldPessAlterSped.setBairroNovo(newPessAlterSped.getBairroNovo());
		return oldPessAlterSped;
	}
	
}
