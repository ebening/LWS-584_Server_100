package com.lowes.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lowes.entity.Solicitud;

public class ComprobacionDepositoDTO {
    
	// estos serian los paths en los formularios cuando hagas el post
	//vendra cargado este objeto
	private Solicitud solicitud;
	private String descripcion;
	private String archivo;
	private String montoDeposito;
	private String pidCm;
	private short activo;
	private Date creacionFecha;
	private int creacionUsuario;
	private Date modificacionFecha;
	private Integer modificacionUsuario;
	private Date fecha_deposito;
	private Integer idComprobacionDeposito;
	
	public Solicitud getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public String getMontoDeposito() {
		return montoDeposito;
	}
	public void setMontoDeposito(String montoDeposito) {
		this.montoDeposito = montoDeposito;
	}
	public String getPidCm() {
		return pidCm;
	}
	public void setPidCm(String pidCm) {
		this.pidCm = pidCm;
	}
	public short getActivo() {
		return activo;
	}
	public void setActivo(short activo) {
		this.activo = activo;
	}
	public Date getCreacionFecha() {
		return creacionFecha;
	}
	public void setCreacionFecha(Date creacionFecha) {
		this.creacionFecha = creacionFecha;
	}
	public int getCreacionUsuario() {
		return creacionUsuario;
	}
	public void setCreacionUsuario(int creacionUsuario) {
		this.creacionUsuario = creacionUsuario;
	}
	public Date getModificacionFecha() {
		return modificacionFecha;
	}
	public void setModificacionFecha(Date modificacionFecha) {
		this.modificacionFecha = modificacionFecha;
	}
	public Integer getModificacionUsuario() {
		return modificacionUsuario;
	}
	public void setModificacionUsuario(Integer modificacionUsuario) {
		this.modificacionUsuario = modificacionUsuario;
	}
	public Date getFecha_deposito() {
		return fecha_deposito;
	}
	public void setFecha_deposito(Date fecha_deposito) {
		this.fecha_deposito = fecha_deposito;
	}
	public Integer getIdComprobacionDeposito() {
		return idComprobacionDeposito;
	}
	public void setIdComprobacionDeposito(Integer idComprobacionDeposito) {
		this.idComprobacionDeposito = idComprobacionDeposito;
	}
	
	
	

}
