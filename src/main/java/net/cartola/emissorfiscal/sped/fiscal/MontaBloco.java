package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 21/09/2020
 * @author robson.costa
 * 
 * T -> Bloco de registro que será montado
 * R -> Informações recebidas, referente as movimentações de um mês
 */
public interface MontaBloco <T, R extends MovimentoMensalIcmsIpi> {

	T criarBloco(R movimentacoesMensalIcmsIpi);
	
}
