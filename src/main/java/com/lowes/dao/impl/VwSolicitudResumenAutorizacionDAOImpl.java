package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.VwSolicitudResumenAutorizacionDAO;
import com.lowes.entity.VwSolicitudResumenAutorizacion;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;

@Repository
public class VwSolicitudResumenAutorizacionDAOImpl implements VwSolicitudResumenAutorizacionDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Override
	public List<VwSolicitudResumenAutorizacion> getAllVwSolicitudResumenAutorizacion() {
		return hibernateUtil.fetchAll(VwSolicitudResumenAutorizacion.class);
	}

	@Override
	public VwSolicitudResumenAutorizacion getVwSolicitudResumenAutorizacion(Integer idVwSolicitudResumenAutorizacion) {
		return hibernateUtil.fetchById(idVwSolicitudResumenAutorizacion, VwSolicitudResumenAutorizacion.class);
	}

	@Override
	public List<VwSolicitudResumenAutorizacion> getAllVwSolicitudResumenAutorizacionByUsuarioEstatusSolicitud(
		Integer idUsuario, Integer idEstadoSolicitud, String fechaInicial, String fechaFinal) {
		
		String queryString = null;
		Map<String, String> parameters = new HashMap<String, String>();
	
			 queryString ="FROM " + VwSolicitudResumenAutorizacion.class.getName()
					+ " WHERE ID_USUARIO_AUTORIZA = :idUsuario"
					+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
					+ " AND ID_ESTADO_AUTORIZACION = :idEstadoAutorizacion";
			 
			    parameters.put("idUsuario", idUsuario.toString());
				parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
				parameters.put("idEstadoAutorizacion", Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR.toString());
		
		if(fechaInicial != null){
			fechaInicial += " 00:00:00";
			parameters.put("fechaInicial", fechaInicial);
			queryString += " AND CREACION_FECHA >= :fechaInicial";
		}
		
		if(fechaFinal != null){
			fechaFinal += " 23:59:59";
			parameters.put("fechaFinal", fechaFinal);
			queryString += " AND CREACION_FECHA <= :fechaFinal";
		}
		
		queryString += " ORDER BY FECHA_FACTURA";
		
		List<VwSolicitudResumenAutorizacion> vwSolicitudResumen = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return vwSolicitudResumen;
	}

	@Override
	public Integer getCountSolicitudAutorizacionByUsuario(Integer idUsuario, Integer idEstadoSolicitud) {
		String queryString ="FROM " + VwSolicitudResumenAutorizacion.class.getName()
				+ " WHERE ID_USUARIO_AUTORIZA = :idUsuario"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
				+ " AND ID_ESTADO_AUTORIZACION = :idEstadoAutorizacion";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		parameters.put("idEstadoAutorizacion", Etiquetas.ESTADO_AUTORIZACION_POR_REVISAR.toString());
		
		List<VwSolicitudResumenAutorizacion> vwSolicitudResumen = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return vwSolicitudResumen.size();
		
		
	}

}
