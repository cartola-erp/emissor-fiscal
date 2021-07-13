package net.cartola.emissorfiscal.sped.fiscal;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static net.cartola.emissorfiscal.documento.TipoServico.AGUA;
import static net.cartola.emissorfiscal.documento.TipoServico.CTE;
import static net.cartola.emissorfiscal.documento.TipoServico.ENERGIA;
import static net.cartola.emissorfiscal.documento.TipoServico.INTERNET;
import static net.cartola.emissorfiscal.documento.TipoServico.TELEFONE_FIXO_MOVEL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.contador.ContadorService;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.documento.TipoServico;
import net.cartola.emissorfiscal.loja.Loja;
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
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Service
class MovimentoMensalIcmsIpiService implements BuscaMovimentacaoMensal<MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(MovimentoMensalIcmsIpi.class.getName());
	
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
	private ContadorService contadorService;
	
	
	@Autowired
	private SpedFiscalRegE110Service spedFiscRegE110ApuracaoPropriaService;
	
	@Autowired
	private SpedFiscalRegE310Service spedFiscRegE310DifalService;
	
	
	@Override
	public MovimentoMensalIcmsIpi buscarMovimentacoesDoPeriodo(Loja loja, Long contadorId, LocalDate dataInicio, LocalDate dataFim) {
		LOG.log(Level.INFO, "Inicio da busca das movimentações para a LOJA {0}, no PERIODO de {1} a {2} " , new Object[]{loja, dataInicio, dataFim});
		MovimentoMensalIcmsIpi movimentoMensalIcmsIpi = new MovimentoMensalIcmsIpi();

		LocalDateTime dataHoraInicio = LocalDateTime.of(dataInicio, LocalTime.of(4, 0, 0));
		LocalDateTime dataHoraFim = LocalDateTime.of(dataFim, LocalTime.of(23, 59, 0));
		
		Optional<Pessoa> lojaEmitOrDest = pessoaService.findByCnpj(String.valueOf(loja.getCnpj()));

		List<DocumentoFiscal> listDocFiscal = getListDocumentoFiscal(dataHoraInicio, dataHoraFim, lojaEmitOrDest, asList(TipoServico.NENHUM)); // (Lista com todos os DocumentoFiscais, da loja que está sendo feito o SPED)
		List<DocumentoFiscal> listDocFiscalServico = getListDocumentoFiscalServico(dataHoraInicio, dataHoraFim, lojaEmitOrDest);
		List<DocumentoFiscal> listSatsEmitidos = getListSatsEmitidos(dataHoraInicio, dataHoraFim, lojaEmitOrDest);		// Modelo _59
		List<Pessoa> listCadPessoas = getListCadastrosPessoas(listDocFiscal, listDocFiscalServico);
		// TODO  Buscando todas as Pessoas Envolvidas, que tiveram alguma alteração necessária para o SPED ICMS IPI
		List<PessoaAlteradoSped> listPessAlteradoSped = getListCadastroPessoaAlteradoSped(dataInicio, dataFim, listCadPessoas);
		Set<DocumentoFiscalItem> setItens = getSetDeItens(listDocFiscal, listSatsEmitidos);
		List<ProdutoAlteradoSped> listProdAlteradoSped = getListProdAlteradoSped(dataInicio, dataFim, setItens);
		List<ProdutoUnidade> listProdUnid = getListProdutoUnidade(setItens);
		Set<Operacao> operacoesSet = getListOperacoes(listDocFiscal, listSatsEmitidos, listDocFiscalServico); //listDocFiscal.stream().map(DocumentoFiscal::getOperacao).collect(toSet());
		
		Contador contador = contadorService.findOne(contadorId).get();
		Set<SpedFiscalRegE110> setRegE110ApuracaoPropria = spedFiscRegE110ApuracaoPropriaService.findRegE110ByPeriodoAndLoja(dataInicio, dataFim, loja);
		Set<SpedFiscalRegE310> setRegE310Difal = spedFiscRegE310DifalService.findRegE310ByPeriodoAndLoja(dataInicio, dataFim, loja);
		
		// ########## Setando os valores buscados acima que, para retornar o Obj de MOVIMENTACAO MENSAL ##########
//		TODO	-> SETAR no obj de movimentos --> 	Produto alterado Sped
	
		
		movimentoMensalIcmsIpi.setListDocumentoFiscal(listDocFiscal);
		movimentoMensalIcmsIpi.setListDocumentoFiscalServico(listDocFiscalServico);
		movimentoMensalIcmsIpi.setListSatsEmitidos(listSatsEmitidos);
		movimentoMensalIcmsIpi.setListCadastros(listCadPessoas);
		movimentoMensalIcmsIpi.setListCadastrosAlteradosSped(listPessAlteradoSped);
		movimentoMensalIcmsIpi.setListItens(setItens);
		movimentoMensalIcmsIpi.setListProdutoAlteradoSped(listProdAlteradoSped);
		movimentoMensalIcmsIpi.setListProdUnid(listProdUnid);
		movimentoMensalIcmsIpi.setListOperacoes(operacoesSet);
		movimentoMensalIcmsIpi.setLoja(loja);
		movimentoMensalIcmsIpi.setContador(contador);
		
		movimentoMensalIcmsIpi.setSetSpedFiscRegE110ApuracaoPropria(setRegE110ApuracaoPropria);
		movimentoMensalIcmsIpi.setSetSpedFiscRegE310Difal(setRegE310Difal);
		
		
		movimentoMensalIcmsIpi.setDataInicio(dataInicio);
		movimentoMensalIcmsIpi.setDataFim(dataFim);
		
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
	
	
	private List<DocumentoFiscal> getListDocumentoFiscal(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Optional<Pessoa> lojaEmitOrDest, List<TipoServico> listTipoServico ) {
		List<DocumentoFiscal> listDocFiscal = new ArrayList<>();
		List<DocumentoFiscal> listDocFiscalEntradaEmitidoTerceiros = docFiscService.findByPeriodoCadastroAndLojaAndTipoOperacaoAndTipoServico(dataHoraInicio, dataHoraFim, lojaEmitOrDest.get(), IndicadorDeOperacao.ENTRADA, listTipoServico);
		List<DocumentoFiscal> listDocFiscalEmitidasPelaLojaAtual = docFiscService.findByPeriodoCadastroAndEmitenteAndTipoServico(dataHoraInicio, dataHoraFim, lojaEmitOrDest.get(), listTipoServico);
		
		listDocFiscal.addAll(listDocFiscalEntradaEmitidoTerceiros);
		listDocFiscal.addAll(listDocFiscalEmitidasPelaLojaAtual);
		return listDocFiscal;
	}
	
	/**
	 * 
	 * @param dataHoraInicio
	 * @param dataHoraFim
	 * @param lojaEmitOrDest
	 * @return Todos os DocumentoFiscal do Periodo, Cujo O TipoServico seja algums dos -> OUTROS, CTE, ENERGIA, AGUA, INTERNET ou TELEFONE_FIXO_MOVEL
	 */
	private List<DocumentoFiscal> getListDocumentoFiscalServico(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Optional<Pessoa> lojaEmitOrDest) {
		List<TipoServico> listTipoServico = asList(TipoServico.OUTROS, CTE, ENERGIA, AGUA, INTERNET, TELEFONE_FIXO_MOVEL);
		return getListDocumentoFiscal(dataHoraInicio, dataHoraFim, lojaEmitOrDest, listTipoServico);
	}
	
	
	private List<DocumentoFiscal> getListSatsEmitidos(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Optional<Pessoa> lojaEmitOrDest) {
//		List<DocumentoFiscal> listSatsEmitidos = new ArrayList<>();
		return docFiscService.findByPeriodoCadastroAndEmitenteAndModeloAndTipoOperacao(dataHoraInicio, dataHoraFim, lojaEmitOrDest.get(), ModeloDocumentoFiscal._59, IndicadorDeOperacao.SAIDA);
	}
	
	private List<Pessoa> getListCadastrosPessoas(List<DocumentoFiscal> listDocFiscal, List<DocumentoFiscal> listDocFiscalServico) {
		Set<Long> pessoasId = listDocFiscal.stream().map(docFiscal -> docFiscal.getEmitente().getId()).collect(toSet());
		pessoasId.addAll(listDocFiscal.stream().map(docFiscal -> docFiscal.getDestinatario().getId()).collect(toSet()));
		
		pessoasId.addAll(listDocFiscal.stream().map(docFiscalServico -> docFiscalServico.getEmitente().getId()).collect(toSet()));
		pessoasId.addAll(listDocFiscalServico.stream().map(docFiscalServico -> docFiscalServico.getDestinatario().getId()).collect(toSet()));
		List<Pessoa> listCadPessoas = pessoaService.findByIdsIn(pessoasId);
		return listCadPessoas;
	}

	private List<PessoaAlteradoSped> getListCadastroPessoaAlteradoSped(LocalDate dataInicio, LocalDate dataFim, List<Pessoa> listCadPessoas) {
		Set<String> cpfCnpjSet = getListCpfCnpj(listCadPessoas);
		List<PessoaAlteradoSped> listPessAlteradoSped = pessAlterSpedService.findPessoaAlteradoPorPeriodoSped(dataInicio, dataFim, cpfCnpjSet);
		return listPessAlteradoSped;
	}


	private Set<DocumentoFiscalItem> getSetDeItens(List<DocumentoFiscal> listDocFiscal, List<DocumentoFiscal> listSatsEmitidos) {
		Set<DocumentoFiscalItem> setItens = new HashSet<>();
		
		List<DocumentoFiscalItem> listDocFiscItens = docFiscalItemService.findItensByVariosDocumentoFiscal(listDocFiscal);
		List<DocumentoFiscalItem> listItensDosSats = docFiscalItemService.findItensByVariosDocumentoFiscal(listSatsEmitidos);
		
		setItens.addAll(listDocFiscItens);
		setItens.addAll(listItensDosSats);
		return setItens;
	}
	
	private List<ProdutoAlteradoSped> getListProdAlteradoSped(LocalDate dataInicio, LocalDate dataFim, Set<DocumentoFiscalItem> setItens) {
		Set<Integer> setCodigoProdutoErp = new HashSet<>();
		setCodigoProdutoErp = setItens.stream().map(DocumentoFiscalItem::getProdutoCodigoErp).collect(toSet());
		List<ProdutoAlteradoSped> listProdAlteradoSped = prodAlterSpedService.findProdutoAlteradoPorPeriodoSped(dataInicio, dataFim, setCodigoProdutoErp);
		return listProdAlteradoSped;
	}
	
	private List<ProdutoUnidade> getListProdutoUnidade(Set<DocumentoFiscalItem> listItens) {
		Set<String> unidadesSet = listItens.stream().map(DocumentoFiscalItem::getUnidade).collect(toSet());
		List<ProdutoUnidade> listProdUnid = prodUnidService.findByListSiglas(unidadesSet);

		return listProdUnid;
	}

	private Set<Operacao> getListOperacoes(List<DocumentoFiscal> listDocFiscal, List<DocumentoFiscal> listSatsEmitidos, List<DocumentoFiscal> listDocFiscalServico) {
		Set<Operacao> setOperacoes = new HashSet<>();
		Set<Operacao> setOperacoesDocFiscal = listDocFiscal.stream().map(DocumentoFiscal::getOperacao).collect(toSet());
		Set<Operacao> setOperacoesSats = listSatsEmitidos.stream().map(DocumentoFiscal::getOperacao).collect(toSet());
		Set<Operacao> setOperacoesServico = listDocFiscalServico.stream().map(DocumentoFiscal::getOperacao).collect(toSet());

		setOperacoes.addAll(setOperacoesDocFiscal);
		setOperacoes.addAll(setOperacoesSats);
		setOperacoes.addAll(setOperacoesServico);
		return setOperacoes;
	}

	
}
