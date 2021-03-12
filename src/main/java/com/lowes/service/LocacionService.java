/**
 * 
 */
package com.lowes.service;

import java.util.List;

import com.lowes.entity.Locacion;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface LocacionService {
	public Integer createLocacion(Locacion locacion);

	public Locacion updateLocacion(Locacion locacion);

	public void deleteLocacion(Integer id);

	public List<Locacion> getAllLocaciones();

	public Locacion getLocacion(Integer id);
}
