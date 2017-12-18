package org.graphstream.ui.javafx.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
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
			URL url = ImageCache.class.getClassLoader().getResource(fileNameOrUrl);
			BufferedImage buffImage = null ;
			Image image = null ;
			
			if (url != null) { // The image is in the class path.
				try {
					buffImage = ImageIO.read(url);
					image = SwingFXUtils.toFXImage(buffImage, null);
					imageCache.put(fileNameOrUrl, image);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					url = new URL(fileNameOrUrl);
					
					buffImage = ImageIO.read(url);
					image = SwingFXUtils.toFXImage(buffImage, null);
					imageCache.put(fileNameOrUrl, image);
				}
				catch (Exception e) {
					try {
						buffImage = ImageIO.read( new File( fileNameOrUrl ) );	// Try the file.
						image = SwingFXUtils.toFXImage(buffImage, null);
						imageCache.put( fileNameOrUrl, image );
					}
					catch (Exception ex) {
						image = dummy ;
						imageCache.put( fileNameOrUrl, image );
						Logger.getLogger(ImageCache.class.getSimpleName()).log(Level.WARNING, "Cannot read image '%s'.".format(fileNameOrUrl), e);
					}
				}
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
