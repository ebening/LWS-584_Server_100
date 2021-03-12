package com.lowes.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.FacturaConXML;
import com.lowes.dto.FacturaDesgloseDTO;
import com.lowes.dto.FacturaSolicitudDTO;
import com.lowes.dto.SolicitudDTO;
import com.lowes.entity.Aid;
import com.lowes.entity.CategoriaMayor;
import com.lowes.entity.CategoriaMenor;
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
import com.lowes.entity.SolicitudArchivo;
import com.lowes.entity.TipoFactura;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.AidService;
import com.lowes.service.CategoriaMayorService;
import com.lowes.service.CategoriaMenorService;
import com.lowes.service.CompaniaService;
import com.lowes.service.CuentaContableService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class CajaChicaController {
	
	private Integer idUsuario = 1;

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
	private FacturaService facturaService;
	@Autowired
	private FacturaDesgloseService facturaDesgloseService;
	@Autowired
	private MonedaService monedaService;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private FormaPagoService formaPagoService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;

	private UtilController util = new UtilController();

	//private static List<FacturaDesgloseDTO> lstFactDto = new ArrayList<>();
	private static List<FacturaSolicitudDTO> lstFactDto;
	private static SolicitudDTO solicitudDTO;
	
	@RequestMapping("cajaChica2")
	public ModelAndView cajaChica(HttpSession session, Integer id) {
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		Solicitud solicitudActual = null;
		Integer idEstadoSolicitud = 0;
		
		lstFactDto = new ArrayList<>();
		solicitudDTO = new SolicitudDTO();
		
		//FacturaConXML facturaConXML = new FacturaConXML();

		// configuraciones permitidas por usuario
		List<UsuarioConfSolicitante> uconfigSol = uConfsolicitanteService.getUsuarioConfSolByIdUsuario(idUsuario);

		// Obtener combos filtrador por configuracion de solicitante.
		List<Locacion> lcPermitidas = getLocacionesPermitidasPorUsuario(uconfigSol);
		List<CuentaContable> ccPermitidas = getCuentasContablesPermitidasPorUsuario(uconfigSol);
		
		// obtener los jefe para opcion de usuario solicitante.
		List<Usuario> lstUsuariosJefe = new ArrayList<>();
		Usuario usuario = usuarioService.getUsuario(idUsuario);
		lstUsuariosJefe.add(usuario.getUsuario());
		
		// Obtener Companias, Proveedores y Monedas
		List<Compania> lstCompanias = new ArrayList<>(); 
		lstCompanias.add(companiaService.getCompania(usuario.getCompania().getIdcompania()));
		List<Proveedor> lstProveedores = proveedorService.getAllProveedores();
		List<Moneda> lstMoneda = monedaService.getAllMoneda();	
		List<FormaPago> lstFormaPago = formaPagoService.getAllFormaPago();	
		
		Solicitud solicitud = null;
		//Factura factura = null;
		List<FacturaDesglose> facturasDesglose = new ArrayList<>();
		List<FacturaDesgloseDTO> facturasDesgloseDTO = new ArrayList<>();
		
		// Obtener solicitud,factura y su desglose si se requiere:
		if (id != null && id > Etiquetas.CERO) {
			solicitudDTO.setIdSolicitudSession(id);
			solicitud = solicitudService.getSolicitud(solicitudDTO.getIdSolicitudSession());
			if (solicitud != null) {
				idEstadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
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
				factSol.setIdFactura(factura.getIdFactura());
				factSol.setConcepto(factura.getConceptoGasto());
				factSol.setConCompFiscal((factura.getTipoFactura().getIdTipoFactura() == Etiquetas.TIPO_FACTURA_FACTURA) ? true : false);
				//factSol.setConCompFiscal(factura.get);
				factSol.setCuentaContable(factura.getCuentaContable().getIdCuentaContable());
				factSol.setFecha(util .parseFecha(factura.getFechaFactura()));
				factSol.setFolio(factura.getFactura());
				factSol.setFolioFiscal(factura.getFolioFiscal());
				factSol.setIdCompania(solicitud.getCompania().getIdcompania());
				factSol.setIEPS(factura.getIeps());
				factSol.setIVA(factura.getIva());
				
				//retenciones
				if(factura.getConRetenciones() > Etiquetas.CERO_S){
					factSol.setConRetenciones(Etiquetas.TRUE);
					factSol.setISRRetenido(factura.getIsrRetenido());
					factSol.setIVARetenido(factura.getIvaRetenido());
				}else{
					factSol.setConRetenciones(Etiquetas.FALSE);
				}
				
				factSol.setLocacion(solicitud.getLocacion().getIdLocacion());
				factSol.setMoneda(factura.getMoneda().getIdMoneda());
				factSol.setProveedor(factura.getProveedor().getIdProveedor());
				factSol.setRFC(factura.getProveedor().getRfc());
				factSol.setSerie(factura.getSerieFactura());
				factSol.setSubTotal(factura.getSubtotal());
				factSol.setTotal(factura.getTotal());				
				lstFactDto.add(factSol);
			}
			//factura = solicitud.getFacturas().get(Etiquetas.CERO);
			if (lstFact != null && solicitud.getFacturas().size() > Etiquetas.CERO) {
				
				solicitudDTO.setCompania(solicitud.getCompania());
				solicitudDTO.setMontoTotal(solicitud.getMontoTotal());
				solicitudDTO.setMoneda(solicitud.getMoneda());
				solicitudDTO.setLocacion(solicitud.getLocacion());
				solicitudDTO.setFormaPago(solicitud.getFormaPago());
				if(solicitud.getUsuarioByIdUsuario().getIdUsuario() != solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario()){
					solicitudDTO.setSolicitante(true);
					solicitudDTO.setUsuarioSolicitante(solicitud.getUsuarioByIdUsuarioSolicita());
				}else{
					solicitudDTO.setSolicitante(false);
				}
                
				solicitudDTO.setConcepto(solicitud.getConceptoGasto());
				
				//Set subtotal de facturas en solicitud.
				BigDecimal subTotal = new BigDecimal(0.0);
				for(FacturaSolicitudDTO fs : lstFactDto){
					subTotal = subTotal.add(fs.getSubTotal());
				}
				solicitudDTO.setSubTotal(subTotal);
				
			}
		} else {
			// mensaje de solicitud no valida a la vista.
			solicitudDTO.setIdSolicitudSession(Etiquetas.CERO);
		}
		
		//revisar si se trata de una modificaci�n para activar mensaje en la vista
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
		
		solicitudDTO.setLstFactDto(lstFactDto);
		model.put("solicitudDTO", solicitudDTO);
		model.put("lcPermitidas", lcPermitidas);
		model.put("ccPermitidas", ccPermitidas);
		model.put("lstCompanias", lstCompanias);
		model.put("lstProveedores", lstProveedores);
		model.put("lstMonedas", lstMoneda);
		model.put("lstFormaPago", lstFormaPago);
		model.put("lstUsuariosJefe", lstUsuariosJefe);

		return new ModelAndView("reembolso", model);
	}
	

	private Date parseDate(String date, String format) throws ParseException
	{
	    SimpleDateFormat formatter = new SimpleDateFormat(format);
	    return formatter.parse(date);
	}

	private Solicitud loadSolicitud(SolicitudDTO solicitudDTO) {

		Solicitud sol = new Solicitud();

		// definir tipo de solicitud
		
		sol.setTipoSolicitud(new TipoSolicitud(3));
		sol.setMontoTotal(solicitudDTO.getMontoTotal());
		sol.setUsuarioByIdUsuario(new Usuario(idUsuario));

		if (solicitudDTO.isSolicitante()) {
			sol.setUsuarioByIdUsuarioSolicita(new Usuario(solicitudDTO.getUsuarioSolicitante().getIdUsuario()));
		} else {
			sol.setUsuarioByIdUsuarioSolicita(new Usuario(idUsuario));
		}
		
		sol.setConceptoGasto(solicitudDTO.getConcepto());
		sol.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.UNO));
		sol.setMoneda(solicitudDTO.getMoneda());
		sol.setCompania(solicitudDTO.getCompania());
		sol.setLocacion(solicitudDTO.getLocacion());
		sol.setFormaPago(solicitudDTO.getFormaPago());

//		if (SolicitudDTO.isTrack_asset()) {
//			sol.setTrackAsset(Etiquetas.UNO_S);
//		} else {
//			sol.setTrackAsset(Etiquetas.CERO_S);
//		}

		return sol;
	}

	
	private List<Locacion> getLocacionesPermitidasPorUsuario(List<UsuarioConfSolicitante> uconfigSol) {

		// cargar los tipos de locacion permitidos por usuario
		List<Integer> idsLocaciones = new ArrayList<>();
		for (UsuarioConfSolicitante conf : uconfigSol) {
			if (conf != null) {
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
	
	private Factura loadFactura(Integer idSolicitud, FacturaSolicitudDTO reembolsoDTO){
		
		Factura factura = new Factura();
		
		factura.setSolicitud(new Solicitud(idSolicitud));
		Moneda mon = monedaService.getMoneda(reembolsoDTO.getMoneda());
		factura.setMoneda(mon);
		factura.setFactura(reembolsoDTO.getFolio());
		Compania comania = companiaService.getCompania(reembolsoDTO.getIdCompania());
		factura.setCompaniaByIdCompania(comania);
		CuentaContable ccont = cuentaContableService.getCuentaContable(reembolsoDTO.getCuentaContable());
		factura.setCuentaContable(ccont);
		TipoFactura tFact = new TipoFactura();
		tFact.setIdTipoFactura((reembolsoDTO.isConCompFiscal() == true) ? Etiquetas.TIPO_FACTURA_FACTURA : Etiquetas.TIPO_FACTURA_COMPROBANTE );
		factura.setTipoFactura(tFact);

//		if (facturaConXML.isTrack_asset()) {
//			factura.setCompaniaByIdCompaniaLibroContable(facturaConXML.getId_compania_libro_contable());
//			factura.setPar(facturaConXML.getPar());
//			factura.setTrackAsset(Etiquetas.UNO_S);
//		}else{
//			factura.setTrackAsset(Etiquetas.CERO_S);
//		}
		Proveedor prov = proveedorService.getProveedor(reembolsoDTO.getProveedor());
		factura.setProveedor(prov);
		factura.setSerieFactura(reembolsoDTO.getSerie());
		factura.setFolioFiscal(reembolsoDTO.getFolioFiscal());
		
		
		try {
			factura.setFechaFactura(parseDate(reembolsoDTO.getFecha(), "dd/MM/yyyy"));
		} catch (ParseException e) {
	
		}
		
		factura.setSubtotal(reembolsoDTO.getSubTotal());
		factura.setTotal(reembolsoDTO.getTotal());

		if (reembolsoDTO.getIEPS() != null) {
			factura.setIeps(reembolsoDTO.getIEPS());
		}
		if (reembolsoDTO.getIVA() != null) {
			factura.setIva(reembolsoDTO.getIVA());
		}

		// retenciones
		if (reembolsoDTO.isConRetenciones()) {
			factura.setConRetenciones(Etiquetas.UNO_S);
			factura.setIvaRetenido(reembolsoDTO.getIVARetenido());
			factura.setIsrRetenido(reembolsoDTO.getISRRetenido());
		} else {
			factura.setConRetenciones(Etiquetas.CERO_S);
		}

		return factura;
	}
}
