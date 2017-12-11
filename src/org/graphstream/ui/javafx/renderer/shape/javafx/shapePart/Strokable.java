package org.graphstream.ui.javafx.renderer.shape.javafx.shapePart;

import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.FxDefaultCamera;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapeStroke;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Strokable {
    /** The stroke color. */
	public Color strokeColor = null ;

	/** The stroke. */
	public ShapeStroke theStroke = null ;
 	
	/** The stroke width. */
	public double theStrokeWidth = 0.0 ;

 	/** Paint the stroke of the shape. */
	public void stroke( GraphicsContext g, Shape shape ) {
		if(theStroke != null) {
			theStroke.stroke( theStrokeWidth, ((Form)shape)).changeStrokeProperties(g);

			g.setStroke(strokeColor);
			g.setFill(strokeColor);
			((Form)shape).drawByPoints(g, true);
		}	  
	}
	
 	/** Configure all the static parts needed to stroke the shape. */
 	public void configureStrokableForGroup( Style style, FxDefaultCamera camera ) {
		theStrokeWidth = camera.getMetrics().lengthToGu( style.getStrokeWidth() );
		
		/*if( strokeColor == null )*/ strokeColor = ShapeStroke.strokeColor( style );
		/*if( theStroke   == null )*/ theStroke   = ShapeStroke.strokeForArea( style );
 	}
}