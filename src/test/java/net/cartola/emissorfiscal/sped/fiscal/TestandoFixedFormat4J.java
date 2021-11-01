//package net.cartola.emissorfiscal.sped.fiscal;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.Writer;
//import java.util.ArrayList;
//import java.util.List;
//
//import coffeepot.bean.wr.writer.DelimitedWriter;
//import net.cartola.emissorfiscal.sped.fiscal.SpedFiscal;
//import net.cartola.emissorfiscal.sped.fiscal.bloco0.Bloco0;
//import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0000AberturaArquivoDigital;
//import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0001AberturaDoBloco;
//
///**
// * 20/08/2020
// * @author robson.costa
// */
//public class TestandoFixedFormat4J {
//	
//	public static void main(String[] args) throws IOException {
//		
////		FixedFormatManager ffm = new FixedFormatManagerImpl();
//		
//		Reg0000AberturaArquivoDigital reg0000 = new Reg0000AberturaArquivoDigital();
////		Reg0000AberturaArquivoDigital reg0000 = ffm.load(Reg0000AberturaArquivoDigital.class, data);
//
//		
//		
////		reg0000.setCodVer(VersaoDoLayout.V_014);
//		reg0000.setNome("Empire ");
////		Reg0001AberturaDoBloco reg0001 = new Reg0001AberturaDoBloco();
//		
//		Bloco0 bloco0 = new Bloco0();
////		bloco0.setReg0000(reg0000);
//
////		bloco0.setReg0001(reg0001);
//		
//		
//		Bloco0 bloco00 = new Bloco0();
//		Reg0000AberturaArquivoDigital newreg0000 = new Reg0000AberturaArquivoDigital();
//		newreg0000.setNome("Dots");
//		List<Reg0000AberturaArquivoDigital>  reg00List = new ArrayList<>();
//		
//		reg00List.add(reg0000);
//		reg00List.add(newreg0000);
//		
//		bloco00.setReg0000(newreg0000);
////		bloco00.setReg0000(reg00List);
////		Reg0001AberturaDoBloco newreg0001 = new Reg0001AberturaDoBloco();
////		newreg0001.setReg("032");
////		bloco00.setReg0001(newreg0001);
//		
//		
//		List<Bloco0> bloco0List = new ArrayList<>();
//		bloco0List.add(bloco0);
//		bloco0List.add(bloco00);
//		
//		SpedFiscal spedFiscal = new SpedFiscal();
//		
////		spedFiscal.setBloco0(bloco0List);
//		
//		
//        File file = new File("SPED_FISCAL.txt");
//        Writer w = new FileWriter(file);
//        
//		DelimitedWriter dw = new DelimitedWriter(w);
//		dw.setDelimiter('|');
//		dw.setEscape('\\');
//		dw.setRecordInitializator("|");
//		dw.setRecordTerminator("|\r\n");
//		dw.setVersion(18);
//		dw.write(bloco00);
//		
//		
//		
//		dw.flush();
//		dw.close();
////		System.out.println("Expportei: " +ffm.export(reg0000));		
//		
//	}
//}
