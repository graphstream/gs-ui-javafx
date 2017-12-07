package org.graphstream.ui.fx.renderer.shape.fx.shapePart;

import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.shape.fx.ShapeStroke;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form;
import org.graphstream.ui.fx.util.ColorManager;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

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
   	public void cast( GraphicsContext g, Shape shape ) {
   		g.setStroke(theShadowColor);
   		g.setFill(theShadowColor);
   		shadowStroke.stroke( theShadowWidth , ((Form)shape) ).changeStrokeProperties(g);
   	  	((Form)shape).drawByPoints(g, false);
   	}
 
    /** Configure all the static parts needed to cast the shadow of the shape. */
 	public void configureShadowableLineForGroup( Style style, FxDefaultCamera camera) {
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