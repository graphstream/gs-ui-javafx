package org.graphstream.ui.fx.renderer.shape.fx.basicShapes;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.PolygonalShape;

public class DiamondShape extends PolygonalShape {

	@Override
	public void make(Backend backend, FxDefaultCamera camera) {
		double x  = area.theCenter.x;
		double y  = area.theCenter.y;
		double w2 = area.theSize.x / 2;
		double h2 = area.theSize.y / 2;
		
		theShape().reset();
		theShape().moveTo( x - w2, y );
		theShape().lineTo( x, y - h2 );
		theShape().lineTo( x + w2, y );
		theShape().lineTo( x, y + h2 );
	}

	@Override
	public void makeShadow(Backend backend, FxDefaultCamera camera) {
		double x  = area.theCenter.x + shadowable.theShadowOff.x;
		double y  = area.theCenter.y + shadowable.theShadowOff.y;
		double w2 = ( area.theSize.x + shadowable.theShadowWidth.x ) / 2;
		double h2 = ( area.theSize.y + shadowable.theShadowWidth.y ) / 2;
		
		theShape().reset();
		theShape().moveTo( x - w2, y );
		theShape().lineTo( x, y - h2 );
		theShape().lineTo( x + w2, y );
		theShape().lineTo( x, y + h2 );
	}
}