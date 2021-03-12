package com.lowes.dao;

import java.util.List;

import com.lowes.entity.AidConfiguracion;

public interface AidConfiguracionDAO {
	
	public Integer createAidConfiguracion(AidConfiguracion aidConfiguracion);
    public AidConfiguracion updateAidConfiguracion(AidConfiguracion aidConfiguracion);
    public void deleteAidConfiguracion(Integer id);
    public List<AidConfiguracion> getAllAidConfiguracion();
    public AidConfiguracion getAidConfiguracion(Integer id);	

}