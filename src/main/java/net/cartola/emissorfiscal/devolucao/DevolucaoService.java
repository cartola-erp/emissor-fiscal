package net.cartola.emissorfiscal.devolucao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoService;
import net.cartola.emissorfiscal.loja.LojaService;
import net.cartola.emissorfiscal.operacao.OperacaoService;
import net.cartola.emissorfiscal.pessoa.PessoaService;

/**
 * @date 17 de set. de 2021
 * @author robson.costa
 */
@Service
public class DevolucaoService extends DocumentoService {

	private static final Logger LOG = Logger.getLogger(DevolucaoService.class.getName());
	
	@Autowired
	private DevolucaoRepository devolucaoRepository;

	@Autowired
	private DevolucaoItemService devolucaoItemService;

	@Autowired
	private OperacaoService operacaoService;
			
	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private PessoaService pessoaService;
	
	
	
	
	public Optional<DocumentoFiscal> save(Devolucao devolucao) {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Salvando a Devolucao {0} " ,devolucao);
		Map<String, Boolean> mapErros = new HashMap<>();
		super.setValoresNecessariosParaOsItens(devolucao, mapErros);
		super.setValoresNecessariosParaODocumento(devolucao, mapErros);
		
		Devolucao devolucaoSaved = devolucaoRepository.saveAndFlush(devolucao);
		
		// fazer calcular e gerar o documento fiscal
		
		return null;
//		return Optional.ofNullable(devolucaoSaved);
	}

	
	
}
