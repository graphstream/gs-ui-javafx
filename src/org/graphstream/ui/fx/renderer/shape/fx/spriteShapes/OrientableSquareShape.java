package org.graphstream.ui.fx.renderer.shape.fx.spriteShapes;

import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.fx.renderer.shape.fx.baseShapes.OrientableRectangularAreaShape;

import javafx.scene.shape.Shape;

public class OrientableSquareShape extends OrientableRectangularAreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	public Shape theShape() {
		return theShape;
	}
}