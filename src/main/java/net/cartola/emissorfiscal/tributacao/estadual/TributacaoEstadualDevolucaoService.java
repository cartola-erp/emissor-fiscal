package net.cartola.emissorfiscal.tributacao.estadual;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.operacao.Operacao;

/**
 * @date 30 de set. de 2021
 * @author robson.costa
 */
@Service
public class TributacaoEstadualDevolucaoService {

	@Autowired
	private TributacaoEstadualDevolucaoRepository tribEstaDevolucaoRepository;
	
	
	public Set<TributacaoEstadualDevolucao> findByOperacao(Operacao operacao) {
		return tribEstaDevolucaoRepository.findByOperacao(operacao);
	}
	
	
}
