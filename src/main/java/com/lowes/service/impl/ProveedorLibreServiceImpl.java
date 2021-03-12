/**
 * 
 */
package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ProveedorLibreDAO;
import com.lowes.entity.ProveedorLibre;
import com.lowes.service.ProveedorLibreService;

/**
 * @author miguelrg
 * @version 1.0
 */
@Service
@Transactional
public class ProveedorLibreServiceImpl implements ProveedorLibreService {

	public ProveedorLibreServiceImpl() {
		System.out.println("ProveedorLibreServiceImplConstruct()");
	}

	@Autowired
	private ProveedorLibreDAO proveedorLibreDAO;

	@Override
	public Integer createProveedorLibre(ProveedorLibre proveedorLibre) {
		return proveedorLibreDAO.createProveedorLibre(proveedorLibre);
	}

	@Override
	public ProveedorLibre updateProveedorLibre(ProveedorLibre proveedorLibre) {
		return proveedorLibreDAO.updateProveedorLibre(proveedorLibre);
	}

	@Override
	public void deleteProveedorLibre(int idProveedorLibre) {
		proveedorLibreDAO.deleteProveedorLibre(idProveedorLibre);
	}

	@Override
	public List<ProveedorLibre> getAllProveedorLibre() {
		return proveedorLibreDAO.getAllProveedorLibre();
	}

	@Override
	public ProveedorLibre getProveedorLibre(int idProveedorLibre) {
		return proveedorLibreDAO.getProveedorLibre(idProveedorLibre);
	}

	@Override
	public ProveedorLibre existeProveedor(String rfc) {
		return proveedorLibreDAO.existeProveedor(rfc);
	}

}
