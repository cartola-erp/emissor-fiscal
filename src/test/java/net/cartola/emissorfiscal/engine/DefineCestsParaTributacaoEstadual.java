package net.cartola.emissorfiscal.engine;

import java.util.ArrayList;
import java.util.Arrays;
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
public class DefineCestsParaTributacaoEstadual {

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
	
//	public static void main(String[] args) {
//		DefineCestsParaTributacaoEstadual thisClass = new DefineCestsParaTributacaoEstadual();
//		thisClass.buscaTribEsta();
//	}

	@Test
//	public void atualizaOCestParaTodasAsTributacoes() {
	public void test_01_DeveAtualizarOCestEVerificarSeAlgunsSaoOsEsperados() {

		List<TributacaoEstadual> listTribEsta = tribEstaRepository.findAll();
		System.out.println("executei: " +listTribEsta.size());
		buscarExistenciaCodigoCestParaNcm(listTribEsta);
		
		tribEstaRepository.saveAll(listTribEsta);
//		return listTribEsta;
	}
	
	
	public void buscarExistenciaCodigoCestParaNcm(List<TributacaoEstadual> listTribEsta) {
		CestNcmModel ncm = null;
		for (TributacaoEstadual tribEsta : listTribEsta) {
			String classeCompleta = Integer.toString(tribEsta.getNcm().getNumero());
			String classeCom7Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 7);
			String classeCom6Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 6);
			String classeCom5Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 5);
			String classeCom4Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 4);
			String classeCom3Digitos = Integer.toString(tribEsta.getNcm().getNumero()).substring(0, 3);
			
			List<String> ncms = Arrays.asList(classeCompleta, classeCom7Digitos, classeCom6Digitos, classeCom5Digitos, classeCom4Digitos, classeCom3Digitos);
//			List<Integer> listIntNcms = ncms.stream().flatMapToInt(Number.class::cast).mapToInt(Number::intValue).boxed().collect(toList());
			List<Integer> listIntNcms = new ArrayList<>();
			listIntNcms.add(Integer.valueOf(classeCompleta));
			listIntNcms.add(Integer.valueOf(classeCompleta));
			listIntNcms.add(Integer.valueOf(classeCom7Digitos));
			listIntNcms.add(Integer.valueOf(classeCom6Digitos));
			listIntNcms.add(Integer.valueOf(classeCom5Digitos));
			listIntNcms.add(Integer.valueOf(classeCom4Digitos));
			listIntNcms.add(Integer.valueOf(classeCom3Digitos));
			
			//			String where = "\n WHERE CN.NCM_SH=" + classeCompleta + " OR CN.NCM_SH=" + classeCom7Digitos
//					+ " OR CN.NCM_SH=" + classeCom6Digitos + " OR " + "CN.NCM_SH=" + classeCom5Digitos
//					+ " OR CN.NCM_SH=" + classeCom4Digitos + " OR CN.NCM_SH=" + classeCom3Digitos;
//			List<CestNcmModel> listaNcmsSemLike = CestNcmModel.getItens(where);
			List<CestNcmModel> listaNcmsSemLike = cestNcmService.findByNcmIn(listIntNcms);
//			if(classeCompleta.equals("83059000")) {
//				System.out.println("achei a ");
//			}
			if (listaNcmsSemLike != null && !listaNcmsSemLike.isEmpty()) {
				if (listaNcmsSemLike.size() == 1) {
					ncm = listaNcmsSemLike.get(0);
				} else {
					int sizeNcmAnterior = 0;
					for (CestNcmModel n : listaNcmsSemLike) {
						String aux = String.valueOf(n.getNcm());
						if (aux.length() > sizeNcmAnterior) {
							sizeNcmAnterior = aux.length();
							ncm = n;
						}
					}
				}
			}
			if (ncm != null && ncm.getCestCodigo() > 0) {
				/** AQUI EU SETO O CEST, que foi encontrado*/
				tribEsta.setCest(ncm.getCestCodigo());
				ncm = null;
			} else {
				/** Caso NÃO tenha encontrado, eu SETO o CEST de OUTROS */
				tribEsta.setCest(199900);
			}
		}
	}
	

}
