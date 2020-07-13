/*
 * This file is part of GraphStream <http://graphstream-project.org>.
 * 
 * GraphStream is a library whose purpose is to handle static or dynamic
 * graph, create them from scratch, file or any source and display them.
 * 
 * This program is free software distributed under the terms of two licenses, the
 * CeCILL-C license that fits European law, and the GNU Lesser General Public
 * License. You can  use, modify and/ or redistribute the software under the terms
 * of the CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
 * URL <http://www.cecill.info> or under the terms of the GNU LGPL as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C and LGPL licenses and that you accept their terms.
 */

 /**
  * @author Antoine Dutot <antoine.dutot@graphstream-project.org>
  * @author Guilhelm Savin <guilhelm.savin@graphstream-project.org>
  * @author Hicham Brahimi <hicham.brahimi@graphstream-project.org>
  */
  
package org.graphstream.ui.javafx.util;

import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.graphicGraph.stylesheet.Style;

import javafx.scene.paint.Color;

/**
 * Static class used for translate awt color to javafx
 */
public class ColorManager {
	/**
	 * Get fill awt color in styleGroup and convert to javafx Color
	 * @param stylegroup
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getFillColor(StyleGroup group, int id) {
		return getColor(group.getFillColor(id));
	}
	
	/**
	 * Get fill awt color in group and convert to javafx Color
	 * @param style
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getFillColor(Style group, int id) {
		return getColor(group.getFillColor(id));
	}
	
	/**
	 * Get stroke awt color in styleGroup and convert to javafx Color
	 * @param stylegroup
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getStrokeColor(StyleGroup group, int id) {
		return getColor(group.getStrokeColor(id));
	}
	
	/**
	 * Get stroke awt color in group and convert to javafx Color
	 * @param style
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getStrokeColor(Style group, int id) {
		return getColor(group.getStrokeColor(id));
	}
	
	/**
	 * Get canvas awt color in styleGroup and convert to javafx Color
	 * @param stylegroup
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getCanvasColor(StyleGroup group, int id) {
		return getColor(group.getCanvasColor(id));
	}
	
	/**
	 * Get shadow awt color in styleGroup and convert to javafx Color
	 * @param styleGroup
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getShadowColor(StyleGroup group, int id) {
		return getColor(group.getShadowColor(id));
	}
	
	/**
	 * Get shadow awt color in group and convert to javafx Color
	 * @param style
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getShadowColor(Style group, int id) {
		return getColor(group.getShadowColor(id));
	}

	/**
	 * Get text awt color in group and convert to javafx Color
	 * @param style
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getTextColor(Style group, int id) {
		return getColor(group.getTextColor(id));
	}
	
	/**
	 * Get text background awt color in group and convert to javafx Color
	 * @param style
	 * @param id
	 * @return javafx.scene.paint.Color
	 */
	public static Color getTextBackgroundColor(Style group, int id) {
		return getColor(group.getTextBackgroundColor(id));
	}

	/**
	 * Convert java.awt.Color to javafx.scene.paint.Color
	 * @param java.awt.Color 
	 * @return javafx.scene.paint.Color
	 */
	public static Color getColor(org.graphstream.ui.graphicGraph.stylesheet.Color gscoreColor) {
		int r = gscoreColor.getRed();
		int g = gscoreColor.getGreen();
		int b = gscoreColor.getBlue();
		int a = gscoreColor.getAlpha();
		double opacity = a / 255.0 ;
		
		return Color.rgb(r, g, b, opacity);
	}
}
