package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaAlteradoSped;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0150;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0150Service implements MontaGrupoDeRegistroList<Reg0150, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0150Service.class.getName());

	private Map<Long, PessoaAlteradoSped> mapPessoaAlteradoPorCnpjAnt = new HashMap<>();
	private Map<Long, PessoaAlteradoSped> mapPessoaAlteradoPorCnpjNovo = new HashMap<>();
	
	@Override
	public List<Reg0150> montarGrupoDeRegistro(MovimentacoesMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0150 ");
		
		List<Pessoa> listCadPessoa = movimentosIcmsIpi.getListCadastros();
		List<PessoaAlteradoSped> listCadAlteradoSped = movimentosIcmsIpi.getListCadastrosAlteradosSped();
		populaMapCadPessoaAlterado(listCadPessoa, listCadAlteradoSped);


		List<Reg0150> listReg0150 = new ArrayList<>();
		
		listCadPessoa.stream().forEach(pessoa -> {
			Reg0150 reg0150 = new Reg0150();
//			reg0150.set
		});
		
		return null;
	}

	/* Ver como irá funcionar a atualização do cadastro de pessoa. Ex.:
	 * Quando atualizar no ERP, manda atualizar aqui no emissor-fiscal ?
	 * Ou somente atualiza aqui no emissorfiscal, quando mandarem algum DocFiscal, que aquela Pessoa esteja envolvida !!!? */
	private void populaMapCadPessoaAlterado(List<Pessoa> listCadPessoa, List<PessoaAlteradoSped> listCadPessoaAlterado) {
		
//		if (mapPessoaAlteradoPorCnpjAnt.isEmpty()) {
//			listCadPessoaAlterado.stream().forEach(pessoaAlterado ->
//				mapPessoaAlteradoPorCnpjAnt.put(pessoaAlterado.getCpfCnpjAnt(), pessoaAlterado)
//			);
//		}
		
		if (mapPessoaAlteradoPorCnpjNovo.isEmpty()) {
//			listCadPessoaAlterado.stream().collect(Collectors.toMap(pessoaAlteradoSped -> PessoaAlteradoSped::getCpfCnpjAnt, 
//			PessoaAlteradoSped -> listCadPessoa.stream()
//			.filter(cadPessoa -> cadPessoa.getCnpj().equals(PessoaAlteradoSped))));
			listCadPessoaAlterado.stream().forEach(pessoaAlterado -> 
			mapPessoaAlteradoPorCnpjNovo.put(pessoaAlterado.getCpfCnpjNovo(), pessoaAlterado)
			);
		}
		
	}
	
	
	
	

}
