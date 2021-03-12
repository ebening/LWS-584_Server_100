package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.SolicitudFechaPagoEbsDAO;
import com.lowes.entity.SolicitudFechaPagoEbs;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;

@Repository
public class SolicitudFechaPagoEbsDAOImpl implements SolicitudFechaPagoEbsDAO{
	
	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Integer createSolicitudFechaPagoEbs(SolicitudFechaPagoEbs solicitudFechaPagoEbs) {
		return (Integer) hibernateUtil.create(solicitudFechaPagoEbs);
	}

	@Override
	public SolicitudFechaPagoEbs updateSolicitudFechaPagoEbs(SolicitudFechaPagoEbs solicitudFechaPagoEbs) {
		return hibernateUtil.update(solicitudFechaPagoEbs);
	}

	@Override
	public void deleteSolicitudFechaPagoEbs(Integer id) {
		hibernateUtil.delete(getSolicitudFechaPagoEbs(id));
	}

	@Override
	public List<SolicitudFechaPagoEbs> getAllSolicitudFechaPagoEbs() {
		return hibernateUtil.fetchAll(SolicitudFechaPagoEbs.class);
	}

	@Override
	public SolicitudFechaPagoEbs getSolicitudFechaPagoEbs(Integer id) {
		return hibernateUtil.fetchById(id,SolicitudFechaPagoEbs.class);
	}

	@Override
	public List<SolicitudFechaPagoEbs> getSolicitudFechaPagoEbsNoActualizadas() {
		String queryString ="FROM " + SolicitudFechaPagoEbs.class.getName()
				+ " WHERE ACTUALIZADA = :noActualizada";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("noActualizada", Etiquetas.CERO.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

}