package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.AidConfiguracionDAO;
import com.lowes.entity.AidConfiguracion;
import com.lowes.service.AidConfiguracionService;

@Service
@Transactional
public class AidConfiguracionServiceImpl implements AidConfiguracionService{
	
	AidConfiguracionServiceImpl(){
		System.out.println("AidConfiguracionServiceImpl()");
	}

	@Autowired
	private AidConfiguracionDAO aidConfiguracionDAO;
	
	@Override
	public Integer createAidConfiguracion(AidConfiguracion aidConfiguracion) {
		return aidConfiguracionDAO.createAidConfiguracion(aidConfiguracion);
	}

	@Override
	public AidConfiguracion updateAidConfiguracion(AidConfiguracion aidConfiguracion) {
		return aidConfiguracionDAO.updateAidConfiguracion(aidConfiguracion);
	}

	@Override
	public void deleteAidConfiguracion(Integer id) {
		aidConfiguracionDAO.deleteAidConfiguracion(id);
	}

	@Override
	public List<AidConfiguracion> getAllAidConfiguracion() {
		return aidConfiguracionDAO.getAllAidConfiguracion();
	}

	@Override
	public AidConfiguracion getAidConfiguracion(Integer id) {
		return aidConfiguracionDAO.getAidConfiguracion(id);
	}

}