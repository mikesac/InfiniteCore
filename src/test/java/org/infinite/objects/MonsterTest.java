package org.infinite.objects;

import static org.junit.Assert.assertNotNull;

import org.infinite.db.dao.DaoManager;
import org.infinite.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;

public class MonsterTest {

	private static DaoManager daoManager;
	
	@BeforeClass
	public static void setUp() throws Exception {
		XmlBeanFactory f = TestUtil.getBeanFactory();		
		daoManager 	= (DaoManager) f.getBean("DaoManager");
	}
	
	
	@Test
	public void testMonster() {
		
		Monster m = new Monster("Goblin",daoManager);
		assertNotNull("Monster is null", m);
			
		System.out.println("m.getArmorCA()"+m.getArmorCA());
		System.out.println("m.getBaseCA()"+m.getBaseCA());
		System.out.println("m.getInitiative()"+m.getInitiative(0));
		System.out.println("m.getShieldCA()"+m.getShieldCA());
		System.out.println("m.getTotalCA()"+m.getTotalCA());
		
	}

}
