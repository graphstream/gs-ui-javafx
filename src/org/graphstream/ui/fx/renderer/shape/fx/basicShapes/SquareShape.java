package org.graphstream.ui.fx.renderer.shape.fx.basicShapes;

import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.RectangularAreaShape;

import javafx.scene.shape.Shape;

public class SquareShape extends RectangularAreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	public Shape theShape() {
		return theShape;
	}
}