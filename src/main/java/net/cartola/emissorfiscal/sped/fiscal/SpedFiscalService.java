package net.cartola.emissorfiscal.sped.fiscal;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class SpedFiscalService implements MontaSpedFiscal<SpedFiscal, MovimentoMensalIcmsIpi> {
	
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
	public SpedFiscal criarSpedFiscal(MovimentoMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		LOG.log(Level.INFO, "Criando os Blocos do SPED FISCAL (Icms IPI) ");

		/**
		 * COMPLETABLE FUTURE 
		 * Aparentemente como somente o BLOCO 9 que depende de outro (no caso de todos acima dele). É viável
		 * Eu montar todos os outros blocos paralelamente; Logo que todos os outros terminarem (acho que verifico com ".get()" )
		 * aí sim eu começo a montagem do BLOCO 9
		 *
		 * @see https://www.devmedia.com.br/trabalhando-com-completablefuture-no-java/32854
		 * @see https://www.baeldung.com/java-completablefuture
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

		LOG.log(Level.INFO, "Terminado a criação dos Blocos do SPED FISCAL (Icms IPI) ");
		return spedFiscal;
	}


}
