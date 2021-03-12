package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TipoSolicitudDAO;
import com.lowes.entity.TipoSolicitud;
import com.lowes.util.HibernateUtil;

@Repository
public class TipoSolicitudDAOImpl implements TipoSolicitudDAO{
	
	public TipoSolicitudDAOImpl(){
		System.out.println("TipoSolicitudDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createTipoSolicitud(TipoSolicitud tipoSolicitud) {
		return (Integer) hibernateUtil.create(tipoSolicitud);
	}

	@Override
	public TipoSolicitud updateTipoSolicitud(TipoSolicitud tipoSolicitud) {
		return hibernateUtil.update(tipoSolicitud);
	}

	@Override
	public void deleteTipoSolicitud(Integer idTipoSolicitud) {
		TipoSolicitud tipoSolicitud = getTipoSolicitud(idTipoSolicitud); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(tipoSolicitud);
	}

	@Override
	public List<TipoSolicitud> getAllTipoSolicitud() {
		return hibernateUtil.fetchAll(TipoSolicitud.class);
	}
	
	@Override
	public List<TipoSolicitud> getAllTipoSolicitudOrder(String propiedad) {
		return hibernateUtil.fetchAllOrder(TipoSolicitud.class, propiedad);
	}

	@Override
	public TipoSolicitud getTipoSolicitud(Integer idTipoSolicitud) {
		return hibernateUtil.fetchById(idTipoSolicitud, TipoSolicitud.class);
	}

}