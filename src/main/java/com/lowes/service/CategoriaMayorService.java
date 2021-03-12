package com.lowes.service;

import java.util.List;

import com.lowes.entity.CategoriaMayor;

public interface CategoriaMayorService {
	
	public Integer createCategoriaMayor(CategoriaMayor categoriaMayor);
    public CategoriaMayor updateCategoriaMayor(CategoriaMayor categoriaMayor);
    public void deleteCategoriaMayor(Integer id);
    public List<CategoriaMayor> getAllCategoriaMayor();
    public CategoriaMayor getCategoriaMayor(Integer id);

}