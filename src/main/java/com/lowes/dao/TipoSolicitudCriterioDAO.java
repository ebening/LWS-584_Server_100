package com.lowes.dao;

import java.util.List;

import com.lowes.entity.TipoSolicitudCriterio;

public interface TipoSolicitudCriterioDAO {
	
	public Integer createTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio);
    public TipoSolicitudCriterio updateTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio);
    public void deleteTipoSolicitudCriterio(Integer id);
    public List<TipoSolicitudCriterio> getAllTipoSolicitudCriterio();
    public TipoSolicitudCriterio getTipoSolicitudCriterio(Integer idTipoSolicitudCriterio);
    public List<TipoSolicitudCriterio> getTipoSolicitudCriteriosByTipoSolicitud(Integer idTipoSolicitud);
    public TipoSolicitudCriterio getNextTipoSolicitudCriterio(Integer idTipoSolicitud, Integer orden);
}
