package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.CompraDto;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110Service;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE111Service;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE111;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoGuiaEstadualService;

/**
 * @date 6 de jul. de 2021
 * @author robson.costa
 */
@Service
class RegE111Service {

	@Autowired // usarei para buscar se tem registros gerardos/preenchidos pelo usuarioo no periodo
	private SpedFiscalRegE110Service spedFiscRegE110Service;
	
	@Autowired // (salvos os registros gerados usando a service abaixo)
	private SpedFiscalRegE111Service spedFiscRegE111Service;
	
	@Autowired
	private DocumentoFiscalItemService docFiscItemService;
	
	@Autowired
	private CalculoGuiaEstadualService calcGuiaEstaService;
	
	// Aliquotas usadas para calcular o Crédito/Debitos do DIFAL das compras interestaduais de CONSUMO/ATIVO
	private static final BigDecimal ICMS_ALIQ_IMPORTADO = new BigDecimal(0.04D);
	private static final BigDecimal ICMS_ALIQ_NACIONAL = new BigDecimal(0.12D);		// OBS: de Qualquer UF -> SP, é 12% a aliquota
	private static final BigDecimal ICMS_ALIQ_INTERNA_SP = new BigDecimal(0.18D);
	

	
	/**
	 * Aqui é onde, de fato que eu tenho que tentar "prever" as situações que acontece/aconteceu no mês:
	 * 	
	 * - Ao menos as mais comuns acredito que eu consigo montar a lógica Ex.::
	 * 
	 * 	- Compra de consumo interestadual (SP000207);
	 * 	- Recebimento de saldo devedor (SP000219)
	 *  - 
	 *  
	 * @param movimentosIcmsIpi
	 * @return
	 */
	
	public List<RegE111> montarGrupoRegE111(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		List<RegE111> listRegE111 = new ArrayList<>();
		
		Set<SpedFiscalRegE110> setSpedFiscRegE110ApuracaoPropria = movimentosIcmsIpi.getSetSpedFiscRegE110ApuracaoPropria();
		
		Set<SpedFiscalRegE110> opRegE110preenchidoPeloUser = spedFiscRegE110Service.findRegE110ByPeriodoAndLoja(movimentosIcmsIpi.getDataInicio(), movimentosIcmsIpi.getDataFim(), movimentosIcmsIpi.getLoja());
		
		// DIFAL, de uso e consumo/ativo (interestadual) 
		List<RegE111> listRegE111DebitoECreditoDifalConsumoAtivo = gerarDebitoECreditoUsoConsumoOuAtivoInterestadual(movimentosIcmsIpi);
		
		
		// Estorno de Debito de devolucao p/ fornecedor 
		RegE111 regE111EstornoDebitoDevolucaoParaFornecedor = gerarEstornoDebitoDevolucaoParaFornecedor(movimentosIcmsIpi);
		
		// Referentes as notas de SC, ES e MG
		BigDecimal totalIcmsNotasDeSc = totalizarIcmsNotasDeSc(movimentosIcmsIpi);
		RegE111 regE111CreditosIcmsNotasDeSc = gerarCreditosNotasDeSantaCatarina(totalIcmsNotasDeSc);
		RegE111 regE111DebitosIcmsNotasDeSc = gerarDebitosNotasDeSantaCatarina(totalIcmsNotasDeSc);
		
		
		listRegE111.addAll(listRegE111DebitoECreditoDifalConsumoAtivo);
		listRegE111.add(regE111EstornoDebitoDevolucaoParaFornecedor);
		
		listRegE111.add(regE111CreditosIcmsNotasDeSc);
		listRegE111.add(regE111DebitosIcmsNotasDeSc);
		
		/**
		 * Acredito que isso abaixo conseguirei, descobrir depois que terminar de montar o REG E110 (então terei que salvar tais informações no DB);
		 */
		// Se Loja == 1 (totalizar os codigos
			// --> SP000219|Recebimento de saldo devedor - estabelecimento centralizador.|01012015|)
			// --> SP020730|Recebimento de saldo credor – estabelecimento centralizador.|01012015|
		// SE loja != 1 
			// --> SP000218|Transferência de saldo credor  para estabelecimento centralizador.|01012015|
			// --> SP020729|Transferência de saldo devedor para estabelecimento centralizador.|01012015|
		
		return listRegE111;
	}


	

	/**
	 * Será Calculado o Total de ICMS, das notas de SC, MG e ES, que foram pagas no período;
	 * 
	 * @param movimentosIcmsIpi
	 * @return totalcms, pagos das entradas de para comercialização, de itens das UF, SC, MG e/ou ES;
	 * 
	 */
	private BigDecimal totalizarIcmsNotasDeSc(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		Set<DocumentoFiscal> listDocFiscSantaCatarina = movimentosIcmsIpi.getListDocFiscSantaCatarina();
//		Set<CompraDto> setCompraDtoNotasSc = new HashSet<>();
		Set<BigDecimal> totalGareCompra = new HashSet<>();
		for (DocumentoFiscal docFiscDeSc : listDocFiscSantaCatarina) {
			CompraDto compraDto = calcGuiaEstaService.calculaGuiaGareIcmsStEntrada(docFiscDeSc);
			if (compraDto.isFoiCalculadoIcmsSt()) {
				BigDecimal total = compraDto.getTotalCalcGareCompra().getTotal() != null ? compraDto.getTotalCalcGareCompra().getTotal() : BigDecimal.ZERO;
				totalGareCompra.add(total);
			}
		}
		BigDecimal totalIcmsDasNotasDeSC = totalGareCompra.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalIcmsDasNotasDeSC;
	}

	/**
	 * - Será gerado um REG  E111, com o valor que pagamos de ICMS das notas de SC, ES e MG (vide a tabela trib_esta_guia) <\br>
	 * - COD AJUSSTE -> SP020799|OUTRAS HIPÓTESES - PREENCHIDA PELO CONTRIBUINTE.|01012015|	<\br>
	 * 
	 * - PS: <b> Tem que ser gerado um REGISTRO E111, com o valor que pagamos de ICMS, com um COD de CRÉDITO e OUTRO com o COD de DÉBITO, para "abater", os valores<\b> <\br>
	 * 		 <b> Conforme o que foi passado pela contabilidade/fiscal </b>
	 * 
	 * @param totalIcmsNotasDeSc
	 * @return
	 */
	private RegE111 gerarCreditosNotasDeSantaCatarina(BigDecimal totalIcmsNotasDeSc) {
		RegE111 regE111 = new RegE111();
		regE111.setCodAjApur("SP020799");
		regE111.setDescrComplAj("");
		regE111.setVlAjApur(totalIcmsNotasDeSc);
		return regE111;
	}

	/**
	 * - Será gerado um REG  E111, com o valor que pagamos de ICMS das notas de SC, ES e MG (vide a tabela trib_esta_guia) <\br>
	 * - SP000299|OUTRAS HIPÓTESES - PREENCHIDA PELO CONTRIBUINTE.|01012015| <\br>
	 * - 
	 * - PS: <b> Tem que ser gerado um REGISTRO E111, com o valor que pagamos de ICMS, com um COD de CRÉDITO e OUTRO com o COD de DÉBITO, para "abater", os valores<\b> <\br>
	 * 		 <b> Conforme o que foi passado pela contabilidade/fiscal </b>
	 * 
	 * @param totalIcmsNotasDeSc
	 * @return
	 */
	private RegE111 gerarDebitosNotasDeSantaCatarina(BigDecimal totalIcmsNotasDeSc) {
		RegE111 regE111 = new RegE111();
		regE111.setCodAjApur("SP000299");
		regE111.setDescrComplAj("");
		regE111.setVlAjApur(totalIcmsNotasDeSc);
		return regE111;
	}

	

	/**
	 * - Algumas entradas nossa de compra para comercialização NÃO TEMOS direito a crédito de ICMS mesmo o item vindo com icms; </br>
	 * - Se tivermos que fazer alguma devolução para o fornecedor, de algums desses itens o mesmo saira com ICMS na saída (pois temos que devolver esse imposto para o fornecedor); </br>
	 * 
	 * PORTANTO, TODOS os itens que SAIREM na CFOP (5411/6411), porém com ICMS, temos que fazer um estorno no SPED FISCAL ICMS IPI, para Não "Pagarmos impostos a mais", (já que não tivemos crédito de icms na entrada)
	 * 
	 * - COD da tabela 5.1.1 usado no REG E111 -> SP030899 - "OUTRAS HIPÓTESES - PREENCHIDA PELO CONTRIBUINTE"
	 * 
	 * @param movimentosIcmsIpi
	 * @return
	 */
	private RegE111 gerarEstornoDebitoDevolucaoParaFornecedor(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// OBS: Se algum item tem essa cfop, e saiu com ICMS, TEMOS que fazer o ESTORNO no SPED, pois nas entradas não tivemos crédito
		RegE111 regE111 = new RegE111();
		List<Integer> listCfopDevolucaoSemIcms = asList(5411, 6411);
		List<Integer> listCstQueSaemComIcms = asList(0, 10);
		
		Set<DocumentoFiscalItem> setItensDevolucaoParaFazerEstorno = movimentosIcmsIpi.getListItens().stream().
				filter(item -> listCfopDevolucaoSemIcms.contains(item.getCfop()) && 
						listCstQueSaemComIcms.contains(item.getIcmsCst())).collect(toSet());

		Set<Long> setDocFiscIdDevolucaoFazerEstorno = setItensDevolucaoParaFazerEstorno.stream().map(DocumentoFiscalItem::getId).collect(toSet());
		
		String descComplAj = montarDescrComplAjEstornoDebitoDevolucaoParaFornecedor(movimentosIcmsIpi, setDocFiscIdDevolucaoFazerEstorno);
		
		regE111.setCodAjApur("SP030899");				// SP030899|OUTRAS HIPÓTESES - PREENCHIDA PELO CONTRIBUINTE.	// TODO --> PREENCHER COM O CODIGO DA TABELA 5.1.1.	
		regE111.setDescrComplAj(descComplAj);
		regE111.setVlAjApur(setItensDevolucaoParaFazerEstorno.stream().map(DocumentoFiscalItem::getIcmsValor).reduce(BigDecimal.ZERO, BigDecimal::add));
		return regE111;
	}
	
	
	/**
	 * Será feito a apuração do DIFAL, para UsoConsumoOuAtivoInterestadual
	 * 
	 * 
	 * 
	 * @param movimentosIcmsIpi
	 * @return 
	 */
	private List<RegE111> gerarDebitoECreditoUsoConsumoOuAtivoInterestadual(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<RegE111> listRegE111 = new ArrayList<>();
		Set<DocumentoFiscalItem> listDocFiscItemAtivoOuConsumoInterestadual = getListDocFiscItemAtivoOuConsumoInterestadual(movimentosIcmsIpi);
		Set<Long> setDocFiscIdCompraAtivoOuConsumoInterestadual = listDocFiscItemAtivoOuConsumoInterestadual.stream().map(DocumentoFiscalItem::getId).collect(toSet());
		Map<Long, List<DocumentoFiscal>> mapDocFiscConsumoInterestadualPorIdFornecedor = getMapaDocFiscAtivoConsumoPorIdFornecedor(setDocFiscIdCompraAtivoOuConsumoInterestadual, movimentosIcmsIpi.getMapDocumentoFiscalPorCodigo());
		String descComplAjApura = montarDescrAtivoOuConsumoInterestadual(mapDocFiscConsumoInterestadualPorIdFornecedor, setDocFiscIdCompraAtivoOuConsumoInterestadual);

		RegE111 regE111DebitoUsoConsumoOuAtivoInterestadual = gerarDebitosUsoConsumoAtivoInterestadual(listDocFiscItemAtivoOuConsumoInterestadual, descComplAjApura);
		RegE111 regE111CreditoUsoConsumoOuAtivoInterestadual = gerarCreditosUsoConsumoAtivoInterestadual(listDocFiscItemAtivoOuConsumoInterestadual, descComplAjApura);
		
		listRegE111.add(regE111DebitoUsoConsumoOuAtivoInterestadual);
		listRegE111.add(regE111CreditoUsoConsumoOuAtivoInterestadual);
		
		return listRegE111;
	}

	
	// ==================================================================================================================================================================
	// ==================================================================================================================================================================
	// ==================================================================================================================================================================
	

	private Set<DocumentoFiscalItem> getListDocFiscItemAtivoOuConsumoInterestadual(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		// TODO => Ainda, tenho minhas dúvidas se o melhor é considerar as CFOP que estiver no ITEM, ou a OPERAÇÃO em que a nota foi dado entrada;
		List<Integer> listCfopsAtivoOuConsumo = asList(2551, 2407, 2556);
		Set<DocumentoFiscalItem> listDocFiscItemAtivoOuConsumoInterestadual = movimentosIcmsIpi.getListItens().stream().filter(item -> listCfopsAtivoOuConsumo.contains(item.getCfop())).collect(Collectors.toSet());
	
		return listDocFiscItemAtivoOuConsumoInterestadual;
	}
	
	
	/**
	 * Será calculado o DIFAL, para os ITENS que serão usados como Consumo/Ativo, cujo a entrada sejam INTERESTADUAL; <\br>
	 * 
	 * CODIGO AJUSTE -> SP000207 (Tabela 5.1.1)
	 * 
	 * @param movimentosIcmsIpi
	 * @param descComplAjApura 
	 * @return
	 */
	private RegE111 gerarDebitosUsoConsumoAtivoInterestadual(Set<DocumentoFiscalItem> listDocFiscItemAtivoOuConsumoInterestadual, String descComplAjApura) {
		// TODO Auto-generated method stub
		RegE111 regE111 = new RegE111();
		// TODO => Ainda, tenho minhas dúvidas se o melhor é considerar as CFOP que estiver no ITEM, ou a OPERAÇÃO em que a nota foi dado entrada;

		
		BigDecimal icmsValorInterestadualConsumoImportado = listDocFiscItemAtivoOuConsumoInterestadual.stream()
				.filter(item -> docFiscItemService.verificaSeEhImportado(item))
				.map(itemImportado -> itemImportado.getIcmsBase().multiply(ICMS_ALIQ_IMPORTADO))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
			
		BigDecimal icmsValorInterestadualConsumoNacional = listDocFiscItemAtivoOuConsumoInterestadual.stream()
				.filter(item -> !docFiscItemService.verificaSeEhImportado(item))
				.map(itemNacional -> itemNacional.getIcmsBase().multiply(ICMS_ALIQ_NACIONAL))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
			
		BigDecimal debitoDifalUsoConsumo = icmsValorInterestadualConsumoImportado.add(icmsValorInterestadualConsumoNacional);
		
		
		regE111.setCodAjApur("SP000207");				// SP000207|Entrada de mercadoria, oriunda de outro Estado, destinada a uso, consumo ou integração no ativo imobilizado ou utilização de serviço iniciado fora do território paulista - Diferencial de alíquota.|01012015|
		regE111.setDescrComplAj(descComplAjApura);
		regE111.setVlAjApur(debitoDifalUsoConsumo);

		return regE111; 
	}

	/***
	 * 
	 * Será calculado o DIFAL, para os ITENS que serão usados como Consumo/Ativo, cujo a entrada sejam INTERESTADUAL; <\br>
	 * 
	 * CODIGO AJUSTE -> SP020718 (Tabela 5.1.1)
	 * 
	 * @param listDocFiscItemAtivoOuConsumoInterestadual
	 * @param descComplAjApura
	 * @return
	 */
	private RegE111 gerarCreditosUsoConsumoAtivoInterestadual(Set<DocumentoFiscalItem> listDocFiscItemAtivoOuConsumoInterestadual, String descComplAjApura) {
		// TODO Auto-generated method stub
		RegE111 regE111 = new RegE111();
		BigDecimal icmsAliqDifalImportado = ICMS_ALIQ_INTERNA_SP.subtract(ICMS_ALIQ_IMPORTADO);
		BigDecimal icmsAliqDifalNacional = ICMS_ALIQ_INTERNA_SP.subtract(ICMS_ALIQ_NACIONAL);

		BigDecimal icmsValorInterestadualConsumoImportado = listDocFiscItemAtivoOuConsumoInterestadual.stream()
			.filter(item -> docFiscItemService.verificaSeEhImportado(item))
			.map(itemImportado -> itemImportado.getIcmsBase().multiply(icmsAliqDifalImportado))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal icmsValorInterestadualConsumoNacional = listDocFiscItemAtivoOuConsumoInterestadual.stream()
			.filter(item -> !docFiscItemService.verificaSeEhImportado(item))
			.map(itemNacional -> itemNacional.getIcmsBase().multiply(icmsAliqDifalNacional))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		 BigDecimal creditoDifalUsoConsumo = icmsValorInterestadualConsumoImportado.add(icmsValorInterestadualConsumoNacional);
		
		regE111.setCodAjApur("SP020718");				// SP020718|Entrada de mercadoria, oriunda de outro Estado, destinada a uso, consumo ou integração no ativo imobilizado, ou utilização de serviço iniciado noutro Estado - Diferencial de alíquota.|01012015|
		regE111.setDescrComplAj(descComplAjApura);
		regE111.setVlAjApur(creditoDifalUsoConsumo);

		return regE111;
	}
	
	
	
	/**
	 * Retornar a descrição das Notas de entradas de CONSUMO/ATIVO, que serão escrituradas no Registro E111 - com o CODIGO (da tabela 5.1.1) -> SP000207
	 * 
	 * @param movimentosIcmsIpi
	 * @param setDocFiscIdConsumoInterestadual
	 * @return 
	 */
	private String montarDescrAtivoOuConsumoInterestadual(Map<Long, List<DocumentoFiscal>> mapDocFiscConsumoInterestadualPorIdFornecedor, Set<Long> setDocFiscIdConsumoInterestadual) {
		StringBuilder descComplAj = new StringBuilder();
		
		setDocFiscIdConsumoInterestadual.stream().forEach(docFiscId -> {
			List<DocumentoFiscal> listDocFiscConsumoPorFornecedor = mapDocFiscConsumoInterestadualPorIdFornecedor.get(docFiscId);
			if (listDocFiscConsumoPorFornecedor != null) {
				boolean isFirst = true;
				String nomeFornecedor = "";
				for (DocumentoFiscal docFiscConsumoPorFornecedor : listDocFiscConsumoPorFornecedor) {
					if (isFirst) {
						descComplAj.append("NF ");
						nomeFornecedor = docFiscConsumoPorFornecedor.getEmitente().getNome();
						isFirst = false;
					}
					descComplAj.append(docFiscConsumoPorFornecedor.getNumeroNota()).append(", ");
				}
				descComplAj.append(nomeFornecedor).append(", ");
			}
		});
		return descComplAj.toString();
	}
	

	
	/**
	 * Será montado um Mapa de DocumentoFiscais de Ativo/Consumo, interestadual, pelo Id do Emitente/Fornecedor;
	 * 
	 * @param setDocFiscIdCompraAtivoOuConsumoInterestadual - Todos os ID's, dos DocumentoFiscais de entrada que São de CONSUMO e ou ATIVO;
	 * @param mapDocFiscPorId - > Mapa de DocFisc, por Id
	 * @return
	 */
	private Map<Long, List<DocumentoFiscal>> getMapaDocFiscAtivoConsumoPorIdFornecedor(Set<Long> setDocFiscIdCompraAtivoOuConsumoInterestadual, Map<Long, DocumentoFiscal> mapDocFiscPorId) {
		Map<Long, DocumentoFiscal> mapDocFiscInterestadualPorId = mapDocFiscPorId.entrySet().stream()
				.filter(docFisc -> setDocFiscIdCompraAtivoOuConsumoInterestadual.contains(docFisc.getKey()))
				.collect(toMap(Entry::getKey, Entry::getValue));
		
		Map<Long, List<DocumentoFiscal>> mapDocFiscInterAtivoConsumoPorIdEmitente = mapDocFiscInterestadualPorId.values()
				.stream().collect(groupingBy( (DocumentoFiscal docuFisc) -> docuFisc.getEmitente().getId()) );
		 
		return mapDocFiscInterAtivoConsumoPorIdEmitente;
	}

	/**
	 * Será montado a DEscrição das Mercadorias, que nas entradas não tivemos créditos, porém nas Devoluções tivemos que devolver o valor do imposto que veio; <\br>
	 * Descrição referente ao estorno desse débito
	 *
	 * @param movimentosIcmsIpi
	 * @param setDocFiscIdDevolucaoFazerEstorno
	 * @return
	 */
	private String montarDescrComplAjEstornoDebitoDevolucaoParaFornecedor(MovimentoMensalIcmsIpi movimentosIcmsIpi, Set<Long> setDocFiscIdDevolucaoFazerEstorno) {
		Map<Long, DocumentoFiscal> mapDocFiscPorId = movimentosIcmsIpi.getMapDocumentoFiscalPorCodigo();

		StringBuilder inicioDescricao = new StringBuilder();
		StringBuilder notaDescricao = new StringBuilder();
		StringBuilder fimDescricao = new StringBuilder();

		inicioDescricao.append("DEVOLUÇÃO DE MERCADORIA RECEBIDA COM SUBSTITUIÇÃO TRIBUTÁRIA - NOTA FISCAL Nº ");
		
		setDocFiscIdDevolucaoFazerEstorno.stream().forEach(docFiscId -> {
			DocumentoFiscal docFisc = mapDocFiscPorId.get(docFiscId);
			if (docFisc != null) {
				notaDescricao.append(docFisc.getNumeroNota());
				notaDescricao.append(" DE ");
				notaDescricao.append(docFisc.getEmissao());
				notaDescricao.append(",");
			}
		});
		fimDescricao .append(" NA QUAL NÃO FOI APROVEITADO CRÉDITO NA ENTRADA.");
		String descComplAj = inicioDescricao.append(notaDescricao).append(fimDescricao).toString();
		return descComplAj;
	}

	
	
}
