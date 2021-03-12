package com.lowes.entity;
// Generated 28/12/2015 05:50:04 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TipoDocumento generated by hbm2java
 */
@Entity
@Table(name = "TIPO_DOCUMENTO", schema = "LWS584")
public class TipoDocumento implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int tipoDocumento;
	private String descripcion;

	public TipoDocumento() {
	}
	
	public TipoDocumento(int tipoDocumento){
		this.tipoDocumento = tipoDocumento;
	}

	public TipoDocumento(int tipoDocumento, String descripcion) {
		this.tipoDocumento = tipoDocumento;
		this.descripcion = descripcion;
	}
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name = "TIPO_DOCUMENTO", unique = true, nullable = false)
	public int getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@Column(name = "DESCRIPCION", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}