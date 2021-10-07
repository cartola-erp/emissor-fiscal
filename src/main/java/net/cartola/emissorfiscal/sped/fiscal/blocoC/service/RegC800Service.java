package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCodSituacao;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getNumeroCfe;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getNumeroSerieSat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.NFeStatus;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.properties.SpedFiscalProperties;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC800;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
class RegC800Service implements MontaGrupoDeRegistroList<RegC800, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegC800Service.class.getName());

	@Autowired
	private RegC850Service  regC850Service;
	
	@Autowired
	private SpedFiscalProperties spedFiscPropertie;
	
	@Override
	public List<RegC800> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C800");
		Loja lojaSped = movimentosIcmsIpi.getLoja();
		List<DocumentoFiscal> listSatsEmitidos = movimentosIcmsIpi.getListSatsEmitidos();
		List<RegC800> listRegC800 = new ArrayList<>(listSatsEmitidos.size());
		
		listSatsEmitidos.stream().forEach(satEmititdo -> {
			listRegC800.add(gerarRegistroC800(satEmititdo, lojaSped, movimentosIcmsIpi));
		});
		return listRegC800;
	}


	private RegC800 gerarRegistroC800(DocumentoFiscal satEmititdo, Loja lojaSped, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		if (isSatCancelado(satEmititdo)) {
			return preencherSatCancelado(satEmititdo, lojaSped);
		}
		RegC800 regC800 = new RegC800(satEmititdo, this.spedFiscPropertie);
		/// VERIFICAR como preencher o REG C815
		regC800.setRegC850(regC850Service.montarGrupoRegC850(satEmititdo, movimentosIcmsIpi));
		return regC800;
	}


	private boolean isSatCancelado(DocumentoFiscal satEmititdo) {
		List<NFeStatus> satsNaoAutorizados = Arrays.asList(NFeStatus.CANCELADA, NFeStatus.DENEGADA, NFeStatus.INUTILIZADA);
		return satsNaoAutorizados.contains(satEmititdo.getStatus());
	}
	
	private RegC800 preencherSatCancelado(DocumentoFiscal satEmititdo, Loja lojaSped) {
		RegC800 regC800 = new RegC800();
		regC800.setCodMod(satEmititdo.getModelo());
		regC800.setCodSit(getCodSituacao(satEmititdo));
		regC800.setNumCfe(getNumeroCfe(satEmititdo));
		regC800.setNrSat(getNumeroSerieSat(satEmititdo));
		regC800.setChvCfe(satEmititdo.getNfeChaveAcesso());
		return regC800;
	}


}
