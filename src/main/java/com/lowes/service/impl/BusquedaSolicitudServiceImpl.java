package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.BusquedaSolicitudDAO;
import com.lowes.dto.BusquedaSolicitudDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.Locacion;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;
import com.lowes.service.BusquedaSolicitudService;

@Service
@Transactional
public class BusquedaSolicitudServiceImpl implements BusquedaSolicitudService {

	@Autowired
	private BusquedaSolicitudDAO busquedaSolicitudDAO;

	@Override
	public List<Solicitud> getSolicitudesBusqueda(BusquedaSolicitudDTO filtros, Usuario usuario, Integer puestoAP, Integer puestoConfirmacionAP) {
		return busquedaSolicitudDAO.getSolicitudesBusqueda(filtros, usuario, puestoAP, puestoConfirmacionAP);
	}

	@Override
	public List<Proveedor> getProveedores() {
		return busquedaSolicitudDAO.getProveedores();
	}

	@Override
	public List<Compania> getCompanias() {
		return busquedaSolicitudDAO.getCompanias();
	}

	@Override
	public List<Locacion> getLocaciones() {
		return busquedaSolicitudDAO.getLocaciones();
	}

	@Override
	public List<Proveedor> getProveedoresTodos() {
		return busquedaSolicitudDAO.getProveedoresTodas();
	}
	
	

}
