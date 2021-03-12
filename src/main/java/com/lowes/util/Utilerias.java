package com.lowes.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.jboss.logging.Logger;

import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.Usuario;

public class Utilerias {
	
	private static final Logger logger = Logger.getLogger(Class.class.getClass());
	
	public static String getMensajeWS(List<WSFacturaMessageResponse> WSFacturaMessageResponse) {

		String mensaje = null;
		if (WSFacturaMessageResponse != null && WSFacturaMessageResponse.size() > Etiquetas.CERO) {
			mensaje = WSFacturaMessageResponse.get(Etiquetas.CERO).getMessage();
			String[] mensaje_split = mensaje.split("\\|");
			mensaje = mensaje_split[Etiquetas.CERO];
		}
		return mensaje;
	}
	
	
	public static final String getRandomNum(){
		
		String num = "0";
		Random r = new Random();
		Integer n = r.nextInt(150) + 1;
		num = n.toString();
		return num;
		
	}
	
	
	public static ByteArrayOutputStream getCloneInputStream(InputStream is){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = is.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}

			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return baos;
	}
	
	
	public static String getFilesPath(){
		
		String path = null;
		path = PropertyUtil.getProperty("lws.path");  
		
		return path;
	} 
	
	public static String getImagenesdePerfilPath(){
		
		String path = PropertyUtil.getProperty("lws.pathImagenesPerfil");  
		
		return path;
	} 

	public static String toSHA1(String message) {
		if (message == null) {
			logger.error("Error message is null");
			return null;
		}
		String str = null;
		try {
			str = String.format("%032x", new BigInteger(1, DigestUtils.sha(message.getBytes("utf-8"))));
		} catch (Exception e) {
			logger.error("Error encrypt password", e);
		}
		return str;
	}
	
	public static Date parserSqlDate(String strDate) {
		strDate = strDate.replaceAll("/", "-");
		Date date = java.sql.Date.valueOf(strDate);
		return date;
	}

	public static Integer convertToInteger(String cadena) throws NumberFormatException {
		Integer numero = new Integer(cadena);
		return numero;
	}

	public static BigDecimal convertToBigDecimal(String cadena) throws NumberFormatException {
		BigDecimal numero = new BigDecimal(cadena);
		return numero;
	}
	
	public static String replaceComillas (String cadena){
		cadena= cadena.replace("\"", "");
		return cadena;
	}
	
	public static String convertDateFormat(Date fecha){
		DateFormat fechaF = new SimpleDateFormat("dd/MM/yyyy");
		return fecha != null? fechaF.format(fecha).toString() : "";
	}
	
//	public static String convertDateStringFormat(Date fecha){
//		DateFormat fechaF = new SimpleDateFormat("dd-MMM-yyyy");
//		return fecha != null? fechaF.format(fecha).toString() : "";
//	}
	
	public static Date parseDate(String date, String format) throws ParseException{
		Date date2 = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			if(date != null && date.isEmpty() == false){
				date2 = formatter.parse(date);
			}
		} catch (ParseException e) {
			logger.info("Error al formatear String a Date");
			e.printStackTrace();
		}
		
		return date2;
	}
	
	public static BigDecimal convertStringToBigDecimal (String number){
		if(number != null && number.isEmpty()== false ){
			number = number.replace(",", "");
			number.trim();
			return new BigDecimal(number);
		}else
			return new BigDecimal(Etiquetas.CERO).setScale(Etiquetas.DOS);		
	}
	
	public static String loadEmailTemplate (ServletContext context) {
		String str = null;
		InputStream inputStream = null;
		String resource = null;
		try {
			resource = String.format("/WEB-INF/%s", "templateEmail.html");
			inputStream = context.getResourceAsStream(resource);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));
			
			StringBuilder builder = new StringBuilder();
			String aux = "";

			while ((aux = bufferedReader.readLine()) != null) {
			    builder.append(aux);
			}
			str = builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str;
		
	}
	
	  public static Integer validaAcceso(Solicitud solicitud, Integer tipoSolicitudSesion, Usuario usuarioSesion, List<SolicitudAutorizacion> autorizadores, Integer puestoAP, Integer puestoConfirmacionAP){
		  
		  puestoConfirmacionAP = 101;
		  
		  Integer acceso = Etiquetas.NO_VISUALIZAR;
		  //1.- editar y visualizar la solicitud  (solo el propietario de la solicitud)
		  //2.- visualizar la solicitud (jefe, y autorizadores)
		  //3.- no visualizar (
		  
		// si el acceso no corresponde al tipo de solicitud de la solicitud actual no se visualiza.  
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == tipoSolicitudSesion) {
			// si es el propietario de la solicitud
			Usuario Propietario = solicitud.getUsuarioByIdUsuarioSolicita();
			
			// propietario (solicitante) o creador.
			if (usuarioSesion.getIdUsuario() == Propietario.getIdUsuario() || usuarioSesion.getIdUsuario() == solicitud.getCreacionUsuario()) {
				acceso = Etiquetas.VISUALIZAR_Y_EDITAR;
				return acceso;
			} else {
				// si no es el propietario puede ser su jefe un autorizador
				if (usuarioSesion.getIdUsuario() == solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuarioJefe()) {
              	   acceso = Etiquetas.VISUALIZAR;
              	   return acceso;
				} 
				// si no es el, ni su jefe, puede ser algun autorizador de la solicitud	
				if (autorizadores != null && autorizadores.isEmpty() == false) {
					for (SolicitudAutorizacion autorizador : autorizadores) {
                      if(autorizador.getUsuarioByIdUsuarioAutoriza().getIdUsuario() == usuarioSesion.getIdUsuario()){
                    	  acceso = Etiquetas.VISUALIZAR;
                    	  return acceso;
                      }
					}
				}
				// si no es ninguno de los anteriores valida si es puesto AP
				if (usuarioSesion.getPuesto().getIdPuesto() == puestoAP || usuarioSesion.getPuesto().getIdPuesto() == puestoConfirmacionAP){
					acceso = Etiquetas.VISUALIZAR;
					return acceso;
				}
				
			}
		}
		return acceso;
	  }
	  
	  public static List<FacturaDesglose> getSortFacturasDesglose(List<FacturaDesglose> facturasDesglose){
		  
		  List<FacturaDesglose> desglosefinal = new ArrayList<>();
		  desglosefinal = facturasDesglose.stream().sorted((f1, f2) -> Integer.compare(f1.getIdFacturaDesglose(),f2.getIdFacturaDesglose())).collect(Collectors.toList());;
	
		  return desglosefinal;
	  } 
	  
	  // Para comparaciones de fechas en Hibernate
	  // Posiciona una fecha al inicio del d�a
	public static Date getFormattedFromDateTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	// Posiciona una fecha en el final del d�a
	public static Date getFormattedToDateTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static boolean isProveedorSimple(short permiteAnticipoMultiple){
		return permiteAnticipoMultiple==Etiquetas.CERO_S;
	}
	
}
