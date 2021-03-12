package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.KilometrajeRecorridoDAO;
import com.lowes.entity.KilometrajeRecorrido;
import com.lowes.service.KilometrajeRecorridoService;

@Service
@Transactional
public class KilometrajeRecorridoServiceImpl implements KilometrajeRecorridoService{
	
	public KilometrajeRecorridoServiceImpl(){
		System.out.println("KilometrajeRecorridoServiceImpl()");
	}
	
	@Autowired
	private KilometrajeRecorridoDAO kilometrajeRecorridoDAO;

	@Override
	public Integer createKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido) {
		return kilometrajeRecorridoDAO.createKilometrajeRecorrido(kilometrajeRecorrido);
	}

	@Override
	public KilometrajeRecorrido updateKilometrajeRecorrido(KilometrajeRecorrido kilometrajeRecorrido) {
		return kilometrajeRecorridoDAO.updateKilometrajeRecorrido(kilometrajeRecorrido);
	}

	@Override
	public void deleteKilometrajeRecorrido(Integer id) {
		kilometrajeRecorridoDAO.deleteKilometrajeRecorrido(id);
	}

	@Override
	public List<KilometrajeRecorrido> getAllKilometrajeRecorrido() {
		return kilometrajeRecorridoDAO.getAllKilometrajeRecorrido();
	}

	@Override
	public KilometrajeRecorrido getKilometrajeRecorrido(Integer id) {
		return kilometrajeRecorridoDAO.getKilometrajeRecorrido(id);
	}

}