package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110Service;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE111Service;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE111;

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
		List<RegE111> listE111 = new ArrayList<>();
		
		Set<SpedFiscalRegE110> setSpedFiscRegE110ApuracaoPropria = movimentosIcmsIpi.getSetSpedFiscRegE110ApuracaoPropria();
		
		Set<SpedFiscalRegE110> opRegE110preenchidoPeloUser = spedFiscRegE110Service.findRegE110ByPeriodoAndLoja(movimentosIcmsIpi.getDataInicio(), movimentosIcmsIpi.getDataFim(), movimentosIcmsIpi.getLoja());
		
		RegE111 regE111EntradaUsoConsumoOuAtivoInterestadual = gerarDebitosUsoConsumoAtivoInterestadual(movimentosIcmsIpi);
		RegE111 regE111EstornoDebitoDevolucaoParaFornecedor = gerarEstornoDebitoDevolucaoParaFornecedor(movimentosIcmsIpi);
		
		listE111.add(regE111EstornoDebitoDevolucaoParaFornecedor);
		listE111.add(regE111EntradaUsoConsumoOuAtivoInterestadual);

		
		/**
		 * Acredito que isso abaixo conseguirei, descobrir depois que terminar de montar o REG E110 (então terei que salvar tais informações no DB);
		 */
		// Se Loja == 1 (totalizar os codigos
			// --> SP000219|Recebimento de saldo devedor - estabelecimento centralizador.|01012015|)
			// --> SP020730|Recebimento de saldo credor – estabelecimento centralizador.|01012015|
		// SE loja != 1 
			// --> SP000218|Transferência de saldo credor  para estabelecimento centralizador.|01012015|
			// --> SP020729|Transferência de saldo devedor para estabelecimento centralizador.|01012015|
		
		return listE111;
	}

	
	private RegE111 gerarDebitosUsoConsumoAtivoInterestadual(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		RegE111 regE111 = new RegE111();
		// TODO => Ainda, tenho minhas dúvidas se o melhor é considerar as CFOP que estiver no ITEM, ou a OPERAÇÃO em que a nota foi dado entrada;
		List<Integer> listCfopsAtivoOuConsumo = asList(2551, 2407, 2556);
		
		Set<DocumentoFiscalItem> listDocFiscItemAtivoOuConsumoInterestadual = movimentosIcmsIpi.getListItens().stream().filter(item -> listCfopsAtivoOuConsumo.contains(item.getCfop())).collect(Collectors.toSet());
		Set<Long> setDocFiscIdCompraAtivoOuConsumoInterestadual = listDocFiscItemAtivoOuConsumoInterestadual.stream().map(DocumentoFiscalItem::getId).collect(toSet());
		Map<Long, List<DocumentoFiscal>> mapDocFiscConsumoInterestadualPorIdFornecedor = getMapaDocFiscAtivoConsumoPorIdFornecedor(setDocFiscIdCompraAtivoOuConsumoInterestadual, movimentosIcmsIpi.getMapDocumentoFiscalPorCodigo());
		
		String descComplAjApura = montarDescrAtivoOuConsumoInterestadual(mapDocFiscConsumoInterestadualPorIdFornecedor, setDocFiscIdCompraAtivoOuConsumoInterestadual);
		
		regE111.setCodAjApur("SP000207");				// SP000207|Entrada de mercadoria, oriunda de outro Estado, destinada a uso, consumo ou integração no ativo imobilizado ou utilização de serviço iniciado fora do território paulista - Diferencial de alíquota.|01012015|
		regE111.setDescrComplAj(descComplAjApura);
		regE111.setVlAjApur(calcularDifalParaConsumoEAtivoInterestadual(listDocFiscItemAtivoOuConsumoInterestadual));
		
		return regE111;
	}



	/**
	 * Será calculado o DIFAL, das ENTRADAS INTERESTADUAL, que sejam para CONSUMO e/ou ATIVO;
	 * 
	 * @param listDocFiscItemAtivoOuConsumoInterestadual - Lista dos itens 
	 * @return
	 */
	private BigDecimal calcularDifalParaConsumoEAtivoInterestadual(Set<DocumentoFiscalItem> listDocFiscItemAtivoOuConsumoInterestadual) {
		BigDecimal aliqImportado = new BigDecimal(0.04D);
		BigDecimal aliqNacional = new BigDecimal(0.12D);		// OBS: de Qualquer UF -> SP, é 12% a aliquota
		
		BigDecimal icmsValorInterestadualConsumoImportado = listDocFiscItemAtivoOuConsumoInterestadual.stream()
			.filter(item -> docFiscItemService.verificaSeEhImportado(item))
			.map(itemImportado -> itemImportado.getIcmsBase().multiply(aliqImportado))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal icmsValorInterestadualConsumoNacional = listDocFiscItemAtivoOuConsumoInterestadual.stream()
			.filter(item -> !docFiscItemService.verificaSeEhImportado(item))
			.map(itemNacional -> itemNacional.getIcmsBase().multiply(aliqNacional))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return icmsValorInterestadualConsumoImportado.add(icmsValorInterestadualConsumoNacional);
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
	
	
	// ---------======
	
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
				notaDescricao.append(docFisc.getId());
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
