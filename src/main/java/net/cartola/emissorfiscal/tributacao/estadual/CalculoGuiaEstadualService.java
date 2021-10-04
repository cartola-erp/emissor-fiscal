package net.cartola.emissorfiscal.tributacao.estadual;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static net.cartola.emissorfiscal.util.DocumentoFiscalUtil.isFornSimplesNacional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sendgrid.helpers.mail.Mail;

import net.cartola.emissorfiscal.documento.CompraDto;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.engine.EmailEngine;
import net.cartola.emissorfiscal.engine.EmailModel;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.loja.LojaService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.properties.GuiaGareEmailProperties;
import net.cartola.emissorfiscal.tributacao.CalculoGareCompra;

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
	
	private String emailTitle;

	@Autowired
	private TributacaoEstadualGuiaService tribEstaGuiaService;
	
	@Autowired
	private GuiaGareEmailProperties emailPropertie;
	
	@Autowired
	private EstadoService estadoService;

	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private EmailEngine emailEngine;
	
	@Autowired
	private DocumentoFiscalItemService docFiscItemService;
	
//	private SpringTemplateEngine templateEngine;
	

	public CompraDto calculaGuiaGareIcmsStEntrada(DocumentoFiscal documentoFiscal) {
		LOG.log(Level.INFO, "Fazendo o Calculo Estadual das Guia para os DocumentoFiscal, de entrada");
		List<CalculoGareCompra> listCalculoIcmsStCompra = new ArrayList<>();
		List<CalculoGareCompra> listCalcGareItem = new ArrayList<>();
		Optional<Loja> opLoja = lojaService.findByCnpj(documentoFiscal.getDestinatario().getCnpj());
		Estado estadoOrigem = estadoService.findBySigla(documentoFiscal.getEmitente().getEndereco().getUf()).get();
		Estado estadoDestino = estadoService.findBySigla(documentoFiscal.getDestinatario().getEndereco().getUf()).get();
		Set<Ncm> ncms = documentoFiscal.getNcms();
				
		List<TributacaoEstadualGuia> listTribEstaGuiaGare = tribEstaGuiaService.findTribEstaGuiaByTipoGuiaUfOrigemUfDestinoOperENcms(TipoGuia.GARE_ICMS, estadoOrigem, estadoDestino, documentoFiscal.getOperacao(), ncms);
		
		Map<Ncm, Map<Boolean, TributacaoEstadualGuia>> mapTribEstaGuiaPorNcmAndOrigem = listTribEstaGuiaGare.stream()
				.collect(groupingBy(TributacaoEstadualGuia::getNcm, 
						toMap(TributacaoEstadualGuia::isProdutoImportado, (TributacaoEstadualGuia tribEstaGuia) -> tribEstaGuia)));
		
		CompraDto compraDto = new CompraDto();
		compraDto.setDocFiscal(documentoFiscal);
		
		documentoFiscal.getItens().stream().forEach(docItem -> {
			boolean existeIcmStParaNcm = mapTribEstaGuiaPorNcmAndOrigem.containsKey(docItem.getNcm());
			if (existeIcmStParaNcm) {
				boolean isProdutoImportado = docFiscItemService.verificaSeEhImportado(docItem);
				TributacaoEstadualGuia tribEstaGuia = mapTribEstaGuiaPorNcmAndOrigem.get(docItem.getNcm()).get(isProdutoImportado);
				CalculoGareCompra calcGareIcmsStEntr = calcularIcmsStParaOItemDaCompra(docItem, tribEstaGuia, documentoFiscal, opLoja);
				listCalculoIcmsStCompra.add(calcGareIcmsStEntr);
//				mapCalcGarePorItem.put(docItem, calcGareIcmsStEntr);
				listCalcGareItem.add(calcGareIcmsStEntr);
				compraDto.setFoiCalculadoIcmsSt(true);
			}
		});
		
		listCalcGareItem.sort(Comparator.comparing(CalculoGareCompra::getNumItem));
		compraDto.setListCalcGareItem(listCalcGareItem);
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
	private CalculoGareCompra calcularIcmsStParaOItemDaCompra(DocumentoFiscalItem docItem, TributacaoEstadualGuia tribEstaGuia, DocumentoFiscal docFiscal, Optional<Loja> opLoja) {
		CalculoGareCompra calcGareCompra = new CalculoGareCompra();
		StringBuilder infoCompl = new StringBuilder();
		infoCompl.append("Nota Fiscal Nº ").append(docFiscal.getNumeroNota()).append(" CNPJ Nº ")
				.append(docFiscal.getEmitente().getCnpj())
				.append(" Conforme Portaria CAT 16 de 2008 e o art. 426-A § 4°do RICMS/2000");
		
		final BigDecimal icmsAliquota =  isFornSimplesNacional(docFiscal) ? docItem.getIcmsAliquota() : tribEstaGuia.getIcmsAliquota();
		
		/** Fazendo calculo de ICMS ST, para o item */
		final BigDecimal aliqIvaMva = BigDecimal.ONE.add(tribEstaGuia.getIcmsIva());
		// Como as entradas em si não é calculada aqui, e sim no ERP, a base é a msm que recebeu de lá previamente
		final BigDecimal baseCalcIcms = docItem.getIcmsBase();
		final BigDecimal baseCalcIcmsComFretIpi = baseCalcIcms.add(docItem.getIpiValor());
		// Estava da forma abaixo, porém o ICMS base do erp já vem com o frete, por isso acima no BC ICMS ST, adicionei apenas o IPI (ele nao vai na BC ICMS proprio)
//		BigDecimal baseCalcIcmsComFretIpi = baseCalcIcms.add(docItem.getValorFrete()).add(docItem.getIpiValor());
		
		final BigDecimal baseCalcIcmsSt = baseCalcIcmsComFretIpi.subtract(docItem.getDesconto()).multiply(aliqIvaMva);
		final BigDecimal valorIcmsSt = baseCalcIcmsSt.multiply(tribEstaGuia.getIcmsAliqInternaDestino());
		final BigDecimal creditoIcmsProprio = baseCalcIcms.multiply(icmsAliquota);
		final BigDecimal valorIcmsStAReter = valorIcmsSt.subtract(creditoIcmsProprio);

		/** JUROS, MULTA e TOTAL (ainda tenho que calcular) **/
		BigDecimal juros = BigDecimal.ZERO;
	    BigDecimal multa = BigDecimal.ZERO;
	    BigDecimal valorTotal = valorIcmsStAReter.add(juros).add(multa);
		
	    calcGareCompra.setNumNota(docFiscal.getNumeroNota());
	    calcGareCompra.setNumCompra(docFiscal.getDocumento());
	    calcGareCompra.setNumItem(docItem.getItem());
	    calcGareCompra.setCodigoX(docItem.getCodigoX());
	    calcGareCompra.setCodigoSequencia(docItem.getCodigoSequencia());
	    calcGareCompra.setNcmEntrada(Integer.parseInt(docItem.getClasseFiscal()));
	    
		calcGareCompra.setTipoGuia(TipoGuia.GARE_ICMS);
		calcGareCompra.setCodigoReceita(632);
		opLoja.ifPresent(loja -> {
			calcGareCompra.setLoja(loja);
			calcGareCompra.setLojaId(loja.getId());
		});
		calcGareCompra.setDocFiscItem(docItem);
		calcGareCompra.setTribEstaGuia(tribEstaGuia);
		calcGareCompra.setInfoComplementar(infoCompl.toString());
		calcGareCompra.setDataVencimento(LocalDate.now());
		String mesAnoRef = LocalDate.now().getMonthValue() +"/"+ LocalDate.now().getYear();
		calcGareCompra.setMesAnoReferencia(mesAnoRef);
		calcGareCompra.setValorPrincipal(valorIcmsStAReter);
		calcGareCompra.setJuros(juros);
		calcGareCompra.setMulta(multa);
		calcGareCompra.setTotal(valorTotal);
		return calcGareCompra;
	}
	


	/**
	 * Se a CST for do SN (3 dígitos, irá calcular a BC ICMS). <\br>
	 * Caso não seja será retornado para a base de calculo do ICMS que veio do ERP
	 * 
	 * @param docFiscal
	 * @param docItem 
	 * @return
	 */
	private BigDecimal getIcmsBase(DocumentoFiscal docFiscal, DocumentoFiscalItem docItem) {
		if (isFornSimplesNacional(docFiscal)) {
			return docItem.getValorUnitario().multiply(docItem.getQuantidade()).add(docItem.getValorFrete());
		}
		return docItem.getIcmsBase();
	}


	/**
	 * Irá calcular qual o total de uma lista de Calculos de Gare (Juros, Multa, valor total etc...)
	 * 
	 * @param listCalcIcmsStCompra
	 * @return
	 */
	private CalculoGareCompra totalizaCalcGareIcmsStCompras(List<CalculoGareCompra> listCalcIcmsStCompra) {
		 CalculoGareCompra totalCalcGareCompra = new CalculoGareCompra();
		 
		 if (!listCalcIcmsStCompra.isEmpty()) {
			 CalculoGareCompra objCalcGareCompra = listCalcIcmsStCompra.get(0);
			 BigDecimal totalValorPrincipal = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getValorPrincipal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 BigDecimal totalJuros = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getJuros()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 BigDecimal totalMulta = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getMulta()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 BigDecimal total = listCalcIcmsStCompra.stream().map(calcIcmsStCompra -> calcIcmsStCompra.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			 
			 totalCalcGareCompra.setNumNota(objCalcGareCompra.getNumNota());
			 totalCalcGareCompra.setNumCompra(objCalcGareCompra.getNumCompra());
			 totalCalcGareCompra.setLojaId(objCalcGareCompra.getLojaId());
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
		context.setVariable("listCalcGare", compraDto.getListCalcGareItem());
		
		CalculoGareCompra totalCalcGareCompra = compraDto.getTotalCalcGareCompra();
		context.setVariable("loja", totalCalcGareCompra.getLoja());
		context.setVariable("totalCalc", totalCalcGareCompra);

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
		Mail mail = emailSendGrid.withFrom(emailPropertie.getFrom()).withTo(emailPropertie.getTo()).withTitle(emailTitle).withContentHtml(contentHtml).build();
		return mail;
	}
	
	

	
	
}
