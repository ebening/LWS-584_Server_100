package com.lowes.service;

import java.util.List;

import com.lowes.entity.ComprobacionAnticipoFactura;

public interface ComprobacionAnticipoFacturaService {
	
	public Integer createComprobacionAnticipoFactura(ComprobacionAnticipoFactura comprobacionAnticipoFactura);
    public ComprobacionAnticipoFactura updateComprobacionAnticipoFactura(ComprobacionAnticipoFactura comprobacionAnticipoFactura);
    public void deleteComprobacionAnticipoFactura(Integer idComprobacionAnticipoFactura);
    public List<ComprobacionAnticipoFactura> getAllComprobacionAnticipoFactura();
	public List<ComprobacionAnticipoFactura> getComprobacionByIdAnticipo(Integer idAnticipo);
	public void deleteBySolicitudComprobacion(Integer idSolicitudComprobacion);

}
