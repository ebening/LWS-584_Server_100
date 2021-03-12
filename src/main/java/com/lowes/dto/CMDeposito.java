/**
 * 
 */
package com.lowes.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lowes.entity.Solicitud;

/**
 * @author miguelrguillen
 *
 */
public class CMDeposito {
	private int idDocumento;
	
	private int idSolicitud;
	private String tipoDocumento;
	private String tipoSolDescripcion;
	private String solicitanteNumEmpleado;
	private String locacionDesc;
	private String companiaDesc;
	private String descProveedor;
	private String rfcProveedor;
	private String numProveedor;
	private BigDecimal montoTotal;
	private String monDescCorta;
	private Date creacionFecha;
	private Date fechaDeposito;

	private String ruta;
	private Solicitud solicitud;
	
	/**
	 * @return the idSolicitud
	 */
	public int getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * @return the tipoSolDescripcion
	 */
	public String getTipoSolDescripcion() {
		return tipoSolDescripcion;
	}
	/**
	 * @param tipoSolDescripcion the tipoSolDescripcion to set
	 */
	public void setTipoSolDescripcion(String tipoSolDescripcion) {
		this.tipoSolDescripcion = tipoSolDescripcion;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the descProveedor
	 */
	public String getDescProveedor() {
		return descProveedor;
	}
	/**
	 * @param descProveedor the descProveedor to set
	 */
	public void setDescProveedor(String descProveedor) {
		this.descProveedor = descProveedor;
	}
	/**
	 * @return the rfcProveedor
	 */
	public String getRfcProveedor() {
		return rfcProveedor;
	}
	/**
	 * @param rfcProveedor the rfcProveedor to set
	 */
	public void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}
	/**
	 * @return the numProveedor
	 */
	public String getNumProveedor() {
		return numProveedor;
	}
	/**
	 * @param numProveedor the numProveedor to set
	 */
	public void setNumProveedor(String numProveedor) {
		this.numProveedor = numProveedor;
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
	 * @return the monDescCorta
	 */
	public String getMonDescCorta() {
		return monDescCorta;
	}
	/**
	 * @param monDescCorta the monDescCorta to set
	 */
	public void setMonDescCorta(String monDescCorta) {
		this.monDescCorta = monDescCorta;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the idDocumento
	 */
	public int getIdDocumento() {
		return idDocumento;
	}
	/**
	 * @param idDocumento the idDocumento to set
	 */
	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}
	/**
	 * @return the solicitud
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}
	/**
	 * @param solicitud the solicitud to set
	 */
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}
	/**
	 * @return the solicitanteNumEmpleado
	 */
	public String getSolicitanteNumEmpleado() {
		return solicitanteNumEmpleado;
	}
	/**
	 * @param solicitanteNumEmpleado the solicitanteNumEmpleado to set
	 */
	public void setSolicitanteNumEmpleado(String solicitanteNumEmpleado) {
		this.solicitanteNumEmpleado = solicitanteNumEmpleado;
	}
	/**
	 * @return the locacionDesc
	 */
	public String getLocacionDesc() {
		return locacionDesc;
	}
	/**
	 * @param locacionDesc the locacionDesc to set
	 */
	public void setLocacionDesc(String locacionDesc) {
		this.locacionDesc = locacionDesc;
	}
	/**
	 * @return the companiaDesc
	 */
	public String getCompaniaDesc() {
		return companiaDesc;
	}
	/**
	 * @param companiaDesc the companiaDesc to set
	 */
	public void setCompaniaDesc(String companiaDesc) {
		this.companiaDesc = companiaDesc;
	}
	/**
	 * @return the fechaDeposito
	 */
	public Date getFechaDeposito() {
		return fechaDeposito;
	}
	/**
	 * @param fechaDeposito the fechaDeposito to set
	 */
	public void setFechaDeposito(Date fechaDeposito) {
		this.fechaDeposito = fechaDeposito;
	}
	/**
	 * @return the creacionFecha
	 */
	public Date getCreacionFecha() {
		return creacionFecha;
	}
	/**
	 * @param creacionFecha the creacionFecha to set
	 */
	public void setCreacionFecha(Date creacionFecha) {
		this.creacionFecha = creacionFecha;
	}
}
