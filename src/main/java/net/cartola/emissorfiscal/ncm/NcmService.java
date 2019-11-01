package net.cartola.emissorfiscal.ncm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class NcmService {
	
	@Autowired
	NcmRepository repository;
	
	public List<Ncm> findAll() {
		return repository.findAll();
	}
	
	public Optional<Ncm> save(Ncm ncm) {
		boolean isNumeroCorreto = isNumeroNcmCorreto(ncm.getNumero());
		if (!isNumeroCorreto) {
			return Optional.empty();
		}
		return Optional.ofNullable(repository.saveAndFlush(ncm));
	}

	public List<Ncm> findByNumero(int numeroNcm) {
		return repository.findNcmByNumero(numeroNcm);
	}

	public Optional<Ncm> findOne(Long id) {
		return repository.findById(id); 
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	
	// ====================  "VALIDAÇÕES" =================== 
	public boolean existeNumeroEExcecao(Ncm ncm) {
		return repository.findNumeroAndExcecao(ncm.getNumero(), ncm.getExcecao()).isPresent();
	}

	public boolean isNumeroNcmCorreto(int numero)  {
		String numeroNcm = Integer.toString(numero);
		if (numeroNcm.length() <= 7 || numeroNcm.length() >= 9) {
			return  false;
		} 
		return true;
	}
	
	public boolean isExcecaoCorreto(int excecao) {
		String excecaoNcm = Integer.toString(excecao);
		if (excecaoNcm.length() >= 3 || excecaoNcm.length() == 1) {
			return false;
		}
		return true;
	}
	

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
