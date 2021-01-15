package net.cartola.emissorfiscal.engine;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
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
	public void atualizaOCestParaTodasAsTributacoes() {
		List<TributacaoEstadual> listTribEsta = tribEstaRepository.findAll();
		System.out.println("executei: " +listTribEsta.size());
//		return listTribEsta;
	}
	
	

}
