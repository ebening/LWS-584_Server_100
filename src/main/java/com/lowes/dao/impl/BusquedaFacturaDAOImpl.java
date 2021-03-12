package com.lowes.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.BusquedaFacturaDAO;
import com.lowes.dto.BusquedaFacturaDTO;
import com.lowes.dto.ProveedorAsesorDTO;
import com.lowes.entity.Compania;
import com.lowes.entity.Factura;
import com.lowes.entity.Locacion;
import com.lowes.entity.Proveedor;
import com.lowes.entity.Solicitud;
import com.lowes.entity.Usuario;
import com.lowes.service.ProveedorService;
import com.lowes.service.SolicitudService;
import com.lowes.service.UsuarioService;
import com.lowes.util.Etiquetas;
import com.lowes.util.Utilerias;

@Repository
public class BusquedaFacturaDAOImpl implements BusquedaFacturaDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private SolicitudService solicitudService;

	@Override
	public List<Factura> getFacturasBusqueda(BusquedaFacturaDTO filtros, Usuario usuario, Integer puestoAP, Integer puestoConfirmacionAP) {

		filtros.setImporteInicialFiltro(Utilerias.convertStringToBigDecimal(filtros.getStrImporteInicialFiltro()));
		filtros.setImporteFinalFiltro(Utilerias.convertStringToBigDecimal(filtros.getStrImporteFinalFiltro()));
		
		boolean filtroProveedorByAsesor = false;
		Integer idProveedorAsesor = 0;
		
		if (usuario != null && filtros != null) {

			// Crear una sesion Hibernate
			Session session = sessionFactory.getCurrentSession();

			// Crear un objeto de consulta
			Criteria cr = session.createCriteria(Factura.class);

			if (usuario.getPuesto().getIdPuesto() != puestoAP && usuario.getPuesto().getIdPuesto() != puestoConfirmacionAP) {
				if (usuario.getEsSolicitante() == Etiquetas.UNO_S && usuario.getEsAutorizador() == Etiquetas.UNO_S) {

					cr.createCriteria("solicitud").createAlias("usuarioByIdUsuarioSolicita", "usrSolicita")
							.createCriteria("solicitudAutorizacion", JoinType.LEFT_OUTER_JOIN)
							.createAlias("usuarioByIdUsuarioAutoriza", "usrAutoriza", JoinType.LEFT_OUTER_JOIN);

					Criterion usrSolicita = Restrictions.eq("usrSolicita.idUsuario", usuario.getIdUsuario());
					Criterion usrAutoriza = Restrictions.eq("usrAutoriza.idUsuario", usuario.getIdUsuario());

					LogicalExpression logicalUsuario = Restrictions.or(usrSolicita, usrAutoriza);

					cr.add(logicalUsuario);

				} else if (usuario.getEsSolicitante() == Etiquetas.UNO_S) {
					cr.createCriteria("solicitud.usuarioByIdUsuarioSolicita")
							.add(Restrictions.eq("idUsuario", usuario.getIdUsuario()));
				} else if (usuario.getEsAutorizador() == Etiquetas.UNO_S) {
					cr.createCriteria("solicitud.solicitudAutorizacion").createCriteria("usuarioByIdUsuarioAutoriza")
							.add(Restrictions.eq("idUsuario", usuario.getIdUsuario()));
				}
			} else {
				if (filtros.getIdUsuarioSolicitanteFiltro() != null && filtros.getIdUsuarioSolicitanteFiltro() > 0) {
					cr.createCriteria("solicitud.usuarioByIdUsuarioSolicita")
							.add(Restrictions.eq("idUsuario", filtros.getIdUsuarioSolicitanteFiltro()));
				}

				if (filtros.getIdUsuarioAutorizadorFiltro() != null && filtros.getIdUsuarioAutorizadorFiltro() > 0) {
//					cr.createCriteria("solicitud.solicitudAutorizacion.usuarioByIdUsuarioAutoriza")
//							.add(Restrictions.eq("idUsuario", filtros.getIdUsuarioAutorizadorFiltro()));
					cr.createCriteria("solicitud.solicitudAutorizacion").createCriteria("usuarioByIdUsuarioAutoriza")
					.add(Restrictions.eq("idUsuario", filtros.getIdUsuarioAutorizadorFiltro()));
				}
			}
			
			DetachedCriteria subFactura = DetachedCriteria.forClass(Factura.class);
			DetachedCriteria solicitud = DetachedCriteria.forClass(Solicitud.class);

			// Agregar criterios con valores recibidos a la consulta.
			if (filtros.getIdCompaniaFiltro() != null && filtros.getIdCompaniaFiltro() > 0) {
				subFactura.createCriteria("companiaByIdCompania")
						.add(Restrictions.eq("idcompania", filtros.getIdCompaniaFiltro()));
			}

			if (filtros.getIdProveedorFiltro() != null && filtros.getIdProveedorFiltro() > 0) {
				
				
				ProveedorAsesorDTO proveedorAsesor = new ProveedorAsesorDTO();
				proveedorAsesor = this.getIdAndCheckTipoNumero(filtros.getIdProveedorFiltro());
				
				if(proveedorAsesor.getTipo().equals(Etiquetas.TIPO_PROVEEDOR_STRING)){
					
					//busqueda por proveedor
					subFactura.createCriteria("proveedor").add(Restrictions.eq("idProveedor", proveedorAsesor.getIdProveedor()));
					
				}else{
				    
					//busqueda por asesor
					filtroProveedorByAsesor = true;
					idProveedorAsesor = proveedorAsesor.getIdAsesor();
					
					Criterion creador = Restrictions.eq("usuarioByIdUsuario.idUsuario",idProveedorAsesor);
					Criterion asesor = Restrictions.eq("usuarioByIdUsuarioAsesor.idUsuario",idProveedorAsesor);
					Criterion solicita = Restrictions.eq("usuarioByIdUsuarioSolicita.idUsuario",idProveedorAsesor);
					Junction conditionGroup = Restrictions.disjunction();
					conditionGroup.add(asesor).add(solicita);
					LogicalExpression logicalUsuario = Restrictions.or(creador,conditionGroup);
					solicitud.add(logicalUsuario);
					
					//solicitud.createCriteria("usuarioByIdUsuarioAsesor").add(Restrictions.eq("idUsuario", idProveedorAsesor));
					
				}
				
			}

			if (filtros.getFacturaFiltro() != null && !filtros.getFacturaFiltro().equals("")) {
				//subFactura.add(Restrictions.eq("factura", filtros.getFacturaFiltro()));
				subFactura.add(Restrictions.like("factura", filtros.getFacturaFiltro(), MatchMode.ANYWHERE));
				
			}

			if (filtros.getIdTipoSolicitudFiltro() != null && filtros.getIdTipoSolicitudFiltro() > 0) {
				solicitud.createCriteria("tipoSolicitud")
						.add(Restrictions.eq("idTipoSolicitud", filtros.getIdTipoSolicitudFiltro()));
			}

			if (filtros.getIdEstadoSolicitudFiltro() != null && filtros.getIdEstadoSolicitudFiltro() > 0) {
				solicitud.createCriteria("estadoSolicitud")
						.add(Restrictions.eq("idEstadoSolicitud", filtros.getIdEstadoSolicitudFiltro()));
			}

			if (filtros.getIdLocacionFiltro() != null && filtros.getIdLocacionFiltro() > 0) {
				solicitud.createCriteria("locacion")
						.add(Restrictions.eq("idLocacion", filtros.getIdLocacionFiltro()));
			}

			if (filtros.getFechaPagoFacturaInicial() != null && filtros.getFechaPagoFacturaFinal() != null) {
				solicitud.add(Restrictions.between("fechaPago", Utilerias.getFormattedFromDateTime(filtros.getFechaPagoFacturaInicial()),
						Utilerias.getFormattedToDateTime(filtros.getFechaPagoFacturaFinal())));
			}
			
			if (filtros.getFechaFacturaInicial() != null && filtros.getFechaFacturaFinal() != null) {
				subFactura.add(Restrictions.between("fechaFactura", Utilerias.getFormattedFromDateTime(filtros.getFechaFacturaInicial()),
						Utilerias.getFormattedToDateTime(filtros.getFechaFacturaFinal())));
			}

			if (filtros.getStrImporteInicialFiltro() != null && filtros.getStrImporteFinalFiltro() != null
					&& filtros.getStrImporteInicialFiltro() != "" && filtros.getStrImporteFinalFiltro() != "") {
				subFactura.add(Restrictions.between("total", filtros.getImporteInicialFiltro(),
						filtros.getImporteFinalFiltro()));
			}

			if (filtros.getIdMonedaFiltro() != null && filtros.getIdMonedaFiltro() > 0) {
				subFactura.createCriteria("moneda").add(Restrictions.eq("idMoneda", filtros.getIdMonedaFiltro()));
			}
			
			subFactura.setProjection(Projections.property("idFactura"));
			solicitud.setProjection(Projections.property("idSolicitud"));
			
			cr.add(Property.forName("idFactura").in(subFactura)).add(Property.forName("solicitud.idSolicitud").in(solicitud));
			cr.addOrder(Order.asc("fechaFactura"));
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			@SuppressWarnings("unchecked")
			List<Factura> list = (List<Factura>) cr.list();
			
			if (filtroProveedorByAsesor == true) {
				List<Factura> listFildradaPorProveedorAsesor = new ArrayList<>();
				for (Factura factura : list) {
//					Integer idUsuario = factura.getUsuarioByIdUsuario() != null ? factura.getUsuarioByIdUsuario().getIdUsuario() : 0;
//					Integer idUsuarioSolicita = factura.getUsuarioByIdUsuarioSolicita() != null ? factura.getUsuarioByIdUsuarioSolicita().getIdUsuario() : 0;
					Integer idUsuarioAsesor = factura.getSolicitud().getUsuarioByIdUsuarioAsesor() != null ? factura.getSolicitud().getUsuarioByIdUsuarioAsesor().getIdUsuario() : 0;
					
//					if ( idUsuario == idProveedorAsesor	|| idUsuarioSolicita == idProveedorAsesor || idUsuarioAsesor == idProveedorAsesor) {//comentarizado por edgar 19 de septiembre
					if (idUsuarioAsesor == idProveedorAsesor) {
						
						// si hay un creador, solicitante o beneficiaro asociado entonces se filtra.
						listFildradaPorProveedorAsesor.add(factura);

					}
				}
             
				return listFildradaPorProveedorAsesor;
				
			} else {
				return list;
			}
//			for(Factura factura : list){
//				//Solicitud solicitud = solicitudService.getSolicitud(factura.getSolicitud().getidsol)
//				
//				//System.out.println("Creador:" + factura.getSolicitud().getUsuarioByIdUsuario() != null ? factura.getSolicitud().getUsuarioByIdUsuario().getNombre() : "N/A");
//				//System.out.println("Solicitante:" + factura.getSolicitud().getUsuarioByIdUsuarioSolicita() != null ?  factura.getSolicitud().getUsuarioByIdUsuarioSolicita().getNombre() : "N/A");
//				//System.out.println("Asesor:" + factura.getSolicitud().getUsuarioByIdUsuarioAsesor() != null ? factura.getSolicitud().getUsuarioByIdUsuarioAsesor().getNombre() : "N/A");
//				System.out.println("-------------");
//				
//			}

//			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<Proveedor> getProveedores() {
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(Factura.class);

		cr.setProjection(Projections.distinct(Projections.property("proveedor")))
				.add(Restrictions.isNotNull("proveedor"));

		@SuppressWarnings("unchecked")
		List<Proveedor> list = (List<Proveedor>) cr.list();

		// Se ordena la lista de proveeedores por numeroProveedor
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

		cr.setProjection(Projections.distinct(Projections.property("compania")))
				.add(Restrictions.isNotNull("compania"));

		@SuppressWarnings("unchecked")
		List<Compania> list = (List<Compania>) cr.list();

		list.sort((compania1, compania2) -> compania1.getDescripcion().compareToIgnoreCase(compania2.getDescripcion()));

		return list;
	}

	@Override
	public List<Locacion> getLocaciones() {
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(Solicitud.class);

		cr.setProjection(Projections.distinct(Projections.property("locacion")))
				.add(Restrictions.isNotNull("locacion"));

		@SuppressWarnings("unchecked")
		List<Locacion> list = (List<Locacion>) cr.list();

		// Se ordena la lista de locaciones por n�mero de Locaci�n
		list.sort((locacion1, locacion2) -> Integer.valueOf(locacion1.getNumero()).compareTo(Integer.valueOf(locacion2.getNumero())));
		// Se ordena la lista de locaciones por descripci�n de Locaci�n
		//list.sort((locacion1, locacion2) -> locacion1.getDescripcion().compareToIgnoreCase(locacion2.getDescripcion()));

		return list;
	}

	@Override
	public List<Proveedor> getProveedoresTodos() {
		// Crear una sesion Hibernate
				Session session = sessionFactory.getCurrentSession();

				// Crear un objeto de consulta
				Criteria cr = session.createCriteria(Proveedor.class);

				//cr.setProjection(Projections.distinct(Projections.property("numeroProveedor"))).add(Restrictions.isNotNull("proveedor"));

				@SuppressWarnings("unchecked")
				List<Proveedor> list = (List<Proveedor>) cr.list();

				// Se ordena la lista de proveeedores por numeroProveedor
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
		List<Proveedor> proveedor = proveedorService.getProveedorByNumero(numero);
		if(usuario != null && usuario.getNumeroProveedor() != null){
			
			//si el numero de proveedor esta en usuario entonces es un asesor
			proveedorAsesor.setIdProveedorAsesor(usuario.getIdUsuario());
			proveedorAsesor.setTipo(Etiquetas.TIPO_ASESOR_STRING);
			proveedorAsesor.setIdAsesor(usuario.getIdUsuario());
	
			
			if(proveedor.isEmpty() == false){
				proveedorAsesor.setIdProveedor(proveedor.get(0).getIdProveedor());
			}
			
		}else{
			
			// si solo esta el numero en proveedor entonces es un asesor.
			List<Proveedor> proveedorN = proveedorService.getProveedorByNumero(numero);
			proveedorAsesor.setIdProveedor(proveedorN.get(0).getIdProveedor());
			proveedorAsesor.setTipo(Etiquetas.TIPO_PROVEEDOR_STRING);
			
		}
		
		
		return proveedorAsesor;
		
	}
	
	

}