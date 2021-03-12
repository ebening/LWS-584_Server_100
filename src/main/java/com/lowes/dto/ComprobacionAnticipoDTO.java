package com.lowes.dto;

import java.math.BigDecimal;
import java.util.List;

import com.lowes.entity.Compania;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.TipoProveedor;
import com.lowes.entity.Usuario;

public class ComprobacionAnticipoDTO {

	private Compania compania;
 	private FormaPago formaPago;
 	private Locacion locacion;
	private Moneda moneda;
	private String importeTotal;
	private String concepto;
	private TipoProveedor tipoProveedor;
	private Proveedor proveedor;
	private Integer idSolicitudComprobacionSession;
	private Integer idSolicitudAnticipoSession;
	private Integer tipoDeBeneficiario;
	private Boolean creacion;
	private Boolean modificacion;
	private String saldo;
	private Integer idComprobacionDeposito;
	private String fecha_deposito;
	private String importeDeposito;
	
	//mike
	private Integer idSolicitud;
	private Integer idTipoSolicitud;
	private boolean solicitante;
	private Usuario usuarioCreacion;
	private Usuario usuarioSolicitante;
	private Integer idSolicitanteJefe;
	private Integer beneficiario;
	private Integer idSolicitante;
	private BigDecimal subTotal;
	private BigDecimal montoTotal;
	private List<FacturaSolicitudDTO> lstFactDto;
	private Integer espSolicitante;
	private Integer estatusSolicitud;
	private boolean esLocacionCSC;
	private List<Locacion> lcPermitidas;
	private List<CuentaContable> ccPermitidas;
	private String strMontoTotal;
	private BigDecimal montoTotalMultiple;
	
	//Comprobaci�n m�ltiple
	private boolean compMultiple;
	private List<ComprobacionAnticipoFacturaDTO> lstAnticipos;
	private List<ComprobacionAnticipoFacturaDTO> lstAnticiposIncluidos;
	private boolean hasChange;
	
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
	public Integer getIdSolicitudComprobacionSession() {
		return idSolicitudComprobacionSession;
	}
	public void setIdSolicitudComprobacionSession(Integer idSolicitudComprobacionSession) {
		this.idSolicitudComprobacionSession = idSolicitudComprobacionSession;
	}
	public Integer getIdSolicitudAnticipoSession() {
		return idSolicitudAnticipoSession;
	}
	public void setIdSolicitudAnticipoSession(Integer idSolicitudAnticipoSession) {
		this.idSolicitudAnticipoSession = idSolicitudAnticipoSession;
	}
	public Integer getTipoDeBeneficiario() {
		return tipoDeBeneficiario;
	}
	public void setTipoDeBeneficiario(Integer tipoDeBeneficiario) {
		this.tipoDeBeneficiario = tipoDeBeneficiario;
	}
	public Boolean getCreacion() {
		return creacion;
	}
	public void setCreacion(Boolean creacion) {
		this.creacion = creacion;
	}
	public Boolean getModificacion() {
		return modificacion;
	}
	public void setModificacion(Boolean modificacion) {
		this.modificacion = modificacion;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getFecha_deposito() {
		return fecha_deposito;
	}
	public void setFecha_deposito(String fecha_deposito) {
		this.fecha_deposito = fecha_deposito;
	}
	/**
	 * @return the idSolicitud
	 */
	public Integer getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * @return the idTipoSolicitud
	 */
	public Integer getIdTipoSolicitud() {
		return idTipoSolicitud;
	}
	/**
	 * @param idTipoSolicitud the idTipoSolicitud to set
	 */
	public void setIdTipoSolicitud(Integer idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}
	/**
	 * @return the solicitante
	 */
	public boolean isSolicitante() {
		return solicitante;
	}
	/**
	 * @param solicitante the solicitante to set
	 */
	public void setSolicitante(boolean solicitante) {
		this.solicitante = solicitante;
	}
	/**
	 * @return the usuarioCreacion
	 */
	public Usuario getUsuarioCreacion() {
		return usuarioCreacion;
	}
	/**
	 * @param usuarioCreacion the usuarioCreacion to set
	 */
	public void setUsuarioCreacion(Usuario usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	/**
	 * @return the usuarioSolicitante
	 */
	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}
	/**
	 * @param usuarioSolicitante the usuarioSolicitante to set
	 */
	public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}
	/**
	 * @return the idSolicitanteJefe
	 */
	public Integer getIdSolicitanteJefe() {
		return idSolicitanteJefe;
	}
	/**
	 * @param idSolicitanteJefe the idSolicitanteJefe to set
	 */
	public void setIdSolicitanteJefe(Integer idSolicitanteJefe) {
		this.idSolicitanteJefe = idSolicitanteJefe;
	}
	/**
	 * @return the beneficiario
	 */
	public Integer getBeneficiario() {
		return beneficiario;
	}
	/**
	 * @param beneficiario the beneficiario to set
	 */
	public void setBeneficiario(Integer beneficiario) {
		this.beneficiario = beneficiario;
	}
	/**
	 * @return the idSolicitante
	 */
	public Integer getIdSolicitante() {
		return idSolicitante;
	}
	/**
	 * @param idSolicitante the idSolicitante to set
	 */
	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
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
	 * @return the montoTotal
	 */
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}
	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
	/**
	 * @return the lstFactDto
	 */
	public List<FacturaSolicitudDTO> getLstFactDto() {
		return lstFactDto;
	}
	/**
	 * @param lstFactDto the lstFactDto to set
	 */
	public void setLstFactDto(List<FacturaSolicitudDTO> lstFactDto) {
		this.lstFactDto = lstFactDto;
	}
	/**
	 * @return the espSolicitante
	 */
	public Integer getEspSolicitante() {
		return espSolicitante;
	}
	/**
	 * @param espSolicitante the espSolicitante to set
	 */
	public void setEspSolicitante(Integer espSolicitante) {
		this.espSolicitante = espSolicitante;
	}
	/**
	 * @return the estatusSolicitud
	 */
	public Integer getEstatusSolicitud() {
		return estatusSolicitud;
	}
	/**
	 * @param estatusSolicitud the estatusSolicitud to set
	 */
	public void setEstatusSolicitud(Integer estatusSolicitud) {
		this.estatusSolicitud = estatusSolicitud;
	}
	/**
	 * @return the esLocacionCSC
	 */
	public boolean getEsLocacionCSC() {
		return esLocacionCSC;
	}
	/**
	 * @param esLocacionCSC the esLocacionCSC to set
	 */
	public void setEsLocacionCSC(boolean esLocacionCSC) {
		this.esLocacionCSC = esLocacionCSC;
	}
	/**
	 * @return the lcPermitidas
	 */
	public List<Locacion> getLcPermitidas() {
		return lcPermitidas;
	}
	/**
	 * @param lcPermitidas the lcPermitidas to set
	 */
	public void setLcPermitidas(List<Locacion> lcPermitidas) {
		this.lcPermitidas = lcPermitidas;
	}
	/**
	 * @return the ccPermitidas
	 */
	public List<CuentaContable> getCcPermitidas() {
		return ccPermitidas;
	}
	/**
	 * @param ccPermitidas the ccPermitidas to set
	 */
	public void setCcPermitidas(List<CuentaContable> ccPermitidas) {
		this.ccPermitidas = ccPermitidas;
	}
	/**
	 * @return the strMontoTotal
	 */
	public String getStrMontoTotal() {
		return strMontoTotal;
	}
	/**
	 * @param strMontoTotal the strMontoTotal to set
	 */
	public void setStrMontoTotal(String strMontoTotal) {
		this.strMontoTotal = strMontoTotal;
	}
	/**
	 * @return the compMultiple
	 */
	public boolean isCompMultiple() {
		return compMultiple;
	}
	/**
	 * @param compMultiple the compMultiple to set
	 */
	public void setCompMultiple(boolean compMultiple) {
		this.compMultiple = compMultiple;
	}
	/**
	 * @return the lstAnticipos
	 */
	public List<ComprobacionAnticipoFacturaDTO> getLstAnticipos() {
		return lstAnticipos;
	}
	/**
	 * @param lstAnticipos the lstAnticipos to set
	 */
	public void setLstAnticipos(List<ComprobacionAnticipoFacturaDTO> lstAnticipos) {
		this.lstAnticipos = lstAnticipos;
	}

	public String getImporteDeposito() {
		return importeDeposito;
	}
	
	public void setImporteDeposito(String importeDeposito) {
		this.importeDeposito = importeDeposito;
	}
	
	public Integer getIdComprobacionDeposito() {
		return idComprobacionDeposito;
	}
	
	public void setIdComprobacionDeposito(Integer idComprobacionDeposito) {
		this.idComprobacionDeposito = idComprobacionDeposito;
	}
	
	public List<ComprobacionAnticipoFacturaDTO> getLstAnticiposIncluidos() {
		return lstAnticiposIncluidos;
	}
	
	public void setLstAnticiposIncluidos(List<ComprobacionAnticipoFacturaDTO> lstAnticiposIncluidos) {
		this.lstAnticiposIncluidos = lstAnticiposIncluidos;
	}
	
	public BigDecimal getMontoTotalMultiple() {
		return montoTotalMultiple;
	}
	public void setMontoTotalMultiple(BigDecimal montoTotalMultiple) {
		this.montoTotalMultiple = montoTotalMultiple;
	}
	public boolean isHasChange() {
		return hasChange;
	}
	public void setHasChange(boolean hasChange) {
		this.hasChange = hasChange;
	}
	

}
