package com.lowes.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.MenuDAO;
import com.lowes.entity.Menu;
import com.lowes.util.HibernateUtil;

/**
 * @author  Josue Sanchez
 * @version 1.0
 */
@Repository
public class MenuDAOImpl implements MenuDAO {
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public MenuDAOImpl(){
		System.out.println("MenuDAOImpl");
	}

	@Override
	public List<Menu> getAllMenu() {
		
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(Menu.class);

		cr.addOrder(Order.asc("orden"));

		@SuppressWarnings("unchecked")
		List<Menu> menu = (List<Menu>) cr.list();

		return menu;
	}

	@Override
	public Menu getMenu(Integer id) {
		return hibernateUtil.fetchById(id, Menu.class);
	}
	
	
}
