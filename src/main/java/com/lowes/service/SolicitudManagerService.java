package com.lowes.service;

import java.util.Map;

import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;

public interface SolicitudManagerService {
	
	public Map<Boolean,String> autorizarSolicitud(Solicitud solicitud, Usuario usuario);
	public Map<Boolean,String> cancelarSolicitud(Solicitud solicitud, Usuario usuario);
	public Map<Boolean,String> enviarSolicitud(Solicitud solicitud, Usuario usuario);
	public Map<Boolean,String> rechazarSolicitud(Solicitud solicitud, Usuario usuario, String motivoRechazo);
	public void crearNotificacion(Solicitud solicitud);
	
}
