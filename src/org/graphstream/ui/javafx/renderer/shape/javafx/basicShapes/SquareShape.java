package org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes;

import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.RectangularAreaShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;

import javafx.scene.shape.Shape;

public class SquareShape extends RectangularAreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	public Shape theShape() {
		return theShape;
	}
}