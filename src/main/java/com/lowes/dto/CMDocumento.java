/**
 * 
 */
package com.lowes.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lowes.entity.Solicitud;

/**
 * @author Adinfi
 *
 */
public class CMDocumento {
	private int idDocumento;
	private int idSolicitud;
	private int idFactura;
	private String compDescripcion;
	private String serie;
	private String tipoSolDescripcion;
	private String tipoFactura;
	private String tipoDocumento;
	private String descProveedor;
	private String rfcProveedor;
	private String numProveedor;
	private BigDecimal montoTotal;
	private String monDescCorta;
	private Date fechaFactura;
	private Date fechaPagoFactura;
	private String par;
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
	 * @return the idFactura
	 */
	public int getIdFactura() {
		return idFactura;
	}
	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	/**
	 * @return the compDescripcion
	 */
	public String getCompDescripcion() {
		return compDescripcion;
	}
	/**
	 * @param compDescripcion the compDescripcion to set
	 */
	public void setCompDescripcion(String compDescripcion) {
		this.compDescripcion = compDescripcion;
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
	 * @return the tipoFactura
	 */
	public String getTipoFactura() {
		return tipoFactura;
	}
	/**
	 * @param tipoFactura the tipoFactura to set
	 */
	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
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
	 * @return the fechaFactura
	 */
	public Date getFechaFactura() {
		return fechaFactura;
	}
	/**
	 * @param fechaFactura the fechaFactura to set
	 */
	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	/**
	 * @return the fechaPagoFactura
	 */
	public Date getFechaPagoFactura() {
		return fechaPagoFactura;
	}
	/**
	 * @param fechaPagoFactura the fechaPagoFactura to set
	 */
	public void setFechaPagoFactura(Date fechaPagoFactura) {
		this.fechaPagoFactura = fechaPagoFactura;
	}
	/**
	 * @return the par
	 */
	public String getPar() {
		return par;
	}
	/**
	 * @param par the par to set
	 */
	public void setPar(String par) {
		this.par = par;
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
