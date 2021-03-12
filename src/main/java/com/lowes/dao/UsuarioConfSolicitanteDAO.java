/**
 * 
 */
package com.lowes.dao;

import java.util.List;

import com.lowes.entity.UsuarioConfSolicitante;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface UsuarioConfSolicitanteDAO {
	public Integer createUsuarioConfSolicitante(UsuarioConfSolicitante usuarioConfSolicitante);

	public UsuarioConfSolicitante updateUsuarioConfSolicitante(UsuarioConfSolicitante usuarioConfSolicitante);

	public void deleteUsuarioConfSolicitante(int id);

	public List<UsuarioConfSolicitante> getAllUsuarioConfSolicitante();

	public UsuarioConfSolicitante getUsuarioConfSolicitante(int id);
	
	public List<UsuarioConfSolicitante> getUsuarioConfSolByIdUsuario(int idUsuario);

	public List<UsuarioConfSolicitante> getUsuarioConfSolicitante(int idUsuario, int idTipoSolicitud);
}