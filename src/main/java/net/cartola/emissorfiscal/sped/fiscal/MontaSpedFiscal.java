package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 21/09/2020
 * @author robson.costa
 * 
 * T -> Objeto SpedFiscal, que será MONTADO para criar o arquivo .txt, do SpedFiscalIcmsIpi
 * R -> Informações recebidas, referente as movimentações de um mês
 */
public interface MontaSpedFiscal <T extends SpedFiscal, R extends MovimentoMensalIcmsIpi> { 

	SpedFiscal criarSpedFiscal(R movimentacoesMensalIcmsIpi);
	
}
