package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TipoSolicitudCriterioDAO;
import com.lowes.entity.TipoSolicitudCriterio;
import com.lowes.util.HibernateUtil;

@Repository
public class TipoSolicitudCriterioDAOImpl implements TipoSolicitudCriterioDAO{
	
	public TipoSolicitudCriterioDAOImpl(){
		System.out.println("TipoSolicitudCriterioDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio) {
		return (Integer) hibernateUtil.create(tipoSolicitudCriterio);
	}

	@Override
	public TipoSolicitudCriterio updateTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio) {
		return hibernateUtil.update(tipoSolicitudCriterio);
	}

	@Override
	public void deleteTipoSolicitudCriterio(Integer idTipoSolicitudCriterio) {
		TipoSolicitudCriterio tipoSolicitudCriterio = getTipoSolicitudCriterio(idTipoSolicitudCriterio);
		hibernateUtil.delete(tipoSolicitudCriterio);
	}

	@Override
	public List<TipoSolicitudCriterio> getAllTipoSolicitudCriterio() {
		return hibernateUtil.fetchAll(TipoSolicitudCriterio.class);
	}

	@Override
	public TipoSolicitudCriterio getTipoSolicitudCriterio(Integer idTipoSolicitudCriterio) {
		return hibernateUtil.fetchById(idTipoSolicitudCriterio, TipoSolicitudCriterio.class);
	}

	@Override
	public List<TipoSolicitudCriterio> getTipoSolicitudCriteriosByTipoSolicitud(Integer idTipoSolicitud) {
		String queryString = "FROM " + TipoSolicitudCriterio.class.getName()
				+ " WHERE ID_TIPO_SOLICITUD = :idTipoSolicitud"
				+ " ORDER BY orden";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idTipoSolicitud", idTipoSolicitud.toString());
		
		List<TipoSolicitudCriterio> tipoSolicitudCriterios = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return tipoSolicitudCriterios;
	}

	@Override
	public TipoSolicitudCriterio getNextTipoSolicitudCriterio(Integer idTipoSolicitud, Integer orden) {
		String queryString ="FROM " + TipoSolicitudCriterio.class.getName()
				+ " WHERE ID_TIPO_SOLICITUD = :idTipoSolicitud"
				+ " AND ORDEN = :orden"
				+ " ORDER BY orden";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idTipoSolicitud", idTipoSolicitud.toString());
		parameters.put("orden", orden.toString());
		
		List<TipoSolicitudCriterio> tipoSolicitudCriterios = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (tipoSolicitudCriterios.size() > 0) {
			return tipoSolicitudCriterios.get(0);
		} else {
			return null;
		}
		
	}
	
}
