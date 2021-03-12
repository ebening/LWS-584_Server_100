package com.lowes.service;

import java.util.List;

import com.lowes.entity.EstadoSolicitud;

public interface EstadoSolicitudService {
	
    public List<EstadoSolicitud> getAllEstadoSolicitud();
    public EstadoSolicitud getEstadoSolicitud(Integer idEstadoSolicitud);
    public List<EstadoSolicitud> getAllEstadoSolicitudOrder(String propiedad);
    public List<EstadoSolicitud> findByEstadoSolicitud(String estadoSolicitud);

}