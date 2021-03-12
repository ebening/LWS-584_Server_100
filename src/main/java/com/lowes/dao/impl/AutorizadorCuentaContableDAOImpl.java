package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.AutorizadorCuentaContableDAO;
import com.lowes.entity.AutorizadorCuentaContable;
import com.lowes.util.HibernateUtil;

@Repository
public class AutorizadorCuentaContableDAOImpl implements AutorizadorCuentaContableDAO {
	
	public AutorizadorCuentaContableDAOImpl(){
		System.out.println("AutorizadorCuentaContableDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createAutorizadorCuentaContable(AutorizadorCuentaContable autorizadorCuentaContable) {
		return (Integer) hibernateUtil.create(autorizadorCuentaContable);
	}

	@Override
	public AutorizadorCuentaContable updateAutorizadorCuentaContable(AutorizadorCuentaContable autorizadorCuentaContable) {
		return hibernateUtil.update(autorizadorCuentaContable);
	}

	@Override
	public void deleteAutorizadorCuentaContable(Integer idAutorizadorCuentaContable) {
		AutorizadorCuentaContable autorizadorCuentaContable = getAutorizadorCuentaContable(idAutorizadorCuentaContable);
		hibernateUtil.delete(autorizadorCuentaContable);
	}

	@Override
	public List<AutorizadorCuentaContable> getAllAutorizadorCuentaContable() {
		return hibernateUtil.fetchAll(AutorizadorCuentaContable.class);
	}

	@Override
	public AutorizadorCuentaContable getAutorizadorCuentaContable(Integer idAutorizadorCuentaContable) {
		return hibernateUtil.fetchById(idAutorizadorCuentaContable, AutorizadorCuentaContable.class);
	}

	@Override
	public List<AutorizadorCuentaContable> getAutorizadorCuentaContableByCuentaContable(Integer idCuentaContable) {
		String queryString ="FROM " + AutorizadorCuentaContable.class.getName()
				+ " WHERE ID_CUENTA_CONTABLE = :idCuentaContable"
				+ " ORDER BY ID_NIVEL_AUTORIZA ASC";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idCuentaContable", idCuentaContable.toString());
	
		List<AutorizadorCuentaContable> autorizadorCuentaContable = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return autorizadorCuentaContable;
	}

}
