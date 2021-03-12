package com.lowes.controller;


import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

import com.lowes.dto.KilometrajeDTO;
import com.lowes.dto.KilometrajeDesgloseDTO;
import com.lowes.entity.AidConfiguracion;
import com.lowes.entity.Compania;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.FacturaKilometraje;
import com.lowes.entity.FormaPago;
import com.lowes.entity.KilometrajeRecorrido;
import com.lowes.entity.KilometrajeUbicacion;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.CompaniaService;
import com.lowes.service.FacturaKilometrajeService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.KilometrajeRecorridoService;
import com.lowes.service.KilometrajeUbicacionService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class kilometrajeController {
	
	private static final Logger logger = Logger.getLogger(KilometrajeRecorrido.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private MonedaService monedaService;
	@Autowired
	private CompaniaService companiaService;
	@Autowired
	private LocacionService locacionService;
	@Autowired
	private FormaPagoService formaPagoService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private KilometrajeUbicacionService kilometrajeUbicacionService;
	@Autowired
	private KilometrajeRecorridoService kilometrajeRecorridoService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private FacturaKilometrajeService facturaKilometrajeService;
	@Autowired
    private UsuarioService usuarioService;
	@Autowired
	private UsuarioConfSolicitanteService uConfsolicitanteService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	
		
		@RequestMapping(value = "/kilometraje", method = RequestMethod.GET)
		public ModelAndView kilometrajeRecorrido(HttpSession session, Integer id) {	
		
		//declaraciones de objetos 
		KilometrajeDTO kilometrajeDTO = new KilometrajeDTO();
		HashMap<String, Object> model = new HashMap<String, Object>();
		Solicitud solicitud = new Solicitud();
        List<KilometrajeUbicacion> ubicacionesAux = new ArrayList<>();
		Integer idEstadoSolicitud = 0;
		
		//forma de pago default
		kilometrajeDTO.setFormaPago(new FormaPago(Integer.parseInt(parametroService.getParametroByName("formaPagoTransferencia").getValor())));
		kilometrajeDTO.setIdFormaPago(Integer.parseInt(parametroService.getParametroByName("formaPagoTransferencia").getValor()));
		String rangoDias = parametroService.getParametroByName("rangoDiasKilometraje").getValor();
		String rangoDiasAlias = parametroService.getParametroByName("rangoDiasKilometraje").getAlias();
        
		// cargando objetos con data de DB2
		List<Moneda> monedaList = monedaService.getAllMoneda();
		List<Compania> companiaList = companiaService.getAllCompania();
		List<FormaPago> formaPagoList = formaPagoService.getAllFormaPago();
		List<KilometrajeUbicacion> kilometrajeUbicacionList = kilometrajeUbicacionService.getAllKilometrajeUbicacion();
		
		// valor por parametro del importe auto propio
		kilometrajeDTO.setImporteAutoPropio(parametroService.getParametroByName("importeAutoPropio").getValor());
		
		//locaciones permitidas por usuario
		// configuraciones permitidas por usuario
		// extraer usuario en session para guardar la solicitud.
		Usuario usuarioSession = usuarioService.getUsuarioSesion();
		List<UsuarioConfSolicitante> uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(usuarioSession.getIdUsuario());
		List<Locacion> locacionesPermitidas = getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_KILOMETRAJE);
		kilometrajeDTO.setLocacion(new Locacion(usuarioSession.getLocacion().getIdLocacion()));
		
		// si trae un parametro numerico para mostrar solicitud se llama al servicio
		if(id != null && id > Etiquetas.CERO){
			solicitud = solicitudService.getSolicitud(id);
			if(solicitud != null){
				kilometrajeDTO.setIdSolicitudSession(solicitud.getIdSolicitud());
				idEstadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
			}
		}
		
		// si el resultado del servicio contiene informacion se carga el objeto
		if(solicitud != null && solicitud.getIdSolicitud() > Etiquetas.CERO){
			kilometrajeDTO.setLocacion(solicitud.getLocacion());
			kilometrajeDTO.setConcepto(solicitud.getConceptoGasto());
			kilometrajeDTO.setIdSolicitud(solicitud.getIdSolicitud());
			
			//revisar si se trata de una modificaci�n para activar mensaje en la vista
			if(session.getAttribute("actualizacion") != null){
				kilometrajeDTO.setModificacion(Etiquetas.TRUE);
				session.setAttribute("actualizacion", null);
			}else{
				kilometrajeDTO.setModificacion(Etiquetas.FALSE);
			}
			
			//revisar si se trata de un nuevo registro para activar mensaje en la vista
			if(session.getAttribute("creacion") != null){
				kilometrajeDTO.setCreacion(Etiquetas.TRUE);
				session.setAttribute("creacion", null);
			}else{
				kilometrajeDTO.setCreacion(Etiquetas.FALSE);
			}
		}
		
		List<FacturaKilometraje> facturasKilometrajes = facturaKilometrajeService.getAllFacturaKilometrajeByIdSolicitud(solicitud.getIdSolicitud());
		List<KilometrajeDesgloseDTO> desgloses = new ArrayList<>();
		
		List<KilometrajeRecorrido> kilometraje = kilometrajeRecorridoService.getAllKilometrajeRecorrido();

		for(FacturaKilometraje fk : facturasKilometrajes){
			KilometrajeDesgloseDTO desglose = new KilometrajeDesgloseDTO();
			ubicacionesAux = new ArrayList<>();
			
			desglose.setFecha(Utilerias.convertDateFormat(fk.getKilometrajeFecha()));
			desglose.setMotivo(fk.getMotivo());
			//obtener ubicaciones a partir del id del recorrido:
			HashMap<String, Integer> ubicaciones = getUbicaciones(fk.getKilometrajeRecorrido().getIdKilometrajeRecorrido());
			Integer idOrigen = ubicaciones.get("origen");
			desglose.setOrigen(new KilometrajeUbicacion(idOrigen));
			desglose.setDestino(new KilometrajeUbicacion(ubicaciones.get("destino")));
			desglose.setViajes(fk.getNumeroViajes());
			desglose.setKilometros_recorridos(fk.getkilometrajeRecorridoCalculado());
			desglose.setImporte(fk.getImporte().toString());
			
			
			/*obtener los destinos por ubicacion seleccionada cuando la solicitud se encuentre guardada*/
			for (KilometrajeRecorrido km : kilometraje) {
				if (idOrigen == km.getKilometrajeUbicacionByIdOrigen().getIdUbicacion()) {
					ubicacionesAux.add(km.getKilometrajeUbicacionByIdDestino());
				}
			}
			desglose.setDestinos(ubicacionesAux);
			
			// adjuntar todo el objeto a la lista de objetos.
            desgloses.add(desglose);
		}

		kilometrajeDTO.setKilometrajeDesgloseList(desgloses);
		
		
		// Enviar archivos anexados por solicitud:

		List<SolicitudArchivo> solicitudArchivoList = new ArrayList<>();
		if (kilometrajeDTO.getIdSolicitudSession() != null && kilometrajeDTO.getIdSolicitudSession() > Etiquetas.CERO) {
			solicitudArchivoList = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(kilometrajeDTO.getIdSolicitudSession());
		}
		
		//set de objetos para la vista
		model.put("kilometrajeDTO", kilometrajeDTO);
		model.put("monedaList", monedaList);
		model.put("locacionList", locacionesPermitidas);
		model.put("companiaList", companiaList);
		model.put("formaPagoList", formaPagoList);
		model.put("kilometrajeUbicacionList", kilometrajeUbicacionList);
		model.put("rangoDias", rangoDias);
		model.put("rangoDiasAlias", rangoDiasAlias);
		model.put("solicitudArchivoList", solicitudArchivoList);
		model.put("idEstadoSolicitud", idEstadoSolicitud);

		
		// enviar el modelo con objetos a la vista.
		return new ModelAndView("kilometraje",model);
	}
	
	
	
	@RequestMapping(value = "/addRowKilometraje", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> addRow(HttpSession session, @RequestParam Integer numrows,
			HttpServletRequest request, HttpServletResponse response) {
		
		String num = Utilerias.getRandomNum();
		Integer numLinea = numrows + 1;
		
		String json = null;
		StringBuilder row = new StringBuilder();
		
		List<KilometrajeUbicacion> ubicaciones = new ArrayList<>();
		ubicaciones = kilometrajeUbicacionService.getAllKilometrajeUbicacion();
		
		row.append("<tr>");
		 //linea
		 row.append("<td class=\"linea\" align=\"center\">").append(numLinea).append("</td>");
		 //fecha
		 row.append("<td>");
		 row.append("<div class=\"form-group sandbox-container\">");
	      row.append("<input onchange=\"verificarFecha(value,").append(num).append(")\" readonly=\"true\"  id=\"kilometrajeDesgloseList").append(num).append(".fecha\" name=\"kilometrajeDesgloseList[").append(num).append("].fecha\"  value=\"\"  class=\"form-control fechaKm\" style=\"background-color:white\" type=\"text\">");
		 row.append("</div>");
		 //motivo
		 row.append("</td>");
		 row.append("<td>");
		 row.append("<input maxlength=\"100\" id=\"kilometrajeDesgloseList").append(num).append(".motivo\" name=\"kilometrajeDesgloseList[").append(num).append("].motivo\" class=\"form-control motivoKm\" value=\"\" type=\"text\">");
		 row.append("</td>");
		 //ubicacion origen
		 row.append("<td>");
		 row.append("<select onChange=\"getDestinos(").append(num).append(")\" id=\"kilometrajeDesgloseList").append(num).append(".origen.idUbicacion\" name=\"kilometrajeDesgloseList[").append(num).append("].origen.idUbicacion\" class=\"form-control origenKm\">");
		 row.append("<option value=\"-1\">Seleccione:</option>");
		 for(KilometrajeUbicacion ubicacion : ubicaciones){
			row.append("<option value=\"").append(ubicacion.getIdUbicacion()).append("\" ").append(" >").append(ubicacion.getDescripcion()).append("</option>");
		 }
		 row.append("</select>");
		 row.append("</td>");
		 //ubicacion Destino
		 row.append("<td>");
		 row.append("<select onChange=\"setKilometraje(").append(num).append(")\" id=\"kilometrajeDesgloseList").append(num).append(".destino.idUbicacion\" name=\"kilometrajeDesgloseList[").append(num).append("].destino.idUbicacion\" class=\"form-control destinoKm\">");
		 row.append("<option value=\"-1\">Seleccione:</option>");
		 row.append("</select>");
		 row.append("</td>");
		 // viajes
		 row.append("<td>");
		 row.append("<input type=\"number\" onkeyup=\"suma(").append(num).append(")\" id=\"kilometrajeDesgloseList").append(num).append(".viajes\" name=\"kilometrajeDesgloseList[").append(num).append("].viajes\" style=\"text-align:right\" class=\"form-control viajesKm\" value=\"1\" type=\"text\">");
		 row.append("</td>");
		 //kilometros recorridos
		 row.append("<td>");
		 row.append("<input type=\"number\" onkeyup=\"suma(").append(num).append(")\" disabled=\"true\" id=\"kilometrajeDesgloseList").append(num).append(".kilometros_recorridos\" name=\"kilometrajeDesgloseList[").append(num).append("].kilometros_recorridos\" style=\"text-align:right\" class=\"form-control kilometraje\" value=\"\" type=\"text\">");
		 row.append("</td>");
		 //importe
		 row.append("<td>");
		 row.append("<input id=\"kilometrajeDesgloseList").append(num).append(".importe\" disabled=\"true\" name=\"kilometrajeDesgloseList[").append(num).append("].importe\" class=\"form-control currencyFormat importeKm\" value=\"\" type=\"text\">");
		 row.append("</td>");
		 //boton eliminar
		 row.append("<td><button type=\"button\" class=\"btn btn-danger removerFila\">Remover</button></td>");
		 row.append("</tr>");
		
		
		ObjectMapper map = new ObjectMapper();
		if (!row.toString().isEmpty()) {
			try {
				json = map.writeValueAsString(row.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/saveKilometraje", method = RequestMethod.POST)
	public ModelAndView saveKilometraje(@ModelAttribute("kilometrajeDTO") KilometrajeDTO kilometrajeDTO,
			HttpSession session) {
		
		Integer idSolicitud = null;
		// extraer usuario en session para guardar la solicitud.
		Usuario usuarioSession = usuarioService.getUsuarioSesion();
		
		// actualizacion de solicitud de kilometraje
		if(kilometrajeDTO.getIdSolicitud() != null && kilometrajeDTO.getIdSolicitud() > Etiquetas.CERO){
			
			//solicitud update
			Solicitud solicitud = new Solicitud();
			
			//id para el update
			idSolicitud = kilometrajeDTO.getIdSolicitud();
			
			//valores de la pantalla
			solicitud.setIdSolicitud(idSolicitud);
			solicitud.setMoneda(new Moneda(kilometrajeDTO.getIdMoneda()));
			solicitud.setCompania(new Compania(kilometrajeDTO.getIdCompania()));
			solicitud.setLocacion(new Locacion(kilometrajeDTO.getLocacion().getIdLocacion()));
			solicitud.setFormaPago(kilometrajeDTO.getFormaPago());
			solicitud.setConceptoGasto(kilometrajeDTO.getConcepto());
			
			//obtener valores fijos (fecha creacion, usuario creacion, etc)
			Solicitud solicitudAux = solicitudService.getSolicitud(idSolicitud);
			
			//not null columns, con valores de la BD
			solicitud.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_KILOMETRAJE));
			solicitud.setUsuarioByIdUsuario(solicitudAux.getUsuarioByIdUsuario());
			solicitud.setUsuarioByIdUsuarioSolicita(solicitudAux.getUsuarioByIdUsuarioSolicita());
			solicitud.setActivo(Etiquetas.UNO_S);
			solicitud.setCreacionFecha(solicitudAux.getCreacionFecha());
			solicitud.setCreacionUsuario(solicitudAux.getCreacionUsuario());
			solicitud.setEstadoSolicitud(solicitudAux.getEstadoSolicitud());
			solicitud.setTrackAsset(Etiquetas.CERO_S);
			BigDecimal montoTotal = getMontoTotalKilometraje(kilometrajeDTO);
			solicitud.setMontoTotal(montoTotal);
			
			//valores del update
			solicitud.setModificacionFecha(new Date());
			solicitud.setModificacionUsuario(usuarioSession.getIdUsuario());
			
			//crear sesion para mensaje en la vista
            session.setAttribute("actualizacion", Etiquetas.TRUE);
			
			solicitudService.updateSolicitud(solicitud);
			
			
			   if(kilometrajeDTO.getKilometrajeDesgloseList() != null){
				   
				   //delete all for insert all
				   facturaKilometrajeService.deleteFacturaKilometrajeByIdSolicitud(idSolicitud);
				   
					for(KilometrajeDesgloseDTO km : kilometrajeDTO.getKilometrajeDesgloseList()){
						if(km.getImporte() != null && km.getViajes() != null){
							//objeto para guardar
							FacturaKilometraje fkm = new FacturaKilometraje();
							//set todos los valores
							fkm.setKilometrajeRecorrido(new KilometrajeRecorrido(getRecorrido(km.getOrigen().getIdUbicacion(), km.getDestino().getIdUbicacion())));
							fkm.setSolicitud(new Solicitud(idSolicitud));
							
							try {
								fkm.setKilometrajeFecha(Utilerias.parseDate(km.getFecha(), "dd/MM/yyyy"));
							} catch (ParseException e) {
						
							}
							
							fkm.setMotivo(km.getMotivo());
							fkm.setNumeroViajes(km.getViajes());
							fkm.setkilometrajeRecorridoCalculado(km.getKilometros_recorridos());
							fkm.setImporte(Utilerias.convertStringToBigDecimal(km.getImporte()));
							//defaults
							fkm.setActivo(Etiquetas.UNO_S);
							fkm.setCreacionFecha(new Date());
							fkm.setCreacionUsuario(usuarioSession.getIdUsuario());
							
							facturaKilometrajeService.createFacturaKilometraje(fkm);
						}
					}
				}
			
		}else{
			//nueva solicitud de kilometraje
			Solicitud solicitud = new Solicitud();
			
			//valores de la pantalla.
			solicitud.setMoneda(new Moneda(kilometrajeDTO.getIdMoneda()));
			solicitud.setCompania(new Compania(kilometrajeDTO.getIdCompania()));
			solicitud.setLocacion(new Locacion(kilometrajeDTO.getLocacion().getIdLocacion()));
			solicitud.setFormaPago(kilometrajeDTO.getFormaPago());
			solicitud.setConceptoGasto(kilometrajeDTO.getConcepto());
			
			//not null columns
			solicitud.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_KILOMETRAJE));
			solicitud.setIdUsuario(usuarioSession.getIdUsuario());
			solicitud.setUsuarioByIdUsuarioSolicita(usuarioSession);
			solicitud.setUsuarioByIdUsuario(usuarioSession);
			solicitud.setActivo(Etiquetas.UNO_S);
			solicitud.setCreacionFecha(new Date());
			solicitud.setCreacionUsuario(usuarioSession.getIdUsuario());
			solicitud.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.UNO));
			solicitud.setTrackAsset(Etiquetas.CERO_S);
			BigDecimal montoTotal = getMontoTotalKilometraje(kilometrajeDTO);
			solicitud.setMontoTotal(montoTotal);
			
			//crear sesion para mensaje en la vista
            session.setAttribute("creacion", Etiquetas.TRUE);
			
			//persistencia.
			idSolicitud = solicitudService.createSolicitud(solicitud);
			
            if(kilometrajeDTO.getKilometrajeDesgloseList() != null){
				for(KilometrajeDesgloseDTO km : kilometrajeDTO.getKilometrajeDesgloseList()){
					if(km.getImporte() != null && km.getViajes() != null){
						//objeto para guardar
						FacturaKilometraje fkm = new FacturaKilometraje();
						//set todos los valores
						fkm.setKilometrajeRecorrido(new KilometrajeRecorrido(getRecorrido(km.getOrigen().getIdUbicacion(), km.getDestino().getIdUbicacion())));
						fkm.setSolicitud(new Solicitud(idSolicitud));
						
						try {
							fkm.setKilometrajeFecha(Utilerias.parseDate(km.getFecha(), "dd/MM/yyyy"));
						} catch (ParseException e) {
					
						}
						
						fkm.setMotivo(km.getMotivo());
						fkm.setNumeroViajes(km.getViajes());
						fkm.setkilometrajeRecorridoCalculado(km.getKilometros_recorridos());
						fkm.setImporte(Utilerias.convertStringToBigDecimal(km.getImporte()));
						//defaults
						fkm.setActivo(Etiquetas.UNO_S);
						fkm.setCreacionFecha(new Date());
						fkm.setCreacionUsuario(usuarioSession.getIdUsuario());
						
						facturaKilometrajeService.createFacturaKilometraje(fkm);
					}
				}
			}
			
		}
		
		return new ModelAndView("redirect:kilometraje?id="+idSolicitud);
	}
	
	
	// metodo ajax para obtener el kilometra a partir de origen y destino
	@RequestMapping(value = "/getKilometraje", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getKilometraje(HttpSession session, @RequestParam Integer idOrigen,
			@RequestParam Integer idDestino, @RequestParam String idElemento, HttpServletRequest request,
			HttpServletResponse response) {

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();

		Integer kilometrajeRecorrido = 0;

		// encontrar el kilometraje
		List<KilometrajeRecorrido> kilometraje = kilometrajeRecorridoService.getAllKilometrajeRecorrido();
		for (KilometrajeRecorrido km : kilometraje) {
			if (idOrigen == km.getKilometrajeUbicacionByIdOrigen().getIdUbicacion()
					&& idDestino == km.getKilometrajeUbicacionByIdDestino().getIdUbicacion()) {
				kilometrajeRecorrido = km.getNumeroKilometros();
				break;
			}
		}

		// para identificar el target del elemento en DOM para agregar las
		// opciones
		String str = idElemento;
		str = str.replaceAll("[^-?0-9]+", " ");
		String idKilometraje = "kilometrajeDesgloseList@.kilometros_recorridos";

		idKilometraje = idKilometraje.replace("@", str);
		idKilometraje = idKilometraje.replaceAll("\\s+", "");
         
		result.put("kilometrajeRecorrido", kilometrajeRecorrido.toString());
		result.put("idKilometraje", idKilometraje);

		// bind json
		ObjectMapper map = new ObjectMapper();
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	  
	  
		// metodo ajax para obtener el kilometra a partir de origen y destino
	  @RequestMapping(value = "/getDestinos", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody
	    ResponseEntity<String> getDestinos(HttpSession session, @RequestParam Integer idOrigen, @RequestParam String idElemento, HttpServletRequest request, HttpServletResponse response) {
		  
		    String json = null;
	        HashMap<String, String> result = new HashMap<String, String>();
	        
	        StringBuilder row = new StringBuilder();
	        List<KilometrajeUbicacion> ubicacionesAux = new ArrayList<>();

	        //obtener las ubicaciones de destino
			List<KilometrajeRecorrido> kilometraje = kilometrajeRecorridoService.getAllKilometrajeRecorrido();
			for (KilometrajeRecorrido km : kilometraje) {
				if (idOrigen == km.getKilometrajeUbicacionByIdOrigen().getIdUbicacion()) {
					ubicacionesAux.add(km.getKilometrajeUbicacionByIdDestino());
				}
			}
			
			 // armado de html para la lista de destinos
			 row.append("<option value=\"-1\">Seleccione:</option>");
			 for(KilometrajeUbicacion ubicacion : ubicacionesAux){
					row.append("<option value=\"").append(ubicacion.getIdUbicacion()).append("\" ").append(" >").append(ubicacion.getDescripcion()).append("</option>");
			 }
			 
			// para identificar el target del elemento en DOM para agregar las opciones
		    // de categoria mayor y menor dependiento de la seleccion del AID
	        String str = idElemento;
	        str = str.replaceAll("[^-?0-9]+", " ");
	        String idDestino = "kilometrajeDesgloseList@.destino.idUbicacion";
	        
	        idDestino = idDestino.replace("@", str);
	        idDestino = idDestino.replaceAll("\\s+","");
			
	        result.put("optionsDestino", row.toString());
		    result.put("idDestino", idDestino);
		    
		  
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
	
	  
		
		// metodo ajax para obtener el kilometra a partir de origen y destino
		@RequestMapping(value = "/getImporte", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> getImporte(HttpSession session, @RequestParam String kilometros, @RequestParam String idElemento,
				Integer viajes, Integer idOrigen, Integer idDestino, HttpServletRequest request,
				HttpServletResponse response) {

			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();
			
			Integer kilometrajeRecorrido = 0;

			// encontrar el kilometraje
			List<KilometrajeRecorrido> kilometraje = kilometrajeRecorridoService.getAllKilometrajeRecorrido();
			for (KilometrajeRecorrido km : kilometraje) {
				if (idOrigen == km.getKilometrajeUbicacionByIdOrigen().getIdUbicacion()
						&& idDestino == km.getKilometrajeUbicacionByIdDestino().getIdUbicacion()) {
					kilometrajeRecorrido = km.getNumeroKilometros();
					break;
				}
			}

			Double importeTotal = 0D;
			String importeAutoPropio = parametroService.getParametroByName("importeAutoPropio").getValor();
			/* CALCULO DE IMPORTE VIJES X KM X MONTO FIJO ***/
			Integer totalKm = Etiquetas.CERO;
			// revisar nullos y vacios para evitar errores en el calculo
			if(kilometros != null && kilometros.isEmpty() == false){
			  if(viajes != null && viajes > Etiquetas.CERO){
			    totalKm = viajes * kilometrajeRecorrido;
			  }else{
				totalKm = Etiquetas.CERO * Integer.parseInt(kilometros);
			  }
			}else{
		      totalKm = viajes * Etiquetas.CERO;
			}
			
			//si aun no captura la cantidad de viajes pero si selecciono origen y destino entonces 
			//se setea la cantidad de km en el total de km.
			if (totalKm == 0D) {
				if(idOrigen == Etiquetas.OTRO_DESTINO || idDestino == Etiquetas.OTRO_DESTINO){
					float floatTotalKm = Float.parseFloat(kilometros);
					totalKm = Math.round(floatTotalKm);
					//totalKm = Integer.parseInt(kilometros);
				}else{
					if (kilometros != null && kilometros.isEmpty() == false && Double.parseDouble(kilometros) > 0D) {
						totalKm = Integer.parseInt(kilometrajeRecorrido.toString());
					}	
				}
			}
			
			// importe total del resultado del total de km por el parametro fijo de importe 
			// si no hay numero de viajes ingresado el calculo debe ser cero. 
			if(viajes != null && viajes > Etiquetas.CERO){
				importeTotal = totalKm * Double.parseDouble(importeAutoPropio);
			}else{
				importeTotal = Etiquetas.CERO * Double.parseDouble(importeAutoPropio);
			}
			

			// para identificar el target del elemento en DOM para agregar las
			// opciones
			String str = idElemento;
			str = str.replaceAll("[^-?0-9]+", " ");
			String idElementoImporte = "kilometrajeDesgloseList@.importe";

			idElementoImporte = idElementoImporte.replace("@", str);
			idElementoImporte = idElementoImporte.replaceAll("\\s+", "");
	         
			result.put("importe", importeTotal.toString());
			result.put("kmtotales", totalKm.toString());
			result.put("idElementoImporte", idElementoImporte);

			// bind json
			ObjectMapper map = new ObjectMapper();
			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// respuesta
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		  
		
		private List<Locacion> getLocacionesPermitidasPorUsuario(List<UsuarioConfSolicitante> uconfigSol, Integer tipoSolicitud) {

			// cargar los tipos de locacion permitidos por usuario
			List<Integer> idsLocaciones = new ArrayList<>();
			for (UsuarioConfSolicitante conf : uconfigSol) {
				if (conf != null && conf.getTipoSolicitud().getIdTipoSolicitud() == tipoSolicitud) {
					idsLocaciones.add(conf.getLocacion().getIdLocacion());
				}
			}

			List<Locacion> lc = locacionService.getAllLocaciones();
			List<Locacion> lcPermitidas = new ArrayList<>();
			for (Locacion l : lc) {
				if (idsLocaciones.contains(l.getIdLocacion())) {
					lcPermitidas.add(l);
				}
			}

			return lcPermitidas;
		}
		
		private Integer getRecorrido(Integer origen, Integer destino){
			Integer recorridoMatch = null;
			
			List<KilometrajeRecorrido> recorridos = kilometrajeRecorridoService.getAllKilometrajeRecorrido();
			for(KilometrajeRecorrido recorrido : recorridos){
				if(recorrido.getKilometrajeUbicacionByIdOrigen().getIdUbicacion() == origen && 
						recorrido.getKilometrajeUbicacionByIdDestino().getIdUbicacion() == destino){
					recorridoMatch = recorrido.getIdKilometrajeRecorrido();
					break;
				}
			}
			
			return recorridoMatch;
		}
		
		//obtiene las ubicaiones origen y destino por el id de recorrido configurado en la base de datos.
		private  HashMap<String, Integer> getUbicaciones(Integer idRecorrido){
			
			HashMap<String, Integer> ubicaciones = new HashMap<>();
			KilometrajeRecorrido kmRecorrido = kilometrajeRecorridoService.getKilometrajeRecorrido(idRecorrido);
			ubicaciones.put("origen", kmRecorrido.getKilometrajeUbicacionByIdOrigen().getIdUbicacion());
			ubicaciones.put("destino", kmRecorrido.getKilometrajeUbicacionByIdDestino().getIdUbicacion());
			
			return ubicaciones;
		}
		  
	
		// metodo ajax para obtener el kilometra a partir de origen y destino
		@RequestMapping(value = "/verificarFecha", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> verificarFecha(HttpSession session, @RequestParam String fecha, @RequestParam Integer idSolicitud, HttpServletRequest request,HttpServletResponse response) {

			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();
			Boolean fechaOK = false;
			
			
		if (fecha != null && fecha.isEmpty() == false) {

			String importeAutoPropio = parametroService.getParametroByName("rangoDiasKilometraje").getValor();
			Integer rangoInteger = Integer.parseInt(importeAutoPropio);

			Date fechaDate = null;
			try {
				fechaDate = Utilerias.parseDate(fecha, "dd/MM/yyyy");
			} catch (ParseException e) {

			}

			if (idSolicitud != null && idSolicitud > Etiquetas.CERO) {
				// obtener fecha de la solicitud guardada
				Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);
				Date fechaSolicitud = solicitud.getCreacionFecha();
				// converir fecha tipo Date en LocalDate para usarla en la resta
				LocalDate fechaSolicitudLocalDate = fechaSolicitud.toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				// convertir fecha en localdate para restar los dias
				// establecidos en parametro
				LocalDate fechaLimite = fechaSolicitudLocalDate.minusDays(rangoInteger);
				// regresar fecha de localDate a Date para revisar si se
				// encuentra en el rango
				Date fechaLimiteDate = Date.from(fechaLimite.atStartOfDay(ZoneId.systemDefault()).toInstant());
				// si esta en el rango se marca como valida de lo contrario se
				// queda con el valor falso default.
				if (fechaDate.before(fechaSolicitud) && fechaDate.after(fechaLimiteDate)) {
					fechaOK = true;
				}

			} else {
				// la misma logica pero aqui es tomando la fecha actual cuando
				// la solicitud no esta guardada.
				Date fechaSolicitud = new Date();
				LocalDate fechaLimite = LocalDate.now().minusDays(rangoInteger);
				Date fechaLimiteDate = Date.from(fechaLimite.atStartOfDay(ZoneId.systemDefault()).toInstant());
				if (fechaDate.before(fechaSolicitud) && fechaDate.after(fechaLimiteDate)) {
					fechaOK = true;
				}
			}
			
			if(fechaOK){
				result.put("respuesta", "valida");
			}else{
				result.put("respuesta", "no-valida");
			}
			
		}else{
			result.put("respuesta", "no-valida-null");
		}

			// bind json
			ObjectMapper map = new ObjectMapper();
			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// respuesta
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		  
		
		
		private BigDecimal getMontoTotalKilometraje(KilometrajeDTO dto){
			
			BigDecimal monto = new BigDecimal("0");
			
			if (dto.getKilometrajeDesgloseList() != null && dto.getKilometrajeDesgloseList().isEmpty() == false) {
				for (KilometrajeDesgloseDTO desglose : dto.getKilometrajeDesgloseList()) {
	              monto = monto.add(Utilerias.convertStringToBigDecimal(desglose.getImporte()));
				}
			}
						
			return monto;
		}
		
		
		// metodo ajax para obtener el kilometra a partir de origen y destino
		@RequestMapping(value = "/revisarArchivosEnSolicitud", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		public @ResponseBody ResponseEntity<String> revisarArchivosEnSolicitud(HttpSession session,@RequestParam Integer idSolicitud, HttpServletRequest request,HttpServletResponse response) {

			
			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();
			
			
            // contiene archivos la solicitud?			
			List<SolicitudArchivo> archivos =  solicitudArchivoService.getAllSolicitudArchivoBySolicitud(idSolicitud);
			if(archivos != null && archivos.isEmpty() == false){
				result.put("respuesta", "true");
			}else{
				result.put("respuesta", "false");
			}
			
			
			// bind json
			ObjectMapper map = new ObjectMapper();
			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// respuesta
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		
		
}
