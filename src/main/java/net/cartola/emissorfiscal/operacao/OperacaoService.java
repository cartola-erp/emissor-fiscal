package net.cartola.emissorfiscal.operacao;

import java.util.*;

import javax.validation.Valid;

import javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
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

	public Page<Operacao> rtnTodos(Pageable pageable){

		return operacaoRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id")));
	}

	public List<Operacao> findByIdsIn(Set<Long> operacaoIds) {
		return operacaoRepository.findByIdIn(operacaoIds);
	}
	
	public Optional<Operacao> save(Operacao operacao) {
		String desc = operacao.getDescricao();
		List<Operacao> isOperacaoExiste = operacaoRepository.findOperacaoByParteDaDescricao(desc);
		if(!isOperacaoExiste.isEmpty()){
			operacao.setDataAlteracao(new Date());
			operacao.setAlteradoPor(SecurityContextHolder.getContext().getAuthentication().getName());
		}else {
			operacao.setDataCriacao(new Date());
			operacao.setCriadoPor(SecurityContextHolder.getContext().getAuthentication().getName());
		}
		return Optional.ofNullable(operacaoRepository.saveAndFlush(operacao));
	}

	public Optional<Operacao> save(Operacao oldOperacao, Operacao operacaoToUpdate) {
		String descricao = StringUtil.prepareString(operacaoToUpdate.getDescricao());
		operacaoToUpdate.setDescricao(descricao);
		// Para manter as mesmas configuracoes da opeacão que estava anteriormente
		operacaoToUpdate.setDevolucao(oldOperacao.isDevolucao());
		operacaoToUpdate.setRemessaParaFornecedor(oldOperacao.isRemessaParaFornecedor());
		return save(operacaoToUpdate);
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

	// TA EXCLUINDO O REGISTRO NO BANCO, PRECISO ARRUMAR DEPOIS.
	public void deleteById(long id) {
		Optional<Operacao> operacao = Optional.of(findOne(id).get());
		operacao.get().setExcluidoPor(SecurityContextHolder.getContext().getAuthentication().getName());
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
