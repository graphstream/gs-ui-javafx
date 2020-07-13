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
  
package org.graphstream.ui.javafx.renderer.shape;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;

/** Base for all shapes. */
public interface Shape {
	
	/** Configure as much as possible the graphics before painting several version of this shape
     * at different positions.
     * @param backend The rendering back-end.
     * @param style The style for the group.
     * @param camera the view parameters. */
	void configureForGroup(Backend backend, Style style, DefaultCamera2D camera) ;
	
	/** Configure all the dynamic and per element settings.
	  * Some configurations can only be done before painting the element, since they change for
	  * each element.
	  * @param backend The rendering back-end.
	  * @param element The specific element to render.
	  * @param skeleton The element geometry and information.
	  * @param camera the view parameters. */
	void configureForElement(Backend backend, GraphicElement element, Skeleton skeleton, DefaultCamera2D camera);
	
	/** Must create the shape from informations given earlier, that is, resize it if needed and
     * position it, and do all the things that are specific to each element, and cannot be done
     * for the group of elements.
     * This method is made to be called inside the render() method, hence it is protected.
     * @param backend The rendering back-end.
     * @param camera the view parameters. */
	void make(Backend backend, DefaultCamera2D camera);
	
	/** Same as {@link #make(Camera)} for the shadow shape. The shadow shape may be moved and
	  * resized compared to the original shape. This method is made to be called inside the
	  * renderShadow() method, hence it is protected. */
 	void makeShadow(Backend backend, DefaultCamera2D camera);
 
 	/** Render the shape for the given element.
     * @param backend The rendering back-end.
     * @param camera The view parameters.
     * @param element The element to render.
     * @param skeleton The element geometry and information. */
 	void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton);
  
  	/** Render the shape shadow for the given element. The shadow is rendered in a different pass
     * than usual rendering, therefore it is a separate method. */
 	void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton);
}
