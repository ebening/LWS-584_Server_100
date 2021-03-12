package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.SolicitudAnticipoViajeDAO;
import com.lowes.entity.SolicitudAnticipoViaje;
import com.lowes.service.SolicitudAnticipoViajeService;

@Service
@Transactional
public class SolicitudAnticipoViajeServiceImpl implements SolicitudAnticipoViajeService{
	
	public SolicitudAnticipoViajeServiceImpl(){
		System.out.println("SolicitudAnticipoViajeServiceImpl()");
	}

	@Autowired
	private SolicitudAnticipoViajeDAO solicitudAnticipoViajeDAO;
	
	@Override
	public Integer createSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje) {
		return solicitudAnticipoViajeDAO.createSolicitudAnticipoViaje(solicitudAnticipoViaje);
	}

	@Override
	public SolicitudAnticipoViaje updateSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje) {
		return solicitudAnticipoViajeDAO.updateSolicitudAnticipoViaje(solicitudAnticipoViaje);
	}

	@Override
	public void deleteSolicitudAnticipoViaje(Integer idSolicitudAnticipoViaje) {
		solicitudAnticipoViajeDAO.deleteSolicitudAnticipoViaje(idSolicitudAnticipoViaje);
	}

	@Override
	public List<SolicitudAnticipoViaje> getAllSolicitudAnticipoViaje() {
		return solicitudAnticipoViajeDAO.getAllSolicitudAnticipoViaje();
	}

	@Override
	public SolicitudAnticipoViaje getSolicitudAnticipoViaje(Integer idSolicitudAnticipoViaje) {
		return solicitudAnticipoViajeDAO.getSolicitudAnticipoViaje(idSolicitudAnticipoViaje);
	}

	@Override
	public SolicitudAnticipoViaje getSolicitudAnticipoViajeBySolicitud(Integer idSolicitud) {
		return solicitudAnticipoViajeDAO.getSolicitudAnticipoViajeBySolicitud(idSolicitud);
	}
	@Override
	public List<SolicitudAnticipoViaje> getSolicitudAnticipoViajesBySolicitud(Integer idSolicitud) {
		return solicitudAnticipoViajeDAO.getSolicitudAnticipoViajesBySolicitud(idSolicitud);
	}

}