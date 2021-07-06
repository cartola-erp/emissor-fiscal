package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Repository
public interface SpedFiscalRegE116Repository extends JpaRepository<SpedFiscalRegE116, Long> {

	Optional<SpedFiscalRegE116> findByDataInicioSpedAndDataFimSped(LocalDate dataInicio, LocalDate dataFim);

}
