package org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes;

import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.PolygonalShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Path2D;

public class DiamondShape extends PolygonalShape {

	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		double x  = area.theCenter.x;
		double y  = area.theCenter.y;
		double w2 = area.theSize.x / 2;
		double h2 = area.theSize.y / 2;
		
		theShape = new Path2D(10, true);
		theShape().moveTo( x - w2, y );
		theShape().lineTo( x, y - h2 );
		theShape().lineTo( x + w2, y );
		theShape().lineTo( x, y + h2 );
		theShape.closePath();
	}

	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		double x  = area.theCenter.x + shadowable.theShadowOff.x;
		double y  = area.theCenter.y + shadowable.theShadowOff.y;
		double w2 = ( area.theSize.x + shadowable.theShadowWidth.x ) / 2;
		double h2 = ( area.theSize.y + shadowable.theShadowWidth.y ) / 2;
		
		theShape = new Path2D(10, true);
		theShape().moveTo( x - w2, y );
		theShape().lineTo( x, y - h2 );
		theShape().lineTo( x + w2, y );
		theShape().lineTo( x, y + h2 );
		theShape.closePath();
	}
}