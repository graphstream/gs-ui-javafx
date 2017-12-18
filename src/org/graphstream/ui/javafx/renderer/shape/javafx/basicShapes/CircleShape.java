package org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes;

import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Ellipse2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.RectangularAreaShape;

public class CircleShape extends RectangularAreaShape {
	private Form theShape = new Ellipse2D();
	
	public Form theShape() {
		return theShape;
	}
}