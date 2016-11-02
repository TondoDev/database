package org.tondo.hibernate.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tondo.hibernate.domain.Person;

public class HibernateBasicTest {

	private static EntityManagerFactory factory;
	private EntityManager manager;
	
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
	
	@Test
	public void testPersist() {
		this.manager.getTransaction().begin();
		this.manager.persist(Person.create("Petrik", 250, 25.87, new Date()));
		
		// hibernate will insert data to DB when commit is called
		System.out.println("before Comm");
		this.manager.getTransaction().commit();
		System.out.println("After Comm");
	}
}
