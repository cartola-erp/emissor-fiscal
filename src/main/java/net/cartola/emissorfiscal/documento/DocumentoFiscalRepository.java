package net.cartola.emissorfiscal.documento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoFiscalRepository extends JpaRepository<DocumentoFiscal, Long> {

	
}
