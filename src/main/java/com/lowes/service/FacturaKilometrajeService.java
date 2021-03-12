package com.lowes.service;

import java.util.List;

import com.lowes.entity.FacturaKilometraje;

public interface FacturaKilometrajeService {
	
	public Integer createFacturaKilometraje(FacturaKilometraje facturaKilometraje);
    public FacturaKilometraje updateFacturaKilometraje(FacturaKilometraje facturaKilometraje);
    public void deleteFacturaKilometraje(Integer id);
    public List<FacturaKilometraje> getAllFacturaKilometraje();
    public FacturaKilometraje getFacturaKilometraje(Integer id);
    public List<FacturaKilometraje> getAllFacturaKilometrajeByKilometrajeRecorrido(Integer idKilometrajeRecorrido);
	public List<FacturaKilometraje> getAllFacturaKilometrajeByIdSolicitud(Integer idSolicitud);
	public void deleteFacturaKilometraje(FacturaKilometraje facturaKilometraje);
	public void deleteFacturaKilometrajeByIdSolicitud(Integer idSolicitud);


}