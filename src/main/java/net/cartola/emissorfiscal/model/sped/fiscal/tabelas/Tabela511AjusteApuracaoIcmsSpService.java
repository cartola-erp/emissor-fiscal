package net.cartola.emissorfiscal.model.sped.fiscal.tabelas;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 12 de jul. de 2021
 * @author robson.costa
 */
@Service
public class Tabela511AjusteApuracaoIcmsSpService {

	@Autowired
	private Tabela511AjusteApuracaoIcmsSpRepository tabelaAjustIcmsSpRepo;
	
	private static Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapTabela511PorTipoAjust;
	
	private static Map<TipoAjusteTabela511, Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>>> mapTabela511PorTipoAjusEDeducao;
	
	
	public List<Tabela511AjusteApuracaoIcmsSp> findAll() {
		return tabelaAjustIcmsSpRepo.findAll();
	}
	
	
	/**
	 * Retorna uma lista com os códigos da Tabela 5.1.1 - "...Ajuste apuracao ICMS SP...", 
	 * pelo {@link TipoAjusteTabela511}
	 * e {@link TipoDeducaoTabela511} 
	 * @param tipoAjuste
	 * @param tipoDeducao
	 * @return
	 */
	public Set<Tabela511AjusteApuracaoIcmsSp> buscarPorTipoAjusteEDeducao(TipoAjusteTabela511 tipoAjuste, TipoDeducaoTabela511 tipoDeducao) {
		Map<TipoAjusteTabela511, Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>>> map;
		Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapTabela511PorTipoAjuste = getMapTabela511PorTipoAjuste();

		if (mapTabela511PorTipoAjuste != null && !mapTabela511PorTipoAjuste.isEmpty() && mapTabela511PorTipoAjusEDeducao != null && mapTabela511PorTipoAjusEDeducao.isEmpty()) {
			populaMapTabela511PorTipoAjusteEDeducao(mapTabela511PorTipoAjuste);
		}
		
		Set<Tabela511AjusteApuracaoIcmsSp> setTabela511AjusteApuracaoIcmsSp = mapTabela511PorTipoAjusEDeducao.get(tipoAjuste).get(tipoDeducao);
		return setTabela511AjusteApuracaoIcmsSp;
	}
	
	
	private void populaMapTabela511PorTipoAjusteEDeducao(Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapTabela511PorTipoAjuste) {
		// TODO Auto-generated method stub
		for (TipoAjusteTabela511 tipoAjuste : mapTabela511PorTipoAjuste.keySet()) {
			Set<Tabela511AjusteApuracaoIcmsSp> setTabela511AjusteApuracao = mapTabela511PorTipoAjuste.get(tipoAjuste);
			
//			for (Tabela511AjusteApuracaoIcmsSp tabela511AjusteApuracaoIcmsSp : setTabela511AjusteApuracao) {
//				// verifica tipo de deducao
//			}
			
			setTabela511AjusteApuracao.stream().forEachOrdered(tbl511AjustApuracaoIcmsSp -> {
				Character quartoCaracter = tbl511AjustApuracaoIcmsSp.getCodigoAjuste().charAt(3);
				TipoDeducaoTabela511 tipoDeducao = getTipoDeducao(quartoCaracter);
				
				// popula o mapa de deducao
			});
		}
	}


	/**
	 * Retorna uma lista com os códigos da Tabela 5.1.1 - "...Ajuste apuracao ICMS SP...", pelo {@link TipoAjusteTabela511}
	 * 
	 * @param tipoAjuste
	 * @return
	 */
	public Set<Tabela511AjusteApuracaoIcmsSp> buscarPorTipoAjuste(TipoAjusteTabela511 tipoAjuste) {
		Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapTabela511PorTipoAjuste = getMapTabela511PorTipoAjuste();
		Set<Tabela511AjusteApuracaoIcmsSp> listTabela511PorTipoAjust = mapTabela511PorTipoAjuste.get(tipoAjuste);
		
		return listTabela511PorTipoAjust;
	}
	
	
	private Map<TipoAjusteTabela511, Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>>> getMapTabela511PorTipoAjusteEDeducao(
			TipoAjusteTabela511 tipoAjuste, TipoDeducaoTabela511 tipoDeducao) {
		// TODO Auto-generated method stub
		Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapTabela511PorTipoAjuste = getMapTabela511PorTipoAjuste();
		return null;
	}


	private Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> getMapTabela511PorTipoAjuste() {
		if (mapTabela511PorTipoAjust == null || mapTabela511PorTipoAjust.isEmpty()) {
			List<Tabela511AjusteApuracaoIcmsSp> listAjustesTabela511 = findAll();
			
			listAjustesTabela511.stream().forEachOrdered(tbl511 -> {
				Character terceiroCaracter = tbl511.getCodigoAjuste().charAt(2);
				TipoAjusteTabela511 tipoAjuste = getTipoAjuste(terceiroCaracter);
				populaMapTabela511PorTipoAjuste(tbl511, tipoAjuste);
			});
		}
		return mapTabela511PorTipoAjust;
	}
	
	private void populaMapTabela511PorTipoAjuste(Tabela511AjusteApuracaoIcmsSp tbl511, TipoAjusteTabela511 tipoAjuste) {
		if (mapTabela511PorTipoAjust.containsKey(tipoAjuste) && tipoAjuste != null) {
			mapTabela511PorTipoAjust.get(tipoAjuste).add(tbl511);
		} else {
			Set<Tabela511AjusteApuracaoIcmsSp> listTabela511 = new HashSet<>();
			listTabela511.add(tbl511);
			mapTabela511PorTipoAjust.put(tipoAjuste, listTabela511);
		}
	}

	private TipoAjusteTabela511 getTipoAjuste(Character terceiroCaracter) {
		TipoAjusteTabela511 tipoAjuste = null;
		for (TipoAjusteTabela511 tpAjuste : getListTiposAjustes()) {
			if (tpAjuste.getTerceiroCodigo().equals(terceiroCaracter)) {
				tipoAjuste = tpAjuste;
			}
		}
		return tipoAjuste;
	}

	
	private TipoDeducaoTabela511 getTipoDeducao(Character quartoCaracter) {
		TipoDeducaoTabela511 tipoDeducao = null;
		 for (TipoDeducaoTabela511 tpDeducao : getListTiposDeducao()) {
			if (tpDeducao.getQuartoCodigo().equals(quartoCaracter)) {
				tipoDeducao = tpDeducao;
			}
		 }
		 return tipoDeducao;
	}
	

	private List<TipoAjusteTabela511> getListTiposAjustes() {
		List<TipoAjusteTabela511> listTipoAjust =  Arrays.asList(TipoAjusteTabela511.values());
		return listTipoAjust;
	}
	
	private List<TipoDeducaoTabela511> getListTiposDeducao() {
		List<TipoDeducaoTabela511> listTipoDeducao = Arrays.asList(TipoDeducaoTabela511.values());
		return listTipoDeducao;
	}
	
}
