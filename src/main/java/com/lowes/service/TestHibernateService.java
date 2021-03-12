package com.lowes.service;

import java.util.List;

import com.lowes.entity.TestHibernate;

public interface TestHibernateService {
	public Integer createTestHibernate(TestHibernate testHibernate);
    public TestHibernate updateTestHibernate(TestHibernate testHibernate);
    public void deleteTestHibernate(Integer id);
    public List<TestHibernate> getAllTestHibernate();
    public TestHibernate getTestHibernate(Integer id);	
}
