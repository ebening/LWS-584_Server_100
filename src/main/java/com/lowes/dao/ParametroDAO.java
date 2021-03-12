package com.lowes.dao;

import java.util.List;

import com.lowes.entity.Parametro;

public interface ParametroDAO {
	
	public Integer createParametro(Parametro parametro);
    public Parametro updateParametro(Parametro parametro);
    public void deleteParametro(Integer id);
    public List<Parametro> getAllParametro();
    public List<Parametro> getAllParametro(String query);
    public List<Parametro> getAllParametroEditable();
    public Parametro getParametro(Integer id);
    public Parametro getParametroByName(String parametro);
    public List<Parametro> getParametrosByName(String parametro);
}