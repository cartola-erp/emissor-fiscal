package net.cartola.emissorfiscal.util;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.ENTRADA;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
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
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.properties.SpedFiscalProperties;
import net.cartola.emissorfiscal.sped.fiscal.RegistroAnalitico;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
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
	 * Obtem o <b> "Código do Participante" </b>
	 * Usos nos REGISTROS: 0150 e C100;
	 * 
	 * @param pessoa
	 * @return Código do participante para a pessoa (emitente ou destinatario) 
	 */
	public static String getCodPart(Pessoa pessoa) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(8);
		nf.setMaximumFractionDigits(8);
//		StringBuilder codPart = new StringBuilder(pessoa.getCodigoErp()).append(0).append(pessoa.getLojaErp());  
		String codPart = pessoa.getCodigoErp() + "0"+ pessoa.getLojaErp();  
		String codParFormatado = StringUtil.somenteNumeros(nf.format(Integer.valueOf(codPart)));
		return codParFormatado;
//		return nf.format(codPart);
	}
	
	/**
	 * Usado no REG C100;
	 * 
	 * Obtem o código do participante para o <b>destinatário</b> se for operacao de <b>Entrada</b>
	 * <b>Saída</b> codigo particapante do Emitente
	 * 
	 * Caso o Modelo documento seja == 65 (NFC-e), retorna String <b>Vazia</b>
	 * @param docFisc
	 * @return
	 */
	public static String getCodPart(DocumentoFiscal docFisc) {
//		LOG.log(Level.INFO, "Obtendo o CODIGO DO PARTICIPANTE para o DocumentoFiscal {0} " ,docFisc);
		if (docFisc.getModelo() != ModeloDocumentoFiscal._65) {
			if (docFisc.getTipoOperacao() == IndicadorDeOperacao.ENTRADA) {
				return getCodPart(docFisc.getEmitente());
			}
			return getCodPart(docFisc.getDestinatario());
		}
		return "";
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
			if (nfeStatus.getCodigo().length() == 2) {
				int nfeStatusCodigo = Integer.parseInt(nfeStatus.getCodigo());
				SituacaoDoDocumento sitDoc = SituacaoDoDocumento.values()[nfeStatusCodigo];
				return sitDoc;
			}
		return null;
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
		String codItem = item.getCodigoX().toString() + item.getCodigoSequencia();
		return codItem;
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
		return (tipoOperacao.equals(ENTRADA) && spedFiscPropertie.isInformarDescontoEntrada()) || spedFiscPropertie.isInformarDescontoSaida();
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
