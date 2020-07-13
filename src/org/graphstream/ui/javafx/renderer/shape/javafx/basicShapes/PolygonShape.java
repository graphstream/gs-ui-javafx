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
  
package org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.AreaSkeleton;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Path2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.PolygonalShape;
import org.graphstream.ui.javafx.util.AttributeUtils;

public class PolygonShape extends PolygonalShape implements AttributeUtils {
	Point3[] theValues = null ;
	Point3 minPoint = null ;
	Point3 maxPoint = null ;
	Object valuesRef = null ;
	
	@Override
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, DefaultCamera2D camera) {
		super.configureForElement(bck, element, skel, camera);
		
        if(element.hasAttribute( "ui.points" )) {
			Object oldRef = valuesRef;
			valuesRef = element.getAttribute("ui.points");
			// We use valueRef to avoid
			// recreating the values array for nothing.
			if( ( theValues == null ) || ( oldRef != valuesRef ) ) {
				theValues = getPoints(valuesRef);
				
				if(skel instanceof AreaSkeleton) {
				    Tuple<Point3, Point3> tuple = boundingBoxOfPoints(theValues);

				    minPoint = tuple.x;
				    maxPoint = tuple.y;
				}
			}
		
			AreaSkeleton ninfo = (AreaSkeleton)skel;
			ninfo.theSize.set(maxPoint.x-minPoint.x, maxPoint.y-minPoint.y);
			area.theSize.copy(ninfo.theSize);
		}
	}
	
	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		double x = area.theCenter.x;
		double y = area.theCenter.y;
        double n = theValues.length;
        
        theShape = new Path2D((int)n+2, true);
        
        if(n > 0) {
        	theShape().moveTo(x+theValues[0].x, y+theValues[0].y);
        	for(int i = 0 ; i < n ; i++) {
        	    theShape().lineTo(x+theValues[i].x, y+theValues[i].y);
        	}
        }		
        theShape.closePath();
	}
	
	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		double n = theValues.length;
		double x  = area.theCenter.x + shadowable.theShadowOff.x;
		double y  = area.theCenter.y + shadowable.theShadowOff.y;

        theShape = new Path2D((int)n+2, true);
        
        if(n > 0) {
        	theShape().moveTo(x+theValues[0].x, y+theValues[0].y);
        	for(int i = 0 ; i < n ; i++) {
        	    theShape().lineTo(x+theValues[i].x, y+theValues[i].y);
        	}
        }
        theShape.closePath();
	}
}