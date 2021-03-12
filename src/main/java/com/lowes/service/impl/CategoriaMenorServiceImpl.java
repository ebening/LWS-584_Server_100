/**
 * 
 */
package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.CategoriaMenorDAO;
import com.lowes.entity.CategoriaMenor;
import com.lowes.service.CategoriaMenorService;

/**
 * @author miguelrg
 * @version 1.0
 */
@Service
@Transactional
public class CategoriaMenorServiceImpl implements CategoriaMenorService {

	public CategoriaMenorServiceImpl() {
		System.out.println("CategoriaMenorServiceImplConstruct()");
	}

	@Autowired
	private CategoriaMenorDAO categoriaMenorDAO;

	@Override
	public Integer createCategoriaMenor(CategoriaMenor categoriaMenor) {
		return categoriaMenorDAO.createCategoriaMenor(categoriaMenor);
	}

	@Override
	public CategoriaMenor updateCategoriaMenor(CategoriaMenor categoriaMenor) {
		return categoriaMenorDAO.updateCategoriaMenor(categoriaMenor);
	}

	@Override
	public void deleteCategoriaMenor(int id) {
		categoriaMenorDAO.deleteCategoriaMenor(id);
	}

	@Override
	public List<CategoriaMenor> getAllCategoriaMenor() {
		return categoriaMenorDAO.getAllCategoriaMenor();
	}

	@Override
	public CategoriaMenor getCategoriaMenor(int id) {
		return categoriaMenorDAO.getCategoriaMenor(id);
	}
}