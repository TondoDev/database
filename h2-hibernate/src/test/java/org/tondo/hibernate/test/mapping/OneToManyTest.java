package org.tondo.hibernate.test.mapping;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.RollbackException;

import org.junit.Test;
import org.tondo.hibernate.mappings.otm.bidi.OneToManyMaster;
import org.tondo.hibernate.mappings.otm.bidi.OneToManySlave;
import org.tondo.hibernate.test.HibernateTestBase;

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
			// TODO not true, can be saved without master. why?
			manager.getTransaction().commit();
			fail("RollbackException because of foreign key is null");
		} catch(RollbackException e) {}
	}
	
	@Test
	public void testSaveTogether() {
		OneToManyMaster master = new OneToManyMaster();
		master.setMasterProp("Master master");

		OneToManySlave sl = new OneToManySlave(10);
		sl.setMaster(master);
		
		Set<OneToManySlave> slaves = new HashSet<>();
		slaves.add(sl);
		master.setItems(slaves);
		
		manager.getTransaction().begin();
		manager.persist(master);
		manager.persist(sl);
		manager.getTransaction().commit();
		
		assertNotNull(master.getId());
		recreateManager();
		
		OneToManyMaster foundMaster = manager.find(OneToManyMaster.class, master.getId());
		System.out.println("--");
		assertEquals(1, foundMaster.getItems().size());
		
	}

}
