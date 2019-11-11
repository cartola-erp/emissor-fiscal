package net.cartola.emissorfiscal.emissorfiscal.service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemRepository;
import net.cartola.emissorfiscal.documento.DocumentoFiscalRepository;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.documento.Pessoa;
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

	private static final String NCM1 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1);
	private static final String NCM2 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_2);
	private static final String NCM3 = "34561287";

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

	public List<Ncm> defineNcms() {
		List<Ncm> ncms = new LinkedList<>();
		String[][] data = { { NCM1, "43", "Essa é uma DESCRIÇÃO do PRIMEIRO NCM para o teste" },
				{ NCM2, "32", "Essa é uma DESCRIÇÃO do SEGUNDO NCM para o teste" },
				{ NCM3, "54", "Essa é uma DESCRIÇÃO do TERCEIRO NCM para o teste" } };

		for (String[] dados : data) {
			int aux = 0;
			Ncm ncm = new Ncm();
			ncm.setNumero(Integer.parseInt(dados[aux++]));
			ncm.setExcecao(Integer.parseInt(dados[aux++]));
			ncm.setDescricao(dados[aux++]);
			ncms.add(ncm);
		}
		ncmRepository.saveAll(ncms);
		return ncms;
	}

	public void criarDocumentoFiscal() {
		List<DocumentoFiscal> documentosFiscais = new LinkedList<>();

		String[][] data = { { "tipo1", "SP", "Emitente Regime Apuração 1", "SP", "FISICA" },
				{ "tipo2", "SP", "Emitente Regime Apuração 2", "SP", "JURIDICA" },
				{ "tipo3", "SP", "Emitente Regime Apuração 3", "MG", "FISICA" },
				{ "tipo4", "SP", "Emitente Regime Apuração 4", "MG", "JURIDICA" } };

		int aux = 0;
		for (String[] dados : data) {
			DocumentoFiscal docFiscal = new DocumentoFiscal();
			docFiscal.setTipo(dados[aux++]);
			docFiscal.setEmitenteUf(EstadoSigla.valueOf(dados[aux++]));
			docFiscal.setEmitenteRegimeApuracao(dados[aux++]);
			docFiscal.setDestinatarioUf(EstadoSigla.valueOf(dados[aux++]));
			docFiscal.setDestinatarioPessoa(Pessoa.valueOf(dados[aux++]));
			docFiscal.setItens(criarDocumentoFiscalItem());
			documentosFiscais.add(docFiscal);
		}
		docFiscalRepository.saveAll(documentosFiscais);
		documentosFiscais.stream().forEach(docFiscal -> docFiscalItemRepository.saveAll(docFiscal.getItens()));
	}

	public List<DocumentoFiscalItem> criarDocumentoFiscalItem() {
		List<DocumentoFiscalItem> documentoFiscalItens = new LinkedList<>();
		List<Ncm> ncms = defineNcms();
		String[][] data = { { "CONSUMO", "10", "5506", NCM1 }, { "CONSUMO", "5", "5506", NCM2 },
				{ "REVENDA", "10", "5566", NCM3 } };

		int aux = 0;
		for (String[] dados : data) {
			DocumentoFiscalItem docFiscalItem = new DocumentoFiscalItem();
			docFiscalItem.setFinalidade(Finalidade.valueOf(dados[aux++]));
			docFiscalItem.setQuantidade(new BigDecimal(dados[aux++]));
			docFiscalItem.setValorUnitario(new BigDecimal(dados[aux++]));
			int ncmCodigo = Integer.parseInt(dados[aux++]);
			docFiscalItem.setNcm(ncms.stream().filter(ncm -> ncm.getNumero() == ncmCodigo).findAny().get());
			documentoFiscalItens.add(docFiscalItem);
		}

		return documentoFiscalItens;
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
