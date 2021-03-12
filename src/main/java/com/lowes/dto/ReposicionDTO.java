package com.lowes.dto;

import java.util.List;

import com.lowes.entity.Compania;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.TipoProveedor;
import com.lowes.entity.ViajeConcepto;
import com.lowes.entity.ViajeDestino;
import com.lowes.entity.ViajeMotivo;

public class ReposicionDTO {

	private Integer idSolicitudSession;
	private Boolean creacion;
	private Boolean modificacion;
	
	public Boolean getModificacion() {
		return modificacion;
	}
	public void setModificacion(Boolean modificacion) {
		this.modificacion = modificacion;
	}
	public Boolean getCreacion() {
		return creacion;
	}
	public void setCreacion(Boolean creacion) {
		this.creacion = creacion;
	}
	public Integer getIdSolicitudSession() {
		return idSolicitudSession;
	}
	public void setIdSolicitudSession(Integer idSolicitudSession) {
		this.idSolicitudSession = idSolicitudSession;
	}
}
