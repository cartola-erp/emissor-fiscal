package net.cartola.emissorfiscal.ncm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NcmService {
	
	@Autowired
	NcmRepository repository;
	
	public List<Ncm> findAll() {
		return repository.findAll();
	}
	
	public Ncm save(Ncm ncm) {
		return repository.saveAndFlush(ncm);
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
	

}
