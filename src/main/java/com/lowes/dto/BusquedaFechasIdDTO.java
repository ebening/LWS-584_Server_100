package com.lowes.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BusquedaFechasIdDTO {
	
	private Date fechaInicial;
	private Date fechaFinal;
	private Integer idBusqueda;
	private String fechaInicialString;
	private String fechaFinalString;
	
	public Date getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(Date fechaInicial) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		setFechaInicialString(df.format(fechaInicial));
		this.fechaInicial = fechaInicial;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		setFechaFinalString(df.format(fechaFinal));
		this.fechaFinal = fechaFinal;
	}
	public Integer getIdBusqueda() {
		return idBusqueda;
	}
	public void setIdBusqueda(Integer idBusqueda) {
		this.idBusqueda = idBusqueda;
	}
	public String getFechaInicialString() {
		return fechaInicialString;
	}
	public void setFechaInicialString(String fechaInicialString) {
		this.fechaInicialString = fechaInicialString;
	}
	public String getFechaFinalString() {
		return fechaFinalString;
	}
	public void setFechaFinalString(String fechaFinalString) {
		this.fechaFinalString = fechaFinalString;
	}
	
}
