package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.SolicitudAnticipoViajeAerolineaDAO;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAnticipoViaje;
import com.lowes.entity.SolicitudAnticipoViajeAerolinea;
import com.lowes.service.SolicitudAnticipoViajeAerolineaService;
import com.lowes.util.HibernateUtil;

@Repository
public class SolicitudAnticipoViajeAerolineaDAOImpl implements SolicitudAnticipoViajeAerolineaDAO{

	@Autowired
	private HibernateUtil hibernateUtil;

	public SolicitudAnticipoViajeAerolineaDAOImpl(){
		System.out.println("SolicitudAnticipoViajeAerolineaDAOImpl()");
	}
	
	@Override
	public Integer createSolicitudAnticipoViajeAerolinea(
			SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea) {
		return (Integer) hibernateUtil.create(solicitudAnticipoViajeAerolinea);
	}

	@Override
	public SolicitudAnticipoViajeAerolinea updateSolicitudAnticipoViajeAerolinea(
			SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea) {
		return hibernateUtil.update(solicitudAnticipoViajeAerolinea);
	}

	@Override
	public void deleteSolicitudAnticipoViajeAerolinea(Integer id) {
		hibernateUtil.delete(hibernateUtil.fetchById(id, SolicitudAnticipoViajeAerolinea.class));
	}

	@Override
	public List<SolicitudAnticipoViajeAerolinea> getAllSolicitudAnticipoViajeAerolinea() {
		return hibernateUtil.fetchAll(SolicitudAnticipoViajeAerolinea.class);
	}

	@Override
	public SolicitudAnticipoViajeAerolinea getSolicitudAnticipoViajeAerolinea(Integer id) {
		return hibernateUtil.fetchById(id,SolicitudAnticipoViajeAerolinea.class);
	}

	@Override
	public List<SolicitudAnticipoViajeAerolinea> getAllSolicitudAnticipoViajeAerolineaBySol(Integer idSolicitudAnticipoViaje) {
		String queryString = "FROM " + SolicitudAnticipoViajeAerolinea.class.getName()
				+ " WHERE ID_SOLICITUD_ANTICIPO_VIAJE = :idSolicitudAnticipoViaje";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitudAnticipoViaje", String.valueOf(idSolicitudAnticipoViaje));
		List<SolicitudAnticipoViajeAerolinea> aerlonieas = hibernateUtil.fetchAllHql(queryString, parameters);
		return aerlonieas;
	}

}