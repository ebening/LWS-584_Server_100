package com.lowes.dto;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.lowes.util.Etiquetas;

public class FacturaDTO {
	
    private String rfcEmisor;
    private String rfcReceptor;
    private String uuid;
    private String folioFiscal;
    private String serie;
    private String folio;
	private String fechaEmision;
	private String fechaPago;
    private String moneda;
    private BigDecimal subTotal = new BigDecimal(0).setScale(Etiquetas.DOS);
    private BigDecimal iva = new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);
    private BigDecimal tasaIva = new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);
    private BigDecimal ieps = new BigDecimal(0).setScale(Etiquetas.DOS);
    private BigDecimal tasaIeps = new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);
    private BigDecimal total = new BigDecimal(0).setScale(Etiquetas.DOS);
    private BigDecimal cantidadNeta = new BigDecimal(0).setScale(Etiquetas.DOS);
	private boolean incluyeIVA;
	private boolean incluyeRetenciones;
    private BigDecimal ivaRetenido = new BigDecimal(0).setScale(Etiquetas.DOS);
    private BigDecimal isrRetenido = new BigDecimal(0).setScale(Etiquetas.DOS);
    private String tipoDeComprobante;
    private String nombreEmisor;
    private String conceptos;
    
    private BigDecimal tua = new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);
    private BigDecimal ish = new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);
    private BigDecimal yri = new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);
    private BigDecimal otrosCargos = new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);
    
	public String getRfcEmisor() {
		return rfcEmisor;
	}
	public void setRfcEmisor(String rfc) {
		this.rfcEmisor = rfc;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public BigDecimal getIeps() {
		return ieps;
	}
	public void setIeps(BigDecimal ieps) {
		this.ieps = ieps;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getCantidadNeta() {
		return cantidadNeta;
	}
	public void setCantidadNeta(BigDecimal cantidadNeta) {
		this.cantidadNeta = cantidadNeta;
	}
	public boolean isIncluyeIVA() {
		return incluyeIVA;
	}
	public void setIncluyeIVA(boolean incluyeIVA) {
		this.incluyeIVA = incluyeIVA;
	}
	public boolean isIncluyeRetenciones() {
		return incluyeRetenciones;
	}
	public void setIncluyeRetenciones(boolean incluyeRetenciones) {
		this.incluyeRetenciones = incluyeRetenciones;
	}
	public BigDecimal getIvaRetenido() {
		return ivaRetenido;
	}
	public void setIvaRetenido(BigDecimal ivaRetenido) {
		this.ivaRetenido = ivaRetenido;
	}
	public BigDecimal getIsrRetenido() {
		return isrRetenido;
	}
	public void setIsrRetenido(BigDecimal isrRetenido) {
		this.isrRetenido = isrRetenido;
	}
	public String getRfcReceptor() {
		return rfcReceptor;
	}
	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}
	/**
	 * @return the tasaIva
	 */
	public BigDecimal getTasaIva() {
		return tasaIva;
	}
	/**
	 * @param tasaIva the tasaIva to set
	 */
	public void setTasaIva(BigDecimal tasaIva) {
		this.tasaIva = tasaIva;
	}
	/**
	 * @return the tasaIeps
	 */
	public BigDecimal getTasaIeps() {
		return tasaIeps;
	}
	/**
	 * @param tasaIeps the tasaIeps to set
	 */
	public void setTasaIeps(BigDecimal tasaIeps) {
		this.tasaIeps = tasaIeps;
	}
	
	public String getTipoDeComprobante() {
		return tipoDeComprobante;
	}
	public void setTipoDeComprobante(String tipoDeComprobante) {
		this.tipoDeComprobante = tipoDeComprobante;
	}
	public String getNombreEmisor() {
		return nombreEmisor;
	}
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}
	public String getConceptos() {
		return conceptos;
	}
	public void setConceptos(String conceptos) {
		this.conceptos = conceptos;
	}
	public BigDecimal getTua() {
		return tua;
	}
	public void setTua(BigDecimal tua) {
		this.tua = tua;
	}
	public BigDecimal getIsh() {
		return ish;
	}
	public void setIsh(BigDecimal ish) {
		this.ish = ish;
	}
	public BigDecimal getYri() {
		return yri;
	}
	public void setYri(BigDecimal yri) {
		this.yri = yri;
	}
	public BigDecimal getOtrosCargos() {
		return otrosCargos;
	}
	public void setOtrosCargos(BigDecimal otrosCargos) {
		this.otrosCargos = otrosCargos;
	}
	
	
}
