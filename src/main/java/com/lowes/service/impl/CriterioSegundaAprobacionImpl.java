package com.lowes.service.impl;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.Usuario;
import com.lowes.service.CriterioService;
import com.lowes.service.EmailService;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Configuration
@Service
@Transactional
public class CriterioSegundaAprobacionImpl implements CriterioService {
	
	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private EstadoAutorizacionService estadoAutorizacionService;
	@Autowired
	private TipoCriterioService tipoCriterioService;
	@Autowired
	private EstadoSolicitudService estadoSolicitudService;
	@Autowired
	private EmailService emailService;
	
	private String ultimoAutorizador;
	private Usuario usuarioAutorizador = null;
	private static final Logger logger = Logger.getLogger(SolicitudAutorizacionService.class);


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
		
		
		
		
		//si no tiene nivel se trata de un puesto menor igual a gerencia. entonces valida el director de la locacion
		if(solicitud.getUsuarioByIdUsuarioSolicita().getPuesto().getNivelAutoriza() == null){
			this.setUsuarioAutorizador(solicitud.getUsuarioByIdUsuarioSolicita().getLocacion().getDirector());
		}else{
		// si si tiene se trata de un puesto alto entonces va a validarlo el director.
			
			String puesto = parametroService.getParametroByName("puestoDirectorLowes").getValor();
			List<Usuario>  autorizadorDirector = usuarioService.getUsuariosByPuesto(Integer.parseInt(puesto));
			this.setUsuarioAutorizador(autorizadorDirector.get(0));
		}
		
		if(this.getUsuarioAutorizador() != null && solicitud.getSegundaAprobacion() != null && solicitud.getSegundaAprobacion() > Etiquetas.CERO_S){
			
			logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: "
					+ this.getUsuarioAutorizador().getIdUsuario());
			
			boolean respuesta = false;
			
			// constructor del  objeto solicitudAutorizacion
			SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO,
					estadoAutorizacionService.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR), //1
					estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA), //5
					solicitud,
					tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_SEGUNDA_APROBACION), //9
					this.getUsuarioAutorizador(), 
					usuario,
					Etiquetas.UNO_S, 
					Etiquetas.UNO_S, 
					new Date(), 
					usuario.getIdUsuario());
			
			Integer idSol = solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);
			
			if(idSol != null && idSol > Etiquetas.CERO){
				respuesta =  true;
			}
			
			return respuesta;
			
		}else{
			
			boolean esSegundaAprobacion = solicitud.getSegundaAprobacion() != null
					&& solicitud.getSegundaAprobacion() > Etiquetas.CERO_S;
					
			if(esSegundaAprobacion == false){
				return true;
			}else{
				logger.info("El criterio de segunda aprobacion no se cumple no hay autorizador.");
				return false;
			}		
		}
	}

	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {

		boolean esSegundaAprobacion = solicitud.getSegundaAprobacion() != null
				&& solicitud.getSegundaAprobacion() > Etiquetas.CERO_S;

		if (esSegundaAprobacion) {

			List<SolicitudAutorizacion> solAuts = solicitudAutorizacionService
					.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());

			for (SolicitudAutorizacion solAut : solAuts) {
				if ((solAut.getRechazado() == null || solAut.getRechazado() == 0)
						&& solAut.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_SEGUNDA_APROBACION) {
					// cuanto el estado de la autorizacion sea 2 osea
					// autorizado.
					if (solAut.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.DOS) {
						return true;
					} else {
						return false;
					}
				}
			}

			return false;
		} else {
			return true;
		}

	}

	
	@Override
	public void enviarCorreo(Solicitud solicitud) {
		logger.info("enviar correo segunda aprobacion");
		// Notificación email
		
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA){
			this.setUltimoAutorizador(null);
		}

		// Verificar en base de datos si el envío de notificaciones
		// está activo
		Integer envioCorreo = Integer.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());

		if (envioCorreo == Etiquetas.UNO) {
			try {
					logger.info("Enviando correo a: " + this.getUsuarioAutorizador().getNumeroNombreCompletoUsuario()
							+ " - Solicitud: " + solicitud.getIdSolicitud());
					emailService.enviarCorreo(Etiquetas.TIPO_CRITERIO_SEGUNDA_APROBACION, this.getUltimoAutorizador(), this.getUsuarioAutorizador().getCorreoElectronico(), solicitud);
				
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
		return sol.getUsuarioByIdUsuario().getIdUsuario() == autorizador.getIdUsuario();
	}

	public Usuario getUsuarioAutorizador() {
		return usuarioAutorizador;
	}

	public void setUsuarioAutorizador(Usuario usuarioAutorizador) {
		this.usuarioAutorizador = usuarioAutorizador;
	}
	
	
	

}
