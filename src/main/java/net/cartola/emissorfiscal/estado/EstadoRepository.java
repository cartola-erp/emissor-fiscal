package net.cartola.emissorfiscal.estado;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

	@Query("SELECT e FROM Estado e WHERE e.sigla = :sigla")
	Optional<Estado> findEstadoBySigla(EstadoSigla sigla);

	Set<Estado> findBySiglaIn(List<EstadoSigla> listSigla);

}
