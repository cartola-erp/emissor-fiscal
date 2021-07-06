package net.cartola.emissorfiscal.model.sped.fiscal.icms.propria;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.loja.Loja;

/**
 * @date 5 de jul. de 2021
 * @author robson.costa
 */
@Repository
public interface SpedFiscalRegE110Repository extends JpaRepository<SpedFiscalRegE110, Long> {

	Optional<SpedFiscalRegE111> findByDataInicioApuracaoAndDataFimApuracaoAndLoja(LocalDate dataInicio, LocalDate dataFim, Loja loja);

	
}
