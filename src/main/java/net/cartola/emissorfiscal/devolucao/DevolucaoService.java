package net.cartola.emissorfiscal.devolucao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoService;

/**
 * @date 17 de set. de 2021
 * @author robson.costa
 */
@Service
public class DevolucaoService extends DocumentoService {

	private static final Logger LOG = Logger.getLogger(DevolucaoService.class.getName());
	
	@Autowired
	private DevolucaoRepository devolucaoRepository;

//	@Autowired
//	private DevolucaoItemService devolucaoItemService;
	

	
	public Optional<Devolucao> save(Devolucao devolucao) {
		LOG.log(Level.INFO, "Salvando a Devolucao {0} " ,devolucao);
		Map<String, Boolean> mapErros = new HashMap<>();
		super.setValoresNecessariosParaOsItens(devolucao, mapErros);
		super.setValoresNecessariosParaODocumento(devolucao, mapErros);
		
		Devolucao devolucaoSaved = devolucaoRepository.saveAndFlush(devolucao);
				
		return Optional.ofNullable(devolucaoSaved);
	}


	
	
}
