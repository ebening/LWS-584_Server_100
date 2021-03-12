package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.FacturaGastoViajeDAO;
import com.lowes.entity.FacturaGastoViaje;
import com.lowes.service.FacturaGastoViajeService;

@Service
@Transactional
public class FacturaGastoViajeServiceImpl implements FacturaGastoViajeService {
	
	@Autowired
	private FacturaGastoViajeDAO facturaGastoViajeDAO;

	@Override
	public Integer createFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje) {
		return facturaGastoViajeDAO.createFacturaGastoViaje(facturaGastoViaje);
	}

	@Override
	public FacturaGastoViaje updateFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje) {
		return facturaGastoViajeDAO.updateFacturaGastoViaje(facturaGastoViaje);
	}

	@Override
	public void deleteFacturaGastoViaje(Integer idfacturaGastoViaje) {
		facturaGastoViajeDAO.deleteFacturaGastoViaje(idfacturaGastoViaje);
	}
	
	@Override
	public void deleteFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje) {
		facturaGastoViajeDAO.deleteFacturaGastoViaje(facturaGastoViaje);
	}

	@Override
	public FacturaGastoViaje getFacturaGastoViaje(Integer idFacturaGastoViaje) {
		return facturaGastoViajeDAO.getFacturaGastoViaje(idFacturaGastoViaje);
	}

	@Override
	public List<FacturaGastoViaje> getAllFacturaGastoViaje() {
		return facturaGastoViajeDAO.getAllFacturaGastoViaje();
	}

	@Override
	public FacturaGastoViaje getFacturaGastoViajeByIdFactura(Integer idFactura) {
		return facturaGastoViajeDAO.getFacturaGastoViajeByIdFactura(idFactura);
	}

	@Override
	public void deleteAllFacturaGastoViajeByIdFactura(Integer idFactura) {
		facturaGastoViajeDAO.deleteAllFacturaGastoViajeByIdFactura(idFactura);
	}
}