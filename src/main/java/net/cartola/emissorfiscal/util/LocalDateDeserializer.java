package net.cartola.emissorfiscal.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 27 de abr de 2020
 *
 * @author gregory.feijon
 *
 */
@JsonComponent
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String dateStr = p.getText().trim();
		if (dateStr.length() > 10) {
			return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)).toLocalDate();
		} else {
			return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_PATTERN));
		}
	}
	
}
