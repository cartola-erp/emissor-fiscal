package net.cartola.emissorfiscal.exception;

import static net.cartola.emissorfiscal.util.ValidationHelper.processaErros;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @date 25 de nov. de 2021
 * @author robson.costa
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//public class EmissorFiscalException extends Exception {
public class EmissorFiscalException extends RuntimeException {

	private static final Logger LOG = Logger.getLogger(EmissorFiscalException.class.getName());
	
	private static final long serialVersionUID = 4355449072546406227L;

	
	public EmissorFiscalException(String messsage) {
		super(messsage);
	}
	
	public EmissorFiscalException(Map<String, Boolean> mapErros) {
		this(processaErros(mapErros));
	}
	
	public EmissorFiscalException(List<String> listMessages) {
		super(montaMensagemErro(listMessages));
	}
	
	protected static String montaMensagemErro(List<String> listErros) {
		StringBuilder sb = new StringBuilder();
		listErros.stream().forEach(erro -> {
			if (erro.contains(":")) {
				sb.append(erro).append("\n\n");
			} else {
				sb.append(erro).append("\n");
			}
		});
		return sb.toString();
	}
	
	
}

