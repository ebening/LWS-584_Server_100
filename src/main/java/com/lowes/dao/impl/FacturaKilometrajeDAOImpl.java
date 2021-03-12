package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.FacturaKilometrajeDAO;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.FacturaKilometraje;
import com.lowes.util.HibernateUtil;

@Repository
public class FacturaKilometrajeDAOImpl implements FacturaKilometrajeDAO{
	
	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Integer createFacturaKilometraje(FacturaKilometraje facturaKilometraje) {
		return (Integer)hibernateUtil.create(facturaKilometraje);
	}

	@Override
	public FacturaKilometraje updateFacturaKilometraje(FacturaKilometraje facturaKilometraje) {
		return hibernateUtil.update(facturaKilometraje);
	}

	@Override
	public void deleteFacturaKilometraje(Integer id) {
		hibernateUtil.delete(new FacturaKilometraje(id));
	}
	
	@Override
	public void deleteFacturaKilometraje(FacturaKilometraje facturaKilometraje) {
		hibernateUtil.delete(facturaKilometraje);
	}

	@Override
	public List<FacturaKilometraje> getAllFacturaKilometraje() {
		return hibernateUtil.fetchAll(FacturaKilometraje.class);
	}

	@Override
	public FacturaKilometraje getFacturaKilometraje(Integer id) {
		return hibernateUtil.fetchById(id, FacturaKilometraje.class);
	}

	@Override
	public List<FacturaKilometraje> getAllFacturaKilometrajeByKilometrajeRecorrido(Integer idKilometrajeRecorrido) {
		String queryString = "FROM " + FacturaKilometraje.class.getName()
				+ " WHERE ID_KILOMETRAJE_RECORRIDO = :idKilometrajeRecorrido";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idKilometrajeRecorrido", idKilometrajeRecorrido.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public List<FacturaKilometraje> getAllFacturaKilometrajeByIdSolicitud(Integer idSolicitud) {
		String queryString = "FROM " + FacturaKilometraje.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public void deleteFacturaKilometrajeByIdSolicitud(Integer idSolicitud) {
		HashMap<String,String> parametros = new HashMap<>();
		parametros.put("idSolicitud", idSolicitud.toString());
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(FacturaKilometraje.class.getName());
		query.append(" WHERE ID_SOLICITUD = :idSolicitud");
			
		hibernateUtil.fetchDeleteAndUpdateQuerys(query.toString(), parametros);
	}
	
	

}