/**
 * 
 */
package com.lowes.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lowes.entity.Moneda;

/**
 * @author miguelr
 *
 */
public class ComprobacionAnticipoFacturaDTO {
	private int idSolicitud;
	private Date fechaPago;
	private Date creacionFecha;
	private BigDecimal montoTotal;
	private BigDecimal montoComprobado;
	private BigDecimal montoSaldo;
	private Moneda moneda;
	private String concepto;
	
	private String fechaPagoStr;
	private String creacionFechaStr;
	private int idEstadoSolicitud;
	
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
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}
	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
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
	 * @return the montoComprobado
	 */
	public BigDecimal getMontoComprobado() {
		return montoComprobado;
	}
	/**
	 * @param montoComprobado the montoComprobado to set
	 */
	public void setMontoComprobado(BigDecimal montoComprobado) {
		this.montoComprobado = montoComprobado;
	}
	/**
	 * @return the montoSaldo
	 */
	public BigDecimal getMontoSaldo() {
		return montoSaldo;
	}
	/**
	 * @param montoSaldo the montoSaldo to set
	 */
	public void setMontoSaldo(BigDecimal montoSaldo) {
		this.montoSaldo = montoSaldo;
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
	public String getFechaPagoStr() {
		return fechaPagoStr;
	}
	public void setFechaPagoStr(String fechaPagoStr) {
		this.fechaPagoStr = fechaPagoStr;
	}
	public String getCreacionFechaStr() {
		return creacionFechaStr;
	}
	public void setCreacionFechaStr(String creacionFechaStr) {
		this.creacionFechaStr = creacionFechaStr;
	}
	public int getIdEstadoSolicitud() {
		return idEstadoSolicitud;
	}
	public void setIdEstadoSolicitud(int idEstadoSolicitud) {
		this.idEstadoSolicitud = idEstadoSolicitud;
	}

}
