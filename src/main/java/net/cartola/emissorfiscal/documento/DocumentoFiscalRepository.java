package net.cartola.emissorfiscal.documento;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

@Repository
public interface DocumentoFiscalRepository extends JpaRepository<DocumentoFiscal, Long> {

	List<DocumentoFiscal> findByOperacao(Operacao operacao);

	List<DocumentoFiscal> findByOperacaoIn(Collection<Operacao> operacoes);

//	@Query("SELECT doc, e FROM DocumentoFiscal doc INNER JOIN doc.emitente e WHERE e.id = :idEmitente")
//	List <DocumentoFiscal> findDocFiscalByEmitenteId(@Param("idEmitente") Long idEmitente);
	
	Optional<DocumentoFiscal> findDocumentoFiscalByEmitenteCnpjAndTipoOperacaoAndSerieAndNumeroNota(String cnpj, IndicadorDeOperacao tipoOperacao, Long serie, Long numero);

	/**
	 * Busca todos os DocumentoFiscais, de um determinado Periodo, para um Destinatario, para um TipoOperacao
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @param destinatario
	 * @param indOper
	 * @return
	 */
	List<DocumentoFiscal> findByCadastroBetweenAndDestinatarioAndTipoOperacao(LocalDateTime dataInicio, LocalDateTime dataFim, Pessoa destinatario, IndicadorDeOperacao indOper);

	/**
	 * Retorna todos os DocumentoFiscais, em um determinado Periodo, para um Emitente, especifico 
	 * 
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param emitente
	 * @return
	 */
	List<DocumentoFiscal> findByCadastroBetweenAndEmitente(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Pessoa emitente);

	
	List<DocumentoFiscal> findByNfeChaveAcessoIn(Collection<String> setChaveAcessoReferencia);
	
	/**
	 * Buscando todos os DocumentoFiscais de um PERIODO para um determinado MODELO para uma Pessoa de ENTRADA ou SAÍDA
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param emitente
	 * @param modelo
	 * @return
	 */
	List<DocumentoFiscal> findByCadastroBetweenAndEmitenteAndModeloAndTipoOperacao(LocalDateTime dataHoraInicio,
			LocalDateTime dataHoraFim, Pessoa emitente, ModeloDocumentoFiscal modelo, IndicadorDeOperacao tipoOperacao);
	


}
