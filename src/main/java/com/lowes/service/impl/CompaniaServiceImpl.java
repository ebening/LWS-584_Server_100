package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.CompaniaDAO;
import com.lowes.entity.Compania;
import com.lowes.service.CompaniaService;

@Service
@Transactional
public class CompaniaServiceImpl implements CompaniaService {
	
	public CompaniaServiceImpl(){
		System.out.println("CompaniaServiceImpl()");
	}
	
	@Autowired
	private CompaniaDAO companiaDAO;

	@Override
	public Integer createCompania(Compania compania) {
		return companiaDAO.createCompania(compania);
	}

	@Override
	public Compania updateCompania(Compania compania) {
		return companiaDAO.updateCompania(compania);
	}

	@Override
	public void deleteCompania(Integer idCompania) {
		companiaDAO.deleteCompania(idCompania);
	}

	@Override
	public List<Compania> getAllCompania() {
		return companiaDAO.getAllCompania();
	}

	@Override
	public Compania getCompania(Integer idCompania) {
		return companiaDAO.getCompania(idCompania);
	}

	@Override
	public String getTokenCompania(String rfcReceptor) {
		return companiaDAO.getTokenCompania(rfcReceptor);
	}

}