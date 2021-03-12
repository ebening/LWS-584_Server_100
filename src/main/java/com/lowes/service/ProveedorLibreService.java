/**
 * 
 */
package com.lowes.service;

import java.util.List;

import com.lowes.entity.ProveedorLibre;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface ProveedorLibreService {
	public Integer createProveedorLibre(ProveedorLibre proveedorLibre);

	public ProveedorLibre updateProveedorLibre(ProveedorLibre proveedorLibre);

	public void deleteProveedorLibre(int idProveedorLibre);

	public List<ProveedorLibre> getAllProveedorLibre();

	public ProveedorLibre getProveedorLibre(int idProveedorLibre);

	public ProveedorLibre existeProveedor(String rfc);

}