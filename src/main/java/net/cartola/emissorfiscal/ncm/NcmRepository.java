package net.cartola.emissorfiscal.ncm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NcmRepository extends JpaRepository<Ncm, Long> {
	


//	@Query("SELECT n FROM Ncm n WHERE n.numero LIKE %:numero%")
	@Query("SELECT n FROM Ncm n WHERE n.numero = :numero")
	List<Ncm> findNcmByNumero(int numero);
}
