package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.SolicitudAutorizacionDAO;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.service.SolicitudAutorizacionService;

@Service
@Transactional
public class SolicitudAutorizacionServiceImpl implements SolicitudAutorizacionService {

	@Autowired
	private SolicitudAutorizacionDAO solicitudAutorizacionDAO;
		
	@Override
	public Integer createSolicitudAutorizacion(SolicitudAutorizacion solicitudAutorizacion) {
		return solicitudAutorizacionDAO.createSolicitudAutorizacion(solicitudAutorizacion);
	}

	@Override
	public SolicitudAutorizacion updateSolicitudAutorizacion(SolicitudAutorizacion solicitudAutorizacion) {
		return solicitudAutorizacionDAO.updateSolicitudAutorizacion(solicitudAutorizacion);
	}

	@Override
	public void deleteSolicitudAutorizacion(Integer idSolicitudAutorizacion) {
		solicitudAutorizacionDAO.deleteSolicitudAutorizacion(idSolicitudAutorizacion);
	}

	@Override
	public List<SolicitudAutorizacion> getAllSolicitudAutorizacion() {
		return solicitudAutorizacionDAO.getAllSolicitudAutorizacion();
	}

	@Override
	public SolicitudAutorizacion getSolicitudAutorizacion(Integer idSolicitudAutorizacion) {
		return solicitudAutorizacionDAO.getSolicitudAutorizacion(idSolicitudAutorizacion);
	}

	@Override
	public SolicitudAutorizacion getLastSolicitudAutorizacion(Integer idSolicitud) {
		return solicitudAutorizacionDAO.getLastSolicitudAutorizacion(idSolicitud);
	}

	@Override
	public SolicitudAutorizacion getCurrentSolicitudAutorizacion(Integer idSolicitud) {
		return solicitudAutorizacionDAO.getCurrentSolicitudAutorizacion(idSolicitud);
	}

	@Override
	public SolicitudAutorizacion getSolicitudAutorizacionBySolicitudUsuario(Integer idSolicitud, Integer idUsuario) {
		return solicitudAutorizacionDAO.getSolicitudAutorizacionBySolicitudUsuario(idSolicitud, idUsuario);
	}

	@Override
	public Integer getCountSolicitudAutorizacionByUsuario(Integer idUsuario, Integer idEstadoSolicitud) {
		return solicitudAutorizacionDAO.getCountSolicitudAutorizacionByUsuario(idUsuario, idEstadoSolicitud);
	}

	@Override
	public List<SolicitudAutorizacion> getAllSolicitudAutorizacionBySolicitud(Integer idSolicitud) {
		return solicitudAutorizacionDAO.getAllSolicitudAutorizacionBySolicitud(idSolicitud);
	}
	
	@Override
	public List<SolicitudAutorizacion> getAllSolicitudAutorizacionActivaBySolicitud(Integer idSolicitud) {
		return solicitudAutorizacionDAO.getAllSolicitudAutorizacionActivaBySolicitud(idSolicitud);
	}

	@Override
	public Integer getCountAutorizadoresCriterio(Integer idSolicitud, Integer idTipoCriterio) {
		return solicitudAutorizacionDAO.getCountAutorizadoresCriterio(idSolicitud, idTipoCriterio);
	}

	@Override
	public Integer getCountAutorizadoresCriterioVendorRisk(Integer idSolicitud, Integer idTipoCriterio) {
		return solicitudAutorizacionDAO.getCountAutorizadoresCriterioVendorRisk(idSolicitud, idTipoCriterio);
	}

	@Override
	public List<SolicitudAutorizacion> getAllAutorizadoresByCriterio(Integer idSolicitud, Integer idTipoCriterio) {
		return solicitudAutorizacionDAO.getAllAutorizadoresByCriterio(idSolicitud, idTipoCriterio);
	}

	@Override
	public Integer getCountAllByUsuarioAutorizo(Integer idSolicitud, Integer idUsuario) {
		return solicitudAutorizacionDAO.getCountAllByUsuarioAutorizo(idSolicitud, idUsuario);
	}

	@Override
	public Integer getCountAllByUsuarioAutorizoCriterio(Integer idSolicitud, Integer idUsuario, Integer criterio) {
		return solicitudAutorizacionDAO.getCountAllByUsuarioAutorizoCriterio(idSolicitud, idUsuario, criterio);
	}

	@Override
	public Integer getCountAllByUsuarioAutorizoAutomatico(Integer idSolicitud, Integer idUsuario, Integer criterio) {
		return solicitudAutorizacionDAO.getCountAllByUsuarioAutorizoAutomatico(idSolicitud, idUsuario, criterio);
	}
	
	
	
	
	
}
