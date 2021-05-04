package net.cartola.emissorfiscal.ibpt;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 17 de mar de 2020
 * @author robson.costa
 */
@Repository
public interface DeOlhoNoImpostoRepository extends JpaRepository<DeOlhoNoImposto, Long> {
	
	List<DeOlhoNoImposto> findByNcmInAndExIn(Collection<Integer> ncm, Collection<String> exce);
	
}

