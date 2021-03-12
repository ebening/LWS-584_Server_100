package com.lowes.dao;

import java.util.List;

import com.lowes.entity.VwSolicitudResumenAutorizacion;

public interface VwSolicitudResumenAutorizacionDAO {

    public List<VwSolicitudResumenAutorizacion> getAllVwSolicitudResumenAutorizacion();
    public VwSolicitudResumenAutorizacion getVwSolicitudResumenAutorizacion(Integer idVwSolicitudResumenAutorizacion);
    public List<VwSolicitudResumenAutorizacion> getAllVwSolicitudResumenAutorizacionByUsuarioEstatusSolicitud(Integer idUsuario, Integer idEstadoSolicitud, String fechaInicial, String fechaFinal);
    public Integer getCountSolicitudAutorizacionByUsuario(Integer idUsuario, Integer idEstadoSolicitud);

}
