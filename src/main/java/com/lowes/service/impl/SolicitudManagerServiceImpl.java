package com.lowes.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoSolicitudCriterio;
import com.lowes.entity.TipoCriterio;
import com.lowes.entity.TipoNotificacion;
import com.lowes.entity.Usuario;
import com.lowes.controller.UtilController;
import com.lowes.entity.EstadoAutorizacion;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Notificacion;
import com.lowes.service.SolicitudManagerService;
import com.lowes.service.TipoSolicitudCriterioService;
import com.lowes.service.SolicitudService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.util.Etiquetas;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.NotificacionService;
import com.lowes.service.ParametroService;

@Service
@Transactional
public class SolicitudManagerServiceImpl implements SolicitudManagerService {

	private static final Logger logger = Logger.getLogger(SolicitudManagerServiceImpl.class);

	private Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private TipoSolicitudCriterioService tipoSolicitudCriterioService;

	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;

	@Autowired
	private EstadoSolicitudService estadoSolicitudService;

	@Autowired
	private EstadoAutorizacionService estadoAutorizacionService;

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private UtilController utilController;

	@Autowired
	private TipoCriterioService tipoCriterioService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private NotificacionService notificacionService;
	
	private List<TipoSolicitudCriterio> tipoSolicitudCriterios;

	public SolicitudManagerServiceImpl() {

	}

	@Override
	public Map<Boolean, String> autorizarSolicitud(Solicitud solicitud, Usuario usuario) {
		// Autorizar solicitud
		SolicitudAutorizacion solicitudAutorizacion = solicitudAutorizacionService
				.getSolicitudAutorizacionBySolicitudUsuario(solicitud.getIdSolicitud(), usuario.getIdUsuario());

		Map<Boolean, String> estadoAutorizarSolicitud = new HashMap<>();

		if (solicitudAutorizacion != null) {
			EstadoAutorizacion estadoAutorizacionAutorizada = estadoAutorizacionService
					.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO);
			solicitudAutorizacion.setEstadoAutorizacion(estadoAutorizacionAutorizada);
			solicitudAutorizacion.setModificacionFecha(new Date());
			solicitudAutorizacion.setFechaAutoriza(new Date());
			solicitudAutorizacion.setModificacionUsuario(usuario.getIdUsuario());
			
			solicitudAutorizacionService.updateSolicitudAutorizacion(solicitudAutorizacion);
			
			

			List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService
					.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());

			boolean completo = true;
			if (solicitudAutorizacion.getTipoCriterio().getIdTipoCriterio() != Etiquetas.TIPO_CRITERIO_VALIDACION_AP) {

				for (SolicitudAutorizacion sa : solicitudAutorizacionAnterior) {
					if (sa.getEstadoAutorizacion()
							.getIdEstadoAutorizacion() != Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO) {
						completo = false;
					}
				}
			}else{
				for(SolicitudAutorizacion solAut : solicitudAutorizacionAnterior){
					if(solAut.getUsuarioByIdUsuarioAutoriza().getIdUsuario() != usuario.getIdUsuario()){
					
						SolicitudAutorizacion solicitudAutorizacionAP = new SolicitudAutorizacion();
						solicitudAutorizacionAP = solAut;
						solicitudAutorizacionAP.setEstadoAutorizacion(estadoAutorizacionAutorizada);
						solicitudAutorizacionAP.setModificacionFecha(new Date());
						solicitudAutorizacionAP.setFechaAutoriza(new Date());
						solicitudAutorizacionAP.setModificacionUsuario(usuario.getIdUsuario());
						solicitudAutorizacionAP.setUltimoMovimiento((short) -1);
						
						solicitudAutorizacionService.updateSolicitudAutorizacion(solicitudAutorizacionAP);
					}
				}
			}
			
			logger.info("Solicitud " + solicitud.getIdSolicitud() + " autorizada por " + usuario.getIdUsuario());

			if (completo) {
				Map<Boolean, String> enviarSolicitud = enviarSolicitud(solicitud, usuario);
				if (!enviarSolicitud.containsKey(true)) {
					completo = false;
				}
			}

			if (completo) {
				estadoAutorizarSolicitud.put(true, etiqueta.AUTORIZACION_AUTORIZADA_ENVIADA);
			} else {
				estadoAutorizarSolicitud.put(true, etiqueta.AUTORIZACION_AUTORIZADA);
			}

			return estadoAutorizarSolicitud;
		} else {
			logger.info("La solicitud " + solicitud.getIdSolicitud() + " no fue autorizada.");

			estadoAutorizarSolicitud.put(false, etiqueta.AUTORIZACION_NO_AUTORIZADA);
			return estadoAutorizarSolicitud;
		}
	}

	@Override
	public Map<Boolean, String> cancelarSolicitud(Solicitud solicitud, Usuario usuario) {

		Map<Boolean, String> estadoAutorizarSolicitud = new HashMap<>();

		EstadoSolicitud estadoSolicitudCancelado = estadoSolicitudService.getEstadoSolicitud(7);
		EstadoSolicitud estadoSolicitudNueva = estadoSolicitudService.getEstadoSolicitud(1);

		// Solo se puede cancelar una solicitud nueva
		if (solicitud.getEstadoSolicitud().equals(estadoSolicitudNueva)) {
			solicitud.setEstadoSolicitud(estadoSolicitudCancelado);
			solicitud.setModificacionFecha(new Date());
			solicitud.setModificacionUsuario(usuario.getIdUsuario());

			solicitudService.updateSolicitud(solicitud);

			logger.info("Se ha cancelado la solicitud: " + solicitud.getIdSolicitud());

			estadoAutorizarSolicitud.put(true, etiqueta.SOLICITUD_CANCELADA);
			return estadoAutorizarSolicitud;
		} else {
			logger.info("La solicitud no puede eliminarse porque ya ha sido enviada a autorización.");

			estadoAutorizarSolicitud.put(false, etiqueta.SOLICITUD_NO_CANCELADA);
			return estadoAutorizarSolicitud;
		}

	}

	@Override
	public Map<Boolean, String> enviarSolicitud(Solicitud solicitud, Usuario usuario) {
		// Variable para verificar si se cumplió el siguiente paso.
		boolean creado = false;
		boolean ultimo = false;
		Map<Boolean, String> estadoEnvio = new HashMap<>();
		boolean tieneConfiguracion = false;
		TipoSolicitudCriterio tipoSolicitudCriterioActual = null;
		TipoCriterio criterioValidacionAP = tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_VALIDACION_AP);
		TipoCriterio criterioConfirmacionAP = tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_CONFIRMACION_AP);

		// Obtener lista de criterios
		tipoSolicitudCriterios = tipoSolicitudCriterioService.getTipoSolicitudCriteriosByTipoSolicitud(solicitud.getTipoSolicitud().getIdTipoSolicitud());

		SolicitudAutorizacion lastSolicitudAutorizacion = solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud());
		TipoSolicitudCriterio nextCriterio = null;
		TipoCriterio nextCriterioTmp = null;
		TipoCriterio lastCriterio = null;
		boolean pasoCompleto = false;
		
		//Si existe un criterio anterior, éste se tomará en cuenta para asignar el criterio siguiente
		if(lastSolicitudAutorizacion != null){
			lastCriterio = lastSolicitudAutorizacion.getTipoCriterio();
			nextCriterioTmp = lastCriterio;
			tieneConfiguracion = true;
		}
		
		for(TipoSolicitudCriterio tsc : tipoSolicitudCriterios){
			
			logger.info("criterio ciclo:" + tsc.getTipoCriterio().getTipoCriterio());
			
			if (!creado && (lastCriterio == null || solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_NUEVA || 
			solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA || nextCriterioTmp.equals(tsc.getTipoCriterio()))) {
				
				tipoSolicitudCriterioActual = tsc;
				
				if(lastCriterio == null || solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_NUEVA || 
						solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA){					
					nextCriterio = tsc;					
				} else {
					try {
						Class<?> nextCriterioClass = Class.forName(tsc.getTipoCriterio().getClase());
						Object nextCriterioInstance = appContext.getBean(Class.forName(tsc.getTipoCriterio().getClase()));
						logger.info("Clase: " + tsc.getTipoCriterio().getClase());
						Method nextCriterioCrearSolicitud = nextCriterioClass.getMethod("validarPasoCompleto", Solicitud.class, Usuario.class);
						
						pasoCompleto = (boolean)nextCriterioCrearSolicitud.invoke(nextCriterioInstance, solicitud, usuario);						
						
						if(pasoCompleto){						
//							Si el paso ya se completó para VendorRisk, se recorren los criterios hasta llegar a AP							
							if((solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML // original
									|| solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML)
									&& lastCriterio != null
									&& lastCriterio.getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VENDOR_RISK){
								for(TipoSolicitudCriterio tscLocal : tipoSolicitudCriterios){
									if(tscLocal.getTipoCriterio().equals(criterioValidacionAP)){
										nextCriterio = tscLocal;
									}
								}								
							}else
								nextCriterio = tipoSolicitudCriterioService.getNextTipoSolicitudCriterio(solicitud.getTipoSolicitud().getIdTipoSolicitud(), tsc.getOrden() + Etiquetas.UNO);
						} else {
							// Mientras el paso no esté completo, se sigue con el criterio actual
							nextCriterio = tipoSolicitudCriterioActual;
						}												
						
					} catch (Exception e) {
						logger.info("Error al invocar " + tsc.getTipoCriterio().getClase() + "." + "validarPasoCompleto");
						logger.error(e);
					}
				}
				
				//Si la solicitud es de no mercancías y el ultimo criterio es igual a vendor risk el siguiente criterio sera de validacion ap
				if (nextCriterio != null){
					if (!((solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML
												|| solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML)
												&& lastCriterio != null
												&& lastCriterio.getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VENDOR_RISK
												&& !nextCriterio.equals(criterioValidacionAP))){ // original
						logger.info("El siguiente criterio es: " + nextCriterio.getTipoCriterio().getTipoCriterio());
						
						// Se envía la solicitud al siguiente criterio para su
						// evaluación.
						try {
							
							//si el paso es AP y no tiene ninguna configuracion, no se envia a AP y se le envia un mensaje al usuario.
							if((tsc.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_CONFIRMACION_AP || 
									tsc.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VALIDACION_AP ) && tieneConfiguracion == false){
								estadoEnvio.put(false, parametroService.getParametroByName("msgSinAutorizadoresPrevioAP").getValor());
								break;
							}
							
							creado = nextCriterioCrearSolicitud(nextCriterio, solicitud, usuario);
							
							logger.info("creado: " + creado);
						} catch (Exception e) {
							logger.info("Error al invocar " + nextCriterio.getTipoCriterio().getClase() + "." + "crearSolicitud");
							logger.error(e);
						}						
					} else {
						try {
							Class<?> nextCriterioClass = Class.forName(lastCriterio.getClase());
							Object nextCriterioInstance = appContext.getBean(Class.forName(lastCriterio.getClase()));
							Method nextCriterioCrearSolicitud = nextCriterioClass.getMethod("validarPasoCompleto", Solicitud.class, Usuario.class);						
							boolean pasoCompletoVendorRisk = (boolean)nextCriterioCrearSolicitud.invoke(nextCriterioInstance, solicitud, usuario);

							if(nextCriterio.getTipoCriterio().getClase().equals(criterioValidacionAP.getClase()) || 
									(lastCriterio.getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VENDOR_RISK && pasoCompletoVendorRisk == false)){						
								creado = nextCriterioCrearSolicitud(nextCriterio, solicitud, usuario);
							}
							nextCriterioTmp = nextCriterio.getTipoCriterio(); // original
						}catch (Exception e) {
							logger.info("Error al invocar " + tsc.getTipoCriterio().getClase() + "." + "validarPasoCompleto");
							logger.error(e);
						}

					}
//					Se valida la creación se una nueva SolicitudAutorización
//					Si se creó, se actualiza el criterioActual
					if (creado) {
						tipoSolicitudCriterioActual = nextCriterio;
						// si entro y creo alguna solicitud_autorizacion se marca como que si tiene una configuracion
						// con esto se frenaria ir a los criterios de AP
						tieneConfiguracion = true;
						
						// validacion de autorizadores automaticos sin ninguno no automatico por delante.
						if ((tsc.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_MONTOS
								|| tsc.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VENDOR_RISK
								|| tsc.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE
								|| tsc.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_NIVELES_AUTORIZACION)
								&& nextCriterio.getTipoCriterio()
										.getIdTipoCriterio() != Etiquetas.TIPO_CRITERIO_VALIDACION_AP) {
							Class<?> nextCriterioClass;
							try {
								nextCriterioClass = Class.forName(nextCriterio.getTipoCriterio().getClase());
								Object nextCriterioInstance = appContext.getBean(Class.forName(nextCriterio.getTipoCriterio().getClase()));
								logger.info("Clase: " + nextCriterio.getTipoCriterio().getClase());
								Method nextCriterioCrearSolicitud = nextCriterioClass.getMethod("validarPasoCompleto", Solicitud.class, Usuario.class);
								boolean pasoCompletoAutomatico = (boolean)nextCriterioCrearSolicitud.invoke(nextCriterioInstance, solicitud, usuario);
								
								if(pasoCompletoAutomatico){
									nextCriterioTmp = tipoSolicitudCriterioService.getNextTipoSolicitudCriterio(solicitud.getTipoSolicitud().getIdTipoSolicitud(), tsc.getOrden() + Etiquetas.UNO).getTipoCriterio();
									creado = false;
								}
								
							}catch (Exception e) {
								logger.info("Error al invocar " + tsc.getTipoCriterio().getClase() + "." + "validarPasoCompleto");
								logger.error(e);
							}
						}
						
					} else {
						nextCriterioTmp = nextCriterio.getTipoCriterio();
					}
					
				} else if (lastCriterio != null){
					ultimo = true;
				}
			}
			
			
		}// fin for
		
		logger.info("Solicitud: " + solicitud.getIdSolicitud() + " - Tipo: "
				+ solicitud.getTipoSolicitud().getDescripcion());

    if(tieneConfiguracion){
		if (creado) {
			if (tipoSolicitudCriterioActual != null && tipoSolicitudCriterioActual.getTipoCriterio().equals(criterioValidacionAP)) {
				EstadoSolicitud estadoSolicitudPorAutorizar = estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA);
				solicitud.setEstadoSolicitud(estadoSolicitudPorAutorizar);
				solicitud.setModificacionFecha(new Date());
				solicitud.setModificacionUsuario(usuario.getIdUsuario());

				solicitudService.updateSolicitud(solicitud);
			} else if (tipoSolicitudCriterioActual != null && tipoSolicitudCriterioActual.getTipoCriterio().equals(criterioConfirmacionAP)) {
				EstadoSolicitud estadoSolicitudPorAutorizar = estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_CONFIRMAR);
				solicitud.setEstadoSolicitud(estadoSolicitudPorAutorizar);
				solicitud.setModificacionFecha(new Date());
				solicitud.setModificacionUsuario(usuario.getIdUsuario());

				solicitudService.updateSolicitud(solicitud);
			} else {

				EstadoSolicitud estadoSolicitudPorAutorizar = estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR);
				solicitud.setEstadoSolicitud(estadoSolicitudPorAutorizar);
				solicitud.setModificacionFecha(new Date());
				solicitud.setModificacionUsuario(usuario.getIdUsuario());

				solicitudService.updateSolicitud(solicitud);
			}

			estadoEnvio.put(true, etiqueta.AUTORIZACION_ENVIADA);
		} else if (ultimo) {
			
			//** Prueba para envio de notificacion... inicia 23/03/2016...
			crearNotificacion(solicitud);
			//** Fin de prueba
			
			EstadoSolicitud estadoSolicitudPorAutorizar = estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA);
			solicitud.setEstadoSolicitud(estadoSolicitudPorAutorizar);
			solicitud.setModificacionFecha(new Date());
			solicitud.setModificacionUsuario(usuario.getIdUsuario());
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_ANTICIPO 
				|| solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE)
				solicitud.setComprobada(Etiquetas.CERO);

			Solicitud solUpdate = solicitudService.updateSolicitud(solicitud);
			
			/*
			 * despues de que toma el estatus 5 de validada se exporta a EBS.
			 * */
			
			//IF COMPLETO ES IGUAL A TRUE
			//SE ENVÍA SOLICITUD A EBS (TABLAS DE EXPORTACIÓN)
			if(solUpdate.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.CINCO){
				
				boolean enviado = utilController.enviarEBS(solicitud, usuario);
				if(enviado){
					logger.info("Solicitud " + solicitud.getIdSolicitud() + " Enviada a EBS");
				}
			}
			
		} else {
			estadoEnvio.put(false, etiqueta.AUTORIZACION_NO_ENVIADA);
		}
		
    }
		return estadoEnvio;
	}

	@Override
	public Map<Boolean, String> rechazarSolicitud(Solicitud solicitud, Usuario usuario, String motivoRechazo) {

		// Rechazar solicitud
		SolicitudAutorizacion solicitudRechazo = solicitudAutorizacionService
				.getSolicitudAutorizacionBySolicitudUsuario(solicitud.getIdSolicitud(), usuario.getIdUsuario());

		Map<Boolean, String> estadoRechazarSolicitud = new HashMap<>();

		if (solicitudRechazo != null) {
			
			EstadoAutorizacion estadoAutorizacionRechazada = estadoAutorizacionService
					.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_RECHAZADO);
			EstadoSolicitud estadoSolicitudRechazada = estadoSolicitudService
					.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA);

			
			if(solicitudRechazo.getTipoCriterio().getIdTipoCriterio() != Etiquetas.TIPO_CRITERIO_VALIDACION_AP){
				// Rechazo para el usuario actual
				
					solicitud.setMotivoRechazo(motivoRechazo);
					solicitud.setEstadoSolicitud(estadoSolicitudRechazada);
					solicitud.setModificacionFecha(new Date());
					solicitud.setModificacionUsuario(usuario.getIdUsuario());
				
					solicitudRechazo.setEstadoSolicitud(estadoSolicitudRechazada);
					solicitudRechazo.setEstadoAutorizacion(estadoAutorizacionRechazada);
					solicitudRechazo.setFechaAutoriza(new Date());
					solicitudRechazo.setModificacionFecha(new Date());
					solicitudRechazo.setModificacionUsuario(usuario.getIdUsuario());
					solicitudRechazo.setSolicitud(solicitud);
					solicitudRechazo.setMotivoRechazo(motivoRechazo);

					solicitudAutorizacionService.updateSolicitudAutorizacion(solicitudRechazo);
					
				
				    solicitudService.updateSolicitud(solicitud);

					/*
					 * marcar columna de rechazado, para controlar el proceso de
					 * repetidos y orden
					 */
					List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService
							.getAllSolicitudAutorizacionBySolicitud(solicitud.getIdSolicitud());

					for (SolicitudAutorizacion solAut : solicitudAutorizacionAnterior) {
						SolicitudAutorizacion s = solAut;
						s.setRechazado(Etiquetas.UNO_S);
						solicitudAutorizacionService.updateSolicitudAutorizacion(s);
					}
				
				

			}else{
				// si se rechaza por AP entonces hay que marcar a todos lo autorizadores como rechazado.
				
				List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService
						.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());
				
				solicitud.setMotivoRechazo(motivoRechazo);
				solicitud.setEstadoSolicitud(estadoSolicitudRechazada);
				solicitud.setModificacionFecha(new Date());
				solicitud.setModificacionUsuario(usuario.getIdUsuario());

				
				for(SolicitudAutorizacion solAut : solicitudAutorizacionAnterior){
					    
						solicitudRechazo = solAut;
						solicitudRechazo.setEstadoSolicitud(estadoSolicitudRechazada);
						solicitudRechazo.setEstadoAutorizacion(estadoAutorizacionRechazada);
						solicitudRechazo.setFechaAutoriza(new Date());
						solicitudRechazo.setModificacionFecha(new Date());
						solicitudRechazo.setModificacionUsuario(usuario.getIdUsuario());
						solicitudRechazo.setSolicitud(solicitud);
						solicitudRechazo.setMotivoRechazo(motivoRechazo);
						
						if(solAut.getUsuarioByIdUsuarioAutoriza().getIdUsuario() != usuario.getIdUsuario()){
							solicitudRechazo.setUltimoMovimiento((short) -1);
						}
						
						solicitudAutorizacionService.updateSolicitudAutorizacion(solicitudRechazo);
				}
				
				/* marcar columna de rechazado, para controlar el proceso de repetidos y orden */
				List<SolicitudAutorizacion> solicitudAutorizacionAnteriorTodos = solicitudAutorizacionService
						.getAllSolicitudAutorizacionBySolicitud(solicitud.getIdSolicitud());
				
				for(SolicitudAutorizacion solAut : solicitudAutorizacionAnteriorTodos){
					SolicitudAutorizacion s = solAut;
					s.setRechazado(Etiquetas.UNO_S);
					solicitudAutorizacionService.updateSolicitudAutorizacion(s);
				}
				
				solicitudService.updateSolicitud(solicitud);

			}
			
			logger.info("Solicitud " + solicitud.getIdSolicitud() + " rechazada por " + usuario.getIdUsuario());
			estadoRechazarSolicitud.put(true, etiqueta.AUTORIZACION_RECHAZADA);
			return estadoRechazarSolicitud;
			
		}else{
			logger.info("La solicitud " + solicitud.getIdSolicitud() + " no fue rechazada.");

			estadoRechazarSolicitud.put(false, etiqueta.AUTORIZACION_NO_RECHAZADA);
			return estadoRechazarSolicitud;
		}

	}
	
	@Override
	public void crearNotificacion(Solicitud solicitud) {

		Notificacion notificacion = new Notificacion();

		notificacion.setEstatus("N");
		notificacion.setFecha(solicitud.getCreacionFecha());
		notificacion.setNumNotificacion(Etiquetas.CERO);

		int antiguedad = Integer.parseInt(parametroService.getParametroByName("anticipoDiasAntiguedad1").getValor());

		Calendar proximoEnvio = Calendar.getInstance();

		proximoEnvio.setTime(notificacion.getFecha()); // Configuramos la fecha
		// que se recibe

		proximoEnvio.add(Calendar.DAY_OF_YEAR, antiguedad); // numero de días a
		// añadir, o restar
		// en caso de días<0

		notificacion.setProximoEnvio(proximoEnvio.getTime());
		notificacion.setSolicitud(solicitud);

		TipoNotificacion tipoNot = new TipoNotificacion();

		tipoNot.setIdTipoNotificacion(Etiquetas.UNO);

		notificacion.setTipoNotificacion(tipoNot);
		notificacion.setUsuario(solicitud.getUsuarioByIdUsuarioSolicita());

		notificacionService.createNotificacion(notificacion);

	}
	
	private boolean nextCriterioCrearSolicitud(TipoSolicitudCriterio nextCriterio, Solicitud solicitud, Usuario usuario){
		boolean creado = false;
		try {
			Class<?> nextCriterioClass = Class.forName(nextCriterio.getTipoCriterio().getClase());
			Object nextCriterioInstance = appContext
					.getBean(Class.forName(nextCriterio.getTipoCriterio().getClase()));
			Method nextCriterioCrearSolicitud = nextCriterioClass.getMethod("crearSolicitud",
					Solicitud.class, Usuario.class);
			creado = (boolean) nextCriterioCrearSolicitud.invoke(nextCriterioInstance, solicitud, usuario);
		} catch (Exception e) {
			logger.info("Error al invocar " + nextCriterio.getTipoCriterio().getClase() + "." + "crearSolicitud");
			logger.error(e);
		}
		
		//Si se creó la solicitud, se envía correo 
		if (creado) {
			try {
				Class<?> nextCriterioClass;
				nextCriterioClass = Class.forName(nextCriterio.getTipoCriterio().getClase());
				Object nextCriterioInstance = appContext
						.getBean(Class.forName(nextCriterio.getTipoCriterio().getClase()));
				Method nextCriterioEnviarCorreo = nextCriterioClass.getMethod("enviarCorreo", Solicitud.class);
				nextCriterioEnviarCorreo.invoke(nextCriterioInstance, solicitud);
			} catch (Exception e) {
				logger.info("Error al invocar " + nextCriterio.getTipoCriterio().getClase() + "." + "enviarCorreo");
				logger.error(e);
			}
		}
		
		return creado;
	}

}