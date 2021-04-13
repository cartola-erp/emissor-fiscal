
package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0450;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0450Service implements MontaGrupoDeRegistroList<Reg0450, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0450Service.class.getName());
	
	@Override
	public List<Reg0450> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro 0450 ");
		List<Reg0450> listReg0450 = new ArrayList<>();
		
		// Dentro do meu objeto MovimentoMensalIcmsIpi --> Posso ter uma Model que seja referente a "parametrização" desse registro...
		// Mas pelos arquivos do SPED que vi, acredito que sempre será a Descrição abaixo para esse registro
		
		movimentosIcmsIpi.getListCodInfoComplementarFisco().stream().forEach(infoCompl -> {
			Reg0450 reg0450 = new Reg0450();
			
			reg0450.setCodInf(infoCompl.getCodInfo());
			reg0450.setTxt(infoCompl.getDescricao());
			listReg0450.add(reg0450);			
		});
		
		
		LOG.log(Level.INFO, "Registro 0450, terminado: {0} " ,listReg0450);
		return listReg0450;
	}

}
