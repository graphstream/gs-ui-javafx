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

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapePaint;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapeStroke;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;
import org.graphstream.ui.javafx.util.ColorManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FillableLine {
	ShapeStroke fillStroke = null ;
	double theFillPercent = 0.0 ;
	Color theFillColor = null ;
	boolean plainFast = false ;
  
	public void fill(GraphicsContext g, double width, double dynColor, Form shape) {
		if(fillStroke != null) {
		    if(plainFast) {
				g.setStroke(theFillColor);
				g.setFill(theFillColor);
				shape.drawByPoints(g, false);
		    }
		    else {
		    	g.setStroke(theFillColor);
				g.setFill(theFillColor);
				
				fillStroke.stroke(width, shape, null).changeStrokeProperties(g);
								
				shape.drawByPoints(g, false);
			}
		}
	}
 
	public void fill(GraphicsContext g, double width, Form shape) { fill(g, width, theFillPercent, shape); }
 
	public void configureFillableLineForGroup(Backend bck, Style style, DefaultCamera2D camera, double theSize) {
		fillStroke = ShapeStroke.strokeForConnectorFill( style );
  	  	plainFast = (style.getSizeMode() == StyleConstants.SizeMode.NORMAL); 
		theFillColor = ColorManager.getFillColor(style, 0);
		bck.graphics2D().setStroke(theFillColor);
		bck.graphics2D().setFill(theFillColor);
		if(fillStroke != null) {
			fillStroke.stroke(theSize, null, theFillColor).changeStrokeProperties(bck.graphics2D());
		}
	}

	public void configureFillableLineForElement( Style style, DefaultCamera2D camera, GraphicElement element ) {
		theFillPercent = 0 ;
  	  	if( style.getFillMode() == StyleConstants.FillMode.DYN_PLAIN && element != null ) {
  	  		
	  	  	if ( element.getAttribute( "ui.color" ) instanceof Number ) {
  	  			theFillPercent = (float)((Number)element.getAttribute( "ui.color" ));
  	  			theFillColor = ShapePaint.interpolateColor( style.getFillColors(), theFillPercent ) ;
  	  		}
  	  		else if ( element.getAttribute( "ui.color" ) instanceof Color ) {
  	  			theFillColor = ((Color)element.getAttribute( "ui.color" )); 
  	  			theFillPercent = 0;	
  	  		}
  	  		else {
  	  			theFillPercent = 0f;
  	  			theFillColor = ColorManager.getFillColor(style, 0);
  	  		}
	  	  	
  	  		plainFast = false;
  	  	}
	}
}