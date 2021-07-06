package net.cartola.emissorfiscal.model.sped.fiscal.difal;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Repository
public interface SpedFiscalRegE316Repository extends JpaRepository<SpedFiscalRegE316, Long> {

	Optional<SpedFiscalRegE316> findByDataInicioAndDataFim(LocalDate dataInicio, LocalDate dataFim);

}
