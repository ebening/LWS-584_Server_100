package com.lowes.dto;

import com.lowes.entity.Compania;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.TipoProveedor;
import com.lowes.entity.ViajeDestino;

public class AnticipoViajeDTO {

	private Compania compania;
 	private FormaPago formaPago;
 	private Locacion locacion;
	private Moneda moneda;
	private String importeTotal;
	private String concepto;
	private TipoProveedor tipoProveedor;
	private Proveedor proveedor;
	private Integer idSolicitudSession;
	private Integer tipoDeBeneficiario;
	private Boolean creacion;
	private Boolean modificacion;
	private Integer beneficiario;
	
	private ViajeDestino viajeDestino;
	
	public Boolean getModificacion() {
		return modificacion;
	}
	public void setModificacion(Boolean modificacion) {
		this.modificacion = modificacion;
	}
	public Boolean getCreacion() {
		return creacion;
	}
	public void setCreacion(Boolean creacion) {
		this.creacion = creacion;
	}
	public Compania getCompania() {
		return compania;
	}
	public void setCompania(Compania compania) {
		this.compania = compania;
	}
	public FormaPago getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	public Locacion getLocacion() {
		return locacion;
	}
	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}
	public Moneda getMoneda() {
		return moneda;
	}
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	public String getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public TipoProveedor getTipoProveedor() {
		return tipoProveedor;
	}
	public void setTipoProveedor(TipoProveedor tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public Integer getIdSolicitudSession() {
		return idSolicitudSession;
	}
	public void setIdSolicitudSession(Integer idSolicitudSession) {
		this.idSolicitudSession = idSolicitudSession;
	}
	public Integer getTipoDeBeneficiario() {
		return tipoDeBeneficiario;
	}
	public void setTipoDeBeneficiario(Integer tipoDeBeneficiario) {
		this.tipoDeBeneficiario = tipoDeBeneficiario;
	}
	public Integer getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Integer beneficiario) {
		this.beneficiario = beneficiario;
	}
}
