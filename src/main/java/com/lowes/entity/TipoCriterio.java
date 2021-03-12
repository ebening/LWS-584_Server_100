package com.lowes.entity;
// Generated 28/12/2015 05:50:04 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TipoCriterio generated by hbm2java
 */
@Entity
@Table(name = "TIPO_CRITERIO", schema = "LWS584")
public class TipoCriterio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idTipoCriterio;
	private String tipoCriterio;
	private String clase;

	public TipoCriterio() {
	}

	public TipoCriterio(int idTipoCriterio, String tipoCriterio) {
		this.idTipoCriterio = idTipoCriterio;
		this.tipoCriterio = tipoCriterio;
	}
	
	public TipoCriterio(int idTipoCriterio) {
		this.idTipoCriterio = idTipoCriterio;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name = "ID_TIPO_CRITERIO", unique = true, nullable = false)
	public int getIdTipoCriterio() {
		return this.idTipoCriterio;
	}

	public void setIdTipoCriterio(int idTipoCriterio) {
		this.idTipoCriterio = idTipoCriterio;
	}

	@Column(name = "TIPO_CRITERIO", nullable = false, length = 100)
	public String getTipoCriterio() {
		return this.tipoCriterio;
	}

	public void setTipoCriterio(String tipoCriterio) {
		this.tipoCriterio = tipoCriterio;
	}
	
	@Column(name = "CLASE", length = 100)
	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}
}
