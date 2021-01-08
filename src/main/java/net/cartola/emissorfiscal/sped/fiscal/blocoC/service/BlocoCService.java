package net.cartola.emissorfiscal.sped.fiscal.blocoC.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.sped.fiscal.MontaBloco;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.BlocoC;

/**
 * 18/09/2020
 * @author robson.costa
 */
@Service
public class BlocoCService implements MontaBloco<BlocoC, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(BlocoCService.class.getName());
	
	@Autowired
	private RegC001Service regC001Service;
	
	@Autowired
	private RegC100Service regC100Service;
	
	@Autowired
	private RegC350Service regC350Service;
	
	@Autowired
	private RegC400Service regC400Service;
	
	@Autowired
	private RegC500Service regC500Service;
	
	@Autowired
	private RegC800Service regC800Service;
	
	@Autowired
	private RegC990Service regC990Service;
	
	
	@Override
	public BlocoC criarBloco(MovimentacoesMensalIcmsIpi movimentacoesMensalIcmsIpi) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Montando o BLOCO C, com INICIO em: {0} e TERMINO: {1} ", movimentacoesMensalIcmsIpi.getDataInicio());
		BlocoC blocoC = new BlocoC();
		
		/**
		 * TODO (Para ver/pensar melhor)
		 * Ver se eu tenho que fazer alguma validação aqui. Ex.: irá preecher registro X para aquele Mês?
		 * Tentar capturar possiveis erros.: Ex o famosão -> @NullPointerException
		 */
		
		blocoC.setRegC001(regC001Service.montarGrupoDeRegistroSimples(movimentacoesMensalIcmsIpi));
		blocoC.setRegC100(regC100Service.montarGrupoDeRegistro(movimentacoesMensalIcmsIpi));
		blocoC.setRegC350(regC350Service.montarGrupoDeRegistro(movimentacoesMensalIcmsIpi));
		blocoC.setRegC400(regC400Service.montarGrupoDeRegistro(movimentacoesMensalIcmsIpi));
		blocoC.setRegC500(regC500Service.montarGrupoDeRegistro(movimentacoesMensalIcmsIpi));
		blocoC.setRegC800(regC800Service.montarGrupoDeRegistro(movimentacoesMensalIcmsIpi));
		blocoC.setRegC990(regC990Service.montarGrupoDeRegistroSimples(movimentacoesMensalIcmsIpi));
		
		return blocoC;
	}


	
}