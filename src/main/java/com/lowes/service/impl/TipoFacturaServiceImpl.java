package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TipoFacturaDAO;
import com.lowes.entity.TipoFactura;
import com.lowes.service.TipoFacturaService;

@Service
@Transactional
public class TipoFacturaServiceImpl implements TipoFacturaService{

	public TipoFacturaServiceImpl(){
		System.out.println("TipoFacturaServiceImpl()");
	}
	
	@Autowired
	private TipoFacturaDAO tipoFacturaDAO; 
	
	@Override
	public Integer createTipoFactura(TipoFactura tipoFactura) {
		return tipoFacturaDAO.createTipoFactura(tipoFactura);
	}

	@Override
	public TipoFactura updateTipoFactura(TipoFactura tipoFactura) {
		return tipoFacturaDAO.updateTipoFactura(tipoFactura);
	}

	@Override
	public void deleteTipoFactura(Integer id) {
		tipoFacturaDAO.deleteTipoFactura(id);
	}

	@Override
	public List<TipoFactura> getAllTipoFactura() {
		return tipoFacturaDAO.getAllTipoFactura();
	}

	@Override
	public TipoFactura getTipoFactura(Integer id) {
		return tipoFacturaDAO.getTipoFactura(id);
	}

}