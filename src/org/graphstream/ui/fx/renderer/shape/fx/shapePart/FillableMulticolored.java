package org.graphstream.ui.fx.renderer.shape.fx.shapePart;

import org.graphstream.ui.fx.FxDefaultCamera;
import org.graphstream.ui.fx.util.ColorManager;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

import javafx.scene.paint.Color;

public class FillableMulticolored {
	public Color[] fillColors = null ;
	
	public void configureFillableMultiColoredForGroup(Style style, FxDefaultCamera camera) {
		int count = style.getFillColorCount();
		
		if(fillColors == null || fillColors.length != count) {
			fillColors = new Color[count];
		}
		
		for (int i = 0 ; i < count ; i++) {
			fillColors[i] = ColorManager.getFillColor(style, i);
		}
	}
}