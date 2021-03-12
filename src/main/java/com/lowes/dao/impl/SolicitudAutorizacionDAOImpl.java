package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.SolicitudAutorizacionDAO;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.util.HibernateUtil;

import com.lowes.util.Etiquetas;


@Repository
public class SolicitudAutorizacionDAOImpl implements SolicitudAutorizacionDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer createSolicitudAutorizacion(SolicitudAutorizacion solicitudAutorizacion) {
		return  (Integer) hibernateUtil.create(solicitudAutorizacion);
	}

	@Override
	public SolicitudAutorizacion updateSolicitudAutorizacion(SolicitudAutorizacion solicitudAutorizacion) {
		return hibernateUtil.update(solicitudAutorizacion);
	}

	@Override
	public void deleteSolicitudAutorizacion(Integer idSolicitudAutorizacion) {
		SolicitudAutorizacion solicitudAutorizacion = new SolicitudAutorizacion();
		solicitudAutorizacion = hibernateUtil.fetchById(idSolicitudAutorizacion, SolicitudAutorizacion.class);
		hibernateUtil.delete(solicitudAutorizacion);
	}

	@Override
	public List<SolicitudAutorizacion> getAllSolicitudAutorizacion() {
		return hibernateUtil.fetchAll(SolicitudAutorizacion.class);
	}

	@Override
	public SolicitudAutorizacion getSolicitudAutorizacion(Integer idSolicitudAutorizacion) {
		return hibernateUtil.fetchById(idSolicitudAutorizacion, SolicitudAutorizacion.class);
	}

	@Override
	public SolicitudAutorizacion getLastSolicitudAutorizacion(Integer idSolicitud){
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud"
				+ " ORDER BY ID_SOLICITUD_AUTORIZACION DESC";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		
		List<SolicitudAutorizacion> lastSolicitudAutorizaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (lastSolicitudAutorizaciones.size() > 0)
		{
			return lastSolicitudAutorizaciones.get(0);
		} else {
			return null;
		}
	}

	@Override
	public SolicitudAutorizacion getCurrentSolicitudAutorizacion(Integer idSolicitud) {
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud"
				+ " AND ULTIMO_MOVIMIENTO = :ultimoMovimiento";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		parameters.put("ultimoMovimiento", "1");
		
		List<SolicitudAutorizacion> lastSolicitudAutorizaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (lastSolicitudAutorizaciones.size() > 0)
		{
			return lastSolicitudAutorizaciones.get(0);
		} else {
			return null;
		}
	}

	@Override
	public SolicitudAutorizacion getSolicitudAutorizacionBySolicitudUsuario(Integer idSolicitud, Integer idUsuario) {
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud"
				+ " AND ID_USUARIO_AUTORIZA = :idUsuario"
				+ " AND ULTIMO_MOVIMIENTO = :ultimoMovimiento"
				+ " ORDER BY CREACION_FECHA DESC";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("ultimoMovimiento", "1");
		
		List<SolicitudAutorizacion> lastSolicitudAutorizaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (lastSolicitudAutorizaciones.size() > 0)
		{
			return lastSolicitudAutorizaciones.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Integer getCountSolicitudAutorizacionByUsuario(Integer idUsuario, Integer idEstadoSolicitud) {
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_USUARIO_AUTORIZA = :idUsuario"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
				+ " AND ULTIMO_MOVIMIENTO = :ultimoMovimiento";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		parameters.put("ultimoMovimiento", "1");
		
		List<SolicitudAutorizacion> lastSolicitudAutorizaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return lastSolicitudAutorizaciones.size();
	}

	@Override
	public List<SolicitudAutorizacion> getAllSolicitudAutorizacionBySolicitud(Integer idSolicitud) {
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud ORDER BY ID_SOLICITUD_AUTORIZACION ASC";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
		
	}
	
	@Override
	public List<SolicitudAutorizacion> getAllSolicitudAutorizacionActivaBySolicitud(Integer idSolicitud) {
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud"
				+ " AND ULTIMO_MOVIMIENTO = :ultimoMovimiento";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		parameters.put("ultimoMovimiento", "1");
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
		
	}

	@Override
	public Integer getCountAutorizadoresCriterio(Integer idSolicitud, Integer idTipoCriterio) {
		
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(SolicitudAutorizacion.class);
		
		cr.add(Restrictions.eq("ultimoMovimiento", Etiquetas.UNO_S)).createCriteria("solicitud").add(Restrictions.eq("idSolicitud", idSolicitud))
											.createCriteria("estadoSolicitud").add(Restrictions.ne("idEstadoSolicitud", Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA))
											.add(Restrictions.ne("idEstadoSolicitud", Etiquetas.ID_ESTADO_SOLICITUD_NUEVA));
		cr.createCriteria("tipoCriterio").add(Restrictions.eq("idTipoCriterio", idTipoCriterio));
		
		return ((Long)cr.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudAutorizacion> getAllAutorizadoresByCriterio(Integer idSolicitud, Integer idTipoCriterio) {
		
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_SOLICITUD = :idSolicitud"
		        + " AND ID_TIPO_CRITERIO = :idTipoCriterio"
                + " ORDER BY ID_SOLICITUD_AUTORIZACION ASC";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idSolicitud", idSolicitud.toString());
		parameters.put("idTipoCriterio", idTipoCriterio.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
	@Override
	public Integer getCountAutorizadoresCriterioVendorRisk(Integer idSolicitud, Integer idTipoCriterio) {
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(SolicitudAutorizacion.class);

		cr.add(Restrictions.ne("ultimoMovimiento", Etiquetas.UNO_S));
		cr.createCriteria("solicitud")
				.add(Restrictions.eq("idSolicitud", idSolicitud)).createCriteria("estadoSolicitud")
				.add(Restrictions.ne("idEstadoSolicitud", Etiquetas.ID_ESTADO_SOLICITUD_RECHAZADA))
				.add(Restrictions.ne("idEstadoSolicitud", Etiquetas.ID_ESTADO_SOLICITUD_NUEVA));
		cr.add(Restrictions.disjunction());
		cr.createCriteria("estadoAutorizacion").add(Restrictions.ne("idEstadoAutorizacion", Etiquetas.ESTADO_AUTORIZACION_RECHAZADO));
		cr.createCriteria("tipoCriterio").add(Restrictions.eq("idTipoCriterio", idTipoCriterio));

		return ((Long) cr.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	@Override
	public Integer getCountAllByUsuarioAutorizo(Integer idSolicitud, Integer idUsuario) {
		
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_USUARIO_AUTORIZA = :idUsuario"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
				+ " AND ID_SOLICITUD = :idSolicitud"
		        + " AND COALESCE(RECHAZADO,0) != :rechazado";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", Etiquetas.DOS.toString());  //autorizado.
		parameters.put("idSolicitud", idSolicitud.toString());
		parameters.put("rechazado", Etiquetas.UNO.toString());
		
		List<SolicitudAutorizacion> lastSolicitudAutorizaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return lastSolicitudAutorizaciones.size();
	}

	@Override
	public Integer getCountAllByUsuarioAutorizoCriterio(Integer idSolicitud, Integer idUsuario, Integer criterio) {
		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_USUARIO_AUTORIZA = :idUsuario"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
				+ " AND ID_SOLICITUD = :idSolicitud"
		        + " AND ID_TIPO_CRITERIO = :idCriterio"
		        + " AND COALESCE(RECHAZADO,0) != :rechazado";

		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", Etiquetas.DOS.toString());  //autorizado.
		parameters.put("idSolicitud", idSolicitud.toString());
		parameters.put("idCriterio", criterio.toString());
		parameters.put("rechazado", Etiquetas.UNO.toString());

		List<SolicitudAutorizacion> lastSolicitudAutorizaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return lastSolicitudAutorizaciones.size();
	}

	@Override
	public Integer getCountAllByUsuarioAutorizoAutomatico(Integer idSolicitud, Integer idUsuario, Integer criterio) {

		String queryString = "FROM " + SolicitudAutorizacion.class.getName()
				+ " WHERE ID_USUARIO_AUTORIZA = :idUsuario"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
				+ " AND ID_SOLICITUD = :idSolicitud"
		        + " AND ID_TIPO_CRITERIO = :idCriterio"
		        + " AND COALESCE(RECHAZADO,0) != :rechazado"
		        + " AND COALESCE(AUTOMATICO,0) != :automatico";

		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", Etiquetas.DOS.toString());  //autorizado.
		parameters.put("idSolicitud", idSolicitud.toString());
		parameters.put("idCriterio", criterio.toString());
		parameters.put("rechazado", Etiquetas.UNO.toString());
		parameters.put("automatico", Etiquetas.UNO.toString());

		List<SolicitudAutorizacion> lastSolicitudAutorizaciones = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return lastSolicitudAutorizaciones.size();
		
	}
	
	
	
	
}