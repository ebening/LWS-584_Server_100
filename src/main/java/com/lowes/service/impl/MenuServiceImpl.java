package com.lowes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.MenuDAO;
import com.lowes.entity.Menu;
import com.lowes.entity.PerfilMenu;
import com.lowes.service.MenuService;
import com.lowes.service.PerfilMenuService;

/**
 * @author Josue Sanchez
 * @version 1.0
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuDAO menuDAO;
	
	@Autowired
	private PerfilMenuService perfilMenuService;
	
	public MenuServiceImpl(){
		System.out.println("MenuServiceImpl");
	}

	@Override
	public List<Menu> getAllMenu() {
		try {
			return menuDAO.getAllMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Menu getMenu(Integer id) {
		return menuDAO.getMenu(id);
	}
	
	@Override
	public List<Menu> getMenuByPerfil(Integer idPerfil) {
		List<PerfilMenu> perfilMenuElementos = perfilMenuService.getPerfilMenuByPerfil(idPerfil);
		List<Menu> menuPerfil = new ArrayList<>();
		
		for(Integer i = 0; i < perfilMenuElementos.size(); i++){
			perfilMenuElementos.get(i).getMenu().setMenus(new ArrayList<>());
		}

		for (PerfilMenu perfilMenu : perfilMenuElementos) {
			if (perfilMenu.getMenu().getMenuPadre() == null) {
				
				Menu menuPadre = perfilMenu.getMenu();

				List<Menu> menuPerfilHijo1 = new ArrayList<>();

				for (PerfilMenu perfilMenuHijo1 : perfilMenuElementos) {
					if (perfilMenuHijo1.getMenu().getMenuPadre() != null) {
						if (perfilMenuHijo1.getMenu().getMenuPadre().getIdMenu() == menuPadre.getIdMenu()) {
							
							Menu menuHijo1 = perfilMenuHijo1.getMenu();

							List<Menu> menuPerfilHijo2 = new ArrayList<>();

							for (PerfilMenu perfilMenuHijo2 : perfilMenuElementos) {
								if (perfilMenuHijo2.getMenu().getMenuPadre() != null) {
									if (perfilMenuHijo2.getMenu().getMenuPadre().getIdMenu() == menuHijo1.getIdMenu()) {
										
										menuPerfilHijo2.add(perfilMenuHijo2.getMenu());
									}
								}
							}

							if (menuPerfilHijo2.size() > 0) {
								
								menuHijo1.setMenus(menuPerfilHijo2);
							}
							
							menuPerfilHijo1.add(menuHijo1);
						}
					}
				}

				if (menuPerfilHijo1.size() > 0) {
					
					menuPadre.setMenus(menuPerfilHijo1);
				}

				menuPerfil.add(menuPadre);
			}
		}

		return menuPerfil;
	}
}
