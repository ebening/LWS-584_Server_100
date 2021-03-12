/**
 * 
 */
package com.lowes.dto;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

import com.ibm.mm.sdk.common.DKConstant;

/**
 * Esta clase abstrae los atributos de un Documento utilizado en Content
 * Manager.
 * 
 * @author Adanm.
 * @version 1.0, 23-02-2012.
 */
public class Documento {

	/**
	 * packagePid: Este es el Pid del Objeto DKWorkPackageICM a traves del cual
	 * el documento viaja dentro del flujo.
	 */
	private String nombre;
	private String descripcion;
	private short itemType = DKConstant.DK_CM_DOCUMENT;
	private String packagePid;
	private String pid;
	private String url = "";
	private int nivel = 1;
	private ArrayList<Atributo> atributos;
	private String estado;

	/**
	 * Constructor predefinido. Metodo que crea un nuevo Documento con valores
	 * predefinidos.
	 */
	public Documento() {
	}

	/**
	 * Constructor parametrizado. Metodo que crea un nuevo Documento asignando
	 * el pid Inresado.
	 * 
	 * @param pid
	 *            - Pid del documento.
	 */
	public Documento(String pid) {
		this.atributos = new ArrayList<Atributo>();
		this.pid = pid;
		this.nivel = 1;
	}

	/**
	 * Constructor parametrizado. Metodo que crea un nuevo Documento con la
	 * informacion ingresada.
	 * 
	 * @param pid
	 *            - Pid del Documento.
	 * @param url
	 *            - Url del documento.
	 * @param atributos
	 *            - Atributos del documento.
	 */
	public Documento(String pid, String url, ArrayList<Atributo> atributos) {
		this.pid = pid;
		this.url = url;
		this.atributos = atributos;
		this.nivel = 1;
	}

	/**
	 * @return El nombre del documento como se conoce dentro de ContentManager.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            - Nombre para asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return La descripcion del documento.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            - Descripcion para asignar.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * El itemType espesifica el tipo de objeto que representa dentro de
	 * ContentManager. Este puede ser de dos tipos: - Documento =
	 * DKConstant.DK_CM_DOCUMENT [1]. - Folder = DKConstant.DK_CM_FOLDER [2].
	 * 
	 * @return El itemType del documento.
	 */
	public short getItemType() {
		return itemType;
	}

	/**
	 * El itemType espesifica el tipo de objeto que representa dentro de
	 * ContentManager. Este puede ser de dos tipos: - Documento =
	 * DKConstant.DK_CM_DOCUMENT [1]. - Folder = DKConstant.DK_CM_FOLDER [2].
	 * 
	 * @param itemType
	 *            - El itemType para asignar.
	 */
	public void setItemType(short itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return El Pid del Pquete en donde viaja el documento.
	 */
	public String getPackagePid() {
		return packagePid;
	}

	/**
	 * @param packagePid
	 *            - Pid del pauqte para asignar.
	 */
	public void setPackagePid(String packagePid) {
		this.packagePid = packagePid;
	}

	/**
	 * @return El pid del documento.
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            - El pid para asignar.
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return La url del documento.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            - La url para asignar.
	 */
	public void setUrl(String url) {

		if (url != null && !url.isEmpty()) {
			this.url = url;
			this.itemType = DKConstant.DK_CM_DOCUMENT;
		}
	}

	/**
	 * @return El nivel del documento.
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * @param nivel
	 *            - Nivel para asignar.
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return Estado del documento en el flujo.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            - Estado del documento para asignar.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return Los atributos del documento.
	 */
	public ArrayList<Atributo> getAtributos() {
		return atributos;
	}

	/**
	 * @param atributos
	 *            - Atributos pàra asignar.
	 */
	public void setAtributos(ArrayList<Atributo> atributos) {
		this.atributos = atributos;
	}

	/**
	 * Este metodo busca dentro de su lista de atributos aquel que coinsida con
	 * el nombre ingresado y regresa su valor.
	 * 
	 * @param attrName
	 *            - Nombre del atributo buscado.
	 * @return Valor del atrobuto buscado o null si no existe un atributo con
	 *         ese nombre.
	 */
	public String getValorAtributo(String attrName) {
		Atributo atributo = this.getAtributo(attrName);

		if (atributo != null) {
			return atributo.getValor();
		}

		return null;
	}

	/**
	 * Este metodo busca dentro de su lista de atributos aquei que coinsida con
	 * el nombre ingresado y devuelve el objeto del Atributo.
	 * 
	 * @param attrName
	 *            - Nombre del atributo.
	 * @return Objeto Attr o null si no existe un atributo con ese nombre.
	 */
	public Atributo getAtributo(String attrName) {
		for (Atributo attr : atributos) {
			if (attr.getName().equals(attrName)) {
				return attr;
			}
		}
		return null;
	}

	/**
	 * Este metodo agrega un nuevo atributo a la lista de atributos del Item
	 * Type si este no existe previamente.
	 * 
	 * @param name
	 *            - Nombre del Atributo.
	 * @param value
	 *            - Valor del atributo.
	 * @return false - Si el atributo ya existe.
	 */
	public boolean addAtributo(String name, String value) {
		if (this.atributos == null) {
			this.atributos = new ArrayList<Atributo>();
		}

		for (Atributo attr : atributos) {
			if (attr.getName().equals(name)) {
				return false;
			}
		}

		Atributo newAttr = new Atributo(name, value);
		this.atributos.add(newAttr);
		return true;
	}

	/**
	 * Este metodo agrega el atributo ingresao a la lista de atributos del Item
	 * Type si este no existe previamente.
	 * 
	 * @param atributo
	 *            - Objeto Atributo.
	 * @return false - Si el atributo ya existe.
	 */
	public boolean addAtributo(Atributo atributo) {
		if (this.atributos == null) {
			this.atributos = new ArrayList<Atributo>();
		}

		for (Atributo attr : atributos) {
			if (attr.getName().equals(atributo.getName())) {
				return false;
			}
		}

		this.atributos.add(atributo);
		return true;
	}

	/**
	 * 
	 */
	public Documento clone() {
		Documento docClone = new Documento();
		docClone.setNombre(nombre);
		docClone.setDescripcion(descripcion);
		docClone.setItemType(itemType);
		docClone.setEstado(estado);
		docClone.setNivel(nivel);
		docClone.setUrl(url);
		docClone.setPid(packagePid);
		docClone.setPackagePid(packagePid);

		for (Atributo attr : atributos) {
			docClone.addAtributo(attr.clone());
		}

		return docClone;
	}

	public String toString() {
		StringBuilder docString = new StringBuilder();
		docString.append("\nDocumento").append("\nPid").append(pid);

		if (atributos != null && !atributos.isEmpty()) {
			docString.append("\nAtributos");
			for (Atributo atributo : atributos) {
				docString.append("\n").append(atributo.toString());
			}
		}

		return docString.toString();
	}

}