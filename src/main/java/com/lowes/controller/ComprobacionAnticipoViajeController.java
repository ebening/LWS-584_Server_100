package com.lowes.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.ArchivoDTO;
import com.lowes.dto.ComprobacionAnticipoViajeByIdDTO;
import com.lowes.dto.ComprobacionAnticipoViajeDTO;
import com.lowes.dto.ComprobacionAnticipoViajeDesgloseDTO;
import com.lowes.dto.ComprobacionAnticipoViajeDesgloseDetalleDTO;
import com.lowes.dto.FacturaDesgloseDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.ComprobacionAnticipo;
import com.lowes.entity.ComprobacionDeposito;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Factura;
import com.lowes.entity.FacturaArchivo;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.FacturaGastoViaje;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAnticipoViaje;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.entity.TipoDocumento;
import com.lowes.entity.TipoFactura;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.entity.ViajeConcepto;
import com.lowes.entity.ViajeDestino;
import com.lowes.entity.ViajeMotivo;
import com.lowes.entity.ViajeTipoAlimentos;
import com.lowes.service.CompaniaService;
import com.lowes.service.ComprobacionAnticipoService;
import com.lowes.service.ComprobacionDepositoService;
import com.lowes.service.CuentaContableService;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaGastoViajeService;
import com.lowes.service.FacturaService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ProveedorLibreService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudAnticipoViajeService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.service.TipoDocumentoService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.service.ViajeConceptoService;
import com.lowes.service.ViajeDestinoService;
import com.lowes.service.ViajeMotivoService;
import com.lowes.service.ViajeTipoAlimentosService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Scope("session")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ComprobacionAnticipoViajeController {

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
	private ViajeTipoAlimentosService viajeTipoAlimentosService;
	@Autowired
	private FormaPagoService formaPagoService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private UsuarioConfSolicitanteService uConfsolicitanteService;
	@Autowired
	private CuentaContableService cuentaContableService;
	@Autowired
	private ViajeDestinoService viajeDestinoService;
	@Autowired
	private ViajeMotivoService viajeMotivoService;
	@Autowired
	private ViajeConceptoService viajeConceptoService;
	@Autowired
	private SolicitudAnticipoViajeService solicitudAnticipoViajeService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private FacturaGastoViajeService facturaGastoViajeService;
	@Autowired
	private FacturaDesgloseService facturaDesgloseService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private ProveedorLibreService proveedorLibreService;
	@Autowired
	private ComprobacionAnticipoService comprobacionAnticipoService;
	@Autowired
	private ComprobacionDepositoService comprobacionDepositoService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private FacturaArchivoService facturaArchivoService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	
//	@Autowired
//	private ViajeTipoAlimentosService viajeTipoAlimentosService;
	
	private static final Logger logger = Logger.getLogger(ComprobacionAnticipoViajeController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	private UtilController util = new UtilController();
	
	private Integer idUsuario;
	//Lista de sesion para guardar las facturas capturadas
	private ComprobacionAnticipoViajeDTO comprobacionAnticipoViajeDTO;
	//Lista de sesion para desgloses a eliminar
	private List<ComprobacionAnticipoViajeDesgloseDTO> lstDesgloseDelete;
	//Lista de sesion para facturas a eliminar
	private List<ComprobacionAnticipoViajeDesgloseDetalleDTO> lstDesgloseDetalleDelete;
	//Desglose backup
	private List<ComprobacionAnticipoViajeDesgloseDetalleDTO> desgloseAnterior;
	//guarda los archivos de cada factura temporalmente
	private List<ArchivoDTO> currentFiles;

	// Comprobar gastos de viaje ---------------------------------------------------------------------

	@RequestMapping("/comprobacionAnticipoViaje")
	private ModelAndView comprobacionAnticipoViaje(HttpSession session, Integer ida, Integer id) {
		comprobacionAnticipoViajeDTO = new ComprobacionAnticipoViajeDTO();
		this.idUsuario = usuarioService.getUsuarioSesion().getIdUsuario();
		List<SolicitudArchivo> solicitudArchivoList = new ArrayList<>();

		//declaraciones de objetos
		lstDesgloseDetalleDelete = new ArrayList<>();
		lstDesgloseDelete = new ArrayList<>();
		HashMap<String, Object> modelo = new HashMap<>();
		comprobacionAnticipoViajeDTO.setIdTipoSolicitud(Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE);
		
		List<ViajeConcepto> viajesConceptoList = viajeConceptoService.getAllViajeConcepto();
		
		Solicitud solicitud = new Solicitud();
		Solicitud comprobacion = new Solicitud();
		
		SolicitudAnticipoViaje solicitudAnticipoViaje = new SolicitudAnticipoViaje();
		Integer idEstadoSolicitud = 0;
		Integer idSolicitud = 0;
		
		Usuario solicitante = null;
		
		if(ida != null && ida > Etiquetas.CERO || id != null && id > Etiquetas.CERO){
			if (ida != null && ida > Etiquetas.CERO) {
				solicitud = solicitudService.getSolicitud(ida);
				idSolicitud = solicitud.getIdSolicitud();
				solicitante = solicitud.getUsuarioByIdUsuarioSolicita();
				if (solicitud != null && solicitud.getIdSolicitud() > Etiquetas.CERO) {
					comprobacionAnticipoViajeDTO.setIdUsuarioSolicita(solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario());
					comprobacionAnticipoViajeDTO.setIdSolicitudAnticipoSession(solicitud.getIdSolicitud());
					comprobacionAnticipoViajeDTO.setCompania(solicitud.getCompania());
					comprobacionAnticipoViajeDTO.setLocacion(solicitud.getLocacion());
					comprobacionAnticipoViajeDTO.setMoneda(solicitud.getMoneda());
					comprobacionAnticipoViajeDTO.setFormaPago(solicitud.getFormaPago());
					comprobacionAnticipoViajeDTO.setImporte(solicitud.getMontoTotal());
					comprobacionAnticipoViajeDTO.setConAnticipo(true);
					comprobacionAnticipoViajeDTO.setPreguntaExtranjero1(false);
					comprobacionAnticipoViajeDTO.setPreguntaExtranjero2(false);
					
					// tomar informacion del anticipo por el id de la solicitud
					solicitudAnticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(solicitud.getIdSolicitud());
					if (solicitudAnticipoViaje != null) {
						comprobacionAnticipoViajeDTO.setViajeDestino(solicitudAnticipoViaje.getViajeDestino());
						comprobacionAnticipoViajeDTO.setViajeMotivo(solicitudAnticipoViaje.getViajeMotivo());
						//Set si Otro Viaje Destino
						ViajeDestino vd = viajeDestinoService.getViajeDestino(solicitudAnticipoViaje.getViajeDestino().getIdViajeDestino());
						if(vd.getEsOtro() != null){
							if(vd.getEsOtro() == 1)
								comprobacionAnticipoViajeDTO.setOtroDestino(solicitudAnticipoViaje.getDescripcionOtroDestino());
						}
						//Set si Otro Viaje Motivo
						ViajeMotivo vm = viajeMotivoService.getViajeMotivo(solicitudAnticipoViaje.getViajeMotivo().getIdViajeMotivo());
						if(vm.getEsOtro() != null){
							if(vm.getEsOtro() == 1)
								comprobacionAnticipoViajeDTO.setOtroMotivo(solicitudAnticipoViaje.getDescripcionOtroMotivo());
						}						
						comprobacionAnticipoViajeDTO.setFechaRegreso(Utilerias.convertDateFormat(solicitudAnticipoViaje.getFinFecha()));
						comprobacionAnticipoViajeDTO.setFechaInicio(Utilerias.convertDateFormat(solicitudAnticipoViaje.getInicioFecha()));
						comprobacionAnticipoViajeDTO.setNumeroPersonas(solicitudAnticipoViaje.getNumPersonas());
					}
				}
				
			}
			else if(id != null && id > Etiquetas.CERO){
				comprobacion = solicitudService.getSolicitud(id);
				idSolicitud = comprobacion.getIdSolicitud();
				solicitante = comprobacion.getUsuarioByIdUsuarioSolicita();
				comprobacionAnticipoViajeDTO.setIdUsuarioSolicita(solicitante.getIdUsuario());
				comprobacionAnticipoViajeDTO.setIdSolicitudComprobacionSession(comprobacion.getIdSolicitud());
				comprobacionAnticipoViajeDTO.setCompania(comprobacion.getCompania());
				comprobacionAnticipoViajeDTO.setLocacion(comprobacion.getLocacion());
				comprobacionAnticipoViajeDTO.setMoneda(comprobacion.getMoneda());
				comprobacionAnticipoViajeDTO.setFormaPago(comprobacion.getFormaPago());
				comprobacionAnticipoViajeDTO.setImporte(comprobacion.getMontoTotal());
				comprobacionAnticipoViajeDTO.setResultadoViaje(comprobacion.getConceptoGasto());
				
				// Enviar archivos anexados por solicitud:
					solicitudArchivoList = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(id); 
				
				if (comprobacion != null) {
					//Carga relacion anticipo/comprobacion
					ComprobacionAnticipo relacionCompAnt = comprobacionAnticipoService.getAnticiposByComprobacion(id).get(0);
					//identifica si tiene anticipo, o se generó sin anticipo (si el id de comprobacion es igual al del anticipo, entonces entro sin anticipo)
					if(relacionCompAnt.getSolicitudByIdSolicitudAnticipo().getIdSolicitud() == relacionCompAnt.getSolicitudByIdSolicitudComprobacion().getIdSolicitud())
						comprobacionAnticipoViajeDTO.setConAnticipo(false);
					//Si son diferentes si tiene solicitud de anticipo
					else{
						comprobacionAnticipoViajeDTO.setConAnticipo(true);
						comprobacionAnticipoViajeDTO.setImporte(relacionCompAnt.getSolicitudByIdSolicitudAnticipo().getMontoTotal());
					}
					
					solicitudAnticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(relacionCompAnt.getSolicitudByIdSolicitudAnticipo().getIdSolicitud());
					if (solicitudAnticipoViaje != null) {
						if(solicitudAnticipoViaje.getPreguntaExtrangero1() != null)
							comprobacionAnticipoViajeDTO.setPreguntaExtranjero1(solicitudAnticipoViaje.getPreguntaExtrangero1() == 1 ? true : false);
						else
							comprobacionAnticipoViajeDTO.setPreguntaExtranjero1(false);
						if(solicitudAnticipoViaje.getPreguntaExtrangero2() != null)
							comprobacionAnticipoViajeDTO.setPreguntaExtranjero2(solicitudAnticipoViaje.getPreguntaExtrangero2() == 1 ? true : false);
						else
							comprobacionAnticipoViajeDTO.setPreguntaExtranjero2(false);
						
						comprobacionAnticipoViajeDTO.setViajeDestino(solicitudAnticipoViaje.getViajeDestino());
						comprobacionAnticipoViajeDTO.setViajeMotivo(solicitudAnticipoViaje.getViajeMotivo());
						//Set si Otro Viaje Destino
						ViajeDestino vd = viajeDestinoService.getViajeDestino(solicitudAnticipoViaje.getViajeDestino().getIdViajeDestino());
						if(vd.getEsOtro() != null){
							if(vd.getEsOtro() == 1)
								comprobacionAnticipoViajeDTO.setOtroDestino(solicitudAnticipoViaje.getDescripcionOtroDestino());
						}
						//Set si Otro Viaje Motivo
						ViajeMotivo vm = viajeMotivoService.getViajeMotivo(solicitudAnticipoViaje.getViajeMotivo().getIdViajeMotivo());
						if(vm.getEsOtro() != null){
							if(vm.getEsOtro() == 1)
								comprobacionAnticipoViajeDTO.setOtroMotivo(solicitudAnticipoViaje.getDescripcionOtroMotivo());
						}
						comprobacionAnticipoViajeDTO.setFechaRegreso(Utilerias.convertDateFormat(solicitudAnticipoViaje.getFinFecha()));
						comprobacionAnticipoViajeDTO.setFechaInicio(Utilerias.convertDateFormat(solicitudAnticipoViaje.getInicioFecha()));
						comprobacionAnticipoViajeDTO.setNumeroPersonas(solicitudAnticipoViaje.getNumPersonas());
					}
					
					for(Factura factDesg : facturaService.getAllFacturaBySolicitud(id)){
						FacturaGastoViaje factDesgViaje = facturaGastoViajeService.getFacturaGastoViajeByIdFactura(factDesg.getIdFactura());
						
						if (factDesgViaje != null) {
							
							ComprobacionAnticipoViajeDesgloseDTO compAntViajDesg = new ComprobacionAnticipoViajeDesgloseDTO();
							compAntViajDesg.setIdFactura(factDesg.getIdFactura());
							compAntViajDesg.setIdFacturaGV(factDesgViaje.getIdFacturaGastoViaje());
							compAntViajDesg.setFecha_factura(Utilerias.convertDateFormat(factDesg.getFechaFactura()));
							compAntViajDesg.setFecha_gasto(Utilerias.convertDateFormat(factDesgViaje.getFechaGasto()));
							compAntViajDesg.setComercio(factDesgViaje.getComercio());
							compAntViajDesg.setCiudadTexto(factDesgViaje.getCiudad());
							compAntViajDesg.setIdMoneda(factDesg.getMoneda().getIdMoneda());
							compAntViajDesg.setConcepto(factDesg.getConceptoGasto());
							compAntViajDesg.setSubTotal(factDesg.getSubtotal());
							compAntViajDesg.setIva(factDesg.getIva());
							compAntViajDesg.setIeps(factDesg.getIeps());
							compAntViajDesg.setOtrosImpuestos(factDesgViaje.getOtrosImpuestos());
							compAntViajDesg.setTua(factDesgViaje.getTua());
							compAntViajDesg.setIsh(factDesgViaje.getIsh());
							compAntViajDesg.setYri(factDesgViaje.getYri());
							compAntViajDesg.setSumaOtrosImpuestos(factDesgViaje.getSumaOtrosImpuestos());
							compAntViajDesg.setTotal(factDesg.getTotal());
							compAntViajDesg.setNumeroPersonas(factDesgViaje.getNumeroPersonas());
							compAntViajDesg.setViajeConcepto(factDesgViaje.getViajeConcepto());
							compAntViajDesg.setViajeTipoAlimentos(factDesgViaje.getViajeTipoAlimentos());
							compAntViajDesg.setIdLocacion(factDesg.getLocacion().getIdLocacion());
							compAntViajDesg.setCuentaContable(factDesg.getCuentaContable());
							compAntViajDesg.setIdCuentaContable(factDesg.getCuentaContable().getIdCuentaContable());
							compAntViajDesg.setConCompFiscal(
									(factDesg.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_FACTURA)
											? true : false);
							compAntViajDesg.setSegundaAprobacion(
									(factDesgViaje.getSegundaAprobacion() == Etiquetas.UNO_S) ? true : false);
							if (factDesg.getProveedor() != null)
								compAntViajDesg.setProveedor(factDesg.getProveedor().getIdProveedor());
							else if (factDesg.getProveedorLibre() != null)
								compAntViajDesg.setProveedorLibre(factDesg.getProveedorLibre().getIdProveedorLibre());
							compAntViajDesg.setFolio(factDesg.getFactura());
							compAntViajDesg.setSerie(factDesg.getSerieFactura());
							compAntViajDesg.setFolioFiscal(factDesg.getFolioFiscal());
							List<FacturaDesglose> fact = factDesg.getFacturaDesgloses();
							if (factDesgViaje.getDesglosar() == Etiquetas.UNO_S) {
								compAntViajDesg.setDesglosar(true);
								compAntViajDesg.setLstDesgloseDetalle(preparaDesgloseDetalle(fact));
							} else
								compAntViajDesg.setDesglosar(false);
							comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().add(compAntViajDesg);

						}
						
					}
				}
				idEstadoSolicitud = comprobacion.getEstadoSolicitud().getIdEstadoSolicitud();
			}
		}
		//Se inicia captura sin anticipo
		else{
			comprobacionAnticipoViajeDTO.setConAnticipo(false);
			comprobacionAnticipoViajeDTO.setPreguntaExtranjero1(false);
			comprobacionAnticipoViajeDTO.setPreguntaExtranjero2(false);
			idSolicitud = Etiquetas.CERO;
		}
		
		String nombreSolicitante = usuarioService.getUsuarioSesion().getNombreCompletoUsuario();
		Usuario usuarioSession = usuarioService.getUsuarioSesion();

		// Listas
		List<Compania> companiaList = companiaService.getAllCompania();
		List<CuentaContable> cuentaContableList = cuentaContableService.getAllCuentaContable();
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(usuarioSession.getIdUsuario());
		List<ComprobacionDeposito> comprobacionDeposito =  comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(idSolicitud);
		
		// Obtener combos filtrador por configuracion de solicitante.
		List<CuentaContable> ccPermitidas = UtilController.getCuentasContablesPermitidasPorUsuario(uconfigSol, cuentaContableService.getAllCuentaContable());
		List<Locacion> locacionesPermitidas = new ArrayList<>();
		
		/*carga de locaciones por anticipo y confirmacion de anticipo de gastos de viaje.*/
		//List<Locacion> locacionesPermitidasAnticipo = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE,locacionService.getAllLocaciones(), usuarioSession.getIdUsuario());
		List<Locacion> locacionesPermitidasComprobacion = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, comprobacionAnticipoViajeDTO.getIdTipoSolicitud() ,locacionService.getAllLocaciones(), usuarioSession.getIdUsuario());
		
		//locacionesPermitidas.addAll(locacionesPermitidasAnticipo);
		locacionesPermitidas.addAll(locacionesPermitidasComprobacion);
		
		List<FormaPago> formaPagoList = formaPagoService.getAllFormaPago();
		List<Moneda> monedaList = monedaService.getAllMoneda();
		List<ViajeTipoAlimentos> tipoAlimentosList = viajeTipoAlimentosService.getAllViajeTipoAlimentos();
		List<ViajeDestino> viajeDestinoList = viajeDestinoService.getAllViajeDestino();
		List<ViajeMotivo> viajeMotivoList = viajeMotivoService.getAllViajeMotivo();

		//Filas
		List<String> filas = new ArrayList<String>();
		
		if (comprobacionDeposito.size() > 0) {
			ComprobacionDeposito lastComprobacionDeposito = comprobacionDeposito.get(comprobacionDeposito.size() - 1);
			modelo.put("deposito", lastComprobacionDeposito);
			
			//si hay depositos solo deberia traer uno y restarlo al saldo
			Integer idComprobacionDeposito = 0;
			BigDecimal montoDeposito = BigDecimal.ZERO;
			String fechaDeposito = Utilerias.convertDateFormat(new Date());
			List<ComprobacionDeposito> depositos = new ArrayList();
			
			if(id != null && id > 0)
			depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(id);


			
			
			 for (ComprobacionDeposito comprobacionDeposito1 : depositos) {
					idComprobacionDeposito = comprobacionDeposito1.getIdComprobacionDeposito();
					montoDeposito = comprobacionDeposito1.getMontoDeposito();
					fechaDeposito = Utilerias.convertDateFormat(comprobacionDeposito1.getFechaDeposito());
					
					//comprobacionAnticipoViajeDTO.setSaldo(saldo.subtract(montoDeposito).toString());
					
					comprobacionAnticipoViajeDTO.setIdComprobacionDeposito(idComprobacionDeposito);
					comprobacionAnticipoViajeDTO.setFecha_deposito(fechaDeposito);
					comprobacionAnticipoViajeDTO.setImporteDeposito(montoDeposito.toString());
					
				}
		}
		
		
		
		
		//objetos para la vista
		modelo.put("companiaList", companiaList);
		modelo.put("motivos", viajeMotivoList);
		modelo.put("nombreSolicitante", nombreSolicitante);
		modelo.put("destinos", viajeDestinoList);
		modelo.put("viajesConceptoList", viajesConceptoList);
		modelo.put("ccPermitidas", ccPermitidas);
		modelo.put("cuentaContableList", cuentaContableList);
		modelo.put("comprobacionAnticipoViajeDTO", comprobacionAnticipoViajeDTO);
		modelo.put("monedaList", monedaList);
		modelo.put("tipoAlimentosList", tipoAlimentosList);
		modelo.put("formaPagoList", formaPagoList);
		modelo.put("idEstadoSolicitud", idEstadoSolicitud);
		modelo.put("locacionesPermitidas", locacionesPermitidas);
		modelo.put("filas", filas);
		modelo.put("idSolicitud", idSolicitud);
		modelo.put("tipoSolicitud",Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE);
		modelo.put("solicitudArchivoList", solicitudArchivoList);

		return new ModelAndView("comprobacionAnticipoViaje",modelo);
	}
	
	private List<ComprobacionAnticipoViajeDesgloseDetalleDTO> preparaDesgloseDetalle(List<FacturaDesglose> fact) {
		List<ComprobacionAnticipoViajeDesgloseDetalleDTO> listaDesglose = new ArrayList<>();
		for(FacturaDesglose fd : fact){
			ComprobacionAnticipoViajeDesgloseDetalleDTO desglose = new ComprobacionAnticipoViajeDesgloseDetalleDTO();
			desglose.setIdFacturaDesglose(fd.getIdFacturaDesglose());
			desglose.setCuentaContable(fd.getCuentaContable());
			desglose.setLocacion(fd.getLocacion());
			desglose.setNombre(fd.getNombre());
			desglose.setOtrosImpuestos(fd.getOtrosImpuestos());
			desglose.setSubTotal(fd.getSubtotal());
			desglose.setViajeConcepto(fd.getViajeConcepto());
			desglose.setViajeTipoAlimentos(fd.getViajeTipoAlimentos());
			listaDesglose.add(desglose);
		}
		return listaDesglose;
	}
	
	private String generarFila(Integer nlinea, String conceptos, String locaciones, String tipoAlimentos, Integer ran ) {

		StringBuilder html = new StringBuilder();
		String columnas = "";

		String linea = "<td class=\"centro linea-linea\">"+nlinea+"</td>";
		String eliminar = "<td class=\"linea-eliminar\">"+
				"<button title=\"Eliminar\" style=\"height: 22px;\" type=\"button\" class=\"btn btn-xs btn-danger\">"+
				"<span class=\"glyphicon glyphicon-trash\"></span>"+
				"</button>"+
				"</td>";
		String facturaComprobante = "<td class=\"linea-factura-comprobante\">"+
				"<div class=\"center\" style=\"min-width:85px;\"><a href=\"\" data-toggle=\"modal\" data-target=\"#modal-factura\">Factura</a>"+
				" / <a href=\"\" class=\"modalita\" data-toggle=\"modal\" data-target=\"#modal-comprobante\">Comprobante</a></div>"+
				"</td>";
		String fechaFacturaComprobante = "<td class=\"linea-fecha-factura\"></td>";		
		String fechaGasto = "<td class=\"linea-fecha-gasto\"><div class=\"date-grid-container form-group-parse\"><input type=\"text\" class=\"form-control fecha_gasto_linea\" class=\"form-control\"/></div> </td>";		
		String comercio = "<td class=\"linea-comercio\"></td>";		
		String concepto = "<td class=\"linea-concepto\">"+
				"<select id=\"comprobacionAntDesglose0.viajeConcepto.idViajeConcepto\" name=\"comprobacionAntDesglose[0].viajeConcepto.idViajeConcepto\" style=\"min-width:120px;\" class=\"form-control\">"+
				"<option title=\""+etiqueta.SELECCIONE+"\" value=\"-1\" >"+etiqueta.SELECCIONE+"</option>"+
				conceptos+
				"</select>"+
				"</td>";		
		String ciudad = "<td class=\"linea-ciudad\"><input path=\"\" style=\"min-width:120px;\" class=\"form-control centro\" /></td>";
		String subtotal = "<td class=\"moneyFormat linea-subtotal\">0.00</td>";
		String iva = "<td class=\"moneyFormat linea-iva\"></td>";
		String ips = "<td class=\"moneyFormat linea-ips\"></td>";
		String otrosImpuestos = "<td class=\"moneyFormat linea-otros-impuestos\">0.00</td>";
		String total = "<td class=\"moneyFormat linea-total\" style=\"font-weight:bold; min-width: 66px;\">0.00</td>";		
		String locacion = "<td class=\"linea-locacion\">"+
				"<select style=\"min-width:100px;\" class=\"form-control\">"+
				"<option title=\""+etiqueta.SELECCIONE+"\" value=\"-1\" >"+etiqueta.SELECCIONE+"</option>"+
				locaciones+
				"</select>"+
				"</td>";
		String cuentaContable = "<td class=\"linea-cuenta-contable\">"+
				"<select style=\"min-width:100px;\" class=\"form-control\">"+
				"</select>"+
				"</td>";
		String bld ="<td class=\"linea-bld\">"+
				"<select style=\"min-width:100px;\" class=\"form-control\">"+
				"<option title=\""+etiqueta.SELECCIONE+"\" value=\"-1\" >"+etiqueta.SELECCIONE+"</option>"+
				tipoAlimentos+
				"</select>"+
				"</td>";		
		String segundaAutorizacion = "<td class=\"centro linea-segunda-autorizacion\">"+
				"<input type=\"checkbox\" value=\"\" disabled>"+
				"</td>";		
		String desglosar = "<td class=\"centro linea-desglosar\">"+
				"<input type=\"checkbox\" value=\"\" disabled>"+
				"</td>";		
		String numeroPersonas = "<td class=\"linea-numero-personas\"><input path=\"\" class=\"form-control centro\" value=\"1\"/></td>"; 
		String irDesglose = "<td class=\"centro linea-ir-desglose\">"+
				"<button type=\"button\" style=\"height: 22px; margin-right: 10%;\" class=\"detalleDesglose\" disabled><span>Ir</span></button>"+
				"<span class=\"glyphicon glyphicon-ok-circle palomita desglosado\"></span>"+
				"</td>";

		locacion = "<td class=\"linea-locacion\">"+ 
				"<select id=\"comprobacionAntDesglose"+ran+".locacion.idLocacion\" name=\"comprobacionAntDesglose"+ran+".locacion.idLocacion\" style=\"min-width:100px;\" class=\"form-control\" onchange=\"actualizarCuentas(this.id)\">"+
				"<option title=\""+etiqueta.SELECCIONE+"\" value=\"-1\" >"+etiqueta.SELECCIONE+"</option>"+
				locaciones+
				"</select>"+
				"</td>";
		cuentaContable =  "<td class=\"linea-cuenta-contable\">"+
				"<select id=\"comprobacionAntDesglose"+ran+".cuentaContable.idCuentaContable\" name=\"comprobacionAntDesglose"+ran+".cuentaContable.idCuentaContable\" style=\"min-width:100px;\" class=\"form-control ccontable\">"+
				"<option></option>"+
				"</select>"+
				"</td>";
		
		columnas = linea+eliminar+facturaComprobante+fechaFacturaComprobante+fechaGasto+comercio+concepto+ciudad+subtotal+iva+ips;
		columnas += otrosImpuestos+total+locacion+cuentaContable+bld+segundaAutorizacion+desglosar+numeroPersonas+irDesglose;

		html.append("<tr class=\"gradeX table-row t-row\">"+columnas+"</tr>");
		return html.toString();
	}

	@RequestMapping(value = "/agregaLineaGastosViaje", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> addRow(HttpSession session, @RequestParam Integer numrows,@RequestParam Integer tipoSolicitud,
			HttpServletRequest request, HttpServletResponse response, @RequestParam String datos) {
		
		if(!comprobacionAnticipoViajeDTO.isConAnticipo()){
			updateAnticipoViajeDTO(datos);
		}

		Usuario usuarioSession = usuarioService.getUsuarioSesion();

		// Listas
		List<ViajeConcepto> viajesConceptoList = viajeConceptoService.getAllViajeConcepto();
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(usuarioSession.getIdUsuario());
	    
		List<Locacion> locacionesPermitidas = new ArrayList<>();
		/*carga de locaciones por anticipo y confirmacion de anticipo de gastos de viaje.*/
		//List<Locacion> locacionesPermitidasAnticipo = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE,locacionService.getAllLocaciones(), usuarioSession.getIdUsuario());
		List<Locacion> locacionesPermitidasComprobacion = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, comprobacionAnticipoViajeDTO.getIdTipoSolicitud() ,locacionService.getAllLocaciones(), usuarioSession.getIdUsuario());
		
		//locacionesPermitidas.addAll(locacionesPermitidasAnticipo);
		locacionesPermitidas.addAll(locacionesPermitidasComprobacion);
		
		List<ViajeTipoAlimentos> viajeTipoAlimentosList = viajeTipoAlimentosService.getAllViajeTipoAlimentos();

		/* Convertir listas a options ------------------------------------------------  */
		String option = "";
		StringBuilder options = new StringBuilder();
         
		// Lista Locaciones
		for (Locacion locacion : locacionesPermitidas) {
			int idLocacion = locacion.getIdLocacion();
			String descripcion = locacion.getNumeroDescripcionLocacion();
			option = "<option value=\""+idLocacion+"\">"+descripcion+"</option>";
			options.append(option);
		}
		String locaciones = options.toString(); 

		// Lista conceptos
		options.setLength(0);
		for (ViajeConcepto concepto : viajesConceptoList) {
			int idConcepto = concepto.getIdViajeConcepto();
			String descripcion = concepto.getDescripcion();
			option = "<option value=\""+idConcepto+"\">"+descripcion+"</option>";
			options.append(option);
		}
		String conceptos = options.toString(); 

		// Lista viajeAlimentos
		options.setLength(0);
		for (ViajeTipoAlimentos tipoAlimento : viajeTipoAlimentosList) {
			int idTipoAlimento = tipoAlimento.getIdViajeTipoAlimentos();
			String descripcion = tipoAlimento.getDescripcion();
			option = "<option value=\""+idTipoAlimento+"\">"+descripcion+"</option>";
			options.append(option);
		}
		
		String tipoAlimentos = options.toString(); 
		String json = null;

		//para la serializacion de los inputs de la fila.
		Random r = new Random();
		Integer randomNumber = r.nextInt(150) + 1;
		// para la columna de lineas
		Integer numLinea = numrows + 1;
		//html generado 
		String row = generarFila(numLinea, conceptos, locaciones, tipoAlimentos, randomNumber);

		if(row.isEmpty() == false){
			ComprobacionAnticipoViajeDesgloseDTO obj = new ComprobacionAnticipoViajeDesgloseDTO();
			comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().add(obj);
		}

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

	// Agrega el objeto línea -----------------------------------------------------------------------

	@RequestMapping(value = "/enviaLinea", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> enviaLinea (HttpSession session, @RequestBody String datos, @RequestParam Integer tipoSolicitud,
			@RequestParam Integer row, @RequestParam Integer conComp, HttpServletRequest request, HttpServletResponse response) {

		ObjectMapper mapper = new ObjectMapper();
		ComprobacionAnticipoViajeDesgloseDTO obj = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(row);
		
		try {
			obj = mapper.readValue(datos, ComprobacionAnticipoViajeDesgloseDTO.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Locacion locacion = new Locacion(obj.getIdLocacion());
		obj.setLocacion(locacion);

		CuentaContable cuentaContable = new CuentaContable();
		obj.setCuentaContable(cuentaContable);

		ViajeConcepto viajeConcepto = new ViajeConcepto();
		obj.setViajeConcepto(viajeConcepto);
		obj.setArchivos(currentFiles);
		obj.setConCompFiscal((conComp == 1 ? true : false));
		currentFiles = null;
		comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().set(row, obj);
		String json = null;

		try {
			json = mapper.writeValueAsString("{data:'"+comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().size()+"'}");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	// Fin Agrega el objeto línea -------------------------------------------------------------------
	
	@RequestMapping(value = "/guardaArchivosComprobacion", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	private @ResponseBody ResponseEntity<String> guardaArchivosComprobacion(MultipartHttpServletRequest request, HttpServletResponse response) {
		currentFiles = new ArrayList<>();

		if (request != null) {
			/*
			 * extract files
			 */
			final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			final MultiValueMap<String, MultipartFile> files = multiRequest.getMultiFileMap();            
			for(List<MultipartFile> lmpf : files.values()){
				for(MultipartFile f : lmpf){
					try {
						currentFiles.add(new ArchivoDTO(f.getOriginalFilename(),f.getBytes()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		}       
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(String.valueOf(currentFiles.size()), responseHeaders, HttpStatus.OK);
	}

	// Elimina el objeto línea -------------------------------------------------------------------
	@RequestMapping(value = "/eliminaLineaGastosViaje", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> eliminaLineaGastosViaje (HttpSession session, @RequestParam Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		
		Integer pre = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().size();
		lstDesgloseDelete.add(comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(id));
		comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().remove(comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(id));
		Integer total = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().size(); 
		String json = "{\"total\":"+total+",\"pre\":"+pre+"}";

		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	
	// Actualiza línea ----------------------------------------------------------------------------
	@RequestMapping(value = "/actualizaLineaGastosViaje", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> actualizaLineaGastosViaje(
			HttpSession session,
			@RequestBody String datos,
			@RequestParam Integer row,
			@RequestParam Integer tipoSolicitud,
			HttpServletRequest request,
			HttpServletResponse response)
	{
		
		ObjectMapper mapper = new ObjectMapper();
		ComprobacionAnticipoViajeDesgloseDTO obj = new ComprobacionAnticipoViajeDesgloseDTO();
		try {
			obj = mapper.readValue(datos, ComprobacionAnticipoViajeDesgloseDTO.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Datos del objeto y validaciones
		String comercio = obj.getComercio();
		String ciudadTexto =  obj.getCiudadTexto();
		Integer idViajeConcepto = obj.getIdViajeConcepto();
		Integer idViajeTipoAlimento = obj.getIdViajeTipoAlimento();
		Integer idCuentaContable = obj.getIdCuentaContable();
		boolean desglosar = obj.isDesglosar();
		String fechaGasto = obj.getFecha_gasto();
		String fechaFactura = obj.getFecha_factura();
		String serie = obj.getSerie();
		String folio = obj.getFolio();
		String folioFiscal = obj.getFolioFiscal();
		BigDecimal ieps = obj.getIeps();
		BigDecimal iva = obj.getIva();
		Integer idLocacion = obj.getIdLocacion();
		Integer idMoneda = obj.getIdMoneda();
		Integer numeroPersonas = obj.getNumeroPersonas();
		BigDecimal otrosImpuestos = obj.getOtrosImpuestos();
		boolean segundaAprobacion = obj.isSegundaAprobacion();
		BigDecimal subTotal = obj.getSubTotal();
		BigDecimal total = obj.getTotal();
		
		List<ComprobacionAnticipoViajeDesgloseDTO> listadoLineas = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose();
		
		// Guardar datos en el objeto
		listadoLineas.get(row).setComercio(comercio);
		listadoLineas.get(row).setCiudadTexto(ciudadTexto);
		listadoLineas.get(row).setIdViajeConcepto(idViajeConcepto);
		if(idViajeConcepto != null && idViajeConcepto > 0)
			listadoLineas.get(row).setViajeConcepto(viajeConceptoService.getViajeConcepto(idViajeConcepto));
		listadoLineas.get(row).setIdViajeTipoAlimento(idViajeTipoAlimento);
		if(idViajeTipoAlimento != null && idViajeTipoAlimento > 0)
			listadoLineas.get(row).setViajeTipoAlimentos(viajeTipoAlimentosService.getViajeTipoAlimentos(idViajeTipoAlimento));
		if(idCuentaContable != null)
			listadoLineas.get(row).setIdCuentaContable(idCuentaContable);
		listadoLineas.get(row).setDesglosar(desglosar);
		listadoLineas.get(row).setFecha_gasto(fechaGasto);
		listadoLineas.get(row).setFecha_factura(fechaFactura);
		if(serie != null)
			listadoLineas.get(row).setSerie(serie);
		if(folio != null)
			listadoLineas.get(row).setFolio(folio);
		if(folioFiscal != null)
			listadoLineas.get(row).setFolioFiscal(folioFiscal);
		listadoLineas.get(row).setIva(iva);
		listadoLineas.get(row).setIeps(ieps);
		listadoLineas.get(row).setIdLocacion(idLocacion);
		if(idMoneda != null)
			listadoLineas.get(row).setIdMoneda(idMoneda);
		listadoLineas.get(row).setNumeroPersonas(numeroPersonas);
		if(otrosImpuestos != null)
		listadoLineas.get(row).setOtrosImpuestos(otrosImpuestos);
		listadoLineas.get(row).setSegundaAprobacion(segundaAprobacion);
		listadoLineas.get(row).setSubTotal(subTotal);
		listadoLineas.get(row).setTotal(total);
		
		esSegundaAprobacion(comprobacionAnticipoViajeDTO);
		
		String json = "{ \"comercio\":\""+listadoLineas.get(row).getComercio()+"\""+
				",\"ciudadTexto\":\""+listadoLineas.get(row).getCiudadTexto()+"\""+
				",\"idViajeConcepto\":\""+listadoLineas.get(row).getIdViajeConcepto()+"\""+
				",\"idViajeTipoAlimento\":\""+listadoLineas.get(row).getIdViajeTipoAlimento()+"\""+
				",\"idCuentaContable\":\""+listadoLineas.get(row).getIdCuentaContable()+"\""+
				",\"desglosar\":\""+listadoLineas.get(row).isDesglosar()+"\""+
				",\"fecha_gasto\":\""+listadoLineas.get(row).getFecha_gasto()+"\""+
				",\"fecha_factura\":\""+listadoLineas.get(row).getFecha_factura()+"\""+
				",\"folio\":\""+listadoLineas.get(row).getFolio()+"\""+
				",\"folioFiscal\":\""+listadoLineas.get(row).getFolioFiscal()+"\""+
				",\"ieps\":\""+listadoLineas.get(row).getIeps()+"\""+
				",\"iva\":\""+listadoLineas.get(row).getIva()+"\""+
				",\"idLocacion\":\""+listadoLineas.get(row).getIdLocacion()+"\""+
				",\"idMoneda\":\""+listadoLineas.get(row).getIdMoneda()+"\""+
				",\"numeroPersonas\":\""+listadoLineas.get(row).getNumeroPersonas()+"\""+
				",\"otrosImpuestos\":\""+listadoLineas.get(row).getOtrosImpuestos()+"\""+
				",\"segundaAprobacion\":\""+listadoLineas.get(row).isSegundaAprobacion()+"\""+
				",\"subTotal\":\""+listadoLineas.get(row).getSubTotal()+"\""+
				",\"total\":\""+listadoLineas.get(row).getTotal()+"\"}";

		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		
	}
	
	//---------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/saveComprobacionAnticipoViaje", method = RequestMethod.POST)
	private ModelAndView saveComprobacionAnticipoViaje(
			@ModelAttribute("comprobacionAnticipoViajeDTO") ComprobacionAnticipoViajeDTO compAnticipoViajeDTO,
			HttpSession session) {
		
		Integer idComprobacionViaje = null;
		Integer idSolicitud = null;
		
		//Se carga la solicitud de sesion, si es anticipo (ida), si es comprobacion (id) o si es comprobacion sin anticipo.
		if(comprobacionAnticipoViajeDTO.getIdSolicitudAnticipoSession() != null)
			idSolicitud = comprobacionAnticipoViajeDTO.getIdSolicitudAnticipoSession();
		else if (comprobacionAnticipoViajeDTO.getIdSolicitudComprobacionSession() != null)
			idComprobacionViaje = comprobacionAnticipoViajeDTO.getIdSolicitudComprobacionSession();

		//Si no tiene anticipo y hay que actualizar los datos, con los que capturó en el POST.
		if(!comprobacionAnticipoViajeDTO.isConAnticipo())
			actualizaDTOConAnticipo(compAnticipoViajeDTO);
		
		comprobacionAnticipoViajeDTO.setPreguntaExtranjero1(compAnticipoViajeDTO.getPreguntaExtranjero1());
		comprobacionAnticipoViajeDTO.setPreguntaExtranjero2(compAnticipoViajeDTO.getPreguntaExtranjero2());
		comprobacionAnticipoViajeDTO.setResultadoViaje(compAnticipoViajeDTO.getResultadoViaje());
		
		Solicitud solicitudSaveOrUpdate = new Solicitud();
		Factura facturaSaveOrUpdate = new Factura();
		Solicitud solicitud = new Solicitud();
		Usuario user = usuarioService.getUsuarioSesion();
		
		//Check Segunda aprovación
		boolean segAprov = esSegundaAprobacion(comprobacionAnticipoViajeDTO); 
		

		// si cuenta con un id entonces es una solicitud para edicion
		if (idComprobacionViaje != null
				&& idComprobacionViaje > 0) {
			
			//Actualiza si es comprobacion sin solicitud anticipo
			//if(!comprobacionAnticipoViajeDTO.isConAnticipo())
			configuraAnticipo(comprobacionAnticipoViajeDTO, idComprobacionViaje, "update");
			
			// CORRESPONDE A UN UPDATE DE SOLICITUD
			
			//se cargan los valores de control desde la tabla.
			solicitud = solicitudService.getSolicitud(comprobacionAnticipoViajeDTO.getIdSolicitudComprobacionSession());
			
			// se cargan los datos de la solicitud
			solicitudSaveOrUpdate = loadSolicitud(comprobacionAnticipoViajeDTO);
			solicitudSaveOrUpdate.setCreacionFecha(solicitud.getCreacionFecha());
			solicitudSaveOrUpdate.setCreacionUsuario(solicitud.getCreacionUsuario());
			solicitudSaveOrUpdate.setEstadoSolicitud(solicitud.getEstadoSolicitud());
			solicitudSaveOrUpdate.setIdSolicitud(idComprobacionViaje);
			solicitudSaveOrUpdate.setModificacionFecha(new Date());
			solicitudSaveOrUpdate.setModificacionUsuario(idUsuario);
			if(segAprov)
				solicitudSaveOrUpdate.setSegundaAprobacion(Etiquetas.UNO_S);
			else
				solicitudSaveOrUpdate.setSegundaAprobacion(Etiquetas.CERO_S);
			solicitudSaveOrUpdate = solicitudService.updateSolicitud(solicitudSaveOrUpdate);
			
			List<ComprobacionAnticipoViajeDesgloseDTO> compDesglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose();
			
			if (idComprobacionViaje != null && idComprobacionViaje > 0) {
				if (compDesglose != null && !compDesglose.isEmpty()) {
					for (ComprobacionAnticipoViajeDesgloseDTO compDesgfactura : compDesglose) {
						facturaSaveOrUpdate = loadFactura(solicitudSaveOrUpdate.getIdSolicitud(), compDesgfactura);
						facturaSaveOrUpdate.setActivo(Etiquetas.UNO_S);
						facturaSaveOrUpdate.setCreacionUsuario(idUsuario);
						facturaSaveOrUpdate.setCreacionFecha(new Date());
						
						Integer idFactura;
						if(compDesgfactura.getIdFactura() != null){						
							idFactura = compDesgfactura.getIdFactura();
							facturaSaveOrUpdate.setIdFactura(idFactura);
							facturaSaveOrUpdate.setModificacionFecha(new Date());
							facturaSaveOrUpdate.setModificacionUsuario(idUsuario);
							//ACTUALIZAR: llamada al servicio con el objeto cargado.
							facturaSaveOrUpdate = facturaService.updateFactura(facturaSaveOrUpdate);
						}
						else{
							// CREAR factura
							facturaSaveOrUpdate.setCreacionUsuario(idUsuario);
							facturaSaveOrUpdate.setCreacionFecha(new Date());
							idFactura = facturaService.createFactura(facturaSaveOrUpdate);	
						}
						
						//CREA Factura adicionales comp anticipo viaje
						FacturaGastoViaje fgv = new FacturaGastoViaje();
						Integer idFacturaGV;
						
						if(compDesgfactura.getIdFacturaGV() != null){
							fgv = facturaGastoViajeService.getFacturaGastoViaje(compDesgfactura.getIdFacturaGV());
						}
						
						fgv = loadFacturaGV(idFactura, compDesgfactura);
						
						if(compDesgfactura.getIdFacturaGV() != null){						
							
							fgv.setModificacionFecha(new Date());
							fgv.setModificacionUsuario(idUsuario);
							
							//ACTUALIZAR: llamada al servicio con el objeto cargado.
							fgv = facturaGastoViajeService.updateFacturaGastoViaje(fgv);
						}
						else{
							fgv.setCreacionUsuario(user.getIdUsuario());
							fgv.setCreacionFecha(new Date());
							// CREAR factura 
							idFacturaGV = facturaGastoViajeService.createFacturaGastoViaje(fgv);	
						}
						
						if(compDesgfactura.isDesglosar() && compDesgfactura.getLstDesgloseDetalle() != null){
							// CREAR Desglose
							for(ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetalle : compDesgfactura.getLstDesgloseDetalle()){
								FacturaDesglose fdesgloseSaveOrUpdate = new FacturaDesglose();								
								fdesgloseSaveOrUpdate = loadFacturaDesgloseDetalle(idFactura, desgDetalle);
								
								Integer idDesglose;
								if(desgDetalle.getIdFacturaDesglose() != null){						
									idDesglose = desgDetalle.getIdFacturaDesglose();
									fdesgloseSaveOrUpdate.setIdFacturaDesglose(idDesglose);
									fdesgloseSaveOrUpdate.setModificacionFecha(new Date());
									fdesgloseSaveOrUpdate.setModificacionUsuario(idUsuario);
									//ACTUALIZAR: llamada al servicio con el objeto cargado.
									fdesgloseSaveOrUpdate = facturaDesgloseService.updateFacturaDesglose(fdesgloseSaveOrUpdate);
								}
								else{
									fdesgloseSaveOrUpdate.setCreacionUsuario(user.getIdUsuario());
									fdesgloseSaveOrUpdate.setCreacionFecha(new Date());
									// CREAR factura 
									idDesglose = facturaDesgloseService.createFacturaDesglose(fdesgloseSaveOrUpdate);
								}
							}
						}
						if(compDesgfactura.getArchivos() != null)
							guardarArchivosFactura(compDesgfactura.getArchivos(),solicitudSaveOrUpdate.getIdSolicitud(),idFactura);
					}
				}
				//Se eliminan desglosesdetalle al guadar
				for(ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetElim : lstDesgloseDetalleDelete){
					//Borrado de registro Desgloce
					if(desgDetElim.getIdFacturaDesglose() != null)
						facturaDesgloseService.deleteFacturaDesglose(desgDetElim.getIdFacturaDesglose());
				}
				//Se eliminan desgloses con detalle al guadar
				for(ComprobacionAnticipoViajeDesgloseDTO desg : lstDesgloseDelete){
					if(desg.getIdFactura() != null){
						//Borrado de archivos en bdd
						facturaArchivoService.deleteAllFacturaArchivoByIdFactura(desg.getIdFactura());
						//Borrado de registro Desgloce
						facturaDesgloseService.deleteAllByIdFactura(desg.getIdFactura());
						//Borrado de registro FacturaGV
						facturaGastoViajeService.deleteAllFacturaGastoViajeByIdFactura(desg.getIdFactura());
						//Borrado de registro Factura
						facturaService.deleteFactura(facturaService.getFactura(desg.getIdFactura()));
					}
				}					
			}
		} else {
			// SE CREARA LA SOLICITUD POR PRIMERA VEZ
			//se cargan los valores generales de la solicitud.
			solicitud = loadSolicitud(comprobacionAnticipoViajeDTO);
			solicitud.setCreacionFecha(new Date());
			solicitud.setActivo(Etiquetas.UNO_S);
			solicitud.setCreacionUsuario(user.getIdUsuario());
			if(segAprov)
				solicitudSaveOrUpdate.setSegundaAprobacion(Etiquetas.UNO_S);
			else
				solicitudSaveOrUpdate.setSegundaAprobacion(Etiquetas.CERO_S);
			//creacion.
			idComprobacionViaje = solicitudService.createSolicitud(solicitud);
			
			//guardar relacion comprobacion-anticipo
			if(idSolicitud != null)
				guardarRelacionComprobacionAnticipo(idComprobacionViaje, idSolicitud);
			//guardar relacion comprobacion-anticipo pero sin anticipo
			else{
				configuraAnticipo(compAnticipoViajeDTO, idComprobacionViaje, "save");
				guardarRelacionComprobacionAnticipo(idComprobacionViaje, idComprobacionViaje);
			}
			
			List<ComprobacionAnticipoViajeDesgloseDTO> compDesglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose();
			
			if (idComprobacionViaje != null && idComprobacionViaje > 0) {
				if (compDesglose != null && !compDesglose.isEmpty()) {
					for (ComprobacionAnticipoViajeDesgloseDTO compDesgfactura : compDesglose) {
						
						//CREAR Factura
						Factura f = new Factura();
						f = loadFactura(idComprobacionViaje, compDesgfactura);
						f.setCreacionUsuario(user.getIdUsuario());
						f.setCreacionFecha(new Date());
						f.setActivo(Etiquetas.UNO_S);
						
						Integer idFactura = facturaService.createFactura(f);
						
						//CREA Factura adicionales comp anticipo viaje
						FacturaGastoViaje fgv= new FacturaGastoViaje();
						fgv = loadFacturaGV(idFactura, compDesgfactura);
						fgv.setCreacionUsuario(user.getIdUsuario());
						fgv.setCreacionFecha(new Date());
						fgv.setActivo(Etiquetas.UNO_S);
						
						facturaGastoViajeService.createFacturaGastoViaje(fgv);
						
						if(compDesgfactura.isDesglosar()){
							// CREAR Desglose
							for(ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetalle : compDesgfactura.getLstDesgloseDetalle()){
								FacturaDesglose fdesgloseSaveOrUpdate = new FacturaDesglose();
								FacturaDesgloseDTO fdto = new FacturaDesgloseDTO();
								fdto.setConcepto("PONER CONCEPTO");
								fdto.setCuentaContable(desgDetalle.getCuentaContable());
								fdto.setLocacion(desgDetalle.getLocacion());
								fdto.setStrSubTotal(desgDetalle.getSubTotal().toPlainString());
								fdesgloseSaveOrUpdate = loadFacturaDesgloseDetalle(idFactura, desgDetalle);
								
								facturaDesgloseService.createFacturaDesglose(fdesgloseSaveOrUpdate);
							}
						}
						if(compDesgfactura.getArchivos() != null)
							guardarArchivosFactura(compDesgfactura.getArchivos(), idComprobacionViaje, idFactura);
					}
				}
			}
		}
		return new ModelAndView("redirect:comprobacionAnticipoViaje?id="+idComprobacionViaje);
	}
	
	private void actualizaDTOConAnticipo(ComprobacionAnticipoViajeDTO compAnticipoViajeDTO) {
		comprobacionAnticipoViajeDTO.setCompania(compAnticipoViajeDTO.getCompania());
		comprobacionAnticipoViajeDTO.setFormaPago(compAnticipoViajeDTO.getFormaPago());
		comprobacionAnticipoViajeDTO.setLocacion(compAnticipoViajeDTO.getLocacion());
		comprobacionAnticipoViajeDTO.setMoneda(compAnticipoViajeDTO.getMoneda());
		comprobacionAnticipoViajeDTO.setDestinoViaje(compAnticipoViajeDTO.getDestinoViaje());
		comprobacionAnticipoViajeDTO.setOtroDestino(compAnticipoViajeDTO.getOtroDestino());
		comprobacionAnticipoViajeDTO.setMotivoViaje(compAnticipoViajeDTO.getMotivoViaje());
		comprobacionAnticipoViajeDTO.setOtroMotivo(compAnticipoViajeDTO.getOtroMotivo());
		comprobacionAnticipoViajeDTO.setFechaInicio(compAnticipoViajeDTO.getFechaInicio());
		comprobacionAnticipoViajeDTO.setFechaRegreso(compAnticipoViajeDTO.getFechaRegreso());
		comprobacionAnticipoViajeDTO.setNumeroPersonas(compAnticipoViajeDTO.getNumeroPersonas());
		comprobacionAnticipoViajeDTO.setImporte(Utilerias.convertStringToBigDecimal(compAnticipoViajeDTO.getStrImporte()));
		comprobacionAnticipoViajeDTO.setResultadoViaje(compAnticipoViajeDTO.getResultadoViaje());
		comprobacionAnticipoViajeDTO.setPreguntaExtranjero1(compAnticipoViajeDTO.getPreguntaExtranjero1());
		comprobacionAnticipoViajeDTO.setPreguntaExtranjero2(compAnticipoViajeDTO.getPreguntaExtranjero2());
	}

	private void configuraAnticipo(ComprobacionAnticipoViajeDTO compAnticipoViajeDTO, Integer idSolicitud, String opcion) {	
		SolicitudAnticipoViaje solicitudAnticipoViaje = new SolicitudAnticipoViaje();
		//Guarda en Solicitud anticipo viaje
		if(opcion == "save"){
			solicitudAnticipoViaje.setSolicitud(solicitudService.getSolicitud(idSolicitud));
			solicitudAnticipoViaje.setActivo(Etiquetas.UNO_S);
			solicitudAnticipoViaje.setCreacionFecha(new Date());
			solicitudAnticipoViaje.setCreacionUsuario(usuarioService.getUsuarioSesion().getIdUsuario());
		}
		//Update en Solicitud anticipo viaje
		else if (opcion == "update"){
			solicitudAnticipoViaje = solicitudAnticipoViajeService.getSolicitudAnticipoViajeBySolicitud(comprobacionAnticipoService.getAnticiposByComprobacion(idSolicitud).get(0).getSolicitudByIdSolicitudAnticipo().getIdSolicitud());
			solicitudAnticipoViaje.setActivo(Etiquetas.UNO_S);
			solicitudAnticipoViaje.setModificacionUsuario(usuarioService.getUsuarioSesion().getIdUsuario());
			solicitudAnticipoViaje.setModificacionFecha(new Date());
		}
		//Load Object
		if (compAnticipoViajeDTO != null) {
			//Set si Otro Viaje Destino
			ViajeDestino vd = viajeDestinoService.getViajeDestino(compAnticipoViajeDTO.getViajeDestino().getIdViajeDestino());
			if(vd.getEsOtro() != null){
				if(vd.getEsOtro() == 1)
					solicitudAnticipoViaje.setDescripcionOtroDestino(compAnticipoViajeDTO.getOtroDestino());
			}
			//Set si Otro Viaje Motivo
			ViajeMotivo vm = viajeMotivoService.getViajeMotivo(compAnticipoViajeDTO.getViajeMotivo().getIdViajeMotivo());
			if(vm.getEsOtro() != null){
				if(vm.getEsOtro() == 1)
					solicitudAnticipoViaje.setDescripcionOtroMotivo(compAnticipoViajeDTO.getOtroMotivo());
			}
			solicitudAnticipoViaje.setViajeDestino(compAnticipoViajeDTO.getViajeDestino());
			solicitudAnticipoViaje.setViajeMotivo(compAnticipoViajeDTO.getViajeMotivo());
			try {
				solicitudAnticipoViaje.setInicioFecha(Utilerias.parseDate(compAnticipoViajeDTO.getFechaInicio(), "dd/MM/yyyy"));
				solicitudAnticipoViaje.setFinFecha(Utilerias.parseDate(compAnticipoViajeDTO.getFechaRegreso(), "dd/MM/yyyy"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			solicitudAnticipoViaje.setNumPersonas(compAnticipoViajeDTO.getNumeroPersonas());
			solicitudAnticipoViaje.setPreguntaExtrangero1(compAnticipoViajeDTO.getPreguntaExtranjero1() == true ? (short) 1 : 0);
			solicitudAnticipoViaje.setPreguntaExtrangero2(compAnticipoViajeDTO.getPreguntaExtranjero2() == true ? (short) 1 : 0);
			solicitudAnticipoViaje.setResultadoViaje(compAnticipoViajeDTO.getResultadoViaje());
		}
		//Perform option
		if(opcion == "save")
			solicitudAnticipoViajeService.createSolicitudAnticipoViaje(solicitudAnticipoViaje);
		else if (opcion == "update")
			solicitudAnticipoViajeService.updateSolicitudAnticipoViaje(solicitudAnticipoViaje);
	}

	private FacturaDesglose loadFacturaDesgloseDetalle(Integer idFactura,
			ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetalle) {
		FacturaDesglose fdesglose = new FacturaDesglose();
		fdesglose.setFactura(new Factura(idFactura));
		fdesglose.setLocacion(desgDetalle.getLocacion());
		fdesglose.setCuentaContable(desgDetalle.getCuentaContable());
		fdesglose.setConcepto("PONER CONCEPTO DESGLOSE");
		fdesglose.setSubtotal(desgDetalle.getSubTotal());
		fdesglose.setOtrosImpuestos(desgDetalle.getOtrosImpuestos());
		fdesglose.setViajeConcepto(desgDetalle.getViajeConcepto());
		if(fdesglose.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_ALIMENTOS)
			fdesglose.setViajeTipoAlimentos(desgDetalle.getViajeTipoAlimentos());
		fdesglose.setNombre(desgDetalle.getNombre());
		fdesglose.setCreacionFecha(new Date());
		fdesglose.setActivo(Etiquetas.UNO_S);
		return fdesglose;
	}
	private FacturaGastoViaje loadFacturaGV(Integer idSolicitud, ComprobacionAnticipoViajeDesgloseDTO compDesgfactura) {
		FacturaGastoViaje factura = new FacturaGastoViaje();

		factura.setFactura(facturaService.getFactura(idSolicitud));
		try {
			factura.setFechaGasto(Utilerias.parseDate(compDesgfactura.getFecha_gasto(), "dd/MM/yyyy"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		factura.setComercio(compDesgfactura.getComercio());
		factura.setViajeConcepto(viajeConceptoService.getViajeConcepto(compDesgfactura.getIdViajeConcepto()));
		factura.setCiudad(compDesgfactura.getCiudadTexto());
		factura.setOtrosImpuestos(compDesgfactura.getOtrosImpuestos() != null ? compDesgfactura.getOtrosImpuestos() : new BigDecimal(0.00));
		factura.setTua(compDesgfactura.getTua() != null ? compDesgfactura.getTua() : new BigDecimal(0.00));
		factura.setIsh(compDesgfactura.getIsh() != null ? compDesgfactura.getIsh() : new BigDecimal(0.00));
		factura.setYri(compDesgfactura.getYri() != null ? compDesgfactura.getYri() : new BigDecimal(0.00));
		if(factura.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_ALIMENTOS){
			factura.setViajeTipoAlimentos(viajeTipoAlimentosService.getViajeTipoAlimentos(compDesgfactura.getIdViajeTipoAlimento()));
		}
		
		factura.setSegundaAprobacion(compDesgfactura.isSegundaAprobacion() ? (short) 1 : (short) 0);
		factura.setDesglosar(compDesgfactura.isDesglosar() ? (short) 1 : (short) 0);
		if(compDesgfactura.isDesglosar())
			factura.setNumeroPersonas(compDesgfactura.getNumeroPersonas());
		
		return factura;
	}
	
	/**
	 * @author miguelr
	 * @param solicitudDTO
	 * @return Solicitud - Objeto con los datos de la solicitud.
	 * CARGA LA INFORMACIÓN DE LA SOLICITUD EN UN OBJETO TIPO SOLICITUD PARA SU INSERCIÓN A BDD.
	 */
	private Solicitud loadSolicitud(ComprobacionAnticipoViajeDTO comprobacionAnticipoViajeDTO) {
		Solicitud sol = new Solicitud();
		Usuario user = usuarioService.getUsuarioSesion();
		
		BigDecimal totalComprobacion = new BigDecimal(0.00);
		
		//totales de los desgloses
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : comprobacionAnticipoViajeDTO.getComprobacionAntDesglose()){
			if(desglose.getTotal() != null){
				totalComprobacion = totalComprobacion.add(desglose.getTotal());
			}
		}
		//mas el total del deposito
		if(comprobacionAnticipoViajeDTO.getImporteDeposito() != null){
			BigDecimal deposito = new BigDecimal(comprobacionAnticipoViajeDTO.getImporteDeposito()).setScale(2);
			totalComprobacion = totalComprobacion.add(deposito);
		}
		
		// definir tipo de solicitud
		sol.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE));
		//sol.setMontoTotal(comprobacionAnticipoViajeDTO.getImporte());
		sol.setMontoTotal(totalComprobacion);
	    sol.setUsuarioByIdUsuario(user);
		sol.setUsuarioByIdUsuarioSolicita(user);
		sol.setConceptoGasto(comprobacionAnticipoViajeDTO.getResultadoViaje());
		sol.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.UNO));
		sol.setMoneda(comprobacionAnticipoViajeDTO.getMoneda());
		sol.setCompania(comprobacionAnticipoViajeDTO.getCompania());
		sol.setLocacion(comprobacionAnticipoViajeDTO.getLocacion());
		sol.setFormaPago(comprobacionAnticipoViajeDTO.getFormaPago());
		sol.setLocacion(comprobacionAnticipoViajeDTO.getLocacion());
		
		
		return sol;
	}
	
	/**
	 * @author miguelr
	 * @param idSolicitud
	 * @param facturaSolicitudDTO
	 * @return Factura
	 * CARGA LA INFORMACIÓN DE LA FACTURA EN UN OBJETO.
	 */
	private Factura loadFactura(Integer idSolicitud, ComprobacionAnticipoViajeDesgloseDTO comprobacionAnticipoViajeDesgloseDTO){
		Factura factura = new Factura();
		factura.setSolicitud(new Solicitud(idSolicitud));
		factura.setMoneda(monedaService.getMoneda(comprobacionAnticipoViajeDesgloseDTO.getIdMoneda()));
		factura.setFactura(comprobacionAnticipoViajeDesgloseDTO.getFolio());
		//factura.setCompaniaByIdCompania(companiaService.getCompania(comprobacionAnticipoViajeDesgloseDTO.getIdCompania()));
		factura.setCuentaContable(cuentaContableService.getCuentaContable(comprobacionAnticipoViajeDesgloseDTO.getIdCuentaContable()));

		TipoFactura tFact = new TipoFactura();
		tFact.setIdTipoFactura((comprobacionAnticipoViajeDesgloseDTO.isConCompFiscal() == true) ? Etiquetas.TIPO_FACTURA_FACTURA : Etiquetas.TIPO_FACTURA_COMPROBANTE );
		factura.setTipoFactura(tFact);
		try {
			factura.setFechaFactura(Utilerias.parseDate(comprobacionAnticipoViajeDesgloseDTO.getFecha_factura(), "dd/MM/yyyy"));
		} catch (ParseException e) {
	
		}
		
		if(comprobacionAnticipoViajeDesgloseDTO.isConCompFiscal()){
			
			if(comprobacionAnticipoViajeDesgloseDTO.getProveedor() != null)
				factura.setProveedor(proveedorService.getProveedor(comprobacionAnticipoViajeDesgloseDTO.getProveedor()));
			else
				factura.setProveedorLibre(proveedorLibreService.getProveedorLibre(comprobacionAnticipoViajeDesgloseDTO.getProveedorLibre()));

			factura.setSerieFactura(comprobacionAnticipoViajeDesgloseDTO.getSerie());
			factura.setFolioFiscal(comprobacionAnticipoViajeDesgloseDTO.getFolioFiscal());
			
			// retenciones
//			if (facturaComp.isConRetenciones()) {
//				factura.setConRetenciones(Etiquetas.UNO_S);
//				factura.setIvaRetenido(facturaComp.getIVARetenido());
//				factura.setIsrRetenido(facturaComp.getISRRetenido());
//			} else {
//				factura.setConRetenciones(Etiquetas.CERO_S);
//			}
		}
		factura.setIva((comprobacionAnticipoViajeDesgloseDTO.getIva() != null) ? comprobacionAnticipoViajeDesgloseDTO.getIva() : new BigDecimal(0.00));
		factura.setIeps((comprobacionAnticipoViajeDesgloseDTO.getIeps() != null) ? comprobacionAnticipoViajeDesgloseDTO.getIeps() : new BigDecimal(0.00));
		//factura.setPorcentajeIva(comprobacionAnticipoViajeDesgloseDTO.getTasaIva());
		//factura.setPorcentajeIeps(comprobacionAnticipoViajeDesgloseDTO.getTasaIeps());
		factura.setConceptoGasto(comprobacionAnticipoViajeDesgloseDTO.getConcepto());
		factura.setSubtotal(comprobacionAnticipoViajeDesgloseDTO.getSubTotal());
		factura.setTotal(comprobacionAnticipoViajeDesgloseDTO.getTotal());
		factura.setLocacion(locacionService.getLocacion(comprobacionAnticipoViajeDesgloseDTO.getIdLocacion()));
		return factura;
	}
	
	/**
	 * @author miguelr
	 * @param idSolicitud
	 * @param facturaSolicitudDTO
	 * @return Factura
	 * CARGA LA INFORMACIÓN DE LA FACTURA DESGLOSE EN UN OBJETO.
	 */
	private FacturaDesglose loadFacturaDesglose(Integer idFactura, ComprobacionAnticipoViajeDesgloseDTO compDesgfactura){
		FacturaDesglose fdesglose = new FacturaDesglose();
		fdesglose.setFactura(new Factura(idFactura));
		fdesglose.setLocacion(compDesgfactura.getLocacion());
		fdesglose.setCuentaContable(compDesgfactura.getCuentaContable());
		fdesglose.setConcepto(compDesgfactura.getConcepto());
		fdesglose.setSubtotal(compDesgfactura.getSubTotal());
		fdesglose.setCreacionFecha(new Date());
		fdesglose.setActivo(Etiquetas.UNO_S);
		return fdesglose;
	}

	// metodo ajax para la carga dinamica de edicion.
	@RequestMapping(value = "/getDetalleDesglose", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> getDetalleDesglose(HttpSession session, @RequestParam int index,
			HttpServletRequest request, HttpServletResponse response) {
		
		String json = null;
		boolean desgloseCompleto = false;
		HashMap<String, String> result = new HashMap<String, String>();
		
		List<ComprobacionAnticipoViajeDesgloseDetalleDTO> lstDesgloseDetalle = new ArrayList<>();
		
		ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(index-1);
		
		desgloseCompleto = desglose.isDesgloseCompleto();
		
		// consulta
		if (index > Etiquetas.CERO) {
			result.put("comercio", String.valueOf(desglose.getComercio()));
			result.put("subTotal", String.valueOf(desglose.getSubTotal()));
			result.put("otrosImpuestos", String.valueOf(desglose.getOtrosImpuestos()));
			result.put("numeroPersonas", String.valueOf(desglose.getNumeroPersonas()));
			
			BigDecimal saldoSubtotal = new BigDecimal(0);
			BigDecimal saldoOtros = new BigDecimal(0);
			BigDecimal pendienteSubtotal = new BigDecimal(0);
			BigDecimal pendienteOtros = new BigDecimal(0);
			
			//Si ya tiene un desglose detalle guardado o si se calcula un inicial de acuerdo al numero de personas.
			if(desglose.getLstDesgloseDetalle() != null){
				for(ComprobacionAnticipoViajeDesgloseDetalleDTO item : desglose.getLstDesgloseDetalle()){
					saldoSubtotal = saldoSubtotal.add(item.getSubTotal());
					saldoOtros = saldoOtros.add(item.getOtrosImpuestos());
					pendienteSubtotal = desglose.getSubTotal().subtract(saldoSubtotal);
					pendienteOtros = desglose.getOtrosImpuestos().subtract(saldoOtros);
				}
			}
			else{
				for(int i = 0; i < desglose.getNumeroPersonas(); i++){
					ComprobacionAnticipoViajeDesgloseDetalleDTO item = new ComprobacionAnticipoViajeDesgloseDetalleDTO();
					item.setSubTotal(desglose.getSubTotal().divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR));
					item.setOtrosImpuestos(desglose.getOtrosImpuestos().divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR));
					
					item.setLocacion(new Locacion(desglose.getIdLocacion()));
					item.setCuentaContable(new CuentaContable(desglose.getIdCuentaContable()));
					item.setViajeConcepto(desglose.getViajeConcepto());
					item.setViajeTipoAlimentos(desglose.getViajeTipoAlimentos());
					
					
					saldoSubtotal = saldoSubtotal.add(item.getSubTotal());
					saldoOtros = saldoOtros.add(item.getOtrosImpuestos());
					pendienteSubtotal = desglose.getSubTotal().subtract(saldoSubtotal);
					pendienteOtros = desglose.getOtrosImpuestos().subtract(saldoOtros);
					lstDesgloseDetalle.add(item);
				}
				desglose.setLstDesgloseDetalle(lstDesgloseDetalle);
			}

			result.put("desgloseCompleto", String.valueOf(desgloseCompleto));
			result.put("saldoSubtotal", String.valueOf(saldoSubtotal));
			result.put("saldoOtros", String.valueOf(saldoOtros));
			result.put("pendienteSubtotal", String.valueOf(pendienteSubtotal));
			result.put("pendienteOtros", String.valueOf(pendienteOtros));
			result.put("gridDesgloseDetalle", buildGridDesgloseDetalle(desglose.getLstDesgloseDetalle()));

			// bind json
			ObjectMapper map = new ObjectMapper();
			if (!result.isEmpty()) {
				try {
					json = map.writeValueAsString(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		desgloseAnterior = cloneArray(desglose.getLstDesgloseDetalle());
		
		esSegundaAprobacion(comprobacionAnticipoViajeDTO);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}
	
	private List<ComprobacionAnticipoViajeDesgloseDetalleDTO> cloneArray(
			List<ComprobacionAnticipoViajeDesgloseDetalleDTO> lstDesgloseDetalle) {
	    List<ComprobacionAnticipoViajeDesgloseDetalleDTO> clone = new ArrayList<ComprobacionAnticipoViajeDesgloseDetalleDTO>(lstDesgloseDetalle.size());
	    for(ComprobacionAnticipoViajeDesgloseDetalleDTO item: lstDesgloseDetalle) 
	    	clone.add(item.clone());
	    return clone;
	}
	
	/**
	 * @author miguelr
	 * Peticion ajax que agrega un row al desglose detalle
	 * @param session
	 * @param detIndex El indice del desglose
	 * @param numrows
	 * @param request
	 * @param response
	 * @return json - Respuesta
	 */
	@RequestMapping(value = "/addRowDesgloseDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> addRowDesgloseDetalle(HttpSession session, @RequestParam Integer numrows, 
			@RequestParam Integer detIndex, HttpServletRequest request, HttpServletResponse response) {

		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();
		
		result.put("rowDetalle", buildRowDesgloseDetalle(numrows+1, detIndex));

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
	
	/**
	 * @author miguelr
	 * @param items
	 * @return String
	 * CONSTRUYE GRID GENERICO PARA DESGLOSE DETALLE
	 */
	private String buildGridDesgloseDetalle(List<ComprobacionAnticipoViajeDesgloseDetalleDTO> items) {
		StringBuilder gridDesgloseDetalle = new StringBuilder();
		Integer numLinea = 1;
		
		//Carga lista de conceptos
		List<ViajeConcepto> viajeConceptoList = viajeConceptoService.getAllViajeConcepto();
		//Carga Lista de tipo alimentos
		List<ViajeTipoAlimentos> viajeTipoAlimentos = viajeTipoAlimentosService.getAllViajeTipoAlimentos();
		// Obtener combos filtrador por configuracion de solicitante.
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);

		List<Locacion> lcPermitidas = new ArrayList<>();
		/*carga de locaciones por anticipo y confirmacion de anticipo de gastos de viaje.*/
		//List<Locacion> locacionesPermitidasAnticipo = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE,locacionService.getAllLocaciones(), idUsuario);
		List<Locacion> locacionesPermitidasComprobacion = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, comprobacionAnticipoViajeDTO.getIdTipoSolicitud() ,locacionService.getAllLocaciones(), idUsuario);
		
		//lcPermitidas.addAll(locacionesPermitidasAnticipo);
		lcPermitidas.addAll(locacionesPermitidasComprobacion);
		
		for(ComprobacionAnticipoViajeDesgloseDetalleDTO detalle : items){
			
			//Linea
			gridDesgloseDetalle.append("<tr>");
			gridDesgloseDetalle.append("<td class=\"centro\"><div class=\"linea\">" + numLinea + "</div></td>");
			
			// editar/remover
			gridDesgloseDetalle.append("<td class=\"centro\"><button type=\"button\" style=\"height: 22px;\" class=\"btn btn-xs btn-danger removerFila\"><span class=\"glyphicon glyphicon-trash\"></span></button></td>");
			
			//Subtotal
			gridDesgloseDetalle.append("<td><input type=\"text\" id=\"lstDetalle" + numLinea
					+ ".subTotal\" name=\"lstDetalle[" + numLinea
					+ "].subTotal\" onblur=\"detSbtsOnChange.call(this)\" class=\"form-control detSbts currencyFormat\" value=\"" + detalle.getSubTotal() + "\" ></td>");
			
			//Otros Impuestos
			gridDesgloseDetalle.append("<td><input type=\"text\" id=\"lstDetalle" + numLinea
					+ ".otrosImp\" name=\"lstDetalle[" + numLinea
					+ "].otrosImp\" onblur=\"detOtrosImpOnChange.call(this)\" class=\"form-control detOtrosImp currencyFormat\" value=\"" + detalle.getOtrosImpuestos() + "\" ></td>");
			
			//Concepto
			gridDesgloseDetalle.append("<td><select disabled=\"true\" id=\"lstDetalle[" + numLinea + "].concepto\" name=\"lstDetalle["
					+ numLinea + "].concepto\" onChange=\"detConceptoOnChange.call(this)\" class=\"form-control detConcepto\">");
			
			gridDesgloseDetalle.append("<option value=\"");
			gridDesgloseDetalle.append(String.valueOf("-1"));
			gridDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
			for (ViajeConcepto concepto : viajeConceptoList) {
				gridDesgloseDetalle.append("<option value=\""+String.valueOf(concepto.getIdViajeConcepto())+"\"");
				if(detalle.getViajeConcepto() != null){
					if(detalle.getViajeConcepto().getIdViajeConcepto() == concepto.getIdViajeConcepto()){
						gridDesgloseDetalle.append("selected");
					}
				}
				gridDesgloseDetalle.append("> " + concepto.getDescripcion() + " </option>");
			}	
			gridDesgloseDetalle.append("</select>");
			gridDesgloseDetalle.append("</td>");
			
			//BLD
			String disableBLD = "disabled=\"true\"";
//			if(detalle.getViajeConcepto() != null){
//				disableBLD = detalle.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_ALIMENTOS ? "" : "disabled=\"true\"";
//			}
			
			gridDesgloseDetalle.append("<td><select id=\"lstDetalle[" + numLinea + "].bld\" name=\"lstDetalle["
					+ numLinea + "].bld\" onChange=\"detBldOnChange.call(this)\" class=\"form-control detBld\" "+ disableBLD +">");
			
			gridDesgloseDetalle.append("<option value=\"");
			gridDesgloseDetalle.append(String.valueOf("-1"));
			gridDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
			for (ViajeTipoAlimentos alimentos : viajeTipoAlimentos) {
				gridDesgloseDetalle.append("<option value=\""+String.valueOf(alimentos.getIdViajeTipoAlimentos())+"\"");
				if(detalle.getViajeTipoAlimentos() != null){
					if(detalle.getViajeTipoAlimentos().getIdViajeTipoAlimentos() == alimentos.getIdViajeTipoAlimentos())
						gridDesgloseDetalle.append("selected");
				}
				gridDesgloseDetalle.append("> " + alimentos.getDescripcion() + " </option>");
			}	
			gridDesgloseDetalle.append("</select>");
			gridDesgloseDetalle.append("</td>");
			
			//Locacion
			gridDesgloseDetalle.append("<td><select id=\"lstDetalle[" + numLinea + "].locacion\" onChange=\"detLocacionOnChange.call(this)\" name=\"lstDetalle["
					+ numLinea + "].locacion\" class=\"form-control detLoc\">");
			
			gridDesgloseDetalle.append("<option value=\"");
			gridDesgloseDetalle.append(String.valueOf("-1"));
			gridDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
			for (Locacion locacione : lcPermitidas) {
				gridDesgloseDetalle.append("<option value=\""+String.valueOf(locacione.getIdLocacion())+"\"");
				if(detalle.getLocacion() != null){
					if(detalle.getLocacion().getIdLocacion() == locacione.getIdLocacion())
						gridDesgloseDetalle.append("selected");
				}
				gridDesgloseDetalle.append("> " + locacione.getDescripcion() + " </option>");
			}	
			gridDesgloseDetalle.append("</select>");
			gridDesgloseDetalle.append("</td>");
			
			//CuentaContable
			gridDesgloseDetalle.append("<td class=\"ccontable\"><select id=\"lstDesgDetCC" + numLinea + "\" name=\"lstDetalle["
					+ numLinea + "].desgDetCC\" onChange=\"detCuentaContOnChange.call(this)\" class=\"form-control detCuentasCont\" "+0+">");
			
			gridDesgloseDetalle.append("<option value=\"");
			gridDesgloseDetalle.append(String.valueOf("-1"));
			gridDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
			
			if(detalle.getCuentaContable() != null && detalle.getLocacion() != null){
				List<CuentaContable> ccPermitidas = UtilController.getCuentasContablesPermitidasPorLocacion(detalle.getLocacion().getIdLocacion(), uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario), Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE);
				for (CuentaContable cuentaContable : ccPermitidas) {
					gridDesgloseDetalle.append("<option value=\""+String.valueOf(cuentaContable.getIdCuentaContable())+"\"");
					if(detalle.getCuentaContable().getIdCuentaContable() == cuentaContable.getIdCuentaContable())
							gridDesgloseDetalle.append("selected");
					gridDesgloseDetalle.append("> " + cuentaContable.getDescripcion() + " </option>");
				}	
			}
	
			gridDesgloseDetalle.append("</select>");
			gridDesgloseDetalle.append("</td>");
			
			//nombre
			gridDesgloseDetalle.append("<td><input maxlength=\"100\" id=\"lstDetalle[" + numLinea + "].nombreCaptura\" onChange=\"detNombreOnChange.call(this)\" class=\"form-control nombresCaptura input-tabla-desglose\" type=\"text\" value=\"" + (detalle.getNombre()!= null ? detalle.getNombre() : "") + "\" /></td>");
			
			numLinea += 1;
		}
		
		return gridDesgloseDetalle.toString();
	}
	
	/**
	 * @author miguelr
	 * @param numLinea
	 * @param detIndex
	 * @return String
	 * CONSTRUYE UN ROW VACÍO PARA EL DESGLOSE DETALLE
	 */
	private String buildRowDesgloseDetalle(Integer numLinea, Integer detIndex) {
		StringBuilder rowDesgloseDetalle = new StringBuilder();
		
		ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
		
		Integer idLocacionDesglose = 0;
		Integer idCuentaContableDesglose = 0;
		Integer idConceptoDesglose = 0;
		List<CuentaContable> ccPermitidasDesglose = new ArrayList<>();

		if(desglose != null){
			idLocacionDesglose = desglose.getIdLocacion();
			idCuentaContableDesglose = desglose.getIdCuentaContable();
			idConceptoDesglose = desglose.getIdViajeConcepto();
		}
		
		//setear la cuenta contable default
		if (idLocacionDesglose != null && idLocacionDesglose > 0 && idCuentaContableDesglose != null
				&& idCuentaContableDesglose > 0) {
			
			List<CuentaContable> ccPermitidasComprobacion = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacionDesglose, uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario), Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE);
			ccPermitidasDesglose.addAll(ccPermitidasComprobacion);
		}

		
		//Carga lista de conceptos
		List<ViajeConcepto> viajeConceptoList = viajeConceptoService.getAllViajeConcepto();
		//Carga Lista de tipo alimentos
		List<ViajeTipoAlimentos> viajeTipoAlimentos = viajeTipoAlimentosService.getAllViajeTipoAlimentos();
		// Obtener combos filtrador por configuracion de solicitante.
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
		
		List<Locacion> lcPermitidas = new ArrayList<>();
		/*carga de locaciones por anticipo y confirmacion de anticipo de gastos de viaje.*/
		List<Locacion> locacionesPermitidasAnticipo = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE,locacionService.getAllLocaciones(), idUsuario);
		List<Locacion> locacionesPermitidasComprobacion = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, comprobacionAnticipoViajeDTO.getIdTipoSolicitud() ,locacionService.getAllLocaciones(), idUsuario);
		
		lcPermitidas.addAll(locacionesPermitidasAnticipo);
		lcPermitidas.addAll(locacionesPermitidasComprobacion);

		//Linea
		rowDesgloseDetalle.append("<tr>");
		rowDesgloseDetalle.append("<td class=\"centro\"><div class=\"linea\">" + numLinea + "</div></td>");
		
		// editar/remover
		rowDesgloseDetalle.append("<td class=\"centro\"><button type=\"button\" style=\"height: 22px;\" class=\"btn btn-xs btn-danger removerFila\"><span class=\"glyphicon glyphicon-trash\"></span></button></td>");
		
		//Subtotal
		rowDesgloseDetalle.append("<td><input type=\"text\" id=\"lstDetalle" + numLinea
				+ ".subTotal\" name=\"lstDetalle[" + numLinea
				+ "].subTotal\" onblur=\"detSbtsOnChange.call(this)\" class=\"form-control detSbts currencyFormat\" value=\"0.00\" ></td>");
		
		//Otros Impuestos
		rowDesgloseDetalle.append("<td><input type=\"text\" id=\"lstDetalle" + numLinea
				+ ".otrosImp\" name=\"lstDetalle[" + numLinea
				+ "].otrosImp\" onblur=\"detOtrosImpOnChange.call(this)\" class=\"form-control detOtrosImp currencyFormat\" value=\"0.00\" ></td>");
		
		//Concepto
		rowDesgloseDetalle.append("<td><select disabled=\"true\" id=\"lstDetalle[" + numLinea + "].concepto\" name=\"lstDetalle["
				+ numLinea + "].concepto\" onChange=\"detConceptoOnChange.call(this)\" class=\"form-control detConcepto\">");
		
		rowDesgloseDetalle.append("<option value=\"");
		rowDesgloseDetalle.append(String.valueOf("-1"));
		rowDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
		for (ViajeConcepto concepto : viajeConceptoList) {
			if (idConceptoDesglose != null && idConceptoDesglose > 0
					&& idConceptoDesglose == concepto.getIdViajeConcepto()) {
				rowDesgloseDetalle.append("<option selected value=\"");
				rowDesgloseDetalle.append(String.valueOf(concepto.getIdViajeConcepto()));
				rowDesgloseDetalle.append("\"> " + concepto.getDescripcion() + " </option>");
			} else {
				rowDesgloseDetalle.append("<option value=\"");
				rowDesgloseDetalle.append(String.valueOf(concepto.getIdViajeConcepto()));
				rowDesgloseDetalle.append("\"> " + concepto.getDescripcion() + " </option>");
			}

		}
		rowDesgloseDetalle.append("</select>");
		rowDesgloseDetalle.append("</td>");
		
		//BLD
		rowDesgloseDetalle.append("<td><select id=\"lstDetalle[" + numLinea + "].bld\" name=\"lstDetalle["
				+ numLinea + "].bld\" onChange=\"detBldOnChange.call(this)\" class=\"form-control detBld\" disabled=\"true\">");
		
		rowDesgloseDetalle.append("<option value=\"");
		rowDesgloseDetalle.append(String.valueOf("-1"));
		rowDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
		for (ViajeTipoAlimentos alimentos : viajeTipoAlimentos) {
			rowDesgloseDetalle.append("<option value=\"");
			rowDesgloseDetalle.append(String.valueOf(alimentos.getIdViajeTipoAlimentos()));
			rowDesgloseDetalle.append("\"> " + alimentos.getDescripcion() + " </option>");
		}	
		rowDesgloseDetalle.append("</select>");
		rowDesgloseDetalle.append("</td>");
		
		//Locacion
		
		rowDesgloseDetalle.append("<td><select id=\"lstDetalle[" + numLinea + "].locacion\" onChange=\"detLocacionOnChange.call(this)\" name=\"lstDetalle["
				+ numLinea + "].locacion\" class=\"form-control detLoc\">");
		
		rowDesgloseDetalle.append("<option value=\"");
		rowDesgloseDetalle.append(String.valueOf("-1"));
		rowDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
		for (Locacion locacione : lcPermitidas) {
			if(idLocacionDesglose != null && idLocacionDesglose > 0 && idLocacionDesglose == locacione.getIdLocacion()){
				rowDesgloseDetalle.append("<option selected value=\"");
				rowDesgloseDetalle.append(String.valueOf(locacione.getIdLocacion()));
				rowDesgloseDetalle.append("\"> " + locacione.getDescripcion() + " </option>");
			}else{
				rowDesgloseDetalle.append("<option value=\"");
				rowDesgloseDetalle.append(String.valueOf(locacione.getIdLocacion()));
				rowDesgloseDetalle.append("\"> " + locacione.getDescripcion() + " </option>");
			}
		}	
		rowDesgloseDetalle.append("</select>");
		rowDesgloseDetalle.append("</td>");
		
		//CuentaContable
		rowDesgloseDetalle.append("<td class=\"ccontable\"><select id=\"lstDesgDetCC[" + numLinea + "].cuentaContable\" name=\"lstDetalle["
				+ numLinea + "].desgDetCC\" onChange=\"detCuentaContOnChange.call(this)\" class=\"form-control detCuentasCont\">");
		
		
		if (ccPermitidasDesglose != null && ccPermitidasDesglose.isEmpty() == false) {
			rowDesgloseDetalle.append("<option value=\"-1\">Seleccione:</option>");
			for (CuentaContable cc : ccPermitidasDesglose) {
				if (idCuentaContableDesglose != null && idCuentaContableDesglose > 0
						&& idCuentaContableDesglose == cc.getIdCuentaContable()) {
					rowDesgloseDetalle.append("<option selected value=");
					rowDesgloseDetalle.append(cc.getIdCuentaContable());
					rowDesgloseDetalle.append(">");
					rowDesgloseDetalle.append(cc.getDescripcion());
					rowDesgloseDetalle.append("</option>");
				} else {
					rowDesgloseDetalle.append("<option value=");
					rowDesgloseDetalle.append(cc.getIdCuentaContable());
					rowDesgloseDetalle.append(">");
					rowDesgloseDetalle.append(cc.getDescripcion());
					rowDesgloseDetalle.append("</option>");
				}

			}
		} else {
			rowDesgloseDetalle.append("<option value=\"");
			rowDesgloseDetalle.append(String.valueOf("-1"));
			rowDesgloseDetalle.append("\"> " + "Seleccione:" + " </option>");
		}
		

		rowDesgloseDetalle.append("</select>");
		rowDesgloseDetalle.append("</td>");
		
		//nombre
		rowDesgloseDetalle.append("<td><input maxlength=\"100\" id=\"lstDetalle[" + numLinea + "].nombreCaptura\" onChange=\"detNombreOnChange.call(this)\" class=\"form-control nombresCaptura input-tabla-desglose\" type=\"text\" value=\"\" /></td>");
		
		
		ComprobacionAnticipoViajeDesgloseDetalleDTO item = new ComprobacionAnticipoViajeDesgloseDetalleDTO();
		item.setSubTotal(new BigDecimal(0));
		item.setOtrosImpuestos(new BigDecimal(0));
		desglose.getLstDesgloseDetalle().add(item);

		return rowDesgloseDetalle.toString();
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param subtotal
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DEL SUBTOTAL CUANDO ES MODIFICADA EN EL GRID.
	 */
	@RequestMapping(value = "/updStbsDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updStbsDetalle(HttpSession session, @RequestParam Integer detIndex, @RequestParam Integer numrow,
			@RequestParam String value, HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		String subTotal = value;
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.getLstDesgloseDetalle().get(numrow -1).setSubTotal(new BigDecimal(subTotal));
			option.append("true");
		}
		catch(Exception e){
			option.append("false");
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param otrosImp
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DEL SUBTOTAL CUANDO ES MODIFICADA EN EL GRID.
	 */
	@RequestMapping(value = "/updOtrosImpDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updOtrosImpDetalle(HttpSession session, @RequestParam Integer detIndex, @RequestParam Integer numrow,
			@RequestParam String value, HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		String otrosImp = value;
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.getLstDesgloseDetalle().get(numrow -1).setOtrosImpuestos(new BigDecimal(otrosImp));
			option.append("true");
		}
		catch(Exception e){
			option.append("false");
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param concepto
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DEL CONCEPTO CUANDO ES MODIFICADA EN EL GRID.
	 */
	@RequestMapping(value = "/updConceptoDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updConceptoDetalle(HttpSession session, @RequestParam Integer detIndex, @RequestParam Integer numrow,
			@RequestParam Integer value, HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		Integer concepto = value;
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.getLstDesgloseDetalle().get(numrow -1).setViajeConcepto(viajeConceptoService.getViajeConcepto(concepto));
			option.append("true");
		}
		catch(Exception e){
			option.append("false");
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param tipoAlimento
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DEL TIPO ALIMENTO CUANDO ES MODIFICADA EN EL GRID.
	 */
	@RequestMapping(value = "/updBldDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updBldDetalle(HttpSession session, @RequestParam Integer detIndex, @RequestParam Integer numrow,
			@RequestParam Integer value, HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		Integer tipoAlimento = value;
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.getLstDesgloseDetalle().get(numrow -1).setViajeTipoAlimentos(viajeTipoAlimentosService.getViajeTipoAlimentos(tipoAlimento));
			option.append("true");
		}
		catch(Exception e){
			option.append("false");
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DE LA LOCACION CUANDO ES MODIFICADA EN EL COMBO DEL GRID.
	 */
	@RequestMapping(value = "/updLocacionDesgloseDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updLocacion(HttpSession session, @RequestParam Integer detIndex, @RequestParam Integer numrow,
		   @RequestParam Integer idLocacion, HttpServletRequest request, HttpServletResponse response) {		

		StringBuilder option = new StringBuilder();

		//List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
		List<CuentaContable> ccPermitidas = new ArrayList<>();
		List<CuentaContable> ccPermitidasComprobacion = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacion, uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario), Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE);
		ccPermitidas.addAll(ccPermitidasComprobacion);
		
		
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.getLstDesgloseDetalle().get(numrow -1).setLocacion(locacionService.getLocacion(idLocacion));
		}
		catch(Exception e){

		}
		
		option.append("<option value=\"-1\">Seleccione:</option>");
		for(CuentaContable cc : ccPermitidas){
			option.append("<option value=");
			option.append(cc.getIdCuentaContable());
			option.append(">");
			option.append(cc.getDescripcion());
			option.append("</option>"); 
		}	
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}

	
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param cuentaContable
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DE CUENTA CONTABLE CUANDO ES MODIFICADA EN EL GRID.
	 */
	@RequestMapping(value = "/updCuentaContDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updCuentaContDetalle(HttpSession session, @RequestParam Integer detIndex, @RequestParam Integer numrow,
			@RequestParam Integer value, HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		Integer cuentaContable = value;
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.getLstDesgloseDetalle().get(numrow -1).setCuentaContable(cuentaContableService.getCuentaContable(cuentaContable));
			option.append("true");
		}
		catch(Exception e){
			option.append("false");
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param nombre
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DE NOMBRE CUANDO ES MODIFICADA EN EL GRID.
	 */
	@RequestMapping(value = "/updNombreDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updNombreDetalle(HttpSession session, @RequestParam Integer detIndex, @RequestParam Integer numrow,
			@RequestParam String value, HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		String nombre = value;
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.getLstDesgloseDetalle().get(numrow -1).setNombre(nombre);
			option.append("true");
		}
		catch(Exception e){
			option.append("false");
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param detIndex
	 * @param numrows
	 * @param request
	 * @param response
	 * @return OK
	 * ELIMINA EL ROW DEL OBJETO TEMPORAL.
	 */
	@RequestMapping(value = "/updDesgloseDetalleOnDelete", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updDesgloseDetalleOnDelete(HttpSession session, @RequestParam Integer numrow,
			@RequestParam Integer detIndex, HttpServletRequest request, HttpServletResponse response) {

		StringBuilder option = new StringBuilder();
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);			
			lstDesgloseDetalleDelete.add(desglose.getLstDesgloseDetalle().get(numrow-1));
			desglose.getLstDesgloseDetalle().remove(numrow-1);
			option.append("true");
		}
		catch(Exception e){
			option.append("false");
		}
			
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return OK
	 * GUARDA EL DESGLOSE DETALLE EN EL OBJETO PRINCIPAL DE LA SOLICITUD
	 */
	@RequestMapping(value = "/saveDesgloseDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> saveDesgloseDetalle(HttpSession session, @RequestParam Integer detIndex,
		   HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.setDesgloseCompleto(true);
			option.append("true");
			esSegundaAprobacion(comprobacionAnticipoViajeDTO);
		}catch(Exception e){
			option.append("false");
		}		
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return OK
	 * CANCELA EL DESGLOSE DETALLE Y VUELVE A UN ESTADO ANTERIOR
	 */
	@RequestMapping(value = "/cancelDesgloseDetalle", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> cancelDesgloseDetalle(HttpSession session, @RequestParam Integer detIndex,
		   HttpServletRequest request, HttpServletResponse response) {		
		StringBuilder option = new StringBuilder();
		try{
			ComprobacionAnticipoViajeDesgloseDTO desglose = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(detIndex-1);
			desglose.setLstDesgloseDetalle(desgloseAnterior);
			desgloseAnterior = null;
			option.append("true");
		}catch(Exception e){
			option.append("false");
		}		
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param compAntViaje
	 * @return boolean
	 * CONSULTA SI ALGUNO DE LOS CONCEPTOS LANZA SEGUNDA VALIDACIÓN
	 */
	private boolean esSegundaAprobacion(ComprobacionAnticipoViajeDTO compAntViaje){
		boolean res = false;
		//Reset previous validations
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : compAntViaje.getComprobacionAntDesglose()){
			desglose.setSegundaAprobacion(false);
		}
		if(validaAlimentosDiario(compAntViaje))
			res = true;
		if(validaAlimentos(compAntViaje))
			res = true;
		if(validaHospedaje(compAntViaje))
			res = true;
		if(validaTransporteAereo(compAntViaje))
			res = true;
		if(validaSinComprobante(compAntViaje))
			res = true;
		if(validaRentaAuto(compAntViaje))
			res = true;
		
		return res;
	}
	
	/**
	 * @author miguelr
	 * @param compAntViaje
	 * @return boolean
	 * CONSULTA CONCEPTO ALIMENTO DIARIO LANZA SEGUNDA VALIDACIÓN
	 */
	private boolean validaAlimentosDiario(ComprobacionAnticipoViajeDTO compAntViaje) {
		boolean res = false;
		BigDecimal totalComp = new BigDecimal(0);
		BigDecimal tarifa = (compAntViaje.getViajeDestino().getEsViajeInternacional() == 1) ? viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_ALIMENTOS).getDolaresTarifa() : viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_ALIMENTOS).getPesosTarifa();
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : compAntViaje.getComprobacionAntDesglose()){
			if(desglose.getViajeConcepto() == null){
				continue;
			}
			else if(desglose.isDesglosar() && desglose.isDesgloseCompleto()){
				for(ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetalle : desglose.getLstDesgloseDetalle()){
					if(desgDetalle.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_ALIMENTOS){
						totalComp = totalComp.add(desgDetalle.getSubTotal());
					}
				}
			}
			else if(desglose.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_ALIMENTOS){
				totalComp = totalComp.add(desglose.getSubTotal());
			}					
		}
		
		if(totalComp.compareTo(tarifa) == 1 ? true : false){
			res = true;
		}
		
		return res;
	}
	
	/**
	 * @author miguelr
	 * @param compAntViaje
	 * @return boolean
	 * CONSULTA CONCEPTO ALIMENTOS LANZA SEGUNDA VALIDACIÓN
	 */
	private boolean validaAlimentos(ComprobacionAnticipoViajeDTO compAntViaje) {
		boolean res = false;
		BigDecimal tarifa = (compAntViaje.getViajeDestino().getEsViajeInternacional() == 1) ? viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_ALIMENTOS).getDolaresTarifa() : viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_ALIMENTOS).getPesosTarifa();
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : compAntViaje.getComprobacionAntDesglose()){
			BigDecimal totalComp = new BigDecimal(0);
			if(desglose.getViajeConcepto() == null){
				desglose.setSegundaAprobacion(false);
				continue;				
			}
			else if(desglose.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_ALIMENTOS){
				if(desglose.isDesglosar() && desglose.isDesgloseCompleto()){
					for(ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetalle : desglose.getLstDesgloseDetalle()){
						totalComp = totalComp.add(desgDetalle.getSubTotal());
					}
					totalComp = totalComp.divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR);
				}
				else{
					if(desglose.getNumeroPersonas() != null)
						totalComp = desglose.getSubTotal().divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR);
					else{
						totalComp = new BigDecimal(0.00);
						System.out.println("No hay num personas");
					}
				}
				
				if(totalComp.compareTo(tarifa) == 1 ? true : false){
					res = true;
					desglose.setSegundaAprobacion(true);
				}else{
					desglose.setSegundaAprobacion(false);
				}
			}
		}				
		return res;
	}
	
	/**
	 * @author miguelr
	 * @param compAntViaje
	 * @return boolean
	 * CONSULTA CONCEPTO HOSPEDAJE LANZA SEGUNDA VALIDACIÓN
	 */
	private boolean validaHospedaje(ComprobacionAnticipoViajeDTO compAntViaje) {
		boolean res = false;
		BigDecimal totalComp = new BigDecimal(0);
		BigDecimal tarifa = (compAntViaje.getViajeDestino().getEsViajeInternacional() == 1) ? viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_HOSPEDAJE).getDolaresTarifa() : viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_HOSPEDAJE).getPesosTarifa();
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : compAntViaje.getComprobacionAntDesglose()){
			if(desglose.getViajeConcepto() == null){
				desglose.setSegundaAprobacion(false);
				continue;
			}
			else if(desglose.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_HOSPEDAJE){
				if(desglose.isDesglosar() && desglose.isDesgloseCompleto()){
					for(ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetalle : desglose.getLstDesgloseDetalle()){
						totalComp = totalComp.add(desgDetalle.getSubTotal());
					}
					totalComp = totalComp.divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR);
				}
				else{
					if(desglose.getNumeroPersonas() != null)
						totalComp = desglose.getSubTotal().divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR);
					else{
						totalComp = new BigDecimal(0.00);
						System.out.println("No hay num personas");
					}
				}
				if(totalComp.compareTo(tarifa) == 1 ? true : false){
					res = true;
					desglose.setSegundaAprobacion(true);
				}else{
					desglose.setSegundaAprobacion(false);
				}
			}
		}
		return res;
	}
	
	/**
	 * @author miguelr
	 * @param compAntViaje
	 * @return boolean
	 * CONSULTA CONCEPTO TRANSPORTE AEREO LANZA SEGUNDA VALIDACIÓN
	 */
	private boolean validaTransporteAereo(ComprobacionAnticipoViajeDTO compAntViaje) {
		boolean res = false;
		BigDecimal totalComp = new BigDecimal(0);
		
		//validar control si no existe dicho concepto.
		if(viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_TRANSPORTE_AEREO) == null){
			return false;
		}
		
		BigDecimal tarifa = (compAntViaje.getViajeDestino().getEsViajeInternacional() == 1) ? viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_TRANSPORTE_AEREO).getDolaresTarifa() : viajeConceptoService.getViajeConcepto(Etiquetas.CONCEPTO_TRANSPORTE_AEREO).getPesosTarifa();
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : compAntViaje.getComprobacionAntDesglose()){
			if(desglose.getViajeConcepto() == null){
				desglose.setSegundaAprobacion(false);
				continue;
			}
			else if(desglose.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_TRANSPORTE_AEREO){
				if(desglose.isDesglosar() && desglose.isDesgloseCompleto()){
					for(ComprobacionAnticipoViajeDesgloseDetalleDTO desgDetalle : desglose.getLstDesgloseDetalle()){
						totalComp = totalComp.add(desgDetalle.getSubTotal());
					}
					totalComp = totalComp.divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR);
				}
				else{
					totalComp = desglose.getSubTotal().divide(new BigDecimal(desglose.getNumeroPersonas()),2, RoundingMode.FLOOR);
				}
				if(totalComp.compareTo(tarifa) == 1 ? true : false){
					res = true;
					desglose.setSegundaAprobacion(true);
				}else{
					desglose.setSegundaAprobacion(false);
				}
			}
		}				
		return res;
	}
	
	/**
	 * @author miguelr
	 * @param compAntViaje
	 * @return boolean
	 * CONSULTA SI DESGLOSE SIN COMPROBANTE LANZA SEGUNDA VALIDACIÓN
	 */
	private boolean validaSinComprobante(ComprobacionAnticipoViajeDTO compAntViaje) {
		boolean res = false;
		
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : compAntViaje.getComprobacionAntDesglose()){
			//Comprueba si cada desglose tiene valor
//			if(desglose.){
//				res = true;
//			}				
		}
		
		return res;
	}
	
	/**
	 * @author miguelr
	 * @param compAntViaje
	 * @return boolean
	 * CONSULTA CONCEPTO RENTA AUTO LANZA SEGUNDA VALIDACIÓN
	 */
	private boolean validaRentaAuto(ComprobacionAnticipoViajeDTO compAntViaje) {
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = Utilerias.parseDate(compAntViaje.getFechaInicio(), "dd/MM/yyyy");
			endDate = Utilerias.parseDate(compAntViaje.getFechaRegreso(), "dd/MM/yyyy");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long diffDays = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
		boolean res = false;
		for(ComprobacionAnticipoViajeDesgloseDTO desglose : compAntViaje.getComprobacionAntDesglose()){
			if(desglose.getViajeConcepto() == null){
				desglose.setSegundaAprobacion(false);
				continue;
			}
			else if(desglose.getViajeConcepto().getIdViajeConcepto() == Etiquetas.CONCEPTO_RENTA_AUTOS){
				if(diffDays == 1){
					res = true;
					desglose.setSegundaAprobacion(true);
				}else{
					desglose.setSegundaAprobacion(false);
				}
			}				
		}
		
		return res;
	}
			
	// Fin de Comprobar gastos de viaje --------------------------------------------------------------
	
	private void guardarRelacionComprobacionAnticipo(Integer comprobacion , Integer anticipo){
		ComprobacionAnticipo comprobacionAnticipo = new ComprobacionAnticipo();
		comprobacionAnticipo.setSolicitudByIdSolicitudAnticipo(new Solicitud(anticipo));
		comprobacionAnticipo.setSolicitudByIdSolicitudComprobacion(new Solicitud(comprobacion));
		comprobacionAnticipoService.createComprobacionAnticipo(comprobacionAnticipo);
	}
	
	/**
	 * @param files
	 * @param idSolicitud
	 * @param idFactura
	 * @return true si se guardó correctamente, false si hubo un error al guardar.
	 * REALIZA EL GUARDADO FÍSICO DE LOS ARCHIVOS.
	 */
	private boolean guardarArchivosFactura(List<ArchivoDTO> files, Integer idSolicitud, Integer idFactura) {

		// ruta de guardado.
		boolean guardado = false;
		
		SolicitudArchivo solicitudArchivo = new SolicitudArchivo();
		FacturaArchivo facturaArchivo = new FacturaArchivo();
		File archivo = null;
		String UID = UUID.randomUUID().toString();
		String fileName = null;		
		String ruta = Utilerias.getFilesPath() + idSolicitud + "/";
		List<TipoDocumento> tipos = tipoDocumentoService.getAllTipoDocumento();

		// revisar si existe el folder si no: se crea.
		if (files != null) {
			for (ArchivoDTO file : files) {
				
				File folder = new File(ruta);
				if(!folder.exists()){
					folder.mkdirs();
				}

				String descripcion = file.getOriginalFileName();
				String extencion = FilenameUtils.getExtension(descripcion).toUpperCase();
				fileName = UID + "_" + descripcion;
				archivo = new File(ruta + fileName);
				
				Integer idTipo = null;
				if(files.size()>1){
					// validar tipo de archivo valido
					for (TipoDocumento tipo : tipos) {
						if (tipo.getDescripcion().equals(extencion)) {
							idTipo = tipo.getTipoDocumento();
							break;
						}
					}
				}
				else{
					//Si es un solo archivo, es comprobante
					idTipo = Etiquetas.TIPO_FACTURA_COMPROBANTE;
				}

                
				// si el tipo de archivo no es valido
				if (idTipo != null) {
					try {
						// para escribir en directorio
						byte[] bytes = file.getData();
						BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(archivo));
						buffStream.write(bytes);
						buffStream.close();

						// carga del objeto para guardar en tabla.
						facturaArchivo.setActivo(Etiquetas.UNO_S);
						facturaArchivo.setArchivo(fileName);
						facturaArchivo.setCreacionFecha(new Date());
						facturaArchivo.setCreacionUsuario(idUsuario);
						facturaArchivo.setDescripcion(descripcion);
						facturaArchivo.setFactura(new Factura(idFactura));
						facturaArchivo.setTipoDocumento(new TipoDocumento(idTipo));
						
						//guardar.
						facturaArchivoService.createFacturaArchivo(facturaArchivo);
						
						//Escribir archivo en base de datos
						logger.info(etiqueta.ATENCION + etiqueta.ARCHIVO_ANEXADO);

					} catch (IOException e) {
						logger.error(e);
					}
				}

			}
			guardado = true;
		}
		return guardado;
	}

	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DE LA LOCACION CUANDO ES MODIFICADA EN EL COMBO DEL GRID.
	 */
	@RequestMapping(value = "/updLocacionDesgloseGV", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	private @ResponseBody ResponseEntity<String> updLocacionDesgloseGV(HttpSession session, @RequestParam Integer numrow,
		   @RequestParam Integer idLocacion, HttpServletRequest request, HttpServletResponse response) {
		int ccSelected = 0;
		
		if(comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(numrow).getCuentaContable() != null)
			ccSelected = comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(numrow).getCuentaContable().getIdCuentaContable();
			
		StringBuilder option = new StringBuilder();
		comprobacionAnticipoViajeDTO.getComprobacionAntDesglose().get(numrow).setIdCuentaContable(ccSelected);
		// configuraciones permitidas por usuario
		List<CuentaContable> ccPermitidas = new ArrayList<>();
		List<CuentaContable> ccPermitidasComprobacion = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacion, uConfsolicitanteService.getUsuarioConfSolByIdUsuario(comprobacionAnticipoViajeDTO.getIdUsuarioSolicita()), Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE);
		List<CuentaContable> ccPermitidasAnticipo = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacion, uConfsolicitanteService.getUsuarioConfSolByIdUsuario(comprobacionAnticipoViajeDTO.getIdUsuarioSolicita()), Etiquetas.SOLICITUD_ANTICIPO_GASTOS_VIAJE);
		
		ccPermitidas.addAll(ccPermitidasComprobacion);
		ccPermitidas.addAll(ccPermitidasAnticipo);
		
		
		option.append("<option value=\"-1\">Seleccione:</option>");
		for(CuentaContable cc : ccPermitidas){
			if(cc.getIdCuentaContable() == ccSelected){
				option.append("<option selected value=");
			}
			else{
				option.append("<option value=");
			}
			
			option.append(cc.getIdCuentaContable());
			option.append("> " + cc.getNumeroDescripcionCuentaContable() + " </option>");
		}
		
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		return new ResponseEntity<String>(option.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	private void updateAnticipoViajeDTO(String datos) {
		ObjectMapper mapper = new ObjectMapper();
		ComprobacionAnticipoViajeByIdDTO obj = new ComprobacionAnticipoViajeByIdDTO();
		try {
			obj = mapper.readValue(datos, ComprobacionAnticipoViajeByIdDTO.class);
			comprobacionAnticipoViajeDTO.setCompania(companiaService.getCompania(obj.getCompania()));
			comprobacionAnticipoViajeDTO.setFormaPago(formaPagoService.getFormaPago(obj.getFormaPago()));
			comprobacionAnticipoViajeDTO.setLocacion(locacionService.getLocacion(obj.getLocacion()));
			comprobacionAnticipoViajeDTO.setMoneda(monedaService.getMoneda(obj.getMoneda()));
			comprobacionAnticipoViajeDTO.setViajeDestino(viajeDestinoService.getViajeDestino(obj.getViajeDestino()));
			comprobacionAnticipoViajeDTO.setViajeMotivo(viajeMotivoService.getViajeMotivo(obj.getViajeMotivo()));
			comprobacionAnticipoViajeDTO.setOtroDestino(obj.getOtroDestino());
			comprobacionAnticipoViajeDTO.setOtroMotivo(obj.getOtroMotivo());
			comprobacionAnticipoViajeDTO.setFechaInicio(obj.getFechaInicio());
			comprobacionAnticipoViajeDTO.setFechaRegreso(obj.getFechaRegreso());
			comprobacionAnticipoViajeDTO.setNumeroPersonas(obj.getNumeroPersonas());
			comprobacionAnticipoViajeDTO.setImporte(obj.getImporte());
			comprobacionAnticipoViajeDTO.setPreguntaExtranjero1(obj.getPreguntaExtranjero1());
			comprobacionAnticipoViajeDTO.setPreguntaExtranjero2(obj.getPreguntaExtranjero2());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}