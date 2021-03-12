package com.lowes.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.ArchivoDTO;
import com.lowes.dto.FacturaDesgloseDTO;
import com.lowes.dto.FacturaSolicitudDTO;
import com.lowes.dto.SolicitudDTO;
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
import com.lowes.entity.ProveedorLibre;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoDocumento;
import com.lowes.entity.TipoFactura;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.CompaniaService;
import com.lowes.service.CuentaContableService;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.ProveedorLibreService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.SolicitudService;
import com.lowes.service.TipoDocumentoService;
import com.lowes.service.TipoProveedorService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Scope("session")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ReembolsosController {
	
	private Integer idUsuario = null;

	@Autowired
	private LocacionService locacionService;
	@Autowired
	private CuentaContableService cuentaContableService;
	@Autowired
	private UsuarioConfSolicitanteService uConfsolicitanteService;
	@Autowired
	private CompaniaService companiaService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private ProveedorLibreService proveedorLibreService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private MonedaService monedaService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FormaPagoService formaPagoService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private FacturaArchivoService facturaArchivoService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private FacturaDesgloseService facturaDesgloseService;
	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;
	@Autowired
	private TipoProveedorService tipoProveedorService;
	
	private static final Logger logger = Logger.getLogger(ReembolsosController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	private UtilController util = new UtilController();

	//Lista de sesion para guardar las facturas capturadas
	private List<FacturaSolicitudDTO> lstFactDto;
	//Lista de sesion para facturas a eliminar
	private List<FacturaSolicitudDTO> lstFactEliminar;
	//Objeto de sesion para solicitud
	private SolicitudDTO solicitudDTO;
	//guarda los archivos de cada factura temporalmente
	private List<ArchivoDTO> currentFiles;
	
	@RequestMapping({ "/reembolso", "/cajaChica" })
	public ModelAndView reembolso(HttpSession session, Integer id, HttpServletRequest request) {
		this.idUsuario = usuarioService.getUsuarioSesion().getIdUsuario();
		HashMap<String, Object> model = new HashMap<String, Object>();
		Solicitud solicitudActual = null;
		Integer idEstadoSolicitud = 0;
		boolean configCorrecta = true;
		List<CuentaContable> cuentasContablesEnFactura  = new ArrayList<>();
		
		Integer usuarioCreacion;
		lstFactDto = new ArrayList<>();
		lstFactEliminar = new ArrayList<>();
		solicitudDTO = new SolicitudDTO();
		Solicitud solicitud = null;
		
		Usuario usuario = usuarioService.getUsuario(idUsuario);
		
		//get mapping
		String mapping = request.getServletPath();
		
		//Set Tipo Solicitud
		if(mapping.equals("/reembolso"))
			solicitudDTO.setIdTipoSolicitud(Etiquetas.SOLICITUD_REEMBOLSOS);
		else if(mapping.equals("/cajaChica"))
			solicitudDTO.setIdTipoSolicitud(Etiquetas.SOLICITUD_CAJA_CHICA);
		
		// configuraciones permitidas por usuario
		List<UsuarioConfSolicitante> uconfigSol = null;
		List<Locacion> lcPermitidas = null;
		List<CuentaContable> ccPermitidas = null;
		// Obtener Companias, Proveedores y Monedas
		List<Compania> lstCompanias = new ArrayList<>();
		lstCompanias.add(companiaService.getCompania(usuario.getCompania().getIdcompania()));
		List<Proveedor> lstProveedores = proveedorService.getAllProveedores();
		List<Moneda> lstMoneda = monedaService.getAllMoneda();
		// Usuario Beneficiario
		List<Usuario> lstUsrBenef = usuarioService.getUsuariosBeneficiariosByLocacion(usuario.getLocacion().getIdLocacion());
		
		if (id == null) {
			solicitudDTO.setUsuarioSolicitante(usuario);
			usuarioCreacion = idUsuario;
			// destruir la sesion, creada por parametro
			session.setAttribute("solicitud", null);
			if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
				uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(idUsuario, solicitudDTO.getIdTipoSolicitud());
			}else if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS){
				uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(idUsuario, solicitudDTO.getIdTipoSolicitud());
			}
			// Obtener combos filtrador por configuracion de solicitante.
			lcPermitidas = getLocacionesPermitidasPorUsuario(uconfigSol, solicitudDTO.getIdTipoSolicitud());
			//Carga cuentas contables
			ccPermitidas = getCuentasContablesPermitidasPorUsuario(uconfigSol);
			//Compania
		}
		else{
			solicitud = solicitudService.getSolicitud(id);
			solicitudDTO.setUsuarioSolicitante(solicitud.getUsuarioByIdUsuarioSolicita());
			usuarioCreacion = solicitud.getUsuarioByIdUsuario().getIdUsuario();
			if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
				uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(solicitud.getUsuarioByIdUsuario().getIdUsuario(), solicitudDTO.getIdTipoSolicitud());
			}else if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS){
				uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario(), solicitudDTO.getIdTipoSolicitud());
			}
			// Obtener combos filtrador por configuracion de solicitante.
			lcPermitidas = getLocacionesPermitidasPorUsuario(uconfigSol, solicitudDTO.getIdTipoSolicitud());
			ccPermitidas = getCuentasContablesPermitidasPorUsuario(uconfigSol);
			
			//Si la locacion de la solicitud ya no es la que el usuario tiene en su configuración.
			if(!contieneLocacion(lcPermitidas, solicitud.getLocacion())){
				lcPermitidas = Collections.singletonList(solicitud.getLocacion());
				ccPermitidas = getCuentasContablesBySolicitud(solicitud.getIdSolicitud());
				configCorrecta = false;
			}
			
			if(!contieneCompania(lstCompanias, solicitud.getCompania())){
				lstCompanias = Collections.singletonList(solicitud.getCompania());
				configCorrecta = false;
			}
			
			if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
				lstUsrBenef = Collections.singletonList(solicitud.getUsuarioByIdUsuarioAsesor());
			}
		}
		
		//Para caja chica
		if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
			// Comprueba si tiene locaciones CSC o si es locación de tienda
			Integer tipoLocacionUsr = usuarioService.getUsuario(usuarioCreacion).getLocacion().getTipoLocacion().getIdTipoLocacion();
			Integer tipoLocacionCSC = Integer.parseInt(parametroService.getParametroByName("tipoLocacionCSC").getValor());

			solicitudDTO.setEsLocacionCSC(tipoLocacionUsr == tipoLocacionCSC ? true : false);
		}
		
		solicitudDTO.setLocacion(usuarioService.getUsuario(usuarioCreacion).getLocacion());
		solicitudDTO.setLcPermitidas(lcPermitidas);
		solicitudDTO.setCcPermitidas(ccPermitidas);
		
		// obtener los jefe para opcion de usuario solicitante.
		List<Usuario> lstUsuariosJefe = new ArrayList<>();
		
		if(usuario.getUsuario() != null){
		  lstUsuariosJefe.add(usuario.getUsuario());
		}
		
		// Especifica solicitante
		solicitudDTO.setEspSolicitante(Integer.valueOf(usuario.getEspecificaSolicitante()));
		
		//Get forma pago
		List<FormaPago> lstFormaPago;
		if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
			List<FormaPago> lstFormaPagoTmp = formaPagoService.getAllFormaPago();
			lstFormaPago = new ArrayList<>();
			for(FormaPago fp : lstFormaPagoTmp)
			{
				if(fp.getIdFormaPago() == Integer.parseInt(parametroService.getParametroByName("formaPagoTransferencia").getValor())){
					lstFormaPago.add(fp);
				}
			}
		}
		else
			lstFormaPago = formaPagoService.getAllFormaPago();
		
		// Obtener solicitud,factura y su desglose si se requiere:
		if (id != null && id > Etiquetas.CERO) {
			solicitudDTO.setIdSolicitudSession(id);
			solicitud = solicitudService.getSolicitud(solicitudDTO.getIdSolicitudSession());
			if (solicitud != null) {
				idEstadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
		    	solicitudDTO.setEstatusSolicitud(solicitud.getEstadoSolicitud().getIdEstadoSolicitud());
			}
		}

		if (solicitud != null && solicitud.getFacturas().size() > Etiquetas.CERO) {
			// si la solicitud es valida entonces crear una variable de sesion
			// para el update.
			session.setAttribute("solicitud", solicitud);
			List<Factura> lstFact = solicitud.getFacturas();
			//List<FacturaSolicitudDTO> lstFactDto = new ArrayList<>();
			FacturaSolicitudDTO factSol;
			for(Factura factura : lstFact)
			{
				factSol = new FacturaSolicitudDTO();
				if(factura.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_FACTURA)
				{
					if(factura.getProveedor() != null)
					{
						factSol.setProveedor(factura.getProveedor().getIdProveedor());
						factSol.setRazonSocial(factura.getProveedor().getDescripcion());
						factSol.setRFC(factura.getProveedor().getRfc());
					}else{
						factSol.setProveedorLibre(factura.getProveedorLibre().getDescripcion());
						factSol.setRazonSocial(factura.getProveedorLibre().getDescripcion());
						factSol.setRFC(factura.getProveedorLibre().getRfc());
					}
					factSol.setSerie(factura.getSerieFactura());
					factSol.setFolioFiscal(factura.getFolioFiscal());
//					factSol.setStrIEPS(factura.getIeps()!=null ? factura.getIeps().toString() : "0.0");
					factSol.setIEPS(factura.getIeps());
					factSol.setTasaIeps(factura.getPorcentajeIeps());
//					factSol.setStrIVA(factura.getIva() != null ? factura.getIva().toString() : "0.0");
					factSol.setIVA(factura.getIva());
					factSol.setTasaIva(factura.getPorcentajeIva());
					
					//retenciones
					if(factura.getConRetenciones() > Etiquetas.CERO_S){
						factSol.setConRetenciones(Etiquetas.TRUE);
//						factSol.setStrISRRetenido(factura.getIsrRetenido() != null? factura.getIsrRetenido().toString() : "0.0");
						factSol.setISRRetenido(factura.getIsrRetenido());
//						factSol.setStrIVARetenido(factura.getIvaRetenido() != null ? factura.getIvaRetenido().toString() : "0.0");
						factSol.setIVARetenido(factura.getIvaRetenido());
					}else{
						factSol.setConRetenciones(Etiquetas.FALSE);
					}
				}
				factSol.setIdFactura(factura.getIdFactura());
				factSol.setConcepto(factura.getConceptoGasto());
				factSol.setConCompFiscal((factura.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_FACTURA) ? true : false);
				factSol.setCuentaContable(factura.getCuentaContable().getIdCuentaContable());
				cuentasContablesEnFactura.add(factura.getCuentaContable());
				factSol.setFecha(factura.getFechaFactura() != null ? util .parseFecha(factura.getFechaFactura()) : null);
				factSol.setFolio(factura.getFactura());
				factSol.setIdCompania(solicitud.getCompania().getIdcompania());
				factSol.setLocacion(factura.getLocacion().getIdLocacion());
				factSol.setMoneda(factura.getMoneda().getIdMoneda());
//				factSol.setStrSubTotal(factura.getSubtotal() != null ? factura.getSubtotal().toString() : "0.0");
				factSol.setSubTotal(factura.getSubtotal());
//				factSol.setStrTotal(factura.getTotal() != null ? factura.getTotal().toString() : "0.0");
				factSol.setTotal(factura.getTotal());
				//get archivos de cada factura
				List<FacturaArchivo> lstFactArchivo = facturaArchivoService.getAllFacturaArchivoByIdFactura(factura.getIdFactura());
				factSol.setFacturaArchivos(lstFactArchivo);
				lstFactDto.add(factSol);
			}
			//factura = solicitud.getFacturas().get(Etiquetas.CERO);
			if (lstFact != null && solicitud.getFacturas().size() > Etiquetas.CERO) {
				
				solicitudDTO.setCompania(solicitud.getCompania());
				solicitudDTO.setMontoTotal(solicitud.getMontoTotal());
				solicitudDTO.setMoneda(solicitud.getMoneda());
				solicitudDTO.setLocacion(solicitud.getLocacion());
				solicitudDTO.setFormaPago(solicitud.getFormaPago());
				if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS){
					if(solicitud.getUsuarioByIdUsuario().getIdUsuario() != solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario()){
						solicitudDTO.setSolicitante(true);
						solicitudDTO.setUsuarioSolicitante(solicitud.getUsuarioByIdUsuarioSolicita());
						solicitudDTO.setIdSolicitante(solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario());
					}else{
						solicitudDTO.setSolicitante(false);
						solicitudDTO.setIdSolicitante(solicitud.getUsuarioByIdUsuario().getIdUsuario());
					}
				}					
				else if(solicitud.getTipoSolicitud().getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
					solicitudDTO.setBeneficiario((lstUsrBenef.size() > 0) ? lstUsrBenef.get(0).getIdUsuario() : -1);
				}
				solicitudDTO.setUsuarioCreacion(solicitud.getUsuarioByIdUsuario());
                solicitudDTO.setEstatusSolicitud(solicitud.getEstadoSolicitud().getIdEstadoSolicitud());
                Integer puestoAP = Integer.parseInt(parametroService.getParametroByName("puestoAutorizacionAP").getValor());
        		Integer puestoConfirmacionAP = Integer.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
                //validacion de propietario de la solicitud
				// se manda la lista de autorizadores, if null : no aplica este criterio 
	  		    //List<SolicitudAutorizacion> autorizadores = solicitudAutorizacionService.getAllAutorizadoresByCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE);
	  		    List<SolicitudAutorizacion> autorizadores = solicitudAutorizacionService.getAllSolicitudAutorizacionBySolicitud(solicitud.getIdSolicitud());
	  		    // resolver el acceso a la solicitud actual.
	  		    Integer acceso = Utilerias.validaAcceso(solicitud, solicitudDTO.getIdTipoSolicitud(),usuarioService.getUsuarioSesion(), autorizadores, puestoAP, puestoConfirmacionAP);
	  		    if(acceso == Etiquetas.NO_VISUALIZAR){
	  			      return new ModelAndView("redirect:/");
	  		    }else if(acceso == Etiquetas.VISUALIZAR){
	  		    		idEstadoSolicitud = Etiquetas.SOLO_VISUALIZACION;
	  		    		solicitudDTO.setEstatusSolicitud(Etiquetas.SOLO_VISUALIZACION);
	  		    }
                
                
				solicitudDTO.setConcepto(solicitud.getConceptoGasto());
				
				//Set subtotal de facturas en solicitud.
				BigDecimal subTotal = new BigDecimal(0.0);
				for(FacturaSolicitudDTO fs : lstFactDto){
//					subTotal = subTotal.add(Utilerias.convertCurrencyToString(fs.getStrSubTotal()));
					subTotal = subTotal.add(fs.getSubTotal());
				}
				solicitudDTO.setSubTotal(subTotal);
				
			}
		} else {
			// mensaje de solicitud no valida a la vista.
			solicitudDTO.setIdSolicitudSession(Etiquetas.CERO);
			solicitudDTO.setUsuarioCreacion(usuario);
			solicitudDTO.setBeneficiario((lstUsrBenef.size() > 0) ? lstUsrBenef.get(0).getIdUsuario() : -1);
			solicitudDTO.setEstatusSolicitud(null);
		}
		
		//revisar si se trata de una modificaciï¿½n para activar mensaje en la vista
		if(session.getAttribute("actualizacion") != null){
			solicitudDTO.setModificacion(Etiquetas.TRUE);
			session.setAttribute("actualizacion", null);
		}else{
			solicitudDTO.setModificacion(Etiquetas.FALSE);
		}
		
		//revisar si se trata de un nuevo registro para activar mensaje en la vista
		if(session.getAttribute("creacion") != null){
			solicitudDTO.setCreacion(Etiquetas.TRUE);
			session.setAttribute("creacion", null);
		}else{
			solicitudDTO.setCreacion(Etiquetas.FALSE);
		}
		
        
		// Enviar archivos anexados por solicitud:
		List<SolicitudArchivo> solicitudArchivoList = new ArrayList<>();
		if(solicitudDTO.getIdSolicitudSession() > Etiquetas.CERO){
			solicitudArchivoList = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(solicitudDTO.getIdSolicitudSession()); 
		}
		
		//Set cuenta contable no deducible para documentos sin comprobante fiscal
		String cuentaContable = parametroService.getParametroByName("ccNoDeducible").getValor();
		Integer ccID = UtilController.getIDByCuentaContable(cuentaContable , cuentaContableService.getAllCuentaContable());
		CuentaContable ccNoComprobante = new CuentaContable();
		if(ccID != null){
			ccNoComprobante = cuentaContableService.getCuentaContable(ccID);
		}
		solicitudDTO.setLstFactDto(lstFactDto);
		
		
		//Se agrega cuenta contables de facturas.
		List<CuentaContable> cuentaContableAuxiliar = solicitudDTO.getCcPermitidas();
		cuentaContableAuxiliar.addAll(cuentasContablesEnFactura);
		solicitudDTO.setCcPermitidas(null);
		solicitudDTO.setCcPermitidas(cuentaContableAuxiliar);
		
		model.put("solicitudDTO", solicitudDTO);
		model.put("usuarioSesion", idUsuario);
		model.put("ccNoComprobante", ccNoComprobante);
		model.put("lstUsrBenef", lstUsrBenef);
		model.put("lstCompanias", lstCompanias);
		model.put("lstProveedores", lstProveedores);
		model.put("lstMonedas", lstMoneda);
		model.put("lstFormaPago", lstFormaPago);
		model.put("lstUsuariosJefe", lstUsuariosJefe);
		model.put("configCorrecta", configCorrecta);

		return new ModelAndView(mapping, model);
	}
	
	/**
	 * @author miguelr
	 * Peticion ajax que actualiza datos de la vista al controller.
	 * @param session
	 * @param numrows
	 * @param compania
	 * @param conComp
	 * @param locacion
	 * @param proveedor
	 * @param rfcEmisor
	 * @param folio
	 * @param serie
	 * @param folioFiscal
	 * @param fecha
	 * @param subTotal
	 * @param moneda
	 * @param conRetenciones
	 * @param iva
	 * @param iva_retenido
	 * @param ieps
	 * @param isr_retenido
	 * @param total
	 * @param conceptoSol
	 * @param request
	 * @param response
	 * @return json - Respuesta
	 */
	@RequestMapping(value = "/addRowReemConXML", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> addRow(HttpSession session, @RequestParam Integer numrows,
			 @RequestParam Integer compania, @RequestParam boolean conComp, @RequestParam Integer locacion, @RequestParam Integer proveedor,
			 @RequestParam String proveedorLibre, @RequestParam String rfcEmisor, @RequestParam String folio, @RequestParam String serie, 
			 @RequestParam String folioFiscal, @RequestParam String fecha, @RequestParam String subTotal,
			 @RequestParam Integer moneda, @RequestParam boolean conRetenciones, @RequestParam String iva, @RequestParam Double tasaIva,
			 @RequestParam String iva_retenido, @RequestParam String ieps, @RequestParam Double tasaIeps, @RequestParam String isr_retenido,
			 @RequestParam String total, @RequestParam String conceptoSol, @RequestParam Integer beneficiario,
			 
			HttpServletRequest request, HttpServletResponse response) {
		
//        try{
//        	String conceptoSol1 = URLDecoder.decode(conceptoSol,"UTF-8");
//        	String conceptoSol2 = new String(conceptoSol.getBytes("UTF-8"),"ASCII");
//            System.out.println(conceptoSol);
//            System.out.println(conceptoSol1);
//            System.out.println(conceptoSol2);
//        }
//        catch(Exception E){
//        }
//		
		solicitudDTO.setLocacion(locacionService.getLocacion(locacion));
		solicitudDTO.setMoneda(monedaService.getMoneda(moneda));
		
		if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
			if(solicitudDTO.getBeneficiario() == null && beneficiario != -1)
				solicitudDTO.setBeneficiario(beneficiario);
		}
		else if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS){
			if(beneficiario != -1)
				solicitudDTO.setIdSolicitante(beneficiario);
			else{
				solicitudDTO.setIdSolicitante(solicitudDTO.getUsuarioCreacion().getIdUsuario());
			}
		}
				
		FacturaSolicitudDTO item = new FacturaSolicitudDTO();
		String razonSocial = "";
		item.setIdCompania(compania);
		item.setLocacion(locacion);
		item.setConCompFiscal(conComp);
		if(conComp)
		{
			item.setConRetenciones(conRetenciones);
			item.setFolioFiscal(folioFiscal);
			item.setIEPS(Utilerias.convertStringToBigDecimal(ieps));
			item.setTasaIeps(new BigDecimal(tasaIeps));
			item.setISRRetenido(Utilerias.convertStringToBigDecimal(isr_retenido));
			item.setIVA(Utilerias.convertStringToBigDecimal(iva));
			item.setTasaIva(new BigDecimal(tasaIva));
			item.setIVARetenido(Utilerias.convertStringToBigDecimal(iva_retenido));
			//Cuando el proveedor no está registrado
			if(proveedor != -1){
				item.setProveedor(proveedor);
				razonSocial = proveedorService.getProveedor(proveedor).getDescripcion();
			}else{
				item.setProveedorLibre(proveedorLibre);
				razonSocial = proveedorLibre;
			}
			item.setRFC(rfcEmisor);
			item.setSerie(serie);
		}
		else{
			item.setIEPS(new BigDecimal(0.0));
			item.setIVA(new BigDecimal(0.0));
			item.setTasaIva(new BigDecimal(0.0));
			item.setTasaIeps(new BigDecimal(0.0));
		}
		
		//Set cuenta contable no deducible para documentos sin comprobante fiscal
		String cuentaContable = parametroService.getParametroByName("ccNoDeducible").getValor();
		
		CuentaContable ccNoComprobante = null;
		Integer idCuenta = UtilController.getIDByCuentaContable(cuentaContable , cuentaContableService.getAllCuentaContable());
		if(idCuenta != null)
			ccNoComprobante = cuentaContableService.getCuentaContable(idCuenta);
		
		if(!conComp && ccNoComprobante != null){
			item.setCuentaContable(ccNoComprobante.getIdCuentaContable());
		}
		
		
		item.setFecha(fecha);
		item.setFolio(folio);
		item.setMoneda(moneda);
		item.setSubTotal(Utilerias.convertStringToBigDecimal(subTotal));
		item.setTotal(Utilerias.convertStringToBigDecimal(total));
		item.setFiles(currentFiles);
		currentFiles = null;
		item.setConcepto(conceptoSol);
		lstFactDto.add(item);

		String json = null;
		StringBuilder row2 = new StringBuilder();

		// Obtener combos filtrador por configuracion de solicitante.
		List<Locacion> lcPermitidas = solicitudDTO.getLcPermitidas();
		// Obtener combos filtrador por configuracion de solicitante.
		Locacion lcSelecc = locacionService.getLocacion(locacion);
		
		
		List<CuentaContable> ccPermitidas = null;
		List<UsuarioConfSolicitante> uconfigSol = null;
		//COMENTADO 13-05-2016 POR QUE REQUIEREN ÚNICAMENTE LA CONFIGURACION DEL USUARIO CREACION.
//		if(beneficiario != -1)
//			uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(beneficiario, solicitudDTO.getIdTipoSolicitud());
//		else
//			uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(idUsuario, solicitudDTO.getIdTipoSolicitud());
		
		if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(solicitudDTO.getUsuarioCreacion().getIdUsuario(), solicitudDTO.getIdTipoSolicitud());
		}
		else if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS){
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(solicitudDTO.getIdSolicitante(), solicitudDTO.getIdTipoSolicitud());
		}
		

		ccPermitidas = getCuentasContablesPermitidasPorLocacion(locacion, uconfigSol);

		String num = String.valueOf(numrows);
		Integer numLinea = numrows + 1;

		row2.append("<tr class=\"odd gradeX\">");
		row2.append("<td align=\"center\"><div class=\"linea\">" + numLinea + "</div></td>");

		// subtotal
		row2.append("<td><input type=\"text\" onChange=\"sumSbt()\" id=\"lstFactDto" + num
				+ ".subTotal\" name=\"lstFactDto[" + num
				+ "].subTotal\" class=\"form-control subtotales currencyFormat\" value=\"" + subTotal + "\" disabled></td>");
		
		String disabledcsc = (solicitudDTO.getEsLocacionCSC() == true ? "" : "disabled");
		String disabledNoDed = (solicitudDTO.getEsLocacionCSC() == true ? "" : "disabled");
		// locaciones
		row2.append("<td><select id=\"lstFactDto[" + num + "].locacion\" onChange=\"locacionOnChange.call(this)\" name=\"lstFactDto["
				+ num + "].locacion\" class=\"form-control locaciones\" "+disabledcsc+">");
		if(solicitudDTO.getEsLocacionCSC()){
			if(conComp){
				row2.append("<option value=\"-1\">Seleccione:</option>");
				for (Locacion lc : lcPermitidas) {
					row2.append("<option value=\"");
					row2.append(String.valueOf(lc.getIdLocacion()));
					if(lcSelecc.getIdLocacion() == lc.getIdLocacion()){
						row2.append("\" selected>");
					}
					else{
						row2.append("\">");
					}
					row2.append(lc.getNumeroDescripcionLocacion() + " </option>");
				}
			}else{
				//COMENTAD POR CAMBIO, se elimina cuando se acepte
				//row2.append("<option value=\"");
				//row2.append(String.valueOf(lcSelecc.getIdLocacion()));
				//row2.append("\"> " + lcSelecc.getNumeroDescripcionLocacion() + " </option>");
				row2.append("<option value=\"-1\">Seleccione:</option>");
				for (Locacion lc : lcPermitidas) {
					row2.append("<option value=\"");
					row2.append(String.valueOf(lc.getIdLocacion()));
					if(lcSelecc.getIdLocacion() == lc.getIdLocacion()){
						row2.append("\" selected>");
					}
					else{
						row2.append("\">");
					}
					row2.append(lc.getNumeroDescripcionLocacion() + " </option>");
				}
			}

		}
		else{
			row2.append("<option value=\"");
			row2.append(String.valueOf(lcSelecc.getIdLocacion()));
			row2.append("\"> " + lcSelecc.getNumeroDescripcionLocacion() + " </option>");
		}
		row2.append("</select>");
		row2.append("</td>");
		
		String disabledcc = (conComp ? "" : "disabled");
		// cuenta contables
		row2.append("<td class=\"ccontable\"><select id=\"lstFactDtoCC" + num
				+ "\" onChange=\"ccOnChange.call(this)\" name=\"lstFactDto[" + num
				+ "].cuentaContable\" class=\"form-control ccontable\" "+disabledcc+">");
		if(conComp){
			row2.append("<option value=\"-1\">Seleccione:</option>");
				for (CuentaContable cc : ccPermitidas) {
					row2.append("<option value=\"");
					row2.append(String.valueOf(cc.getIdCuentaContable()));
					row2.append("\"> " + cc.getNumeroDescripcionCuentaContable() + " </option>");
				}
			
		}
		else{
			row2.append("<option value=\"");
			if(ccNoComprobante != null){
				row2.append(String.valueOf(ccNoComprobante.getIdCuentaContable()));
				row2.append("\"> " + ccNoComprobante.getNumeroDescripcionCuentaContable() + " </option>");
			}
			else{
				row2.append("-1");
				row2.append("\"> " + etiqueta.CCNODED_NO_CONFIGURADA + " </option>");
			}
		}
		row2.append("</select>");
		row2.append("</td>");
		
		// Factura/Folio
		if(razonSocial != "")
			row2.append("<td>" + razonSocial + " - " + serie + " - " + folio +"</td>");
		else
			row2.append("<td>" + folio +"</td>");
		
		// concepto
		row2.append("<td><input id=\"lstFactDto" + num + ".concepto\" onChange=\"conceptoOnChange.call(this)\" name=\"lstFactDto[" + num
				+ "].concepto\" class=\"form-control conceptogrid\" maxlength=\"500\" value=\"" + conceptoSol + "\" type=\"text\"></td>");
		
		// IVA
		row2.append("<td><input type=\"text\" id=\"lstFactDto" + num
				+ ".iva\" name=\"lstFactDto[" + num
				+ "].iva\" class=\"form-control ivas currencyFormat\" value=\"" + Utilerias.convertStringToBigDecimal(iva).setScale(Etiquetas.DOS) + "\" disabled></td>");
		
		// IEPS
		row2.append("<td><input type=\"text\" id=\"lstFactDto" + num
				+ ".ieps\" name=\"lstFactDto[" + num
				+ "].ieps\" class=\"form-control ieps currencyFormat\" value=\"" + Utilerias.convertStringToBigDecimal(ieps).setScale(Etiquetas.DOS) + "\" disabled></td>");

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

	/**
	 * @author Adinfi
	 * @param date
	 * @param format
	 * @return Date - Fecha parseada
	 * @throws ParseException
	 * DA FORMATO A UN STRING FECHA.
	 */
	private Date parseDate(String date, String format) throws ParseException
	{
	    SimpleDateFormat formatter = new SimpleDateFormat(format);
	    return formatter.parse(date);
	}

	/**
	 * @author Adinfi
	 * @param solicitudDTO
	 * @return Solicitud - Objeto con los datos de la solicitud.
	 * CARGA LA INFORMACIÓN DE LA SOLICITUD EN UN OBJETO TIPO SOLICITUD PARA SU INSERCIÓN A BDD.
	 */
	private Solicitud loadSolicitud(SolicitudDTO solicitudDTO) {
		Solicitud sol = new Solicitud();
		// definir tipo de solicitud
		sol.setTipoSolicitud(new TipoSolicitud(solicitudDTO.getIdTipoSolicitud()));
		sol.setMontoTotal(Utilerias.convertStringToBigDecimal(solicitudDTO.getStrMontoTotal()));
		sol.setUsuarioByIdUsuario(new Usuario(idUsuario));

		if (solicitudDTO.isSolicitante()) {
			sol.setUsuarioByIdUsuarioSolicita(new Usuario(solicitudDTO.getUsuarioSolicitante().getIdUsuario()));
		} else {
			if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA)
			{
				//sol.setUsuarioByIdUsuarioSolicita(new Usuario(solicitudDTO.getBeneficiario()));
				sol.setUsuarioByIdUsuarioSolicita(new Usuario(idUsuario));
			}
			else{
				sol.setUsuarioByIdUsuarioSolicita(new Usuario(idUsuario));
			}
		}
		sol.setConceptoGasto(solicitudDTO.getConcepto());
		sol.setMoneda(solicitudDTO.getMoneda());
		sol.setCompania(solicitudDTO.getCompania());
		sol.setLocacion(solicitudDTO.getLocacion());
		sol.setFormaPago(solicitudDTO.getFormaPago());
		return sol;
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return String con suma total.
	 * REALIZA LA SUMA DEL MONTO TOTAL CUANDO SE AGREGAN O ELIMINAN FACTURAS DEL GRID.
	 */
	@RequestMapping(value = "/getLocacionSolicitante", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getLocacionSolicitante(HttpSession session, @RequestParam Integer idSolicitante,
			HttpServletRequest request, HttpServletResponse response) {
		
		//idSolicitante = solicitudDTO.getIdTipoSolicitud() != Etiquetas.TIPO_SOL_REP_CAJA_CHICA && idSolicitante == -1 ? solicitudDTO.getUsuarioSolicitante().getIdUsuario() : idSolicitante;
		
		// configuraciones permitidas por usuario
		List<UsuarioConfSolicitante> uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(idSolicitante, solicitudDTO.getIdTipoSolicitud());
		// Obtener combos filtrador por configuracion de solicitante.
		List<Locacion> lcPermitidas = getLocacionesPermitidasPorUsuario(uconfigSol, solicitudDTO.getIdTipoSolicitud());
		// Comprueba si tiene locaciones CSC o si es locación de tienda
		solicitudDTO.setEsLocacionCSC(lcPermitidas.size() > 1 && solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA ? true : false);
		//Carga cuentas contables
		List<CuentaContable> ccPermitidas = getCuentasContablesPermitidasPorUsuario(uconfigSol);
		
		solicitudDTO.setLcPermitidas(lcPermitidas);
		solicitudDTO.setCcPermitidas(ccPermitidas);
		
		StringBuilder option = new StringBuilder();
		option.append("<option value=\"-1\">Seleccione:</option>");
		for(Locacion loc : lcPermitidas){
			option.append("<option value=");
			option.append(loc.getIdLocacion());
			option.append("> " + loc.getNumeroDescripcionLocacion() + " </option>");
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
	 * @return String con suma total.
	 * REALIZA LA SUMA DEL MONTO TOTAL CUANDO SE AGREGAN O ELIMINAN FACTURAS DEL GRID.
	 */
	@RequestMapping(value = "/getTotalSol", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getTotalSol(HttpSession session, @RequestParam Integer numrows,
			HttpServletRequest request, HttpServletResponse response) {		
		BigDecimal total = new BigDecimal(0.0);
		for(FacturaSolicitudDTO rem : lstFactDto){
			total = total.add(rem.getTotal());
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(String.valueOf(total), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return String con suma total actualizada.
	 * REALIZA LA SUMA DEL MONTO TOTAL CUANDO SE AGREGAN O ELIMINAN FACTURAS DEL GRID.
	 */
	@RequestMapping(value = "/updTotalOnDelete", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> updTotalOnDelete(HttpSession session, @RequestParam Integer numrow,
			HttpServletRequest request, HttpServletResponse response) {
		BigDecimal total = new BigDecimal(0.0);
		lstFactEliminar.add(lstFactDto.get(numrow-1));
		lstFactDto.remove(numrow-1);
		for(FacturaSolicitudDTO rem : lstFactDto){
			total = total.add(rem.getTotal());
		}
		solicitudDTO.setMontoTotal(new BigDecimal(total.doubleValue()));
		//Set subtotal de facturas en solicitud.
		BigDecimal subTotal = new BigDecimal(0.0);
		for(FacturaSolicitudDTO fs : lstFactDto){
			subTotal = subTotal.add(fs.getSubTotal());
		}
		solicitudDTO.setSubTotal(subTotal);		
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(String.valueOf(total), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return String con suma total actualizada.
	 * RESETEA LOS VALORES DE LA SOLICITUD CUANDO SE CAMBIA UN VALÓR.
	 */
	@RequestMapping(value = "/resetSolicitud", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> resetSolicitud(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		BigDecimal total = new BigDecimal(0.0);
		
		for(FacturaSolicitudDTO fs : lstFactDto){
			lstFactEliminar.add(fs);
		}
		
		lstFactDto.clear();
		
		solicitudDTO.setMontoTotal(new BigDecimal(total.doubleValue()));
		solicitudDTO.setSubTotal(new BigDecimal(total.doubleValue()));		
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(String.valueOf(total), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param numrows
	 * @param request
	 * @param response
	 * @return OK
	 * ACTUALIZA EL VALOR DE LA CUENTA CONTABLE CUANDO ES MODIFICADA EN EL COMBO DEL GRID.
	 */
	@RequestMapping(value = "/updCuentaCont", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> updCuentaCont(HttpSession session, @RequestParam Integer numrow,
		   @RequestParam Integer idCtaCont, HttpServletRequest request, HttpServletResponse response) {		
		lstFactDto.get(numrow -1).setCuentaContable(idCtaCont);
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(String.valueOf(0), responseHeaders, HttpStatus.CREATED);
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
	@RequestMapping(value = "/updLocacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> updLocacion(HttpSession session, @RequestParam boolean isFirst, @RequestParam Integer numrow,
		   @RequestParam Integer idLocacion, HttpServletRequest request, HttpServletResponse response) {		
		lstFactDto.get(numrow -1).setLocacion(idLocacion);
		int ccSelected = 0;
		if(isFirst){
			if(lstFactDto.get(numrow -1).getCuentaContable() != null)
				ccSelected = lstFactDto.get(numrow -1).getCuentaContable();
		}
		StringBuilder option = new StringBuilder();
		if(lstFactDto.get(numrow -1).getFolioFiscal() != null){
			// configuraciones permitidas por usuario
			List<UsuarioConfSolicitante> uconfigSol = null;
			if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA)
				uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante((solicitudDTO.getUsuarioCreacion()) != null ? solicitudDTO.getUsuarioCreacion().getIdUsuario() : idUsuario, solicitudDTO.getIdTipoSolicitud());
			else if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS)
				uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(solicitudDTO.getIdSolicitante(), solicitudDTO.getIdTipoSolicitud());
			else
				uconfigSol = uConfsolicitanteService.getUsuarioConfSolicitante(idUsuario, solicitudDTO.getIdTipoSolicitud());
			
			List<CuentaContable> ccPermitidas = getCuentasContablesPermitidasPorLocacion(idLocacion, uconfigSol);
			if(!ccPermitidas.contains(ccSelected)){
				ccPermitidas = Collections.singletonList(cuentaContableService.getCuentaContable(ccSelected));
			}
			
			option.append("<option value=\"-1\">Seleccione:</option>");
			for(CuentaContable cc : ccPermitidas){
				if(cc.getIdCuentaContable() == ccSelected)
					option.append("<option selected value=");
				else
					option.append("<option value=");
				
				option.append(cc.getIdCuentaContable());
				option.append("> " + cc.getNumeroDescripcionCuentaContable() + " </option>");
			}			
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
	 * ACTUALIZA EL CONCEPTO DE LA FACTURA EN EL OBJETO DEL CONTROLLER.
	 */
	@RequestMapping(value = "/updConceptoFact", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> updConceptoFact(HttpSession session, @RequestParam Integer numrow,
		   @RequestParam String concepto, HttpServletRequest request, HttpServletResponse response) {		
		lstFactDto.get(numrow -1).setConcepto(concepto);
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(String.valueOf(0), responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @param uconfigSol
	 * @return List<Locacion> La lista de las locaciones
	 * CARGA LAS LOCACIONES PERPITIDAS DE ACUERDO A LA CONFIGURACIÓN DEL USUARIO.
	 */
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
	
	/**
	 * @param uconfigSol
	 * @return List<CuentaContable> La lista de las cuentas contables
	 * CARGA LAS CUENTAS CONTABLES PERPITIDAS DE ACUERDO A LA CONFIGURACIÓN DEL USUARIO.
	 */
	private List<CuentaContable> getCuentasContablesPermitidasPorUsuario(List<UsuarioConfSolicitante> uconfigSol) {
		// cargar los tipos de cuenta contable permitidos por usuario
		List<Integer> idCuentaContable = new ArrayList<>();
		for (UsuarioConfSolicitante confCuentas : uconfigSol) {
			if (confCuentas != null) {
				idCuentaContable.add(confCuentas.getCuentaContable().getIdCuentaContable());
			}
		}

		List<CuentaContable> ccontable = cuentaContableService.getAllCuentaContable();
		List<CuentaContable> ccontableAux = new ArrayList<>();
		for (CuentaContable cc : ccontable) {
			if (idCuentaContable.contains(cc.getIdCuentaContable())) {
				ccontableAux.add(cc);
			}
		}

		return ccontableAux;
	}
	
	/**
	 * @param uconfigSol
	 * @return List<CuentaContable> La lista de las cuentas contables
	 * CARGA LAS CUENTAS CONTABLES PERPITIDAS DE ACUERDO A LA LOCACION
	 */
	private List<CuentaContable> getCuentasContablesPermitidasPorLocacion(Integer idLocacion, List<UsuarioConfSolicitante> uconfigSol) {
		// cargar los tipos de cuenta contable permitidos por usuario
		List<Integer> idCuentaContable = new ArrayList<>();
		for (UsuarioConfSolicitante confCuentas : uconfigSol) {
			if (confCuentas.getLocacion().getIdLocacion() == idLocacion) {
				idCuentaContable.add(confCuentas.getCuentaContable().getIdCuentaContable());
			}
		}

		List<CuentaContable> ccontable = cuentaContableService.getAllCuentaContable();
		List<CuentaContable> ccontableAux = new ArrayList<>();
		for (CuentaContable cc : ccontable) {
			if (idCuentaContable.contains(cc.getIdCuentaContable())) {
				ccontableAux.add(cc);
			}
		}

		return ccontableAux;
	}
	
	/**
	 * @param solicitudDTO
	 * @param session
	 * RECIBE EL OBJETO SOLICITUD CON TODA LA INFORMACIÓN DE SUS FACTURAS E INSERTA VALORES EN BDD.
	 */
	@RequestMapping(value = "/saveSolicitud", method = RequestMethod.POST)
	public ModelAndView saveSolicitud(@ModelAttribute("solicitudDTO") SolicitudDTO solicitudDTO, HttpSession session) {
		
		// se crea el objeto de solicitud, factura y desglose
		Solicitud solicitudSaveOrUpdate = new Solicitud();
		Factura facturaSaveOrUpdate = new Factura();
		Integer idSolicitudNueva = null;

		// saber si hay en session una solicitud
		Solicitud solicitudActual = (Solicitud) session.getAttribute("solicitud");
		if (solicitudActual != null && solicitudActual.getIdSolicitud() > Etiquetas.CERO) {
		
	    // 1 - ACTUALIZAR la solicitud
		/* TODO  quitar el llamado a bd*/	
		Solicitud solicitudSaveOrUpdateAux = solicitudService.getSolicitud(solicitudActual.getIdSolicitud());

			// se cargan los datos de la solicitud
			solicitudSaveOrUpdate = loadSolicitud(solicitudDTO);
			solicitudSaveOrUpdate.setCreacionFecha(solicitudSaveOrUpdateAux.getCreacionFecha());
			solicitudSaveOrUpdate.setCreacionUsuario(solicitudSaveOrUpdateAux.getCreacionUsuario());
			solicitudSaveOrUpdate.setIdSolicitud(solicitudActual.getIdSolicitud());
			solicitudSaveOrUpdate.setModificacionFecha(new Date());
			solicitudSaveOrUpdate.setModificacionUsuario(idUsuario);
			solicitudSaveOrUpdate.setEstadoSolicitud(solicitudActual.getEstadoSolicitud());
			
			// Guarda Beneficiario
			if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
				solicitudSaveOrUpdate.setTipoProveedor(tipoProveedorService.getTipoProveedor(Etiquetas.TIPO_PROVEEDOR_BENEFICIARIO));
				solicitudSaveOrUpdate.setUsuarioByIdUsuarioAsesor(usuarioService.getUsuario(solicitudDTO.getBeneficiario()));
			}
			
			// solicitud service actualizar.
			solicitudSaveOrUpdate = solicitudService.updateSolicitud(solicitudSaveOrUpdate);
			idSolicitudNueva = solicitudSaveOrUpdate.getIdSolicitud();
			
			// si se guardo y por lo tanto el id' que regreso es mayor a cero entonces cargamos los datos de factura.
			if(idSolicitudNueva > Etiquetas.CERO){
				
				//Se carga lista de facturas
				for(FacturaSolicitudDTO fact : lstFactDto){
					facturaSaveOrUpdate = loadFactura(idSolicitudNueva, fact);
					facturaSaveOrUpdate.setActivo(Etiquetas.UNO_S);
					facturaSaveOrUpdate.setCreacionUsuario(idUsuario);
					facturaSaveOrUpdate.setCreacionFecha(new Date());
					
					Integer idFactura;
					if(fact.getIdFactura() != null){						
						idFactura = fact.getIdFactura();
						facturaSaveOrUpdate.setIdFactura(idFactura);
						//ACTUALIZAR: llamada al servicio con el objeto cargado.
						facturaSaveOrUpdate = facturaService.updateFactura(facturaSaveOrUpdate);
					}
					else{
						// CREAR factura 
						idFactura = facturaService.createFactura(facturaSaveOrUpdate);
						System.out.println(idFactura);
						
						// CREAR Desgloce
						FacturaDesglose fdesgloseSaveOrUpdate = new FacturaDesglose();
						FacturaDesgloseDTO fdto = new FacturaDesgloseDTO();
						fdto.setConcepto(fact.getConcepto());
						fdto.setCuentaContable(cuentaContableService.getCuentaContable(fact.getCuentaContable()));
						fdto.setLocacion(locacionService.getLocacion(fact.getLocacion()));
						fdto.setStrSubTotal(fact.getTotal().toString());
						
						fdesgloseSaveOrUpdate = loadFacturaDesglose(idFactura, fdto);
						
						// guardar desglose
					    facturaDesgloseService.createFacturaDesglose(fdesgloseSaveOrUpdate);
						
						
					}
					guardarArchivosFactura(fact.getFiles(),idSolicitudNueva,idFactura);
				}
				
				//Se eliminan facturas al guadar
				for(FacturaSolicitudDTO factElim : lstFactEliminar){
					//Borrado de archivos en bdd
					List<FacturaArchivo> currFactFiles = factElim.getFacturaArchivos();
					for(FacturaArchivo archivoFact : currFactFiles){
						//si es borrado lógico
						//af.setActivo((short) 0);
						//facturaArchivoService.updateFacturaArchivo(af);
						
						//borrado permanente de bdd
						facturaArchivoService.deleteFacturaArchivo(archivoFact);
					}
					//Borrado de registro Desgloce
					facturaDesgloseService.deleteAllByIdFactura(factElim.getIdFactura());
					
					//Borrado de registro Factura
					Factura facElim = facturaService.getFactura(factElim.getIdFactura());
					facturaService.deleteFactura(facElim);
				}
			}
			
			// crear variable de sesion para el mensaje de actualzaciï¿½n
			  Integer updt = Etiquetas.UNO;
			  session.setAttribute("actualizacion", updt );
			
		} else {
			/* ******* CREAR INFO POR PRIMERA VEZ ********* */
			// se cargan los datos de la solicitud
			solicitudSaveOrUpdate = loadSolicitud(solicitudDTO);
			
			/* TODO */
			// revisar estos valores en el update
			solicitudSaveOrUpdate.setActivo(Etiquetas.UNO_S);
			solicitudSaveOrUpdate.setCreacionFecha(new Date());
			solicitudSaveOrUpdate.setCreacionUsuario(idUsuario);
			solicitudSaveOrUpdate.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.UNO));
			
			// Guarda Beneficiario
			if(solicitudDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA){
				solicitudSaveOrUpdate.setTipoProveedor(tipoProveedorService.getTipoProveedor(Etiquetas.TIPO_PROVEEDOR_BENEFICIARIO));
				solicitudSaveOrUpdate.setUsuarioByIdUsuarioAsesor(usuarioService.getUsuario(solicitudDTO.getBeneficiario()));
			}

			// guardar factura y desglose por primera vez.
			idSolicitudNueva = solicitudService.createSolicitud(solicitudSaveOrUpdate);
			

			//Se carga lista de facturas
			for(FacturaSolicitudDTO fact : lstFactDto){
				// si se guardo y por lo tanto el id' que regreso es mayor a cero entonces cargamos los datos de factura.
				if(idSolicitudNueva > Etiquetas.CERO){
					facturaSaveOrUpdate = loadFactura(idSolicitudNueva, fact);
					facturaSaveOrUpdate.setActivo(Etiquetas.UNO_S);
					facturaSaveOrUpdate.setCreacionUsuario(idUsuario);
					facturaSaveOrUpdate.setCreacionFecha(new Date());
				}
				
				// CREAR factura 
				Integer idFactura = facturaService.createFactura(facturaSaveOrUpdate);
				System.out.println(idFactura);
				
				// CREAR Desgloce
				FacturaDesglose fdesgloseSaveOrUpdate = new FacturaDesglose();
				FacturaDesgloseDTO fdto = new FacturaDesgloseDTO();
				fdto.setConcepto(fact.getConcepto());
				fdto.setCuentaContable(cuentaContableService.getCuentaContable(fact.getCuentaContable()));
				fdto.setLocacion(locacionService.getLocacion(fact.getLocacion()));
				fdto.setStrSubTotal(fact.getTotal().toString());
				
				fdesgloseSaveOrUpdate = loadFacturaDesglose(idFactura, fdto);
				
				// guardar desglose
			    facturaDesgloseService.createFacturaDesglose(fdesgloseSaveOrUpdate);
				
				//GuardaArchivo
				guardarArchivosFactura(fact.getFiles(),idSolicitudNueva,idFactura);
			}
			
			// crear variable de sesion para el mensaje de actualzaciï¿½n
			  Integer newrow = Etiquetas.UNO;
			  session.setAttribute("creacion", newrow );

		} //fin.else
         
		if(solicitudDTO.getIdTipoSolicitud().equals(Etiquetas.SOLICITUD_REEMBOLSOS)){
		  return new ModelAndView("redirect:reembolso?id="+idSolicitudNueva);
		}else{
		  return new ModelAndView("redirect:cajaChica?id="+idSolicitudNueva);
		}
	}
	
	private FacturaDesglose loadFacturaDesglose(Integer idFactura, FacturaDesgloseDTO fdto){
		FacturaDesglose fdesglose = new FacturaDesglose();
		fdesglose.setFactura(new Factura(idFactura));
		fdesglose.setLocacion(fdto.getLocacion());
		fdesglose.setCuentaContable(fdto.getCuentaContable());
		fdesglose.setConcepto(fdto.getConcepto());
		fdesglose.setSubtotal(Utilerias.convertStringToBigDecimal(fdto.getStrSubTotal()));
		fdesglose.setCreacionFecha(new Date());
		fdesglose.setActivo(Etiquetas.UNO_S);
		return fdesglose;
	}
	
	/**
	 * @param idSolicitud
	 * @param facturaSolicitudDTO
	 * @return Factura
	 * CARGA LA INFORMACIÓN DE LA FACTURA EN UN OBJETO.
	 */
	private Factura loadFactura(Integer idSolicitud, FacturaSolicitudDTO facturaSolicitudDTO){
		
		Factura factura = new Factura();
		factura.setSolicitud(new Solicitud(idSolicitud));
		Moneda mon = monedaService.getMoneda(facturaSolicitudDTO.getMoneda());
		factura.setMoneda(mon);
		factura.setFactura(facturaSolicitudDTO.getFolio());
		Compania compania = companiaService.getCompania(facturaSolicitudDTO.getIdCompania());
		factura.setCompaniaByIdCompania(compania);
		CuentaContable ccont = cuentaContableService.getCuentaContable(facturaSolicitudDTO.getCuentaContable());
		factura.setCuentaContable(ccont);
		TipoFactura tFact = new TipoFactura();
		tFact.setIdTipoFactura((facturaSolicitudDTO.isConCompFiscal() == true) ? Etiquetas.TIPO_FACTURA_FACTURA : Etiquetas.TIPO_FACTURA_COMPROBANTE );
		factura.setTipoFactura(tFact);
		try {
			if(facturaSolicitudDTO.getFecha() != null){
				factura.setFechaFactura(parseDate(facturaSolicitudDTO.getFecha(), "dd/MM/yyyy"));
			}
		} catch (ParseException e) {
	
		}
		
		if(facturaSolicitudDTO.isConCompFiscal()){
			if(facturaSolicitudDTO.getProveedor() != null){
				Proveedor prov = proveedorService.getProveedor(facturaSolicitudDTO.getProveedor());
				factura.setProveedor(prov);
			}
			else{
				ProveedorLibre existe = proveedorLibreService.existeProveedor(facturaSolicitudDTO.getRFC());
				if(existe != null)
					factura.setProveedorLibre(existe);
				else
					factura.setProveedorLibre(agregaNuevoProveedor(facturaSolicitudDTO));
			}
			factura.setSerieFactura(facturaSolicitudDTO.getSerie());
			factura.setFolioFiscal(facturaSolicitudDTO.getFolioFiscal());
			
			// retenciones
			if (facturaSolicitudDTO.isConRetenciones()) {
				factura.setConRetenciones(Etiquetas.UNO_S);
				factura.setIvaRetenido(facturaSolicitudDTO.getIVARetenido());
				factura.setIsrRetenido(facturaSolicitudDTO.getISRRetenido());
			} else {
				factura.setConRetenciones(Etiquetas.CERO_S);
			}
			
			if (facturaSolicitudDTO.getIEPS() != null) {
				factura.setIeps(facturaSolicitudDTO.getIEPS());
				factura.setPorcentajeIeps(facturaSolicitudDTO.getTasaIeps());
			}
			if (facturaSolicitudDTO.getIVA() != null) {
				factura.setIva(facturaSolicitudDTO.getIVA());
				factura.setPorcentajeIva(facturaSolicitudDTO.getTasaIva());
			}
		}
		factura.setConceptoGasto(facturaSolicitudDTO.getConcepto());
		factura.setSubtotal(facturaSolicitudDTO.getSubTotal());
		factura.setTotal(facturaSolicitudDTO.getTotal());
		factura.setLocacion(locacionService.getLocacion(facturaSolicitudDTO.getLocacion()));
		return factura;
	}
	
	/**
	 * @param facturaSolicitudDTO
	 * @return ProveedorLibre si se guardó correctamente, null si hubo un error al guardar.
	 * CREA UN NUEVO REGISTRO EN LA TABLA DE PROVEEDOR_LIBRE CUANDO EL PROVEEDOR DE LA FACTURA NO EXISTE EN EL SISTEMA
	 */
	private ProveedorLibre agregaNuevoProveedor(FacturaSolicitudDTO facturaSolicitudDTO) {
		ProveedorLibre nuevo = new ProveedorLibre();
		nuevo.setDescripcion(facturaSolicitudDTO.getProveedorLibre());
		nuevo.setRfc(facturaSolicitudDTO.getRFC());
		nuevo.setActivo(Etiquetas.UNO_S);
		nuevo.setCreacionUsuario(idUsuario);
		nuevo.setCreacionFecha(new Date());
		Integer idProveedorLibre = proveedorLibreService.createProveedorLibre(nuevo);
		if(idProveedorLibre != null)
			return nuevo;
		else
			return null;
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
	 * @param request
	 * @param response
	 * GUARDA LA DIRECCIÓN Y BYTES EN SESION DE LOS ARCHIVOS A SUBIR.
	 */
	@RequestMapping(value = "/guardarArchivos", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public @ResponseBody ResponseEntity<String> guardarArchivos(MultipartHttpServletRequest request, HttpServletResponse response) {
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
	
	/**
	 * @author miguelr
	 * @param session
	 * @param nombre
	 * @return OK
	 * VALIDA SI EL NOMBRE DE DOCUMENTO YA EXISTE EN EL ROW.
	 */
	@RequestMapping(value = "/validaNombreDocumento", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> validaNombreDocumento(HttpSession session, @RequestParam String filename,
		   HttpServletRequest request, HttpServletResponse response) {
		
		String res = "true";
		for(FacturaSolicitudDTO factura : solicitudDTO.getLstFactDto()){
			//Si ya está guardado en base de datos o no
			if(factura.getFacturaArchivos() != null){
				for(FacturaArchivo file : factura.getFacturaArchivos()){
					if(file.getDescripcion().equals(filename))
						res = "false";
				}
			}else{
				for(ArchivoDTO file : factura.getFiles()){
					if(file.getOriginalFileName().equals(filename))
						res = "false";
				}
			}
		}
		
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(res, responseHeaders, HttpStatus.CREATED);
	}
	
	/**
	 * @author miguelr
	 * @param session
	 * @param nombre
	 * @return OK
	 * VALIDA FACTURA UNICA LOCAL XML
	 */
	@RequestMapping(value = "/validaXMLLocal", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> validaXMLLocal(HttpSession session, @RequestParam String uuid,
		   HttpServletRequest request, HttpServletResponse response) {
		String res = "true";
		for(FacturaSolicitudDTO factura : solicitudDTO.getLstFactDto()){
			if(factura.isConCompFiscal())
				if(factura.getFolioFiscal().equals(uuid))
					res = "false";
		}
		// respuesta
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(res, responseHeaders, HttpStatus.CREATED);
	}
	
	private List<CuentaContable> getCuentasContablesBySolicitud(Integer idSolicitud) {
		List<CuentaContable> ccPermitidas = new ArrayList<>();
		for(Factura fact : solicitudService.getSolicitud(idSolicitud).getFacturas()){
			if(!ccPermitidas.contains(fact.getCuentaContable()))
				ccPermitidas.add(fact.getCuentaContable());
		}
		return ccPermitidas;
	}
	
	private boolean contieneLocacion(List<Locacion> lcPermitidas, Locacion locacion) {
		boolean contains = false;
		for(Locacion loc : lcPermitidas){
			if (loc.getIdLocacion() == locacion.getIdLocacion()){
				contains = true;
				break;
			}
		}
		return contains;		
	}
	
	private boolean contieneCompania(List<Compania> lstCompanias, Compania compania) {
		boolean contains = false;
		for(Compania comp : lstCompanias){
			if (comp.getIdcompania() == compania.getIdcompania()){
				contains = true;
				break;
			}
		}
		return contains;		
	}
	
	private boolean contieneBeneficiario(List<Usuario> lstBeneficiario, Usuario usuario) {
		boolean contains = false;
		for(Usuario usr : lstBeneficiario){
			if (usr.getIdUsuario() == usuario.getIdUsuario()){
				contains = true;
				break;
			}
		}
		return contains;		
	}
}