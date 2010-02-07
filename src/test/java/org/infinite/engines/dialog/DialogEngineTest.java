package org.infinite.engines.dialog;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.infinite.engines.dialog.dto.Dialog;
import org.infinite.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;


public class DialogEngineTest {

	DialogEngine engine;
	
	@Before
	public void setUp() throws Exception {
		XmlBeanFactory factory = TestUtil.getBeanFactory();
		engine= (DialogEngine) factory.getBean("DialogEngine");
	}
	
	@Test
	public void testDialog(){
		
		try {
			
			//FileInputStream ifs = new FileInputStream("/var/www/infinite/data/dialogs/npc/merch1.json");
//			ArrayList<Dialog> dialogs = engine.getDialogData(ifs);
			
			List<Dialog> dialogs = engine.getDialogData( this.getClass().getResourceAsStream("dialog.json") );
			
			assertNotNull(dialogs);
			
			Dialog d = engine.selectDialog(0, dialogs);
			assertNotNull(d);
			
			System.out.println( d.getSentence() );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
