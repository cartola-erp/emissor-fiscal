package net.cartola.emissorfiscal.cfop;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CfopService {	

	@Autowired
	private CfopRepository cfopRepository;

	public List<Cfop> findAll() {
		return cfopRepository.findAll();
	}

	public Page<Cfop> rtnTodos(Pageable pageable){
		return  cfopRepository.findAll(pageable);
	}

	public Optional<Cfop> save(Cfop cfop) {
		return Optional.ofNullable(cfopRepository.saveAndFlush(cfop));
	}

	public List<Cfop> findByNumero(int numeroNcm) {
		return cfopRepository.findNcmByNumero(numeroNcm);
	}
	
//	public Optional<Cfop> findNcmByNumeroAndExcecao(int numero, int excecao) {
//		return cfopRepository.findNumeroAndExcecao(numero, excecao);
//	}
	
	public Optional<Cfop> findOne(Long id) {
		return cfopRepository.findById(id);
	}

	public void deleteById(long id) {
		cfopRepository.deleteById(id);
	}

	// ==================== "VALIDAÇÕES" ===================

//	public List<String> getMensagensErros(BindingResult bindResult, boolean existeNumeroEExecao) {
//		List<String> msg = new ArrayList<>();
//		for (ObjectError objError : bindResult.getAllErrors()) {
//			msg.add(objError.getDefaultMessage());
//		}
//		if (existeNumeroEExecao) {
//			msg.add("Já existe essa combinação de NÚMERO e EXCEÇÃO");
//		}
//		return msg;
//	}
}
