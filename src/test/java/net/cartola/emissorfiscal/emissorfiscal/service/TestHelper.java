package net.cartola.emissorfiscal.emissorfiscal.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemRepository;
import net.cartola.emissorfiscal.documento.DocumentoFiscalRepository;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoRepository;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmRepository;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoRepository;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaRepository;
import net.cartola.emissorfiscal.pessoa.PessoaTipo;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualRepository;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalRepository;
import net.cartola.emissorfiscal.usuario.Perfil;
import net.cartola.emissorfiscal.usuario.Usuario;
import net.cartola.emissorfiscal.usuario.UsuarioPerfil;
import net.cartola.emissorfiscal.usuario.UsuarioService;

/**
 * 8 de nov de 2019
 * 
 * @author gregory.feijon
 */

@Component
public class TestHelper {

	private static final String NCM1 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1);
	private static final String NCM2 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_2);
	private static final String NCM3 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_3); 

	public static final String OPERACAO_VENDA = "Venda";
	public static final String OPERACAO_VENDA_INTERESTADUAL = "Venda Interestadual";
	public static final String OPERACAO_COMPRA = "Compra";
	public static final String OPERACAO_DEVOLUÇAO = "Devolução";
	public static final String OPERACAO_DEVOLUCAO_FORNECEDOR = "Devolução para o fornecedor";
	public static final String OPERACAO_DEVOLUCAO_FORNECEDOR_FORA_ESTADO = "Devolução para o fornecedor fora do estado";
	public static final String OPERACAO_DEVOLUCAO_CLIENTE = "Devolução do cliente";
	public static final String OPERACAO_REMESSA = "Remessa";
	public static final String OPERACAO_REMESSA_CONSIGNADA = "Remessa consignada";

	private static final String PESSOA_CNPJ = "12345678901234";
	private static final String PESSOA_UF_SP = EstadoSigla.SP.toString();
	private static final String PESSOA_REGIME_APURACAO = "Regime de apuração 1";
	private static final String PESSOA_TIPO_FISICA = PessoaTipo.FISICA.toString();
	                
	private static final String PESSOA_CNPJ_2 = "02329838429395";
	private static final String PESSOA_UF_MG = EstadoSigla.MG.toString();
	private static final String PESSOA_TIPO_JURIDICA = PessoaTipo.JURIDICA.toString();
	                
	private static final String DOC_FISCAL_SERIE_1 = "262265758";
	private static final String DOC_FISCAL_SERIE_2 = "883591913";
	private static final String DOC_FISCAL_SERIE_3 = "283871141";
	private static final String DOC_FISCAL_SERIE_4 = "357115603";
                    
	private static final String DOC_FISCAL_NUMERO_1 = "82211429431055";
	private static final String DOC_FISCAL_NUMERO_2 = "45436809221754";
	private static final String DOC_FISCAL_NUMERO_3 = "65791541873496";
	private static final String DOC_FISCAL_NUMERO_4 = "30802553106899";
	
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private OperacaoRepository operacaoRepository;

	@Autowired
	private NcmRepository ncmRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private DocumentoFiscalRepository docFiscalRepository;

	@Autowired
	private DocumentoFiscalItemRepository docFiscalItemRepository;

	@Autowired
	private TributacaoEstadualRepository tributacaoEstadualRepository;

	@Autowired
	private TributacaoFederalRepository tributacaoFederalRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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

	private List<Operacao> defineOperacoes() {
		List<Operacao> operacoes = new LinkedList<>();
		String[][] data = { { OPERACAO_VENDA }, { OPERACAO_VENDA_INTERESTADUAL }, { OPERACAO_COMPRA },
				{ OPERACAO_DEVOLUÇAO }, { OPERACAO_DEVOLUCAO_FORNECEDOR },
				{ OPERACAO_DEVOLUCAO_FORNECEDOR_FORA_ESTADO }, { OPERACAO_DEVOLUCAO_CLIENTE }, { OPERACAO_REMESSA },
				{ OPERACAO_REMESSA_CONSIGNADA } };

		for (String[] dados : data) {
			int aux = 0;
			Operacao operacao = new Operacao();
			operacao.setDescricao(dados[aux]);
			operacoes.add(operacao);
		}
		operacaoRepository.saveAll(operacoes);
		return operacoes;
	}

	private List<Ncm> defineNcms() {
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
	
	private List<Pessoa> criarPessoa() {
		List<Pessoa> pessoas = new LinkedList<>();
		String[][] data = { {PESSOA_CNPJ, PESSOA_UF_SP, PESSOA_REGIME_APURACAO, PESSOA_TIPO_JURIDICA},
							{PESSOA_CNPJ_2, PESSOA_UF_SP, PESSOA_REGIME_APURACAO, PESSOA_TIPO_FISICA},
							{PESSOA_CNPJ, PESSOA_UF_MG, PESSOA_REGIME_APURACAO, PESSOA_TIPO_FISICA},
							{PESSOA_CNPJ_2, PESSOA_UF_MG, PESSOA_REGIME_APURACAO, PESSOA_TIPO_JURIDICA} };
		
		for (String[] dados : data) {
			int aux = 0;
			Pessoa pessoa = new Pessoa();
			pessoa.setCnpj(Long.valueOf(dados[aux++]));
			pessoa.setUf(EstadoSigla.valueOf(dados[aux++]));
			pessoa.setRegimeApuracao(dados[aux++]);
			pessoa.setPessoaTipo(PessoaTipo.valueOf(dados[aux++]));
			pessoas.add(pessoa);
		}
		return pessoaRepository.saveAll(pessoas);
	}

	public void criarDocumentoFiscal() {
		List<DocumentoFiscal> documentosFiscais = new LinkedList<>();
		List<Operacao> operacoes = defineOperacoes();
		List<Ncm> ncms = defineNcms();
		List<Pessoa> pessoas = criarPessoa();
		
//		String[][] data = { { "tipo1", "SP", "Emitente Regime Apuração 1", "SP", "FISICA", OPERACAO_VENDA },
//				{ "tipo2", "SP", "Emitente Regime Apuração 2", "SP", "JURIDICA", OPERACAO_VENDA },
//				{ "tipo3", "SP", "Emitente Regime Apuração 3", "MG", "FISICA", OPERACAO_VENDA_INTERESTADUAL },
//				{ "tipo4", "SP", "Emitente Regime Apuração 4", "MG", "JURIDICA", OPERACAO_VENDA_INTERESTADUAL } };
		String[][] data = { { "NFE", PESSOA_TIPO_JURIDICA, PESSOA_TIPO_FISICA, PESSOA_UF_SP, OPERACAO_VENDA, DOC_FISCAL_SERIE_1, DOC_FISCAL_NUMERO_1 },
				{ "SAT", PESSOA_TIPO_JURIDICA, PESSOA_TIPO_JURIDICA, PESSOA_UF_SP, OPERACAO_VENDA, DOC_FISCAL_SERIE_2, DOC_FISCAL_NUMERO_2 },
				{ "CTE", PESSOA_TIPO_JURIDICA, PESSOA_TIPO_JURIDICA, PESSOA_UF_MG, OPERACAO_VENDA_INTERESTADUAL, DOC_FISCAL_SERIE_3, DOC_FISCAL_NUMERO_3 },
				{ "ECF", PESSOA_TIPO_JURIDICA, PESSOA_TIPO_FISICA, PESSOA_UF_MG, OPERACAO_VENDA_INTERESTADUAL, DOC_FISCAL_SERIE_4, DOC_FISCAL_NUMERO_4 } };

		
		for (String[] dados : data) {
			int aux = 0;
			DocumentoFiscal docFiscal = new DocumentoFiscal();
			docFiscal.setTipo(dados[aux++]);
			String emitenteTipo = dados[aux++];
			docFiscal.setEmitente(pessoas.stream().filter(p -> p.getPessoaTipo().toString().equals(emitenteTipo)).findAny().get());
			
			String destinatarioTipo = dados[aux++];
			String destinatarioUf = dados[aux++];
			docFiscal.setDestinatario(pessoas.stream()
					.filter(p -> p.getPessoaTipo().toString().equals(destinatarioTipo))
					.filter(p -> p.getUf().toString().equals(destinatarioUf)).findAny().get());
			String operacaoDescricao = dados[aux++];
			docFiscal.setOperacao(operacoes.stream()
					.filter(operacao -> operacao.getDescricao().equals(operacaoDescricao)).findAny().get());
			docFiscal.setSerie(Long.valueOf(dados[aux++]));
			docFiscal.setNumero(Long.valueOf(dados[aux++]));
			docFiscal.setItens(criarDocumentoFiscalItem(ncms));
			documentosFiscais.add(docFiscal);
		}
		documentosFiscais.stream().forEach(docFiscal -> {
			docFiscal.getItens().stream().forEach(docItem -> {
				docItem.setDocumentoFiscal(docFiscal);
			});
		});
		docFiscalRepository.saveAll(documentosFiscais);
	}

	private List<DocumentoFiscalItem> criarDocumentoFiscalItem(List<Ncm> ncms) {
		List<DocumentoFiscalItem> documentoFiscalItens = new LinkedList<>();

		String[][] data = { { "CONSUMO", "10", "5", "5102", NCM1 }, { "CONSUMO", "5", "5", "5105", NCM2 },
				{ "CONSUMO", "10", "5", "5102", NCM3 } };

		for (String[] dados : data) {
			int aux = 0;
			DocumentoFiscalItem docFiscalItem = new DocumentoFiscalItem();
			docFiscalItem.setFinalidade(Finalidade.valueOf(dados[aux++]));
			docFiscalItem.setQuantidade(new BigDecimal(dados[aux++]));
			docFiscalItem.setValorUnitario(new BigDecimal(dados[aux++]));
			docFiscalItem.setCfop(Integer.parseInt(dados[aux++]));
			int ncmCodigo = Integer.parseInt(dados[aux++]);
			docFiscalItem.setNcm(ncms.stream().filter(ncm -> ncm.getNumero() == ncmCodigo).findAny().get());
			documentoFiscalItens.add(docFiscalItem);
		}

		return documentoFiscalItens;
	}

	public void criarUsuarioRoot() {
		Optional<Usuario> opUsuario = usuarioService.findByLogin("root");
		if (!opUsuario.isPresent()) {
			Usuario u = new Usuario();
			u.setLogin("root");
			u.setNome("FengHuang");
			u.setEmail("ningYi@riseofphoenix.cn");
			u.setSenha(bCryptPasswordEncoder.encode("root"));
			
			UsuarioPerfil perfilUm = new UsuarioPerfil();
			perfilUm.setPerfil(Perfil.ROLE_CONTADOR);
			
			UsuarioPerfil perfilDois = new UsuarioPerfil();
			perfilDois.setPerfil(Perfil.ROLE_CONTADOR);
			
			u.setPerfis(Arrays.asList(perfilUm, perfilDois));
			usuarioService.save(u);
		}
	}
	
	public void limpaBanco() {
		tributacaoEstadualRepository.deleteAll();
		tributacaoFederalRepository.deleteAll();
		estadoRepository.deleteAll();
		docFiscalItemRepository.deleteAll();
		docFiscalRepository.deleteAll();
		operacaoRepository.deleteAll();
		ncmRepository.deleteAll();
	}
}
