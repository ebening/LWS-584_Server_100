package com.lowes.dao;

import java.util.List;

import com.lowes.entity.Moneda;

public interface MonedaDAO {
	
	public Integer createMoneda(Moneda moneda);
    public Moneda updateMoneda(Moneda moneda);
    public void deleteMoneda(Integer id);
    public List<Moneda> getAllMoneda();
    public Moneda getMoneda(Integer id);	
    public List<Moneda> getAllMonedas();
}