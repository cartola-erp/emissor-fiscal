package net.cartola.emissorfiscal.sped.fiscal;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import net.cartola.emissorfiscal.sped.fiscal.bloco0.service.Bloco0Service;
import net.cartola.emissorfiscal.sped.fiscal.bloco9.Bloco9;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.service.BlocoCService;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.service.BlocoDService;
import net.cartola.emissorfiscal.sped.fiscal.blocoE.service.BlocoEService;
import net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service.Bloco1Service;
import net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service.BlocoBService;
import net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service.BlocoGService;
import net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service.BlocoHService;
import net.cartola.emissorfiscal.sped.fiscal.blocosBGHK1.service.BlocoKService;

/**
 * 21/09/2020
 * @author robson.costa
 */
public class SpedFiscalService implements MontaArquivoSpedFiscal<SpedFiscal, MovimentacoesMensalIcmsIpi> {
	
	private static final Logger LOG = Logger.getLogger(SpedFiscalService.class.getName());

	@Autowired
	private Bloco0Service bloco0Service;
	
	@Autowired
	private BlocoBService blocoBService;
	
	@Autowired
	private BlocoCService blocoCService;
	
	@Autowired 
	private BlocoDService blocoDService;
	
	@Autowired
	private BlocoEService blocoEService;
	
	@Autowired
	private BlocoGService blocoGService;
	
	@Autowired
	private BlocoHService blocoHService;
	
	@Autowired
	private BlocoKService blocoKService;
	
	@Autowired
	private Bloco1Service bloco1Service;
	
	// TENHO QUE VERIFICAR COMO QUE IREI MONTAR ESSE BLOCO já que basicamente é a contagem de cada linha que teve em cada bloco
//	@Autowired		
//	private Bloco9Service bloco9Service;
	
	
	
	@Override
	public SpedFiscal criarArquivoSpedFiscal(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o arquivo do SPED FISCAL (Icms IPI) ");
		/** 
		 * @Todo 
		 * Iterar dentro de um for, de lojas? mas onde? aqui ou no controller? 
		 * acho que o ideal será no controller.
		 * Porém o importante de saber é que caso 1 loja de certo tenho que salvar o seu arquivo no banco, e apenas retornar 
		 * as que deram errado;
		 */
		
		SpedFiscal spedFiscal = new SpedFiscal();
		
		spedFiscal.setBloco0(bloco0Service.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBlocoB(blocoBService.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBlocoC(blocoCService.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBlocoD(blocoDService.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBlocoE(blocoEService.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBlocoG(blocoGService.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBlocoH(blocoHService.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBlocoK(blocoKService.criarBloco(movimentacoesMensalIcmsIpi));
		spedFiscal.setBloco1(bloco1Service.criarBloco(movimentacoesMensalIcmsIpi));
//		spedFiscal.setBloco9(bloco9Service.criarBloco(movimentacoesMensalIcmsIpi));
		
		return spedFiscal;
	}


}
