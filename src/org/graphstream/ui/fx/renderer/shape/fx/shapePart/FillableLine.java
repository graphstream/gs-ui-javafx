package org.graphstream.ui.fx.renderer.shape.fx.shapePart;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.shape.fx.ShapePaint;
import org.graphstream.ui.fx.renderer.shape.fx.ShapeStroke;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form;
import org.graphstream.ui.fx.util.ColorManager;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

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
				fillStroke.stroke(width, shape).changeStrokeProperties(g);
				
				g.setStroke(theFillColor);
				g.setFill(theFillColor);
								
				shape.drawByPoints(g, false);
			}
		}
	}
 
	public void fill(GraphicsContext g, double width, Shape shape) { fill(g, width, theFillPercent, (Form)shape); }
 
	public void configureFillableLineForGroup(Backend bck, Style style, FxDefaultCamera camera, double theSize) {
		fillStroke = ShapeStroke.strokeForConnectorFill( style );
  	  	plainFast = (style.getSizeMode() == StyleConstants.SizeMode.NORMAL); 
		theFillColor = ColorManager.getFillColor(style, 0);
		bck.graphics2D().setStroke(theFillColor);
		bck.graphics2D().setFill(theFillColor);
		if(fillStroke != null) {
			fillStroke.stroke(theSize, null).changeStrokeProperties(bck.graphics2D());
		}
	}

	public void configureFillableLineForElement( Style style, FxDefaultCamera camera, GraphicElement element ) {
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