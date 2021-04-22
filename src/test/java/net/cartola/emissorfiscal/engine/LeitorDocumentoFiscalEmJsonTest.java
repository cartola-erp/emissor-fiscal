package net.cartola.emissorfiscal.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalRepository;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.tributacao.estadual.CalculoFiscalEstadual;
import net.cartola.emissorfiscal.tributacao.federal.CalculoFiscalFederal;

/**
 * @autor robson.costa
 * @data 20 de abr. de 2021
 */


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LeitorDocumentoFiscalEmJsonTest {
	
	private static final Logger LOG = Logger.getLogger(LeitorDocumentoFiscalEmJsonTest.class.getName());
	
	@Autowired
	private DocumentoFiscalService docFiscService;
	
	@Autowired
	private DocumentoFiscalRepository docFiscRepository;
	
	@Autowired
	private CalculoFiscalEstadual calcFiscEstadual;
	
	@Autowired
	private CalculoFiscalFederal calcFiscFederal;
	
	private static String PATH_JSON = "C:\\Users\\robson.costa\\Documents\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\emissor-fiscal\\src\\test\\resources\\";
	
	@Test
	public void test01_LendoCompras() {
		List<DocumentoFiscal> listComprasLidasJson = lerJson("compras_dbf_doc_fisc.json");
		List<DocumentoFiscal> listCompraToBeSaved = new ArrayList<>();
		List<List<String>> listErros = new ArrayList<>();
//		listCompras.stream().forEach(compra -> {
//			calcFiscEstadual.calculaImposto(compra);
//			calcFiscEstadual.calculaImposto(compra);
//			listCompraToBeSaved.add(compra);
//		});
			
		listComprasLidasJson.forEach(compra -> {
			List<String> erros  = docFiscService.setaValoresNecessariosCompra(compra);
			LOG.log(Level.WARNING, "ERROS do Documento:  {0}  " , erros);
			if (!erros.isEmpty()) {
				listErros.add(erros);
			} else {
				listCompraToBeSaved.add(compra);
			}
		});
		System.out.println(listErros);
		List<DocumentoFiscal> savedList = docFiscRepository.saveAll(listCompraToBeSaved);
		
		System.out.println("Compras salvas --> " +savedList.size()+ " >DE: " +listComprasLidasJson.size());
		System.out.println("DIFERENÇA == " +(listComprasLidasJson.size() - savedList.size()));
	}
	
	
	@Test
	public void test02_LendoNfesDocFisc() {
		List<DocumentoFiscal> listComprasLidasJson = lerJson("nfes_doc_fisc.json");
		List<DocumentoFiscal> listNfeToCalcularImpostos = new ArrayList<>();
		List<DocumentoFiscal> listNfesDocsFiscToBeSaved = new ArrayList<>();
		List<List<String>> listErros = new ArrayList<>();
			
		listComprasLidasJson.forEach(nfesDocFisc -> {
			List<String> erros  = docFiscService.validaDadosESetaValoresNecessarios(nfesDocFisc, true, true);
			LOG.log(Level.WARNING, "ERROS do Documento:  {0}  " , erros);
			if (!erros.isEmpty()) {
				listErros.add(erros);
			} else {
				listNfeToCalcularImpostos.add(nfesDocFisc);
			}
		});
		
		listNfeToCalcularImpostos.forEach(nfesDocFisc -> {
			calcFiscEstadual.calculaImposto(nfesDocFisc);
			calcFiscFederal.calculaImposto(nfesDocFisc);
			listNfesDocsFiscToBeSaved.add(nfesDocFisc);
		});
		
		System.out.println(listErros);
		List<DocumentoFiscal> savedList = docFiscRepository.saveAll(listNfesDocsFiscToBeSaved);
		
		System.out.println("NFES salvas --> " +savedList.size()+ " >DE: " +listComprasLidasJson.size());
		System.out.println("DIFERENÇA == " +(listComprasLidasJson.size() - savedList.size()));
	}

	
	/**
	 * Passado o caminho do JSON, do Tipo DocumentoFiscal, o mesmo será lido e retornado numa lista
	 * @param jsonPath
	 * @return
	 */
	private List<DocumentoFiscal> lerJson(String jsonPath) {
		List<DocumentoFiscal> listDocFiscal = new ArrayList<>();
		String path =  PATH_JSON + jsonPath;

		try(JsonReader jsonReader = new JsonReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
					.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) 
					.create();
            jsonReader.beginArray(); //start of json array
            int numberOfRecords = 0;
            while (jsonReader.hasNext()){ //next json array element
                DocumentoFiscal document = gson.fromJson(jsonReader, DocumentoFiscal.class);
                listDocFiscal.add(document);
                numberOfRecords++;
            }
            jsonReader.endArray();
            System.out.println("Total Records Found : "+numberOfRecords);
            
		} catch (FileNotFoundException exFileNotFound) {
			exFileNotFound.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		return listDocFiscal;
	}
	
	
	
}


class LocalDateAdapter implements JsonDeserializer<LocalDate> {

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final Locale BRASIL = new Locale("pt", "BR");
	
	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		String dateStr = json.getAsString().trim();
		if (dateStr.length() > 10) {
			return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, BRASIL)).toLocalDate();
		} else {
			return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_PATTERN, BRASIL));
		}
	}
}

final class LocalDateTimeAdapter implements JsonDeserializer<LocalDateTime> {
	
	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	@Override
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		String dateStr = json.getAsString().trim();
		if (dateStr.contains("T")) {
			return LocalDateTime.parse(dateStr.replace("T", " "), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		} else {
			return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		}
	}
}
