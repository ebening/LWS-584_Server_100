package com.lowes.dao;

import java.util.List;

import com.lowes.entity.EstadoSolicitud;

public interface EstadoSolicitudDAO {
    public List<EstadoSolicitud> getAllEstadoSolicitud();
    public EstadoSolicitud getEstadoSolicitud(Integer idEstadoSolicitud);
	public List<EstadoSolicitud> getAllEstadoSolicitudOrder(String propiedad);
	public List<EstadoSolicitud> findByEstadoSolicitud(String estadoSolicitud);
}