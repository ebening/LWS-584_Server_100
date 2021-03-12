package com.lowes.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.Usuario;
import com.lowes.service.CriterioService;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.SolicitudService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.service.ParametroService;
import com.lowes.service.EmailService;

@Configuration
@Service
@Transactional
public class CriterioAPImpl implements CriterioService {

	private static final Logger logger = Logger.getLogger(CriterioSolicitanteImpl.class);

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private EstadoSolicitudService estadoSolicitudService;

	@Autowired
	private EstadoAutorizacionService estadoAutorizacionService;

	@Autowired
	private TipoCriterioService tipoCriterioService;

	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;

	@Autowired
	private ParametroService parametroService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SolicitudService solicitudService;

	@Override
	public boolean crearSolicitud(Solicitud solicitud, Usuario usuario) {
		// Validar si existen usuarios con puesto de AP.

		Integer puestoAPParam = Integer
				.parseInt(parametroService.getParametroByName("puestoAutorizacionAP").getValor());

		List<Usuario> usuariosAP = usuarioService.getUsuariosByPuesto(puestoAPParam);
		
		// Obtener último autorizador
		String autorizador = "";
		if (solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud()) != null
				&& solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud())
						.getUsuarioByIdUsuarioAutoriza() != null) {
			Usuario u = solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud())
					.getUsuarioByIdUsuarioAutoriza();
			autorizador = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno();
		}

		if (!usuariosAP.isEmpty()) {

			// Marcar paso anterior como "no actual"
			List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService
					.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());

			for (SolicitudAutorizacion sa : solicitudAutorizacionAnterior) {
				sa.setUltimoMovimiento(Etiquetas.CERO_S);
				solicitudAutorizacionService.updateSolicitudAutorizacion(sa);
			}

			//Usuario usuarioAP = usuariosAP.get(0);
            for(Usuario usuarioAP : usuariosAP ){
			logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: "
					+ usuarioAP.getIdUsuario());

				
				SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO,
						estadoAutorizacionService.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR), //1
						estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA), //3
						solicitud,
						tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_VALIDACION_AP), //8
						usuarioAP, 
						usuario,
						Etiquetas.UNO_S, 
						Etiquetas.UNO_S, 
						new Date(), 
						usuario.getIdUsuario());
	
				solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);
			
            }

			return true;
		} else {
			logger.info("El criterio de AP no se cumple.");
			return false;
		}
	}

	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {
		
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS){
			
			EstadoSolicitud estadoAP =  solicitudService.getSolicitud(solicitud.getIdSolicitud()).getEstadoSolicitud();
			
			/*VERIFICAR QUE LA SOLICITUD YA SE AUTORIZO ESTATUS: 3*/
			if(estadoAP.getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA){
				return true;
			}else{
				return false;
			}
			
		}else{
			return true;
		}
		
	}

	@Override
	public void enviarCorreo(Solicitud solicitud) {
		// No se envían notificaciones para el CriterioAP
	}

	@Override
	public boolean crearSolicitudConfirmada(Solicitud solicitud, Usuario usuarioAutoriza, Usuario usuarioSolicita) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean yaAutorizo(Solicitud solicitud, Usuario usuario) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean autorizadorEsSolicitanteCreador(Usuario autorizador, Solicitud sol) {
		return false;
	}

}