
package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.ObservacoesLancamentoFiscal;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0460;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0460Service implements MontaGrupoDeRegistroList<Reg0460, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0460Service.class.getName());
	
	
	/**
	 * Esse método deverá ser chamado no MINIMO depois que GERAR o BLOCO D;
	 * Pois para ser gerado o REGISTRO 0460, será usado a Lista de Registro Analitico, que foi preenchida, 
	 * nos Registros C190| D190
	 * 
	 */
	@Override
	public List<Reg0460> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0460 ");
		List<Reg0460> listReg0460 = new ArrayList<>();

		Set<ObservacoesLancamentoFiscal> setObservacoesLancamentoFiscal = movimentosIcmsIpi.getSetObservacoesLancamentoFiscal();

		Set<String> setCfopsObservacoes = setObservacoesLancamentoFiscal.stream().map(ObservacoesLancamentoFiscal::getCodObs).collect(Collectors.toSet());
		
		
//		|0460|1102|ObservaCAo para CFOP 1102Conforme Portaria CAT 662018|
		 
		for (String codObs : setCfopsObservacoes ) {
//			String codObs = observacaoLancamentoFiscal.getCodObs();
			if (codObs.length() == 4) {
				// Observacao para CFOP 1102 Conforme Portaria CAT 662018
				String descricao = "Observacao para CFOP " +codObs+ " Conforme Portaria CAT 662018";
				Reg0460 reg0460 = new Reg0460();
				reg0460.setCodObs(codObs);
				reg0460.setTxt(descricao);
				listReg0460.add(reg0460);
			}
		}
	
		return listReg0460;
	}


}
