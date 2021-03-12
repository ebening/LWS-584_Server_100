package com.lowes.dto;

import com.lowes.entity.Aid;
import com.lowes.entity.CategoriaMayor;
import com.lowes.entity.CategoriaMenor;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.Locacion;

public class FacturaDesgloseDTO {
	
	private Locacion locacion;
	private CuentaContable cuentaContable;
	private String concepto;
	private Aid aid;
	private CategoriaMayor categoriaMayor;
	private CategoriaMenor categoriaMenor;
	private String strSubTotal;
	private Integer indexInput;
	private Integer indexFactura;
	private Integer usuarioSolicitante;
	private Integer tipoSolicitud;
	
	
	public FacturaDesgloseDTO(){
		
	}
	
	public FacturaDesgloseDTO(String strSubTotal,  Locacion locacion, CuentaContable cuentaContable , String concepto, Aid aid, CategoriaMayor categoriaMayor, CategoriaMenor categoriaMenor ){
		this.strSubTotal = strSubTotal;
		this.locacion = locacion;
		this.cuentaContable = cuentaContable;
		this.concepto = concepto;
		this.aid = aid;
		this.categoriaMayor = categoriaMayor;
		this.categoriaMenor = categoriaMenor;
	}
	
	
	public String getStrSubTotal() {
		return strSubTotal;
	}

	public void setStrSubTotal(String strSubTotal) {
		this.strSubTotal = strSubTotal;
	}

	public Locacion getLocacion() {
		return locacion;
	}
	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}
	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Aid getAid() {
		return aid;
	}
	public void setAid(Aid aid) {
		this.aid = aid;
	}
	public CategoriaMayor getCategoriaMayor() {
		return categoriaMayor;
	}
	public void setCategoriaMayor(CategoriaMayor categoriaMayor) {
		this.categoriaMayor = categoriaMayor;
	}
	public CategoriaMenor getCategoriaMenor() {
		return categoriaMenor;
	}
	public void setCategoriaMenor(CategoriaMenor categoriaMenor) {
		this.categoriaMenor = categoriaMenor;
	}

	public Integer getIndexInput() {
		return indexInput;
	}
	public void setIndexInput(Integer indexInput) {
		this.indexInput = indexInput;
	}

	public Integer getIndexFactura() {
		return indexFactura;
	}

	public void setIndexFactura(Integer indexFactura) {
		this.indexFactura = indexFactura;
	}

	public Integer getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Integer usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	
	
	
	
	
}
