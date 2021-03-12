/**
 * 
 */
package com.lowes.dto;

import java.math.BigDecimal;
import java.util.List;

import com.lowes.entity.Compania;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Usuario;


/**
 * @author Adinfi
 *
 */
public class SolicitudDTO {

	private Integer idSolicitud;
	private Integer idTipoSolicitud;
	private boolean solicitante;
	private Usuario usuarioCreacion;
	private Usuario usuarioSolicitante;
	private Integer idSolicitanteJefe;
	private Moneda moneda;
	private FormaPago formaPago;
	private Integer beneficiario;
	private Integer idSolicitante;
	private Compania compania;
	private Locacion locacion;
	private String concepto;
	private Integer idSolicitudSession;
	private BigDecimal subTotal;
	private BigDecimal montoTotal;
	private List<FacturaSolicitudDTO> lstFactDto;
	private boolean modificacion;
	private boolean creacion;
	private Integer espSolicitante;
	private Integer estatusSolicitud;
	private boolean esLocacionCSC;
	private List<Locacion> lcPermitidas;
	private List<CuentaContable> ccPermitidas;
	private String strMontoTotal;
	
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
	 * @return the moneda
	 */
	public Moneda getMoneda() {
		return moneda;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	/**
	 * @return the formaPago
	 */
	public FormaPago getFormaPago() {
		return formaPago;
	}
	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}
	/**
	 * @return the compania
	 */
	public Compania getCompania() {
		return compania;
	}
	/**
	 * @param compania the compania to set
	 */
	public void setCompania(Compania compania) {
		this.compania = compania;
	}
	/**
	 * @return the locacion
	 */
	public Locacion getLocacion() {
		return locacion;
	}
	/**
	 * @param locacion the locacion to set
	 */
	public void setLocacion(Locacion locacion) {
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
	 * @return the idSolicitudSession
	 */
	public Integer getIdSolicitudSession() {
		return idSolicitudSession;
	}
	/**
	 * @param idSolicitudSession the idSolicitudSession to set
	 */
	public void setIdSolicitudSession(Integer idSolicitudSession) {
		this.idSolicitudSession = idSolicitudSession;
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
	 * @param facturas the lstFactDto to set
	 */
	public void setLstFactDto(List<FacturaSolicitudDTO> lstFactDto) {
		this.lstFactDto = lstFactDto;
	}
	/**
	 * @return the modificacion
	 */
	public boolean isModificacion() {
		return modificacion;
	}
	/**
	 * @param modificacion the modificacion to set
	 */
	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}
	/**
	 * @return the creacion
	 */
	public boolean isCreacion() {
		return creacion;
	}
	/**
	 * @param creacion the creacion to set
	 */
	public void setCreacion(boolean creacion) {
		this.creacion = creacion;
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
	
	
	
}
