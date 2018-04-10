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
  
package org.graphstream.ui.javafx.renderer.shape.javafx.shapePart;

import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapeStroke;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;
import org.graphstream.ui.javafx.util.ColorManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShadowableLine {
	/** The shadow paint. */
	public ShapeStroke shadowStroke = null;

	/** Additional width of a shadow (added to the shape size). */
	public double theShadowWidth = 0.0;
 
	/** Offset of the shadow according to the shape center. */
	public Point2 theShadowOff = new Point2();

	public Color theShadowColor = null ;
 
	/** Sety the shadow width added to the shape width. */
	public void shadowWidth( double width ) { theShadowWidth = width; }
 
 	/** Set the shadow offset according to the shape. */ 
	public void shadowOffset( double xoff, double yoff ) { theShadowOff.set( xoff, yoff ); }
	
 	/**
     * Render the shadow.
     * @param g The Java2D graphics.
     */
   	public void cast( GraphicsContext g, Form shape ) {
   		g.setStroke(theShadowColor);
   		g.setFill(theShadowColor);
   		shadowStroke.stroke( theShadowWidth , shape, null ).changeStrokeProperties(g);
   	  	shape.drawByPoints(g, true);
   	}
 
    /** Configure all the static parts needed to cast the shadow of the shape. */
 	public void configureShadowableLineForGroup( Style style, DefaultCamera2D camera) {
 		theShadowWidth = camera.getMetrics().lengthToGu( style.getSize(), 0 ) +
 			camera.getMetrics().lengthToGu( style.getShadowWidth() ) +
 			camera.getMetrics().lengthToGu( style.getStrokeWidth() ) ;
 		theShadowOff.x = camera.getMetrics().lengthToGu( style.getShadowOffset(), 0 );
 		theShadowOff.y = theShadowOff.x ;
 		if( style.getShadowOffset().size() > 1 ) 
 			camera.getMetrics().lengthToGu( style.getShadowOffset(), 1 ) ;
  	  	theShadowColor = ColorManager.getShadowColor(style, 0);
 		shadowStroke   = ShapeStroke.strokeForConnectorFill( style );
 	}	
}