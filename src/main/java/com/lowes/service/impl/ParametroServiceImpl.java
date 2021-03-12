package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ParametroDAO;
import com.lowes.entity.Parametro;
import com.lowes.service.ParametroService;

@Service
@Transactional
public class ParametroServiceImpl implements ParametroService{
	
	public ParametroServiceImpl(){
		System.out.println("ParametroServiceImpl()");
	}
	
	@Autowired
	private ParametroDAO parametroDAO;

	@Override
	public Integer createParametro(Parametro parametro) {
		return parametroDAO.createParametro(parametro);
	}

	@Override
	public Parametro updateParametro(Parametro parametro) {
		return parametroDAO.updateParametro(parametro);
	}

	@Override
	public void deleteParametro(Integer id) {
		parametroDAO.deleteParametro(id);
	}

	@Override
	public List<Parametro> getAllParametro() {
		return parametroDAO.getAllParametro();
	}

	@Override
	public Parametro getParametro(Integer id) {
		return parametroDAO.getParametro(id);
	}

	@Override
	public Parametro getParametroByName(String parametro) {
		return parametroDAO.getParametroByName(parametro);
	}

	@Override
	public List<Parametro> getAllParametroEditable() {
		return parametroDAO.getAllParametroEditable();
	}

	@Override
	public List<Parametro> getParametrosByName(String parametro) {
		return parametroDAO.getParametrosByName(parametro);
	}

}