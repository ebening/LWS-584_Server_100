package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.SolicitudDAO;
import com.lowes.entity.Solicitud;
import com.lowes.entity.SolicitudAutorizacion;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;


@Repository
public class SolicitudDAOImpl implements SolicitudDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Integer createSolicitud(Solicitud solicitud) {
		return  (Integer) hibernateUtil.create(solicitud);
	}

	@Override
	public Solicitud updateSolicitud(Solicitud solicitud) {
		return hibernateUtil.update(solicitud);
	}

	@Override
	public void deleteSolicitud(Integer idSolicitud) {
		Solicitud solicitud = new Solicitud();
		solicitud = hibernateUtil.fetchById(idSolicitud, Solicitud.class);
		hibernateUtil.delete(solicitud);
	}

	@Override
	public List<Solicitud> getAllSolicitud() {
		return hibernateUtil.fetchAll(Solicitud.class);
	}

	@Override
	public Solicitud getSolicitud(Integer idSolicitud) {
		return hibernateUtil.fetchById(idSolicitud, Solicitud.class);
	}

	@Override
	public Integer getSolicitudCountByUsuarioEstatus(Integer idUsuario, Integer idEstadoSolicitud) {
			String queryString = "FROM " + Solicitud.class.getName()
					+ " WHERE ID_USUARIO_SOLICITA = :idUsuario"
					+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud";
			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("idUsuario", idUsuario.toString());
			parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
			
			List<SolicitudAutorizacion> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
			
			return listSolicitudes.size();
	}
	
	@Override
	public Integer getSolicitudCountByUsuarioEstatusCajaChica(Integer idUsuario, Integer idEstadoSolicitud) {
		String queryString = "FROM " + Solicitud.class.getName()
				+ " WHERE CREACION_USUARIO = :idUsuario"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
		        + " AND (ID_TIPO_SOLICITUD = :cajaChica"
                + " OR ID_TIPO_SOLICITUD = :noMercanciasConXML"
                + " OR ID_TIPO_SOLICITUD = :noMercanciasSinXML"
                + " OR ID_TIPO_SOLICITUD = :reembolsos)"
                + " AND CREACION_USUARIO != ID_USUARIO_SOLICITA)";

		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		parameters.put("cajaChica", Etiquetas.SOLICITUD_CAJA_CHICA.toString());
		parameters.put("noMercanciasConXML", Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML.toString());
		parameters.put("noMercanciasSinXML", Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML.toString());
		parameters.put("reembolsos", Etiquetas.SOLICITUD_REEMBOLSOS.toString());
		
		List<SolicitudAutorizacion> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return listSolicitudes.size();
	}
	
	@Override
	public Integer getSolicitudCountByUsuarioEstatusDoble(Integer idUsuario, Integer idEstadoSolicitud1, Integer idEstadoSolicitud2) {
			String queryString = "FROM " + Solicitud.class.getName()
					+ " WHERE ID_USUARIO_SOLICITA = :idUsuario"
					+ " AND (ID_ESTADO_SOLICITUD = :idEstadoSolicitud1"
					+ " OR ID_ESTADO_SOLICITUD = :idEstadoSolicitud2)";
			
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("idUsuario", idUsuario.toString());
			parameters.put("idEstadoSolicitud1", idEstadoSolicitud1.toString());
			parameters.put("idEstadoSolicitud2", idEstadoSolicitud2.toString());
			
			List<SolicitudAutorizacion> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
			
			return listSolicitudes.size();
	}
	
	@Override
	public Integer getSolicitudCountByUsuarioEstatusDobleCajaChica(Integer idUsuario, Integer idEstadoSolicitud1,
			Integer idEstadoSolicitud2) {
		
		String queryString = "FROM " + Solicitud.class.getName()
				+ " WHERE CREACION_USUARIO = :idUsuario"
				+ " AND (ID_ESTADO_SOLICITUD = :idEstadoSolicitud1"
				+ " OR ID_ESTADO_SOLICITUD = :idEstadoSolicitud2)"
		        + " AND (ID_TIPO_SOLICITUD = :cajaChica "
                + " OR ID_TIPO_SOLICITUD   = :noMercanciasConXML  "
                + " OR ID_TIPO_SOLICITUD   = :noMercanciasSinXML "
                + " OR ID_TIPO_SOLICITUD   = :reembolsos) "
                + " AND (CREACION_USUARIO != ID_USUARIO_SOLICITA) ";

		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idEstadoSolicitud1", idEstadoSolicitud1.toString());
		parameters.put("idEstadoSolicitud2", idEstadoSolicitud2.toString());
		parameters.put("cajaChica", Etiquetas.SOLICITUD_CAJA_CHICA.toString());
		parameters.put("noMercanciasConXML", Etiquetas.SOLICITUD_NO_MERCANCIAS_CON_XML.toString());
		parameters.put("noMercanciasSinXML", Etiquetas.SOLICITUD_NO_MERCANCIAS_SIN_XML.toString());
		parameters.put("reembolsos", Etiquetas.SOLICITUD_REEMBOLSOS.toString());
		
		List<SolicitudAutorizacion> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return listSolicitudes.size();
		
	}

	@Override
	public List<Solicitud> getAllSolicitudByStatus(Integer idEstadoSolicitud) {
		String queryString = "FROM " + Solicitud.class.getName()
				+ " WHERE ID_ESTADO_SOLICITUD = :idEstadoSolicitud AND (ENVIADO_CM IS NULL OR ENVIADO_CM = 0)";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		
		List<Solicitud> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		return listSolicitudes;
	}

	@Override
	public List<Solicitud> getAnticiposPendientes(Integer idUsuario, Integer idProveedor) {
		String queryString = "FROM " + Solicitud.class.getName()
				+ " WHERE ID_USUARIO_SOLICITA = :idUsuario AND ID_PROVEEDOR = :idProveedor AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud AND COMPROBADA = 0";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", String.valueOf(idUsuario));
		parameters.put("idProveedor", String.valueOf(idProveedor));
		parameters.put("idEstadoSolicitud", String.valueOf(Etiquetas.UNO));
//		parameters.put("idEstadoSolicitud", String.valueOf(Etiquetas.SEIS));
		
		List<Solicitud> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		return listSolicitudes;
	}

	@Override
	public Integer getSolicitudCountByAnticipoPentiente(Integer idUsuario, Integer idTipoSolicitud,
			Integer idEstadoSolicitud) {
		String queryString = "FROM " + Solicitud.class.getName()
				+ " WHERE CREACION_USUARIO = :idUsuario"
				+ " AND ID_TIPO_SOLICITUD = :idTipoSolicitud"
				+ " AND ID_ESTADO_SOLICITUD = :idEstadoSolicitud"
		        + " AND (COMPROBADA = 0 OR COMPROBADA IS NULL)";
//                + " AND (CREACION_USUARIO != ID_USUARIO_SOLICITA)";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		parameters.put("idTipoSolicitud", idTipoSolicitud.toString());
		parameters.put("idEstadoSolicitud", idEstadoSolicitud.toString());
		
		List<SolicitudAutorizacion> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		
		return listSolicitudes.size();
	}

	@Override
	public List<Solicitud> getAnticiposMultiplesByEstatus(Integer idUsuario, Integer idProveedor, Integer idEstadoSolicitudPagada, Integer idEstadoSolicitudMultiple) {
		String queryString = "FROM " + Solicitud.class.getName() + " WHERE ID_USUARIO_SOLICITA = :idUsuario AND ID_PROVEEDOR = :idProveedor AND (ID_ESTADO_SOLICITUD = :idEstadoSolicitud ";
		if (idEstadoSolicitudMultiple != null) {
			queryString +=  " OR ID_ESTADO_SOLICITUD = :idEstadoSolicitudMultiple ";
		}
		queryString +=  ") AND COMPROBADA = 0";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", String.valueOf(idUsuario));
		parameters.put("idProveedor", String.valueOf(idProveedor));
		parameters.put("idEstadoSolicitud", String.valueOf(idEstadoSolicitudPagada));
		if (idEstadoSolicitudMultiple != null) {
			parameters.put("idEstadoSolicitudMultiple", String.valueOf(idEstadoSolicitudMultiple));
		}
		System.out.println("queryString :" +queryString);
		List<Solicitud> listSolicitudes = hibernateUtil.fetchAllHql(queryString.toString(), parameters);
		return listSolicitudes;
	}

	@Override
	public List<Solicitud> getAnticiposMultiplesByEstatusAComprobar(Integer idUsuario, Integer idProveedor, Integer idEstadoSolicitudPagada, Integer idEstadoSolicitudMultiple, Integer idSolicitud) {
		String queryString = "SELECT * FROM SOLICITUD WHERE ID_USUARIO_SOLICITA = "+idUsuario+" AND ID_PROVEEDOR = "+idProveedor+" AND (ID_ESTADO_SOLICITUD = "+idEstadoSolicitudPagada;
		if (idEstadoSolicitudMultiple != null) {
			queryString +=  " OR ID_ESTADO_SOLICITUD = "+idEstadoSolicitudMultiple;
		}
		queryString +=  ") AND COMPROBADA = 0 AND ID_SOLICITUD NOT IN (SELECT ID_SOLICITUD_MULTIPLE FROM COMPROBACION_ANTICIPO_MULTIPLE WHERE ID_SOLICITUD != "+idSolicitud+") ";
		System.out.println("queryString :" +queryString);
		List<Solicitud> listSolicitudes = hibernateUtil.fetchAll(queryString.toString(), Solicitud.class);
		return listSolicitudes;
	}

	@Override
	public List<Solicitud> getAnticiposPendientesPorComprobar(Integer idUsuario, Integer idProveedor, Integer idEstadoCreado, Integer idEstadoCancelado, Integer idEstadoComprobado) {
		String queryString = "FROM " + Solicitud.class.getName()
				+ " WHERE ID_USUARIO_SOLICITA = :idUsuario AND ID_PROVEEDOR = :idProveedor AND ID_ESTADO_SOLICITUD <> :idEstadoCreado AND ID_ESTADO_SOLICITUD <> :idEstadoCancelado AND ID_ESTADO_SOLICITUD <> :idEstadoComprobado";// AND COMPROBADA = 0
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", String.valueOf(idUsuario));
		parameters.put("idProveedor", String.valueOf(idProveedor));
		parameters.put("idEstadoCreado", String.valueOf(idEstadoCreado));
		parameters.put("idEstadoCancelado", String.valueOf(idEstadoCancelado));
		parameters.put("idEstadoComprobado", String.valueOf(idEstadoComprobado));
		
		List<Solicitud> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		return listSolicitudes;
	}
	@Override
	public List<Solicitud> getSolicitudesByProveedor(Integer idProveedor) {
		String queryString = "FROM " + Solicitud.class.getName()
				+ " WHERE ID_PROVEEDOR = :idProveedor";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idProveedor", String.valueOf(idProveedor));
//		parameters.put("idEstadoSolicitud", String.valueOf(Etiquetas.SEIS));
		
		List<Solicitud> listSolicitudes = hibernateUtil.fetchAllHql(queryString, parameters);
		return listSolicitudes;
	}
}
