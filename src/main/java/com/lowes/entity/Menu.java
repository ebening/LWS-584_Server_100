
package com.lowes.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Josue Sanchez
 * @version 1.0
 */

@Entity
@Table(name = "MENU" , schema = "LWS584")
public class Menu implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idMenu;
	private Menu menuPadre;
	private String nombre;
	private String descripcion;
	private String ruta;
	private short esNodo;
	private Integer orden;
	private String classIcon;
	private List<Menu> menus = new ArrayList<Menu>(0);

	public Menu() {
	}

	public Menu(int idMenu, String nombre, String descripcion, short esNodo) {
		this.idMenu = idMenu;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.esNodo = esNodo;
	}

	public Menu(int idMenu, Menu menuPadre, String nombre, String descripcion, String ruta, short esNodo, Integer orden, String classIcon,
			List<Menu> menus) {
		this.idMenu = idMenu;
		this.menuPadre = menuPadre;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ruta = ruta;
		this.esNodo = esNodo;
		this.orden = orden;
		this.classIcon = classIcon;
		this.menus = menus;
	}

	@Id
	@Column(name = "ID_MENU", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_MENUPADRE")
	public Menu getMenuPadre() {
		return this.menuPadre;
	}

	public void setMenuPadre(Menu menuPadre) {
		this.menuPadre = menuPadre;
	}

	@Column(name = "NOMBRE", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "DESCRIPCION", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "RUTA", length = 100)
	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Column(name = "ES_NODO", nullable = false)
	public short getEsNodo() {
		return this.esNodo;
	}

	public void setEsNodo(short esNodo) {
		this.esNodo = esNodo;
	}

	@Column(name = "ORDEN")
	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
	@Column(name = "CLASS_ICON", length = 25)
	public String getClassIcon() {
		return this.classIcon;
	}

	public void setClassIcon(String classIcon) {
		this.classIcon = classIcon;
	}
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "menuPadre")
	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

}
