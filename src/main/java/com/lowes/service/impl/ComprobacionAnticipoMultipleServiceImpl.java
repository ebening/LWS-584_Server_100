package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ComprobacionAnticipoMultipleDAO;
import com.lowes.entity.ComprobacionAnticipoMultiple;
import com.lowes.service.ComprobacionAnticipoMultipleService;

@Service
@Transactional
public class ComprobacionAnticipoMultipleServiceImpl implements ComprobacionAnticipoMultipleService {
	
	@Autowired
	private ComprobacionAnticipoMultipleDAO comprobacionAnticipoMultipleDAO;
	
	@Override
	public Integer createComprobacionAnticipoMultiple(ComprobacionAnticipoMultiple comprobacionAnticipoMultiple) {
		return comprobacionAnticipoMultipleDAO.createComprobacionAnticipoMultiple(comprobacionAnticipoMultiple);
	}

	@Override
	public ComprobacionAnticipoMultiple updateComprobacionAnticipoMultiple(
			ComprobacionAnticipoMultiple comprobacionAnticipoMultiple) {
		return comprobacionAnticipoMultipleDAO.updateComprobacionAnticipoMultiple(comprobacionAnticipoMultiple);
	}

	@Override
	public void deleteComprobacionAnticipoMultiple(Integer idComprobacionAnticipoMultiple) {
		comprobacionAnticipoMultipleDAO.deleteComprobacionAnticipoMultiple(idComprobacionAnticipoMultiple);
		
	}

	@Override
	public List<ComprobacionAnticipoMultiple> getAllComprobacionAnticipoMultiple() {
		return comprobacionAnticipoMultipleDAO.getAllComprobacionAnticipoMultiple();
	}

	@Override
	public List<ComprobacionAnticipoMultiple> getSolicitudByIdSolicitud(Integer idSolicitud) {
		return comprobacionAnticipoMultipleDAO.getSolicitudByIdSolicitud(idSolicitud);
	}

	@Override
	public ComprobacionAnticipoMultiple getSolicitudByIdSolicitudMultiple(Integer idSolicitudMultiple) {
		return comprobacionAnticipoMultipleDAO.getSolicitudByIdSolicitudMultiple(idSolicitudMultiple);
	}

}
