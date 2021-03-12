package com.lowes.service.impl;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.EstadoAutorizacion;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoCriterio;
import com.lowes.service.CriterioService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.ParametroService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.service.EmailService;

@Configuration
@Service
@Transactional
public class CriterioSolicitanteImpl implements CriterioService {

	private static final Logger logger = Logger.getLogger(CriterioSolicitanteImpl.class);

	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;

	@Autowired
	private EstadoSolicitudService estadoSolicitudService;

	@Autowired
	private TipoCriterioService tipoCriterioService;

	@Autowired
	private EstadoAutorizacionService estadoAutorizacionService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private UsuarioService usuarioService;
	

	public CriterioSolicitanteImpl() {

	}

	private String ultimoAutorizador;
	
	@Override
	public boolean crearSolicitud(Solicitud solicitud, Usuario usuario) {
		
		// Obtener último autorizador
		String autorizador = "";
		this.setUltimoAutorizador(null);
		if (solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud()) != null
				&& solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud())
						.getUsuarioByIdUsuarioAutoriza() != null) {
			Usuario u = solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud())
					.getUsuarioByIdUsuarioAutoriza();
			autorizador = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno();
			this.setUltimoAutorizador(autorizador);
		}
		Usuario usuarioSolicitanteBeneficiario = null;
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
			usuarioSolicitanteBeneficiario = solicitud.getUsuarioByIdUsuarioAsesor();
		}else{
			usuarioSolicitanteBeneficiario = solicitud.getUsuarioByIdUsuarioSolicita();
		}
		
		
		// Se valida si el usuario que crea la solicitud es igual al usuario
		// solicitante.
		    //if (!solicitud.getUsuarioByIdUsuario().equals(solicitud.getUsuarioByIdUsuarioSolicita())) {
			if (usuarioSolicitanteBeneficiario != null && !solicitud.getUsuarioByIdUsuario().equals(usuarioSolicitanteBeneficiario)) {
//			if (false) {
			
			//Marcar paso anterior como "no actual"
			List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());
			
			for(SolicitudAutorizacion sa : solicitudAutorizacionAnterior){
				sa.setUltimoMovimiento(Etiquetas.CERO_S);
				solicitudAutorizacionService.updateSolicitudAutorizacion(sa);
			}
			
			
			
			//Asignando solicitud
			logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: "
					+ solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario());

			SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO,
					estadoAutorizacionService.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR), //1
					estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR), //2
					solicitud, 
					tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_VALIDACION_SOLICITANTE), //1
					usuarioSolicitanteBeneficiario,
					usuario, 
					Etiquetas.UNO_S, 
					Etiquetas.UNO_S, 
					new Date(),
					usuario.getIdUsuario());

			solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);

			return true;
			
		} else {
			logger.info("El criterio Solicitante no se cumple...");
			return false;
		}

	}

	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {
		return true;
	}
	
	@Override
	public void enviarCorreo(Solicitud solicitud){
		logger.info("enviar correo CriterioSolicitante");
		// Notificación email

		// Verificar en base de datos si el envío de notificaciones está activo
		Integer envioCorreo = Integer
				.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());

		if (envioCorreo == Etiquetas.UNO) {
			try {
				
				if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA){
					this.setUltimoAutorizador(null);
				}
				
				logger.info("Enviando correo a: " + solicitud.getUsuarioByIdUsuarioSolicita().getNumeroNombreCompletoUsuario()
					+ " - Solicitud: " + solicitud.getIdSolicitud());
				
				
				emailService.enviarCorreo(Etiquetas.CRITERIO_UNO, this.getUltimoAutorizador(),
						solicitud.getUsuarioByIdUsuarioSolicita().getCorreoElectronico(), solicitud);
				
			} catch (Exception e) {
				logger.info("El mensaje no pudo ser enviado");
			}
		}
	}

	public String getUltimoAutorizador() {
		return ultimoAutorizador;
	}

	public void setUltimoAutorizador(String ultimoAutorizador) {
		this.ultimoAutorizador = ultimoAutorizador;
	}
	
	/* como es el primer criterio y orden 1 no se ocupa validar si hay autorizadores repetidos.*/

	@Override
	public boolean crearSolicitudConfirmada(Solicitud solicitud, Usuario usuarioAutoriza, Usuario usuarioSolicita) {
      return false;
	}

	@Override
	public boolean yaAutorizo(Solicitud solicitud, Usuario usuario) {
	  return false;
	}

	@Override
	public boolean autorizadorEsSolicitanteCreador(Usuario autorizador, Solicitud sol) {
		return false;
	}
	
	

}
