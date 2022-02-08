package net.cartola.emissorfiscal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import net.cartola.emissorfiscal.converter.StringToLocalDateConverter;

/**
 * Classe para adicionar as implementações da Interface -> {@linkplain org.springframework.core.convert.converter.Converter} <\br>
 * 
 * @date 11 de jan. de 2022
 * @author robson.costa
 */
@Configuration
public class WebConfigBeans {
	
	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;
	
	@PostConstruct
	public void init() {
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
		GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
		genericConversionService.addConverter(new StringToLocalDateConverter());
	}
}
