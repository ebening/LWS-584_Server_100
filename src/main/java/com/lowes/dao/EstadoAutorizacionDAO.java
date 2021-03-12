package com.lowes.dao;

import java.util.List;

import com.lowes.entity.EstadoAutorizacion;

public interface EstadoAutorizacionDAO {
	public List<EstadoAutorizacion> getAllEstadoAutorizacion();

	public EstadoAutorizacion getEstadoAutorizacion(Integer idEstadoAutorizacion);
}
