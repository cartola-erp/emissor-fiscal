package net.cartola.emissorfiscal.util;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.ENTRADA;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.SAIDA;
import static net.cartola.emissorfiscal.documento.TipoServico.NENHUM;
import static net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal.NFSE;
import static net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal._57;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.FinalidadeEmissao;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.documento.NFeStatus;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.inventario.InventarioItem;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.properties.SpedFiscalProperties;
import net.cartola.emissorfiscal.sped.fiscal.RegistroAnalitico;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.ProcessoEmissaoNfe;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;

/**
 * Classe Utilitária para ser usada no SPED ICMS IPI 
 * 
 * @author robson.costa
 *
 */
public final class SpedFiscalUtil {
	
	private static final Logger LOG = Logger.getLogger(SpedFiscalUtil.class.getName());

	

	private SpedFiscalUtil() {}
	
	
	/**
	 * Usado no REG C100;
	 * 
	 * Obtem o código do participante para o <b>destinatário</b> se for operacao de <b>Entrada</b>
	 * <b>Saída</b> codigo particapante do Emitente <br>
	 * 
	 * O mapLojaPorCnpj, tem que estar preenchido com todas as lojas
	 * 
	 * Caso o Modelo documento seja == 65 (NFC-e), retorna String <b>Vazia</b>
	 * @param docFisc
	 * @param mapLojaPorCnpj
	 * @return
	 */
	public static String getCodPart(DocumentoFiscal docFisc, Map<String, Loja> mapLojaPorCnpj) {
		if (docFisc.getModelo() != ModeloDocumentoFiscal._65) {
			boolean isEmitenteEqualsLoja = docFisc.getEmitente().getCnpj().equals(docFisc.getLoja().getCnpj());
//			if (docFisc.getTipoOperacao() == IndicadorDeOperacao.ENTRADA) {
			if (docFisc.getTipoOperacao() == IndicadorDeOperacao.ENTRADA && !isEmitenteEqualsLoja) {
				return getCodPart(docFisc.getEmitente(), mapLojaPorCnpj);
			}
			return getCodPart(docFisc.getDestinatario(), mapLojaPorCnpj);
		}
		return "";
	}

	
	/**
	 * Se a pessoa for uma loja, será devolvido o COD participante com base na tabela de LOJA <br>
	 * 
	 * O mapLojaPorCnpj, tem que estar preenchido com todas as lojas
	 * 
	 * @param mapLojaPorCnpj
	 * @param pessoa
	 * @return
	 */
	public static String getCodPart(Pessoa pessoa, Map<String, Loja> mapLojaPorCnpj) {
		if (mapLojaPorCnpj != null && mapLojaPorCnpj.containsKey(pessoa.getCnpj())) {
			Loja loja = mapLojaPorCnpj.get(pessoa.getCnpj());
			return getCodPart(loja);
		}
		return getCodPart(pessoa);
	}


	/**
	 * Obtem o <b> "Código do Participante" <b>
	 * Usos nos REGISTROS: 0150 e C100;
	 * 
	 * @param pessoa
	 * @return Código do participante para a pessoa (emitente ou destinatario) 
	 */
	private static String getCodPart(Pessoa pessoa) {
		String codPart = pessoa.getCodigoErp() + "0"+ pessoa.getLojaErp();  
		return getCodPart(codPart);
	}
	
	/**
	 * Obtem o <b> "Código do Participante"<b>, para a Loja/Filial <br>
	 * 
	 * @param loja
	 * @return
	 */
	private static String getCodPart(Loja loja) {
		String codPart = loja.getCodigoLoja() + "0" + "1";
		return getCodPart(codPart);
	}
	
	/**
	 * 
	 * @param codPart
	 * @return Código do participante formatado
	 */
	private static String getCodPart(String codPart) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(8);
		nf.setMaximumFractionDigits(8);

		String codParFormatado = StringUtil.somenteNumeros(nf.format(Integer.valueOf(codPart)));
		return codParFormatado;
	}

	
	/**
	 * 
	 * @param docFisc
	 * @return
	 */
	public static SituacaoDoDocumento getCodSituacao(DocumentoFiscal docFisc) {
		// TODO Colocar regra para quando for um DocumentoFiscal "COMPLEMENTAR"
		NFeStatus nfeStatus = docFisc.getStatus();
			if(docFisc.getFinalidadeEmissao().equals(FinalidadeEmissao.COMPLEMENTAR)) {
				return SituacaoDoDocumento.DOCUMENTO_FISCAL_COMPLEMENTAR;
			}
			
			if (ModeloDocumentoFiscal._55.equals(docFisc.getModelo()) && isNfeAvulsa(docFisc) ) {
				return SituacaoDoDocumento.DOCUMENTO_FISCAL_EMITIDO_COM_BASE_EM_REGIME_ESPECIAL_OU_NORMA_ESPECÍFICA;
			}
			if (nfeStatus.getCodigo().length() == 2) {
				int nfeStatusCodigo = Integer.parseInt(nfeStatus.getCodigo());
				SituacaoDoDocumento sitDoc = SituacaoDoDocumento.values()[nfeStatusCodigo];
				return sitDoc;
			}
		return null;
	}
	
	/**
	 * Será verificado se a NFE, é uma "NFA-e" (Nota Fiscal Avulsa eletronica) <br>
	 * NFA, são notas emitidas por PJ que não tem obrigatoriedade de emitir NFe, porém quando precisam emitem pela site da SEFAZ !!!
	 * @param docFisc
	 * @return
	 */
	private static boolean isNfeAvulsa(DocumentoFiscal docFisc) {
		List<String> tagProcEmit = XmlUtil.getTagConteudo(docFisc.getXml(), "procEmi", false);
		if(!tagProcEmit.isEmpty()) {
			String procEmit = tagProcEmit.get(0);

			return procEmit.equals(ProcessoEmissaoNfe.NFE_AVULSA_PELO_FISCO.getCodigo()) 
					|| procEmit.equals(ProcessoEmissaoNfe.NFE_AVULSA_PELO_CONTRIBUINTE_COM_CERTIFICADO_PELO_SITE_DO_FISCO.getCodigo());
		}
		return false;
	}


	/**
	 * Usado no REG C100;
	 * 
	 * Irá retornar se a emissão do DocumentoFiscal é:
	 * 	- Emissão Própria		| (Entradas e Saídas)
	 * 	- Emissão de Terceiros  | (Somente Entradas)
	 * 
	 * @param docFisc
	 * @param lojaSped
	 * @return
	 */
	public static IndicadorDoEmitente getIndicadorEmitente(DocumentoFiscal docFisc, Loja lojaSped) {
//		LOG.log(Level.INFO, "Obtendo o indicador do emitente para o DocumentoFiscal {0} " ,docFisc);

		Pessoa emitente = docFisc.getEmitente();
		if (emitente.getCnpj().equals(lojaSped.getCnpj())) {
			return IndicadorDoEmitente.EMISSAO_PROPRIA;
		}
		return IndicadorDoEmitente.TERCEIROS;
	}

	public static String getCodItem(DocumentoFiscalItem item) {
		return getCodItem(item.getCodigoX().toString(), item.getCodigoSequencia());
	}
	
	public static String getCodItem(InventarioItem item) {
		return getCodItem(item.getCodigoX().toString(), item.getCodigoSequencia());
	}
	
	private static String getCodItem(String codigoX, String codigoSequencia) {
		return codigoX + codigoSequencia;
	}


	public static String getCstIcmsComOrigem(ProdutoOrigem origem, int cstIcms) {
		if (cstIcms == 0) {
			return Integer.toString(origem.ordinal()) + "00";
		}
		return Integer.toString(origem.ordinal()) + Integer.toString(cstIcms);
	}
	
	
	/**
	 * Irá devolver somente a CST do icms, referente a "tabela B" (ou seja, sem a origem do produto concatenada)
	 * @param cstIcms
	 * @return
	 */
	public static String getCstIcmsSemOrigem(String cstIcms) {
		if (cstIcms != null && cstIcms.length() == 3) {
			return cstIcms.substring(1);
		}
		return cstIcms;
	}
	
	/**
	 * 
	 * @param icmsCst
	 * @return true -> Se icmsCst for igual a => 30, 40 ou 41 
	 * 
	 */
	public static boolean isIcmsCstIsentaOuNaoTributada(String icmsCst) {
		Predicate<String> predicateIsIcmsCstIsentaOuNt = (p) -> p.equals("30") || p.equals("40") || p.equals("41");
		return predicateIsIcmsCstIsentaOuNt.test(icmsCst);
	}
	
	
	/**
	 * Irá comparar o cnpj do emitente e destinatario
	 * @param docFisc
	 * @return
	 */
	public static boolean isEmitenteEqualsDestinatario(DocumentoFiscal docFisc) {
		return docFisc.getEmitente().getCnpj().equals(docFisc.getDestinatario().getCnpj());
	}
	
	
	/**
	 * Irá devolver se é para informar o desconto (conforme a operação do DocumentoFiscal) <\br>
	 * E o Valor da propriedade presente em {@linkplain} {@link SpedFiscalProperties} ;
	 * 
	 * @param spedFiscPropertie
	 * @param tipoOperacao
	 * @return
	 */
	public static boolean isInformaDesconto(IndicadorDeOperacao tipoOperacao, SpedFiscalProperties spedFiscPropertie) {
		return (tipoOperacao.equals(ENTRADA) && spedFiscPropertie.isInformarDescontoEntrada()) || 
				(tipoOperacao.equals(SAIDA) && spedFiscPropertie.isInformarDescontoSaida());
	}
	
	
	public static boolean isEntradaEmitidaPelaLoja(DocumentoFiscal docFisc, Loja lojaSped) {
		return getIndicadorEmitente(docFisc, lojaSped).equals(IndicadorDoEmitente.EMISSAO_PROPRIA) && docFisc.getTipoOperacao().equals(ENTRADA);
	}
	
	/**
	 * Irá verificar se o DocumentoFiscal, é ou não, uma entrada de consumo
	 * 
	 * @param docFisc
	 * @return
	 */
	public static boolean isEntradaConsumoOuAtivo(DocumentoFiscal docFisc) {
		final Set<Long> codOperacaoConsumo = new HashSet<>();
	    codOperacaoConsumo.addAll(Arrays.asList(18l, 34l, 35l, 65l, 70l, 78l));
	    
	    Operacao oper = docFisc.getOperacao();
	    Long operId = oper != null ? oper.getId() : 0l;
//	    return codOperacaoConsumo.contains(operId) && docFisc.getTipoOperacao().equals(IndicadorDeOperacao.ENTRADA);
	    return codOperacaoConsumo.contains(operId);
	}
	
	/**
	 * Irá retornar um Predicate para validar se a operação de entrada é escriturada; <br>
	 * PS: No caso somente as entrada emitidas por nóis, (que nos arquivos de exemplo do SPED) não estão escrituradas <br>
	 * 
	 * <b> EX.: <b> 73 - Compra de sucata de não contribuinte ... <br>
	 * 
	 * PS: As operações de saídas nenhuma é escriturada
	 * 
	 * @param lojaAtualSped
	 * @return
	 */
	public static Predicate<DocumentoFiscal> criaPredicateIsOperacaoDoDocumentoEscriturada(Loja lojaAtualSped) {
		 Predicate<DocumentoFiscal> isOperacaoDoDocumentoEscriturada = docFiscal -> ENTRADA.equals(docFiscal.getTipoOperacao()) 
				&& !docFiscal.getEmitente().getCnpj().equals(lojaAtualSped.getCnpj())
				&& !_57.equals(docFiscal.getModelo())
				&& !NFSE.equals(docFiscal.getModelo())
				&& NENHUM.equals(docFiscal.getTipoServico());
		return isOperacaoDoDocumentoEscriturada;
	}
	
	/**
	 * Irá retornar o mês de referência no formato MM/YYYY
	 * @param dataInicio
	 * @return
	 */
	public static String getMesReferencia(LocalDate dataInicio) {
		 if (dataInicio.getMonthValue() <= 9) {
			 return "0"+ Integer.toString(dataInicio.getMonthValue()) + (Integer.toString(dataInicio.getYear()));
		 }
		return Integer.toString(dataInicio.getMonthValue()) + (Integer.toString(dataInicio.getYear()));
	}
	
	/**
	 * Se a CFOP de algum DocumentoFiscalItem, for igual a 5929 ou 6929, então o DocumentoFiscal(NFE) "recebido" é referente, a 
	 * um SAT emitido anteriormente
	 *  
	 * @param docFisc
	 * @return 
	 */
	public static boolean isNfeReferenteASat(DocumentoFiscal docFisc) {
		Set<Integer> setCfops = docFisc.getItens().stream().map(DocumentoFiscalItem::getCfop).collect(toSet());
//		boolean isNfeReferenteASat = (setCfops.contains(5929) || setCfops.contains(6929));
		return (setCfops.contains(5929) || setCfops.contains(6929));
	}
	
	public static boolean isDocumentoFiscalEmDigitacao(DocumentoFiscal documentoFiscal) {
		Predicate<DocumentoFiscal> isDigitacao = p -> p.getStatus().equals(NFeStatus.DIGITACAO);
		return isDigitacao.test(documentoFiscal);
	}
		
	/**
	 * TODOs os SATS AUTORIZADOS, tem que ter um XML, se não tiver é porque não foi autorizado <\br> 
	 * Ou ao menos, não atualizou o status aqui no emissor-fiscal
	 * 
	 * @param satEmititdo
	 * @return
	 */
	public static String getNumeroSerieSat(DocumentoFiscal satEmititdo) {
		List<String> listNserieSAT = XmlUtil.getTagConteudo(satEmititdo.getXml(), "nserieSAT", false);
		return listNserieSAT == null ? "" : listNserieSAT.get(0);
//		return null;
	}

	/**
	 * Retorna <b>true<b>, se o Status da NFE, for: CANCELADA, DENEGADA ou INUTILIZADA
	 * 
	 * @param docFisc
	 * @return
	 */
	public static boolean isNfeNaoAutorizada(DocumentoFiscal docFisc) {
		List<NFeStatus> nfesNaoAutorizadas = Arrays.asList(NFeStatus.CANCELADA, NFeStatus.DENEGADA, NFeStatus.INUTILIZADA);
		return nfesNaoAutorizadas.contains(docFisc.getStatus());
	}
	/**
	 * Irá obter, o Número do Cupom Fiscal Elerônico <\br>, no XML do SAT
	 * tag <nCFe>
	 * @param satEmititdo
	 * @return
	 */
	public static String getNumeroCfe(DocumentoFiscal satEmititdo) {
		List<String> nCFe = XmlUtil.getTagConteudo(satEmititdo.getXml(), "nCFe", false);
		return nCFe == null ? "" : nCFe.get(0);
	}

	
	/**
	 * Irá retornar um mapa para o Registro analítico dos ITENS, do DocumentoFiscal.
	 * 
	 * Usado nos REGISTROS: C190 e C850
	 * @param docFisc
	 * @return mapPorOrigemCstCfopAliqIcms
	 */
	public static Map<ProdutoOrigem, Map<Integer, Map<Integer, Map<BigDecimal, List<DocumentoFiscalItem>>>>> getMapaItensParaRegistroAnalitico(DocumentoFiscal docFisc) {
		Map<ProdutoOrigem, Map<Integer, Map<Integer, Map<BigDecimal, List<DocumentoFiscalItem>>>>> mapPorOrigemCstCfopAliqIcms = 
				docFisc.getItens().stream().collect(groupingBy(DocumentoFiscalItem::getOrigem, 
						groupingBy(DocumentoFiscalItem::getIcmsCst, 
								groupingBy(DocumentoFiscalItem::getCfop, 
										groupingBy(DocumentoFiscalItem::getIcmsAliquota)))));

		return mapPorOrigemCstCfopAliqIcms;
	}
	
	
	/**
	 * Será retornado uma map de registro analiticio, por CFOP e CST
	 * 
	 * @param <T>
	 * @param listRegistroAnalitico
	 * @return 
	 */
	public static <T extends RegistroAnalitico> Map<Integer, Map<String, List<T>>> getMapaRegistroAnaliticoPorCfopECst(List<T> listRegistroAnalitico) {
		Map<Integer, Map<String, List<T>>> mapRegistroAnaliticoPorCfopECst = listRegistroAnalitico.stream().
					collect(groupingBy(RegistroAnalitico::getCfop, 
								groupingBy(regAnalitico -> getCstIcmsSemOrigem(regAnalitico.getCstIcms()))));
		return mapRegistroAnaliticoPorCfopECst;
	}


}
