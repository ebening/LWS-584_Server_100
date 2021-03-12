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

import com.lowes.entity.AutorizadorCuentaContable;
import com.lowes.entity.EstadoAutorizacion;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.NivelAutoriza;
import com.lowes.service.CriterioService;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.NivelAutorizaService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.service.AutorizadorCuentaContableService;
import com.lowes.service.EmailService;

@Configuration
@Service
@Transactional
public class CriterioCuentaContableImpl implements CriterioService {

	private static final Logger logger = Logger.getLogger(CriterioSolicitanteImpl.class);

	@Autowired
	private AutorizadorCuentaContableService autorizadorCuentaContableService;

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
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private NivelAutorizaService nivelAutorizaService;
	
	private String ultimoAutorizador;
	private Usuario usuarioAutorizador;
	public Integer sessionCountCuentaContable = 0;
	private List<Usuario> autorizadores = new ArrayList<>();
	private List<Usuario> usuarioAutorizadorList = new ArrayList<>();

	

	@Override
	public boolean crearSolicitud(Solicitud solicitud, Usuario usuario) {
		
		boolean autorizacionCreada = false;
		autorizadores = new ArrayList<>();
		boolean creado = false;
		Integer creadosAutomaticos = 0;
		
		// Obtener �ltimo autorizador
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
		
		// Se eval�a si existe cuenta contable para todos los detalles de la factura
		List<FacturaDesglose> facturaDesgloses = solicitud.getFacturas().get(0).getFacturaDesgloses();
		List<Usuario> usuariosAutorizadores = new ArrayList<Usuario>();
		
		// se obtienen los autorizadores 
		usuariosAutorizadores = getAutorizadoresCuentaContable(solicitud);
		List<SolicitudAutorizacion> autorizadoresEnviados = solicitudAutorizacionService.getAllAutorizadoresByCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE);
		List<SolicitudAutorizacion> autorizadoresEnviadosAux = new ArrayList<>(); 
		for(SolicitudAutorizacion aut : autorizadoresEnviados){
			if(aut.getRechazado() == null || aut.getRechazado() != 1){
				autorizadoresEnviadosAux.add(aut);
			}
		}
		
		// Obtener el �ltimo autorizador de la solicitud
		SolicitudAutorizacion ultimoAutorizadorDeLaSolicitud = solicitudAutorizacionService
				.getLastSolicitudAutorizacion(solicitud.getIdSolicitud());
		
		
		if (usuariosAutorizadores.size() > 0 && autorizadoresEnviadosAux.size() != usuariosAutorizadores.size()) {
			
			//se obtiene el siguiente autorizador.
			usuarioAutorizador = getSiguienteAutorizador(solicitud, usuariosAutorizadores, ultimoAutorizadorDeLaSolicitud);
			this.setUsuarioAutorizador(usuarioAutorizador);
			
			Integer countAutorizadores = solicitudAutorizacionService.getCountAutorizadoresCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE).intValue();
				
			
			   if (countAutorizadores.equals(0)) {
				//Marcar paso anterior como "no actual" solo la primera vez.
				List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());
				
				for(SolicitudAutorizacion sa : solicitudAutorizacionAnterior){
					if(sa.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO){
						sa.setUltimoMovimiento(Etiquetas.CERO_S);
						solicitudAutorizacionService.updateSolicitudAutorizacion(sa);
					}
				 }
			   }
				
				logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: "+ usuarioAutorizador.getIdUsuario());
				
				
				
				// autorizadar automaticos (repetidos y propietarios) solo por primera vez
				if (countAutorizadores.equals(0)) {
					this.setUsuarioAutorizadorList(usuariosAutorizadores);
					for(Usuario usr : usuariosAutorizadores){
						/* SOLO LO HACE LA PRIMERA VEZ QUE SE EJECUTA ESTE METODO Y VALIDA TODOS LOS AUTORIZADORES DEL SET */
						//|| autorizadorEsSolicitanteCreador(usuarioAutorizador, solicitud)
						if(this.yaAutorizo(solicitud, usr) || autorizadorEsSolicitanteCreador(usr, solicitud)){
		                    /* SI YA AUTORIZO PREVIAMENTE ESTE USUARIO, YA NO SE ENVIA A VALIDAR AL USUARIO: INTERNAMENTE SE AUTORIZA */
							/* SI EL AUTORIZADOR ES EL USUARIO QUE REGISTRO LA SOLICITUD TAMBIEN SE OMITE (SE CONFIRMA AUTOMATICAMENTE)*/
							this.crearSolicitudConfirmada(solicitud, usr, usuario);
							creadosAutomaticos++;
						}
					}
				}
				
			    //si el siguiente autorizador es un autorizador repetido se busca el siquiente.
				if (this.yaAutorizo(solicitud, usuarioAutorizador)) {
					// si los autorizadores se enviaron todos automaticamente se salta a validar paso completo.
					if(creadosAutomaticos != usuariosAutorizadores.size()){
						SolicitudAutorizacion solAut = new SolicitudAutorizacion();
						
						solAut.setTipoCriterio(new TipoCriterio(Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE));
						solAut.setEstadoAutorizacion(new EstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO));
						solAut.setUsuarioByIdUsuarioAutoriza(usuarioAutorizador);
						
						Usuario usuarioAut = getSiguienteAutorizador(solicitud, usuariosAutorizadores, solAut);
						usuarioAutorizador = usuarioAut;
						this.setUsuarioAutorizador(usuarioAutorizador);
						
					}else{
						usuarioAutorizador = null;
					}
				}
				
				
				if(usuarioAutorizador != null){
					
					/*
					 * SI ESTE USUARIO NO A AUTORIZADO PREVIAMENTE Y NO ES EL CREADOR DE LA SOLICITUD SE ENVIA
					 * AL USUARIO PARA SU AUTORIZACI�N
					 */
					
					SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO,
							estadoAutorizacionService
									.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR),
							estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR),
							solicitud, tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE),
							usuarioAutorizador, usuario, Etiquetas.UNO_S, Etiquetas.UNO_S, new Date(),
							usuario.getIdUsuario());

					solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);

					creado = true;
					
				}else{
					logger.info(
							"El criterio de locacion por usuario no se cumple porque no se encontr� el siguiente usuario a asignar.");
					return false;
				}
				
				
				return creado;
			
		} else {
			
			if (validarPasoCompleto(solicitud, usuario)) {
				return true;
			} else {
				logger.info("El criterio cuenta contable no se cumple.");
				return false;
			}
			
		}
		
	}
	
	
	//private AutorizadorCuentaContable getUltimoPaso
	

	private Usuario getSiguienteAutorizador(Solicitud solicitud,
			List<Usuario> autorizadores, SolicitudAutorizacion ultimoAutorizadorDeLaSolicitud) {

		Usuario usuarioNivel = null;
		boolean encontrado = false;
		
		SolicitudAutorizacion ultimaAut = solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud());
	
		// si no hay autorizador registrado o el registro est� en estado rechazado se inicia desde el primer nivel
		if (ultimoAutorizadorDeLaSolicitud != null && ultimoAutorizadorDeLaSolicitud.getEstadoAutorizacion().getIdEstadoAutorizacion() != Etiquetas.ESTADO_AUTORIZACION_RECHAZADO 
			&& ultimoAutorizadorDeLaSolicitud.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE) {
			// se recorren los autorizadores configurados
			for (Usuario autorizador : autorizadores) {
				logger.info("AUTORIZADOR: " + autorizador.getIdUsuario());
				// si est� marcado el flag de encontrado entonces toma el usuario actual del ciclo
				if (encontrado == false) {
					if (autorizador.getIdUsuario() == ultimoAutorizadorDeLaSolicitud.getUsuarioByIdUsuarioAutoriza()
							.getIdUsuario()) {
						// si el �ltimo autorizador rechaz� entonces la
						// solicitud inicia el ciclo de autorizacion
						// desde el primer nivel
						if (ultimoAutorizadorDeLaSolicitud.getEstadoAutorizacion()
								.getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_RECHAZADO) {
							usuarioNivel = autorizador;
							break;
						} else {
							// el siguiente usuario seral el proximo
							// autorizador.
							encontrado = true;
							continue;
						}
					}
				} else {
					if(yaAutorizoCriterioCuentaContable(solicitud, autorizador)){
						logger.info("AUTORIZADOR: " + autorizador.getIdUsuario() +" "+autorizador.getNombre());
						// si ya autorizo niveles pero aun se encuentran mas repetidos en el set actual se envian
						// esto con el proposito de equilibrar el paso completo (totalAutorizadores == YaAutorizaron)
						Integer actualmenteAutorizados = actualmenteAutorizados(autorizador, solicitud);
						Integer autorizadorEnSet = 0;
						for(Usuario usrAutorizador : autorizadores){
							if(usrAutorizador.getIdUsuario() == autorizador.getIdUsuario()){
								autorizadorEnSet++;
							}
						}
						if(actualmenteAutorizados != autorizadorEnSet){
							/*autorizar los autorizadores que faltan automaticamente.*/
							Integer faltan = autorizadorEnSet - actualmenteAutorizados;
							for(Integer x = 0 ; x < faltan; x++){
								crearSolicitudConfirmada(solicitud, autorizador, solicitud.getUsuarioByIdUsuarioSolicita());
							}
						}
					 	continue;
					}else{
						usuarioNivel = autorizador;
						break;
					}
					
				}
			}
		} else {
			// si no hay registros en la tabla el usuario con nivel 1 
			// o el ultimo estado es rechazada entonces se toma el autorizador
			// de primer nivel
			usuarioNivel = autorizadores.get(0);
		}

		return usuarioNivel;
	}
	
	
	
	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {
//		El paso est� incompleto mientras el n�mero de autorizadores sea diferente del
//		n�mero de usuarios que ya autorizaron en la tabla de SolicitudAutorizaci�n
		
		//Toma los desgloses de la factura en este caso solo habr� un desglose
		List<FacturaDesglose> facturaDesgloses = solicitud.getFacturas().get(Etiquetas.CERO).getFacturaDesgloses();
		Integer totalAutorizadores = Etiquetas.CERO;
		Integer idCuentaContable = Etiquetas.CERO;
		
		//total de autorizadores de la cuenta contable.
		totalAutorizadores =  getAutorizadoresCuentaContable(solicitud).size();
		
		
		//lista auxiliar para ir guardando los autorizadores actuales
		List<SolicitudAutorizacion> autorizaron = new ArrayList<>();
		// trae todo el set de registros de autorizacdores de la tabla solicitud autorizacion
//		List<SolicitudAutorizacion> autorizaronTodos = solicitudAutorizacionService.getAllAutorizadoresByCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE);
		List<SolicitudAutorizacion> autorizaronTodos = solicitudAutorizacionService.getAllSolicitudAutorizacionBySolicitud(solicitud.getIdSolicitud());
		
		//recorre toda la lista de autorizadores en solicitud autorizacion
		if (autorizaronTodos != null && autorizaronTodos.size() > Etiquetas.CERO) {
			for (SolicitudAutorizacion autorizo : autorizaronTodos) {
				//si encuentra un rechazado limpia la lista
				if (autorizo.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_RECHAZADO) {
					autorizaron.clear();
				} else {
					//de lo contrario lo agrega
					if(autorizo.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE && autorizo.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO)
						//de lo contrario lo agrega
						autorizaron.add(autorizo);
				}
			}
			//al final se obtendra una lista correcta de los autorizadores que ya validaron despues de un rechazo.
		}
		
		//return totalAutorizadores == solicitudAutorizacionService.getCountAutorizadoresCriterioVendorRisk(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE).intValue();
		return totalAutorizadores == autorizaron.size();

	}


	@Override
	public void enviarCorreo(Solicitud solicitud) {
		logger.info("enviar correo CriterioCuentaContable");
		// Notificaci�n email
		
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA){
			this.setUltimoAutorizador(null);
		}

		// Verificar en base de datos si el env�o de notificaciones est� activo
		Integer envioCorreo = Integer
				.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());

		if (envioCorreo == Etiquetas.UNO && this.getUsuarioAutorizador() != null) {
			try {
					
					logger.info("Enviando correo a: " + this.getUsuarioAutorizador().getNumeroNombreCompletoUsuario()
							+ " - Solicitud: " + solicitud.getIdSolicitud());
					emailService.enviarCorreo(Etiquetas.CRITERIO_CUATRO, this.getUltimoAutorizador(),this.getUsuarioAutorizador().getCorreoElectronico(), solicitud);
					
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
		solicitudAutorizacion.setTipoCriterio(new TipoCriterio(Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE));
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
			sessionCountCuentaContable = sessionCountCuentaContable + 1;
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
	
	
	public List<Usuario> getAutorizadoresCuentaContable(Solicitud solicitud){
		
		//Toma los desgloses de la factura en este caso solo habr� un desglose
		List<FacturaDesglose> facturaDesgloses = solicitud.getFacturas().get(Etiquetas.CERO).getFacturaDesgloses();
	    Integer totalAutorizadores = Etiquetas.CERO;
	    Integer idCuentaContable = null;
		Integer idCuentaContableAux = 0;
		idCuentaContableAux = idCuentaContable;
		boolean inicio= true;
		List<Usuario> autorizadoresCC = new ArrayList<>();
		
		//recorre los desgloses
		if(facturaDesgloses != null && facturaDesgloses.size() > Etiquetas.CERO){
			
			for (FacturaDesglose facturaDesglose : facturaDesgloses) {
				
				idCuentaContable = facturaDesglose.getCuentaContable().getIdCuentaContable();
				
				if(inicio || (idCuentaContable != null && idCuentaContableAux != null && !idCuentaContable.equals(idCuentaContableAux))){
					// Obtener el total de autorizadores configurados para
					// la cuenta contable de cada desglose
					List<AutorizadorCuentaContable> autorizadorCuentaContable = autorizadorCuentaContableService
							.getAutorizadorCuentaContableByCuentaContable(
									facturaDesglose.getCuentaContable().getIdCuentaContable());
					
					List<AutorizadorCuentaContable> autorizadorCuentaContableSolicitud = new ArrayList<>();
					
					
                    //obtener los niveles de la solicitud.
					List<NivelAutoriza> nivelesAutoriza = nivelAutorizaService.getAllNivelAutoriza();
					List<NivelAutoriza> nivelesSolicitud = new ArrayList<>();

				    for(NivelAutoriza nivel : nivelesAutoriza){
				    	
				    	if(solicitud.getMoneda().getIdMoneda() == Etiquetas.ID_PESOS){
				    		
				    		/*Siempre va a tener por lo menos el primer nivel y cada brinco si el monto es menor/igual al limite */
							nivelesSolicitud.add(nivel);
							// si el monto total es menor o igual al monto limite en pesos se termina la busqueda: ya no alcanza mas niveles.
							// Martes 13 de Septiembre 2016 Edgar rivera 
//							Integer resultcompare = solicitud.getMontoTotal().compareTo(nivel.getPesosLimite());
//							if(resultcompare <= Etiquetas.CERO){
//								break;
//							}else{
//								continue;
//							}
				    		
				    		
				    	}else if(solicitud.getMoneda().getIdMoneda() == Etiquetas.ID_DOLARES){
				    		
				    		/*Siempre va a tener por lo menos el primer nivel y cada brinco si el monto es menor/igual al limite */
							nivelesSolicitud.add(nivel);
							// si el monto total es menor o igual al monto limite en pesos se termina la busqueda: ya no alcanza mas niveles.
							Integer resultcompare = solicitud.getMontoTotal().compareTo(nivel.getDolaresLimite());
							if(resultcompare <= Etiquetas.CERO){
								break;
							}else{
								continue;
							}
				    		
				    	}
				    	
				    }
				    
				    //filtrar autorizadores por niveles de solicitud
				    for(AutorizadorCuentaContable aut : autorizadorCuentaContable){
				    	if(nivelesSolicitud.contains(aut.getNivelAutoriza())){
				    		autorizadorCuentaContableSolicitud.add(aut);
				    	}
				    }

					// Actualiza el n�mero total de autorizadores de
					// cuenta contable por Solicitud
					totalAutorizadores = totalAutorizadores + autorizadorCuentaContableSolicitud.size();
					
					for(AutorizadorCuentaContable autorizadorCC : autorizadorCuentaContableSolicitud){
						autorizadoresCC.add(autorizadorCC.getUsuario());
					}
					
					inicio = false;
					idCuentaContableAux = idCuentaContable;
					
				}
				
				
			}					
		}
		
		
		return autorizadoresCC;
	}
	
	
	private boolean yaAutorizoCriterioCuentaContable(Solicitud solicitud, Usuario autorizador){
		Integer autorizacionesPorUsuarioCriterio = solicitudAutorizacionService.getCountAllByUsuarioAutorizoCriterio(solicitud.getIdSolicitud(), autorizador.getIdUsuario(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE);
		
		if(autorizacionesPorUsuarioCriterio > 0){
			return true;
		}else{
			return false;
		}
	}
	
	 private Integer actualmenteAutorizados(Usuario usuario, Solicitud solicitud){
		  return solicitudAutorizacionService.getCountAllByUsuarioAutorizoAutomatico(solicitud.getIdSolicitud(), usuario.getIdUsuario(), Etiquetas.TIPO_CRITERIO_CUENTA_CONTABLE);
	  }
	
	@Override
	public boolean autorizadorEsSolicitanteCreador(Usuario autorizador, Solicitud sol) {
		return sol.getUsuarioByIdUsuario().getIdUsuario() == autorizador.getIdUsuario();
	}


	public List<Usuario> getAutorizadores() {
		return autorizadores;
	}


	public void setAutorizadores(List<Usuario> autorizadores) {
		this.autorizadores = autorizadores;
	}


	public List<Usuario> getUsuarioAutorizadorList() {
		return usuarioAutorizadorList;
	}


	public void setUsuarioAutorizadorList(List<Usuario> usuarioAutorizadorList) {
		this.usuarioAutorizadorList = usuarioAutorizadorList;
	}
	
	
	
	
	

}