package com.lowes.service.impl;

import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoCriterio;
import com.lowes.entity.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.AutorizadorProveedorRiesgo;
import com.lowes.entity.EstadoAutorizacion;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.Proveedor;
import com.lowes.service.CriterioService;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.service.AutorizadorProveedorRiesgoService;
import com.lowes.service.EmailService;

@Configuration
@Service
@Transactional
public class CriterioVendorRiskImpl implements CriterioService {

	private static final Logger logger = Logger.getLogger(CriterioSolicitanteImpl.class);
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AutorizadorProveedorRiesgoService autorizadorProveedorRiesgoService;

	@Autowired
	private EstadoSolicitudService estadoSolicitudService;

	@Autowired
	private EstadoAutorizacionService estadoAutorizacionService;

	@Autowired
	private TipoCriterioService tipoCriterioService;

	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ParametroService parametroService;
	
	private String ultimoAutorizador;
	private Usuario usuarioAutorizador;

	@Override
	public boolean crearSolicitud(Solicitud solicitud, Usuario usuario) {
		// Se valida si el proveedor esta especificado como vendor risk
		Proveedor proveedor = solicitud.getFacturas().get(0).getProveedor();
		
		if(proveedor != null){
//			if(false){
			List<AutorizadorProveedorRiesgo> autorizadorProveedorRiesgo = new ArrayList<>();
			
			
			//si es un proveedor de riesgo se buscan sus autorizadores.
			if(proveedor != null && proveedor.getProveedorRiesgo() == Etiquetas.UNO_S){
			autorizadorProveedorRiesgo = autorizadorProveedorRiesgoService
				.getAutorizadorProveedorRiesgoByProveedor(proveedor.getIdProveedor());
			}
					
			// Obtener último autorizador
			String autorizador = "";
			this.setUltimoAutorizador(null);
			if (solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud()) != null
					&& solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud())
							.getUsuarioByIdUsuarioAutoriza() != null) {
				Usuario u = solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud())
						.getUsuarioByIdUsuarioAutoriza();
				autorizador = u.getNombre() + " " + u.getApellidoPaterno() + " " + u.getApellidoMaterno();
				this.setUltimoAutorizador(autorizador);
			}

			if (autorizadorProveedorRiesgo != null && autorizadorProveedorRiesgo.size() > Etiquetas.CERO) {

				// Marcar pasos anteriores como "no actual"
				List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService
						.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());

				for (SolicitudAutorizacion sa : solicitudAutorizacionAnterior) {
					if(sa.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO){
					  sa.setUltimoMovimiento(Etiquetas.CERO_S);
					  solicitudAutorizacionService.updateSolicitudAutorizacion(sa);
					}
				}
				
				// Obtener el último autorizador de la solicitud
				SolicitudAutorizacion ultimoAutorizadorDeLaSolicitud = solicitudAutorizacionService
						.getLastSolicitudAutorizacion(solicitud.getIdSolicitud());
                
				//para saber el orden de envio de los autorizadores segun el nivel.
				Usuario usuarioAutorizador = getSiguienteAutorizador(solicitud, autorizadorProveedorRiesgo,ultimoAutorizadorDeLaSolicitud);
				this.setUsuarioAutorizador(usuarioAutorizador);
				boolean creado = false;


				
				if (usuarioAutorizador != null) {
					logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: "
							+ usuarioAutorizador.getIdUsuario());
					
					// saber si es la primera vez que se valida este criterio.
					boolean primeraVez = true;
					if(ultimoAutorizadorDeLaSolicitud != null && ultimoAutorizadorDeLaSolicitud.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VENDOR_RISK){
						primeraVez = false;
					}
					
					/* SOLO LO HACE LA PRIMERA VEZ QUE SE EJECUTA ESTE METODO Y VALIDA TODOS LOS AUTORIZADORES DEL SET */
					if(primeraVez){
						for(AutorizadorProveedorRiesgo usrAutorizador : autorizadorProveedorRiesgo ){
							
							if(this.yaAutorizo(solicitud, usrAutorizador.getUsuario()) || autorizadorEsSolicitanteCreador(usuarioAutorizador, solicitud) ){
			                    /* SI YA AUTORIZO PREVIAMENTE ESTE USUARIO, YA NO SE ENVIA A VALIDAR AL USUARIO: INTERNAMENTE SE AUTORIZA */
								/*SI EL AUTORIZADOR ES EL USUARIO QUE REGISTRO LA SOLICITUD TAMBIEN SE OMITE (SE CONFIRMA AUTOMATICAMENTE)*/
								this.crearSolicitudConfirmada(solicitud, usrAutorizador.getUsuario(), usuario);
							}
							
						}
					}
					
					//si el siguiente autorizador es un autorizador repetido se busca el siquiente.
					if(this.yaAutorizo(solicitud, usuarioAutorizador)){
						usuarioAutorizador = getSiguienteAutorizador(solicitud, autorizadorProveedorRiesgo,ultimoAutorizadorDeLaSolicitud);
						this.setUsuarioAutorizador(usuarioAutorizador);
					}
	                    
					if(usuarioAutorizador != null){
					
						
						
						/* SI ESTE USUARIO NO A AUTORIZADO PREVIAMENTE Y NO ES EL CREADOR DE LA SOLICITUD SE ENVIA AL USUARIO PARA SU AUTORIZACIÓN */
						
						SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO,
								estadoAutorizacionService.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR), // 1
								estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR), // 2
								solicitud, tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_VENDOR_RISK), // 3
								usuarioAutorizador, usuario, Etiquetas.UNO_S, Etiquetas.UNO_S, new Date(),
								usuario.getIdUsuario());

						solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);

						creado = true;
						
					}
					
					return creado;
                     
					
				} else {
					logger.info("El criterio vendor risk no se cumple.");
					return false;
				}
			} else {
				logger.info("El criterio de Vendor Risk no se cumple porque no existen autorizadores configurados.");
				return false;
			}
		}else {
			logger.info("El criterio de Vendor Risk no se cumple porque el proveedor no se encuentra especificado en el sistema.");
			return false;
		}
	}

	private Usuario getSiguienteAutorizador(Solicitud solicitud,
			List<AutorizadorProveedorRiesgo> autorizadorProveedorRiesgo, SolicitudAutorizacion ultimoAutorizadorDeLaSolicitud) {

		Usuario usuarioNivel = null;
		boolean encontrado = false;
	
		// si no hay autorizador registrado o el registro está en estado rechazado se inicia desde el primer nivel
		if (ultimoAutorizadorDeLaSolicitud != null && ultimoAutorizadorDeLaSolicitud.getEstadoAutorizacion().getIdEstadoAutorizacion() != Etiquetas.ESTADO_AUTORIZACION_RECHAZADO 
			&& ultimoAutorizadorDeLaSolicitud.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VENDOR_RISK) {
			// se recorren los autorizadores configurados
			for (AutorizadorProveedorRiesgo autorizador : autorizadorProveedorRiesgo) {
				logger.info("NIVEL AUTORIZACION: " + autorizador.getNivelAutoriza().getIdNivelAutoriza());
				// si está marcado el flag de encontrado entonces toma el usuario actual del ciclo
				if (encontrado == false) {
					if (autorizador.getUsuario().getIdUsuario() == ultimoAutorizadorDeLaSolicitud.getUsuarioByIdUsuarioAutoriza()
							.getIdUsuario()) {
						// si el último autorizador rechazó entonces la
						// solicitud inicia el ciclo de autorizacion
						// desde el primer nivel
						if (ultimoAutorizadorDeLaSolicitud.getEstadoAutorizacion()
								.getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_RECHAZADO) {
							usuarioNivel = autorizadorProveedorRiesgo.get(0).getUsuario();
							break;
						} else {
							// el siguiente usuario seral el priximo
							// autorizador.
							encontrado = true;
							continue;
						}
					}
				} else {
					if(yaAutorizoCriterioVendorRisk(solicitud, autorizador.getUsuario())){
					 	continue;
					}else{
						usuarioNivel = autorizador.getUsuario();
						break;
					}
					
				}
			}
		} else {
			// si no hay registros en la tabla el usuario con nivel 1 
			// o el ultimo estado es rechazada entonces se toma el autorizador
			// de primer nivel
			usuarioNivel = autorizadorProveedorRiesgo.get(0).getUsuario();
		}

		return usuarioNivel;
	}
	
	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {
//		El paso está incompleto mientras el número de autorizadores sea diferente del
//		número de usuario que ya autorizaron en la tabla de SolicitudAutorización
		List<AutorizadorProveedorRiesgo> autorizadoresProveedorRiesgo = autorizadorProveedorRiesgoService.getAutorizadorProveedorRiesgoByProveedor(solicitud.getFacturas().get(0).getProveedor().getIdProveedor());
		
		//lista auxiliar para ir guardando los autorizadores actuales
		List<SolicitudAutorizacion> autorizaron = new ArrayList<>();

		// trae todo el set de registros de autorizadores de la tabla solicitud autorizacion
//		List<SolicitudAutorizacion> autorizaronTodos = solicitudAutorizacionService.getAllAutorizadoresByCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_VENDOR_RISK);
		List<SolicitudAutorizacion> allAutorizadoresList = solicitudAutorizacionService.getAllSolicitudAutorizacionBySolicitud(solicitud.getIdSolicitud());
				
		//recorre toda la lista de autorizadores en solicitud autorizacion
		if(allAutorizadoresList != null && allAutorizadoresList.size() > Etiquetas.CERO){
			for(SolicitudAutorizacion autorizo : allAutorizadoresList){
				if (autorizo.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_RECHAZADO) {
					autorizaron.clear();
				} else {
					if (autorizo.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_VENDOR_RISK
							&& autorizo.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO)
						// de lo contrario lo agrega
						autorizaron.add(autorizo);
				}
				
			}
		}
//				if (autorizaronTodos != null && autorizaronTodos.size() > Etiquetas.CERO) {
//					for (SolicitudAutorizacion autorizo : autorizaronTodos) {
//						//si encuentra un rechazado limpia la lista
//						if (autorizo.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_RECHAZADO) {
//							autorizaron.clear();
//						} else {
//							//de lo contrario lo agrega
//							autorizaron.add(autorizo);
//						}
//					}
//					//al final se obtendra una lista correcta de los autorizadores que ya validaron después de un rechazo.
//				}
				

		return autorizadoresProveedorRiesgo.size() == autorizaron.size();
		
	}

	@Override
	public void enviarCorreo(Solicitud solicitud) {
		logger.info("enviar correo CriterioVendorRisk");
		// Notificación email
		
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA){
			this.setUltimoAutorizador(null);
		}

		// Verificar en base de datos si el envío de notificaciones está activo
		Integer envioCorreo = Integer
				.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());

		if (envioCorreo == Etiquetas.UNO) {
			try {
				logger.info("Enviando correo a: " + this.getUsuarioAutorizador().getNumeroNombreCompletoUsuario()
						+ " - Solicitud: " + solicitud.getIdSolicitud());
				emailService.enviarCorreo(Etiquetas.CRITERIO_TRES, this.getUltimoAutorizador(),
						this.getUsuarioAutorizador().getCorreoElectronico(), solicitud);
				
			} catch (Exception e) {
				logger.info("El mensaje no pudo ser enviado");
			}
		}
	}

	public String getUltimoAutorizador() {
		return ultimoAutorizador;
	}

	public void setUltimoAutorizador(String ultimoAutorizador) {
		this.ultimoAutorizador = ultimoAutorizador;
	}
	
	public Usuario getUsuarioAutorizador() {
		return usuarioAutorizador;
	}

	public void setUsuarioAutorizador(Usuario usuarioAutorizador) {
		this.usuarioAutorizador = usuarioAutorizador;
	}
	
	@Override
	public boolean crearSolicitudConfirmada(Solicitud solicitud, Usuario usuarioAutoriza, Usuario usuarioSolicita) {

		SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion();
		
		//setters para la autorizacion automatica.
		solicitudAutorizacion.setSolicitud(solicitud);
		solicitudAutorizacion.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR));
		solicitudAutorizacion.setTipoCriterio(new TipoCriterio(Etiquetas.TIPO_CRITERIO_VENDOR_RISK));
		solicitudAutorizacion.setEstadoAutorizacion(new EstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO));
		solicitudAutorizacion.setUsuarioByIdUsuarioEnvia(usuarioSolicita);
		solicitudAutorizacion.setUsuarioByIdUsuarioAutoriza(usuarioAutoriza);
		solicitudAutorizacion.setUltimoMovimiento(Etiquetas.CERO_S);  // revisar comportamiento de ultimo movimiento
		solicitudAutorizacion.setFechaAutoriza(new Date());
		
		/*campos de control de tabla*/
		
		solicitudAutorizacion.setActivo(Etiquetas.UNO_S);
		solicitudAutorizacion.setCreacionFecha(new Date());
		solicitudAutorizacion.setCreacionUsuario(usuarioService.getUsuarioSesion().getIdUsuario());
		solicitudAutorizacion.setVisible(Etiquetas.CERO_S);
		
		Integer idSolAut = solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);
		
		if(idSolAut != null && idSolAut > 0){
			return true;
		}else{
			return false;
		}
		
	}
	
	@Override
	public boolean yaAutorizo(Solicitud solicitud, Usuario usuario) {
		
		Integer autorizacionesPorUsuario = solicitudAutorizacionService.getCountAllByUsuarioAutorizo(solicitud.getIdSolicitud(), usuario.getIdUsuario());
		
		if(autorizacionesPorUsuario > 0){
			return true;
		}else{
			return false;
		}
		
	}
	
  public boolean yaAutorizoCriterioVendorRisk(Solicitud solicitud, Usuario usuario) {
		
	Integer autorizacionesPorUsuarioCriterio = solicitudAutorizacionService.getCountAllByUsuarioAutorizoCriterio(solicitud.getIdSolicitud(), usuario.getIdUsuario(), Etiquetas.TIPO_CRITERIO_VENDOR_RISK);
	
	if(autorizacionesPorUsuarioCriterio > 0){
		return true;
	}else{
		return false;
	}
		
}
  
	@Override
	public boolean autorizadorEsSolicitanteCreador(Usuario autorizador, Solicitud sol) {
		return sol.getUsuarioByIdUsuario().getIdUsuario() == autorizador.getIdUsuario();
	}
		
}