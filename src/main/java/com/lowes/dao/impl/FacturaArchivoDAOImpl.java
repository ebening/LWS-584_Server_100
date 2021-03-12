package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.FacturaArchivoDAO;
import com.lowes.entity.FacturaArchivo;
import com.lowes.util.HibernateUtil;

@Repository
public class FacturaArchivoDAOImpl implements FacturaArchivoDAO{
	
	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Integer createFacturaArchivo(FacturaArchivo facturaArchivo) {
		return (Integer)hibernateUtil.create(facturaArchivo);
	}

	@Override
	public FacturaArchivo updateFacturaArchivo(FacturaArchivo facturaArchivo) {
		return hibernateUtil.update(facturaArchivo);
	}

	@Override
	public void deleteFacturaArchivo(Integer idFacturaArchivo) {
		hibernateUtil.delete(new FacturaArchivo(idFacturaArchivo));
	}
	
	@Override
	public void deleteFacturaArchivo(FacturaArchivo facturaArchivo) {
		hibernateUtil.delete(facturaArchivo);
	}

	@Override
	public List<FacturaArchivo> getAllFacturaArchivo() {
		return hibernateUtil.fetchAll(FacturaArchivo.class);
	}

	@Override
	public FacturaArchivo getFacturaArchivo(Integer idFacturaArchivo) {
		return hibernateUtil.fetchById(idFacturaArchivo, FacturaArchivo.class);
	}

	@Override
	public List<FacturaArchivo> getAllFacturaArchivoByFacturaTipoDocumento(Integer idFactura, Integer idTipoDocumento) {
		String queryString = "FROM " + FacturaArchivo.class.getName()
				+ " WHERE ID_FACTURA = :idFactura"
				+ " AND ID_TIPO_DOCUMENTO = :idTipoDocumento";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idFactura", idFactura.toString());
		parameters.put("idTipoDocumento", idTipoDocumento.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);

	}

	@Override
	public List<FacturaArchivo> getAllFacturaArchivoByIdFactura(Integer idFactura) {
		String queryString = "FROM " + FacturaArchivo.class.getName()
				+ " WHERE ID_FACTURA = :idFactura";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idFactura", idFactura.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
	@Override
	public void deleteAllFacturaArchivoByIdFactura(Integer idFactura) {
		HashMap<String,String> parametros = new HashMap<>();
		parametros.put("idFactura", idFactura.toString());
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(FacturaArchivo.class.getName());
		query.append(" WHERE ID_FACTURA = :idFactura");
			
		hibernateUtil.fetchDeleteAndUpdateQuerys(query.toString(), parametros);
	}
}