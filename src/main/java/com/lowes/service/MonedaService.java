package com.lowes.service;

import java.util.List;

import com.lowes.entity.Moneda;

public interface MonedaService {
	
	public Integer createMoneda(Moneda moneda);
    public Moneda updateMoneda(Moneda moneda);
    public void deleteMoneda(Integer id);
    public List<Moneda> getAllMoneda();
    public Moneda getMoneda(Integer id);
    public List<Moneda> getAllMonedas();

}