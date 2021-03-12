package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.CategoriaMayorDAO;
import com.lowes.entity.CategoriaMayor;
import com.lowes.service.CategoriaMayorService;

@Service
@Transactional
public class CategoriaMayorServiceImpl implements CategoriaMayorService{
	
	public CategoriaMayorServiceImpl(){
		System.out.println("CategoriaMayorServiceImpl()");
	}
	
	@Autowired
	private CategoriaMayorDAO categoriaMayorDAO;

	@Override
	public Integer createCategoriaMayor(CategoriaMayor categoriaMayor) {
		return categoriaMayorDAO.createCategoriaMayor(categoriaMayor);
	}

	@Override
	public CategoriaMayor updateCategoriaMayor(CategoriaMayor categoriaMayor) {
		return categoriaMayorDAO.updateCategoriaMayor(categoriaMayor);
	}

	@Override
	public void deleteCategoriaMayor(Integer id) {
		categoriaMayorDAO.deleteCategoriaMayor(id);
	}

	@Override
	public List<CategoriaMayor> getAllCategoriaMayor() {
		return categoriaMayorDAO.getAllCategoriaMayor();
	}

	@Override
	public CategoriaMayor getCategoriaMayor(Integer id) {
		return categoriaMayorDAO.getCategoriaMayor(id);
	}

}