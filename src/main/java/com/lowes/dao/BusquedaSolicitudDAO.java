package com.lowes.dao;

import java.util.List;

import com.lowes.dto.BusquedaSolicitudDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.Locacion;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;

public interface BusquedaSolicitudDAO {
	
	public List<Solicitud> getSolicitudesBusqueda(BusquedaSolicitudDTO filtros, Usuario usuario, Integer puestoAP, Integer puestoConfirmacionAP);
	public List<Proveedor> getProveedores();
	public List<Compania> getCompanias();
	public List<Locacion> getLocaciones();
	public List<Proveedor> getProveedoresTodas();


}