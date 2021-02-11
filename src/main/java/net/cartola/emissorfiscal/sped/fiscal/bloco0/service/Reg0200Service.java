package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.ncm.Ncm;
import net.cartola.emissorfiscal.produto.ProdutoAlteradoSped;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0200;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0205;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0205CamposAlterados;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0206;
import net.cartola.emissorfiscal.sped.fiscal.enums.TipoDoItem;
import net.cartola.emissorfiscal.util.StringUtil;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0200Service implements MontaGrupoDeRegistroList<Reg0200, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0200Service.class.getName());
	
	private Map<Integer, ProdutoAlteradoSped> mapItemAlteradoPorProduCodiErp = new HashMap<>();
	
	@Override
	public List<Reg0200> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0200 ");
		// TODO : Mudar a Lista de ITENS para um SET (pois da forma atual, irá repetir os itens)
		// TODO : PS: Acredito que terei que buscar a CEST, p/ TODOS os itens (pois nas entradas eu não tenho) 
		List<DocumentoFiscalItem> listItens = movimentosIcmsIpi.getListItens();
		List<ProdutoAlteradoSped> listProdAlterado = movimentosIcmsIpi.getListProdutoAlteradoSped();
		populaMapItemAlterado(listItens, listProdAlterado);
		
		List<Reg0200> listReg0200 = new ArrayList<>();
		listItens.stream().forEach(item -> {
			Reg0200 reg0200 = new Reg0200();
			Ncm ncm = item.getNcm();
			String ncmString = Integer.toString(ncm.getNumero());
			reg0200.setCodItem(getCodItem(item));
			reg0200.setDescrItem(item.getDescricaoEmpresa());
			reg0200.setCodBarra(item.getEan());
//			reg0200.setCodAntItem(codAntItem);
			reg0200.setUnidInv(item.getUnidade());
			reg0200.setTipoItem(getTipoItem(item));
			reg0200.setCodNcm(ncmString);
			reg0200.setExIpi(Integer.toString(ncm.getExcecao()));
			reg0200.setCodGen(ncmString.substring(0, 2));
//			reg0200.setCodLst(codLst);
			reg0200.setAliqIcms(getAliqIcms(item));
			reg0200.setCest(formataCest(item.getIcmsCest()));
			
			reg0200.setReg0205(montaReg0205ItemAlterado(item));
			reg0200.setReg0206(montaReg0206CodAnp(item));
//			reg0200.setReg0210(reg0210);	// Nos Arquivos que a Gabi passou não vi sendo usados
//			reg0200.setReg0220(reg0220);
			listReg0200.add(reg0200);
		});
		
		
		LOG.log(Level.INFO, "Grupo de Registro 0200, terminado: {0} " ,listReg0200);
		return listReg0200;
	}

	/**
	 *  Cria o Registro 0205 - (Para os itens que tiveram alteração no Codigo ou Descrição)
	 *  
	 * @param item
	 * @return List<Reg0205> - Retorna lista de campos alterados
	 */
	private List<Reg0205> montaReg0205ItemAlterado(DocumentoFiscalItem item) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0205 ");

		List<Reg0205> listReg0205 = null;
		if(mapItemAlteradoPorProduCodiErp.containsKey(item.getProdutoCodigoErp())) {
			ProdutoAlteradoSped itemAlteradoSped = mapItemAlteradoPorProduCodiErp.get(item.getProdutoCodigoErp());
			List<Reg0205CamposAlterados> listCampAlterado = findFieldsUpdates(itemAlteradoSped);
			listReg0205 = preencheReg0205(listCampAlterado, itemAlteradoSped);
		}
		
		LOG.log(Level.INFO, "Grupo de Registro 0205, terminado: {0} " ,listReg0205);
		return listReg0205;
	}
	
	/**
	 * Cria o Registro 0206 - Para informar o código ANP, quando o produto for do tipo óleo
	 * @param item
	 * @return Reg0206 - Com o preenchimento do Código ANP
	 */
	private Reg0206 montaReg0206CodAnp(DocumentoFiscalItem item) {
		Reg0206 reg0206 = null;
		if (item.getCodigoAnp() != 0) {
			LOG.log(Level.INFO, "Montando o Registro 0206 ");
			reg0206 = new Reg0206();
			reg0206.setCodComb(String.valueOf(item.getCodigoAnp()));

			LOG.log(Level.INFO, "Registro 0206, terminado: {0} " , reg0206);
		}
		return reg0206;
	}
	
	private List<Reg0205> preencheReg0205(List<Reg0205CamposAlterados> listCampAlterado, ProdutoAlteradoSped itemAlteradoSped) {
		List<Reg0205> listReg0205 = new ArrayList<>();
		
		for (Reg0205CamposAlterados campoAlterado : listCampAlterado) {
			Reg0205 reg0205 = new Reg0205();
			reg0205.setDtIni(itemAlteradoSped.getDtInicialUtilizacaoDescAnterior());
			reg0205.setDtFim(itemAlteradoSped.getDtFinalUtilizacaoDescAnterior());
			
			switch (campoAlterado) {
			case CODIGO:
				String codAnterior = itemAlteradoSped.getCodigoXAnt().toString() + itemAlteradoSped.getCodigoSequenciaAnt();
				reg0205.setCodAntItem(codAnterior);
				break;
			case DESCRICAO_ITEM:
				reg0205.setDescrAntItem(itemAlteradoSped.getDescricaoAnt());
				break;
			}
			listReg0205.add(reg0205);
		}
		return listReg0205;
	}
	
	private List<Reg0205CamposAlterados> findFieldsUpdates(ProdutoAlteradoSped itemAlteradoSped) {
		List<Reg0205CamposAlterados> listCamposAlterados = new ArrayList<>();
		String codAnterior = itemAlteradoSped.getCodigoXAnt().toString() + itemAlteradoSped.getCodigoSequenciaAnt();
		if(!itemAlteradoSped.getCodigoNovo().equalsIgnoreCase(codAnterior)) {
			listCamposAlterados.add(Reg0205CamposAlterados.CODIGO);
		}
		if (!itemAlteradoSped.getDescricaoAnt().equalsIgnoreCase(itemAlteradoSped.getDescricaoNova())) {
			listCamposAlterados.add(Reg0205CamposAlterados.DESCRICAO_ITEM);
		}
		return listCamposAlterados;
	}


	
	private String getCodItem(DocumentoFiscalItem item) {
		String codItem = item.getCodigoX().toString() + item.getCodigoSequencia();
		return codItem;
	}
	
	private TipoDoItem getTipoItem(DocumentoFiscalItem item) {
		switch (item.getFinalidade()) {
		case COMERCIALIZACAO:
			return TipoDoItem.MERCADORIA_PARA_REVENDA;
		case PATRIMONIO: 
			return TipoDoItem.ATIVO_IMOBILIZADO;
		case CONSUMO:
			return TipoDoItem.MATERIAL_DE_USO_E_CONSUMO;
		default:
			return TipoDoItem.OUTRAS;
		}
	}
	
	private Double getAliqIcms(DocumentoFiscalItem item) {
		BigDecimal icmsAndFcpAliq = item.getIcmsAliquota().add(item.getIcmsFcpAliquota()).multiply(new BigDecimal(100D));
		return icmsAndFcpAliq.doubleValue();
	}

	private static String formataCest(int cest) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(7);
		nf.setMaximumFractionDigits(7);
		return StringUtil.somenteNumeros(nf.format(cest));
	}

	private void populaMapItemAlterado(List<DocumentoFiscalItem> listItens, List<ProdutoAlteradoSped> listProdAlterado) {
		if (mapItemAlteradoPorProduCodiErp.isEmpty()) {
			listProdAlterado.stream().forEach(prodAlterado -> mapItemAlteradoPorProduCodiErp
					.put(prodAlterado.getProdutoCodigoErp(), prodAlterado));
		}
	}
	
	
}
