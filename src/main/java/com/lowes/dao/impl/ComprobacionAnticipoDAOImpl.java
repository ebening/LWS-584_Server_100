package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ComprobacionAnticipoDAO;
import com.lowes.entity.ComprobacionAnticipo;
import com.lowes.entity.Solicitud;
import com.lowes.util.HibernateUtil;

@Repository
public class ComprobacionAnticipoDAOImpl implements ComprobacionAnticipoDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createComprobacionAnticipo(ComprobacionAnticipo comprobacionAnticipo) {
		return (Integer) hibernateUtil.create(comprobacionAnticipo);
	}

	@Override
	public ComprobacionAnticipo updateComprobacionAnticipo(ComprobacionAnticipo comprobacionAnticipo) {
		return hibernateUtil.update(comprobacionAnticipo);
	}

	@Override
	public void deleteComprobacionAnticipo(Integer idComprobacionAnticipo) {
		ComprobacionAnticipo comprobacionAnticipo = new ComprobacionAnticipo();
		comprobacionAnticipo = hibernateUtil.fetchById(idComprobacionAnticipo, ComprobacionAnticipo.class);
		hibernateUtil.delete(comprobacionAnticipo);
	}

	@Override
	public List<ComprobacionAnticipo> getAllComprobacionAnticipo() {
		return hibernateUtil.fetchAll(ComprobacionAnticipo.class);
	}

	@Override
	public List<ComprobacionAnticipo> getAnticiposByComprobacion(Integer idComprobacion) {
		String queryString = "FROM " + ComprobacionAnticipo.class.getName()
				+ " WHERE ID_SOLICITUD_COMPROBACION = :idComprobacion";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idComprobacion", idComprobacion.toString());
		List<ComprobacionAnticipo> lstComprobacionAnticipo = hibernateUtil.fetchAllHql(queryString, parameters);
		return lstComprobacionAnticipo;
	}

	@Override
	public ComprobacionAnticipo getComprobacionByAnticipo(Integer idAnticipo) {
		String queryString = "FROM " + ComprobacionAnticipo.class.getName()
				+ " WHERE ID_SOLICITUD_ANTICIPO = :idAnticipo";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idAnticipo", idAnticipo.toString());
		List<ComprobacionAnticipo> lstComprobacionAnticipo = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if(lstComprobacionAnticipo.isEmpty())
			return null;
		else
			return lstComprobacionAnticipo.get(0);
	}
}
