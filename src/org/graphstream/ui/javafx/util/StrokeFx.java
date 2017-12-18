package org.graphstream.ui.javafx.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineCap;

public class StrokeFx {
	protected double width ;
	private Double dashes;
	private StrokeLineCap cap ;
	
	public StrokeFx() {
		this(1, null, StrokeLineCap.SQUARE);
	}
	
	public StrokeFx(double width) {
		this(width, null, StrokeLineCap.SQUARE);
	}
	
	public StrokeFx(double width, Double dashes, StrokeLineCap cap) {
		this.width = width ;
		this.dashes = dashes ;
		this.cap = cap ;
	}
	
	public void changeStrokeProperties(GraphicsContext g) {		
		g.setLineWidth(width);

		if (dashes == null)
			g.setLineDashes(null);
		else
			g.setLineDashes(dashes);
		
		g.setLineCap(cap);
	}
}
