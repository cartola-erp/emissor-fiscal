package net.cartola.emissorfiscal.ncm;

import java.util.List;

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
	

}
