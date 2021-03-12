package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.MonedaDAO;
import com.lowes.entity.Moneda;
import com.lowes.service.MonedaService;

@Service
@Transactional
public class MonedaServiceImpl implements MonedaService{
	
	public MonedaServiceImpl(){
		System.out.println("MonedaServiceImpl()");
	}

	@Autowired
	private MonedaDAO monedaDAO;
	
	@Override
	public Integer createMoneda(Moneda moneda) {
		return monedaDAO.createMoneda(moneda);
	}

	@Override
	public Moneda updateMoneda(Moneda moneda) {
		return monedaDAO.updateMoneda(moneda);
	}

	@Override
	public void deleteMoneda(Integer idMoneda) {
		monedaDAO.deleteMoneda(idMoneda);
	}

	@Override
	public List<Moneda> getAllMoneda() {
		return monedaDAO.getAllMoneda();
	}

	@Override
	public Moneda getMoneda(Integer idMoneda) {
		return monedaDAO.getMoneda(idMoneda);
	}

	@Override
	public List<Moneda> getAllMonedas() {
		return monedaDAO.getAllMonedas();
	}

}