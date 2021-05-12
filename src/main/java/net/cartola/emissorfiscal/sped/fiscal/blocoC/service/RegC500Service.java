package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

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
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o Registro C500");
		List<RegC500> listRegC500 = new ArrayList<>();
		
		Loja lojaSped = movimentosIcmsIpi.getLoja();
		List<DocumentoFiscal> listDocumentoFiscalServico = movimentosIcmsIpi.getListDocumentoFiscalServico();

		listDocumentoFiscalServico.stream().forEach(servico -> {
			if (servico.getTipoServico().equals(TipoServico.ENERGIA)) {
				RegC500 regC500 = new RegC500(servico, lojaSped);
				
				regC500.setRegC590(null); // ni hao, Preciso setar essa parada??
				listRegC500.add(regC500);
			}
		});
		
		LOG.log(Level.INFO, "Registro C500, terminado. REG C500: ");
		return listRegC500;
	}

}
