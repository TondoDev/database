package org.tondo.hibernate.test;

import java.util.Date;

import org.junit.Test;
import org.tondo.hibernate.domain.Person;

public class HibernateBasicTest extends HibernateTestBase {
	
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
