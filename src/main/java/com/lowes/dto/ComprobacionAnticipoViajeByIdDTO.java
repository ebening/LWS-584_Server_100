package com.lowes.dto;

import java.math.BigDecimal;

public class ComprobacionAnticipoViajeByIdDTO {

	private Integer compania;
	private Integer locacion;
	private Integer moneda;
	private Integer formaPago;
	private String fechaInicio;
	private String fechaRegreso;
	private BigDecimal importe;
	private String strImporte;
	private Integer numeroPersonas;
	private Boolean preguntaExtranjero1;
	private Boolean preguntaExtranjero2;
	private Integer viajeTipoAlimentos;
	private Integer idTipoSolicitud;
	private Integer viajeMotivo;
	private Integer viajeDestino;
	private String otroMotivo;
	private String otroDestino;
	
	public Integer getCompania() {
		return compania;
	}
	public void setCompania(Integer compania) {
		this.compania = compania;
	}
	public Integer getLocacion() {
		return locacion;
	}
	public void setLocacion(Integer locacion) {
		this.locacion = locacion;
	}
	public Integer getMoneda() {
		return moneda;
	}
	public void setMoneda(Integer moneda) {
		this.moneda = moneda;
	}
	public Integer getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(Integer formaPago) {
		this.formaPago = formaPago;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaRegreso() {
		return fechaRegreso;
	}
	public void setFechaRegreso(String fechaRegreso) {
		this.fechaRegreso = fechaRegreso;
	}
	public BigDecimal getImporte() {
		return importe;
	}
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
	public String getStrImporte() {
		return strImporte;
	}
	public void setStrImporte(String strImporte) {
		this.strImporte = strImporte;
	}
	public Integer getNumeroPersonas() {
		return numeroPersonas;
	}
	public void setNumeroPersonas(Integer numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}
	public Boolean getPreguntaExtranjero1() {
		return preguntaExtranjero1;
	}
	public void setPreguntaExtranjero1(Boolean preguntaExtranjero1) {
		this.preguntaExtranjero1 = preguntaExtranjero1;
	}
	public Boolean getPreguntaExtranjero2() {
		return preguntaExtranjero2;
	}
	public void setPreguntaExtranjero2(Boolean preguntaExtranjero2) {
		this.preguntaExtranjero2 = preguntaExtranjero2;
	}
	public Integer getViajeTipoAlimentos() {
		return viajeTipoAlimentos;
	}
	public void setViajeTipoAlimentos(Integer viajeTipoAlimentos) {
		this.viajeTipoAlimentos = viajeTipoAlimentos;
	}
	public Integer getIdTipoSolicitud() {
		return idTipoSolicitud;
	}
	public void setIdTipoSolicitud(Integer idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}
	public Integer getViajeMotivo() {
		return viajeMotivo;
	}
	public void setViajeMotivo(Integer viajeMotivo) {
		this.viajeMotivo = viajeMotivo;
	}
	public Integer getViajeDestino() {
		return viajeDestino;
	}
	public void setViajeDestino(Integer viajeDestino) {
		this.viajeDestino = viajeDestino;
	}
	public String getOtroMotivo() {
		return otroMotivo;
	}
	public void setOtroMotivo(String otroMotivo) {
		this.otroMotivo = otroMotivo;
	}
	public String getOtroDestino() {
		return otroDestino;
	}
	public void setOtroDestino(String otroDestino) {
		this.otroDestino = otroDestino;
	}
}
