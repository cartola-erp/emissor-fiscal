package net.cartola.emissorfiscal.devolucao;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoService;
import net.cartola.emissorfiscal.loja.Loja;

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
	private DevolucaoOrigemService devolucaoOrigemService;

	public Map<Integer, Map<Long, Map<String, DevolucaoItem>>> getMapDevolucaoPorItemCodigoXECodigoSeq(Devolucao devolucao) {
		return devolucao.getItens().stream().collect(groupingBy(DevolucaoItem::getItem, groupingBy(DevolucaoItem::getCodigoX,
						toMap(DevolucaoItem::getCodigoSequencia, (devoItem) -> devoItem ))));
	}
	
	public Optional<Devolucao> findDevolucaoParaODocumentoFiscal(DocumentoFiscal newDocFiscal) {
		int documento = newDocFiscal.getDocumento();
		String lojaCnpj = newDocFiscal.getLoja().getCnpj();
		String emitenteCnpj = newDocFiscal.getEmitente().getCnpj();
		
		Optional<Devolucao> opDevolucao = devolucaoRepository.findByDocumentoAndLojaCnpjAndEmitenteCnpjAndOperacao(documento, lojaCnpj, emitenteCnpj, newDocFiscal.getOperacao());
		return opDevolucao;
	}

	public void deleteById(Long id) {
		devolucaoRepository.deleteById(id);
	}
	
	public Optional<Devolucao> save(Devolucao devolucao) {
		LOG.log(Level.INFO, "Salvando a Devolucao {0} ", devolucao);
		Map<String, Boolean> mapErros = new HashMap<>();
		super.setValoresNecessariosParaOsItens(devolucao, mapErros);
		this.setValoresNecessariosParaODocumento(devolucao, mapErros);
		devolucao.setCadastro(now());
		devolucao.setAlterado(now());

		Optional<Devolucao> opDevolucao = devolucaoRepository.findByDocumentoAndLoja(devolucao.getDocumento(), devolucao.getLoja());
		if (opDevolucao.isPresent()) {
			Devolucao oldDevolucao = opDevolucao.get();
			devolucaoItemService.deleteByListItens(oldDevolucao.getItens());
			devolucaoOrigemService.deleteByListDevolucaoOrigem(oldDevolucao.getDevolucaoOrigem());

			devolucao.setId(oldDevolucao.getId());
		}

		Optional<Devolucao> opDevolucaoSaved = Optional.ofNullable(devolucaoRepository.saveAndFlush(devolucao));
		LOG.log(Level.INFO, "A Devolucao {0} foi salva? {1} ", new Object[] { devolucao.getDocumento(), opDevolucaoSaved.isPresent() });
		return opDevolucaoSaved;
	}

//	@Override
//	protected void setValoresNecessariosParaODocumento(Documento<? extends Item> docuFisc, Map<String, Boolean> mapErros) {
	private void setValoresNecessariosParaODocumento(Devolucao devolucao, Map<String, Boolean> mapErros) {
		super.setValoresNecessariosParaODocumento(devolucao, mapErros);
		Map<String, Loja> mapLojaPorCnpj = lojaService.findAll().stream().collect(toMap(Loja::getCnpj, (loja) -> loja));

		for (DevolucaoOrigem origem : devolucao.getDevolucaoOrigem()) {
			String cnpjLojaOrigem = origem.getOrigemLoja().getCnpj();
			if (isLojaExistente(mapLojaPorCnpj, cnpjLojaOrigem, mapErros)) {
				origem.setOrigemLoja(mapLojaPorCnpj.get(cnpjLojaOrigem));
				origem.setDevolucao(devolucao);
			}
		}
	}

	private boolean isLojaExistente(Map<String, Loja> mapLojaPorCnpj, String cnpjLojaOrigem,
			Map<String, Boolean> mapErros) {
		if (mapLojaPorCnpj != null && mapLojaPorCnpj.containsKey(cnpjLojaOrigem)) {
			return true;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Loja de origem: ").append(cnpjLojaOrigem).append(" NÃO encontrada no emissor-fiscal ! ");
		mapErros.put(sb.toString(), true);
		return false;
	}


}
