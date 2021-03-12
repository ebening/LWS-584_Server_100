package com.lowes.service;

import java.util.List;

import com.lowes.entity.PerfilMenu;

public interface PerfilMenuService {
	
	public Integer createPerfilMenu(PerfilMenu perfilMenu);
    public PerfilMenu updatePerfilMenu(PerfilMenu perfilMenu);
    public void deletePerfilMenu(Integer idPerfilMenu);
    public List<PerfilMenu> getAllPerfilMenus();
    public PerfilMenu getPerfilMenu(Integer idPerfilMenu);
    public void deletePerfilMenuByPerfil(Integer idPerfil);
    public List<PerfilMenu> getPerfilMenuByPerfil(Integer idPerfil);

}
