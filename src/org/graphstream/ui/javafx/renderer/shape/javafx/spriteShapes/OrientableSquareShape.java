package org.graphstream.ui.javafx.renderer.shape.javafx.spriteShapes;

import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.OrientableRectangularAreaShape;

public class OrientableSquareShape extends OrientableRectangularAreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	public Form theShape() {
		return theShape;
	}
}