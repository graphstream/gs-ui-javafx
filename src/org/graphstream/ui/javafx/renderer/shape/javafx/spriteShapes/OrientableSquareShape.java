package org.graphstream.ui.javafx.renderer.shape.javafx.spriteShapes;

import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.OrientableRectangularAreaShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;

import javafx.scene.shape.Shape;

public class OrientableSquareShape extends OrientableRectangularAreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	public Shape theShape() {
		return theShape;
	}
}