package org.tondo.hibernate.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.RollbackException;

import org.junit.Test;
import org.tondo.hibernate.mappings.OneToManyMaster;
import org.tondo.hibernate.mappings.OneToManySlave;

public class OneToManyTest extends HibernateTestBase {
	
	@Test
	public void testSaveEmptyMaster() {
		OneToManyMaster master = new OneToManyMaster();
		master.setMasterProp("Master master");
		manager.getTransaction().begin();
		manager.persist(master);
		manager.getTransaction().commit();
		
		assertNull(master.getItems());
		assertNotNull(master.getId());
		
		recreateManager();
		
		OneToManyMaster foundMaster = manager.find(OneToManyMaster.class, master.getId());
		assertNotSame(foundMaster, master);
		assertNotNull(foundMaster.getItems());
		assertTrue(foundMaster.getItems().isEmpty());
	}
	
	@Test
	public void testSaveSlaveOnly() {
		OneToManySlave slave = new OneToManySlave(10);
		manager.getTransaction().begin();
		manager.persist(slave);
		try {
			manager.getTransaction().commit();
			fail("RollbackException because of foreign key is null");
		} catch(RollbackException e) {}
	}
	
	@Test
	public void testSaveTogether() {
		OneToManyMaster master = new OneToManyMaster();
		master.setMasterProp("Master master");
		Set<OneToManySlave> slaves = new HashSet<>();
		OneToManySlave sl = new OneToManySlave(10);
		slaves.add(sl);
		
		
		manager.getTransaction().begin();
		manager.persist(master);
		master.setItems(slaves);
		manager.persist(sl);
	
		// we need to save all parts
		
		manager.getTransaction().commit();
	}

}
