package com.lowes.service;

import java.util.List;
import com.lowes.entity.TipoSolicitud;

public interface TipoSolicitudService {
	
	public Integer createTipoSolicitud(TipoSolicitud tipoSolicitud);
    public TipoSolicitud updateTipoSolicitud(TipoSolicitud tipoSolicitud);
    public void deleteTipoSolicitud(Integer idTipoSolicitud);
    public List<TipoSolicitud> getAllTipoSolicitud();
    public TipoSolicitud getTipoSolicitud(Integer idTipoSolicitud);
	List<TipoSolicitud> getAllTipoSolicitudOrder(String propiedad);

}