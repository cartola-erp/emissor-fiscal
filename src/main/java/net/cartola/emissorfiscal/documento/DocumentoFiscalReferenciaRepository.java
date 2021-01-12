package net.cartola.emissorfiscal.documento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @date 11 de jan. de 2021
 * @author robson.costa
 */
@Repository
public interface DocumentoFiscalReferenciaRepository extends JpaRepository<DocumentoFiscalReferencia, Long>{

}
