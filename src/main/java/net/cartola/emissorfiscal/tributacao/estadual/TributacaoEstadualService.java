package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;

@Service
public class TributacaoEstadualService {
	
	@Autowired
	private TributacaoEstadualRepository repository;
	
	public List<TributacaoEstadual> findAll() {
		return repository.findAll();
	}
	
	public Optional<TributacaoEstadual> save(TributacaoEstadual tributacaoEstadual) {
		return Optional.ofNullable(repository.saveAndFlush(tributacaoEstadual));
	}
	
	public List<TributacaoEstadual> saveAll(List<TributacaoEstadual> tributacaoEstadual) {
		return repository.saveAll(tributacaoEstadual);
	}

	public List<TributacaoEstadual> findTributacaoEstadualByNcm(Ncm ncm) {
		return repository.findByNcm(ncm);
	}

	public Optional<TributacaoEstadual> findOne(Long id) {
		return repository.findById(id); 
	}
	
	public List<TributacaoEstadual> findTributacaoEstadualByNcms(Collection<Ncm> ncms) {
		return repository.findByNcmIn(ncms);
	}
	
	public List<TributacaoEstadual> findTributacaoEstadualByOperacaoENcms(Operacao operacao, Collection<Ncm> ncms) {
		return repository.findByOperacaoAndNcmIn(operacao, ncms);
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	
	// ====================  "VALIDAÇÕES" =================== 

	public List<String> getMensagensErros(BindingResult bindResult, boolean existeNumeroEExecao) {
		List<String> msg = new ArrayList<>();
		for(ObjectError objError : bindResult.getAllErrors()) {
			msg.add(objError.getDefaultMessage());
		}
		if (existeNumeroEExecao) {
			msg.add("Já existe essa combinação de NÚMERO e EXCEÇÃO");
		}
		return msg;
	}
}
