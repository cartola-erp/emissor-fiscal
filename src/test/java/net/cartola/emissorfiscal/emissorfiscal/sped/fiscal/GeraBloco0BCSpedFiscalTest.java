package net.cartola.emissorfiscal.emissorfiscal.sped.fiscal;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.sped.fiscal.SpedFiscal;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Bloco0;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0000AberturaArquivoDigital;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0001AberturaDoBloco;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0990EncerramentoDoBloco;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.BlocoB;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.RegB001AberturaDoBloco;
import net.cartola.emissorfiscal.sped.fiscal.blocoB.RegB990EncerramentoDoBlocoB;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.BlocoC;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC001AberturaDoBlocoC;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC100;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC990EncerramentoDoBlocoC;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

/**
 * 02/09/2020
 * @author robson.costa
 */

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GeraBloco0BCSpedFiscalTest {
	
	
	
	@Test 	// 
	public void test01_DeveGravarArquivoDoSpedFiscal() {
		// DEPOIS de preencher alguns registro nos metodos abaixos
		// tentar gravar o arquivo do SPED fiscal, e verificar como está saindo
		
		// Alguns pontos IMPORTANTES: Tenho que ver se precisará ter algum "tratamento especial" (para poder usar a lib "Record),
		// para tipos como:
		
		
		// Enum, BigDecimal, Long, etc
		
		
		
	}
	
	
	
	private SpedFiscal gerarArquivoSpedFiscal() {
		SpedFiscal spedFiscal = new SpedFiscal();
		
		spedFiscal.setBloco0(criaBloco0());
		spedFiscal.setBlocoB(criaBlocoB());
		spedFiscal.setBlocoC(criaBlocoC());
		
		return spedFiscal;
	}
	
	
	
	
	private Bloco0 criaBloco0() {
		Bloco0 bloco0 = new Bloco0();
		Reg0000AberturaArquivoDigital reg0000 = new Reg0000AberturaArquivoDigital();
		// PREENCHER REGISTRO
		
		
		Reg0001AberturaDoBloco reg0001 = new Reg0001AberturaDoBloco();
		// PREENCHER REGISTRO		
		
		
		Reg0990EncerramentoDoBloco reg0990 = new Reg0990EncerramentoDoBloco(12L);
		// PREENCHER REGISTRO
		
		
		
		bloco0.setReg0000(reg0000);
		bloco0.setReg0001(reg0001);
		
		bloco0.setReg0990(reg0990);
		return bloco0;
	}
	
	
	private BlocoB criaBlocoB() {
		BlocoB blocoB = new BlocoB();
		RegB001AberturaDoBloco regB001 = new RegB001AberturaDoBloco();
		// PREENCHER REGISTRO
		
		RegB990EncerramentoDoBlocoB regB990 = new RegB990EncerramentoDoBlocoB();
		// PREENCHER REGISTRO
		
		return blocoB;
	}
	
	private BlocoC criaBlocoC() {
		BlocoC blocoC = new BlocoC();
		
		RegC001AberturaDoBlocoC regC001 = new RegC001AberturaDoBlocoC(IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS);
		// PREENCHER REGISTRO
		
		RegC100 regC100 = new RegC100();
		// PREENCHER REGISTRO
		
		RegC990EncerramentoDoBlocoC regC990 = new RegC990EncerramentoDoBlocoC(2L);
		// PREENCHER REGISTRO

		blocoC.setRegC001(regC001);
//		blocoC.setRegC100(regC100);
		blocoC.setRegC990(regC990);
		
		return blocoC;
	}
	
}
