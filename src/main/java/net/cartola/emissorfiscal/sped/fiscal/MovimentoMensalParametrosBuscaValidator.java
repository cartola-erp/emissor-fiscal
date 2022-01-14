package net.cartola.emissorfiscal.sped.fiscal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @date 11 de jan. de 2022
 * @author robson.costa
 */
public class MovimentoMensalParametrosBuscaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentoMensalParametrosBusca.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
//		ValidationUtils.rejectIfEmpty(errors, null, null, null);
		MovimentoMensalParametrosBusca m = (MovimentoMensalParametrosBusca) obj;

		if ((m.isExportarSpedTodasLojas() && m.isExportarInventario()) && (m.getDataInicioInventario() == null || m.getDataFimInventario() == null) ) {
			errors.rejectValue("dataInicioInventario", "field.require.dataInicioInventario");
			errors.rejectValue("dataFimInventario", "field.require.dataFimInventario");
		}
		
		if (!m.isExportarSpedTodasLojas() && m.isExportarInventario() && m.getInventarioId() == null) {
			errors.rejectValue("inventarioId", "field.require.inventarioId");
		}
		
		if (m.getContadorId() == null || m.getContadorId().equals(0l)) {
			errors.rejectValue("contadorId", "field.require.contadorId");
		}
		
	}

}
