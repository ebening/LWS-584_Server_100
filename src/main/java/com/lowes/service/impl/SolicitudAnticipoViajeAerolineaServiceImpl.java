package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.SolicitudAnticipoViajeAerolineaDAO;
import com.lowes.entity.SolicitudAnticipoViajeAerolinea;
import com.lowes.service.SolicitudAnticipoViajeAerolineaService;

@Service
@Transactional
public class SolicitudAnticipoViajeAerolineaServiceImpl implements SolicitudAnticipoViajeAerolineaService{
	
	public SolicitudAnticipoViajeAerolineaServiceImpl(){
		System.out.println("SolicitudAnticipoViajeAerolineaServiceImpl()");
	}

	@Autowired
	private SolicitudAnticipoViajeAerolineaDAO solicitudAnticipoViajeAerolineaDAO;
	
	@Override
	public Integer createSolicitudAnticipoViajeAerolinea(SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea) {
		return solicitudAnticipoViajeAerolineaDAO.createSolicitudAnticipoViajeAerolinea(solicitudAnticipoViajeAerolinea);
	}

	@Override
	public SolicitudAnticipoViajeAerolinea updateSolicitudAnticipoViajeAerolinea(SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea) {
		return solicitudAnticipoViajeAerolineaDAO.updateSolicitudAnticipoViajeAerolinea(solicitudAnticipoViajeAerolinea);
	}

	@Override
	public void deleteSolicitudAnticipoViajeAerolinea(Integer idSolicitudAnticipoViajeAerolinea) {
		solicitudAnticipoViajeAerolineaDAO.deleteSolicitudAnticipoViajeAerolinea(idSolicitudAnticipoViajeAerolinea);
	}

	@Override
	public List<SolicitudAnticipoViajeAerolinea> getAllSolicitudAnticipoViajeAerolinea() {
		return solicitudAnticipoViajeAerolineaDAO.getAllSolicitudAnticipoViajeAerolinea();
	}

	@Override
	public SolicitudAnticipoViajeAerolinea getSolicitudAnticipoViajeAerolinea(Integer idSolicitudAnticipoViajeAerolinea) {
		return solicitudAnticipoViajeAerolineaDAO.getSolicitudAnticipoViajeAerolinea(idSolicitudAnticipoViajeAerolinea);
	}

	@Override
	public List<SolicitudAnticipoViajeAerolinea> getAllSolicitudAnticipoViajeAerolineaBySol(Integer idSolicitudAnticipoViaje) {
		return solicitudAnticipoViajeAerolineaDAO.getAllSolicitudAnticipoViajeAerolineaBySol(idSolicitudAnticipoViaje);

	}

}