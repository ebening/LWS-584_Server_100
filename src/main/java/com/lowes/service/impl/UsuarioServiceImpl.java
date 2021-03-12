package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.UsuarioDAO;
import com.lowes.entity.Usuario;
import com.lowes.service.UsuarioService;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDAO;
		
	@Override
	public Integer createUsuario(Usuario usuario) {
		return usuarioDAO.createUsuario(usuario);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		return usuarioDAO.updateUsuario(usuario);
	}

	@Override
	public void deleteUsuarioe(Integer id) {
		usuarioDAO.deleteUsuarioe(id);
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		return usuarioDAO.getAllUsuarios();
	}

	@Override
	public Usuario getUsuario(Integer id) {
		return usuarioDAO.getUsuario(id);
	}

	@Override
	public List<Usuario> getUsuariosAutorizadores() {
		return usuarioDAO.getUsuariosAutorizadores();
	}
	
	@Override
	public List<Usuario> getUsuariosSolicitantes() {
		return usuarioDAO.getUsuariosSolicitantes();
	}

	@Override
	public List<Usuario> getUsuariosByPuesto(Integer idPuesto) {
		return usuarioDAO.getUsuariosByPuesto(idPuesto);
	}

	@Override
	public List<Usuario> getUsuarioJefe(Integer idUsuario) {
		return  usuarioDAO.getUsuarioJefe(idUsuario);
	}

	@Override
	public List<Usuario> getUsuariosBeneficiarios() {
		return usuarioDAO.getUsuariosBeneficiarios();
	}

	@Override
	public List<Usuario> getUsuariosBeneficiariosByLocacion(Integer idLocacion) {
		return usuarioDAO.getUsuariosBeneficiariosByLocacion(idLocacion);
	}

	@Override
	public Usuario getUsuarioByCuenta(String cuenta) {
		return usuarioDAO.getUsuarioByCuenta(cuenta);
	}
	@Override
	public Usuario getUsuarioByProveedor(Integer numProveedor) {
		return usuarioDAO.getUsuarioByProveedor(numProveedor);
	}

	@Override
	public Usuario getUsuarioSesion() {
		return usuarioDAO.getUsuarioSesion();
	}	
	
}
