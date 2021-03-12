package com.lowes.dao;

import java.util.List;

import com.lowes.entity.TipoFactura;

public interface TipoFacturaDAO {
	
	public Integer createTipoFactura(TipoFactura tipoFactura);
    public TipoFactura updateTipoFactura(TipoFactura tipoFactura);
    public void deleteTipoFactura(Integer id);
    public List<TipoFactura> getAllTipoFactura();
    public TipoFactura getTipoFactura(Integer id);	

}