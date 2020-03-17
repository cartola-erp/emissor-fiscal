package net.cartola.emissorfiscal.ibpt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 17 de mar de 2020
 * @author robson.costa
 */
@Repository
public interface DeOlhoNoImpostoRepository extends JpaRepository<DeOlhoNoImposto, Long> {
	
	@Query("SELECT imp FROM DeOlhoNoImposto imp WHERE imp.ncm = :ncm AND imp.exce = :exce")
	Optional<DeOlhoNoImposto> findByNcmAndExce(int ncm, String exce);
}

