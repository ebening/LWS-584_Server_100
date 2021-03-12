package com.lowes.service;

import java.util.List;

import com.lowes.entity.SolicitudFechaPagoEbs;

public interface SolicitudFechaPagoEbsService {
	
	public Integer createSolicitudFechaPagoEbs(SolicitudFechaPagoEbs solicitudFechaPagoEbs);
    public SolicitudFechaPagoEbs updateSolicitudFechaPagoEbs(SolicitudFechaPagoEbs solicitudFechaPagoEbs);
    public void deleteSolicitudFechaPagoEbs(Integer id);
    public List<SolicitudFechaPagoEbs> getAllSolicitudFechaPagoEbs();
    public SolicitudFechaPagoEbs getSolicitudFechaPagoEbs(Integer id);
    public List<SolicitudFechaPagoEbs> getSolicitudFechaPagoEbsNoActualizadas();

}