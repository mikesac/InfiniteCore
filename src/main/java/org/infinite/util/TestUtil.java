package org.infinite.util;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public final class TestUtil {

	private TestUtil() {}
	
	public static XmlBeanFactory getBeanFactory(){
		String file = TestUtil.class.getClassLoader().getResource("applicationContext.xml").getFile();
		Resource res = new FileSystemResource( file );
		return new XmlBeanFactory( res );
	}
	
	
	
}
