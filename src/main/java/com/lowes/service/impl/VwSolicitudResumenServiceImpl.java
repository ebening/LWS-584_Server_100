package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.VwSolicitudResumenDAO;
import com.lowes.entity.VwSolicitudResumen;
import com.lowes.service.VwSolicitudResumenService;

@Service
@Transactional
public class VwSolicitudResumenServiceImpl implements VwSolicitudResumenService {
	
	@Autowired
	private VwSolicitudResumenDAO vwSolicitudResumenDAO;
	
	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumen() {
		return vwSolicitudResumenDAO.getAllVwSolicitudResumen();
	}

	@Override
	public VwSolicitudResumen getVwSolicitudResumen(Integer idVwSolicitudResumen) {
		return vwSolicitudResumenDAO.getVwSolicitudResumen(idVwSolicitudResumen);
	}

	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumenByUsuarioEstatusSolicitud(Integer idUsuario, Integer idEstadoSolicitud, String fechaInicial, String fechaFinal) {
		return vwSolicitudResumenDAO.getAllVwSolicitudResumenByUsuarioEstatusSolicitud(idUsuario, idEstadoSolicitud, fechaInicial, fechaFinal);
	}

	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumenByUsuarioEstatusSolicitudCajaChica(Integer idUsuario,
			Integer idEstadoSolicitud, String fechaInicial, String fechaFinal) {
		return vwSolicitudResumenDAO.getAllVwSolicitudResumenByUsuarioEstatusSolicitudCajaChica(idUsuario, idEstadoSolicitud, fechaInicial, fechaFinal);

	}

	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumenPendientesComprobar(Integer idUsuario,
			Integer idEstadoSolicitud, Integer idTipoSolicitud, String fechaInicial, String fechaFinal) {
		return vwSolicitudResumenDAO.getAllVwSolicitudResumenPendientesComprobar(idUsuario, idEstadoSolicitud, idTipoSolicitud, fechaInicial, fechaFinal);
	}
	
	

}
