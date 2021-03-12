package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.CuentaContableDAO;
import com.lowes.entity.CuentaContable;
import com.lowes.util.HibernateUtil;

@Repository
public class CuentaContableDAOImpl implements CuentaContableDAO{
	
	public CuentaContableDAOImpl(){
		System.out.println("CuentaContableDAOImpl()");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createCuentaContable(CuentaContable cuentaContable) {
		return (Integer) hibernateUtil.create(cuentaContable);
	}

	@Override
	public CuentaContable updateCuentaContable(CuentaContable cuentaContable) {
		return hibernateUtil.update(cuentaContable);
	}

	@Override
	public void deleteCuentaContable(Integer id) {
		CuentaContable cuentaContable = getCuentaContable(id); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(cuentaContable);
	}

	@Override
	public List<CuentaContable> getAllCuentaContable() {
		return hibernateUtil.fetchAll(CuentaContable.class);
	}

	@Override
	public CuentaContable getCuentaContable(Integer id) {
		return hibernateUtil.fetchById(id, CuentaContable.class);
	}

	@Override
	public List<CuentaContable> getAllCuentaContableOrderByNumeroCuentaConable() {
		
		String queryString ="FROM " + CuentaContable.class.getName()
				+ " ORDER BY NUMERO_CUENTA_CONTABLE";
		
		Map<String, String> parameters = new HashMap<String, String>();		
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
	public CuentaContable getCCByNumeroCuenta(String numCuentaContable) {
		String queryString = "FROM " + CuentaContable.class.getName()
				+ " WHERE NUMERO_CUENTA_CONTABLE = :numCuentaContable";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("numCuentaContable", numCuentaContable);
		
		List<CuentaContable> result = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if(result != null)
			return result.get(0);
		else
			return null;
	}
}