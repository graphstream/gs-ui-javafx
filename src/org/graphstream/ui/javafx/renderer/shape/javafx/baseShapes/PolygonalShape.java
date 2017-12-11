package org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.javafx.FxDefaultCamera;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Path2D;

import javafx.scene.canvas.GraphicsContext;

public abstract class PolygonalShape extends AreaShape {
	protected Path2D theShape = new Path2D();
 
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