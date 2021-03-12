package com.lowes.dao;

import java.util.List;

import com.lowes.entity.Notificacion;

public interface NotificacionDAO {

	public Integer createNotificacion(Notificacion notificacion);

	public Notificacion updateNotificacion(Notificacion notificacion);

	public void deleteNotificacion(Integer idNotificacion);

	public List<Notificacion> getAllNotificacion();

	public Notificacion getNotificacion(Integer idNotificacion);

	public List<Notificacion> getNotificacionesPendientesByTipo(Integer tipoNotificacion);

}