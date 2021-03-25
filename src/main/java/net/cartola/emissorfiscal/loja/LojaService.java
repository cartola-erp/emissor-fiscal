package net.cartola.emissorfiscal.loja;

import static net.cartola.emissorfiscal.util.StringUtil.somenteNumeros;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Service
public class LojaService {

	@Autowired
	private LojaRepository lojaRepository;
	
	public List<Loja> findAll() {
		return lojaRepository.findAll();
	}
	
	public Optional<Loja> save(Loja loja) {
		loja = retiraCaracteresEspeciais(loja);
		return Optional.ofNullable(lojaRepository.saveAndFlush(loja));
	}
		
	public Optional<Loja> findOne(Long id) {
		return lojaRepository.findById(id);
	}
	
	public Optional<Loja> findByCnpj(String cnpj) {
		return lojaRepository.findLojaByCnpj(cnpj);
	}
	
	private Loja retiraCaracteresEspeciais(Loja loja) {
		String cnpj = somenteNumeros(loja.getCnpj());
		String ie = somenteNumeros(loja.getIe());
		String cnae = somenteNumeros(loja.getCnae());
		String cep = somenteNumeros(loja.getCep());
		
		loja.setCnpj(cnpj);
		loja.setIe(ie);
		loja.setCnae(cnae);
		loja.setCep(cep);
		
		return loja;
	}
	
	
}
