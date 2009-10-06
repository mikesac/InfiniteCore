package org.infinite.db.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.infinite.db.dto.AreaItem;
import org.infinite.util.TestUtil;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;

public class AreaItemTest {

	String aiName = "NewAreaItem"; 	
	String aiIcon = "temp"; 	
	int aiCost = 0; 	
	int aiAreaX = 0; 	
	int aiAreaY = 0; 	
	int aiX = 0; 	
	int aiY = 0; 	
	String aiLock = ""; 	
	int aiType = 0; 	
	String aiQLock = ""; 	
	String aiUrl = ""; 	
	boolean aiDirect = false; 	
	boolean aiLoop = false; 	
	boolean aiHide = false; 	
	int aiLevel = 0; 	
	String aiNpcs = "";
	
	int iAreaID = 9999;
	
	@Test
	public void createTest() {
		
		AreaItem ai = new AreaItem(aiName, aiIcon, aiCost, iAreaID,
				 (short)aiAreaX, (short)aiAreaY, aiX, aiY, aiLock,aiType,aiQLock, aiUrl,  
				 aiLoop, aiHide, aiLevel, aiNpcs);
		
		assertNotNull(ai);
		
		XmlBeanFactory f = TestUtil.getBeanFactory();
		DaoManager manager = (DaoManager) f.getBean("DaoManager");
		
		manager.create(ai);
		
		AreaItem testAi = manager.getAreaItems(iAreaID).get(0);
		
		assertEquals(ai.getName() , testAi.getName());
		assertEquals(ai.getIcon() , testAi.getIcon());
		assertEquals(ai.getCost() , testAi.getCost());
		assertEquals(ai.getAreaid() , testAi.getAreaid());
		assertEquals(ai.getAreax() , testAi.getAreax());
		assertEquals(ai.getAreay() , testAi.getAreay());
		assertEquals(ai.getX() , testAi.getX());
		assertEquals(ai.getY() , testAi.getY());
		assertEquals(ai.getArealock() , testAi.getArealock());
		assertEquals(ai.getAreatype() , testAi.getAreatype());
		assertEquals(ai.getQuestlock() , testAi.getQuestlock());
		assertEquals(ai.getUrl() , testAi.getUrl());
		assertEquals(ai.isDoublestep() , testAi.isDoublestep());
		assertEquals(ai.isHidemode() , testAi.isHidemode());
		assertEquals(ai.getLevel() , testAi.getLevel());
		assertEquals(ai.getNpcs() , testAi.getNpcs());
		
		
		manager.delete(testAi);
		
		
		
	}
	
	
	
}
