package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmService;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoService;

/**
  * @date 8 de mar. de 2021
  * @author robson.costa
  */
@Service
public class TributacaoEstadualGuiaService {

	private static final Logger LOG = Logger.getLogger(TributacaoEstadualGuiaService.class.getName());
	
	@Autowired
	private TributacaoEstadualGuiaRepository tribEstaGuiaRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private OperacaoService operacaoService;
	
	@Autowired
	private NcmService ncmService;
	
	
	public List<TributacaoEstadualGuia> findAll() {
		return tribEstaGuiaRepository.findAll();
	}
	
	public Page<TributacaoEstadualGuia> findAll(PageRequest pr) {
		return tribEstaGuiaRepository.findAll(pr);
	}
	
	public Optional<TributacaoEstadualGuia> save(TributacaoEstadualGuia tribEstaGuia) {
		if (tribEstaGuia != null) {
			divideTribEstaGuiaPorCem(tribEstaGuia);
		}
		return Optional.ofNullable(tribEstaGuiaRepository.saveAndFlush(tribEstaGuia));
	}
	
	public Optional<TributacaoEstadualGuia> save(TributacaoEstadualGuia tribEstaGuia, Long ufOrigemId, Long ufDestinoId, Long operacaoId, Long ncmId) {
		Estado estadoOrigem = estadoService.findOne(ufOrigemId).get();
		Estado estadoDestino = estadoService.findOne(ufDestinoId).get();
		Operacao operacao = operacaoService.findOne(operacaoId).get();
		Ncm ncm = ncmService.findOne(ncmId).get();

		tribEstaGuia.setEstadoOrigem(estadoOrigem);
		tribEstaGuia.setEstadoDestino(estadoDestino);
		tribEstaGuia.setOperacao(operacao);
		tribEstaGuia.setNcm(ncm);
		
		LOG.log(Level.INFO, "Salvando a Tributacao Estadual Guia {0} " ,tribEstaGuia);
		return save(tribEstaGuia);
	}
	
	public List<TributacaoEstadualGuia> saveAll(List<TributacaoEstadualGuia> listTribEstaGuia) {
		listTribEstaGuia.forEach(tribEstaGuia -> {
			if (tribEstaGuia != null) {
				divideTribEstaGuiaPorCem(tribEstaGuia);
			}
		});
		return tribEstaGuiaRepository.saveAll(listTribEstaGuia);
	}

	public List<TributacaoEstadualGuia> findTributacaoEstadualByNcm(Ncm ncm) {
		return tribEstaGuiaRepository.findByNcm(ncm);
	}
	
	public Optional<TributacaoEstadualGuia> findOne(Long id) {
		return tribEstaGuiaRepository.findById(id); 
	}
	
	public List<TributacaoEstadualGuia> findTributacaoEstadualByNcms(Collection<Ncm> ncms) {
		return tribEstaGuiaRepository.findByNcmIn(ncms);
	}
	
	public Page<TributacaoEstadualGuia> findTributacaoEstadualGuiaByNcms(List<Ncm> ncms, PageRequest pr) {
		return tribEstaGuiaRepository.findByNcmIn(ncms, pr);
	}
	
	public List<TributacaoEstadualGuia> findTribEstaGuiaByTipoGuiaUfOrigemUfDestinoProdutoOrigemOperENcms(TipoGuia tipoGuia, Estado estadoOrigem, Estado estadoDestino, 
			Set<ProdutoOrigem> produtoOrigens, Operacao operacao, Set<Ncm> ncms) {
		return tribEstaGuiaRepository.findByTipoGuiaAndEstadoOrigemAndEstadoDestinoAndProdutoOrigemInAndOperacaoAndNcmIn(tipoGuia,
				estadoOrigem, estadoDestino, produtoOrigens, operacao,
				ncms);
	}
	
//	public List<TributacaoEstadualGuia> findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(Operacao operacao, Estado estadoOrigem, Estado estadoDestino, RegimeTributario regimeTributario,
//			Collection<Finalidade> finalidade, Collection<Ncm> ncms) {
//		return tribEstaGuiaRepository.findByOperacaoAndEstadoOrigemAndEstadoDestinoAndRegimeTributarioAndFinalidadeInAndNcmIn(operacao, estadoOrigem, estadoDestino, regimeTributario, finalidade, ncms);
//	}

	public void deleteById(long id) {
		tribEstaGuiaRepository.deleteById(id);
	}

	
	private void divideTribEstaGuiaPorCem(TributacaoEstadualGuia tribEstaGuia) {
		tribEstaGuia.setIcmsAliqInternaDestino(tribEstaGuia.getIcmsAliqInternaDestino().divide(new BigDecimal(100D)));
		tribEstaGuia.setIcmsIva(tribEstaGuia.getIcmsIva().divide(new BigDecimal(100D)));
		tribEstaGuia.setIcmsAliquota(tribEstaGuia.getIcmsAliquota().divide(new BigDecimal(100D)));
	}
	
	public void multiplicaTribEstaGuiaPorCem(TributacaoEstadualGuia tribEstaGuia) {
		tribEstaGuia.setIcmsAliqInternaDestino(tribEstaGuia.getIcmsAliqInternaDestino().multiply(new BigDecimal(100D)));
		tribEstaGuia.setIcmsIva(tribEstaGuia.getIcmsIva().multiply(new BigDecimal(100D)));
		tribEstaGuia.setIcmsAliquota(tribEstaGuia.getIcmsAliquota().multiply(new BigDecimal(100D)));
	}


	// ====================  "VALIDAÇÕES" =================== 

//	public List<String> getMensagensErros(BindingResult bindResult, boolean existeNumeroEExecao) {
//		List<String> msg = new ArrayList<>();
//		for(ObjectError objError : bindResult.getAllErrors()) {
//			msg.add(objError.getDefaultMessage());
//		}
//		if (existeNumeroEExecao) {
//			msg.add("Já existe essa combinação de NÚMERO e EXCEÇÃO");
//		}
//		return msg;
//	}

}
