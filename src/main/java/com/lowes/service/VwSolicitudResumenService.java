package com.lowes.service;

import java.util.List;

import com.lowes.entity.VwSolicitudResumen;

public interface VwSolicitudResumenService {

    public List<VwSolicitudResumen> getAllVwSolicitudResumen();
    public VwSolicitudResumen getVwSolicitudResumen(Integer idVwSolicitudResumen);
    public List<VwSolicitudResumen> getAllVwSolicitudResumenByUsuarioEstatusSolicitud(Integer idUsuario, Integer idEstadoSolicitud, String fechaInicial, String fechaFinal);
    public List<VwSolicitudResumen> getAllVwSolicitudResumenByUsuarioEstatusSolicitudCajaChica(Integer idUsuario, Integer idEstadoSolicitud, String fechaInicial, String fechaFinal);
	public List<VwSolicitudResumen> getAllVwSolicitudResumenPendientesComprobar(Integer idUsuario, Integer idEstadoSolicitud, Integer idTipoSolicitud, String fechaInicial, String fechaFinal);

}
