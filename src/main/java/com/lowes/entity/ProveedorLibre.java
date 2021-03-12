/**
 * 
 */
package com.lowes.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author miguelr
 *
 */
@Entity
@Table(name = "PROVEEDOR_LIBRE", schema = "LWS584")
public class ProveedorLibre implements java.io.Serializable {

	private int idProveedorLibre;
	private String descripcion;
	private String rfc;
	private short activo;
	private Date creacionFecha;
	private int creacionUsuario;
	private Date modificacionFecha;
	private Integer modificacionUsuario;

	public ProveedorLibre() {
	}

	public ProveedorLibre(int idProveedorLibre, String descripcion, String rfc, short activo, Date creacionFecha,
			int creacionUsuario) {
		this.idProveedorLibre = idProveedorLibre;
		this.descripcion = descripcion;
		this.rfc = rfc;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
	}

	public ProveedorLibre(int idProveedorLibre, String descripcion, String rfc, short activo, Date creacionFecha,
			int creacionUsuario, Date modificacionFecha, Integer modificacionUsuario) {
		this.idProveedorLibre = idProveedorLibre;
		this.descripcion = descripcion;
		this.rfc = rfc;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
		this.modificacionFecha = modificacionFecha;
		this.modificacionUsuario = modificacionUsuario;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PROVEEDOR_LIBRE", unique = true, nullable = false)
	public int getIdProveedorLibre() {
		return this.idProveedorLibre;
	}

	public void setIdProveedorLibre(int idProveedorLibre) {
		this.idProveedorLibre = idProveedorLibre;
	}

	@Column(name = "DESCRIPCION", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "RFC", nullable = false, length = 15)
	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	@Column(name = "ACTIVO", nullable = false)
	public short getActivo() {
		return this.activo;
	}

	public void setActivo(short activo) {
		this.activo = activo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREACION_FECHA", nullable = false, length = 26)
	public Date getCreacionFecha() {
		return this.creacionFecha;
	}

	public void setCreacionFecha(Date creacionFecha) {
		this.creacionFecha = creacionFecha;
	}

	@Column(name = "CREACION_USUARIO", nullable = false)
	public int getCreacionUsuario() {
		return this.creacionUsuario;
	}

	public void setCreacionUsuario(int creacionUsuario) {
		this.creacionUsuario = creacionUsuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFICACION_FECHA", length = 26)
	public Date getModificacionFecha() {
		return this.modificacionFecha;
	}

	public void setModificacionFecha(Date modificacionFecha) {
		this.modificacionFecha = modificacionFecha;
	}

	@Column(name = "MODIFICACION_USUARIO")
	public Integer getModificacionUsuario() {
		return this.modificacionUsuario;
	}

	public void setModificacionUsuario(Integer modificacionUsuario) {
		this.modificacionUsuario = modificacionUsuario;
	}
}
