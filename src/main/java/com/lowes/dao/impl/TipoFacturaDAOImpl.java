package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TipoFacturaDAO;
import com.lowes.entity.TipoFactura;
import com.lowes.util.HibernateUtil;

@Repository
public class TipoFacturaDAOImpl implements TipoFacturaDAO {
	
	public TipoFacturaDAOImpl(){
		System.out.println("TipoFacturaDAOImpl");
	}

	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createTipoFactura(TipoFactura tipoFactura) {
		return (Integer) hibernateUtil.create(tipoFactura);
	}

	@Override
	public TipoFactura updateTipoFactura(TipoFactura tipoFactura) {
		return hibernateUtil.update(tipoFactura);
	}

	@Override
	public void deleteTipoFactura(Integer id) {
		TipoFactura tipoFactura = getTipoFactura(id); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(tipoFactura);
	}

	@Override
	public List<TipoFactura> getAllTipoFactura() {
		return hibernateUtil.fetchAll(TipoFactura.class);
	}

	@Override
	public TipoFactura getTipoFactura(Integer id) {
		return hibernateUtil.fetchById(id, TipoFactura.class);
	}

}