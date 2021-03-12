package com.lowes.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.lowes.dao.UsuarioDAO;
import com.lowes.entity.Usuario;
import com.lowes.util.Etiquetas;
import com.lowes.util.HibernateUtil;


@Repository
public class UsuarioDAOImpl implements UsuarioDAO {
	
	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public Integer createUsuario(Usuario usuario) {
		return  (Integer) hibernateUtil.create(usuario);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		return hibernateUtil.update(usuario);
	}

	@Override
	public void deleteUsuarioe(Integer id) {
		Usuario usuario = new Usuario();
		usuario = hibernateUtil.fetchById(id, Usuario.class);
		hibernateUtil.delete(usuario);
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		return hibernateUtil.fetchAllOrder(Usuario.class,"NOMBRE");
	}

	@Override
	public Usuario getUsuario(Integer id) {
		return hibernateUtil.fetchById(id, Usuario.class);
	}

	@Override
	public Usuario getUsuarioFiguraContable() {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE ES_FIGURA_CONTABLE = :figuraContable";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("figuraContable", Etiquetas.UNO.toString());
		
		List<Usuario> usuariosFiguraContable = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (usuariosFiguraContable.size() > 0) {
			return usuariosFiguraContable.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Usuario> getUsuariosAutorizadores() {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE ES_AUTORIZADOR = :esAutorizador"
				+ " ORDER BY UPPER(NOMBRE) ASC";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("esAutorizador", Etiquetas.UNO.toString());
		
		List<Usuario> usuariosAutorizadores = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (usuariosAutorizadores.size() > 0) {
			return usuariosAutorizadores;
		} else {
			return null;
		}
	}
	
	@Override
	public List<Usuario> getUsuariosSolicitantes() {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE ES_SOLICITANTE = :esSolicitante"
				+ " ORDER BY UPPER(NOMBRE) ASC";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("esSolicitante", Etiquetas.UNO.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public List<Usuario> getUsuarioJefe(Integer idUsuario) {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE ID_USUARIO_JEFE = :idUsuario";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idUsuario", idUsuario.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public List<Usuario> getUsuariosByPuesto(Integer idPuesto) {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE ID_PUESTO = :idPuesto";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("idPuesto", idPuesto.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}


	@Override
	public List<Usuario> getUsuariosBeneficiarios() {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE ES_BENEFICIARIO_CAJA_CHICA = :valor";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("valor", "1");
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}

	@Override
	public List<Usuario> getUsuariosBeneficiariosByLocacion(Integer idLocacion) {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE ES_BENEFICIARIO_CAJA_CHICA = :valor"
				+ " AND ID_LOCACION = :idLocacion";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("valor", "1");
		parameters.put("idLocacion", idLocacion.toString());
		
		return hibernateUtil.fetchAllHql(queryString, parameters);
	}
	
	@Override
	public Usuario getUsuarioByCuenta(String cuenta) {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE CUENTA = :cuenta";
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("cuenta", cuenta);
		
		List<Usuario> usuarios = hibernateUtil.fetchAllHql(queryString, parameters);
		
		if (usuarios.size() > 0) {
			return usuarios.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Usuario getUsuarioByProveedor(Integer numProveedor) {
		String queryString ="FROM " + Usuario.class.getName()
				+ " WHERE NUMERO_PROVEEDOR = :numProveedor";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numProveedor", numProveedor);
		
		List<Usuario> usuarios = hibernateUtil.fetchAllHqlObject(queryString, parameters);
		
		if (usuarios.size() > 0) {
			return usuarios.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Usuario getUsuarioSesion() {

		String userName = null;
		Object principal;
		if(SecurityContextHolder.getContext().getAuthentication() == null){
			return null;
		}
		else{
			principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		String queryString = "FROM " + Usuario.class.getName() + " WHERE CUENTA = :cuenta";

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("cuenta", userName);

		List<Usuario> usuarios = hibernateUtil.fetchAllHql(queryString, parameters);

		if (usuarios.size() > 0) {
			return usuarios.get(0);
		} else {
			return null;
		}
	}
}
