package com.lowes.dao;

import java.util.List;

import com.lowes.entity.TipoCriterio;

public interface TipoCriterioDAO {
    public List<TipoCriterio> getAllTipoCriterio();
    public TipoCriterio getTipoCriterio(Integer idTipoCriterio);
}
