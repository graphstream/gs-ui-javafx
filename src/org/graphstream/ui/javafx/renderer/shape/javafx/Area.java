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
  
package org.graphstream.ui.javafx.renderer.shape.javafx;

import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.AreaSkeleton;

/** Trait for elements painted inside an area (most nodes and sprites).
 * 
  * This trait manages the size of the area (the size is rectangular, although the area may not
  * be), its position, and the automatic fit to the contents, if needed.
  * 
  * As this trait computes the position and size of the shape, it should
  * probably be configured first when assembling the configureForGroup
  * and configureForElement methods. */
public class Area {
	/** The shape position. */
	public Point2 theCenter = new Point2();
	
	/** The shape size. */
	public Point2 theSize = new Point2();
	
	/** Fit the shape size to its contents? */
	protected boolean fit = false;
	
	public void configureAreaForGroup(Style style, DefaultCamera2D camera) {
		sizeForGroup(style, camera);
	}
	
	/** Select the general size and position of the shape.
	 * This is done according to:
	 *   - The style,
	 *   - Eventually the element specific size attribute,
	 *   - Eventually the element contents (decor). */
	public void configureAreaForElement(Backend backend, DefaultCamera2D camera, AreaSkeleton skel, GraphicElement element, ShapeDecor decor) {
		Point3 pos = camera.getNodeOrSpritePositionGU(element, null);
		
		if(fit) {
			Tuple<Double, Double> decorSize = decor.size(backend, camera, skel.iconAndText);
			if(decorSize.val1 == 0 || decorSize.val2 == 0)
				sizeForElement(element.getStyle(), camera, element);
			positionAndFit(camera, skel, element, pos.x, pos.y, decorSize.val1, decorSize.val2);
		} 
		else {
			sizeForElement(element.getStyle(), camera, element);
			positionAndFit(camera, skel, element, pos.x, pos.y, 0, 0);
		}
	}
	
	/** Set the general size of the area according to the style.
	 * Also look if the style SizeMode says if the element must fit to its contents.
	 * If so, the configureAreaForElement() method will recompute the size for each
	 * element according to the contents (shape decoration). */
	private void sizeForGroup(Style style, DefaultCamera2D camera) {
		double w = camera.getMetrics().lengthToGu( style.getSize(), 0 );
		double h = w;
		if( style.getSize().size() > 1 )
			h = camera.getMetrics().lengthToGu( style.getSize(), 1 );
		
		  
		theSize.set(w, h);
		fit = (style.getSizeMode() == StyleConstants.SizeMode.FIT);
	}
	
	/** Try to compute the size of this area according to the given element. */
	private void sizeForElement(Style style, DefaultCamera2D camera, GraphicElement element) {
		double w = camera.getMetrics().lengthToGu(style.getSize(), 0);
		double h = w ;
		if(style.getSize().size() > 1) 
			h = camera.getMetrics().lengthToGu(style.getSize(), 1) ;
				
		if(style.getSizeMode() == StyleConstants.SizeMode.DYN_SIZE) {
			Object s = element.getAttribute("ui.size");
		
			if(s != null) {
				w = camera.getMetrics().lengthToGu(StyleConstants.convertValue(s));
				h = w;
			}
		}
		
		theSize.set(w, h);
	}
	
	/** Assign a position to the shape according to the element, set the size of the element,
	 * and update the skeleton of the element. */
	private void positionAndFit(DefaultCamera2D camera, AreaSkeleton skel, GraphicElement element, double x, double y, double contentOverallWidth, double contentOverallHeight) {
		if(skel != null) {
			if(contentOverallWidth > 0 && contentOverallHeight > 0)
				theSize.set(contentOverallWidth, contentOverallHeight);
			
			skel.theSize.copy(theSize);
			skel.theCenter.copy(theCenter);
		}

		theCenter.set(x, y);
	}
}
