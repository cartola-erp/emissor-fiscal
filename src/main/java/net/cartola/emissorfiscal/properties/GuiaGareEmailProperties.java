package net.cartola.emissorfiscal.properties;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * @date 18 de mar. de 2021
 * @author robson.costa
 */
@Getter
@Setter
public class GuiaGareEmailProperties {
	
	@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
	private String from;

	@Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
	private String to;
	
}
