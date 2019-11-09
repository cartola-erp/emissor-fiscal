package net.cartola.emissorfiscal.estado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

	@Query("SELECT e FROM Estado e WHERE e.sigla = :sigla")
	Optional<Estado> findEstadoBySigla(String sigla);

}
