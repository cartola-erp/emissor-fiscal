package net.cartola.emissorfiscal.sped.fiscal;

import java.util.List;

/**
 * 21/09/2020
 * @author robson.costa
 * 
 * List<T> -> Grupo de Registro que será montado (Para registros com mais de uma ocorrencia)
 * R -> Informações recebidas, referente as movimentações de um mês
 */
public interface MontaGrupoDeRegistroList <T, R extends MovimentacoesMensalIcmsIpi> {
	
	List<T> montarGrupoDeRegistro(R movimentacoesMensalIcmsIpi);
	
}
