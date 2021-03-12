package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TipoCriterioDAO;
import com.lowes.entity.TipoCriterio;
import com.lowes.service.TipoCriterioService;


@Service
@Transactional
public class TipoCriterioServiceImpl implements TipoCriterioService {
	
	@Autowired
	private TipoCriterioDAO tipoCriterioDAO;

	@Override
	public List<TipoCriterio> getAllTipoCriterio() {
		return tipoCriterioDAO.getAllTipoCriterio();
	}

	@Override
	public TipoCriterio getTipoCriterio(Integer idTipoCriterio) {
		return tipoCriterioDAO.getTipoCriterio(idTipoCriterio);
	}
}