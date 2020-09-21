package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 21/09/2020
 * @author robson.costa
 * 
 * T -> Objeto SpedFiscal, que será criado o arquivo .txt, do SpedFiscalIcmsIpi
 * R -> Informações recebidas, referente as movimentações de um mês
 */
public interface MontaArquivoSpedFiscal <T extends SpedFiscal, R extends MovimentacoesMensalIcmsIpi> { 

	SpedFiscal criarArquivoSpedFiscal(R movimentacoesMensalIcmsIpi);
	
}
