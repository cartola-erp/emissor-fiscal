package net.cartola.emissorfiscal.sped.fiscal.blocosBGHK19.service;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.isBigDecimalMaiorQueZero;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.inventario.Inventario;
import net.cartola.emissorfiscal.inventario.InventarioItem;
import net.cartola.emissorfiscal.sped.fiscal.MontaGrupoDeRegistroList;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.RegH005;
import net.cartola.emissorfiscal.sped.fiscal.blocoH.RegH010;
import net.cartola.emissorfiscal.sped.fiscal.enums.MotivoDoInventario;

/**
 * @date 10 de nov. de 2021
 * @author robson.costa
 */
@Service
public class RegH005Service implements MontaGrupoDeRegistroList<RegH005, MovimentoMensalIcmsIpi> {

	private static final Logger LOG = Logger.getLogger(RegH005Service.class.getName());
	
	@Override
	public List<RegH005> montarGrupoDeRegistro(MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro H005");
		List<RegH005> listRegH005 = new LinkedList<>();
		Inventario inventario = movimentosIcmsIpi.getInventario();
		
		RegH005 regH005 = new RegH005();
		regH005.setDtInv(inventario.getFim());
		regH005.setVlInv(inventario.getValorDeclarado());
		regH005.setMotInv(MotivoDoInventario.FINAL_PERIODO);

		regH005.setRegH010(gerarRegistroH010(inventario, movimentosIcmsIpi));
		
		listRegH005.add(regH005);
		return listRegH005;
	}

	private List<RegH010> gerarRegistroH010(Inventario inventario, MovimentoMensalIcmsIpi movimentosIcmsIpi) {
		LOG.log(Level.INFO, "Montando o Registro H010");
		List<RegH010> listRegH010 = new LinkedList<>();
		Predicate<InventarioItem> temEmEstoque = item -> item.getEstoqueDeclarado() != null && isBigDecimalMaiorQueZero(item.getEstoqueDeclarado());

		inventario.getItens().stream().filter(temEmEstoque).forEach(item -> {
			listRegH010.add(new RegH010(item));
		});
		
		return listRegH010;
	}

}
