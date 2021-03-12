/**
 * 
 */
package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.PuestoDAO;
import com.lowes.entity.Puesto;
import com.lowes.service.PuestoService;
import com.lowes.util.HibernateUtil;

/**
 * @author miguelrg
 * @version 1.0
 */
@Repository
public class PuestoDAOImpl implements PuestoDAO {

	public PuestoDAOImpl() {
		System.out.println("PuestoDAOImplConstruct");
	}

	@Autowired
	private HibernateUtil hibernateUtil;
	@Autowired
	private PuestoService puestoService;

	@Override
	public Integer createPuesto(Puesto puesto) {
		return (Integer) hibernateUtil.create(puesto);
	}

	@Override
	public Puesto updatePuesto(Puesto puesto) {
		return hibernateUtil.update(puesto);
	}

	@Override
	public void deletePuesto(int id) {
		Puesto puesto = puestoService.getPuesto(id);
		hibernateUtil.delete(puesto);
	}

	@Override
	public List<Puesto> getAllPuestos() {
		return hibernateUtil.fetchAll(Puesto.class);
	}

	@Override
	public Puesto getPuesto(int id) {
		return hibernateUtil.fetchById(id, Puesto.class);
	}
}