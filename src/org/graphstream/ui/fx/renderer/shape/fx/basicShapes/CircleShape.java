package org.graphstream.ui.fx.renderer.shape.fx.basicShapes;

import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.RectangularAreaShape;

import javafx.scene.shape.Shape;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Ellipse2D ;

public class CircleShape extends RectangularAreaShape {
	private Shape theShape = new Ellipse2D();
	
	public Shape theShape() {
		return theShape;
	}
}