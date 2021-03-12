package com.lowes.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.lowes.entity.Compania;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;

public class FacturaConXML {
	
	private List<FacturaDesgloseDTO> facturaDesgloseList;
	private boolean track_asset;
	private CuentaContable cuentaContable;
	private String par;
	private Compania compania;
	private Proveedor proveedor;
	private String rfcEmisor;
	private String folioFiscal;
	private String serie;
	private String folio;
	private BigDecimal subTotal;	
	private Moneda moneda;
	private BigDecimal ieps;
	private BigDecimal total;
	private BigDecimal iva;
	private BigDecimal tasaIVA;
	private boolean conRetenciones;
	private BigDecimal iva_retenido;
	private BigDecimal isr_retenido;
	private String concepto;
	private Compania id_compania_libro_contable;
	private Integer idSolicitudSession;
	private boolean solicitante;
	private Integer idSolicitanteJefe;
	private Date fecha;
	private Locacion locacion;
	private String fecha_factura;
	private boolean cambiarxml;
	private boolean modificacion;
	private boolean creacion;
	private Integer tipoSolicitud;
	
	private String strSubTotal;
	private String strIeps;
	private String strTotal;
	private String strIva_retenido;
	private String strIsr_retenido;
	private String strIva;
	
	private Integer tipoFactura;

	public List<FacturaDesgloseDTO> getFacturaDesgloseList() {
		return facturaDesgloseList;
	}

	public void setFacturaDesgloseList(List<FacturaDesgloseDTO> facturaDesgloseList) {
		this.facturaDesgloseList = facturaDesgloseList;
	}

	public boolean isTrack_asset() {
		return track_asset;
	}

	public void setTrack_asset(boolean track_asset) {
		this.track_asset = track_asset;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public String getPar() {
		return par;
	}

	public void setPar(String par) {
		this.par = par;
	}

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getFolioFiscal() {
		return folioFiscal;
	}

	public void setFolioFiscal(String folioFiscal) {
		this.folioFiscal = folioFiscal;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	
	public BigDecimal getIsr_retenido() {
		return isr_retenido;
	}

	public void setIsr_retenido(BigDecimal isr_retenido) {
		this.isr_retenido = isr_retenido;
	}
    
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getIeps() {
		return ieps;
	}

	public void setIeps(BigDecimal ieps) {
		this.ieps = ieps;
	}

	public boolean isConRetenciones() {
		return conRetenciones;
	}

	public void setConRetenciones(boolean conRetenciones) {
		this.conRetenciones = conRetenciones;
	}

	public BigDecimal getIva_retenido() {
		return iva_retenido;
	}

	public void setIva_retenido(BigDecimal iva_retenido) {
		this.iva_retenido = iva_retenido;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Compania getId_compania_libro_contable() {
		return id_compania_libro_contable;
	}

	public void setId_compania_libro_contable(Compania id_compania_libro_contable) {
		this.id_compania_libro_contable = id_compania_libro_contable;
	}

	public Integer getIdSolicitudSession() {
		return idSolicitudSession;
	}

	public void setIdSolicitudSession(Integer idSolicitudSession) {
		this.idSolicitudSession = idSolicitudSession;
	}

	public boolean isSolicitante() {
		return solicitante;
	}

	public void setSolicitante(boolean solicitante) {
		this.solicitante = solicitante;
	}

	public Integer getIdSolicitanteJefe() {
		return idSolicitanteJefe;
	}

	public void setIdSolicitanteJefe(Integer idSolicitanteJefe) {
		this.idSolicitanteJefe = idSolicitanteJefe;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Locacion getLocacion() {
		return locacion;
	}

	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}
	
	public String getFecha_factura() {
		return fecha_factura;
	}

	public void setFecha_factura(String fecha_factura) {
		this.fecha_factura = fecha_factura;
	}

	public boolean isCambiarxml() {
		return cambiarxml;
	}

	public void setCambiarxml(boolean cambiarxml) {
		this.cambiarxml = cambiarxml;
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

	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

//	---------------------------------------------	

	public String getStrIeps() {
		return strIeps;
	}

	public void setStrIeps(String strIeps) {
		this.strIeps = strIeps;
	}

	public String getStrTotal() {
		return strTotal;
	}

	public void setStrTotal(String strTotal) {
		this.strTotal = strTotal;
	}

	public String getStrIva_retenido() {
		return strIva_retenido;
	}

	public void setStrIva_retenido(String strIva_retenido) {
		this.strIva_retenido = strIva_retenido;
	}

	public String getStrIsr_retenido() {
		return strIsr_retenido;
	}

	public void setStrIsr_retenido(String strIsr_retenido) {
		this.strIsr_retenido = strIsr_retenido;
	}

	public String getStrSubTotal() {
		return strSubTotal;
	}

	public void setStrSubTotal(String strSubTotal) {
		this.strSubTotal = strSubTotal;
	}

	public String getStrIva() {
		return strIva;
	}

	public void setStrIva(String strIva) {
		this.strIva = strIva;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public Integer getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(Integer tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public BigDecimal getTasaIVA() {
		return tasaIVA;
	}

	public void setTasaIVA(BigDecimal tasaIVA) {
		this.tasaIVA = tasaIVA;
	}
		
}