package com.lowes.service;

import java.util.List;
import com.lowes.entity.TipoSolicitudCriterio;

public interface TipoSolicitudCriterioService {
	
	public Integer createTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio);
    public TipoSolicitudCriterio updateTipoSolicitudCriterio(TipoSolicitudCriterio tipoSolicitudCriterio);
    public void deleteTipoSolicitudCriterio(Integer idTipoSolicitudCriterio);
    public List<TipoSolicitudCriterio> getAllTipoSolicitudCriterio();
    public TipoSolicitudCriterio getTipoSolicitudCriterio(Integer idTipoSolicitudCriterio);
    public List<TipoSolicitudCriterio> getTipoSolicitudCriteriosByTipoSolicitud(Integer idTipoSolicitud);
    public TipoSolicitudCriterio getNextTipoSolicitudCriterio(Integer idTipoSolicitud, Integer orden);

}
