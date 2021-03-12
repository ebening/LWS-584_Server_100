package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TipoCriterioDAO;
import com.lowes.entity.TipoCriterio;
import com.lowes.util.HibernateUtil;

@Repository
public class TipoCriterioDAOImpl implements TipoCriterioDAO {
	
	public TipoCriterioDAOImpl(){
		System.out.println("TipoCriterioDAOImpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public List<TipoCriterio> getAllTipoCriterio() {
		return hibernateUtil.fetchAll(TipoCriterio.class);
	}

	@Override
	public TipoCriterio getTipoCriterio(Integer idTipoCriterio) {
		return hibernateUtil.fetchById(idTipoCriterio, TipoCriterio.class);
	}

}
