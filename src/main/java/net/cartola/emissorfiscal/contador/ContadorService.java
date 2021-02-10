package net.cartola.emissorfiscal.contador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.loja.Loja;


/**
 * @date 13 de jan. de 2021
 * @author robson.costa
 */
@Service
public class ContadorService {
	
	@Autowired
	private ContadorRepository contadorRepository;
	
	public List<Contador> findAll() {
		return contadorRepository.findAll();
	}
	
	public Optional<Contador> save(Contador contador) {
		return Optional.ofNullable(contadorRepository.saveAndFlush(contador));
	}
		
	public Optional<Contador> findOne(Long id) {
		return contadorRepository.findById(id);
	}
	
	public Optional<Contador> findByCnpj(String cnpj) {
		return contadorRepository.findContadorByCnpj(cnpj);
	}
	
	public Optional<Contador> findByCpf(String cpf) {
		return contadorRepository.findContadorByCpf(cpf);
	}
	
	public Optional<Contador> findByCrc(String crc) {
		return contadorRepository.findContadorByCrc(crc);
	}
	
}
