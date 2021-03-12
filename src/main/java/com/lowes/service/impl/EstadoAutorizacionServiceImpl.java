package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.EstadoAutorizacionDAO;
import com.lowes.entity.EstadoAutorizacion;
import com.lowes.service.EstadoAutorizacionService;


@Service
@Transactional
public class EstadoAutorizacionServiceImpl implements EstadoAutorizacionService {
	
	@Autowired
	private EstadoAutorizacionDAO estadoAutorizacionDAO;

	@Override
	public List<EstadoAutorizacion> getAllEstadoAutorizacion() {
		return estadoAutorizacionDAO.getAllEstadoAutorizacion();
	}

	@Override
	public EstadoAutorizacion getEstadoAutorizacion(Integer idEstadoAutorizacion) {
		return estadoAutorizacionDAO.getEstadoAutorizacion(idEstadoAutorizacion);
	}
}