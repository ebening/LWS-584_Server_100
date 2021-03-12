package com.lowes.service;

import java.util.List;

import com.lowes.entity.Usuario;

public interface UsuarioService {
	public Integer createUsuario(Usuario usuario);
    public Usuario updateUsuario(Usuario usuario);
    public void deleteUsuarioe(Integer id);
    public List<Usuario> getAllUsuarios();
    public Usuario getUsuario(Integer id);
    public List<Usuario> getUsuariosAutorizadores();
    public List<Usuario> getUsuariosSolicitantes();
	public List<Usuario> getUsuarioJefe(Integer idUsuario);
    public List<Usuario> getUsuariosByPuesto(Integer idPuesto);
    public List<Usuario> getUsuariosBeneficiarios();
	public List<Usuario> getUsuariosBeneficiariosByLocacion(Integer idLocacion);
	public Usuario getUsuarioByCuenta(String cuenta);
	public Usuario getUsuarioByProveedor(Integer numProveedor);
	public Usuario getUsuarioSesion();

}
