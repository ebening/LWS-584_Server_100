package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.AidConfiguracionDAO;
import com.lowes.entity.AidConfiguracion;
import com.lowes.util.HibernateUtil;

@Repository
public class AidConfiguracionDAOImpl implements AidConfiguracionDAO{
	
	public AidConfiguracionDAOImpl(){
		System.out.println("AidConfiguracionDAOImpl()");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createAidConfiguracion(AidConfiguracion aidConfiguracion) {
		return (Integer) hibernateUtil.create(aidConfiguracion);
	}

	@Override
	public AidConfiguracion updateAidConfiguracion(AidConfiguracion aidConfiguracion) {
		return hibernateUtil.update(aidConfiguracion);
	}

	@Override
	public void deleteAidConfiguracion(Integer id) {
		AidConfiguracion aidConfiguracion = getAidConfiguracion(id); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(aidConfiguracion);
	}

	@Override
	public List<AidConfiguracion> getAllAidConfiguracion() {
		return hibernateUtil.fetchAll(AidConfiguracion.class);
	}

	@Override
	public AidConfiguracion getAidConfiguracion(Integer id) {
		return hibernateUtil.fetchById(id, AidConfiguracion.class);
	}

}