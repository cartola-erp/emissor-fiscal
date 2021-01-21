package net.cartola.emissorfiscal.sped.fiscal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import net.cartola.emissorfiscal.loja.Loja;

/**
 * 22/09/2020
 * @author robson.costa
 */
@Getter
@Setter
@Entity
@Table(name = "sped_fisc_arqu")
public class SpedFiscalArquivo {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "loja_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "fnk_sped_fisc_arqu_loja_id"))
	private Loja loja;
	private LocalDate dataInicioSped;
	private LocalDate dataFimSped;
	private Byte[] arquivo;			// Depois de gerar o arquivo setar aqui. O ideal é em bytes ? I do Know, but probably Yes, the ideal is in bytes not String
	private String nomeArquivo;
//	private String linkDownload; 	// Terá link para baixar o arquivo ??? Yes, I think bro !
	private LocalDateTime dataHoraInicioGeracao;
	private LocalDateTime dataHoraFimGeracao;
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "enum('GERANDO', 'ERRO', 'SUCESSO', 'ASSINADO') ")
	private Status status = Status.GERANDO;
	
	
	 
}
