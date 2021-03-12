package com.lowes.service;

import java.util.List;

import com.lowes.entity.KilometrajeUbicacion;

public interface KilometrajeUbicacionService {
	
	public Integer createKilometrajeUbicacion(KilometrajeUbicacion kilometrajeUbicacion);
    public KilometrajeUbicacion updateKilometrajeUbicacion(KilometrajeUbicacion kilometrajeUbicacion);
    public void deleteKilometrajeUbicacion(Integer id);
    public List<KilometrajeUbicacion> getAllKilometrajeUbicacion();
    public KilometrajeUbicacion getKilometrajeUbicacion(Integer id);	

}