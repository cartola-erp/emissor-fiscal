package net.cartola.emissorfiscal.sped.fiscal;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import net.cartola.emissorfiscal.sped.fiscal.bloco0.service.Bloco0Service;
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

		return null;
	}


}
