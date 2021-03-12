/**
 * 
 */
package com.lowes.dto;

/**
 * @author miguelr
 *
 */
public class BeneficiarioDTO {
	private Integer idBeneficiario;
	private String descripcion;
	
	public BeneficiarioDTO(int idBeneficiario, String descripcion) {
		this.idBeneficiario = idBeneficiario;
		this.descripcion = descripcion;
	}
	/**
	 * @return the idBeneficiario
	 */
	public Integer getIdBeneficiario() {
		return idBeneficiario;
	}
	/**
	 * @param idBeneficiario the idBeneficiario to set
	 */
	public void setIdBeneficiario(Integer idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
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
}
