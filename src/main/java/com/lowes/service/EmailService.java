package com.lowes.service;

import com.lowes.entity.Notificacion;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;

public interface EmailService {
	
	public boolean enviarCorreoRetraso(String destinatario, Notificacion data);
	
	public boolean enviarCorreo(Integer criterio, String autorizador, String destinatario, Solicitud solicitud);

	public boolean enviarCorreoRechazo(Usuario rechazadoPor, Solicitud solicitud, String motivoRechazo);
}