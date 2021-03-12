package com.lowes.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.entity.Bitacora;
import com.lowes.service.BitacoraService;
import com.lowes.service.UsuarioService;

/**
 * @author Josue Sanchez
 * @author Javier Castillo
 * @version 1.0
 */
@Repository
public class HibernateUtil {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired 
	private BitacoraService bitacora;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
		
    public <T> Serializable create(final T entity) {
    	
    	// registro de accion en la bitacora
    	String tableName = entity.getClass().getSimpleName();
    	if(tableName.equals(Etiquetas.Bitacora) == false && usuarioService.getUsuarioSesion() != null){
    		bitacora.registrarBitacora(new Bitacora(usuarioService.getUsuarioSesion(),tableName,Etiquetas.crear,new Date()));
    	}
    	
        return sessionFactory.getCurrentSession().save(entity);
    }
    
    public <T> T update(final T entity) {
    	// registro de accion en la bitacora
    	String tableName = entity.getClass().getSimpleName();
    	if(tableName.equals(Etiquetas.Bitacora) == false && usuarioService.getUsuarioSesion() != null){
    		bitacora.registrarBitacora(new Bitacora(usuarioService.getUsuarioSesion(),tableName,Etiquetas.editar,new Date()));
    	}
        sessionFactory.getCurrentSession().merge(entity);   
        return entity;
    }
    
	public <T> void delete(final T entity) {
		// registro de accion en la bitacora
    	String tableName = entity.getClass().getSimpleName();
    	if(tableName.equals(Etiquetas.Bitacora) == false){
    		bitacora.registrarBitacora(new Bitacora(usuarioService.getUsuarioSesion(),tableName,Etiquetas.eliminar,new Date()));
    	}
		sessionFactory.getCurrentSession().delete(entity);
	}

	public <T> void delete(Serializable id, Class<T> entityClass) {
		T entity = fetchById(id, entityClass);
		delete(entity);
	}
    
    @SuppressWarnings("unchecked")	
    public <T> List<T> fetchAll(Class<T> entityClass) {        
        return sessionFactory.getCurrentSession().createQuery(" FROM "+entityClass.getName()).list();        
    }
    
    @SuppressWarnings("unchecked")	
    public <T> List<T> fetchAllOrder(Class<T> entityClass, String propiedad) {        
        return sessionFactory.getCurrentSession().createQuery(" FROM "+entityClass.getName()+" ORDER BY "+propiedad+" ASC").list();        
    }
  
    @SuppressWarnings("rawtypes")
	public <T> List fetchAll(String query) {        
        return sessionFactory.getCurrentSession().createSQLQuery(query).list();        
    }
    
    @SuppressWarnings("rawtypes")
	public <T> List fetchAll(String query, Class<T> entityClass) {        
        SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(query);
        q.addEntity(entityClass);
        return q.list();
    }
    
    @SuppressWarnings("unchecked")
	public <T> List<T> fetchAllHql(String queryString, Map<String, String> parameters) {
    	Query query = sessionFactory.getCurrentSession().createQuery(queryString);
    	for(Map.Entry<String, String> parameter : parameters.entrySet()){
    		query.setParameter(parameter.getKey(), parameter.getValue());
    	}
        return query.list();
    }
    @SuppressWarnings("unchecked")
	public <T> List<T> fetchAllHqlObject(String queryString, Map<String, Object> parameters) {
    	Query query = sessionFactory.getCurrentSession().createQuery(queryString);
    	for(Map.Entry<String, Object> parameter : parameters.entrySet()){
    		query.setParameter(parameter.getKey(), parameter.getValue());
    	}
        return query.list();
    }
    
	public void fetchDeleteAndUpdateQuerys(String queryString, Map<String, String> parameters) {
    	Query query = sessionFactory.getCurrentSession().createQuery(queryString);
    	for(Map.Entry<String, String> parameter : parameters.entrySet()){
    		query.setParameter(parameter.getKey(), parameter.getValue());
    	}
    	query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
	public <T> T fetchById(Serializable id, Class<T> entityClass) {
        return (T)sessionFactory.getCurrentSession().get(entityClass, id);
    }
    
	
}
