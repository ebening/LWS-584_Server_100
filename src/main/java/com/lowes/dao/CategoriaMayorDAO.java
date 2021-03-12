package com.lowes.dao;

import java.util.List;

import com.lowes.entity.CategoriaMayor;

public interface CategoriaMayorDAO {
	
	public Integer createCategoriaMayor(CategoriaMayor categoriaMayor);
    public CategoriaMayor updateCategoriaMayor(CategoriaMayor categoriaMayor);
    public void deleteCategoriaMayor(Integer id);
    public List<CategoriaMayor> getAllCategoriaMayor();
    public CategoriaMayor getCategoriaMayor(Integer id);

}