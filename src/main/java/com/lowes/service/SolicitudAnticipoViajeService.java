package com.lowes.service;

import java.util.List;

import com.lowes.entity.SolicitudAnticipoViaje;

public interface SolicitudAnticipoViajeService {
	
	public Integer createSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje);
    public SolicitudAnticipoViaje updateSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje);
    public void deleteSolicitudAnticipoViaje(Integer id);
    public List<SolicitudAnticipoViaje> getAllSolicitudAnticipoViaje();
    public SolicitudAnticipoViaje getSolicitudAnticipoViaje(Integer id);
	public SolicitudAnticipoViaje getSolicitudAnticipoViajeBySolicitud(Integer idSolicitud);
	List<SolicitudAnticipoViaje> getSolicitudAnticipoViajesBySolicitud(Integer idSolicitud);

}