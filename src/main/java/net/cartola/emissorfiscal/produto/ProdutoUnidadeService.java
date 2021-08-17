package net.cartola.emissorfiscal.produto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 20 de jan. de 2021
 * @author robson.costa
 */
@Service
public class ProdutoUnidadeService {
	
	@Autowired
	private ProdutoUnidadeRepository prodUnidRepository;
	
	public List<ProdutoUnidade> findAll() {
		return prodUnidRepository.findAll();
	}
	
	public Optional<ProdutoUnidade> save(ProdutoUnidade prodUnid) {
		return Optional.ofNullable(prodUnidRepository.saveAndFlush(prodUnid));
	}
		
	public Optional<ProdutoUnidade> findOne(Long id) {
		return prodUnidRepository.findById(id);
	}
	
	public Optional<ProdutoUnidade> findBySigla(String sigla) {
		return prodUnidRepository.findProdutoUnidadeBySigla(sigla);
	}
	
	public List<ProdutoUnidade> findByListSiglas(Collection<String> listSiglas) {
		 List<ProdutoUnidade> listProdutoUnidade = prodUnidRepository.findProdutoUnidadeBySiglaIn(listSiglas);
		 if (listProdutoUnidade != null) {
			 return listProdutoUnidade;
		 }
		return new ArrayList<>();
	}
	
//	public void deleteById(Long id) {
//		prodUnidRepository.deleteById(id);
//	}
	
}
