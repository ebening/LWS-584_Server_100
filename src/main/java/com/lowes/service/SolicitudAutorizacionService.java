package com.lowes.service;

import java.util.List;

import com.lowes.entity.SolicitudAutorizacion;

public interface SolicitudAutorizacionService {
	public Integer createSolicitudAutorizacion(SolicitudAutorizacion solicitudAutorizacion);
    public SolicitudAutorizacion updateSolicitudAutorizacion(SolicitudAutorizacion solicitudAutorizacion);
    public void deleteSolicitudAutorizacion(Integer idSolicitudAutorizacion);
    public List<SolicitudAutorizacion> getAllSolicitudAutorizacion();
    public SolicitudAutorizacion getSolicitudAutorizacion(Integer idSolicitudAutorizacion);
    public SolicitudAutorizacion getLastSolicitudAutorizacion(Integer idSolicitud);
    public SolicitudAutorizacion getCurrentSolicitudAutorizacion(Integer idSolicitud);
    public SolicitudAutorizacion getSolicitudAutorizacionBySolicitudUsuario(Integer idSolicitud, Integer idUsuario);
    public Integer getCountSolicitudAutorizacionByUsuario(Integer idUsuario, Integer idEstadoSolicitud);
    public List<SolicitudAutorizacion> getAllSolicitudAutorizacionBySolicitud(Integer idSolicitud);
    public List<SolicitudAutorizacion> getAllSolicitudAutorizacionActivaBySolicitud(Integer idSolicitud);
    public Integer getCountAutorizadoresCriterio(Integer idSolicitud, Integer idTipoCriterio);
    public Integer getCountAutorizadoresCriterioVendorRisk(Integer idSolicitud, Integer idTipoCriterio);
    public List<SolicitudAutorizacion> getAllAutorizadoresByCriterio(Integer idSolicitud, Integer idTipoCriterio);
    public Integer getCountAllByUsuarioAutorizo(Integer idSolicitud, Integer idUsuario);
    public Integer getCountAllByUsuarioAutorizoCriterio(Integer idSolicitud, Integer idUsuario, Integer criterio);
    public Integer getCountAllByUsuarioAutorizoAutomatico(Integer idSolicitud, Integer idUsuario, Integer criterio);


}
