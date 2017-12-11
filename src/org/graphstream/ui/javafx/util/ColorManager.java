package org.graphstream.ui.javafx.util;

import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

import javafx.scene.paint.Color;

public class ColorManager {
	/**
	 * Get fill awt color in group and convert to javafx Color
	 * @param group
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getFillColor(StyleGroup group, int id) {
		return Color.rgb(group.getFillColor(id).getRed(), group.getFillColor(id).getGreen(), group.getFillColor(id).getBlue());
	}
	

	public static Color getFillColor(Style group, int id) {
		return Color.rgb(group.getFillColor(id).getRed(), group.getFillColor(id).getGreen(), group.getFillColor(id).getBlue());
	}
	
	/**
	 * Get stroke awt color in group and convert to javafx Color
	 * @param group
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getStrokeColor(StyleGroup group, int id) {
		return Color.rgb(group.getStrokeColor(id).getRed(), group.getStrokeColor(id).getGreen(), group.getStrokeColor(id).getBlue());
	}
	
	/**
	 * Get canvas awt color in group and convert to javafx Color
	 * @param group
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getCanvasColor(StyleGroup group, int id) {
		return Color.rgb(group.getCanvasColor(id).getRed(), group.getCanvasColor(id).getGreen(), group.getCanvasColor(id).getBlue());
	}
	
	/**
	 * Get shadow awt color in group and convert to javafx Color
	 * @param group
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getShadowColor(StyleGroup group, int id) {
		return Color.rgb(group.getShadowColor(id).getRed(), group.getShadowColor(id).getGreen(), group.getShadowColor(id).getBlue());
	}

	/**
	 * Get test awt color in group and convert to javafx Color
	 * @param group
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getTextColor(Style group, int id) {
		return Color.rgb(group.getTextColor(id).getRed(), group.getTextColor(id).getGreen(), group.getTextColor(id).getBlue());
	}
	
	/**
	 * Get test background awt color in group and convert to javafx Color
	 * @param group
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getTextBackgroundColor(Style group, int id) {
		return Color.rgb(group.getTextBackgroundColor(id).getRed(), group.getTextBackgroundColor(id).getGreen(), group.getTextBackgroundColor(id).getBlue());
	}
	
	public static Color getShadowColor(Style group, int id) {
		return Color.rgb(group.getShadowColor(id).getRed(), group.getShadowColor(id).getGreen(), group.getShadowColor(id).getBlue());
	}

	public static Color getColor(java.awt.Color awtColor) {
		int r = awtColor.getRed();
		int g = awtColor.getGreen();
		int b = awtColor.getBlue();
		int a = awtColor.getAlpha();
		double opacity = a / 255.0 ;
		
		return Color.rgb(r, g, b, opacity);
	}

	public static Color getStrokeColor(Style group, int id) {
		return Color.rgb(group.getStrokeColor(id).getRed(), group.getStrokeColor(id).getGreen(), group.getStrokeColor(id).getBlue());
	}
	
	
}
