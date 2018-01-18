package org.graphstream.ui.javafx.renderer.shape.javafx.shapePart;

import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.util.ColorManager;

import javafx.scene.paint.Color;

public class FillableMulticolored {
	public Color[] fillColors = null ;
	
	public void configureFillableMultiColoredForGroup(Style style, DefaultCamera2D camera) {
		int count = style.getFillColorCount();
		
		if(fillColors == null || fillColors.length != count) {
			fillColors = new Color[count];
		}
		
		for (int i = 0 ; i < count ; i++) {
			fillColors[i] = ColorManager.getFillColor(style, i);
		}
	}
}