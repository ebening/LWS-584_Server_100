/**
 * 
 */
package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ProveedorLibreDAO;
import com.lowes.entity.ProveedorLibre;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.service.ProveedorLibreService;
import com.lowes.util.HibernateUtil;

/**
 * @author miguelrg
 * @version 1.0
 */
@Repository
public class ProveedorLibreDAOImpl implements ProveedorLibreDAO {

	public ProveedorLibreDAOImpl() {
		System.out.println("ProveedorLibreDAOImplConstruct");
	}

	@Autowired
	private HibernateUtil hibernateUtil;
	@Autowired
	private ProveedorLibreService proveedorLibreService;

	@Override
	public Integer createProveedorLibre(ProveedorLibre proveedorLibre) {
		return (Integer) hibernateUtil.create(proveedorLibre);
	}

	@Override
	public ProveedorLibre updateProveedorLibre(ProveedorLibre proveedorLibre) {
		return hibernateUtil.update(proveedorLibre);
	}

	@Override
	public void deleteProveedorLibre(int idProveedorLibre) {
		ProveedorLibre proveedorLibre = proveedorLibreService.getProveedorLibre(idProveedorLibre);
		hibernateUtil.delete(proveedorLibre);
	}

	@Override
	public List<ProveedorLibre> getAllProveedorLibre() {
		return hibernateUtil.fetchAll(ProveedorLibre.class);
	}

	@Override
	public ProveedorLibre getProveedorLibre(int idProveedorLibre) {
		return hibernateUtil.fetchById(idProveedorLibre, ProveedorLibre.class);
	}

	@Override
	public ProveedorLibre existeProveedor(String rfc) {
		String queryString = "FROM " + ProveedorLibre.class.getName()
				+ " WHERE RFC = :rfc";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("rfc", rfc);
		List<ProveedorLibre> lstProveedorLibre = hibernateUtil.fetchAllHql(queryString, parameters);
		if (lstProveedorLibre.size() > 0)
			return lstProveedorLibre.get(0);
		else
			return null;
	}
	
}