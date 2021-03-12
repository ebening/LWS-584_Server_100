package com.lowes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowes.dao.TestHibernateDAO;
import com.lowes.entity.TestHibernate;
import com.lowes.service.TestHibernateService;


@Service
@Transactional
public class TestHibernateServiceImpl implements TestHibernateService {

	
	public TestHibernateServiceImpl(){
		
	}
	
	@Autowired
	private TestHibernateDAO testHibernateDAO;
	
	@Override
	public Integer createTestHibernate(TestHibernate testHibernate) {
		return testHibernateDAO.createTestHibernate(testHibernate);
	}

	@Override
	public TestHibernate updateTestHibernate(TestHibernate testHibernate) {
		return testHibernateDAO.updateTestHibernate(testHibernate);
	}

	@Override
	public void deleteTestHibernate(Integer id) {
		testHibernateDAO.deleteTestHibernate(id);
	}

	@Override
	public List<TestHibernate> getAllTestHibernate() {
		return testHibernateDAO.getAllTestHibernate();
	}

	@Override
	public TestHibernate getTestHibernate(Integer id) {
		return testHibernateDAO.getTestHibernate(id);
	}

}
