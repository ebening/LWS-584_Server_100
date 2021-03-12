package com.lowes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@Entity
@Table(name = "PERFIL_MENU" , schema = "LWS584")
public class PerfilMenu implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idPerfilMenu;
	private Menu menu;
	private Perfil perfil;

	public PerfilMenu() {
	}

	public PerfilMenu(int idPerfilMenu, Menu menu,  Perfil perfil) {
		this.idPerfilMenu = idPerfilMenu;
		this.menu = menu;
		this.perfil = perfil;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PERFIL_MENU", unique = true, nullable = false)
	public int getIdPerfilMenu() {
		return this.idPerfilMenu;
	}

	public void setIdPerfilMenu(int idPerfilMenu) {
		this.idPerfilMenu = idPerfilMenu;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_MENU", nullable = false)
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne
	@JoinColumn(name = "ID_PERFIL", nullable = false)
	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}