package net.cartola.emissorfiscal.sped.fiscal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.documento.ChaveAcesso;
import net.cartola.emissorfiscal.documento.FinalidadeEmissao;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;

/**
 * @autor robson.costa
 * @data 15 de abr. de 2021
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChaveAcessoTest {

	private static Logger LOG = Logger.getLogger(ChaveAcessoTest.class.getName());
	
//	public static void main(String[] args) {
////		ChaveAcesso chaveAcesso = new ChaveAcesso("35200745543915020450650110002467981388294592");
//		ChaveAcesso chaveAcesso = new ChaveAcesso("35200745543915020450550010000143211163636380");
//
//		
//		System.out.println("CHAVE ACESSO EXTRAIDA ---> " +chaveAcesso.toString());
//	}
	
	@Test
	public void test01_TentaExtrairInformacoesDeUmaChaveAcesso() {
		ChaveAcesso chaveAcesso = new ChaveAcesso("35200745543915020450550010000143211163636380");
		
		assertEquals(EstadoSigla.SP, chaveAcesso.getUfEmitente());
		assertEquals("20", chaveAcesso.getAnoEmissao());
		assertEquals("07", chaveAcesso.getMesEmissao());
		assertEquals("45543915020450", chaveAcesso.getCnpjEmitente());
		assertEquals(ModeloDocumentoFiscal._55, chaveAcesso.getModeloDocumento());
		assertEquals(1, Integer.parseInt(chaveAcesso.getSerie()));
		assertEquals(14321, Integer.parseInt(chaveAcesso.getNumeroNota()));
		assertEquals(FinalidadeEmissao.NORMAL, chaveAcesso.getTipoEmissao());
		assertEquals(16363638, Integer.parseInt(chaveAcesso.getCodigoNfe()));
		assertEquals("0", chaveAcesso.getDigitoVerificador());
		
		LOG.log(Level.INFO, "Chave Acesso OBJ: {0} " ,chaveAcesso);
	}
	
}
