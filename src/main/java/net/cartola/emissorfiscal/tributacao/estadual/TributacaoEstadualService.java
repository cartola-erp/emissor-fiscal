package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import net.cartola.emissorfiscal.ncm.Ncm;

@Service
public class TributacaoEstadualService {
	
	@Autowired
	TributacaoEstadualRepository repository;
	
	public List<TributacaoEstadual> findAll() {
		return repository.findAll();
	}
	
	public Optional<TributacaoEstadual> save(TributacaoEstadual TributacaoEstadual) {
		return Optional.ofNullable(repository.saveAndFlush(TributacaoEstadual));
	}

	public List<TributacaoEstadual> findTributacaoEstadualByNcm(Ncm ncm) {
		return repository.findByNcm(ncm);
	}

	public Optional<TributacaoEstadual> findOne(Long id) {
		return repository.findById(id); 
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
