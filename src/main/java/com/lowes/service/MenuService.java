package com.lowes.service;

import java.util.List;

import com.lowes.entity.Menu;

/**
 * @author Josue Sanchez
 * @version 1.0
 */

public interface MenuService {
	
	public List<Menu> getAllMenu();
	public Menu getMenu(Integer id);
	public List<Menu> getMenuByPerfil(Integer idPerfil);

}


