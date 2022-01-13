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
		// TODO Auto-generated method stub
//		ValidationUtils.rejectIfEmpty(errors, null, null, null);
//		MovimentoMensalParametrosBusca movMensalParamBusca = (MovimentoMensalParametrosBusca) obj;
		MovimentoMensalParametrosBusca m = (MovimentoMensalParametrosBusca) obj;

		if ((m.isExportarSpedTodasLojas() && m.isExportarInventario()) && (m.getDataInicioInventario() == null || m.getDataFimInventario() == null) ) {
//			ValidationUtils.rejectIfEmpty(errors, "dataInicioInventario", "field.require.dataInicioInventario");
//			ValidationUtils.rejectIfEmpty(errors, "dataFimInventario", "field.require.dataFimInventario");
			errors.rejectValue("dataInicioInventario", "field.require.dataInicioInventario");
			errors.rejectValue("dataFimInventario", "field.require.dataFimInventario");

//			errors.rejectValue("data", "data.inicio.fim.inventario.nao.informada");
		}
	}

}
