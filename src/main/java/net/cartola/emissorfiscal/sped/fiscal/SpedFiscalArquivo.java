package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;

/**
 * 22/09/2020
 * @author robson.costa
 */
@Entity(name = "sped_fisc_arqu")
public class SpedFiscalArquivo {
	
	private int loja;
	private LocalDate dataInicioSped;
	private LocalDate dataFimSped;
	private Byte[] arquivo;			// Depois de gerar o arquivo setar aqui. O ideal é em bytes ? I do Know, but probably Yes, the ideal is in bytes not String
	private String nomeArquivo;
//	private String linkDownload; 	// Terá link para baixar o arquivo ??? Yes, I think bro !
	private LocalDateTime dataHoraInicioGeracao;
	private LocalDateTime dataHoraFimGeracao;
	private Status status = Status.GERANDO;
	
	 
}
