package com.lowes.dao;

import java.util.List;

import com.lowes.entity.SolicitudAnticipoViaje;

public interface SolicitudAnticipoViajeDAO {
	
	public Integer createSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje);
    public SolicitudAnticipoViaje updateSolicitudAnticipoViaje(SolicitudAnticipoViaje solicitudAnticipoViaje);
    public void deleteSolicitudAnticipoViaje(Integer id);
    public List<SolicitudAnticipoViaje> getAllSolicitudAnticipoViaje();
    public SolicitudAnticipoViaje getSolicitudAnticipoViaje(Integer id);
	public SolicitudAnticipoViaje getSolicitudAnticipoViajeBySolicitud(Integer idSolicitud);
	public List<SolicitudAnticipoViaje> getSolicitudAnticipoViajesBySolicitud(Integer idSolicitud);

}