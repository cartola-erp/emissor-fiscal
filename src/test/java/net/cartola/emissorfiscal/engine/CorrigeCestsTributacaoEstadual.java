package net.cartola.emissorfiscal.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualRepository;

/**
 * Essa classe deve ser rodada no LOCALHOST, para CORRIGIR os CESTs das tributações estaduais.
 * Com base nas tabelas: cests e cests_ncms (rodar script: /doc/scripts/atualizaTabelaCest/20201207atualizaTabelaCest.sql"), 
 * para popular as tabelas com os CESTS apenas do SEGMENTO de AUTOPEÇAS;
 * 
 * É preciso fazer isso pq, quando rodo a classe: EmissorFiscalCadastroIcmsEstadualTest (no ERP), para inserir as tributações estaduais,
 * o cest que ele obtem, é o que eu li da PLANILHA do KOLOSSUS, porém vários são de outros segmentos que NÃO são de AUTOPEÇAS;
 * 
 * Portanto é preciso rodar essa classe abaixo
 * 
 * @date 15 de jan. de 2021
 * @author robson.costa
 */
//@SpringBootApplication
//@Component
//@ActiveProfiles("homologacao")
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CorrigeCestsTributacaoEstadual {

//	@Autowired
//	private CestNcmRepository cestNcmRepository;
//	
	@Autowired
	private CestRepository cestRepository;
	
	@Autowired
	private CestNcmService cestNcmService;
	 
	@Autowired
	private TributacaoEstadualRepository tribEstaRepository;
	
//	@Autowired
//	private TributacaoEstadualService tribEstaService;
	
//	public static void main(String[] args) {
//		SpringApplication.run(DefineCestsParaTributacaoEstadual.class, args);
//		
////		CestNcmRepository
//	}
	

	@Test
//	public void atualizaOCestParaTodasAsTributacoes() {
	public void test_01_DeveAtualizarOCestEVerificarSeAlgunsSaoOsEsperados() {
		List<TributacaoEstadual> listTribEsta = tribEstaRepository.findAll();
		System.out.println("A quantidade de TRIBUTACAO ESTADUAL achada foi de: " +listTribEsta.size());

		buscarExistenciaCodigoCestParaNcm(listTribEsta);
		List<TributacaoEstadual> listTribEstaUpdated = tribEstaRepository.saveAll(listTribEsta);
		
		System.out.println("A quantidade ATUALIZADA da TRIBUTACAO ESTADUAL foi de: " +listTribEstaUpdated.size());
		assertEquals(listTribEsta.size(), listTribEstaUpdated.size());
		assertNotEquals(0, listTribEsta.size());
		assertNotEquals(0, listTribEstaUpdated.size());

	}
	
	/* Busca o Codigo CEST para o NCM da tributacao estadual, e atualiza o mesmo */
	private void buscarExistenciaCodigoCestParaNcm(List<TributacaoEstadual> listTribEsta) {
		CestNcmModel cestNcm = null;
		for (TributacaoEstadual tribEsta : listTribEsta) {
			List<Integer> listIntNcms = getListIntNcms(tribEsta);
			List<CestNcmModel> listaNcmsSemLike = cestNcmService.findByNcmIn(listIntNcms);
			if (listaNcmsSemLike != null && !listaNcmsSemLike.isEmpty()) {
				if (listaNcmsSemLike.size() == 1) {
					cestNcm = listaNcmsSemLike.get(0);
				} else {
					int sizeNcmAnterior = 0;
					for (CestNcmModel n : listaNcmsSemLike) {
						String aux = String.valueOf(n.getNcm());
						if (aux.length() > sizeNcmAnterior) {
							sizeNcmAnterior = aux.length();
							cestNcm = n;
						}
					}
				}
			}
			if (cestNcm != null && cestNcm.getCestCodigo() > 0) {
				/** AQUI EU SETO O CEST, que foi encontrado*/
				tribEsta.setCest(cestNcm.getCestCodigo());
				cestNcm = null;
			} else {
				/** Caso NÃO tenha encontrado, eu SETO o CEST de OUTROS */
				tribEsta.setCest(199900);
			}
		}
	}


	/**
	 * Obtem uma List de inteiros, para UM determinado NCM, que pode ter o cest com 8, 7, 6 etc digitos da CLASSE FISCAL
	 * @param tribEsta
	 * @return
	 */
	private List<Integer> getListIntNcms(TributacaoEstadual tribEsta) {
		String classeCompleta = Integer.toString(tribEsta.getNcm().getNumero());
		String classeCom7Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 7);
		String classeCom6Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 6);
		String classeCom5Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 5);
		String classeCom4Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 4);
		String classeCom3Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 3);
		
//		List<String> ncms = Arrays.asList(classeCompleta, classeCom7Digitos, classeCom6Digitos, classeCom5Digitos, classeCom4Digitos, classeCom3Digitos);
		List<Integer> listIntNcms = new ArrayList<>();
		listIntNcms.add(Integer.valueOf(classeCompleta));
		listIntNcms.add(Integer.valueOf(classeCompleta));
		listIntNcms.add(Integer.valueOf(classeCom7Digitos));
		listIntNcms.add(Integer.valueOf(classeCom6Digitos));
		listIntNcms.add(Integer.valueOf(classeCom5Digitos));
		listIntNcms.add(Integer.valueOf(classeCom4Digitos));
		listIntNcms.add(Integer.valueOf(classeCom3Digitos));
		
		return listIntNcms;
	}
	

}
