/**
 * 
 */
package com.lowes.dto;

import java.math.BigDecimal;

import com.lowes.entity.SolicitudAnticipoViajeConceptos;
import com.lowes.entity.ViajeConcepto;

/**
 * @author miguelr
 *
 */
public class ViajeConceptoDTO {
	private Integer idViajeConcepto;
	private String idViajeConceptoString;
	private String descripcion;
	private short esCalculado;
	private short esOtro;
	private String importe;
	private boolean seleccionado;
	
	public ViajeConceptoDTO(){}

	public ViajeConceptoDTO(ViajeConcepto concepto) {
		this.idViajeConcepto = concepto.getIdViajeConcepto();
		this.idViajeConceptoString = concepto.getIdViajeConceptoString();
		this.descripcion = concepto.getDescripcion();
		this.esCalculado = concepto.getEsCalculado();
		this.esOtro = concepto.getEsOtro();
		if (concepto.getImporte() != null){
			this.importe = String.valueOf(concepto.getImporte());
			this.seleccionado = true;
		}
		else{
			this.importe = "0.00";
			this.seleccionado = false;
		}
	}

	public ViajeConceptoDTO(SolicitudAnticipoViajeConceptos concepto) {
		this.idViajeConcepto = concepto.getIdSolicitudAnticipoViajeConceptos();
		this.idViajeConceptoString = concepto.getViajeConcepto().getIdViajeConceptoString();
		if(concepto.getViajeConcepto().getEsOtro() == 0)
			this.descripcion = concepto.getViajeConcepto().getDescripcion();
		this.esCalculado = concepto.getViajeConcepto().getEsCalculado();
		this.esOtro = concepto.getViajeConcepto().getEsOtro();
		this.importe = String.valueOf(concepto.getImporte());
		this.seleccionado = false;
	}

	/**
	 * @return the idViajeConcepto
	 */
	public Integer getIdViajeConcepto() {
		return idViajeConcepto;
	}

	/**
	 * @param idViajeConcepto the idViajeConcepto to set
	 */
	public void setIdViajeConcepto(Integer idViajeConcepto) {
		this.idViajeConcepto = idViajeConcepto;
	}

	/**
	 * @return the idViajeConceptoString
	 */
	public String getIdViajeConceptoString() {
		return idViajeConceptoString;
	}

	/**
	 * @param idViajeConceptoString the idViajeConceptoString to set
	 */
	public void setIdViajeConceptoString(String idViajeConceptoString) {
		this.idViajeConceptoString = idViajeConceptoString;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the esCalculado
	 */
	public short getEsCalculado() {
		return esCalculado;
	}

	/**
	 * @param esCalculado the esCalculado to set
	 */
	public void setEsCalculado(short esCalculado) {
		this.esCalculado = esCalculado;
	}

	/**
	 * @return the esOtro
	 */
	public short getEsOtro() {
		return esOtro;
	}

	/**
	 * @param esOtro the esOtro to set
	 */
	public void setEsOtro(short esOtro) {
		this.esOtro = esOtro;
	}

	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}

	/**
	 * @return the seleccionado
	 */
	public boolean isSeleccionado() {
		return seleccionado;
	}

	/**
	 * @param seleccionado the seleccionado to set
	 */
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	
}