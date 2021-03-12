package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.SolicitudAnticipoViajeConceptosDAO;
import com.lowes.entity.SolicitudAnticipoViajeAerolinea;
import com.lowes.entity.SolicitudAnticipoViajeConceptos;
import com.lowes.util.HibernateUtil;

@Repository
public class SolicitudAnticipoViajeConceptosDAOImpl implements SolicitudAnticipoViajeConceptosDAO{
	
	public SolicitudAnticipoViajeConceptosDAOImpl(){
		System.out.println("SolicitudAnticipoViajeConceptosDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createSolicitudAnticipoViajeConceptos(SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConceptos) {
		return (Integer) hibernateUtil.create(solicitudAnticipoViajeConceptos);
	}

	@Override
	public SolicitudAnticipoViajeConceptos updateSolicitudAnticipoViajeConceptos(SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConceptos) {
		return hibernateUtil.update(solicitudAnticipoViajeConceptos);
	}

	@Override
	public void deleteSolicitudAnticipoViajeConceptos(Integer idSolicitudAnticipoViajeConceptos) {
		SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConceptos = getSolicitudAnticipoViajeConceptos(idSolicitudAnticipoViajeConceptos); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(solicitudAnticipoViajeConceptos);
	}

	@Override
	public List<SolicitudAnticipoViajeConceptos> getAllSolicitudAnticipoViajeConceptos() {
		return hibernateUtil.fetchAll(SolicitudAnticipoViajeConceptos.class);
	}

	@Override
	public SolicitudAnticipoViajeConceptos getSolicitudAnticipoViajeConceptos(Integer idSolicitudAnticipoViajeConceptos) {
		return hibernateUtil.fetchById(idSolicitudAnticipoViajeConceptos, SolicitudAnticipoViajeConceptos.class);
	}

	@Override
	public List<SolicitudAnticipoViajeConceptos> getAllSolicitudAnticipoViajeConceptoBySol(Integer idSolicitudAnticipoViaje) {
		String queryString = "FROM " + SolicitudAnticipoViajeConceptos.class.getName()
				+ " WHERE ID_SOLICITUD_ANTICIPO_VIAJE = :idSolicitudAnticipoViaje";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitudAnticipoViaje", String.valueOf(idSolicitudAnticipoViaje));
		List<SolicitudAnticipoViajeConceptos> conceptos = hibernateUtil.fetchAllHql(queryString, parameters);
		return conceptos;
	}

}