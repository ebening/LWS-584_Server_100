	
package com.lowes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.AnticipoDTO;
import com.lowes.dto.BeneficiarioDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.FormaPago;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Parametro;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.CompaniaService;
import com.lowes.service.FacturaDesgloseService;
import com.lowes.service.FacturaService;
import com.lowes.service.FormaPagoService;
import com.lowes.service.LocacionService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudService;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class AnticipoController {

	
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
	
	@RequestMapping("/anticipo")
	public ModelAndView anticipo(HttpSession session, Integer id) {
		
		//declaraciones de objetos
		HashMap<String, Object> modelo = new HashMap<>();
		AnticipoDTO anticipoDTO = new AnticipoDTO();
		Solicitud solicitud = new Solicitud();
		Integer idEstadoSolicitud = 0;
		boolean esMultiple = false;
		// lista de compa�ias para el combo.
		List<Compania> companiaslst = companiaService.getAllCompania();
		
		//locaciones permitidas por usuario
		// configuraciones permitidas por usuario
		// extraer usuario en session para guardar la solicitud.
		Usuario usuarioSession = usuarioService.getUsuarioSesion();
		List<UsuarioConfSolicitante> uconfigSol = usuarioConfSolicitanteService.getUsuarioConfSolByIdUsuario(usuarioSession.getIdUsuario());
		List<Locacion> locacionesPermitidas = UtilController.getLocacionesPermitidasPorUsuario(uconfigSol, Etiquetas.SOLICITUD_ANTICIPO,locacionService.getAllLocaciones(),usuarioSession.getIdUsuario());
		List<BeneficiarioDTO> beneficiarioList = new ArrayList<>();
		
		List<Proveedor> proveedorList = proveedorService.getProveedoresByTipo(Etiquetas.UNO);
		for (Proveedor proveedor : proveedorList) {
			beneficiarioList.add(new BeneficiarioDTO(proveedor.getIdProveedor(), proveedor.getDescripcion()));
		}
		
		// set la locacion correspondiente al usuario
		anticipoDTO.setLocacion(usuarioSession.getLocacion());
		List<Moneda> monedaList = monedaService.getAllMoneda();
		anticipoDTO.setMoneda(new Moneda(Etiquetas.ID_PESOS));
		anticipoDTO.setFormaPago(new FormaPago(Integer.parseInt(parametroService.getParametroByName("formaPagoTransferencia").getValor())));
		anticipoDTO.setCompania(new Compania(Etiquetas.ID_COMPANIA_LOWES ));
		
		
		// si trae un parametro numerico para mostrar solicitud se llama al servicio
				if(id != null && id > Etiquetas.CERO){
					solicitud = solicitudService.getSolicitud(id);
					if(solicitud != null){
						anticipoDTO.setIdSolicitudSession(solicitud.getIdSolicitud());
						idEstadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
						anticipoDTO.setCompania(solicitud.getCompania());
						anticipoDTO.setFormaPago(solicitud.getFormaPago());
						anticipoDTO.setLocacion(solicitud.getLocacion());
						anticipoDTO.setMoneda(solicitud.getMoneda());
						anticipoDTO.setFormaPago(solicitud.getFormaPago());
						anticipoDTO.setImporteTotal(solicitud.getMontoTotal().toString());
						anticipoDTO.setConcepto(solicitud.getConceptoGasto());
						anticipoDTO.setIdSolicitudSession(solicitud.getIdSolicitud());
						anticipoDTO.setTipoProveedor(solicitud.getTipoProveedor());
						anticipoDTO.setBeneficiario(solicitud.getProveedor().getIdProveedor());
						anticipoDTO.setProveedor(solicitud.getProveedor());
//						anticipoDTO.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_ANTICIPO));
						if (solicitud.getProveedor().getPermiteAnticipoMultiple() > Etiquetas.CERO) {
							esMultiple = true;
						}
						//revisar si se trata de una modificaci�n para activar mensaje en la vista
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
		
		List<FormaPago> formaPagoList = formaPagoService.getAllFormaPago();
		
		//objetos para la vista
		modelo.put("companiaList", companiaslst);
		modelo.put("locacionesPermitidas", locacionesPermitidas);
		modelo.put("anticipoDTO", anticipoDTO);
		modelo.put("monedaList", monedaList);
		modelo.put("beneficiarioList", beneficiarioList);
		modelo.put("formaPagoList", formaPagoList);
		modelo.put("idEstadoSolicitud", idEstadoSolicitud);
		modelo.put("esMultiple", esMultiple);
		
		return new ModelAndView("anticipo",modelo);
		
	}
	
	// metodo ajax para obtener los proveedores
	@RequestMapping(value = "/getProveedoresAnticipo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getKilometraje(HttpSession session, @RequestParam Integer idTipoProveedor, @RequestParam Integer locacion, HttpServletRequest request,
			HttpServletResponse response) {
		
		 String json = null;
	     HashMap<String, String> result = new HashMap<String, String>();	     
	     StringBuilder beneficiarios = new StringBuilder("<option title=\"Seleccione:\" value=\"-1\">Seleccione</option>");
	     List<Proveedor> proveedorList = proveedorService.getProveedoresByTipo(idTipoProveedor);
	     
	     for(Proveedor prv : proveedorList){
	    	 beneficiarios.append("<option title=\"\" value=\"").append(prv.getIdProveedor()).append("\">").append(prv.getDescripcion()).append("</option>");
	     }

	     result.put("beneficiarioList", beneficiarios.toString());
		
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
	
	@RequestMapping(value = "/saveAnticipo", method = RequestMethod.POST)
	public ModelAndView saveAnticipo(@ModelAttribute("anticipoDTO") AnticipoDTO anticipoDTO, HttpSession session) {
		Integer idSolicitud = 0;
		Solicitud solicitud = new Solicitud();
		Usuario user = usuarioService.getUsuarioSesion();
		
		Proveedor proveedor = proveedorService.getProveedor(anticipoDTO.getBeneficiario());
		Usuario usuarioAsesor = usuarioService.getUsuarioByProveedor(proveedor.getNumeroProveedor());
		

		if (anticipoDTO.getIdSolicitudSession() != null && anticipoDTO.getIdSolicitudSession() > 0) {
			// Update petici�n
			solicitud = solicitudService.getSolicitud(anticipoDTO.getIdSolicitudSession());
			solicitud.setCompania(anticipoDTO.getCompania());
			solicitud.setFormaPago(anticipoDTO.getFormaPago());
			solicitud.setLocacion(anticipoDTO.getLocacion());
			solicitud.setMoneda(anticipoDTO.getMoneda());
			solicitud.setConceptoGasto(anticipoDTO.getConcepto());
			BigDecimal montoTotal = Utilerias.convertStringToBigDecimal(anticipoDTO.getImporteTotal());
			solicitud.setMontoTotal(montoTotal);
			solicitud.setModificacionFecha(new Date());
			solicitud.setModificacionUsuario(user.getIdUsuario());
			solicitud.setTipoProveedor(anticipoDTO.getTipoProveedor());
			
			solicitud.setProveedor(proveedor);
			if(anticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
				solicitud.setUsuarioByIdUsuarioAsesor(usuarioAsesor);	
			}
			
			
			solicitud = solicitudService.updateSolicitud(solicitud);
			idSolicitud = solicitud.getIdSolicitud();
			session.setAttribute("actualizacion", Etiquetas.TRUE);

			//Actualizacion de factura
			//if(solicitud != null && solicitud.getIdSolicitud() > Etiquetas.CERO){
//				Factura factura = new Factura();
//				// se toma la info de la factura actual que trae la solicitud mapeada por hibernate (la primera y la unica)
//				factura = solicitud.getFacturas().get(Etiquetas.CERO);
//				factura.setSolicitud(new Solicitud(idSolicitud));
//				factura.setMoneda(solicitud.getMoneda());
//				// valor default para el folio de este proceso.
//				factura.setFactura(Etiquetas.Anticipo);
//				factura.setConRetenciones(Etiquetas.CERO_S);
//				factura.setActivo(Etiquetas.UNO_S);
//				factura.setModificacionFecha(new Date());
//				factura.setModificacionUsuario(user.getIdUsuario());
//				
//				//hibernate
//				factura = facturaService.updateFactura(factura);
//				Integer idfactura = factura.getIdFactura();
				
				// guradando tercer nivel: (Desgloses de factura) solo tendra
				// una registro por factura
//				if (idfactura != null && idfactura > Etiquetas.CERO) {
//					FacturaDesglose facturaDesglose = new FacturaDesglose();
//					// se toma la info del desglose actual que trae la factura mapeada por hibernate (el primero y el unico)
//					facturaDesglose = factura.getFacturaDesgloses().get(Etiquetas.CERO);
//					facturaDesglose.setFactura(new Factura(idfactura));
//					facturaDesglose.setLocacion(anticipoDTO.getLocacion());
//					// cuenta contable establecida para los anticipos almacenada
//					// en la tabla de parametros
//					facturaDesglose.setCuentaContable(new CuentaContable(
//							Integer.valueOf(parametroService.getParametroByName("ccAnticipo").getValor())));
//					facturaDesglose.setConcepto(anticipoDTO.getConcepto());
//					facturaDesglose.setSubtotal(Utilerias.convertStringToBigDecimal(anticipoDTO.getImporteTotal()));
//					facturaDesglose.setActivo(Etiquetas.UNO_S);
//					facturaDesglose.setModificacionUsuario(user.getIdUsuario());
//					facturaDesglose.setModificacionFecha(new Date());
//					
//					//hibernate
//					facturaDesgloseService.updateFacturaDesglose(facturaDesglose);
//				}
				
				// var en session para mensaje en la vista.
				

			//}
			
		} else {
			
			//guardando informacion de la solicitud de anticipo por primera vez.
			solicitud.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_NUEVA));
			solicitud.setTipoSolicitud(new TipoSolicitud(Etiquetas.SOLICITUD_ANTICIPO));
			solicitud.setUsuarioByIdUsuario(user);
			solicitud.setUsuarioByIdUsuarioSolicita(user);
			solicitud.setCompania(anticipoDTO.getCompania());
			solicitud.setFormaPago(anticipoDTO.getFormaPago());
			solicitud.setLocacion(anticipoDTO.getLocacion());
			solicitud.setMoneda(anticipoDTO.getMoneda());
			solicitud.setConceptoGasto(anticipoDTO.getConcepto());
			BigDecimal montoTotal = Utilerias.convertStringToBigDecimal(anticipoDTO.getImporteTotal());
			solicitud.setMontoTotal(montoTotal);
			solicitud.setActivo(Etiquetas.UNO_S);
			solicitud.setCreacionFecha(new Date());
			solicitud.setCreacionUsuario(user.getIdUsuario());
			solicitud.setTipoProveedor(anticipoDTO.getTipoProveedor());
			
			solicitud.setProveedor(proveedor);
			if(anticipoDTO.getTipoProveedor().getIdTipoProveedor() == Etiquetas.TIPO_PROVEEDOR_ASESOR){
				solicitud.setUsuarioByIdUsuarioAsesor(usuarioAsesor);	
			}
			
			//hibernate
			idSolicitud = solicitudService.createSolicitud(solicitud);

			// guardando en segundo nivel (tabla factura) solo tendra un
			// registro por solicitud
//			if (idSolicitud != null && idSolicitud > Etiquetas.CERO) {
//				Factura factura = new Factura();
//				factura.setSolicitud(new Solicitud(idSolicitud));
//				factura.setMoneda(solicitud.getMoneda());
//				factura.setFactura(Etiquetas.Anticipo);
//				factura.setConRetenciones(Etiquetas.CERO_S);
//				factura.setActivo(Etiquetas.UNO_S);
//				factura.setCreacionFecha(new Date());
//				factura.setCreacionUsuario(user.getIdUsuario());
//				factura.setFechaFactura(new Date());
//				
//                //hibernate
//				Integer idfactura = facturaService.createFactura(factura);
//
//				// guradando tercer nivel: (Desgloses de factura) solo tendra
//				// una registro por factura
//				if (idfactura != null && idfactura > Etiquetas.CERO) {
//					FacturaDesglose facturaDesglose = new FacturaDesglose();
//					facturaDesglose.setFactura(new Factura(idfactura));
//					facturaDesglose.setLocacion(anticipoDTO.getLocacion());
//					// cuenta contable establecida para los anticipos almacenada
//					// en la tabla de parametros
//					facturaDesglose.setCuentaContable(new CuentaContable(Integer.valueOf(parametroService.getParametroByName("ccAnticipo").getValor())));
//					facturaDesglose.setConcepto(anticipoDTO.getConcepto());
//					facturaDesglose.setSubtotal(Utilerias.convertStringToBigDecimal(anticipoDTO.getImporteTotal()));
//					facturaDesglose.setActivo(Etiquetas.UNO_S);
//					facturaDesglose.setCreacionFecha(new Date());
//					facturaDesglose.setCreacionUsuario(user.getIdUsuario());
//					
//					//hibernate
//					facturaDesgloseService.createFacturaDesglose(facturaDesglose);
//				}
//
//			}
			//crear sesion para mensaje en la vista
            session.setAttribute("creacion", Etiquetas.TRUE);
		}

		return new ModelAndView("redirect:anticipo?id=" + idSolicitud);
	}
	
	
	
	
	
	// metodo ajax para verificar que un anticipo pueda enviarse, dependiendo si 
	@RequestMapping(value = "/checkComprobada", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> checkComprobada(HttpSession session, @RequestParam Integer idProveedor,
			HttpServletRequest request, HttpServletResponse response) {

		HashMap<String, Object> result = new HashMap<>();
		String json = null;
		
		List<Integer> solicitudes = getSolicitudesProveedorSimple(idProveedor);
         
		if(!solicitudes.isEmpty()){
			Parametro parametroMensaje = parametroService.getParametroByName("anticipoSIMPLE");
			result.put("resultado","true");
			result.put("mensaje", (parametroMensaje!=null?parametroMensaje.getValor():"Este beneficiario tiene un anticipo por comprobar") + 
					": "+StringUtils.join(solicitudes.toArray(), ","));
		}else{
			result.put("resultado", "false");
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
	
	private List<Integer> getSolicitudesProveedorSimple(Integer idProveedor) {

		List<Integer> solicitudesId = new ArrayList<Integer>();
		Proveedor proveedor = proveedorService.getProveedor(idProveedor);
		if(proveedor!=null){
			if(Utilerias.isProveedorSimple(proveedor.getPermiteAnticipoMultiple())){
				List<Integer> estatusNoPermitido = Arrays.asList(
						Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR,
						Etiquetas.ID_ESTADO_SOLICITUD_AUTORIZADA,
//						Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA,
						Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA,
						Etiquetas.ID_ESTADO_SOLICITUD_PAGADA,
						Etiquetas.ID_ESTADO_SOLICITUD_POR_CONFIRMAR);
				List<Solicitud> solicitudes = solicitudService.getSolicitudesByProveedor(idProveedor);
				Parametro anticiposPermitidos = parametroService.getParametroByName("etiquetaAnticiposPermitidosConNoMultiple");
				if(anticiposPermitidos != null){
					int anticiposPermitido = Integer.valueOf(anticiposPermitidos.getValor());
					if(solicitudes!=null && !solicitudes.isEmpty()){
						for(Solicitud sol : solicitudes){
							if(estatusNoPermitido.contains(sol.getEstadoSolicitud().getIdEstadoSolicitud())){
								solicitudesId.add(sol.getIdSolicitud());
							}
						}
						if(solicitudesId.size()  <= anticiposPermitido){
							solicitudesId.clear();
						}
					}
				}
			}
		}
		return solicitudesId;
	}
}
