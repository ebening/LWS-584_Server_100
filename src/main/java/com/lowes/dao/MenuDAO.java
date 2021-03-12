package com.lowes.dao;

import java.util.List;
import com.lowes.entity.Menu;
/**
 * @author Josue Sanchez
 * @version 1.0
 */
public interface MenuDAO {
	
	public List<Menu> getAllMenu();
	public Menu getMenu(Integer id);

}
