package org.graphstream.ui.javafx.renderer.shape.javafx.basicShapes;

import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.RectangularAreaShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Ellipse2D;

import javafx.scene.shape.Shape;

public class CircleShape extends RectangularAreaShape {
	private Shape theShape = new Ellipse2D();
	
	public Shape theShape() {
		return theShape;
	}
}