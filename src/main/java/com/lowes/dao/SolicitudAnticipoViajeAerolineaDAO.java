package com.lowes.dao;

import java.util.List;

import com.lowes.entity.SolicitudAnticipoViajeAerolinea;

public interface SolicitudAnticipoViajeAerolineaDAO {
	
	public Integer createSolicitudAnticipoViajeAerolinea(SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea);
    public SolicitudAnticipoViajeAerolinea updateSolicitudAnticipoViajeAerolinea(SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea);
    public void deleteSolicitudAnticipoViajeAerolinea(Integer id);
    public List<SolicitudAnticipoViajeAerolinea> getAllSolicitudAnticipoViajeAerolinea();
    public SolicitudAnticipoViajeAerolinea getSolicitudAnticipoViajeAerolinea(Integer id);
	public List<SolicitudAnticipoViajeAerolinea> getAllSolicitudAnticipoViajeAerolineaBySol(Integer idSolicitudAnticipoViaje);	

}