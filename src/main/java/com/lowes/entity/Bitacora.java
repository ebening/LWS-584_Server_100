package com.lowes.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "BITACORA", schema = "LWS584")
public class Bitacora implements java.io.Serializable {

	private int idBitacora;
	private Usuario usuario;
	private String tabla;
	private String accion;
	private Date fechaHora;

	public Bitacora() {
	}

	public Bitacora(int idBitacora, Usuario usuario) {
		this.idBitacora = idBitacora;
		this.usuario = usuario;
	}

	public Bitacora(int idBitacora, Usuario usuario, String tabla, String accion, Date fechaHora) {
		this.idBitacora = idBitacora;
		this.usuario = usuario;
		this.tabla = tabla;
		this.accion = accion;
		this.fechaHora = fechaHora;
	}
	
	public Bitacora(Usuario usuario, String tabla, String accion, Date fechaHora) {
		this.usuario = usuario;
		this.tabla = tabla;
		this.accion = accion;
		this.fechaHora = fechaHora;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_BITACORA", unique = true, nullable = false)
	public int getIdBitacora() {
		return this.idBitacora;
	}

	public void setIdBitacora(int idBitacora) {
		this.idBitacora = idBitacora;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "TABLA", length = 50)
	public String getTabla() {
		return this.tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	@Column(name = "ACCION", length = 15)
	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", length = 26)
	public Date getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

}