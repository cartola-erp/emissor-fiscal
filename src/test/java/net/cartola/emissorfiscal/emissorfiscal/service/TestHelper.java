package net.cartola.emissorfiscal.emissorfiscal.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItemRepository;
import net.cartola.emissorfiscal.documento.DocumentoFiscalRepository;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoRepository;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmRepository;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoRepository;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualRepository;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalRepository;

/**
 * 8 de nov de 2019
 * 
 * @author gregory.feijon
 */

@Component
public class TestHelper {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private OperacaoRepository operacaoRepository;

	@Autowired
	private NcmRepository ncmRepository;

	@Autowired
	private DocumentoFiscalRepository docFiscalRepository;

	@Autowired
	private DocumentoFiscalItemRepository docFiscalItemRepository;

	@Autowired
	private TributacaoEstadualRepository tributacaoEstadualRepository;

	@Autowired
	private TributacaoFederalRepository tributacaoFederalRepository;

	public void criarEstados() {
		List<Estado> estados = new LinkedList<>();
		String[][] data = { { "SP", "São Paulo" }, { "MG", "Minas Gerais" }, { "RS", "Rio Grande do Sul" },
				{ "PR", "Paraná" } };

		for (String[] dados : data) {
			int aux = 0;
			Estado estado = new Estado();
			estado.setSigla(EstadoSigla.valueOf(dados[aux++]));
			estado.setNome(dados[aux++]);
			estados.add(estado);
		}
		estadoRepository.saveAll(estados);
	}

	public void criarOperacoes() {
		List<Operacao> operacoes = new LinkedList<>();
		String[][] data = { { "Venda" }, { "Compra" }, { "Devolução" }, { "Exportação" }, { "Remessa" }, { "Retorno" },
				{ "Venda Consignada" }, { "Venda para entrega futura" } };

		for (String[] dados : data) {
			int aux = 0;
			Operacao operacao = new Operacao();
			operacao.setDescricao(dados[aux]);
			operacoes.add(operacao);
		}
		operacaoRepository.saveAll(operacoes);
	}

	public void criarNcm() {
		List<Ncm> ncms = new LinkedList<>();
		String[][] data = { { "12345678", "43", "Essa é uma DESCRIÇÃO do PRIMEIRO NCM para o teste" },
				{ "89101112", "32", "Essa é uma DESCRIÇÃO do SEGUNDO NCM para o teste" },
				{ "34561287", "54", "Essa é uma DESCRIÇÃO do TERCEIRO NCM para o teste" } };

		for (String[] dados : data) {
			int aux = 0;
			Ncm ncm = new Ncm();
			ncm.setNumero(Integer.parseInt(dados[aux++]));
			ncm.setExcecao(Integer.parseInt(dados[aux++]));
			ncm.setDescricao(dados[aux++]);
			ncms.add(ncm);
		}
		ncmRepository.saveAll(ncms);
	}

	public void cleanUp() {
		estadoRepository.deleteAll();
		operacaoRepository.deleteAll();
		ncmRepository.deleteAll();
		tributacaoEstadualRepository.deleteAll();
		tributacaoFederalRepository.deleteAll();
		docFiscalItemRepository.deleteAll();
		docFiscalRepository.deleteAll();
	}
}
