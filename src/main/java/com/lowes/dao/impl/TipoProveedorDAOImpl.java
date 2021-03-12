package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TipoProveedorDAO;
import com.lowes.entity.TipoProveedor;
import com.lowes.util.HibernateUtil;

@Repository
public class TipoProveedorDAOImpl implements TipoProveedorDAO{
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	public TipoProveedorDAOImpl(){
		System.out.println("TipoProveedorDAOImpl()");
	}

	@Override
	public Integer createTipoProveedor(TipoProveedor tipoProveedor) {
		return (Integer) hibernateUtil.create(tipoProveedor);
	}

	@Override
	public TipoProveedor updateTipoProveedor(TipoProveedor tipoProveedor) {
		return hibernateUtil.update(tipoProveedor);
	}

	@Override
	public void deleteTipoProveedor(Integer id) {
		TipoProveedor tipoProveedor= getTipoProveedor(id);
		hibernateUtil.delete(tipoProveedor);
	}

	@Override
	public List<TipoProveedor> getAllTipoProveedor() {
		return hibernateUtil.fetchAll(TipoProveedor.class);
	}

	@Override
	public TipoProveedor getTipoProveedor(Integer id) {
		return hibernateUtil.fetchById(id, TipoProveedor.class);
	}

}