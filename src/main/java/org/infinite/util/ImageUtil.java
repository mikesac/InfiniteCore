package org.infinite.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.infinite.objects.Map;

public final class ImageUtil {



	public static BufferedImage scaleImage(BufferedImage image, int width, int height){
		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g.setRenderingHint(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
		}

	public static BufferedImage cropImage(BufferedImage img, int start_x, int start_y, int width, int height){
		return img.getSubimage(start_x, start_y, width, height);
	}

	public static BufferedImage getImagefromStream(InputStream is) throws IOException{
		return ImageIO.read(is);
	}

	public static ByteArrayOutputStream setImageToStream( BufferedImage img, String format) throws IOException{

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		if(format.equalsIgnoreCase("jpg"))
		{
			BufferedImage jpgImage = null;
			if (img.getType() == BufferedImage.TYPE_CUSTOM) {
				jpgImage = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
			} else {
				jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType() );
			}

			//copy the original to the new image
			Graphics2D g2 = null;
			try {
				g2 = jpgImage.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), Color.WHITE, null); //this is to force background color to be white
				g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
			}
			finally {
				if (g2 != null) {
					g2.dispose();
				}
			}
			img = jpgImage;

		}
		else if(format.equalsIgnoreCase("gif")){
			img = convertRGBAToIndexed(img);
		}		
		ImageIO.write(img,format,out);
		
		return out;
	}

	public static BufferedImage prepareMapStripes(InputStream is,int nx,int ny,String path) throws IOException{

		//first scale
		BufferedImage scaledImg =  scaleImage( getImagefromStream(is),Map.MAP_WIDTH, Map.MAP_HEIGHT );

		int w = Map.MAP_WIDTH/nx;
		int h = Map.MAP_HEIGHT/ny;

		for (int i = 0; i < ny; i++) {
			for (int j = 0; j < nx; j++) {
				BufferedImage crop = cropImage(scaledImg, w*j, h*i, w, h);

				File f = new File(path+"/crop_"+i+j+".jpg");
				ImageIO.write(crop, "jpg", f);
			}
		}


		return null;
	}



	private static BufferedImage convertRGBAToIndexed(BufferedImage src) {
		BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
		Graphics g = dest.getGraphics();
		g.setColor(new Color(231,20,189));
		g.fillRect(0, 0, dest.getWidth(), dest.getHeight()); //fill with a hideous color and make it transparent
		dest = makeTransparent(dest,0,0);
		dest.createGraphics().drawImage(src,0,0, null);
		return dest;
	}

	private static BufferedImage makeTransparent(BufferedImage image, int x, int y) {
		ColorModel cm = image.getColorModel();
		if (!(cm instanceof IndexColorModel))
			return image; //sorry...
		IndexColorModel icm = (IndexColorModel) cm;
		WritableRaster raster = image.getRaster();
		int pixel = raster.getSample(x, y, 0); //pixel is offset in ICM's palette
		int size = icm.getMapSize();
		byte[] reds = new byte[size];
		byte[] greens = new byte[size];
		byte[] blues = new byte[size];
		icm.getReds(reds);
		icm.getGreens(greens);
		icm.getBlues(blues);
		IndexColorModel icm2 = new IndexColorModel(8, size, reds, greens, blues, pixel);
		return new BufferedImage(icm2, raster, image.isAlphaPremultiplied(), null);
	}

}

