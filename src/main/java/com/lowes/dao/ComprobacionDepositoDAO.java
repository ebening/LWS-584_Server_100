package com.lowes.dao;

import java.util.List;

import com.lowes.entity.ComprobacionDeposito;

public interface ComprobacionDepositoDAO {
	
	public Integer createComprobacionDeposito(ComprobacionDeposito comprobacionDeposito);
    public ComprobacionDeposito updateComprobacionDeposito(ComprobacionDeposito comprobacionDeposito);
    public void deleteComprobacionDeposito(Integer idComprobacionDeposito);
    public List<ComprobacionDeposito> getAllComprobacionDeposito();
	public List<ComprobacionDeposito> getAllComprobacionDepositoBySolicitud(Integer idSolicitud);
	public ComprobacionDeposito getComprobacionDeposito(Integer idDocumento);
}