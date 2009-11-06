package org.infinite.engines.dialog;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;


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
			ArrayList<Dialog> dialogs = engine.getDialogData("dialog.json");
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
