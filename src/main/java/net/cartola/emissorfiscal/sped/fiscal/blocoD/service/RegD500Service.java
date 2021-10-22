package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import static net.cartola.emissorfiscal.documento.TipoServico.INTERNET;
import static net.cartola.emissorfiscal.documento.TipoServico.TELEFONE_FIXO_MOVEL;

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
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD500;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD590;

/**
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegD500Service implements MontaGrupoDeRegistroList<RegD500, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegD500Service.class.getName());

	
	@Override
	public List<RegD500> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro D500");
		List<RegD500> listRegD500 = new ArrayList<>();
		
		Loja lojaSped = movimentosIcmsIpi.getLoja();
		List<DocumentoFiscal> listDocumentoFiscalServico = movimentosIcmsIpi.getListDocumentoFiscalServico();

		listDocumentoFiscalServico.stream().forEach(servico -> {
			TipoServico tipoServico = servico.getTipoServico();
			if (tipoServico.equals(INTERNET) || tipoServico.equals(TELEFONE_FIXO_MOVEL)) {
				RegD500 regD500 = new RegD500(servico, lojaSped, movimentosIcmsIpi.getMapLojasPorCnpj());
				regD500.setRegD590(montarGrupoRegD590(servico, movimentosIcmsIpi));
				listRegD500.add(regD500);
 			}
		});
		
		LOG.log(Level.INFO, "Registro D500, terminado. REG D500: ");
		return listRegD500;
	}
	
	/**
	 * Preenchimento do REGISTRO ANALÍTICO D590
	 * 
	 * @param servico
	 * @param movimentosIcmsIpi 
	 * @return
	 */
	private List<RegD590> montarGrupoRegD590(DocumentoFiscal servico, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		List<RegD590> listRegD590 = new ArrayList<>();
		RegD590 regD590 = new RegD590();
		
		regD590.setCstIcms("090");
		regD590.setCfop(1303);					//CFOP 1303 - Aquisição de serviço de comunicação por estabelecimento comercial
		regD590.setAliqIcms(BigDecimal.ZERO);
		regD590.setVlOpr(servico.getValorTotalDocumento());
		regD590.setVlBcIcms(BigDecimal.ZERO);
		regD590.setVlIcms(BigDecimal.ZERO);
		regD590.setVlBcIcmsUf(BigDecimal.ZERO);
		regD590.setVlIcmsUf(BigDecimal.ZERO);
		regD590.setVlRedBc(BigDecimal.ZERO);
		regD590.setCodObs(null);

		listRegD590.add(regD590);
		movimentosIcmsIpi.addRegistroAnalitico(listRegD590, servico);
		return listRegD590;
	}

}
