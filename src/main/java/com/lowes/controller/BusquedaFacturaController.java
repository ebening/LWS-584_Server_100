package com.lowes.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.BusquedaFacturaDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Factura;
import com.lowes.entity.FacturaArchivo;
import com.lowes.entity.Locacion;
import com.lowes.entity.Moneda;
import com.lowes.entity.Proveedor;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoSolicitud;
import com.lowes.entity.Usuario;
import com.lowes.service.BusquedaFacturaService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.MonedaService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.TipoSolicitudService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class BusquedaFacturaController {
	
	@Autowired
	private TipoSolicitudService tipoSolicitudService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MonedaService monedaService;
	
	@Autowired
	private EstadoSolicitudService estadoSolicitudService;
	
	@Autowired
	private BusquedaFacturaService busquedaFacturaService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private FacturaArchivoService facturaArchivoService;
	
	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;
	
	@RequestMapping("/busquedaFactura")
	public ModelAndView busquedaFactura(HttpSession session){
		
		Usuario usuarioActual = usuarioService.getUsuarioSesion();
		Integer puestoAP = Integer.parseInt(parametroService.getParametroByName("puestoAutorizacionAP").getValor());
		Integer puestoConfirmacionAP = Integer.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
		Integer monedaPesos = Integer.parseInt(parametroService.getParametroByName("idPesos").getValor());
		Boolean fechaDefault = true;
		
		//enviarCombos
		List<Compania> companiaList = busquedaFacturaService.getCompanias();
		
		
		List<TipoSolicitud> tipoSolicitudList = tipoSolicitudService.getAllTipoSolicitudOrder("descripcion");
		List<EstadoSolicitud> estatusList = estadoSolicitudService.getAllEstadoSolicitudOrder("estadoSolicitud");
		List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudService.getAllEstadoSolicitudOrder("estadoSolicitud");
		List<Locacion> locacionList = busquedaFacturaService.getLocaciones();
		List<Usuario> solicitantesList = usuarioService.getUsuariosSolicitantes();
		List<Usuario> autorizadoresList = usuarioService.getUsuariosAutorizadores();
		List<Moneda> monedaList = monedaService.getAllMoneda();

		BusquedaFacturaDTO filtros = new BusquedaFacturaDTO();
		
		List<Factura> facturasBusqueda = new ArrayList<>();
		
		// Verificar si tiene registros de Facturas
		Boolean tieneRegistros = false;
		if (facturasBusqueda != null && facturasBusqueda.size() > Etiquetas.CERO) {
			tieneRegistros = true;
		}
		
		//agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("busquedaFacturaDTO", filtros);
		modelo.put("companiaList", companiaList);
		modelo.put("tipoSolicitudList", tipoSolicitudList);
		modelo.put("estadoSolicitudList", estadoSolicitudList);
		modelo.put("estatusList", estatusList);
		modelo.put("monedaList", monedaList);
		modelo.put("locacionList", locacionList);
		modelo.put("solicitantesList", solicitantesList);
		modelo.put("autorizadoresList", autorizadoresList);
		modelo.put("facturasBusqueda", facturasBusqueda);
		modelo.put("usuarioActual", usuarioActual);
		modelo.put("puestoAP", puestoAP);
		modelo.put("puestoConfirmacionAP", puestoConfirmacionAP);
		modelo.put("monedaPesos", monedaPesos);
		modelo.put("tieneRegistros", "primera");
		modelo.put("fechaDefault", fechaDefault);
		modelo.put("numeroProveedorAsesor", "-1");

		
		if (session.getAttribute("first") == null) {
			modelo.put("first", true);
		}
		else {modelo.put("first", false);}

		if (session.getAttribute("filtrosFactura") != null) {
			modelo.put("filtros", session.getAttribute("filtrosFactura"));
		}
		
		// enviar el modelo completo			
		return new ModelAndView("busquedaFactura", modelo);
	}
	
	@RequestMapping(value = "busquedaFacturaBuscar", method = RequestMethod.POST)
	public ModelAndView busquedaFactura(@ModelAttribute("busquedaFacturaDTO") BusquedaFacturaDTO filtros, HttpServletRequest request, HttpSession session) {
		
		Usuario usuario = usuarioService.getUsuarioSesion();
		Integer puestoAP = Integer.parseInt(parametroService.getParametroByName("puestoAutorizacionAP").getValor());
		Integer puestoConfirmacionAP = Integer.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
		Integer monedaPesos = Integer.parseInt(parametroService.getParametroByName("idPesos").getValor());
		Boolean fechaDefault = false;
		session.setAttribute("filtrosFactura",filtros);
		session.setAttribute("first",false);
		
		// Realizar b�squeda
		List<Factura> facturasBusqueda = busquedaFacturaService.getFacturasBusqueda(filtros, usuario, puestoAP, puestoConfirmacionAP);
		facturasBusqueda = cargaRutaArchivos(facturasBusqueda);
		
		// Se establece cuales son visibles y cuales no
		facturasBusqueda = actualizaPermisos(facturasBusqueda);

		// enviarCombos
		List<Compania> companiaList = busquedaFacturaService.getCompanias();
		
		List<TipoSolicitud> tipoSolicitudList = tipoSolicitudService.getAllTipoSolicitudOrder("descripcion");
		List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudService.getAllEstadoSolicitudOrder("estadoSolicitud");
		List<Moneda> monedaList = monedaService.getAllMoneda();
		List<Locacion> locacionList = busquedaFacturaService.getLocaciones();
		List<Usuario> solicitantesList = usuarioService.getUsuariosSolicitantes();
		List<Usuario> autorizadoresList = usuarioService.getUsuariosAutorizadores();
		
		//verificar si tiene registros de facturas
		Boolean tieneRegistros = false;
		if(facturasBusqueda != null && facturasBusqueda.size() > Etiquetas.CERO){
			tieneRegistros = true;
		}

		// agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("busquedaSolicitudDTO", filtros);
		modelo.put("companiaList", companiaList);
		modelo.put("tipoSolicitudList", tipoSolicitudList);
		modelo.put("estadoSolicitudList", estadoSolicitudList);
		modelo.put("estatusList", estadoSolicitudList);
		modelo.put("monedaList", monedaList);
		modelo.put("locacionList", locacionList);
		modelo.put("solicitantesList", solicitantesList);
		modelo.put("autorizadoresList", autorizadoresList);
		modelo.put("facturasBusqueda", facturasBusqueda);
		modelo.put("usuarioActual", usuario);
		modelo.put("puestoAP", puestoAP);
		modelo.put("puestoConfirmacionAP", puestoConfirmacionAP);
		modelo.put("monedaPesos", filtros.getIdMonedaFiltro());
		modelo.put("tieneRegistros", tieneRegistros);
		modelo.put("fechaDefault", fechaDefault);
		modelo.put("numeroProveedorAsesor", filtros.getIdProveedorFiltro());

		modelo.put("first", false);
		if (session.getAttribute("filtrosFactura") != null) {
			modelo.put("filtros", session.getAttribute("filtrosFactura"));
		}
		// logger.info(solicitudesBusqueda);

		// enviar el modelo completo
		return new ModelAndView("busquedaFactura", modelo);
	}
	
	
	private List<Factura> cargaRutaArchivos (List<Factura> facturas) {
		
		String ruta = "archivos/";

		if (facturas != null && !facturas.isEmpty()) {
			for (Factura f : facturas) {
				List <FacturaArchivo> archivos = facturaArchivoService.getAllFacturaArchivoByIdFactura(f.getIdFactura());
				if (archivos != null && !archivos.isEmpty()) {
					for (FacturaArchivo file: archivos) {
						
						String ruta2 = null;
						
						if (file.getTipoDocumento().getTipoDocumento() == Etiquetas.ARCHIVO_PDF) {
							
						  String numSolicitud = String.valueOf(f.getSolicitud().getIdSolicitud());
						  ruta2 = ruta+numSolicitud+"/";
	                      f.setArchivoPDF(ruta2+file.getArchivo());
						}
	
						if (file.getTipoDocumento().getTipoDocumento() == Etiquetas.ARCHIVO_XML) {
							  String numSolicitud = String.valueOf(f.getSolicitud().getIdSolicitud());
							  ruta2 = ruta+numSolicitud+"/";
		                      f.setArchivoXML(ruta2+file.getArchivo());
						}
	
						if (file.getTipoDocumento().getTipoDocumento() == Etiquetas.ARCHIVO_COMPROBANTE) {
							  String numSolicitud = String.valueOf(f.getSolicitud().getIdSolicitud());
							  ruta2 = ruta+numSolicitud+"/";
		                      f.setArchivoLibre(ruta2+file.getArchivo());
						}
					}
					
				}
				
				// C�lculo de Fecha Vencimiento: fechaFactura + 30 d�as
				Date fechaFactura = f.getFechaFactura();
				Date fechaDate = null;
				try {
					fechaDate = Utilerias.parseDate(Utilerias.convertDateFormat(fechaFactura), "dd/MM/yyyy");
				} catch (ParseException e) {

				}
				LocalDate fecha = fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate fechaVencimiento = fecha.plusDays(Etiquetas.TREINTA);
				Date fechaVencimientoDate =  Date.from(fechaVencimiento.atStartOfDay(ZoneId.systemDefault()).toInstant());
				String fechaV = Utilerias.convertDateFormat(fechaVencimientoDate);
				String fechaFilpped = fechaV.split("/")[2]+"/"+fechaV.split("/")[1]+"/"+fechaV.split("/")[0];
			    f.setFechaVencimiento(fechaFilpped);
			    fechaFilpped = "";
			}
		
		}
		
		return facturas;
	}
	
	private List<Factura> actualizaPermisos(List<Factura> facturasBusqueda) {
		Integer puestoAP = Integer.parseInt(parametroService.getParametroByName("puestoAutorizacionAP").getValor());
		Integer puestoConfirmacionAP = Integer.parseInt(parametroService.getParametroByName("puestoConfirmacionAP").getValor());
		
		for(Factura factura : facturasBusqueda){
			List<SolicitudAutorizacion> autorizadores = solicitudAutorizacionService.getAllSolicitudAutorizacionBySolicitud(factura.getSolicitud().getIdSolicitud());
  		    // resolver el acceso a la solicitud actual.
  		    Integer acceso = Utilerias.validaAcceso(factura.getSolicitud(), factura.getSolicitud().getTipoSolicitud().getIdTipoSolicitud(),usuarioService.getUsuarioSesion(), autorizadores, puestoAP, puestoConfirmacionAP);
  		    if(acceso == Etiquetas.NO_VISUALIZAR){
  		    	factura.getSolicitud().setUrlVisible(false);
  		    }else if(acceso == Etiquetas.VISUALIZAR || acceso == Etiquetas.VISUALIZAR_Y_EDITAR){
  		    	factura.getSolicitud().setUrlVisible(true);
  		    }
		}
		return facturasBusqueda;
	}
	
}