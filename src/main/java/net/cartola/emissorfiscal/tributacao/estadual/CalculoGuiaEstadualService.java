package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.CompraDto;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.loja.LojaService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.tributacao.CalculoGareCompras;

/**
 * GARE
 * 
 * @date 8 de mar. de 2021
 * @author robson.costa
 */
@Service
//public class CalculoGuiaEstadual implements CalculoFiscal {
public class CalculoGuiaEstadualService {
	
	private static final Logger LOG = Logger.getLogger(CalculoGuiaEstadualService.class.getName());

	
	@Autowired
	private TributacaoEstadualGuiaService tribEstaGuiaService;
	
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private LojaService lojaService;

//	@Override
//	public void calculaImposto(DocumentoFiscal documentoFiscal) {
		// TODO Auto-generated method stub
//	}
	
	public CompraDto calculaGuiaGareIcmsStEntrada(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o Calculo Estadual das Guia para os DocumentoFiscal, de entrada");
		List<CalculoGareCompras> listCalculoIcmsStCompra = new ArrayList<>();
		Map<DocumentoFiscalItem, CalculoGareCompras> mapCalcGarePorItem = new HashMap<>();
		Estado estadoOrigem = estadoService.findBySigla(documentoFiscal.getEmitente().getEndereco().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(documentoFiscal.getDestinatario().getEndereco().getUf()).get();
		Set<ProdutoOrigem> produtoOrigens = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getOrigem).collect(Collectors.toSet());
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());

		List<TributacaoEstadualGuia> listTribEstaGuiaGare = tribEstaGuiaService.findTribEstaGuiaByTipoGuiaUfOrigemUfDestinoProdutoOrigemOperENcms(TipoGuia.GARE_ICMS, estadoOrigem, estadoDestino, produtoOrigens, documentoFiscal.getOperacao(), ncms);
		
//		Map<Ncm, List<TributacaoEstadualGuia>> mapTribEstaGuiaPorNcm = listTribEstaGuiaGare.stream().collect(Collectors.groupingBy(TributacaoEstadualGuia::getNcm));
//		Map<Ncm, Map<ProdutoOrigem, List<TributacaoEstadualGuia>>> mapTribEstaGuiaPorNcmAndProdutoOrigem = listTribEstaGuiaGare.stream()
//				.collect(Collectors.groupingBy(TributacaoEstadualGuia::getNcm, Collectors.groupingBy(TributacaoEstadualGuia::getProdutoOrigem)));

		Map<Ncm, Map<ProdutoOrigem, TributacaoEstadualGuia>> mapTribEstaGuiaPorNcmAndProdutoOrigem = listTribEstaGuiaGare
				.stream()
				.collect(Collectors.groupingBy(TributacaoEstadualGuia::getNcm,
						Collectors.toMap(TributacaoEstadualGuia::getProdutoOrigem,
								(TributacaoEstadualGuia tribEstaGuia) -> tribEstaGuia)));
		
		CompraDto compraDto = new CompraDto();
		compraDto.setDocFiscal(documentoFiscal);
		
		documentoFiscal.getItens().stream().forEach(docItem -> {
			boolean existeIcmStParaNcm = mapTribEstaGuiaPorNcmAndProdutoOrigem.containsKey(docItem.getNcm());
			if (existeIcmStParaNcm) {
				TributacaoEstadualGuia tribEstaGuia = mapTribEstaGuiaPorNcmAndProdutoOrigem.get(docItem.getNcm()).get(docItem.getOrigem());
				CalculoGareCompras calcGareIcmsStEntr = calcularGareCompras(docItem, tribEstaGuia, documentoFiscal);
				listCalculoIcmsStCompra.add(calcGareIcmsStEntr);
				mapCalcGarePorItem.put(docItem, calcGareIcmsStEntr);
			}
		});
		
		compraDto.setMapCalcGarePorItem(mapCalcGarePorItem);
		compraDto.setTotalCalcGareCompras(totalizaCalcGareIcmsStCompras(listCalculoIcmsStCompra));
		
		return compraDto;
	}


	private CalculoGareCompras calcularGareCompras(DocumentoFiscalItem docItem, TributacaoEstadualGuia tribEstaGuia, DocumentoFiscal docFiscal) {
		CalculoGareCompras calcGareCompras = new CalculoGareCompras();
		StringBuilder infoCompl = new StringBuilder();
		Optional<Loja> opLoja = lojaService.findByCnpj(docFiscal.getDestinatario().getCnpj());
		
//		BigDecimal valorTotal = docItem.getQuantidade().multiply(docItem.getValorUnitario())
//				.add(docItem.getValo);
		
		/**
		 * Aqui deve ser calculado os valores da planilha do  excel que a Gabi me passou
		 * 
		 */
		
		
		calcGareCompras.setTipoGuia(TipoGuia.GARE_ICMS);
		calcGareCompras.setCodigoReceita(632);
		opLoja.ifPresent(loja -> calcGareCompras.setLoja(loja));
		
		infoCompl.append("Nota Fiscal Nº ").append(docFiscal.getNumero()).append("CNPJ Nº ")
			.append(docFiscal.getEmitente().getCnpj()).append(" Conforme Portaria CAT 16 de 2008 e o art. 426-A § 4°do RICMS/2000");
		calcGareCompras.setInfoComplementar(infoCompl.toString());
		calcGareCompras.setDataVencimento(LocalDate.now());
//		String mesAnoRef = Integer.toString(LocalDate.now().getMonthValue()) +"/"+ LocalDate.now().getYear();
		String mesAnoRef = LocalDate.now().getMonthValue() +"/"+ LocalDate.now().getYear();
		calcGareCompras.setMesAnoReferencia(mesAnoRef);
		
		
		
		return null;
	}
	
	
	private CalculoGareCompras totalizaCalcGareIcmsStCompras(List<CalculoGareCompras> listCalculoIcmsStCompra) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
