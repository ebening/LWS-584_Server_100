package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.FacturaArchivoDAO;
import com.lowes.entity.FacturaArchivo;
import com.lowes.service.FacturaArchivoService;

@Service
@Transactional
public class FacturaArchivoServiceImpl implements FacturaArchivoService{
	
	@Autowired
	private FacturaArchivoDAO facturaArchivoDAO;

	@Override
	public Integer createFacturaArchivo(FacturaArchivo facturaArchivo) {
		return facturaArchivoDAO.createFacturaArchivo(facturaArchivo);
	}

	@Override
	public FacturaArchivo updateFacturaArchivo(FacturaArchivo facturaArchivo) {
		return facturaArchivoDAO.updateFacturaArchivo(facturaArchivo);
	}

	@Override
	public void deleteFacturaArchivo(Integer idFacturaArchivo) {
		facturaArchivoDAO.deleteFacturaArchivo(idFacturaArchivo);
	}
	
	@Override
	public void deleteFacturaArchivo(FacturaArchivo facturaArchivo) {
		facturaArchivoDAO.deleteFacturaArchivo(facturaArchivo);
	}

	@Override
	public List<FacturaArchivo> getAllFacturaArchivo() {
		return facturaArchivoDAO.getAllFacturaArchivo();
	}

	@Override
	public FacturaArchivo getFacturaArchivo(Integer idFacturaArchivo) {
		return facturaArchivoDAO.getFacturaArchivo(idFacturaArchivo);
	}

	@Override
	public List<FacturaArchivo> getAllFacturaArchivoByFacturaTipoDocumento(Integer idFactura, Integer idTipoDocumento) {
		return facturaArchivoDAO.getAllFacturaArchivoByFacturaTipoDocumento(idFactura, idTipoDocumento);
	}

	@Override
	public List<FacturaArchivo> getAllFacturaArchivoByIdFactura(Integer idFactura) {
		return facturaArchivoDAO.getAllFacturaArchivoByIdFactura(idFactura);
	}

	@Override
	public void deleteAllFacturaArchivoByIdFactura(Integer idFactura) {
		facturaArchivoDAO.deleteAllFacturaArchivoByIdFactura(idFactura);
	}
}