/**
 * 
 */
package com.lowes.service;

import java.util.List;

import com.lowes.entity.TipoLocacion;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface TipoLocacionService {
	public Integer createTipoLocacion(TipoLocacion tipoLocacion);

	public TipoLocacion updateTipoLocacion(TipoLocacion tipoLocacion);

	public void deleteTipoLocacion(int id);

	public List<TipoLocacion> getAllTipoLocaciones();

	public TipoLocacion getTipoLocacion(int id);
}
