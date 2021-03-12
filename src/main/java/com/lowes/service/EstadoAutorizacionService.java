package com.lowes.service;

import java.util.List;

import com.lowes.entity.EstadoAutorizacion;

public interface EstadoAutorizacionService {
	
    public List<EstadoAutorizacion> getAllEstadoAutorizacion();
    public EstadoAutorizacion getEstadoAutorizacion(Integer idEstadoAutorizacion);

}
