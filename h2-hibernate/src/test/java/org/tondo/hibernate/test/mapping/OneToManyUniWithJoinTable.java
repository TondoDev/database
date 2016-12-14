package org.tondo.hibernate.test.mapping;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.tondo.hibernate.mappings.otm.uni.UniSourceJoinT;
import org.tondo.hibernate.mappings.otm.uni.UniTargetJoinT;
import org.tondo.hibernate.test.HibernateTestBase;

/**
 * 
 * @author TondoDev
 *
 */
public class OneToManyUniWithJoinTable extends HibernateTestBase {
	
	@Test
	public void testCreate() {
		Set<UniTargetJoinT>	items = new HashSet<>();
		UniTargetJoinT t = new UniTargetJoinT();
		t.setId(5000L);
		items.add(t);
		
		UniTargetJoinT aa = new UniTargetJoinT();
		aa.setId(6000L);
		items.add(aa);
		
		final long SOURCE_PK = 200L;
		UniSourceJoinT src = new UniSourceJoinT();
		src.setId(SOURCE_PK);
		src.setTargets(items);
		
		manager.getTransaction().begin();
		// both items must be persisted
		manager.persist(t);
		manager.persist(aa);
		manager.persist(src);
		manager.getTransaction().commit();
		
		assertNotNull("Id should be assigned after object is persisted", src.getId());
		recreateManager();
		
		
		UniSourceJoinT found = manager.find(UniSourceJoinT.class, SOURCE_PK);
		assertNotNull(found);
		assertEquals(2,  found.getTargets().size());
	}

}
