package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.KilometrajeUbicacionDAO;
import com.lowes.entity.KilometrajeUbicacion;
import com.lowes.service.KilometrajeUbicacionService;

@Service
@Transactional
public class KilometrajeUbicacionServiceImpl implements KilometrajeUbicacionService{

	public KilometrajeUbicacionServiceImpl(){
		System.out.println("KilometrajeUbicacionServiceImpl");
	}
	
	@Autowired
	private KilometrajeUbicacionDAO kilometrajeUbicacionDAO;
	
	@Override
	public Integer createKilometrajeUbicacion(KilometrajeUbicacion kilometrajeUbicacion) {
		return kilometrajeUbicacionDAO.createKilometrajeUbicacion(kilometrajeUbicacion);
	}

	@Override
	public KilometrajeUbicacion updateKilometrajeUbicacion(KilometrajeUbicacion kilometrajeUbicacion) {
		return kilometrajeUbicacionDAO.updateKilometrajeUbicacion(kilometrajeUbicacion);
	}

	@Override
	public void deleteKilometrajeUbicacion(Integer id) {
		kilometrajeUbicacionDAO.deleteKilometrajeUbicacion(id);
	}

	@Override
	public List<KilometrajeUbicacion> getAllKilometrajeUbicacion() {
		return kilometrajeUbicacionDAO.getAllKilometrajeUbicacion();
	}

	@Override
	public KilometrajeUbicacion getKilometrajeUbicacion(Integer id) {
		return kilometrajeUbicacionDAO.getKilometrajeUbicacion(id);
	}

}