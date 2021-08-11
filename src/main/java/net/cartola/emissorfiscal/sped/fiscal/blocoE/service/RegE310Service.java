package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.isValorMaiorIgualAZero;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE310;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimentoFcp;

/**
 * @date 10 de ago. de 2021
 * @author robson.costa
 */
@Service
class RegE310Service {
	
	@Autowired
	private RegE316Service regE316Service;

	public RegE310 montarGrupoRegE310(MovimentoMensalIcmsIpi movimentosIcmsIpi, List<DocumentoFiscal> listDocFiscNaUf) {
		RegE310 regE310 = new RegE310();
		
		regE310.setIndMovFcpDifal(IndicadorDeMovimentoFcp.COM_OPERACOES);
		/** CAMPO 03  **/
		/** TODO 
		 * Está com "ZERO", mas acredito que esse campo terei pedir para o usuário "CONTADOR/FISCAL", informar esse valor;
		 * AO MENOS, no começo do sistema já que não terei como buscar essas informações das escriturações anteriores
		 * 
		 * Como nos arquivos que a GABI me passou estavam ZERADO, estou deixnado ZERADO 
		 */
		regE310.setVlSldCredAntDifal(BigDecimal.ZERO);
		regE310.setVlTotDebitosDifal(listDocFiscNaUf.stream().map(DocumentoFiscal::getIcmsValorUfDestino).reduce(BigDecimal.ZERO, BigDecimal::add));
		
		// CAMPOS 5, 6 e 7
		regE310.setVlOutDebDifal(BigDecimal.ZERO);
		regE310.setVlTotCreditosDifal(BigDecimal.ZERO);
		regE310.setVlOutCredDifal(BigDecimal.ZERO);
		// CAMPO 8
		regE310.setVlSldDevAntDifal(calcularVlSldDevAntDifal(regE310));
		// CAMPO 9
		regE310.setVlDeducoesDifal(BigDecimal.ZERO);
		// CAMPO 10 e 11
		regE310.setVlRecolDifal(calcularVlRecolDifal(regE310));
		regE310.setVlSldCredTransportarDifal(calcularVlSldCredTransportarDifal(regE310));
		// CAMPO 12
		regE310.setDebEspDifal(BigDecimal.ZERO);
		// ----------------------------------------------------------------------------------------------------------------
		// ---------------- REFERENTE AO FCP/ FECP ---------
		// CAMPOS 13 e 14
		regE310.setVlSldCredAntFcp(BigDecimal.ZERO);
		regE310.setVlTotDebFcpSaida(listDocFiscNaUf.stream().map(DocumentoFiscal::getIcmsFcpValor).reduce(BigDecimal.ZERO, BigDecimal::add));
		// CAMPOS 15, 16 e 17
		regE310.setVlOutDebFcp(BigDecimal.ZERO);
		regE310.setVlTotCredFcpEntr(BigDecimal.ZERO);
		regE310.setVlOutCredFcp(BigDecimal.ZERO);
		// CAMPO 18
		regE310.setVlSldDevAntFcp(calcularVlSldDevAntFcp(regE310));
		// CAMPO 19
		regE310.setVlDeducoesFcp(BigDecimal.ZERO);
		// CAMPOS 20 e 21
		regE310.setVlRecolFcp(calcularVlRecolFcp(regE310));
		regE310.setVlSldCredTransportarFcp(calcularVlSldCredTransportarFcp(regE310));
		// CAMPO 22
		regE310.setDebEspFcp(BigDecimal.ZERO);
		
		regE310.setRegE316(regE316Service.montrarGrupoRegistro(movimentosIcmsIpi, listDocFiscNaUf));
		return regE310;
	}


	// CAMPO 08 (REG E310)
	private BigDecimal calcularVlSldDevAntDifal(RegE310 regE310) {
		
		Function<RegE310, BigDecimal> fnCalcVlSldDevAntDifal = e310 -> e310.getVlTotDebitosDifal()
				.add(e310.getVlOutDebDifal())
				.subtract(e310.getVlSldCredAntDifal())
				.subtract(e310.getVlTotCreditosDifal())
				.subtract(e310.getVlOutCredDifal());
		
		BigDecimal vlSldDevAntDifal = fnCalcVlSldDevAntDifal.apply(regE310);
		
		return isValorMaiorIgualAZero(vlSldDevAntDifal) ? vlSldDevAntDifal : BigDecimal.ZERO;
	}
	
	// CAMPO 10 (REG E310)
	private BigDecimal calcularVlRecolDifal(RegE310 regE310) {
		BigDecimal vlRecolDifal = regE310.getVlSldDevAntDifal().subtract(regE310.getVlDeducoesDifal());
		return isValorMaiorIgualAZero(vlRecolDifal) ? vlRecolDifal : BigDecimal.ZERO;
	}
	
	// CAMPO 11 (REG E310)
	private BigDecimal calcularVlSldCredTransportarDifal(RegE310 regE310) {
		Function<RegE310, BigDecimal> fnCalcVlSldCredTransportarDifal = e310 -> e310.getVlSldCredAntDifal()
				.add(e310.getVlTotCreditosDifal())
				.add(e310.getVlOutCredDifal())
				.add(e310.getVlDeducoesDifal())
				.subtract(e310.getVlTotDebitosDifal())
				.subtract(e310.getVlOutDebDifal());
		
		BigDecimal vlSldCredTransportarDifal = fnCalcVlSldCredTransportarDifal.apply(regE310);
		
		return isValorMaiorIgualAZero(vlSldCredTransportarDifal) ? vlSldCredTransportarDifal : BigDecimal.ZERO;
	}
	
	
	// ============================================= REFERENTE AO FCP/ FECP =====================================================
	
	// CAMPO 18
	private BigDecimal calcularVlSldDevAntFcp(RegE310 regE310) {
		
		Function<RegE310, BigDecimal> fnCalcVlSldDevAntFcp = e310 -> e310.getVlTotDebFcpSaida()
				.add(e310.getVlOutDebFcp())
				.subtract(e310.getVlSldCredAntFcp())
				.subtract(e310.getVlTotCredFcpEntr())
				.subtract(e310.getVlOutCredFcp());
		
		BigDecimal vlSldDevAntFcp = fnCalcVlSldDevAntFcp.apply(regE310);
		
		return isValorMaiorIgualAZero(vlSldDevAntFcp) ? vlSldDevAntFcp : BigDecimal.ZERO;
	}
	
	// CAMPO 20
	private BigDecimal calcularVlRecolFcp(RegE310 regE310) {
		BigDecimal vlRecolFcp = regE310.getVlSldDevAntFcp().subtract(regE310.getVlDeducoesFcp());
		return isValorMaiorIgualAZero(vlRecolFcp) ? vlRecolFcp : BigDecimal.ZERO;
	}
	
	// CAMPO 21
	private BigDecimal calcularVlSldCredTransportarFcp(RegE310 regE310) {
		Function<RegE310, BigDecimal> fnCalcVlSldCredTransportarFcp = e310 -> e310.getVlSldCredAntFcp()
				.add(e310.getVlTotCredFcpEntr())
				.add(e310.getVlOutCredFcp())
				.add(e310.getVlDeducoesFcp())
				.subtract(e310.getVlTotDebFcpSaida())
				.subtract(e310.getVlOutDebFcp());

		BigDecimal vlSldCredTransportarFcp = fnCalcVlSldCredTransportarFcp.apply(regE310);

		return isValorMaiorIgualAZero(vlSldCredTransportarFcp) ? vlSldCredTransportarFcp : BigDecimal.ZERO;
	}
	
}
