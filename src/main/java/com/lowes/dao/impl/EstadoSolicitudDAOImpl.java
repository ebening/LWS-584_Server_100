package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.EstadoSolicitudDAO;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.util.HibernateUtil;

@Repository
public class EstadoSolicitudDAOImpl implements EstadoSolicitudDAO {
	
	public EstadoSolicitudDAOImpl(){
		System.out.println("EstadoSolicitudDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public List<EstadoSolicitud> getAllEstadoSolicitud() {
		return hibernateUtil.fetchAll(EstadoSolicitud.class);
	}

	@Override
	public EstadoSolicitud getEstadoSolicitud(Integer idEstadoSolicitud) {
		return hibernateUtil.fetchById(idEstadoSolicitud, EstadoSolicitud.class);
	}

	@Override
	public List<EstadoSolicitud> getAllEstadoSolicitudOrder(String propiedad) {
		return hibernateUtil.fetchAllOrder(EstadoSolicitud.class, propiedad);
	}
	
	@Override
	public List<EstadoSolicitud> findByEstadoSolicitud(String estadoSolicitud) {
		String queryString = "FROM " + EstadoSolicitud.class.getName()
				+ " WHERE ESTADO_SOLICITUD = :estadoSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("estadoSolicitud", String.valueOf(estadoSolicitud));
		
		List<EstadoSolicitud> listEstadoSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		return listEstadoSolicitudes;
	}

}