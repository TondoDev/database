package org.tondo.hibernate.test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

public class HbBootstrapTest {

	
	@Test
	public void testCreateSessionFactory() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("h2sample");
		
	}
}
