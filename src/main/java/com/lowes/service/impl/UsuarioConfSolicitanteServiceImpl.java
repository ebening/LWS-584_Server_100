/**
 * 
 */
package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.UsuarioConfSolicitanteDAO;
import com.lowes.entity.UsuarioConfSolicitante;
import com.lowes.service.UsuarioConfSolicitanteService;

/**
 * @author miguelrg
 * @version 1.0
 */
@Service
@Transactional
public class UsuarioConfSolicitanteServiceImpl implements UsuarioConfSolicitanteService {

	public UsuarioConfSolicitanteServiceImpl() {
		System.out.println("UsuarioConfSolicitanteServiceImplConstruct()");
	}

	@Autowired
	private UsuarioConfSolicitanteDAO usuarioConfSolicitanteDAO;

	@Override
	public Integer createUsuarioConfSolicitante(UsuarioConfSolicitante usuarioConfSolicitante) {
		return usuarioConfSolicitanteDAO.createUsuarioConfSolicitante(usuarioConfSolicitante);
	}

	@Override
	public UsuarioConfSolicitante updateUsuarioConfSolicitante(UsuarioConfSolicitante usuarioConfSolicitante) {
		return usuarioConfSolicitanteDAO.updateUsuarioConfSolicitante(usuarioConfSolicitante);
	}

	@Override
	public void deleteUsuarioConfSolicitante(int id) {
		usuarioConfSolicitanteDAO.deleteUsuarioConfSolicitante(id);
	}

	@Override
	public List<UsuarioConfSolicitante> getAllUsuarioConfSolicitante() {
		return usuarioConfSolicitanteDAO.getAllUsuarioConfSolicitante();
	}

	@Override
	public UsuarioConfSolicitante getUsuarioConfSolicitante(int id) {
		return usuarioConfSolicitanteDAO.getUsuarioConfSolicitante(id);
	}

	@Override
	public List<UsuarioConfSolicitante> getUsuarioConfSolByIdUsuario(int idUsuario) {
		return usuarioConfSolicitanteDAO.getUsuarioConfSolByIdUsuario(idUsuario);
	}

	@Override
	public List<UsuarioConfSolicitante> getUsuarioConfSolicitante(int idUsuario, int idTipoSolicitud) {
		return usuarioConfSolicitanteDAO.getUsuarioConfSolicitante(idUsuario, idTipoSolicitud);
	}
	

}