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
import org.graphstream.ui.javafx.renderer.AreaSkeleton;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.Decorable;
import org.graphstream.ui.javafx.renderer.shape.Shape;
import org.graphstream.ui.javafx.renderer.shape.javafx.Area;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Fillable;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Shadowable;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Strokable;

public abstract class AreaShape extends Decorable implements Shape {
	
	public Fillable fillable ;
	public Strokable strokable ;
	public Shadowable shadowable ;
	public Area area ;
	
	public AreaShape() {
		this.fillable = new Fillable();
		this.strokable = new Strokable();
		this.shadowable = new Shadowable();
		this.area = new Area();
	}
	
	
	public void configureForGroup(Backend bck, Style style, DefaultCamera2D camera) {
 	  	fillable.configureFillableForGroup(bck, style, camera);
 	  	strokable.configureStrokableForGroup(style, camera);
 	  	shadowable.configureShadowableForGroup(style, camera);
 	  	configureDecorableForGroup(style, camera);
 	  	area.configureAreaForGroup(style, camera);
 	}
 
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, DefaultCamera2D camera) {
		fillable.configureFillableForElement(element.getStyle(), camera, element);
		configureDecorableForElement(bck, camera, element, skel);
		area.configureAreaForElement(bck, camera, (AreaSkeleton)skel, element, theDecor);
	}
}