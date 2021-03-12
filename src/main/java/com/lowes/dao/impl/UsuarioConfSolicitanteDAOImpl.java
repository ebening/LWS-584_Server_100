/**
 * 
 */
package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.UsuarioConfSolicitanteDAO;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.UsuarioConfSolicitanteService;
import com.lowes.util.HibernateUtil;

/**
 * @author miguelrg
 * @version 1.0
 */
@Repository
public class UsuarioConfSolicitanteDAOImpl implements UsuarioConfSolicitanteDAO {

	public UsuarioConfSolicitanteDAOImpl() {
		System.out.println("UsuarioConfSolicitanteDAOImplConstruct");
	}

	@Autowired
	private HibernateUtil hibernateUtil;
	@Autowired
	private UsuarioConfSolicitanteService usuarioConfSolicitanteService;

	@Override
	public Integer createUsuarioConfSolicitante(UsuarioConfSolicitante usuarioConfSolicitante) {
		return (Integer) hibernateUtil.create(usuarioConfSolicitante);
	}

	@Override
	public UsuarioConfSolicitante updateUsuarioConfSolicitante(UsuarioConfSolicitante usuarioConfSolicitante) {
		return hibernateUtil.update(usuarioConfSolicitante);
	}

	@Override
	public void deleteUsuarioConfSolicitante(int id) {
		UsuarioConfSolicitante usuarioConfSolicitante = new UsuarioConfSolicitante();
		usuarioConfSolicitante = usuarioConfSolicitanteService.getUsuarioConfSolicitante(id);
		hibernateUtil.delete(usuarioConfSolicitante);
	}

	@Override
	public List<UsuarioConfSolicitante> getAllUsuarioConfSolicitante() {
		return hibernateUtil.fetchAll(UsuarioConfSolicitante.class);
	}

	@Override
	public UsuarioConfSolicitante getUsuarioConfSolicitante(int id) {
		return hibernateUtil.fetchById(id, UsuarioConfSolicitante.class);
	}

	@Override
	public List<UsuarioConfSolicitante> getUsuarioConfSolByIdUsuario(int idUsuario) {
		 String queryString = "FROM  "+ UsuarioConfSolicitante.class.getName() +" WHERE ID_USUARIO = :idUsuario";
		 Map<String, String> parameters = new HashMap<String, String>();
		 parameters.put("idUsuario", String.valueOf(idUsuario));
		 return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
	@Override
	public List<UsuarioConfSolicitante> getUsuarioConfSolicitante(int idUsuario, int idTipoSolicitud) {
		 String queryString = "FROM  "+ UsuarioConfSolicitante.class.getName() +" WHERE ID_USUARIO = :idUsuario "
		 		+ "AND ID_TIPO_SOLICITUD = :idTipoSolicitud";
		 Map<String, String> parameters = new HashMap<String, String>();
		 parameters.put("idUsuario", String.valueOf(idUsuario));
		 parameters.put("idTipoSolicitud", String.valueOf(idTipoSolicitud));
		 return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
}