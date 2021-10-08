package net.cartola.emissorfiscal.ncm;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class NcmService {

	@Autowired
	private NcmRepository ncmRepository;

	public List<Ncm> findAll() {
		return ncmRepository.findAll();
	}
	
	public Page<Ncm> findAll(PageRequest pr) {
		return ncmRepository.findAll(pr);
	}

	public Optional<Ncm> save(Ncm ncm) {
		boolean isNumeroCorreto = isNumeroNcmCorreto(ncm.getNumero());
		if (!isNumeroCorreto) {
			return Optional.empty();
		}
		return Optional.ofNullable(ncmRepository.saveAndFlush(ncm));
	}

	/**
	 * Irá retornar um mapa de NCMS (Por Numero/Classe e Exececao), que estão persistidos no Banco de dados
	 * @param listNumerosNcms
	 * @return
	 */
	public Map<Integer, Map<Integer, Ncm>> getMapNcmByNumeroIn(Collection<Integer> listNumerosNcms) {
		List<Ncm> listNcmPersistida = this.findNcmByNumeroIn(listNumerosNcms);
		Map<Integer, Map<Integer, Ncm>> mapNcmPorNumeroEExccao = listNcmPersistida
				.stream().collect(groupingBy(Ncm::getNumero, toMap(Ncm::getExcecao, (Ncm ncm) -> ncm )));
		return mapNcmPorNumeroEExccao;
	}
	
	public List<Ncm> findNcmByNumeroIn(Collection<Integer> listNumerosNcms) {
		return ncmRepository.findNcmByNumeroIn(listNumerosNcms);
	}
	
	public List<Ncm> findByNumero(int numeroNcm) {
		return ncmRepository.findNcmByNumero(numeroNcm);
	}
	
	public Page<Ncm> findNcmByNumero(int numero, PageRequest pr) {
		return ncmRepository.findNcmByNumero(numero, pr);
	}
	
	public Optional<Ncm> findNcmByNumeroAndExcecao(int numero, int excecao) {
		return ncmRepository.findNumeroAndExcecao(numero, excecao);
	}
	
	public Optional<Ncm> findOne(Long id) {
		return ncmRepository.findById(id);
	}

	public void deleteById(long id) {
		ncmRepository.deleteById(id);
	}

	// ==================== "VALIDAÇÕES" ===================
	public boolean existeNumeroEExcecao(Ncm ncm) {
		return ncmRepository.findNumeroAndExcecao(ncm.getNumero(), ncm.getExcecao()).isPresent();
	}

	public boolean isNumeroNcmCorreto(int numero) {
		String numeroNcm = Integer.toString(numero);
		if (numeroNcm.length() <= 7 || numeroNcm.length() >= 9) {
			return false;
		}
		return true;
	}

	public boolean isExcecaoCorreto(int excecao) {
		String excecaoNcm = Integer.toString(excecao);
		if (excecaoNcm.length() >= 3 || excecaoNcm.length() == 1) {
			return false;
		}
		return true;
	}

	public List<String> getMensagensErros(BindingResult bindResult, boolean existeNumeroEExecao) {
		List<String> msg = new ArrayList<>();
		for (ObjectError objError : bindResult.getAllErrors()) {
			msg.add(objError.getDefaultMessage());
		}
		if (existeNumeroEExecao) {
			msg.add("Já existe essa combinação de NÚMERO e EXCEÇÃO");
		}
		return msg;
	}
}
