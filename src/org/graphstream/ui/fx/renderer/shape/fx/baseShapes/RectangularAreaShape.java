package org.graphstream.ui.fx.renderer.shape.fx.baseShapes;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.Skeleton;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.graphicGraph.GraphicElement;

import javafx.scene.shape.Shape;

public abstract class RectangularAreaShape extends AreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	@Override
	public void make(Backend backend, FxDefaultCamera camera) {
		double w = area.theSize.x;
		double h = area.theSize.y;
		
		((Form)theShape()).setFrame(area.theCenter.x-w/2, area.theCenter.y-h/2, w, h);	
	}

	@Override
	public void makeShadow(Backend backend, FxDefaultCamera camera) {
		double x = area.theCenter.x + shadowable.theShadowOff.x;
		double y = area.theCenter.y + shadowable.theShadowOff.y;
		double w = area.theSize.x + shadowable.theShadowWidth.x * 2;
		double h = area.theSize.y + shadowable.theShadowWidth.y * 2;
		
		((Form)theShape()).setFrame(x-w/2, y-h/2, w, h);
	}

	@Override
	public void render(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skel) {
		make(bck, camera);
 		fillable.fill(bck.graphics2D(), theShape(), camera);
 		strokable.stroke(bck.graphics2D(), theShape());
 		decorArea(bck, camera, skel.iconAndText, element, theShape());
	}

	@Override
	public void renderShadow(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skeleton) {
		makeShadow(bck, camera);
 		shadowable.cast(bck.graphics2D(), theShape());
	}
	
	public Shape theShape() {
		return theShape;
	}
}