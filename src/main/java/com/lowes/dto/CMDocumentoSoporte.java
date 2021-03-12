/**
 * 
 */
package com.lowes.dto;

import com.lowes.entity.Solicitud;

/**
 * @author Adinfi
 *
 */
public class CMDocumentoSoporte {
	private int idDocumento;
	private int idSolicitud;
	private String tipoDocumento;
	private String tipoSolDescripcion;
	private int numEmpleado;
	private String locacionDesc;
	private String companiaDesc;
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
	 * @return the numEmpleado
	 */
	public int getNumEmpleado() {
		return numEmpleado;
	}
	/**
	 * @param numEmpleado the numEmpleado to set
	 */
	public void setNumEmpleado(int numEmpleado) {
		this.numEmpleado = numEmpleado;
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
}
