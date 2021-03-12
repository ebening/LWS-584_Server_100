package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ViajeDestinoDAO;
import com.lowes.entity.ViajeDestino;
import com.lowes.service.ViajeDestinoService;

@Service
@Transactional
public class ViajeDestinoServiceImpl implements ViajeDestinoService{
	
	public ViajeDestinoServiceImpl(){
		System.out.println("ViajeDestinoServiceImpl()");
	}

	@Autowired
	private ViajeDestinoDAO viajeDestinoDAO;
	
	@Override
	public Integer createViajeDestino(ViajeDestino viajeDestino) {
		return viajeDestinoDAO.createViajeDestino(viajeDestino);
	}

	@Override
	public ViajeDestino updateViajeDestino(ViajeDestino viajeDestino) {
		return viajeDestinoDAO.updateViajeDestino(viajeDestino);
	}

	@Override
	public void deleteViajeDestino(Integer idViajeDestino) {
		viajeDestinoDAO.deleteViajeDestino(idViajeDestino);
	}

	@Override
	public List<ViajeDestino> getAllViajeDestino() {
		return viajeDestinoDAO.getAllViajeDestino();
	}

	@Override
	public ViajeDestino getViajeDestino(Integer idViajeDestino) {
		return viajeDestinoDAO.getViajeDestino(idViajeDestino);
	}

}