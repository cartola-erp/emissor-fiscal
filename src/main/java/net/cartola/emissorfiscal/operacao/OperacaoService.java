package net.cartola.emissorfiscal.operacao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import net.cartola.emissorfiscal.util.StringUtil;

@Service
public class OperacaoService {

	@Autowired
	private OperacaoRepository operacaoRepository;

	public List<Operacao> findAll() {
		List<Operacao> listOperacoes = operacaoRepository.findAll();
		listOperacoes.sort(Comparator.comparing(Operacao::getId));
		return listOperacoes;
	}

	public List<Operacao> findByIdsIn(Set<Long> operacaoIds) {
		return operacaoRepository.findByIdIn(operacaoIds);
	}
	
	public Optional<Operacao> save(Operacao operacao) {
		String descricao = StringUtil.prepareString(operacao.getDescricao());
		operacao.setDescricao(descricao);
		return Optional.ofNullable(operacaoRepository.saveAndFlush(operacao));
	}

	public List<Operacao> findByParteDaDescricao(String descricaoOperacao) {
		return operacaoRepository.findOperacaoByParteDaDescricao(descricaoOperacao);
	}

	public Optional<Operacao> findOperacaoByDescricao(String descricao) {
		return operacaoRepository.findOperacaoByDescricaoIgnoreCase(descricao);
	}

	public boolean isOperacaoExistente(String descricaoOperacao) {
		return operacaoRepository.findOperacaoByDescricaoIgnoreCase(descricaoOperacao).isPresent();
	}

	public Optional<Operacao> findOne(Long id) {
		return operacaoRepository.findById(id);
	}

	public void deleteById(long id) {
		operacaoRepository.deleteById(id);
	}

	public List<String> getMensagensErros(BindingResult bindResult, boolean isOperacaoExistente) {
		List<String> msg = new ArrayList<>();
		for (ObjectError objError : bindResult.getAllErrors()) {
			msg.add(objError.getDefaultMessage());
		}
		if (isOperacaoExistente) {
			msg.add("Já existe uma OPERAÇÃO com essa descrição");
		}
		return msg;
	}

}
