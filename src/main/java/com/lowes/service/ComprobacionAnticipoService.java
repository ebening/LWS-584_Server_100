package com.lowes.service;

import java.util.List;

import com.lowes.entity.ComprobacionAnticipo;

public interface ComprobacionAnticipoService {
	
	public Integer createComprobacionAnticipo(ComprobacionAnticipo comprobacionAnticipo);
    public ComprobacionAnticipo updateComprobacionAnticipo(ComprobacionAnticipo comprobacionAnticipo);
    public void deleteComprobacionAnticipo(Integer idComprobacionAnticipo);
    public List<ComprobacionAnticipo> getAllComprobacionAnticipo();
    public List<ComprobacionAnticipo> getAnticiposByComprobacion(Integer idComprobacion);
	public ComprobacionAnticipo getComprobacionByAnticipo(Integer idAnticipo);
    
}
