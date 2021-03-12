package com.lowes.dao;

import java.util.List;

import com.lowes.entity.LineasEbs;


public interface LineasEbsDAO {
	
	public Integer createLineasEbs(LineasEbs lineasEbs);
    public LineasEbs updateLineasEbs(LineasEbs lineasEbs);
    public void deleteLineasEbs(Integer id);
    public List<LineasEbs> getAllLineasEbs();
    public LineasEbs getLineasEbs(Integer id);

}
