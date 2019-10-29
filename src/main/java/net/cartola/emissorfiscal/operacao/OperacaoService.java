package net.cartola.emissorfiscal.operacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Service
public class OperacaoService {
	
	@Autowired
	OperacaoRepository repository;
	
	public List<Operacao> findAll() {
		return repository.findAll();
	}
	
	public Operacao save(Operacao operacao) {
		return repository.saveAndFlush(operacao);
	}

	public List<Operacao> findByParteDaDescricao(String descricaoOperacao) {
		return repository.findOperacaoByParteDaDescricao(descricaoOperacao);
	}

//	public List<Operacao> findByDescricao(String descricaoOperacao) {
	public boolean isOperacaoExistente(String descricaoOperacao) {
		return repository.findOperacaoByDescricao(descricaoOperacao).isPresent();
	}
	
	public Optional<Operacao> findOne(Long id) {
		return repository.findById(id); 
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}
	

	public List<String> getMensagensErros(BindingResult bindResult, boolean isOperacaoExistente) {
//	public List<String> getMensagensErros(BindingResult bindResult) {
		List<String> msg = new ArrayList<>();
		for(ObjectError objError : bindResult.getAllErrors()) {
			msg.add(objError.getDefaultMessage());
		}
		if (isOperacaoExistente) {
			msg.add("Já existe uma OPERAÇÃO com essa descrição");
		}
		return msg;
	}
	
}
