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
