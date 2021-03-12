package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.AutorizadorProveedorRiesgoDAO;
import com.lowes.entity.AutorizadorProveedorRiesgo;
import com.lowes.util.HibernateUtil;

@Repository
public class AutorizadorProveedorRiesgoDAOImpl implements AutorizadorProveedorRiesgoDAO {
	
	public AutorizadorProveedorRiesgoDAOImpl(){
		System.out.println("AutorizadorProveedorRiesgoDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createAutorizadorProveedorRiesgo(AutorizadorProveedorRiesgo autorizadorProveedorRiesgo) {
		return (Integer) hibernateUtil.create(autorizadorProveedorRiesgo);
	}

	@Override
	public AutorizadorProveedorRiesgo updateAutorizadorProveedorRiesgo(AutorizadorProveedorRiesgo autorizadorProveedorRiesgo) {
		return hibernateUtil.update(autorizadorProveedorRiesgo);
	}

	@Override
	public void deleteAutorizadorProveedorRiesgo(Integer idAutorizadorProveedorRiesgo) {
		AutorizadorProveedorRiesgo autorizadorProveedorRiesgo = getAutorizadorProveedorRiesgo(idAutorizadorProveedorRiesgo);
		hibernateUtil.delete(autorizadorProveedorRiesgo);
	}

	@Override
	public List<AutorizadorProveedorRiesgo> getAllAutorizadorProveedorRiesgo() {
		return hibernateUtil.fetchAll(AutorizadorProveedorRiesgo.class);
	}

	@Override
	public AutorizadorProveedorRiesgo getAutorizadorProveedorRiesgo(Integer idAutorizadorProveedorRiesgo) {
		return hibernateUtil.fetchById(idAutorizadorProveedorRiesgo, AutorizadorProveedorRiesgo.class);
	}

	@Override
	public List<AutorizadorProveedorRiesgo> getAutorizadorProveedorRiesgoByProveedor(Integer idProveedor) {
		String queryString ="FROM " + AutorizadorProveedorRiesgo.class.getName()
				+ " WHERE ID_PROVEEDOR_RIESGO = :idProveedor"
				+ " ORDER BY ID_NIVEL_AUTORIZA ASC";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idProveedor", idProveedor.toString());
		
		List<AutorizadorProveedorRiesgo> autorizadorProveedorRiesgo = hibernateUtil.fetchAllHql(queryString, parameters);		
		return autorizadorProveedorRiesgo;
	}
	
}