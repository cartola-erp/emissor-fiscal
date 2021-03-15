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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sendgrid.helpers.mail.Mail;

import net.cartola.emissorfiscal.documento.CompraDto;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.engine.EmailEngine;
import net.cartola.emissorfiscal.engine.EmailModel;
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
	
	private static final String FROM = "guia.gare.icms_st@autogeral.com.br";
	private static final String TO =  "robson.costa@autogeral.com.br";
	private String emailTitle;

	@Autowired
	private TributacaoEstadualGuiaService tribEstaGuiaService;
	
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private EmailEngine emailEngine;
	
//	private SpringTemplateEngine templateEngine;
	

	public CompraDto calculaGuiaGareIcmsStEntrada(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o Calculo Estadual das Guia para os DocumentoFiscal, de entrada");
		List<CalculoGareCompras> listCalculoIcmsStCompra = new ArrayList<>();
		Map<DocumentoFiscalItem, CalculoGareCompras> mapCalcGarePorItem = new HashMap<>();
		Optional<Loja> opLoja = lojaService.findByCnpj(documentoFiscal.getDestinatario().getCnpj());
		Estado estadoOrigem = estadoService.findBySigla(documentoFiscal.getEmitente().getEndereco().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(documentoFiscal.getDestinatario().getEndereco().getUf()).get();
		Set<ProdutoOrigem> produtoOrigens = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getOrigem).collect(Collectors.toSet());
		Set<Ncm> ncms = documentoFiscal.getItens().stream().map(DocumentoFiscalItem::getNcm).collect(Collectors.toSet());

		List<TributacaoEstadualGuia> listTribEstaGuiaGare = tribEstaGuiaService.findTribEstaGuiaByTipoGuiaUfOrigemUfDestinoProdutoOrigemOperENcms(TipoGuia.GARE_ICMS, estadoOrigem, estadoDestino, produtoOrigens, documentoFiscal.getOperacao(), ncms);
		
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
				CalculoGareCompras calcGareIcmsStEntr = calcularIcmsStParaOItemDaCompra(docItem, tribEstaGuia, documentoFiscal, opLoja);
				listCalculoIcmsStCompra.add(calcGareIcmsStEntr);
				mapCalcGarePorItem.put(docItem, calcGareIcmsStEntr);
				compraDto.setFoiCalculadoIcmsSt(true);
			}
		});
		
		compraDto.setMapCalcGarePorItem(mapCalcGarePorItem);
		compraDto.setTotalCalcGareCompra(totalizaCalcGareIcmsStCompras(listCalculoIcmsStCompra));
		return compraDto;
	}


	/**
	 * Irá fazer o calculo que tem que recolher de GARE para Cada Item do DocumetnoFiscal
	 * 
	 * @param docItem
	 * @param tribEstaGuia
	 * @param docFiscal
	 * @param opLoja
	 * @return
	 */
	private CalculoGareCompras calcularIcmsStParaOItemDaCompra(DocumentoFiscalItem docItem, TributacaoEstadualGuia tribEstaGuia, DocumentoFiscal docFiscal, Optional<Loja> opLoja) {
		CalculoGareCompras calcGareCompras = new CalculoGareCompras();
		StringBuilder infoCompl = new StringBuilder();
		infoCompl.append("Nota Fiscal Nº ").append(docFiscal.getNumero()).append(" CNPJ Nº ")
				.append(docFiscal.getEmitente().getCnpj())
				.append(" Conforme Portaria CAT 16 de 2008 e o art. 426-A § 4°do RICMS/2000");
		
		/** Fazendo calculo de ICMS ST, para o item */
		BigDecimal aliqIvaMva = BigDecimal.ONE.add(tribEstaGuia.getIcmsIva());
		BigDecimal baseCalcIcms = docItem.getQuantidade().multiply(docItem.getValorUnitario());
		BigDecimal baseCalcIcmsComFretIpi = baseCalcIcms.add(docItem.getValorFrete()).add(docItem.getIpiValor());
		
		BigDecimal baseCalcIcmsSt = baseCalcIcmsComFretIpi.subtract(docItem.getDesconto()).multiply(aliqIvaMva);
		BigDecimal valorIcmsSt = baseCalcIcmsSt.multiply(tribEstaGuia.getIcmsAliqInternaDestino());
		BigDecimal creditoIcmsProprio = baseCalcIcms.multiply(tribEstaGuia.getIcmsAliquota());
		BigDecimal valorIcmsStAReter = valorIcmsSt.subtract(creditoIcmsProprio);

		/** JUROS, MULTA e TOTAL (ainda tenho que calcular) **/
		BigDecimal juros = BigDecimal.ZERO;
	    BigDecimal multa = BigDecimal.ZERO;
	    BigDecimal valorTotal = valorIcmsSt.add(juros).add(multa);
		
		calcGareCompras.setTipoGuia(TipoGuia.GARE_ICMS);
		calcGareCompras.setCodigoReceita(632);
		opLoja.ifPresent(loja -> calcGareCompras.setLoja(loja));
		calcGareCompras.setDocFiscItem(docItem);
		calcGareCompras.setTribEstaGuia(tribEstaGuia);
		calcGareCompras.setInfoComplementar(infoCompl.toString());
		calcGareCompras.setDataVencimento(LocalDate.now());
		String mesAnoRef = LocalDate.now().getMonthValue() +"/"+ LocalDate.now().getYear();
		calcGareCompras.setMesAnoReferencia(mesAnoRef);
		calcGareCompras.setValorPrincipal(valorIcmsStAReter);
		calcGareCompras.setJuros(juros);
		calcGareCompras.setMulta(multa);
		calcGareCompras.setTotal(valorTotal);
		return calcGareCompras;
	}
	
	
	/**
	 * Irá calcular qual o total de uma lista de Calculos de Gare (Juros, Multa, valor total etc...)
	 * 
	 * @param listCalcIcmsStCompra
	 * @return
	 */
	private CalculoGareCompras totalizaCalcGareIcmsStCompras(List<CalculoGareCompras> listCalcIcmsStCompra) {
		 CalculoGareCompras totalCalcGareCompra = new CalculoGareCompras();
		 
		 if (!listCalcIcmsStCompra.isEmpty()) {
			 CalculoGareCompras objCalcGareCompra = listCalcIcmsStCompra.get(0);
			 BigDecimal totalValorPrincipal = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getValorPrincipal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 BigDecimal totalJuros = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getJuros()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 BigDecimal totalMulta = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getMulta()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 BigDecimal total = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 
			 totalCalcGareCompra.setTipoGuia(objCalcGareCompra.getTipoGuia());
			 totalCalcGareCompra.setCodigoReceita(objCalcGareCompra.getCodigoReceita());
			 totalCalcGareCompra.setLoja(objCalcGareCompra.getLoja());
			 totalCalcGareCompra.setInfoComplementar(objCalcGareCompra.getInfoComplementar());
			 totalCalcGareCompra.setDataVencimento(objCalcGareCompra.getDataVencimento());
			 totalCalcGareCompra.setMesAnoReferencia(objCalcGareCompra.getMesAnoReferencia());
			 
			 totalCalcGareCompra.setValorPrincipal(totalValorPrincipal);
			 totalCalcGareCompra.setJuros(totalJuros);
			 totalCalcGareCompra.setMulta(totalMulta);
			 totalCalcGareCompra.setTotal(total);
		 }
		
		 return totalCalcGareCompra;
	}

	public void enviarEmail(CompraDto compraDto) {
		String htmlEmail = this.gerarEmail(compraDto);
		Mail emailSendGrid = this.getMailSendgridWithContent(htmlEmail);
		emailEngine.enviarEmail(emailSendGrid);
	}

	/**
	 * Irá gerar o HTML do  Email que será enviado
	 * @param compraDto
	 * @return
	 */
	private String gerarEmail(CompraDto compraDto) {
		StringBuilder sbEmailTitle = new StringBuilder();
		DocumentoFiscal docFiscal = compraDto.getDocFiscal();
		sbEmailTitle.append("Calc. ICMS ST Compra: " +docFiscal.getNfeChaveAcesso()).append(" DE: ").append(docFiscal.getEmitente().getEndereco().getUf());
		emailTitle = sbEmailTitle.toString();
		
		Context context  = new Context();
		
		context.setVariable("docFisc", docFiscal);
		context.setVariable("listCalcGare", compraDto.getMapCalcGarePorItem().values());
		
		CalculoGareCompras totalCalcGareCompra = compraDto.getTotalCalcGareCompra();
		context.setVariable("loja", totalCalcGareCompra.getLoja());
		context.setVariable("totalCalc", totalCalcGareCompra);
//		context.setVariable("infoCompl", totalCalcGareCompra.getInfoComplementar());

		String html = templateEngine.process("tributacao-estadual-guia/email-calculo-icms-st-entrada", context);

		return html;
	}
	
	
	/**
	 * 
	 * @param contentHtml
	 * @return {@link Mail}
	 */
	private Mail getMailSendgridWithContent(String contentHtml) {
		EmailModel emailSendGrid = new EmailModel();
		Mail mail = emailSendGrid.withFrom(FROM).withTo(TO).withTitle(emailTitle).withContentHtml(contentHtml).build();
		return mail;
	}
	
	

	
	
}
