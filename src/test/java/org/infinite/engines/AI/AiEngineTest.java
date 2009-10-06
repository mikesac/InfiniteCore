package org.infinite.engines.AI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.infinite.objects.Monster;
import org.infinite.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;

public class AiEngineTest {

	private AiEngine engine;
	
	@Before
	public void setUp() throws Exception {
		XmlBeanFactory factory = TestUtil.getBeanFactory();
		engine= (AiEngine) factory.getBean("AiEngine");
	}
		
	@Test
	public void testGetLevelPx() {
		
		for (int i = 0; i < 50; i++) {
			System.out.println(i + " - "+ AiEngine.getLevelPx(i));
		}
	}
	
	@Test
	public void testGetLevelByPx() {
		
		int level = AiEngine.getLevelByPx(0);
		for (int i = 0; i < 50000; i+=50) {
			int tmp = AiEngine.getLevelByPx(i);
			System.out.println(i + " - "+ tmp);
			if(level!=tmp){				
				level=tmp;
			}
		}
	}

	@Test
	public void testGetMatchingLevel() {
		
		int level = 15;
		System.out.println(level + " vs ");
		for (int i = 0; i < 1000; i++) {
			int out = engine.getMatchingLevel(level);
			System.out.println(out + " " + ((out!=0)?Math.round(level/out):0) );
		}
		
		System.out.print("END");
			
	}

	@Test
	public void testSpawning() {
	
		try {
			
			String name = "Goblin";
			
			for (int i = 0; i < 100; i++) {
				
			
			Monster m = engine.spawn(name);
			
			assertNotNull(m);
			assertEquals(name, m.getName());
			
			System.out.print( m.getHandRight()!=null?m.getHandRight().getName():"none" + "\t");
			System.out.print( m.getHandLeft()!=null?m.getHandLeft().getName():"none" + "\t" );
			System.out.println( m.getBody()!=null?m.getBody().getName():"none");
		}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	
}
