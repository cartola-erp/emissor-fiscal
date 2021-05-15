package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.TipoServico;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoRegistroSimples;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD001AberturaDoBlocoD;

/**
 * @autor robson.costa
 * @data 5 de mai. de 2021
 */
@Service
class RegD001Service implements MontaGrupoRegistroSimples<RegD001AberturaDoBlocoD, MovimentoMensalIcmsIpi>{

	
	
	@Override
	public RegD001AberturaDoBlocoD montarGrupoDeRegistroSimples(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		// TODO Auto-generated method stub
		RegD001AberturaDoBlocoD regD001 = new RegD001AberturaDoBlocoD();
		Set<TipoServico> setTipoServicoDocFiscSped = movimentosIcmsIpi.getListDocumentoFiscalServico().stream().map(DocumentoFiscal::getTipoServico).collect(toSet());
		
		// Se algum tipo de servico da lista for igual a internet, cte, telefone fixo/movel, então tem dados para preencher no blocoD
		if (getListServicoBlocoD().stream().anyMatch(null)  e(setTipoServicoDocFiscSped)) {
			
		}
		SENÃO então, não tem
		return null;	
	}

	
	private Set<TipoServico> getListServicoBlocoD() {
		Set<TipoServico> listTipoServico = new HashSet<>();
		listTipoServico.add(TipoServico.INTERNET);
		listTipoServico.add(TipoServico.CTE);
		listTipoServico.add(TipoServico.TELEFONE_FIXO_MOVEL);
		return listTipoServico;
	}

}
