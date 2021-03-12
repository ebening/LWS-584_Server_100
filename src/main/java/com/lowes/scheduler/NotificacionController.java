/**
 * 
 */
package com.lowes.scheduler;

import java.util.Calendar;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.lowes.entity.Notificacion;
import com.lowes.service.EmailService;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.NotificacionService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.util.CMConnection;
import com.lowes.util.Etiquetas;

/**
 * @author miguelr
 *
 */
@Controller
@Configuration
@EnableScheduling
public class NotificacionController {
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private FacturaArchivoService facturaArchivoService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	@Autowired
	private NotificacionService notificacionService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ParametroService parametroService;
	
	private static final Logger logger = Logger.getLogger(CMConnection.class);
	/**
	 * @author miguelr
	 * 
	 */
	@Scheduled(cron = "${cron.anticipo}")
	public void enviaNotificaciones(){
		logger.info("Notificaciones: Inicia env�o notificaciones por anticipos pendientes.");
		enviaAnticipoPendiente(Etiquetas.TIPO_NOTIFICACION_ANTIGUEDAD_ANTICIPO);
		enviaAnticipoViajePendiente(Etiquetas.TIPO_NOTIFICACION_ANTIGUEDAD_ANTICIPO_GASTOS_VIAJE);
	}
	
	/**
	 * @author miguelr
	 * @param tipoNotificacion
	 * ENV�A NOTIFICACIONES DIARIAS PROGRAMADAS DE ANTICIPOS CON RETRASO DE ACUERDO A LA TABLA DE NOTIFICACIONES.
	 */
	public void enviaAnticipoPendiente(int tipoNotificacion){
		List<Notificacion> lstNotificacion = notificacionService.getNotificacionesPendientesByTipo(tipoNotificacion);
		
		for(Notificacion not : lstNotificacion){
			//Si est� pendiente
			if(not.getSolicitud().getComprobada() == Etiquetas.CERO){
				if(emailService.enviarCorreoRetraso(not.getUsuario().getCorreoElectronico(), not)){
					int nextParam = not.getNumNotificacion() + 1;
					not.setNumNotificacion(nextParam);
					if(nextParam < Etiquetas.TRES){
						int antiguedad = Integer.parseInt(parametroService.getParametroByName("anticipoDiasAntiguedad" + (nextParam + 1)).getValor());
						Calendar proximoEnvio = Calendar.getInstance();
						proximoEnvio.setTime(not.getProximoEnvio()); // Configuramos la fecha que se recibe
						proximoEnvio.add(Calendar.DAY_OF_YEAR, antiguedad);  // numero de d�as a a�adir, o restar en caso de d�as<0
						not.setProximoEnvio(proximoEnvio.getTime());
						not.setEstatus("P");
					}						
					else
						not.setEstatus("E");
					notificacionService.updateNotificacion(not);					
				}
				else{
					not.setEstatus("W");
					notificacionService.updateNotificacion(not);
					logger.info("Notificaciones: La notificaci�n para la solicitud "+ not.getSolicitud().getIdSolicitud() +" no pudo ser enviada.");
				}
			}
			//Si est� comprobada
			else if(not.getSolicitud().getComprobada() == Etiquetas.UNO){
				not.setEstatus("A");
				notificacionService.updateNotificacion(not);
			}
			//Si error
			else if(not.getSolicitud().getComprobada() == null){
				not.setEstatus("W");
				notificacionService.updateNotificacion(not);
				logger.info("Notificaciones: La solicitud no tiene estatus de comprobaci�n.");
			}
		}
		logger.info("Notificaciones: " + lstNotificacion.size() + " notificaciones por anticipos pendientes enviadas.");
	}
	
	/**
	 * @author erikag
	 * @param tipoNotificacion
	 * ENV�A NOTIFICACIONES DIARIAS PROGRAMADAS DE ANTICIPOS DE GASTOS DE VIAJE CON RETRASO DE ACUERDO A LA TABLA DE NOTIFICACIONES.
	 */
	public void enviaAnticipoViajePendiente(int tipoNotificacion){
		List<Notificacion> lstNotificacion = notificacionService.getNotificacionesPendientesByTipo(tipoNotificacion);
		
		for(Notificacion not : lstNotificacion){
			//Si est� pendiente
			if(not.getSolicitud().getComprobada() == Etiquetas.CERO){
				if(emailService.enviarCorreoRetraso(not.getUsuario().getCorreoElectronico(), not)){
					not.setEstatus("E");
					notificacionService.updateNotificacion(not);					
				}
				else{
					not.setEstatus("W");
					notificacionService.updateNotificacion(not);
					logger.info("Notificaciones: La notificaci�n para la solicitud "+ not.getSolicitud().getIdSolicitud() +" no pudo ser enviada.");
				}
			}
			//Si est� comprobada
			else if(not.getSolicitud().getComprobada() == Etiquetas.UNO){
				not.setEstatus("A");
				notificacionService.updateNotificacion(not);
			}
			//Si error
			else if(not.getSolicitud().getComprobada() == null){
				not.setEstatus("W");
				notificacionService.updateNotificacion(not);
				logger.info("Notificaciones: La solicitud no tiene estatus de comprobaci�n.");
			}
		}
		logger.info("Notificaciones: " + lstNotificacion.size() + " notificaciones por anticipos pendientes enviadas.");
	}
}
