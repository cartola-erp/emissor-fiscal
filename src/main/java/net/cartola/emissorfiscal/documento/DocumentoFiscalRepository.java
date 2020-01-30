package net.cartola.emissorfiscal.documento;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.operacao.Operacao;

@Repository
public interface DocumentoFiscalRepository extends JpaRepository<DocumentoFiscal, Long> {

	List<DocumentoFiscal> findByOperacao(Operacao operacao);

	List<DocumentoFiscal> findByOperacaoIn(Collection<Operacao> operacoes);

//	@Query("SELECT doc, e FROM DocumentoFiscal doc INNER JOIN doc.emitente e WHERE e.id = :idEmitente")
//	List <DocumentoFiscal> findDocFiscalByEmitenteId(@Param("idEmitente") Long idEmitente);
	
	Optional<DocumentoFiscal> findDocumentoFiscalByEmitenteCnpjAndTipoAndSerieAndNumero(Long cnpj, String tipo, Long serie, Long numero);
	

}
