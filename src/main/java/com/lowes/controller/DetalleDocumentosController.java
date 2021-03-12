package com.lowes.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.Factura;
import com.lowes.entity.FacturaArchivo;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudArchivo;
import com.lowes.service.FacturaArchivoService;
import com.lowes.service.FacturaService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Controller
public class DetalleDocumentosController {
	
	private static final Logger logger = Logger.getLogger(AidConfiguracionController.class);
	Etiquetas etiqueta = new Etiquetas("es");
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private SolicitudArchivoService solicitudArchivoService;

	@Autowired
	private FacturaService facturaService;

	@Autowired
	private FacturaArchivoService facturaArchivoService;
	
	@Autowired
	private ParametroService parametroService;
	
	public DetalleDocumentosController() {
		logger.info("DetalleDocumentosController()");
	}
	
	@RequestMapping("/detalleDocumentos")
	public ModelAndView detalleDocumentos(@ModelAttribute Solicitud solicitud) {
		List<Solicitud> solicitudList = solicitudService.getAllSolicitud();
		return new ModelAndView("detalleDocumentos", "solicitudList", solicitudList);
	}

	// metodo ajax para la carga dinamica de información Reembolso
	@RequestMapping(value = "/getDetalleDocumentos", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ResponseEntity<String> getDetalleDocumentos(HttpSession session, @RequestParam String idSolicitudActual,
			HttpServletRequest request, HttpServletResponse response) {

		// objeto de respuesta
		Solicitud solicitud = null;
		List<Factura> facturaList = null;
		List<FacturaArchivo> PDFList = null;
		List<FacturaArchivo> XMLList = null;
		List<FacturaArchivo> comprobanteList = null;
		List<SolicitudArchivo> soporteList = null;
		
		String ruta = "archivos/" +idSolicitudActual + "/";
		String rutaFile = Utilerias.getFilesPath() + idSolicitudActual + "/";



		String json = null;
		ObjectMapper map = new ObjectMapper();
		HashMap<String, String> result = new HashMap<String, String>();
		StringBuilder tablaPDF = new StringBuilder(), tablaXML = new StringBuilder(),
				tablaComprobante = new StringBuilder(), tablaSoporte = new StringBuilder(),
				error = new StringBuilder();
		String pdf, xml, soporte, comprobante;
		Integer idSolicitud = null;

		// consulta
		if (Integer.parseInt(idSolicitudActual) > Etiquetas.CERO) {
			solicitud = solicitudService.getSolicitud(Integer.parseInt(idSolicitudActual));
			idSolicitud = solicitud.getIdSolicitud();
			soporteList = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(Integer.parseInt(idSolicitudActual));
			facturaList = facturaService.getAllFacturaBySolicitud(Integer.parseInt(idSolicitudActual));
			
			if(soporteList != null && soporteList.size() > Etiquetas.CERO){
				for(SolicitudArchivo file : soporteList){
					
					tablaSoporte.append("<tr>");
					tablaSoporte.append("<td>");
					if(isImage(rutaFile+file.getArchivo())){
					tablaSoporte.append("<button title=\"Ver Archivo\" onclick=\"viewIMG('").append(ruta+file.getArchivo()).append("')\" style=\"height: 22px;\" type=\"button\" class=\"btn btn-xs btn-default\">");
					tablaSoporte.append("<span class=\"glyphicon glyphicon-eye-open\"></span></button>");
					}else if(isPDF(file.getArchivo())){
						tablaSoporte.append("<button title=\"Ver Archivo\" onclick=\"viewPDF('").append(ruta+file.getArchivo()).append("')\" style=\"height: 22px;\" type=\"button\" class=\"btn btn-xs btn-default\">");
						tablaSoporte.append("<span class=\"glyphicon glyphicon-eye-open\"></span></button>");
					}
                   
					tablaSoporte.append("<a target=\"_blank\" title=\"Descargar\" class=\"btn btn-xs btn-default\" href=\"").append(ruta+file.getArchivo()).append("\" download=\"").append(file.getDescripcion()).append("\">");
					tablaSoporte.append("<span class=\"downButton glyphicon glyphicon-download\"></span></a>");
					
					tablaSoporte.append("</td>");
                  
					soporte = (file.getDescripcion() != null) ? file.getDescripcion().toString() : "";
					tablaSoporte.append("<td>").append(soporte).append("</td>");
					tablaSoporte.append("</tr>");
					
				}
			}

			if (facturaList != null && facturaList.size() > Etiquetas.CERO) {
				/*
				 * obtener archivos a partir de la solicitud y tipo de documento
				 */
				for (Factura f : facturaList) {					

					XMLList = facturaArchivoService.getAllFacturaArchivoByFacturaTipoDocumento(f.getIdFactura(),
							Etiquetas.UNO);

					for (FacturaArchivo file : XMLList) {
						
						tablaXML.append("<tr>");
						tablaXML.append("<td>");
						tablaXML.append("<a target=\"_blank\" title=\"Descargar\" class=\"btn btn-xs btn-default\" href=\"").append(ruta+file.getArchivo()).append("\" download=\"").append(file.getDescripcion()).append("\">");
						tablaXML.append("<span class=\"downButton glyphicon glyphicon-download\"></span></a>");
						tablaXML.append("</td>");
                      
						xml = (file.getDescripcion() != null) ? file.getDescripcion().toString() : "";
                        tablaXML.append("<td>").append(xml).append("</td>");
                        
                        tablaXML.append("</tr>");
						
					}

					PDFList = facturaArchivoService.getAllFacturaArchivoByFacturaTipoDocumento(f.getIdFactura(),
							Etiquetas.DOS);

					for (FacturaArchivo file : PDFList) {
						tablaPDF.append("<tr>");
						  tablaPDF.append("<td>");
						   tablaPDF.append("<button title=\"Ver Archivo\" onclick=\"viewPDF('").append(ruta+file.getArchivo()).append("')\" style=\"height: 22px;\" type=\"button\" class=\"btn btn-xs btn-default\">");
						   tablaPDF.append("<span class=\"glyphicon glyphicon-eye-open\"></span></button>");
                         
						   //tablaPDF.append("<button title=\"Descargar\" onclick=\"('http://www.website.com/page')\" style=\"height: 22px;\" type=\"button\" class=\"btn btn-xs btn-default\">");
						   //tablaPDF.append("<span class=\"glyphicon glyphicon-download\"></span></button>");
						   
						   tablaPDF.append("<a target=\"_blank\" title=\"Descargar\" class=\"btn btn-xs btn-default\" href=\"").append(ruta+file.getArchivo()).append("\" download=\"").append(file.getDescripcion()).append("\">");
						   tablaPDF.append("<span class=\"downButton glyphicon glyphicon-download\"></span></a>");
						   
                          tablaPDF.append("</td>");
                        
                          pdf = (file.getDescripcion() != null) ? file.getDescripcion().toString() : "";
                          tablaPDF.append("<td>").append(pdf).append("</td>");
                          
 						tablaPDF.append("</tr>");
					}
					
					comprobanteList = facturaArchivoService.getAllFacturaArchivoByFacturaTipoDocumento(f.getIdFactura(), Etiquetas.TRES);
					
					for (FacturaArchivo file : comprobanteList) {
						
						tablaComprobante.append("<tr>");
						tablaComprobante.append("<td>");
						
						if(isImage(rutaFile+file.getArchivo())){
							tablaComprobante.append("<button title=\"Ver Archivo\" onclick=\"viewIMG('").append(ruta+file.getArchivo()).append("')\" style=\"height: 22px;\" type=\"button\" class=\"btn btn-xs btn-default\">");
							tablaComprobante.append("<span class=\"glyphicon glyphicon-eye-open\"></span></button>");
						}
						
						tablaComprobante.append("<a target=\"_blank\" title=\"Descargar\" class=\"btn btn-xs btn-default\" href=\"").append(ruta+file.getArchivo()).append("\" download=\"").append(file.getDescripcion()).append("\">");
						tablaComprobante.append("<span class=\"downButton glyphicon glyphicon-download\"></span></a>");
						
						   tablaComprobante.append("</td>");
                      
						comprobante = (file.getDescripcion() != null) ? file.getDescripcion().toString() : "";
						tablaComprobante.append("<td>").append(comprobante).append("</td>");
						tablaComprobante.append("</tr>");
					}
				}
			}
		}
		
		error.append("<tr>");
		error.append("<td colspan=\"3\">").append(etiqueta.NO_ARCHIVOS_ENCONTRADOS).append("</td>");
		error.append("</tr>");
		
		if(tablaXML.length() > Etiquetas.CERO)
			result.put("tablaXML", tablaXML.toString());
		else
			result.put("tablaXML", error.toString());
		
		if(tablaPDF.length() > Etiquetas.CERO)
			result.put("tablaPDF", tablaPDF.toString());
		else
			result.put("tablaPDF", error.toString());
		
		if(tablaComprobante.length()> Etiquetas.CERO)
			result.put("tablaComprobante", tablaComprobante.toString());
		else
			result.put("tablaComprobante", error.toString()); // poner etiqueta "no se encontraron archivos" en las 4 tablas

		if(tablaSoporte.length() > Etiquetas.CERO)
			result.put("tablaSoporte", tablaSoporte.toString());
		else
			result.put("tablaSoporte", error.toString());
		
		result.put("solicitud", idSolicitud.toString());

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
	
	
	private boolean isImage(String path){
		
		boolean valid = true;
		
		try {
		    BufferedImage image = ImageIO.read(new File(path));
		    if (image == null) {
		        valid = false;
		    }
		} catch(IOException ex) {
		    valid=false;
		}
		
		return valid;
	}
	
	private boolean isPDF(String file){
		
		boolean valid = false;
		
		String ext = FilenameUtils.getExtension(file);
		if(ext.toUpperCase().equals("PDF")){
			valid = true;
		}
		
		
		return valid;
	}

}