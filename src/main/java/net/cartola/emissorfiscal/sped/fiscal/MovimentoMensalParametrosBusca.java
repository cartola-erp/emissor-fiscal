package net.cartola.emissorfiscal.sped.fiscal;

import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AccessLevel;
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
	// São as mesmas datas acima. Apenas com o horário de INICIO/FIM. Para fazer as consultas dos documento fiscal (É alterado pelo set das variaveis acima)
	@Setter(value = AccessLevel.NONE) private LocalDateTime dataHoraInicioSped;
	@Setter(value = AccessLevel.NONE) private LocalDateTime dataHoraFimSped;
	
	private boolean exportarSpedTodasLojas;
	private Long lojaId;
	private Long contadorId;
	
	private boolean exportarInventario;
	private Long inventarioId;;
	private LocalDate dataInicioInventario; // O período do inventário somente deverá ser preenchido caso for gerar o SPED de TODAS as Lojas
	private LocalDate dataFimInventario;
	
	public void setDataInicioSped(LocalDate dataInicioSped) {
		LocalDateTime dataHoraInicioSped = dataInicioSped.atTime(0, 1, 0);
		this.dataHoraInicioSped = dataHoraInicioSped;
		this.dataInicioSped = dataInicioSped;
	}
	
	public void setDataFimSped(LocalDate dataFimSped) {
		LocalDateTime dataHoraFimSped = dataFimSped.atTime(23, 59, 0);
		this.dataHoraFimSped = dataHoraFimSped;
		this.dataFimSped = dataFimSped;
	}
	
	/**
	 * Será retornado a Data inicial, do Sped (que informarão), concatenado com seguinte horário: <b> 00 : 01 : 00 <\b>  <\br>
	 * 
	 * Ex.: 2022-01-01	00 : 01 : 00
	 * 
	 * @return
	 */
	public LocalDateTime getDataHoraInicioSped() {
		if (nonNull(dataInicioSped) && nonNull(dataHoraInicioSped) && dataInicioSped.isEqual(dataHoraInicioSped.toLocalDate())) {
			return dataHoraInicioSped;
		}
		return dataInicioSped != null ? dataInicioSped.atTime(0, 1, 0) : null;
	}

	/**
	 * Será retornado a Data final, do Sped (que informarão), concatenado com seguinte horário: <b> 23 : 59 : 00 <\b>  <\br>
	 * 
	 * Ex.: 2022-01-31	23 : 59 : 00
	 * @return
	 */
	public LocalDateTime getDataHoraFimSped( ) {
		if (nonNull(dataHoraFimSped) && nonNull(dataHoraFimSped) && dataFimSped.isEqual(dataHoraFimSped.toLocalDate())) {
			return dataHoraFimSped;
		}
		return dataHoraFimSped != null ? dataFimSped.atTime(23, 59, 0) : null;
	}
	
			
}
