/**
 * 
 */
package com.lowes.dto;

import java.util.List;

import com.lowes.entity.FacturaArchivo;

/**
 * @author Adinfi
 *
 */
public class ArchivoDTO {
	private String originalFileName;
	private byte[] data;
	private Integer idTipoDocumento;
	
	public ArchivoDTO(){
	}
	
	public ArchivoDTO(String fileName, byte[] data){
		this.originalFileName = fileName;
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @return the originalFileName
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/**
	 * @param originalFileName the originalFileName to set
	 */
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	/**
	 * @return the idTipoDocumento
	 */
	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * @param idTipoDocumento the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
}
