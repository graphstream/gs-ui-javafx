package org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes;

import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.RectangularAreaShape;

public class SquareShape extends RectangularAreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	public Form theShape() {
		return theShape;
	}
}