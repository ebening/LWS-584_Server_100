/**
 * 
 */
package com.lowes.dto;

/**
 * @author miguelr
 *
 */
public class EmailDTO {
	private String subject;
	private String mensaje;
	private String contenido;
	
	public EmailDTO(){
		
	}
	
	public EmailDTO(String subject, String mensaje, String contenido){
		this.subject = subject;
		this.mensaje = mensaje;
		this.contenido = contenido;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido;
	}
	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
}
