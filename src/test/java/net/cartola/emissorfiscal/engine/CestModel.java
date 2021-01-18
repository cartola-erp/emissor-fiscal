package net.cartola.emissorfiscal.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/** @author murilo.lima
 *
 * Classe que guarda o Objeto que representa o cadastro do CEST (Código
 * Especificador da Substituição Tributária)
 */
@Getter
@Setter
@Entity
@Table(name = "cests")
public class CestModel {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int codigo = 0;
   private double item;
//   private transient boolean itemUpdated = false;
   private int cest;
//   private transient boolean cestUpdated = false;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cests")
   private List<CestNcmModel> ncms = new ArrayList<>();
   private String descricao = "";
//   private transient boolean descricaoUpdated = false;
   private Date cadastro = null;
   private Date alterado = null;
   
   
   
   
}
