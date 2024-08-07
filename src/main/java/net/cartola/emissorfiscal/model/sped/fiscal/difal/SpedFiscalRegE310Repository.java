package net.cartola.emissorfiscal.model.sped.fiscal.difal;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.loja.Loja;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Repository
public interface SpedFiscalRegE310Repository extends JpaRepository<SpedFiscalRegE310, Long> {

	Optional<SpedFiscalRegE310> findByDataInicioApuracaoAndDataFimApuracaoAndLoja(LocalDate dataInicio, LocalDate dataFim, Loja loja);

	Set<SpedFiscalRegE310> findByDataInicioApuracaoGreaterThanEqualAndDataFimApuracaoLessThanEqualAndLoja(
			LocalDate dataInicio, LocalDate dataFim, Loja loja);

	
}
