package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.TipoServico;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC500;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC590;

/**
 * 18/09/2020
 * 
 * @author robson.costa
 */
@Service
class RegC500Service implements MontaGrupoDeRegistroList<RegC500, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC500Service.class.getName());

	@Override
	public List<RegC500> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro C500");
		List<RegC500> listRegC500 = new ArrayList<>();
		
		Loja lojaSped = movimentosIcmsIpi.getLoja();
		List<DocumentoFiscal> listDocumentoFiscalServico = movimentosIcmsIpi.getListDocumentoFiscalServico();

		listDocumentoFiscalServico.stream().forEach(servico -> {
			if (servico.getTipoServico().equals(TipoServico.ENERGIA)) {
				RegC500 regC500 = new RegC500(servico, lojaSped);
				regC500.setRegC590(montarGrupoRegC590(servico, movimentosIcmsIpi)); 
				listRegC500.add(regC500);
			}
		});
		
		LOG.log(Level.INFO, "Registro C500, terminado. REG C500: ");
		return listRegC500;
	}

	/**
	 * 
	 * Preenchimento do REGISTRO ANALÍTICO C590
	 * 
	 * @param servico
	 * @param movimentosIcmsIpi 
	 * @return
	 */
	private List<RegC590> montarGrupoRegC590(DocumentoFiscal servico, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<RegC590> listRegC590 = new ArrayList<>();
		RegC590 regC590 = new RegC590();
		
		regC590.setCstIcms("090");
		regC590.setCfop(1253);					//CFOP 1253 - Compra de energia elétrica por estabelecimento comercial
		regC590.setAliqIcms(BigDecimal.ZERO);
		regC590.setVlOpr(servico.getValorTotalDocumento());
		regC590.setVlBcIcms(BigDecimal.ZERO);
		regC590.setVlIcms(BigDecimal.ZERO);
		regC590.setVlBcIcmsSt(BigDecimal.ZERO);
		regC590.setVlIcmsSt(BigDecimal.ZERO);
		regC590.setVlRedBc(BigDecimal.ZERO);
		regC590.setCodObs(null);
		
		listRegC590.add(regC590);
		movimentosIcmsIpi.addRegistroAnalitico(listRegC590, servico);
		return listRegC590;
	}

}
