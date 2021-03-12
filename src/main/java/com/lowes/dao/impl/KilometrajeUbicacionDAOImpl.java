package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.KilometrajeUbicacionDAO;
import com.lowes.entity.KilometrajeUbicacion;
import com.lowes.util.HibernateUtil;

@Repository
public class KilometrajeUbicacionDAOImpl implements KilometrajeUbicacionDAO{
	
	public KilometrajeUbicacionDAOImpl(){
		System.out.println("KilometrajeUbicacionDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createKilometrajeUbicacion(KilometrajeUbicacion kilometrajeUbicacion) {
		return (Integer) hibernateUtil.create(kilometrajeUbicacion);
	}

	@Override
	public KilometrajeUbicacion updateKilometrajeUbicacion(KilometrajeUbicacion kilometrajeUbicacion) {
		return hibernateUtil.update(kilometrajeUbicacion);
	}

	@Override
	public void deleteKilometrajeUbicacion(Integer id) {
		KilometrajeUbicacion kilometrajeUbicacion = getKilometrajeUbicacion(id);
		hibernateUtil.delete(kilometrajeUbicacion);
	}

	@Override
	public List<KilometrajeUbicacion> getAllKilometrajeUbicacion() {
		return hibernateUtil.fetchAll(KilometrajeUbicacion.class);
	}

	@Override
	public KilometrajeUbicacion getKilometrajeUbicacion(Integer id) {
		return hibernateUtil.fetchById(id, KilometrajeUbicacion.class);
	}

}