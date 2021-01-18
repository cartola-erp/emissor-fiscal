package net.cartola.emissorfiscal.engine;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 15 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface CestNcmRepository extends JpaRepository<CestNcmModel, Integer>{

	List<CestNcmModel> findByNcmIn(Collection<Integer> ncms);

//	List<CestNcmModel> findByNcmIn(List<String> ncms);
	
	
}

