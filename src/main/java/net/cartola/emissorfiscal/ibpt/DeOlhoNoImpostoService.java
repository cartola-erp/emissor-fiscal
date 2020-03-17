package net.cartola.emissorfiscal.ibpt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 17 de mar de 2020
 * @author robson.costa
 */
@Service
public class DeOlhoNoImpostoService {
	
	@Autowired
	private DeOlhoNoImpostoRepository olhoNoImpostoRepository;
	
	public Optional<DeOlhoNoImposto> findNcmByNumeroAndExcecao(int ncm, String exce) {
		return olhoNoImpostoRepository.findByNcmAndExce(ncm, exce);
	}
	
	public Optional<DeOlhoNoImposto> findOne(Long id) {
		return olhoNoImpostoRepository.findById(id);
	}
	
	//  fazer calculo
	
}

