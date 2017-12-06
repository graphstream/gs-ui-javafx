package org.graphstream.ui.fx.renderer.shape.fx.baseShapes;

import org.graphstream.ui.fx.Backend;
import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.renderer.Skeleton;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Path2D;
import org.graphstream.ui.graphicGraph.GraphicElement;

import javafx.scene.canvas.GraphicsContext;

public abstract class PolygonalShape extends AreaShape {
	private Path2D theShape = new Path2D();
 
 	public void renderShadow(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skel) {
 		makeShadow(bck, camera);
 		shadowable.cast(bck.graphics2D(), theShape());
 	}
  
 	public void render(Backend bck, FxDefaultCamera camera, GraphicElement element, Skeleton skel) {
 		GraphicsContext g = bck.graphics2D();
 		make(bck, camera);
 		fillable.fill(g, theShape(), camera);
 		strokable.stroke(g, theShape());
 		decorArea(bck, camera, skel.iconAndText, element, theShape());
 	}
 	
 	public Path2D theShape() {
 		return theShape ;
 	}
}