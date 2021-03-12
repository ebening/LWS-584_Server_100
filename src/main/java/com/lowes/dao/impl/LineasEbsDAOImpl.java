package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.lowes.dao.LineasEbsDAO;
import com.lowes.entity.LineasEbs;
import com.lowes.util.HibernateUtil;

@Repository
public class LineasEbsDAOImpl implements LineasEbsDAO {
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createLineasEbs(LineasEbs lineasEbs) {
		return (Integer) hibernateUtil.create(lineasEbs);
	}

	@Override
	public LineasEbs updateLineasEbs(LineasEbs lineasEbs) {
		return hibernateUtil.update(lineasEbs);
	}

	@Override
	public void deleteLineasEbs(Integer id) {
		hibernateUtil.delete(new LineasEbs(id));		
	}

	@Override
	public List<LineasEbs> getAllLineasEbs() {
		return hibernateUtil.fetchAll(LineasEbs.class);
	}

	@Override
	public LineasEbs getLineasEbs(Integer id) {
		return hibernateUtil.fetchById(id, LineasEbs.class);
	}

}
