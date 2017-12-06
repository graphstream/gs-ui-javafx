package org.graphstream.ui.fx.renderer.shape.fx.shapePart;

import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.shape.fx.ShapeStroke;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

public class StrokableLine extends Strokable {
 	public void configureStrokableForGroup( Style style, FxDefaultCamera camera ) {
		theStrokeWidth = camera.getMetrics().lengthToGu( style.getStrokeWidth() ) + camera.getMetrics().lengthToGu( style.getSize(), 0 );
		strokeColor = ShapeStroke.strokeColor( style );
		theStroke = ShapeStroke.strokeForArea( style );
 	}
 	
 	public void configureStrokableLineForGroup( Style style, FxDefaultCamera camera ) { 
 		configureStrokableForGroup( style, camera ) ;
 	}
}