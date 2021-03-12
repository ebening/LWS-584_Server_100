package com.lowes.service;

import java.util.List;

import com.lowes.entity.TipoProveedor;

public interface TipoProveedorService {

	public Integer createTipoProveedor(TipoProveedor tipoProveedor);
    public TipoProveedor updateTipoProveedor(TipoProveedor tipoProveedor);
    public void deleteTipoProveedor(Integer id);
    public List<TipoProveedor> getAllTipoProveedor();
    public TipoProveedor getTipoProveedor(Integer id);
    
}