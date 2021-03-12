package com.lowes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.dto.CMDeposito;
import com.lowes.dto.CMDocumento;
import com.lowes.dto.CMDocumentoSoporte;
import com.lowes.entity.ComprobacionDeposito;
import com.lowes.entity.Factura;
import com.lowes.entity.FacturaArchivo;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.service.ComprobacionDepositoService;
//import com.lowes.scheduler.AppConfig;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.util.ContentManagerExport;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class PruebacmController {

	private static final Logger logger = Logger.getLogger(PruebacmController.class);
	
	Etiquetas etiqueta = new Etiquetas("es");

	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private FacturaArchivoService facturaArchivoService;
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	@Autowired
	private ComprobacionDepositoService comprobacionDepositoService;
	
	public PruebacmController() {
		logger.info("DetalleConceptoController()");
	}

	//private static final ApplicationContext ac =  new FileSystemXmlApplicationContext("/WEB-INF/Spring-Quartz.xml");
	
	@RequestMapping(value="/pruebacm", method=RequestMethod.GET)
	public ModelAndView detalleConcepto(@ModelAttribute Solicitud solicitud, 
			@RequestParam(value="email", required=false) String email) {
		List<Solicitud> solicitudList = null;
		try {
			enviaSolicitudesValidadas();
			logger.info("El mensaje enviado ----------------------------");
		} catch (Exception e1) {
			logger.info("El mensaje no pudo ser enviado");
			e1.printStackTrace();
		}		
		return new ModelAndView("detalleConcepto", "solicitudList", solicitudList);
	}
	
	public void enviaSolicitudesValidadas(){
		logger.info("CM Info: Inicia envío de solicitudes validadas a CM.");
		List<Solicitud> solicitudList = solicitudService.getAllSolicitudByStatus(new Integer(Etiquetas.ID_ESTADO_SOLICITUD_VALIDADA));
		if(!solicitudList.isEmpty()){
			logger.info("CM Info: Se enviarán " + solicitudList.size() + " solicitudes.");
			for(Solicitud s : solicitudList){
				logger.info("CM Info: Inicia importacion solicitud: " + s.getIdSolicitud());
				boolean importado = false;
				try{
					importado = importToContent(s);
				}
				catch (Exception e){
					e.printStackTrace();
					logger.info("CM Error: " + e.toString());
				}
				
				if(importado){
					s.setEnviado_CM(1);
					solicitudService.updateSolicitud(s);
					logger.info("CM Info: Solicitud ["+ s.getIdSolicitud() +"] - Documentos importados.");
				}
				else{
					s.setEnviado_CM(2);
					solicitudService.updateSolicitud(s);
					logger.info("CM Info: Los documentos de la solicitud " + s.getIdSolicitud() + " no pudieron ser importados.");
				}
			}
		}else{
			logger.info("CM: No hay documentos a importar.");
		}
	}
	
	/**
	 * @author miguelr
	 * @param solicitud
	 * @return
	 * IMPORTA UNA SOLICITUD A CM
	 */
	private boolean importToContent(Solicitud solicitud) {
		String retVal = null;

		List<Factura> lstFacturas = solicitud.getFacturas();
		List<SolicitudArchivo> lstArchvioSoporte = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(solicitud.getIdSolicitud());
		List<ComprobacionDeposito> lstArchvioDeposito = comprobacionDepositoService.getAllComprobacionDepositoBySolicitud(solicitud.getIdSolicitud());

		List<CMDocumento> archivosFactura = new ArrayList<>();
		List<CMDocumentoSoporte> archivosSoporte = new ArrayList<>();
		List<CMDeposito> archivosDeposito = new ArrayList<>();
		if(!lstFacturas.isEmpty()){
			//import archivos facturas
			for (Factura f : lstFacturas) {
				List<FacturaArchivo> lstArchivo = facturaArchivoService.getAllFacturaArchivoByIdFactura(f.getIdFactura());
				for (FacturaArchivo fa : lstArchivo){
					archivosFactura.add(fillCMDocumento(solicitud, f, fa));
				}			
			}
		}
		if(!lstArchvioSoporte.isEmpty()){
			//import archivos soporte
			for (SolicitudArchivo sa : lstArchvioSoporte) {
				archivosSoporte.add(fillCMDocumentoSoporte(solicitud, sa));
			}
		}
		if(!lstArchvioDeposito.isEmpty()){
			//import archivos soporte
			for (ComprobacionDeposito da : lstArchvioDeposito) {
				archivosDeposito.add(fillCMDeposito(solicitud, da));
			}
		}

		try {
			ContentManagerExport c = new ContentManagerExport();
			// Date fhInicio = new Date();
			// bitCM = new BitCMConnection();
			c.connectToServer();
			for(CMDocumento cmdoc : archivosFactura){
				retVal = c.importDocument(cmdoc);
				if(retVal != null){
					FacturaArchivo factArchivoUpdt = facturaArchivoService.getFacturaArchivo(cmdoc.getIdDocumento());
					factArchivoUpdt.setPidCm(retVal);
					facturaArchivoService.updateFacturaArchivo(factArchivoUpdt);
				}
				else{
					return false;
				}
			}
			for(CMDocumentoSoporte cmdocs : archivosSoporte){
				retVal = c.importDocument(cmdocs);
				if(retVal != null){
					SolicitudArchivo solArchivoUpdt = solicitudArchivoService.getSolicitudArchivo(cmdocs.getIdDocumento());
					solArchivoUpdt.setPidCm(retVal);
					solicitudArchivoService.updateSolicitudArchivo(solArchivoUpdt);
				}
				else{
					return false;
				}
			}
			for(CMDeposito cmdep : archivosDeposito){
				retVal = c.importDocument(cmdep);
				if(retVal != null){
					ComprobacionDeposito compDeposito = comprobacionDepositoService.getComprobacionDeposito(cmdep.getIdDocumento());
					compDeposito.setPidCm(retVal);
					comprobacionDepositoService.updateComprobacionDeposito(compDeposito);
				}
				else{
					return false;
				}
			}
			c.disconnect();
			// Date fhFin = new Date();
			// double tiempo = (fhFin.getTime() - fhInicio.getTime());
			// Usuarios usuario = (Usuarios)
			// Util.getSessionAttribute("userLoged");
			// bitCM.saveMovimientoCM(usuario.getIdUsuario(),
			// Constant.CM_IMPORT, fhInicio, fhFin, tiempo, 1);
		} catch (Exception e) {
			// LOG.error(e);
			return false;
		}
		return true;
	}

	/**
	 * @author miguelr
	 * @param s LA SOLICITUD
	 * @param d EL DEPOSITO
	 * @param da EL ARCHIVO DEPOSITO
	 * @return cdoc OBJETO CMDeposito CON INFORMACIÓN.
	 * PREPARACIÓN DE OBJETO CMDeposito CON INFORMACIÓN DE LA FACTURA Y SOLICITUD PARA SUBIDA A CM
	 */
	private CMDeposito fillCMDeposito(Solicitud solicitud, ComprobacionDeposito da) {
		CMDeposito cdep = new CMDeposito();
		String basePath = Utilerias.getFilesPath() + solicitud.getIdSolicitud()+"/"+ da.getArchivo();
		cdep.setIdDocumento(da.getIdComprobacionDeposito());
		cdep.setIdSolicitud(solicitud.getIdSolicitud());
		cdep.setTipoDocumento(FilenameUtils.getExtension(basePath).toUpperCase());
		cdep.setTipoSolDescripcion(solicitud.getTipoSolicitud().getDescripcion());
		cdep.setSolicitanteNumEmpleado(String.valueOf(solicitud.getUsuarioByIdUsuarioSolicita().getNumeroEmpleado()));
		if(solicitud.getLocacion() != null)
			cdep.setLocacionDesc(solicitud.getLocacion().getNumeroDescripcionLocacion());
		else
			cdep.setLocacionDesc("N/A");
		cdep.setCompaniaDesc(solicitud.getCompania().getDescripcion());
		cdep.setDescProveedor((solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) ? solicitud.getUsuarioByIdUsuarioAsesor().getNombreCompletoUsuario() : solicitud.getProveedor().getDescripcion());
		cdep.setRfcProveedor((solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) ? solicitud.getUsuarioByIdUsuarioAsesor().getRfc() : solicitud.getProveedor().getRfc());
		cdep.setNumProveedor(((solicitud.getTipoProveedor().getIdTipoProveedor() == Etiquetas.UNO) ? String.valueOf(solicitud.getUsuarioByIdUsuarioAsesor().getNumeroEmpleado()) : String.valueOf(solicitud.getProveedor().getNumeroProveedor())));
		cdep.setMontoTotal(da.getMontoDeposito());
		cdep.setMonDescCorta(solicitud.getMoneda().getDescripcionCorta());
		cdep.setCreacionFecha(da.getCreacionFecha());
		cdep.setRuta(basePath);
		cdep.setSolicitud(solicitud);
		return cdep;
	}

	/**
	 * @author miguelr
	 * @param s LA SOLICITUD
	 * @param f LA FACTURA
	 * @param fa EL ARCHIVO FACTURA
	 * @return cdoc OBJETO CMDocumento CON INFORMACIÓN.
	 * PREPARACIÓN DE OBJETO CMDocumento CON INFORMACIÓN DE LA FACTURA Y SOLICITUD PARA SUBIDA A CM
	 */
	private CMDocumento fillCMDocumento(Solicitud s, Factura f, FacturaArchivo fa) {
		CMDocumento cdoc = new CMDocumento();
		String basePath = Utilerias.getFilesPath() + s.getIdSolicitud()+"/"+ fa.getArchivo();
		cdoc.setIdDocumento(fa.getIdFacturaArchivo());
		cdoc.setCompDescripcion(f.getCompaniaByIdCompania().getDescripcion());
		if(f.getProveedor() != null){
			cdoc.setDescProveedor(f.getProveedor().getDescripcion());
			cdoc.setSerie(f.getSerieFactura());
			cdoc.setRfcProveedor(f.getProveedor().getRfc());
			cdoc.setNumProveedor(String.valueOf(f.getProveedor().getNumeroProveedor()));
		}
		else{
			cdoc.setDescProveedor(s.getUsuarioByIdUsuarioSolicita().getNombreCompletoUsuario());
			cdoc.setSerie("null");
			cdoc.setRfcProveedor("null");
			cdoc.setNumProveedor(String.valueOf(s.getUsuarioByIdUsuarioSolicita().getNumeroEmpleado()));
		}		
		cdoc.setFechaFactura(f.getFechaFactura());
		// cdoc.setFechaPagoFactura();
		cdoc.setIdFactura(f.getIdFactura());
		cdoc.setIdSolicitud(s.getIdSolicitud());
		cdoc.setMonDescCorta(f.getMoneda().getDescripcionCorta());
		cdoc.setMontoTotal(f.getTotal());
		cdoc.setPar(f.getPar());
		cdoc.setRuta(basePath);
		cdoc.setTipoDocumento(fa.getTipoDocumento().getDescripcion());
		cdoc.setTipoFactura((f.getTipoFactura() == null) ? "SIN TIPO FACT" : f.getTipoFactura().getDescripcion());
		cdoc.setTipoSolDescripcion(s.getTipoSolicitud().getDescripcion());
		cdoc.setSolicitud(s);
		return cdoc;
	}
	
	/**
	 * @author miguelr
	 * @param solicitud
	 * @param sa EL ARCHIVO DE SOPORTE
	 * @return cdocs OBJETO CMDocumentoSoporte CON LA INFORMACIÓN CARGADA.
	 * PREPARACIÓN DE OBJETO CMDocumentoSoporte CON INFORMACIÓN DEL DOCUMENTO Y SOLICITUD PARA SUBIDA A CM
	 */
	private CMDocumentoSoporte fillCMDocumentoSoporte(Solicitud solicitud, SolicitudArchivo sa) {
		String basePath = Utilerias.getFilesPath() + solicitud.getIdSolicitud()+"/"+ sa.getArchivo();
		CMDocumentoSoporte cdocs = new CMDocumentoSoporte();
		cdocs.setIdDocumento(sa.getIdSolicitudArchivo());
		cdocs.setCompaniaDesc(solicitud.getCompania().getDescripcion());
		cdocs.setIdSolicitud(solicitud.getIdSolicitud());
		if(solicitud.getLocacion() != null)
			cdocs.setLocacionDesc(solicitud.getLocacion().getNumeroDescripcionLocacion());
		else
			cdocs.setLocacionDesc("N/A");
		cdocs.setNumEmpleado(solicitud.getUsuarioByIdUsuarioSolicita().getNumeroEmpleado());
		cdocs.setRuta(basePath);
		cdocs.setTipoDocumento(FilenameUtils.getExtension(basePath).toUpperCase());
		cdocs.setTipoSolDescripcion(solicitud.getTipoSolicitud().getDescripcion());
		cdocs.setSolicitud(solicitud);
		return cdocs;
	}
}