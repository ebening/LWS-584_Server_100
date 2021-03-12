/**
 * 
 */
package com.lowes.dao;

import java.util.List;

import com.lowes.entity.Puesto;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface PuestoDAO {
	public Integer createPuesto(Puesto puesto);

	public Puesto updatePuesto(Puesto puesto);

	public void deletePuesto(int id);

	public List<Puesto> getAllPuestos();

	public Puesto getPuesto(int id);
}
