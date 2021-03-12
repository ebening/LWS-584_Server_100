/**
 * 
 */
package com.lowes.dto;

import java.math.BigDecimal;

import com.lowes.entity.SolicitudAnticipoViajeAerolinea;

/**
 * @author miguelr
 *
 */
public class AerolineaDTO {
	private Integer id;
	private String nombre;
	private String costo;
	private boolean seleccionado;
	
	public AerolineaDTO(){}
	
	public AerolineaDTO(Integer id, boolean seleccionado){
		this.id = id;
		this.seleccionado = seleccionado;
		this.costo = "0.0";
	}
	
	public AerolineaDTO(SolicitudAnticipoViajeAerolinea aerolinea, boolean seleccionado) {
		this.id = aerolinea.getIdSolicitudAnticipoViajeAerolinea();
		this.nombre = aerolinea.getNombreAerolinea();
		this.costo = String.valueOf(aerolinea.getCostoAerolinea());
		this.seleccionado = seleccionado;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the costo
	 */
	public String getCosto() {
		return costo;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(String costo) {
		this.costo = costo;
	}

	public boolean getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
}
