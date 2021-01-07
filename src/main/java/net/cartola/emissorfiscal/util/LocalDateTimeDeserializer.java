package net.cartola.emissorfiscal.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 04/08/2020
 *
 * @author gregory.feijon
 *
 */

@JsonComponent
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String dateStr = p.getText().trim();
		if (dateStr.contains("T")) {
			return LocalDateTime.parse(dateStr.replace("T", " "), DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		} else {
			return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		}
	}

}
