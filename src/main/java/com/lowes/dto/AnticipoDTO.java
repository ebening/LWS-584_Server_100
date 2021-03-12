package com.lowes.dto;

import java.util.List;

import com.lowes.entity.Compania;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.TipoProveedor;
import com.lowes.entity.ViajeConcepto;
import com.lowes.entity.ViajeDestino;
import com.lowes.entity.ViajeMotivo;

public class AnticipoDTO {

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
	private ViajeMotivo viajeMotivo;
	private String motivoAerolinea;
	private String fechaSalida;
	private String fechaRegreso;
	private Integer numeroPersonas;
	private String otroMotivo;
	private String otroDestino;
	private boolean transporteAereo;
	private List<AerolineaDTO> aerolineas;
	private List<ViajeConceptoDTO> conceptos;
	
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
	public ViajeDestino getViajeDestino() {
		return viajeDestino;
	}
	public void setViajeDestino(ViajeDestino viajeDestino) {
		this.viajeDestino = viajeDestino;
	}
	public ViajeMotivo getViajeMotivo() {
		return viajeMotivo;
	}
	public void setViajeMotivo(ViajeMotivo viajeMotivo) {
		this.viajeMotivo = viajeMotivo;
	}
	public String getMotivoAerolinea() {
		return motivoAerolinea;
	}
	public void setMotivoAerolinea(String motivoAerolinea) {
		this.motivoAerolinea = motivoAerolinea;
	}
	public String getFechaRegreso() {
		return fechaRegreso;
	}
	public void setFechaRegreso(String fechaRegreso) {
		this.fechaRegreso = fechaRegreso;
	}
	public String getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public Integer getNumeroPersonas() {
		return numeroPersonas;
	}
	public void setNumeroPersonas(Integer numeroPersonas) {
		this.numeroPersonas = numeroPersonas;
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
	public List<AerolineaDTO> getAerolineas() {
		return aerolineas;
	}
	public void setAerolineas(List<AerolineaDTO> aerolineas) {
		this.aerolineas = aerolineas;
	}
	public List<ViajeConceptoDTO> getConceptos() {
		return conceptos;
	}
	public void setConceptos(List<ViajeConceptoDTO> conceptos) {
		this.conceptos = conceptos;
	}
	public boolean isTransporteAereo() {
		return transporteAereo;
	}
	public void setTransporteAereo(boolean transporteAereo) {
		this.transporteAereo = transporteAereo;
	}
}
