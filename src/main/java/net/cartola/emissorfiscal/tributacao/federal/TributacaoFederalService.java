package net.cartola.emissorfiscal.tributacao.federal;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Optional<TributacaoFederal> save(TributacaoFederal tributacaoFederal) {
//		if (tributacaoFederal.getId() == null) {
		if (tributacaoFederal != null) {
			divideTributacaoFederalPorCem(tributacaoFederal);
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
