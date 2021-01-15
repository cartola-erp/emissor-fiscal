package net.cartola.emissorfiscal.engine;

import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * @date 15 de jan. de 2021
 * @author robson.costa
 */
@Getter
@Setter
@Entity
@Table(name = "cests_ncms")
@JsonIgnoreProperties(value = { "cests" })
public class CestNcmModel {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo = 0;
	private int cestCodigo = 0;
//	private transient boolean cestCodigoUpdated = false;
	private int ncm = 0;
//	private transient boolean ncmUpdated = false;
	private Date cadastro = null;
	private Date alterado = null;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cests_id", unique = false, foreignKey = @ForeignKey(name = "fnk_cest_id"))
	private CestModel cests;
	
	public static String formataCest(int cest) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(7);
		nf.setMaximumFractionDigits(7);
		return nf.format(cest);
	}
}
