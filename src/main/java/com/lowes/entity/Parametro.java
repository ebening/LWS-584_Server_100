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
* Parametro generated by hbm2java
*/
@Entity
@Table(name = "PARAMETRO")
public class Parametro implements java.io.Serializable {

	private static final long serialVersionUID = 5274606599952574947L;

	private int idParametro;
	private String parametro;
	private String alias;
	private String valor;
	private String tipoDato;
	private short activo;
	private short editable;
	private Date creacionFecha;
	private Integer creacionUsuario;
	private Date modificacionFecha;
	private Integer modificacionUsuario;

	public Parametro() {
	}
	
	public Parametro(int idParametro) {
		this.idParametro = idParametro;
	}

	public Parametro(int idParametro, String parametro, String alias, String tipoDato, short editable, short activo,
			Date creacionFecha, int creacionUsuario) {
		this.idParametro = idParametro;
		this.parametro = parametro;
		this.alias = alias;
		this.tipoDato = tipoDato;
		this.editable = editable;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
	}

	public Parametro(int idParametro, String parametro, String alias, String valor, String tipoDato, short editable,
			short activo, Date creacionFecha, int creacionUsuario, Date modificacionFecha,
			Integer modificacionUsuario) {
		this.idParametro = idParametro;
		this.parametro = parametro;
		this.alias = alias;
		this.valor = valor;
		this.tipoDato = tipoDato;
		this.editable = editable;
		this.activo = activo;
		this.creacionFecha = creacionFecha;
		this.creacionUsuario = creacionUsuario;
		this.modificacionFecha = modificacionFecha;
		this.modificacionUsuario = modificacionUsuario;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PARAMETRO", unique = true, nullable = false)
	public int getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro(int idParametro) {
		this.idParametro = idParametro;
	}

	@Column(name = "PARAMETRO", nullable = false, length = 100)
	public String getParametro() {
		return this.parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	@Column(name = "ALIAS", nullable = false, length = 100)
	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(name = "VALOR", length = 500)
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Column(name = "TIPO_DATO", nullable = false, length = 25)
	public String getTipoDato() {
		return this.tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	@Column(name = "ACTIVO", nullable = false)
	public short getActivo() {
		return this.activo;
	}

	public void setActivo(short activo) {
		this.activo = activo;
	}
	
	@Column(name = "EDITABLE", nullable = false)
	public short getEditable() {
		return this.editable;
	}

	public void setEditable(short editable) {
		this.editable = editable;
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
	public Integer getCreacionUsuario() {
		return this.creacionUsuario;
	}

	public void setCreacionUsuario(Integer creacionUsuario) {
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