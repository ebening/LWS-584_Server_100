/**
 * 
 */
package com.lowes.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase que abstrae las caracteristicas de un 
 * atributo en un Item Type de Content Manager.
 * @version 1.0, 23-02-2012.
 * @author Adanm.
 */
public class Atributo implements Cloneable {
	
	private String name = "";
	private String valor = "";
	private String description = "";
	private short tipo;
	private int longuitud;
	private boolean requerido;
	private boolean representativo;

	/**
	 * Constructor predefinido.
	 * Metodo que crea un nuevo Atributo
	 * con la informacion ingresada.
	 */
	public Atributo(){}
	
	public Atributo(String name, String valor){
		this.name = name;
		this.valor = valor;
	}
	
	public Atributo(String name, String valor, short tipo){
		this.name = name;
		this.valor = valor;
		this.tipo = tipo;
	}
	
	/**
	 * @return El valor del atributo.
	 */
	public String getValor() {
		return valor;	
	}
	
	/**
	 * @param valor - El valor para
	 * asignar al atributo.
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
		
	/**
	 * @return Nombre del Atributo.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name - Nombre para
	 * asignar al Atributo.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return La longitud
	 * del Atributo.
	 */
	public int getLonguitud() {
		return longuitud;
	}
	
	/**
	 * @param longuitud - Longitud
	 * para asignar al atributo.
	 */
	public void setLonguitud(int longuitud) {
		this.longuitud = longuitud;
	}
	
	/**
	 * @return true - Si el
	 * atributo es requerdio.
	 */
	public boolean isRequerido() {
		return requerido;
	}
	
	/**
	 * @param requerido - define si
	 * el atributo es requerido.
	 */
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}
	
	/**
	 * @return El tipo del Atributo.
	 */
	public short getTipo() {
		return tipo;
	}
	
	/**
	 * @param tipo - El tipo para
	 * asignar al Atributo.
	 */
	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the representativo
	 */
	public boolean isRepresentativo() {
		return representativo;
	}

	/**
	 * @param representativo the representativo to set
	 */
	public void setRepresentativo(boolean representativo) {
		this.representativo = representativo;
	}
	
	/**
	 * Metodo utilizado para
	 * crear una copia de este
	 * objeto Atributo.
	 */
	@Override
	public Atributo clone(){
		Atributo cloeAtributo = new Atributo();
		cloeAtributo.setName(this.name);
		cloeAtributo.setValor(this.valor);
		cloeAtributo.setDescription(this.description);
		cloeAtributo.setTipo(this.tipo);
		cloeAtributo.setLonguitud(this.longuitud);
		cloeAtributo.setRequerido(this.requerido);
		cloeAtributo.setRepresentativo(this.representativo);
		
		return cloeAtributo;
	}
	@Override
	public String toString(){
		return "\n     Nombre="+name+
				"\n    Desc="+description;
	}


}
