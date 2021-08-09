package net.cartola.emissorfiscal.sped.fiscal.tabelas;

import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.model.sped.fiscal.tabelas.Tabela511AjusteApuracaoIcmsSp;
import net.cartola.emissorfiscal.model.sped.fiscal.tabelas.Tabela511AjusteApuracaoIcmsSpService;
import net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoAjusteTabela511;
import net.cartola.emissorfiscal.model.sped.fiscal.tabelas.TipoDeducaoTabela511;

/**
 * @date 14 de jul. de 2021
 * @author robson.costa
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tabela511AjusteApuracaoIcmsSpTest {
	
	
	@Autowired
	private Tabela511AjusteApuracaoIcmsSpService tabela511Service;
	
	private static final String CODIGO_AJUST = "SP000209";
	private static final TipoAjusteTabela511 TIPO_AJUST_ICMS_PROPRIO = TipoAjusteTabela511.ICMS_PROPRIA;
	private static final TipoAjusteTabela511 TIPO_AJUST_ICMS_ST = TipoAjusteTabela511.ICMS_ST;

	
	private static final TipoDeducaoTabela511 TIPO_DEDUCAO_ESTORNO_DEBITO = TipoDeducaoTabela511.ESTORNO_DE_DEBITOS;

	@Test
	public void test01_DeveEncontrarIcmsPropria() {
//		Set<Tabela511AjusteApuracaoIcmsSp> set = tabela511Service.buscarPorTipoAjusteEDeducao(TIPO_AJUST_ICMS_PROPRIO, TIPO_DEDUCAO_ESTORNO_DEBITO);
		Set<Tabela511AjusteApuracaoIcmsSp> set = tabela511Service.buscarPorTipoAjuste(TIPO_AJUST_ICMS_PROPRIO);
		
		Set<Tabela511AjusteApuracaoIcmsSp> set2 = tabela511Service.buscarPorTipoAjuste(TIPO_AJUST_ICMS_PROPRIO);

//		System.out.println(set);
		
	}
	
	@Test
	public void test02_DeveEncontrarIcmsPropriaPorDeducao() {
//		Set<Tabela511AjusteApuracaoIcmsSp> set = tabela511Service.buscarPorTipoAjusteEDeducao(TIPO_AJUST_ICMS_PROPRIO, TIPO_DEDUCAO_ESTORNO_DEBITO);
		Set<Tabela511AjusteApuracaoIcmsSp> set = tabela511Service.buscarPorTipoAjusteEDeducao(TIPO_AJUST_ICMS_PROPRIO, TIPO_DEDUCAO_ESTORNO_DEBITO);
//		Set<Tabela511AjusteApuracaoIcmsSp> set = tabela511Service.buscarPorTipoAjusteEDeducao(TIPO_AJUST_ICMS_ST, TIPO_DEDUCAO_ESTORNO_DEBITO);

//		Set<Tabela511AjusteApuracaoIcmsSp> set2 = tabela511Service.buscarPorTipoAjuste(TIPO_AJUST_ICMS_PROPRIO);
		
		System.out.println("test 02");
		set.forEach(System.out::println);
		
		System.out.println(set);
		
	}
}
