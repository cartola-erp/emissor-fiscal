package net.cartola.emissorfiscal.documento;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoFiscalItemRepository extends JpaRepository<DocumentoFiscalItem, Long> {

	/**
	 * @Query(value = " SELECT * FROM docu_fisc_item WHERE docu_fisc_id IN (:listDocFiscIds); ", nativeQuery = true)
	 * 
	 * @param listDocFiscIds
	 * @return
	 */
	@Query(value = " SELECT * FROM docu_fisc_item WHERE docu_fisc_id IN (:listDocFiscIds); ", nativeQuery = true)
	List<DocumentoFiscalItem> findByDocumentoFiscalIdIn(Collection<Long> listDocFiscIds);

}
