package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.AidDAO;
import com.lowes.entity.Aid;
import com.lowes.util.HibernateUtil;

@Repository
public class AidDAOImpl implements AidDAO{

	@Autowired
	private HibernateUtil hibernateUtil;
	
	public AidDAOImpl(){
		System.out.println("AidDAOImpl()");
	}

	@Override
	public Integer createAid(Aid aid) {
		return (Integer) hibernateUtil.create(aid);
	}

	@Override
	public Aid updateAid(Aid aid) {
		return hibernateUtil.update(aid);
	}

	@Override
	public void deleteAid(Integer id) {
		Aid aid= getAid(id);
		hibernateUtil.delete(aid);	
	}

	@Override
	public List<Aid> getAllAid() {
		return hibernateUtil.fetchAll(Aid.class);
	}

	@Override
	public Aid getAid(Integer id) {
		return hibernateUtil.fetchById(id, Aid.class);
	}
}