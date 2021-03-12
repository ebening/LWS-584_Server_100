package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.SolicitudFechaPagoEbsDAO;
import com.lowes.entity.SolicitudFechaPagoEbs;
import com.lowes.service.SolicitudFechaPagoEbsService;

@Service
@Transactional
public class SolicitudFechaPagoEbsServiceImpl implements SolicitudFechaPagoEbsService{
	
	@Autowired
	private SolicitudFechaPagoEbsDAO solicitudFechaPagoEbsDAO;

	@Override
	public Integer createSolicitudFechaPagoEbs(SolicitudFechaPagoEbs solicitudFechaPagoEbs) {
		return solicitudFechaPagoEbsDAO.createSolicitudFechaPagoEbs(solicitudFechaPagoEbs);
	}

	@Override
	public SolicitudFechaPagoEbs updateSolicitudFechaPagoEbs(SolicitudFechaPagoEbs solicitudFechaPagoEbs) {
		return solicitudFechaPagoEbsDAO.updateSolicitudFechaPagoEbs(solicitudFechaPagoEbs);
	}

	@Override
	public void deleteSolicitudFechaPagoEbs(Integer id) {
		solicitudFechaPagoEbsDAO.deleteSolicitudFechaPagoEbs(id);
	}

	@Override
	public List<SolicitudFechaPagoEbs> getAllSolicitudFechaPagoEbs() {
		return solicitudFechaPagoEbsDAO.getAllSolicitudFechaPagoEbs();
	}

	@Override
	public SolicitudFechaPagoEbs getSolicitudFechaPagoEbs(Integer id) {
		return solicitudFechaPagoEbsDAO.getSolicitudFechaPagoEbs(id);
	}

	@Override
	public List<SolicitudFechaPagoEbs> getSolicitudFechaPagoEbsNoActualizadas() {
		return solicitudFechaPagoEbsDAO.getSolicitudFechaPagoEbsNoActualizadas();
	}

}
