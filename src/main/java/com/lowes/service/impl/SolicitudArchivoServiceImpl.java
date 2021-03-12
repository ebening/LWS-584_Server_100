package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.SolicitudArchivoDAO;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.service.SolicitudArchivoService;

@Service
@Transactional
public class SolicitudArchivoServiceImpl implements SolicitudArchivoService {

	@Autowired
	private SolicitudArchivoDAO solicitudArchivoDAO;
		
	@Override
	public Integer createSolicitudArchivo(SolicitudArchivo solicitudArchivo) {
		return solicitudArchivoDAO.createSolicitudArchivo(solicitudArchivo);
	}

	@Override
	public SolicitudArchivo updateSolicitudArchivo(SolicitudArchivo solicitudArchivo) {
		return solicitudArchivoDAO.updateSolicitudArchivo(solicitudArchivo);
	}

	@Override
	public void deleteSolicitudArchivo(Integer idSolicitudArchivo) {
		solicitudArchivoDAO.deleteSolicitudArchivo(idSolicitudArchivo);
	}

	@Override
	public List<SolicitudArchivo> getAllSolicitudArchivo() {
		return solicitudArchivoDAO.getAllSolicitudArchivo();
	}

	@Override
	public SolicitudArchivo getSolicitudArchivo(Integer idSolicitudArchivo) {
		return solicitudArchivoDAO.getSolicitudArchivo(idSolicitudArchivo);
	}

	@Override
	public List<SolicitudArchivo> getAllSolicitudArchivoBySolicitud(Integer idSolicitud) {
		return solicitudArchivoDAO.getAllSolicitudArchivoBySolicitud(idSolicitud);
	}

}