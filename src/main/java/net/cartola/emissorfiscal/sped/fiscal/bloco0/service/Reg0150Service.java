package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.BAIRRO;
import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.COD_MUN;
import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.COD_PAIS;
import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.COMPL;
import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.END;
import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.NOME;
import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.NUM;
import static net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0175CamposAlterados.SUFRAMA;
import static org.flywaydb.core.internal.util.StringUtils.collapseWhitespace;
import static org.flywaydb.core.internal.util.StringUtils.trimLineBreak;
import static org.springframework.util.StringUtils.hasText;

import java.time.LocalDate;
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
import net.cartola.emissorfiscal.util.SpedFiscalUtil;

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
			
			reg0150.setCodPart(SpedFiscalUtil.getCodPart(pessoa));
			reg0150.setNome(pessoa.getNome());
			reg0150.setCodPais(1058);		// 1058 --> Cod Brasil | Tabela 3.2.1 - EFD ICMS IPI
			reg0150.setCnpj(pessoa.getCnpj());
			reg0150.setCpf(pessoa.getCpf());
			
			String ie = (pessoa.getIe() == null || pessoa.getIe() == 0L) ? "" : pessoa.getIe().toString();
			reg0150.setIe(ie);
			reg0150.setCodMun(pessEnd.getIbgeCodigo());
			reg0150.setSuframa(NumberUtil.getNullSafeForUI(pessoa.getCodSuframa()));
			reg0150.setEnd(pessEnd.getLogradouroEndereco());
			reg0150.setNum(pessEnd.getEnderecoNumero());
			
			String complEnd = pessEnd.getComplementoEndereco();
			
			if (complEnd != null) {
				complEnd = collapseWhitespace(trimLineBreak(complEnd));	
			}
			reg0150.setCompl(complEnd);
			reg0150.setBairro(pessEnd.getBairro());
			
			reg0150.setReg0175(montaReg0175(movimentosIcmsIpi, pessoa));
			
			listReg0150.add(reg0150);
		});
		LOG.log(Level.INFO, "Grupo de Registro 0150, terminado: {0} " ,listReg0150);
		return listReg0150;
	}
	
	/**
	 * REG 0175 = Alteração da Tabela de Cadastro de Participante
	 * @param movimentosIcmsIpi 
	 * @param pessoa
	 * @return
	 */
	private List<Reg0175> montaReg0175(MovimentoMensalIcmsIpi movimentosIcmsIpi, Pessoa pessoa) {

		List<Reg0175> listReg0175 = null;
//		if (mapPessoaAlteradoPorCnpjNovo.containsKey(pessoa.getCnpj())) {
//			LOG.log(Level.INFO, "Montando o Grupo de Registro 0175 para o CNPJ: {0} " ,pessoa.getCnpj());
//			PessoaAlteradoSped pessAlteradoSped = mapPessoaAlteradoPorCnpjNovo.get(pessoa.getCnpj());
//			List<Reg0175CamposAlterados> listCampAlterado = findFieldsUpdates(pessAlteradoSped);
//			listReg0175 = preencheReg0175(listCampAlterado, pessAlteradoSped);
//			LOG.log(Level.INFO, "Grupo de Registro 0175 (alteracao de cadastro da PJ), terminado: {0} " ,listReg0175);
//		} else if (mapPessoaAlteradoPorCpfNovo.containsKey(pessoa.getCpf())) {
//			LOG.log(Level.INFO, "Montando o Grupo de Registro 0175 para o CPF: {0} " ,pessoa.getCpf());
//			PessoaAlteradoSped pessAlteradoSped = mapPessoaAlteradoPorCpfNovo.get(pessoa.getCpf());
//			List<Reg0175CamposAlterados> listCampAlterado = findFieldsUpdates(pessAlteradoSped);
//			listReg0175 = preencheReg0175(listCampAlterado, pessAlteradoSped);
//			LOG.log(Level.INFO, "Grupo de Registro 0175 (alteracao de cadastro da PF), terminado: {0} " ,listReg0175);
//		}
//		return listReg0175;
		
		/**
		 * ESTAVA dessa FORMA ACIMA 
		 */
		
		
		// PS: aparentemente a LÓGICA abaixo está errada
		
		PessoaAlteradoSped pessAlteradoSped = null;
		List<Reg0175CamposAlterados> listCampAlterado = null;
		if (mapPessoaAlteradoPorCnpjNovo.containsKey(pessoa.getCnpj())) {
			pessAlteradoSped = mapPessoaAlteradoPorCnpjNovo.get(pessoa.getCnpj());
			listCampAlterado = findFieldsUpdates(pessAlteradoSped);
		} else if (mapPessoaAlteradoPorCpfNovo.containsKey(pessoa.getCpf())) {
			pessAlteradoSped = mapPessoaAlteradoPorCpfNovo.get(pessoa.getCpf());
			listCampAlterado = findFieldsUpdates(pessAlteradoSped);
		}
		
		if (isPessoaAlteradaNoSpedAtual(movimentosIcmsIpi, pessAlteradoSped)) {
			LOG.log(Level.INFO, "Montando o Grupo de Registro 0175 para o CNPJ/CPF: {0} " ,pessoa.getCpf());
			listReg0175 = preencheReg0175(listCampAlterado, pessAlteradoSped);
			LOG.log(Level.INFO, "Grupo de Registro 0175 (alteracao de cadastro da PF/PJ), terminado: {0} " ,listReg0175);
		}

		return listReg0175;
	}
	
	private boolean isPessoaAlteradaNoSpedAtual(MovimentoMensalIcmsIpi movimentosIcmsIpi, PessoaAlteradoSped pessAlteradoSped) {
		if (pessAlteradoSped == null) {
			return false;
		}
		LocalDate dataInicioSped = movimentosIcmsIpi.getDataInicio();
		LocalDate dataFimSped = movimentosIcmsIpi.getDataFim();
		LocalDate dtAlteracaoCadastro = pessAlteradoSped.getDtAlteracaoCadastro();
		
		if ( (dataInicioSped.isBefore(dtAlteracaoCadastro) || dataInicioSped.isEqual(dtAlteracaoCadastro)) && 
				(dataFimSped.isAfter(dtAlteracaoCadastro) || dataFimSped.isEqual(dtAlteracaoCadastro)) ) {
				return true;
		}
		return false;
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
				reg0175.setNrCamp(NOME);
				reg0175.setContAnt(pessAlteradoSped.getNomeAnt());
				break;
			case COD_PAIS:
				reg0175.setNrCamp(COD_PAIS);
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
				reg0175.setNrCamp(COD_MUN);
				reg0175.setContAnt(Integer.toString(pessAlteradoSped.getCodMunAnt()));
				break;
			case SUFRAMA:
				reg0175.setNrCamp(SUFRAMA);
				reg0175.setContAnt(pessAlteradoSped.getSuframaAnt());
				break;
			case END:
				reg0175.setNrCamp(END);
				reg0175.setContAnt(pessAlteradoSped.getEnderecoAnt());
				break;
			case NUM:
				reg0175.setNrCamp(NUM);
				reg0175.setContAnt(Integer.toString(pessAlteradoSped.getNumeroAnt()));
				break;
			case COMPL:
				reg0175.setNrCamp(COMPL);
				reg0175.setContAnt(pessAlteradoSped.getComplementoAnt());
				break;
			case BAIRRO:
				reg0175.setNrCamp(BAIRRO);
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
			listCamposAlterados.add(NOME);
		}
		if(pessAlterado.getCodPaisAnt() != pessAlterado.getCodPaisNovo()) {
			listCamposAlterados.add(COD_PAIS);
		}
		if (pessAlterado.getCodMunAnt() != pessAlterado.getCodMunNovo()) {
			listCamposAlterados.add(COD_MUN);
		}
		if (pessAlterado.getSuframaAnt() != null && pessAlterado.getSuframaNovo() != null &&  !isDiferente(pessAlterado.getSuframaAnt(), pessAlterado.getSuframaNovo())) {
			listCamposAlterados.add(SUFRAMA);
		}
		if (!pessAlterado.getEnderecoAnt().equalsIgnoreCase(pessAlterado.getEnderecoNovo())) {
			listCamposAlterados.add(END);
		}
		if (pessAlterado.getNumeroAnt() != pessAlterado.getNumeroNovo()) {
			listCamposAlterados.add(NUM);
		}
		
		if (pessAlterado.getComplementoAnt() != null && pessAlterado.getComplementoNovo() != null && !isDiferente(pessAlterado.getComplementoAnt(), pessAlterado.getComplementoNovo())) {
			listCamposAlterados.add(COMPL);
		}
		if (!pessAlterado.getBairroAnt().equalsIgnoreCase(pessAlterado.getBairroNovo())) {
			listCamposAlterados.add(BAIRRO);
		}
		return listCamposAlterados;
	}

	private boolean isDiferente(String stringUm, String stringDois) {
		return stringUm.equalsIgnoreCase(stringDois);
	}

	/* Ver como irá funcionar a atualização do cadastro de pessoa. Ex.:
	 * Quando atualizar no ERP, manda atualizar aqui no emissor-fiscal ?
	 * Ou somente atualiza aqui no emissorfiscal, quando mandarem algum DocFiscal, que aquela Pessoa esteja envolvida !!!? */
	private void populaMapCadPessoaAlterado(List<Pessoa> listCadPessoa, List<PessoaAlteradoSped> listCadPessoaAlterado) {
		if (mapPessoaAlteradoPorCnpjNovo.isEmpty()) {
//			listCadPessoaAlterado.stream().forEach(pessoaAlterado -> mapPessoaAlteradoPorCnpjNovo
//					.put(pessoaAlterado.getCnpjNovo(), pessoaAlterado));
			listCadPessoaAlterado.stream().filter(pessAlterado -> hasText(pessAlterado.getCnpjNovo())).forEach(
					pessoaAlterado -> mapPessoaAlteradoPorCnpjNovo.put(pessoaAlterado.getCnpjNovo(), pessoaAlterado));
		}
		
		if (mapPessoaAlteradoPorCpfNovo.isEmpty()) {
			listCadPessoaAlterado.stream().filter(pessAlterado -> hasText(pessAlterado.getCpfNovo()))
					.forEach(pessAlterado -> mapPessoaAlteradoPorCpfNovo.put(pessAlterado.getCpfNovo(), pessAlterado));
		}
	}
	
	
	
	

}
