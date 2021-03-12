package com.lowes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.entity.AutorizadorLocacion;
import com.lowes.entity.AutorizadorProveedorRiesgo;
import com.lowes.entity.EstadoAutorizacion;
import com.lowes.entity.EstadoSolicitud;
import com.lowes.entity.FacturaDesglose;
import com.lowes.entity.NivelAutoriza;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.entity.TipoCriterio;
import com.lowes.entity.Usuario;
import com.lowes.service.AutorizadorLocacionService;
import com.lowes.service.CriterioService;
import com.lowes.service.EmailService;
import com.lowes.service.EstadoAutorizacionService;
import com.lowes.service.EstadoSolicitudService;
import com.lowes.service.NivelAutorizaService;
import com.lowes.service.ParametroService;
import com.lowes.service.SolicitudAutorizacionService;
import com.lowes.service.TipoCriterioService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;

@Configuration
@Service
@Transactional
public class CriterioMontoImpl implements CriterioService {

	private static final Logger logger = Logger.getLogger(CriterioSolicitanteImpl.class);

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EstadoSolicitudService estadoSolicitudService;

	@Autowired
	private EstadoAutorizacionService estadoAutorizacionService;

	@Autowired
	private TipoCriterioService tipoCriterioService;

	@Autowired
	private SolicitudAutorizacionService solicitudAutorizacionService;

	@Autowired
	private NivelAutorizaService nivelAutorizaService;

	@Autowired
	private AutorizadorLocacionService autorizadorLocacionService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ParametroService parametroService;
	
	private String ultimoAutorizador;
	private Usuario usuarioAutorizador;
	
	public Integer sessionCount = 0;
	

	@Override
	public boolean crearSolicitud(Solicitud solicitud, Usuario usuario) {

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
		
		List<Usuario> usuariosAutorizan = new ArrayList<>();
		this.setUsuarioAutorizador(null);
		
		
		//Validacion 1 - Si el usuario tiene un usuario jefe, se asigna al usuario jefe. (el jefe esta dentro del objeto de usuario solicitante.)
		if (solicitud.getUsuarioByIdUsuarioSolicita().getUsuario() != null){
			usuariosAutorizan.add(solicitud.getUsuarioByIdUsuarioSolicita().getUsuario());
		}
		
		// Validaci�n 2 - Buscar nivel de autorizaci�n configurado
		List<NivelAutoriza> nivelesAutoriza = nivelAutorizaService.getAllNivelAutoriza();

		usuariosAutorizan.addAll(getUsuariosAutorizan(solicitud, nivelesAutoriza));
		
	
		//Se asigna la solicitud a los usuarios que cumplieron con los criterios anteriores 
		if (usuariosAutorizan.size() > Etiquetas.CERO) {
//			if (false) {
			Integer countAutorizadores = solicitudAutorizacionService.getCountAutorizadoresCriterio(solicitud.getIdSolicitud(), Etiquetas.TIPO_CRITERIO_MONTOS).intValue();
			
			if (countAutorizadores.equals(0)) {
				// Marcar paso anterior como "no actual"
				List<SolicitudAutorizacion> solicitudAutorizacionAnterior = solicitudAutorizacionService
						.getAllSolicitudAutorizacionActivaBySolicitud(solicitud.getIdSolicitud());

				for (SolicitudAutorizacion sa : solicitudAutorizacionAnterior) {
					sa.setUltimoMovimiento(Etiquetas.CERO_S);
					solicitudAutorizacionService.updateSolicitudAutorizacion(sa);
				}
			}
			
//			Usuario usuarioAutorizador = usuariosAutorizan.get(countAutorizadores);
//			this.setUsuarioAutorizador(usuarioAutorizador);
			
			// Obtener el �ltimo autorizador de la solicitud
			SolicitudAutorizacion ultimoAutorizadorDeLaSolicitud = solicitudAutorizacionService
					.getLastSolicitudAutorizacion(solicitud.getIdSolicitud());
            
			//para saber el orden de envio de los autorizadores segun el nivel.
			this.setUsuarioAutorizador(getSiguienteAutorizador(solicitud, usuariosAutorizan,ultimoAutorizadorDeLaSolicitud));

			if (this.getUsuarioAutorizador() != null) {
				
				logger.info("Asignando la solicitud: " + solicitud.getIdSolicitud() + " al usuario: "
						+ this.getUsuarioAutorizador().getIdUsuario());
				
				boolean creado = false;
				Integer creadosAutomaticos = 0;
				
				//primera vez autoriza automaticamente todos los repetidos.
				if(countAutorizadores == 0){
					for(Usuario usr : usuariosAutorizan){
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
					if (this.yaAutorizo(solicitud, this.getUsuarioAutorizador())) {
						// si los autorizadores se enviaron todos automaticamente se salta a validar paso completo.
						if(creadosAutomaticos != usuariosAutorizan.size()){
							SolicitudAutorizacion solAut = new SolicitudAutorizacion();
							
							solAut.setTipoCriterio(new TipoCriterio(Etiquetas.TIPO_CRITERIO_MONTOS));
							solAut.setEstadoAutorizacion(new EstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO));
							solAut.setUsuarioByIdUsuarioAutoriza(this.getUsuarioAutorizador());
							
							Usuario usuarioAut = getSiguienteAutorizador(solicitud, usuariosAutorizan, solAut);
							this.setUsuarioAutorizador(usuarioAut);
							
						}else{
							this.setUsuarioAutorizador(null);
						}
					}
					
					if(this.getUsuarioAutorizador() != null){
						
						/*
						 * SI ESTE USUARIO NO A AUTORIZADO PREVIAMENTE Y NO ES EL CREADOR DE LA SOLICITUD SE ENVIA
						 * AL USUARIO PARA SU AUTORIZACI�N
						 */
						
						SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion(Etiquetas.CERO,
								estadoAutorizacionService
										.getEstadoAutorizacion(Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR),
								estadoSolicitudService.getEstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR),
								solicitud, tipoCriterioService.getTipoCriterio(Etiquetas.TIPO_CRITERIO_MONTOS),
								this.getUsuarioAutorizador(), usuario, Etiquetas.UNO_S, Etiquetas.UNO_S, new Date(),
								usuario.getIdUsuario());

						solicitudAutorizacionService.createSolicitudAutorizacion(solicitudAutorizacion);

						creado = true;
						
					}

				return creado;

			} else {
				
				//si fueron creados automaticamente se envia true como creados.
				if(validarPasoCompleto(solicitud, this.getUsuarioAutorizador())){
				   return true;
				}
				
				logger.info(
						"El criterio de locacion por usuario no se cumple porque no se encontr� el siguiente usuario a asignar.");
				return false;
			}

		} else {
			logger.info(
					"El criterio de locacion por usuario no se cumple porque no existen autorizadores configurados.");
			return false;
		}
	}

	@Override
	public boolean validarPasoCompleto(Solicitud solicitud, Usuario usuario) {

		List<Usuario> usuariosAutorizan = new ArrayList<>();

		// Validacion 1 - Si el usuario tiene un usuario jefe, se asigna al
		// usuario jefe.
		if (solicitud.getUsuarioByIdUsuarioSolicita().getUsuario() != null) {
			usuariosAutorizan.add(solicitud.getUsuarioByIdUsuarioSolicita().getUsuario());
		}

		// Validaci�n 2 - Buscar nivel de autorizaci�n configurado
		List<NivelAutoriza> nivelesAutoriza = nivelAutorizaService.getAllNivelAutoriza();
		
		usuariosAutorizan.addAll(getUsuariosAutorizan(solicitud, nivelesAutoriza));
		
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
					if(autorizo.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_MONTOS && autorizo.getEstadoAutorizacion().getIdEstadoAutorizacion() == Etiquetas.ESTADO_AUTORIZACION_AUTORIZADO
							&& (autorizo.getRechazado() == null || autorizo.getRechazado() != 1))
						//de lo contrario lo agrega
						autorizaron.add(autorizo);
				}
			}
			//al final se obtendra una lista correcta de los autorizadores que ya validaron despues de un rechazo.
		}
		
			return usuariosAutorizan.size() == autorizaron.size();
	}

	@Override
	public void enviarCorreo(Solicitud solicitud) {
		logger.info("enviar correo CriterioMontos");
		
		// Notificaci�n email
		
		if(solicitud.getEstadoSolicitud().getIdEstadoSolicitud() == Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA){
			this.setUltimoAutorizador(null);
		}

		// Verificar en base de datos si el env�o de notificaciones est� activo
		Integer envioCorreo = Integer
				.parseInt(parametroService.getParametroByName("emailEnviarNotificacion").getValor());

		if (envioCorreo == Etiquetas.UNO) {
			try {
				
				String nombre = this.getUsuarioAutorizador() != null
						&& this.getUsuarioAutorizador().getNumeroNombreCompletoUsuario() != null
								? this.getUsuarioAutorizador().getNumeroNombreCompletoUsuario() : "";

				logger.info("Enviando correo a: " +   nombre 
						+ " - Solicitud: " + solicitud.getIdSolicitud());
				emailService.enviarCorreo(Etiquetas.CRITERIO_CINCO, this.getUltimoAutorizador(), 
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
	
	public List<NivelAutoriza> getNivelesAutorizador(Solicitud solicitud, List<NivelAutoriza> nivelesAutoriza){
		List<NivelAutoriza> nivelesSolicitud = new ArrayList<>();
		List<Usuario> usuariosAutorizan = new ArrayList<>();
		//se busca el nivel de autorizaci�n de la solicitud
		for (NivelAutoriza nvlAtrz : nivelesAutoriza){
			if (solicitud.getMoneda().getDescripcion() == Etiquetas.PESOS){
				if(nvlAtrz.getPesosLimite().compareTo(solicitud.getMontoTotal()) <= Etiquetas.CERO){
					nivelesSolicitud.add(nvlAtrz);
					System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
				}else{
					if(nivelAutorizaService.getNivelAutoriza(nvlAtrz.getIdNivelAutoriza() - 1).getPesosLimite().compareTo(solicitud.getMontoTotal()) < Etiquetas.CERO){
						nivelesSolicitud.add(nvlAtrz);
						System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
					}
					break;
				}
			} else if(solicitud.getMoneda().getDescripcion() == Etiquetas.DOLARES){
				if(nvlAtrz.getDolaresLimite().compareTo(solicitud.getMontoTotal()) <= Etiquetas.CERO){
					nivelesSolicitud.add(nvlAtrz);
					System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
				}else{
					if(nivelAutorizaService.getNivelAutoriza(nvlAtrz.getIdNivelAutoriza() - 1).getDolaresLimite().compareTo(solicitud.getMontoTotal()) < Etiquetas.CERO){
						nivelesSolicitud.add(nvlAtrz);
						System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
					}
					break;
				}
			}
// Se comenta el codigo para validar por la descripcion del tipo de moneda y no por ID			
//				if (solicitud.getMoneda().getIdMoneda() == Etiquetas.ID_PESOS){
//					if(nvlAtrz.getPesosLimite().compareTo(solicitud.getMontoTotal()) <= Etiquetas.CERO){
//						nivelesSolicitud.add(nvlAtrz);
//						System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
//					}else{
//						if(nivelAutorizaService.getNivelAutoriza(nvlAtrz.getIdNivelAutoriza() - 1).getPesosLimite().compareTo(solicitud.getMontoTotal()) < Etiquetas.CERO){
//							nivelesSolicitud.add(nvlAtrz);
//							System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
//						}
//						break;
//					}
//				} else if(solicitud.getMoneda().getIdMoneda() == Etiquetas.ID_DOLARES){
//					if(nvlAtrz.getDolaresLimite().compareTo(solicitud.getMontoTotal()) <= Etiquetas.CERO){
//						nivelesSolicitud.add(nvlAtrz);
//						System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
//					}else{
//						if(nivelAutorizaService.getNivelAutoriza(nvlAtrz.getIdNivelAutoriza() - 1).getDolaresLimite().compareTo(solicitud.getMontoTotal()) < Etiquetas.CERO){
//							nivelesSolicitud.add(nvlAtrz);
//							System.out.println("Nivel agregado: " + nvlAtrz.getIdNivelAutoriza());
//						}
//						break;
//					}
//				}
		}
		
		boolean noEsCSC = false;
		// si la locacion es tipo CSC entonces se envia a validar tambien con los autorizadores de esa locacion.
		List<FacturaDesglose> desgloses = solicitud.getFacturas().get(0).getFacturaDesgloses();
		// validar que tenga desgloses de factura, que debe tener si esta en proceso de validacion.
		if(desgloses != null && desgloses.isEmpty() == false){
			for(FacturaDesglose desglose : desgloses){
				// si la locacion del solicitante es tipo csc y la carga a la solicitud es tipo csc entonces se anexa el autorizador de ese csc.
				if(solicitud.getUsuarioByIdUsuarioSolicita().getLocacion().getTipoLocacion().getIdTipoLocacion() == Etiquetas.UNO && desglose.getLocacion().getTipoLocacion().getIdTipoLocacion() == Etiquetas.UNO){
					for(NivelAutoriza nivelAutorizado : nivelesSolicitud){
						System.out.println("Nivel Autoriza: " + nivelAutorizado.getIdNivelAutoriza());
						
						// sacar los autorizadores de csc puesto en el desglose de la solicitud.
						AutorizadorLocacion autorizadorL = autorizadorLocacionService.getAutorizadorLocacionByLocacionNivelAutoriza(desglose.getLocacion().getIdLocacion(),nivelAutorizado.getIdNivelAutoriza());
						System.out.println("Autorizador: " + (autorizadorL != null ? autorizadorL.getUsuario().getCuenta() : "NA"));
						if(autorizadorL != null && autorizadorL.getUsuario() != null){
							usuariosAutorizan.add(autorizadorL.getUsuario());
							System.out.println("Autorizador Agregado: " + autorizadorL.getUsuario().getCuenta());
						}
					}					
				}else{
					noEsCSC = true;
				}
			}
		}
		
		// si por lo menos uno no es csc valida el autorizador de la locacion.
		if(noEsCSC){
			//2.1. Recorrer los niveles de autorizaci�n para la busqueda en la locaci�n del usuario.
			Boolean terminado = false;
			for (NivelAutoriza nvlAtrz : nivelesAutoriza){
				if (!terminado){
					AutorizadorLocacion autorizadorLocacionEvaluar = autorizadorLocacionService.getAutorizadorLocacionByLocacionNivelAutoriza(solicitud.getUsuarioByIdUsuarioSolicita().getLocacion().getIdLocacion(), nvlAtrz.getIdNivelAutoriza());
					if (autorizadorLocacionEvaluar != null){
						usuariosAutorizan.add(autorizadorLocacionEvaluar.getUsuario());
						if (nvlAtrz.getIdNivelAutoriza() >= nivelesSolicitud.get(0).getIdNivelAutoriza()){
							terminado = true;
						}
					}
				}
			}
		}
		
		return nivelesSolicitud;
	}
	
	public List<Usuario> getUsuariosAutorizan(Solicitud solicitud, List<NivelAutoriza> nivelesAutoriza){
		List<NivelAutoriza> nivelesSolicitud = new ArrayList<>();
		List<Usuario> usuariosAutorizan = new ArrayList<>();
		//se busca el nivel de autorizaci�n de la solicitud
		for (NivelAutoriza nvlAtrz : nivelesAutoriza){
				if (solicitud.getMoneda().getIdMoneda() == Etiquetas.ID_PESOS){
					
					/*Siempre va a tener por lo menos el primer nivel y cada brinco si el monto es menor/igual al limite */
					nivelesSolicitud.add(nvlAtrz);
					// si el monto total es menor o igual al monto limite en pesos se termina la busqueda: ya no alcanza mas niveles.
					Integer resultcompare = solicitud.getMontoTotal().compareTo(nvlAtrz.getPesosLimite());
					if(resultcompare <= Etiquetas.CERO){
						break;
					}else{
						continue;
					}
					
				} else if(solicitud.getMoneda().getIdMoneda() == Etiquetas.ID_DOLARES){
					
					/*Siempre va a tener por lo menos el primer nivel y cada brinco si el monto es menor/igual al limite */
					nivelesSolicitud.add(nvlAtrz);
					// si el monto total es menor o igual al monto limite en pesos se termina la busqueda: ya no alcanza mas niveles.
					Integer resultcompare = solicitud.getMontoTotal().compareTo(nvlAtrz.getDolaresLimite());
					if(resultcompare <= Etiquetas.CERO){
						break;
					}else{
						continue;
					}
				}
		}
		boolean noEsCSC = false;
		// si la locacion es tipo CSC entonces se envia a validar tambien con los autorizadores de esa locacion.
		List<FacturaDesglose> desgloses = solicitud.getFacturas().get(0).getFacturaDesgloses();
		// validar que tenga desgloses de factura, que debe tener si esta en proceso de validacion.
		if(desgloses != null && desgloses.isEmpty() == false){
			for(FacturaDesglose desglose : desgloses){
				AutorizadorLocacion autorizadorL = null;
				if(desglose.getLocacion().getTipoLocacion().getIdTipoLocacion() == Etiquetas.UNO){
					for(NivelAutoriza nivelAutorizado : nivelesSolicitud){
						System.out.println("Nivel Autoriza CSC de desglose: " + nivelAutorizado.getIdNivelAutoriza());
						// sacar los autorizadores de csc puesto en el desglose de la solicitud.
						autorizadorL = autorizadorLocacionService.getAutorizadorLocacionByLocacionNivelAutoriza(desglose.getLocacion().getIdLocacion(),nivelAutorizado.getIdNivelAutoriza());
						System.out.println("Autorizador: " + (autorizadorL != null ? autorizadorL.getUsuario().getCuenta() : "NA"));
						if(autorizadorL != null && autorizadorL.getUsuario() != null){
							usuariosAutorizan.add(autorizadorL.getUsuario());
							System.out.println("Autorizador Agregado: " + autorizadorL.getUsuario().getCuenta());
						}else{
							break;
						}
					}
				}else{
					for(NivelAutoriza nivelAutorizado : nivelesSolicitud){
						System.out.println("Nivel Autoriza CSC de solicitud: " + nivelAutorizado.getIdNivelAutoriza());
						// sacar los autorizadores de csc puesto en el desglose de la solicitud.
						autorizadorL = autorizadorLocacionService.getAutorizadorLocacionByLocacionNivelAutoriza(solicitud.getUsuarioByIdUsuarioSolicita().getLocacion().getIdLocacion(),nivelAutorizado.getIdNivelAutoriza());
						System.out.println("Autorizador: " + (autorizadorL != null ? autorizadorL.getUsuario().getCuenta() : "NA"));
						if(autorizadorL != null && autorizadorL.getUsuario() != null){
							usuariosAutorizan.add(autorizadorL.getUsuario());
							System.out.println("Autorizador Agregado: " + autorizadorL.getUsuario().getCuenta());
						}else{
							break;
						}
					}
				}
			}
		}
		return usuariosAutorizan;
	}
	
	@Override
	public boolean crearSolicitudConfirmada(Solicitud solicitud, Usuario usuarioAutoriza, Usuario usuarioSolicita) {

		SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion();
		
		//setters para la autorizacion automatica.
		solicitudAutorizacion.setSolicitud(solicitud);
		solicitudAutorizacion.setEstadoSolicitud(new EstadoSolicitud(Etiquetas.ID_ESTADO_SOLICITUD_POR_AUTORIZAR));
		solicitudAutorizacion.setTipoCriterio(new TipoCriterio(Etiquetas.TIPO_CRITERIO_MONTOS));
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
			
			/*Para registrar en session cuantas veces se a autorizado automaticamente.*/
			sessionCount = sessionCount + 1;
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
	
	  public boolean yaAutorizoCriterioMontos(Solicitud solicitud, Usuario usuario) {
			
			Integer autorizacionesPorUsuarioCriterio = solicitudAutorizacionService.getCountAllByUsuarioAutorizoCriterio(solicitud.getIdSolicitud(), usuario.getIdUsuario(), Etiquetas.TIPO_CRITERIO_MONTOS);
			
			if(autorizacionesPorUsuarioCriterio > 0){
				return true;
			}else{
				return false;
			}
				
		}
	  
	  
	  
	  private Usuario getSiguienteAutorizador(Solicitud solicitud,
				List<Usuario> autorizadores, SolicitudAutorizacion ultimoAutorizadorDeLaSolicitud) {

			Usuario usuarioNivel = null;
			boolean encontrado = false;
			
			SolicitudAutorizacion ultimaAut = solicitudAutorizacionService.getLastSolicitudAutorizacion(solicitud.getIdSolicitud());
		
			// si no hay autorizador registrado o el registro est� en estado rechazado se inicia desde el primer nivel
			if (ultimoAutorizadorDeLaSolicitud != null && ultimoAutorizadorDeLaSolicitud.getEstadoAutorizacion().getIdEstadoAutorizacion() != Etiquetas.ESTADO_AUTORIZACION_RECHAZADO 
				&& ultimoAutorizadorDeLaSolicitud.getTipoCriterio().getIdTipoCriterio() == Etiquetas.TIPO_CRITERIO_MONTOS) {
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
								// el siguiente usuario seral el priximo
								// autorizador.
								encontrado = true;
								continue;
							}
						}
					} else {
						if(yaAutorizoCriterioMontos(solicitud, autorizador)){
							logger.info("AUTORIZADOR: " + autorizador.getIdUsuario() +" "+autorizador.getNombre());
							// si ya autorizo montos pero aun se encuentran mas repetidos en el set actual se envian
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
	  
	  
	  private Integer actualmenteAutorizados(Usuario usuario, Solicitud solicitud){
		  return solicitudAutorizacionService.getCountAllByUsuarioAutorizoAutomatico(solicitud.getIdSolicitud(), usuario.getIdUsuario(), Etiquetas.TIPO_CRITERIO_MONTOS);
	  }
	  
	  private boolean yaCreoAutomaticamente(Usuario usuario, Solicitud solicitud){
		  
			Integer autorizacionesPorUsuarioCriterio = solicitudAutorizacionService.getCountAllByUsuarioAutorizoAutomatico(solicitud.getIdSolicitud(), usuario.getIdUsuario(), Etiquetas.TIPO_CRITERIO_MONTOS);
			
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