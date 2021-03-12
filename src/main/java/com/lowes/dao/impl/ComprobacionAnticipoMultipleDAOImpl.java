package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ComprobacionAnticipoMultipleDAO;
import com.lowes.entity.ComprobacionAnticipoMultiple;
import com.lowes.util.HibernateUtil;

@Repository
public class ComprobacionAnticipoMultipleDAOImpl implements ComprobacionAnticipoMultipleDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createComprobacionAnticipoMultiple(ComprobacionAnticipoMultiple comprobacionAnticipoMultiple) {
		return (Integer) hibernateUtil.create(comprobacionAnticipoMultiple);
	}

	@Override
	public ComprobacionAnticipoMultiple updateComprobacionAnticipoMultiple(ComprobacionAnticipoMultiple comprobacionAnticipoMultiple) {
		return hibernateUtil.update(comprobacionAnticipoMultiple);
	}

	@Override
	public void deleteComprobacionAnticipoMultiple(Integer idComprobacionAnticipoMultiple) {
		ComprobacionAnticipoMultiple comprobacionAnticipoMultiple = new ComprobacionAnticipoMultiple();
		comprobacionAnticipoMultiple = hibernateUtil.fetchById(idComprobacionAnticipoMultiple, ComprobacionAnticipoMultiple.class);
		hibernateUtil.delete(comprobacionAnticipoMultiple);
	}

	@Override
	public List<ComprobacionAnticipoMultiple> getAllComprobacionAnticipoMultiple() {
		return hibernateUtil.fetchAll(ComprobacionAnticipoMultiple.class);
	}

	@Override
	public List<ComprobacionAnticipoMultiple> getSolicitudByIdSolicitud(Integer idSolicitud) {
		String queryString = "FROM " + ComprobacionAnticipoMultiple.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		List<ComprobacionAnticipoMultiple> list = hibernateUtil.fetchAllHql(queryString, parameters);
		return list;
	}

	@Override
	public ComprobacionAnticipoMultiple getSolicitudByIdSolicitudMultiple(Integer idSolicitudMultiple) {
		String queryString = "FROM " + ComprobacionAnticipoMultiple.class.getName()
				+ " WHERE ID_SOLICITUD_MULTIPLE = :idSolicitudMultiple";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitudMultiple", idSolicitudMultiple.toString());
		List<ComprobacionAnticipoMultiple> list = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}
}
