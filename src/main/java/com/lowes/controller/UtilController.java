package com.lowes.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.google.common.io.ByteStreams;
import com.lowes.dto.FacturaConXML;
import com.lowes.dto.FacturaDTO;
import com.lowes.dto.FacturaDesgloseDTO;
import com.lowes.entity.Aid;
import com.lowes.entity.CategoriaMayor;
import com.lowes.entity.CategoriaMenor;
import com.lowes.entity.Compania;
import com.lowes.entity.ComprobacionAnticipo;
import com.lowes.entity.ComprobacionDeposito;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.EncabezadoEbs;
import com.lowes.entity.Factura;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.FacturaGastoViaje;
import com.lowes.entity.FacturaKilometraje;
import com.lowes.entity.LineasEbs;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.ProveedorLibre;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAnticipoViaje;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.AidService;
import com.lowes.service.CategoriaMayorService;
import com.lowes.service.CategoriaMenorService;
import com.lowes.service.CompaniaService;
import com.lowes.service.ComprobacionAnticipoService;
import com.lowes.service.ComprobacionDepositoService;
import com.lowes.service.CuentaContableService;
import com.lowes.service.EncabezadoEbsService;
import com.lowes.service.FacturaGastoViajeService;
import com.lowes.service.FacturaKilometrajeService;
import com.lowes.service.FacturaService;
import com.lowes.service.LineasEbsService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.ProveedorLibreService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudAnticipoViajeService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudManagerService;
import com.lowes.service.SolicitudService;
import com.lowes.service.TipoFacturaService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.service.WSValidaFacturaService;
import com.lowes.util.Etiquetas;
import com.lowes.util.ParseFacturaConXML;
import com.lowes.util.Utilerias;
import com.lowes.util.WSFacturaMessageResponse;


@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UtilController {
	
	@Autowired
	private CompaniaService companiaService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private MonedaService monedaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private WSValidaFacturaService wsValidaFacturaService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private EncabezadoEbsService encabezadoEbsService;
	@Autowired
	private LineasEbsService lineasEbsService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private SolicitudManagerService solicitudManagerService;
	@Autowired
	private CategoriaMayorService categoriaMayor;
	@Autowired
	private CategoriaMenorService categoriaMenor;
	@Autowired
	private CuentaContableService cuentaContableService;
	@Autowired
	private AidService aidService;
	@Autowired
	private LocacionService locacionService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	@Autowired
	private TipoFacturaService tipoFacturaService;
	@Autowired
	private FacturaKilometrajeService facturaKilometrajeService;
	@Autowired
	private ComprobacionAnticipoService comprobacionAnticipoService;
	@Autowired
	private ProveedorLibreService proveedorLibreService;
	@Autowired
	private SolicitudAnticipoViajeService solicitudAnticipoViajeService;
	@Autowired
	private ComprobacionDepositoService comprobacionDepositoService;
	@Autowired
	private FacturaGastoViajeService facturaGastoViajeService;
	
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	ServletContext servletContext;
	
	
	private static final Logger logger = Logger.getLogger(UtilController.class);

	
	//testing
	@RequestMapping("visorPDF")
	public ModelAndView usuarios() {
		 
		//ServletContext fullPath = servletContext.get("/com/lowes/scheduler/spring-quartz.xml");
		new ClassPathXmlApplicationContext("/com/lowes/scheduler/spring-quartz.xml");
		//new FileSystemXmlApplicationContext(fullPath);
		
		return new ModelAndView("visorPDF");
	}
	
	// testing
	@RequestMapping("filterSelect")
	public ModelAndView filterSelect() {
		List<Usuario> usuarios = usuarioService.getAllUsuarios();
		FacturaConXML facturaConXML = new FacturaConXML();
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put("facturaConXML", facturaConXML);
		model.put("usuarios", usuarios);

		return new ModelAndView("filterSelect",model);
	}
	
	/**
	 * @author Adinfi
	 * @param MultipartHttpServletRequest request
	 * @param HttpServletResponse response
	 * @return String fecha en formato String
	 * METODO QUE EXTRAE LA INFORMACION DEL COMPROBANTE FISCAL EN FORMATO XML
	 */
	@RequestMapping(value = "/resolverXML", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public @ResponseBody ResponseEntity<String> readXML(MultipartHttpServletRequest request, HttpServletResponse response) {

		FacturaDTO factura = new FacturaDTO();
		HashMap<String, String> result = new HashMap<String, String>();
		String json = null;
		String responseMensaje = null;
		Integer responsecode = null;

		if (request != null) {

			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			String contentType = mpf.getContentType();

			if (contentType.equals("text/xml")) {
				try {
					InputStream is = mpf.getInputStream();
					
					/*
					 * Clonar InputStream para no tener problemas de cerrado de "streams"
					 * 
					 * */
					
					ByteArrayOutputStream baos = Utilerias.getCloneInputStream(is);
					
					InputStream is1 = new ByteArrayInputStream(baos.toByteArray()); 
					InputStream is2 = new ByteArrayInputStream(baos.toByteArray()); 
					
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					byte [] archivo = ByteStreams.toByteArray(is1);
					Document doc = dBuilder.parse(is2);
					
					ParseFacturaConXML parse = new ParseFacturaConXML();
					factura = parse.getFactura(doc);
					
					String nombreArchivo = mpf.getOriginalFilename();
					String rfcEmisor = factura.getRfcEmisor();
					String rfcReceptor = factura.getRfcReceptor();
					String importeTotal = String.valueOf(factura.getTotal());
					String token = companiaService.getTokenCompania(rfcReceptor);
					
					if(Integer.parseInt(parametroService.getParametroByName("validaWSDiversa").getValor()) == 1){
					List<WSFacturaMessageResponse> responseWS = wsValidaFacturaService.validaFactura(archivo,
							nombreArchivo, rfcEmisor, rfcReceptor, importeTotal, token);
					
					responseMensaje =  Utilerias.getMensajeWS(responseWS);
					if(responseMensaje != null){
						responsecode = responseWS.get(Etiquetas.CERO).getCode();
					}
					
					if (responsecode < Etiquetas.CERO) {
						responseMensaje = "No fue posible conectarse al servicio de validaci�n fiscal.";
					}
					
					}else{
						responsecode = 00;
					}
					
					if(responsecode == 00 || (Integer.parseInt(parametroService.getParametroByName("validaWSDiversa").getValor()) == 0)){
						
						//Validar Factura �nica en DB2
						Factura f = new Factura();
						f.setFolioFiscal(factura.getFolioFiscal());
						f.setFactura(factura.getFolio());
						f.setSerieFactura(factura.getSerie());
						List<Factura> factDuplicadas = validateFactura(f);
						StringBuilder repetidas = new StringBuilder();
						if(factDuplicadas.size() > 0){
						    for (Iterator<Factura> iterator = factDuplicadas.iterator(); iterator.hasNext();) {
						    	Solicitud solRepetida = iterator.next().getSolicitud();
						    	repetidas.append("["+solRepetida.getIdSolicitud()+"-"+ solRepetida.getUsuarioByIdUsuarioSolicita().getNombre() + " " + solRepetida.getUsuarioByIdUsuarioSolicita().getApellidoPaterno() +"]" + (iterator.hasNext() ? "," : ""));
						    }
						}
						
						if(factDuplicadas.size() == 0 || (Integer.parseInt(parametroService.getParametroByName("validaFacturaUnica").getValor()) == 0)){
							// transformar rfc_emisor en proveedor
							Integer idProveedor = null;
							List<Proveedor> proveedores = proveedorService.getAllProveedores();
							for (Proveedor p : proveedores) {
								if (p.getRfc().equals(factura.getRfcEmisor())) {
									idProveedor = p.getIdProveedor();
									break;
								}
							}

							// transformar rfc_receptor en compa�ia
							Integer idCompania = null;
							List<Compania> companias = companiaService.getAllCompania();
							for (Compania c : companias) {
								if (c.getRfc().equals(factura.getRfcReceptor())) {
									idCompania = c.getIdcompania();
									break;
								}
							}

							// transformar tipo_moneda en id moneda
							Integer idMoneda = null;
							List<Moneda> monedas = monedaService.getAllMoneda();
							for (Moneda m : monedas) {
								String[] descMoneda = m.getDescripcionCortaVariantes().split(",");
								for(String variante : descMoneda){
									//System.out.println(variante+"->"+factura.getMoneda());
									if(factura.getMoneda() != null && variante.toUpperCase().equals(factura.getMoneda().toUpperCase())){
										idMoneda = m.getIdMoneda();
										break;
									}
								}
							}
							
							String tipoFactura = Etiquetas.CERO.toString();
							if(factura.getTipoDeComprobante() != null){
								if(factura.getTipoDeComprobante().equals("ingreso")){
									tipoFactura = Etiquetas.UNO.toString();
								}else{
									tipoFactura = Etiquetas.DOS.toString();
								}
							}

							// // validar moneda
							// if(idMoneda == null){
							// result.put(, value)
							// }
							
							//Si no existe el proveedor en base de datos
							if(idProveedor == null){
								result.put("validxml", "false");
								result.put("faltaProveedor", "true");
								result.put("proveedorLibre", String.valueOf(factura.getNombreEmisor()));
								
								ProveedorLibre existe = proveedorLibreService.existeProveedor(factura.getRfcEmisor());
								if(existe != null)
									result.put("idProveedorLibre", String.valueOf(existe.getIdProveedorLibre()));
								else
									result.put("idProveedorLibre", String.valueOf(agregaNuevoProveedor(factura.getNombreEmisor(),factura.getRfcEmisor()).getIdProveedorLibre()));
							}
							else{
								result.put("validxml", "true");
								result.put("faltaProveedor", "false");
								result.put("idProveedor", String.valueOf(idProveedor));
							}
							
							result.put("idCompania", String.valueOf(idCompania));
							result.put("rsFactura", String.valueOf(companiaService.getCompania(idCompania).getDescripcion()));
							result.put("idMoneda", String.valueOf(idMoneda));
							result.put("folioFiscal", factura.getFolioFiscal());
							result.put("total", String.valueOf(factura.getTotal()));
							result.put("subTotal", String.valueOf(factura.getSubTotal()));
							result.put("rfcEmisor", factura.getRfcEmisor());
							result.put("serie", factura.getSerie());
							result.put("folio", factura.getFolio());
							result.put("concepto", factura.getConceptos());
							result.put("fechaEmision", factura.getFechaEmision());
							result.put("fechaPago", factura.getFechaPago());
							result.put("iva", String.valueOf(factura.getIva()));
							result.put("tua", String.valueOf(factura.getTua()));
							result.put("ish", String.valueOf(factura.getIsh()));
							result.put("yri", String.valueOf(factura.getYri()));
							result.put("otrosCargos", String.valueOf(factura.getOtrosCargos()));
							BigDecimal totalOtrosCargos = factura.getOtrosCargos().add(factura.getTua().add(factura.getIsh().add(factura.getYri())));
							result.put("otrosCargosImpuestos", String.valueOf(totalOtrosCargos));
							result.put("tasaIva", String.valueOf(factura.getTasaIva()));
							result.put("ieps", String.valueOf(factura.getIeps()));
							result.put("tasaIeps", String.valueOf(factura.getTasaIeps()));
							result.put("cantidadNeta", String.valueOf(factura.getCantidadNeta()));
							result.put("ivaRetenido", String.valueOf(factura.getIvaRetenido()));
							result.put("isrRetenido", String.valueOf(factura.getIsrRetenido()));
							// booleans
							result.put("incluyeIVA", String.valueOf(factura.isIncluyeIVA()));
							result.put("incluyeRetenciones", String.valueOf(factura.isIncluyeRetenciones()));
							// webservice
							result.put("wsmensaje",responseMensaje);
							result.put("wscode", responsecode.toString());
							//tipofactura
							result.put("tipoFactura", tipoFactura);
							result.put("validxml", "true");
						}else{
							result.put("validxml", "false");
							result.put("faltaProveedor", "false");
							String msgSolicitudes = (factDuplicadas.size() == 1 ? etiqueta.FAC_UNICA_LA_SOLICITUD : etiqueta.FAC_UNICA_LAS_SOLICITUDES);
							String msgerr = etiqueta.FAC_UNICA_MSG;
							msgerr = msgerr.replace("_factSerie", ((factDuplicadas.get(0).getSerieFactura() != null ? factDuplicadas.get(0).getSerieFactura()+"" : "") + "/"+factDuplicadas.get(0).getFactura()));
							msgerr = msgerr.replace("_msgSol", msgSolicitudes);
							result.put("wsmensaje",msgerr+" "+repetidas);
						}
					}
					else{
						result.put("validxml", "false");
						result.put("faltaProveedor", "false");
						result.put("wsmensaje",responseMensaje);
						result.put("wscode", responsecode.toString());
						
					}
				} catch (IOException | ParserConfigurationException | SAXException | DOMException | ParseException e) {
					e.printStackTrace();
				}

			} else {
				result.put("wsmensaje",etiqueta.NOXML);
				result.put("validxml", "false");
				result.put("faltaProveedor", "false");
			}

		}

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
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
	}

	/**
	 * @author Adinfi
	 * @param Date fecha fecha en fotmato Date
	 * @return String fecha en formato String
	 */
	public String parseFecha(Date fecha){
		String fechaString = null;
		SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfOut = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			fechaString = sdfOut.format(sdfIn.parse(fecha.toString()));
		} catch (ParseException e) {
			
		}
		return fechaString;
	}
	
	/**
	 * @author miguelr
	 * @param Factura La factura a validar.
	 * @return boolean true si es v�lida false si no lo es.
	 */
	public List<Factura> validateFactura(Factura fact){
		List<Factura> factRepetida = new ArrayList<>();
		//boolean isValid = false;
		
		if(fact.getFolioFiscal() != null)
		{
			//1er Validacion: UUID
			List<Factura> lstFactsUUID = facturaService.getFacturaByUUID(fact.getFolioFiscal());
			if(lstFactsUUID.size() > 0)
			{
				for(Factura factItem : lstFactsUUID){
					if(solicitudService.getSolicitud(factItem.getSolicitud().getIdSolicitud()).getEstadoSolicitud().getIdEstadoSolicitud() != Etiquetas.ID_ESTADO_SOLICITUD_CANCELADA){
						factRepetida.add(factItem);
					}
				}
				//isValid = false;
			}
		}
		else{
			//2da Validacion: Folio y Serie
			List<Factura> lstFactsFolio = facturaService.getFacturaByFolio(fact.getFactura());
			if (lstFactsFolio.size() > 0) {
				for (Factura factItem : lstFactsFolio) {
					if(fact.getSerieFactura().equals(factItem.getSerieFactura()))
					{
						factRepetida.add(factItem);
						//isValid = false;
					}
				}
			}
		}	
		return factRepetida;
	}
	
	@RequestMapping(value = "/validarPDF", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public @ResponseBody ResponseEntity<String> validarPDF(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		String json = null;
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();


		if (request != null) {

			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			String contentType = mpf.getContentType();
			
			if(contentType.equals("application/pdf")){
			  result.put("valido", true);
			}else{
		      result.put("valido", false);
			}
			
		}
		
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
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/validarIMG", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public @ResponseBody ResponseEntity<String> validarIMG(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		String json = null;
		HashMap<String, Boolean> result = new HashMap<String, Boolean>();


		if (request != null) {

			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			int size = request.getContentLength();
			
			String contentType = mpf.getContentType();
			//
			if(contentType.equals("image/jpeg") || contentType.equals("image/png") ){
				if(size <= Etiquetas.MAX_FILE_SIZE){
					  result.put("valido", true);
				}else{
					  result.put("valido", false);
				}
			}else{
		      result.put("valido", false);
			}
			
		}
		
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
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
	}
	
	public boolean enviarEBS(Solicitud solicitud, Usuario usuario) {
		int idTipoSolicitud = solicitud.getTipoSolicitud().getIdTipoSolicitud();
	
		// Solicitud tipo 1 -----------------------------------------------
		if (idTipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML) {
			enviarNoMercancias(solicitud);
		}
		// Solicitud tipo 2 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML) {
			enviarNoMercancias(solicitud);
		}
		// Solicitud tipo 3 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_REEMBOLSOS) {
			enviarReembolsos(solicitud);	
		}	
		// Solicitud tipo 4 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_CAJA_CHICA) {
			enviarReembolsos(solicitud);	
		}	
		// Solicitud tipo 5 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_KILOMETRAJE) {
			enviarKilometraje(solicitud);				
		}			
		// Solicitud tipo 6 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO) {
			enviarAnticipo(solicitud);			
		}			
		// Solicitud tipo 7 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO) {
			enviarComprobacionAnticipo(solicitud);		
		}		
		// Solicitud tipo 8 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE) {
			enviarAnticipoViaje(solicitud);			
		}
		// Solicitud tipo 9 -----------------------------------------------
		else if (idTipoSolicitud == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE) {
			enviarComprobacionAnticipoViaje(solicitud);			
		}
		
		return true;
	}
	
	
	// Funciones Enviar EBS -----------------------------------------------

	// [1] EBS SinXML ---------------------------------------------------------
	private void enviarNoMercancias(Solicitud solicitud) {
		
		Factura factura = solicitud.getFacturas().get(Etiquetas.CERO);
		EncabezadoEbs encabezado = new EncabezadoEbs();

		// se carga la solicitud
		encabezado.setSolicitudId(solicitud.getIdSolicitud());

		// se concatena la serie y el folio de la dactura.
		String serie = factura.getSerieFactura();
		String folio = factura.getFactura();
		StringBuilder invoiceNum = new StringBuilder();
		
		if(serie != null && serie.isEmpty() == false){
			invoiceNum.append(serie).append("-").append(folio);
		}else{
			invoiceNum.append(folio);
		}
		encabezado.setInvoiceNum(invoiceNum.toString());
		
        // descripcion EBS
		encabezado.setInvoiceTypeLookupCode(solicitud.getFacturas().get(Etiquetas.CERO).getTipoFactura().getDescripcionEbs());

		/* fecha de la factura unica de solicitud.*/
		encabezado.setInvoiceDate(factura.getFechaFactura());
					
		// numero del proveedor de la factura.
		encabezado.setVendorNum(String.valueOf(factura.getProveedor().getNumeroProveedor()));

		
		/*
		 * 
		 * Actualmente NO se tiene un criterio de validaci�n para el env�o
		 * del valor de Importe Total a la tabla EBS, es decir, el sistema
		 * env�a a EBS el valor de Importe Total = Total (6)
		 * 
		 * Se est� solicitando incluir un criterio de validaci�n para
		 * evaluar si el importe Total se env�a a EBS calculado o como
		 * actualmente se env�a, dando como resultado los siguientes
		 * criterios: Criterio 1: Si en alguno de los campos de IVA Retenido
		 * (4) o ISR Retenido (5) > 0, el valor a enviar a EBS ser�: Importe
		 * Total a enviar a EBS =�Subtotal (1) + IVA (2) + IEPS (3)
		 * 
		 * Criterio 2: Si en los campos de IVA Retenido (4) e ISR Retenido
		 * (5) = 0, el valor a enviar a EBS ser�: Importe Total a enviar a
		 * EBS =�Total (6)
		 * 
		 * Nota1: El c�lculo del segundo criterio NO se ve afectado, es
		 * decir, se env�a lo que hoy se est� enviando. Nota2: Los n�meros
		 * entre par�ntesis hacen referencia a la Imagen 1 del documento
		 */
		
		// monto total de la solicitud
		if(factura.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_NOTA_CREDITO){
		
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML){
				if ((solicitud.getFacturas().get(0).getIvaRetenido() != null && solicitud.getFacturas().get(0).getIvaRetenido().compareTo(BigDecimal.ZERO) > 0)	|| (solicitud.getFacturas().get(0).getIsrRetenido() != null && solicitud.getFacturas().get(0).getIsrRetenido().compareTo(BigDecimal.ZERO) > 0)) {
					encabezado.setInvoiceAmount(solicitud.getFacturas().get(0).getSubtotal().add(solicitud.getFacturas().get(0).getIva()).add(solicitud.getFacturas().get(0).getIeps()).negate());
				}else{
					encabezado.setInvoiceAmount(solicitud.getMontoTotal().negate());
				}
			}else{
				encabezado.setInvoiceAmount(solicitud.getMontoTotal().negate());
			}
			
			
		}else{
			
			if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML){
				if ((solicitud.getFacturas().get(0).getIvaRetenido() != null && solicitud.getFacturas().get(0).getIvaRetenido().compareTo(BigDecimal.ZERO) > 0)	|| (solicitud.getFacturas().get(0).getIsrRetenido() != null && solicitud.getFacturas().get(0).getIsrRetenido().compareTo(BigDecimal.ZERO) > 0)) {
					encabezado.setInvoiceAmount(solicitud.getFacturas().get(0).getSubtotal().add(solicitud.getFacturas().get(0).getIva()).add(solicitud.getFacturas().get(0).getIeps()));
				}else{
					encabezado.setInvoiceAmount(solicitud.getMontoTotal());
				}
			}else{
				encabezado.setInvoiceAmount(solicitud.getMontoTotal());
			}
			
		}

		// formato de moneda
		encabezado.setInvoiceCurrencyCode(solicitud.getMoneda().getDescripcionCorta());
		
		// track asset Control de cambios # 166
		if (factura.getTrackAsset() > Etiquetas.CERO_S) {
			/* Description Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
			ArrayList<String> tokens = new ArrayList<String>();
			tokens.add(etiqueta.PAR + ": " + factura.getPar());
			tokens.add(solicitud.getConceptoGasto());
			String descripcion = buildDescriptionEBS(tokens, 1);
			encabezado.setDescription(descripcion);
		} else {
			// concepto de la solicitud
			if(solicitud.getConceptoGasto().length() > 240){
				encabezado.setDescription(solicitud.getConceptoGasto().substring(0,240));
			}else{
				encabezado.setDescription(solicitud.getConceptoGasto());
			}
		}

		// folio fiscal de la factura
		encabezado.setAttribute15(factura.getFolioFiscal());

		// poner el status que es NEW para todas las solicitudes enviadas
		encabezado.setStatus(parametroService.getParametroByName("etiquetaEstatusEBS").getValor());

		// etiquetaOrigenEBS
		encabezado.setSource(parametroService.getParametroByName("etiquetaOrigenEBS").getValor());
        
		// metodo de pago
		encabezado.setPaymentMethodLookupCode(solicitud.getFormaPago().getDescripcionEbs());

		encabezado.setOrgId(solicitud.getCompania().getIdOrganizacion());

		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
		
		// metodo para agregar las lineas
	    addLineasEBS(solicitud, id);
	}

	// [3] EBS Reembolsos -----------------------------------------------------
	private void enviarReembolsos(Solicitud solicitud) {
		
		// solicitudes: Reembolsos y caja chica.
		EncabezadoEbs encabezado = new EncabezadoEbs();

		// se carga la solicitud
		encabezado.setSolicitudId(solicitud.getIdSolicitud());

		// fecha de la solicitud en formato ddmmaaa
		Date fecha = solicitud.getCreacionFecha();
		SimpleDateFormat sd1 = new SimpleDateFormat("ddMMyyyy");
		
		String date = sd1.toString().replaceAll("-","");
		
		StringBuilder invoiceNum = new StringBuilder();
		invoiceNum.append(sd1.format(fecha));
		encabezado.setInvoiceNum(invoiceNum.toString()+" - "+solicitud.getIdSolicitud());

		//Descripcion EBS -  para reembolsos y caja chica es stadard default
		//encabezado.setInvoiceTypeLookupCode(tipoFacturaService.getTipoFactura(Etiquetas.TRES).getDescripcionEbs());
		encabezado.setInvoiceTypeLookupCode(solicitud.getFacturas().get(Etiquetas.CERO).getTipoFactura().getDescripcionEbs());


		// fecha de la factura unica de solicitud.
		encabezado.setInvoiceDate(solicitud.getCreacionFecha());
		
		// numero del usuario solicitante.
		Integer vendorNum = null;
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.TRES) {
			vendorNum = solicitud.getUsuarioByIdUsuarioSolicita().getNumeroProveedor();
			}
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.CUATRO) {
			vendorNum = solicitud.getUsuarioByIdUsuarioAsesor().getNumeroProveedor();
			}
		
		if(vendorNum!= null)
			encabezado.setVendorNum(vendorNum.toString());
		else
			encabezado.setVendorNum(null);

		// monto total de la solicitud
		encabezado.setInvoiceAmount(solicitud.getMontoTotal());

		// formato de moneda
		encabezado.setInvoiceCurrencyCode(solicitud.getMoneda().getDescripcionCorta());

		// concepto de la solicitud
		if(solicitud.getConceptoGasto().length() > 240){
			encabezado.setDescription(solicitud.getConceptoGasto().substring(0,240));
		}else{
			encabezado.setDescription(solicitud.getConceptoGasto());
		}

		// folio fiscal de la factura (en este tipo de solicitudes va
		// vacio.)
		encabezado.setAttribute15(null);

		// poner el status que es NEW para todas las solicitudes enviadas
		encabezado.setStatus(parametroService.getParametroByName("etiquetaEstatusEBS").getValor());

		// etiquetaOrigenEBS
		encabezado.setSource(parametroService.getParametroByName("etiquetaOrigenEBS").getValor());

		// metodo de pago.
		encabezado.setPaymentMethodLookupCode(solicitud.getFormaPago().getDescripcionEbs());

		encabezado.setOrgId(solicitud.getCompania().getIdOrganizacion());

		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
		
		// metodo para agregar las lineas
		addLineasEBS(solicitud, id);
	}	
	
	// [5] EBS Kilometraje ----------------------------------------------------
	private void enviarKilometraje(Solicitud solicitud) {
		
		String fecha = "";
		fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		
		// solicitudes: Reembolsos y caja chica.
		EncabezadoEbs encabezado = new EncabezadoEbs();
		/* 1: se carga la solicitud */
		encabezado.setSolicitudId(solicitud.getIdSolicitud());
		/* 2: InvoiceNum */
		String fechaNumber = fecha.replace("/", "");
		StringBuilder invoiceNum = new StringBuilder();
		invoiceNum.append(solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append("  ").append(fechaNumber).append("  ").append(solicitud.getIdSolicitud());
		encabezado.setInvoiceNum(invoiceNum.toString());
		/* 3: TypeLookupCode */
		encabezado.setInvoiceTypeLookupCode(solicitud.getTipoSolicitud().getDescripcionInvoiceTypeEBS());
		/* 4: fecha de la factura unica de solicitud. */
		encabezado.setInvoiceDate(solicitud.getCreacionFecha());
		/* 5: numero de proveedor del empleado. */
		Integer vendorNum = solicitud.getUsuarioByIdUsuarioSolicita().getNumeroProveedor();
		if(vendorNum != null)
			encabezado.setVendorNum(vendorNum.toString());
		else
			encabezado.setVendorNum(null);
		/* 6: monto total de la solicitud */
		encabezado.setInvoiceAmount(solicitud.getMontoTotal());
		/* 7: formato de moneda */
		encabezado.setInvoiceCurrencyCode(solicitud.getMoneda().getDescripcionCorta());
		/* 8: Descripci�n de la solicitud */
		Usuario usuario = solicitud.getUsuarioByIdUsuario();
		String name = usuario.getNombreCompletoUsuario();
		String descripcion = solicitud.getTipoSolicitud().getPrefijoDescriptionEBS()+" / "+name;
		encabezado.setDescription(descripcion);
		/* 9: folio fiscal de la factura (en este tipo de solicitudes va vacio.) en documento dice NULL*/
		encabezado.setAttribute15(null);
		/* 10: poner el status que es NEW para todas las solicitudes enviadas */
		encabezado.setStatus(parametroService.getParametroByName("etiquetaEstatusEBS").getValor());
		/* 11: etiquetaOrigenEBS */
		encabezado.setSource(parametroService.getParametroByName("etiquetaOrigenEBS").getValor());
		/* 12: metodo de pago. */
		encabezado.setPaymentMethodLookupCode(solicitud.getFormaPago().getDescripcionEbs());
		/* 13: metodo de pago. */
		encabezado.setOrgId(solicitud.getCompania().getIdOrganizacion());
		
		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
		
		// metodo para agregar las lineas
		addLineasEBS(solicitud, id);
	}	
	
	// [6] EBS Anticipo -------------------------------------------------------
	private void enviarAnticipo(Solicitud solicitud) {
		String fecha = "";
		fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		// solicitudes: Anticipo.
		EncabezadoEbs encabezado = new EncabezadoEbs();

		/* 1: se carga la solicitud */
		encabezado.setSolicitudId(solicitud.getIdSolicitud());
		/* 2: InvoiceNum */
		String fechaNumber = fecha.replace("/", "");
		StringBuilder invoiceNum = new StringBuilder();
		invoiceNum.append(solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append(" ").append(fechaNumber);
		encabezado.setInvoiceNum(invoiceNum.toString());
		/* 3: TypeLookupCode */
		encabezado.setInvoiceTypeLookupCode(solicitud.getTipoSolicitud().getDescripcionInvoiceTypeEBS());
		/* 4: Fecha de la factura unica de solicitud. */
		encabezado.setInvoiceDate(solicitud.getCreacionFecha());
		/* 5: Numero de asesor/proveedor */
		Integer vendorNum;
		String prooveedorDescripcion;
//		if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
//			vendorNum = solicitud.getUsuarioByIdUsuarioSolicita().getNumeroProveedor();
//			Usuario usuario = solicitud.getUsuarioByIdUsuario(); 
//			prooveedorDescripcion = usuario.getNombreCompletoUsuario();
//		}else{
//			vendorNum = solicitud.getProveedor().getNumeroProveedor();
//			prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
//		}
		vendorNum = solicitud.getProveedor().getNumeroProveedor();
		prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
		if(vendorNum!= null)
			encabezado.setVendorNum(vendorNum.toString());
		else
			encabezado.setVendorNum(null);
		/* 6: monto total de la solicitud */
		encabezado.setInvoiceAmount(solicitud.getMontoTotal());
		/* 7: Formato de moneda */
		encabezado.setInvoiceCurrencyCode(solicitud.getMoneda().getDescripcionCorta());
		/* 8: Descripci�n de la solicitud		
		   Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(solicitud.getConceptoGasto());
		tokens.add(prooveedorDescripcion);
		//Tokens + Index de la descripci�n.
		String descripcion = buildDescriptionEBS(tokens, 0);
		encabezado.setDescription(descripcion);
		/* 9: Folio fiscal de la factura (en este tipo de solicitudes va vacio.) */
		encabezado.setAttribute15(null);
		/* 10: Poner el status que es NEW para todas las solicitudes enviadas */
		encabezado.setStatus(parametroService.getParametroByName("etiquetaEstatusEBS").getValor());
		/* 11: etiquetaOrigenEBS */
		encabezado.setSource(parametroService.getParametroByName("etiquetaOrigenEBS").getValor());
		/* 12: metodo de pago. */
		encabezado.setPaymentMethodLookupCode(solicitud.getFormaPago().getDescripcionEbs());
		/* 13: Org ID */
		encabezado.setOrgId(solicitud.getCompania().getIdOrganizacion());
		
		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
		// metodo para agregar las lineas
		addLineasEBS(solicitud, id);
	}

	// [7] EBS Comprobaci�n Anticipo ------------------------------------------
	private void enviarComprobacionAnticipo(Solicitud solicitud) {
		// Generar encabezado
		
		String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		
		Integer vendorNum;
		String prooveedorDescripcion;
//		if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
//			vendorNum = solicitud.getUsuarioByIdUsuarioSolicita().getNumeroProveedor();
//			prooveedorDescripcion = solicitud.getUsuarioByIdUsuario().getNombreCompletoUsuario();
//		}else{
//			vendorNum = solicitud.getProveedor().getNumeroProveedor();
//			prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
//		}
		vendorNum = solicitud.getProveedor().getNumeroProveedor();
		prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
		
		// solicitudes: ComprobacionAnticipo.
		EncabezadoEbs encabezado = new EncabezadoEbs();
		
		/* 1: se carga la solicitud */
		encabezado.setSolicitudId(solicitud.getIdSolicitud());
		/* 2: InvoiceNum */
		String fechaNumber = fecha.replace("/", "");
		StringBuilder invoiceNum = new StringBuilder();
		invoiceNum.append(solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append("  ").append(fechaNumber).append("  ").append(parametroService.getParametroByName("etiquetaInvoiceCompEBS").getValor());
		encabezado.setInvoiceNum(invoiceNum.toString());
		/* 3: TypeLookupCode */
		encabezado.setInvoiceTypeLookupCode(solicitud.getTipoSolicitud().getDescripcionInvoiceTypeEBS());
		/* 4: fecha de la factura unica de solicitud. */
		encabezado.setInvoiceDate(solicitud.getCreacionFecha());
		/* 5: numero de asesor/proveedor */
		if(vendorNum!= null)
			encabezado.setVendorNum(vendorNum.toString());
		else
			encabezado.setVendorNum(null);
		/* 6: Monto total de la solicitud */
		encabezado.setInvoiceAmount(solicitud.getMontoTotal());
		/* 7: formato de moneda */
		encabezado.setInvoiceCurrencyCode(solicitud.getMoneda().getDescripcionCorta());
		/* 8: Descripci�n de la solicitud. Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(solicitud.getTipoSolicitud().getPrefijoDescriptionEBS());
		tokens.add(solicitud.getConceptoGasto());
		tokens.add(prooveedorDescripcion);
		//Tokens + Index de la descripci�n.
		String descripcion = buildDescriptionEBS(tokens, 1);
		encabezado.setDescription(descripcion);
		/* 9: Folio fiscal de la factura (en este tipo de solicitudes va vacio.)*/
		encabezado.setAttribute15(null);
		/* 10: Poner el status que es NEW para todas las solicitudes enviadas */
		encabezado.setStatus(parametroService.getParametroByName("etiquetaEstatusEBS").getValor());
		/* 11: etiquetaOrigenEBS */
		encabezado.setSource(parametroService.getParametroByName("etiquetaOrigenEBS").getValor());
		/* 12: Metodo de pago. */
		encabezado.setPaymentMethodLookupCode(solicitud.getFormaPago().getDescripcionEbs());
		/* 13: Org ID */
		encabezado.setOrgId(solicitud.getCompania().getIdOrganizacion());
		
		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
		
		enviarComprobacionAnticipoDeposito(encabezado, solicitud);
		
		// metodo para agregar las lineas
		addLineasEBS(solicitud, id);
		
		
		
	}	
	
	// [7.1] EBS ComprobacionAnticipoDeposito ---------------------------------------------------------
		private void enviarComprobacionAnticipoDeposito(EncabezadoEbs encabezado, Solicitud solicitud) {
			// Generar encabezado
			List<ComprobacionDeposito> depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(encabezado.getSolicitudId());
//			for (ComprobacionDeposito deposito : depositos) {
			ComprobacionDeposito deposito = new ComprobacionDeposito();
			if(depositos != null && !depositos.isEmpty()){
				deposito = depositos.get(0);
				
				String fecha = Utilerias.convertDateFormat(deposito.getCreacionFecha());
				String fechaDepositoAnticipo;
				Solicitud anticipoGastoViaje;
				
				ComprobacionAnticipo relacionCompAnt = comprobacionAnticipoService.getAnticiposByComprobacion(encabezado.getSolicitudId()).get(0);
				//Carga el anticipo
				anticipoGastoViaje = relacionCompAnt.getSolicitudByIdSolicitudAnticipo();
				
				fechaDepositoAnticipo = Utilerias.convertDateFormat(anticipoGastoViaje.getFechaPago());
				
				/* 2: InvoiceNum */
				String fechaNumber = fecha.replace("/", "");
				StringBuilder invoiceNum = new StringBuilder();
				invoiceNum.append(solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append("  ").append(fechaNumber).append("  ").append(parametroService.getParametroByName("etiquetaInvoiceCompDepositoEBS") != null ? parametroService.getParametroByName("etiquetaInvoiceCompDepositoEBS").getValor(): null);
				encabezado.setInvoiceNum(invoiceNum.toString());
				/* 4: fecha de la factura unica de solicitud. */
				encabezado.setInvoiceDate(deposito.getCreacionFecha());
				/* 6: Monto total de la solicitud */
				encabezado.setInvoiceAmount(deposito.getMontoDeposito());
				/* 8: Descripci�n de la solicitud. Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
				ArrayList<String> tokens = new ArrayList<String>();
				tokens.add(parametroService.getParametroByName("etiquetaFichaDepositoEBS") != null ? parametroService.getParametroByName("etiquetaFichaDepositoEBS").getValor() : null);
				tokens.add(Etiquetas.F_DEL + fechaNumber);
				//Tokens + Index de la descripci�n.
				String descripcion = buildDescriptionEBS(tokens, 1);
				encabezado.setDescription(descripcion);
				/* 15: PrepayNum */
//				encabezado.setPrepayNum(null);
				/* 16: Prepay Apply Amount */
//				encabezado.setPrepayApplyAmount(null);
				
				Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
			}
				//logger("deposito EBS: " + id);
//			}
		}
	
	// [8] EBS AnticipoViaje ---------------------------------------------------------
	private void enviarAnticipoViaje (Solicitud solicitud) {
		
		String fecha = "";
		fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		
		// solicitudes: Anticipo.
		EncabezadoEbs encabezado = new EncabezadoEbs();

		/* 1: se carga la solicitud */
		encabezado.setSolicitudId(solicitud.getIdSolicitud());
		/* 2: InvoiceNum */
		String fechaNumber = fecha.replace("/", "");
		StringBuilder invoiceNum = new StringBuilder();
		invoiceNum.append(solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append(" ").append(fechaNumber).append(" ").append(solicitud.getIdSolicitud());
		encabezado.setInvoiceNum(invoiceNum.toString());
		/* 3: Descripcion EBS */
		encabezado.setInvoiceTypeLookupCode(solicitud.getTipoSolicitud().getDescripcionInvoiceTypeEBS());
		/* 4: Fecha de la factura unica de solicitud. */
		encabezado.setInvoiceDate(solicitud.getCreacionFecha());
		/* 5: Numero de asesor/proveedor */
//		Integer vendorNum = solicitud.getUsuarioByIdUsuarioSolicita().getNumeroProveedor();
		Integer vendorNum = null;
		if(solicitud.getProveedor() != null){
			vendorNum = solicitud.getProveedor().getNumeroProveedor();
		}
		if(vendorNum!= null)
			encabezado.setVendorNum(vendorNum.toString());
		else
			encabezado.setVendorNum(null);
		/* 6: Monto total de la solicitud */
		encabezado.setInvoiceAmount(solicitud.getMontoTotal());
		/* 7: Formato de moneda */
		encabezado.setInvoiceCurrencyCode(solicitud.getMoneda().getDescripcionCorta());
		/* 8: Descripci�n de la solicitud 
		Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(solicitud.getConceptoGasto());
		tokens.add(solicitud.getLocacion().getNumeroDescripcionLocacion());
		tokens.add(String.valueOf(solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(solicitud.getIdSolicitud()).getNumPersonas()));
		//Tokens + Index de la descripci�n.
		String descripcion = buildDescriptionEBS(tokens, 0);
		encabezado.setDescription(descripcion);
		/* 9: Folio fiscal de la factura (en este tipo de solicitudes va vacio.) */
		encabezado.setAttribute15(null);
		// 10: Poner el status que es NEW para todas las solicitudes enviadas
		encabezado.setStatus(parametroService.getParametroByName("etiquetaEstatusEBS").getValor());
		// 11: etiquetaOrigenEBS
		encabezado.setSource(parametroService.getParametroByName("etiquetaOrigenEBS").getValor());
		// 12: Metodo de pago.
		encabezado.setPaymentMethodLookupCode(solicitud.getFormaPago().getDescripcionEbs());
		// 13: Org ID
		encabezado.setOrgId(solicitud.getCompania().getIdOrganizacion());
		// 14: Workflow
		// 15: Terms
		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
//		
		// metodo para agregar las lineas
		addLineasEBS(solicitud, id);
	}
	
	// [9] EBS ComprobacionAnticipoViaje ---------------------------------------------------------
	private void enviarComprobacionAnticipoViaje(Solicitud solicitud) {		
		// Generar encabezado
		String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		Integer vendorNum;
		Solicitud anticipoGastoViaje;
		
		//Carga relacion anticipo/comprobacion
		boolean isReembolso = false;
		ComprobacionAnticipo relacionCompAnt = comprobacionAnticipoService.getAnticiposByComprobacion(solicitud.getIdSolicitud()).get(0);
		//Carga el anticipo
		anticipoGastoViaje = relacionCompAnt.getSolicitudByIdSolicitudAnticipo();
		//identifica si tiene anticipo, o se gener� sin anticipo (si el id de comprobacion es igual al del anticipo, entonces entro sin anticipo)
		if(relacionCompAnt.getSolicitudByIdSolicitudAnticipo().getIdSolicitud() == relacionCompAnt.getSolicitudByIdSolicitudComprobacion().getIdSolicitud())
			isReembolso = true;
		
		// Getting proveedor
		vendorNum = solicitud.getUsuarioByIdUsuarioSolicita().getNumeroProveedor();
		
		// solicitudes: ComprobacionAnticipo.
		EncabezadoEbs encabezado = new EncabezadoEbs();
		
		/* 1: se carga la solicitud */
		encabezado.setSolicitudId(solicitud.getIdSolicitud());
		/* 2: InvoiceNum */
		String fechaNumber = fecha.replace("/", "");
		StringBuilder invoiceNum = new StringBuilder();
		invoiceNum.append(solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append(" ").append(fechaNumber).append(" ").append(parametroService.getParametroByName("etiquetaCompEBS") !=null ? parametroService.getParametroByName("etiquetaCompEBS").getValor():null);
		encabezado.setInvoiceNum(invoiceNum.toString());
		/* 3: TypeLookupCode */
		encabezado.setInvoiceTypeLookupCode(solicitud.getTipoSolicitud().getDescripcionInvoiceTypeEBS());
		/* 4: fecha de la factura unica de solicitud. */
		encabezado.setInvoiceDate(solicitud.getCreacionFecha());
		/* 5: numero de asesor/proveedor */
		if(vendorNum!= null)
			encabezado.setVendorNum(vendorNum.toString());
		else
			encabezado.setVendorNum(null);
		/* 6: Monto total de la solicitud */
		encabezado.setInvoiceAmount(solicitud.getMontoTotal());
		/* 7: formato de moneda */
		encabezado.setInvoiceCurrencyCode(solicitud.getMoneda().getDescripcionCorta());
		/* 8: Descripci�n de la solicitud. Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		if(isReembolso)
			tokens.add(parametroService.getParametroByName("etiquetaDescriptionReembolsoEBS") !=null ? parametroService.getParametroByName("etiquetaDescriptionReembolsoEBS").getValor():null);
		else
			tokens.add(solicitud.getTipoSolicitud().getPrefijoDescriptionEBS());
		
		tokens.add(solicitud.getConceptoGasto());
		tokens.add(solicitud.getLocacion().getNumeroDescripcionLocacion());
		
		SolicitudAnticipoViaje sav = solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(solicitud.getIdSolicitud());
		String numeroPersonas = "0";
		if(sav != null){
			 String.valueOf(sav.getNumPersonas());
		}
		tokens.add(numeroPersonas);
		
		//Tokens + Index de la descripci�n.
		String descripcion = buildDescriptionEBS(tokens, 1);
		encabezado.setDescription(descripcion);
		/* 9: Folio fiscal de la factura (en este tipo de solicitudes va vacio.)*/
		encabezado.setAttribute15(null);
		/* 10: Poner el status que es NEW para todas las solicitudes enviadas */
		encabezado.setStatus(parametroService.getParametroByName("etiquetaEstatusEBS").getValor());
		/* 11: etiquetaOrigenEBS */
		encabezado.setSource(parametroService.getParametroByName("etiquetaOrigenEBS").getValor());
		/* 12: Metodo de pago. */
		encabezado.setPaymentMethodLookupCode(solicitud.getFormaPago().getDescripcionEbs());
		/* 13: Org ID */
		encabezado.setOrgId(solicitud.getCompania().getIdOrganizacion());
		// 14: Workflow
		// 15: Terms
//		/* 15: PrepayNum */
//		String fechaPrepayNum = Utilerias.convertDateFormat(anticipoGastoViaje.getCreacionFecha());
//		StringBuilder PrepayNum = new StringBuilder();
//		PrepayNum.append(anticipoGastoViaje.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append(" - ").append(fechaPrepayNum.replace("/", "")).append(" - ").append(anticipoGastoViaje.getIdSolicitud());
//		encabezado.setPrepayNum(PrepayNum.toString());
//		/* 16: Prepay Apply Amount */
//		encabezado.setPrepayApplyAmount(solicitud.getMontoTotal());
		
		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
		
		if(comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(solicitud.getIdSolicitud()).size() > 0){
			enviarComprobacionAnticipoViajeDeposito(encabezado);
		}
		
		// metodo para agregar las lineas
		addLineasEBS(solicitud, id);
	}
	
	// [9.1] EBS ComprobacionAnticipoViajeDeposito ---------------------------------------------------------
	private void enviarComprobacionAnticipoViajeDeposito(EncabezadoEbs encabezado) {
		// Generar encabezado
		ComprobacionDeposito deposito = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(encabezado.getSolicitudId()).get(0);
		String fecha = Utilerias.convertDateFormat(deposito.getCreacionFecha());
		String fechaDepositoAnticipo;
		Solicitud anticipoGastoViaje;
		
		ComprobacionAnticipo relacionCompAnt = comprobacionAnticipoService.getAnticiposByComprobacion(encabezado.getSolicitudId()).get(0);
		//Carga el anticipo
		anticipoGastoViaje = relacionCompAnt.getSolicitudByIdSolicitudAnticipo();
		
		fechaDepositoAnticipo = Utilerias.convertDateFormat(anticipoGastoViaje.getFechaPago());
		String fechaCreacionAnticipo = Utilerias.convertDateFormat(anticipoGastoViaje.getCreacionFecha());
		/* 2: InvoiceNum */
		String fechaNumber = fecha.replace("/", "");
		StringBuilder invoiceNum = new StringBuilder();
		invoiceNum.append(anticipoGastoViaje.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append(" DEPOSITO ").append(fechaNumber);
		encabezado.setInvoiceNum(invoiceNum.toString());
		/* 6: Monto total de la solicitud */
		encabezado.setInvoiceAmount(deposito.getMontoDeposito());
		/* 8: Descripci�n de la solicitud. Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add("FICHA DEPOSITO ANTICIPO " + anticipoGastoViaje.getIdSolicitud());
		tokens.add("DEL " + fechaCreacionAnticipo.replace("/", ""));
		//Tokens + Index de la descripci�n.
		String descripcion = buildDescriptionEBS(tokens, 1);
		encabezado.setDescription(descripcion);
		// 14: Workflow
		// 15: Terms
		/* 15: PrepayNum */
//		encabezado.setPrepayNum(null);
		/* 16: Prepay Apply Amount */
//		encabezado.setPrepayApplyAmount(null);
		
		Integer id = encabezadoEbsService.createEncabezadoEbs(encabezado);
		//logger("deposito EBS: " + id);
	}
			
	// Fin funciones Enviar EBS -------------------------------------------

	private void addLineasEBS(Solicitud solicitud, Integer encabezadoID) {
		
		//encabezadoID = 36;

		// nomercancias
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() <= Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML) {

			Factura factura = solicitud.getFacturas().get(Etiquetas.CERO);

			// guardar lineas por desglose de la solicitud
			int lineaItem = Etiquetas.UNO;
			if (factura.getFacturaDesgloses() != null && factura.getFacturaDesgloses().isEmpty() == false) {
				for (FacturaDesglose desglose : factura.getFacturaDesgloses()) {
					LineasEbs linea = new LineasEbs();
					// carga de linea con informacion base para todas las lineas
					linea = loadLineaEBSNoMercancias(solicitud, encabezadoID);
					// set especifico de datos para cada linea
					linea.setLineNumber(lineaItem);
					linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
					
					// monto total de la solicitud - Control de cambios 23/06/2016 - miguelr # 172
					if(factura.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_NOTA_CREDITO)
						linea.setAmount(desglose.getSubtotal().negate());
					else
						linea.setAmount(desglose.getSubtotal());

					
					//control de cambios 18/08/16
					if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML){
				      linea.setTaxCode(null);
					}else{
					  //Control de cambios 23/06/2016 - miguelr
					  linea.setTaxCode(factura.getPorcentajeIva() != null ? factura.getPorcentajeIva().intValue() : null);
					}
					
					// track asset Control de cambios # 166
					if (factura.getTrackAsset() > Etiquetas.CERO_S) {
						/* Description Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
						ArrayList<String> tokens = new ArrayList<String>();
						String aid = desglose.getAid() != null ? desglose.getAid().getAid() : "";
						tokens.add(etiqueta.PAR + ": " + factura.getPar() + " " + etiqueta.AID + ": " + aid);
						tokens.add(desglose.getConcepto());
						String descripcion = buildDescriptionEBS(tokens, 1);
						linea.setDescription(descripcion);
					} else {
						if(desglose.getConcepto().length() > 240){
							linea.setDescription(desglose.getConcepto().substring(0, 240));
						}else{
							linea.setDescription(desglose.getConcepto());
						}
					}
					
					String locacionNumero = String.valueOf(desglose.getLocacion().getNumero());
					// contenacion de
					// numerodecompa�ia-numerodelocacion-00-numerocuentacontable.
					linea.setDistCodeConcatenated(
							factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero
									+ "-" + "00-" + desglose.getCuentaContable().getNumeroCuentaContable().toString()+ "-0000");
					// si tiene track as asset entonces cargamos cat mayor y
					// menor
					if (linea.getAssetsTrackingFlag().equals(parametroService.getParametroByName("etiquetaSiEBS").getValor())) {
						linea.setAssetCategoryIdMajor(desglose.getCategoriaMayor() != null ? desglose.getCategoriaMayor().getDescripcion() : "");
						linea.setAssetCategoryIdMinor(desglose.getCategoriaMenor() != null ? desglose.getCategoriaMenor().getDescripcion() : "");
					}
					lineasEbsService.createLineasEbs(linea);
					lineaItem++;
				}
			}

		} else if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS || solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA) {
			// reembolsos caja chica.
			if (solicitud.getFacturas() != null && solicitud.getFacturas().isEmpty() == false) {
				// se guarda la lista de facturas de la solicitud cada una en
				// una linea
				int lineaItem = Etiquetas.UNO;
				for (Factura factura : solicitud.getFacturas()) {
					// set especifico de datos para cada linea
					
					/*
					 * 
					 * LINEAS DE IVA PARA REEMBOLSOS Y CAJA CHICA.
					 * 
					 * */
					
					
					LineasEbs linea = new LineasEbs();
					//SE CARGA EL OBJETO CON LA INFO GENERAL.
					linea = loadLineaReembolsosCajaChica(factura, encabezadoID, lineaItem, solicitud); 
					lineasEbsService.createLineasEbs(linea);
					
					lineaItem++;
					
					/*
					 * 
					 * LINEAS DE IVA PARA REEMBOLSOS Y CAJA CHICA. (IMPUESTOS)
					 * 
					 * */
					
					

					// si cuenta con iva se agrega una linea mas
					if (factura.getIva() != null && factura.getIva().compareTo(BigDecimal.ZERO) > 0) {
						
						//SE CARGA EL OBJETO CON LA INFO GENERAL Y ESPECIFICA DE IMPUESTOS.
						linea = new LineasEbs();
						linea = loadLineaReembolsosCajaChica(factura, encabezadoID, lineaItem, solicitud);
						
						// set especifico de datos para cada linea
						linea.setLineNumber(lineaItem);
						linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaTaxEBS").getValor());
						linea.setAmount(factura.getIva());
						BigDecimal porcentajeIva = factura.getPorcentajeIva();
						if (porcentajeIva != null) {
							linea.setTaxCode(factura.getPorcentajeIva().intValue());
						}
						else {
							linea.setTaxCode(null);
						}
						
						String locacionNumero = "0";
						if(factura.getLocacion() != null){
						  locacionNumero = String.valueOf(factura.getLocacion().getNumero());
						}
						// numerodecompa�ia-numerodelocacion-00-numerocuentacontable.
						

						
						linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ 
							"-" + "00-" + parametroService.getParametroByName("ccIVA").getValor()+"-0000" );
						
						lineasEbsService.createLineasEbs(linea);
						lineaItem++;
					}

					// si cuenta con ieps se agrega una linea mas
					if (factura.getIeps() != null && factura.getIeps().compareTo(BigDecimal.ZERO) > 0) {
						
						//SE CARGA EL OBJETO CON LA INFO GENERAL Y ESPECIFICA DE IMPUESTOS.
						linea = new LineasEbs();
						linea = loadLineaReembolsosCajaChica(factura, encabezadoID, lineaItem, solicitud);
						
						// set especifico de datos para cada linea
						linea.setLineNumber(lineaItem);
						linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaTaxEBS").getValor());
						linea.setAmount(factura.getIeps());
						BigDecimal porcentajeIeps = factura.getPorcentajeIva();
						if (porcentajeIeps != null) {
							linea.setTaxCode(factura.getPorcentajeIva().intValue());
						}
						else {
							linea.setTaxCode(null);
						}
						
						linea.setTaxCode(factura.getPorcentajeIeps().intValue());
						String locacionNumero = "0";
						if(factura.getLocacion() != null){
						  locacionNumero = String.valueOf(factura.getLocacion().getNumero());
						}
						// numerodecompa�ia-numerodelocacion-00-numerocuentacontable.
						linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ 
							"-" + "00-" + parametroService.getParametroByName("ccIEPS").getValor() + "-0000");
						lineasEbsService.createLineasEbs(linea);
						lineaItem++;
					}
					
					// si cuenta con ivaRetenido se agrega una linea mas
					if (factura.getIvaRetenido() != null && factura.getIvaRetenido().compareTo(BigDecimal.ZERO) > 0) {
						
						//SE CARGA EL OBJETO CON LA INFO GENERAL Y ESPECIFICA DE IMPUESTOS.
						linea = new LineasEbs();
						linea = loadLineaReembolsosCajaChica(factura, encabezadoID, lineaItem, solicitud);
						
						// set especifico de datos para cada linea
						linea.setLineNumber(lineaItem);
						linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
						linea.setAmount(factura.getIvaRetenido().negate());
						linea.setTaxCode(null);
//						BigDecimal porcentajeIvaRetenido = factura.getPorcentajeIva();
//						if (porcentajeIvaRetenido != null) {
//							linea.setTaxCode(factura.getPorcentajeIva().intValue());
//						}
//						else {
//							linea.setTaxCode(null);
//						}
						//linea.setTaxCode(0);
						String locacionNumero = "0";
						if(factura.getLocacion() != null){
						  locacionNumero = String.valueOf(factura.getLocacion().getNumero());
						}
						// numerodecompa�ia-numerodelocacion-00-numerocuentacontable.
						linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ 
							"-" + "00-" + parametroService.getParametroByName("ccRetIVA").getValor() + "-0000");
						lineasEbsService.createLineasEbs(linea);
						lineaItem++;
					}
					
					
					// si cuenta con isrretenido se agrega una linea mas
					if (factura.getIsrRetenido() != null && factura.getIsrRetenido().compareTo(BigDecimal.ZERO) > 0) {
						
						//SE CARGA EL OBJETO CON LA INFO GENERAL Y ESPECIFICA DE IMPUESTOS.
						linea = new LineasEbs();
						linea = loadLineaReembolsosCajaChica(factura, encabezadoID, lineaItem, solicitud);
						
						// set especifico de datos para cada linea
						linea.setLineNumber(lineaItem);
						linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
						linea.setAmount(factura.getIsrRetenido().negate());
						linea.setTaxCode(null);
//						BigDecimal porcentajeIsrRetenido = factura.getPorcentajeIva();
//						if (porcentajeIsrRetenido != null) {
//							linea.setTaxCode(factura.getPorcentajeIva().intValue());
//						}
//						else {
//							linea.setTaxCode(null);
//						}
						
						String locacionNumero = "0";
						if(factura.getLocacion() != null){
						  locacionNumero = String.valueOf(factura.getLocacion().getNumero());
						}
						// numerodecompa�ia-numerodelocacion-00-numerocuentacontable.
						linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ 
							"-" + "00-" + parametroService.getParametroByName("ccRetISR").getValor() + "-0000");
						lineasEbsService.createLineasEbs(linea);
						lineaItem++;
					}

				}

			}
		}
		// Kilometraje
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_KILOMETRAJE) {
			int lineaItem = Etiquetas.UNO;
			List<FacturaKilometraje> facturaKmLst = facturaKilometrajeService.getAllFacturaKilometrajeByIdSolicitud(solicitud.getIdSolicitud());
			for(FacturaKilometraje factKm : facturaKmLst){
				LineasEbs linea = loadLineaKilometraje(encabezadoID, lineaItem, solicitud, factKm);
				lineasEbsService.createLineasEbs(linea);
				lineaItem ++;
			}
		}
		// Anticipo
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_ANTICIPO) {
			int lineaItem = Etiquetas.UNO;
			LineasEbs linea = loadLineaAnticipo(encabezadoID, lineaItem, solicitud);
			lineasEbsService.createLineasEbs(linea);
		}
		
		// Comprobaci�n anticipo
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO) {
			
			int lineaItem = Etiquetas.UNO;
			if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
				for(Factura factura : solicitud.getFacturas()){
					LineasEbs linea = loadLineaComprobacionAnticipo(factura, null, encabezadoID, lineaItem, solicitud);
					lineasEbsService.createLineasEbs(linea);
					lineaItem++;
				}
			}else{
				Factura factura = solicitud.getFacturas().get(Etiquetas.CERO);
				if (factura.getFacturaDesgloses() != null && factura.getFacturaDesgloses().isEmpty() == false) {
					for (FacturaDesglose desglose : factura.getFacturaDesgloses()) {
						LineasEbs linea = loadLineaComprobacionAnticipo(factura, desglose, encabezadoID, lineaItem, solicitud);
						lineasEbsService.createLineasEbs(linea);
						lineaItem++;
					}
				}
				
			}
			//Linea de la comprobacion del Deposito
			Factura factura = solicitud.getFacturas().get(Etiquetas.CERO);
			if (factura.getFacturaDesgloses() != null) {
				List<ComprobacionDeposito> depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(encabezadoID);
				ComprobacionDeposito deposito = new ComprobacionDeposito();
				if(depositos != null && !depositos.isEmpty()){
					deposito = depositos.get(0);
					LineasEbs linea = loadLineaComprobacionAnticipoDeposito(factura, null, encabezadoID, lineaItem, solicitud, deposito);
					lineasEbsService.createLineasEbs(linea);
				}
			}
		}
		
		// Anticipo Viaje
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE) {
			int lineaItem = Etiquetas.UNO;
			LineasEbs linea = loadLineaAnticipoViaje(encabezadoID, lineaItem, solicitud);
			lineasEbsService.createLineasEbs(linea);
		}
		
		// Comprobacion Anticipo Viaje
		if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE) {	
			int lineaItem = Etiquetas.UNO;
			List<Factura> facturaLst = facturaService.getAllFacturaBySolicitud(solicitud.getIdSolicitud());
			for(Factura fact : facturaLst){
				LineasEbs linea = loadLineaComprobacionGastoViaje(encabezadoID, lineaItem, solicitud, fact);
				lineasEbsService.createLineasEbs(linea);
				lineaItem ++;
			}
		}
		
	}

	// metodo para cargar una linea con la informaci�n general.
	// [1,2] No Mercancias ----------------------------------------------------------------
	private LineasEbs loadLineaEBSNoMercancias(Solicitud solicitud, Integer encabezadoID) {

		LineasEbs linea = new LineasEbs();
		Factura factura = solicitud.getFacturas().get(Etiquetas.CERO);

		linea.setFacturaId(factura.getIdFactura());
		linea.setInvoiceId(encabezadoID);
		linea.setAttribute3(factura.getProveedor().getRfc());
		linea.setAttribute4(factura.getFactura());
		linea.setAttribute5(factura.getFolioFiscal());
		linea.setOrgId(factura.getCompaniaByIdCompania().getIdOrganizacion());

		// track asset ?
		if (factura.getTrackAsset() > Etiquetas.CERO_S) {
			linea.setAssetsTrackingFlag(parametroService.getParametroByName("etiquetaSiEBS").getValor());
		 	linea.setAssetBookTypeCode(factura.getCompaniaByIdCompania().getDescripcionEbs());

		} else {
			linea.setAssetsTrackingFlag(parametroService.getParametroByName("etiquetaNoEBS").getValor());
		}

		return linea;
	}
	
	// [3,4] Reembolsos y caja chica ----------------------------------------------------------------
	private LineasEbs loadLineaReembolsosCajaChica(Factura factura, Integer encabezadoID, int lineaItem, Solicitud solicitud){
		
		LineasEbs linea = new LineasEbs();
		
		linea.setFacturaId(factura.getIdFactura());
		linea.setInvoiceId(encabezadoID);
		linea.setLineNumber(lineaItem);
		linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
		linea.setAmount(factura.getSubtotal());
		// description
		String rfc = (factura.getProveedor() != null) ? factura.getProveedor().getRfc() : "";
		if(rfc.equals("")){
			rfc = (factura.getProveedorLibre() != null) ? factura.getProveedorLibre().getRfc() : null; 
		}
		
	if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS){
			
			
			if (solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS) {
				Proveedor proveedor = factura.getProveedor();
				String razonSocial = "";
				String descripcion = "";
//				Integer NumFactura = factura.getIdFactura(); // Cambio Secomenta para enviar numero factura enves de id
				String numFactura = factura.getFactura();

				if (proveedor != null) {
					razonSocial = proveedor.getDescripcion();
					descripcion = razonSocial+" / "+numFactura+" / "+solicitud.getConceptoGasto();
				}
				else {
					if(factura.getProveedorLibre() != null){
						ProveedorLibre proveedorLibre =  factura.getProveedorLibre();
						razonSocial = proveedorLibre.getDescripcion();
						descripcion = razonSocial+" / "+numFactura+" / "+solicitud.getConceptoGasto();
					}else{
						descripcion = numFactura+" / "+solicitud.getConceptoGasto();
					}
					
				}
			
			 linea.setDescription(descripcion.length() > 240 ? descripcion.substring(0,240) : descripcion);
			}
			
		
			
			//linea.setDescription(rfc + "-" + factura.getFactura() + "-"	+ solicitud.getConceptoGasto());
		}else if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
			
			String descripcion = "";
			if(factura.getProveedor() != null || factura.getProveedorLibre() != null){
				
				if(factura.getProveedor() != null && factura.getProveedor().getDescripcion() != null){
					descripcion = factura.getProveedor().getDescripcion() + " / " + factura.getFactura() + " / "	+ factura.getConceptoGasto();
				}else if(factura.getProveedorLibre() != null && factura.getProveedorLibre().getDescripcion() != null){
					descripcion = factura.getProveedorLibre().getDescripcion() + " / " + factura.getFactura() + " / "	+ factura.getConceptoGasto();
				}else{
					descripcion = factura.getFactura() + " / "	+ factura.getConceptoGasto();
				}
				
			}else{
				descripcion = factura.getFactura() + " / "	+ factura.getConceptoGasto();
			}
			
			linea.setDescription(descripcion.length() > 240 ? descripcion.substring(0,240) : descripcion);
		}
		
		
		String locacionNumero = "0";
		if(factura.getLocacion() != null){
		  locacionNumero = String.valueOf(factura.getLocacion().getNumero());
		}
		// contenacion de
		//COMPANIA.NUMERO_COMPANIA &"-"& LOCACION.NUMERO &"-"& "00" &"-"& CUENTA_CONTABLE.NUMERO_CUENTA_CONTABLE &"-0000"
		linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero + 
			"-" + "00-" + factura.getCuentaContable().getNumeroCuentaContable().toString() + "-0000");

		linea.setAttribute3(rfc);
		linea.setAttribute4(factura.getFactura());
		linea.setAttribute5(factura.getFolioFiscal());
		/* ORG_ID */
		linea.setOrgId(factura.getCompaniaByIdCompania().getIdOrganizacion());
		linea.setAssetsTrackingFlag(parametroService.getParametroByName("etiquetaNoEBS").getValor());
		//Control de cambios 23/06/2016 - miguelr
		linea.setTaxCode(null);
		
		return linea;
		
	}
	
	// [5] Kilometraje ----------------------------------------------------------------
	private LineasEbs loadLineaKilometraje(Integer encabezadoID, int lineaItem, Solicitud solicitud, FacturaKilometraje factKm){
		String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		String cuenta = parametroService.getParametroByName("ccNoDeducible").getValor();
		Integer idOrganizacion = solicitud.getCompania().getIdOrganizacion();
		String companiaNum = solicitud.getCompania().getNumeroCompania();
		Integer locacion = solicitud.getLocacion().getIdLocacion();
		
		LineasEbs linea = new LineasEbs();
		/* 1: Invoice ID */
		linea.setInvoiceId(encabezadoID);
		/* 2: LineNumber */
		linea.setLineNumber(lineaItem);
		/* 3: TypeLookupCode */
		linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
		/* 4: Importe de recorrido */
		linea.setAmount(factKm.getImporte());
		/* 5: Descripci�n de la solicitud */
		String origen = factKm.getKilometrajeRecorrido().getKilometrajeUbicacionByIdOrigen().getDescripcion();
		String destino = factKm.getKilometrajeRecorrido().getKilometrajeUbicacionByIdDestino().getDescripcion();
		String motivo = factKm.getMotivo();
		String kmFecha = Utilerias.convertDateFormat(factKm.getKilometrajeFecha()).replace("/", "");
		String descripcion = kmFecha + " " + origen + "-" + destino + " " + motivo;
		if(descripcion.length() > 240){
			linea.setDescription(descripcion.substring(0, 240));
		}else{
			linea.setDescription(descripcion);
		}
		/* 6: TaxCode */
		linea.setTaxCode(null);
		/* 7: DistCodeConcatenated */
		String concatenated = companiaNum+"-"+locacion+"-00-"+cuenta+"-0000";
		linea.setDistCodeConcatenated(concatenated);
		/* 8: Attributo 3 */
		linea.setAttribute3(solicitud.getUsuarioByIdUsuarioSolicita().getRfc());
		/* 9: Attributo 4 */	
		String atributo4 = "";
		String fechaNumber = fecha.replace("/", "");
		atributo4 = solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()+" "+fechaNumber+" "+solicitud.getIdSolicitud();
		linea.setAttribute4(atributo4);
		/* 10: Attributo 5 */	
		linea.setAttribute5(null);
		/* 11: ORG_ID */
		linea.setOrgId(idOrganizacion);
		/* 12,13,14,15: TAX CODE */
		linea.setAssetsTrackingFlag("N");
		linea.setAssetBookTypeCode(null);
		linea.setAssetCategoryIdMajor(null);
		linea.setAssetCategoryIdMinor(null);
		
		return linea;
	}
	
	// [6] Anticipo -------------------------------------------------------------------
	private LineasEbs loadLineaAnticipo(Integer encabezadoID, int lineaItem, Solicitud solicitud) {
		String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		String cuenta = parametroService.getParametroByName("ccAnticipo").getValor();
		Integer idOrganizacion = solicitud.getCompania().getIdOrganizacion();
		String companiaNum = solicitud.getCompania().getNumeroCompania();
		
		String rfcProveedor;
		String prooveedorDescripcion;
		if (solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR) {
			rfcProveedor = solicitud.getUsuarioByIdUsuarioAsesor().getRfc();
//			prooveedorDescripcion = solicitud.getUsuarioByIdUsuario().getNombreCompletoUsuario();
		} else {
			rfcProveedor = solicitud.getProveedor().getRfc();
//			prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
		}
		prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
		
		LineasEbs linea = new LineasEbs();

		/* 1: Invoice ID */
		linea.setInvoiceId(encabezadoID);
		/* 2: LineNumber */
		linea.setLineNumber(lineaItem);
		/* 3: TypeLookupCode */
		linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
		/* 4: Amount */
		linea.setAmount(solicitud.getMontoTotal());
		/* 5: Descripci�n de la solicitud
		Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto /
		nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(solicitud.getConceptoGasto());
		tokens.add(prooveedorDescripcion);
		String descripcion = buildDescriptionEBS(tokens, 0);
		linea.setDescription(descripcion);
		/* 6: TAX code */
		linea.setTaxCode(null);
		/* 7: Concatenated */
		String concatenated = companiaNum + "-0000-00-" + cuenta + "-0000";
		linea.setDistCodeConcatenated(concatenated);
		/* 8: Attribute 3 */
		linea.setAttribute3(rfcProveedor);
		/* 9: Attributo 4 */
		String atributo4 = "";
		String fechaNumber = fecha.replace("/", "");
		atributo4 = solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS() + "  " + fechaNumber;
		linea.setAttribute4(atributo4);
		/* 10: Attributo 5 */
		linea.setAttribute5(null);
		/* 11: Org ID */
		linea.setOrgId(idOrganizacion);
		/* 12, 13, 14, 15 Assets */
		linea.setAssetsTrackingFlag("N");
		linea.setAssetBookTypeCode(null);
		linea.setAssetCategoryIdMajor(null);
		linea.setAssetCategoryIdMinor(null);

		return linea;
	}
		
	// [7] Comprobar anticipo ---------------------------------------------------------
	private LineasEbs loadLineaComprobacionAnticipo(Factura factura, FacturaDesglose desglose, Integer encabezadoID, Integer lineaItem, Solicitud solicitud) {
		LineasEbs linea = new LineasEbs();
		String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		// numero de asesor/proveedor
		String prooveedorDescripcion;
		if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
			prooveedorDescripcion = solicitud.getUsuarioByIdUsuario().getNombreCompletoUsuario();
		}else{
			prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
		}
		
		/* 1: Factura ID */
		linea.setFacturaId(factura.getIdFactura());
		/* 2: Invoice ID */
		linea.setInvoiceId(encabezadoID);
		/* 3: LineNumber */
		linea.setLineNumber(lineaItem);
		/* 4: TypeLookupCode */
		String lineTypeLookupCode = parametroService.getParametroByName("etiquetaItemEBS").getValor();
//		if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
//			parametroService.getParametroByName("etiquetaItemEBS").getValor();
//		}else if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_PROVEEDOR){
//			parametroService.getParametroByName("etiquetaTaxEBS").getValor();
//		}
		linea.setLineTypeLookupCode(lineTypeLookupCode);
		/* 5: Amount */
		if(desglose != null)
			linea.setAmount(desglose.getSubtotal());
		else
			linea.setAmount(factura.getSubtotal());
		/* 6: Descripci�n de la solicitud Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(solicitud.getTipoSolicitud().getPrefijoDescriptionEBS() + " ANTICIPO");
		if(desglose != null)
			tokens.add(desglose.getConcepto());
		else
			tokens.add(solicitud.getConceptoGasto());
		tokens.add(prooveedorDescripcion);
		//Tokens + Index de la descripci�n.
		String descripcion = buildDescriptionEBS(tokens, 1);
		linea.setDescription(descripcion);
		/* 7: TaxCode */
		BigDecimal porcentajeIva = factura.getPorcentajeIva();
		if (porcentajeIva != null) {
			linea.setTaxCode(factura.getPorcentajeIva().intValue());
		}
		else {
			linea.setTaxCode(null);
		}
		
		String locacionNumero = "";
		if(desglose != null)
			locacionNumero = String.valueOf(desglose.getLocacion().getNumero());
		else
			locacionNumero = String.valueOf(factura.getLocacion().getNumero());
		/* 8: Concatenated */
		if(desglose != null)
			linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ "-" + "00-" + desglose.getCuentaContable().getNumeroCuentaContable().toString()+ "-0000");
		else
			linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ "-" + "00-" + factura.getCuentaContable().getNumeroCuentaContable().toString()+ "-0000");
		/* 9: Atributo 3 */
		linea.setAttribute3(factura.getProveedor().getRfc());
		/* 10: Atributo 4 */
		String atributo4 = "";
		String fechaNumber = fecha.replace("/", "");
		atributo4 = solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS() + "  " + fechaNumber;
		linea.setAttribute4(atributo4);
//		linea.setAttribute4(factura.getFactura());
		/* 11: Atributo 5 */
		linea.setAttribute5(factura.getFolioFiscal());
		/* 12: Org ID */
		linea.setOrgId(factura.getCompaniaByIdCompania().getIdOrganizacion());
		/* Otros */
		linea.setAssetsTrackingFlag("N");
		if (linea.getAssetsTrackingFlag().equals(parametroService.getParametroByName("etiquetaSiEBS").getValor())) {
			linea.setAssetCategoryIdMajor(desglose.getCategoriaMayor() != null ? desglose.getCategoriaMayor().getDescripcion() : "");
			linea.setAssetCategoryIdMinor(desglose.getCategoriaMenor() != null ? desglose.getCategoriaMenor().getDescripcion() : "");
		}else {
			linea.setAssetBookTypeCode(null);
			linea.setAssetCategoryIdMajor(null);
			linea.setAssetCategoryIdMinor(null);
		}
//		lineasEbsService.createLineasEbs(linea);
//		lineaItem++;

		return linea;
	}
	
	// [7.1] Comprobar anticipo Deposito---------------------------------------------------------
		private LineasEbs loadLineaComprobacionAnticipoDeposito(Factura factura, FacturaDesglose desglose, Integer encabezadoID, Integer lineaItem, Solicitud solicitud, ComprobacionDeposito deposito) {
				LineasEbs linea = new LineasEbs();
			
				String fecha = Utilerias.convertDateFormat(deposito.getCreacionFecha());
				String fechaNumber = fecha.replace("/", "");
				
//				String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
				// numero de asesor/proveedor
				String prooveedorDescripcion;
				if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
					prooveedorDescripcion = solicitud.getUsuarioByIdUsuario().getNombreCompletoUsuario();
				}else{
					prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
				}
				
				/* 1: Factura ID */
				linea.setFacturaId(factura.getIdFactura());
				/* 2: Invoice ID */
				linea.setInvoiceId(encabezadoID);
				/* 3: LineNumber */
				linea.setLineNumber(lineaItem);
				/* 4: TypeLookupCode */
				String lineTypeLookupCode = parametroService.getParametroByName("etiquetaItemEBS").getValor();
				linea.setLineTypeLookupCode(lineTypeLookupCode);
				/* 5: Amount */
				linea.setAmount(solicitud.getMontoTotal());
				/* 6: Descripci�n de la solicitud Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto / nombre"] */
				ArrayList<String> tokens = new ArrayList<String>();
				tokens.add(parametroService.getParametroByName("etiquetaFichaDepositoEBS") != null ? parametroService.getParametroByName("etiquetaFichaDepositoEBS").getValor() : null);
				tokens.add(Etiquetas.F_DEL + fechaNumber);
				//Tokens + Index de la descripci�n.
				String descripcion = buildDescriptionEBS(tokens, 1);
				linea.setDescription(descripcion);
				/* 7: TaxCode */
				linea.setTaxCode(null);
				String locacionNumero = "";
				if(desglose != null)
					locacionNumero = String.valueOf(desglose.getLocacion().getNumero());
				else
					locacionNumero = String.valueOf(factura.getLocacion().getNumero());
				/* 8: Concatenated */
				if(desglose != null)
					linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ "-" + "00-" + desglose.getCuentaContable().getNumeroCuentaContable().toString()+ "-0000");
				else
					linea.setDistCodeConcatenated(factura.getCompaniaByIdCompania().getNumeroCompania().toString() + "-" + locacionNumero+ "-" + "00-" + factura.getCuentaContable().getNumeroCuentaContable().toString()+ "-0000");
				/* 9: Atributo 3 */
				linea.setAttribute3(factura.getProveedor().getRfc());
				/* 10: Atributo 4 */
//				String atributo4 = "";
//				String fechaNumber = fecha.replace("/", "");
				StringBuilder atributo4 = new StringBuilder();
				atributo4.append(solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()).append("  ").append(fechaNumber).append("  ").append(parametroService.getParametroByName("etiquetaInvoiceCompDepositoEBS") != null ? parametroService.getParametroByName("etiquetaInvoiceCompDepositoEBS").getValor(): null);
//				atributo4 = solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS() + "  " + fechaNumber + ;
				linea.setAttribute4(atributo4.toString());
//				linea.setAttribute4(factura.getFactura());
				/* 11: Atributo 5 */
				linea.setAttribute5(null);
				/* 12: Org ID */
				linea.setOrgId(factura.getCompaniaByIdCompania().getIdOrganizacion());
				/* Otros */
				linea.setAssetsTrackingFlag("N");
				if (linea.getAssetsTrackingFlag().equals(parametroService.getParametroByName("etiquetaSiEBS").getValor())) {
					linea.setAssetCategoryIdMajor(desglose.getCategoriaMayor() != null ? desglose.getCategoriaMayor().getDescripcion() : "");
					linea.setAssetCategoryIdMinor(desglose.getCategoriaMenor() != null ? desglose.getCategoriaMenor().getDescripcion() : "");
				} else {
					linea.setAssetBookTypeCode(null);
					linea.setAssetCategoryIdMajor(null);
					linea.setAssetCategoryIdMinor(null);
				}
			
//			lineasEbsService.createLineasEbs(linea);
//			lineaItem++;

			return linea;
		}
	
	// [8] Anticipo -------------------------------------------------------------------
	private LineasEbs loadLineaAnticipoViaje(Integer encabezadoID, int lineaItem, Solicitud solicitud) {
		String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		String cuenta = parametroService.getParametroByName("ccAnticipo").getValor();
		Integer idOrganizacion = solicitud.getCompania().getIdOrganizacion();
		String companiaNum = solicitud.getCompania().getNumeroCompania();
		
		String rfcProveedor=null;
		String prooveedorDescripcion="";
		
		
//		if (solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR) {
//			rfcProveedor = solicitud.getUsuarioByIdUsuarioAsesor().getRfc();
//			prooveedorDescripcion = solicitud.getUsuarioByIdUsuario().getNombreCompletoUsuario();
//		} else {
//			rfcProveedor = solicitud.getProveedor().getRfc();
//			prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
//		}
		
		if(solicitud.getProveedor()!=null){
			rfcProveedor = solicitud.getProveedor().getRfc();
			prooveedorDescripcion = solicitud.getProveedor().getDescripcion();
		}


		LineasEbs linea = new LineasEbs();
		/* 2: Invoice ID*/
		linea.setInvoiceId(encabezadoID);
		/* 3: Line number*/
		linea.setLineNumber(lineaItem);
		/* 4: TypeLookupCode*/
		linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
		/* 5: Amount*/
		linea.setAmount(solicitud.getMontoTotal());
		/* 6: Descripci�n de la solicitud Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto /nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(solicitud.getConceptoGasto());
		tokens.add(prooveedorDescripcion);
		String descripcion = buildDescriptionEBS(tokens, 0);
		linea.setDescription(descripcion);
		/* 7: Tax Code*/
		linea.setTaxCode(null);
		/* 8: Concatenated */		
		Usuario usuario = solicitud.getUsuarioByIdUsuario();
		String concatenated = companiaNum + "-0000-00-" + cuenta + "-0000";
		linea.setDistCodeConcatenated(concatenated);
		/* 9: Attribute 3 */
		linea.setAttribute3(rfcProveedor);
		/* 10: Attributo 4 */
		String atributo4 = "";
		String fechaNumber = fecha.replace("/", "");
		atributo4 = solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS() + " - " + fechaNumber + " - "+ solicitud.getIdSolicitud();
		linea.setAttribute4(atributo4);
		/* 11: Attributo 5 */
		linea.setAttribute5(null);
		/* 12: Org ID */
		linea.setOrgId(idOrganizacion);
		// Assets 13, 14, 15, 16
		linea.setAssetsTrackingFlag("N");
		linea.setAssetBookTypeCode(null);
		linea.setAssetCategoryIdMajor(null);
		linea.setAssetCategoryIdMinor(null);
		return linea;
	}
	
	// [9] Comprobacion Gasto Viaje -------------------------------------------------------------------
	private LineasEbs loadLineaComprobacionGastoViaje(Integer encabezadoID, int lineaItem, Solicitud solicitud, Factura fact) {
		String fecha = Utilerias.convertDateFormat(solicitud.getCreacionFecha());
		String cuenta = parametroService.getParametroByName("ccNoDeducible").getValor();
		Integer idOrganizacion = solicitud.getCompania().getIdOrganizacion();
		String companiaNum = solicitud.getCompania().getNumeroCompania();
		Integer locacion = solicitud.getLocacion().getIdLocacion();
		
		LineasEbs linea = new LineasEbs();
		/* 1: ID Factura */
		linea.setFacturaId(fact.getIdFactura());
		/* 2: Invoice ID */
		linea.setInvoiceId(encabezadoID);
		/* 2: LineNumber */
		linea.setLineNumber(lineaItem);
		/* 3: TypeLookupCode */
		linea.setLineTypeLookupCode(parametroService.getParametroByName("etiquetaItemEBS").getValor());
		/* 4: Importe de recorrido */
		linea.setAmount(fact.getTotal());
		/* 5: Descripci�n de la solicitud
		Ingrese los tokens en orden deseado. ejemplo: ["ConceptoGasto /
		nombre"] */
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(solicitud.getConceptoGasto());
		FacturaGastoViaje facturaGv = facturaGastoViajeService.getFacturaGastoViajeByIdFactura(fact.getIdFactura());
		if(facturaGv.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_ALIMENTOS)
			tokens.add(facturaGv.getViajeTipoAlimentos().getDescripcion());		
		String descripcion = buildDescriptionEBS(tokens, 0);
		linea.setDescription(descripcion);
		/* 6: TaxCode */
		linea.setTaxCode(null);
		/* 7: DistCodeConcatenated */
		String concatenated = companiaNum+"-"+locacion+"-00-"+cuenta+"-0000";
		linea.setDistCodeConcatenated(concatenated);
		/* 8: Attributo 3 */
		linea.setAttribute3(solicitud.getUsuarioByIdUsuarioSolicita().getRfc());
		/* 9: Attributo 4 */	
		String atributo4 = "";
		String fechaNumber = fecha.replace("/", "");
//		atributo4 = solicitud.getTipoSolicitud().getPrefijoInvoiceNumEBS()+" - "+fechaNumber+" - "+solicitud.getIdSolicitud();
		atributo4 = fact.getFactura();//Folio Factura
		linea.setAttribute4(atributo4);
		/* 10: Attributo 5 */	
		linea.setAttribute5(fact.getFolioFiscal());
		/* 11: ORG_ID */
		linea.setOrgId(idOrganizacion);
		/* 12,13,14,15: TAX CODE */
		linea.setAssetsTrackingFlag("N");
		linea.setAssetBookTypeCode(null);
		linea.setAssetCategoryIdMajor(null);
		linea.setAssetCategoryIdMinor(null);
		
		return linea;
	}
	// Fin comprobarAnticipo ------------------------------------------------------
	
	public static List<Locacion> getLocacionesPermitidasPorUsuario(List<UsuarioConfSolicitante> uconfigSol, Integer tipoSolicitud, List<Locacion> lc, Integer idUsuario) {

		// cargar los tipos de locacion permitidos por usuario
		List<Integer> idsLocaciones = new ArrayList<>();
		for (UsuarioConfSolicitante conf : uconfigSol) {
			if (conf != null && conf.getTipoSolicitud().getIdTipoSolicitud() == tipoSolicitud && conf.getUsuario().getIdUsuario() == idUsuario) {
				idsLocaciones.add(conf.getLocacion().getIdLocacion());
			}
		}

		List<Locacion> lcPermitidas = new ArrayList<>();
		for (Locacion l : lc) {
			if (idsLocaciones.contains(l.getIdLocacion())) {
				lcPermitidas.add(l);
			}
		}

		return lcPermitidas;
	}
	
	
	// metodo ajax para el cambio de estatus a cancelada.
	@RequestMapping(value = "/enviarAProceso", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody ResponseEntity<String> enviarAProceso(HttpSession session, @RequestParam Integer idSolicitud, HttpServletRequest request, HttpServletResponse response) {
		  
		  String json = null;
	      HashMap<String, String> result = new HashMap<String, String>();

		  if(idSolicitud != null && idSolicitud > Etiquetas.CERO){
			  
		      Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);
			  Usuario	usuario = usuarioService.getUsuario(usuarioService.getUsuarioSesion().getIdUsuario());
			  
			  Map<Boolean, String> respuesta = new HashMap<>();
			  
			  respuesta = solicitudManagerService.enviarSolicitud(solicitud,  usuario);
			  
			  String respuestaTrue = respuesta.get(true);
			  String respuestaFalse = respuesta.get(false);
			  
			  if(respuestaTrue != null){
				  result.put("resultado", "true");
				  result.put("mensaje", respuestaTrue);

			  }else if(respuestaFalse != null){
				  result.put("resultado", "false");
				  result.put("mensaje", respuestaFalse);
			  }
	
		  }else{
			  result.put("resultado", "false");
		  }
		  
        //bind json
	        ObjectMapper map = new ObjectMapper();
	        if (!result.isEmpty()) {
	            try {
	                json = map.writeValueAsString(result);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        //respuesta
	        HttpHeaders responseHeaders = new HttpHeaders(); 
	        responseHeaders.add("Content-Type", "application/json; charset=utf-8"); 
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	    }
	  
	  
	  public HashMap<String, Object> getConXmlDatos(HttpSession session, Integer id, Integer idUsuario,
			UsuarioConfSolicitanteService uConfsolicitanteService, List<Locacion> locaciones,
			List<CuentaContable> ccontable, AidService aidService, CategoriaMayorService categoriaMayor,
			CategoriaMenorService categoriaMenor, CompaniaService companiaService, ProveedorService proveedorService,
			MonedaService monedaService, UsuarioService usuarioService, SolicitudService solicitudService,SolicitudArchivoService solicitudArchivoService) {

		HashMap<String, Object> model = new HashMap<String, Object>();
		FacturaConXML facturaConXML = new FacturaConXML();
		Integer idEstadoSolicitud = 0;

			if (id == null) {
				// destruir la sesion, creada por parametro
				session.setAttribute("solicitud", null);
			}

			// configuraciones permitidas por usuario
			List<UsuarioConfSolicitante> uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
			// Obtener combos filtrador por configuracion de solicitante.
			List<Locacion> lcPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol,Etiquetas.UNO,locaciones,idUsuario);
			List<CuentaContable> ccPermitidas = getCuentasContablesPermitidasPorUsuario(uconfigSol,ccontable);

			// OBTENER AID'S
			List<Aid> listAids = aidService.getAllAid();

			// Obtener categoria mayor y menor
			List<CategoriaMayor> listCatMayor = categoriaMayor.getAllCategoriaMayor();
			List<CategoriaMenor> listCatMenor = categoriaMenor.getAllCategoriaMenor();

			// Obtener Companias, Proveedores y Monedas
			List<Compania> lstCompanias = companiaService.getAllCompania();
			List<Proveedor> lstProveedores = proveedorService.getAllProveedores();
			List<Moneda> lstMoneda = monedaService.getAllMoneda();

			// obtener los jefe para opcion de usuario solicitante.
			List<Usuario> lstUsuariosJefe = new ArrayList<>();
			Usuario usuario = usuarioService.getUsuario(idUsuario);
			if(usuario.getUsuario() != null){
			  lstUsuariosJefe.add(usuario.getUsuario());
			}

			Solicitud solicitud = null;
			Factura factura = null;
			List<FacturaDesglose> facturasDesglose = new ArrayList<>();
			List<FacturaDesgloseDTO> facturasDesgloseDTO = new ArrayList<>();

			// Obtener solicitud,factura y su desglose si se requiere:
			if (id != null && id > Etiquetas.CERO) {
				facturaConXML.setIdSolicitudSession(id);
				solicitud = solicitudService.getSolicitud(facturaConXML.getIdSolicitudSession());
				if(solicitud != null){
				  idEstadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
				}
			}

			if (solicitud != null && solicitud.getFacturas().size() > Etiquetas.CERO) {

				// si la solicitud es v�lida entonces crear una variable de sesi�n
				// para el update.
				session.setAttribute("solicitud", solicitud);

				factura = solicitud.getFacturas().get(Etiquetas.CERO);
				if (factura != null && solicitud.getFacturas().size() > Etiquetas.CERO) {
					
					if(factura.getCompaniaByIdCompania() != null)
						facturaConXML.setCompania(factura.getCompaniaByIdCompania());
					if(factura.getProveedor() != null){
						facturaConXML.setProveedor(factura.getProveedor());				
						facturaConXML.setRfcEmisor(factura.getProveedor().getRfc());
					}
					if(factura.getFolioFiscal() != null)
						facturaConXML.setFolioFiscal(factura.getFolioFiscal());
					facturaConXML.setFolio(factura.getFactura()); //
					if(factura.getSerieFactura() != null)				
						facturaConXML.setSerie(factura.getSerieFactura());
					if(factura.getTipoFactura() != null)				
						facturaConXML.setTipoFactura(factura.getTipoFactura().getIdTipoFactura());								
					
					//retenciones
					if(factura.getConRetenciones() > Etiquetas.CERO_S){
						facturaConXML.setConRetenciones(Etiquetas.TRUE);
						facturaConXML.setStrIsr_retenido(factura.getIsrRetenido() != null ? factura.getIsrRetenido().toString() : "");
						facturaConXML.setStrIva_retenido(factura.getIvaRetenido() != null ? factura.getIvaRetenido().toString() : "");
					}else{
						facturaConXML.setConRetenciones(Etiquetas.FALSE);
					}
					
					String fechaString = null;
					SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdfOut = new SimpleDateFormat("MM/dd/yyyy");
					
					try {
						fechaString = sdfOut.format(sdfIn.parse(factura.getFechaFactura().toString()));
						facturaConXML.setFecha_factura(fechaString);
					} catch (ParseException e) {
						
					}
	                
					facturaConXML.setConcepto(solicitud.getConceptoGasto());
					
					if(factura.getTrackAsset() > Etiquetas.CERO){
					  facturaConXML.setTrack_asset(Etiquetas.TRUE);
					}else{
					  facturaConXML.setTrack_asset(Etiquetas.FALSE);
					}
					
					facturaConXML.setPar(factura.getPar());
					facturaConXML.setId_compania_libro_contable(factura.getCompaniaByIdCompaniaLibroContable());

					facturaConXML.setStrIva(factura.getIva()!=null ? factura.getIva().toString(): "");
					facturaConXML.setStrIeps(factura.getIeps()!=null ? factura.getIeps().toString() : "");
					facturaConXML.setStrTotal(factura.getTotal()!=null ? factura.getTotal().toString():"");
					facturaConXML.setStrSubTotal(factura.getSubtotal() !=null ? factura.getSubtotal().toString() : "");;
					
					facturaConXML.setMoneda(factura.getMoneda());
					if (solicitud.getUsuarioByIdUsuario().getIdUsuario() != solicitud.getUsuarioByIdUsuarioSolicita()
							.getIdUsuario()) {
						facturaConXML.setSolicitante(true);
						facturaConXML.setIdSolicitanteJefe(solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario());
					} else {
						facturaConXML.setSolicitante(false);
					}

					facturasDesglose = factura.getFacturaDesgloses();
					if (facturasDesglose != null && facturasDesglose.size() > Etiquetas.CERO) {
						for (FacturaDesglose fd : facturasDesglose) {
							FacturaDesgloseDTO fdd = new FacturaDesgloseDTO();
							if (fd.getAid() != null && fd.getAid().getIdAid() > Etiquetas.CERO) {
								fdd.setAid(fd.getAid());
							}
							if (fd.getCategoriaMayor() != null
									&& fd.getCategoriaMayor().getIdCategoriaMayor() > Etiquetas.CERO) {
								fdd.setCategoriaMayor(fd.getCategoriaMayor());
							}
							if (fd.getCategoriaMenor() != null
									&& fd.getCategoriaMenor().getIdCategoriaMenor() > Etiquetas.CERO) {
								fdd.setCategoriaMenor(fd.getCategoriaMenor());
							}
							fdd.setConcepto(fd.getConcepto());
							fdd.setCuentaContable(fd.getCuentaContable());
							fdd.setLocacion(fd.getLocacion());
							fdd.setStrSubTotal(fd.getSubtotal().toString());
							facturasDesgloseDTO.add(fdd);
						}
					}
				}
			} else {
				// mensaje de solicitud no valida a la vista.
				facturaConXML.setIdSolicitudSession(Etiquetas.CERO);
			}

			facturaConXML.setFacturaDesgloseList(facturasDesgloseDTO);
			
			//revisar si se trata de una modificaci�n para activar mensaje en la vista
			if(session.getAttribute("actualizacion") != null){
				facturaConXML.setModificacion(Etiquetas.TRUE);
				session.setAttribute("actualizacion", null);
			}else{
				facturaConXML.setModificacion(Etiquetas.FALSE);
			}
			
			//revisar si se trata de un nuevo registro para activar mensaje en la vista
			if(session.getAttribute("creacion") != null){
				facturaConXML.setCreacion(Etiquetas.TRUE);
				session.setAttribute("creacion", null);
			}else{
				facturaConXML.setCreacion(Etiquetas.FALSE);
			}
			
	        
			// Enviar archivos anexados por solicitud:
			
			List<SolicitudArchivo> solicitudArchivoList = new ArrayList<>();
			if(facturaConXML.getIdSolicitudSession() > Etiquetas.CERO){
				solicitudArchivoList = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(facturaConXML.getIdSolicitudSession()); 
			}
			
			Usuario usuarioSession = usuarioService.getUsuarioSesion();
			
			model.put("facturaConXML", facturaConXML);
			model.put("lcPermitidas", lcPermitidas);
			model.put("ccPermitidas", ccPermitidas);
			model.put("listAids", listAids);
			model.put("listCatMayor", listCatMayor);
			model.put("listCatMenor", listCatMenor);
			model.put("lstCompanias", lstCompanias);
			model.put("lstProveedores", lstProveedores);
			model.put("lstMoneda", lstMoneda);
			if(facturaConXML.isSolicitante() == true && facturaConXML.getIdSolicitanteJefe() == usuarioSession.getIdUsuario()){
				lstUsuariosJefe = new ArrayList<>();
				lstUsuariosJefe.add(usuarioSession);
				model.put("lstUsuariosJefe", lstUsuariosJefe);
			}else{
				model.put("lstUsuariosJefe", lstUsuariosJefe);

			}
			
			model.put("estadoSolicitud", idEstadoSolicitud);
			model.put("solicitudArchivoList", solicitudArchivoList);
			model.put("tipoSolicitud",Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML);
			model.put("isSolicitante", usuario.getEspecificaSolicitante());
			model.put("usuarioSession", usuarioSession);
			
			return model;
	  }
	  
	  
	public static List<CuentaContable> getCuentasContablesPermitidasPorUsuario(List<UsuarioConfSolicitante> uconfigSol, List<CuentaContable> ccontable) {
		// cargar los tipos de cuenta contable permitidos por usuario
		List<Integer> idCuentaContable = new ArrayList<>();
		for (UsuarioConfSolicitante confCuentas : uconfigSol) {
			if (confCuentas != null) {
				idCuentaContable.add(confCuentas.getCuentaContable().getIdCuentaContable());
			}
		}

		List<CuentaContable> ccontableAux = new ArrayList<>();
		for (CuentaContable cc : ccontable) {
			if (idCuentaContable.contains(cc.getIdCuentaContable())) {
				ccontableAux.add(cc);
			}
		}

		return ccontableAux;
	}
		
	public static List<CuentaContable> getCuentasContablesPermitidasPorLocacion(Integer locacionID,
			List<UsuarioConfSolicitante> uconfigSol, Integer tipoSolicitud) {
		List<CuentaContable> ccontableAux = new ArrayList<>();

		if (uconfigSol != null && uconfigSol.isEmpty() == false) {
			for (UsuarioConfSolicitante conf : uconfigSol) {
              if(conf.getLocacion().getIdLocacion() == locacionID && conf.getTipoSolicitud().getIdTipoSolicitud() == tipoSolicitud){
            	  ccontableAux.add(conf.getCuentaContable());
              }
			}
		}

		return ccontableAux;
	}
		
	public static String getCuentasContablesHTML(Integer idLocacion, List<UsuarioConfSolicitante> uconfigSol, Integer tipoSolicitud){
		  StringBuilder row2 = new StringBuilder();
			row2.append("<option value=\"-1\">Seleccione:</option>");
			

			List<CuentaContable> ccPermitidas = new ArrayList<>();
	        //List<CuentaContable> ccPermitidasAnticipo = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacion, uconfigSol, Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE);
	        List<CuentaContable> ccPermitidasComprobacion = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacion, uconfigSol, tipoSolicitud);
	        
	        //ccPermitidas.addAll(ccPermitidasAnticipo);
	        ccPermitidas.addAll(ccPermitidasComprobacion);
	        
		    for (CuentaContable cc : ccPermitidas) {
				row2.append("<option value=\"");
				row2.append(String.valueOf(cc.getIdCuentaContable()));
				StringBuilder ccontableDesc = new StringBuilder();
				ccontableDesc.append(cc.getNumeroDescripcionCuentaContable());
//					ccontableDesc.append("(");
//					ccontableDesc.append(cc.getNumeroCuentaContable());
//					ccontableDesc.append(")");
				row2.append("\"> " + ccontableDesc.toString() + " </option>");
			}
		    return row2.toString();
	  }
		  
	public static String getCuentasContablesHTML(Integer idLocacion, List<UsuarioConfSolicitante> uconfigSol,Integer idCuentaContable, Integer tipoSolicitud){
		  StringBuilder row2 = new StringBuilder();
			row2.append("<option value=\"-1\">Seleccione:</option>");
	        List<CuentaContable> ccPermitidas = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacion, uconfigSol, tipoSolicitud);
		    for (CuentaContable cc : ccPermitidas) {
		    	
		    	if(idCuentaContable == cc.getIdCuentaContable()){
				row2.append("<option selected value=\"");
		    	}else{
					row2.append("<option value=\"");
		    	}
				
				row2.append(String.valueOf(cc.getIdCuentaContable()));
				StringBuilder ccontableDesc = new StringBuilder();
				ccontableDesc.append(cc.getDescripcion());
				ccontableDesc.append("(");
				ccontableDesc.append(cc.getNumeroCuentaContable());
				ccontableDesc.append(")");
				row2.append("\"> " + ccontableDesc.toString() + " </option>");
			}
		    return row2.toString();
	  }
		
	public static Integer getIDByCuentaContable(String cuentaContable, List<CuentaContable> cuentasContables){
		
		Integer idCuentaContable = null;
		
		for(CuentaContable cuenta : cuentasContables){
			try{
			if(cuenta.getNumeroCuentaContable() != null && cuentaContable.equals(cuenta.getNumeroCuentaContable())){
				Integer t = 0;
				//System.out.println(t);
				idCuentaContable = cuenta.getIdCuentaContable();
				break;
			}
			}catch(Exception e){
				logger.error(e);
			}
		}
			
		return idCuentaContable;
	}
		
	/**
	 * @param facturaSolicitudDTO
	 * @return ProveedorLibre si se guard� correctamente, null si hubo un error al guardar.
	 * CREA UN NUEVO REGISTRO EN LA TABLA DE PROVEEDOR_LIBRE CUANDO EL PROVEEDOR DE LA FACTURA NO EXISTE EN EL SISTEMA
	 */
	private ProveedorLibre agregaNuevoProveedor(String descripcion, String Rfc) {
		ProveedorLibre nuevo = new ProveedorLibre();
		nuevo.setDescripcion(descripcion);
		nuevo.setRfc(Rfc);
		nuevo.setActivo(Etiquetas.UNO_S);
		nuevo.setCreacionUsuario(usuarioService.getUsuarioSesion().getIdUsuario());
		nuevo.setCreacionFecha(new Date());
		Integer idProveedorLibre = proveedorLibreService.createProveedorLibre(nuevo);
		if (idProveedorLibre != null)
			return nuevo;
		else
			return null;
	}
		
	private String buildDescriptionEBS(ArrayList<String> tokens, int indexDesc) {
		int joins = 3 * (tokens.size() - 1);
		int charsLeft = 240 - joins;
		for (int i = tokens.size() - 1; i >= 0; i--) {
			if (i != indexDesc)
				charsLeft -= tokens.get(i).length();
		}
		if (tokens.get(indexDesc).length() > charsLeft)
			tokens.set(indexDesc, tokens.get(indexDesc).substring(0, charsLeft));

		return String.join(" / ", tokens);
	}
	
	
		@RequestMapping(value = "/conceptoFactura", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		    public @ResponseBody ResponseEntity<String> conceptoFactura(HttpSession session, @RequestParam Integer idFactura, HttpServletRequest request, HttpServletResponse response) {
			  
			  String json = null;
		      HashMap<String, String> result = new HashMap<String, String>();

			  if(idFactura != null && idFactura > Etiquetas.CERO){
				  result.put("conceptoGasto", facturaService.getFactura(idFactura).getConceptoGasto());
				  result.put("idSolicitud", String.valueOf(facturaService.getFactura(idFactura).getSolicitud().getIdSolicitud()));
				  
			  }else{
				  result.put("conceptoGasto", null);
				  result.put("idSolicitud", null);
			  }
			  
	        //bind json
		        ObjectMapper map = new ObjectMapper();
		        if (!result.isEmpty()) {
		            try {
		                json = map.writeValueAsString(result);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		        
		        //respuesta
		        HttpHeaders responseHeaders = new HttpHeaders(); 
		        responseHeaders.add("Content-Type", "application/json; charset=utf-8"); 
		        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		    }
		  
	
	
	
	
}