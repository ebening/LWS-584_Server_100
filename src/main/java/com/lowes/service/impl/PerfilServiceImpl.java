package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.PerfilDAO;
import com.lowes.entity.Perfil;
import com.lowes.service.PerfilService;

@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {
	
	public PerfilServiceImpl(){
		
	}

	@Autowired
	private PerfilDAO perfilDAO;
	
	
	@Override
	public Integer createPerfil(Perfil perfil) {
		return perfilDAO.createPerfil(perfil);
	}

	@Override
	public Perfil updatePerfil(Perfil perfil) {
		return perfilDAO.updatePerfil(perfil);
	}

	@Override
	public void deletePerfil(Integer idPerfil) {
		perfilDAO.deletePerfil(idPerfil);

	}

	@Override
	public List<Perfil> getAllPerfiles() {
		return perfilDAO.getAllPerfiles();
	}

	@Override
	public Perfil getPerfil(Integer idPerfil) {
		return perfilDAO.getPerfil(idPerfil);
	}

}
