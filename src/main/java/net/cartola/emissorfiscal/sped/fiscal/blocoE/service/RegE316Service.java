package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getMesReferencia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE316;
import net.cartola.emissorfiscal.sped.fiscal.enums.ObrigacaoIcmsARecolher;

/**
 * @date 11 de ago. de 2021
 * @author robson.costa
 */
@Service
class RegE316Service {

	
	
	public List<RegE316> montrarGrupoRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi, List<DocumentoFiscal> listDocFiscNaUf) {
		// TODO Auto-generated method stub
		List<RegE316> listRegE316 = new ArrayList<>();
		
		final String mesRef = getMesReferencia(movimentosIcmsIpi.getDataInicio());

		listDocFiscNaUf.forEach(docFiscNaUf -> {
			RegE316 regE316 = new RegE316();
			
			regE316.setCodOr(ObrigacaoIcmsARecolher.ANTECIPACAO_DIFERENCIAL_ALIQUOTAS);
			// TODO verificar se o melhor é calcular aqui novamente
			// VL_OR = DIFAL + FCP (será que sempre será esse o resultado CERTO do calculo) ??
			regE316.setVlOr(calcularVlOr(docFiscNaUf)); 
			regE316.setDtVcto(LocalDate.now());
			regE316.setCodRec("100102");
			
			regE316.setNumProc(null);
			regE316.setIndProc(null);
			regE316.setProc(null);
			regE316.setTxtCompl(null);
			regE316.setMesRef(mesRef);
			
			listRegE316.add(regE316);
		});
		
		return listRegE316;
	}

	// CAMPO 03 
	private BigDecimal calcularVlOr(DocumentoFiscal docFiscNaUf) {
		BigDecimal vlOr = docFiscNaUf.getIcmsValorUfDestino().add(docFiscNaUf.getIcmsFcpValor());
		return vlOr;
	}
	
	

}
