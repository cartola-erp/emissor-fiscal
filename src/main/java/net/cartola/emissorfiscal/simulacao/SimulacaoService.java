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
	
	
	public List<String> getMsgResultadoCalculo(DocumentoFiscal documentoFiscal ) {
		List<String> listResult = new ArrayList<String>();
		DocumentoFiscalItem item = documentoFiscal.getItens().get(0);
		
		listResult.add("Operação: " +documentoFiscal.getOperacao().getDescricao());
		listResult.add("Uf Origem: " +documentoFiscal.getEmitente().getUf() );
		listResult.add("Uf Destino: " +documentoFiscal.getDestinatario().getUf() );
		listResult.add("QTD ITEM: " + item.getQuantidade() );
		listResult.add("VALOR DO ITEM: " + item.getValorUnitario() );
		listResult.add("FINALIDADE: " +item.getFinalidade() );
		listResult.add("NCM: " +item.getNcm().getNumero() );
		listResult.add("REGIME TRIBUTÁRIO: " +documentoFiscal.getDestinatario().getRegimeTributario() );
		
		listResult.add(" ===================================== VALORES DOCUMENTO FISCAL ===================================== ");
		listResult.add("ICMS VALOR (NFE): " +documentoFiscal.getIcmsValor());
		listResult.add("PIS BASE (NFE): " +documentoFiscal.getPisBase());
		listResult.add("PIS VALOR (NFE): " +documentoFiscal.getPisValor());
		listResult.add("COFINS BASE (NFE): " +documentoFiscal.getCofinsBase());
		listResult.add("COFINS VALOR (NFE): " +documentoFiscal.getCofinsValor());
		listResult.add("IPI BASE (NFE): " +documentoFiscal.getIpiBase());
		listResult.add("IPI VALOR (NFE): " +documentoFiscal.getIpiValor());
		listResult.add(" =========================================== VALORES ITEM =========================================== ");
		listResult.add("COFINS ALIQ. (ITEM): " +item.getCofinsAliquota() );
		listResult.add("COFINS BASE (ITEM): " +item.getCofinsBase() );
		listResult.add("COFINS CST (ITEM): " +item.getCofinsCst() );
		listResult.add("IPI ALIQ. (ITEM): " +item.getIpiAliquota() );
		listResult.add("IPI BASE (ITEM): " +item.getIpiBase() );
		listResult.add("IPI CST (ITEM): " +item.getIpiCst() );
		listResult.add("PIS ALIQ. (ITEM): " +item.getPisAliquota() );
		listResult.add("PIS BASE (ITEM): " +item.getPisBase() );
		listResult.add("PIS CST (ITEM): " +item.getPisCst() );
		
		return listResult;
	}
	
	

	
}

