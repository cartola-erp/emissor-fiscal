package net.cartola.emissorfiscal.documento;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import net.cartola.emissorfiscal.devolucao.Devolucao;
import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.devolucao.DevolucaoItemService;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.loja.LojaService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaService;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;
import net.cartola.emissorfiscal.produto.ProdutoUnidadeService;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDePagamento;

/**
 * @date 22 de set. de 2021
 * @author robson.costa
 */
public abstract class DocumentoService {

	private static final Logger LOG = Logger.getLogger(DocumentoService.class.getName());
	
	@Autowired 
	protected NcmService ncmService;
	
	@Autowired
	protected ProdutoUnidadeService prodUnidService;
	
	@Autowired
	protected LojaService lojaService;
	
	@Autowired
	protected OperacaoService operacaoService;
	
	@Autowired
	protected PessoaService pessoaService;
	
	@Autowired
	protected EstadoService estadoService;
	
//	@Autowired
//	protected DocumentoFiscalItemService itemService;
//	private ItemService itemService;
	
	@Autowired
	private DevolucaoItemService devolucaoItemService;
	
	/**
	 * Irá buscar e setar os objetos necessários para os ITENS (Ncm, ProdutoUnidade e o Documento(DocumentoFiscal ou Devolucao) ) <\br>
	 * E adiciona Msg de erro no Map, caso não exista Ncm ou  o ProdutoUnidade 
	 * 
	 * @param documento
	 * @param mapErros
	 * @return Set<Ncms>
	 */
	protected <T extends Documento<Item>> void setValoresNecessariosParaOsItens(Documento<? extends Item> documento, Map<String, Boolean> mapErros) {
		Set<Integer> setNumeroNcms = documento.getItens().stream().map(Item::getClasseFiscal)
				.map(ncmNumero -> Integer.valueOf(ncmNumero)).collect(toSet());
		Map<Integer, Map<Integer, Ncm>> mapNcmPorNumeroEExcecao = ncmService.findNcmByNumeroIn(setNumeroNcms)
				.stream().collect(groupingBy(Ncm::getNumero,
											toMap(Ncm::getExcecao, (Ncm ncm) -> ncm)));
		
		Set<String> listSigla = documento.getItens().stream().map(Item::getUnidade).map(ProdutoUnidade::getSigla).collect(toSet());
		Map<String, ProdutoUnidade> mapProdutoUnidadePorSigla = prodUnidService.findByListSiglas(listSigla)
				.stream().collect(toMap(ProdutoUnidade::getSigla, (ProdutoUnidade prodUnid) -> prodUnid));
//		
		// ========================== Setando e Validando os valores, para os itens do documento fiscal ===========================================
		if (documento instanceof DocumentoFiscal) {
			DocumentoFiscal documentoFiscal = DocumentoFiscal.class.cast(documento);
			for(DocumentoFiscalItem docItem : documentoFiscal.getItens()) {
				docItem.setDocumentoFiscal(documentoFiscal);
				setNcm(docItem, mapNcmPorNumeroEExcecao, mapErros);
				setProdutoUnidade(docItem, mapProdutoUnidadePorSigla, mapErros);
			}
		} else if (documento instanceof Devolucao ) {
			Devolucao devolucao = Devolucao.class.cast(documento);
			Map<String, Loja> mapLojaPorCnpj = lojaService.findAll().stream().collect(Collectors.toMap(Loja::getCnpj, (Loja loja) -> loja));
			for(DevolucaoItem devolucaoItem :  devolucao.getItens()) {
				devolucaoItem.setDevolucao(devolucao);
				devolucaoItemService.setLojaOrigem(devolucaoItem, mapLojaPorCnpj, mapErros);
				setNcm(devolucaoItem, mapNcmPorNumeroEExcecao, mapErros);
				setProdutoUnidade(devolucaoItem, mapProdutoUnidadePorSigla, mapErros);
			}
		}
		// ========================================================================================================================================
	}
	
	
	/**
	 * Irá setar o ncm para o item do documento fiscal, SE o ncm existir
	 * 
	 * @param mapErros
	 * @param mapNcmPorNumeroEExcecao
	 * @param item
	 */
	private void setNcm(Item item, Map<Integer, Map<Integer, Ncm>> mapNcmPorNumeroEExcecao, Map<String, Boolean> mapErros) {
		Integer numeroNcm = Integer.parseInt(item.getClasseFiscal());
		int exTipi = (item.getExcecao() > 5) ? 0 : item.getExcecao();
		boolean isNcmPresent = false;

		Ncm ncm = null;
		if (mapNcmPorNumeroEExcecao.containsKey(numeroNcm)) {
			ncm = mapNcmPorNumeroEExcecao.get(numeroNcm).get(exTipi);
			isNcmPresent = ncm != null;
		}
		 
		if(isNcmPresent) {
			item.setClasseFiscal(ncm);
		}
		mapErros.put("O NCM: " +item.getClasseFiscal()+ " | EX: " +item.getExcecao()+ " NÃO existe", !isNcmPresent);
	}
	
	
	/**
	 * @param mapErros
	 * @param mapProdutoUnidadePorSigla
	 * @param item
	 */
	private void setProdutoUnidade(Item item, Map<String, ProdutoUnidade> mapProdutoUnidadePorSigla, Map<String, Boolean> mapErros) {
		ProdutoUnidade unidadeItem = item.getUnidade();
		if (unidadeItem != null &&  mapProdutoUnidadePorSigla.containsKey(unidadeItem.getSigla()) ) {
			ProdutoUnidade produtoUnidade = mapProdutoUnidadePorSigla.get(unidadeItem.getSigla());
			item.setUnidade(produtoUnidade);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("Unidade não encontrada para o item: ").append(item.getItem()).append(" X/s: " ).append(item.getCodigoX()).append(" ").append(item.getCodigoSequencia());
			mapErros.put(sb.toString() , true);
		}
	}

	/**
	 * Irá validar se pode buscar as tributacões (PIS/COFINS e ICMS)
	 * 
	 * @param opOperacao		- Operação do DocumentoFiscal (Ex.: Venda, Transferência, Venda InterEstadual etc...)
	 * @param ncmsDocFiscal		- Todos os Ncms válidos, encontrados nos itens do DocumentoFiscal
	 * @param emitente			- Loja que emitiu o DocumentoFiscal
	 * @param opUfOrigem 		- Estado de Origem
	 * @param opUfDestino 		- Estado de Destino
	 * @param mapErros			- Mapa com todas as mensagens de erros/validações. (Será adicionadas novas nesse method, caso tenha)
	 * @return <b>true</b> 		-> Irá buscar as tributacoes | <b> false </b> -> Se alguma informação que precisa não estiver presente, Não irá buscar a tributacao
	 */
	protected void setValoresNecessariosParaODocumento(Documento<? extends Item> docuFisc, Map<String, Boolean> mapErros) {
		Optional<Operacao> opOperacao = operacaoService.findOperacaoByDescricao(docuFisc.getOperacao().getDescricao());
		Optional<Pessoa> opEmitente = pessoaService.verificaSePessoaExiste(docuFisc.getEmitente());
		Optional<Pessoa> opDestinatario = pessoaService.verificaSePessoaExiste(docuFisc.getDestinatario());
		
		Optional<Estado> opUfOrigem = estadoService.findBySigla(opEmitente.get().getEndereco().getUf());
		Optional<Estado> opUfDestino = estadoService.findBySigla(opDestinatario.get().getEndereco().getUf());
		
		isPermitidoBuscarTributacoes(opOperacao, docuFisc.getNcms(), opEmitente.get(), opUfOrigem, opUfDestino, mapErros);
		
		if (!mapErros.containsValue(true)) {
			setValoresNecessariosParaODocumentoFiscalEntrada(docuFisc, opOperacao, opEmitente, opDestinatario, mapErros );
		}
		
		mapErros.put("O CNPJ: " +docuFisc.getEmitente().getCnpj()+ " do emitente NÃO existe" , !opEmitente.isPresent());
		mapErros.put("O CNPJ: " +docuFisc.getDestinatario().getCnpj()+ " do destinatário NÃO existe", !opDestinatario.isPresent());
		
	}

	protected void setValoresNecessariosParaODocumentoFiscalEntrada(Documento<? extends Item> documento, Optional<Operacao> opOperacao, Optional<Pessoa> opEmitente, Optional<Pessoa> opDestinatario, Map<String, Boolean> mapErros) {
		Optional<Loja> opLoja = lojaService.findByCnpj(documento.getLoja().getCnpj());
		
		if (opOperacao.isPresent() && opEmitente.isPresent() && opDestinatario.isPresent()) {
			documento.setOperacao(opOperacao.get());
			documento.setEmitente(opEmitente.get());
			documento.setDestinatario(opDestinatario.get());
		}
		
//		mapErros.put("O CNPJ: " +documento.getEmitente().getCnpj()+ " do emitente NÃO existe" , !opEmitente.isPresent());
//		mapErros.put("O CNPJ: " +documento.getDestinatario().getCnpj()+ " do destinatário NÃO existe", !opDestinatario.isPresent());
		
		if (opLoja.isPresent()) {
			documento.setLoja(opLoja.get());
		} else {
			Loja loja = documento.getLoja();
			StringBuilder erro = new StringBuilder();
			erro.append("Loja CNPJ: ").append(loja.getCnpj()).append(" | não cadastrado no EMISSOR-FISCAL! ").append(" | loja: ").append(loja.getCodigoLoja());
			mapErros.put(erro.toString(), true);
		}
		
		if (documento instanceof DocumentoFiscal) {
			DocumentoFiscal docuFisc = DocumentoFiscal.class.cast(documento);
			if (docuFisc.getIndicadorPagamento() == null) {
				docuFisc.setIndicadorPagamento(IndicadorDePagamento.OUTROS);
			}
			
			docuFisc.getReferencias().forEach(referencia -> {
				referencia.setDocumentoFiscal(docuFisc);
			});
		}
		
	}
	
	
	/**
	 * Irá validar se pode buscar as tributacões (PIS/COFINS e ICMS)
	 * 
	 * @param opOperacao		- Operação do DocumentoFiscal (Ex.: Venda, Transferência, Venda InterEstadual etc...)
	 * @param ncmsDocFiscal		- Todos os Ncms válidos, encontrados nos itens do DocumentoFiscal
	 * @param emitente			- Loja que emitiu o DocumentoFiscal
	 * @param opUfOrigem 		- Estado de Origem
	 * @param opUfDestino 		- Estado de Destino
	 * @param mapErros			- Mapa com todas as mensagens de erros/validações. (Será adicionadas novas nesse method, caso tenha)
	 * @return <b>true</b> -> Irá buscar as tributacoes | <b> false </b> -> Se alguma informação que precisa não estiver presente, Não irá buscar a tributacao
	 */
	private void isPermitidoBuscarTributacoes(Optional<Operacao> opOperacao, Set<Ncm> ncmsDocFiscal, Pessoa emitente, Optional<Estado> opUfOrigem, Optional<Estado> opUfDestino, Map<String, Boolean> mapErros) {
		LOG.log(Level.INFO, "Validando se pode buscar as tributacoes (PIS/COFINS e ICMS), para o DocumentoFiscal ");

		if (!opOperacao.isPresent()) {
			mapErros.put("A operação da NFE (DocumentoFiscal), NÃO está cadastrada no emissor-fiscal " ,true);
		}
		if (ncmsDocFiscal.isEmpty()) {
			mapErros.put("Nenhum NCM válido encontrado no DocumentoFiscal", true);
		}
		if (!opUfOrigem.isPresent()) {
			mapErros.put("Estado de ORIGEM do EMITENTE não encontrado" ,true);

		}
		if (!opUfDestino.isPresent()) {
			mapErros.put("Estado de DESTINO do destinatário não encontrado" ,true);
		}
		if (emitente.getRegimeTributario() == null) {
			mapErros.put("O Regime Tributário, do EMITENTE está NULO" ,true);
		}
	}
	
}
