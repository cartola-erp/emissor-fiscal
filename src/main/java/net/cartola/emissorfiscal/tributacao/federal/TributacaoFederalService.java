package net.cartola.emissorfiscal.tributacao.federal;

import static java.util.stream.Collectors.toSet;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;

/**
 * 6 de nov de 2019
 * 
 * @author gregory.feijon
 */

@Service
public class TributacaoFederalService {

	@Autowired
	private TributacaoFederalRepository tributacaoFederalRepository;

	public List<TributacaoFederal> findAll() {
		return tributacaoFederalRepository.findAll();
	}
	
	public Page<TributacaoFederal> findAll(PageRequest pr) {
		return tributacaoFederalRepository.findAll(pr);
	}

	public Optional<TributacaoFederal> save(TributacaoFederal tributacaoFederal) {
//		if (tributacaoFederal.getId() == null) {
		if (tributacaoFederal != null) {

			Long tribuId = tributacaoFederal.getId();
			Optional<TributacaoFederal> isTribuExiste = Optional.ofNullable(tributacaoFederalRepository.findByIdTribu(tribuId));
			if(isTribuExiste.isPresent()) {
				divideTributacaoFederalPorCem(tributacaoFederal);
				tributacaoFederal.setDataAlteracao(new Date());
				tributacaoFederal.setAlteradoPor(SecurityContextHolder.getContext().getAuthentication().getName());
			}else{
				divideTributacaoFederalPorCem(tributacaoFederal);
				tributacaoFederal.setDataCriacao(new Date());
				tributacaoFederal.setCriadoPor(SecurityContextHolder.getContext().getAuthentication().getName());
			}
		}
		return Optional.ofNullable(tributacaoFederalRepository.saveAndFlush(tributacaoFederal));
	}

	public List<TributacaoFederal> saveAll(List<TributacaoFederal> tributacoesFederais) {
		tributacoesFederais.forEach(tributacaoEstadual -> {
			if (tributacaoEstadual != null) {
				divideTributacaoFederalPorCem(tributacaoEstadual);
			}
		});
		return tributacaoFederalRepository.saveAll(tributacoesFederais);
	}

	public List<TributacaoFederal> findTributacaoFederalByNcm(Ncm ncm) {
		return tributacaoFederalRepository.findByNcm(ncm);
	}
	
	public List<TributacaoFederal> findTributacaoFederalByVariosNcms(Collection<Ncm> ncms) {
		return tributacaoFederalRepository.findByNcmIn(ncms);
	}
	
	public Page<TributacaoFederal> findTributacaoFederalByVariosNcms(Collection<Ncm> ncms, PageRequest pr) {
		return tributacaoFederalRepository.findByNcmIn(ncms, pr);
	}
	
	public Set<TributacaoFederal> findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(DocumentoFiscal docuFisc) {
		Set<Finalidade> finalidades = docuFisc.getItens().stream().map(DocumentoFiscalItem::getFinalidade).collect(toSet());
		RegimeTributario regimeTributarioEmitente = docuFisc.getEmitente().getRegimeTributario();
		
		return this.findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(docuFisc.getOperacao(), 
				regimeTributarioEmitente, finalidades, docuFisc.getNcms());
	}
	
	public Set<TributacaoFederal> findTributacaoFederalByVariosNcmsEOperacaoEFinalidadeERegimeTributario(Operacao operacao,RegimeTributario regimeTributario,
			Collection<Finalidade> finalidade, Collection<Ncm> ncms) {
		return tributacaoFederalRepository.findByOperacaoAndRegimeTributarioAndFinalidadeInAndNcmIn(operacao, regimeTributario, finalidade, ncms );
	}

	public List<TributacaoFederal> findTributacaoFederalByOperacao(Operacao operacao) {
		return tributacaoFederalRepository.findByOperacao(operacao);
	}

	public List<TributacaoFederal> findTributacaoFederalByVariasOperacoes(List<Operacao> operacoes) {
		return tributacaoFederalRepository.findByOperacaoIn(operacoes);
	}

	public Optional<TributacaoFederal> findOne(Long id) {
		return tributacaoFederalRepository.findById(id);
	}

	public void deleteById(Long id) {
		tributacaoFederalRepository.deleteById(id);
	}
	
	private void divideTributacaoFederalPorCem(TributacaoFederal tributacaoFederal) {
		tributacaoFederal.setPisAliquota(tributacaoFederal.getPisAliquota().divide(new BigDecimal(100D)));
		tributacaoFederal.setPisBase(tributacaoFederal.getPisBase().divide(new BigDecimal(100D)));
		tributacaoFederal.setCofinsAliquota(tributacaoFederal.getCofinsAliquota().divide(new BigDecimal(100D)));
		tributacaoFederal.setCofinsBase(tributacaoFederal.getCofinsBase().divide(new BigDecimal(100D)));
		tributacaoFederal.setIpiAliquota(tributacaoFederal.getIpiAliquota().divide(new BigDecimal(100D)));
		tributacaoFederal.setIpiBase(tributacaoFederal.getIpiBase().divide(new BigDecimal(100D)));
	}
	
	public void multiplicaTributacaoFederalPorCem(TributacaoFederal tributacaoFederal) {
		tributacaoFederal.setPisAliquota(tributacaoFederal.getPisAliquota().multiply(new BigDecimal(100D)));
		tributacaoFederal.setPisBase(tributacaoFederal.getPisBase().multiply(new BigDecimal(100D)));
		tributacaoFederal.setCofinsAliquota(tributacaoFederal.getCofinsAliquota().multiply(new BigDecimal(100D)));
		tributacaoFederal.setCofinsBase(tributacaoFederal.getCofinsBase().multiply(new BigDecimal(100D)));
		tributacaoFederal.setIpiAliquota(tributacaoFederal.getIpiAliquota().multiply(new BigDecimal(100D)));
		tributacaoFederal.setIpiBase(tributacaoFederal.getIpiBase().multiply(new BigDecimal(100D)));
	}
}
