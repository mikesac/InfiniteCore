package org.infinite.objects;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.infinite.db.dao.DaoManager;
import org.infinite.db.dto.PlayerKnowSpell;
import org.infinite.db.dto.PlayerOwnItem;
import org.infinite.engines.items.ItemsEngine;
import org.infinite.engines.magic.MagicEngine;
import org.infinite.util.TestUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;


public class CharacterTest {

	private static Character c=null;
	
	@BeforeClass
	public static void setUp() throws Exception {
		XmlBeanFactory f = TestUtil.getBeanFactory();		
		
		DaoManager dmanager 	= (DaoManager) f.getBean("DaoManager");
		MagicEngine magicEngine = (MagicEngine) f.getBean("MagicEngine");
		ItemsEngine itemsEngine = (ItemsEngine) f.getBean("ItemsEngine");
		
		c = new Character("testChar","mike",dmanager,itemsEngine,magicEngine);
	}
	
	
	
	@Test
	public void testChar() throws Exception{				
		assertNotNull(c);
	}
	
	
	@Test
	public void testBattlePlanSerialization() throws Exception{
	
		int weaponId = c.getHandRightPoi().getId();
		int spellId = c.getSpellBookFight().get(0).getId();
		
		
		String inputA = "A"+weaponId;
		String inputS = "S"+spellId+",s"+spellId+",s"+spellId+"";
		String input = inputA + "," + inputS;
		
		//test Deserialize
		ArrayList<Object> deserializeBattlePlan = c.deserializeBattlePlan(input);
		
		assertEquals(input.split(",").length , deserializeBattlePlan.size());
		assertNotNull(deserializeBattlePlan.get(0));
		assertNotNull(deserializeBattlePlan.get(1));
		assertTrue(weaponId==((PlayerOwnItem)deserializeBattlePlan.get(0)).getId() );
		assertTrue(spellId == ((PlayerKnowSpell)deserializeBattlePlan.get(1)).getId() );
		
		//test Serialize
		String out = c.serializeBattlePlan( deserializeBattlePlan );
		assertTrue(input.equalsIgnoreCase(out) );
		
		//testRemoveSerialize
		c.setBattlePlan( deserializeBattlePlan );
		c.changeFromBattlePlan("A"+weaponId,"A5");
		
		out = c.serializeBattlePlan( c.getBattlePlan() );
		assertTrue(out.equalsIgnoreCase(inputS) );
		
		
	}
	
}
