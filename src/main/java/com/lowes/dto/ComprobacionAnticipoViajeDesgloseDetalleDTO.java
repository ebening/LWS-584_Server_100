package com.lowes.dto;

import java.math.BigDecimal;

import com.lowes.entity.CuentaContable;
import com.lowes.entity.Locacion;
import com.lowes.entity.ViajeConcepto;
import com.lowes.entity.ViajeTipoAlimentos;

public class ComprobacionAnticipoViajeDesgloseDetalleDTO implements Cloneable {

	private Integer idFacturaDesglose;
	private BigDecimal subTotal;
	private BigDecimal otrosImpuestos;
	private ViajeConcepto viajeConcepto;
	private ViajeTipoAlimentos viajeTipoAlimentos;
	private Locacion locacion;
	private CuentaContable cuentaContable;
	private String nombre;
	
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
	 * @return the otrosImpuestos
	 */
	public BigDecimal getOtrosImpuestos() {
		return otrosImpuestos;
	}
	/**
	 * @param otrosImpuestos the otrosImpuestos to set
	 */
	public void setOtrosImpuestos(BigDecimal otrosImpuestos) {
		this.otrosImpuestos = otrosImpuestos;
	}
	/**
	 * @return the viajeConcepto
	 */
	public ViajeConcepto getViajeConcepto() {
		return viajeConcepto;
	}
	/**
	 * @param viajeConcepto the viajeConcepto to set
	 */
	public void setViajeConcepto(ViajeConcepto viajeConcepto) {
		this.viajeConcepto = viajeConcepto;
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
	 * @return the cuentaContable
	 */
	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}
	/**
	 * @param cuentaContable the cuentaContable to set
	 */
	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the viajeTipoAlimentos
	 */
	public ViajeTipoAlimentos getViajeTipoAlimentos() {
		return viajeTipoAlimentos;
	}
	/**
	 * @param viajeTipoAlimentos the viajeTipoAlimentos to set
	 */
	public void setViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos) {
		this.viajeTipoAlimentos = viajeTipoAlimentos;
	}
	
	public ComprobacionAnticipoViajeDesgloseDetalleDTO clone() { 
		try { 
		// call clone in Object. 
			return (ComprobacionAnticipoViajeDesgloseDetalleDTO) super.clone(); 
		} catch(CloneNotSupportedException e) { 
			System.out.println("Cloning not allowed."); 
			return this; 
		} 
	}
	public Integer getIdFacturaDesglose() {
		return idFacturaDesglose;
	}
	public void setIdFacturaDesglose(Integer idFacturaDesglose) {
		this.idFacturaDesglose = idFacturaDesglose;
	} 
}
