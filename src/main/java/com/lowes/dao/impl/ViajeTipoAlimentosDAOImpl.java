package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.ViajeTipoAlimentosDAO;
import com.lowes.entity.ViajeTipoAlimentos;
import com.lowes.util.HibernateUtil;

@Repository
public class ViajeTipoAlimentosDAOImpl implements ViajeTipoAlimentosDAO{
	
	public ViajeTipoAlimentosDAOImpl(){
		System.out.println("ViajeTipoAlimentosDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos) {
		return (Integer) hibernateUtil.create(viajeTipoAlimentos);
	}

	@Override
	public ViajeTipoAlimentos updateViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos) {	
		return hibernateUtil.update(viajeTipoAlimentos);
	}

	@Override
	public void deleteViajeTipoAlimentos(Integer idViajeTipoAlimentos) {
		hibernateUtil.delete(getViajeTipoAlimentos(idViajeTipoAlimentos));
	}

	@Override
	public List<ViajeTipoAlimentos> getAllViajeTipoAlimentos() {
		return hibernateUtil.fetchAll(ViajeTipoAlimentos.class);
	}

	@Override
	public ViajeTipoAlimentos getViajeTipoAlimentos(Integer idViajeTipoAlimentos) {
		return hibernateUtil.fetchById(idViajeTipoAlimentos,ViajeTipoAlimentos.class);
	}

}