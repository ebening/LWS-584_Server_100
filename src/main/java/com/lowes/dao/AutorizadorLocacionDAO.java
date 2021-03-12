package com.lowes.dao;

import java.util.List;

import com.lowes.entity.AutorizadorLocacion;

public interface AutorizadorLocacionDAO {
	
	public Integer createAutorizadorLocacion(AutorizadorLocacion autorizadorLocacion);
    public AutorizadorLocacion updateAutorizadorLocacion(AutorizadorLocacion autorizadorLocacion);
    public void deleteAutorizadorLocacion(Integer idAutorizadorLocacion);
    public List<AutorizadorLocacion> getAllAutorizadorLocacion();
    public AutorizadorLocacion getAutorizadorLocacion(Integer idAutorizadorLocacion);
    public AutorizadorLocacion getAutorizadorLocacionByLocacionNivelAutoriza(Integer idLocacion, Integer idNivelAutoriza);
}
