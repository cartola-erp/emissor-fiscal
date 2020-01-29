package net.cartola.emissorfiscal.tributacao.estadual;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.operacao.Operacao;

@Service
public class TributacaoEstadualService {
	
	@Autowired
	private TributacaoEstadualRepository repository;
	
	public List<TributacaoEstadual> findAll() {
		return repository.findAll();
	}
	
	public Optional<TributacaoEstadual> save(TributacaoEstadual tributacaoEstadual) {
		if (tributacaoEstadual.getId() == null) {
			tributacaoEstadual.setIcmsAliquota(tributacaoEstadual.getIcmsAliquota().divide(new BigDecimal(100D)));
			tributacaoEstadual.setIcmsAliquotaDestino(tributacaoEstadual.getIcmsAliquotaDestino().divide(new BigDecimal(100D)));
			tributacaoEstadual.setIcmsBase(tributacaoEstadual.getIcmsBase().divide(new BigDecimal(100D)));
			tributacaoEstadual.setIcmsIva(tributacaoEstadual.getIcmsIva().divide(new BigDecimal(100D)));
		}
		return Optional.ofNullable(repository.saveAndFlush(tributacaoEstadual));
	}
	
	public List<TributacaoEstadual> saveAll(List<TributacaoEstadual> tributacaoEstaduals) {
		tributacaoEstaduals.forEach(tributacaoEstadual -> {
			if (tributacaoEstadual.getId() == null) {
				tributacaoEstadual.setIcmsAliquota(tributacaoEstadual.getIcmsAliquota().divide(new BigDecimal(100D)));
				tributacaoEstadual.setIcmsAliquotaDestino(tributacaoEstadual.getIcmsAliquotaDestino().divide(new BigDecimal(100D)));
				tributacaoEstadual.setIcmsBase(tributacaoEstadual.getIcmsBase().divide(new BigDecimal(100D)));
				tributacaoEstadual.setIcmsIva(tributacaoEstadual.getIcmsIva().divide(new BigDecimal(100D)));
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
	
	public List<TributacaoEstadual> findTributacaoEstadualByOperacaoENcms(Operacao operacao, Collection<Ncm> ncms) {
		return repository.findByOperacaoAndNcmIn(operacao, ncms);
	}

	public void deleteById(long id) {
		repository.deleteById(id);
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
