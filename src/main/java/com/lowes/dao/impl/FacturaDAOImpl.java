package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.FacturaDAO;
import com.lowes.entity.Factura;
import com.lowes.util.HibernateUtil;

@Repository
public class FacturaDAOImpl implements FacturaDAO {
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createFactura(Factura factura) {
		return (Integer) hibernateUtil.create(factura);
	}

	@Override
	public Factura updateFactura(Factura factura) {
		return hibernateUtil.update(factura);
	}

	@Override
	public void deleteFactura(Integer idfactura) {
		hibernateUtil.delete(new Factura(idfactura));
	}
	
	@Override
	public void deleteFactura(Factura factura) {
		hibernateUtil.delete(factura);
	}

	@Override
	public Factura getFactura(Integer idFactura) {
		return hibernateUtil.fetchById(idFactura, Factura.class);
	}

	@Override
	public List<Factura> getAllFactura() {
		return hibernateUtil.fetchAll(Factura.class);
	}
	
	@Override
	public List<Factura> getAllFacturaBySolicitud(Integer idSolicitud) {
		String queryString = "FROM " + Factura.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
		
	}

	@Override
	public List<Factura> getFacturaByUUID(String UUID) {
		String queryString = "FROM " + Factura.class.getName()
				+ " WHERE FOLIO_FISCAL = :UUID";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("UUID", UUID);
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public List<Factura> getFacturaByFolio(String folio) {
		String queryString = "FROM " + Factura.class.getName()
				+ " WHERE FACTURA = :folio";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("folio", folio);
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

}
