package com.lowes.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.lowes.dto.AerolineaDTO;
import com.lowes.dto.AnticipoDTO;
import com.lowes.dto.ViajeConceptoDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Factura;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAnticipoViaje;
import com.lowes.entity.SolicitudAnticipoViajeAerolinea;
import com.lowes.entity.SolicitudAnticipoViajeConceptos;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.entity.ViajeConcepto;
import com.lowes.entity.ViajeDestino;
import com.lowes.entity.ViajeMotivo;
//import com.lowes.entity.Locacion;
//import com.lowes.entity.Moneda;
import com.lowes.service.CompaniaService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudAnticipoViajeAerolineaService;
import com.lowes.service.SolicitudAnticipoViajeConceptosService;
import com.lowes.service.SolicitudAnticipoViajeService;
import com.lowes.service.SolicitudService;
//import com.lowes.service.LocacionService;
//import com.lowes.service.MonedaService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.service.ViajeConceptoService;
import com.lowes.service.ViajeDestinoService;
import com.lowes.service.ViajeMotivoService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AnticipoViajeController {

	
	@Autowired
	private CompaniaService companiaService;
	@Autowired
	private LocacionService locacionService;
	@Autowired
	private UsuarioConfSolicitanteService usuarioConfSolicitanteService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private MonedaService monedaService;
	@Autowired
	private FormaPagoService formaPagoService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private SolicitudAnticipoViajeService solicitudAnticipoViajeService;
	@Autowired
	private SolicitudAnticipoViajeAerolineaService solicitudAnticipoViajeAerolineaService;
	@Autowired
	private SolicitudAnticipoViajeConceptosService solicitudAnticipoViajeConceptoService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private FacturaDesgloseService facturaDesgloseService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private ViajeDestinoService viajeDestinoService;
	@Autowired
	private ViajeMotivoService viajeMotivoService;
	@Autowired
	private ViajeConceptoService viajeConceptoService;
	
	private static final Logger logger = Logger.getLogger(ReembolsosController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@RequestMapping("/anticipoViaje")
	public ModelAndView anticipoViaje(HttpSession session, Integer id) {
		
		//declaraciones de objetos
		HashMap<String, Object> modelo = new HashMap<>();
		AnticipoDTO anticipoDTO = new AnticipoDTO();
		Solicitud solicitud = new Solicitud();
		Integer idEstadoSolicitud = 0;
 		
		// lista de compañias para el combo.
		List<Compania> companiaslst = companiaService.getAllCompania();
		
		//locaciones permitidas por usuario
		// configuraciones permitidas por usuario
		// extraer usuario en session para guardar la solicitud.
		Usuario usuarioSession = usuarioService.getUsuarioSesion();
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(usuarioSession.getIdUsuario());
		List<Locacion> locacionesPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE,locacionService.getAllLocaciones(), usuarioSession.getIdUsuario());

		// set la locacion correspondiente al usuario
		anticipoDTO.setLocacion(usuarioSession.getLocacion());
		List<Moneda> monedaList = monedaService.getAllMoneda();
		anticipoDTO.setMoneda(new Moneda(Etiquetas.ID_PESOS));
		anticipoDTO.setFormaPago(new FormaPago(Etiquetas.ID_TRANSFERENCIA));
		anticipoDTO.setCompania(new Compania(Etiquetas.ID_COMPANIA_LOWES ));
		
		//Carga lista de destinos
		List<ViajeDestino> viajeDestinoList = viajeDestinoService.getAllViajeDestino();
		
		//Carga lista de motivos
		List<ViajeMotivo> viajeMotivoList = viajeMotivoService.getAllViajeMotivo();
		
		//Carga lista de conceptos
		//List<ViajeConcepto> viajeConceptoList = viajeConceptoService.getAllViajeConcepto();
		List<ViajeConcepto> viajeConceptoList = viajeConceptoService.getAllViajeConceptoAnticipo();
		
		List<ViajeConceptoDTO> conceptos = null;
		
		
		//Carga lista de aerolineas
		List<AerolineaDTO> aerolineas = null;
		
		
		// si trae un parametro numerico para mostrar solicitud se llama al servicio
		if(id != null && id > Etiquetas.CERO){
			solicitud = solicitudService.getSolicitud(id);
			if(solicitud != null){
				anticipoDTO.setIdSolicitudSession(solicitud.getIdSolicitud());
				idEstadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
				anticipoDTO.setCompania(solicitud.getCompania());
				anticipoDTO.setFormaPago(new FormaPago(Etiquetas.ID_TRANSFERENCIA));
				anticipoDTO.setLocacion(solicitud.getLocacion());
				anticipoDTO.setMoneda(solicitud.getMoneda());
				anticipoDTO.setImporteTotal(solicitud.getMontoTotal().toString());
				anticipoDTO.setConcepto(solicitud.getConceptoGasto());
				anticipoDTO.setTipoProveedor(solicitud.getTipoProveedor());
				anticipoDTO.setBeneficiario(solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario());
				
				
				//Datos de anticipo gastos de viaje
				SolicitudAnticipoViaje anticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(solicitud.getIdSolicitud());
				if(anticipoViaje != null){
					//Set si Otro Viaje Destino
					ViajeDestino vd = viajeDestinoService.getViajeDestino(anticipoViaje.getViajeDestino().getIdViajeDestino());
					if(vd.getEsOtro() != null){
						if(vd.getEsOtro() == 1)
							anticipoDTO.setOtroDestino(anticipoViaje.getDescripcionOtroDestino());
					}
					//Set si Otro Viaje Motivo
					ViajeMotivo vm = viajeMotivoService.getViajeMotivo(anticipoViaje.getViajeMotivo().getIdViajeMotivo());
					if(vm.getEsOtro() != null){
						if(vm.getEsOtro() == 1)
							anticipoDTO.setOtroMotivo(anticipoViaje.getDescripcionOtroMotivo());
					}
					anticipoDTO.setViajeDestino(anticipoViaje.getViajeDestino());
					anticipoDTO.setViajeMotivo(anticipoViaje.getViajeMotivo());
					anticipoDTO.setFechaSalida(Utilerias.convertDateFormat(anticipoViaje.getInicioFecha()));
					anticipoDTO.setFechaRegreso(Utilerias.convertDateFormat(anticipoViaje.getFinFecha()));
					anticipoDTO.setNumeroPersonas(anticipoViaje.getNumPersonas());
					anticipoDTO.setMotivoAerolinea(anticipoViaje.getMotivoCotizacion());
				}

				
				//Información de transporte aereo
				List<SolicitudAnticipoViajeAerolinea> SolAntAerolineas = solicitudAnticipoViajeAerolineaService.getAllSolicitudAnticipoViajeAerolineaBySol(anticipoViaje.getIdSolicitudAnticipoViaje());
				if(!SolAntAerolineas.isEmpty()){
					anticipoDTO.setTransporteAereo(true);
					aerolineas = initializeWith(SolAntAerolineas, anticipoViaje.getIdSolicitudAerolineaSeleccionado());
					
				}
				else{
					anticipoDTO.setTransporteAereo(false);
					aerolineas = initializeWith(3);
				}
				
				//Información de conceptos de gasto de viaje
				List<SolicitudAnticipoViajeConceptos> SolAntConceptos = solicitudAnticipoViajeConceptoService.getAllSolicitudAnticipoViajeConceptoBySol(anticipoViaje.getIdSolicitudAnticipoViaje());
				if(!SolAntConceptos.isEmpty()){
					conceptos = initializeConceptos(viajeConceptoList, SolAntConceptos, anticipoViaje.getDescripcionOtro());
				}
				else{
					conceptos = initializeConceptoList(viajeConceptoList);
				}
				
				
				//revisar si se trata de una modificaciï¿½n para activar mensaje en la vista
				if(session.getAttribute("actualizacion") != null){
					anticipoDTO.setModificacion(Etiquetas.TRUE);
					session.setAttribute("actualizacion", null);
				}else{
					anticipoDTO.setModificacion(Etiquetas.FALSE);
				}
				
				//revisar si se trata de un nuevo registro para activar mensaje en la vista
				if(session.getAttribute("creacion") != null){
					anticipoDTO.setCreacion(Etiquetas.TRUE);
					session.setAttribute("creacion", null);
				}else{
					anticipoDTO.setCreacion(Etiquetas.FALSE);
				}
				
			}
		}
		//Nuevo anticipo
		else{
			anticipoDTO.setImporteTotal("0.0");
			conceptos = initializeConceptoList(viajeConceptoList);
			aerolineas = initializeWith(3);
		}
		
		List<FormaPago> formaPagoList = formaPagoService.getAllFormaPago();
		
		//objetos para la vista
		anticipoDTO.setAerolineas(aerolineas);
		anticipoDTO.setConceptos(conceptos);
		modelo.put("companiaList", companiaslst);
		modelo.put("viajeDestinoList", viajeDestinoList);
		modelo.put("viajeMotivoList", viajeMotivoList);
		modelo.put("viajeConceptoList", viajeConceptoList);
		modelo.put("locacionesPermitidas", locacionesPermitidas);
		modelo.put("anticipoDTO", anticipoDTO);
		modelo.put("monedaList", monedaList);
		modelo.put("formaPagoList", formaPagoList);
		modelo.put("idEstadoSolicitud", idEstadoSolicitud);

		return new ModelAndView("anticipoViaje",modelo);
		
	}

	private List<ViajeConceptoDTO> initializeConceptos(List<ViajeConcepto> viajeConceptoList, List<SolicitudAnticipoViajeConceptos> solAntConceptos, String descripcionOtro) {
		for(ViajeConcepto viajeConcepto : viajeConceptoList){
			for(SolicitudAnticipoViajeConceptos concepto : solAntConceptos){
				if(viajeConcepto.getIdViajeConcepto() == concepto.getViajeConcepto().getIdViajeConcepto()){
					if (viajeConcepto.getEsOtro() == 1)
						viajeConcepto.setDescripcion(descripcionOtro);
					viajeConcepto.setImporte(concepto.getImporte());
				}
			}
		}
		return initializeConceptoList(viajeConceptoList);
	}

	private List<AerolineaDTO> initializeWith(List<SolicitudAnticipoViajeAerolinea> anticipoViaje, int idSeleccionado) {
		List<AerolineaDTO> lst = new ArrayList<>();
		for(SolicitudAnticipoViajeAerolinea aerolinea : anticipoViaje){
			lst.add(new AerolineaDTO(aerolinea, (aerolinea.getIdSolicitudAnticipoViajeAerolinea() == idSeleccionado ? true : false)));
		}
		return lst;
	}

	private List<ViajeConceptoDTO> initializeConceptoList(List<ViajeConcepto> viajeConceptoList) {
		List<ViajeConceptoDTO> list = new ArrayList<>();
		for(ViajeConcepto concepto : viajeConceptoList){
			list.add(new ViajeConceptoDTO(concepto));
		}
		return list;
	}

	private List<AerolineaDTO> initializeWith(int size) {
		List<AerolineaDTO> lst = new ArrayList<>();
		for(int i = 0; i < size; i++){
			lst.add(new AerolineaDTO(i+1, false));
		}
		return lst;
	}

	@RequestMapping(value = "/saveAnticipoViaje", method = RequestMethod.POST)
	public ModelAndView saveAnticipoViaje(@ModelAttribute("anticipoDTO") AnticipoDTO anticipoDTO, HttpSession session) {
		Integer idSolicitud = 0;
		Integer idSolicitudAnticipoViaje = 0;
		Integer idSolicitudAnticipoViajeAerolinea = 0;
		Solicitud solicitud = new Solicitud();
		Usuario user = usuarioService.getUsuarioSesion();

		if (anticipoDTO.getIdSolicitudSession() != null && anticipoDTO.getIdSolicitudSession() > 0) {
			// Update petición
			solicitud = solicitudService.getSolicitud(anticipoDTO.getIdSolicitudSession());
			solicitud.setCompania(anticipoDTO.getCompania());
			solicitud.setFormaPago(new FormaPago(Etiquetas.ID_TRANSFERENCIA));
			solicitud.setLocacion(anticipoDTO.getLocacion());
			solicitud.setMoneda(anticipoDTO.getMoneda());
			solicitud.setConceptoGasto(anticipoDTO.getConcepto());
			BigDecimal montoTotal = Utilerias.convertStringToBigDecimal(anticipoDTO.getImporteTotal());
			solicitud.setMontoTotal(montoTotal);
			solicitud.setModificacionFecha(new Date());
			solicitud.setModificacionUsuario(user.getIdUsuario());
			solicitud.setTipoProveedor(anticipoDTO.getTipoProveedor());
			
			solicitud = solicitudService.updateSolicitud(solicitud);
			idSolicitud = solicitud.getIdSolicitud();
			
			//Update en Solicitud anticipo viaje
			if (idSolicitud != null && idSolicitud > Etiquetas.CERO) {
				SolicitudAnticipoViaje solicitudAnticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(idSolicitud);
				solicitudAnticipoViaje.setSolicitud(solicitudService.getSolicitud(idSolicitud));
				//Set si Otro Viaje Destino
				ViajeDestino vd = viajeDestinoService.getViajeDestino(anticipoDTO.getViajeDestino().getIdViajeDestino());
				if(vd.getEsOtro() != null){
					if(vd.getEsOtro() == 1)
						solicitudAnticipoViaje.setDescripcionOtroDestino(anticipoDTO.getOtroDestino());
				}
				//Set si Otro Viaje Motivo
				ViajeMotivo vm = viajeMotivoService.getViajeMotivo(anticipoDTO.getViajeMotivo().getIdViajeMotivo());
				if(vm.getEsOtro() != null){
					if(vm.getEsOtro() == 1)
						solicitudAnticipoViaje.setDescripcionOtroMotivo(anticipoDTO.getOtroMotivo());
				}
				solicitudAnticipoViaje.setViajeDestino(anticipoDTO.getViajeDestino());
				solicitudAnticipoViaje.setViajeMotivo(anticipoDTO.getViajeMotivo());
				try {
					solicitudAnticipoViaje.setInicioFecha(Utilerias.parseDate(anticipoDTO.getFechaSalida(), "dd/MM/yyyy"));
					solicitudAnticipoViaje.setFinFecha(Utilerias.parseDate(anticipoDTO.getFechaRegreso(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				solicitudAnticipoViaje.setNumPersonas(anticipoDTO.getNumeroPersonas());
				solicitudAnticipoViaje.setMotivoCotizacion(anticipoDTO.getMotivoAerolinea());
				
				//check aerolineas viaje aereo
				if(anticipoDTO.getAerolineas() != null){
					if(anticipoDTO.getAerolineas() != null){
						for(AerolineaDTO aerolinea : anticipoDTO.getAerolineas()){
							if(aerolinea.getSeleccionado())
								solicitudAnticipoViaje.setIdSolicitudAerolineaSeleccionado(aerolinea.getId());
						}
					}
				}else{
					solicitudAnticipoViaje.setMotivoCotizacion(null);
					solicitudAnticipoViaje.setIdSolicitudAerolineaSeleccionado(0);
				}
				//check otro concepto
				for(ViajeConceptoDTO concepto : anticipoDTO.getConceptos()){
					if(concepto.isSeleccionado()){
						if (concepto.getEsOtro() == 1){
							solicitudAnticipoViaje.setDescripcionOtro(concepto.getDescripcion());
							break;
						}
					}
				}
				solicitudAnticipoViaje.setActivo(Etiquetas.UNO_S);
				solicitudAnticipoViaje.setModificacionUsuario(user.getIdUsuario());
				solicitudAnticipoViaje.setModificacionFecha(new Date());
				
				solicitudAnticipoViajeService.updateSolicitudAnticipoViaje(solicitudAnticipoViaje);
				idSolicitudAnticipoViaje = solicitudAnticipoViaje.getIdSolicitudAnticipoViaje();
			}
			
			//Guarda en Solicitud anticipo viaje aerolinea
			SolicitudAnticipoViaje anticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViaje(idSolicitudAnticipoViaje);
			int index = 1;
			if (idSolicitudAnticipoViaje != null && idSolicitudAnticipoViaje > Etiquetas.CERO && anticipoDTO.isTransporteAereo()) {
				if(anticipoDTO.getAerolineas() != null){
					for(AerolineaDTO aerolinea : anticipoDTO.getAerolineas()){
						SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea = solicitudAnticipoViajeAerolineaService.getSolicitudAnticipoViajeAerolinea(aerolinea.getId());
						if(solicitudAnticipoViajeAerolinea != null){
							solicitudAnticipoViajeAerolinea.setSolicitudAnticipoViaje(anticipoViaje);
							solicitudAnticipoViajeAerolinea.setNombreAerolinea(aerolinea.getNombre());
							solicitudAnticipoViajeAerolinea.setCostoAerolinea(Utilerias.convertStringToBigDecimal(aerolinea.getCosto()));
							solicitudAnticipoViajeAerolinea.setActivo(Etiquetas.UNO_S);
							solicitudAnticipoViajeAerolinea.setModificacionUsuario(user.getIdUsuario());
							solicitudAnticipoViajeAerolinea.setModificacionFecha(new Date());
							
							solicitudAnticipoViajeAerolineaService.updateSolicitudAnticipoViajeAerolinea(solicitudAnticipoViajeAerolinea);
							
							if (aerolinea.getSeleccionado()){
								anticipoViaje.setIdSolicitudAerolineaSeleccionado(solicitudAnticipoViajeAerolinea.getIdSolicitudAnticipoViajeAerolinea());
								solicitudAnticipoViajeService.updateSolicitudAnticipoViaje(anticipoViaje);
							}
						}else{
							solicitudAnticipoViajeAerolinea = new SolicitudAnticipoViajeAerolinea();
							solicitudAnticipoViajeAerolinea.setSolicitudAnticipoViaje(solicitudAnticipoViajeService.getSolicitudAnticipoViaje(idSolicitudAnticipoViaje));
							solicitudAnticipoViajeAerolinea.setNombreAerolinea(aerolinea.getNombre());
							solicitudAnticipoViajeAerolinea.setCostoAerolinea(Utilerias.convertStringToBigDecimal(aerolinea.getCosto()));
							solicitudAnticipoViajeAerolinea.setActivo(Etiquetas.UNO_S);
							solicitudAnticipoViajeAerolinea.setCreacionFecha(new Date());
							solicitudAnticipoViajeAerolinea.setCreacionUsuario(user.getIdUsuario());
							
							idSolicitudAnticipoViajeAerolinea = solicitudAnticipoViajeAerolineaService.createSolicitudAnticipoViajeAerolinea(solicitudAnticipoViajeAerolinea);
							if (index == anticipoViaje.getIdSolicitudAerolineaSeleccionado()){
								anticipoViaje.setIdSolicitudAerolineaSeleccionado(idSolicitudAnticipoViajeAerolinea);
								solicitudAnticipoViajeService.updateSolicitudAnticipoViaje(anticipoViaje);
							}
							index++;	
						}
					}
				}else{
					List<SolicitudAnticipoViajeAerolinea> aeros = solicitudAnticipoViajeAerolineaService.getAllSolicitudAnticipoViajeAerolineaBySol(idSolicitudAnticipoViaje);
					for(SolicitudAnticipoViajeAerolinea a : aeros){
						solicitudAnticipoViajeAerolineaService.deleteSolicitudAnticipoViajeAerolinea(a.getIdSolicitudAnticipoViajeAerolinea());	
					}
				}

			}
			
			Integer idSolicitudAnticipoViajeConcepto= 0;
			//Guarda en Solicitud anticipo viaje aerolinea
			if (idSolicitudAnticipoViaje != null && idSolicitudAnticipoViaje > Etiquetas.CERO) {
				List<SolicitudAnticipoViajeConceptos> lstConceptos = solicitudAnticipoViajeConceptoService.getAllSolicitudAnticipoViajeConceptoBySol(idSolicitudAnticipoViaje);
				List<ViajeConceptoDTO> conceptos = anticipoDTO.getConceptos();
				
				for(ViajeConceptoDTO concepto : anticipoDTO.getConceptos()){
					if(concepto.isSeleccionado()){
						SolicitudAnticipoViajeConceptos conceptoSelec = getViajeConceptoByDTO(lstConceptos, concepto.getIdViajeConcepto());
						if(conceptoSelec != null){
							logger.info("Se actualiza el concepto.");
							conceptoSelec.setImporte(Utilerias.convertStringToBigDecimal(concepto.getImporte()));
							conceptoSelec.setActivo(Etiquetas.UNO_S);
							conceptoSelec.setModificacionUsuario(user.getIdUsuario());
							conceptoSelec.setModificacionFecha(new Date());
							solicitudAnticipoViajeConceptoService.updateSolicitudAnticipoViajeConceptos(conceptoSelec);
						}
						else{
							logger.info("Se agrega un nuevo concepto.");
							conceptoSelec = new SolicitudAnticipoViajeConceptos();
							conceptoSelec.setSolicitudAnticipoViaje(solicitudAnticipoViajeService.getSolicitudAnticipoViaje(idSolicitudAnticipoViaje));
							conceptoSelec.setViajeConcepto(viajeConceptoService.getViajeConcepto(concepto.getIdViajeConcepto()));
							conceptoSelec.setImporte(Utilerias.convertStringToBigDecimal(concepto.getImporte()));
							
							conceptoSelec.setActivo(Etiquetas.UNO_S);
							conceptoSelec.setCreacionFecha(new Date());
							conceptoSelec.setCreacionUsuario(user.getIdUsuario());
							
							idSolicitudAnticipoViajeConcepto = solicitudAnticipoViajeConceptoService.createSolicitudAnticipoViajeConceptos(conceptoSelec);
						}
					}else{
						logger.info("Se elimina el concepto.");
						SolicitudAnticipoViajeConceptos conceptoDelete = getViajeConceptoByDTO(lstConceptos, concepto.getIdViajeConcepto());
						if(conceptoDelete != null)
							solicitudAnticipoViajeConceptoService.deleteSolicitudAnticipoViajeConceptos(conceptoDelete.getIdSolicitudAnticipoViajeConceptos());	
					}
				}
			}
			
			// var en session para mensaje en la vista.
			session.setAttribute("actualizacion", Etiquetas.TRUE);
			
		} else {
			
			//guardando informacion de la solicitud de anticipo por primera vez.
			solicitud.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_NUEVA));
			solicitud.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE));
			solicitud.setUsuarioByIdUsuario(user);
			solicitud.setUsuarioByIdUsuarioSolicita(user);
			solicitud.setCompania(anticipoDTO.getCompania());
			solicitud.setFormaPago(new FormaPago(Etiquetas.ID_TRANSFERENCIA));
			solicitud.setLocacion(anticipoDTO.getLocacion());
			solicitud.setMoneda(anticipoDTO.getMoneda());
			solicitud.setConceptoGasto(anticipoDTO.getConcepto());
			BigDecimal montoTotal = Utilerias.convertStringToBigDecimal(anticipoDTO.getImporteTotal());
			solicitud.setMontoTotal(montoTotal);
			solicitud.setActivo(Etiquetas.UNO_S);
			solicitud.setCreacionFecha(new Date());
			solicitud.setCreacionUsuario(user.getIdUsuario());

			//hibernate
			idSolicitud = solicitudService.createSolicitud(solicitud);
			
			
			//Guarda en Solicitud anticipo viaje
			if (idSolicitud != null && idSolicitud > Etiquetas.CERO) {
				SolicitudAnticipoViaje solicitudAnticipoViaje = new SolicitudAnticipoViaje();
				solicitudAnticipoViaje.setSolicitud(solicitudService.getSolicitud(idSolicitud));
				//Set si Otro Viaje Destino
				ViajeDestino vd = viajeDestinoService.getViajeDestino(anticipoDTO.getViajeDestino().getIdViajeDestino());
				if(vd.getEsOtro() != null){
					if(vd.getEsOtro() == 1)
						solicitudAnticipoViaje.setDescripcionOtroDestino(anticipoDTO.getOtroDestino());
				}
				//Set si Otro Viaje Motivo
				ViajeMotivo vm = viajeMotivoService.getViajeMotivo(anticipoDTO.getViajeMotivo().getIdViajeMotivo());
				if(vm.getEsOtro() != null){
					if(vm.getEsOtro() == 1)
						solicitudAnticipoViaje.setDescripcionOtroMotivo(anticipoDTO.getOtroMotivo());
				}
				solicitudAnticipoViaje.setViajeDestino(anticipoDTO.getViajeDestino());
				solicitudAnticipoViaje.setViajeMotivo(anticipoDTO.getViajeMotivo());
				try {
					solicitudAnticipoViaje.setInicioFecha(Utilerias.parseDate(anticipoDTO.getFechaSalida(), "dd/MM/yyyy"));
					solicitudAnticipoViaje.setFinFecha(Utilerias.parseDate(anticipoDTO.getFechaRegreso(), "dd/MM/yyyy"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				solicitudAnticipoViaje.setNumPersonas(anticipoDTO.getNumeroPersonas());
				solicitudAnticipoViaje.setMotivoCotizacion(anticipoDTO.getMotivoAerolinea());
				
				//check aerolineas viaje aereo
				if(anticipoDTO.isTransporteAereo()){
					if(anticipoDTO.getAerolineas() != null){
						for(AerolineaDTO aerolinea : anticipoDTO.getAerolineas()){
							if(aerolinea.getSeleccionado())
								solicitudAnticipoViaje.setIdSolicitudAerolineaSeleccionado(aerolinea.getId());
						}
					}
				}
				
				//check otro concepto
				for(ViajeConceptoDTO concepto : anticipoDTO.getConceptos()){
					if(concepto.isSeleccionado()){
						if (concepto.getEsOtro() == 1){
							solicitudAnticipoViaje.setDescripcionOtro(concepto.getDescripcion());
							break;
						}
					}
				}

				solicitudAnticipoViaje.setActivo(Etiquetas.UNO_S);
				solicitudAnticipoViaje.setCreacionFecha(new Date());
				solicitudAnticipoViaje.setCreacionUsuario(user.getIdUsuario());
				
				idSolicitudAnticipoViaje = solicitudAnticipoViajeService.createSolicitudAnticipoViaje(solicitudAnticipoViaje);
			}
			
			//Guarda en Solicitud anticipo viaje aerolinea
			SolicitudAnticipoViaje anticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViaje(idSolicitudAnticipoViaje);
			int index = 1;
			if (idSolicitudAnticipoViaje != null && idSolicitudAnticipoViaje > Etiquetas.CERO && anticipoDTO.isTransporteAereo()) {
				for(AerolineaDTO aerolinea : anticipoDTO.getAerolineas()){
					SolicitudAnticipoViajeAerolinea solicitudAnticipoViajeAerolinea = new SolicitudAnticipoViajeAerolinea();
					solicitudAnticipoViajeAerolinea.setSolicitudAnticipoViaje(solicitudAnticipoViajeService.getSolicitudAnticipoViaje(idSolicitudAnticipoViaje));
					solicitudAnticipoViajeAerolinea.setNombreAerolinea(aerolinea.getNombre());
					solicitudAnticipoViajeAerolinea.setCostoAerolinea(Utilerias.convertStringToBigDecimal(aerolinea.getCosto()));
					solicitudAnticipoViajeAerolinea.setActivo(Etiquetas.UNO_S);
					solicitudAnticipoViajeAerolinea.setCreacionFecha(new Date());
					solicitudAnticipoViajeAerolinea.setCreacionUsuario(user.getIdUsuario());
					
					idSolicitudAnticipoViajeAerolinea = solicitudAnticipoViajeAerolineaService.createSolicitudAnticipoViajeAerolinea(solicitudAnticipoViajeAerolinea);
					if (index == anticipoViaje.getIdSolicitudAerolineaSeleccionado()){
						anticipoViaje.setIdSolicitudAerolineaSeleccionado(idSolicitudAnticipoViajeAerolinea);
						solicitudAnticipoViajeService.updateSolicitudAnticipoViaje(anticipoViaje);
					}
					index++;	
				}
			}
			
			Integer idSolicitudAnticipoViajeConcepto= 0;
			//Guarda en Solicitud anticipo viaje aerolinea
			if (idSolicitudAnticipoViaje != null && idSolicitudAnticipoViaje > Etiquetas.CERO) {
				for(ViajeConceptoDTO concepto : anticipoDTO.getConceptos()){
					if(concepto.isSeleccionado()){
						SolicitudAnticipoViajeConceptos solicitudAnticipoViajeConcepto = new SolicitudAnticipoViajeConceptos();
						solicitudAnticipoViajeConcepto.setSolicitudAnticipoViaje(solicitudAnticipoViajeService.getSolicitudAnticipoViaje(idSolicitudAnticipoViaje));
						solicitudAnticipoViajeConcepto.setViajeConcepto(viajeConceptoService.getViajeConcepto(concepto.getIdViajeConcepto()));
						solicitudAnticipoViajeConcepto.setImporte(Utilerias.convertStringToBigDecimal(concepto.getImporte()));
						
						solicitudAnticipoViajeConcepto.setActivo(Etiquetas.UNO_S);
						solicitudAnticipoViajeConcepto.setCreacionFecha(new Date());
						solicitudAnticipoViajeConcepto.setCreacionUsuario(user.getIdUsuario());
						
						idSolicitudAnticipoViajeConcepto = solicitudAnticipoViajeConceptoService.createSolicitudAnticipoViajeConceptos(solicitudAnticipoViajeConcepto);
					}
				}
			}
			
			//crear sesion para mensaje en la vista
            session.setAttribute("creacion", Etiquetas.TRUE);
		}

		return new ModelAndView("redirect:anticipoViaje?id=" + idSolicitud);
	}

	private SolicitudAnticipoViajeConceptos getViajeConceptoByDTO(List<SolicitudAnticipoViajeConceptos> lstConceptos, Integer idViajeConcepto) {
		for(SolicitudAnticipoViajeConceptos anticipoConcepto : lstConceptos){
			if(anticipoConcepto.getViajeConcepto().getIdViajeConcepto() == idViajeConcepto)
				return anticipoConcepto;
		}
		return null;
	}

	/**
	 * @author miguelr
	 * @param 
	 * @return importe
	 * CALCULA EL IMPORTE DEL CONCEPTO
	 */
	@RequestMapping(value = "/calculaImporteConcepto", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> calculaImporteConcepto(HttpSession session,
			@RequestParam Integer moneda, @RequestParam String fechaSalida, @RequestParam String fechaRegreso, 
			@RequestParam Integer numPersonas, @RequestParam Integer concepto, HttpServletRequest request, HttpServletResponse response) {		
		
		ViajeConcepto vc = viajeConceptoService.getViajeConcepto(concepto);
		int dias = Etiquetas.UNO;
		int personas = Etiquetas.UNO;
		short esCalculado = vc.getEsCalculado();
		BigDecimal importe = new BigDecimal(0);
		
		if (vc != null && esCalculado == Etiquetas.UNO){
			
			if(vc.getCalculoDias() == Etiquetas.UNO){
				dias = calculaDiasEntreFechas(fechaSalida, fechaRegreso);
			}
			
			if(vc.getCalculoPersonas() == Etiquetas.UNO){
				personas = numPersonas;
			}
			
			int formula = dias * personas;
			if(moneda == Etiquetas.ID_PESOS)
				importe = importe.add(vc.getPesosTarifa().multiply(new BigDecimal(formula)));
			else if(moneda == Etiquetas.ID_DOLARES)
				importe = importe.add(vc.getDolaresTarifa().multiply(new BigDecimal(formula)));
		}

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();
		
		result.put("idConcepto", (Character.toLowerCase(vc.getDescripcion().charAt(0)) + vc.getDescripcion().substring(1)).replaceAll("\\s",""));
		result.put("importe", importe.toString());
		result.put("esCalculado", String.valueOf(esCalculado));

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

	private int calculaDiasEntreFechas(String fechaSalida, String fechaRegreso) {
		Date dinicio = null, dfinal = null;
        long milis1, milis2, diff;

        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");

        try {
                // PARSEO STRING A DATE
                dinicio = sdf.parse(fechaSalida);
                dfinal = sdf.parse(fechaRegreso);                    
               
        } catch (ParseException e) {

                System.out.println("Se ha producido un error en el parseo");
        }
       
        //INSTANCIA DEL CALENDARIO GREGORIANO
        Calendar cinicio = Calendar.getInstance();
        Calendar cfinal = Calendar.getInstance();

        //ESTABLECEMOS LA FECHA DEL CALENDARIO CON EL DATE GENERADO ANTERIORMENTE
         cinicio.setTime(dinicio);
         cfinal.setTime(dfinal);


	     milis1 = cinicio.getTimeInMillis();
	
	     milis2 = cfinal.getTimeInMillis();
	
	
	     diff = milis2-milis1;
		
		 return (int) Math.abs ( diff / (24 * 60 * 60 * 1000) );
	}
}
