/**
 * 
 */
package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TipoLocacionDAO;
import com.lowes.entity.TipoLocacion;
import com.lowes.service.TipoLocacionService;
import com.lowes.util.HibernateUtil;

/**
 * @author miguelrg
 * @version 1.0
 */
@Repository
public class TipoLocacionDAOImpl implements TipoLocacionDAO {

	public TipoLocacionDAOImpl() {
		System.out.println("TipoLocacionDAOImplConstruct");
	}

	@Autowired
	private HibernateUtil hibernateUtil;
	@Autowired
	private TipoLocacionService tipoLocacionService;

	@Override
	public Integer createTipoLocacion(TipoLocacion tipoLocacion) {
		return (Integer) hibernateUtil.create(tipoLocacion);
	}

	@Override
	public TipoLocacion updateTipoLocacion(TipoLocacion tipoLocacion) {
		return hibernateUtil.update(tipoLocacion);
	}

	@Override
	public void deleteTipoLocacion(int id) {
		TipoLocacion tipoLocacion = tipoLocacionService.getTipoLocacion(id);
		hibernateUtil.delete(tipoLocacion);
	}

	@Override
	public List<TipoLocacion> getAllTipoLocaciones() {
		return hibernateUtil.fetchAll(TipoLocacion.class);
	}

	@Override
	public TipoLocacion getTipoLocacion(int id) {
		return hibernateUtil.fetchById(id, TipoLocacion.class);
	}
}