package com.lowes.dao;

import java.util.List;
import com.lowes.entity.TipoSolicitud;

public interface TipoSolicitudDAO {
	
	public Integer createTipoSolicitud(TipoSolicitud tipoSolicitud);
    public TipoSolicitud updateTipoSolicitud(TipoSolicitud tipoSolicitud);
    public void deleteTipoSolicitud(Integer id);
    public List<TipoSolicitud> getAllTipoSolicitud();
    public TipoSolicitud getTipoSolicitud(Integer id);
	List<TipoSolicitud> getAllTipoSolicitudOrder(String propiedad);

}