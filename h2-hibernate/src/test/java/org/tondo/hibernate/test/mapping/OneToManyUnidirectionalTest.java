package org.tondo.hibernate.test.mapping;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.tondo.hibernate.mappings.otm.uni.UniSource;
import org.tondo.hibernate.mappings.otm.uni.UniTarget;
import org.tondo.hibernate.test.HibernateTestBase;

/**
 * 
 * @author TondoDev
 *
 */
public class OneToManyUnidirectionalTest  extends HibernateTestBase {

	
	@Test
	public void testCreate() {
		Set<UniTarget> items = new HashSet<>();
		UniTarget t = new UniTarget();
		t.setData("Slnko");
		t.setId(555L);
		items.add(t);
		
		UniSource src = new UniSource();
		src.setId(666L);
		src.setTargets(items);
		
		manager.getTransaction().begin();
		manager.persist(t);
		manager.persist(src);
		manager.getTransaction().commit();
		
		assertNotNull("Id should be assigned after object is persisted", src.getId());
		recreateManager();
		
		UniTarget foundTar = manager.find(UniTarget.class, 555L);
		assertNotNull(foundTar);
		
		UniSource foundSrc = manager.find(UniSource.class, 666L);
		assertNotNull(foundSrc);
		System.out.println("--- before get items");
		assertTrue(foundSrc.getTargets().size() > 0);
	}
}
