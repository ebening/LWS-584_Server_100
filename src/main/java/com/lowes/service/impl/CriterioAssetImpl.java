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
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoCriterio;
import com.lowes.entity.Usuario;
import com.lowes.service.CriterioService;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.EmailService;

@Configuration
@Service
@Transactional
public class CriterioAssetImpl implements CriterioService {
	
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
	private EmailService emailService;
	
	@Autowired
	private ParametroService parametroService;
	
	private String ultimoAutorizador;
	private Usuario usuarioFiguraContable;
	
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
				
		//Se valida si la solicitud es marcada como asset
		if (solicitud.getTrackAsset() == Short.parseShort("1")){
			//Se valida si existe una figura contable
			String idUsuarioFiguraContable = parametroService.getParametroByName("usuarioFiguraContable").getValor();
			Usuario usuarioFiguraContable = usuarioService.getUsuario(Integer.parseInt(idUsuarioFiguraContable));
			this.setUsuarioFiguraContable(usuarioFiguraContable);
			
			//if (usuarioFiguraContable != null){
				if (usuarioFiguraContable != null){
				
				//Marcar paso anterior como "no actual"
				List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());
				
				for(SolicitudAutorizacion sa : solicitudAutorizacionAnterior){
					sa.setUltimoMovimiento(Etiquetas.CERO_S);
					solicitudAutorizacionService.updateSolicitudAutorizacion(sa);
				}
				
				logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: " + usuarioFiguraContable.getIdUsuario());
				
				if(this.yaAutorizo(solicitud, usuarioFiguraContable)){
					
					/* SI YA AUTORIZO PREVIAMENTE ESTE USUARIO, YA NO SE ENVIA A VALIDAR AL USUARIO: INTERNAMENTE SE AUTORIZA */
					
				    this.crearSolicitudConfirmada(solicitud, usuarioFiguraContable, usuario);
					
					return false;
					
				}else{
					
					/*SI EL AUTORIZADOR ES EL USUARIO QUE REGISTRO LA SOLICITUD TAMBIEN SE OMITE (SE CONFIRMA AUTOMATICAMENTE)*/
					if(autorizadorEsSolicitanteCreador(usuarioFiguraContable, solicitud)){
					    this.crearSolicitudConfirmada(solicitud, usuarioFiguraContable, usuario);
					    return false;
					}else{
						/* SI ESTE USUARIO NO A AUTORIZADO PREVIAMENTE Y SI NO ES EL CREADOR DE LA SOLICITUD SE ENVIA AL USUARIO PARA SU AUTORIZACIÓN */

						SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO
								, estadoAutorizacionService.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR) //1
								, estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR) //2
								, solicitud
								, tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_ACTIVO_FIJO) //2
								, usuarioFiguraContable
								, usuario
								, Etiquetas.UNO_S
								, Etiquetas.UNO_S
								, new Date()
								, usuario.getIdUsuario());
						
						solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);
						
						return true;
					}

				}
			
			} else {
				logger.info("El criterio track as asset no se cumple debido a que no existe una figura contable.");
				return false;
			}
		} else {
			logger.info("El criterio track as asset no se cumple.");
			return false;
		}
		
	}

	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {
		return true;
	}

	@Override
	public void enviarCorreo(Solicitud solicitud) {
		logger.info("enviar correo CriterioActivoFijo");
		// Notificación email
		
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA){
			this.setUltimoAutorizador(null);
		}

		// Verificar en base de datos si el envío de notificaciones está activo
		Integer envioCorreo = Integer
				.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());

		if (envioCorreo == Etiquetas.UNO) {
			try {
				logger.info("Enviando correo a: " + this.getUsuarioFiguraContable().getNumeroNombreCompletoUsuario()
						+ " - Solicitud: " + solicitud.getIdSolicitud());
				emailService.enviarCorreo(Etiquetas.CRITERIO_DOS, this.getUltimoAutorizador(),
						this.getUsuarioFiguraContable().getCorreoElectronico(), solicitud);
				
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

	public Usuario getUsuarioFiguraContable() {
		return usuarioFiguraContable;
	}

	public void setUsuarioFiguraContable(Usuario usuarioFiguraContable) {
		this.usuarioFiguraContable = usuarioFiguraContable;
	}

	@Override
	public boolean crearSolicitudConfirmada(Solicitud solicitud, Usuario usuarioAutoriza, Usuario usuarioSolicita) {

		SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion();
		
		//setters para la autorizacion automatica.
		
		solicitudAutorizacion.setSolicitud(solicitud);
		solicitudAutorizacion.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR));
		solicitudAutorizacion.setTipoCriterio(new TipoCriterio(Etiquetas.TIPO_CRITERIO_ACTIVO_FIJO));
		solicitudAutorizacion.setEstadoAutorizacion(new EstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO));
		solicitudAutorizacion.setUsuarioByIdUsuarioEnvia(usuarioSolicita);
		solicitudAutorizacion.setUsuarioByIdUsuarioAutoriza(usuarioAutoriza);
		solicitudAutorizacion.setUltimoMovimiento(Etiquetas.CERO_S);  // revisar comportamiento de ultimo movimiento
		solicitudAutorizacion.setFechaAutoriza(new Date());
		
		/*campos de control de tabla*/
		
		solicitudAutorizacion.setActivo(Etiquetas.UNO_S);
		solicitudAutorizacion.setCreacionFecha(new Date());
		solicitudAutorizacion.setCreacionUsuario(usuarioService.getUsuarioSesion().getIdUsuario());
		solicitudAutorizacion.setVisible(Etiquetas.CERO_S);
		
		Integer idSolAut = solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);
		
		if(idSolAut != null && idSolAut > 0){
			return true;
		}else{
			return false;
		}
		
	}
	
	@Override
	public boolean yaAutorizo(Solicitud solicitud, Usuario usuario) {
		
		Integer autorizacionesPorUsuario = solicitudAutorizacionService.getCountAllByUsuarioAutorizo(solicitud.getIdSolicitud(), usuario.getIdUsuario());
		
		if(autorizacionesPorUsuario > 0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean autorizadorEsSolicitanteCreador(Usuario autorizador, Solicitud sol) {
		return sol.getUsuarioByIdUsuario().getIdUsuario() == autorizador.getIdUsuario();
	}
	
}