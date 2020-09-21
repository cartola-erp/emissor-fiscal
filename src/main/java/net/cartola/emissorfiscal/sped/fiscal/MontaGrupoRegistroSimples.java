package net.cartola.emissorfiscal.sped.fiscal;

/**
 * 21/09/2020
 * @author robson.costa
 * 
 * T -> Grupo de Registro que será montado (Que tem uma Ocorrencia)
 * R -> Informações recebidas, referente as movimentações de um mês
 */
public interface MontaGrupoRegistroSimples <T, R extends MovimentacoesMensalIcmsIpi> {
	
	T montarGrupoDeRegistroSimples(R movimentacoesMensalIcmsIpi);
	
}
