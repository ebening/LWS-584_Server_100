/**
 * 
 */
package com.lowes.dto;

import java.math.BigDecimal;
import java.util.List;

import com.lowes.entity.FacturaArchivo;

/**
 * @author miguelr
 *
 */
public class FacturaSolicitudDTO {
	private Integer idFactura;
	private Integer idCompania;
	private boolean conCompFiscal;
	private List<ArchivoDTO> files;
	private List<FacturaArchivo> facturaArchivos;
	private Integer locacion;
	private String concepto;
	private Integer proveedor;
	private String proveedorLibre;
	private String RFC;
	private String folio;
	private String serie;
	private String folioFiscal;
	private String fecha;
	private Integer moneda;
	private boolean conRetenciones;
	private BigDecimal subTotal;
	private BigDecimal IVA;
	private BigDecimal tasaIva;
	private BigDecimal IVARetenido;
	private BigDecimal IEPS;
	private BigDecimal tasaIeps;
	private BigDecimal ISRRetenido;
	private BigDecimal total;
	private Integer cuentaContable;
	private String razonSocial;
	private Integer tipoFactura;
	private String strSubTotal;
	private String strIeps;
	private String strTotal;
	private String strIva_retenido;
	private String strIsr_retenido;
	private String strIva;
	private boolean facturaMultiple;
	private BigDecimal montoParcialFactura;
	private BigDecimal montoRestanteFactura;
	private Integer tipoSolicitud;


	public FacturaSolicitudDTO() {
	}
	
	public FacturaSolicitudDTO(ComprobacionAnticipoViajeDesgloseDTO obj) {
		
	}
	
	/**
	 * @return the conCompFiscal
	 */
	public boolean isConCompFiscal() {
		return conCompFiscal;
	}
	/**
	 * @param conCompFiscal the conCompFiscal to set
	 */
	public void setConCompFiscal(boolean conCompFiscal) {
		this.conCompFiscal = conCompFiscal;
	}
	/**
	 * @return the proveedor
	 */
	public Integer getProveedor() {
		return proveedor;
	}
	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Integer proveedor) {
		this.proveedor = proveedor;
	}
	/**
	 * @return the rFC
	 */
	public String getRFC() {
		return RFC;
	}
	/**
	 * @param rFC the rFC to set
	 */
	public void setRFC(String rFC) {
		RFC = rFC;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}
	/**
	 * @return the folioFiscal
	 */
	public String getFolioFiscal() {
		return folioFiscal;
	}
	/**
	 * @param folioFiscal the folioFiscal to set
	 */
	public void setFolioFiscal(String folioFiscal) {
		this.folioFiscal = folioFiscal;
	}
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the subTotal
	 */
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}
	/**
	 * @return the moneda
	 */
	public Integer getMoneda() {
		return moneda;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(Integer moneda) {
		this.moneda = moneda;
	}
	/**
	 * @return the conRetenciones
	 */
	public boolean isConRetenciones() {
		return conRetenciones;
	}
	/**
	 * @param conRetenciones the conRetenciones to set
	 */
	public void setConRetenciones(boolean conRetenciones) {
		this.conRetenciones = conRetenciones;
	}
	/**
	 * @return the iVA
	 */
	public BigDecimal getIVA() {
		return IVA;
	}
	/**
	 * @param iVA the iVA to set
	 */
	public void setIVA(BigDecimal iVA) {
		IVA = iVA;
	}
	/**
	 * @return the iVARetenido
	 */
	public BigDecimal getIVARetenido() {
		return IVARetenido;
	}
	/**
	 * @param iVARetenido the iVARetenido to set
	 */
	public void setIVARetenido(BigDecimal iVARetenido) {
		IVARetenido = iVARetenido;
	}
	/**
	 * @return the iEPS
	 */
	public BigDecimal getIEPS() {
		return IEPS;
	}
	/**
	 * @param iEPS the iEPS to set
	 */
	public void setIEPS(BigDecimal iEPS) {
		IEPS = iEPS;
	}
	/**
	 * @return the iSRRetenido
	 */
	public BigDecimal getISRRetenido() {
		return ISRRetenido;
	}
	/**
	 * @param iSRRetenido the iSRRetenido to set
	 */
	public void setISRRetenido(BigDecimal iSRRetenido) {
		ISRRetenido = iSRRetenido;
	}
	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	/**
	 * @return the cuentaContable
	 */
	public Integer getCuentaContable() {
		return cuentaContable;
	}
	/**
	 * @param cuentaContable the cuentaContable to set
	 */
	public void setCuentaContable(Integer cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	/**
	 * @return the locacion
	 */
	public Integer getLocacion() {
		return locacion;
	}
	/**
	 * @param locacion the locacion to set
	 */
	public void setLocacion(Integer locacion) {
		this.locacion = locacion;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the idCompania
	 */
	public Integer getIdCompania() {
		return idCompania;
	}
	/**
	 * @param idCompania the idCompania to set
	 */
	public void setIdCompania(Integer idCompania) {
		this.idCompania = idCompania;
	}
	/**
	 * @return the idFactura
	 */
	public Integer getIdFactura() {
		return idFactura;
	}
	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}
	/**
	 * @return the files
	 */
	public List<ArchivoDTO> getFiles() {
		return files;
	}
	/**
	 * @param files the files to set
	 */
	public void setFiles(List<ArchivoDTO> files) {
		this.files = files;
	}
	/**
	 * @return the facturaArchivos
	 */
	public List<FacturaArchivo> getFacturaArchivos() {
		return facturaArchivos;
	}
	/**
	 * @param facturaArchivos the facturaArchivos to set
	 */
	public void setFacturaArchivos(List<FacturaArchivo> facturaArchivos) {
		this.facturaArchivos = facturaArchivos;
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
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public Integer getTipoFactura() {
		return tipoFactura;
	}
	public void setTipoFactura(Integer tipoFactura) {
		this.tipoFactura = tipoFactura;
	}
	public String getStrSubTotal() {
		return strSubTotal;
	}
	public void setStrSubTotal(String strSubTotal) {
		this.strSubTotal = strSubTotal;
	}
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
	public String getStrIva() {
		return strIva;
	}
	public void setStrIva(String strIva) {
		this.strIva = strIva;
	}
	public boolean isFacturaMultiple() {
		return facturaMultiple;
	}
	public void setFacturaMultiple(boolean facturaMultiple) {
		this.facturaMultiple = facturaMultiple;
	}
	public BigDecimal getMontoParcialFactura() {
		return montoParcialFactura;
	}
	public void setMontoParcialFactura(BigDecimal montoParcialFactura) {
		this.montoParcialFactura = montoParcialFactura;
	}
	public BigDecimal getMontoRestanteFactura() {
		return montoRestanteFactura;
	}
	public void setMontoRestanteFactura(BigDecimal montoRestanteFactura) {
		this.montoRestanteFactura = montoRestanteFactura;
	}
	public String getProveedorLibre() {
		return proveedorLibre;
	}
	public void setProveedorLibre(String proveedorLibre) {
		this.proveedorLibre = proveedorLibre;
	}

	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	
	
}
