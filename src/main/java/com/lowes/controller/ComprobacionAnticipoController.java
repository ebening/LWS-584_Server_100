package com.lowes.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.ArchivoDTO;
import com.lowes.dto.ComprobacionAnticipoDTO;
import com.lowes.dto.ComprobacionAnticipoFacturaDTO;
import com.lowes.dto.ComprobacionDepositoDTO;
import com.lowes.dto.FacturaDesgloseDTO;
import com.lowes.dto.FacturaSolicitudDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.ComprobacionAnticipo;
import com.lowes.entity.ComprobacionAnticipoFactura;
import com.lowes.entity.ComprobacionAnticipoMultiple;
import com.lowes.entity.ComprobacionDeposito;
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
import com.lowes.entity.TipoDocumento;
import com.lowes.entity.TipoFactura;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.AidService;
import com.lowes.service.CategoriaMayorService;
import com.lowes.service.CategoriaMenorService;
import com.lowes.service.CompaniaService;
import com.lowes.service.ComprobacionAnticipoFacturaService;
import com.lowes.service.ComprobacionAnticipoMultipleService;
import com.lowes.service.ComprobacionAnticipoService;
import com.lowes.service.ComprobacionDepositoService;
import com.lowes.service.CuentaContableService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.service.TipoDocumentoService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Scope("session")
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ComprobacionAnticipoController {
	private Integer idUsuario = null;
	
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
	private FacturaService facturaService;
	@Autowired
	private FacturaDesgloseService facturaDesgloseService;
	@Autowired
	private ParametroService parametroService;
	@Autowired
	private UsuarioConfSolicitanteService uConfsolicitanteService;
	@Autowired
	private CuentaContableService cuentaContableService;
	@Autowired
	private AidService aidService;
	@Autowired
	private CategoriaMayorService categoriaMayor;
	@Autowired
	private CategoriaMenorService categoriaMenor;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	@Autowired
	private FacturaArchivoService facturaArchivoService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	@Autowired
	private ComprobacionAnticipoService comprobacionAnticipoService;
	@Autowired
	private ComprobacionAnticipoFacturaService comprobacionAnticipoFacturaService;
	@Autowired
	private ComprobacionDepositoService comprobacionDepositoService;
	@Autowired
	ComprobacionAnticipoMultipleService comprobacionAnticipoMultipleService;
	@Autowired
	private EstadoSolicitudService estadoSolicitudService;
	
	
	private static final Logger logger = Logger.getLogger(ComprobacionAnticipoController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	private UtilController util = new UtilController();

	//Lista de sesion para guardar las facturas capturadas
	private List<FacturaSolicitudDTO> lstFactDto;
	//Lista de sesion para facturas a eliminar
	private List<FacturaSolicitudDTO> lstFactEliminar;
	//Objeto de sesion para solicitud
	private ComprobacionAnticipoDTO comprobacionAnticipoDTO;
	//guarda los archivos de cada factura temporalmente
	private List<ArchivoDTO> currentFiles;
	//guarda la lista de desgloses
	private List<FacturaDesgloseDTO> lstDesgloses;
	
	private Integer anticipoActual = null;
	
	@RequestMapping("/comprobacionAnticipo")
	private ModelAndView comprobacionAnticipo(HttpSession session, Integer ida, Integer id){
		this.idUsuario = usuarioService.getUsuarioSesion().getIdUsuario();
		
		lstFactDto = new ArrayList<>();
		lstFactEliminar = new ArrayList<>();
		lstDesgloses = new ArrayList<>();
		
		//declaraciones de objetos
		HashMap<String, Object> modelo = new HashMap<>();
		comprobacionAnticipoDTO = new ComprobacionAnticipoDTO();
		Solicitud solicitud = new Solicitud();
		Solicitud comprobacion = new Solicitud();
		Integer idEstadoSolicitud = 0;
		Double totalComprobado = 0D;
		Integer tipoProveedor = null;
		Proveedor proveedor = new Proveedor();
		boolean esMultiple = false;
		
		CuentaContable ccNoComprobante = new CuentaContable();
		
		// lista de compa�ias para el combo.
		List<Compania> companiaslst = companiaService.getAllCompania();
		//List<Proveedor> lstProveedores = proveedorService.getAllProveedores();
		
		Usuario usuario = usuarioService.getUsuario(idUsuario);
		
		//locaciones permitidas por usuario
		// configuraciones permitidas por usuario
		// extraer usuario en session para guardar la solicitud.
		Usuario usuarioSession = usuarioService.getUsuarioSesion();
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(usuarioSession.getIdUsuario());
		List<Locacion> locacionesPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO,locacionService.getAllLocaciones(),this.idUsuario);
		
		// Comprueba si tiene locaciones CSC o si es locaci�n de tienda
		comprobacionAnticipoDTO.setEsLocacionCSC(locacionesPermitidas.size() > 1 && comprobacionAnticipoDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA ? true : false);
		
		//Carga cuentas contables
		List<CuentaContable> ccPermitidas = UtilController.getCuentasContablesPermitidasPorUsuario(uconfigSol, cuentaContableService.getAllCuentaContable());

		comprobacionAnticipoDTO.setLcPermitidas(locacionesPermitidas);
		comprobacionAnticipoDTO.setCcPermitidas(ccPermitidas);
		// set la locacion correspondiente al usuario
		comprobacionAnticipoDTO.setLocacion(usuarioSession.getLocacion());
		List<Moneda> monedaList = monedaService.getAllMoneda();
		Solicitud solicitudInicial = null;
		if(anticipoActual != null)
			solicitudInicial = solicitudService.getSolicitud(anticipoActual);
		
		BigDecimal montoDeposito = BigDecimal.ZERO;
		String fechaDeposito = Utilerias.convertDateFormat(new Date());
		List<ComprobacionDeposito> depositos = new ArrayList();
		//si trae ambos parametros se trata de una comprobacion multiple.
		if(ida != null && ida > Etiquetas.CERO && id != null && id > Etiquetas.CERO){
			
			// set id del anticipo en session de clase.
			anticipoActual = ida;	
				
			//va por la solicitud en cuestion con el valor numerico del parametro	
			comprobacion = solicitudService.getSolicitud(ida);
			
			depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(ida);
			
		//si trae parametros ya sea de anticipo o de una comprobacion:
		}else if(ida != null && ida > Etiquetas.CERO || id != null && id > Etiquetas.CERO){
			
			/*** si viene de un anticipo normal para crear una comprobaci�n ****/
			if(ida != null && ida > Etiquetas.CERO){
			    
				// set id del anticipo en session de clase.
				anticipoActual = ida;	
					
				//va por la solicitud en cuestion con el valor numerico del parametro	
				solicitud = solicitudService.getSolicitud(ida);
				
				//tipo proveedor
				tipoProveedor = solicitud.getTipoProveedor().getIdTipoProveedor();

				// si la solicitud aun no tiene el estatus de validada no se puede comprobar.
				// y se redirecciona 
				if(solicitud == null || solicitud.getEstadoSolicitud().getIdEstadoSolicitud() != Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA){
					//return new ModelAndView("/", modelo);
				}
				
				//cargar informacion del anticipo
				comprobacionAnticipoDTO = loadAnticipoComprobacion(solicitud);
				//el saldo cuando aun no se guarda es igual al importe total del anticipo.
				comprobacionAnticipoDTO.setSaldo(solicitud.getMontoTotal().toString());
				comprobacionAnticipoDTO.setIdSolicitudAnticipoSession(solicitud.getIdSolicitud());
				depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(ida);
				
				//para el caso de proveedores con anticipos multiples
				if (solicitud.getProveedor() != null) {
					proveedor = solicitud.getProveedor();
					if(tipoProveedor == Etiquetas.DOS){
						if (proveedor.getPermiteAnticipoMultiple() > Etiquetas.CERO) {
							esMultiple = true;
							comprobacionAnticipoDTO.setEstatusSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE);
							comprobacionAnticipoDTO.setLstAnticipos(esMultiple(idUsuario, ida, Etiquetas.ID_ESTADO_SOLICITUD_PAGADA, Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE));
							comprobacionAnticipoDTO.setLstAnticiposIncluidos(esMultiple(idUsuario, ida, Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE, null));
						}
					}
				}
			}
			
			
			/*** si viene de una compobacion normal guardada ****/
			if(id != null && id > Etiquetas.CERO){
			   
			   comprobacion = solicitudService.getSolicitud(id);
			   idEstadoSolicitud = comprobacion.getEstadoSolicitud().getIdEstadoSolicitud();
			   //tipo proveedor
			   tipoProveedor = comprobacion.getTipoProveedor().getIdTipoProveedor();
			   //BigDecimal saldo = comprobacion.getMontoTotal()
			   comprobacionAnticipoDTO.setSaldo(comprobacion.getMontoTotal().toString());
			   
	           cargarComprobacionGuardada(comprobacion,session);
	           comprobacionAnticipoDTO.setCcPermitidas(ccPermitidas);
	           depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(id);
				//para el caso de proveedores con anticipos multiples
				if (comprobacion.getProveedor() != null) {
					proveedor = comprobacion.getProveedor();
					if(tipoProveedor == Etiquetas.DOS){
						if (proveedor.getPermiteAnticipoMultiple() > Etiquetas.CERO) {
							esMultiple = true;
							comprobacionAnticipoDTO.setEstatusSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE);
							comprobacionAnticipoDTO.setLstAnticipos(esMultiple(idUsuario, id, Etiquetas.ID_ESTADO_SOLICITUD_PAGADA, Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE));
							comprobacionAnticipoDTO.setLstAnticiposIncluidos(esMultiple(idUsuario, id, Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE, null));
						}
					}
				}
			}
			
			

			
		}else{
			
			// se trata de una comprobacion sin anticipo por primera vez
			
		}
		
		//si hay depositos solo deberia traer uno y restarlo al saldo
		Integer idComprobacionDeposito = 0;
		if(comprobacionAnticipoDTO.getSaldo()!=null){
			BigDecimal saldo = new BigDecimal(comprobacionAnticipoDTO.getSaldo());
			for (ComprobacionDeposito comprobacionDeposito : depositos) {
				idComprobacionDeposito = comprobacionDeposito.getIdComprobacionDeposito();
				montoDeposito = comprobacionDeposito.getMontoDeposito();
				fechaDeposito = Utilerias.convertDateFormat(comprobacionDeposito.getFechaDeposito());
				comprobacionAnticipoDTO.setSaldo(saldo.subtract(montoDeposito).toString());
			}
		}
		comprobacionAnticipoDTO.setIdComprobacionDeposito(idComprobacionDeposito);
		comprobacionAnticipoDTO.setFecha_deposito(fechaDeposito);
		comprobacionAnticipoDTO.setImporteDeposito(montoDeposito.toString());
		
		List<FormaPago> formaPagoList = formaPagoService.getAllFormaPago();
		
		
		//revisar si se trata de una modificaci�n para activar mensaje en la vista
		if(session.getAttribute("actualizacion") != null){
			comprobacionAnticipoDTO.setModificacion(Etiquetas.TRUE);
			session.setAttribute("actualizacion", null);
		}else{
			comprobacionAnticipoDTO.setModificacion(Etiquetas.FALSE);
		}
		
		//revisar si se trata de un nuevo registro para activar mensaje en la vista
		if(session.getAttribute("creacion") != null){
			comprobacionAnticipoDTO.setCreacion(Etiquetas.TRUE);
			session.setAttribute("creacion", null);
		}else{
			comprobacionAnticipoDTO.setCreacion(Etiquetas.FALSE);
		}
		
		//Set cuenta contable no deducible para documentos sin comprobante fiscal
		String cuentaContable = parametroService.getParametroByName("ccNoDeducible").getValor();
		Integer ccID = UtilController.getIDByCuentaContable(cuentaContable , cuentaContableService.getAllCuentaContable());
		if(ccID != null){
			ccNoComprobante = cuentaContableService.getCuentaContable(ccID);
		}
		modelo.put("ccNoComprobante", ccNoComprobante);	
		
		//objetos para la vista
		modelo.put("tipoProveedor",tipoProveedor);
		comprobacionAnticipoDTO.setLstFactDto(lstFactDto);
		modelo.put("companiaList", companiaslst);
		modelo.put("locacionesPermitidas", locacionesPermitidas);
		modelo.put("comprobacionAnticipoDTO", comprobacionAnticipoDTO);
		modelo.put("monedaList", monedaList);
		modelo.put("formaPagoList", formaPagoList);
		modelo.put("idEstadoSolicitud", idEstadoSolicitud);
		modelo.put("esMultiple", esMultiple);
		
		modelo.put("fechaCreacionAnticipo", Utilerias.convertDateFormat(solicitudInicial!=null ? solicitudInicial.getCreacionFecha() : new Date()));

		
		UtilController utilc = new UtilController();
		List<CuentaContable> ccontable = cuentaContableService.getAllCuentaContable();
		
		HashMap<String, Object> modeloNoMercancias = utilc.getConXmlDatos(session, null, usuarioSession.getIdUsuario(),
				uConfsolicitanteService, locacionService.getAllLocaciones(), ccontable, aidService, categoriaMayor,
				categoriaMenor, companiaService, proveedorService, monedaService, usuarioService, solicitudService,
				solicitudArchivoService);
		
		HashMap<String, Object> modeloFinal = new HashMap<>();

		modeloFinal.putAll(modeloNoMercancias);
		modeloFinal.putAll(modelo);
		
		return new ModelAndView("comprobacionAnticipo",modeloFinal);	
	}
	
	private List<ComprobacionAnticipoFacturaDTO> esMultiple(Integer idUsuario, Integer ida, Integer idEstadoSolicitudPagada, Integer idEstadoSolicitudMultiple) {
		List<ComprobacionAnticipoFacturaDTO> anticipos = new ArrayList<>();
		Solicitud solicitud = solicitudService.getSolicitud(ida);
		if(anticipoActual != null) ida = anticipoActual;
		Proveedor prov = solicitud.getProveedor();
//		BigDecimal montoTotalMultiple = BigDecimal.ZERO;
		//Si es proveedor y permite anticipo m�ltiple
		if(solicitud.getTipoProveedor().getIdTipoProveedor() == 2 && prov.getPermiteAnticipoMultiple() == Etiquetas.UNO){
			List<Solicitud> solicitudes = solicitudService.getAnticiposMultiplesByEstatusAComprobar(idUsuario, prov.getIdProveedor(), idEstadoSolicitudPagada, idEstadoSolicitudMultiple, anticipoActual);
			for(Solicitud s : solicitudes){
				if(ida != s.getIdSolicitud()){
					ComprobacionAnticipoFacturaDTO a = new ComprobacionAnticipoFacturaDTO();
					a.setIdSolicitud(s.getIdSolicitud());
					a.setFechaPagoStr(Utilerias.convertDateFormat(s.getFechaPago()));
					a.setCreacionFechaStr(Utilerias.convertDateFormat(s.getCreacionFecha()));
					a.setMontoTotal(s.getMontoTotal());
//					montoTotalMultiple = montoTotalMultiple.add(s.getMontoTotal());
					a.setIdEstadoSolicitud(s.getEstadoSolicitud().getIdEstadoSolicitud());
					//Get comprobaciones guardadas
					BigDecimal montoComprobado = BigDecimal.ZERO;
					List<ComprobacionAnticipoFactura> compAnticipo = comprobacionAnticipoFacturaService.getComprobacionByIdAnticipo(s.getIdSolicitud());
					if(!compAnticipo.isEmpty()){
						for(ComprobacionAnticipoFactura compAnt : compAnticipo){
							montoComprobado = montoComprobado.add(compAnt.getMontoFactura());
						}
					}
					a.setMontoComprobado(montoComprobado);
					a.setMontoSaldo(s.getMontoTotal().subtract(montoComprobado));
					a.setMoneda(s.getMoneda());
					a.setConcepto(s.getConceptoGasto());
					anticipos.add(a);
				}			
			}
		}
//		comprobacionAnticipoDTO.setMontoTotalMultiple(montoTotalMultiple);
		return anticipos;
	}

	// metodo ajax para el cambio de estatus a cancelada.
	  @RequestMapping(value = "/cancelarSolicitudComprobacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    private @ResponseBody
	    ResponseEntity<String> cancelarSolicitudComprobacion(HttpSession session, @RequestParam Integer idSolicitud, HttpServletRequest request, HttpServletResponse response) {
		  
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
		@RequestMapping(value = "/addRowCompAsesor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> addRowCompAsesor(HttpSession session, @RequestParam Integer numrows,
				 @RequestParam Integer compania, @RequestParam boolean conComp, @RequestParam Integer locacion, @RequestParam Integer proveedor,
				 @RequestParam String rfcEmisor, @RequestParam String folio, @RequestParam String serie, 
				 @RequestParam String folioFiscal, @RequestParam String fecha, @RequestParam String subTotal,
				 @RequestParam Integer moneda, @RequestParam boolean conRetenciones, @RequestParam String iva, @RequestParam Double tasaIva,
				 @RequestParam String iva_retenido, @RequestParam String ieps, @RequestParam Double tasaIeps, @RequestParam String isr_retenido,
				 @RequestParam String total, @RequestParam String conceptoSol, @RequestParam Integer beneficiario,
				 
				HttpServletRequest request, HttpServletResponse response) {
			
			comprobacionAnticipoDTO.setLocacion(locacionService.getLocacion(locacion));
			comprobacionAnticipoDTO.setMoneda(monedaService.getMoneda(moneda));
			
			if(comprobacionAnticipoDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_CAJA_CHICA)
				if(comprobacionAnticipoDTO.getBeneficiario() == null && beneficiario != -1)
					comprobacionAnticipoDTO.setBeneficiario(beneficiario);
			else if(comprobacionAnticipoDTO.getIdTipoSolicitud() == Etiquetas.SOLICITUD_REEMBOLSOS)
				if(comprobacionAnticipoDTO.getIdSolicitante() == null && beneficiario != -1)
					comprobacionAnticipoDTO.setIdSolicitante(beneficiario);
					
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
				item.setProveedor(proveedor);
				razonSocial = proveedorService.getProveedor(proveedor).getDescripcion();
				item.setRFC(rfcEmisor);
				item.setSerie(serie);
			}
			else{
				item.setIEPS(new BigDecimal(0.0));
				item.setIVA(new BigDecimal(0.0));
				item.setTasaIva(new BigDecimal(0.0));
				item.setTasaIeps(new BigDecimal(0.0));
				//Set cuenta contable no deducible para documentos sin comprobante fiscal
				CuentaContable ccNoComprobante = cuentaContableService.getCCByNumeroCuenta(parametroService.getParametroByName("ccNoDeducible").getValor());
				if(ccNoComprobante != null)
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
			HashMap<String, String> result = new HashMap<String, String>();
			
			result.put("gridFacturas", buildGridFacturas(item, numrows));
			result.put("gridComprobacion", buildGridComprobacion(item, numrows));

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
		 * Peticion ajax que actualiza datos de la vista al controller.
		 * @param session
		 * @param selectedIndex
		 * @param folio
		 * @param fecha
		 * @param subTotal
		 * @param moneda
		 * @param total
		 * @param request
		 * @param response
		 * @return json - Respuesta
		 */
		@RequestMapping(value = "/editRowCompAsesor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> editRowCompAsesor(HttpSession session, @RequestParam String folio, 
				 @RequestParam String fecha, @RequestParam String subTotal, @RequestParam Integer moneda, @RequestParam String total, 
				 @RequestParam Integer selectedIndex, HttpServletRequest request, HttpServletResponse response) {
					
			FacturaSolicitudDTO item = lstFactDto.get(selectedIndex - 1);
			
			item.setFecha(fecha);
			item.setFolio(folio);
			item.setMoneda(moneda);
			item.setSubTotal(Utilerias.convertStringToBigDecimal(subTotal));
			item.setTotal(Utilerias.convertStringToBigDecimal(total));

			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();
			
			result.put("selectedIndex", String.valueOf(selectedIndex));
			result.put("folio", String.valueOf(folio));
			result.put("subTotal", String.valueOf(subTotal));

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
		
		private String buildGridComprobacion(FacturaSolicitudDTO item, int numrows) {
			StringBuilder gridComprobacion = new StringBuilder();
			String razonSocial = "";
			String razonSocial1 = "";
			if(item.isConCompFiscal()) {
				razonSocial = proveedorService.getProveedor(item.getProveedor()).getDescripcion();
			} else {
				if(item.getProveedor() != null)
					razonSocial1 = proveedorService.getProveedor(item.getProveedor()).getDescripcion();
			}
			String num = String.valueOf(numrows);
			Integer numLinea = numrows + 1;
			
			//Linea
			gridComprobacion.append("<tr id="+numrows+" class=\"odd gradeX\">");
			gridComprobacion.append("<td align=\"center\"><div class=\"linea\">" + numLinea + "</div></td>");
			
			// editar/remover
			gridComprobacion.append("<td style=\"width: 15%;\"class=\"center\"><button type=\"button\" style=\"height: 22px; margin-right: 15%;\" class=\"boton_editar btn btn-xs btn-warning editarFila\"><span class=\"glyphicon glyphicon-pencil\"></span></button>"
					+ "<button type=\"button\" style=\"height: 22px;\" class=\"btn btn-xs btn-danger removerFila\"><span class=\"glyphicon glyphicon-trash\"></span></button></td>");
			
			//Concepto
			gridComprobacion.append("<td>"+ item.getConcepto() +"</td>");

			// Factura/Folio
			gridComprobacion.append("<td>" + razonSocial + " " + ((item.getSerie() != null) ? item.getSerie() : "") + " "  +  razonSocial1 + " " + item.getFolio()  + "</td>");
			
			//Subtotal
			gridComprobacion.append("<td><input type=\"text\" id=\"lstFactDto" + num
					+ ".subTotal\" name=\"lstFactDto[" + num
					+ "].subTotal\" class=\"form-control sbts subtotalesNM currencyFormat\" value=\"" + item.getSubTotal() + "\" disabled></td>");
			
			//IVA
			gridComprobacion.append("<td><input type=\"number\" id=\"lstFactDto" + num
					+ ".iva\" name=\"lstFactDto[" + num
					+ "].iva\" class=\"form-control ivasComp currencyFormat\" value=\"" + item.getIVA() + "\" type=\"text\" disabled></td>");						
			//Total
			gridComprobacion.append("<td><input type=\"text\" id=\"lstFactDto" + num
					+ ".total\" name=\"lstFactDto[" + num
					+ "].total\" class=\"form-control currencyFormat\" value=\"" + item.getTotal() + "\" disabled></td>");
			
			gridComprobacion.append("</tr>");
			
			return gridComprobacion.toString();
		}
		

		private String buildGridFacturas(FacturaSolicitudDTO item, int numrows) {
			StringBuilder gridFacturas = new StringBuilder();
			String razonSocial = "";
			if(item.isConCompFiscal())
				razonSocial = proveedorService.getProveedor(item.getProveedor()).getDescripcion();
	
			CuentaContable ccNoComprobante = cuentaContableService.getCCByNumeroCuenta(parametroService.getParametroByName("ccNoDeducible").getValor());
			// Obtener combos filtrador por configuracion de solicitante.
			List<Locacion> lcPermitidas = comprobacionAnticipoDTO.getLcPermitidas();
			// Obtener combos filtrador por configuracion de solicitante.
			Locacion lcSelecc = locacionService.getLocacion(item.getLocacion());
			
			List<CuentaContable> ccPermitidas = null;
			List<UsuarioConfSolicitante> uconfigSol = null;
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
			
			ccPermitidas = getCuentasContablesPermitidasPorLocacion(item.getLocacion(), uconfigSol);

			String num = String.valueOf(numrows);
			Integer numLinea = numrows + 1;

			gridFacturas.append("<tr class=\"odd gradeX\">");
			gridFacturas.append("<td align=\"center\"><div class=\"linea\">" + numLinea + "</div></td>");

			// subtotal
			gridFacturas.append("<td><input type=\"text\" onChange=\"sumSbt()\" id=\"lstFactDto" + num
					+ ".subTotal\" name=\"lstFactDto[" + num
					+ "].subTotal\" class=\"form-control subtotales currencyFormat\" value=\"" + item.getSubTotal() + "\" disabled></td>");
			
			String disabledcsc = (comprobacionAnticipoDTO.getEsLocacionCSC() == true ? "" : "disabled");
			// locaciones
			gridFacturas.append("<td><select id=\"lstFactDto[" + num + "].locacion\" onChange=\"locacionOnChange.call(this)\" name=\"lstFactDto["
					+ num + "].locacion\" class=\"form-control locaciones\" "+disabledcsc+">");
			if(comprobacionAnticipoDTO.getEsLocacionCSC())
			{
				gridFacturas.append("<option value=\"-1\">Seleccione:</option>");
				for (Locacion lc : lcPermitidas) {
					gridFacturas.append("<option value=\"");
					gridFacturas.append(String.valueOf(lc.getIdLocacion()));
					gridFacturas.append("\"> " + lc.getNumeroDescripcionLocacion() + " </option>");
				}
			}
			else{
				gridFacturas.append("<option value=\"");
				gridFacturas.append(String.valueOf(lcSelecc.getIdLocacion()));
				gridFacturas.append("\"> " + lcSelecc.getNumeroDescripcionLocacion() + " </option>");
			}
			gridFacturas.append("</select>");
			gridFacturas.append("</td>");
			
			String disabledcc = (item.isConCompFiscal() ? "" : "disabled");
			// cuenta contables
			gridFacturas.append("<td><select id=\"lstFactDtoCC" + num
					+ "\" onChange=\"ccOnChange.call(this)\" name=\"lstFactDto[" + num
					+ "].cuentaContable\" class=\"form-control ccontable\" "+disabledcc+">");
			if(item.isConCompFiscal()){
				gridFacturas.append("<option value=\"-1\">Seleccione:</option>");
					for (CuentaContable cc : ccPermitidas) {
						gridFacturas.append("<option value=\"");
						gridFacturas.append(String.valueOf(cc.getIdCuentaContable()));
						StringBuilder ccontableDesc = new StringBuilder();
						ccontableDesc.append(cc.getDescripcion());
						ccontableDesc.append("(");
						ccontableDesc.append(cc.getNumeroCuentaContable());
						ccontableDesc.append(")");
						gridFacturas.append("\"> " + ccontableDesc.toString() + " </option>");
					}
			}
			else{
				gridFacturas.append("<option value=\"");
				gridFacturas.append(String.valueOf(ccNoComprobante.getIdCuentaContable()));
//				gridFacturas.append("\"> " + ccNoComprobante.getDescripcion() + " </option>");
				StringBuilder ccontableDesc = new StringBuilder();
				ccontableDesc.append(ccNoComprobante.getDescripcion());
				ccontableDesc.append("(");
				ccontableDesc.append(ccNoComprobante.getNumeroCuentaContable());
				ccontableDesc.append(")");
				gridFacturas.append("\"> " + ccontableDesc.toString() + " </option>");
			}
			gridFacturas.append("</select>");
			gridFacturas.append("</td>");
			
			// Factura/Folio
			gridFacturas.append("<td>" + razonSocial + " " + ((item.getSerie() != null) ? item.getSerie() : "") + " " + item.getFolio() +"</td>");
			
			// concepto
			gridFacturas.append("<td><input id=\"lstFactDto" + num + ".concepto\" onChange=\"conceptoOnChange.call(this)\" name=\"lstFactDto[" + num
					+ "].concepto\" class=\"form-control conceptogrid\" value=\"" + item.getConcepto() + "\" type=\"text\"></td>");
			
			// IVA
			gridFacturas.append("<td><input type=\"number\" id=\"lstFactDto" + num
					+ ".iva\" name=\"lstFactDto[" + num
					+ "].iva\" class=\"form-control ivas currencyFormat\" value=\"" + item.getIVA() + "\" type=\"text\" disabled></td>");
			
			// IEPS
			gridFacturas.append("<td><input type=\"number\" id=\"lstFactDto" + num
					+ ".ieps\" name=\"lstFactDto[" + num
					+ "].ieps\" class=\"form-control ieps currencyFormat\" value=\"" + item.getIEPS() + "\" type=\"text\" disabled></td>");

			// remover
			gridFacturas.append("<td><button type=\"button\" style=\"height: 22px;\" class=\"btn btn-xs btn-danger removerFila\"><span class=\"glyphicon glyphicon-trash\"></span></button></td>");
			
			gridFacturas.append("</tr>");
			
			return gridFacturas.toString();
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
		@RequestMapping(value = "/getTotalSolComp", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> getTotalSol(HttpSession session, @RequestParam Integer numrows,
				HttpServletRequest request, HttpServletResponse response) {		
			BigDecimal total = new BigDecimal(0.0);
			for(FacturaSolicitudDTO rem : lstFactDto){
				total = total.add(rem.getTotal());
			}
			
			//Calculo del saldo
			//comprobacionAnticipoDTO.setSaldo(String.valueOf(comprobacionAnticipoDTO.getMontoTotal().subtract(total)));
			
			// respuesta
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(String.valueOf(total), responseHeaders, HttpStatus.CREATED);
		}
		
		/**
		 * @author miguelr
		 * @param request
		 * @param response
		 * GUARDA LA DIRECCI�N Y BYTES EN SESION DE LOS ARCHIVOS A SUBIR.
		 */
		@RequestMapping(value = "/guardarArchivosComprobacion", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		private @ResponseBody ResponseEntity<String> guardarArchivos(MultipartHttpServletRequest request, HttpServletResponse response) {
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
		 * @param numrows
		 * @param request
		 * @param response
		 * @return OK
		 * ACTUALIZA EL VALOR DE LA CUENTA CONTABLE CUANDO ES MODIFICADA EN EL COMBO DEL GRID.
		 */
		@RequestMapping(value = "/updCuentaContCompAsesor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> updCuentaCont(HttpSession session, @RequestParam Integer numrow,
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
		 * ACTUALIZA EL CONCEPTO DE LA FACTURA EN EL OBJETO DEL CONTROLLER.
		 */
		@RequestMapping(value = "/updConceptoFactCompAsesor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> updConceptoFact(HttpSession session, @RequestParam Integer numrow,
			   @RequestParam String concepto, HttpServletRequest request, HttpServletResponse response) {		
			lstFactDto.get(numrow -1).setConcepto(concepto);
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
		 * @return String con suma total actualizada.
		 * REALIZA LA SUMA DEL MONTO TOTAL CUANDO SE AGREGAN O ELIMINAN FACTURAS DEL GRID.
		 */
		@RequestMapping(value = "/updTotalOnDeleteCompAsesor", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> updTotalOnDelete(HttpSession session, @RequestParam Integer numrow,
				HttpServletRequest request, HttpServletResponse response) {
			BigDecimal total = new BigDecimal(0.0);
			lstFactEliminar.add(lstFactDto.get(numrow-1));
			lstFactDto.remove(numrow-1);
			for(FacturaSolicitudDTO rem : lstFactDto){
				total = total.add(rem.getTotal());
			}
			comprobacionAnticipoDTO.setMontoTotal(new BigDecimal(total.doubleValue()));
			//Set subtotal de facturas en solicitud.
			BigDecimal subTotal = new BigDecimal(0.0);
			for(FacturaSolicitudDTO fs : lstFactDto){
				subTotal = subTotal.add(fs.getSubTotal());
			}
			comprobacionAnticipoDTO.setSubTotal(subTotal);		
			// respuesta
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(String.valueOf(total), responseHeaders, HttpStatus.CREATED);
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
		
		
		// metodo ajax para la carga dinamica de edicion.
		@RequestMapping(value = "/editarGridComp", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> editarGridComp(HttpSession session, @RequestParam int index,
				HttpServletRequest request, HttpServletResponse response) {
			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();
			// consulta
			if (index > Etiquetas.CERO) {
				FacturaSolicitudDTO factura = lstFactDto.get(index - 1);
				result.put("conCompFiscal", String.valueOf(factura.isConCompFiscal()));
				result.put("idProveedor", String.valueOf(factura.getProveedor()));
				result.put("idCompania", String.valueOf(factura.getIdCompania()));
				result.put("rsFactura", String.valueOf(companiaService.getCompania(factura.getIdCompania()).getDescripcion()));
				result.put("idMoneda", String.valueOf(factura.getMoneda()));
				result.put("folioFiscal", factura.getFolioFiscal());
				result.put("total", String.valueOf(factura.getTotal()));
				result.put("subTotal", String.valueOf(factura.getSubTotal()));
				result.put("rfcEmisor", factura.getRFC());
				result.put("serie", factura.getSerie());
				result.put("folio", factura.getFolio());
				result.put("fechaEmision", factura.getFecha());// getFechaEmision());
				//result.put("fechaPago", factura.getFechaPago());
				result.put("iva", factura.getStrIva());
				result.put("tasaIva", String.valueOf(factura.getTasaIva()));
				result.put("ieps", factura.getStrIeps());
				result.put("tasaIeps", String.valueOf(factura.getTasaIeps()));
				result.put("cantidadNeta", String.valueOf(factura.getTotal()));
				result.put("incluyeRetenciones", String.valueOf(factura.isConRetenciones()));
				result.put("ivaRetenido", String.valueOf(factura.getIVARetenido() ==null ? "0.00" : factura.getIVARetenido()));
				result.put("isrRetenido", String.valueOf(factura.getISRRetenido() ==null ? "0.00" : factura.getISRRetenido()));
				result.put("concepto", String.valueOf(factura.getConcepto()));
				result.put("tipoSolicitud",String.valueOf(factura.getTipoSolicitud()));
				
				
				ObjectMapper map = new ObjectMapper();
				if (!result.isEmpty()) {
					try {
						json = map.writeValueAsString(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		
		
		@RequestMapping(value = "/saveFacturaNM", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	    private @ResponseBody
	    ResponseEntity<String> saveFacturaNM(HttpSession session,  
	    	HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "pdf", required = false) MultipartFile pdf, 
	    	@RequestParam(value = "xml", required = false) MultipartFile xml,@RequestParam Integer idCompania, @RequestParam Integer idProveedor, 
	    	@RequestParam String rfcEmisor,@RequestParam String folio,@RequestParam String serie,@RequestParam String fecha,
	    	@RequestParam String strSubTotal, @RequestParam Integer moneda, @RequestParam String iva, @RequestParam String folioFiscal,
	    	@RequestParam Boolean conRetenciones,@RequestParam String iva_retenido,@RequestParam String isr_retenido,@RequestParam String ieps,
	    	@RequestParam String total,@RequestParam String concepto,@RequestParam String numrows, @RequestParam boolean esEdicion, @RequestParam Integer facturaEnEdicion, @RequestParam Integer tipoSolicitud) {

	        String json = null;
	        HashMap<String, String> result = new HashMap<String, String>();
	        //currentFiles = new ArrayList<>();

	        
			FacturaSolicitudDTO item = new FacturaSolicitudDTO();
			
			// se clona el objeto a cargar para mantener los valores que vienen desde la base de datos. como el id de la factura. etc.
			if(esEdicion == true){
			  item = lstFactDto.get(facturaEnEdicion-1);
			}
					
			item.setIdCompania(idCompania);
			item.setConRetenciones(conRetenciones);
			item.setFolioFiscal(folioFiscal);
			item.setIEPS(Utilerias.convertStringToBigDecimal(ieps));
			item.setISRRetenido(Utilerias.convertStringToBigDecimal(isr_retenido));
			item.setIVA(Utilerias.convertStringToBigDecimal(iva));
			item.setIVARetenido(Utilerias.convertStringToBigDecimal(iva_retenido));
			item.setProveedor(idProveedor);
			item.setRFC(rfcEmisor);
			item.setSerie(serie);
			item.setFecha(fecha);
			item.setFolio(folio);
			item.setMoneda(moneda);
			item.setSubTotal(Utilerias.convertStringToBigDecimal(strSubTotal));
			item.setTotal(Utilerias.convertStringToBigDecimal(total));
			//item.setFiles(currentFiles);
			item.setConcepto(concepto);
			item.setTipoSolicitud(tipoSolicitud);
			
			
			Integer index = null; 
			if(esEdicion == false){
				lstFactDto.add(item);
				index = lstFactDto.size() - Etiquetas.UNO;

			}else{
				lstFactDto.set(facturaEnEdicion-1, item);
				index = facturaEnEdicion-1;
			}
			
			
	        
	        result.put("gridComprobacion", buildGridComprobacion(item, index));
	        result.put("index",index.toString());
	        result.put("tipoSolicitud",tipoSolicitud.toString());
	        
	        ObjectMapper map = new ObjectMapper();
	        if (!result.isEmpty()) {
	            try {
	                json = map.writeValueAsString(result);
	                System.out.println("Send Message  :::::::: : " + json);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        HttpHeaders responseHeaders = new HttpHeaders(); 
	        responseHeaders.add("Content-Type", "application/json; charset=utf-8"); 
	        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
	    }
		
		
		@RequestMapping(value = "/saveComprobacionAnticipo", method = RequestMethod.POST)
		private ModelAndView saveComprobacionAnticipo(
				@ModelAttribute("comprobacionAnticipoDTO") ComprobacionAnticipoDTO comprobacionAnticipoDTO,
				HttpSession session) {
			
			Integer idComprobacion = null;
			Integer idSolicitud = null;
			
			//Se carga la solicitud de sesion, si es anticipo (ida) o si es comprobacion (id)
			if(comprobacionAnticipoDTO.getIdSolicitudAnticipoSession() != null)
				idSolicitud = comprobacionAnticipoDTO.getIdSolicitudAnticipoSession();
			else
				idComprobacion = comprobacionAnticipoDTO.getIdSolicitudComprobacionSession();
			
			Solicitud solicitudSaveOrUpdate = new Solicitud();
			Factura facturaSaveOrUpdate = new Factura();
			Solicitud solicitud = new Solicitud();
			Usuario user = usuarioService.getUsuarioSesion();
//			Proveedor proveedor = proveedorService.getProveedor(comprobacionAnticipoDTO.getBeneficiario());
			
			// saber si hay en session una solicitud
			//Solicitud solicitudActual = (Solicitud) session.getAttribute("solicitud");
			
			if (comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) {
				
				/*
		         * SI EL TIPO DE PROVEEDOR ES UNO (ASESOR) ENTONCES EL PROCESO DE GUARDADO ES POR COMPROBANTE
		         * **/
				
				// si cuenta con un id entonces es una solicitud para edicion
				if (comprobacionAnticipoDTO.getIdSolicitudComprobacionSession() != null
						&& comprobacionAnticipoDTO.getIdSolicitudComprobacionSession() > 0) {
					// CORRESPONDE A UN UPDATE DE SOLICITUD
					
					//se cargan los valores de control desde la tabla.
					solicitud = solicitudService.getSolicitud(comprobacionAnticipoDTO.getIdSolicitudComprobacionSession());
					
					// se cargan los datos de la solicitud
					solicitudSaveOrUpdate = loadSolicitud(comprobacionAnticipoDTO);
					solicitudSaveOrUpdate.setCreacionFecha(solicitud.getCreacionFecha());
					solicitudSaveOrUpdate.setCreacionUsuario(solicitud.getCreacionUsuario());
					solicitudSaveOrUpdate.setIdSolicitud(idComprobacion);
					solicitudSaveOrUpdate.setModificacionFecha(new Date());
					solicitudSaveOrUpdate.setModificacionUsuario(idUsuario);
					solicitudSaveOrUpdate.setTipoProveedor(comprobacionAnticipoDTO.getTipoProveedor());
//					solicitudSaveOrUpdate.setUsuarioByIdUsuarioAsesor((comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) ? new Usuario(comprobacionAnticipoDTO.getBeneficiario()) : null);
//					solicitudSaveOrUpdate.setProveedor((comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.DOS) ? new Proveedor(comprobacionAnticipoDTO.getBeneficiario()) : null);
					solicitudSaveOrUpdate.setProveedor(new Proveedor(comprobacionAnticipoDTO.getBeneficiario()));
					// solicitud service actualizar.
					solicitudSaveOrUpdate = solicitudService.updateSolicitud(solicitudSaveOrUpdate);

					if(solicitudSaveOrUpdate.getIdSolicitud() > Etiquetas.CERO){
						//Se carga lista de facturas
						for(FacturaSolicitudDTO fact : lstFactDto){
							facturaSaveOrUpdate = loadFactura(solicitudSaveOrUpdate.getIdSolicitud(), fact);
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
							if(fact.getFiles() != null)
								guardarArchivosFactura(fact.getFiles(),solicitudSaveOrUpdate.getIdSolicitud(),idFactura);
						}
						
						//Se eliminan facturas al guadar
						for(FacturaSolicitudDTO factElim : lstFactEliminar){
							//Borrado de archivos en bdd
							List<FacturaArchivo> currFactFiles = factElim.getFacturaArchivos();
							for(FacturaArchivo archivoFact : currFactFiles){
								//si es borrado l�gico
								//af.setActivo((short) 0);
								//facturaArchivoService.updateFacturaArchivo(af);
								
								//borrado permanente de bdd
								facturaArchivoService.deleteFacturaArchivo(archivoFact);
							}
							//Borrado de registro Desgloce
							if(factElim.getIdFactura()!=null){
								facturaDesgloseService.deleteAllByIdFactura(factElim.getIdFactura());
								
								//Borrado de registro Factura
								Factura facElim = facturaService.getFactura(factElim.getIdFactura());
								facturaService.deleteFactura(facElim);
							}
						}
					}
					// crear variable de sesion para el mensaje de actualzaci�n
					  Integer updt = Etiquetas.UNO;
					  session.setAttribute("actualizacion", updt );
				} else {
					
					// SE CREARA LA SOLICITUD POR PRIMERA VEZ
					
					//se cargan los valores generales de la solicitud.
					solicitud = loadSolicitud(comprobacionAnticipoDTO);
					//valores de control de la tabla.
					solicitud.setCreacionFecha(new Date());
					solicitud.setCreacionUsuario(user.getIdUsuario());
					
					solicitud.setTipoProveedor(comprobacionAnticipoDTO.getTipoProveedor());
//					solicitud.setUsuarioByIdUsuarioAsesor((comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) ? new Usuario(comprobacionAnticipoDTO.getBeneficiario()) : null);
//					solicitud.setProveedor((comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.DOS) ? new Proveedor(comprobacionAnticipoDTO.getBeneficiario()) : null);
					solicitud.setProveedor( new Proveedor(comprobacionAnticipoDTO.getBeneficiario()));
					
					
					//creacion.
					idComprobacion = solicitudService.createSolicitud(solicitud);
					
					//guardar relacion comprobacion-anticipo
					guardarRelacionComprobacionAnticipo(idComprobacion,anticipoActual);
					
					if (idComprobacion != null && idComprobacion > 0) {
						if (lstFactDto != null && !lstFactDto.isEmpty()) {
							for (FacturaSolicitudDTO factura : lstFactDto) {
								
								//CREAR Factura
								Factura f = new Factura();
								f = loadFactura(idComprobacion, factura);
								f.setCreacionUsuario(user.getIdUsuario());
								f.setCreacionFecha(new Date());
								f.setActivo(Etiquetas.UNO_S);
								
								Integer idFactura = facturaService.createFactura(f);
								
								// CREAR Desglose
								FacturaDesglose fdesgloseSaveOrUpdate = new FacturaDesglose();
								FacturaDesgloseDTO fdto = new FacturaDesgloseDTO();
								fdto.setConcepto(factura.getConcepto());
								fdto.setCuentaContable(cuentaContableService.getCuentaContable(factura.getCuentaContable()));
								fdto.setLocacion(locacionService.getLocacion(factura.getLocacion()));
								fdto.setStrSubTotal(factura.getTotal().toString());
								
								fdesgloseSaveOrUpdate = loadFacturaDesglose(idFactura, fdto);
								
								Integer idDesglose = facturaDesgloseService.createFacturaDesglose(fdesgloseSaveOrUpdate);
								
								if(factura.getFiles() != null)
									guardarArchivosFactura(factura.getFiles(), idComprobacion, idFactura);
								
							}
						}
						
					}
					// crear variable de sesion para el mensaje de actualzaci�n
					  Integer newrow = Etiquetas.UNO;
					  session.setAttribute("creacion", newrow );
				}
			
				
			} else if (comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.DOS) {
				 

				/*
		         * SI EL TIPO DE PROVEEDOR ES DOS (PROVEEDOR) ENTONCES EL PROCESO DE GUARDADO ES POR NO MERCANCIAS CON Y SIN XML
		         * **/
				
					
				// si cuenta con un id entonces es una solicitud para edicion
				if (comprobacionAnticipoDTO.getIdSolicitudComprobacionSession() != null
						&& comprobacionAnticipoDTO.getIdSolicitudComprobacionSession() > 0) {
					// update
					
					//se cargan los valores generales de la solicitud.
					// se cargan los datos de la solicitud
					
					solicitud = solicitudService.getSolicitud(comprobacionAnticipoDTO.getIdSolicitudComprobacionSession());
					
					solicitudSaveOrUpdate = loadSolicitudNM(comprobacionAnticipoDTO);
					solicitudSaveOrUpdate.setCreacionFecha(solicitud.getCreacionFecha());
					solicitudSaveOrUpdate.setCreacionUsuario(solicitud.getCreacionUsuario());
					solicitudSaveOrUpdate.setIdSolicitud(idComprobacion);
					solicitudSaveOrUpdate.setModificacionFecha(new Date());
					solicitudSaveOrUpdate.setModificacionUsuario(idUsuario);
					solicitudSaveOrUpdate.setTipoProveedor(comprobacionAnticipoDTO.getTipoProveedor());
//					solicitudSaveOrUpdate.setUsuarioByIdUsuarioAsesor((comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) ? new Usuario(comprobacionAnticipoDTO.getBeneficiario()) : null);
//					solicitudSaveOrUpdate.setProveedor((comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.DOS) ? new Proveedor(comprobacionAnticipoDTO.getBeneficiario()) : null);
					solicitudSaveOrUpdate.setProveedor(new Proveedor(comprobacionAnticipoDTO.getBeneficiario()));
					
					// solicitud service actualizar.
					solicitudSaveOrUpdate = solicitudService.updateSolicitud(solicitudSaveOrUpdate);
					
					if(solicitudSaveOrUpdate != null && solicitudSaveOrUpdate.getIdSolicitud() > 0){
						
						//Se carga lista de facturas
						Integer index = 0;
						for(FacturaSolicitudDTO fact : lstFactDto){
							
							facturaSaveOrUpdate = loadFacturaNM(solicitudSaveOrUpdate.getIdSolicitud(),fact);
							facturaSaveOrUpdate.setActivo(Etiquetas.UNO_S);
							facturaSaveOrUpdate.setCreacionUsuario(idUsuario);
							facturaSaveOrUpdate.setCreacionFecha(new Date());
							facturaSaveOrUpdate.setConceptoGasto(fact.getConcepto());
							
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
							}
							if(comprobacionAnticipoDTO.isHasChange()){//para saber si hubo cambios en el modal de no mercancias
								// eliminar desgloses guardados y reemplazarlos.
								facturaDesgloseService.deleteAllByIdFactura(idFactura);
								
								// CREAR Desglose
	                            FacturaDesglose desglose = new FacturaDesglose();
								// si la factura tiene desgloses se guardan.
								if (idFactura > Etiquetas.CERO && lstDesgloses != null
										&& lstDesgloses.size() > Etiquetas.CERO) {
									for (FacturaDesgloseDTO fdto : lstDesgloses) {
										if(fdto.getIndexFactura() == index){
											//cargar datos del desglose 
											desglose = loadFacturaDesglose(idFactura, fdto);
											// guardar desglose
											facturaDesgloseService.createFacturaDesglose(desglose);
										}
									}
								}
							}
							index++;
						}
						
						//Se eliminan facturas al guadar
						for(FacturaSolicitudDTO factElim : lstFactEliminar){
							//Borrado de archivos en bdd
							List<FacturaArchivo> currFactFiles = factElim.getFacturaArchivos();
							if(currFactFiles!=null){
								for(FacturaArchivo archivoFact : currFactFiles){
									//si es borrado l�gico
									//af.setActivo((short) 0);
									//facturaArchivoService.updateFacturaArchivo(af);
									
									//borrado permanente de bdd
									facturaArchivoService.deleteFacturaArchivo(archivoFact);
								}
							}
							if(factElim.getIdFactura()!=null){
								//Borrado de registro Desgloce
								facturaDesgloseService.deleteAllByIdFactura(factElim.getIdFactura());
								comprobacionAnticipoFacturaService.deleteBySolicitudComprobacion(idComprobacion);
								
								//Borrado de registro Factura
								Factura facElim = facturaService.getFactura(factElim.getIdFactura());
								facturaService.deleteFactura(facElim);
							}
						}
						
					}
					
					// crear variable de sesion para el mensaje de actualzaci�n
					  Integer updt = Etiquetas.UNO;
					  session.setAttribute("actualizacion", updt );
				} else {
		            
					// SE CREARA POR PRIMERA VEZ
					
					//se cargan los valores generales de la solicitud.
					solicitud = loadSolicitudNM(comprobacionAnticipoDTO);
					//valores de control de la tabla.
					solicitud.setCreacionFecha(new Date());
					solicitud.setCreacionUsuario(user.getIdUsuario());
					solicitud.setTipoProveedor(comprobacionAnticipoDTO.getTipoProveedor());
					solicitud.setProveedor((comprobacionAnticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.DOS) ? new Proveedor(comprobacionAnticipoDTO.getBeneficiario()) : null);
					
					//creacion de la comprobacion
					idSolicitud = solicitudService.createSolicitud(solicitud);
					idComprobacion = idSolicitud;
					
					// guardar relacion solicitudComprobacion - anticipo
					guardarRelacionComprobacionAnticipo(idSolicitud, anticipoActual);
					//CAMBIAR el id de Anticipo por comprobacion en tabla comprobacion_deposito
					ComprobacionDepositoDTO comprobacionDepositoDTO = new ComprobacionDepositoDTO();
//					Integer  id = this.saveDeposito(comprobacionDepositoDTO);
					List<ComprobacionDeposito> listComDep = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(anticipoActual);
					if(listComDep!=null && !listComDep.isEmpty()){
						comprobacionDepositoDTO.setIdComprobacionDeposito(listComDep.get(0).getIdComprobacionDeposito());
						ComprobacionDeposito deposito = comprobacionDepositoService.getComprobacionDeposito(comprobacionDepositoDTO.getIdComprobacionDeposito());
						deposito.setSolicitud(solicitud);
				    	comprobacionDepositoService.updateComprobacionDeposito(deposito);
					}
			    	
					
					if (idSolicitud != null && idSolicitud > 0) {
						if (lstFactDto != null && lstFactDto.isEmpty() == false) {
							Integer index = 0;
							for (FacturaSolicitudDTO factura : lstFactDto) {
								
								Factura f = new Factura();
								f = loadFacturaNM(idSolicitud, factura);
								f.setCreacionUsuario(user.getIdUsuario());
								f.setCreacionFecha(new Date());
								f.setActivo(Etiquetas.UNO_S);
								f.setConceptoGasto(factura.getConcepto());
								
								Integer idFactura = facturaService.createFactura(f);
								
								//si el proveedor del anticipo es multiple guardar la reelacion con la factura en cuestion.
								Solicitud anticipo = solicitudService.getSolicitud(comprobacionAnticipoDTO.getIdSolicitudAnticipoSession());
								if(anticipo.getProveedor().getPermiteAnticipoMultiple() == Etiquetas.UNO_S && factura.isFacturaMultiple() == true){
									guardarRelacionComprobacionAnticipoDinamico(idSolicitud, comprobacionAnticipoDTO.getIdSolicitudAnticipoSession(), idFactura, factura.getMontoParcialFactura());
								}
								
								//DESGLOSES
								FacturaDesglose desglose = new FacturaDesglose();
								
								// si la factura tiene desgloses se guardan.
								if (idFactura > Etiquetas.CERO && lstDesgloses != null
										&& lstDesgloses.size() > Etiquetas.CERO) {
									for (FacturaDesgloseDTO fdto : lstDesgloses) {
										if(fdto.getIndexFactura() == index){
											//cargar datos del desglose 
											desglose = loadFacturaDesglose(idFactura, fdto);
											// guardar desglose
											facturaDesgloseService.createFacturaDesglose(desglose);
										}
									}
								}
								
								index++;
							}
						}
					}
					// crear variable de sesion para el mensaje de actualzaci�n
					  Integer newrow = Etiquetas.UNO;
					  session.setAttribute("creacion", newrow );
					
				}
			}
	
			return new ModelAndView("redirect:comprobacionAnticipo?id="+idComprobacion+"&saved=true");
		}
		
		
		/**
		 * @param idSolicitud
		 * @param facturaSolicitudDTO
		 * @return Factura
		 * CARGA LA INFORMACI�N DE LA FACTURA EN UN OBJETO.
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
				factura.setFechaFactura(Utilerias.parseDate(facturaSolicitudDTO.getFecha(), "dd/MM/yyyy"));
			} catch (ParseException e) {
		
			}
			
			if(facturaSolicitudDTO.isConCompFiscal()){
				Proveedor prov = proveedorService.getProveedor(facturaSolicitudDTO.getProveedor());
				factura.setProveedor(prov);
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
		 * @author Adinfi
		 * @param solicitudDTO
		 * @return Solicitud - Objeto con los datos de la solicitud.
		 * CARGA LA INFORMACI�N DE LA SOLICITUD EN UN OBJETO TIPO SOLICITUD PARA SU INSERCI�N A BDD.
		 */
		private Solicitud loadSolicitud(ComprobacionAnticipoDTO comprobacionAnticipoDTO) {
			Solicitud sol = new Solicitud();
			Usuario user = usuarioService.getUsuarioSesion();

			// definir tipo de solicitud
			sol.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO));
			sol.setMontoTotal(Utilerias.convertStringToBigDecimal(comprobacionAnticipoDTO.getStrMontoTotal()));
			
//			sol.setUsuarioByIdUsuario(new Usuario(comprobacionAnticipoDTO.getBeneficiario()));
//			sol.setUsuarioByIdUsuarioSolicita(new Usuario(comprobacionAnticipoDTO.getBeneficiario()));
		    sol.setUsuarioByIdUsuario(user);
			sol.setUsuarioByIdUsuarioSolicita(user);
			
			sol.setConceptoGasto(comprobacionAnticipoDTO.getConcepto());
			sol.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.UNO));
			sol.setMoneda(comprobacionAnticipoDTO.getMoneda());
			sol.setCompania(comprobacionAnticipoDTO.getCompania());
			sol.setLocacion(comprobacionAnticipoDTO.getLocacion());
			sol.setFormaPago(comprobacionAnticipoDTO.getFormaPago());
			
			return sol;
		}
		
		
		private Solicitud loadSolicitudNM(ComprobacionAnticipoDTO comprobacionAnticipoDTO) {
			
			this.idUsuario = usuarioService.getUsuarioSesion().getIdUsuario();
			
			Solicitud sol = new Solicitud();

			sol.setActivo(Etiquetas.UNO_S);
			sol.setMoneda(comprobacionAnticipoDTO.getMoneda());
			sol.setCompania(comprobacionAnticipoDTO.getCompania());
			sol.setFormaPago(comprobacionAnticipoDTO.getFormaPago());
			
			// definir tipo de solicitud
			sol.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_COMPROBACION_ANTICIPO));
			//sol.setMontoTotal(Utilerias.convertStringToBigDecimal(comprobacionAnticipoDTO.getImporteTotal()));
			sol.setMontoTotal(Utilerias.convertStringToBigDecimal(comprobacionAnticipoDTO.getStrMontoTotal()));

			
			sol.setUsuarioByIdUsuario(new Usuario(idUsuario));
			sol.setUsuarioByIdUsuarioSolicita(new Usuario(idUsuario));
			
			sol.setConceptoGasto(comprobacionAnticipoDTO.getConcepto());
			sol.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.UNO));
			
			sol.setLocacion(comprobacionAnticipoDTO.getLocacion());
			
			
			return sol;
		}
		
		private Factura loadFacturaNM(Integer idSolicitud, FacturaSolicitudDTO facturaSolicitudDTO){
			
			Factura factura = new Factura();
			
			factura.setSolicitud(new Solicitud(idSolicitud));
			factura.setMoneda(new Moneda(facturaSolicitudDTO.getMoneda()));
			factura.setFactura(facturaSolicitudDTO.getFolio());
			factura.setCompaniaByIdCompania(new Compania(facturaSolicitudDTO.getIdCompania()));
			factura.setProveedor(new Proveedor(facturaSolicitudDTO.getProveedor()));
			factura.setSerieFactura(facturaSolicitudDTO.getSerie());
			factura.setFolioFiscal(facturaSolicitudDTO.getFolioFiscal());
			
			if(facturaSolicitudDTO.getTipoFactura() == null){
				// tipo de factura standar
				factura.setTipoFactura(new TipoFactura(Etiquetas.UNO));
			}else{
				factura.setTipoFactura(new TipoFactura(facturaSolicitudDTO.getTipoFactura()));
			}
			
			try {
				factura.setFechaFactura(Utilerias.parseDate(facturaSolicitudDTO.getFecha(), "dd/MM/yyyy"));
			} catch (ParseException e) {
		
			}
			
			factura.setSubtotal(Utilerias.convertStringToBigDecimal(facturaSolicitudDTO.getSubTotal().toString()));
			
			factura.setTotal(Utilerias.convertStringToBigDecimal(facturaSolicitudDTO.getTotal().toString()));

			if (facturaSolicitudDTO.getIEPS() != null) {
				factura.setIeps(Utilerias.convertStringToBigDecimal(facturaSolicitudDTO.getIEPS().toString()));
			}
			if (facturaSolicitudDTO.getIVA() != null) {
				factura.setIva(Utilerias.convertStringToBigDecimal(facturaSolicitudDTO.getIVA().toString()));
			}

			// retenciones
			if (facturaSolicitudDTO.isConRetenciones()) {
				factura.setConRetenciones(Etiquetas.UNO_S);
				factura.setIvaRetenido(facturaSolicitudDTO.getIVARetenido());
				factura.setIsrRetenido(facturaSolicitudDTO.getISRRetenido());
			} else {
				factura.setConRetenciones(Etiquetas.CERO_S);
			}

			return factura;
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
		 * @param files
		 * @param idSolicitud
		 * @param idFactura
		 * @return true si se guard� correctamente, false si hubo un error al guardar.
		 * REALIZA EL GUARDADO F�SICO DE LOS ARCHIVOS.
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
		
		
		
		@RequestMapping(value = "/addRowConXMLComprobacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> addRow(HttpSession session, @RequestParam Integer numrows,@RequestParam Integer tipoSolicitud,@RequestParam Integer idSolicitud,@RequestParam Integer idSolicitante,
				HttpServletRequest request, HttpServletResponse response) {

			String json = null;
			StringBuilder row2 = new StringBuilder();
			FacturaDesgloseDTO desglose = new FacturaDesgloseDTO();
			
			desglose.setUsuarioSolicitante(idSolicitante);
			desglose.setTipoSolicitud(tipoSolicitud);
	        
			List<UsuarioConfSolicitante> uconfigSol = new ArrayList<>();
			Integer idLocacionUsuario = null;
			if(idSolicitante != null && idSolicitante != -1){
			// configuraciones permitidas por solicitante seleccionado
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idSolicitante);
			idLocacionUsuario = usuarioService.getUsuario(idSolicitante).getLocacion().getIdLocacion();
			}else{
			// configuraciones permitidas por usuario en sesion
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
			idLocacionUsuario = usuarioService.getUsuario(idUsuario).getLocacion().getIdLocacion();
			}
			
			// filtrar locaciones por tipo de solicitud.
			// Obtener combos filtrador por configuracion de solicitante.
			List<Locacion> lcPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol,tipoSolicitud,locacionService.getAllLocaciones(),this.idUsuario);

            
			//numero aleatoreo para serializar los inputs.
			String num = Utilerias.getRandomNum();
			
			Integer numLinea = numrows + 1;

			row2.append("<tr class=\"odd gradeX\">");
			row2.append("<td align=\"center\"><div class=\"linea\">" + numLinea + "</div>");
			row2.append("<input class=\"objectInput\" type=\"hidden\"  value=\""+num+"\"/>");
			row2.append("</td>");
			
			desglose.setIndexInput(Integer.parseInt(num));

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
				ccPermitidasPorUsuario = getCuentasContablesPermitidasPorLocacion(idLocacionUsuario, uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario));
			}
			// cuenta contables
			row2.append("<td><select id=\"facturaDesgloseList" + num
					+ ".cuentaContable.idCuentaContable\" name=\"facturaDesgloseList[" + num
					+ "].cuentaContable.idCuentaContable\" class=\"form-control ccontable\">");
			row2.append("<option value=\"-1\">Seleccione:</option>");
			
			// sin xml cuentas precargadas.
			if(idSolicitud == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
				 Integer idCuentaContableNoND = UtilController.getIDByCuentaContable(parametroService.getParametroByName("ccNoDeducible").getValor(), cuentaContableService.getAllCuentaContable());
				 CuentaContable cc = cuentaContableService.getCuentaContable(idCuentaContableNoND);
				 row2.append("<option selected value=\""+cc.getIdCuentaContable()+"\"> "+cc.getNumeroDescripcionCuentaContable()+" </option>");
			}
			row2.append("</select>");
			row2.append("</td>");

			// concepto
			row2.append("<td><input maxlength=\"500\" id=\"facturaDesgloseList" + num + ".concepto\" name=\"facturaDesgloseList[" + num
					+ "].concepto\" class=\"form-control conceptogrid\" value=\"\" type=\"text\"></td>");

			
			// remover
			row2.append("<td><button value=\""+num+"\" type=\"button\" class=\"btn btn-danger removerFilaDesglose\">Remover</button></td>");
			row2.append("</tr>");
			
			
			// se agrega el desglose a la lista de desgloses.
			lstDesgloses.add(desglose);

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
		
		
		@RequestMapping(value = "/updateDesgloseListNM", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> updateDesgloseListNM(HttpSession session,
				@RequestParam Integer indice,@RequestParam String subtotal,@RequestParam Integer locacion,
				@RequestParam Integer cuentaContable,@RequestParam String concepto,@RequestParam Integer indexfactura, HttpServletRequest request, HttpServletResponse response) {
			
			String json = null;
			String row2 = "ok";
			
	
			//cargar los elementos del desglose al objeto especifico
			for(FacturaDesgloseDTO fd : lstDesgloses){
				if(fd.getIndexInput() == indice && fd.getIndexFactura() == null){
					fd.setStrSubTotal(subtotal);
					fd.setLocacion(new Locacion(locacion));
					fd.setCuentaContable(new CuentaContable(cuentaContable));
					fd.setConcepto(concepto);
					// si el index de la factura es null entonces es la primera = 0
					fd.setIndexFactura(indexfactura);
					break;
				}else if(fd.getIndexInput() == indice && fd.getIndexFactura() == indexfactura){
					//update cuando ya se guardo el numero de factura
					fd.setStrSubTotal(subtotal);
					fd.setLocacion(new Locacion(locacion));
					fd.setCuentaContable(new CuentaContable(cuentaContable));
					fd.setConcepto(concepto);
					break;
				}
			}
			
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
		
		// metodo ajax para la carga dinamica de edicion.
		@RequestMapping(value = "/editarDesgloses", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
		private @ResponseBody ResponseEntity<String> editarDesgloses(HttpSession session, @RequestParam int index,
				HttpServletRequest request, HttpServletResponse response) {
			String json = null;
			HashMap<String, String> result = new HashMap<String, String>();
			StringBuilder desgloseshtml = new StringBuilder();
			// consulta
			if (index > Etiquetas.CERO) {
	
				//cargar los elementos del desglose al objeto especifico
				//index: numero de filas ej. fila 1 : 1-1 = 0  posicion en la lista de objetos.
				for(FacturaDesgloseDTO fd : lstDesgloses){
					if(fd.getIndexFactura() != null && fd.getIndexFactura() == index-1){
						desgloseshtml.append(generarFilaDesglose(fd));
					}
				}
				
				result.put("desgloses", desgloseshtml.toString());
				
				ObjectMapper map = new ObjectMapper();
				if (!result.isEmpty()) {
					try {
						json = map.writeValueAsString(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Content-Type", "application/json; charset=utf-8");
			return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
		}
		
		
		private String generarFilaDesglose(FacturaDesgloseDTO facturaDesgloseDTO){	
			
			String json = null;
			StringBuilder row2 = new StringBuilder();
	        
			List<UsuarioConfSolicitante> uconfigSol = new ArrayList<>();
			Integer idLocacionUsuario = null;
			if(facturaDesgloseDTO.getUsuarioSolicitante() != null && facturaDesgloseDTO.getUsuarioSolicitante() != -1){
			// configuraciones permitidas por solicitante seleccionado
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(facturaDesgloseDTO.getUsuarioSolicitante());
			idLocacionUsuario = usuarioService.getUsuario(facturaDesgloseDTO.getUsuarioSolicitante()).getLocacion().getIdLocacion();
			}else{
			// configuraciones permitidas por usuario en sesion
			uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);
			idLocacionUsuario = usuarioService.getUsuario(idUsuario).getLocacion().getIdLocacion();
			}
			
			// filtrar locaciones por tipo de solicitud.
			// Obtener combos filtrador por configuracion de solicitante.
			List<Locacion> lcPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol,facturaDesgloseDTO.getTipoSolicitud(),locacionService.getAllLocaciones(),this.idUsuario);


			String num = String.valueOf(facturaDesgloseDTO.getIndexInput());
			Integer numLinea = facturaDesgloseDTO.getIndexInput() + 1;
			
			row2.append("<tr class=\"odd gradeX\">");
			row2.append("<td align=\"center\"><div class=\"linea\">" + numLinea + "</div>");
			row2.append("<input class=\"objectInput\" type=\"hidden\"  value=\""+num+"\"/>");
			row2.append("</td>");
			

			// subtotal
			row2.append("<td><input step=\"any\" onblur=\"sumSbt()\" id=\"facturaDesgloseList" + num
					+ ".strSubTotal\" name=\"facturaDesgloseList[" + num
					+ "].strSubTotal\" class=\"form-control subtotales currencyFormat sbtGrid\" value=\""+facturaDesgloseDTO.getStrSubTotal()+"\" type=\"text\"></td>");

			// locaciones
			String accionSeleccion = "";
			if(facturaDesgloseDTO.getTipoSolicitud() != Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
				accionSeleccion =  "onChange=\"actualizarCuentas(this.id)\"";
			}
			row2.append("<td><select "+accionSeleccion+" id=\"facturaDesgloseList" + num + ".locacion.idLocacion\" name=\"facturaDesgloseList["
					+ num + "].locacion.idLocacion\" class=\"form-control locaciones\">");
			row2.append("<option value=\"-1\">Seleccione:</option>");
			for (Locacion loc : lcPermitidas) {
				
				// locacion guardada en objeto.
				Integer idLocacion = facturaDesgloseDTO.getLocacion().getIdLocacion();
				
				if(facturaDesgloseDTO.getTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML && loc.getIdLocacion() == idLocacionUsuario){
					row2.append("<option selected  value=\"");
				}else{
					if(idLocacion == loc.getIdLocacion()){
						row2.append("<option selected  value=\"");
					}else{
						row2.append("<option value=\"");
					}
				}
				row2.append(String.valueOf(loc.getIdLocacion()));
				row2.append("\"> " + loc.getNumeroDescripcionLocacion() + " </option>");
			}
			row2.append("</select>");
			row2.append("</td>");
			
			// si el tipo de solicitud es sin xml no mercancias, se cargan las cuentas contables por 
			//locacion de usuario seleccionada automatica.
			List<CuentaContable> ccPermitidasPorUsuario = null;
			if(facturaDesgloseDTO.getTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
				ccPermitidasPorUsuario = getCuentasContablesPermitidasPorLocacion(idLocacionUsuario, uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario));
			}
			// cuenta contables
			row2.append("<td><select id=\"facturaDesgloseList" + num
					+ ".cuentaContable.idCuentaContable\" name=\"facturaDesgloseList[" + num
					+ "].cuentaContable.idCuentaContable\" class=\"form-control ccontable\">");
			
			
			// sin xml cuentas precargadas.
			if(facturaDesgloseDTO.getTipoSolicitud() == Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML){
				 Integer idCuentaContableNoND = UtilController.getIDByCuentaContable(parametroService.getParametroByName("ccNoDeducible").getValor(), cuentaContableService.getAllCuentaContable());
				 CuentaContable cc = cuentaContableService.getCuentaContable(idCuentaContableNoND);
				 row2.append("<option selected value=\""+cc.getIdCuentaContable()+"\"> "+cc.getNumeroDescripcionCuentaContable()+" </option>");
			}else{
				Integer idLocacion = facturaDesgloseDTO.getLocacion().getIdLocacion();
				String ccHtml = UtilController.getCuentasContablesHTML(idLocacion,uconfigSol,facturaDesgloseDTO.getCuentaContable().getIdCuentaContable(),facturaDesgloseDTO.getTipoSolicitud());
				row2.append(ccHtml);
			}
			row2.append("</select>");
			row2.append("</td>");

			// concepto
			row2.append("<td><input maxlength=\"500\" id=\"facturaDesgloseList" + num + ".concepto\" name=\"facturaDesgloseList[" + num
					+ "].concepto\" class=\"form-control conceptogrid\" value=\""+facturaDesgloseDTO.getConcepto()+"\" type=\"text\"></td>");

			
			// remover
			row2.append("<td><button value=\""+num+"\" type=\"button\" class=\"btn btn-danger removerFilaDesglose\">Remover</button></td>");
			row2.append("</tr>");
			
			
			return row2.toString();
		}
		
		private List<FacturaDesgloseDTO> getDesglosesByFactura(Integer index){
			
			List<FacturaDesgloseDTO> desgloses = new ArrayList<>();
			
			for(FacturaDesgloseDTO desglose : lstDesgloses){
				if(desglose.getIndexFactura() == index){
					desgloses.add(desglose);
				}
			}
			
			return desgloses;
		}
	
		  
		  private Integer saveDeposito(ComprobacionDepositoDTO comprobacionDepositoDTO){
			  
//			  boolean creado = false;
			  
			  ComprobacionDeposito cd = new ComprobacionDeposito( );
			    
			    // solicitud a la que se le va a crear el deposito.
			    cd.setSolicitud(comprobacionDepositoDTO.getSolicitud());
			    cd.setArchivo(comprobacionDepositoDTO.getArchivo());
			    cd.setMontoDeposito(Utilerias.convertStringToBigDecimal(comprobacionDepositoDTO.getMontoDeposito()));
			    cd.setPidCm(comprobacionDepositoDTO.getPidCm());
			    cd.setActivo(Etiquetas.UNO_S);
			    cd.setCreacionFecha(new Date());
		    	cd.setCreacionUsuario(usuarioService.getUsuarioSesion().getIdUsuario());
			    cd.setFechaDeposito(comprobacionDepositoDTO.getFecha_deposito());
			    cd.setDescripcion(comprobacionDepositoDTO.getDescripcion());
			    
	            //se crea el objeto para guardarlo en la base de datos si el valor del id es insertado
			    //regresa el id del registro si este no es nulo y mayor a cero el registro es correcto.
			    Integer idDeposito = Etiquetas.CERO;
			    if (comprobacionDepositoDTO.getIdComprobacionDeposito() != null && comprobacionDepositoDTO.getIdComprobacionDeposito() > Etiquetas.CERO){
			    	ComprobacionDeposito deposito = comprobacionDepositoService.getComprobacionDeposito(comprobacionDepositoDTO.getIdComprobacionDeposito());
			    	deposito.setFechaDeposito(comprobacionDepositoDTO.getFecha_deposito());
			    	deposito.setMontoDeposito(Utilerias.convertStringToBigDecimal(comprobacionDepositoDTO.getMontoDeposito()));
			    	deposito.setArchivo(comprobacionDepositoDTO.getArchivo());
			    	comprobacionDepositoService.updateComprobacionDeposito(deposito);
			    }else{
			    	idDeposito = comprobacionDepositoService.createComprobacionDeposito(cd);
			    }
//			    Integer idDeposito = comprobacionDepositoService.createComprobacionDeposito(cd);
//			    if(idDeposito != null && idDeposito > Etiquetas.CERO){
//			    	creado = true;
//			    }
			    
			  
			  return idDeposito;
		  }
		  
		  
		  @RequestMapping(value="/saveDeposito", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
			private ResponseEntity<String> singleSave(MultipartHttpServletRequest request, HttpServletResponse response,
					@RequestParam Integer idSolicitud, @RequestParam Integer idComprobacionDeposito, @RequestParam String archivo, @RequestParam String fecha,
					@RequestParam String monto_deposito, @RequestParam String descripcion) {
			
				HashMap<String, String> result = new HashMap<String, String>();
				String json = null;
				
				Solicitud solicitud = new Solicitud();
				Usuario user = usuarioService.getUsuarioSesion();
				ComprobacionDepositoDTO dto = new ComprobacionDepositoDTO();
				dto.setIdComprobacionDeposito(idComprobacionDeposito);
				dto.setArchivo(archivo);
				//if (idSolicitud == null) idSolicitud = 
					
				dto.setSolicitud(new Solicitud(idSolicitud));
				
				dto.setDescripcion(descripcion);
				
				try {
					dto.setFecha_deposito(Utilerias.parseDate(fecha, "dd/MM/yyyy"));
				} catch (ParseException e1) {
					logger.error("Error fecha:" +e1);
				}
				
				dto.setCreacionFecha(new Date());
				dto.setMontoDeposito(monto_deposito);
				Integer  id = this.saveDeposito(dto);
				if( id > Etiquetas.CERO){
		    		result.put("guardado","true");
		    		result.put("idComprobacionDeposito", ""+id);
				}else{
		    		result.put("actualizado","true");
		    		
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
				
		    	
		    	//--------------------------------------------------------------------------
		    	
		    	HttpHeaders responseHeaders = new HttpHeaders(); 
		        responseHeaders.add("Content-Type", "application/json; charset=utf-8"); 
		        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
				
			}
			
		  @RequestMapping(value="/deleteDeposito", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			private ResponseEntity<String> deleteDeposito(HttpServletResponse response,
					@RequestParam Integer idComprobacionDeposito) {
			  
			  	HashMap<String, Object> result = new HashMap<String, Object>();
			  	
			  	comprobacionDepositoService.deleteComprobacionDeposito(idComprobacionDeposito);
			  	result.put("success", Boolean.TRUE);
			  	String json = null;
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
			
		    
		  @RequestMapping(value = "/getDeposito", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			private @ResponseBody ResponseEntity<String> getDeposito(HttpSession session, @RequestParam Integer idComprobacionDeposito, HttpServletRequest request, HttpServletResponse response) {
		
				HashMap<String, String> result = new HashMap<String, String>();
				String json = null;
				//obtenemos la solicitud de anticipo
				ComprobacionDeposito deposito = comprobacionDepositoService.getComprobacionDeposito(idComprobacionDeposito);
				
				if(deposito != null){
					result.put("idComprobacionDeposito", ""+deposito.getIdComprobacionDeposito());
					result.put("fechaDeposito", Utilerias.convertDateFormat(deposito.getFechaDeposito()));
					result.put("montoDeposito", deposito.getMontoDeposito().toString());
				}
					
				
				// mapper json
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
		  
			@RequestMapping(value="saveDepositoArchivo", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
			private ResponseEntity<String> singleSave(MultipartHttpServletRequest request, HttpServletResponse response, @RequestParam Integer idSolicitud) {
				
				String ruta;
				File archivo;
				
				// va a venir como par�metro. ID de sesi�n
				ruta = Utilerias.getFilesPath() + idSolicitud + "/";
				
				HashMap<String, String> result = new HashMap<String, String>();
				String json = null;
				
				
				SolicitudArchivo solicitudArchivo = new SolicitudArchivo(); 
				String UID = UUID.randomUUID().toString();
				String fileName = null;

				if(request != null){
					try {
						File folder = new File(ruta);
						if(!folder.exists()){
							folder.mkdirs();
						}
						
						Iterator<String> itr =  request.getFileNames();
					    MultipartFile file = request.getFile(itr.next());
						
						fileName = UID + "_" +file.getOriginalFilename();
						archivo = new File(ruta + fileName);
						
						if(!FilenameUtils.isExtension(archivo.getName(),"gif")) {
							
						
								byte[] bytes = file.getBytes();
								BufferedOutputStream buffStream = new BufferedOutputStream(
										new FileOutputStream(archivo));
								buffStream.write(bytes);
								buffStream.close();
				
								logger.info(etiqueta.ATENCION + etiqueta.ARCHIVO_ANEXADO);
						
								result.put("anexado", "true");
								result.put("file", fileName);
								result.put("descripcion", file.getOriginalFilename());
//							} // verifica tama�o
						}else{
							logger.info(etiqueta.ERROR + etiqueta.EXTENSION_INVALIDA);				
							result.put("invalido", "true");
						}
					} catch (Exception e) {

						logger.error(etiqueta.ERROR, e);			
						result.put("no_anexado", "true");
					}
				} else {
					logger.error(etiqueta.ERROR + etiqueta.ARCHIVO_VACIO);		
					result.put("vacio", "true");
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
		        return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
			}
			
			
			
			@RequestMapping(value = "/eliminarDesglose", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			private @ResponseBody ResponseEntity<String> eliminarDesglose(HttpSession session, @RequestParam Integer indice,
					@RequestParam(value="indiceFactura",required = false) Integer indiceFactura, HttpServletRequest request, HttpServletResponse response) {
		
				HashMap<String, String> result = new HashMap<String, String>();
				String json = null;
				
				//si el indice es -1 la factura aun no esta guardada.
				if(indiceFactura == -1){
					indiceFactura = null;
				}else{
					result.put("indiceFactura",indiceFactura+"");
				}
		        
				//si el numero de indice del desglose y el numero de la factura coincide: es el elemento a eliminar.
				if (lstDesgloses != null && !lstDesgloses.isEmpty()) {
					for (FacturaDesgloseDTO d : lstDesgloses) {
						if(d.getIndexInput().equals(indice)){
							lstDesgloses.remove(d);
							result.put("indice",indice+"");
							result.put("resultado", "true");
							break;
						}
					}
				}
		       
				// mapper json
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
			
			
			
			@RequestMapping(value = "/limpiarDesgloses", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			private @ResponseBody ResponseEntity<String> limpiarDesgloses(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
				HashMap<String, String> result = new HashMap<String, String>();
				String json = null;

				for (Iterator<FacturaDesgloseDTO> iterator = lstDesgloses.iterator(); iterator.hasNext(); ) {
					FacturaDesgloseDTO d  = iterator.next();
					if (d.getIndexFactura() == null){
						iterator.remove();
					}
				}
				
				// mapper json
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
			
			
			@RequestMapping(value = "/validaFacturasAnticipo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			private @ResponseBody ResponseEntity<String> checkFacturas(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
				HashMap<String, String> result = new HashMap<String, String>();
				String json = null;
				//obtenemos la solicitud de anticipo
				Solicitud anticipo = solicitudService.getSolicitud(anticipoActual);
				
				List<ComprobacionDeposito> depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(anticipo.getIdSolicitud());

						
				if(!lstFactDto.isEmpty() || !depositos.isEmpty()){
					
					//anticipo tipo proveedor
					if(anticipo.getTipoProveedor() != null && anticipo.getTipoProveedor().getIdTipoProveedor() == 2 
							&& anticipo.getProveedor() != null && anticipo.getProveedor().getPermiteAnticipoMultiple() == 1){
						
						Double montoAnticipo = anticipo.getMontoTotal().doubleValue();
						Double montoComprobado = 0D;
						Double montoSolicitudMultiple = 0D;
						List<Solicitud> solicitudesLigadas = solicitudService.getAnticiposMultiplesByEstatusAComprobar(idUsuario, anticipo.getProveedor().getIdProveedor(), Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE,  null, anticipo.getIdSolicitud());
						for (Solicitud solicitudMultiple : solicitudesLigadas) {
							montoSolicitudMultiple += solicitudMultiple.getMontoTotal().doubleValue();
						}
						
						Double saldo = montoAnticipo + montoSolicitudMultiple;
						Double montoDeposito = 0D;
//						List<ComprobacionDeposito> depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(anticipo.getIdSolicitud());
//						if(depositos.isEmpty()) {
							//busca la relacion de comprobacion-anticipo
							ComprobacionAnticipo comprobacionAnticipo = comprobacionAnticipoService.getComprobacionByAnticipo(anticipo.getIdSolicitud());
							if (comprobacionAnticipo!=null) {
//								ComprobacionAnticipo comprobacionAnticipo = anticipos.get(Etiquetas.CERO);
								//con la relacion se obtiene los datos del anticipo comprobado
//								Solicitud anticipoComprobado = solicitudService.getSolicitud(comprobacionAnticipo.getSolicitudByIdSolicitudAnticipo().getIdSolicitud());
								//busca depositos por id de comprobacion
								depositos = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(comprobacionAnticipo.getSolicitudByIdSolicitudComprobacion().getIdSolicitud());
							}
//						}
						for (ComprobacionDeposito comprobacionDeposito : depositos) {
							montoDeposito = comprobacionDeposito.getMontoDeposito().doubleValue();
							saldo -= montoDeposito;
						}
						Boolean seComprobo = false;

						
						//recorremos las facturas para revisar los saldos y compararlos con la solicitud
						int currentFacturas = 1;
						for(FacturaSolicitudDTO fact : lstFactDto){
							Double montoFactura = fact.getTotal().doubleValue();
							Double montoActualFactura = 0D;
							
							//suma de montos de facturas
							montoComprobado = montoComprobado + montoFactura;
							montoActualFactura = montoFactura - montoComprobado;
							
							// aportacion de la factura al montoAnticipo - el saldo
							Double aportado = montoFactura - saldo;
							if(aportado < 0){
							 fact.setMontoParcialFactura(new BigDecimal(montoFactura).setScale(Etiquetas.DOS, BigDecimal.ROUND_HALF_DOWN));
							}else if(aportado >= 0){
							 fact.setMontoParcialFactura(new BigDecimal(saldo).setScale(Etiquetas.DOS, BigDecimal.ROUND_HALF_DOWN));
							}
							
							// restante de la factura
							Double restante = montoFactura - saldo;
							if(restante <= 0){
								fact.setMontoRestanteFactura(new BigDecimal(0).setScale(Etiquetas.DOS, BigDecimal.ROUND_HALF_DOWN));
							}else{
								fact.setMontoRestanteFactura(new BigDecimal(restante).setScale(Etiquetas.DOS, BigDecimal.ROUND_HALF_DOWN));
							}
							
							//calculo de saldo por comprobar
							saldo = montoAnticipo + montoSolicitudMultiple - montoComprobado - montoDeposito;
							
							if(saldo <= 0D){
								if(restante > 0 && seComprobo == false){
									// si la factura cumplio completo el total del anticipo y le sobro saldo entonces
									// se marca como multiple para usarse en una segunda comprobacion.
									fact.setFacturaMultiple(true);
									seComprobo = true;
									result.put("status", "ok");
								} else if(restante <= 0 && seComprobo == false){
									fact.setFacturaMultiple(true);
//									seComprobo = true;
									result.put("status", "ok");
								}else{
									result.put("status", "error");
									result.put("mensaje", "La factura con el monto: "+montoFactura+" Excede el monto a comprobar, ya existe una factura con sobrante");
									break;
								}
							}
						
							//ciclos de facturas
							currentFacturas++;
						}
					}else{
						// si no es multiple que le permita guardar.
						result.put("status", "ok");
					}
				}else{
					result.put("status", "error");
					result.put("mensaje", "Sin facturas agregadas.");
				}
				
				// mapper json
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
			
//			@RequestMapping(value = "/validaEstatusAnticipo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
//			private @ResponseBody ResponseEntity<String> validaEstatusAnticipo(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
//		
//				HashMap<String, String> result = new HashMap<String, String>();
//				String json = null;
//				//obtenemos la solicitud de anticipo
//				Solicitud anticipo = solicitudService.getSolicitud(anticipoActual);
//					//anticipo tipo proveedor
//					if(anticipo.getTipoProveedor() != null && anticipo.getTipoProveedor().getIdTipoProveedor() == 2 
//							&& anticipo.getProveedor() != null && anticipo.getProveedor().getPermiteAnticipoMultiple() == 1){
//						StringBuilder idAnticipos = new StringBuilder();
//						Integer idEstadoCreado = 0;
//						Integer idEstadoCancelado = 0;
//						Integer idEstadoComprobado = 0;
//						
//						List<EstadoSolicitud> capturados = estadoSolicitudService.findByEstadoSolicitud(Etiquetas.ESTADO_SOLICITUD_CAPTURADO);
//						if (!capturados.isEmpty()){
//							EstadoSolicitud estadoSolicitudCreado =  capturados.get(0);
//							idEstadoCreado = estadoSolicitudCreado.getIdEstadoSolicitud();	
//						}
//						
//						List<EstadoSolicitud> cancelados = estadoSolicitudService.findByEstadoSolicitud(Etiquetas.ESTADO_SOLICITUD_CANCELADO);
//						if (!cancelados.isEmpty()){
//							EstadoSolicitud estadoCancelado = cancelados.get(0);
//							idEstadoCancelado = estadoCancelado.getIdEstadoSolicitud();				
//						}
//						
//						List<EstadoSolicitud> comprobados = estadoSolicitudService.findByEstadoSolicitud(Etiquetas.ESTADO_SOLICITUD_COMPROBADO);
//						if (!comprobados.isEmpty()){
//							EstadoSolicitud estadoSolicitudCapturado = comprobados.get(0);
//							idEstadoComprobado = estadoSolicitudCapturado.getIdEstadoSolicitud();					
//						}
//						
//						List<Solicitud> anticiposPendientes = solicitudService.getAnticiposPendientesPorComprobar(idUsuario, anticipo.getProveedor().getIdProveedor(), idEstadoCreado, idEstadoCancelado, idEstadoComprobado);
//						for (Solicitud solicitud : anticiposPendientes) {
//							idAnticipos.append(solicitud.getIdSolicitud());
//							idAnticipos.append(" ");
//						}
//						result.put("status", "error");
//						result.put("mensaje", parametroService.getParametroByName("anticipoSIMPLE").getValor() + ": "+idAnticipos.toString());
//					}else{
//						// si no es multiple que le permita guardar.
//						result.put("status", "ok");
//					}
//				// mapper json
//				ObjectMapper map = new ObjectMapper();
//				if (!result.isEmpty()) {
//					try {
//						json = map.writeValueAsString(result);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//		
//				HttpHeaders responseHeaders = new HttpHeaders();
//				responseHeaders.add("Content-Type", "application/json; charset=utf-8");
//				return new ResponseEntity<String>(json, responseHeaders, HttpStatus.CREATED);
//			}
	
			private ComprobacionAnticipoDTO loadAnticipoComprobacion(Solicitud solicitud){
				
				ComprobacionAnticipoDTO anticipo =  new ComprobacionAnticipoDTO();
				
				anticipo.setCompania(solicitud.getCompania());
				anticipo.setLocacion(solicitud.getLocacion());
				anticipo.setFormaPago(solicitud.getFormaPago());
				anticipo.setMoneda(solicitud.getMoneda());
				anticipo.setImporteTotal(solicitud.getMontoTotal().toString());
				anticipo.setTipoProveedor(solicitud.getTipoProveedor());
//				anticipo.setBeneficiario((solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) ? solicitud.getUsuarioByIdUsuarioAsesor().getIdUsuario() : solicitud.getProveedor().getIdProveedor()); //todos salen de tabla proveedores
				anticipo.setBeneficiario(solicitud.getProveedor().getIdProveedor());
				anticipo.setFecha_deposito(Utilerias.convertDateFormat(solicitud.getFechaPago()));
				
				if(solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.DOS){
					if(solicitud.getProveedor().getPermiteAnticipoMultiple() == 1){
						anticipo.setCompMultiple(true);
					}
				}
				

				return anticipo;
			}
			
			 private void guardarRelacionComprobacionAnticipo(Integer comprobacion , Integer anticipo){
				  ComprobacionAnticipo comprobacionAnticipo = new ComprobacionAnticipo();
				  comprobacionAnticipo.setSolicitudByIdSolicitudAnticipo(new Solicitud(anticipo));
				  comprobacionAnticipo.setSolicitudByIdSolicitudComprobacion(new Solicitud(comprobacion));
				  comprobacionAnticipoService.createComprobacionAnticipo(comprobacionAnticipo);
				 }
			 
			 private void guardarRelacionComprobacionAnticipoDinamico(Integer comprobacion , Integer anticipo, Integer factura, BigDecimal monto){
			     ComprobacionAnticipoFactura compFactura = new ComprobacionAnticipoFactura();
			     compFactura.setSolicitudByIdSolicitudAnticipo(new Solicitud(anticipo));
			     compFactura.setSolicitudByIdSolicitudComprobacion(new Solicitud(comprobacion));
			     compFactura.setFactura(new Factura(factura));
			     compFactura.setMontoFactura(monto);
			     comprobacionAnticipoFacturaService.createComprobacionAnticipoFactura(compFactura);
			     
		     }
			 
			 
			 private void cargarComprobacionGuardada(Solicitud comprobacion, HttpSession session){
					
				    //se limpia el dto del modelo 
					comprobacionAnticipoDTO = new ComprobacionAnticipoDTO();
						
					//busca la relacion de comprobacion-anticipo
					List<ComprobacionAnticipo> anticipos = comprobacionAnticipoService.getAnticiposByComprobacion(comprobacion.getIdSolicitud());
					if (!anticipos.isEmpty()) {
//						for (ComprobacionAnticipo comprobacionAnticipo : anticipos) {
							//como no es multiple saca la primera la unica.
							ComprobacionAnticipo anticipo = anticipos.get(Etiquetas.CERO);
							
							//con la relacion se obtiene los datos del anticipo comprobado
							Solicitud anticipoComprobado = solicitudService.getSolicitud(anticipo.getSolicitudByIdSolicitudAnticipo().getIdSolicitud());
							
							//id del anticipo actual
							anticipoActual = anticipoComprobado.getIdSolicitud();
							
							//se carga los datos del anticipo comprobado al dto
							comprobacionAnticipoDTO = loadAnticipoComprobacion(anticipoComprobado);
							comprobacionAnticipoDTO.setIdSolicitudComprobacionSession(comprobacion.getIdSolicitud());
							BigDecimal montoSolicitudMultiple = BigDecimal.ZERO;
							List<ComprobacionAnticipoMultiple>  list = comprobacionAnticipoMultipleService.getSolicitudByIdSolicitud(anticipoActual);
							for (ComprobacionAnticipoMultiple comprobacionAnticipoMultiple : list) {
								Solicitud solicitudMultiple = solicitudService.getSolicitud(comprobacionAnticipoMultiple.getIdSolicitudMultiple().getIdSolicitud());
								montoSolicitudMultiple = montoSolicitudMultiple.add(solicitudMultiple.getMontoTotal());
							}
							comprobacionAnticipoDTO.setSaldo((anticipoComprobado.getMontoTotal().add(montoSolicitudMultiple).subtract(comprobacion.getMontoTotal()).toString()));
							comprobacionAnticipoDTO.setConcepto(comprobacion.getConceptoGasto());
							comprobacionAnticipoDTO.setMontoTotal(comprobacion.getMontoTotal());

							//Fix para hacer update ala solicitud comprobacion
							List<Locacion> lc = new ArrayList<Locacion>();
							lc.add(anticipoComprobado.getLocacion());
							comprobacionAnticipoDTO.setLcPermitidas(lc);
				            comprobacionAnticipoDTO.setLocacion(anticipoComprobado.getLocacion());
//						}
					}

					
		            
		            
					
					if (comprobacion != null) {
						// si la comprobacion es valida entonces crear una variable de sesion
						// para el update.
						session.setAttribute("solicitud", comprobacion);
						
						//sacamos todas las facturas de la comprobacion
						List<Factura> lstFact = comprobacion.getFacturas();
						FacturaSolicitudDTO factSol;
						Integer idNumeroFactura = 0;
						
						BigDecimal subTotal = new BigDecimal(0);
						for(Factura factura : lstFact)
						{
							factSol = new FacturaSolicitudDTO();
							if(factura.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_FACTURA)
							{
								factSol.setProveedor(factura.getProveedor().getIdProveedor());
								factSol.setRazonSocial(factura.getProveedor().getDescripcion());
								factSol.setRFC(factura.getProveedor().getRfc());
								factSol.setSerie(factura.getSerieFactura());
								factSol.setFolioFiscal(factura.getFolioFiscal());
								factSol.setIEPS(factura.getIeps());
								factSol.setIVA(factura.getIva());
								factSol.setStrIva(String.valueOf(factura.getIva()));
								factSol.setStrIeps(String.valueOf(factura.getIeps()));
								
								//si cuenta con folio fiscal se trata de una factura de tipo sol. con xml  1 = con xml |  2 = sin xml
								if(factura.getFolioFiscal() != null && factura.getFolioFiscal().isEmpty() == false){
									factSol.setTipoSolicitud(Etiquetas.UNO);
								}else{
									factSol.setTipoSolicitud(Etiquetas.DOS);
								}
								
								//retenciones
								if(factura.getConRetenciones() > Etiquetas.CERO_S){
									factSol.setConRetenciones(Etiquetas.TRUE);
									factSol.setISRRetenido(factura.getIsrRetenido());
									factSol.setIVARetenido(factura.getIvaRetenido());
								}else{
									factSol.setConRetenciones(Etiquetas.FALSE);
//									factSol.setISRRetenido(new BigDecimal(0.0));
//									factSol.setIVARetenido(new BigDecimal(0.0));
								}
							}
							factSol.setIdFactura(factura.getIdFactura());
							factSol.setConcepto(factura.getConceptoGasto());
							factSol.setConCompFiscal((factura.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_FACTURA) ? true : false);
							
							if(factura.getCuentaContable() != null){
							 factSol.setCuentaContable(factura.getCuentaContable().getIdCuentaContable());
							}
							
							factSol.setFecha(util.parseFecha(factura.getFechaFactura()));
							factSol.setFolio(factura.getFactura());
							factSol.setIdCompania(comprobacion.getCompania().getIdcompania());
							
							if(factura.getLocacion() != null){
							factSol.setLocacion(factura.getLocacion().getIdLocacion());
							}
							
							factSol.setMoneda(factura.getMoneda().getIdMoneda());
							factSol.setSubTotal(factura.getSubtotal());
							subTotal = subTotal.add(factSol.getSubTotal());
							factSol.setTotal(factura.getTotal());
							//get archivos de cada factura
							List<FacturaArchivo> lstFactArchivo = facturaArchivoService.getAllFacturaArchivoByIdFactura(factura.getIdFactura());
							factSol.setFacturaArchivos(lstFactArchivo);
							lstFactDto.add(factSol);
							
							
							// si es tipo proveedor y tiene desgloses se cargan
							if(comprobacion.getTipoProveedor().getIdTipoProveedor() == Etiquetas.DOS && factura.getFacturaDesgloses() != null &&
									factura.getFacturaDesgloses().isEmpty() == false){
								
								List<FacturaDesglose> desglosesDeFactura = Utilerias.getSortFacturasDesglose(factura.getFacturaDesgloses());
								
								for(FacturaDesglose desglose : desglosesDeFactura){
									
									FacturaDesgloseDTO dto = new FacturaDesgloseDTO();
									Random r = new Random();
									Integer nrand = r.nextInt(150) + 1;
									
									dto.setIndexInput(nrand);
									dto.setIndexFactura(idNumeroFactura);
									dto.setLocacion(desglose.getLocacion());
									dto.setStrSubTotal(desglose.getSubtotal().toString());
									dto.setUsuarioSolicitante(factura.getCreacionUsuario());
									dto.setConcepto(desglose.getConcepto());
									dto.setCuentaContable(desglose.getCuentaContable());
									
									// si cuenta con un folio fiscal es de tipo no mercancias con xml 
									if(factura.getFolioFiscal() != null && factura.getFolioFiscal().isEmpty() == false){
										dto.setTipoSolicitud(Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML);
									}else{
										dto.setTipoSolicitud(Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML);
									}
									
									lstDesgloses.add(dto);
									
								}
							}
							idNumeroFactura++;
						}
						//Fix para actualizar la solicitud de comprobacion
						comprobacionAnticipoDTO.setLstFactDto(lstFactDto);
						comprobacionAnticipoDTO.setSubTotal(subTotal);
					}
					
			 }
			 

			// metodo ajax para el cambio de estatus a Multiple para ligar solicitudes.
			@RequestMapping(value = "/cambiarEstatusSolicitudComprobacion", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
			private @ResponseBody
			ResponseEntity<String> cambiarEstatusSolicitudComprobacion(HttpSession session, @RequestParam Integer idSolicitud, @RequestParam Integer idComprobacion, @RequestParam String idSolicitudesALigar, @RequestParam String idSolicitudesADesligar, HttpServletRequest request, HttpServletResponse response) {

				String json = null;
				HashMap<String, String> result = new HashMap<String, String>();
				StringBuilder itemHtml = new StringBuilder();
				BigDecimal importeTotal = BigDecimal.ZERO;
				int i = 0;
				
				Solicitud solicitud = new Solicitud();
				if(idSolicitud == 0) {
					solicitud = solicitudService.getSolicitud(idComprobacion);
				} else {
					solicitud.setIdSolicitud(idSolicitud);
				}
				//Liga solicitudes a otra 
				for(String idSolicitudStr : idSolicitudesALigar.split(",")){
//					System.out.println(idSolicitud);
					if (idSolicitudStr != null && !idSolicitudStr.equals("-1")) {
						Solicitud solicitudAligar = solicitudService.getSolicitud(Integer.valueOf(idSolicitudStr));
						
						//actualizar la solicitud a estatus Multiple
						solicitudAligar.setIdEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE);
						solicitudAligar.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_MULTIPLE));
						Solicitud solicitudAux = null;
						solicitudAux = solicitudService.updateSolicitud(solicitudAligar);
						ComprobacionAnticipoMultiple comprobacionAnticipoMultiple = comprobacionAnticipoMultipleService.getSolicitudByIdSolicitudMultiple(solicitudAligar.getIdSolicitud());
						if(comprobacionAnticipoMultiple == null){
							comprobacionAnticipoMultiple = new ComprobacionAnticipoMultiple();
							comprobacionAnticipoMultiple.setIdSolicitud(solicitud);
							comprobacionAnticipoMultiple.setIdSolicitudMultiple(solicitudAligar);
							comprobacionAnticipoMultipleService.createComprobacionAnticipoMultiple(comprobacionAnticipoMultiple);
						}
//						solicitudAux = new Solicitud();
						if(solicitudAux != null){
							result.put("resultado", "true");
						}else{
							result.put("resultado", "false");
						}
						
						itemHtml.append(builtRowGridAnticiposIncluidos(solicitudAligar, i));
						importeTotal = importeTotal.add(solicitudAligar.getMontoTotal());
						i++;
					} else {
						result.put("resultado", "false");
					}
				}
				//Desligar la solicitud de la comprobacion
				for(String idSolicitudStr : idSolicitudesADesligar.split(",")){
					if (idSolicitudStr != null && !idSolicitudStr.equals("-1")) {
						Solicitud solicitudADesligar = solicitudService.getSolicitud(Integer.valueOf(idSolicitudStr));
						//actualizar la solicitud a estatus Multiple
						solicitudADesligar.setIdEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_PAGADA);
						solicitudADesligar.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_PAGADA));
						solicitudService.updateSolicitud(solicitudADesligar);
						ComprobacionAnticipoMultiple comprobacionAnticipoMultiple = comprobacionAnticipoMultipleService.getSolicitudByIdSolicitudMultiple(solicitudADesligar.getIdSolicitud());
						if(comprobacionAnticipoMultiple != null){
							comprobacionAnticipoMultipleService.deleteComprobacionAnticipoMultiple(comprobacionAnticipoMultiple.getIdComprobacionAnticipoMultiple());
						}
					}
				}
				if (itemHtml.length() == 0) {
					result.put("resultado", "true");
					itemHtml.append(builtRowGridAnticiposIncluidos(null,0));
				}
				result.put("anticiposIncluidos", itemHtml.toString());
				result.put("importeTotal", importeTotal.toString());
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
			
			private String builtRowGridAnticiposIncluidos(Solicitud solicitud, int index) {
				StringBuilder row = new StringBuilder();

				if ( index == 0){
					//Header
					row.append("<thead><tr>"
							+ "<th>" + etiqueta.NUMERO_DE_ANTICIPO + "</th>"
							+ "<th>" + etiqueta.IMPORTE_TOTAL + "</th>"
							+ "<th>" + etiqueta.IMPORTE_COMPROBADO + "</th>"
							+ "</tr></thead>"); 
				}

				if( solicitud != null ){
					//ID
					row.append("<tr idSolicitud="+solicitud.getIdSolicitud()+" class=\"odd gradeX\">");
					row.append("<td align=\"center\"><div class=\"linea\">" + solicitud.getIdSolicitud() + "</div></td>");

					//Importe Total
					row.append("<td><input type=\"text\" class=\"form-control currencyFormat importeTotalIncluido\" value=\"" + solicitud.getMontoTotal() + "\" disabled></td>");
					
					//Get comprobaciones guardadas
					BigDecimal montoComprobado = new BigDecimal(0.0);
					List<ComprobacionAnticipoFactura> compAnticipo = comprobacionAnticipoFacturaService.getComprobacionByIdAnticipo(solicitud.getIdSolicitud());
					if(!compAnticipo.isEmpty()){
						for(ComprobacionAnticipoFactura compAnt : compAnticipo){
							montoComprobado = montoComprobado.add(compAnt.getMontoFactura());
						}
					}
					//comprobado
					row.append("<td><input type=\"text\" class=\"form-control sbts subtotalesNM currencyFormat\" value=\"" + montoComprobado + "\" disabled></td>");
					
					row.append("</tr>");
				}else {
					row.append("<tr></tr>");
				}
				
				return row.toString();
			}
			
			 
}
