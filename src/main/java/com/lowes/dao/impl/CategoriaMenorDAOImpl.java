/**
 * 
 */
package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.CategoriaMenorDAO;
import com.lowes.entity.CategoriaMenor;
import com.lowes.service.CategoriaMenorService;
import com.lowes.util.HibernateUtil;

/**
 * @author mrodriguezg
 *
 */
@Repository
public class CategoriaMenorDAOImpl implements CategoriaMenorDAO {

	public CategoriaMenorDAOImpl() {
		System.out.println("CategoriaMenorDAOImplConstruct");
	}

	@Autowired
	private HibernateUtil hibernateUtil;

	@Autowired
	private CategoriaMenorService categoriaMenorService;

	@Override
	public Integer createCategoriaMenor(CategoriaMenor categoriaMenor) {
		return (Integer) hibernateUtil.create(categoriaMenor);
	}

	@Override
	public CategoriaMenor updateCategoriaMenor(CategoriaMenor categoriaMenor) {
		return hibernateUtil.update(categoriaMenor);
	}

	@Override
	public void deleteCategoriaMenor(int id) {
		CategoriaMenor categoriaMenor = new CategoriaMenor();
		categoriaMenor = categoriaMenorService.getCategoriaMenor(id);
		hibernateUtil.delete(categoriaMenor);
	}

	@Override
	public List<CategoriaMenor> getAllCategoriaMenor() {
		return hibernateUtil.fetchAll(CategoriaMenor.class);
	}

	@Override
	public CategoriaMenor getCategoriaMenor(int id) {
		return hibernateUtil.fetchById(id, CategoriaMenor.class);
	}
}
