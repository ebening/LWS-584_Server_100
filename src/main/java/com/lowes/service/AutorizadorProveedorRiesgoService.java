package com.lowes.service;

import java.util.List;

import com.lowes.entity.AutorizadorProveedorRiesgo;

public interface AutorizadorProveedorRiesgoService {
	public Integer createAutorizadorProveedorRiesgo(AutorizadorProveedorRiesgo autorizadorProveedorRiesgo);
    public AutorizadorProveedorRiesgo updateAutorizadorProveedorRiesgo(AutorizadorProveedorRiesgo autorizadorProveedorRiesgo);
    public void deleteAutorizadorProveedorRiesgo(Integer idAutorizadorProveedorRiesgo);
    public List<AutorizadorProveedorRiesgo> getAllAutorizadorProveedorRiesgo();
    public AutorizadorProveedorRiesgo getAutorizadorProveedorRiesgo(Integer idAutorizadorProveedorRiesgo);
    public List<AutorizadorProveedorRiesgo> getAutorizadorProveedorRiesgoByProveedor(Integer idProveedor);

}