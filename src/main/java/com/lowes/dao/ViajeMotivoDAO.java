package com.lowes.dao;

import java.util.List;

import com.lowes.entity.ViajeMotivo;

public interface ViajeMotivoDAO {
	
	public Integer createViajeMotivo(ViajeMotivo viajeMotivo);
    public ViajeMotivo updateViajeMotivo(ViajeMotivo viajeMotivo);
    public void deleteViajeMotivo(Integer id);
    public List<ViajeMotivo> getAllViajeMotivo();
    public ViajeMotivo getViajeMotivo(Integer id);	

}