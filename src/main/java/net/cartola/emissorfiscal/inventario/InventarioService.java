package net.cartola.emissorfiscal.inventario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalParametrosBusca;


/**
 * @date 19 de nov. de 2021
 * @author robson.costa
 */
@Service
public class InventarioService {

	@Autowired
	private InventarioRepository inventarioRepository;

	public List<Inventario> findAll() {
		return inventarioRepository.findAll();
	}
	
	public List<Inventario> findAllByLojaId(Long lojaId) {
		return inventarioRepository.findByLojaId(lojaId);
	}
	
	public Optional<Inventario> save(Inventario inventario) {
		return Optional.ofNullable(inventarioRepository.saveAndFlush(inventario));
	}
		
	public Optional<Inventario> findOne(Long id) {
		return inventarioRepository.findById(id);
	}

	public Optional<Inventario> findByLojaAndPeriodo(Loja loja, MovimentoMensalParametrosBusca paramBuscaSped) {
		return findByLojaAndPeriodo(loja.getId(), paramBuscaSped.getDataInicioInventario(), paramBuscaSped.getDataFimInventario());
	}

	private Optional<Inventario> findByLojaAndPeriodo(Long lojaId, LocalDate dataInicioInventario, LocalDate dataFimInventario) {
		return inventarioRepository.findByLojaIdAndInicioAndFim(lojaId, dataInicioInventario, dataFimInventario);
	}
	
}
