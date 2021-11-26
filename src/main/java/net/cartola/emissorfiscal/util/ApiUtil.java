package net.cartola.emissorfiscal.util;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import net.cartola.emissorfiscal.exception.EmissorFiscalException;
import net.cartola.emissorfiscal.response.Response;

/**
 * 2 de abr de 2020
 * @author robson.costa
 */
public final class ApiUtil {
	
	private static final Logger LOG = Logger.getLogger(ApiUtil.class.getName());
	
	private ApiUtil () {}
	
	public static <T> Response <T> criarResponseDeErro(EmissorFiscalException ex) {
		return criaResponseDeErro(ex.getMessage());
	}
	
	public static <T> Response <T> criaResponseDeErro(String erro) {
		LOG.info(erro);
		return criaResponseDeErros(Arrays.asList(erro));
	}
	
	public static <T> Response <T> criaResponseDeErro(List<String> erros) {
		return criaResponseDeErros(erros);
	}
	
	private static <T> Response<T> criaResponseDeErros(List<String> errors) {
		Response<T> response = new Response<>();
		response.setErrors(errors);
		return response;
	}

	
	
	
}

