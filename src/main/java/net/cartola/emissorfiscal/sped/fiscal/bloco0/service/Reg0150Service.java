package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.pessoa.Pessoa;
import net.cartola.emissorfiscal.pessoa.PessoaAlteradoSped;
import net.cartola.emissorfiscal.pessoa.PessoaEndereco;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0150;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados;
import net.cartola.emissorfiscal.util.NumberUtil;
import net.cartola.emissorfiscal.util.StringUtil;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0150Service implements MontaGrupoDeRegistroList<Reg0150, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0150Service.class.getName());

//	private Map<Long, PessoaAlteradoSped> mapPessoaAlteradoPorCnpjAnt = new HashMap<>();
	
	private Map<String, PessoaAlteradoSped> mapPessoaAlteradoPorCnpjNovo = new HashMap<>();
	private Map<String, PessoaAlteradoSped> mapPessoaAlteradoPorCpfNovo = new HashMap<>();
	
	@Override
	public List<Reg0150> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0150 ");
		List<Pessoa> listCadPessoa = movimentosIcmsIpi.getListCadastros();
		List<PessoaAlteradoSped> listCadAlteradoSped = movimentosIcmsIpi.getListCadastrosAlteradosSped();
		populaMapCadPessoaAlterado(listCadPessoa, listCadAlteradoSped);

		List<Reg0150> listReg0150 = new ArrayList<>();
		
		listCadPessoa.stream().forEach(pessoa -> {
			Reg0150 reg0150 = new Reg0150();
			PessoaEndereco pessEnd = pessoa.getEndereco();
			
			reg0150.setCodPart(getCodPart(pessoa));
			reg0150.setNome(pessoa.getNome());
			reg0150.setCodPais(1058);		// 1058 --> Cod Brasil | Tabela 3.2.1 - EFD ICMS IPI
			reg0150.setCnpj(pessoa.getCnpj());
			reg0150.setCpf(pessoa.getCpf());
			reg0150.setIe(pessoa.getIe().toString());
			reg0150.setCodMun(pessEnd.getIbgeCodigo());
			reg0150.setSuframa(NumberUtil.getNullSafeForUI(pessoa.getCodSuframa()));
			reg0150.setEnd(pessEnd.getLogradouroEndereco());
			reg0150.setNum(pessEnd.getEnderecoNumero());
			reg0150.setCompl(pessEnd.getComplementoEndereco());
			reg0150.setBairro(pessEnd.getBairro());
			
			reg0150.setReg0175(montaReg0175(pessoa));
			
			listReg0150.add(reg0150);
		});
		LOG.log(Level.INFO, "Grupo de Registro 0150, terminado: {0} " ,listReg0150);
		return listReg0150;
	}

	private String getCodPart(Pessoa pessoa) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(8);
		nf.setMaximumFractionDigits(8);
//		StringBuilder codPart = new StringBuilder(pessoa.getCodigoErp()).append(0).append(pessoa.getLojaErp());  
		String codPart = pessoa.getCodigoErp() + "0"+ pessoa.getLojaErp();  
		String codParFormatado = StringUtil.somenteNumeros(nf.format(Integer.valueOf(codPart)));
		return codParFormatado;
//		return nf.format(codPart);
	}
	
	private List<Reg0175> montaReg0175(Pessoa pessoa) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0175 ");

		List<Reg0175> listReg0175 = null;
		if (mapPessoaAlteradoPorCnpjNovo.containsKey(pessoa.getCnpj())) {
			PessoaAlteradoSped pessAlteradoSped = mapPessoaAlteradoPorCnpjNovo.get(pessoa.getCnpj());
			List<Reg0175CamposAlterados> listCampAlterado = findFieldsUpdates(pessAlteradoSped);
			listReg0175 = preencheReg0175(listCampAlterado, pessAlteradoSped);
		} else if (mapPessoaAlteradoPorCpfNovo.containsKey(pessoa.getCpf())) {
			PessoaAlteradoSped pessAlteradoSped = mapPessoaAlteradoPorCpfNovo.get(pessoa.getCpf());
			List<Reg0175CamposAlterados> listCampAlterado = findFieldsUpdates(pessAlteradoSped);
			listReg0175 = preencheReg0175(listCampAlterado, pessAlteradoSped);
		}
		LOG.log(Level.INFO, "Grupo de Registro 0175, terminado: {0} " ,listReg0175);
		return listReg0175;
	}
	
	/**
	 * Cria o Registro 0175: Para cada campo alterado de uma determinada Pessoa
	 * @param listCampAlterado
	 * @param pessAlteradoSped
	 * @return List<Reg0175> com todos os campos alterados de uma pessoa, no periodo do SPED
	 */
	private List<Reg0175> preencheReg0175(List<Reg0175CamposAlterados> listCampAlterado, PessoaAlteradoSped pessAlteradoSped) {
		List<Reg0175> listReg0175 = new ArrayList<>();
		for (Reg0175CamposAlterados campoAlterado : listCampAlterado) {
			Reg0175 reg0175 = new Reg0175();
			reg0175.setDtAlt(pessAlteradoSped.getDtAlteracaoCadastro());

			switch (campoAlterado) {
			case NOME:
				reg0175.setNrCamp(Reg0175CamposAlterados.NOME);
				reg0175.setContAnt(pessAlteradoSped.getNomeAnt());
				break;
			case COD_PAIS:
				reg0175.setNrCamp(Reg0175CamposAlterados.COD_PAIS);
				reg0175.setContAnt(Integer.toString(pessAlteradoSped.getCodPaisAnt()));
				break;
			case CNPJ:
				reg0175.setNrCamp(Reg0175CamposAlterados.CNPJ);
				reg0175.setContAnt(pessAlteradoSped.getCnpjAnt());
				break;
			case CPF:
				reg0175.setNrCamp(Reg0175CamposAlterados.CPF);
				reg0175.setContAnt(pessAlteradoSped.getCpfAnt());
				break;
			case COD_MUN:
				reg0175.setNrCamp(Reg0175CamposAlterados.COD_MUN);
				reg0175.setContAnt(Integer.toString(pessAlteradoSped.getCodMunAnt()));
				break;
			case SUFRAMA:
				reg0175.setNrCamp(Reg0175CamposAlterados.SUFRAMA);
				reg0175.setContAnt(pessAlteradoSped.getSuframaAnt());
				break;
			case END:
				reg0175.setNrCamp(Reg0175CamposAlterados.END);
				reg0175.setContAnt(pessAlteradoSped.getEnderecoAnt());
				break;
			case NUM:
				reg0175.setNrCamp(Reg0175CamposAlterados.NUM);
				reg0175.setContAnt(Integer.toString(pessAlteradoSped.getNumeroAnt()));
				break;
			case COMPL:
				reg0175.setNrCamp(Reg0175CamposAlterados.COMPL);
				reg0175.setContAnt(pessAlteradoSped.getComplementoAnt());
				break;
			case BAIRRO:
				reg0175.setNrCamp(Reg0175CamposAlterados.BAIRRO);
				reg0175.setContAnt(pessAlteradoSped.getBairroAnt());
				break;
			}
			listReg0175.add(reg0175);
		}
		return listReg0175;
	}

	/**
	 * Procura os campos que foram alterados para aquela pessoa
	 * @param pessAlterado
	 * @return A lista de campos que foram alterado a PESSOA, no periodo do SPED
	 */
	private List<Reg0175CamposAlterados> findFieldsUpdates(PessoaAlteradoSped pessAlterado) {
		List<Reg0175CamposAlterados> listCamposAlterados = new ArrayList<>();

		if (!pessAlterado.getNomeAnt().equalsIgnoreCase(pessAlterado.getNomeNovo())) {
			listCamposAlterados.add(Reg0175CamposAlterados.NOME);
		}
		if(pessAlterado.getCodPaisAnt() != pessAlterado.getCodMunNovo()) {
			listCamposAlterados.add(Reg0175CamposAlterados.COD_PAIS);
		}
		if (pessAlterado.getCodMunAnt() != pessAlterado.getCodMunNovo()) {
			listCamposAlterados.add(Reg0175CamposAlterados.COD_MUN);
		}
		if (!pessAlterado.getSuframaAnt().equalsIgnoreCase(pessAlterado.getSuframaNovo())) {
			listCamposAlterados.add(Reg0175CamposAlterados.SUFRAMA);
		}
		if (!pessAlterado.getEnderecoAnt().equalsIgnoreCase(pessAlterado.getEnderecoNovo())) {
			listCamposAlterados.add(Reg0175CamposAlterados.END);
		}
		if (pessAlterado.getNumeroAnt() != pessAlterado.getNumeroNovo()) {
			listCamposAlterados.add(Reg0175CamposAlterados.NUM);
		}
		if (!pessAlterado.getComplementoAnt().equalsIgnoreCase(pessAlterado.getComplementoNovo())) {
			listCamposAlterados.add(Reg0175CamposAlterados.COMPL);
		}
		if (!pessAlterado.getBairroAnt().equalsIgnoreCase(pessAlterado.getBairroNovo())) {
			listCamposAlterados.add(Reg0175CamposAlterados.BAIRRO);
		}
		return listCamposAlterados;
	}


	/* Ver como irá funcionar a atualização do cadastro de pessoa. Ex.:
	 * Quando atualizar no ERP, manda atualizar aqui no emissor-fiscal ?
	 * Ou somente atualiza aqui no emissorfiscal, quando mandarem algum DocFiscal, que aquela Pessoa esteja envolvida !!!? */
	private void populaMapCadPessoaAlterado(List<Pessoa> listCadPessoa, List<PessoaAlteradoSped> listCadPessoaAlterado) {
		if (mapPessoaAlteradoPorCnpjNovo.isEmpty()) {
//			listCadPessoaAlterado.stream().forEach(pessoaAlterado -> mapPessoaAlteradoPorCnpjNovo
//					.put(pessoaAlterado.getCnpjNovo(), pessoaAlterado));
			listCadPessoaAlterado.stream().filter(pessAlterado -> !pessAlterado.getCnpjNovo().isEmpty()).forEach(
					pessoaAlterado -> mapPessoaAlteradoPorCnpjNovo.put(pessoaAlterado.getCnpjNovo(), pessoaAlterado));
		}
		
		if (mapPessoaAlteradoPorCpfNovo.isEmpty()) {
			listCadPessoaAlterado.stream().filter(pessAlterado -> !pessAlterado.getCpfNovo().isEmpty())
					.forEach(pessAlterado -> mapPessoaAlteradoPorCpfNovo.put(pessAlterado.getCpfNovo(), pessAlterado));
		}
	}
	
	
	
	

}
