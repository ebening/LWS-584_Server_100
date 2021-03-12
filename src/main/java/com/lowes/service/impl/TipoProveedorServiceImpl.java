package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TipoProveedorDAO;
import com.lowes.entity.TipoProveedor;
import com.lowes.service.TipoProveedorService;

@Service
@Transactional
public class TipoProveedorServiceImpl implements TipoProveedorService {
	
	public TipoProveedorServiceImpl(){
		System.out.println("TipoProveedorServiceImpl()");
	}
	
	@Autowired
	private TipoProveedorDAO tipoProveedorDAO;

	@Override
	public Integer createTipoProveedor(TipoProveedor tipoProveedor) {
		return tipoProveedorDAO.createTipoProveedor(tipoProveedor);
	}

	@Override
	public TipoProveedor updateTipoProveedor(TipoProveedor tipoProveedor) {
		return tipoProveedorDAO.updateTipoProveedor(tipoProveedor);
	}

	@Override
	public void deleteTipoProveedor(Integer id) {
		tipoProveedorDAO.deleteTipoProveedor(id);
	}

	@Override
	public List<TipoProveedor> getAllTipoProveedor() {
		return tipoProveedorDAO.getAllTipoProveedor();
	}

	@Override
	public TipoProveedor getTipoProveedor(Integer id) {
		return tipoProveedorDAO.getTipoProveedor(id);
	}

}