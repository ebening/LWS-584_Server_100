package com.lowes.service;

import java.util.List;

import com.lowes.entity.SolicitudAnticipoViajeConceptos;

public interface SolicitudAnticipoViajeConceptosService {
	
	public Integer createSolicitudAnticipoViajeConceptos(SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConceptos);
    public SolicitudAnticipoViajeConceptos updateSolicitudAnticipoViajeConceptos(SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConceptos);
    public void deleteSolicitudAnticipoViajeConceptos(Integer id);
    public List<SolicitudAnticipoViajeConceptos> getAllSolicitudAnticipoViajeConceptos();
    public SolicitudAnticipoViajeConceptos getSolicitudAnticipoViajeConceptos(Integer id);
	public List<SolicitudAnticipoViajeConceptos> getAllSolicitudAnticipoViajeConceptoBySol(Integer idSolicitudAnticipoViaje);	

}