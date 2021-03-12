package com.lowes.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
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

import com.lowes.entity.Compania;
import com.lowes.entity.Locacion;
import com.lowes.entity.Perfil;
import com.lowes.entity.Puesto;
import com.lowes.entity.Usuario;
import com.lowes.service.CompaniaService;
import com.lowes.service.LocacionService;
import com.lowes.service.PerfilService;
import com.lowes.service.PuestoService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

import org.jboss.logging.Logger;



/**
 * 
 * @author Josue Sanchez
 * @version 1.0
 * Diciembre 2015
 * 
 */

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CompaniaService companiaService;
	@Autowired
	private LocacionService locacionService;
	@Autowired
	private PerfilService perfilService;
	@Autowired
	private PuestoService puestoService;	
	

	private static final Logger logger = Logger.getLogger(UsuarioController.class);
	
	public UsuarioController(){
		
	}

	/*dejar comentado esto: futuro performance*/
	
//	@PostConstruct
//	public void init(){
//		this.usuariosGlobalList = usuarioService.getAllUsuarios();
//		this.companiasGlobalList = companiaService.getAllCompania();
//		this.locacionesGlobalList = locacionService.getAllLocaciones();
//		this.perfilesGlobalList = perfilService.getAllPerfiles();
//	}
	
	@RequestMapping("usuarios")
	public ModelAndView usuarios(@ModelAttribute Usuario usuario) {
		
		//Usuario usuarioSesion = usuario;

		int currentUserId = usuario.getIdUsuario();
		
		
		// enviar lista de usuarios
		List<Usuario> usuariosList = usuarioService.getAllUsuarios();
		List<Usuario> usuariosJefe = usuarioService.getAllUsuarios();
		
		String autojefe = "";
		
//		for (Usuario jefe : usuariosJefe) {
//			if (currentUserId == jefe.getIdUsuario()) {
//				autojefe = jefe.getNombre()+" - "+usuarioSesion.getNombre();
//				String temp = autojefe;
//				jefe.remo
//			}
//		}
		
		Iterator<Usuario> jefe = usuariosJefe.iterator();
		while (jefe.hasNext()) {
		   Usuario user = jefe.next(); // must be called before you can call i.remove()
		   //autojefe = user.getNombre()+" - "+usuarioSesion.getNombre();
		   if (user.getIdUsuario() == usuario.getIdUsuario()) {
			   jefe.remove();
		   }
		   // Do something
		   
		}
		
		//enviarCombos
		List<Compania> companiaList = companiaService.getAllCompania();
		List<Locacion> locacionList = locacionService.getAllLocaciones();
		List<Perfil>   perfilList  = perfilService.getAllPerfiles();
		List<Puesto> puestoList = puestoService.getAllPuestos();
		
		//agregar listas al modelo
		HashMap<String, Object> modelo = new HashMap<>();
		modelo.put("usuariosList", usuariosList);
		modelo.put("companiaList", companiaList);
		modelo.put("locacionList", locacionList);
		modelo.put("perfilList", perfilList);
		modelo.put("puestoList", puestoList);
		modelo.put("usuariosJefeList", usuariosJefe);
		
		// enviar el modelo completo
		return new ModelAndView("usuarios", "modelo", modelo);
	}
	
	
	@RequestMapping("saveUsuario")
    public ModelAndView saveUsuario(@ModelAttribute Usuario usuario,@RequestParam(value = "imagenp", required = false) MultipartFile imagen_perfil) {
		
		Usuario usuarioSesion = usuarioService.getUsuarioSesion();
		
        if(usuario.getIdUsuario() <= Etiquetas.CERO){ // si el id es mayor a cero entonces se trata de una edici�n
        	
        	//TODO pendiente integrar id de usuario en sesion y puesto locacion
        	usuario.setCreacionFecha(new Date());
        	usuario.setActivo(Etiquetas.UNO_S);
        	usuario.setCreacionUsuario(usuarioSesion.getIdUsuario());
        	
        	//seteando en duro compa�ia
        	Compania comp = new Compania();
        	comp.setIdcompania(usuario.getIdCompania());
        	usuario.setCompania(comp);
        	
        	//seteando en duro locacion
        	Locacion loc = new Locacion();
        	loc.setIdLocacion(usuario.getIdLocacion());
        	usuario.setLocacion(loc);
        	
        	//seteando en duro perfil
        	Perfil perfil = new Perfil();
        	perfil.setIdPerfil(usuario.getIdPerfil());
        	usuario.setPerfil(perfil);
        	
        	//seteando en duro puesto
        	Puesto puesto = new Puesto();
        	puesto.setIdPuesto(usuario.getIdPuesto());
        	usuario.setPuesto(puesto);
        	
        	//seteando jefe        	
        	if(usuario.getIdUsuarioJefe() != -1 && usuario.getIdUsuario() != usuario.getIdUsuarioJefe() && usuarioService.getUsuario(usuario.getIdUsuarioJefe())!=null){
        		Usuario jefe = new Usuario();
        		jefe.setIdUsuario(usuario.getIdUsuarioJefe());
        		usuario.setUsuario(jefe);
        	}
        	
        	//BOOLEANOS
        	if(usuario.getEsSolicitanteB()){ usuario.setEsSolicitante(Etiquetas.UNO_S);}
        	else { usuario.setEsSolicitante(Etiquetas.CERO_S);}
        	
        	if(usuario.getEsAutorizadorB()){usuario.setEsAutorizador(Etiquetas.UNO_S);}
        	else {usuario.setEsAutorizador(Etiquetas.CERO_S);} 
        	        	
        	if(usuario.getEspecificaSolicitanteB()){ usuario.setEspecificaSolicitante(Etiquetas.UNO_S);}
        	else { usuario.setEspecificaSolicitante(Etiquetas.CERO_S);}
        	
        	if(usuario.getEsBeneficiarioCajaChicaB()) { usuario.setEsBeneficiarioCajaChica(Etiquetas.UNO_S);}
        	else { usuario.setEsBeneficiarioCajaChica(Etiquetas.CERO_S);}
        	        
        	usuario.setContrasena(Utilerias.toSHA1(usuario.getContrasena()));
        	
        	
        	
        	if(imagen_perfil != null && imagen_perfil.isEmpty() == false){
        		
        		// para guardar el nombre del archivo
	        	String descripcion = imagen_perfil.getOriginalFilename();
				String extencion = FilenameUtils.getExtension(descripcion).toUpperCase();
				// se crea un nombre de imagen generico
				String UID = UUID.randomUUID().toString();
            	String nombreImagenPerfil = UID+"."+extencion;
            	// seteando para guardarlo en la bd
            	usuario.setFotoPerfil(nombreImagenPerfil);
        		
	        	// guardamos la imagen de perfil en directorio  (metodo void)
	        	guardarImagenDePerfilArchivo(imagen_perfil,nombreImagenPerfil);
	        	
        	}
        	
        	
        	usuarioService.createUsuario(usuario);
        	
        	} else {
        		
        		Usuario usuarioEdicion = usuarioService.getUsuario(usuario.getIdUsuario());
	        	usuario.setCreacionFecha(usuarioEdicion.getCreacionFecha());
	        	usuario.setCreacionUsuario(usuarioEdicion.getCreacionUsuario());
	        	usuario.setFotoPerfil(usuarioEdicion.getFotoPerfil());
	        	//TODO pendiente de definir la funcionalidad de activo
	        	usuario.setActivo(usuarioEdicion.getActivo());
	        	
	        	//seteando objeto compa�ia
	        	Compania comp = new Compania();
	        	comp.setIdcompania(usuario.getIdCompania());
	        	usuario.setCompania(comp);
	        	
	        	//seteando objeto locacion
	        	Locacion loc = new Locacion();
	        	loc.setIdLocacion(usuario.getIdLocacion());
	        	usuario.setLocacion(loc);
	        	
	        	//seteando objeto perfil
	        	Perfil perfil = new Perfil();
	        	perfil.setIdPerfil(usuario.getIdPerfil());
	        	usuario.setPerfil(perfil);
	        	
	        	//seteando en duro puesto
	        	Puesto puesto = new Puesto();
	        	puesto.setIdPuesto(usuario.getIdPuesto());
	        	usuario.setPuesto(puesto);
	        	
	        	//BOOLEANOS
	        	if(usuario.getEsSolicitanteB()){ usuario.setEsSolicitante(Etiquetas.UNO_S);}
	        	else { usuario.setEsSolicitante(Etiquetas.CERO_S);}
	        	
	        	if(usuario.getEsAutorizadorB()){usuario.setEsAutorizador(Etiquetas.UNO_S);}
	        	else {usuario.setEsAutorizador(Etiquetas.CERO_S);} 
	        	
	        	if(usuario.getEspecificaSolicitanteB()){ usuario.setEspecificaSolicitante(Etiquetas.UNO_S);}
	        	else { usuario.setEspecificaSolicitante(Etiquetas.CERO_S);}
	        	
	        	if(usuario.getEsBeneficiarioCajaChicaB()) { usuario.setEsBeneficiarioCajaChica(Etiquetas.UNO_S);}
	        	else { usuario.setEsBeneficiarioCajaChica(Etiquetas.CERO_S);}
	        	
	        	if(usuario.getIdUsuarioJefe() != -1 && usuario.getIdUsuario() != usuario.getIdUsuarioJefe() && usuarioService.getUsuario(usuario.getIdUsuarioJefe())!=null){
	        		Usuario jefe = new Usuario();
	        		jefe.setIdUsuario(usuario.getIdUsuarioJefe());
	        		usuario.setUsuario(jefe);
	        	}
	        	       
	        	usuario.setModificacionUsuario(usuarioSesion.getIdUsuario());
	        	usuario.setModificacionFecha(new Date());
	        	
	        	if (usuarioEdicion.getContrasena() != null 
	        			&& usuarioEdicion.getContrasena().equals(usuario.getContrasena()) == false ) {        		
	        		usuario.setContrasena(Utilerias.toSHA1(usuario.getContrasena()));
	        	}
	        	
	        	if(imagen_perfil != null && imagen_perfil.isEmpty() == false){
	        		
	        		// para guardar el nombre del archivo
		        	String descripcion = imagen_perfil.getOriginalFilename();
					String extencion = FilenameUtils.getExtension(descripcion).toLowerCase();
					// se crea un nombre de imagen generico
					String UID = UUID.randomUUID().toString();
	            	String nombreImagenPerfil = UID+"."+extencion;
	            	// seteando para guardarlo en la bd
	            	usuario.setFotoPerfil(nombreImagenPerfil);
	        		
		        	// guardamos la imagen de perfil en directorio  (metodo void)
		        	guardarImagenDePerfilArchivo(imagen_perfil,nombreImagenPerfil);
		        	
	        	}
	        	
	        	usuarioService.updateUsuario(usuario);
        }
        return new ModelAndView("redirect:usuarios");
    }
	
	
	@RequestMapping("eliminarUsuario")
	public ModelAndView eliminarUsuario(@RequestParam int id){
		usuarioService.deleteUsuarioe(id);
        return new ModelAndView("redirect:usuarios");
	}
		  
	  // metodo ajax para la carga dinamica de edicion.
	  @RequestMapping(value = "/getUsuarioEdit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody
	    ResponseEntity<String> sendDataEdit(HttpSession session, @RequestParam Integer id, HttpServletRequest request, HttpServletResponse response) {

		    String json = null;
	        HashMap<String, String> result = new HashMap<String, String>();
	        Usuario usuarioEdicion = new Usuario();
	        
	        // traer el usuario para editarlo.
	        if(id > 0){
		        usuarioEdicion = usuarioService.getUsuario(id);
	        }
	        
	        if(usuarioEdicion != null){
	        	result.put("idUsuario", String.valueOf(usuarioEdicion.getIdUsuario()));
	        	result.put("numeroEmpleado", String.valueOf(usuarioEdicion.getNumeroEmpleado()));
				result.put("nombre", usuarioEdicion.getNombre());
				result.put("apellidoPaterno", usuarioEdicion.getApellidoPaterno());
				result.put("apellidoMaterno", usuarioEdicion.getApellidoMaterno());
				result.put("correoElectronico", usuarioEdicion.getCorreoElectronico());
				result.put("cuenta", usuarioEdicion.getCuenta());
				result.put("contrasena", usuarioEdicion.getContrasena());
	        	result.put("idCompania", String.valueOf(usuarioEdicion.getCompania().getIdcompania()));
	        	result.put("idLocacion", String.valueOf(usuarioEdicion.getLocacion().getIdLocacion()));
	        	result.put("idPerfil", String.valueOf(usuarioEdicion.getPerfil().getIdPerfil()));
	        	result.put("idPuesto", String.valueOf(usuarioEdicion.getPuesto().getIdPuesto()));
	        	
	        	if(usuarioEdicion.getUsuario() != null)
	        		result.put("idJefe", String.valueOf(usuarioEdicion.getUsuario().getIdUsuario()));
	        	else
	        		result.put("idJefe", String.valueOf(Etiquetas.CERO));
	        	
	        	result.put("solicitante", String.valueOf(usuarioEdicion.getEsSolicitante()));
	        	result.put("autorizador", String.valueOf(usuarioEdicion.getEsAutorizador()));
	        	result.put("figuraContable", String.valueOf(usuarioEdicion.getEsFiguraContable()));
	        	result.put("especificaSolicitante", String.valueOf(usuarioEdicion.getEspecificaSolicitante()));
	        	result.put("beneficiarioCajaChica", String.valueOf(usuarioEdicion.getEsBeneficiarioCajaChica()));
	        	
	        	// para detectar si tiene foto de perfil
	        	if(usuarioEdicion.getFotoPerfil() != null && usuarioEdicion.getFotoPerfil().isEmpty() == false){
	        		result.put("tieneFotoPerfil", "true");
	        		result.put("nombreFotoPerfil", usuarioEdicion.getFotoPerfil());
	        	}else{
	        		result.put("tieneFotoPerfil", "false");
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
	  
	  
	  // metodo ajax para la carga dinamica de edicion.
	  @RequestMapping(value = "/checkUsuario", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public @ResponseBody ResponseEntity<String> checkUsuario(HttpSession session, 
	    @RequestParam(value = "id" , required = false) Integer id, 
	    @RequestParam(value = "numeroEmpleado" , required = false) Integer numeroEmpleado,
	    @RequestParam(value = "cuenta" , required = false) String cuenta, 
	    @RequestParam(value = "idLocacion" , required = false) Integer idLocacion , 
	    HttpServletRequest request, HttpServletResponse response) {

		    String json = null;
	        HashMap<String, String> result = new HashMap<String, String>();
	        
	        // prevenir un nulo cuando es usuario nuevo.
	        if(id == null){
	        	id = 0;
	        }
	        
	        //verificar si existen los valores en la lista de usuarios.
	        boolean correo = false;
	        boolean locacion = false;
	        boolean numEmpleado = false;
	        boolean cuentaban = false;
			List<Usuario> usuariosList = usuarioService.getAllUsuarios();
			String usuarioLocacion = null;
	        
	        for(Usuario usr : usuariosList){
	        	
	        	//busca por numeros repetidos
	        	if(usr.getNumeroEmpleado() == numeroEmpleado && id != usr.getIdUsuario()){
	        		numEmpleado = true;
	        	}
	        	
	        	//busca correos repetidos
//	        	if(usr.getCorreoElectronico().equals(correoElectronico) && id != usr.getIdUsuario()){
//	        		correo = true;
//	        	}
	        	
	        	//busca cuentas repetidas
	        	if(usr.getCuenta().equals(cuenta) && id != usr.getIdUsuario()){
	        		cuentaban = true;
	        	}
	        	
	        	
	        	if(usr.getLocacion().getIdLocacion() == idLocacion && id != usr.getIdUsuario() && usr.getEsBeneficiarioCajaChica() == Etiquetas.UNO_S){
	        		locacion = true;
	        		usuarioLocacion = usr.getNombre()+" "+usr.getApellidoPaterno()+" "+usr.getApellidoMaterno();
	        	}
	        	
	        	// el ciclo se detiene si solo las tres banderas de busqueda estan activas
	        	if(locacion == true && numEmpleado == true && cuentaban == true){
	        		break;
	        	}
	        }
	            //seteo de valores
	        	result.put("numeroEmpleado", String.valueOf(numEmpleado));
				result.put("correoElectronico", String.valueOf(correo));
				result.put("cuenta", String.valueOf(cuentaban));
				result.put("locacion", String.valueOf(locacion));
				result.put("usuarioLocacion", usuarioLocacion);

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
	  
    //getters and setters
	  
	  private void guardarImagenDePerfilArchivo(MultipartFile imagen, String nombre_definido){
		  
			// path para guardar las imagenes de perfil
			String ruta = Utilerias.getImagenesdePerfilPath();
			File archivo = null;
			byte[] bytes;

			File folder = new File(ruta);
			if(!folder.exists()){
				folder.mkdirs();
			}
			archivo = new File(ruta+nombre_definido);
		  
			try {
				
				bytes = imagen.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(archivo));
				buffStream.write(bytes);
				buffStream.close();
				
			} catch (IOException e) {
				logger.error(e);
			}
			
		  
	  }
	  

}
