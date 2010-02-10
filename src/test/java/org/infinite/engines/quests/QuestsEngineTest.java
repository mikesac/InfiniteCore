package org.infinite.engines.quests;

import static org.junit.Assert.*;

import org.infinite.db.dao.DaoManager;
import org.infinite.db.dto.Item;
import org.infinite.db.dto.Quest;
import org.infinite.engines.items.ItemsEngine;
import org.infinite.engines.magic.MagicEngine;
import org.infinite.objects.Character;
import org.infinite.util.TestUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;

public class QuestsEngineTest {

	private static Character c = null;
	private static QuestsEngine questsEngine = null;
	private static DaoManager daoManager = null;

	private static int questID = -1;
	
	@BeforeClass
	public static void setUp() throws Exception {
		XmlBeanFactory f = TestUtil.getBeanFactory();

		daoManager = (DaoManager) f.getBean("DaoManager");
		MagicEngine magicEngine = (MagicEngine) f.getBean("MagicEngine");
		ItemsEngine itemsEngine = (ItemsEngine) f.getBean("ItemsEngine");
		questsEngine = (QuestsEngine) f.getBean("QuestsEngine");
		c = new Character("testChar", "test1", daoManager, itemsEngine,
				magicEngine);
	}

	@Test
	public void testBeans() throws Exception {
		assertNotNull(c);
		assertNotNull(questsEngine);
		assertNotNull(daoManager);
	}

	@Test
	public void testDtoCreation() throws Exception {

		
		//create a temporary test quest
		String[] itm = new String[]{"Dagger"};
		
		Item it = daoManager.getItemsByName( itm ).get(0);
		assertNotNull(it);

		Quest q = new Quest(it.getId(), it.getId(), "testQuest", "d1", "d2", "d3", 0, 0, false, 10f, 10);
		assertNotNull(q);

		questID = daoManager.create(q);
		q = daoManager.getQuestById(questID);
		assertNotNull(q);
		
		//remove from player's quests to be sure it's clean
		c.removeFromQuest(q.getId());
		
		//test assignment
		questsEngine.setQuestAssigned(c, q);		
		assertEquals(true, questsEngine.isQuestAssigned(c, q.getId()));
		
		//test executed
		questsEngine.setQuestExecuted(c, q.getId());		
		assertEquals(true, questsEngine.isQuestAssigned(c, q.getId()));
		assertTrue( QuestsEngine.QUEST_STATUS_EXECUTED == c.getQuestByQuestId(q.getId()).getStatus() );
		
		//test completed
		questsEngine.setQuestCompleted(c, q.getId());		
		assertEquals(true, questsEngine.isQuestAssigned(c, q.getId()));
		assertTrue( QuestsEngine.QUEST_STATUS_COMPLETED == c.getQuestByQuestId(q.getId()).getStatus() );
		
		//test aborting
		questsEngine.setQuestAborted(c, q.getId());
		assertEquals(false, questsEngine.isQuestAssigned(c, q.getId()));
		
		
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		
		//cleanup of quests
		if(questID!=-1){
			daoManager.delete(daoManager.getQuestById(questID));
		}
	}
}
