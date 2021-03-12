/**
 * 
 */
package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.PuestoDAO;
import com.lowes.entity.Puesto;
import com.lowes.service.PuestoService;

/**
 * @author miguelrg
 * @version 1.0
 */
@Service
@Transactional
public class PuestoServiceImpl implements PuestoService {

	public PuestoServiceImpl() {
		System.out.println("PuestoServiceImplConstruct()");
	}

	@Autowired
	private PuestoDAO puestoDAO;

	@Override
	public Integer createPuesto(Puesto puesto) {
		return puestoDAO.createPuesto(puesto);
	}

	@Override
	public Puesto updatePuesto(Puesto puesto) {
		return puestoDAO.updatePuesto(puesto);
	}

	@Override
	public void deletePuesto(int id) {
		puestoDAO.deletePuesto(id);
	}

	@Override
	public List<Puesto> getAllPuestos() {
		return puestoDAO.getAllPuestos();
	}

	@Override
	public Puesto getPuesto(int id) {
		return puestoDAO.getPuesto(id);
	}

}
