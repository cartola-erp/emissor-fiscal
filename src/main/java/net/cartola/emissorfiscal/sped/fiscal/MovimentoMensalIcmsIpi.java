package net.cartola.emissorfiscal.sped.fiscal;

import static java.util.stream.Collectors.toMap;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.ENTRADA;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.SAIDA;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodSituacao;
import static net.cartola.emissorfiscal.util.ValidationHelper.collectionNotEmptyOrNull;
import static net.cartola.emissorfiscal.util.ValidationHelper.isMapNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.model.sped.fiscal.difal.SpedFiscalRegE310;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaAlteradoSped;
import net.cartola.emissorfiscal.produto.ProdutoAlteradoSped;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;


/**
 * 
 * Essa classe deverá encapsular os objetos, que tem no ERP (banco autogeral)
 * Pois será a responsavel de receber o JSON no Controller, e gerar o ARQUIVO do SPED FISCAL
 * Caso eu pense em um nome melhor, basta eu mudar aqui, já que criei apenas para colocar nos
 * generics das interface que irei usar nas Services
 * 
 * PS: Tbm tenho que ver se esse é o melhor pacote para ficar essa model ( @MovimentacoesMensalIcmsIpi)
 *
 * 
 * 21/09/2020
 * @author robson.costa
 */
@Getter
@Setter
public class MovimentoMensalIcmsIpi {
	
//	private List<DocumentoFiscal> listCompras;
	@Setter(value = AccessLevel.NONE)
	private List<DocumentoFiscal> listDocFiscInterestadualComDifal;
	@Setter(value = AccessLevel.NONE) private Map<Long, DocumentoFiscal> mapDocumentoFiscalPorCodigo;
	
	/**
	 * Mapa preenchido em RUNTIME, depois de ser gerado o REG C100/ D100
	 * Até o momento, é usado somente no REG E110, para calcular o campo 15 (DEB_ESP)
	 */
	@Setter(value = AccessLevel.NONE) private Map<SituacaoDoDocumento, Set<DocumentoFiscal>> mapDocumentoFiscalPorSituacao;
	
	private List<DocumentoFiscal> listDocumentoFiscal;
	private List<DocumentoFiscal> listDocumentoFiscalServico;
	private Set<DocumentoFiscal> listDocFiscSantaCatarina;
	private List<DocumentoFiscal> listSatsEmitidos; 		// DocumentoFiscal - Modelo _59
	private List<Pessoa> listCadastros;
	private List<PessoaAlteradoSped> listCadastrosAlteradosSped;
	
	private Set<DocumentoFiscalItem> listItens;
	private List<ProdutoAlteradoSped> listProdutoAlteradoSped;
	private List<ProdutoUnidade> listProdUnid;
	private Collection<Operacao> listOperacoes;
	
	private Loja loja;
//	private Pessoa loja;			// CONFESSO que ainda tou na duvida se pego a loja de "CADASTROS" ou da tbl "LOJA" msm
	private Contador contador;
	
	private List<CodificacaoReg0450InfoComplementarFisco> listCodInfoComplementarFisco;
	/**
	 * Super Classe, dos REGISTROS -> C195 e D195
	 * Atualmente é usado para Gerar o REGISTRO 0460 (e também quando tudo que teve ajuste)
	 * 
	 */
	private Set<ObservacoesLancamentoFiscal> setObservacoesLancamentoFiscal = new HashSet<>();
//	private Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes;
	
	/**
	 * Registro, Filho do E100. Apesar de poder ter vários E100, no DocumentoFiscal. Acredito que sempre
	 * terá 1 nos SPED que geramos. Por isso estarei somandos os valores, no momento que em que estiver
	 * preenchendo os registros do SPED. E não depois ao final;
	 * 
	 */
	private Map<IndicadorDeOperacao, Set<RegistroAnalitico>> mapRegistroAnaliticoPorTipoOperacao;
	
	
	/**
	 * São as mesmas inforações do mapa (acima ^^), mapAnaliticoPorTipoOpereacao, porém esse é por Documento Fiscal
	 */
//	private Map<DocumentoFiscal, Set<RegistroAnalitico>> mapRegistroAnaliticoPorDocFisc;


	private Set<SpedFiscalRegE110> setSpedFiscRegE110ApuracaoPropria;
	
	private Set<SpedFiscalRegE310> setSpedFiscRegE310Difal;
	
	private LocalDate dataInicio;
	private LocalDate dataFim;

	/**
	 * Método Chamado dentro do REG C101Service.
	 * Usado para adicionar o DocumentoFiscal, com DIFA e/ou FCP
	 * @param docuFiscComDifal
	 */
	public void addDocFiscInterestadualComDifal(DocumentoFiscal docuFiscComDifal) {
		// @Todo
//		if (AQUI TENHO QUE FAZER UMA VALIDAÇÃO VERIFICANDO SE TEM DIFAL) {
			if (this.listDocFiscInterestadualComDifal == null) {
				listDocFiscInterestadualComDifal = new ArrayList<>();
			}
			this.listDocFiscInterestadualComDifal.add(docuFiscComDifal);
//		}
	}
	
	
	public List<DocumentoFiscal> getListDocFiscInterestadualComDifal() {
		if (this.listDocFiscInterestadualComDifal == null ) {
			this.listDocFiscInterestadualComDifal = new ArrayList<>();
			return this.listDocFiscInterestadualComDifal;
		}
		return this.listDocFiscInterestadualComDifal;
	}
	
	public void setListProdutoAlteradoSped(List<ProdutoAlteradoSped> listProdutoAlterado) { 
		if ((this.listProdutoAlteradoSped == null || this.listProdutoAlteradoSped.isEmpty()) && listProdutoAlterado == null) {
			this.listProdutoAlteradoSped = new ArrayList<>();
		} else {
			this.listProdutoAlteradoSped = listProdutoAlterado;
		}
	}
	

	public List<CodificacaoReg0450InfoComplementarFisco> getListCodInfoComplementarFisco() {
		if (this.listCodInfoComplementarFisco == null || this.listCodInfoComplementarFisco.isEmpty()) {
			this.listCodInfoComplementarFisco = new ArrayList<>();
			CodificacaoReg0450InfoComplementarFisco codInfoComplementarFisco = new CodificacaoReg0450InfoComplementarFisco();
			
			codInfoComplementarFisco.setId(1L);
			codInfoComplementarFisco.setCodInfo("01");
			codInfoComplementarFisco.setDescricao("INFORMACOES COMPLEMENTARES DE INTERESSE DO FISCO");
			listCodInfoComplementarFisco.add(codInfoComplementarFisco);
		}
		return listCodInfoComplementarFisco;
	}

	public void setListCodInfoComplementarFisco(List<CodificacaoReg0450InfoComplementarFisco> listCodInfoComplementarFisco) {
		this.listCodInfoComplementarFisco = listCodInfoComplementarFisco;
	}
	

	public Map<IndicadorDeOperacao, Set<RegistroAnalitico>> getMapRegistroAnaliticoPorTipoOperacao() {
		return mapRegistroAnaliticoPorTipoOperacao;
	}
	
	
	/**
	 * Será adicionado no a ObservacaoDeLancamentoFiscal (REG C195 ou REG D195) <\br>
	 * no  <b> this.setOservacoesLancamentoFiscal <\b>
	 * 
	 * @param <T>
	 * @param t
	 */
	public <T extends ObservacoesLancamentoFiscal> void addObservacaoLancamentoFiscal(List<T> t) {
		if (t != null) {
			this.setObservacoesLancamentoFiscal.addAll(t);
		}
	}
	
	
	/**
	 * O DocumentoFiscal, será adicionado no mapa de DocumentoFiscal, por situacão
	 * 
	 * @param docFisc
	 */
	public void addDocumentoFiscalPorSituacao(DocumentoFiscal docFisc) {
		if (this.mapDocumentoFiscalPorSituacao == null) {
			this.mapDocumentoFiscalPorSituacao = new HashMap<>();
		}
		SituacaoDoDocumento codSit = getCodSituacao(docFisc);
		
		if (this.mapDocumentoFiscalPorSituacao.containsKey(codSit)) {
			this.mapDocumentoFiscalPorSituacao.get(codSit).add(docFisc);
		} else {
			Set<DocumentoFiscal> setDocFisc = new HashSet<>();
			setDocFisc.add(docFisc);
			this.mapDocumentoFiscalPorSituacao.put(codSit, setDocFisc);
		}
	}
	
	/**
	 * Adiciona os Objetos que são do tipo -> {@linkplain RegistroAnalitico};
	 * No Map -> "setRegistroAnalitico", que será usado para escriturar o REGISTRO E110
	 * 
	 * @param <T>
	 * @param t
	 */
	public <T extends RegistroAnalitico> void addRegistroAnalitico(T t, DocumentoFiscal docFisc) {
		if (this.mapRegistroAnaliticoPorTipoOperacao == null) {
			this.mapRegistroAnaliticoPorTipoOperacao = new HashMap<>();
		}
		Set<RegistroAnalitico> setRegistroAnalitico = new HashSet<>();
		setRegistroAnalitico.add(t);
		addRegistroAnalitico(setRegistroAnalitico, docFisc);
	}
	
	/**
	 * Adiciona um Lista Objetos que são do tipo -> {@linkplain RegistroAnalitico};
	 * No Mapa ->  "Map<IndicadorDeOperacao, Set<RegistroAnalitico>>", que será usado para escriturar o REGISTRO E110
	 * 
	 * @param <T>
	 * @param t
	 */
	public <T extends RegistroAnalitico> void addRegistroAnalitico(List<T> t, DocumentoFiscal docFisc) {
		if (this.mapRegistroAnaliticoPorTipoOperacao == null) {
			this.mapRegistroAnaliticoPorTipoOperacao = new HashMap<>();
		}
		Set<RegistroAnalitico> setRegistroAnalitico = new HashSet<>();
		setRegistroAnalitico.addAll(t);
		addRegistroAnalitico(setRegistroAnalitico, docFisc);
	}
	
	/**
	 * 
	 * @param setRegistroAnalitico
	 * @param docFisc
	 */
	private void addRegistroAnalitico(Set<RegistroAnalitico> setRegistroAnalitico, DocumentoFiscal docFisc) {
		if (docFisc.getTipoOperacao().equals(ENTRADA)) {
			if (!this.mapRegistroAnaliticoPorTipoOperacao.containsKey(ENTRADA)) {
				this.mapRegistroAnaliticoPorTipoOperacao.put(ENTRADA, setRegistroAnalitico);
			} else {
				this.mapRegistroAnaliticoPorTipoOperacao.get(ENTRADA).addAll(setRegistroAnalitico);
			}
		} else {
			if (!this.mapRegistroAnaliticoPorTipoOperacao.containsKey(SAIDA)) {
				this.mapRegistroAnaliticoPorTipoOperacao.put(SAIDA, setRegistroAnalitico);
			} else {
				this.mapRegistroAnaliticoPorTipoOperacao.get(SAIDA).addAll(setRegistroAnalitico);
			}
		}
//		this.mapRegistroAnaliticoPorDocFisc.put(docFisc, setRegistroAnalitico);
	}
	
	/**
	 * Será montado um Mapa, com todos os DocumentosFiscais, que foram buscado no PERÍODO; <\br>
	 * 
	 * Key -> Id do DocumentoFiscal <\br>
	 * Value -> DocumentoFiscal		<\br>
	 * 
	 * OBS: Para preencher esse mapa, as Listas de DocumentosFiscais, já deveram estar preenchidas (pois não será feito uma nova busca para preencher elas);
	 * 
	 * @return
	 * 
	 */
	public  Map<Long, DocumentoFiscal> getMapDocumentoFiscalPorCodigo() {
		if(isMapNull(mapDocumentoFiscalPorCodigo)) {
			this.mapDocumentoFiscalPorCodigo = new HashMap<>();
		}

		if (mapDocumentoFiscalPorCodigo.isEmpty()) {
			if (collectionNotEmptyOrNull(this.listDocumentoFiscal)) {
				mapDocumentoFiscalPorCodigo = this.listDocumentoFiscal.stream().collect(toMap(DocumentoFiscal::getId, (DocumentoFiscal docFisc) -> docFisc ));
			}
			
			if (collectionNotEmptyOrNull(this.listDocumentoFiscalServico)) {
				mapDocumentoFiscalPorCodigo = this.listDocumentoFiscalServico.stream().collect(toMap(DocumentoFiscal::getId, (DocumentoFiscal docFisc) -> docFisc));
			}
			
			if (collectionNotEmptyOrNull(this.listSatsEmitidos)) {
				mapDocumentoFiscalPorCodigo = this.listDocumentoFiscalServico.stream().collect(toMap(DocumentoFiscal::getId, (DocumentoFiscal docFisc) -> docFisc));
			}
		}
		return mapDocumentoFiscalPorCodigo;
	}
	
	
	
	
	
	
//	public LocalDate getDataInicio() {
//		return dataInicio;
//	}
//	
//	public void setDataInicio(LocalDate dataInicio) {
//		this.dataInicio = dataInicio;
//	}
//
//	public LocalDate getDataFim() {
//		return dataFim;
//	}
//	
//	public void setDataFim(LocalDate dataFim) {
//		this.dataFim = dataFim;
//	}


}
