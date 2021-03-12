package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.EstadoAutorizacionDAO;
import com.lowes.entity.EstadoAutorizacion;
import com.lowes.util.HibernateUtil;

@Repository
public class EstadoAutorizacionDAOImpl implements EstadoAutorizacionDAO {
	
	public EstadoAutorizacionDAOImpl() {
		System.out.println("EstadoAutorizacionDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public List<EstadoAutorizacion> getAllEstadoAutorizacion() {
		return hibernateUtil.fetchAll(EstadoAutorizacion.class);
	}

	@Override
	public EstadoAutorizacion getEstadoAutorizacion(Integer idEstadoAutorizacion) {
		return hibernateUtil.fetchById(idEstadoAutorizacion, EstadoAutorizacion.class);
	}

}
