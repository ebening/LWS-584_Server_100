package com.lowes.entity;


import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  @author Josue Sanchez
 */

@Entity
@Table(name = "TESTHIBERNATE" , schema = "LWS584")
public class TestHibernate implements java.io.Serializable {

	private int id;
	private String nombre;
	private Double precio;

	public TestHibernate() {
	}

	public TestHibernate(int id) {
		this.id = id;
	}

	public TestHibernate(int id, String nombre, Double precio) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}

	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "NOMBRE", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "PRECIO", precision = 53, scale = 0)
	public Double getPrecio() {
		return this.precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

}
