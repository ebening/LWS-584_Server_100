package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.FacturaDAO;
import com.lowes.entity.Factura;
import com.lowes.service.FacturaService;

@Service
@Transactional
public class FacturaServiceImpl implements FacturaService {
	
	@Autowired
	private FacturaDAO facturaDAO;

	@Override
	public Integer createFactura(Factura factura) {
		return facturaDAO.createFactura(factura);
	}

	@Override
	public Factura updateFactura(Factura factura) {
		return facturaDAO.updateFactura(factura);
	}

	@Override
	public void deleteFactura(Integer idfactura) {
		facturaDAO.deleteFactura(idfactura);
	}
	
	@Override
	public void deleteFactura(Factura factura) {
		facturaDAO.deleteFactura(factura);
	}

	@Override
	public Factura getFactura(Integer idFactura) {
		return facturaDAO.getFactura(idFactura);
	}

	@Override
	public List<Factura> getAllFactura() {
		return facturaDAO.getAllFactura();
	}

	@Override
	public List<Factura> getAllFacturaBySolicitud(Integer idSolicitud) {
		return facturaDAO.getAllFacturaBySolicitud(idSolicitud);
	}

	@Override
	public List<Factura> getFacturaByUUID(String UUID) {
		return facturaDAO.getFacturaByUUID(UUID);
	}

	@Override
	public List<Factura> getFacturaByFolio(String folio) {
		return facturaDAO.getFacturaByFolio(folio);
	}
	
}