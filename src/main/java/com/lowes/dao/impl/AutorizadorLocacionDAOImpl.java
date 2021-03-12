package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.AutorizadorLocacionDAO;
import com.lowes.entity.AutorizadorLocacion;
import com.lowes.util.HibernateUtil;

@Repository
public class AutorizadorLocacionDAOImpl implements AutorizadorLocacionDAO {
	
	public AutorizadorLocacionDAOImpl(){
		System.out.println("AutorizadorLocacionDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createAutorizadorLocacion(AutorizadorLocacion autorizadorLocacion) {
		return (Integer) hibernateUtil.create(autorizadorLocacion);
	}

	@Override
	public AutorizadorLocacion updateAutorizadorLocacion(AutorizadorLocacion autorizadorLocacion) {
		return hibernateUtil.update(autorizadorLocacion);
	}

	@Override
	public void deleteAutorizadorLocacion(Integer idAutorizadorLocacion) {
		AutorizadorLocacion autorizadorLocacion = getAutorizadorLocacion(idAutorizadorLocacion);
		hibernateUtil.delete(autorizadorLocacion);
	}

	@Override
	public List<AutorizadorLocacion> getAllAutorizadorLocacion() {
		return hibernateUtil.fetchAll(AutorizadorLocacion.class);
	}

	@Override
	public AutorizadorLocacion getAutorizadorLocacion(Integer idAutorizadorLocacion) {
		return hibernateUtil.fetchById(idAutorizadorLocacion, AutorizadorLocacion.class);
	}

	@Override
	public AutorizadorLocacion getAutorizadorLocacionByLocacionNivelAutoriza(Integer idLocacion, Integer idNivelAutoriza) {
		String queryString ="FROM " + AutorizadorLocacion.class.getName()
				+ " WHERE ID_LOCACION = :idLocacion"
				+ " AND ID_NIVEL_AUTORIZA = :idNivelAutoriza";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idLocacion", idLocacion.toString());
		parameters.put("idNivelAutoriza", idNivelAutoriza.toString());
		
		List<AutorizadorLocacion> autorizadoresLocacion = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (autorizadoresLocacion.size() > 0) {
			return autorizadoresLocacion.get(0);
		} else {
			return null;
		}
	}

}
