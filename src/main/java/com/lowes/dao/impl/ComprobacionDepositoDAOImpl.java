package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ComprobacionDepositoDAO;
import com.lowes.entity.ComprobacionDeposito;
import com.lowes.util.HibernateUtil;

@Repository
public class ComprobacionDepositoDAOImpl implements ComprobacionDepositoDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createComprobacionDeposito(ComprobacionDeposito comprobacionDeposito) {
		return (Integer)hibernateUtil.create(comprobacionDeposito);
	}

	@Override
	public ComprobacionDeposito updateComprobacionDeposito(ComprobacionDeposito comprobacionDeposito) {
		return hibernateUtil.update(comprobacionDeposito);
	}

	@Override
	public void deleteComprobacionDeposito(Integer idComprobacionDeposito) {
		ComprobacionDeposito comprobacionDeposito = new ComprobacionDeposito();
		comprobacionDeposito = hibernateUtil.fetchById(idComprobacionDeposito, ComprobacionDeposito.class);
		hibernateUtil.delete(comprobacionDeposito);		
	}

	@Override
	public List<ComprobacionDeposito> getAllComprobacionDeposito() {
		return hibernateUtil.fetchAll(ComprobacionDeposito.class);
	}

	@Override
	public List<ComprobacionDeposito> getAllComprobacionDepositoBySolicitud(Integer idSolicitud) {
		String queryString ="FROM " + ComprobacionDeposito.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public ComprobacionDeposito getComprobacionDeposito(Integer idDocumento) {
		return hibernateUtil.fetchById(idDocumento, ComprobacionDeposito.class);
	}

}
