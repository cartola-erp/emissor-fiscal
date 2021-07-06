package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 5 de jul. de 2021
 * @author robson.costa
 */
@Repository
public interface SpedFiscalRegE111Repository extends JpaRepository<SpedFiscalRegE111, Long> {

	Optional<SpedFiscalRegE111> findByDataInicioSpedAndDataFimSped(LocalDate dataInicio, LocalDate dataFim);

	
}
