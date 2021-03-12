package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.EstadoSolicitudDAO;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.service.EstadoSolicitudService;


@Service
@Transactional
public class EstadoSolicitudServiceImpl implements EstadoSolicitudService {
	
	@Autowired
	private EstadoSolicitudDAO estadoSolicitudDAO;

	@Override
	public List<EstadoSolicitud> getAllEstadoSolicitud() {
		return estadoSolicitudDAO.getAllEstadoSolicitud();
	}
	
	@Override
	public List<EstadoSolicitud> getAllEstadoSolicitudOrder(String propiedad) {
		return estadoSolicitudDAO.getAllEstadoSolicitudOrder(propiedad);
	}
	
	@Override
	public EstadoSolicitud getEstadoSolicitud(Integer idEstadoSolicitud) {
		return estadoSolicitudDAO.getEstadoSolicitud(idEstadoSolicitud);
	}
	
	public List<EstadoSolicitud> findByEstadoSolicitud(String estadoSolicitud) {
		return estadoSolicitudDAO.findByEstadoSolicitud(estadoSolicitud);
	}
	
}