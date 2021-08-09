package net.cartola.emissorfiscal.model.sped.fiscal.tabelas;

import java.util.Arrays;
import java.util.HashMap;
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
	
//	private static Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapTabela511PorTipoAjust;
	private static Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapTabela511PorTipoAjust;
	
	private static Map<TipoAjusteTabela511, Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>>> mapTabela511PorTipoAjustEDeducao;
	
	
	public List<Tabela511AjusteApuracaoIcmsSp> findAll() {
		return tabelaAjustIcmsSpRepo.findAll();
	}
	
	
	/**
	 * Retorna uma lista com os códigos da Tabela 5.1.1 - "...Ajuste apuracao ICMS SP...", pelo {@link TipoAjusteTabela511}
	 * 
	 * @param tipoAjuste
	 * @return
	 */
	public Set<Tabela511AjusteApuracaoIcmsSp> buscarPorTipoAjuste(TipoAjusteTabela511 tipoAjuste) {
		Set<Tabela511AjusteApuracaoIcmsSp> listTabela511PorTipoAjust = null;
		preencherMapTabela511PorTipoAjuste();
		if (mapTabela511PorTipoAjust != null && mapTabela511PorTipoAjust.containsKey(tipoAjuste)) {
			listTabela511PorTipoAjust = mapTabela511PorTipoAjust.get(tipoAjuste);
		}
		return (listTabela511PorTipoAjust != null) ? listTabela511PorTipoAjust : new HashSet<>() ;
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
		Set<Tabela511AjusteApuracaoIcmsSp> setPorTipoAjusteAndTipoDeducao = null;
		preencherMapTabela511PorTipoAjuste();
		if (!isMapNullOrEmpty(mapTabela511PorTipoAjust) && isMapNullOrEmpty(mapTabela511PorTipoAjustEDeducao)) {
			for (TipoAjusteTabela511 tipoAjusteNoMapa : mapTabela511PorTipoAjust.keySet()) {
				Set<Tabela511AjusteApuracaoIcmsSp> setTbl511PorTipoAjuste = mapTabela511PorTipoAjust.get(tipoAjusteNoMapa);
				preencherMapTabela511PorTipoAjustEDeducao(setTbl511PorTipoAjuste, tipoAjusteNoMapa);
			}
			
			setPorTipoAjusteAndTipoDeducao = mapTabela511PorTipoAjustEDeducao.get(tipoAjuste).get(tipoDeducao);
		}
		return (setPorTipoAjusteAndTipoDeducao != null) ? setPorTipoAjusteAndTipoDeducao : new HashSet<>();
	}

	
	/**
	 * Será preenchido o "mapTabela511PorTipoAjust" (caso o mesmo esteja vazio ou seja null)
	 */
	private void preencherMapTabela511PorTipoAjuste() {
		if (mapTabela511PorTipoAjust == null || mapTabela511PorTipoAjust.isEmpty()) {
			List<Tabela511AjusteApuracaoIcmsSp> listAjustesTabela511 = findAll();
			listAjustesTabela511.stream().forEachOrdered(tbl511 -> {
				TipoAjusteTabela511 tipoAjuste = getTipoAjuste(tbl511);
				populaMapTabela511PorTipoAjuste(tbl511, tipoAjuste);
			});
		}
	}

	private void preencherMapTabela511PorTipoAjustEDeducao (Set<Tabela511AjusteApuracaoIcmsSp> setTbl511PorTipoAjuste, TipoAjusteTabela511 tipoAjusteNoMapa)  {
//		Map<TipoAjusteTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapPorTipoAjuste = null;
//		if (isMapNullOrEmpty(mapTabela511PorTipoAjustEDeducao)) {
			Set<Tabela511AjusteApuracaoIcmsSp> setPorTipoAjuste = mapTabela511PorTipoAjust.get(tipoAjusteNoMapa);
//			if (!isMapNull(mapPorTipoAjuste)) {
			if (setPorTipoAjuste != null && !setPorTipoAjuste.isEmpty()) {
				for (Tabela511AjusteApuracaoIcmsSp tbl511 : setPorTipoAjuste) {
					populaMapTabela511PorTipoAjusteEDeducao(tbl511, tipoAjusteNoMapa);
				}
			}
//		}
	}
	

	private void populaMapTabela511PorTipoAjusteEDeducao(Tabela511AjusteApuracaoIcmsSp tbl511, TipoAjusteTabela511 tipoAjuste) {
		// TODO Auto-generated method stub
		if (isMapNullOrEmpty(mapTabela511PorTipoAjustEDeducao)) {
			mapTabela511PorTipoAjustEDeducao = new HashMap<>();
		}
		TipoDeducaoTabela511 tipoDeducao = getTipoDeducao(tbl511);
		if (mapTabela511PorTipoAjustEDeducao.containsKey(tipoAjuste) && tipoDeducao != null) {
			Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapPorTipoAjuste = mapTabela511PorTipoAjustEDeducao.get(tipoAjuste);
			if (!isMapNull(mapPorTipoAjuste) && mapPorTipoAjuste.containsKey(tipoDeducao)) {
				mapPorTipoAjuste.get(tipoDeducao).add(tbl511);
			} else {
				Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> newMapPorTipoDeducao = new HashMap<>();
				Set<Tabela511AjusteApuracaoIcmsSp> setPorDeducao = new HashSet<>();
				setPorDeducao.add(tbl511);
				newMapPorTipoDeducao.put(tipoDeducao, setPorDeducao);
				if(mapTabela511PorTipoAjustEDeducao.containsKey(tipoAjuste)) {
					Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> mapPorDeducao = mapTabela511PorTipoAjustEDeducao.get(tipoAjuste);
					if (mapPorDeducao.containsKey(tipoDeducao)) {
						mapPorDeducao.get(tipoDeducao).add(tbl511);
					} else {
						mapTabela511PorTipoAjustEDeducao.get(tipoAjuste).put(tipoDeducao, setPorDeducao);
					}
				} else {
					mapTabela511PorTipoAjustEDeducao.put(tipoAjuste, newMapPorTipoDeducao);
				}
			}
		} else {
			Map<TipoDeducaoTabela511, Set<Tabela511AjusteApuracaoIcmsSp>> newMapPorTipoDeducao = new HashMap<>();
			Set<Tabela511AjusteApuracaoIcmsSp> setPorDeducao = new HashSet<>();
			setPorDeducao.add(tbl511);
			newMapPorTipoDeducao.put(tipoDeducao, setPorDeducao);
			mapTabela511PorTipoAjustEDeducao.put(tipoAjuste, newMapPorTipoDeducao);
		}
	}

	
	private void populaMapTabela511PorTipoAjuste(Tabela511AjusteApuracaoIcmsSp tbl511, TipoAjusteTabela511 tipoAjuste) {
		if (isMapNullOrEmpty(mapTabela511PorTipoAjust)) {
			mapTabela511PorTipoAjust = new HashMap<>();
		}
		if (mapTabela511PorTipoAjust.containsKey(tipoAjuste) && tipoAjuste != null) {
			mapTabela511PorTipoAjust.get(tipoAjuste).add(tbl511);
		} else {
			Set<Tabela511AjusteApuracaoIcmsSp> setTabela511 = new HashSet<>();
			setTabela511.add(tbl511);
			mapTabela511PorTipoAjust.put(tipoAjuste, setTabela511);
		}
	}

	// =====================================================================================================================
	
	// NESSA PARTE TENHO QUE PREENCHER o: 			mapTabela511PorTipoAjustEDeducao			
	
	// =====================================================================================================================
	
	private <K, V> boolean isMapNullOrEmpty(Map<K, V> mapToVerify) {
		return (isMapNull(mapToVerify) || mapToVerify.isEmpty());
	}

	private <K, V> boolean isMapNull(Map<K, V> mapToVerify) {
		return (mapToVerify == null);
	}
	
	private TipoAjusteTabela511 getTipoAjuste(Tabela511AjusteApuracaoIcmsSp tbl511) {
		Character terceiroCaracter = tbl511.getCodigoAjuste().charAt(2);
		TipoAjusteTabela511 tipoAjuste = getTipoAjuste(terceiroCaracter);
		return tipoAjuste;
	}


	/**
	 * Irá obter o TipoAjuste, pela a String do Cod de ajuste 
	 * @param codAjApur
	 * @return
	 */
	public static TipoAjusteTabela511 getTipoAjuste(String codAjApur) {
		if (codAjApur != null && codAjApur.length() == 8) {
			return getTipoAjuste(codAjApur.charAt(2));
		}
		return null;
	}
	
	/**
	 * Será retornado o de TipoAjuste, de ICMS, conforme o pelo TERCEIRO CARACTERE do COD. AJUSTE;
	 * 
	 * @param terceiroCaracter
	 * @return
	 */
	private static TipoAjusteTabela511 getTipoAjuste(Character terceiroCaracter) {
		TipoAjusteTabela511 tipoAjuste = null;
		for (TipoAjusteTabela511 tpAjuste : getListTiposAjustes()) {
			if (tpAjuste.getTerceiroCodigo().equals(terceiroCaracter)) {
				tipoAjuste = tpAjuste;
			}
		}
		return tipoAjuste;
	}


	public static TipoDeducaoTabela511 getTipoDeducao(String codAjApur) {
		if (codAjApur != null && codAjApur.length() == 8) {
			return getTipoDeducao(codAjApur.charAt(3));
		}
		return null;
	}
	
	private TipoDeducaoTabela511 getTipoDeducao(Tabela511AjusteApuracaoIcmsSp tbl511) {
		Character quartoCaracter = tbl511.getCodigoAjuste().charAt(3);
		TipoDeducaoTabela511 tipoDeducao = getTipoDeducao(quartoCaracter);
		return tipoDeducao;
	}
	
	
	/**
	 * É retornado o TipoDeducao, conforme o QUARTO CARACTERE do COD. AJUSTE;
	 * 
	 * @param quartoCaracter
	 * @return
	 */
	private static TipoDeducaoTabela511 getTipoDeducao(Character quartoCaracter) {
		TipoDeducaoTabela511 tipoDeducao = null;
		 for (TipoDeducaoTabela511 tpDeducao : getListTiposDeducao()) {
			if (tpDeducao.getQuartoCodigo().equals(quartoCaracter)) {
				tipoDeducao = tpDeducao;
			}
		 }
		 return tipoDeducao;
	}
	
	
	private static List<TipoAjusteTabela511> getListTiposAjustes() {
		List<TipoAjusteTabela511> listTipoAjust =  Arrays.asList(TipoAjusteTabela511.values());
		return listTipoAjust;
	}
	
	private static List<TipoDeducaoTabela511> getListTiposDeducao() {
		List<TipoDeducaoTabela511> listTipoDeducao = Arrays.asList(TipoDeducaoTabela511.values());
		return listTipoDeducao;
	}
	
}
