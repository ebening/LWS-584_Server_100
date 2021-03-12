/**
 * 
 */
package com.lowes.service;

import java.util.List;

import com.lowes.entity.Proveedor;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface ProveedorService {
	public Integer createProveedor(Proveedor proveedor);

	public Proveedor updateProveedor(Proveedor proveedor);

	public void deleteProveedor(int id);

	public List<Proveedor> getAllProveedores();

	public Proveedor getProveedor(int id);
	
	public List<Proveedor> getAllProveedoresByProveedorRiesgo();
	
	public List<Proveedor> getProveedoresByTipo(int tipo);
	
	public List<Proveedor> getProveedorByNumero(Integer numeroProveedor);
}