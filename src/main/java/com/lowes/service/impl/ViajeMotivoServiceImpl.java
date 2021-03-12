package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ViajeMotivoDAO;
import com.lowes.entity.ViajeMotivo;
import com.lowes.service.ViajeMotivoService;

@Service
@Transactional
public class ViajeMotivoServiceImpl implements ViajeMotivoService{
	
	public ViajeMotivoServiceImpl(){
		System.out.println("ViajeMotivoServiceImpl()");
	}

	@Autowired
	private ViajeMotivoDAO viajeMotivoDAO;
	
	@Override
	public Integer createViajeMotivo(ViajeMotivo viajeMotivo) {
		return viajeMotivoDAO.createViajeMotivo(viajeMotivo);
	}

	@Override
	public ViajeMotivo updateViajeMotivo(ViajeMotivo viajeMotivo) {
		return viajeMotivoDAO.updateViajeMotivo(viajeMotivo);
	}

	@Override
	public void deleteViajeMotivo(Integer idViajeMotivo) {
		viajeMotivoDAO.deleteViajeMotivo(idViajeMotivo);
	}

	@Override
	public List<ViajeMotivo> getAllViajeMotivo() {
		return viajeMotivoDAO.getAllViajeMotivo();
	}

	@Override
	public ViajeMotivo getViajeMotivo(Integer idViajeMotivo) {
		return viajeMotivoDAO.getViajeMotivo(idViajeMotivo);
	}

}