package com.lowes.service;

import java.util.List;

import com.lowes.entity.LineasEbs;

public interface LineasEbsService {
	public Integer createLineasEbs(LineasEbs lineasEbs);
    public LineasEbs updateLineasEbs(LineasEbs lineasEbs);
    public void deleteLineasEbs(Integer id);
    public List<LineasEbs> getAllLineasEbs();
    public LineasEbs getLineasEbs(Integer id);

}
