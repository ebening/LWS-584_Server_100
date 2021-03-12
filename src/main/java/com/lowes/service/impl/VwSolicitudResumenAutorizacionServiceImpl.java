
package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.VwSolicitudResumenAutorizacionDAO;
import com.lowes.entity.VwSolicitudResumenAutorizacion;
import com.lowes.service.VwSolicitudResumenAutorizacionService;

@Service
@Transactional
public class VwSolicitudResumenAutorizacionServiceImpl implements VwSolicitudResumenAutorizacionService {
	
	@Autowired
	private VwSolicitudResumenAutorizacionDAO vwSolicitudResumenAutorizacionDAO;
	
	@Override
	public List<VwSolicitudResumenAutorizacion> getAllVwSolicitudResumenAutorizacion() {
		return vwSolicitudResumenAutorizacionDAO.getAllVwSolicitudResumenAutorizacion();
	}

	@Override
	public VwSolicitudResumenAutorizacion getVwSolicitudResumenAutorizacion(Integer idVwSolicitudResumenAutorizacion) {
		return vwSolicitudResumenAutorizacionDAO.getVwSolicitudResumenAutorizacion(idVwSolicitudResumenAutorizacion);
	}

	@Override
	public List<VwSolicitudResumenAutorizacion> getAllVwSolicitudResumenAutorizacionByUsuarioEstatusSolicitud(Integer idUsuario, Integer idEstadoSolicitud, String fechaInicial, String fechaFinal) {
		return vwSolicitudResumenAutorizacionDAO.getAllVwSolicitudResumenAutorizacionByUsuarioEstatusSolicitud(idUsuario, idEstadoSolicitud, fechaInicial, fechaFinal);
	}

	@Override
	public Integer getCountSolicitudAutorizacionByUsuario(Integer idUsuario, Integer idEstadoSolicitud) {
		return vwSolicitudResumenAutorizacionDAO.getCountSolicitudAutorizacionByUsuario(idUsuario, idEstadoSolicitud);
	}

}
