package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.BusquedaFacturaDAO;
import com.lowes.dto.BusquedaFacturaDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.Factura;
import com.lowes.entity.Locacion;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Usuario;
import com.lowes.service.BusquedaFacturaService;

@Service
@Transactional
public class BusquedaFacturaServiceImpl implements BusquedaFacturaService {
	
	@Autowired
	private BusquedaFacturaDAO busquedaFacturaDAO;

	@Override
	public List<Factura> getFacturasBusqueda(BusquedaFacturaDTO filtros, Usuario usuario, Integer puestoAP, Integer puestoConfirmacionAP) {
		return busquedaFacturaDAO.getFacturasBusqueda(filtros, usuario, puestoAP, puestoConfirmacionAP);
	}

	@Override
	public List<Proveedor> getProveedores() {
		return busquedaFacturaDAO.getProveedores();
	}

	@Override
	public List<Compania> getCompanias() {
		return busquedaFacturaDAO.getCompanias();
	}

	@Override
	public List<Locacion> getLocaciones() {
		return busquedaFacturaDAO.getLocaciones();
	}

	@Override
	public List<Proveedor> getProveedoresTodos() {
		return busquedaFacturaDAO.getProveedoresTodos();
	}
	
	
	

}
