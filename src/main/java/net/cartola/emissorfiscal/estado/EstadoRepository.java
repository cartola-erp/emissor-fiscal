package net.cartola.emissorfiscal.estado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	


 	@Query("SELECT e FROM Estado e WHERE e.sigla = :sigla")
 	List<Estado> findEstadoBySigla (String sigla);
	
	
}
