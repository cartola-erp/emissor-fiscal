package net.cartola.emissorfiscal.sped.fiscal.blocoD.service;

import static net.cartola.emissorfiscal.util.NumberUtilRegC100.multiplicaAliqPorCem;
import static net.cartola.emissorfiscal.util.SpedFiscalUtil.getCstIcmsComOrigem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.cartola.emissorfiscal.documento.DocumentoFiscal;
import net.cartola.emissorfiscal.documento.ProdutoOrigem;
import net.cartola.emissorfiscal.sped.fiscal.MovimentoMensalIcmsIpi;
import net.cartola.emissorfiscal.sped.fiscal.blocoC.RegC190;
import net.cartola.emissorfiscal.sped.fiscal.blocoD.RegD190;

/**
 * @autor robson.costa
 * @data 6 de mai. de 2021
 */
@Service
class RegD190Service {

	// CFOP POSSIVEIS: "DE -> PARA"
	// 6353 -> 2353 (CTE Fora do estado)
	// 5353 -> 1353 (cte dentro do estado) 
	
	
	
	/**
	 * Irá realizar o Registro Analítico para o DocumentoFiscal, modelo 57 (CTE).
	 * Apesar de retornar uma lista. para o nosso caso, segundo a Contabilidade, sempre será um único registro;
	 * 
	 * Caso tenha mais de uma aliquota de imposto na CTE, a lógica terá que ser feita igual a do {@link RegC190} (Service);
	 * Obtendo o Mapa de itens para Registro analítico utilizando a classe {@link SpedFiscalUtil.getMapaItensParaRegistroAnalitico()}
	 * 
	 * @param servicoTransporte
	 * @return
	 */
	public List<RegD190> montarGrupoRegC190(DocumentoFiscal servicoTransporte, MovimentoMensalIcmsIpi movimentoMensalIcmsIpi) {
		List<RegD190> listRegD190 = new ArrayList<>();
		
		servicoTransporte.getItens().stream().forEach(impostoCte -> {
			RegD190 regD190 = new RegD190();
			ProdutoOrigem origem = impostoCte.getOrigem();
			
			regD190.setCstIcms(getCstIcmsComOrigem(origem, impostoCte.getIcmsCst()));
			regD190.setCfop(impostoCte.getCfop());
			regD190.setAliqIcms(multiplicaAliqPorCem(impostoCte.getIcmsAliquota()));
			regD190.setVlOpr(impostoCte.getIcmsBase());
			regD190.setVlBcIcms(impostoCte.getIcmsBase());
			regD190.setVlIcms(impostoCte.getIcmsValor());
			regD190.setVlRedBc(BigDecimal.ZERO);
			regD190.setCodObs(null);
			
			listRegD190.add(regD190);
		});
		movimentoMensalIcmsIpi.addRegistroAnalitico(listRegD190, servicoTransporte);
		return listRegD190;
	}

	
	
	
	
}
