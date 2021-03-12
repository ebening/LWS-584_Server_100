package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.NotificacionDAO;
import com.lowes.entity.Notificacion;
import com.lowes.entity.Solicitud;
import com.lowes.util.HibernateUtil;

@Repository
public class NotificacionDAOImpl implements NotificacionDAO{

	@Autowired
	private HibernateUtil hibernateUtil;
	
	public NotificacionDAOImpl(){
		System.out.println("NotificacionDAOImpl()");
	}

	@Override
	public Integer createNotificacion(Notificacion notificacion) {
		return (Integer) hibernateUtil.create(notificacion);
	}

	@Override
	public Notificacion updateNotificacion(Notificacion notificacion) {
		return hibernateUtil.update(notificacion);
	}

	@Override
	public void deleteNotificacion(Integer idNotificacion) {
		Notificacion notificacion= getNotificacion(idNotificacion);
		hibernateUtil.delete(notificacion);	
	}

	@Override
	public List<Notificacion> getAllNotificacion() {
		return hibernateUtil.fetchAll(Notificacion.class);
	}

	@Override
	public Notificacion getNotificacion(Integer idNotificacion) {
		return hibernateUtil.fetchById(idNotificacion, Notificacion.class);
	}
	
	@Override
	public List<Notificacion> getNotificacionesPendientesByTipo(Integer tipoNotificacion) {
		String queryString = "FROM " + Notificacion.class.getName()
				+ " WHERE ID_TIPO_NOTIFICACION = :tipoNotificacion AND PROXIMO_ENVIO = current_date"
				+ " AND (ESTATUS = 'P' OR ESTATUS = 'N')";		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("tipoNotificacion", tipoNotificacion.toString());
		
		List<Notificacion> listNotificaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		return listNotificaciones;
	}
}