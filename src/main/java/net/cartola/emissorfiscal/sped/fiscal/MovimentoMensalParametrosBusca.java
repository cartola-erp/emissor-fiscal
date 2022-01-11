package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Essa classe será resposável, por "representar" os campos da Tela que é gerado O sped fiscal icms ipi <br>
 * <b>(gerar-icms-ipi.html) <\b>
 * - Nela será setados os valores <br>
 * Ex.: a loja do sped a ser exportado, periodo do inventario (se for exportar o inventario), 
 * id do contador, periodo do SPED  etc... <br>
 * 
 * 
 * @date 28 de dez. de 2021
 * @author robson.costa
 */
@ToString
@Getter
@Setter
public class MovimentoMensalParametrosBusca {
	
	
	private LocalDate dataInicioSped;
	private LocalDate dataFimSped;
	
	private boolean exportarSpedTodasLojas;
	private Long lojaId;
	private Long contadorId;
	
	private boolean exportarInventario;
	private Long inventarioId;;
	private LocalDate dataInicioInventario; // O período do inventário somente deverá ser preenchido caso for gerar o SPED de TODAS as Lojas
	private LocalDate dataFimInventario;
	
	
}
