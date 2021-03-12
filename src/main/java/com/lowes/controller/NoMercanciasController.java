package com.lowes.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.StringEncoder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.FacturaConXML;
import com.lowes.dto.FacturaDesgloseDTO;
import com.lowes.entity.Aid;
import com.lowes.entity.AidConfiguracion;
import com.lowes.entity.CategoriaMayor;
import com.lowes.entity.CategoriaMenor;
import com.lowes.entity.Compania;
import com.lowes.entity.CuentaContable;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Factura;
import com.lowes.entity.FacturaArchivo;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoDocumento;
import com.lowes.entity.TipoFactura;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.AidConfiguracionService;
import com.lowes.service.AidService;
import com.lowes.service.CategoriaMayorService;
import com.lowes.service.CategoriaMenorService;
import com.lowes.service.CompaniaService;
import com.lowes.service.CuentaContableService;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.SolicitudService;
import com.lowes.service.TipoDocumentoService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

import antlr.StringUtils;

@Scope("session")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class NoMercanciasController {


	@Autowired
	private LocacionService locacionService;
	@Autowired
	private CuentaContableService cuentaContableService;
	@Autowired
	private AidService aidService;
	@Autowired
	private CategoriaMayorService categoriaMayor;
	@Autowired
	private CategoriaMenorService categoriaMenor;
	@Autowired
	private UsuarioConfSolicitanteService uConfsolicitanteService;
	@Autowired
	private CompaniaService companiaService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private MonedaService monedaService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private FacturaDesgloseService facturaDesgloseService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private FacturaArchivoService facturaArchivoService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private AidConfiguracionService aidConfiguracionService;
	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;
	
	private static final Logger logger = Logger.getLogger(NoMercanciasController.class);
	Etiquetas etiqueta = new Etiquetas("es");
    private Integer idUsuario = null;
	private Integer solicitanteSeleccion = null;
	
	@RequestMapping(value = "/conXML", method = RequestMethod.GET)
	public ModelAndView inicioConXML(HttpSession session, Integer id) {
		
		this.idUsuario = usuarioService.getUsuarioSesion().getIdUsuario();
		Integer usuarioSolicitud = this.idUsuario;

		HashMap<String, Object> model = new HashMap<String, Object>();
		FacturaConXML facturaConXML = new FacturaConXML();
		Integer idEstadoSolicitud = 0;

		if (id == null) {
			// destruir la sesion, creada por parametro
			session.setAttribute("solicitud", null);
		}

		// configuraciones permitidas por usuario
		List<UsuarioConfSolicitante> uconfigSol = new ArrayList<>();
		if (id != null && id > Etiquetas.CERO) {
		  Solicitud solicitudSolicitante = solicitudService.getSolicitud(id);
		  if(solicitudSolicitante != null){
			  uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(solicitudSolicitante.getUsuarioByIdUsuarioSolicita().getIdUsuario());
		      usuarioSolicitud = solicitudSolicitante.getUsuarioByIdUsuarioSolicita().getIdUsuario();
		  }else{
			  uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
		  }
		}else{
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
		}
		
		// Obtener combos filtrador por configuracion de solicitante.
		List<Locacion> lcPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol,Etiquetas.UNO,locacionService.getAllLocaciones(),usuarioSolicitud);
		List<CuentaContable> ccontable = cuentaContableService.getAllCuentaContable();
		List<CuentaContable> ccPermitidas = UtilController.getCuentasContablesPermitidasPorUsuario(uconfigSol,ccontable);

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
			  
			  //validacion de propietario de la solicitud
			// se manda la lista de autorizadores, if null : no aplica este criterio 
  		    //List<SolicitudAutorizacion> autorizadores = solicitudAutorizacionService.getAllAutorizadoresByCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE);
  		    List<SolicitudAutorizacion> autorizadores = solicitudAutorizacionService.getAllSolicitudAutorizacionBySolicitud(solicitud.getIdSolicitud());
  		    // resolver el acceso a la solicitud actual.
  			Integer puestoAP = Integer.parseInt(parametroService.getParametroByName("puestoAutorizacionAP").getValor());
  			Integer puestoConfirmacionAP = Integer.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
  		    Integer acceso = Utilerias.validaAcceso(solicitud, Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML,usuarioService.getUsuarioSesion(), autorizadores, puestoAP, puestoConfirmacionAP);
  		    if(acceso == Etiquetas.NO_VISUALIZAR){
  			      return new ModelAndView("redirect:/");
  		    }else if(acceso == Etiquetas.VISUALIZAR){
  		    		idEstadoSolicitud = Etiquetas.SOLO_VISUALIZACION;
  		    	}
			}
		}

		if (solicitud != null && solicitud.getFacturas().size() > Etiquetas.CERO) {

			// si la solicitud es válida entonces crear una variable de sesión
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
				SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
				
				try {
					fechaString = sdfOut.format(sdfIn.parse(factura.getFechaFactura().toString()));
					facturaConXML.setFecha_factura(fechaString);
				} catch (ParseException e) {
					
				}
				
				// el concepto se des-escapa para mandar a vista los caracteres especiales correctamente.
				facturaConXML.setConcepto(StringEscapeUtils.unescapeJava(solicitud.getConceptoGasto()));
                
				
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
				facturaConXML.setStrSubTotal(factura.getSubtotal() !=null ? factura.getSubtotal().toString() : "");
				facturaConXML.setTasaIVA(factura.getPorcentajeIva());
				
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
						
						fdd.setConcepto(StringEscapeUtils.unescapeJava(fd.getConcepto()));
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
		
		//revisar si se trata de una modificaciï¿½n para activar mensaje en la vista
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

		return new ModelAndView("conXML", model);
	}

	
	@RequestMapping(value = "/sinXML", method = RequestMethod.GET)
	public ModelAndView inicioSinXML(HttpSession session, Integer id){
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		FacturaConXML facturaConXML = new FacturaConXML();
		Integer idEstadoSolicitud = 0;
		this.idUsuario = usuarioService.getUsuarioSesion().getIdUsuario();
		Integer usuarioSolicitud = this.idUsuario;
		CuentaContable ccNoComprobante = new CuentaContable();


		if (id == null) {
			// destruir la sesion, creada por parametro
			session.setAttribute("solicitud", null);
		}

		// configuraciones permitidas por usuario
		List<UsuarioConfSolicitante> uconfigSol = new ArrayList<>();
		if (id != null && id > Etiquetas.CERO) {
		  Solicitud solicitudSolicitante = solicitudService.getSolicitud(id);
		  if(solicitudSolicitante != null){
			  uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(solicitudSolicitante.getUsuarioByIdUsuarioSolicita().getIdUsuario());
		      usuarioSolicitud = solicitudSolicitante.getUsuarioByIdUsuarioSolicita().getIdUsuario();
		  }else{
			  uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
		  }
		}else{
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
		}

		// Obtener combos filtrador por configuracion de solicitante.
		List<Locacion> lcPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol,Etiquetas.DOS,locacionService.getAllLocaciones(),usuarioSolicitud);
		List<CuentaContable> ccontable = cuentaContableService.getAllCuentaContable();
		List<CuentaContable> ccPermitidas = UtilController.getCuentasContablesPermitidasPorUsuario(uconfigSol,ccontable);

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
				facturaConXML.setIdSolicitudSession(id);
			}
		}

		if (solicitud != null && solicitud.getFacturas().size() > Etiquetas.CERO) {

			// si la solicitud es valida entonces crear una variable de sesion
			// para el update.
			session.setAttribute("solicitud", solicitud);

			factura = solicitud.getFacturas().get(Etiquetas.CERO);
			if (factura != null && solicitud.getFacturas().size() > Etiquetas.CERO) {
				
				facturaConXML.setCompania(factura.getCompaniaByIdCompania());
				facturaConXML.setProveedor(factura.getProveedor());
				facturaConXML.setRfcEmisor(factura.getProveedor().getRfc());
				facturaConXML.setFolioFiscal(factura.getFolioFiscal());
				facturaConXML.setFolio(factura.getFactura());
				facturaConXML.setSerie(factura.getSerieFactura());
				
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
				SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy");
				
				try {
					fechaString = sdfOut.format(sdfIn.parse(factura.getFechaFactura().toString()));
					facturaConXML.setFecha_factura(fechaString);
				} catch (ParseException e) {
					
				}
				
				facturaConXML.setConcepto(StringEscapeUtils.unescapeJava(solicitud.getConceptoGasto()));
				
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
				facturaConXML.setStrSubTotal(factura.getSubtotal() !=null ? factura.getSubtotal().toString() : "");
				facturaConXML.setTasaIVA(factura.getPorcentajeIva());

				
				facturaConXML.setMoneda(factura.getMoneda());
				if (solicitud.getUsuarioByIdUsuario().getIdUsuario() != solicitud.getUsuarioByIdUsuarioSolicita()
						.getIdUsuario()) {
					facturaConXML.setSolicitante(true);
					facturaConXML.setIdSolicitanteJefe(solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario());
				} else {
					facturaConXML.setSolicitante(false);
				}

				facturasDesglose = factura.getFacturaDesgloses();
				facturasDesglose = Utilerias.getSortFacturasDesglose(facturasDesglose);
				
				
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
						fdd.setConcepto(StringEscapeUtils.unescapeJava(fd.getConcepto()));
						fdd.setCuentaContable(fd.getCuentaContable());
						fdd.setLocacion(fd.getLocacion());
						fdd.setStrSubTotal(fd.getSubtotal().toString());
						facturasDesgloseDTO.add(fdd);
					}
				}
			}
			// get cuenta contable from table
			ccNoComprobante = cuentaContableService.getCuentaContable(factura.getFacturaDesgloses().get(0).getCuentaContable().getIdCuentaContable());
		} else {
			// mensaje de solicitud no valida a la vista.
			facturaConXML.setIdSolicitudSession(Etiquetas.CERO);
			
			//Set cuenta contable no deducible para documentos sin comprobante fiscal
			String cuentaContable = parametroService.getParametroByName("ccNoDeducible").getValor();
			Integer ccID = UtilController.getIDByCuentaContable(cuentaContable , cuentaContableService.getAllCuentaContable());
			if(ccID != null){
				ccNoComprobante = cuentaContableService.getCuentaContable(ccID);
			}
		}

		facturaConXML.setFacturaDesgloseList(facturasDesgloseDTO);
		
		//revisar si se trata de una modificaciï¿½n para activar mensaje en la vista
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
		
		model.put("ccNoComprobante", ccNoComprobante);		
		model.put("facturaConXML", facturaConXML);
		model.put("lcPermitidas", lcPermitidas);
		model.put("ccPermitidas", ccPermitidas);
		model.put("listAids", listAids);
		model.put("listCatMayor", listCatMayor);
		model.put("listCatMenor", listCatMenor);
		model.put("lstCompanias", lstCompanias);
		model.put("lstProveedores", lstProveedores);
		model.put("lstMoneda", lstMoneda);
		model.put("lstUsuariosJefe", lstUsuariosJefe);
		model.put("estadoSolicitud", idEstadoSolicitud);
		model.put("solicitudArchivoList", solicitudArchivoList);
		model.put("tipoSolicitud",Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML);
		model.put("isSolicitante", usuario.getEspecificaSolicitante());
		model.put("usuarioSession", usuarioSession);

		return new ModelAndView("conXML", model);
	}
	
	@RequestMapping(value = "/saveFacturaXML", method = RequestMethod.POST)
	public ModelAndView saveFacturaXML(@ModelAttribute("facturaConXML") FacturaConXML facturaConXML,
			HttpSession session, @RequestParam("file") MultipartFile[] files) {
		
		// se crea el objeto de solicitud, factura y desglose
		Solicitud solicitudSaveOrUpdate = new Solicitud();
		Factura facturaSaveOrUpdate = new Factura();
		FacturaDesglose fdesgloseSaveOrUpdate = new FacturaDesglose();
		Integer idSolicitudNueva = null;

		// saber si hay en session una solicitud
		Solicitud solicitudActual = (Solicitud) session.getAttribute("solicitud");
		
		if (solicitudActual != null && solicitudActual.getIdSolicitud() > Etiquetas.CERO) {
		
	    // 1 - ACTUALIZAR la solicitud
		/* TODO  quitar el llamado a bd*/	
		Solicitud solicitudSaveOrUpdateAux = solicitudService.getSolicitud(solicitudActual.getIdSolicitud());

			// se cargan los datos de la solicitud
			solicitudSaveOrUpdate = loadSolicitud(facturaConXML);
			solicitudSaveOrUpdate.setCreacionFecha(solicitudSaveOrUpdateAux.getCreacionFecha());
			solicitudSaveOrUpdate.setCreacionUsuario(solicitudSaveOrUpdateAux.getCreacionUsuario());
			solicitudSaveOrUpdate.setIdSolicitud(solicitudActual.getIdSolicitud());
			solicitudSaveOrUpdate.setModificacionFecha(new Date());
			solicitudSaveOrUpdate.setModificacionUsuario(idUsuario);
			// solicitud service actualizar.
			solicitudSaveOrUpdate.setEstadoSolicitud(solicitudSaveOrUpdateAux.getEstadoSolicitud());
			//solicitudSaveOrUpdate.setConceptoGasto("hola");
			solicitudSaveOrUpdate = solicitudService.updateSolicitud(solicitudSaveOrUpdate);
			idSolicitudNueva = solicitudSaveOrUpdate.getIdSolicitud();
			
			// si se guardo y por lo tanto el id' que regreso es mayor a cero entonces cargamos los datos de factura.
			if(idSolicitudNueva > Etiquetas.CERO){
				facturaSaveOrUpdate = loadFactura(idSolicitudNueva, facturaConXML);
				facturaSaveOrUpdate.setActivo(Etiquetas.UNO_S);
				facturaSaveOrUpdate.setCreacionUsuario(solicitudSaveOrUpdateAux.getFacturas().get(Etiquetas.CERO).getCreacionUsuario());
				facturaSaveOrUpdate.setCreacionFecha(solicitudSaveOrUpdateAux.getFacturas().get(Etiquetas.CERO).getCreacionFecha());
				facturaSaveOrUpdate.setModificacionFecha(new Date());
				facturaSaveOrUpdate.setModificacionUsuario(idUsuario);
				Integer idFactura  = solicitudSaveOrUpdateAux.getFacturas().get(Etiquetas.CERO).getIdFactura();
				facturaSaveOrUpdate.setIdFactura(idFactura);
				
				//ACTUALIZAR: llamada al servicio con el objeto cargado.
				facturaSaveOrUpdate = facturaService.updateFactura(facturaSaveOrUpdate);
				
				// actualizar ARCHIVOS
				if(files != null && files.length > Etiquetas.CERO){
				  guardarArchivosFactura(files, idSolicitudNueva, idFactura);
				}
				
				// eliminar desgloses guardados y reemplazarlos.
				facturaDesgloseService.deleteAllByIdFactura(idFactura);

				// si la factura tiene desgloses se guardan.
				if (idFactura > Etiquetas.CERO && facturaConXML.getFacturaDesgloseList() != null
						&& facturaConXML.getFacturaDesgloseList().size() > Etiquetas.CERO) {
					for (FacturaDesgloseDTO fdto : facturaConXML.getFacturaDesgloseList()) {
						//cargar datos del desglose si tienen info: evitar nulos 
						if(fdto.getStrSubTotal() != null && fdto.getStrSubTotal().isEmpty() == false && fdto.getCuentaContable() != null){
						  fdesgloseSaveOrUpdate = loadFacturaDesglose(idFactura, fdto, facturaConXML);
						  // guardar desglose
					      facturaDesgloseService.createFacturaDesglose(fdesgloseSaveOrUpdate);
						}
					}
				}
				
			}
			
			// crear variable de sesion para el mensaje de actualzaciï¿½n
			  Integer updt = Etiquetas.UNO;
			  session.setAttribute("actualizacion", updt );
			
		} else {
			/* ******* CREAR INFO POR PRIMERA VEZ ********* */
			// se cargan los datos de la solicitud
			solicitudSaveOrUpdate = loadSolicitud(facturaConXML);
			
		    //campos control
			solicitudSaveOrUpdate.setCreacionFecha(new Date());
			solicitudSaveOrUpdate.setCreacionUsuario(idUsuario);
			solicitudSaveOrUpdate.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.UNO));

			// guardar factura y desglose por primera vez.
			idSolicitudNueva = solicitudService.createSolicitud(solicitudSaveOrUpdate);
			
			// si se guardo y por lo tanto el id' que regreso es mayor a cero entonces cargamos los datos de factura.
			if(idSolicitudNueva > Etiquetas.CERO){
				facturaSaveOrUpdate = loadFactura(idSolicitudNueva, facturaConXML);
				facturaSaveOrUpdate.setActivo(Etiquetas.UNO_S);
				facturaSaveOrUpdate.setCreacionUsuario(idUsuario);
				facturaSaveOrUpdate.setCreacionFecha(new Date());
				}
			
			// CREAR factura 
			Integer idFactura = facturaService.createFactura(facturaSaveOrUpdate);
			
			// GUARDAR ARCHIVOS
			guardarArchivosFactura(files, idSolicitudNueva, idFactura);

			// si la factura tiene desgloses se guardan.
			if (idFactura > Etiquetas.CERO && facturaConXML.getFacturaDesgloseList() != null
					&& facturaConXML.getFacturaDesgloseList().size() > Etiquetas.CERO) {
				for (FacturaDesgloseDTO fdto : facturaConXML.getFacturaDesgloseList()) {
					//evitar insertar desgloses nulos
					if(fdto != null && fdto.getStrSubTotal() != null && fdto.getConcepto() != null && fdto.getLocacion() != null){
						//cargar datos del desglose 
						fdesgloseSaveOrUpdate = loadFacturaDesglose(idFactura, fdto, facturaConXML);
						// guardar desglose
						facturaDesgloseService.createFacturaDesglose(fdesgloseSaveOrUpdate);
					}
				}
			}
			
			// crear variable de sesion para el mensaje de actualzaciï¿½n
			  Integer newrow = Etiquetas.UNO;
			  session.setAttribute("creacion", newrow );

		} //fin.else
         
		if(facturaConXML.getTipoSolicitud() == Etiquetas.UNO){
		  return new ModelAndView("redirect:conXML?id="+idSolicitudNueva);
		}else{
		  return new ModelAndView("redirect:sinXML?id="+idSolicitudNueva);
		}
	}
	
	

	private Solicitud loadSolicitud(FacturaConXML facturaConXML) {

		Solicitud sol = new Solicitud();

		sol.setActivo(Etiquetas.UNO_S);

		sol.setMoneda(facturaConXML.getMoneda());
		sol.setCompania(facturaConXML.getCompania());
		
		sol.setFormaPago(new FormaPago(Integer.parseInt(parametroService.getParametroByName("formaPagoTransferencia").getValor())));
		
		// definir tipo de solicitud
		sol.setTipoSolicitud(new TipoSolicitud(facturaConXML.getTipoSolicitud()));
		sol.setMontoTotal(Utilerias.convertStringToBigDecimal(facturaConXML.getStrTotal()));
		sol.setUsuarioByIdUsuario(new Usuario(idUsuario));

		if (facturaConXML.isSolicitante()) {
			sol.setUsuarioByIdUsuarioSolicita(new Usuario(facturaConXML.getIdSolicitanteJefe()));
			sol.setLocacion(usuarioService.getUsuario(facturaConXML.getIdSolicitanteJefe()).getLocacion());
		} else {
			sol.setUsuarioByIdUsuarioSolicita(new Usuario(idUsuario));
			sol.setLocacion(usuarioService.getUsuario(idUsuario).getLocacion());
		}
		
		if(facturaConXML.getConcepto().length() >= 500){
			sol.setConceptoGasto(StringEscapeUtils.escapeJava(facturaConXML.getConcepto()).substring(0, 500));
			
		}else{
			sol.setConceptoGasto(facturaConXML.getConcepto());
		}

		if (facturaConXML.isTrack_asset()) {
			sol.setTrackAsset(Etiquetas.UNO_S);
		} else {
			sol.setTrackAsset(Etiquetas.CERO_S);
		}

		return sol;
	}
	
	
	private Factura loadFactura(Integer idSolicitud, FacturaConXML facturaConXML){
		
		Factura factura = new Factura();
		 
		factura.setSolicitud(new Solicitud(idSolicitud));
		factura.setMoneda(facturaConXML.getMoneda());
		factura.setFactura(facturaConXML.getFolio());
		factura.setCompaniaByIdCompania(facturaConXML.getCompania());
		

		if(facturaConXML.getConcepto().length() >= 500){
			factura.setConceptoGasto(StringEscapeUtils.escapeJava(facturaConXML.getConcepto()).substring(0, 500));


		}else{
			factura.setConceptoGasto(facturaConXML.getConcepto());
		}
		
		if (facturaConXML.isTrack_asset()) {
			factura.setCompaniaByIdCompaniaLibroContable(facturaConXML.getId_compania_libro_contable());
			factura.setPar(facturaConXML.getPar());
			factura.setTrackAsset(Etiquetas.UNO_S);
		}else{
			factura.setTrackAsset(Etiquetas.CERO_S);
		}

		factura.setProveedor(facturaConXML.getProveedor());
		factura.setSerieFactura(facturaConXML.getSerie());
		factura.setFolioFiscal(facturaConXML.getFolioFiscal());
		if(facturaConXML.getTipoFactura() == null){
			// tipo de factura standar
			factura.setTipoFactura(new TipoFactura(Etiquetas.UNO));
		}else{
			factura.setTipoFactura(new TipoFactura(facturaConXML.getTipoFactura()));
		}
		
		
		try {
			factura.setFechaFactura(Utilerias.parseDate(facturaConXML.getFecha_factura(), "dd/MM/yyyy"));
		} catch (ParseException e) {
	
		}
		
		factura.setSubtotal(Utilerias.convertStringToBigDecimal(facturaConXML.getStrSubTotal()));
		factura.setTotal(Utilerias.convertStringToBigDecimal(facturaConXML.getStrTotal()));

		if (facturaConXML.getStrIeps() != null) {
			factura.setIeps(Utilerias.convertStringToBigDecimal(facturaConXML.getStrIeps()));
		}
		if (facturaConXML.getStrIva() != null) {
			factura.setIva(Utilerias.convertStringToBigDecimal(facturaConXML.getStrIva()));
		}
		
		factura.setPorcentajeIva(facturaConXML.getTasaIVA());

		// retenciones
		if (facturaConXML.isConRetenciones()) {
			factura.setConRetenciones(Etiquetas.UNO_S);
			factura.setIvaRetenido(Utilerias.convertStringToBigDecimal(facturaConXML.getStrIva_retenido()));
			factura.setIsrRetenido(Utilerias.convertStringToBigDecimal(facturaConXML.getStrIsr_retenido()));
		} else {
			factura.setConRetenciones(Etiquetas.CERO_S);
		}

		return factura;
	}
	
	
	private FacturaDesglose loadFacturaDesglose(Integer idFactura, FacturaDesgloseDTO fdto, FacturaConXML facturaConXML){
		
		FacturaDesglose fdesglose = new FacturaDesglose();
		
		fdesglose.setFactura(new Factura(idFactura));
		fdesglose.setLocacion(fdto.getLocacion());
		fdesglose.setCuentaContable(fdto.getCuentaContable());
		
		if(fdto.getConcepto().length() >= 500){
			fdesglose.setConcepto(fdto.getConcepto().substring(0,500));
		}else{
			fdesglose.setConcepto(fdto.getConcepto());
		}
		
		
		fdesglose.setSubtotal(Utilerias.convertStringToBigDecimal(fdto.getStrSubTotal()));
		fdesglose.setCreacionFecha(new Date());
		fdesglose.setActivo(Etiquetas.UNO_S);

		if (facturaConXML.isTrack_asset()) {

			if (fdto.getAid() != null && fdto.getAid().getIdAid() > Etiquetas.CERO) {
				fdesglose.setAid(fdto.getAid());
			}

			if (fdto.getCategoriaMayor() != null
					&& fdto.getCategoriaMayor().getIdCategoriaMayor() > Etiquetas.CERO) {
				fdesglose.setCategoriaMayor(fdto.getCategoriaMayor());
			}

			if (fdto.getCategoriaMenor() != null
					&& fdto.getCategoriaMenor().getIdCategoriaMenor() > Etiquetas.CERO) {
				fdesglose.setCategoriaMenor(fdto.getCategoriaMenor());
			}
		}
		
		return fdesglose;
	}
	
	
	@RequestMapping(value = "/addRowConXML", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> addRow(HttpSession session, @RequestParam Integer numrows,@RequestParam Integer tipoSolicitud,@RequestParam Integer idSolicitud,@RequestParam Integer idSolicitante,
			@RequestParam String concepto, HttpServletRequest request, HttpServletResponse response) {

		String json = null;
		StringBuilder row2 = new StringBuilder();
        
		List<UsuarioConfSolicitante> uconfigSol = new ArrayList<>();
		Integer idLocacionUsuario = null;
		if(idSolicitante != null && idSolicitante != -1){
		// configuraciones permitidas por solicitante seleccionado
		uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idSolicitante);
		idLocacionUsuario = usuarioService.getUsuario(idSolicitante).getLocacion().getIdLocacion();
		solicitanteSeleccion = idSolicitante;
		}else{
		// configuraciones permitidas por usuario en sesion
		uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
		idLocacionUsuario = usuarioService.getUsuario(idUsuario).getLocacion().getIdLocacion();
		solicitanteSeleccion = this.idUsuario;
		}
		
		// filtrar locaciones por tipo de solicitud.
		// Obtener combos filtrador por configuracion de solicitante.
		List<Locacion> lcPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol,tipoSolicitud,locacionService.getAllLocaciones(),solicitanteSeleccion);


		// OBTENER AID'S
		List<Aid> listAids = aidService.getAllAid();


		//String num = String.valueOf(numrows);
		//numero aleatoreo para serializar los inputs.
		String num = Utilerias.getRandomNum();
		
		Integer numLinea = numrows + 1;

		row2.append("<tr class=\"odd gradeX\">");
		row2.append("<td align=\"center\"><div class=\"linea\">" + numLinea + "</div></td>");

		// subtotal
		row2.append("<td><input step=\"any\" onblur=\"sumSbt()\" id=\"facturaDesgloseList" + num
				+ ".strSubTotal\" name=\"facturaDesgloseList[" + num
				+ "].strSubTotal\" class=\"form-control subtotales currencyFormat sbtGrid\" value=\"\" type=\"text\"></td>");

		// locaciones
		String accionSeleccion = "";
		if(idSolicitud != Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
			accionSeleccion =  "onChange=\"actualizarCuentas(this.id)\"";
		}
		row2.append("<td><select "+accionSeleccion+" id=\"facturaDesgloseList" + num + ".locacion.idLocacion\" name=\"facturaDesgloseList["
				+ num + "].locacion.idLocacion\" class=\"form-control locaciones\">");
		row2.append("<option value=\"-1\">Seleccione:</option>");
		for (Locacion loc : lcPermitidas) {
			
			if(idSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML && loc.getIdLocacion() == idLocacionUsuario){
				row2.append("<option selected  value=\"");
			}else{
				row2.append("<option value=\"");
			}
			row2.append(String.valueOf(loc.getIdLocacion()));
			row2.append("\"> " + loc.getNumeroDescripcionLocacion() + " </option>");
		}
		row2.append("</select>");
		row2.append("</td>");
		
		// si el tipo de solicitud es sin xml no mercancias, se cargan las cuentas contables por 
		//locacion de usuario seleccionada automatica.
		List<CuentaContable> ccPermitidasPorUsuario = null;
		if(idSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
			ccPermitidasPorUsuario = UtilController.getCuentasContablesPermitidasPorLocacion(idLocacionUsuario, uConfsolicitanteService.getUsuarioConfSolByIdUsuario(solicitanteSeleccion),idSolicitud);
		}
		// cuenta contables
		row2.append("<td><select id=\"facturaDesgloseList" + num
				+ ".cuentaContable.idCuentaContable\" name=\"facturaDesgloseList[" + num
				+ "].cuentaContable.idCuentaContable\" class=\"form-control ccontable\">");
		row2.append("<option value=\"-1\">Seleccione:</option>");
		
		// sin xml cuentas precargadas.
		if(idSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
			 Integer idCuentaContableNoND = UtilController.getIDByCuentaContable(parametroService.getParametroByName("ccNoDeducible").getValor(), cuentaContableService.getAllCuentaContable());
			 CuentaContable cc = new CuentaContable();
			 if(idCuentaContableNoND != null){
				 cc = cuentaContableService.getCuentaContable(idCuentaContableNoND);
				 row2.append("<option selected value=\""+cc.getIdCuentaContable()+"\"> "+cc.getNumeroDescripcionCuentaContable()+" </option>");
			 }else{
				 row2.append("<option selected value=\"-1\"> "+etiqueta.CCNODED_NO_CONFIGURADA+" </option>");
			 }
		}
		row2.append("</select>");
		row2.append("</td>");

		// concepto
		row2.append("<td><input maxlength=\"500\" id=\"facturaDesgloseList" + num + ".concepto\" name=\"facturaDesgloseList[" + num
				+ "].concepto\" class=\"form-control conceptogrid\" value=\""+concepto+"\" type=\"text\"></td>");

		
		if(idSolicitud == 1){
			
			accionSeleccion =  "onChange=\"alSeleccionarAID(this.id)\"";
			
			// aids
			row2.append("<td><select "+accionSeleccion+" id=\"facturaDesgloseList" + num + ".aid.idAid\" name=\"facturaDesgloseList[" + num
					+ "].aid.idAid\" class=\"form-control aidAll trackAsset\">");
			row2.append("<option value=\"-1\">Seleccione:</option>");
			for (Aid aid : listAids) {
				row2.append("<option value=\"");
				row2.append(String.valueOf(aid.getIdAid()));
				row2.append("\"> " + aid.getNumeroDescripcionAid() + " </option>");
			}
			row2.append("</select>");
			row2.append("</td>");
	
			// categoria mayor
			row2.append("<td><select id=\"facturaDesgloseList" + num
					+ ".categoriaMayor.idCategoriaMayor\" name=\"facturaDesgloseList[" + num
					+ "].categoriaMayor.idCategoriaMayor\" class=\"form-control trackAsset\">");
			row2.append("<option value=\"-1\">Seleccione:</option>");
			row2.append("</select>");
			row2.append("</td>");
	
			// categoria menor
			row2.append("<td><select id=\"facturaDesgloseList" + num
					+ ".categoriaMenor.idCategoriaMenor\" name=\"facturaDesgloseList[" + num
					+ "].categoriaMenor.idCategoriaMenor\" class=\"form-control trackAsset\">");
			row2.append("<option value=\"-1\">Seleccione:</option>");
			row2.append("</select>");
			row2.append("</td>");
			
			
		}
		// remover
		row2.append("<td><button type=\"button\" class=\"btn btn-danger removerFila\">Remover</button></td>");
		row2.append("</tr>");

		ObjectMapper map = new ObjectMapper();
		if (!row2.toString().isEmpty()) {
			try {
				json = map.writeValueAsString(row2.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);

	}
	
	
	@RequestMapping(value = "/getRFC", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getRFC(HttpSession session, @RequestParam Integer idProveedor,
			HttpServletRequest request, HttpServletResponse response) {
		
		String result = null;
		String json = null;
		String rfc = "false";
		
		Proveedor proveedor = proveedorService.getProveedor(idProveedor);
		if(proveedor != null){
			rfc = proveedor.getRfc();
		}
		result = rfc;
		
		ObjectMapper map = new ObjectMapper();
		if (!result.toString().isEmpty()) {
			try {
				json = map.writeValueAsString(result.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	}

	


	
	

	
	
	  // metodo ajax para el cambio de estatus a cancelada.
	  @RequestMapping(value = "/cancelarSolicitud", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody
	    ResponseEntity<String> cancelarSolicitud(HttpSession session, @RequestParam Integer idSolicitud, HttpServletRequest request, HttpServletResponse response) {
		  
		  String json = null;
	        HashMap<String, String> result = new HashMap<String, String>();
	        
		  if(idSolicitud != null && idSolicitud > Etiquetas.CERO){
		      Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);
		      
		      solicitud.setIdEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_CANCELADA);
		      solicitud.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_CANCELADA));
		      
		      Solicitud solicitudAux = null;
		      solicitudAux = solicitudService.updateSolicitud(solicitud);
		      
		      if(solicitudAux != null){
				  result.put("resultado", "true");
		      }else{
				  result.put("resultado", "false");
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
	  
	  

	  
	private boolean guardarArchivosFactura(MultipartFile[] files, Integer idSolicitud, Integer idFactura) {

		// ruta de guardado.
		boolean guardado = false;

		File archivo = null;
		String UID = UUID.randomUUID().toString();
		String fileName = null;
		FacturaArchivo facturaArchivo = new FacturaArchivo();
		String ruta = Utilerias.getFilesPath() + idSolicitud + "/";
		List<TipoDocumento> tipos = tipoDocumentoService.getAllTipoDocumento();

		// revisar si existe el folder si no: se crea.
		if (files != null && files.length > Etiquetas.CERO) {
			for (MultipartFile file : files) {
				
				File folder = new File(ruta);
				if(!folder.exists()){
					folder.mkdirs();
				}

				String descripcion = file.getOriginalFilename();
				String extencion = FilenameUtils.getExtension(descripcion);
				fileName = UID+"."+extencion;
				extencion = extencion.toUpperCase();
				archivo = new File(ruta + fileName);

				// validar tipo de archivo valido
				Integer idTipo = null;
				for (TipoDocumento tipo : tipos) {
					if (tipo.getDescripcion().equals(extencion)) {
						idTipo = tipo.getTipoDocumento();
						break;
					}
				}
                
				// si el tipo de archivo no es valido
				if (idTipo != null) {
					try {
						// para escribir en directorio
						byte[] bytes = file.getBytes();
						BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(archivo));
						buffStream.write(bytes);
						buffStream.close();

						// carga del objeto para guardar en tabla.
						facturaArchivo.setActivo(Etiquetas.UNO_S);
						facturaArchivo.setArchivo(fileName);
						facturaArchivo.setCreacionFecha(new Date());
						facturaArchivo.setCreacionUsuario(-1);
						facturaArchivo.setDescripcion(descripcion);
						facturaArchivo.setFactura(new Factura(idFactura));
						facturaArchivo.setTipoDocumento(new TipoDocumento(idTipo));
						
						//guardar.
						facturaArchivoService.createFacturaArchivo(facturaArchivo);

					} catch (IOException e) {
						logger.error(e);
					}
				}

			}
			guardado = true;
		}
		
		return guardado;
	}
	
	
	// metodo ajax para el cambio de estatus a cancelada.
		  @RequestMapping(value = "/seleccionLocacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		    public @ResponseBody
		    ResponseEntity<String> seleccionLocacion(HttpSession session, @RequestParam Integer idLocacion,@RequestParam String idElemento,@RequestParam Integer tipoSolicitud, HttpServletRequest request, HttpServletResponse response) {
			  
			    String json = null;
		        HashMap<String, String> result = new HashMap<String, String>();
		        
		        //para evirar nulo cuando no sea establecido un solicitante ya sea por session o por seleccion
		        if(solicitanteSeleccion == null){
		        	solicitanteSeleccion = usuarioService.getUsuarioSesion().getIdUsuario();
		        }
		        
				List<UsuarioConfSolicitante> uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(solicitanteSeleccion);
				
		        String str = idElemento;
		        str = str.replaceAll("[^-?0-9]+", " ");
		        String idCCElementDOM = "facturaDesgloseList@.cuentaContable.idCuentaContable";
		        if (tipoSolicitud == Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO_GASTOS_VIAJE) idCCElementDOM = "comprobacionAntDesglose@.cuentaContable.idCuentaContable";
		        
		        idCCElementDOM = idCCElementDOM.replace("@", str);
		        idCCElementDOM = idCCElementDOM.replaceAll("\\s+","");
			    
			    result.put("options", UtilController.getCuentasContablesHTML(idLocacion, uconfigSol,tipoSolicitud));
			    result.put("idElemento", idCCElementDOM);
			    
			  
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
		  
		  // ----------------------------------------------------------------------------------------
		  
			// metodo ajax para el cambio de estatus a cancelada.
		  @RequestMapping(value = "/seleccionAID", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		    public @ResponseBody
		    ResponseEntity<String> seleccionAID(HttpSession session, @RequestParam Integer idAid,@RequestParam String idElemento, HttpServletRequest request, HttpServletResponse response) {
			  
			    String json = null;
		        HashMap<String, String> result = new HashMap<String, String>();
		        
		        StringBuilder row = new StringBuilder();
		        StringBuilder row2 = new StringBuilder();
		        
		        //lista de conficuraciones
		        List<AidConfiguracion> configs = aidConfiguracionService.getAllAidConfiguracion();
		        List<AidConfiguracion> configsAux = new ArrayList<>();
		        
		        // filtrar por el AID seleccionado
		        if(configs != null){
		        	for(AidConfiguracion conf : configs){
			        	if(conf.getAid().getIdAid() == idAid){
			        	  configsAux.add(conf);
			        	}
			        }	
		        }
		        
		        // para identificar el target del elemento en DOM para agregar las opciones
		        // de categoria mayor y menor dependiento de la seleccion del AID
		        String str = idElemento;
		        str = str.replaceAll("[^-?0-9]+", " ");
		        String idCatMayor = "facturaDesgloseList@.categoriaMayor.idCategoriaMayor";
		        String idCatMenor = "facturaDesgloseList@.categoriaMenor.idCategoriaMenor";
		        
		        idCatMenor = idCatMenor.replace("@", str);
		        idCatMenor = idCatMenor.replaceAll("\\s+","");
		        
		        idCatMayor = idCatMayor.replace("@", str);
		        idCatMayor = idCatMayor.replaceAll("\\s+","");
		        
		        
				// generacion de HTML para desplegar en los selects.
				if (configsAux != null && configsAux.isEmpty() == false) {
					
					// CAT Mayor
					row.append("<option value=\"-1\">Seleccione:</option>");
					for (AidConfiguracion conf : configsAux) {
						row.append("<option value=\"" + conf.getCategoriaMayor().getIdCategoriaMayor() + "\">"
								+ conf.getCategoriaMayor().getDescripcion() + "</option>");
					}
					
					// CAT Menor
					row2.append("<option value=\"-1\">Seleccione:</option>");
					for (AidConfiguracion conf : configsAux) {
						row2.append("<option value=\"" + conf.getCategoriaMenor().getIdCategoriaMenor() + "\">"
								+ conf.getCategoriaMenor().getDescripcion() + "</option>");
					}
				}
			    
			    result.put("optionsMayor", row.toString());
			    result.put("optionsMenor", row2.toString());
			    result.put("idCatMenor", idCatMenor);
			    result.put("idCatMayor", idCatMayor);
			    
			  
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
		  
		  
		  @RequestMapping(value = "/modalNoMercancias", method = RequestMethod.GET)
			public ModelAndView modalNoMercancias(HttpSession session, Integer id) {
			  this.idUsuario = usuarioService.getUsuarioSesion().getIdUsuario();

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
				List<Locacion> lcPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol,Etiquetas.UNO,locacionService.getAllLocaciones(),this.idUsuario);
				List<CuentaContable> ccontable = cuentaContableService.getAllCuentaContable();
				List<CuentaContable> ccPermitidas = UtilController.getCuentasContablesPermitidasPorUsuario(uconfigSol,ccontable);

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
					  
					//validacion de propietario de la solicitud
						// se manda la lista de autorizadores, if null : no aplica este criterio 
			  		    List<SolicitudAutorizacion> autorizadores = solicitudAutorizacionService.getAllAutorizadoresByCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE);
			  		    // resolver el acceso a la solicitud actual.
			  		    Integer puestoAP = Integer.parseInt(parametroService.getParametroByName("puestoAutorizacionAP").getValor());
			  			Integer puestoConfirmacionAP = Integer.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
			  		    Integer acceso = Utilerias.validaAcceso(solicitud, Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML,usuarioService.getUsuarioSesion(), autorizadores, puestoAP, puestoConfirmacionAP);
			  		    if(acceso == Etiquetas.NO_VISUALIZAR){
			  			      return new ModelAndView("redirect:/");
			  		    }else if(acceso == Etiquetas.VISUALIZAR){
			  		    		idEstadoSolicitud = Etiquetas.SOLO_VISUALIZACION;
			  		    	}
			  		    
					  
					}
				}

				if (solicitud != null && solicitud.getFacturas().size() > Etiquetas.CERO) {

					// si la solicitud es válida entonces crear una variable de sesión
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
						facturasDesglose = Utilerias.getSortFacturasDesglose(facturasDesglose);
						
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
								fdd.setConcepto(StringEscapeUtils.unescapeJava(fd.getConcepto()));
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
				
				//revisar si se trata de una modificaciï¿½n para activar mensaje en la vista
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

				return new ModelAndView("modalNoMercancias", model);
		  }
	  
		  
		

}
