package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.FacturaKilometrajeDAO;
import com.lowes.entity.FacturaKilometraje;
import com.lowes.service.FacturaKilometrajeService;

@Service
@Transactional
public class FacturaKilometrajeServiceImpl implements FacturaKilometrajeService{

	@Autowired
	private FacturaKilometrajeDAO facturaKilometrajeDAO;
	
	@Override
	public Integer createFacturaKilometraje(FacturaKilometraje facturaKilometraje) {
		return facturaKilometrajeDAO.createFacturaKilometraje(facturaKilometraje);
	}

	@Override
	public FacturaKilometraje updateFacturaKilometraje(FacturaKilometraje facturaKilometraje) {
		return facturaKilometrajeDAO.updateFacturaKilometraje(facturaKilometraje);
	}

	@Override
	public void deleteFacturaKilometraje(Integer id) {
		facturaKilometrajeDAO.deleteFacturaKilometraje(id);
	}
	
	@Override
	public void deleteFacturaKilometraje(FacturaKilometraje facturaKilometraje) {
		facturaKilometrajeDAO.deleteFacturaKilometraje(facturaKilometraje);
	}

	@Override
	public List<FacturaKilometraje> getAllFacturaKilometraje() {
		return facturaKilometrajeDAO.getAllFacturaKilometraje();
	}

	@Override
	public FacturaKilometraje getFacturaKilometraje(Integer id) {
		return facturaKilometrajeDAO.getFacturaKilometraje(id);
	}

	@Override
	public List<FacturaKilometraje> getAllFacturaKilometrajeByKilometrajeRecorrido(Integer idKilometrajeRecorrido) {
		return facturaKilometrajeDAO.getAllFacturaKilometrajeByKilometrajeRecorrido(idKilometrajeRecorrido);
	}

	@Override
	public List<FacturaKilometraje> getAllFacturaKilometrajeByIdSolicitud(Integer idSolicitud) {
		return facturaKilometrajeDAO.getAllFacturaKilometrajeByIdSolicitud(idSolicitud);
	}

	@Override
	public void deleteFacturaKilometrajeByIdSolicitud(Integer idSolicitud) {
		facturaKilometrajeDAO.deleteFacturaKilometrajeByIdSolicitud(idSolicitud);
	}
	
	

}
