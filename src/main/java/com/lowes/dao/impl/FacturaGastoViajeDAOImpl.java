package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.FacturaGastoViajeDAO;
import com.lowes.entity.FacturaGastoViaje;
import com.lowes.util.HibernateUtil;

@Repository
public class FacturaGastoViajeDAOImpl implements FacturaGastoViajeDAO {
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje) {
		return (Integer) hibernateUtil.create(facturaGastoViaje);
	}

	@Override
	public FacturaGastoViaje updateFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje) {
		return hibernateUtil.update(facturaGastoViaje);
	}

	@Override
	public void deleteFacturaGastoViaje(Integer idfacturaGastoViaje) {
		hibernateUtil.delete(new FacturaGastoViaje(idfacturaGastoViaje));
	}
	
	@Override
	public void deleteFacturaGastoViaje(FacturaGastoViaje facturaGastoViaje) {
		hibernateUtil.delete(facturaGastoViaje);
	}

	@Override
	public FacturaGastoViaje getFacturaGastoViaje(Integer idFacturaGastoViaje) {
		return hibernateUtil.fetchById(idFacturaGastoViaje, FacturaGastoViaje.class);
	}

	@Override
	public List<FacturaGastoViaje> getAllFacturaGastoViaje() {
		return hibernateUtil.fetchAll(FacturaGastoViaje.class);
	}
	
	@Override
	public FacturaGastoViaje getFacturaGastoViajeByIdFactura(Integer idFactura) {
		String queryString = "FROM " + FacturaGastoViaje.class.getName()
				+ " WHERE ID_FACTURA = :idFactura";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idFactura", idFactura.toString());
		
		List<FacturaGastoViaje> result = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if(result != null && !result.isEmpty() )
			return result.get(0);
		else
			return null;		
	}

	@Override
	public void deleteAllFacturaGastoViajeByIdFactura(Integer idFactura) {
		HashMap<String,String> parametros = new HashMap<>();
		parametros.put("idFactura", idFactura.toString());
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(FacturaGastoViaje.class.getName());
		query.append(" WHERE ID_FACTURA = :idFactura");
			
		hibernateUtil.fetchDeleteAndUpdateQuerys(query.toString(), parametros);
	}
}
