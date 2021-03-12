package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.ComprobacionAnticipoFacturaDAO;
import com.lowes.entity.ComprobacionAnticipoFactura;
import com.lowes.service.ComprobacionAnticipoFacturaService;

@Service
@Transactional
public class ComprobacionAnticipoFacturaServiceImpl implements ComprobacionAnticipoFacturaService {
	
	@Autowired
	private ComprobacionAnticipoFacturaDAO comprobacionAnticipoFacturaDAO;
	
	@Override
	public Integer createComprobacionAnticipoFactura(ComprobacionAnticipoFactura comprobacionAnticipoFactura) {
		return comprobacionAnticipoFacturaDAO.createComprobacionAnticipoFactura(comprobacionAnticipoFactura);
	}

	@Override
	public ComprobacionAnticipoFactura updateComprobacionAnticipoFactura(
			ComprobacionAnticipoFactura comprobacionAnticipoFactura) {
		return comprobacionAnticipoFacturaDAO.updateComprobacionAnticipoFactura(comprobacionAnticipoFactura);
	}

	@Override
	public void deleteComprobacionAnticipoFactura(Integer idComprobacionAnticipoFactura) {
		comprobacionAnticipoFacturaDAO.deleteComprobacionAnticipoFactura(idComprobacionAnticipoFactura);
	}

	@Override
	public List<ComprobacionAnticipoFactura> getAllComprobacionAnticipoFactura() {
		return comprobacionAnticipoFacturaDAO.getAllComprobacionAnticipoFactura();
	}

	@Override
	public List<ComprobacionAnticipoFactura> getComprobacionByIdAnticipo(Integer idAnticipo) {
		return comprobacionAnticipoFacturaDAO.getComprobacionByIdAnticipo(idAnticipo);
	}
	
	@Override
	public void deleteBySolicitudComprobacion(Integer idSolicitudComprobacion) {
		comprobacionAnticipoFacturaDAO.deleteBySolicitudComprobacion(idSolicitudComprobacion);
	}

}
