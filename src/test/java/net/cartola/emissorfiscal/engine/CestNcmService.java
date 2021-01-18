package net.cartola.emissorfiscal.engine;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 15 de jan. de 2021
 * @author robson.costa
 */
@Service
public class CestNcmService {

	@Autowired
	private CestNcmRepository cestNcmRepository;
	
	
	public List<CestNcmModel> findByNcmIn(Collection<Integer> ncms) {
		return cestNcmRepository.findByNcmIn(ncms);
	}

//	public List<CestNcmModel> findByNcmIn(List<String> ncms) {
//		return cestNcmRepository.findByNcmIn(ncms);
//	}

}
