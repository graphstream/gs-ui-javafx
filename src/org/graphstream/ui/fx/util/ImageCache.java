package org.graphstream.ui.fx.util;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;

public class ImageCache {

	protected static HashMap<String, Image> imageCache = new HashMap<>();
	
	protected static Image dummy = null ;
		
	public void init() {
		/*Graphics2D g2 = img.createGraphics();
		g2.setColor(Color.RED);
		g2.drawRect(0, 0, img.getWidth()-1, img.getHeight()-1);
		g2.drawLine(0, 0, img.getWidth()-1, img.getHeight()-1);
		g2.drawLine(0, img.getHeight()-1, img.getWidth()-1, 0);*/
	}
	
	
	public static Image loadImage(String fileNameOrUrl) {
		return loadImage(fileNameOrUrl, false);
	}


	public static Image loadImage(String fileNameOrUrl, boolean forceTryReload) {
		if (imageCache.get(fileNameOrUrl) == null) {
			Image image = null ;			
			File file = new File(fileNameOrUrl);
			
			if (file.exists()) { // The image is in the class path.
				try {
					image = new Image(fileNameOrUrl);
					imageCache.put(fileNameOrUrl, image);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				image = dummy ;
				imageCache.put( fileNameOrUrl, image );
				Logger.getLogger(ImageCache.class.getSimpleName()).log(Level.WARNING, "Cannot read image "+fileNameOrUrl+".");
			}
			
			return image ;
		}
		else {
			if(imageCache.get(fileNameOrUrl) == dummy && forceTryReload) {
				imageCache.remove(fileNameOrUrl) ;
				return loadImage(fileNameOrUrl);
			}
			else
				return imageCache.get(fileNameOrUrl);
		}
	}
	
	public static Image dummyImage() {
		return dummy ;
	}
}
