package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.ENTRADA;
import static net.cartola.emissorfiscal.documento.IndicadorDeOperacao.SAIDA;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.Tabela511AjusteApuracaoIcmsSpService.getTipoAjuste;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.Tabela511AjusteApuracaoIcmsSpService.getTipoDeducao;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoAjusteTabela511.ICMS_PROPRIA;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511.DEBITOS_ESPECIAIS;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511.DEDUCOES_DO_IMPOSTO_APURADO;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511.ESTORNO_DE_CREDITOS;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511.ESTORNO_DE_DEBITOS;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511.OUTROS_CREDITOS;
import static net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511.OUTROS_DEBITOS;
import static net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento.ESCRITURACAO_EXTEMPORANEA_DE_DOCUMENTO_COMPLEMENTAR;
import static net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento.ESCRITURAÇÃO_EXTEMPORÂNEA_DE_DOCUMENTO_REGULAR;
import static net.cartola.emissorfiscal.util.NumberUtilRegC100.getBigDecimalNullSafe;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.model.sped.fiscal.icms.propria.SpedFiscalRegE110Service;
import net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoAjusteTabela511;
import net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.ObservacoesLancamentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.OutrasObrigacoesEAjustes;
import net.cartola.emissorfiscal.sped.fiscal.RegistroAnalitico;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC195;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC197;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD195;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD197;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE110;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE111;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;
import net.cartola.emissorfiscal.util.NumberUtilRegC100;

/**
 * @data 11 de jun. de 2021
 * @author robson.costa
 */
@Service
class RegE110Service {
	
	@Autowired
	private RegE111Service reg111Service;
	
	@Autowired
	private RegE116Service regE116Service;
	
	@Autowired
	private SpedFiscalRegE110Service spedFiscRegE110Service;

//	@Autowired
//	private Tabela511AjusteApuracaoIcmsSpService tbl511AjusteApuracaoIcmsSpService;
	
	/** 
	 * Super Classe do REG C 197 e D197 
	 */
	private Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes;


	
	public RegE110 montaGrupoRegE110(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		RegE110 regE110 = new RegE110();
		
		List<RegE111> listRegE111 = reg111Service.montarGrupoRegE111(movimentosIcmsIpi);
		
		regE110.setVlTotDebitos(calcularVlTotalDebitos(movimentosIcmsIpi.getMapRegistroAnaliticoPorTipoOperacao()));	/** CAMPO 02  **/ 
		regE110.setVlAjDebitos(calcularVlAjusteDebitos(movimentosIcmsIpi.getSetObservacoesLancamentoFiscal()));			/** CAMPO 03  **/ 
		/**
		 *  LEMBRANDO que algumas coisas do registro E111  são preenchidas manualmente com base... ex.: estornos de devolucoes, difal etc...
		 *  mas acredito que consigo "prever a maioria desses casos, fazendo "uma pré consulta", e mostrando para o usuário antes de gerar",
		 *  acho que inclusive, isso seja viável salvar numa tabela, em que o Analista fiscal, possa editar ou até msm adicionar esses tipos de informações
		 *  antes de gerar o SPED FISCAL, 
		 */
		regE110.setVlTotDebitos(calcularVlAjApur(listRegE111, ICMS_PROPRIA, OUTROS_DEBITOS));					/** (Ref.: Reg E111)	CAMPO 04  **/ 
		regE110.setVlEstornosCred(calcularVlAjApur(listRegE111, ICMS_PROPRIA, ESTORNO_DE_CREDITOS));			/** (Ref.: Reg E111)	CAMPO 05  **/
		/** CAMPO 06  **/
		regE110.setVlTotCreditos(calcularVlTotalCreditos(movimentosIcmsIpi.getMapRegistroAnaliticoPorTipoOperacao()));		
		/** CAMPO 07  **/
		regE110.setVlAjCreditos(calcularVlAjusteCreditos(movimentosIcmsIpi.getSetObservacoesLancamentoFiscal()));			
		regE110.setVlTotAjCreditos(calcularVlAjApur(listRegE111, ICMS_PROPRIA, OUTROS_CREDITOS));			/** (Ref.: Reg E111)	CAMPO 08  **/
		regE110.setVlEstornosDeb(calcularVlAjApur(listRegE111, ICMS_PROPRIA, ESTORNO_DE_DEBITOS)); 			/** (Ref.: Reg E111)	CAMPO 09  **/
		
		/** CAMPO 10  **/
		/** TODO 
		 * Está com "NULL", mas acredito que esse campo terei pedir para o usuário "CONTADOR/FISCAL", informar esse valor;
		 * AO MENOS, no começo do sistema já que não terei como buscar essas informações das escriturações anteriores
		 * 
		 */
		regE110.setVlSldCredorAnt(null);

		/** CAMPO 11  **/
		BigDecimal vlSldApurado = calcularVlSldApurado(regE110);
		boolean isSldApuradoMaiorQueZero = isValorMaiorQueZero(vlSldApurado);
		regE110.setVlSldApurado(isSldApuradoMaiorQueZero ? vlSldApurado : BigDecimal.ZERO);
		/** CAMPO 12  **/
		regE110.setVlTotDed(calcularVlTotDed(movimentosIcmsIpi.getSetObservacoesLancamentoFiscal(), listRegE111, vlSldApurado));
		/** CAMPO 13  **/
		BigDecimal vlIcmsRecolher = calcularVlIcmsRecolher(regE110);
		boolean isVlIcmsRecolherMaiorQueZero = isValorMaiorQueZero(vlIcmsRecolher);
		regE110.setVlIcmsRecolher(isVlIcmsRecolherMaiorQueZero ? vlIcmsRecolher : BigDecimal.ZERO);
		/** CAMPO 14  **/
		BigDecimal vlSldCredorTransportar = calcularVlSldCredorTransportar(regE110);
		regE110.setVlSldCredorTransportar(vlSldCredorTransportar);
		/** CAMPO 15 **/
		regE110.setDebEsp(calcularDebEsp(movimentosIcmsIpi, listRegE111));
		
		regE110.setRegE116(regE116Service.montarGrupoDeRegistro(movimentosIcmsIpi, regE110));
		return regE110;
	}

	/**
	 * Será calculado o "VL AJ APUR", do registro E111, conforme o código de apura;
	 * 
	 * @param listRegE111
	 * @param tipoAjuste -> Terceiro caracter do codigo de apuração
	 * @param tipoDeducao -> Quarto caracter do código de apuração
	 * @return total do "VL AJ APUR", para a combinação de "Tipo Ajuste" e "Tipo Deducao";
	 */
	private BigDecimal calcularVlAjApur(List<RegE111> listRegE111, TipoAjusteTabela511 tipoAjuste, TipoDeducaoTabela511 tipoDeducao) {
		BigDecimal totalVlAjApur = listRegE111.stream()
							.filter(regE111 -> tipoAjuste.equals(getTipoAjuste(regE111.getCodAjApur())) && tipoDeducao.equals(getTipoDeducao(regE111.getCodAjApur())))
							.map(RegE111::getVlAjApur)
							.reduce(BigDecimal.ZERO, BigDecimal::add);
		return totalVlAjApur;
	}


	// Campo 02 
	private BigDecimal calcularVlTotalDebitos(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map) {
		if (!isMapPopulado(map, SAIDA)) {
			return BigDecimal.ZERO;
		}
		Set<RegistroAnalitico> setRegistroAnalitico = map.get(SAIDA);
		BigDecimal vlTotalDebitos = setRegistroAnalitico.stream().map(RegistroAnalitico::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return vlTotalDebitos;
	}
	
	// Campo 03
	private BigDecimal calcularVlAjusteDebitos(Set<ObservacoesLancamentoFiscal> setObservacoesLancamentoFiscal) {
		Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes = getSetOutrasObrigacoesEAjustes(setObservacoesLancamentoFiscal);
		BigDecimal vlAjusteDebitos = setOutrasObrigacoesEAjustes.stream().filter(outraObrigacao -> isValorAjusteDebitos(outraObrigacao))
				.map(OutrasObrigacoesEAjustes::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return (vlAjusteDebitos == null) ? BigDecimal.ZERO : vlAjusteDebitos;
	}



	// Campo 06
	private BigDecimal calcularVlTotalCreditos(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map) {
		if (!isMapPopulado(map, ENTRADA)) {
			return BigDecimal.ZERO;
		}
		Set<RegistroAnalitico> setRegistroAnalitico = map.get(ENTRADA);
		BigDecimal vlTotalCreditos = setRegistroAnalitico.stream().map(RegistroAnalitico::getVlIcms).reduce(BigDecimal.ZERO, BigDecimal::add);
		return vlTotalCreditos;
	}

	// CAMPO 07
	private BigDecimal calcularVlAjusteCreditos(Set<ObservacoesLancamentoFiscal> setObservacoesLancamentoFiscal) {
		Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes = getSetOutrasObrigacoesEAjustes(setObservacoesLancamentoFiscal);
		
		BigDecimal vlAjusteCreditos = setOutrasObrigacoesEAjustes.stream().filter(outraObrigacao -> isValorAjusteCredito(outraObrigacao))
				.map(OutrasObrigacoesEAjustes::getVlIcms)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return (vlAjusteCreditos == null) ? BigDecimal.ZERO : vlAjusteCreditos;
	}

	
	// CAMPO 11 e 13
	private boolean isValorMaiorQueZero(BigDecimal value) {
		if (value != null && (value.compareTo(BigDecimal.ZERO)) >= 0) {
//			return vlSldApurado;
			return true;
		}
		return false;
//		return BigDecimal.ZERO;
	}

	
	// CAMPO 11 Funcao que calcular o VL SLD APURADO
	Function<RegE110, BigDecimal> fnCalcVlSldApuracao = e110 -> getBigDecimalNullSafe(e110.getVlTotDebitos())
			.add(getBigDecimalNullSafe(e110.getVlAjDebitos())
			.add(getBigDecimalNullSafe(e110.getVlTotAjDebitos()))
			.add(getBigDecimalNullSafe(e110.getVlEstornosCred()))
			.subtract(getBigDecimalNullSafe(e110.getVlTotCreditos()))
			.subtract(getBigDecimalNullSafe(e110.getVlAjCreditos()))
			.subtract(getBigDecimalNullSafe(e110.getVlTotAjCreditos()))
			.subtract(getBigDecimalNullSafe(e110.getVlEstornosDeb()))
			.subtract(getBigDecimalNullSafe(e110.getVlSldCredorAnt())));
	
	// CAMPO 11
	private BigDecimal calcularVlSldApurado(RegE110 regE110) {
		BigDecimal vlSldApurado = BigDecimal.ZERO;
		vlSldApurado = fnCalcVlSldApuracao.apply(regE110);
		return vlSldApurado;
	}
	



	// CAMPO 12
	private BigDecimal calcularVlTotDed(Set<ObservacoesLancamentoFiscal> setObservacoesLancamentoFiscal, List<RegE111> listRegE111, BigDecimal vlSldApurado) {
		boolean isSldApuradoMaiorQueZero = isValorMaiorQueZero(vlSldApurado);
		Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes = getSetOutrasObrigacoesEAjustes(setObservacoesLancamentoFiscal);
		List<String> listTerceiroChar = Arrays.asList("6");
		List<String> listQuartoChar = Arrays.asList("0");
				
		BigDecimal totalVlIcmsOutrasObrigacoes = setOutrasObrigacoesEAjustes.stream()
									.filter(outraObrigacao -> isValorAjusteCreditoOrDebito(outraObrigacao, listTerceiroChar, listQuartoChar))
									.map(OutrasObrigacoesEAjustes::getVlIcms)
									.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalRegE111DeducaoIcms = calcularVlAjApur(listRegE111, ICMS_PROPRIA, DEDUCOES_DO_IMPOSTO_APURADO);
		
		BigDecimal vlTotDed = BigDecimal.ZERO;

		if (!isSldApuradoMaiorQueZero) {
			vlTotDed = totalVlIcmsOutrasObrigacoes.add(totalRegE111DeducaoIcms).add(vlSldApurado);	
		} else {
			vlTotDed = totalVlIcmsOutrasObrigacoes.add(totalRegE111DeducaoIcms);
		}
		return vlTotDed;
	}
	
	// CAMPO 13
	private BigDecimal calcularVlIcmsRecolher(RegE110 regE110) {
		BigDecimal vlIcmsRecolher = regE110.getVlSldApurado().subtract(regE110.getVlTotDed());
		return vlIcmsRecolher;
	}

//	/**
//	 * CAMPO 14
//	 * 
//	 * TODO -> Tenho minhas dúvidas, se é para ser "somado/adicionado", o vlIcmsRecolher no SALDO CREDOR da FORMA que está abaixo
//	 * 
//	 * @param isSldApuradoMaiorQueZero
//	 * @param vlSldApurado
//	 * @param isVlIcmsRecolherMaiorQueZero
//	 * @param vlIcmsRecolher
//	 * @return
//	 */
//	private BigDecimal calcularVlSldCredorTransportar(boolean isSldApuradoMaiorQueZero, BigDecimal vlSldApurado, boolean isVlIcmsRecolherMaiorQueZero, BigDecimal vlIcmsRecolher) {
//		BigDecimal vldSldCredorTransportar = BigDecimal.ZERO;
//		if (!isSldApuradoMaiorQueZero) {
//			 vldSldCredorTransportar = vldSldCredorTransportar.add(vlSldApurado.abs());
//		}
//		
//		if (!isVlIcmsRecolherMaiorQueZero) {
//			vldSldCredorTransportar = vldSldCredorTransportar.add(vlIcmsRecolher.abs());
//		}
//		return isValorMaiorQueZero(vldSldCredorTransportar) ? BigDecimal.ZERO : vldSldCredorTransportar.abs();
//	}

	// CAMPO 14
	private BigDecimal calcularVlSldCredorTransportar(RegE110 regE110) {
		BigDecimal vlSldCredorTransportar = BigDecimal.ZERO;
		vlSldCredorTransportar = fnCalcVlSldApuracao.apply(regE110).subtract(getBigDecimalNullSafe(regE110.getVlTotDed()));
		
		return isValorMaiorQueZero(vlSldCredorTransportar) ? BigDecimal.ZERO : vlSldCredorTransportar;
	}
	
	// CAMPO 15
	private BigDecimal calcularDebEsp(MovimentoMensalIcmsIpi movimentosIcmsIpi, List<RegE111> listRegE111) {
		// TODO Auto-generated method stub
		List<String> listTerceiroCharOutrasObriga = Arrays.asList("7");
		List<String> listQuartoCharOutrasObriga = Arrays.asList("0");
		
		Map<SituacaoDoDocumento, Set<DocumentoFiscal>> mapDocFiscPorSituacao = movimentosIcmsIpi.getMapDocumentoFiscalPorSituacao();
		Set<DocumentoFiscal> setDocFiscExtemporaneo = new HashSet<>();
		setDocFiscExtemporaneo.addAll(mapDocFiscPorSituacao.get(ESCRITURAÇÃO_EXTEMPORÂNEA_DE_DOCUMENTO_REGULAR));
		setDocFiscExtemporaneo.addAll(mapDocFiscPorSituacao.get(ESCRITURACAO_EXTEMPORANEA_DE_DOCUMENTO_COMPLEMENTAR));
		
		BigDecimal totalDebEspDocFiscExtemporaneo = setDocFiscExtemporaneo.stream()
			.map( docFisc -> NumberUtilRegC100.getVlrOrBaseCalc(docFisc.getIcmsValor(), docFisc.getTipoOperacao()))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		Set<OutrasObrigacoesEAjustes> setOutrasObrigacoesEAjustes = getSetOutrasObrigacoesEAjustes(movimentosIcmsIpi.getSetObservacoesLancamentoFiscal());
		BigDecimal totalDebEspOperacoesProprias = setOutrasObrigacoesEAjustes.stream()
			.filter(outraObrigacao -> isValorAjusteCreditoOrDebito(outraObrigacao, listTerceiroCharOutrasObriga, listQuartoCharOutrasObriga))
			.map(OutrasObrigacoesEAjustes::getVlIcms)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totalVlAjApurDebitosEspeciais = calcularVlAjApur(listRegE111, ICMS_PROPRIA, DEBITOS_ESPECIAIS);
		
		BigDecimal totalDebitosEspeciais = totalDebEspDocFiscExtemporaneo.add(totalDebEspOperacoesProprias).add(totalVlAjApurDebitosEspeciais);
		return totalDebitosEspeciais;
	}
	
	
	/**
	 * @param setObservacoesLancamentoFiscal
	 * @return 
	 */
	private Set<OutrasObrigacoesEAjustes> getSetOutrasObrigacoesEAjustes(Set<ObservacoesLancamentoFiscal> setObservacoesLancamentoFiscal) {
		// Irei receber no metodo um set de obs. de lancamento fiscal
		
		if (this.setOutrasObrigacoesEAjustes == null || this.setOutrasObrigacoesEAjustes.isEmpty()) {
			 this.setOutrasObrigacoesEAjustes = new HashSet<>();
			
			for (ObservacoesLancamentoFiscal obsLancamentoFiscal : setObservacoesLancamentoFiscal) {
				if (obsLancamentoFiscal instanceof RegC195) {
					List<RegC197> regC197 = ((RegC195) obsLancamentoFiscal).getRegC197();
					setOutrasObrigacoesEAjustes.addAll(regC197);
				}
				
				if (obsLancamentoFiscal instanceof RegD195) {
					List<RegD197> regD197 = ((RegD195) obsLancamentoFiscal).getRegD197();
					setOutrasObrigacoesEAjustes.addAll(regD197);
				}
			}
		}
		return this.setOutrasObrigacoesEAjustes;
	}

	
	// =========================================================================== Validacoes ==============================================================================

	/**
	 * Irá verificar se tem algo no Mapa de Registro analitico, referente a uma chave (ENTRADA ou SAIDA)
	 * 
	 * @param map
	 * @param indOperacao
	 * @return
	 */
	private boolean isMapPopulado(Map<IndicadorDeOperacao, Set<RegistroAnalitico>> map, IndicadorDeOperacao indOperacao) {
		if (map == null || map.isEmpty() || !map.containsKey(indOperacao)) {
			return false;
		}
		return true;
	}
	
	// CAMPO 03
	private boolean isValorAjusteDebitos(OutrasObrigacoesEAjustes outraObrigacoes) {
		List<String> listTerceiroChar = Arrays.asList("3", "4", "5");
		List<String> listQuartoChar = Arrays.asList("0", "3", "4", "5", "6", "7", "8");
		return isValorAjusteCreditoOrDebito(outraObrigacoes, listTerceiroChar, listQuartoChar);
	}
	
	// CAMPO 06
	private boolean isValorAjusteCredito(OutrasObrigacoesEAjustes outraObrigacao) {
		List<String> listTerceiroChar = Arrays.asList("0", "1", "2");
		List<String> listQuartoChar = Arrays.asList("0", "3", "4", "5", "6", "7", "8");
		return isValorAjusteCreditoOrDebito(outraObrigacao, listTerceiroChar, listQuartoChar);
	}

	/**
	 * 
	 * @param outraObrigacoes	- Obj: Usado para escriturar os registros C197, D197 e C597 (validação será feita dentro do codAj)
	 * @param listTerceiroChar - Lista dos TERCEIRO caracteres válidos! Para o AJUSTE de CREDITO  ou DÉBITO (CAMPOS:  03 e 06) </br>
	 * @param listQuartoChar - Lista dos QUARTO caracteres válidos ! Para o AJUSTE de CREDITO  ou DÉBITO (CAMPOS:  03 e 06)
	 * @return
	 */
	private boolean isValorAjusteCreditoOrDebito(OutrasObrigacoesEAjustes outraObrigacoes, List<String> listTerceiroChar, List<String> listQuartoChar) {
		String terceiroChar = outraObrigacoes.getCodAj().substring(2, 3);
		String quartoChar = outraObrigacoes.getCodAj().substring(3, 4);
		if (listTerceiroChar.contains(terceiroChar) && listQuartoChar.contains(quartoChar)) {
			return true;
		}
		return false;
	}

	
}
