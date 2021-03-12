package com.lowes.service;

import java.util.List;

import com.lowes.entity.ViajeTipoAlimentos;

public interface ViajeTipoAlimentosService {
	
	public Integer createViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos);
    public ViajeTipoAlimentos updateViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos);
    public void deleteViajeTipoAlimentos(Integer id);
    public List<ViajeTipoAlimentos> getAllViajeTipoAlimentos();
    public ViajeTipoAlimentos getViajeTipoAlimentos(Integer id);

}