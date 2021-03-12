package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.VwSolicitudResumenDAO;
import com.lowes.entity.VwSolicitudResumen;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;

@Repository
public class VwSolicitudResumenDAOImpl implements VwSolicitudResumenDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;
	
	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumen() {
		return hibernateUtil.fetchAll(VwSolicitudResumen.class);
	}

	@Override
	public VwSolicitudResumen getVwSolicitudResumen(Integer idVwSolicitudResumen) {
		return hibernateUtil.fetchById(idVwSolicitudResumen, VwSolicitudResumen.class);
	}

	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumenByUsuarioEstatusSolicitud(Integer idUsuario, Integer idEstadoSolicitud, String fechaInicial, String fechaFinal) {
		String queryString ="FROM " + VwSolicitudResumen.class.getName()
				+ " WHERE (ID_USUARIO_SOLICITA = :idUsuario OR CREACION_USUARIO = :idUsuario)"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		
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
		
		List<VwSolicitudResumen> vwSolicitudResumen = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return vwSolicitudResumen;
	}

	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumenByUsuarioEstatusSolicitudCajaChica(Integer idUsuario,
			Integer idEstadoSolicitud, String fechaInicial, String fechaFinal) {
		
		String queryString ="FROM " + VwSolicitudResumen.class.getName()
				+ " WHERE CREACION_USUARIO = :idUsuario"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
		        + " AND (ID_TIPO_SOLICITUD = :cajaChica"
                + " OR  ID_TIPO_SOLICITUD = :noMercanciasConXML"
                + " OR  ID_TIPO_SOLICITUD = :noMercanciasSinXML)"
                + " AND (CREACION_USUARIO != ID_USUARIO_SOLICITA)";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		parameters.put("cajaChica", Etiquetas.SOLICITUD_CAJA_CHICA.toString());
		parameters.put("noMercanciasConXML", Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML.toString());
		parameters.put("noMercanciasSinXML", Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML.toString());
		
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
		
		List<VwSolicitudResumen> vwSolicitudResumen = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return vwSolicitudResumen;
	}

	@Override
	public List<VwSolicitudResumen> getAllVwSolicitudResumenPendientesComprobar(Integer idUsuario,
			Integer idEstadoSolicitud, Integer idTipoSolicitud, String fechaInicial, String fechaFinal) {
		String queryString ="FROM " + VwSolicitudResumen.class.getName()
				+ " WHERE CREACION_USUARIO = :idUsuario"
				+ " AND ID_TIPO_SOLICITUD = :idTipoSolicitud"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
				+ " AND (COMPROBADA = 0 OR COMPROBADA IS NULL)";
               // + " AND (CREACION_USUARIO != ID_USUARIO_SOLICITA)";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		parameters.put("idTipoSolicitud", idTipoSolicitud.toString());
		
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
		
		List<VwSolicitudResumen> vwSolicitudResumen = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return vwSolicitudResumen;
	}
	
	

}
