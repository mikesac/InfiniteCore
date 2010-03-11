package org.infinite.db.dao;

import static org.junit.Assert.assertNotNull;

import org.infinite.db.dto.Npc;
import org.infinite.db.dto.Quest;
import org.infinite.util.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;


public class NpcTest {

	@Test
	public void createTest() {
	
		XmlBeanFactory f = TestUtil.getBeanFactory();
		DaoManager manager = (DaoManager) f.getBean("DaoManager");
				
		Npc npc = manager.getNpcById(1);
		assertNotNull(npc);
		
		int qid = npc.getQuest();

		Quest q = manager.getQuestById(qid);
		assertNotNull(q);
		
		
	}
}
