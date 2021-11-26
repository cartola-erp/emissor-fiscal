package net.cartola.emissorfiscal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.cartola.emissorfiscal.response.Response;
import net.cartola.emissorfiscal.util.ApiUtil;

/**
 * @date 25 de nov. de 2021
 * @author robson.costa
 */
@RestControllerAdvice
public class EmissorFiscalExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Se for feita o LANCAMENTO da exceção <b>EmissorFiscalException<b> <br>
	 * 
	 * Será retornado um HTTP STATUS 500
	 * 
	 * @param <T>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(EmissorFiscalException.class)
	public final <T> ResponseEntity<Response<T>> handleEmissorFiscalException(EmissorFiscalException ex) {
//		return ResponseEntity.badRequest().body(ApiUtil.criarResponseDeErro(ex));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiUtil.criarResponseDeErro(ex));
	}
//	public final <>
	
	
	
}
