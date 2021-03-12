package com.lowes.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.EncabezadoEbs;
import com.lowes.entity.FacturaKilometraje;
import com.lowes.entity.LineasEbs;
import com.lowes.entity.Notificacion;
import com.lowes.entity.Solicitud;
import com.lowes.service.EmailService;
import com.lowes.service.EncabezadoEbsService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaKilometrajeService;
import com.lowes.service.KilometrajeRecorridoService;
import com.lowes.service.LineasEbsService;
import com.lowes.service.NotificacionService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ParaPruebasController {

	private static final Logger logger = Logger.getLogger(ParaPruebasController.class);
	
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private KilometrajeRecorridoService kms;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private EncabezadoEbsService encabezadoEbsService;
	
	@Autowired
	private LineasEbsService lineasEbsService;
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private FacturaKilometrajeService facturaKilometrajeService ;
	
	public ParaPruebasController() {
		logger.info("DetalleConceptoController()");
	}

	@RequestMapping(value="/paraPruebas", method=RequestMethod.GET)
	public ModelAndView detalleConcepto(@ModelAttribute Solicitud solicitud, 
			@RequestParam(value="email", required=false) String email, HttpServletRequest req) {
		List<Solicitud> solicitudList = solicitudService.getAllSolicitud();
		
		/*
		 * prueba para e-Mail
		 */
		HashMap<String, String> datos = new HashMap<String, String>();
////		No mercancías 
		datos.put("@FACTURA", "Factura T33535");
		datos.put("@IMPORTE", "509405.12");
		datos.put("@PROVEEDOR", "Informatica Empresarial Integrada, S.A. de C.V.");
		datos.put("@SOLICITANTE", "Ender Polanco");
		datos.put("@ULTIMO_AUTORIZADOR", "Josue Sanchez");
		datos.put("Criterio", new String("3"));
		datos.put("TipoSolicitud", new String("2"));
		
//		Reembolso / Caja Chica
//		datos.put("@REQUEST", "T33535");
//		datos.put("@IMPORTE", "5094.72");
//		datos.put("@SOLICITANTE", "Ender Polanco");
//		datos.put("@ULTIMO_AUTORIZADOR", "Josué Sánchez");
//		datos.put("Criterio", new String("8"));
//		datos.put("TipoSolicitud", new String("4"));
		
//		Kilometraje
//		Solicitud sol = solicitudService.getSolicitud(3);
//		FacturaKilometraje fk1= new FacturaKilometraje(55, 
//								kms.getKilometrajeRecorrido(1), 
//								sol, 
//								new Date(), 
//								"Motivo 1", 
//								2, 
//								12.50, 
//								new BigDecimal(76.5), 
//								Etiquetas.UNO_S,
//								new Date(), 
//								102);
//		FacturaKilometraje fk2= new FacturaKilometraje(56, 
//				kms.getKilometrajeRecorrido(3), 
//				sol, 
//				new Date(), 
//				"Motivo 2", 
//				1, 
//				10.0, 
//				new BigDecimal(52.6), 
//				Etiquetas.UNO_S,
//				new Date(), 
//				102);
//		
//		List<FacturaKilometraje> datos = new ArrayList<>();
//		datos.add(fk1);
//		datos.add(fk2);
		
//		Anticipos
//		Solicitud sol = solicitudService.getSolicitud(341);
//		datos.put("@REQUEST", String.valueOf(sol.getIdSolicitud()));
//		datos.put("@IMPORTE", sol.getMontoTotal().toString());
//		datos.put("@DESC_MONEDA", sol.getMoneda().getDescripcionCorta());
//		datos.put("@FECHA_DEPOSITO", new Date().toString());
//		datos.put("@ANTIGUEDAD", "7"); // sólo se llena para recordatorio
//		datos.put("@BENEFICIARIO", "8987 - Gabriel Ochoa");
//		datos.put("@SOLICITANTE", sol.getUsuarioByIdUsuarioSolicita().getNumeroEmpleado() + " - " 
//				+sol.getUsuarioByIdUsuarioSolicita().getNombre() + " "
//				+ sol.getUsuarioByIdUsuarioSolicita().getApellidoPaterno() + " "
//				+ sol.getUsuarioByIdUsuarioSolicita().getApellidoMaterno());
//		datos.put("@CONCEPTO", sol.getConceptoGasto());
//		datos.put("TipoSolicitud", String.valueOf(sol.getTipoSolicitud().getIdTipoSolicitud()));
////		datos.put("estatusSolicitud", String.valueOf(sol.getEstadoSolicitud().getIdEstadoSolicitud()));
//		datos.put("estatusSolicitud", String.valueOf("6"));
		
		
		try {
//			emailService.enviarCorreo(3, "Yo", "miguelr@adinfi.com", solicitudService.getSolicitud(409));
////			emailService.enviarCorreo(email == null ? "erikag@adinfi.com" : email, datos);
////			emailService.enviarCorreoKm(email == null ? "erikag@adinfi.com" : email, datos);
//			email= "erikag@adinfi.com";
//			//emailService.enviarCorreo(sol.getTipoSolicitud().getIdTipoSolicitud(), email, datos);
//
//			logger.info("El mensaje enviado ----------------------------");
//			
//			Notificacion not = notificacionService.getNotificacion(21);
//			//Si está pendiente
//			if(not.getSolicitud().getComprobada() == Etiquetas.CERO){
//				if(emailService.enviarCorreoRetraso(not.getUsuario().getCorreoElectronico(), not)){
//					int nextParam = not.getNumNotificacion() + 1;
//					not.setNumNotificacion(nextParam);
//					if(nextParam < Etiquetas.TRES){
//						int antiguedad = Integer.parseInt(parametroService.getParametroByName("anticipoDiasAntiguedad" + (nextParam + 1)).getValor());
//						Calendar proximoEnvio = Calendar.getInstance();
//						proximoEnvio.setTime(not.getProximoEnvio()); // Configuramos la fecha que se recibe
//						proximoEnvio.add(Calendar.DAY_OF_YEAR, antiguedad);  // numero de días a añadir, o restar en caso de días<0
//						not.setProximoEnvio(proximoEnvio.getTime());
//						not.setEstatus("P");
//					}						
//					else
//						not.setEstatus("E");
//					notificacionService.updateNotificacion(not);					
//				}
//				else{
//					not.setEstatus("W");
//					notificacionService.updateNotificacion(not);
//					logger.info("Notificaciones: La notificación para la solicitud "+ not.getSolicitud().getIdSolicitud() +" no pudo ser enviada.");
//				}
//			}
//			//Si está comprobada
//			else if(not.getSolicitud().getComprobada() == Etiquetas.UNO){
//				not.setEstatus("A");
//				notificacionService.updateNotificacion(not);
//			}
//			//Si error
//			else if(not.getSolicitud().getComprobada() == null){
//				not.setEstatus("W");
//				notificacionService.updateNotificacion(not);
//				logger.info("Notificaciones: La solicitud no tiene estatus de comprobación.");
//			}
//		
//		logger.info("Notificaciones: notificaciones por anticipos pendientes enviadas.");
			
		} catch (Exception e1) {
			logger.info("El mensaje no pudo ser enviado");
			e1.printStackTrace();
		}
		/*
		 * --------------------------------
		 */
		
		return new ModelAndView("detalleConcepto", "solicitudList", solicitudList);
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getPrueba", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Solicitud solicitud = new Solicitud();

		// consulta
		if (Integer.parseInt(intxnId) > Etiquetas.CERO) {
			solicitud = solicitudService.getSolicitud((Integer.parseInt(intxnId)));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		// seteando la respuesta
		result.put("conceptoGasto", solicitud.getConceptoGasto());
		result.put("idSolicitud", String.valueOf(solicitud.getIdSolicitud()));

		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/encabezado") 
	public void encabezado(){
		
		
		
		List<FacturaKilometraje> facturaKm = facturaKilometrajeService.getAllFacturaKilometrajeByIdSolicitud(113);
		//String a = facturaKm[0].kilometrajeRecorrido.kilometrajeUbicacionByDestino.descripcion();
		FacturaKilometraje f = facturaKm.get(0);
		String origen = f.getKilometrajeRecorrido().getKilometrajeUbicacionByIdOrigen().getDescripcion();
		String destino = f.getKilometrajeRecorrido().getKilometrajeUbicacionByIdDestino().getDescripcion();

		
//		encabezadoEbsService.deleteEncabezadoEbs(2);
//		
//		EncabezadoEbs encabezado = new EncabezadoEbs();
//		
//		//encabezado.setSolicitud(new Solicitud(291));
//		
//		
//		/*
//		 * Reembolsos: serie y folio concatenado _
//		 * 
//		 * 
//		 * */
//		encabezado.setInvoiceNum("invnumber123456");
//		encabezado.setInvoiceTypeLookupCode("123465");
//		encabezado.setInvoiceDate(new Date());
//		encabezado.setVendorNum("132465");
//		encabezado.setInvoiceAmount(new BigDecimal("132.00"));
//		encabezado.setInvoiceCurrencyCode("MXN");
//		encabezado.setDescription("la descripción");
//		encabezado.setAttribute15("132465Atribute15");
//		encabezado.setStatus("validada");
//	    encabezado.setSource("source");
//	    encabezado.setPaymentMethodLookupCode("paymentlookcodeode");
//	    encabezado.setOrgId(123);
//		
//	    
//		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
//		
//		LineasEbs linea = new LineasEbs();
//		//linea.setFactura(new Factura(338));
//		//linea.setEncabezadoEbs(new EncabezadoEbs(id));
//		linea.setLineNumber(1);
//		linea.setLineTypeLookupCode("linetypwlookupcode");
//		linea.setAmount(new BigDecimal("562.29"));
//		linea.setDescription("description");
//		linea.setTaxCode(23);
//		linea.setDistCodeConcatenated("123546");
//		linea.setAttribute3("atribute3");
//		linea.setAttribute4("attribute4");
//		linea.setAttribute5("attribute5");
//		linea.setOrgId(1);
//		linea.setAssetsTrackingFlag("a");
//		linea.setAssetBookTypeCode("assetBookT");
//		linea.setAssetCategoryIdMajor("assetCategoryIdMajor");
//		linea.setAssetCategoryIdMinor("assetCategoryIdMinor");
//		
//		lineasEbsService.createLineasEbs(linea);
//		
//		encabezadoEbsService.getAllEncabezadoEbs();
		
		
	}

}