package com.lowes.dto;

import java.util.List;

import com.lowes.entity.Compania;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.util.Etiquetas;

public class KilometrajeDTO {
	
	private Moneda moneda;
	private Compania compania;
	private Locacion locacion;
	private String  importeAutoPropio;
	private FormaPago formaPago;
	private String concepto;
	private List<KilometrajeDesgloseDTO> kilometrajeDesgloseList;
	private Integer idSolicitud;
	private boolean modificacion;
	private boolean creacion;
	private Integer idSolicitudSession;

	
	//defaults
	private Integer idMoneda =  Etiquetas.ID_PESOS;
	private Integer idCompania = Etiquetas.ID_COMPANIA_LOWES;
	private Integer idFormaPago;
	
	
	public Moneda getMoneda() {
		return moneda;
	}
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	public Compania getCompania() {
		return compania;
	}
	public void setCompania(Compania compania) {
		this.compania = compania;
	}
	public Locacion getLocacion() {
		return locacion;
	}
	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}
	public String getImporteAutoPropio() {
		return importeAutoPropio;
	}
	public void setImporteAutoPropio(String importeAutoPropio) {
		this.importeAutoPropio = importeAutoPropio;
	}
	public FormaPago getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public List<KilometrajeDesgloseDTO> getKilometrajeDesgloseList() {
		return kilometrajeDesgloseList;
	}
	public void setKilometrajeDesgloseList(List<KilometrajeDesgloseDTO> kilometrajeDesgloseList) {
		this.kilometrajeDesgloseList = kilometrajeDesgloseList;
	}
	public Integer getIdMoneda() {
		return idMoneda;
	}
	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}
	public Integer getIdCompania() {
		return idCompania;
	}
	public void setIdCompania(Integer idCompania) {
		this.idCompania = idCompania;
	}
	public Integer getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	public Integer getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public boolean isModificacion() {
		return modificacion;
	}
	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}
	public boolean isCreacion() {
		return creacion;
	}
	public void setCreacion(boolean creacion) {
		this.creacion = creacion;
	}
	public Integer getIdSolicitudSession() {
		return idSolicitudSession;
	}
	public void setIdSolicitudSession(Integer idSolicitudSession) {
		this.idSolicitudSession = idSolicitudSession;
	}
	
	
}
