package com.lowes.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.lowes.dto.EmailDTO;
import com.lowes.entity.FacturaKilometraje;
import com.lowes.entity.Notificacion;
import com.lowes.entity.Parametro;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;
import com.lowes.service.EmailService;
import com.lowes.service.FacturaKilometrajeService;
import com.lowes.service.ParametroService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

import mx.adinfi.mail.sender.EmailSender;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
	
	Etiquetas etiquetas = new Etiquetas("es");

	@Autowired
	private EmailSender emailSender;
	@Autowired
	private FacturaKilometrajeService facturaKilometrajeService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	ServletContext servletContext;
	
	Etiquetas etiqueta = new Etiquetas("es");

	DecimalFormat formato = new DecimalFormat(etiqueta.E_FORMATO_MONEDA);

	String formulario, asunto, celda1, celda2, mensaje;
	List<Parametro> parametroList;

	public EmailServiceImpl() {
		logger.info("EmailServiceImpl()");
	}
	
	public boolean enviarCorreoRechazo(Usuario rechazadoPor, Solicitud solicitud, String motivoRechazo){
		Object data = this.preparaCorreoRechazo(rechazadoPor, solicitud, motivoRechazo);
		EmailDTO email = new EmailDTO();
		String destinatario;
		
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA)
			destinatario = solicitud.getUsuarioByIdUsuario().getCorreoElectronico();
		else
			destinatario = solicitud.getUsuarioByIdUsuarioSolicita().getCorreoElectronico();
		
		// se obtiene todo el template para el email.
		String emailTemplate = this.emailTemplate();
		try{
			if(data instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> datos = (HashMap<String, String>) data;
				email = construirCorreoRechazo(datos);
			}
		}catch(Exception e){
			
		}
		emailTemplate = emailTemplate.replaceAll("@SUBJECT", Matcher.quoteReplacement(StringEscapeUtils.escapeHtml4(email.getSubject())));
		emailTemplate = emailTemplate.replaceAll("@MENSAJE", StringEscapeUtils.escapeHtml4(email.getMensaje()));
		emailTemplate = emailTemplate.replaceAll("@CONTENIDO", Matcher.quoteReplacement(email.getContenido()));
		emailTemplate = emailTemplate.replaceAll("@DISCLAIMER", Matcher.quoteReplacement(parametroService.getParametroByName("emailDisclaimer").getValor()));
		
		try{
			emailSender.sendMail(destinatario, emailTemplate, email.getSubject());
		}catch(Exception e){
			logger.error("Notificaciones: Ha ocurrido un error al enviar la notificacion: "+ e);
			return false;
		}
		return true;
	}
	
	/**
	 * @author miguelr
	 * @param criterio
	 * @param autorizador
	 * @param destinatario
	 * @param solicitud
	 * ENVÍA CORREO DE ACUERDO AL TIPO DE SOLICITUD.
	 */
	public boolean enviarCorreo(Integer criterio, String autorizador, String destinatario, Solicitud solicitud) {
		Object data = this.preparaCorreo(criterio, autorizador, solicitud);
		EmailDTO email = new EmailDTO();
		
		// se obtiene todo el template para el email.
		String emailTemplate = this.emailTemplate();
		
		if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_KILOMETRAJE){
			if(data instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<FacturaKilometraje> datos = (List<FacturaKilometraje>) data;
				email = construirCorreoKm(datos);
			}
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_ANTICIPO){
			if(data instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> datos = (HashMap<String, String>) data;
				email = construirCorreoAnticipos(datos);
			}
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO){
			if(data instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> datos = (HashMap<String, String>) data;
				email = construirCorreoComprobacionAnticipos(datos);
			}
		}
		else if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE){
			if(data instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> datos = (HashMap<String, String>) data;
				email = construirCorreoAnticipoGastosViaje(datos);
			}
		}
		else if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE){
			if(data instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> datos = (HashMap<String, String>) data;
				email = construirCorreoComprobacionAnticipoGastosViaje(datos);
			}
		}
		else{ //Default mail service: No Mercancias / Reembolso / Caja Chica
			if(data instanceof HashMap) {
				@SuppressWarnings("unchecked")
				HashMap<String, String> datos = (HashMap<String, String>) data;
				email = construirCorreo(datos);
			}
		}
		
		emailTemplate = emailTemplate.replaceAll("@SUBJECT", Matcher.quoteReplacement(StringEscapeUtils.escapeHtml4(email.getSubject())));
		emailTemplate = emailTemplate.replaceAll("@MENSAJE", StringEscapeUtils.escapeHtml4(email.getMensaje()));
		emailTemplate = emailTemplate.replaceAll("@CONTENIDO", Matcher.quoteReplacement(email.getContenido()));
		emailTemplate = emailTemplate.replaceAll("@DISCLAIMER", Matcher.quoteReplacement(parametroService.getParametroByName("emailDisclaimer").getValor()));
		
		try{
			emailSender.sendMail(destinatario, emailTemplate, email.getSubject());
		}catch(Exception e){
			logger.error("Notificaciones: Ha ocurrido un error al enviar la notificacion: "+ e);
			return false;
		}
		return true;
	}

	/**
	 * @author miguelr
	 * @param destinatario
	 * @param data
	 * ENVÍA CORREO DE SOLICITUDES DE ANTICIPO RETRASADAS.
	 */
	public boolean enviarCorreoRetraso(String destinatario, Notificacion data) {
		EmailDTO email = new EmailDTO();
		
		// se obtiene todo el template para el email.
		String emailTemplate = this.emailTemplate();
		
		email = construirCorreoAnticipoRetraso(data);
		
		emailTemplate = emailTemplate.replaceAll("@SUBJECT", Matcher.quoteReplacement(StringEscapeUtils.escapeHtml4(email.getSubject())));
		emailTemplate = emailTemplate.replaceAll("@MENSAJE", StringEscapeUtils.escapeHtml4(email.getMensaje()));
		emailTemplate = emailTemplate.replaceAll("@CONTENIDO", Matcher.quoteReplacement(email.getContenido()));
		emailTemplate = emailTemplate.replaceAll("@DISCLAIMER", Matcher.quoteReplacement(parametroService.getParametroByName("emailDisclaimer").getValor()));
		
		try{
			emailSender.sendMail(destinatario, emailTemplate, email.getSubject());
		}catch(Exception e){
			logger.error("Notificaciones: Ha ocurrido un error al enviar la notificacion: "+ e);
			return false;
		}
		return true;
		
	}
	
	/**
	 * @author miguelr
	 * @param datos
	 * CONSTRUYE EL CORREO DE NOTIFICACION POR RETRASO.
	 */
	private EmailDTO construirCorreoAnticipoRetraso(Notificacion datos) {
		StringBuilder mensaje= new StringBuilder();
		StringBuilder subject = new StringBuilder();
		StringBuilder contenido = new StringBuilder();
		
		int idTipoNotificacion = datos.getTipoNotificacion().getIdTipoNotificacion();
		
		long antiguedad = (datos.getProximoEnvio().getTime() - datos.getFecha().getTime()) / (24 * 60 * 60 * 1000);
		
		String importeString = formatoMonto(datos.getSolicitud().getMontoTotal().toString(), datos.getSolicitud().getMoneda().getDescripcionCorta());
		
//		Mensaje
		if(idTipoNotificacion == Etiquetas.TIPO_NOTIFICACION_ANTIGUEDAD_ANTICIPO)
			mensaje.append(etiqueta.E_NOTIFICACION_ANTIGUEDAD_MSJINICIO);
		if(idTipoNotificacion == Etiquetas.TIPO_NOTIFICACION_ANTIGUEDAD_ANTICIPO_GASTOS_VIAJE)
			mensaje.append(etiqueta.E_NOTIFICACION_ANTIGUEDAD_ANTICIPOVIAJE_MSJINICIO);
		mensaje.append(antiguedad);
		mensaje.append(" " + etiqueta.E_DIAS);
		mensaje.append("\n");
		mensaje.append(etiqueta.E_NOTIFICACION_ANTIGUEDAD_MSJFIN);
		
		//generacion de subject de correo
		subject = new StringBuilder();
		subject.append(etiqueta.E_ANTICIPO);
		subject.append(" " + datos.getSolicitud().getIdSolicitud());
		subject.append(" - ");
		subject.append(datos.getSolicitud().getUsuarioByIdUsuarioSolicita().getNumeroNombreCompletoUsuario());
		subject.append(" - ");
		subject.append(Utilerias.convertDateFormat(datos.getFecha()));
		subject.append(" - ");
		subject.append(etiqueta.E_PENDIENTE_DE_COMPROBAR+" - " + etiqueta.E_ANTIGUEDAD +" "+ antiguedad +" "+ etiqueta.E_DIAS);
		
		String tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>", table = "<table align=\"center\">";
		
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; width: 80%; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<th style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-left: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		
		//Build Table
		contenido.append(table);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_ANTICIPO+": <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(String.valueOf(datos.getSolicitud().getIdSolicitud())) + tdc + trc);
		if(idTipoNotificacion == Etiquetas.TIPO_NOTIFICACION_ANTIGUEDAD_ANTICIPO)
			contenido.append(tr + thStyled +"<strong> "+etiqueta.E_BENEFICIARIO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.getSolicitud().getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario()) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_IMPORTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(importeString) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_FECHA_DEP_ANTICIPO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(Utilerias.convertDateFormat(datos.getFecha())) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_CONCEPTO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.getSolicitud().getConceptoGasto()) + tdc + trc);
		contenido.append(tablec);
		//End build Table
		
		return new EmailDTO(subject.toString(), mensaje.toString(), contenido.toString());
	}

	/**
	 * @author miguelr
	 * @param datos
	 * CONSTRUYE EL CUERPO DEL CORREO
	 */
	private EmailDTO construirCorreo(HashMap<String, String> datos){
		Integer criterio = Integer.parseInt(datos.get("Criterio"));
		Integer tipoSolicitud = Integer.parseInt(datos.get("TipoSolicitud"));
		String mensaje="", inicioAsunto="", inicioAsuntoR="", inicioAsuntoCC="";
		StringBuilder subject = new StringBuilder();
		StringBuilder contenido = new StringBuilder();
		
		String importeString = formatoMonto(datos.get("@IMPORTE"), datos.get("@DESC_MONEDA"));
		
		if(criterio!= null && (criterio==Etiquetas.CRITERIO_UNO || criterio==Etiquetas.CRITERIO_OCHO)){ // Criterio 1 u 8
			//mensaje =  etiqueta.E_MENSAJE_VALIDACION;
			mensaje =  etiqueta.E_MENSAJE_AUTORIZACION;
			inicioAsunto = etiqueta.E_AUTORIZACION_PAGO + " ";
			inicioAsuntoR = etiqueta.E_AUTORIZACION_PAGO_REEMBOLSO + " ";
			inicioAsuntoCC = etiqueta.E_AUTORIZACION_PAGO_CAJA_CHICA + " ";
		}else{
			if(criterio!= null && (criterio==Etiquetas.CRITERIO_DOS || criterio==Etiquetas.CRITERIO_TRES || criterio==Etiquetas.CRITERIO_CUATRO || criterio==Etiquetas.CRITERIO_CINCO || criterio==Etiquetas.CRITERIO_SEIS || criterio==Etiquetas.CRITERIO_SIETE)){
				// Criterio 2 - 6			
				mensaje =  etiqueta.E_MENSAJE_AUTORIZACION;
				inicioAsunto = etiqueta.E_AUTORIZACION_PAGO + " ";
				inicioAsuntoR = etiqueta.E_AUTORIZACION_PAGO_REEMBOLSO + " ";
				inicioAsuntoCC = etiqueta.E_AUTORIZACION_PAGO_CAJA_CHICA + " ";
			}
		}
		
		//Build Subject NoMercancias
		if(tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML || tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
			subject = new StringBuilder();
			subject.append(inicioAsunto);
			subject.append(datos.get("@FACTURA"));
			subject.append(" - De: ");
			subject.append(datos.get("@PROVEEDOR"));
			subject.append(" - Por: ");
			subject.append(importeString);
			subject.append(" - Solicitante: ");
			subject.append(datos.get("@SOLICITANTE"));
		}
		//Build Subject Reembolsos y CajaChica
		else if (tipoSolicitud == Etiquetas.SOLICITUD_REEMBOLSOS || tipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA){
			String solicitanteBeneficiario = "";
			if(tipoSolicitud==Etiquetas.SOLICITUD_REEMBOLSOS){
				inicioAsunto = inicioAsuntoR;
				solicitanteBeneficiario = datos.get("@SOLICITANTE");
			}
			if(tipoSolicitud==Etiquetas.SOLICITUD_CAJA_CHICA){
				inicioAsunto = inicioAsuntoCC;
				solicitanteBeneficiario = datos.get("@BENEFICIARIO");
			}
			subject.append(inicioAsunto);
			subject.append(" - De: ");
			subject.append(solicitanteBeneficiario);
			subject.append(" - Por: ");
			subject.append(importeString);
		}
		
		String tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>", table = "<table align=\"center\">";
		
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; width: 80%; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<th style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-left: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		
		//Build Table
		contenido.append(table);
			contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITUD+"  <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@REQUEST")+"-"+datos.get("@TIPO_SOLICITUD_STR")) + tdc + trc);
		if(tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML || tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
			contenido.append(tr + thStyled +"<strong> "+etiqueta.E_FACTURA+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@FACTURA")) + tdc + trc);
			contenido.append(tr + thStyled +"<strong> "+etiqueta.E_PROVEEDOR+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@PROVEEDOR")) + tdc + trc);
		}
		else if (tipoSolicitud == Etiquetas.SOLICITUD_REEMBOLSOS || tipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA){
			//contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITUD+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@REQUEST")) +"-"+ StringEscapeUtils.escapeHtml4(datos.get("TipoSolicitud")) + tdc + trc);
			if(tipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA)
				contenido.append(tr + thStyled +"<strong> "+etiqueta.E_BENEFICIARIO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@BENEFICIARIO")) + tdc + trc);
		}
		
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_IMPORTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(importeString) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITANTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@SOLICITANTE")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_ULTIMO_AUTORIZADOR+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@ULTIMO_AUTORIZADOR")!= null? datos.get("@ULTIMO_AUTORIZADOR") : "") + tdc + trc);
		contenido.append(tablec);
		//End build Table
		
		return new EmailDTO(subject.toString(), mensaje, contenido.toString());
	}
	
	/**
	 * @author miguelr
	 * @param datos
	 * CONSTRUYE EL CUERPO DEL CORREO PARA RECHAZO DE SOLICITUDES
	 */
	private EmailDTO construirCorreoRechazo(HashMap<String, String> datos){
		Integer tipoSolicitud = Integer.parseInt(datos.get("TipoSolicitud"));
		String asuntoRCC="", asuntoCC="",asuntoNM="", mensaje="";
		StringBuilder subject = new StringBuilder();
		StringBuilder contenido = new StringBuilder();
		
		String importeString = formatoMonto(datos.get("@IMPORTE"), datos.get("@DESC_MONEDA"));
		
		asuntoNM = etiquetas.E_RECHAZO_NO_MERCANCIAS;
		asuntoRCC = etiquetas.E_RECHAZO_REEM_CAJA_CHICA;
		mensaje =  etiquetas.E_RECHAZO_MENSAJE;
		
		//Build Subject NoMercancias
		if(tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML || tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
			subject = new StringBuilder();
			asuntoNM = asuntoNM.replace("_folioSerie", datos.get("@FACTURA"));
			asuntoNM = asuntoNM.replace("_razonSocial", datos.get("@PROVEEDOR"));
			asuntoNM = asuntoNM.replace("_monto", importeString);
			//asuntoNM = asuntoNM.replace("_monto", datos.get("@PROVEEDOR"));
			subject.append(asuntoNM);
		}
		//Build Subject Reembolsos y CajaChica
		else if (tipoSolicitud == Etiquetas.SOLICITUD_REEMBOLSOS || tipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA){
			if(tipoSolicitud==Etiquetas.SOLICITUD_REEMBOLSOS){
				asuntoRCC = asuntoRCC.replace("_tipoSol", datos.get("@TIPO_SOLICITUD_STR"));
				asuntoRCC = asuntoRCC.replace("_solicitud", datos.get("@ID_SOLICITUD"));
				asuntoRCC = asuntoRCC.replace("_monto", importeString);
				subject.append(asuntoRCC);
			}
			else if(tipoSolicitud==Etiquetas.SOLICITUD_CAJA_CHICA){
				asuntoRCC = asuntoRCC.replace("_tipoSol", datos.get("@TIPO_SOLICITUD_STR"));
				asuntoRCC = asuntoRCC.replace("_solicitud", datos.get("@ID_SOLICITUD"));
				asuntoRCC = asuntoRCC.replace("_monto", importeString);
				subject.append(asuntoRCC);
			}
		}
		
		String tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>", table = "<table align=\"center\">";
		
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; width: 80%; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<th style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-left: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		
		//Build Table
		contenido.append(table);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITUD+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@ID_SOLICITUD")+"-"+datos.get("@TIPO_SOLICITUD_STR")) + tdc + trc);
		if(tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML || tipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
			contenido.append(tr + thStyled +"<strong> "+etiqueta.E_FACTURA+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@FACTURA")) + tdc + trc);
			contenido.append(tr + thStyled +"<strong> "+etiqueta.E_PROVEEDOR+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@PROVEEDOR")) + tdc + trc);
		}
//		else if (tipoSolicitud == Etiquetas.SOLICITUD_REEMBOLSOS || tipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA){
//			contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITUD+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@REQUEST")) + tdc + trc);
//		}
//		
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_IMPORTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(importeString) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> Rechazado por: <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@RECHAZO_POR")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> Motivo: <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@RECHAZO_MOTIVO")) + tdc + trc);
//		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_RECHAZO_POR+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@RECHAZO_POR")) + tdc + trc);
//		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_RECHAZO_MOTIVO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@RECHAZO_MOTIVO")) + tdc + trc);
		contenido.append(tablec);
		//End build Table
		
		return new EmailDTO(subject.toString(), mensaje, contenido.toString());
	}
	
	/**
	 * @author erikag
	 * @param datos
	 * CONSTRUYE EL CUERPO DEL CORREO PARA TIPO DE SOLICITUD KILOMETRAJE
	 */
	private EmailDTO construirCorreoKm(List<FacturaKilometraje> datos){		
		
		Double totalKm= 0.0; 
		BigDecimal totalImporte = new BigDecimal(0.0);
		String tds = "", tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>",
				table = "<table align=\"center\" BGCOLOR=\"#BCD8F8\" cellspacing=\"10\">";
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<td style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-top: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		String importe ="", totalImporteStr="", fecha="";
		String contenido = table
				+ tr
				+ thStyled + "<strong></strong></td>"
				+ thStyled + "<strong>" + etiqueta.FECHA + "</strong></td>"
				+ thStyled + "<strong>" + etiqueta.E_KM_MOTIVO + "</strong></td>"
				+ thStyled + "<strong>" + etiqueta.ORIGEN + "</strong></td>"
				+ thStyled + "<strong>" + etiqueta.DESTINO + "</strong></td>"
				+ thStyled + "<strong>" + etiqueta.E_KM_VIAJES + "</strong></td>"
				+ thStyled + "<strong>" + etiqueta.E_KM + "</strong></td>"
				+ thStyled + "<strong>" + etiqueta.IMPORTE + "</strong></td>"
				+ trc;
		int contadorLinea = Etiquetas.UNO;
		for(FacturaKilometraje fk : datos){
			tds+= tr;
			tds+= tdStyled + contadorLinea + tdc;
			fecha= Utilerias.convertDateFormat(fk.getKilometrajeFecha());
			tds+= tdStyled + fecha + tdc;
			tds+= tdStyled + StringEscapeUtils.escapeHtml4(fk.getMotivo()) + tdc;
			tds+= tdStyled + StringEscapeUtils.escapeHtml4(fk.getKilometrajeRecorrido().getKilometrajeUbicacionByIdOrigen().getDescripcion()) + tdc;
			tds+= tdStyled + StringEscapeUtils.escapeHtml4(fk.getKilometrajeRecorrido().getKilometrajeUbicacionByIdDestino().getDescripcion()) + tdc;
			tds+= tdStyled + fk.getNumeroViajes() + tdc;
			tds+= tdStyled + fk.getkilometrajeRecorridoCalculado() + tdc;	
			totalKm+= fk.getkilometrajeRecorridoCalculado();
			importe = Utilerias.replaceComillas(formato.format(Double.valueOf(fk.getImporte().toString())));
			tds+= tdStyled + StringEscapeUtils.escapeHtml4(importe) + tdc;
			totalImporte = totalImporte.add(fk.getImporte());
			tds+= trc;
			contadorLinea ++;
		}
		//totalImporteStr = Matcher.quoteReplacement("$" + Utilerias.replaceComillas(formato.format(totalImporte)));
		totalImporteStr = Utilerias.replaceComillas("$" + Utilerias.replaceComillas(formato.format(totalImporte)));
		contenido+= tds 
			+ tdStyled + tdc
			+ tdStyled + tdc
			+ tdStyled + tdc
			+ tdStyled + tdc
			+ tdStyled + tdc
			+ thStyled + "<font face='Calibri'> <strong>" + etiqueta.TOTAL + "</strong> </font>" + tdc
			+ thStyled + "<font face='Calibri'> <strong>" + totalKm + "</strong> </font>" + tdc
			+ thStyled + "<font face='Calibri'> <strong>" + StringEscapeUtils.escapeHtml4(totalImporteStr) + "</strong> </font>" + tdc
			+ tablec;
		
		System.out.println(contenido);
		
		StringBuilder subject = new StringBuilder();
		String solicitante = datos.get(0).getSolicitud().getUsuarioByIdUsuarioSolicita().getNombre() 
			+ " " + datos.get(0).getSolicitud().getUsuarioByIdUsuarioSolicita().getApellidoPaterno()
			+ " " + datos.get(0).getSolicitud().getUsuarioByIdUsuarioSolicita().getApellidoMaterno();
		
		StringBuilder importesb = new StringBuilder();
		String importeString = datos.get(0).getSolicitud().getMontoTotal().toString();
		importesb.append("$").append(formato.format(Double.valueOf(importeString))).append(" " + datos.get(0).getSolicitud().getMoneda().getDescripcionCorta());
		importeString = null;
		importeString = importesb.toString();
		importeString = Utilerias.replaceComillas(importeString);
		
		//generacion de subject de correo
		subject = new StringBuilder();
		subject.append(etiqueta.E_PAGO_KM);
		subject.append(" - ");
		subject.append(solicitante);
		subject.append(" - Por: ");
		subject.append(importeString);

		return new EmailDTO(subject.toString(), etiqueta.E_KM_MENSAJE, contenido);
	}
	
	/**
	 * @author erikag
	 * @param datos
	 * CONSTRUYE EL CUERPO DEL CORREO PARA TIPO DE SOLICITUD ANTICIPOS
	 */
	private EmailDTO construirCorreoAnticipos(HashMap<String, String> datos){
		StringBuilder subject = new StringBuilder(), mensaje = new StringBuilder(), contenido = new StringBuilder();
		String inicioAsunto="";
		
		String importeString = formatoMonto(datos.get("@IMPORTE"), datos.get("@DESC_MONEDA"));
		
		inicioAsunto = etiqueta.E_SOLICITUD_ANTICIPO; 
		
		//Subject
		subject.append(inicioAsunto);			
		subject.append(" - Para: ");
		subject.append(datos.get("@BENEFICIARIO"));
		subject.append(" - Importe: ");
		subject.append(importeString);
		subject.append(" - Solicita: ");
		subject.append(datos.get("@SOLICITANTE"));
		
		//Mensaje
		mensaje.append("");
		
		//Contenido
		String tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>", table = "<table align=\"center\">";
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; width: 80%; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<th style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-left: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		
		//Build Table
		contenido.append(table);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_ANTICIPO+": <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@REQUEST")) + tdc + trc);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_BENEFICIARIO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@BENEFICIARIO")) + tdc + trc);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_IMPORTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(importeString) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITANTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@SOLICITANTE")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_CONCEPTO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@CONCEPTO")) + tdc + trc);
		contenido.append(tablec);
		//End build Table
		
		//Regresa datos
		return new EmailDTO(subject.toString(), mensaje.toString(), contenido.toString());	
	}
	
	/**
	 * @author erikag
	 * @param datos
	 * CONSTRUYE EL CUERPO DEL CORREO PARA TIPO DE SOLICITUD: COMPROBACIÓN ANTICIPOS
	 */
	private EmailDTO construirCorreoComprobacionAnticipos(HashMap<String, String> datos){
		StringBuilder subject = new StringBuilder(), mensaje = new StringBuilder(), contenido = new StringBuilder();
		String inicioAsunto="";
		
		String importeString = formatoMonto(datos.get("@IMPORTE"), datos.get("@DESC_MONEDA"));
		
		inicioAsunto = etiqueta.COMPROBACION_ANTICIPO_TITULO; 
		
		//Subject
		subject.append(inicioAsunto);			
		subject.append(" - De: ");
		subject.append(datos.get("@BENEFICIARIO"));
		subject.append(" - Importe: ");
		subject.append(importeString);
		subject.append(" - Comprueba: ");
		subject.append(datos.get("@SOLICITANTE"));
		
		//Mensaje
		mensaje.append("");
		
		//Contenido
		String tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>", table = "<table align=\"center\">";
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; width: 80%; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<th style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-left: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		
		//Build Table
		contenido.append(table);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_COMPROBACION+": <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@REQUEST")) + tdc + trc);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_BENEFICIARIO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@BENEFICIARIO")) + tdc + trc);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_IMPORTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(importeString) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_COMPRUEBA+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@SOLICITANTE")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_ULTIMO_AUTORIZADOR+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@ULTIMO_AUTORIZADOR")!= null? datos.get("@ULTIMO_AUTORIZADOR") : "") + tdc + trc);
		contenido.append(tablec);
		//End build Table
		
		//Regresa datos
		return new EmailDTO(subject.toString(), mensaje.toString(), contenido.toString());	
	}
	
	/**
	 * @author erikag
	 * @param datos
	 * CONSTRUYE EL CUERPO DEL CORREO PARA TIPO DE SOLICITUD: ANTICIPOS DE GASTOS DE VIAJE
	 */
	private EmailDTO construirCorreoAnticipoGastosViaje(HashMap<String, String> datos){
		StringBuilder subject = new StringBuilder(), mensaje = new StringBuilder(), contenido = new StringBuilder();
		String inicioAsunto="";
		
		String importeString = formatoMonto(datos.get("@IMPORTE"), datos.get("@DESC_MONEDA"));
		
		inicioAsunto = etiqueta.E_SOLICITUD_ANTICIPO_GASTOS_VIAJE; 
		
		//Subject
		subject.append(inicioAsunto);			
		subject.append(" - Importe: ");
		subject.append(importeString);
		subject.append(" - Solicita: ");
		subject.append(datos.get("@SOLICITANTE"));
		
		//Mensaje
		mensaje.append("");
		
		//Contenido
		String tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>", table = "<table align=\"center\">";
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; width: 80%; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<th style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-left: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		
		//Build Table
		contenido.append(table);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_ANTICIPO+": <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@REQUEST")) + tdc + trc);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_IMPORTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(importeString) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITANTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@SOLICITANTE")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_CONCEPTO+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@CONCEPTO")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_ULTIMO_AUTORIZADOR+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@ULTIMO_AUTORIZADOR")!= null? datos.get("@ULTIMO_AUTORIZADOR") : "") + tdc + trc);
		contenido.append(tablec);
		//End build Table
		
		//Regresa datos
		return new EmailDTO(subject.toString(), mensaje.toString(), contenido.toString());	
	}
	
	/**
	 * @author erikag
	 * @param datos
	 * CONSTRUYE EL CUERPO DEL CORREO PARA TIPO DE SOLICITUD: COMPROBACIÓN DE ANTICIPOS DE GASTOS DE VIAJE
	 */
	private EmailDTO construirCorreoComprobacionAnticipoGastosViaje(HashMap<String, String> datos){
		StringBuilder subject = new StringBuilder(), mensaje = new StringBuilder(), contenido = new StringBuilder();
		String inicioAsunto="";
		
		String importeString = formatoMonto(datos.get("@IMPORTE"), datos.get("@DESC_MONEDA"));
		
		inicioAsunto = etiqueta.E_COMPROBACION_SOLICITUD_ANTICIPO_GASTOS_VIAJE; 
		
		//Subject
		subject.append(inicioAsunto);
		subject.append(" - De: ");
		subject.append(datos.get("@SOLICITANTE"));
		subject.append(" - Importe: ");
		subject.append(importeString);
		
		//Mensaje
		mensaje.append("");
		
		//Contenido
		String tr= "<tr> <font face='Calibri'> ", trc ="</font> </tr>", tdc = "</td>",tablec= "</table>", table = "<table align=\"center\">";
		String tdStyled = "<td style=\"background: #e8edff; border-bottom: 1px solid #fff; width: 80%; color: #253159; border-top: 1px solid transparent; padding: 8px;\">";
		String thStyled = "<th style=\"font-size: 13px; font-weight: normal; background: #b9c9fe; border-left: 4px solid #aabcfe; border-bottom: 1px solid #fff; color: #051035; padding: 8px;\">";
		
		//Build Table
		contenido.append(table);			
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_COMPROBACION+": <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@REQUEST")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_SOLICITANTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@SOLICITANTE")) + tdc + trc);
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_IMPORTE+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(importeString) + tdc + trc);		
		contenido.append(tr + thStyled +"<strong> "+etiqueta.E_ULTIMO_AUTORIZADOR+" <strong></th>" + tdStyled + StringEscapeUtils.escapeHtml4(datos.get("@ULTIMO_AUTORIZADOR")!= null? datos.get("@ULTIMO_AUTORIZADOR") : "") + tdc + trc);
		contenido.append(tablec);
		//End build Table
		
		//Regresa datos
		return new EmailDTO(subject.toString(), mensaje.toString(), contenido.toString());	
	}
	
	/**
	 * @author erikag
	 * EL TEMPLATE GENERAL DEL EMAIL
	 */
	private String emailTemplate(){
		String mail = null;
		String fullPath = servletContext.getRealPath("/WEB-INF/resources/theme1/html/emailTemplate.html");
	    try {
			List<String> content = Files.readLines(new File(fullPath), Charsets.UTF_8);
			mail = content.toString();
			mail = mail.replace(",","");
			mail = mail.replace("[","");
			mail = mail.replace("]","");
			
		} catch (IOException e) {
			
		}
		return mail;
	}
	
	/**
	 * @author miguelr
	 * @param datos
	 * PREPARA LOS DATOS DE LA NOTIFICACION EN HASHMAP
	 */
	private Object preparaCorreo(Integer criterio, String autorizador, Solicitud solicitud) {
		//HashMap<String, String> emailData = new HashMap<>();

		// Si es Tipo No Mercancías con/sin XML
		int idTipoSolicitud = solicitud.getTipoSolicitud().getIdTipoSolicitud();
		String proveedor="";
		if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML
				|| idTipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML)) {
			HashMap<String, String> emailData = new HashMap<>();
			emailData.put("@REQUEST", String.valueOf(solicitud.getIdSolicitud()));
			emailData.put("@FACTURA", solicitud.getFacturas().get(0).getFactura());
			if(solicitud.getFacturas().get(0).getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_NOTA_CREDITO)
				emailData.put("@IMPORTE", solicitud.getMontoTotal().negate().toString());
			else
				emailData.put("@IMPORTE", solicitud.getMontoTotal().toString());
				
			emailData.put("@PROVEEDOR", solicitud.getFacturas().get(0).getProveedor().getDescripcion().replace(".", ".&#8203;"));
			emailData.put("@DESC_MONEDA", solicitud.getMoneda() !=null ? solicitud.getMoneda().getDescripcionCorta() : "");
			emailData.put("@SOLICITANTE", solicitud.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			emailData.put("@ULTIMO_AUTORIZADOR", (autorizador != null ? autorizador : ""));
			emailData.put("@TIPO_SOLICITUD_STR", String.valueOf(solicitud.getTipoSolicitud().getDescripcion()));
			emailData.put("Criterio", String.valueOf(criterio));
			emailData.put("TipoSolicitud", String.valueOf(idTipoSolicitud));
			return emailData;
		} 
		// Si es tipo Reembolso o Caja chica
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_REEMBOLSOS
				|| idTipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA)) {
			HashMap<String, String> emailData = new HashMap<>();
			emailData.put("@REQUEST", String.valueOf(solicitud.getIdSolicitud()));
			emailData.put("@IMPORTE", solicitud.getMontoTotal().toString());
			emailData.put("@DESC_MONEDA", solicitud.getMoneda() !=null ? solicitud.getMoneda().getDescripcionCorta() : "");
			emailData.put("@SOLICITANTE", solicitud.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			emailData.put("@ULTIMO_AUTORIZADOR", (autorizador != null ? autorizador : ""));
			emailData.put("@TIPO_SOLICITUD_STR", String.valueOf(solicitud.getTipoSolicitud().getDescripcion()));
			emailData.put("Criterio", String.valueOf(criterio));
			emailData.put("TipoSolicitud", String.valueOf(idTipoSolicitud));
			if(idTipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA)
				emailData.put("@BENEFICIARIO", solicitud.getUsuarioByIdUsuarioAsesor().getNombreCompletoUsuario());
			return emailData;

		}
		// Si es tipo Kilometraje
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_KILOMETRAJE)) {
			return facturaKilometrajeService.getAllFacturaKilometrajeByIdSolicitud(solicitud.getIdSolicitud());
		}
		// Si es tipo Anticipo
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO)) {
			HashMap<String, String> emailData = new HashMap<>();
			emailData.put("@REQUEST", String.valueOf(solicitud.getIdSolicitud()));
			emailData.put("@IMPORTE", solicitud.getMontoTotal().toString());
			emailData.put("@DESC_MONEDA", solicitud.getMoneda() !=null ? solicitud.getMoneda().getDescripcionCorta() : "");
			emailData.put("@FECHA_DEPOSITO", solicitud.getCreacionFecha().toString());
			emailData.put("@BENEFICIARIO", solicitud.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			emailData.put("@SOLICITANTE", solicitud.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			emailData.put("@CONCEPTO", solicitud.getConceptoGasto());
			emailData.put("@TIPO_SOLICITUD_STR", String.valueOf(solicitud.getTipoSolicitud().getDescripcion()));
			emailData.put("TipoSolicitud", String.valueOf(solicitud.getTipoSolicitud().getIdTipoSolicitud()));
			emailData.put("estatusSolicitud", String.valueOf(solicitud.getEstadoSolicitud().getIdEstadoSolicitud()));
			return emailData;
		}
//		Si es tipo ComprobacionAnticipo
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO)) {
			HashMap<String, String> emailData = new HashMap<>();
			emailData.put("@REQUEST", String.valueOf(solicitud.getIdSolicitud()));
			emailData.put("@IMPORTE", solicitud.getMontoTotal().toString());
			emailData.put("@DESC_MONEDA", solicitud.getMoneda() !=null ? solicitud.getMoneda().getDescripcionCorta() : "");
			proveedor = solicitud.getFacturas().get(0).getProveedor() != null ? solicitud.getFacturas().get(0).getProveedor().getNumeroDescripcionProveedor() : "";
			emailData.put("@BENEFICIARIO", proveedor );
			emailData.put("@SOLICITANTE", solicitud.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			emailData.put("@ULTIMO_AUTORIZADOR", (autorizador != null ? autorizador : ""));
			emailData.put("@TIPO_SOLICITUD_STR", String.valueOf(solicitud.getTipoSolicitud().getDescripcion()));
			emailData.put("TipoSolicitud", String.valueOf(solicitud.getTipoSolicitud().getIdTipoSolicitud()));
			emailData.put("estatusSolicitud", String.valueOf(solicitud.getEstadoSolicitud().getIdEstadoSolicitud()));
			return emailData;
		}
//		Si es tipo AnticipoGastosViaje
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE)) {
			HashMap<String, String> emailData = new HashMap<>();
			emailData.put("@REQUEST", String.valueOf(solicitud.getIdSolicitud()));
			emailData.put("@IMPORTE", solicitud.getMontoTotal().toString());
			emailData.put("@DESC_MONEDA", solicitud.getMoneda() !=null ? solicitud.getMoneda().getDescripcionCorta() : "");
			emailData.put("@SOLICITANTE", solicitud.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			emailData.put("@CONCEPTO", solicitud.getConceptoGasto());
			emailData.put("@ULTIMO_AUTORIZADOR", (autorizador != null ? autorizador : ""));
			return emailData;
		}
//		Si es tipo ComprobacionAnticipoGastosViaje
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE)) {
			HashMap<String, String> emailData = new HashMap<>();
			emailData.put("@REQUEST", String.valueOf(solicitud.getIdSolicitud()));
			emailData.put("@IMPORTE", solicitud.getMontoTotal().toString());
			emailData.put("@DESC_MONEDA", solicitud.getMoneda() !=null ? solicitud.getMoneda().getDescripcionCorta() : "");
			emailData.put("@SOLICITANTE", solicitud.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			emailData.put("@ULTIMO_AUTORIZADOR",(autorizador != null ? autorizador : ""));
			return emailData;
		}
		
		return null;
	}
	
	/**
	 * @author miguelr
	 * @param datos
	 * PREPARA LOS DATOS DE LA NOTIFICACION PARA RECHAZO EN HASHMAP
	 */
	private Object preparaCorreoRechazo(Usuario rechazadoPor, Solicitud solicitud, String motivoRechazo) {
		HashMap<String, String> emailData = new HashMap<>();

		// Si es Tipo No Mercancías con/sin XML
		int idTipoSolicitud = solicitud.getTipoSolicitud().getIdTipoSolicitud();
		emailData.put("TipoSolicitud", String.valueOf(idTipoSolicitud));
		emailData.put("@TIPO_SOLICITUD_STR", String.valueOf(solicitud.getTipoSolicitud().getDescripcion()));
		emailData.put("@ID_SOLICITUD", String.valueOf(solicitud.getIdSolicitud()));
		
		if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML
				|| idTipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML)) {
			emailData.put("@FACTURA", solicitud.getFacturas().get(0).getFactura());
			emailData.put("@PROVEEDOR", solicitud.getFacturas().get(0).getProveedor().getDescripcion().replace(".", ".&#8203;"));
		} 
		// Si es tipo Reembolso o Caja chica
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_REEMBOLSOS
				|| idTipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA)) {
		}
		// Si es tipo Kilometraje
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_KILOMETRAJE)) {
		}
		// Si es tipo Anticipo
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO)) {

		}
//		Si es tipo ComprobacionAnticipo
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO)) {
		}
//		Si es tipo AnticipoGastosViaje
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE)) {
		}
//		Si es tipo ComprobacionAnticipoGastosViaje
		else if (idTipoSolicitud != 0 && (idTipoSolicitud == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE)) {
		}
		
		emailData.put("@IMPORTE", solicitud.getMontoTotal().toString());
		emailData.put("@DESC_MONEDA", solicitud.getMoneda() !=null ? solicitud.getMoneda().getDescripcionCorta() : "");
		emailData.put("@RECHAZO_POR", rechazadoPor.getNombreCompletoUsuario());
		emailData.put("@RECHAZO_MOTIVO", motivoRechazo);
		
		return emailData;
	}
	
	public String formatoMonto(String importe, String descMoneda){
		StringBuilder importesb = new StringBuilder();
		importesb.append("$").append(formato.format(Double.valueOf(importe))).append(" " + descMoneda);
		return Utilerias.replaceComillas(importesb.toString());
	}
}