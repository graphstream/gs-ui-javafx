package org.graphstream.ui.javafx.renderer.shape.javafx.shapePart;

import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.FxDefaultCamera;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapePaint;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapePaint.ShapeAreaPaint;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapePaint.ShapeColorPaint;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Shadowable {
	/** The shadow paint. */
	public ShapePaint shadowPaint = null ;

	/** Additional width of a shadow (added to the shape size). */
	public Point2 theShadowWidth = new Point2();
 
	/** Offset of the shadow according to the shape center. */
	public Point2 theShadowOff = new Point2();

	/** Set the shadow width added to the shape width. */
	public void shadowWidth( double width, double height ) { theShadowWidth.set( width, height ); }
 
 	/** Set the shadow offset according to the shape. */ 
	public void shadowOffset( double xoff, double yoff ) { theShadowOff.set( xoff, yoff ); }
 
 	/**
     * Render the shadow.
     * @param g The Java2D graphics.
     */
	public void cast( GraphicsContext g, Form shape) {
		if ( shadowPaint instanceof ShapeAreaPaint ) {
			Paint p = ((ShapeAreaPaint)shadowPaint).paint( shape, 1 ) ;
			g.setStroke(p);
			g.setFill(p);
			shape.drawByPoints(g, false);
		}
		else if ( shadowPaint instanceof ShapeColorPaint ) {
			Paint p = ((ShapeColorPaint)shadowPaint).paint( 0, null ) ;
			g.setStroke(p);
			g.setFill(p);
			shape.drawByPoints(g, false);
		}
		else {
			System.out.println("no shadow !!!");
		}
   	}
 
    /** Configure all the static parts needed to cast the shadow of the shape. */
 	public void configureShadowableForGroup( Style style, FxDefaultCamera camera ) {
 		theShadowWidth.x = camera.getMetrics().lengthToGu( style.getShadowWidth() );
 		theShadowWidth.y = theShadowWidth.x;
 		theShadowOff.x   = camera.getMetrics().lengthToGu( style.getShadowOffset(), 0 );
 		theShadowOff.y   = theShadowOff.x ;
 		if( style.getShadowOffset().size() > 1 ) 
 			theShadowOff.y = camera.getMetrics().lengthToGu( style.getShadowOffset(), 1 ); 
 	  
  	  	/*if( shadowPaint == null )*/ shadowPaint = ShapePaint.apply( style, true );
 	}
}