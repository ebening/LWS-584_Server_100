package com.lowes.service;

import java.util.List;

import com.lowes.entity.Notificacion;

public interface NotificacionService {

	public Integer createNotificacion(Notificacion notificacion);

	public Notificacion updateNotificacion(Notificacion notificacion);

	public void deleteNotificacion(Integer idNotificacion);

	public List<Notificacion> getAllNotificaciones();

	public Notificacion getNotificacion(Integer idNotificacion);

	public List<Notificacion> getNotificacionesPendientesByTipo(Integer tipoNotificacion);

}