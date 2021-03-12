/**
 * 
 */
package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.LocacionDAO;
import com.lowes.entity.Locacion;
import com.lowes.service.LocacionService;
import com.lowes.util.HibernateUtil;

/**
 * @author miguelrg
 * @version 1.0
 */
@Repository
public class LocacionDAOImpl implements LocacionDAO {

	public LocacionDAOImpl() {
		System.out.println("LocacionDAOImplConstruct");
	}

	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Autowired
	private LocacionService locacionService;

	@Override
	public Integer createLocacion(Locacion locacion) {
		return (Integer) hibernateUtil.create(locacion);
	}

	@Override
	public Locacion updateLocacion(Locacion locacion) {
		return hibernateUtil.update(locacion);
	}

	@Override
	public void deleteLocacion(Integer id) {
		Locacion locacion = locacionService.getLocacion(id);
		hibernateUtil.delete(locacion);
	}

	@Override
	public List<Locacion> getAllLocaciones() {
		return hibernateUtil.fetchAll(Locacion.class);
	}

	@Override
	public Locacion getLocacion(Integer id) {
		return hibernateUtil.fetchById(id, Locacion.class);
	}
}