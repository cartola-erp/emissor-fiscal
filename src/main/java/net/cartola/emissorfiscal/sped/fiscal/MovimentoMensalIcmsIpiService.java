package net.cartola.emissorfiscal.sped.fiscal;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.SAIDA;
import static net.cartola.emissorfiscal.documento.TipoServico.AGUA;
import static net.cartola.emissorfiscal.documento.TipoServico.CTE;
import static net.cartola.emissorfiscal.documento.TipoServico.ENERGIA;
import static net.cartola.emissorfiscal.documento.TipoServico.INTERNET;
import static net.cartola.emissorfiscal.documento.TipoServico.TELEFONE_FIXO_MOVEL;
import static net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal.NFSE;
import static net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal._59;
import static net.cartola.emissorfiscal.util.PredicateUtil.distinctByKey;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.criaPredicateIsOperacaoDoDocumentoEscriturada;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodItem;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.isDocumentoFiscalEmDigitacao;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.isNfeNaoAutorizada;
import static net.cartola.emissorfiscal.util.ValidationHelper.collectionIsEmptyOrNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.contador.ContadorService;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.documento.TipoServico;
import net.cartola.emissorfiscal.inventario.Inventario;
import net.cartola.emissorfiscal.inventario.InventarioService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.loja.LojaService;
import net.cartola.emissorfiscal.model.sped.fiscal.difal.SpedFiscalRegE310;
import net.cartola.emissorfiscal.model.sped.fiscal.difal.SpedFiscalRegE310Service;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110Service;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaAlteradoSped;
import net.cartola.emissorfiscal.pessoa.PessoaAlteradoSpedService;
import net.cartola.emissorfiscal.pessoa.PessoaService;
import net.cartola.emissorfiscal.produto.ProdutoAlteradoSped;
import net.cartola.emissorfiscal.produto.ProdutoAlteradoSpedService;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;
import net.cartola.emissorfiscal.produto.ProdutoUnidadeService;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualGuiaService;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Service
class MovimentoMensalIcmsIpiService implements BuscaMovimentacaoMensal<MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(MovimentoMensalIcmsIpiService.class.getName());
	
	@Autowired
	private DocumentoFiscalService docFiscService;
	
	@Autowired
	private DocumentoFiscalItemService docFiscalItemService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PessoaAlteradoSpedService pessAlterSpedService;
	
	@Autowired
	private ProdutoAlteradoSpedService prodAlterSpedService;
	
//	produto 
	
	@Autowired
	private ProdutoUnidadeService prodUnidService;
	
	@Autowired
	private OperacaoService operacaoService;
	
//	LOJA SERVICE
	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private ContadorService contadorService;
	
	@Autowired
	private InventarioService inventarioService;
	
	@Autowired
	private TributacaoEstadualGuiaService tribEstaGuiaService;
	
	@Autowired
	private SpedFiscalRegE110Service spedFiscRegE110ApuracaoPropriaService;
	
	@Autowired
	private SpedFiscalRegE310Service spedFiscRegE310DifalService;
	
	
	@Override
	public MovimentoMensalIcmsIpi buscarMovimentacoesDoPeriodo(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja) {
		LOG.log(Level.INFO, "Inicio da busca das movimentações para a LOJA {0}, no PERIODO de {1} a {2} " , new Object[]{loja, paramBuscaSped.getDataInicioSped(), paramBuscaSped.getDataHoraFimSped()});
		MovimentoMensalIcmsIpi movimentoMensalIcmsIpi = new MovimentoMensalIcmsIpi();

//		Optional<Pessoa> lojaEmitOrDest = pessoaService.findByCnpj(String.valueOf(loja.getCnpj()));

		List<DocumentoFiscal> listDocFiscal = getListDocumentoFiscal(paramBuscaSped, loja, asList(TipoServico.NENHUM, TipoServico.CTE), movimentoMensalIcmsIpi); // (Lista com todos os DocumentoFiscais, da loja que está sendo feito o SPED)
		List<DocumentoFiscal> listDocFiscalOutrosServicos = getListDocFiscalOutrosServicos(paramBuscaSped, loja, movimentoMensalIcmsIpi);
		List<DocumentoFiscal> listSatsEmitidos = getListSatsEmitidos(paramBuscaSped, loja, movimentoMensalIcmsIpi);		// Modelo _59
		List<Pessoa> listCadPessoas = getListCadastrosPessoas(listDocFiscal, listDocFiscalOutrosServicos);
		// TODO  Buscando todas as Pessoas Envolvidas, que tiveram alguma alteração necessária para o SPED ICMS IPI
		List<PessoaAlteradoSped> listPessAlteradoSped = getListCadastroPessoaAlteradoSped(paramBuscaSped, listCadPessoas);
		Set<DocumentoFiscalItem> setItens = getSetDeItens(listDocFiscal, loja);
		List<ProdutoAlteradoSped> listProdAlteradoSped = getListProdAlteradoSped(paramBuscaSped, setItens);
		List<ProdutoUnidade> listProdUnid = getListProdutoUnidade(setItens);
		Set<Operacao> operacoesSet = getListOperacoes(listDocFiscal, loja); //listDocFiscal.stream().map(DocumentoFiscal::getOperacao).collect(toSet());
		Map<String, Loja> mapLojasPorCnpj = lojaService.findAll().stream().collect(toMap(Loja::getCnpj, (Loja aLoja ) -> aLoja ));
		
		Set<DocumentoFiscal> setDocFiscalSantaCatarina = getListDocFiscalSantaCatarina(paramBuscaSped, loja);
		Contador contador = contadorService.findOne(paramBuscaSped.getContadorId()).get();
		Optional<Inventario> opInventario =  getInventario(paramBuscaSped, loja);
		Set<SpedFiscalRegE110> setRegE110ApuracaoPropria = spedFiscRegE110ApuracaoPropriaService.findRegE110ByPeriodoAndLoja(paramBuscaSped, loja);
		Set<SpedFiscalRegE310> setRegE310Difal = spedFiscRegE310DifalService.findRegE310ByPeriodoAndLoja(paramBuscaSped, loja);

		// ########## Setando os valores buscados acima que, para retornar o Obj de MOVIMENTACAO MENSAL ##########
//		TODO	-> SETAR no obj de movimentos --> 	Produto alterado Sped
	
		movimentoMensalIcmsIpi.setParamBuscaSped(paramBuscaSped);
		movimentoMensalIcmsIpi.setListDocumentoFiscal(listDocFiscal);
		movimentoMensalIcmsIpi.setListDocumentoFiscalServico(listDocFiscalOutrosServicos);
		movimentoMensalIcmsIpi.setListSatsEmitidos(listSatsEmitidos);
		movimentoMensalIcmsIpi.setListCadastros(listCadPessoas);
		movimentoMensalIcmsIpi.setListCadastrosAlteradosSped(listPessAlteradoSped);
		movimentoMensalIcmsIpi.setListItens(setItens);
		movimentoMensalIcmsIpi.setListProdutoAlteradoSped(listProdAlteradoSped);
		movimentoMensalIcmsIpi.setListProdUnid(listProdUnid);
		movimentoMensalIcmsIpi.setListOperacoes(operacoesSet);
		movimentoMensalIcmsIpi.setMapLojasPorCnpj(mapLojasPorCnpj);
		movimentoMensalIcmsIpi.setLoja(loja);
		movimentoMensalIcmsIpi.setContador(contador);
		opInventario.ifPresent(movimentoMensalIcmsIpi::setInventario);
		movimentoMensalIcmsIpi.setListDocFiscSantaCatarina(setDocFiscalSantaCatarina);			// Documentos Fiscais de comercialização de santa catarina
		movimentoMensalIcmsIpi.setSetSpedFiscRegE110ApuracaoPropria(setRegE110ApuracaoPropria);
		movimentoMensalIcmsIpi.setSetSpedFiscRegE310Difal(setRegE310Difal);
		
		
		movimentoMensalIcmsIpi.setDataInicio(paramBuscaSped.getDataInicioSped());
		movimentoMensalIcmsIpi.setDataFim(paramBuscaSped.getDataFimSped());
		
		LOG.log(Level.INFO, "Terminado a busca das movimentações para a LOJA {0}" ,loja);
		return movimentoMensalIcmsIpi;
	}

	
	private Set<String> getListCpfCnpj(List<Pessoa> listCadPessoas) {
		Set<String> cpfCnpjSet = new HashSet<>();
		Set<String> cpfSet = listCadPessoas.stream().filter(pessoa -> pessoa.getCpf() != null && !pessoa.getCpf().isEmpty()).map(Pessoa::getCpf).collect(toSet());
		Set<String> cnpjSet = listCadPessoas.stream().filter(pessoa -> pessoa.getCnpj() != null && !pessoa.getCnpj().isEmpty()).map(Pessoa::getCnpj).collect(toSet());
		
		cpfCnpjSet.addAll(cpfSet);
		cpfCnpjSet.addAll(cnpjSet);
		return cpfCnpjSet;
	}
	
	
	private List<DocumentoFiscal> getListDocumentoFiscal(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja, List<TipoServico> listTipoServico, MovimentoMensalIcmsIpi movimentoMensalIcmsIpi ) {
//		LOG.log(Level.INFO, "Buscando DocumentoFiscais, que o Tipo Servico seja == NENHUM" );
		List<DocumentoFiscal> listDocFiscal = new ArrayList<>();
		List<DocumentoFiscal> listDocFiscalEmitidasPelaLojaAtual = docFiscService.
				findByPeriodoCadastroAndLojaAndTipoServico(paramBuscaSped.getDataHoraInicioSped(), paramBuscaSped.getDataHoraFimSped(), loja, listTipoServico);

		List<DocumentoFiscal> listDocFiscEmDigitacao = listDocFiscalEmitidasPelaLojaAtual.stream()
				.filter(docFiscEmtidoPelaLojaAtual -> isDocumentoFiscalEmDigitacao(docFiscEmtidoPelaLojaAtual)).collect(toList());
		movimentoMensalIcmsIpi.addDocumentoFiscalEmDigitacao(listDocFiscEmDigitacao);

		listDocFiscalEmitidasPelaLojaAtual.removeIf(docFiscEmitidoPelaLojaAtual -> isDocumentoFiscalEmDigitacao(docFiscEmitidoPelaLojaAtual));
		listDocFiscal.addAll(listDocFiscalEmitidasPelaLojaAtual);
		
//		List<DocumentoFiscal> listDocFiscalEntradaEmitidoTerceiros = docFiscService.findByPeriodoCadastroAndLojaAndTipoServico(dataHoraInicio, dataHoraFim, loja, listTipoServico);
//		listDocFiscal.addAll(listDocFiscalEntradaEmitidoTerceiros);
//		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, DocumentosFiscais == TipoServico.NENHUM  ", listDocFiscal.size());
		return listDocFiscal;
	}
	
	/**
	 * 
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param loja
	 * @param movimentoMensalIcmsIpi 
	 * @return Todos os DocumentoFiscal do Periodo, Cujo O TipoServico seja algums dos -> OUTROS, CTE, ENERGIA, AGUA, INTERNET ou TELEFONE_FIXO_MOVEL
	 */
	private List<DocumentoFiscal> getListDocFiscalOutrosServicos(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja, MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		LOG.log(Level.INFO, "Buscando DocumentoFiscais, que sejam de SERVICO" );
		List<TipoServico> listTipoServico = asList(TipoServico.OUTROS, CTE, ENERGIA, AGUA, INTERNET, TELEFONE_FIXO_MOVEL);
		List<DocumentoFiscal> listDocFiscServico = getListDocumentoFiscal(paramBuscaSped, loja, listTipoServico, movimentoMensalIcmsIpi);
		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, DocumentosFiscais de SERVICO  ", listDocFiscServico.size());
		return listDocFiscServico;
	}
	
	
	private List<DocumentoFiscal> getListSatsEmitidos(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja, MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		List<DocumentoFiscal> listSatEmitido = docFiscService.
				findByPeriodoCadastroAndLojaAndModeloAndTipoOperacao(paramBuscaSped.getDataHoraInicioSped(), paramBuscaSped.getDataHoraFimSped(), loja, _59, SAIDA).
				stream().sorted(comparingLong(DocumentoFiscal::getNumeroNota)).collect(Collectors.toList());

		List<DocumentoFiscal> listSatEmitidoEmDigitacao = listSatEmitido.stream().filter(satEmitido -> isDocumentoFiscalEmDigitacao(satEmitido)).collect(toList());
		movimentoMensalIcmsIpi.addDocumentoFiscalEmDigitacao(listSatEmitidoEmDigitacao);

		listSatEmitido.removeIf(satEmitido -> isDocumentoFiscalEmDigitacao(satEmitido));
		return listSatEmitido;
	}
	
	private List<Pessoa> getListCadastrosPessoas(List<DocumentoFiscal> listDocFiscal, List<DocumentoFiscal> listDocFiscalOutrosServico) {
		LOG.log(Level.INFO, "Buscando todos os cadastros de Pessoas, com base nos DocumentoFiscais" );
		Set<Long> pessoasId = new HashSet<>();
		Predicate<DocumentoFiscal> escrituraDocumento = doc -> !_59.equals(doc.getModelo()) && !NFSE.equals(doc.getModelo()) && !TipoServico.OUTROS.equals(doc.getTipoServico());
		Predicate<DocumentoFiscal> isNfeAutorizada = nfe -> !isNfeNaoAutorizada(nfe);
		
		final Set<Long> idsEmitenteDocFiscal = listDocFiscal.stream().filter(docFisc -> escrituraDocumento.and(isNfeAutorizada).test(docFisc))
				.map(docFiscal -> docFiscal.getEmitente().getId()).collect(toSet());
		final Set<Long> idsDestinatarioDocFiscal = listDocFiscal.stream().filter(docFisc -> escrituraDocumento.and(isNfeAutorizada).test(docFisc))
				.map(docFiscal -> docFiscal.getDestinatario().getId()).collect(toSet());

		final Set<Long> idsEmitenteServico = listDocFiscalOutrosServico.stream().filter(docFiscServ -> escrituraDocumento.and(isNfeAutorizada).test(docFiscServ))
				.map(docFiscalServico -> docFiscalServico.getEmitente().getId()).collect(toSet());
		final Set<Long> idsDestinatarioServico = listDocFiscalOutrosServico.stream().filter(docFiscServ -> escrituraDocumento.and(isNfeAutorizada).test(docFiscServ))
				.map(docFiscalServico -> docFiscalServico.getDestinatario().getId()).collect(toSet());
		
		pessoasId.addAll(idsEmitenteDocFiscal);
		pessoasId.addAll(idsDestinatarioDocFiscal);
		pessoasId.addAll(idsEmitenteServico);
		pessoasId.addAll(idsDestinatarioServico);
		List<Pessoa> listCadPessoas = pessoaService.findByIdsIn(pessoasId);
		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, cadastro de pessoas (PF/PJ)  ", listCadPessoas.size());
		return listCadPessoas;
	}

	private List<PessoaAlteradoSped> getListCadastroPessoaAlteradoSped(MovimentoMensalParametrosBusca paramBuscaSped, List<Pessoa> listCadPessoas) {
		LOG.log(Level.INFO, "Buscando todos os cadastros de PessoaAlteradoSped (que foram alteradas), no PERIODO de {0} a {1} " , new Object[]{paramBuscaSped.getDataInicioSped(), paramBuscaSped.getDataFimSped()});
		Set<String> cpfCnpjSet = getListCpfCnpj(listCadPessoas);
		List<PessoaAlteradoSped> listPessAlteradoSped = pessAlterSpedService.findPessoaAlteradoPorPeriodoSped(paramBuscaSped.getDataInicioSped(), paramBuscaSped.getDataFimSped(), cpfCnpjSet);
		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, PessoaAlteradoSped (PF/PJ)  ", listPessAlteradoSped.size());
		return listPessAlteradoSped;
	}


	private Set<DocumentoFiscalItem> getSetDeItens(List<DocumentoFiscal> listDocFiscal, Loja lojaAtualSped) {
		LOG.log(Level.INFO, "Buscando todos os ITENS que estão em uma NFE e/ou SAT" );
		Set<DocumentoFiscalItem> setItens = new HashSet<>();
		Predicate<DocumentoFiscal> isBuscaItem = d -> !d.getTipoServico().equals(TipoServico.CTE) && !d.getTipoOperacao().equals(SAIDA);
		Predicate<DocumentoFiscal> isOperacaoDoDocumentoEscriturada = criaPredicateIsOperacaoDoDocumentoEscriturada(lojaAtualSped);

	    List<DocumentoFiscal> listDocFiscalSemCte = listDocFiscal.stream()
	    		.filter(docFisc -> isBuscaItem.and(isOperacaoDoDocumentoEscriturada).test(docFisc)).collect(toList());

		List<DocumentoFiscalItem> listDocFiscItens = docFiscalItemService.findItensByVariosDocumentoFiscal(getListIdsForDocFiscal(listDocFiscalSemCte));
		setItens.addAll(listDocFiscItens);
		
		Set<DocumentoFiscalItem> setItensUnicos = setItens.stream().filter(distinctByKey(item -> getCodItem(item))).collect(toSet());
		
		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, ITENS ", setItens.size());
		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, ITENS ÚNICOS", setItensUnicos.size());

		return setItensUnicos;
	}


	/**
	 * @param listDocFiscal
	 * @return
	 */
	public Set<Long> getListIdsForDocFiscal(List<DocumentoFiscal> listDocFiscal) {
		if (collectionIsEmptyOrNull(listDocFiscal)){
			return new HashSet<>();
		}
		return listDocFiscal.stream().map(DocumentoFiscal::getId).collect(toSet());
	}
	
	private List<ProdutoAlteradoSped> getListProdAlteradoSped(MovimentoMensalParametrosBusca paramBuscaSped, Set<DocumentoFiscalItem> setItens) {
		LOG.log(Level.INFO, "Buscando todos os produtos de ProdutoAlteradoSped (produtos que foram alteradas), no PERIODO de {0} a {1} " , new Object[]{paramBuscaSped.getDataInicioSped(), paramBuscaSped.getDataFimSped()});

		Set<Integer> setCodigoProdutoErp = new HashSet<>();
		setCodigoProdutoErp = setItens.stream().map(DocumentoFiscalItem::getProdutoCodigoErp).collect(toSet());
		List<ProdutoAlteradoSped> listProdAlteradoSped = prodAlterSpedService.findProdutoAlteradoPorPeriodoSpedEProdutoCodigo(paramBuscaSped.getDataInicioSped(), paramBuscaSped.getDataFimSped(), setCodigoProdutoErp);
		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, ProdutoAlteradoSped  ", listProdAlteradoSped.size());
		return listProdAlteradoSped;
	}
	
	/**
	 * Será retornado uma List<ProdutoUnidade>, que é referente a todas que foram usada em algum item no atual periodo;
	 * (Seja na entrada e/ou saída)
	 * 
	 * @param listItens
	 * @return
	 */
	private List<ProdutoUnidade> getListProdutoUnidade(Set<DocumentoFiscalItem> listItens) {
		LOG.log(Level.INFO, "Buscando todas as UNIDADES dos ITENS que estão em uma NFE e/ou SAT" );
		Set<ProdutoUnidade> setProdutoUnidade = listItens.stream().map(DocumentoFiscalItem::getUnidade).collect(toSet());
		Set<String> unidadesSet = setProdutoUnidade.stream().map(ProdutoUnidade::getSigla).collect(toSet());
				
		List<ProdutoUnidade> listProdUnid = prodUnidService.findByListSiglas(unidadesSet);
		LOG.log(Level.INFO, "Foram encontrado um total de: {0}, ProdutoUnidade  ", listProdUnid.size());
		return listProdUnid;
	}

	
	/**
	 * Será retornado apenas as operações de entradas <br> 
	 * Pois informamos apenas no REG C170 o código da operação (E esse registro é apenas nas entradas)
	 * 
	 * @param listDocFiscal
	 * @param lojaAtualSped 
	 * @return
	 */
	private Set<Operacao> getListOperacoes(List<DocumentoFiscal> listDocFiscal, Loja lojaAtualSped) {
		Set<Operacao> setOperacoes = new HashSet<>();
		final Predicate<DocumentoFiscal> isOperacaoDoDocumentoEscriturada = criaPredicateIsOperacaoDoDocumentoEscriturada(lojaAtualSped);
		final Set<Operacao> setOperacoesDocFiscal = listDocFiscal.stream()
				.filter(docFiscal -> isOperacaoDoDocumentoEscriturada.test(docFiscal))
				.map(DocumentoFiscal::getOperacao).collect(toSet());
		setOperacoes.addAll(setOperacoesDocFiscal);
		
		return setOperacoes;
	}
	
	/**
	 * Será retornado todos os DocumentoFiscais de ENTRADA, cujo a Origem esteja na TABELA "trib_esta_guia";	<\br>
	 * O nome está como de "SC - Santa Catarina", pois é de lá que a maioria são, e é como a contabilidade acaba se referindo para essas notas/compras, 
	 * que "nóis", que somos obrigados por recolher o ICMS 
	 * 
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param loja
	 * @return
	 */
	private Set<DocumentoFiscal> getListDocFiscalSantaCatarina(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja) {
		Set<DocumentoFiscal> setDocFiscSantaCatarina = docFiscService.findDocsQueRecolhemosIcmsNaEntradaDeSantaCatarinaPorPeriodo(paramBuscaSped.getDataHoraInicioSped(), paramBuscaSped.getDataHoraFimSped());
		Set<DocumentoFiscal> listDocFiscSCLojaAtual = setDocFiscSantaCatarina.stream().filter(docFiscSc -> docFiscSc.getLoja().getId().equals(loja.getId()) 
				&& docFiscSc.getDestinatario().getCnpj().equals(loja.getCnpj())).collect(toSet());
		
		return listDocFiscSCLojaAtual;
	}

	
	/**
	 * Se for um inventário específico, será retornado pelo id que está em "paraBuscaSped" <\br>
	 * Caso seja, por período (quando exporta para todas as lojas), será buscado pelo período informado e da loja 
	 * que está sendo "iterada" no momento;
	 * 
	 * @param paramBuscaSped
	 * @param loja
	 * @return
	 */
	private Optional<Inventario> getInventario(MovimentoMensalParametrosBusca paramBuscaSped, Loja loja) {
		Predicate<MovimentoMensalParametrosBusca> exportarInventarioPorId = 
				inve -> ( inve.isExportarInventario() && inve.getInventarioId() != null  && 
					inve.getDataInicioInventario() == null && inve.getDataFimInventario() == null);
		Predicate<MovimentoMensalParametrosBusca> exportarInventarioPorPeriodo = 
				inve -> inve.isExportarInventario() && inve.getDataInicioInventario() != null && inve.getDataFimInventario() != null;
				
		if (exportarInventarioPorId.test(paramBuscaSped)) {
			 return inventarioService.findOne(paramBuscaSped.getInventarioId());
		} else if (exportarInventarioPorPeriodo.test(paramBuscaSped)) {
			return inventarioService.findByLojaAndPeriodo(loja, paramBuscaSped);
		}
		return Optional.empty();
	}


	
}
