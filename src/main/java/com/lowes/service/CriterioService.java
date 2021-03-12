package com.lowes.service;

import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;

/**
 * @author Javier Castillo
 *
 */
public interface CriterioService {
	
	public boolean crearSolicitud(Solicitud solicitud, Usuario usuario);
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario);
	public void enviarCorreo(Solicitud solicitud);
	public boolean crearSolicitudConfirmada(Solicitud solicitud, Usuario usuarioAutoriza, Usuario usuarioSolicita);
	public boolean yaAutorizo(Solicitud solicitud,Usuario usuario);
	public boolean autorizadorEsSolicitanteCreador(Usuario autorizador, Solicitud sol);

}