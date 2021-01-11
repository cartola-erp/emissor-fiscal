package net.cartola.emissorfiscal.documento;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cartola.emissorfiscal.util.LocalDateDeserializer;

/**
 * 25/09/2020
 * @author robson.costa
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "docu_fisc_ref")
public class DocumentoFiscalReferencia implements Serializable {
	
	private static final long serialVersionUID = 2949833005905966630L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	private int loja;
	private Long numero;
	private int item;
	private DocumentoReferenciaTipo tipo;
	private Long cnpjEmitente;
	private Integer ufEmitente;
	private Long ecf;
	private Integer numDocumentoCoo;
	private String serieModelo;
	private String chave;
	private String modelo;
//	private Integer lojaVinculada;
//	private Integer compraVinculada;
	
//	@Column(name = "icms_cst", scale = 4, nullable = false)
	@Nullable
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "docu_fisc_id", unique = false, foreignKey = @ForeignKey(name = "fnk_docu_fisc"))
	private DocumentoFiscal documentoFiscal;

	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate cadastro;
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate alterado;
	

	
}
