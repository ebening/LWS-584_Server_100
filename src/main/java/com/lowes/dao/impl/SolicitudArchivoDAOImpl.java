package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.SolicitudArchivoDAO;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.util.HibernateUtil;


@Repository
public class SolicitudArchivoDAOImpl implements SolicitudArchivoDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Integer createSolicitudArchivo(SolicitudArchivo solicitudArchivo) {
		return  (Integer) hibernateUtil.create(solicitudArchivo);
	}

	@Override
	public SolicitudArchivo updateSolicitudArchivo(SolicitudArchivo solicitudArchivo) {
		return hibernateUtil.update(solicitudArchivo);
	}

	@Override
	public void deleteSolicitudArchivo(Integer idSolicitudArchivo) {
		SolicitudArchivo solicitudArchivo = new SolicitudArchivo();
		solicitudArchivo = hibernateUtil.fetchById(idSolicitudArchivo, SolicitudArchivo.class);
		hibernateUtil.delete(solicitudArchivo);
	}

	@Override
	public List<SolicitudArchivo> getAllSolicitudArchivo() {
		return hibernateUtil.fetchAll(SolicitudArchivo.class);
	}

	@Override
	public SolicitudArchivo getSolicitudArchivo(Integer idSolicitudArchivo) {
		return hibernateUtil.fetchById(idSolicitudArchivo, SolicitudArchivo.class);
	}

	@Override
	public List<SolicitudArchivo> getAllSolicitudArchivoBySolicitud(Integer idSolicitud) {
		String queryString ="FROM " + SolicitudArchivo.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

}