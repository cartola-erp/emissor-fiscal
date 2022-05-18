package net.cartola.emissorfiscal.tributacao.estadual;

import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoService;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

@Service
public class TributacaoEstadualService {
	
	@Autowired
	private TributacaoEstadualRepository repository;
	
	@Autowired
	protected EstadoService estadoService;

	/**
	 * Com base nos parâmetros de consulta de GnreAliquotaDto (regime tributário emitente, uf origem e destino), será buscado e preenchido a lista de aliquotas da GNRE
	 * 
	 * PS: Atualmente isso somente é usado para o ecommerce, fazer a consulta para calcular quantos de acréscimo cada item terá na venda interestadual
	 * 
	 * @param gnreAliquotaDto
	 * @return
	 */
	public Optional<GnreAliquotaDto> findGnreAliquota(GnreAliquotaDto gnreAliquotaDto) {
		Set<GnreAliquota> setGnreAliquota = new HashSet<>();
		EstadoSigla siglaOrigem = EstadoSigla.valueOf(gnreAliquotaDto.getUfOrigem());
		EstadoSigla siglaDestino = EstadoSigla.valueOf(gnreAliquotaDto.getUfDestino());

		Set<TributacaoEstadual> setTributacoesEstaduais = repository.findByRegimeTributarioAndOrigemSiglaAndDestinoSigla(gnreAliquotaDto.getRegimeTributarioEmitente(), siglaOrigem, siglaDestino);

		setTributacoesEstaduais.forEach(tribEsta -> {
			GnreAliquota gnreAliquota = new GnreAliquota(tribEsta);
			setGnreAliquota.add(gnreAliquota);
		});
		gnreAliquotaDto.setListGnreAliquota(setGnreAliquota);
		return Optional.ofNullable(gnreAliquotaDto);
	}

	
	public List<TributacaoEstadual> findAll() {
		return repository.findAll();
	}
	
	public Page<TributacaoEstadual> findAll(PageRequest pr) {
		return repository.findAll(pr);
	}
	
	public Optional<TributacaoEstadual> save(TributacaoEstadual tributacaoEstadual) {
		if (tributacaoEstadual != null) {
			divideTributacaoEstadualPorCem(tributacaoEstadual);
		}
		return Optional.ofNullable(repository.saveAndFlush(tributacaoEstadual));
	}
	
	public List<TributacaoEstadual> saveAll(List<TributacaoEstadual> tributacaoEstaduals) {
		tributacaoEstaduals.forEach(tributacaoEstadual -> {
			if (tributacaoEstadual != null) {
				divideTributacaoEstadualPorCem(tributacaoEstadual);
			}
		});
		return repository.saveAll(tributacaoEstaduals);
	}

	public List<TributacaoEstadual> findTributacaoEstadualByNcm(Ncm ncm) {
		return repository.findByNcm(ncm);
	}
	
	public Optional<TributacaoEstadual> findOne(Long id) {
		return repository.findById(id); 
	}
	
	public List<TributacaoEstadual> findTributacaoEstadualByNcms(Collection<Ncm> ncms) {
		return repository.findByNcmIn(ncms);
	}
	
	public Page<TributacaoEstadual> findTributacaoEstadualByNcms(List<Ncm> ncms, PageRequest pr) {
		return repository.findByNcmIn(ncms, pr);
	}
	
	public List<TributacaoEstadual> findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(DocumentoFiscal docuFisc) {
		Operacao operacao = docuFisc.getOperacao();
		Optional<Estado> opUfOrigem = estadoService.findBySigla(docuFisc.getEmitente().getEndereco().getUf());
		Optional<Estado> opUfDestino = operacao.isInterestatual()
				? estadoService.findBySigla(docuFisc.getDestinatario().getEndereco().getUf())
				: opUfOrigem;
		RegimeTributario regimeTributarioEmitente = docuFisc.getEmitente().getRegimeTributario();
		Set<Finalidade> finalidades = docuFisc.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(toSet());

		return this.findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(operacao, opUfOrigem.get(), opUfDestino.get(),
				regimeTributarioEmitente, finalidades, docuFisc.getNcms());
	}
	
	public List<TributacaoEstadual> findTribuEstaByOperUfOrigemUfDestinoRegTribuEFinalidadeENcms(Operacao operacao, Estado estadoOrigem, Estado estadoDestino, RegimeTributario regimeTributario,
			Collection<Finalidade> finalidade, Collection<Ncm> ncms) {
		return repository.findByOperacaoAndEstadoOrigemAndEstadoDestinoAndRegimeTributarioAndFinalidadeInAndNcmIn(operacao, estadoOrigem, estadoDestino, regimeTributario, finalidade, ncms);
	}

	
	public void deleteById(long id) {
		repository.deleteById(id);
	}
	
	private void divideTributacaoEstadualPorCem(TributacaoEstadual tributacaoEstadual) {
		tributacaoEstadual.setIcmsAliquota(tributacaoEstadual.getIcmsAliquota().divide(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsAliquotaDestino(tributacaoEstadual.getIcmsAliquotaDestino().divide(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsBase(tributacaoEstadual.getIcmsBase().divide(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsIva(tributacaoEstadual.getIcmsIva().divide(new BigDecimal(100D)));
		tributacaoEstadual.setFcpAliquota(tributacaoEstadual.getFcpAliquota().divide(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsStAliquota(tributacaoEstadual.getIcmsStAliquota().divide(new BigDecimal(100D)));
	}
	
	public void multiplicaTributacaoEstadualPorCem(TributacaoEstadual tributacaoEstadual) {
		tributacaoEstadual.setIcmsAliquota(tributacaoEstadual.getIcmsAliquota().multiply(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsAliquotaDestino(tributacaoEstadual.getIcmsAliquotaDestino().multiply(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsBase(tributacaoEstadual.getIcmsBase().multiply(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsIva(tributacaoEstadual.getIcmsIva().multiply(new BigDecimal(100D)));
		tributacaoEstadual.setFcpAliquota(tributacaoEstadual.getFcpAliquota().multiply(new BigDecimal(100D)));
		tributacaoEstadual.setIcmsStAliquota(tributacaoEstadual.getIcmsStAliquota().multiply(new BigDecimal(100D)));
	}
	
	// ====================  "VALIDAÇÕES" =================== 

	public List<String> getMensagensErros(BindingResult bindResult, boolean existeNumeroEExecao) {
		List<String> msg = new ArrayList<>();
		for(ObjectError objError : bindResult.getAllErrors()) {
			msg.add(objError.getDefaultMessage());
		}
		if (existeNumeroEExecao) {
			msg.add("Já existe essa combinação de NÚMERO e EXCEÇÃO");
		}
		return msg;
	}

}
