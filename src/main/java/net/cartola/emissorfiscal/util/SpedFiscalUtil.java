package net.cartola.emissorfiscal.util;

import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.documento.NFeStatus;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.sped.fiscal.enums.IndicadorDoEmitente;
import net.cartola.emissorfiscal.sped.fiscal.enums.ModeloDocumentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.enums.SituacaoDoDocumento;

/**
 * Classe Utilitária para ser usada no SPED ICMS IPI 
 * 
 * @author robson.costa
 *
 */
public final class SpedFiscalUtil {
	
	private static final Logger LOG = Logger.getLogger(SpedFiscalUtil.class.getName());


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
	
	/**
	 * Usado no REG C100;
	 * 
	 * Obtem o código do participante para o <b>destinatário</b> se for operacao de <b>Entrada</b>
	 * <b>Saída</b> codigo particapante do Emitente
	 * 
	 * Caso o Modelo documento seja == 65 (NFC-e), retorna String <b>Vazia</b>
	 * @param docFisc
	 * @return
	 */
	public static String getCodPart(DocumentoFiscal docFisc) {
		LOG.log(Level.INFO, "Obtendo o CODIGO DO PARTICIPANTE para o DocumentoFiscal {0} " ,docFisc);
		if (docFisc.getModelo() != ModeloDocumentoFiscal._65) {
			if (docFisc.getTipoOperacao() == IndicadorDeOperacao.ENTRADA) {
				return SpedFiscalUtil.getCodPart(docFisc.getDestinatario());
			}
			return SpedFiscalUtil.getCodPart(docFisc.getEmitente());
		}
		return "";
	}

	/**
	 * 
	 * @param docFisc
	 * @return
	 */
	public static SituacaoDoDocumento getCodSituacao(DocumentoFiscal docFisc) {
		// TODO Colocar regra para quando for um DocumentoFiscal "COMPLEMENTAR"
		NFeStatus nfeStatus = docFisc.getStatus();
			if (nfeStatus.getCodigo().length() == 2) {
				int nfeStatusCodigo = Integer.parseInt(nfeStatus.getCodigo());
				SituacaoDoDocumento sitDoc = SituacaoDoDocumento.values()[nfeStatusCodigo];
				return sitDoc;
			}
		return null;
	}
	
	/**
	 * Usado no REG C100;
	 * 
	 * Irá retornar se a emissão do DocumentoFiscal é:
	 * 	- Emissão Própria		| (Entradas e Saídas)
	 * 	- Emissão de Terceiros  | (Somente Entradas)
	 * 
	 * @param docFisc
	 * @param lojaSped
	 * @return
	 */
	public static IndicadorDoEmitente getIndicadorEmitente(DocumentoFiscal docFisc, Loja lojaSped) {
		LOG.log(Level.INFO, "Obtendo o indicador do emitente para o DocumentoFiscal {0} " ,docFisc);

		Pessoa emitente = docFisc.getEmitente();
		if (emitente.getCnpj().equals(lojaSped.getCnpj())) {
			return IndicadorDoEmitente.EMISSAO_PROPRIA;
		}
		return IndicadorDoEmitente.TERCEIROS;
	}
	
}
