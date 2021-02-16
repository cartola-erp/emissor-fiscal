package net.cartola.emissorfiscal.emissorfiscal.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemRepository;
import net.cartola.emissorfiscal.documento.DocumentoFiscalRepository;
import net.cartola.emissorfiscal.documento.IndicadorDeOperacao;
import net.cartola.emissorfiscal.documento.Finalidade;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.estado.Estado;
import net.cartola.emissorfiscal.estado.EstadoRepository;
import net.cartola.emissorfiscal.estado.EstadoSigla;
import net.cartola.emissorfiscal.model.TributacaoFederalBuilder;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.ncm.NcmRepository;
import net.cartola.emissorfiscal.operacao.Operacao;
import net.cartola.emissorfiscal.operacao.OperacaoRepository;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaEndereco;
import net.cartola.emissorfiscal.pessoa.PessoaEnderecoRepository;
import net.cartola.emissorfiscal.pessoa.PessoaRepository;
import net.cartola.emissorfiscal.pessoa.PessoaTipo;
import net.cartola.emissorfiscal.pessoa.RegimeTributario;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadual;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualRepository;
import net.cartola.emissorfiscal.tributacao.estadual.TributacaoEstadualService;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederal;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalRepository;
import net.cartola.emissorfiscal.tributacao.federal.TributacaoFederalService;
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

	public static final String NCM_ICMS_00 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_1);
	public static final String NCM_ICMS_00_DIFAL_FCP = Integer.toString(7089000);

	public static final String NCM_ICMS_10 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_2);
	private static final String NCM_ICMS_20 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_3); 
	public static final String NCM_ICMS_30 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_4); 
	private static final String NCM_ICMS_40 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_5); 

	public static final String NCM_ICMS_60 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_6); 
	private static final String NCM_ICMS_70 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_7);
	private static final String NCM_ICMS_90 = Integer.toString(NcmServiceLogicTest.NCM_NUMERO_REGISTRO_8);
	
	public static final String OPERACAO_VENDA = "Venda";
	public static final String OPERACAO_VENDA_INTERESTADUAL = "Venda Interestadual";
	public static final String OPERACAO_COMPRA = "Compra";
	public static final String OPERACAO_DEVOLUÇAO = "Devolução";
	public static final String OPERACAO_DEVOLUCAO_FORNECEDOR = "Devolução para o fornecedor";
	public static final String OPERACAO_DEVOLUCAO_FORNECEDOR_FORA_ESTADO = "Devolução para o fornecedor fora do estado";
	public static final String OPERACAO_DEVOLUCAO_CLIENTE = "Devolução do cliente";
	public static final String OPERACAO_REMESSA = "Remessa";
	public static final String OPERACAO_REMESSA_CONSIGNADA = "Remessa consignada";

	public static final String PESSOA_EMITENTE_CNPJ = "5437537000137";
	public static final String PESSOA_EMITENTE_UF_SP = EstadoSigla.SP.toString();
	public static final String PESSOA_EMITENTE_REGI_TRIBU_REAL = RegimeTributario.NORMAL.toString();
	public static final String PESSOA_TIPO_JURIDICA = PessoaTipo.JURIDICA.toString();
	                
	public static final String PESSOA_DEST_CNPJ_SP = "53124228000150";
	public static final String PESSOA_DEST_UF_SP = EstadoSigla.SP.toString();
	public static final String PESSOA_TIPO_FISICA = PessoaTipo.FISICA.toString();
	
	public static final String PESSOA_DEST_CNPJ_RJ = "2737439000399";
	public static final String PESSOA_DEST_UF_RJ = EstadoSigla.RJ.toString();
//	public static final String PESSOA_TIPO_FISICA = PessoaTipo.FISICA.toString();
	                
	public static final String DOC_FISCAL_SERIE_1 = "262265758";
	private static final String DOC_FISCAL_SERIE_2 = "883591913";
	private static final String DOC_FISCAL_SERIE_3 = "283871141";
	private static final String DOC_FISCAL_SERIE_4 = "357115603";
                    
	public static final String DOC_FISCAL_NUMERO_1 = "82211429431055";
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
	private PessoaEnderecoRepository pessoaEndRepository;
	
	@Autowired
	private DocumentoFiscalRepository docFiscalRepository;

	@Autowired
	private DocumentoFiscalItemRepository docFiscalItemRepository;

	@Autowired
	private TributacaoEstadualRepository tributacaoEstadualRepository;
	
	@Autowired
	private TributacaoEstadualService icmsService;

	@Autowired
	private TributacaoFederalRepository tributacaoFederalRepository;
	
	@Autowired 
	private TributacaoFederalService tributacaoFederalService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void criarEstados() {
		List<Estado> estados = new LinkedList<>();
		String[][] data = { { "SP", "São Paulo" }, { "MG", "Minas Gerais" }, { "RS", "Rio Grande do Sul" },
				{ "RJ", "Rio de Janeiro" } };

		for (String[] dados : data) {
			int aux = 0;
			Estado estado = new Estado();
			estado.setSigla(EstadoSigla.valueOf(dados[aux++]));
			estado.setNome(dados[aux++]);
			estados.add(estado);
		}
		estadoRepository.saveAll(estados);
	}

	public List<Operacao> criarOperacoes() {
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
		return operacaoRepository.saveAll(operacoes);
	}

	public List<Ncm> criarNcms() {
		List<Ncm> ncms = new LinkedList<>();
		String[][] data = { { NCM_ICMS_00, "0", "Varetas e perfis de borracha vulcan.n/alveol.n/endurec." },
				{ NCM_ICMS_00_DIFAL_FCP, "0", "Outros legumes de vagem,frescos ou refrigerados" },
				{ NCM_ICMS_10, "0", "Peças para caixas de marchas p/veiculos automoveis" },
				{ NCM_ICMS_20, "0", "Outras carnes,de suino,congeladas" },
				{ NCM_ICMS_30, "0", "Outros suinos,vivos,de peso inferior a 50kg" },
				{ NCM_ICMS_40, "0", "Outros suinos,vivos,de peso igual ou superior a 50kg" },
				{ NCM_ICMS_60, "0", "Depurador por convers.catalitica de gases de escap.veic" },
				{ NCM_ICMS_70, "0", "Outros (motores diesel/semidiesel,p/veic.cap.87)" },
				{ NCM_ICMS_90, "0", "Estacionários, de potência normal iso superior a 412,5kw (550hp), segundo norma iso 3046/1" }};
		
		for (String[] dados : data) {
			int aux = 0;
			Ncm ncm = new Ncm();
			ncm.setNumero(Integer.parseInt(dados[aux++]));
			ncm.setExcecao(Integer.parseInt(dados[aux++]));
			ncm.setDescricao(dados[aux++]);
			ncms.add(ncm);
		}
		return ncmRepository.saveAll(ncms);
	}
	
	public List<Pessoa> criarPessoa() {
		List<Pessoa> pessoas = new LinkedList<>();
		String[][] data = { {PESSOA_EMITENTE_CNPJ, PESSOA_EMITENTE_UF_SP, PESSOA_EMITENTE_REGI_TRIBU_REAL, PESSOA_TIPO_JURIDICA},
							{PESSOA_DEST_CNPJ_SP, PESSOA_EMITENTE_UF_SP, PESSOA_EMITENTE_REGI_TRIBU_REAL, PESSOA_TIPO_JURIDICA},
							{PESSOA_DEST_CNPJ_RJ, PESSOA_DEST_UF_RJ, PESSOA_EMITENTE_REGI_TRIBU_REAL, PESSOA_TIPO_FISICA}};
		
		for (String[] dados : data) {
			int aux = 0;
			Pessoa pessoa = new Pessoa();
			pessoa.setCnpj(dados[aux++]);
			PessoaEndereco endereco = new PessoaEndereco();
			endereco.setUf(EstadoSigla.valueOf(dados[aux++]));
			pessoa.setEndereco(endereco);
			pessoa.setRegimeTributario(RegimeTributario.valueOf(dados[aux++]));
			pessoa.setPessoaTipo(PessoaTipo.valueOf(dados[aux++]));
			pessoas.add(pessoa);
		}
//		documentosFiscais.stream().forEach(docFiscal -> {
//			docFiscal.getItens().stream().forEach(docItem -> {
//				docItem.setDocumentoFiscal(docFiscal);
//			});
//		});
	

		
		return pessoaRepository.saveAll(pessoas);
	}
	
	
	public TributacaoEstadual criarTribEstaVenda(Ncm ncm, int cst, EstadoSigla ufDestino, BigDecimal iva, BigDecimal icmsBase, 
			BigDecimal fcpAliq, BigDecimal icmsStAliq, int cfop) {
		Estado estadoOrigem = estadoRepository.findEstadoBySigla(EstadoSigla.SP).get();
		Estado estadoDestino = estadoRepository.findEstadoBySigla(ufDestino).get();

		Operacao operacao = operacaoRepository.findOperacaoByDescricaoIgnoreCase(OPERACAO_VENDA).get();
		TributacaoEstadual icms = new TributacaoEstadual();
		
		icms.setEstadoOrigem(estadoOrigem);
		icms.setEstadoDestino(estadoDestino);
		icms.setOperacao(operacao);
		icms.setNcm(ncm);
		icms.setFinalidade(Finalidade.CONSUMO);
		icms.setRegimeTributario(RegimeTributario.NORMAL);
		icms.setIcmsCst(cst);
		icms.setIcmsBase(icmsBase);
		
		icms.setIcmsAliquota(new BigDecimal(18D));
		icms.setIcmsIva(iva);
		icms.setIcmsAliquotaDestino(new BigDecimal(12D));
		icms.setFcpAliquota(fcpAliq);
		icms.setIcmsStAliquota(icmsStAliq);
		icms.setCest(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_CEST);
		icms.setCfop(cfop);
//		icms.setCest(101010);			// Nem sei se existe, qql valor somente para testar
		icms.setMensagem(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_MENSAGEM);

		return icmsService.save(icms).get();
	}
	
	//  Criar TRIBUTAÇÕES ESTADUAIS, CONFORME LISTA DE NCM e a OPERAÇÃO
	public List<TributacaoEstadual> criarTribEstaPorNcmsEOperDentroDeSP(List<Ncm> listNcms, Operacao operacao) {
		Estado estadoOrigem = estadoRepository.findEstadoBySigla(EstadoSigla.SP).get();
		List<TributacaoEstadual> listTributacoes = new ArrayList<>();
		listNcms.stream().forEach(ncm -> {
			TributacaoEstadual icms = new TributacaoEstadual();
			icms.setEstadoOrigem(estadoOrigem);
			icms.setEstadoDestino(estadoOrigem);
			icms.setOperacao(operacao);
			icms.setNcm(ncm);
			icms.setFinalidade(Finalidade.CONSUMO);
			icms.setRegimeTributario(RegimeTributario.NORMAL);
			icms.setIcmsCst(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_CST);
			icms.setIcmsBase(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_BASE);
			icms.setIcmsAliquota(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA);
			icms.setIcmsIva(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_IVA);
			icms.setIcmsAliquotaDestino(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_ALIQUOTA_DESTINO);
			icms.setCest(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_CEST);
			icms.setMensagem(TributacaoEstadualLogicTest.TRIBUTACAO_ESTADUAL_ICMS_MENSAGEM);
			listTributacoes.add(icms);
		});
		List<TributacaoEstadual> listIcms = icmsService.saveAll(listTributacoes);
		return listIcms;
	}
	
	//  Criar TRIBUTAÇÕES FEDERAIS, CONFORME LISTA DE NCM e a OPERAÇÃO
	public List<TributacaoFederal> criarTributacaoFederal(List<Ncm> listNcms, Operacao operacao) {
		List<TributacaoFederal> listTribFederal = new LinkedList<>();
		listNcms.stream().forEach(ncm -> {
			TributacaoFederalBuilder tributFedBuilder = new TributacaoFederalBuilder().withNcm(ncm)
					.withOperacao(operacao).withPisCst(TributacaoFederalServiceLogicTest.PIS_CST)
					.withFinalidadeo(Finalidade.CONSUMO)
					.withRegimeTributario(RegimeTributario.NORMAL)
					.withPisBase(TributacaoFederalServiceLogicTest.PIS_BASE)
					.withPisAliquota(TributacaoFederalServiceLogicTest.PIS_ALIQUOTA)
					.withCofinsCst(TributacaoFederalServiceLogicTest.COFINS_CST)
					.withCofinsBase(TributacaoFederalServiceLogicTest.COFINS_BASE)
					.withCofinsAliquota(TributacaoFederalServiceLogicTest.COFINS_ALIQUOTA)
					.withIpiCst(TributacaoFederalServiceLogicTest.IPI_CST)
					.withIpiBase(TributacaoFederalServiceLogicTest.IPI_BASE)
					.withIpiAliquota(TributacaoFederalServiceLogicTest.IPI_ALIQUOTA);
			listTribFederal.add(tributFedBuilder.build());

		});
		return tributacaoFederalService.saveAll(listTribFederal);
	}
	
//	private boolean verificaListaTribFederal(List<TributacaoFederal> listTribFederal, DocumentoFiscal docFiscal,
//			DocumentoFiscalItem docFiscalItem) {
//		return listTribFederal.stream()
//				.filter(tribFed -> tribFed.getNcm().getId().equals(docFiscalItem.getNcm().getId())
//						&& tribFed.getOperacao().getId().equals(docFiscal.getOperacao().getId()))
//				.findAny().isPresent();
//	}
	
	public void criarDocumentoFiscal() {
		List<DocumentoFiscal> documentosFiscais = new LinkedList<>();
		List<Operacao> operacoes = criarOperacoes();
		List<Ncm> ncms = criarNcms();
		List<Pessoa> pessoas = criarPessoa();
		
		String[][] data = { { "NFE", PESSOA_TIPO_JURIDICA, PESSOA_TIPO_JURIDICA, PESSOA_EMITENTE_UF_SP, OPERACAO_VENDA, DOC_FISCAL_SERIE_1, DOC_FISCAL_NUMERO_1 },
				{  PESSOA_TIPO_JURIDICA, PESSOA_TIPO_JURIDICA, PESSOA_EMITENTE_UF_SP, OPERACAO_VENDA, DOC_FISCAL_SERIE_2, DOC_FISCAL_NUMERO_2 },
				{  PESSOA_TIPO_JURIDICA, PESSOA_TIPO_JURIDICA, PESSOA_DEST_UF_SP, OPERACAO_VENDA_INTERESTADUAL, DOC_FISCAL_SERIE_3, DOC_FISCAL_NUMERO_3 },
				{  PESSOA_TIPO_JURIDICA, PESSOA_TIPO_JURIDICA, PESSOA_DEST_UF_SP, OPERACAO_VENDA_INTERESTADUAL, DOC_FISCAL_SERIE_4, DOC_FISCAL_NUMERO_4 } };

		
		for (String[] dados : data) {
			int aux = 0;
			DocumentoFiscal docFiscal = new DocumentoFiscal();
			docFiscal.setTipoOperacao(IndicadorDeOperacao.SAIDA);
			String emitenteTipo = dados[aux++];
			docFiscal.setEmitente(pessoas.stream().filter(p -> p.getPessoaTipo().toString().equals(emitenteTipo)).findAny().get());
			
			String destinatarioTipo = dados[aux++];
			String destinatarioUf = dados[aux++];
			docFiscal.setDestinatario(pessoas.stream()
					.filter(p -> p.getPessoaTipo().toString().equals(destinatarioTipo))
					.filter(p -> p.getEndereco().getUf().toString().equals(destinatarioUf)).findAny().get());
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

		String[][] data = { { "CONSUMO", "NACIONAL", "10", "5", "5102", NCM_ICMS_00 }, { "CONSUMO", "NACIONAL", "5", "5", "5105", NCM_ICMS_10 },
				{ "CONSUMO", "NACIONAL", "10", "5", "5102", NCM_ICMS_20 } };

		for (String[] dados : data) {
			int aux = 0;
			DocumentoFiscalItem docFiscalItem = new DocumentoFiscalItem();
			docFiscalItem.setFinalidade(Finalidade.valueOf(dados[aux++]));
			docFiscalItem.setOrigem(ProdutoOrigem.valueOf(dados[aux++]));
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
		pessoaRepository.deleteAll();
	}
}
