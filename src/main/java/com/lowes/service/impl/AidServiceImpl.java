package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.AidDAO;
import com.lowes.entity.Aid;
import com.lowes.service.AidService;

@Service
@Transactional
public class AidServiceImpl implements AidService{
	
	public AidServiceImpl(){
		System.out.println("AidServiceImpl()");
	}
	
	@Autowired
	private AidDAO aidDAO;

	@Override
	public Integer createAid(Aid aid) {
		return aidDAO.createAid(aid);
	}

	@Override
	public Aid updateAid(Aid aid) {
		return aidDAO.updateAid(aid);
	}

	@Override
	public void deleteAid(Integer id) {
		aidDAO.deleteAid(id);
	}

	@Override
	public List<Aid> getAllAid() {
		return aidDAO.getAllAid();
	}

	@Override
	public Aid getAid(Integer id) {
		return aidDAO.getAid(id);
	}

}