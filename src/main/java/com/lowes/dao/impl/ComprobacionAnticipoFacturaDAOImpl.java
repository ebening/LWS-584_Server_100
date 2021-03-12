package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ComprobacionAnticipoFacturaDAO;
import com.lowes.entity.ComprobacionAnticipoFactura;
import com.lowes.util.HibernateUtil;

@Repository
public class ComprobacionAnticipoFacturaDAOImpl implements ComprobacionAnticipoFacturaDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;

	
	@Override
	public Integer createComprobacionAnticipoFactura(ComprobacionAnticipoFactura comprobacionAnticipoFactura) {
		return (Integer) hibernateUtil.create(comprobacionAnticipoFactura);
	}

	@Override
	public ComprobacionAnticipoFactura updateComprobacionAnticipoFactura(
			ComprobacionAnticipoFactura comprobacionAnticipoFactura) {
		return hibernateUtil.update(comprobacionAnticipoFactura);
	}

	@Override
	public void deleteComprobacionAnticipoFactura(Integer idComprobacionAnticipoFactura) {
		ComprobacionAnticipoFactura comprobacionAnticipoFactura = new ComprobacionAnticipoFactura();
		comprobacionAnticipoFactura = hibernateUtil.fetchById(idComprobacionAnticipoFactura, ComprobacionAnticipoFactura.class);
		hibernateUtil.delete(comprobacionAnticipoFactura);

	}

	@Override
	public List<ComprobacionAnticipoFactura> getAllComprobacionAnticipoFactura() {
		return hibernateUtil.fetchAll(ComprobacionAnticipoFactura.class);
	}

	@Override
	public List<ComprobacionAnticipoFactura> getComprobacionByIdAnticipo(Integer idAnticipo) {
		String queryString = "FROM " + ComprobacionAnticipoFactura.class.getName()
				+ " WHERE ID_SOLICITUD_ANTICIPO = :idAnticipo";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idAnticipo", String.valueOf(idAnticipo));
		
		List<ComprobacionAnticipoFactura> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		return listSolicitudes;
	}
	
	@Override
	public void deleteBySolicitudComprobacion(Integer idSolicitudComprobacion) {
		HashMap<String,String> parametros = new HashMap<>();
		parametros.put("idSolicitudComprobacion", idSolicitudComprobacion.toString());
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(ComprobacionAnticipoFactura.class.getName());
		query.append(" WHERE ID_SOLICITUD_COMPROBACION = :idSolicitudComprobacion");
			
		hibernateUtil.fetchDeleteAndUpdateQuerys(query.toString(), parametros);
	}
	

}
