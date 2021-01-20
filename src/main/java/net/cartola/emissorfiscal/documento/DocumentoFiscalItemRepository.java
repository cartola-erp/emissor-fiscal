package net.cartola.emissorfiscal.documento;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.ncm.Ncm;

@Repository
public interface DocumentoFiscalItemRepository extends JpaRepository<DocumentoFiscalItem, Long> {

	List<DocumentoFiscalItem> findByNcm(Ncm ncm);

	List<DocumentoFiscalItem> findByNcmIn(Collection<Ncm> ncms);

	List<DocumentoFiscalItem> findByDocumentoFiscalIn(Collection<DocumentoFiscal> listDocumentosFiscais);

}
