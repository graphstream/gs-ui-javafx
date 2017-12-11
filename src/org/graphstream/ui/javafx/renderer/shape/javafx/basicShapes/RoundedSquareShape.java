package org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes;

import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.javafx.FxDefaultCamera;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.RectangularAreaShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;

import javafx.scene.shape.Shape;

public class RoundedSquareShape extends RectangularAreaShape {
	Rectangle2D theShape = new Rectangle2D();
	
	@Override
	public void make(Backend backend, FxDefaultCamera camera) {
		double w = area.theSize.x ;
		double h = area.theSize.x ;
		double r = h/8 ;
		if( h/8 > w/8 )
			r = w/8 ;
		((Rectangle2D) theShape()).setRoundRect( area.theCenter.x-w/2, area.theCenter.y-h/2, w, h, r, r ) ;
	}
	
	@Override
	public void makeShadow(Backend backend, FxDefaultCamera camera) {
		double x = area.theCenter.x + shadowable.theShadowOff.x;
		double y = area.theCenter.y + shadowable.theShadowOff.y;
		double w = area.theSize.x + shadowable.theShadowWidth.x * 2;
		double h = area.theSize.y + shadowable.theShadowWidth.y * 2;
		double r = h/8 ;
		if( h/8 > w/8 ) 
			r = w/8;
				
		((Rectangle2D) theShape()).setRoundRect( x-w/2, y-h/2, w, h, r, r );
	}
	
	@Override
	public Shape theShape() {
		return theShape;
	}
}