package net.cartola.emissorfiscal.sped.fiscal.blocoE.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.RegE300;

/**
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegE300Service implements MontaGrupoDeRegistroList<RegE300, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegE300Service.class.getName());

	@Autowired
	private RegE310Service regE310Service;
	
	
	@Override
	public List<RegE300> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro E300");
		List<RegE300> listE300 = new ArrayList<>();
		Map<EstadoSigla, List<DocumentoFiscal>> mapDocFiscInterestadualComDifalPorUf = movimentosIcmsIpi
				.getListDocFiscInterestadualComDifal().stream()
				.collect(Collectors.groupingBy((docFisc) -> docFisc.getEmitente().getEndereco().getUf()));
		
		
		for (EstadoSigla uf : mapDocFiscInterestadualComDifalPorUf.keySet()) {
			List<DocumentoFiscal> listDocFiscNaUf = mapDocFiscInterestadualComDifalPorUf.get(uf);
			RegE300 regE300 = new RegE300();
			regE300.setUf(uf.name());
			regE300.setDtIni(movimentosIcmsIpi.getDataInicio());
			regE300.setDtFin(movimentosIcmsIpi.getDataFim());
			regE300.setRegE310(regE310Service.montarGrupoRegE310(movimentosIcmsIpi, listDocFiscNaUf));
			listE300.add(regE300);
		}
		LOG.log(Level.INFO, "Registro E300, terminado. REG E300: ");
		return listE300;
	}

	// ================================= REFERENTE AO REG E310 =================================
	
	
	
}
