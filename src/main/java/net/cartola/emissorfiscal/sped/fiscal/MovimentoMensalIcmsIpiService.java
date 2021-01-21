package net.cartola.emissorfiscal.sped.fiscal;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.contador.Contador;
import net.cartola.emissorfiscal.contador.ContadorService;
import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.documento.DocumentoFiscalService;
import net.cartola.emissorfiscal.loja.Loja;
import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaService;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;
import net.cartola.emissorfiscal.produto.ProdutoUnidadeService;

/**
 * @date 21 de jan. de 2021
 * @author robson.costa
 */
@Service
class MovimentoMensalIcmsIpiService implements BuscaMovimentacaoMensal<MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(MovimentoMensalIcmsIpi.class.getName());
	
	@Autowired
	private DocumentoFiscalService docFiscService;
	
	@Autowired
	private DocumentoFiscalItemService docFiscalItemService;
	
	@Autowired
	private PessoaService pessoaService;
	
//	@Autowired
//	private PESSOA ALTERADO SERVICE
	
//	@Autowired
//	private PRODUTO ALTERADO SERVICE
	
//	produto 
	
	@Autowired
	private ProdutoUnidadeService prodUnidService;
	
//	LOJA SERVICE
	
	@Autowired
	private ContadorService contadorService;
	
	@Override
	public MovimentoMensalIcmsIpi buscarMovimentacoesDoPeriodo(Loja loja, Long contadorId, LocalDate dataInicio, LocalDate dataFim) {
		LOG.log(Level.INFO, "Inicio da busca das movimentações para a LOJA {0}, no PERIODO de {1} a {2} " , new Object[]{loja, dataInicio, dataFim});

		MovimentoMensalIcmsIpi movimentoMensalIcmsIpi = new MovimentoMensalIcmsIpi();
		
		Optional<Pessoa> lojaEmitOrDest = pessoaService.findByCnpj(String.valueOf(loja.getCnpj()));
		List<DocumentoFiscal> listDocFiscal = docFiscService.findByPeriodoCadastroAndLoja(dataInicio, dataFim, lojaEmitOrDest.get());
		// Buscando os Id e todas as Pessoas, envolvidas nas movimentações (emitentes e destinatarios)
		Set<Long> pessoasId = listDocFiscal.stream().map(docFiscal -> docFiscal.getEmitente().getId()).collect(toSet());
		pessoasId.addAll(listDocFiscal.stream().map(docFiscal -> docFiscal.getDestinatario().getId()).collect(toSet()));
		List<Pessoa> listCadPessoas = pessoaService.findByIdsIn(pessoasId);
		
		// TODO  Buscando todas as Pessoas Envolvidas, que tiveram alguma alteração necessária para o SPED ICMS IPI
		
		// TODO Buscando todos os Itens Assim como Suas Unidades
		List<DocumentoFiscalItem> listItens = docFiscalItemService.findItensByVariosDocumentoFiscal(listDocFiscal);
		Set<String> unidadesSet = listItens.stream().map(DocumentoFiscalItem::getUnidade).collect(toSet());
		List<ProdutoUnidade> listProdUnid = prodUnidService.findByListSiglas(unidadesSet);

		Contador contador = contadorService.findOne(contadorId).get();

		// TODO Buscando todos os produtos Movimentados, que tiveram, alguma alteração que seja necessária informar no SPED ICMS IPI
		
		
		// ########## Setando os valores buscados acima que, para retornar o Obj de MOVIMENTACAO MENSAL ##########
		movimentoMensalIcmsIpi.setListDocumentoFiscal(listDocFiscal);
		movimentoMensalIcmsIpi.setListCadastros(listCadPessoas);
		
//		TODO	-> SETAR no obj de movimentos --> 	Pessoa/Cadastro alterado sped
		
		movimentoMensalIcmsIpi.setListItens(listItens);
		
//		TODO	-> SETAR no obj de movimentos --> 	Produto alterado Sped
		
		movimentoMensalIcmsIpi.setListProdUnid(listProdUnid);
		movimentoMensalIcmsIpi.setLoja(loja);
		movimentoMensalIcmsIpi.setContador(contador);
		movimentoMensalIcmsIpi.setDataInicio(dataInicio);
		movimentoMensalIcmsIpi.setDataFim(dataFim);
		
		LOG.log(Level.INFO, "Terminado a busca das movimentações para a LOJA {0}" ,loja);
		return movimentoMensalIcmsIpi;
	}
	
	

	
}
