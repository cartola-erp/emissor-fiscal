package net.cartola.emissorfiscal.ncm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NcmRepository extends JpaRepository<Ncm, Long> {
	
	Ncm findByNumero(Long numero);
}
