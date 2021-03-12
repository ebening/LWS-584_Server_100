package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.AutorizadorLocacionDAO;
import com.lowes.entity.AutorizadorLocacion;
import com.lowes.service.AutorizadorLocacionService;

@Service
@Transactional
public class AutorizadorLocacionServiceImpl implements AutorizadorLocacionService {
	
	public AutorizadorLocacionServiceImpl(){
		
	}

	@Autowired
	private AutorizadorLocacionDAO autorizadorLocacionDAO;
	
	
	@Override
	public Integer createAutorizadorLocacion(AutorizadorLocacion autorizadorLocacion) {
		return autorizadorLocacionDAO.createAutorizadorLocacion(autorizadorLocacion);
	}

	@Override
	public AutorizadorLocacion updateAutorizadorLocacion(AutorizadorLocacion autorizadorLocacion) {
		return autorizadorLocacionDAO.updateAutorizadorLocacion(autorizadorLocacion);
	}

	@Override
	public void deleteAutorizadorLocacion(Integer idAutorizadorLocacion) {
		autorizadorLocacionDAO.deleteAutorizadorLocacion(idAutorizadorLocacion);

	}

	@Override
	public List<AutorizadorLocacion> getAllAutorizadorLocacion() {
		return autorizadorLocacionDAO.getAllAutorizadorLocacion();
	}

	@Override
	public AutorizadorLocacion getAutorizadorLocacion(Integer idAutorizadorLocacion) {
		return autorizadorLocacionDAO.getAutorizadorLocacion(idAutorizadorLocacion);
	}

	@Override
	public AutorizadorLocacion getAutorizadorLocacionByLocacionNivelAutoriza(Integer idLocacion, Integer idNivelAutoriza) {
		return autorizadorLocacionDAO.getAutorizadorLocacionByLocacionNivelAutoriza(idLocacion, idNivelAutoriza);
	}

}
