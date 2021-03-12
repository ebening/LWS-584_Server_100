package com.lowes.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lowes.entity.CuentaContable;
import com.lowes.entity.Locacion;
import com.lowes.entity.ViajeConcepto;
import com.lowes.entity.ViajeTipoAlimentos;

public class ComprobacionAnticipoViajeDesgloseDTO {
	private Integer idFactura;
	private Integer idFacturaGV;
	private String fecha_factura;
	private String fecha_gasto;
	private String comercio;
	private String serie;
	private String folio;
	private String folioFiscal;
	private ViajeConcepto viajeConcepto;
	private ViajeTipoAlimentos viajeTipoAlimentos;
	private Integer idViajeConcepto;
	private Integer idViajeTipoAlimento;
	private String ciudadTexto;
	private BigDecimal subTotal;
	private BigDecimal iva;
	private BigDecimal ieps;
	private BigDecimal otrosImpuestos;
	private BigDecimal sumaOtrosImpuestos;
	private BigDecimal tua;
	private BigDecimal ish;
	private BigDecimal yri;	
	private BigDecimal total;
	private Locacion locacion;
	private Integer idLocacion;
	private Integer idMoneda;
	private CuentaContable cuentaContable;
	private Integer idCuentaContable;
	private boolean segundaAprobacion;
	private boolean desglosar;
	private Integer numeroPersonas;
	private List<ComprobacionAnticipoViajeDesgloseDetalleDTO> lstDesgloseDetalle; 
	private String concepto;
	private List<ArchivoDTO> archivos = new ArrayList<ArchivoDTO>();
	private boolean conCompFiscal;
	private Integer proveedor;
	private Integer proveedorLibre;
	private FacturaSolicitudDTO factura;
	private boolean desgloseCompleto;
	
	public ComprobacionAnticipoViajeDesgloseDTO () {
		
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	public String getFecha_factura() {
		return fecha_factura;
	}

	public void setFecha_factura(String fecha_factura) {
		this.fecha_factura = fecha_factura;
	}

	public String getFecha_gasto() {
		return fecha_gasto;
	}

	public void setFecha_gasto(String fecha_gasto) {
		this.fecha_gasto = fecha_gasto;
	}

	public String getComercio() {
		return comercio;
	}

	public void setComercio(String comercio) {
		this.comercio = comercio;
	}

	public ViajeConcepto getViajeConcepto() {
		return viajeConcepto;
	}

	public void setViajeConcepto(ViajeConcepto viajeConcepto) {
		this.viajeConcepto = viajeConcepto;
	}

	public String getCiudadTexto() {
		return ciudadTexto;
	}

	public void setCiudadTexto(String ciudadTexto) {
		this.ciudadTexto = ciudadTexto;
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

	public BigDecimal getOtrosImpuestos() {
		return otrosImpuestos;
	}

	public void setOtrosImpuestos(BigDecimal otrosImpuestos) {
		this.otrosImpuestos = otrosImpuestos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Locacion getLocacion() {
		return locacion;
	}

	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}

	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public boolean isSegundaAprobacion() {
		return segundaAprobacion;
	}

	public void setSegundaAprobacion(boolean segundaAprobacion) {
		this.segundaAprobacion = segundaAprobacion;
	}

	public boolean isDesglosar() {
		return desglosar;
	}

	public void setDesglosar(boolean desglosar) {
		this.desglosar = desglosar;
	}

	public Integer getNumeroPersonas() {
		return numeroPersonas;
	}

	public void setNumeroPersonas(Integer numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFolioFiscal() {
		return folioFiscal;
	}

	public void setFolioFiscal(String folioFiscal) {
		this.folioFiscal = folioFiscal;
	}

	public Integer getIdViajeConcepto() {
		return idViajeConcepto;
	}

	public void setIdViajeConcepto(Integer idViajeConcepto) {
		this.idViajeConcepto = idViajeConcepto;
	}

	public Integer getIdCuentaContable() {
		return idCuentaContable;
	}

	public void setIdCuentaContable(Integer idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}

	public Integer getIdLocacion() {
		return idLocacion;
	}

	public void setIdLocacion(Integer idLocacion) {
		this.idLocacion = idLocacion;
	}

	public Integer getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}
	
	public List<ComprobacionAnticipoViajeDesgloseDetalleDTO> getLstDesgloseDetalle() {
		return lstDesgloseDetalle;
	}

	public void setLstDesgloseDetalle(List<ComprobacionAnticipoViajeDesgloseDetalleDTO> lstDesgloseDetalle) {
		this.lstDesgloseDetalle = lstDesgloseDetalle;
	}

	public boolean isDesgloseCompleto() {
		return desgloseCompleto;
	}

	public void setDesgloseCompleto(boolean desgloseCompleto) {
		this.desgloseCompleto = desgloseCompleto;
	}

	public List<ArchivoDTO> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<ArchivoDTO> archivos) {
		this.archivos = archivos;
	}

	public FacturaSolicitudDTO getFactura() {
		return factura;
	}

	public void setFactura(FacturaSolicitudDTO factura) {
		this.factura = factura;
	}

	public boolean isConCompFiscal() {
		return conCompFiscal;
	}

	public void setConCompFiscal(boolean conCompFiscal) {
		this.conCompFiscal = conCompFiscal;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Integer getProveedor() {
		return proveedor;
	}

	public void setProveedor(Integer proveedor) {
		this.proveedor = proveedor;
	}

	public Integer getProveedorLibre() {
		return proveedorLibre;
	}

	public void setProveedorLibre(Integer proveedorLibre) {
		this.proveedorLibre = proveedorLibre;
	}

	public ViajeTipoAlimentos getViajeTipoAlimentos() {
		return viajeTipoAlimentos;
	}

	public void setViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos) {
		this.viajeTipoAlimentos = viajeTipoAlimentos;
	}

	public Integer getIdViajeTipoAlimento() {
		return idViajeTipoAlimento;
	}

	public void setIdViajeTipoAlimento(Integer idViajeTipoAlimento) {
		this.idViajeTipoAlimento = idViajeTipoAlimento;
	}

	public Integer getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Integer getIdFacturaGV() {
		return idFacturaGV;
	}

	public void setIdFacturaGV(Integer idFacturaGV) {
		this.idFacturaGV = idFacturaGV;
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

	public BigDecimal getSumaOtrosImpuestos() {
		return sumaOtrosImpuestos;
	}

	public void setSumaOtrosImpuestos(BigDecimal sumaOtrosImpuestos) {
		this.sumaOtrosImpuestos = sumaOtrosImpuestos;
	}
}
