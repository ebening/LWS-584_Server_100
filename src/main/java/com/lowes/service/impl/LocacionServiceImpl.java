/**
 * 
 */
package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.LocacionDAO;
import com.lowes.entity.Locacion;
import com.lowes.service.LocacionService;

/**
 * @author miguelrg
 * @version 1.0
 */
@Service
@Transactional
public class LocacionServiceImpl implements LocacionService {

	public LocacionServiceImpl() {
		System.out.println("LocacionServiceImplConstruct()");
	}

	@Autowired
	private LocacionDAO locacionDAO;

	@Override
	public Integer createLocacion(Locacion locacion) {
		return locacionDAO.createLocacion(locacion);
	}

	@Override
	public Locacion updateLocacion(Locacion locacion) {
		return locacionDAO.updateLocacion(locacion);
	}

	@Override
	public void deleteLocacion(Integer id) {
		locacionDAO.deleteLocacion(id);
	}

	@Override
	public List<Locacion> getAllLocaciones() {
		return locacionDAO.getAllLocaciones();
	}

	@Override
	public Locacion getLocacion(Integer id) {
		return locacionDAO.getLocacion(id);
	}

}
