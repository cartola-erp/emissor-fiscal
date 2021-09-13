package net.cartola.emissorfiscal.estado;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> findAll() {
		return estadoRepository.findAll();
	}

	public Optional<Estado> save(Estado estado) {
		return Optional.ofNullable(estadoRepository.saveAndFlush(estado));
	}

	public Optional<Estado> findBySigla(EstadoSigla siglaEstado) {	
		return estadoRepository.findEstadoBySigla(siglaEstado);
	}
	
//	public Map<EstadoSigla, Estado> findBySiglaIn(List<EstadoSigla> listSigla) {
//		Set<Estado> setEstado = estadoRepository.findBySiglaIn(listSigla);
//		Map<EstadoSigla, Estado> mapEstadoPorSigla = setEstado.stream().collect(Collectors.toMap(Estado::getSigla, (Estado estado) -> estado));
//		return mapEstadoPorSigla;
//	}

	public Optional<Estado> findOne(Long id) {
		return estadoRepository.findById(id);
	}

	public void deleteById(long id) {
		estadoRepository.deleteById(id);
	}

	public List<String> getMensagensErros(BindingResult bindResult, boolean existeEsseEstado) {
		List<String> msg = new ArrayList<>();
		for (ObjectError objError : bindResult.getAllErrors()) {
			msg.add(objError.getDefaultMessage());
		}
		if (existeEsseEstado) {
			msg.add("Esse estado, já está cadastrado");
		}
		return msg;
	}

}
