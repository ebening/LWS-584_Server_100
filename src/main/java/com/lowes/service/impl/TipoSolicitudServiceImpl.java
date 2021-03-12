package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TipoSolicitudDAO;
import com.lowes.entity.TipoSolicitud;
import com.lowes.service.TipoSolicitudService;

@Service
@Transactional
public class TipoSolicitudServiceImpl implements TipoSolicitudService{
	
	public TipoSolicitudServiceImpl(){
		System.out.println("TipoSolicitudServiceImpl()");
	}
	
	@Autowired
	private TipoSolicitudDAO tipoSolicitudDAO;

	@Override
	public Integer createTipoSolicitud(TipoSolicitud tipoSolicitud) {
		return tipoSolicitudDAO.createTipoSolicitud(tipoSolicitud);
	}

	@Override
	public TipoSolicitud updateTipoSolicitud(TipoSolicitud tipoSolicitud) {
		return tipoSolicitudDAO.updateTipoSolicitud(tipoSolicitud);
	}

	@Override
	public void deleteTipoSolicitud(Integer idTipoSolicitud) {
		tipoSolicitudDAO.deleteTipoSolicitud(idTipoSolicitud);
	}

	@Override
	public List<TipoSolicitud> getAllTipoSolicitud() {
		return tipoSolicitudDAO.getAllTipoSolicitud();
	}
	
	@Override
	public List<TipoSolicitud> getAllTipoSolicitudOrder(String propiedad) {
		return tipoSolicitudDAO.getAllTipoSolicitudOrder(propiedad);
	}

	@Override
	public TipoSolicitud getTipoSolicitud(Integer idTipoSolicitud) {
		return tipoSolicitudDAO.getTipoSolicitud(idTipoSolicitud);
	}

}