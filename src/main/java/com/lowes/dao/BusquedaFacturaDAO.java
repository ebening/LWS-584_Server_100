package com.lowes.dao;

import java.util.List;

import com.lowes.dto.BusquedaFacturaDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.Factura;
import com.lowes.entity.Locacion;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Usuario;

public interface BusquedaFacturaDAO {
	
	public List<Factura> getFacturasBusqueda(BusquedaFacturaDTO filtros, Usuario usuario, Integer puestoAP, Integer puestoConfirmacionAP);
	public List<Proveedor> getProveedores();
	public List<Compania> getCompanias();
	public List<Locacion> getLocaciones();
	public List<Proveedor> getProveedoresTodos();
}
