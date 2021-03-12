package com.lowes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.Moneda;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoSolicitud;
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
public class CriterioConfirmacionAPImpl implements CriterioService {
	
	
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;
	@Autowired
	private EstadoAutorizacionService estadoAutorizacionService;
	@Autowired
	private TipoCriterioService tipoCriterioService;
	@Autowired
	private EstadoSolicitudService estadoSolicitudService;
	
	@Autowired
	private EmailService emailService;
	
	private Usuario usuarioAutorizador;
	private String ultimoAutorizador;
	
	
	private static final Logger logger = Logger.getLogger(CriterioSolicitanteImpl.class);

	
	@Override
	public boolean crearSolicitud(Solicitud solicitud, Usuario usuario) {
		
		String autorizador = "";
		this.setUltimoAutorizador(null);
		
		List<SolicitudAutorizacion> solicitudAturoizacionAP = solicitudAutorizacionService.getAllAutorizadoresByCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_VALIDACION_AP);
		if(solicitudAturoizacionAP != null){
			
			for(SolicitudAutorizacion solAut : solicitudAturoizacionAP ){
				if((solAut.getRechazado() == null || solAut.getRechazado() != Etiquetas.UNO_S) && solAut.getUltimoMovimiento() != -1){
					this.setUltimoAutorizador(solAut.getUsuarioByIdUsuarioAutoriza().getNombreCompletoUsuario());
					break;
				}
			}
			
		}
		
	
		
		
		BigDecimal monto = solicitud.getMontoTotal();
		Moneda tipoMoneda = solicitud.getMoneda();
		TipoSolicitud tipoSolicitud = solicitud.getTipoSolicitud();
		Boolean respuesta = false;
		
		List<Usuario> usuariosAP = new ArrayList<>();
		
		if (tipoMoneda.getIdMoneda() == Etiquetas.ID_PESOS
//				&& tipoSolicitud.getMontoConfirmacionPesos()
//						.compareTo(BigDecimal.valueOf(Etiquetas.CERO)) == Etiquetas.UNO
				&& monto.compareTo(tipoSolicitud.getMontoConfirmacionPesos()) == Etiquetas.UNO) {
			
			Integer puestoAPParam = Integer
					.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
			usuariosAP = usuarioService.getUsuariosByPuesto(puestoAPParam);
			
		} else if (tipoMoneda.getIdMoneda() == Etiquetas.ID_DOLARES
//				&& tipoSolicitud.getMontoConfirmacionDolares()
//						.compareTo(BigDecimal.valueOf(Etiquetas.CERO)) == Etiquetas.UNO
				&& monto.compareTo(tipoSolicitud.getMontoConfirmacionDolares()) == Etiquetas.UNO) {
			
			Integer puestoAPParam = Integer
					.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
			usuariosAP = usuarioService.getUsuariosByPuesto(puestoAPParam);
			
		}
		
		if (!usuariosAP.isEmpty()) {
			
			// Marcar paso anterior como "no actual"
			List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService
					.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());

			for (SolicitudAutorizacion sa : solicitudAutorizacionAnterior) {
				sa.setUltimoMovimiento(Etiquetas.CERO_S);
				solicitudAutorizacionService.updateSolicitudAutorizacion(sa);
			}

			Usuario usuarioAP = usuariosAP.get(0);
			this.setUsuarioAutorizador(usuarioAP);
			                                                                                                                                                                                                                                                    
			logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: "
					+ usuarioAP.getIdUsuario());
			
			// constructor del  objeto solicitudAutorizacion
			SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO,
					estadoAutorizacionService.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR), //1
					estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA), //5
					solicitud,
					tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_CONFIRMACION_AP), //9
					usuarioAP, 
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
			logger.info("El criterio de confirmacion AP no se cumple.");
			return respuesta;
		}
	}

	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {
		return true;
	}

	@Override
	public void enviarCorreo(Solicitud solicitud) {
		
		logger.info("enviar correo Confirmacion AP");
		// Notificación email

		// Verificar en base de datos si el envío de notificaciones
		// está activo
		Integer envioCorreo = Integer.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());

		if (envioCorreo == Etiquetas.UNO) {
			try {
					logger.info("Enviando correo a: " + this.getUsuarioAutorizador().getNumeroNombreCompletoUsuario()
							+ " - Solicitud: " + solicitud.getIdSolicitud());
					emailService.enviarCorreo(Etiquetas.TIPO_CRITERIO_CONFIRMACION_AP, this.getUltimoAutorizador(), this.getUsuarioAutorizador().getCorreoElectronico(), solicitud);
					
				
			} catch (Exception e) {
				logger.info("El mensaje no pudo ser enviado");
			}
		}
		
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

	public String getUltimoAutorizador() {
		return ultimoAutorizador;
	}

	public void setUltimoAutorizador(String ultimoAutorizador) {
		this.ultimoAutorizador = ultimoAutorizador;
	}
	
	
	
	
	

}