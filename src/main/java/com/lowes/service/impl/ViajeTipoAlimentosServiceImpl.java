package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ViajeTipoAlimentosDAO;
import com.lowes.entity.ViajeTipoAlimentos;
import com.lowes.service.ViajeTipoAlimentosService;

@Service
@Transactional
public class ViajeTipoAlimentosServiceImpl implements ViajeTipoAlimentosService{
	
	public ViajeTipoAlimentosServiceImpl(){
		System.out.println("ViajeTipoAlimentosServiceImpl()");
	}

	@Autowired
	private ViajeTipoAlimentosDAO viajeTipoAlimentosDAO;
	
	@Override
	public Integer createViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos) {
		return viajeTipoAlimentosDAO.createViajeTipoAlimentos(viajeTipoAlimentos);
	}

	@Override
	public ViajeTipoAlimentos updateViajeTipoAlimentos(ViajeTipoAlimentos viajeTipoAlimentos) {
		return viajeTipoAlimentosDAO.updateViajeTipoAlimentos(viajeTipoAlimentos);
	}

	@Override
	public void deleteViajeTipoAlimentos(Integer idViajeTipoAlimentos) {
		viajeTipoAlimentosDAO.deleteViajeTipoAlimentos(idViajeTipoAlimentos);
	}

	@Override
	public List<ViajeTipoAlimentos> getAllViajeTipoAlimentos() {
		return viajeTipoAlimentosDAO.getAllViajeTipoAlimentos();
	}

	@Override
	public ViajeTipoAlimentos getViajeTipoAlimentos(Integer idViajeTipoAlimentos) {
		return viajeTipoAlimentosDAO.getViajeTipoAlimentos(idViajeTipoAlimentos);
	}

}