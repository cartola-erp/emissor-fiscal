package net.cartola.emissorfiscal.util;

import static java.lang.Integer.toString;
import static net.cartola.emissorfiscal.util.XmlUtil.getTagConteudo;

import java.util.List;

import net.cartola.emissorfiscal.devolucao.DevolucaoItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;

/**
 * @date 2 de out. de 2021
 * @author robson.costa
 */
public final class DocumentoFiscalUtil {
	
	/**
	 * Se a tag CRT (Codigo Regime Tributario) do xml for igual a 1 é do Simples Nacional
	 * @param docFiscal
	 * @return
	 */
	public static boolean isFornSimplesNacional(DocumentoFiscal docFiscal) {
		List<String> tagCRT = getTagConteudo(docFiscal.getXml(), "CRT", false);
		if (!tagCRT.isEmpty()) {
			return tagCRT.get(0).equals("1");
		}
		return false;
	}

	public static boolean isItemFromSimplesNacionalFor(DevolucaoItem item) {
		return Integer.toString(item.getIcmsCstFornecedor()).length() >= 3;
	}
    
}
