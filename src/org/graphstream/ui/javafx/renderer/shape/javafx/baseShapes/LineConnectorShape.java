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
  
package org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.FillableLine;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.ShadowableLine;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.StrokableLine;

public abstract class LineConnectorShape extends ConnectorShape {
	
	public FillableLine fillableLine ;
	public StrokableLine strokableLine;
	public ShadowableLine shadowableLine;
	
	public LineConnectorShape() {
		this.fillableLine = new FillableLine() ;
		this.strokableLine = new StrokableLine() ;
		this.shadowableLine = new ShadowableLine() ;
	}
	
	
	public void configureForGroup(Backend bck, Style style, DefaultCamera2D camera) {
		super.configureForGroup(bck, style, camera);
		fillableLine.configureFillableLineForGroup(bck, style, camera, theSize);
		strokableLine.configureStrokableLineForGroup(style, camera);
		shadowableLine.configureShadowableLineForGroup(style, camera);
 	}
 
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, DefaultCamera2D camera) {
		fillableLine.configureFillableLineForElement(element.getStyle(), camera, element);
		super.configureForElement(bck, element, skel, camera);
	}
}