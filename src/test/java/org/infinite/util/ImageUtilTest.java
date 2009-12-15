package org.infinite.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;


public class ImageUtilTest {

	private static String outputFolder = "/tmp/";
	private int counter = 0;
	@Test
	public void formatConversionTest(){
		
		testFormatConversion("png");
		testFormatConversion("gif");
		testFormatConversion("jpg");
		
	}

	private void testFormatConversion(String format) {
		String fileName = "/imgs/test_color."+format;
		
		InputStream is = getClass().getResourceAsStream(fileName);
		outputFolder = getClass().getResource("/imgs/output/empty.txt").toString();
		outputFolder = outputFolder.substring( outputFolder.indexOf(":")+1, outputFolder.lastIndexOf("/")+1);
		
		assertNotNull(is);
		
		try {
			BufferedImage img = ImageUtil.getImagefromStream(is);
			assertNotNull(img);
			if(!format.equals("gif")){
				System.out.println("testing ["+format+"] to [gif] conversion");
				testConversion(fileName, img,"gif");
			}
			
			if(!format.equals("jpg")){
				System.out.println("testing ["+format+"] to [jpg] conversion");
				testConversion(fileName, img,"jpg");
			}
			
			if(!format.equals("png")){
				System.out.println("testing ["+format+"] to [png] conversion");
				testConversion(fileName, img,"png");
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	private void testConversion(String fileName,BufferedImage img, String format) throws IOException, FileNotFoundException {
		ByteArrayOutputStream os = ImageUtil.setImageToStream(img, format );
		assertNotNull(os);			
		
		FileOutputStream fos = new FileOutputStream(outputFolder + (counter++)+"_" + fileName.substring(fileName.lastIndexOf("/")+1).replace(".", "_")+"."+ format);
		os.writeTo(fos);
		fos.flush();
		fos.close();

	}
	
	
	
	
}
