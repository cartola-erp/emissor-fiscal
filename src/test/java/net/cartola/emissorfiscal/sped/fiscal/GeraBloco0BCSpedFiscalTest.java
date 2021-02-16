package net.cartola.emissorfiscal.sped.fiscal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Function;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import coffeepot.bean.wr.writer.DelimitedWriter;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
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
import net.cartola.emissorfiscal.sped.fiscal.enums.FinalidadeDoArquivo;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;
import net.cartola.emissorfiscal.sped.fiscal.enums.VersaoDoLayout;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.CustomEnumHandler;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.LocalDateHandler;

/**
 * 02/09/2020
 * @author robson.costa
 */

//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GeraBloco0BCSpedFiscalTest {
	
	
	
//	@Test 	// 
//	public void test01_DeveGravarArquivoDoSpedFiscal() throws IOException {
	public static void main(String[] args) throws IOException {

		// DEPOIS de preencher alguns registro nos metodos abaixos
		// tentar gravar o arquivo do SPED fiscal, e verificar como está saindo
		
		// Alguns pontos IMPORTANTES: Tenho que ver se precisará ter algum "tratamento especial" (para poder usar a lib "Record),
		// para tipos como:
		
//		SpedFiscalWriter spedFiscalWriter = new SpedFiscalWriter( new File("SPED_FISCAL_coffee.txt"));
//		Reg0000 re000 = new Reg0000();
//		re000.setCodVer(VersaoLayout.VERSAO_014);
//		spedFiscalWriter.write(re000);
//		
//		spedFiscalWriter.flush();
//		spedFiscalWriter.close();
		
		// Enum, BigDecimal, Long, etc
		
		SpedFiscal spedFiscal = gerarArquivoSpedFiscal();
		File file = new File("SPED_FISCAL.txt");
		
		Writer w = new FileWriter(file);
		
		DelimitedWriter dw = new DelimitedWriter(w);
		dw.setDelimiter('|');
		dw.setEscape('\\');
		dw.setRecordInitializator("|");
		dw.setRecordTerminator("|\r\n");

		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(LocalDate.class, LocalDateHandler.class);
		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(Enum.class, CustomEnumHandler.class);
		
		
		dw.write(spedFiscal);

		dw.flush();
		dw.close();
		
		System.out.println("Arquivo Exportado: ");
	}
	
	
	
	private static SpedFiscal gerarArquivoSpedFiscal() {
		SpedFiscal spedFiscal = new SpedFiscal();
		
		spedFiscal.setBloco0(criaBloco0());
		spedFiscal.setBlocoB(criaBlocoB());
//		spedFiscal.setBlocoC(criaBlocoC());
		
		return spedFiscal;
	}
	
	
	
	
	private static Bloco0 criaBloco0() {
		Bloco0 bloco0 = new Bloco0();
		
		Reg0000AberturaArquivoDigital reg0000 = new Reg0000AberturaArquivoDigital();
		// PREENCHER REGISTRO
		reg0000.setNome("Thomas Shelby");
		reg0000.setCodVer(VersaoDoLayout.V_014);
//		reg0000.setCodFin(FinalidadeDoArquivo.REMESSA_ARQUIVO_ORIGINAL);
//		reg0000.setDtFin(LocalDate.now());
//		reg0000.setDtIni(LocalDate.now());
		reg0000.setDtFin(LocalDate.now());
		reg0000.setDtIni(LocalDate.now());
		
//		Reg0001AberturaDoBloco reg0001 = new Reg0001AberturaDoBloco();
		// PREENCHER REGISTRO		
		
		Reg0990EncerramentoDoBloco reg0990 = new Reg0990EncerramentoDoBloco(12L);
		// PREENCHER REGISTRO
		
		bloco0.setReg0000(reg0000);
//		bloco0.setReg0001(reg0001);
		
		bloco0.setReg0990(reg0990);
		return bloco0;
	}
	
	
	private static BlocoB criaBlocoB() {
		BlocoB blocoB = new BlocoB();
		RegB001AberturaDoBloco regB001 = new RegB001AberturaDoBloco();
		// PREENCHER REGISTRO
		regB001.setIndDad(IndicadorDeMovimento.BLOCO_SEM_DADOS_INFORMADOS);
//		regB001.setTestBigDecimal(new BigDecimal(1232D));
		
		RegB990EncerramentoDoBlocoB regB990 = new RegB990EncerramentoDoBlocoB(2L);
		// PREENCHER REGISTRO
		blocoB.setRegB001(regB001);
		blocoB.setRegB990(regB990);
		
		return blocoB;
	}
	
	private BlocoC criaBlocoC() {
		BlocoC blocoC = new BlocoC();
		
		RegC001AberturaDoBlocoC regC001 = new RegC001AberturaDoBlocoC(IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS);
		// PREENCHER REGISTRO
		RegC100 regC100 = new RegC100();
		// PREENCHER REGISTRO
		regC100.setChvNfe(1212087L);
		regC100.setDtDoc(LocalDate.now());
		regC100.setDtES(LocalDate.now());
		regC100.setIndOper(IndicadorDeOperacao.SAIDA);
		
		RegC990EncerramentoDoBlocoC regC990 = new RegC990EncerramentoDoBlocoC(2L);
		// PREENCHER REGISTRO

		blocoC.setRegC001(regC001);
//		blocoC.setRegC100(regC100);
		blocoC.setRegC990(regC990);
		
//		Function<T, R>
		
//		Comparator<DocumentoFiscalItem> compare = 
//		Comparator.comparing(keyExtractor)
		
		return blocoC;
	}
	
}
