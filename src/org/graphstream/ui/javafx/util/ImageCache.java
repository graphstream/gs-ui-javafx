/*
 * This file is part of GraphStream <http://graphstream-project.org>.
 * 
 * GraphStream is a library whose purpose is to handle static or dynamic
 * graph, create them from scratch, file or any source and display them.
 * 
 * This program is free software distributed under the terms of two licenses, the
 * CeCILL-C license that fits European law, and the GNU Lesser General Public
 * License. You can  use, modify and/ or redistribute the software under the terms
 * of the CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
 * URL <http://www.cecill.info> or under the terms of the GNU LGPL as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C and LGPL licenses and that you accept their terms.
 */

 /**
  * @author Antoine Dutot <antoine.dutot@graphstream-project.org>
  * @author Guilhelm Savin <guilhelm.savin@graphstream-project.org>
  * @author Hicham Brahimi <hicham.brahimi@graphstream-project.org>
  */
  
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
