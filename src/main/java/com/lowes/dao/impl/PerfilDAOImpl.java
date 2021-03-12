package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.PerfilDAO;
import com.lowes.entity.Perfil;
import com.lowes.util.HibernateUtil;

@Repository
public class PerfilDAOImpl implements PerfilDAO {
	
	public PerfilDAOImpl(){
		System.out.println("PerfilDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createPerfil(Perfil perfil) {
		return (Integer) hibernateUtil.create(perfil);
	}

	@Override
	public Perfil updatePerfil(Perfil perfil) {
		return hibernateUtil.update(perfil);
	}

	@Override
	public void deletePerfil(Integer idPerfil) {
		Perfil perfil = getPerfil(idPerfil);
		hibernateUtil.delete(perfil);
	}

	@Override
	public List<Perfil> getAllPerfiles() {
		return hibernateUtil.fetchAll(Perfil.class);
	}

	@Override
	public Perfil getPerfil(Integer idPerfil) {
		return hibernateUtil.fetchById(idPerfil, Perfil.class);
	}

}
