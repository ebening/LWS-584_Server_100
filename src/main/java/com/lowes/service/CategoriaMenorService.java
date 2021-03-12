/**
 * 
 */
package com.lowes.service;

import java.util.List;

import com.lowes.entity.CategoriaMenor;

/**
 * @author miguelrg
 * @version 1.0
 */
public interface CategoriaMenorService {
	public Integer createCategoriaMenor(CategoriaMenor categoriaMenor);

	public CategoriaMenor updateCategoriaMenor(CategoriaMenor categoriaMenor);

	public void deleteCategoriaMenor(int id);

	public List<CategoriaMenor> getAllCategoriaMenor();

	public CategoriaMenor getCategoriaMenor(int id);
}
