package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.SolicitudAnticipoViajeDAO;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAnticipoViaje;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;

@Repository
public class SolicitudAnticipoViajeDAOImpl implements SolicitudAnticipoViajeDAO{
	
	public SolicitudAnticipoViajeDAOImpl(){
		System.out.println("SolicitudAnticipoViajeDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje) {
		return (Integer) hibernateUtil.create(solicitudAnticipoViaje);
	}

	@Override
	public SolicitudAnticipoViaje updateSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje) {
		return hibernateUtil.update(solicitudAnticipoViaje);
	}

	@Override
	public void deleteSolicitudAnticipoViaje(Integer idSolicitudAnticipoViaje) {
		SolicitudAnticipoViaje solicitudAnticipoViaje = getSolicitudAnticipoViaje(idSolicitudAnticipoViaje); // Obtener compa�ia por id y enviar el objeto para eliminar
		hibernateUtil.delete(solicitudAnticipoViaje);
	}

	@Override
	public List<SolicitudAnticipoViaje> getAllSolicitudAnticipoViaje() {
		return hibernateUtil.fetchAll(SolicitudAnticipoViaje.class);
	}

	@Override
	public SolicitudAnticipoViaje getSolicitudAnticipoViaje(Integer idSolicitudAnticipoViaje) {
		return hibernateUtil.fetchById(idSolicitudAnticipoViaje, SolicitudAnticipoViaje.class);
	}

	@Override
	public SolicitudAnticipoViaje getSolicitudAnticipoViajeBySolicitud(Integer idSolicitud) {
		String queryString = "FROM " + SolicitudAnticipoViaje.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", String.valueOf(idSolicitud));
		List<SolicitudAnticipoViaje> anticipo = hibernateUtil.fetchAllHql(queryString, parameters);
		return (anticipo.isEmpty() ? null : anticipo.get(0));
	}
	
	@Override
	public List<SolicitudAnticipoViaje> getSolicitudAnticipoViajesBySolicitud(Integer idSolicitud) {
		String queryString = "FROM " + SolicitudAnticipoViaje.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", String.valueOf(idSolicitud));
		List<SolicitudAnticipoViaje> anticipos = hibernateUtil.fetchAllHql(queryString, parameters);
		return anticipos;
	}

}