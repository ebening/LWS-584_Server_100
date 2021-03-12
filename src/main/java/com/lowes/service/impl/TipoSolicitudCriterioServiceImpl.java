package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TipoSolicitudCriterioDAO;
import com.lowes.entity.TipoSolicitudCriterio;
import com.lowes.service.TipoSolicitudCriterioService;

@Service
@Transactional
public class TipoSolicitudCriterioServiceImpl implements TipoSolicitudCriterioService{
	
	public TipoSolicitudCriterioServiceImpl(){
		System.out.println("TipoSolicitudCriterioServiceImpl()");
	}
	
	@Autowired
	private TipoSolicitudCriterioDAO tipoSolicitudCriterioDAO;

	@Override
	public Integer createTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio) {
		return tipoSolicitudCriterioDAO.createTipoSolicitudCriterio(tipoSolicitudCriterio);
	}

	@Override
	public TipoSolicitudCriterio updateTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio) {
		return tipoSolicitudCriterioDAO.updateTipoSolicitudCriterio(tipoSolicitudCriterio);
	}

	@Override
	public void deleteTipoSolicitudCriterio(Integer idTipoSolicitudCriterio) {
		tipoSolicitudCriterioDAO.deleteTipoSolicitudCriterio(idTipoSolicitudCriterio);
	}

	@Override
	public List<TipoSolicitudCriterio> getAllTipoSolicitudCriterio() {
		return tipoSolicitudCriterioDAO.getAllTipoSolicitudCriterio();
	}

	@Override
	public TipoSolicitudCriterio getTipoSolicitudCriterio(Integer idTipoSolicitudCriterio) {
		return tipoSolicitudCriterioDAO.getTipoSolicitudCriterio(idTipoSolicitudCriterio);
	}

	@Override
	public List<TipoSolicitudCriterio> getTipoSolicitudCriteriosByTipoSolicitud(Integer idTipoSolicitud) {
		return tipoSolicitudCriterioDAO.getTipoSolicitudCriteriosByTipoSolicitud(idTipoSolicitud);
	}

	@Override
	public TipoSolicitudCriterio getNextTipoSolicitudCriterio(Integer idTipoSolicitud, Integer orden) {
		return tipoSolicitudCriterioDAO.getNextTipoSolicitudCriterio(idTipoSolicitud, orden);
	}

}
