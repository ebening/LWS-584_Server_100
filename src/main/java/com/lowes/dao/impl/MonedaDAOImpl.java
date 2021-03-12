package com.lowes.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.MonedaDAO;
import com.lowes.entity.Moneda;
import com.lowes.util.HibernateUtil;

@Repository
public class MonedaDAOImpl implements MonedaDAO{
	
	public MonedaDAOImpl(){
		System.out.println("MonedaDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createMoneda(Moneda moneda) {
		return (Integer) hibernateUtil.create(moneda);
	}

	@Override
	public Moneda updateMoneda(Moneda moneda) {
		return hibernateUtil.update(moneda);
	}

	@Override
	public void deleteMoneda(Integer idMoneda) {
		Moneda moneda = getMoneda(idMoneda); // Obtener compa�ia por id y enviar el objeto para eliminar
		hibernateUtil.delete(moneda);
	}

	@Override
	public List<Moneda> getAllMoneda() {
		List<Moneda> list = new ArrayList<Moneda>();
		List<Moneda> original = hibernateUtil.fetchAll(Moneda.class);
        Map<String, Moneda> monedas = new HashMap<String, Moneda>();
        String idMoneda;
		for (Moneda moneda : original) {
			idMoneda = ""+moneda.getDescripcion().trim();
            if (!monedas.containsKey(idMoneda)) {
                monedas.put(idMoneda, moneda);
            }
		}
		
		for (String key : monedas.keySet()) {
			list.add(monedas.get(key));
        }
		
		return list;
	}

	@Override
	public Moneda getMoneda(Integer idMoneda) {
		return hibernateUtil.fetchById(idMoneda, Moneda.class);
	}

	@Override
	public List<Moneda> getAllMonedas() {
		return hibernateUtil.fetchAll(Moneda.class);
	}

}