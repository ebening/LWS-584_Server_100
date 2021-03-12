package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.PerfilMenuDAO;
import com.lowes.entity.PerfilMenu;
import com.lowes.service.PerfilMenuService;


@Service
@Transactional
public class PerfilMenuServiceImpl implements PerfilMenuService {
	
	@Autowired
	private PerfilMenuDAO perfilMenuDAO;

	@Override
	public Integer createPerfilMenu(PerfilMenu perfilMenu) {
		return perfilMenuDAO.createPerfilMenu(perfilMenu);
	}

	@Override
	public PerfilMenu updatePerfilMenu(PerfilMenu perfilMenu) {
		return perfilMenuDAO.updatePerfilMenu(perfilMenu);
	}

	@Override
	public void deletePerfilMenu(Integer idPerfilMenu) {
		perfilMenuDAO.deletePerfilMenu(idPerfilMenu);		
	}

	@Override
	public List<PerfilMenu> getAllPerfilMenus() {
		return perfilMenuDAO.getAllPerfilMenus();
	}

	@Override
	public PerfilMenu getPerfilMenu(Integer idPerfilMenu) {
		return perfilMenuDAO.getPerfilMenu(idPerfilMenu);
	}

	@Override
	public void deletePerfilMenuByPerfil(Integer idPerfil) {
         perfilMenuDAO.deletePerfilMenuByPerfil(idPerfil);		
	}
	
	@Override
	public List<PerfilMenu> getPerfilMenuByPerfil(Integer idPerfil) {
		return perfilMenuDAO.getPerfilMenuByPerfil(idPerfil);
	}
	
}
