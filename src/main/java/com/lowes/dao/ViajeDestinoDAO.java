package com.lowes.dao;

import java.util.List;

import com.lowes.entity.ViajeDestino;

public interface ViajeDestinoDAO {
	
	public Integer createViajeDestino(ViajeDestino viajeDestino);
    public ViajeDestino updateViajeDestino(ViajeDestino viajeDestino);
    public void deleteViajeDestino(Integer id);
    public List<ViajeDestino> getAllViajeDestino();
    public ViajeDestino getViajeDestino(Integer id);	

}