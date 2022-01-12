package net.cartola.emissorfiscal.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.convert.converter.Converter;

/**
 * @date 11 de jan. de 2022
 * @author robson.costa
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate>{
	
	private static final Logger LOG = Logger.getLogger(StringToLocalDateConverter.class.getName());
	
	private static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public LocalDate convert(String source) {
		try {
//			LocalDate targetType = LocalDate.parse(source, DTF);
			return LocalDate.parse(source, DTF);
		} catch (Exception ex) {
			LOG.log(Level.WARNING, "Erro ao converter a String p/ LocalDate: {0} " ,ex.getStackTrace());
		}
		return null;
	}

}
