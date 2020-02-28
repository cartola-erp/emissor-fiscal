package net.cartola.emissorfiscal.simulacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;

/**
 * 24 de fev de 2020
 * @author robson.costa
 */

@Service
public class SimulacaoService {
	
	
	public List<String> getListMsgResultadoCalculo(DocumentoFiscal documentoFiscal ) {
		List<String> listResult = new ArrayList<String>();
		DocumentoFiscalItem item = documentoFiscal.getItens().get(0);
		
		listResult.add(" =========================================== DADOS INFORMADOS =========================================== ");
		listResult.add("Operação: " +documentoFiscal.getOperacao().getDescricao());
		listResult.add("Uf Origem: " +documentoFiscal.getEmitente().getUf() );
		listResult.add("Uf Destino: " +documentoFiscal.getDestinatario().getUf() );
		listResult.add("QTD ITEM: " + item.getQuantidade() );
		listResult.add("VALOR DO ITEM: " + item.getValorUnitario() );
		listResult.add("FINALIDADE: " +item.getFinalidade() );
		listResult.add("NCM: " +item.getNcm().getNumero() + " | EXCEÇÃO: " +item.getNcm().getExcecao());
		listResult.add("REGIME TRIBUTÁRIO EMITENTE: " +documentoFiscal.getDestinatario().getRegimeTributario());
		listResult.add(" ===================================== VALORES DOCUMENTO FISCAL ===================================== ");
		listResult.add("ICMS BASE (NFE): " +documentoFiscal.getIcmsBase().toPlainString());
		listResult.add("ICMS VALOR (NFE): " +documentoFiscal.getIcmsValor().toPlainString());
//		listResult.add("ICMS ALIQ.: " +documentoFiscal.getIcmsValor());
		listResult.add("PIS BASE (NFE): " +documentoFiscal.getPisBase().toPlainString());
		listResult.add("PIS VALOR (NFE): " +documentoFiscal.getPisValor().toPlainString());
		listResult.add("COFINS BASE (NFE): " +documentoFiscal.getCofinsBase().toPlainString());
		listResult.add("COFINS VALOR (NFE): " +documentoFiscal.getCofinsValor().toPlainString());
		listResult.add("IPI BASE (NFE): " +documentoFiscal.getIpiBase());
		listResult.add("IPI VALOR (NFE): " +documentoFiscal.getIpiValor());
		listResult.add(" =========================================== VALORES ITEM =========================================== ");
		listResult.add(" ----------------------------------- ICMS ----------------------------------- ");
		listResult.add("ICMS BASE (ITEM): " +item.getIcmsBase().toPlainString());
		listResult.add("ICMS VALOR (ITEM): " +item.getIcmsValor().toPlainString());
		listResult.add("ICMS ALIQ.: (ITEM): " +item.getIcmsAliquota().toPlainString());
		listResult.add("ICMS CST.: (ITEM): " +item.getIcmsCst());
		listResult.add("ICMS CEST.: (ITEM): " +item.getIcmsCest());
		listResult.add(" ----------------------------------- PIS ----------------------------------- ");
		listResult.add("PIS BASE (ITEM): " +item.getPisBase().toPlainString());
		listResult.add("PIS VALOR (ITEM): " +item.getPisValor().toPlainString());
		listResult.add("PIS ALIQ. (ITEM): " +item.getPisAliquota().toPlainString());
		listResult.add("PIS CST (ITEM): " +item.getPisCst());
		listResult.add(" ----------------------------------- COFINS ----------------------------------- ");
		listResult.add("COFINS BASE (ITEM): " +item.getCofinsBase().toPlainString());
		listResult.add("COFINS VALOR (ITEM): " +item.getCofinsValor().toPlainString());
		listResult.add("COFINS ALIQ. (ITEM): " +item.getCofinsAliquota().toPlainString());
		listResult.add("COFINS CST (ITEM): " +item.getCofinsCst());
		listResult.add(" ----------------------------------- IPI ----------------------------------- ");
		listResult.add("IPI BASE (ITEM): " +item.getIpiBase());
		listResult.add("IPI VALOR (ITEM): " +item.getIpiValor());
		listResult.add("IPI ALIQ. (ITEM): " +item.getIpiAliquota());
		listResult.add("IPI CST (ITEM): " +item.getIpiCst());

		
		if (listResult.isEmpty()) {
			listResult.add("Está faltando o cadastro de alguma tributação para esse NCM escolhido");
		}
		return listResult;
	}
	
	/**
	 * Irá retornar cada resultado do calculo fiscal, em uma linha diferente
	 * @param documentoFiscal
	 * @return 
	 */
	public StringBuffer getStrbuffMsgResultadoCalculo(DocumentoFiscal documentoFiscal) {
		final StringBuffer sb = new StringBuffer();
		List<String> listMsgResultado = getListMsgResultadoCalculo(documentoFiscal);
		
		listMsgResultado.forEach(msg -> {
			sb.append(msg).append("\n");
		});
		return sb;
	}
	

	
}

