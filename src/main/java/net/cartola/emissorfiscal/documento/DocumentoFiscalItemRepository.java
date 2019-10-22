package net.cartola.emissorfiscal.documento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoFiscalItemRepository extends JpaRepository<DocumentoFiscalItem, Long> {

	
}
