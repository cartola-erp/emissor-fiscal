package net.cartola.emissorfiscal.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import coffeepot.bean.wr.writer.DelimitedWriter;
import net.cartola.emissorfiscal.sped.fiscal.SpedFiscal;
import net.cartola.emissorfiscal.sped.fiscal.SpedFiscalArquivo;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.CustomEnumHandler;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.LocalDateHandler;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.LocalDateTimeHandler;
import net.cartola.emissorfiscal.sped.fiscal.typeHandler.LocalTimeHandler;

public class SpedFiscalWriterUtil {
	
	private static final Logger LOG = Logger.getLogger(SpedFiscalWriterUtil.class.getName());


	/**
	 * Cria o nome do arquivo SPED com base nos parametros informados para a geração do mesmo:
	 * São eles:
	 *  - Loja Id
	 *  - CNPJ
	 *  - Periodo (Data Inicio e Fim)
	 *  
	 * @param spedFiscArqu
	 * @return
	 */
	public static String criarNomeArquivo(SpedFiscalArquivo spedFiscArqu) {
		StringBuilder nome = new StringBuilder();
		if (isCamposNomeValido(spedFiscArqu)) {
			nome.append(spedFiscArqu.getLoja().getId())
				.append("_SpedEFD_ICMS_IPI_")
				.append(spedFiscArqu.getLoja().getCnpj())
				.append("__")
				.append(spedFiscArqu.getDataInicioSped())
				.append("__")
				.append(spedFiscArqu.getDataFimSped())
				.append(".txt");
		} else {
			nome.append(criarNomeArquivo()); 
		}
		return nome.toString();
	}
	
	/**
	 * O nome do arquivo será criado com base no LocalDateTime da JVM
	 * @return
	 */
	public static String criarNomeArquivo() {
		LocalDateTime now = LocalDateTime.now();
		String nome = "SpedEFD_ICMS_IPI_gerado_as_" +now.toString().replace(":", "")+ ".txt";
		return nome;
	}
	
	private static boolean isCamposNomeValido(SpedFiscalArquivo spedFiscArqu) {
		if (spedFiscArqu.getLoja() != null && spedFiscArqu.getLoja().getCnpj() != null
				&& spedFiscArqu.getDataInicioSped() != null && spedFiscArqu.getDataFimSped() != null) {
			return true;
		}
		return false;
	}

	
	public static Path criarDiretorioTemporario() {
		Path path = null;
		try {
			path = Files.createTempDirectory("java-");
		} catch (IOException ex) {
			ex.printStackTrace();
			LOG.log(Level.SEVERE, "Erro ao tentar criar o diretório temporário para salvar o ,txt do SPED ICMS IPI: {0} ", ex);
		} 
		return path;
	}
	
	/**
	 * 
	 * @return Apenas será concatenado o Path, com o nome do Arquivo
	 */
	public static String getDiretorioTemporarioAbsoluto(Path path, String nomeArquivo) {
		return  path.toAbsolutePath() + "//" + nomeArquivo;
	}
	
	/**
	 * 
	 * @param fileSpedFiscal (É o diretório onde será salvo, o arquivo que será gerado. PS: assim como o nome do arquivo etc) 
	 * @return Escritor do SPED FISCAL (Usado para escrever as classes com as anotations. Exs: {@linkplain SpedFiscal}, e todos seus blocos e registros
	 */
	public static DelimitedWriter criarDelimitedWriterSped(File fileSpedFiscal) {
		Writer w = null;
		DelimitedWriter dw = null;
		try {
			w = new FileWriter(fileSpedFiscal);
			dw = new DelimitedWriter(w);
			dw.setDelimiter('|');
			dw.setEscape('\\');
			dw.setRecordInitializator("|");
			dw.setRecordTerminator("|\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(LocalDate.class,LocalDateHandler.class);
		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(LocalTime.class, LocalTimeHandler.class);
		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(LocalDateTime.class,LocalDateTimeHandler.class);
		dw.getObjectMapperFactory().getHandlerFactory().registerTypeHandlerClassFor(Enum.class,CustomEnumHandler.class);
		return dw;
	}
	
	public static byte[] lerBytesDoArquivoGerado(String dirArquivoSpedIcmsIpi) {
		// TODO Auto-generated method stub
//		Status status;
		byte[] todosBytesDoArqu = null;
		try {
			todosBytesDoArqu = Files.readAllBytes(Paths.get(dirArquivoSpedIcmsIpi));
//			boolean is = (readAllBytes.length > 0);
//			spedFiscArqu.setArquivo(Files.readAllBytes(Paths.get(dirArquivoSpedIcmsIpi)));
//			status = Status.SUCESSO;
		} catch (IOException ex) {
			ex.printStackTrace();
//			status = Status.ERRO;
//			spedFiscArqu.setStatus(Status.ERRO);
			LOG.log(Level.WARNING, "Houve algum erro, ao tentar ler o arquivo gerado: {0} " ,ex);
		}
		return todosBytesDoArqu;
	}
	
}
