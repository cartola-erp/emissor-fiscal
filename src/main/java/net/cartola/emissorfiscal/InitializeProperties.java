package net.cartola.emissorfiscal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.cartola.emissorfiscal.properties.GuiaGareEmailProperties;

/**
 * @date 18 de mar. de 2021
 * @author robson.costa
 */
@Configuration
public class InitializeProperties {
	
	@Bean
	@ConfigurationProperties(value = "guia-gare-email")
	public GuiaGareEmailProperties guiaGareEmailProperties() {
		return new GuiaGareEmailProperties();
	}
	
}
