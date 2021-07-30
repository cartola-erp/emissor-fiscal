package net.cartola.emissorfiscal.documento;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	List<DocumentoFiscal> findByCadastroBetweenAndDestinatarioAndTipoOperacaoAndTipoServicoIn(LocalDateTime dataInicio, LocalDateTime dataFim, Pessoa destinatario, 
			IndicadorDeOperacao indOper, Collection<TipoServico> tipoServico);

	/**
	 * Retorna todos os DocumentoFiscais, em um determinado Periodo, para um Emitente, especifico 
	 * 
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param emitente
	 * @return
	 */
	List<DocumentoFiscal> findByCadastroBetweenAndEmitenteAndTipoServicoIn(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Pessoa emitente, Collection<TipoServico> listTipoServico);

	
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
	
	
	// OBS: Será retornado todos os doc fiscais, do periodo independente da loja que deram entrada
	// OBS2: Não necessariamente os DocumentoFiscais que foram retornados aqui, seram pagos/ (ou foram pagos o ICMS na entrada)
	// OBS3: Para descobrir isso terei que ver com base nos itens dos doc fiscais, se eles tem tributacoes na tabela trib_eta_guia
	// (Basta em chamar o método que calcula isso)
	@Query(value =  "SELECT * FROM docu_fisc d \r\n"
			+ "  		INNER JOIN pess p ON (d.emit_id = p.id) \r\n"
			+ "     		INNER JOIN pess_end e ON (p.end_id = e.pess_end_id) \r\n"
			+ "           		INNER JOIN esta uf ON (e.uf = uf.sigla)	\r\n"
			+ "                		INNER JOIN trib_esta_guia t ON (t.esta_orig_id  = uf.esta_id AND d.oper_id = t.oper_id) "
			+ "	   	 WHERE d.cadastro BETWEEN :dtInicio AND :dtFim ;", nativeQuery = true)
	Set<DocumentoFiscal> findAllDocsInterestadualQuePagamosIcmsNaEntradaPorPeriodo(LocalDateTime dtInicio, LocalDateTime dtFim);

	
	
	
}
