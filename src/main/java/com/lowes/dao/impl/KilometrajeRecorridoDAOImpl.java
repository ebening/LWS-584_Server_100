package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.KilometrajeRecorridoDAO;
import com.lowes.entity.KilometrajeRecorrido;
import com.lowes.util.HibernateUtil;

@Repository
public class KilometrajeRecorridoDAOImpl implements KilometrajeRecorridoDAO{

	public KilometrajeRecorridoDAOImpl(){
		System.out.println("KilometrajeRecorridoDAOImpl()");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido) {
		return (Integer) hibernateUtil.create(kilometrajeRecorrido);
	}

	@Override
	public KilometrajeRecorrido updateKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido) {
		return hibernateUtil.update(kilometrajeRecorrido);
	}

	@Override
	public void deleteKilometrajeRecorrido(Integer id) {
		KilometrajeRecorrido kilometrajeRecorrido = getKilometrajeRecorrido(id); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(kilometrajeRecorrido);
	}

	@Override
	public List<KilometrajeRecorrido> getAllKilometrajeRecorrido() {
		return hibernateUtil.fetchAll(KilometrajeRecorrido.class);
	}

	@Override
	public KilometrajeRecorrido getKilometrajeRecorrido(Integer id) {
		return hibernateUtil.fetchById(id, KilometrajeRecorrido.class);
	}

}