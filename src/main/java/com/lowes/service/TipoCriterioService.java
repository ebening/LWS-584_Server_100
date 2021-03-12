package com.lowes.service;

import java.util.List;

import com.lowes.entity.TipoCriterio;

public interface TipoCriterioService {
	
    public List<TipoCriterio> getAllTipoCriterio();
    public TipoCriterio getTipoCriterio(Integer idTipoCriterio);

}
