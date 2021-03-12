package com.lowes.dao;

import java.util.List;

import com.lowes.entity.TipoProveedor;

public interface TipoProveedorDAO {
	
	public Integer createTipoProveedor(TipoProveedor tipoProveedor);
    public TipoProveedor updateTipoProveedor(TipoProveedor tipoProveedor);
    public void deleteTipoProveedor(Integer id);
    public List<TipoProveedor> getAllTipoProveedor();
    public TipoProveedor getTipoProveedor(Integer id);

}