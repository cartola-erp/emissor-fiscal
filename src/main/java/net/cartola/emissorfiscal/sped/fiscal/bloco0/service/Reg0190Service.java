package net.cartola.emissorfiscal.sped.fiscal.bloco0.service;

import static java.util.stream.Collectors.toSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItem;
import net.cartola.emissorfiscal.documento.DocumentoFiscalItemService;
import net.cartola.emissorfiscal.produto.ProdutoUnidade;
import net.cartola.emissorfiscal.produto.ProdutoUnidadeService;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentacoesMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.bloco0.Reg0190;

/**
 * 21/09/2020
 * @author robson.costa
 */
@Service
class Reg0190Service implements MontaGrupoDeRegistroList<Reg0190, MovimentacoesMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(Reg0190Service.class.getName());
	
	@Autowired
	private DocumentoFiscalItemService docuFiscItemService;
	
	@Autowired
	private ProdutoUnidadeService prodUnidService;
	
	@Override
	public List<Reg0190> montarGrupoDeRegistro(MovimentacoesMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Grupo de Registro 0190 ");
		List<Reg0190> listReg0190 = new LinkedList<>();
		List<DocumentoFiscal> listDocFiscal = movimentosIcmsIpi.getListDocumentoFiscal();
		List<DocumentoFiscalItem> listItens = docuFiscItemService.findItensByVariosDocumentoFiscal(listDocFiscal);
		Set<String> unidadesSet = listItens.stream().map(DocumentoFiscalItem::getUnidade).collect(toSet());
		List<ProdutoUnidade> listProdUnid = prodUnidService.findByListSiglas(unidadesSet);

		listProdUnid.stream().forEachOrdered(unidade -> {
			Reg0190 reg0190 = new Reg0190();
			reg0190.setUnid(unidade.getSigla());
			reg0190.setDescr(unidade.getDescricao());
			listReg0190.add(reg0190);
		});
		return listReg0190;
	}

}
