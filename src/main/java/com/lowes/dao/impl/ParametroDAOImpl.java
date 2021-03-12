package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ParametroDAO;
import com.lowes.entity.Parametro;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;

@Repository
public class ParametroDAOImpl implements ParametroDAO{
	
	public ParametroDAOImpl(){
		System.out.println("ParametroDAOImpl()");
	}

	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createParametro(Parametro parametro) {
		return (Integer) hibernateUtil.create(parametro);
	}

	@Override
	public Parametro updateParametro(Parametro parametro) {
		return hibernateUtil.update(parametro);
	}

	@Override
	public void deleteParametro(Integer id) {
		Parametro parametro = getParametro(id); // Obtener compa�ia por id y enviar el objeto para eliminar
		hibernateUtil.delete(parametro);
	}

	@Override
	public List<Parametro> getAllParametro() {
		return hibernateUtil.fetchAll(Parametro.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Parametro> getAllParametro(String query) {
		return hibernateUtil.fetchAll(query);
	}

	@Override
	public Parametro getParametro(Integer id) {
		return hibernateUtil.fetchById(id, Parametro.class);
	}

	@Override
	public Parametro getParametroByName(String parametro) {
		String queryString ="FROM " + Parametro.class.getName()
				+ " WHERE PARAMETRO = :parametro";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("parametro", parametro);
		
		List<Parametro> parametros = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (parametros.size() > 0) {
			return parametros.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Parametro> getAllParametroEditable() {
		String queryString = "FROM " + Parametro.class.getName()
				+ " WHERE EDITABLE = :editable";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("editable", Etiquetas.UNO.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public List<Parametro> getParametrosByName(String parametro) {
		String queryString ="FROM " + Parametro.class.getName()
				+ " WHERE PARAMETRO = :parametro";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("parametro", parametro);
		
		List<Parametro> parametros = hibernateUtil.fetchAllHql(queryString, parameters);
		return parametros;
	}

}