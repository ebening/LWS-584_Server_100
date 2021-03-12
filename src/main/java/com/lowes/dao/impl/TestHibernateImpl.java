package com.lowes.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowes.dao.TestHibernateDAO;
import com.lowes.entity.TestHibernate;
import com.lowes.util.HibernateUtil;

@Repository
public class TestHibernateImpl implements TestHibernateDAO {

	
	public TestHibernateImpl(){
		System.out.println("TestHibernateimpl");
	}
	
	@Autowired
    private HibernateUtil hibernateUtil;
	
	@Override
	public Integer createTestHibernate(TestHibernate testHibernate) {
		return (Integer) hibernateUtil.create(testHibernate);
	}

	@Override
	public TestHibernate updateTestHibernate(TestHibernate testHibernate) {
		return hibernateUtil.update(testHibernate);
	}

	@Override
	public void deleteTestHibernate(Integer id) {
		TestHibernate test = new TestHibernate();
		test.setId(id);
		hibernateUtil.delete(test);
	}

	@Override
	public List<TestHibernate> getAllTestHibernate() {
		return hibernateUtil.fetchAll(TestHibernate.class);
	}

	@Override
	public TestHibernate getTestHibernate(Integer id) {
		return hibernateUtil.fetchById(id, TestHibernate.class);
	}

	
	
	
}
