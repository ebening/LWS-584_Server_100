package com.lowes.dao;

import java.util.List;

import com.lowes.entity.ComprobacionAnticipoMultiple;

public interface ComprobacionAnticipoMultipleDAO {
	
	public Integer createComprobacionAnticipoMultiple(ComprobacionAnticipoMultiple comprobacionAnticipoMultiple);
    public ComprobacionAnticipoMultiple updateComprobacionAnticipoMultiple(ComprobacionAnticipoMultiple comprobacionAnticipoMultiple);
    public void deleteComprobacionAnticipoMultiple(Integer idComprobacionAnticipoMultiple);
    public List<ComprobacionAnticipoMultiple> getAllComprobacionAnticipoMultiple();
	public List<ComprobacionAnticipoMultiple> getSolicitudByIdSolicitud(Integer idSolicitud);
	public ComprobacionAnticipoMultiple getSolicitudByIdSolicitudMultiple(Integer idSolicitudMultiple);
}
