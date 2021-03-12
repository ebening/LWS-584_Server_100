package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.LineasEbsDAO;
import com.lowes.entity.LineasEbs;
import com.lowes.service.LineasEbsService;

@Service
@Transactional
public class LineasEbsServiceImpl implements LineasEbsService{
	
	@Autowired
	private LineasEbsDAO lineasEbsDAO;

	@Override
	public Integer createLineasEbs(LineasEbs lineasEbs) {
		return lineasEbsDAO.createLineasEbs(lineasEbs);
	}

	@Override
	public LineasEbs updateLineasEbs(LineasEbs lineasEbs) {
		return lineasEbsDAO.updateLineasEbs(lineasEbs);
	}

	@Override
	public void deleteLineasEbs(Integer id) {
		lineasEbsDAO.deleteLineasEbs(id);
	}

	@Override
	public List<LineasEbs> getAllLineasEbs() {
		return lineasEbsDAO.getAllLineasEbs();
	}

	@Override
	public LineasEbs getLineasEbs(Integer id) {
		return lineasEbsDAO.getLineasEbs(id);
	}

}
