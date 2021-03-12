package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.NotificacionDAO;
import com.lowes.entity.Notificacion;
import com.lowes.service.NotificacionService;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService{
	
	public NotificacionServiceImpl(){
		System.out.println("NotificacionServiceImpl()");
	}
	
	@Autowired
	private NotificacionDAO notificacionDAO;

	@Override
	public Integer createNotificacion(Notificacion notificacion) {
		return notificacionDAO.createNotificacion(notificacion);
	}

	@Override
	public Notificacion updateNotificacion(Notificacion notificacion) {
		return notificacionDAO.updateNotificacion(notificacion);
	}

	@Override
	public void deleteNotificacion(Integer idNotificacion) {
		notificacionDAO.deleteNotificacion(idNotificacion);
	}

	@Override
	public List<Notificacion> getAllNotificaciones() {
		return notificacionDAO.getAllNotificacion();
	}

	@Override
	public Notificacion getNotificacion(Integer idNotificacion) {
		return notificacionDAO.getNotificacion(idNotificacion);
	}

	@Override
	public List<Notificacion> getNotificacionesPendientesByTipo(Integer tipoNotificacion) {
		return notificacionDAO.getNotificacionesPendientesByTipo(tipoNotificacion);
	}

}