package com.lowes.service;

import java.util.List;

import com.lowes.entity.ComprobacionDeposito;

public interface ComprobacionDepositoService {
	
	public Integer createComprobacionDeposito(ComprobacionDeposito comprobacionDeposito);
    public ComprobacionDeposito updateComprobacionDeposito(ComprobacionDeposito comprobacionDeposito);
    public void deleteComprobacionDeposito(Integer idComprobacionDeposito);
    public List<ComprobacionDeposito> getAllComprobacionDeposito();
	public List<ComprobacionDeposito> getAllComprobacionDepositoBySolicitud(Integer idSolicitud);
	public ComprobacionDeposito getComprobacionDeposito(Integer idDocumento);
    
}
