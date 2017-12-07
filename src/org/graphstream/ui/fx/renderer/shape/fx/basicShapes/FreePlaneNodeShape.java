package org.graphstream.ui.fx.renderer.shape.fx.basicShapes;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.Skeleton;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Line2D;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.RectangularAreaShape;
import org.graphstream.ui.graphicGraph.GraphicElement;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;

public class FreePlaneNodeShape extends RectangularAreaShape {
	Rectangle2D theShape = new Rectangle2D();
	Line2D theLineShape = new Line2D();
	
	@Override
	public void make(Backend backend, FxDefaultCamera camera) {
		double w = area.theSize.x ;
		double h = area.theSize.y ;
		double x = area.theCenter.x ;
		double y = area.theCenter.y ;
		
		((Rectangle2D) theShape()).setFrame( x-w/2, y-h/2, w, h );
		
		w -= strokable.theStrokeWidth;
		
		theLineShape.setFrame( x-w/2, y-h/2, x+w/2, y-h/2 );
	}
	
	@Override
	public void makeShadow(Backend backend, FxDefaultCamera camera) {
		double x = area.theCenter.x + shadowable.theShadowOff.x;
		double y = area.theCenter.y + shadowable.theShadowOff.y;
		double w = area.theSize.x + shadowable.theShadowWidth.x * 2;
		double h = area.theSize.y + shadowable.theShadowWidth.y * 2;
		
		((Rectangle2D) theShape()).setFrame( x-w/2, y-h/2, w, h );
		theLineShape.setFrame( x-w/2, y-h/2, x+w/2, y-h/2 );	
	}
	
	@Override
	public void render(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skel) {
		GraphicsContext g = bck.graphics2D();
		make(bck, camera);
		fillable.fill(g, theShape(), camera);
		strokable.stroke(g, theLineShape);
		decorArea(bck, camera, skel.iconAndText, element, theShape());
	}
	
	@Override
	public Shape theShape() {
		return theShape;
	} 
	
}
