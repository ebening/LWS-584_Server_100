package com.lowes.service;

import java.util.List;

import com.lowes.entity.Solicitud;

public interface SolicitudService {
	public Integer createSolicitud(Solicitud solicitud);
    public Solicitud updateSolicitud(Solicitud solicitud);
    public void deleteSolicitud(Integer idSolicitud);
    public List<Solicitud> getAllSolicitud();
    public Solicitud getSolicitud(Integer idSolicitud);
    public Integer getSolicitudCountByUsuarioEstatus(Integer idUsuario, Integer idEstadoSolicitud);
    public Integer getSolicitudCountByUsuarioEstatusCajaChica(Integer idUsuario, Integer idEstadoSolicitud);
    public Integer getSolicitudCountByUsuarioEstatusDoble(Integer idUsuario, Integer idEstadoSolicitud1, Integer idEstadoSolicitud2);
    public Integer getSolicitudCountByUsuarioEstatusDobleCajaChica(Integer idUsuario, Integer idEstadoSolicitud1, Integer idEstadoSolicitud2);
	public List<Solicitud> getAllSolicitudByStatus(Integer idEstadoSolicitud);
	public List<Solicitud> getAnticiposPendientes(Integer idUsuario, Integer idProveedor);
	public Integer getSolicitudCountByAnticipoPentiente(Integer idUsuario, Integer idTipoSolicitud, Integer idEstadoSolicitud);
	public List<Solicitud> getAnticiposMultiplesByEstatus(Integer idUsuario, Integer idProveedor,  Integer idEstadoSolicitudPagada, Integer idEstadoSolicitudMultiple);
	public List<Solicitud> getAnticiposMultiplesByEstatusAComprobar(Integer idUsuario, Integer idProveedor, Integer idEstadoSolicitudPagada, Integer idEstadoSolicitudMultiple, Integer idSolicitud);
	public List<Solicitud> getAnticiposPendientesPorComprobar(Integer idUsuario, Integer idProveedor, Integer idEstadoCreado, Integer idEstadoCancelado, Integer idEstadoComprobado);
	public List<Solicitud> getSolicitudesByProveedor(Integer idProveedor);
}
