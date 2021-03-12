package com.lowes.service;

import java.util.List;

import com.lowes.entity.SolicitudArchivo;

public interface SolicitudArchivoService {
	public Integer createSolicitudArchivo(SolicitudArchivo solicitudArchivo);
    public SolicitudArchivo updateSolicitudArchivo(SolicitudArchivo solicitudArchivo);
    public void deleteSolicitudArchivo(Integer idSolicitudArchivo);
    public List<SolicitudArchivo> getAllSolicitudArchivo();
    public SolicitudArchivo getSolicitudArchivo(Integer idSolicitudArchivo);
    public List<SolicitudArchivo> getAllSolicitudArchivoBySolicitud(Integer idSolicitud);
}