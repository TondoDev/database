package org.tondo.hibernate.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class HibernateTestBase {
	protected static EntityManagerFactory factory;
	protected EntityManager manager;
	
	@BeforeClass
	public static void initEntityManagerFactory() {
		// loads all @Entity classes
		factory = Persistence.createEntityManagerFactory("h2sample");
	}
	
	@AfterClass
	public static void destroyEntityManagerFactory() {
		if(factory != null) {
			factory.close();
		}
	}
	
	@Before
	public void createManager() {
		this.manager = factory.createEntityManager();
	}
	
	@After
	public void closeManager() {
		if (this.manager != null) {
			this.manager.close();
		}
	}
}
