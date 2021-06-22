package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK19.service;

import static net.cartola.emissorfiscal.util.SpedFiscalWriterUtil.criarDelimitedWriterSped;
import static net.cartola.emissorfiscal.util.SpedFiscalWriterUtil.criarDiretorioTemporario;
import static net.cartola.emissorfiscal.util.SpedFiscalWriterUtil.criarNomeArquivo;
import static net.cartola.emissorfiscal.util.SpedFiscalWriterUtil.getDiretorioTemporarioAbsoluto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import coffeepot.bean.wr.writer.DelimitedWriter;
import net.cartola.emissorfiscal.sped.fiscal.SpedFiscal;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Bloco9;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Reg9001AberturaDoBloco9;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Reg9900;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Reg9990EncerramentoDoBloco9;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Reg9999EncerramentoDoArquivoDigital;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDeMovimento;

@Service
public class Bloco9Service {

	
	public Bloco9 criarBloco(SpedFiscal spedFiscal) throws FileNotFoundException, IOException {
		Path path = criarDiretorioTemporario();
		Bloco9 bloco9 = null;
		if (path != null) {
			String absTempDir = getDiretorioTemporarioAbsoluto(path, criarNomeArquivo());
			File fileWithoutBlockNine = new File(absTempDir);
			fileWithoutBlockNine.deleteOnExit();
			DelimitedWriter dw = criarDelimitedWriterSped(fileWithoutBlockNine);
			dw.write(spedFiscal);
			dw.flush();
			dw.close();

			bloco9 = criarBloco9(new File(absTempDir));
		}
		return bloco9;
	}

//	private Bloco9 criarBloco(String file) throws FileNotFoundException, IOException  {
//		return criarBloco9(new File(file));
//	}
	
	private Bloco9 criarBloco9(File spedFile) throws FileNotFoundException, IOException {
		Bloco9 bloco9 = new Bloco9();
		// String == Nome do Registro ("REG") | Long == Quantidade dele gerada
		Map<String, Long> mapRegPorQuantidade = new LinkedHashMap<>();
		
		Reg9001AberturaDoBloco9 reg9001 = new Reg9001AberturaDoBloco9(IndicadorDeMovimento.BLOCO_COM_DADOS_INFORMADOS);
		bloco9.setReg9001(reg9001);
		
		long qtdLinhasSped = gerarMapRegPorQuantidade(mapRegPorQuantidade, spedFile);
		long qtdLinhasBloco9 = preencherQuantidadeBloco9(mapRegPorQuantidade);
		qtdLinhasSped += qtdLinhasBloco9;

		bloco9.setReg9900(montarGrupoReg9900(mapRegPorQuantidade));
		bloco9.setReg9990(new Reg9990EncerramentoDoBloco9(qtdLinhasBloco9));
		bloco9.setReg9999(new Reg9999EncerramentoDoArquivoDigital(qtdLinhasSped));
		return bloco9;
	}


	private long gerarMapRegPorQuantidade(Map<String, Long> mapRegPorQuantidade, File spedFile) throws IOException {
		long qtdLinhasSped = 0l;
		try (BufferedReader br = new BufferedReader(new FileReader(spedFile))) {
			String reg;
			String linha;
			while ((linha = br.readLine()) != null) {
				reg = linha.substring(1, 5);
				Long qtdReg = mapRegPorQuantidade.get(reg);
				qtdReg = qtdReg == null ? 1 : ++qtdReg;
				mapRegPorQuantidade.put(reg, qtdReg);
				qtdLinhasSped++;
			}
		}
		return qtdLinhasSped;
	}


	private int preencherQuantidadeBloco9(Map<String, Long> mapRegPorQuantidade) {
//		if (mapRegPorQuantidade != null && !mapRegPorQuantidade.isEmpty()) {
			mapRegPorQuantidade.put("9001", 1l);
			long qtdBloco9 = mapRegPorQuantidade.size() + 4l;
			mapRegPorQuantidade.put("9900", qtdBloco9);
			mapRegPorQuantidade.put("9990", 1l);
			mapRegPorQuantidade.put("9999", 1l);
			
			return mapRegPorQuantidade.size() + 3;
//		}
	}

	
	private List<Reg9900> montarGrupoReg9900(Map<String, Long> mapRegPorQuantidade) {
		List<Reg9900> listReg9900 = new ArrayList<>();
		mapRegPorQuantidade.forEach((reg, qtd) -> {
			Reg9900 reg9900 = new Reg9900();
			reg9900.setRegBlc(reg);
			reg9900.setQtdRegBlc(qtd);
			listReg9900.add(reg9900);
		});
		return listReg9900;
	}

		
}
