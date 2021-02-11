package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.produto.ProdutoUnidade;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0190;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0190Service implements MontaGrupoDeRegistroList<Reg0190, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0190Service.class.getName());
	
	@Override
	public List<Reg0190> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0190 ");
		List<Reg0190> listReg0190 = new LinkedList<>();
		List<ProdutoUnidade> listProdUnid = movimentosIcmsIpi.getListProdUnid();
		
		listProdUnid.stream().forEachOrdered(unidade -> {
			Reg0190 reg0190 = new Reg0190();
//			reg0190.setUnid(unidade.getSigla());
//			reg0190.setDescr(unidade.getDescricao());
			reg0190.setUnid(String.valueOf(unidade.getId()));
			reg0190.setDescr(unidade.getSigla());
			listReg0190.add(reg0190);
		});
		LOG.log(Level.INFO, "Grupo de Registro 0190, terminado: {0} " ,listReg0190);
		return listReg0190;
	}

}
