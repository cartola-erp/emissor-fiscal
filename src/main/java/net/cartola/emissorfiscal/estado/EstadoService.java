package net.cartola.emissorfiscal.estado;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class EstadoService {
	
	@Autowired
	EstadoRepository repository;
	
	public List<Estado> findAll() {
		return repository.findAll();
	}
	
	public Optional<Estado> save(Estado estado) {
		estado.setSigla(estado.getSigla().toUpperCase());
		return Optional.ofNullable(repository.saveAndFlush(estado));
	}

	public List<Estado> findBySigla(String siglaEstado) {
		return repository.findEstadoBySigla(siglaEstado);
	}

	public Optional<Estado> findOne(Long id) {
		return repository.findById(id); 
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	public List<String> getMensagensErros(BindingResult bindResult, boolean existeEsseEstado) {
		List<String> msg = new ArrayList<>();
		for(ObjectError objError : bindResult.getAllErrors()) {
			msg.add(objError.getDefaultMessage());
		}
		if (existeEsseEstado) {
			msg.add("Esse estado, já está cadastrado");
		}
		return msg;
	}

}
