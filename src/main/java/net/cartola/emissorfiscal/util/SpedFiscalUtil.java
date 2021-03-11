package net.cartola.emissorfiscal.util;

import java.text.NumberFormat;

import net.cartola.emissorfiscal.pessoa.Pessoa;

/**
 * Classe Utilitária para ser usada no SPED ICMS IPI 
 * 
 * @author robson.costa
 *
 */
public final class SpedFiscalUtil {

	/**
	 * Obtem o <b> "Código do Participante" </b>
	 * Usos nos REGISTROS: 0150 e C100;
	 * 
	 * @param pessoa
	 * @return Código do participante para a pessoa (emitente ou destinatario) 
	 */
	public static String getCodPart(Pessoa pessoa) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(8);
		nf.setMaximumFractionDigits(8);
//		StringBuilder codPart = new StringBuilder(pessoa.getCodigoErp()).append(0).append(pessoa.getLojaErp());  
		String codPart = pessoa.getCodigoErp() + "0"+ pessoa.getLojaErp();  
		String codParFormatado = StringUtil.somenteNumeros(nf.format(Integer.valueOf(codPart)));
		return codParFormatado;
//		return nf.format(codPart);
	}
	
}
