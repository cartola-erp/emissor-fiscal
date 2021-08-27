
package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import static net.cartola.emissorfiscal.util.StringUtil.somenteNumerosELetras;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0400;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0400Service implements MontaGrupoDeRegistroList<Reg0400, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0400Service.class.getName());
	
	@Override
	public List<Reg0400> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro 0400 ");
		List<Reg0400> listReg0400 = new ArrayList<>();
		Set<Operacao> listOperacoes = new HashSet<>();
		listOperacoes.addAll(movimentosIcmsIpi.getListOperacoes());

		TreeSet<Operacao> listOperacoesOrdernadas = listOperacoes.stream().collect(Collectors.toCollection(TreeSet::new));
		
//		listOperacoes.stream().forEachOrdered(operacao -> {
		listOperacoesOrdernadas.stream().forEachOrdered(operacao -> {
			Reg0400 reg0400 = new Reg0400();
			reg0400.setCodNat(String.valueOf(operacao.getId()));
			reg0400.setDescrNat(somenteNumerosELetras(operacao.getDescricao(), true, null));
			listReg0400.add(reg0400);
		});
		
		LOG.log(Level.INFO, "Registro 0400, terminado: {0} " ,listReg0400);
		return listReg0400;
	}

}
