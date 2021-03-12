/**
 * 
 */
package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ProveedorDAO;
import com.lowes.entity.Proveedor;
import com.lowes.service.ProveedorService;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;

/**
 * @author miguelrg
 * @version 1.0
 */
@Repository
public class ProveedorDAOImpl implements ProveedorDAO {

	public ProveedorDAOImpl() {
		System.out.println("ProveedorDAOImplConstruct");
	}
	
	@Autowired
    private SessionFactory sessionFactory;

	@Autowired
	private HibernateUtil hibernateUtil;
	@Autowired
	private ProveedorService proveedorService;

	@Override
	public Integer createProveedor(Proveedor proveedor) {
		return (Integer) hibernateUtil.create(proveedor);
	}

	@Override
	public Proveedor updateProveedor(Proveedor proveedor) {
		return hibernateUtil.update(proveedor);
	}

	@Override
	public void deleteProveedor(int id) {
		Proveedor proveedor = proveedorService.getProveedor(id);
		hibernateUtil.delete(proveedor);
	}

	@Override
	public List<Proveedor> getAllProveedores() {
		return hibernateUtil.fetchAll(Proveedor.class);
	}

	@Override
	public Proveedor getProveedor(int id) {
		return hibernateUtil.fetchById(id, Proveedor.class);
	}
	
	@Override
	public List<Proveedor> getAllProveedoresByProveedorRiesgo() {
		String queryString ="FROM " + Proveedor.class.getName()
				+ " WHERE PROVEEDOR_RIESGO = :proveedorRiesgo";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("proveedorRiesgo", Etiquetas.UNO.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Proveedor> getProveedoresByTipo(int tipo) {
		String queryStr = "SELECT * FROM ("+
				"SELECT "+
				"	P.*,"+
				"	CASE WHEN U.NUMERO_PROVEEDOR IS NULL THEN 2 ELSE 1 END AS TP "+
				"FROM PROVEEDOR P "+
				"LEFT JOIN USUARIO U ON U.NUMERO_PROVEEDOR=P.NUMERO_PROVEEDOR"+
			") UP "+
			"WHERE UP.TP=:tipo "+
			"ORDER BY UP.DESCRIPCION";

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
		
		query.addEntity(Proveedor.class).setParameter("tipo", tipo);
		
		List<Proveedor> l = query.list();
		
		return  l;
	}	

	@Override
	public List<Proveedor> getProveedorByNumero(Integer numeroProveedor) {
		String queryString ="FROM " + Proveedor.class.getName()
				+ " WHERE NUMERO_PROVEEDOR = :numeroProveedor";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numeroProveedor", numeroProveedor);
		
		return hibernateUtil.fetchAllHqlObject(queryString, parameters);
	}
}