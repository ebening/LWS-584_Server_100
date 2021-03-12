package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.SolicitudAnticipoViajeConceptosDAO;
import com.lowes.entity.SolicitudAnticipoViajeConceptos;
import com.lowes.service.SolicitudAnticipoViajeConceptosService;

@Service
@Transactional
public class SolicitudAnticipoViajeConceptosServiceImpl implements SolicitudAnticipoViajeConceptosService{
	
	public SolicitudAnticipoViajeConceptosServiceImpl(){
		System.out.println("SolicitudAnticipoViajeConceptosServiceImpl()");
	}

	@Autowired
	private SolicitudAnticipoViajeConceptosDAO solicitudAnticipoViajeConceptosDAO;
	
	@Override
	public Integer createSolicitudAnticipoViajeConceptos(SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConceptos) {
		return solicitudAnticipoViajeConceptosDAO.createSolicitudAnticipoViajeConceptos(solicitudAnticipoViajeConceptos);
	}

	@Override
	public SolicitudAnticipoViajeConceptos updateSolicitudAnticipoViajeConceptos(SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConceptos) {
		return solicitudAnticipoViajeConceptosDAO.updateSolicitudAnticipoViajeConceptos(solicitudAnticipoViajeConceptos);
	}

	@Override
	public void deleteSolicitudAnticipoViajeConceptos(Integer idSolicitudAnticipoViajeConceptos) {
		solicitudAnticipoViajeConceptosDAO.deleteSolicitudAnticipoViajeConceptos(idSolicitudAnticipoViajeConceptos);
	}

	@Override
	public List<SolicitudAnticipoViajeConceptos> getAllSolicitudAnticipoViajeConceptos() {
		return solicitudAnticipoViajeConceptosDAO.getAllSolicitudAnticipoViajeConceptos();
	}

	@Override
	public SolicitudAnticipoViajeConceptos getSolicitudAnticipoViajeConceptos(Integer idSolicitudAnticipoViajeConceptos) {
		return solicitudAnticipoViajeConceptosDAO.getSolicitudAnticipoViajeConceptos(idSolicitudAnticipoViajeConceptos);
	}

	@Override
	public List<SolicitudAnticipoViajeConceptos> getAllSolicitudAnticipoViajeConceptoBySol(Integer idSolicitudAnticipoViaje) {
		return solicitudAnticipoViajeConceptosDAO.getAllSolicitudAnticipoViajeConceptoBySol(idSolicitudAnticipoViaje);

	}

}