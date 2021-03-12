package com.lowes.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibm.db2.jcc.am.id;
import com.lowes.dao.BusquedaSolicitudDAO;
import com.lowes.dto.BusquedaSolicitudDTO;
import com.lowes.dto.ProveedorAsesorDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.Factura;
import com.lowes.entity.Locacion;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;
import com.lowes.service.ProveedorService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;


@Repository
public class BusquedaSolicitudDAOImpl implements BusquedaSolicitudDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Override
	public List<Solicitud> getSolicitudesBusqueda(BusquedaSolicitudDTO filtros, Usuario usuario, Integer puestoAP, Integer puestoConfirmacionAP) {

		filtros.setImporteMenor(Utilerias.convertStringToBigDecimal(filtros.getStrImporteMenor()));
		filtros.setImporteMayor(Utilerias.convertStringToBigDecimal(filtros.getStrImporteMayor()));
		
		boolean filtroProveedorByAsesor = false;
		Integer idProveedorAsesor = 0;
		
		if(filtros.getSinComprobanteFiscal()){
			filtros.setIdTipoSolicitudFiltro(Etiquetas.DOS);
		}
		
		
		// Agregar criterios con valores recibidos a la consulta.
		if (usuario != null && filtros != null) {
			
			// Crear una sesion Hibernate
			Session session = sessionFactory.getCurrentSession();
			
			// Crear un objeto de consulta
			Criteria cr = session.createCriteria(Solicitud.class);
			
			DetachedCriteria subSolicitud = DetachedCriteria.forClass(Solicitud.class);

			
			// si no es un AP entonces se filtra por solicitante o creador.
			if(usuario.getPuesto().getIdPuesto() != puestoAP && usuario.getPuesto().getIdPuesto() != puestoConfirmacionAP){
				
				Integer test = 0;
				
				/* AUTORIZADORES DE SOLICITUD */
				cr.createAlias("usuarioByIdUsuarioSolicita", "usrSolicita");
				cr.createCriteria("solicitudAutorizacion", JoinType.LEFT_OUTER_JOIN)
						.createAlias("usuarioByIdUsuarioAutoriza", "usrAutoriza", JoinType.LEFT_OUTER_JOIN);

				Criterion usrAutoriza = Restrictions.eq("usrAutoriza.idUsuario", usuario.getIdUsuario());
				
				/* SOLICITUDES: CREADOR, SOLICITANTE, ASESOR  */
				Criterion solicitante = Restrictions.eq("usuarioByIdUsuario.idUsuario",usuario.getIdUsuario());
				Criterion creador = Restrictions.eq("usuarioByIdUsuarioSolicita.idUsuario",usuario.getIdUsuario());
				Criterion asesor = Restrictions.eq("usuarioByIdUsuarioAsesor.idUsuario",usuario.getIdUsuario());
				
				// si es un id de solicitante, creados o asesor.
				Junction conditionGroup = Restrictions.disjunction();
				conditionGroup.add(solicitante).add(creador).add(asesor);
				
				// expresion logica para unir los dos criterios
				LogicalExpression logicalUsuario = Restrictions.or(usrAutoriza, conditionGroup);
				
				//se agrega el criterio unido al criteria de la solicitud.clss
				cr.add(logicalUsuario);
				
			}
			
			if (filtros.getIdCompaniaFiltro() != null && filtros.getIdCompaniaFiltro() > 0) {
				subSolicitud.createCriteria("compania").add(Restrictions.eq("idcompania", filtros.getIdCompaniaFiltro()));
			}
			if (filtros.getIdProveedorFiltro() != null && filtros.getIdProveedorFiltro() > 0) {
				
				ProveedorAsesorDTO proveedorAsesor = new ProveedorAsesorDTO();
				proveedorAsesor = this.getIdAndCheckTipoNumero(filtros.getIdProveedorFiltro());
				
				if(proveedorAsesor.getTipo().equals(Etiquetas.TIPO_PROVEEDOR_STRING)){
					//busqueda por proveedor
					subSolicitud.createCriteria("facturas").createCriteria("proveedor").add(Restrictions.eq("idProveedor", proveedorAsesor.getIdProveedorAsesor()));
				}else{
					
					//busqueda por asesor/creador/beneficiario
					filtroProveedorByAsesor = true;
					idProveedorAsesor = proveedorAsesor.getIdProveedorAsesor();
					
					//subSolicitud.createCriteria("usuarioByIdUsuarioAsesor").add(Restrictions.eq("idUsuario", proveedorAsesor.getIdProveedorAsesor()));
					//subSolicitud.createCriteria("usuarioByIdUsuarioSolicita").add(Restrictions.eq("idUsuario", proveedorAsesor.getIdProveedorAsesor()));
				}
				
			}
			if (filtros.getIdSolicitud() != null && filtros.getIdSolicitud() > 0) {
				subSolicitud.add(Restrictions.eq("idSolicitud", filtros.getIdSolicitud()));
			}
			if (filtros.getIdTipoSolicitudFiltro() != null && filtros.getIdTipoSolicitudFiltro() > 0) {
				subSolicitud.createCriteria("tipoSolicitud").add(Restrictions.eq("idTipoSolicitud", filtros.getIdTipoSolicitudFiltro()));
			}
			if (filtros.getFechaInicial() != null && filtros.getFechaFinal() != null) {
				subSolicitud.add(Restrictions.between("creacionFecha", Utilerias.getFormattedFromDateTime(filtros.getFechaInicial()), Utilerias.getFormattedToDateTime(filtros.getFechaFinal())));
			}			
			if (filtros.getIdEstadoSolicitudFiltro() != null && filtros.getIdEstadoSolicitudFiltro() > 0) {
				subSolicitud.createCriteria("estadoSolicitud")
						.add(Restrictions.eq("idEstadoSolicitud", filtros.getIdEstadoSolicitudFiltro()));
			}
			if (filtros.getStrImporteMenor() != null && filtros.getStrImporteMayor() != null 
					&& filtros.getStrImporteMenor() != "" && filtros.getStrImporteMayor() != "") {
				subSolicitud.add(Restrictions.between("montoTotal", filtros.getImporteMenor(), filtros.getImporteMayor()));
			}
			if (filtros.getIdMonedaFiltro() != null && filtros.getIdMonedaFiltro() > 0) {
				subSolicitud.createCriteria("moneda").add(Restrictions.eq("idMoneda", filtros.getIdMonedaFiltro()));
			}
			if (filtros.getIdLocacionFiltro() != null && filtros.getIdLocacionFiltro() > 0) {
				subSolicitud.createCriteria("locacion").add(Restrictions.eq("idLocacion", filtros.getIdLocacionFiltro()));
			}
			if (filtros.getIdUsuarioSolicitanteFiltro() != null && filtros.getIdUsuarioSolicitanteFiltro() > 0) {
				subSolicitud.createCriteria("usuarioByIdUsuarioSolicita")
						.add(Restrictions.eq("idUsuario", filtros.getIdUsuarioSolicitanteFiltro()));
			}
			if (filtros.getIdUsuarioAutorizadorFiltro() != null && filtros.getIdUsuarioAutorizadorFiltro() > 0) {
				subSolicitud.createCriteria("solicitudAutorizacion").createCriteria("usuarioByIdUsuarioAutoriza")
						.add(Restrictions.eq("idUsuario", filtros.getIdUsuarioAutorizadorFiltro()));
			}
			
			subSolicitud.setProjection(Projections.property("idSolicitud"));
			
			cr.add(Property.forName("idSolicitud").in(subSolicitud));
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			cr.addOrder(Order.asc("creacionFecha"));
			
			@SuppressWarnings("unchecked")
			List<Solicitud> list = (List<Solicitud>) cr.list();
			
			if (filtroProveedorByAsesor == true) {
				List<Solicitud> listFildradaPorProveedorAsesor = new ArrayList<>();
				for (Solicitud solicitud : list) {
					
					Integer idUsuario = solicitud.getUsuarioByIdUsuario() != null ? solicitud.getUsuarioByIdUsuario().getIdUsuario() : 0;
					Integer idUsuarioSolicita = solicitud.getUsuarioByIdUsuarioSolicita() != null ? solicitud.getUsuarioByIdUsuarioSolicita().getIdUsuario() : 0;
					Integer idUsuarioAsesor = solicitud.getUsuarioByIdUsuarioAsesor() != null ? solicitud.getUsuarioByIdUsuarioAsesor().getIdUsuario() : 0;
					
					if ( idUsuario == idProveedorAsesor	|| idUsuarioSolicita == idProveedorAsesor || idUsuarioAsesor == idProveedorAsesor) {//comentarizado por edgar 19 de septiembre
//					if (idUsuarioAsesor == idProveedorAsesor) {
						
						// si hay un creador, solicitante o beneficiaro asociado entonces se filtra.
						listFildradaPorProveedorAsesor.add(solicitud);

					}
				}
             
				return listFildradaPorProveedorAsesor;
				
			} else {
				return list;
			}
		
		} else {
			return null;
		}
	}
	
	@Override
	public List<Proveedor> getProveedores(){
		
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(Factura.class);

		
		cr.setProjection(Projections.distinct(Projections.property("proveedor"))).add(Restrictions.isNotNull("proveedor"));
		
		@SuppressWarnings("unchecked")
		List<Proveedor> list = (List<Proveedor>)cr.list();
		
		//Se ordena la lista de proveeedores por numeroProveedor
		list.sort((proveedor1, proveedor2) -> Integer.valueOf(proveedor1.getNumeroProveedor()).compareTo(Integer.valueOf(proveedor2.getNumeroProveedor())));
		
		//Se ordena la lista de proveeedores en orden alfab�tico
		//list.sort((proveedor1, proveedor2) -> proveedor1.getDescripcion().compareToIgnoreCase(proveedor2.getDescripcion()));
		return list;
	}

	@Override
	public List<Compania> getCompanias() {

		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(Solicitud.class);
		
		cr.setProjection(Projections.distinct(Projections.property("compania"))).add(Restrictions.isNotNull("compania"));
		
		@SuppressWarnings("unchecked")
		List<Compania> list = (List<Compania>)cr.list();
		
		list.sort((compania1, compania2) -> compania1.getDescripcion().compareToIgnoreCase(compania2.getDescripcion()));
		
		return list;
	}

	@Override
	public List<Locacion> getLocaciones() {
		
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(Solicitud.class);
		
		cr.setProjection(Projections.distinct(Projections.property("locacion"))).add(Restrictions.isNotNull("locacion"));
		
		@SuppressWarnings("unchecked")
		List<Locacion> list = (List<Locacion>)cr.list();
		
		// Se ordena la lista de locaciones por n�mero de Locaci�n
		list.sort((locacion1, locacion2) -> Integer.valueOf(locacion1.getNumero()).compareTo(Integer.valueOf(locacion2.getNumero())));
		
		// Se ordena la lista de locaciones por descripci�n de Locaci�n
		//list.sort((locacion1, locacion2) -> locacion1.getDescripcion().compareToIgnoreCase(locacion2.getDescripcion()));
		return list;
	}

	@Override
	public List<Proveedor> getProveedoresTodas() {
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(Proveedor.class);
		
		//cr.setProjection(Projections.distinct(Projections.property("proveedor"))).add(Restrictions.isNotNull("proveedor"));
		
		@SuppressWarnings("unchecked")
		List<Proveedor> list = (List<Proveedor>)cr.list();
		
		//Se ordena la lista de proveeedores por numeroProveedor
		list.sort((proveedor1, proveedor2) -> Integer.valueOf(proveedor1.getNumeroProveedor()).compareTo(Integer.valueOf(proveedor2.getNumeroProveedor())));
		
		//Se ordena la lista de proveeedores en orden alfab�tico
		//list.sort((proveedor1, proveedor2) -> proveedor1.getDescripcion().compareToIgnoreCase(proveedor2.getDescripcion()));
		return list;
	}
	
	
	private ProveedorAsesorDTO getIdAndCheckTipoNumero(Integer numero){
		
		/*
		 * 1.- Tipo Proveedor
		 * 2.- Tipo Asesor
		 * 
		 * */
		
		ProveedorAsesorDTO proveedorAsesor = new ProveedorAsesorDTO();
		
		
		Usuario usuario = usuarioService.getUsuarioByProveedor(numero);
		if(usuario != null && usuario.getNumeroProveedor() != null){
			//si el numero de proveedor esta en usuario entonces es un asesor
			proveedorAsesor.setIdProveedorAsesor(usuario.getIdUsuario());
			proveedorAsesor.setTipo(Etiquetas.TIPO_ASESOR_STRING);
		}else{
			// si solo esta el numero en proveedor entonces es un asesor.
			List<Proveedor> proveedorN = proveedorService.getProveedorByNumero(numero);
			proveedorAsesor.setIdProveedorAsesor(proveedorN.get(0).getIdProveedor());
			proveedorAsesor.setTipo(Etiquetas.TIPO_PROVEEDOR_STRING);
		}
		
		
		return proveedorAsesor;
		
	}
	
	
	
}
