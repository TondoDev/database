package org.tondo.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.tondo.hibernate.domain.Person;


public class Bootstrap {

	public static void main(String... args) {
		EntityManagerFactory factory = null;
		try {
			factory = Persistence.createEntityManagerFactory("h2sample");
			// create records
			EntityManager manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.persist(Person.create("Peter", 180, 100.85, new Date()));
			manager.persist(Person.create("Jana", 165, 800.85, new Date()));
			manager.getTransaction().commit();
			manager.close();
			
			// get records
			manager = factory.createEntityManager();
			List<Person> resultList = manager.createQuery("from Person", Person.class)
				.getResultList();
			for (Person p : resultList) {
				System.out.println("Id: " + p.getId() + " Name: " + p.getName() + " Salary: " + p.getSalary());
			}
			manager.close();
			
			// get single record by identity
			manager = factory.createEntityManager();
			Person foundPers = manager.find(Person.class, 2L);
			System.out.println("Found: "  + foundPers.getName());
			manager.close();
			
			// update found person
			manager = factory.createEntityManager();
			manager.getTransaction().begin();
			foundPers.setSalary(555.55);
			manager.merge(foundPers);
			manager.getTransaction().commit();
			manager.close();
			
			
			// get updated 
			manager = factory.createEntityManager();
			Person updated = manager.find(Person.class, 2L);
			System.out.println("Id: " + updated.getId() + " Name: " + updated.getName() + " Salary: " + updated.getSalary());
			
			manager.close();
			
			
		} finally {
			if (factory != null) {
				factory.close();	
			}
		}
	}
}
