/**
 * 
 */
package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TipoLocacionDAO;
import com.lowes.entity.TipoLocacion;
import com.lowes.service.TipoLocacionService;

/**
 * @author miguelrg
 * @version 1.0
 */
@Service
@Transactional
public class TipoLocacionServiceImpl implements TipoLocacionService {

	public TipoLocacionServiceImpl() {
		System.out.println("TipoLocacionServiceImplConstruct()");
	}

	@Autowired
	private TipoLocacionDAO tipoLocacionDAO;

	@Override
	public Integer createTipoLocacion(TipoLocacion tipoLocacion) {
		return tipoLocacionDAO.createTipoLocacion(tipoLocacion);
	}

	@Override
	public TipoLocacion updateTipoLocacion(TipoLocacion tipoLocacion) {
		return tipoLocacionDAO.updateTipoLocacion(tipoLocacion);
	}

	@Override
	public void deleteTipoLocacion(int id) {
		tipoLocacionDAO.deleteTipoLocacion(id);
	}

	@Override
	public List<TipoLocacion> getAllTipoLocaciones() {
		return tipoLocacionDAO.getAllTipoLocaciones();
	}

	@Override
	public TipoLocacion getTipoLocacion(int id) {
		return tipoLocacionDAO.getTipoLocacion(id);
	}
}