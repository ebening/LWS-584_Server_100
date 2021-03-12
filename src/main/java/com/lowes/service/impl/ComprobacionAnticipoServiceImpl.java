package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ComprobacionAnticipoDAO;
import com.lowes.entity.ComprobacionAnticipo;
import com.lowes.service.ComprobacionAnticipoService;

@Service
@Transactional
public class ComprobacionAnticipoServiceImpl implements ComprobacionAnticipoService {
	
	@Autowired
	private ComprobacionAnticipoDAO comprobacionAnticipoDAO;
	
	@Override
	public Integer createComprobacionAnticipo(ComprobacionAnticipo comprobacionAnticipo) {
		return comprobacionAnticipoDAO.createComprobacionAnticipo(comprobacionAnticipo);
	}

	@Override
	public ComprobacionAnticipo updateComprobacionAnticipo(ComprobacionAnticipo comprobacionAnticipo) {
		return comprobacionAnticipoDAO.updateComprobacionAnticipo(comprobacionAnticipo);
	}

	@Override
	public void deleteComprobacionAnticipo(Integer idComprobacionAnticipo) {
		comprobacionAnticipoDAO.deleteComprobacionAnticipo(idComprobacionAnticipo);
	}

	@Override
	public List<ComprobacionAnticipo> getAllComprobacionAnticipo() {
		return comprobacionAnticipoDAO.getAllComprobacionAnticipo();
	}

	@Override
	public List<ComprobacionAnticipo> getAnticiposByComprobacion(Integer idComprobacion) {
		return comprobacionAnticipoDAO.getAnticiposByComprobacion(idComprobacion);
	}

	@Override
	public ComprobacionAnticipo getComprobacionByAnticipo(Integer idAnticipo) {
		return comprobacionAnticipoDAO.getComprobacionByAnticipo(idAnticipo);
	}

}
