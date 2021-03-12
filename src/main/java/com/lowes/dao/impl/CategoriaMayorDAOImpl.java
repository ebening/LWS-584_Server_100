package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.CategoriaMayorDAO;
import com.lowes.entity.CategoriaMayor;
import com.lowes.util.HibernateUtil;

@Repository
public class CategoriaMayorDAOImpl implements CategoriaMayorDAO{
	
	public CategoriaMayorDAOImpl(){
		System.out.println("CategoriaMayorDAOImpl()");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;

	@Override
	public Integer createCategoriaMayor(CategoriaMayor categoriaMayor) {
		return (Integer) hibernateUtil.create(categoriaMayor);
	}

	@Override
	public CategoriaMayor updateCategoriaMayor(CategoriaMayor categoriaMayor) {
		return hibernateUtil.update(categoriaMayor);
	}

	@Override
	public void deleteCategoriaMayor(Integer id) {
		CategoriaMayor categoriaMayor = getCategoriaMayor(id); // Obtener compañia por id y enviar el objeto para eliminar
		hibernateUtil.delete(categoriaMayor);
	}

	@Override
	public List<CategoriaMayor> getAllCategoriaMayor() {
		return hibernateUtil.fetchAll(CategoriaMayor.class);
	}

	@Override
	public CategoriaMayor getCategoriaMayor(Integer id) {
		return hibernateUtil.fetchById(id, CategoriaMayor.class);
	}

}