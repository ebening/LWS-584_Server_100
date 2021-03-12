package com.lowes.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lowes.dao.PerfilMenuDAO;
import com.lowes.entity.PerfilMenu;
import com.lowes.util.HibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;


@Repository
public class PerfilMenuDAOImpl implements PerfilMenuDAO {
	
    @Autowired
    private HibernateUtil hibernateUtil;
    
    @Autowired
    private SessionFactory sessionFactory;

	@Override
	public Integer createPerfilMenu(PerfilMenu perfilMenu) {
		return (Integer) hibernateUtil.create(perfilMenu);
	}

	@Override
	public PerfilMenu updatePerfilMenu(PerfilMenu perfilMenu) {
		return hibernateUtil.update(perfilMenu);
	}

	@Override
	public void deletePerfilMenu(Integer idPerfilMenu) {
		PerfilMenu pm = new PerfilMenu();
		pm.setIdPerfilMenu(idPerfilMenu);
		hibernateUtil.delete(pm);
	}

	@Override
	public List<PerfilMenu> getAllPerfilMenus() {
		return hibernateUtil.fetchAll(PerfilMenu.class);
	}

	@Override
	public PerfilMenu getPerfilMenu(Integer idPerfilMenu) {
		return hibernateUtil.fetchById(idPerfilMenu, PerfilMenu.class);
	}

	@Override
	public void deletePerfilMenuByPerfil(Integer idPerfil) {
		 sessionFactory.getCurrentSession().getTransaction();	
         Query query = sessionFactory.getCurrentSession().createQuery("delete PerfilMenu where perfil.idPerfil = :idPerfil");
         query.setParameter("idPerfil", idPerfil);
         query.executeUpdate();
		
	}
	
	@Override
	public List<PerfilMenu> getPerfilMenuByPerfil(Integer idPerfil) {
		
		// Crear una sesion Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Crear un objeto de consulta
		Criteria cr = session.createCriteria(PerfilMenu.class);
		
		cr.createCriteria("perfil").add(Restrictions.eq("idPerfil", idPerfil));
		cr.createCriteria("menu").addOrder(Order.asc("orden"));
		
		
		@SuppressWarnings("unchecked")
		List<PerfilMenu> menuPerfil = (List<PerfilMenu>)cr.list();

		return menuPerfil;
	}

}
