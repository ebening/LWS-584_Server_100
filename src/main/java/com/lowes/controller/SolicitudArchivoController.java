package com.lowes.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lowes.entity.SolicitudArchivo;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudArchivoService;
import com.lowes.service.SolicitudService;
import com.lowes.util.Etiquetas;
import com.lowes.util.PropertyUtil;
import com.lowes.util.Utilerias;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class SolicitudArchivoController {
	
	
	
	private static final Logger logger = Logger.getLogger(SolicitudArchivoController.class);

	@Autowired
	private SolicitudArchivoService solicitudArchivoService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private ParametroService parametroService;

	Etiquetas etiqueta = new Etiquetas("es");
	
	String ruta;
	File archivo;
	List<String> fileNames =  new ArrayList<>();

	@RequestMapping("solicitudArchivo")
	public ModelAndView solicitudArchivo() {
		List<SolicitudArchivo> solicitudArchivoList = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(Etiquetas.UNO); // tomar idSolicitud de sesiï¿½n
		return new ModelAndView("solicitudArchivo", "solicitudArchivoList", solicitudArchivoList);
	}
	
	@RequestMapping(value="solicitudArchivoLista", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> solicitudArchivoLista(@RequestParam Integer idSolicitud) {
		
		List<SolicitudArchivo> solicitudArchivoList = solicitudArchivoService.getAllSolicitudArchivoBySolicitud(idSolicitud); // tomar idSolicitud de sesiï¿½n
		
		String json = null;
		ObjectMapper map = new ObjectMapper();
        HashMap<String, String> result = new HashMap<String, String>();
		StringBuilder tabla = new StringBuilder();
		
		fileNames.clear();
		Integer f = 0;
		
		if(solicitudArchivoList!=null && solicitudArchivoList.size()>Etiquetas.CERO){
			for(SolicitudArchivo var: solicitudArchivoList){
				
				fileNames.add(f,var.getDescripcion());
				
				tabla.append("<tr class=\"odd gradeX\">");
				tabla.append("<td>"+var.getArchivo()+"</td>");
				tabla.append("<td class=\"center\">");
				tabla.append("<button onclick=\"eliminar("+var.getIdSolicitudArchivo()+")\"");
				tabla.append("style=\"height: 22px;\" type=\"button\"");
				tabla.append("class=\"btn btn-xs btn-danger\">");
				tabla.append("<span class=\"glyphicon glyphicon-trash\"></span>");
				tabla.append("</button>");
				tabla.append("</td>");
				tabla.append("</tr>");
			}
		}
		
		result.put("listaArchivos", tabla.toString());
		
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


	@RequestMapping(value="saveSolicitudDocument", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> singleSave(MultipartHttpServletRequest request, HttpServletResponse response, @RequestParam Integer idSolicitud) {
		
		// va a venir como parï¿½metro. ID de sesiï¿½n
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
				
				boolean repetido = existe(file.getOriginalFilename(),fileNames);
				String resultado;
				if (repetido) {
//					logger.error(etiqueta.ERROR, e);
//					String[] fileArray = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
					result.put("existe", "true");
				}
				else {
					fileName = UID + "_" +file.getOriginalFilename();
					archivo = new File(ruta + fileName);
					
					if(!FilenameUtils.isExtension(archivo.getName(),"gif")) {
					
							byte[] bytes = file.getBytes();
							BufferedOutputStream buffStream = new BufferedOutputStream(
									new FileOutputStream(archivo));
							buffStream.write(bytes);
							buffStream.close();
			
							logger.info(etiqueta.ATENCION + etiqueta.ARCHIVO_ANEXADO);
					
							solicitudArchivo.setIdSolicitud(idSolicitud);
							solicitudArchivo.setSolicitud(solicitudService.getSolicitud(idSolicitud));
							solicitudArchivo.setDescripcion(file.getOriginalFilename()); // llenado en otra parte del proceso
							solicitudArchivo.setArchivo(fileName);
							solicitudArchivo.setPidCm("PID_CM"); // llenado en otra parte del proceso
							solicitudArchivo.setActivo((short) 1);
							solicitudArchivo.setCreacionFecha(new Date());
							solicitudArchivo.setCreacionUsuario(-1); //llenado con el usuario en sesiï¿½n
							
							solicitudArchivoService.createSolicitudArchivo(solicitudArchivo);
							
							result.put("anexado", "true");
	//					} // verifica tamaï¿½o
					}else{
						logger.info(etiqueta.ERROR + etiqueta.EXTENSION_INVALIDA);				
						result.put("invalido", "true");
					}
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
	
	// metodo para eliminar
	 @RequestMapping(value = "deleteSolicitudArchivo", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody
	    ResponseEntity<String> sendMessage(HttpSession session, @RequestParam String intxnId, HttpServletRequest request, HttpServletResponse response) {
    	
    	HashMap<String, String> result = new HashMap<String, String>();
		String json = null;
		Integer idArchivo = Integer.valueOf(intxnId);
		SolicitudArchivo archivoSolicitud = solicitudArchivoService.getSolicitudArchivo(idArchivo);
		ruta = Utilerias.getFilesPath() + archivoSolicitud.getSolicitud().getIdSolicitud()  + "/";	
		File archivo = new File(ruta+archivoSolicitud.getArchivo());
		

    	try {
    		if (archivo.delete()){
    			logger.info(etiqueta.ATENCION + etiqueta.ARCHIVO_ELIMINADO);
    			result.put("eliminado", "true");
    		}else{
    			logger.info(etiqueta.ATENCION + etiqueta.ARCHIVO_NO_ELIMINADO);
    			result.put("eliminado", "false");
    		}
    		solicitudArchivoService.deleteSolicitudArchivo(Integer.parseInt(intxnId));
		} catch (Exception e) {
			if(e.getMessage().contains(Etiquetas.CAUSE_DEPENDENCY)){
				logger.error(etiqueta.ERROR_DEPENDENCIAS,e);
				result.put("error_dependencia", "true");

			}else{
				logger.error(etiqueta.ERROR_DELETE,e);
				result.put("error_delete", "true");
			}
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

	  @RequestMapping(value = "/validaTamano", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody
	    ResponseEntity<String> validaTamano(HttpSession session, @RequestParam String peso, HttpServletRequest request, HttpServletResponse response) {
		  HashMap<String, String> result = new HashMap<String, String>();
		  String json = null;
		  String maxUpload = PropertyUtil.getProperty("upload.maxUploadSize");
		  Integer tamano=Integer.parseInt(maxUpload);
		  Integer size= Integer.parseInt(peso);
		  
		  if(size>tamano){
			  result.put("valido", "true");
		  }
		  else{
			  result.put("valido", "false");
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
	
	  // Función que comprueba si el nombre del archivo existe
	  public boolean existe(String file, List<String> files) {
			//System.out.println("#1 iterator");
			Iterator<String> iterator = files.iterator();
			Integer i=0;
			boolean igual = false;
			while (iterator.hasNext()) {
				iterator.next();
				String name = files.get(i);
				if (new String(name).equals(file)) {
					igual = true;
					break;
					}
				i++;
				}
		  return igual;
	  }

}