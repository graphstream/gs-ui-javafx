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
  
package org.graphstream.ui.javafx.renderer;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.FxGraphRenderer;

import javafx.scene.canvas.GraphicsContext;

public abstract class StyleRenderer implements GraphicElement.SwingElementRenderer {
	
	protected StyleGroup group ;
	protected boolean hadEvents = false ;
	
	public static StyleRenderer apply(StyleGroup style, FxGraphRenderer mainRenderer) {
		switch (style.getType()) {
			case NODE: return NodeRenderer.apply(style, mainRenderer) ; 
			case EDGE: return new EdgeRenderer(style, mainRenderer) ; 
			case SPRITE: return SpriteRenderer.apply(style, mainRenderer) ; 
			case GRAPH: System.out.println("we got a graph%n"); return null ;
			default: throw new RuntimeException("WTF?");
		}
	}
	
	public StyleRenderer(StyleGroup group) {
		this.group = group ;
	}
	
// Command
	
	/** Render the shadow of all (visible) elements of the group. */
	public void renderShadow(Backend bck, DefaultCamera2D camera) { render(bck, camera, true, renderShadow); }
 
	/** Render all the (visible) elements of the group. */
	public void render(Backend bck, DefaultCamera2D camera) { render(bck, camera, false, renderElement); }
	
	
	/** Main rendering method.
	 * 
     * This works in three phases:
     * - draw all "bulk" elements using renderElement()
     * - draw all "dynamic" elements using renderElement().
     * - draw all "event" elements using renderElement().
     * 
     * Before drawing, the setupRenderingPass() and pushStyle() methods are called. The phase 1 is
     * run. Then for each dynamic element in phase 2, before calling renderElement, for each element
     * the pushDynStyle() method is called.
     * Then for each element modified by an event, in phase 3, the before drawing the element, the
     * event is enabled, then pushStyle() is called, then the element is drawn, and finally the
     * event is disabled.
     * 
     * This rendering pass is made both for shadows and for regular drawing. The shadow and render
     * arguments allow to specify that we are rendering for shadow, and what element rendering
     * method to use (renderElement() or renderShadow()). */
	public void render(Backend bck, DefaultCamera2D camera, boolean shadow, FunctionInVoid<Backend, DefaultCamera2D, GraphicElement> render) {
		setupRenderingPass(bck, camera, shadow);
		pushStyle(bck, camera, shadow);

//var T1 = System.currentTimeMillis
		group.bulkElements().forEach( e -> {
			GraphicElement ge = (GraphicElement)e;
	
			if(camera.isVisible(ge))
				render.apply(bck, camera, ge);
			else 
				elementInvisible(bck, camera, ge);
		});
//var T2 = System.currentTimeMillis
			
		if(group.hasDynamicElements()) {
			group.dynamicElements().forEach( e -> {
				GraphicElement ge = (GraphicElement)e;

				if(camera.isVisible(ge)) {
					if(! group.elementHasEvents(ge)) {
						pushDynStyle(bck, camera, ge);
						render.apply(bck, camera, ge);
					}
				} 
				else {
					elementInvisible(bck, camera, ge);
				}
			});
		}

		if(group.hasEventElements()) {
			group.elementsEvents().forEach( event -> {
				GraphicElement ge = (GraphicElement)event.getElement();
				
				if(camera.isVisible(ge)) {
					event.activate();
					pushStyle(bck, camera, shadow);
					render.apply(bck, camera, ge);
					event.deactivate();
				} 
				else {
					elementInvisible(bck, camera, ge);
				}
			});
			
			hadEvents = true;
		}
		else {
			hadEvents = false;
		}
 
		endRenderingPass(bck, camera, shadow);
   }
	
	// Methods to implement in each renderer
	 
	/** Called before the whole rendering pass for all elements.
	 * @param bck The rendering back-end.
	 * @param camera The camera.
	 * @param forShadow true if we are in the shadow rendering pass. */
	public abstract void setupRenderingPass(Backend bck, DefaultCamera2D camera, boolean forShadow);
	
	/** Called before the rendering of bulk and event elements.
	 * @param bck The rendering back-end.
	 * @param camera The camera.
	 * @param forShadow true if we are in the shadow rendering pass. */
	public abstract void pushStyle(Backend bck, DefaultCamera2D camera, boolean forShadow);
	
	/** Called before the rendering of elements on dynamic styles. This must only change the style
	 * properties that can change dynamically.
	 * @param bck The rendering back-end.
	 * @param camera The camera.
	 * @param element The graphic element concerned by the dynamic style change. */
	public abstract void pushDynStyle(Backend bck, DefaultCamera2D camera, GraphicElement element);
	
	/** Render a single element knowing the style is already prepared. Elements that are not visible
	 * are not drawn.
	 * @param bck The rendering back-end.
	 * @param camera The camera.
	 * @param element The element to render. */
	public abstract void renderElement(Backend bck, DefaultCamera2D camera, GraphicElement element);
	
	FunctionInVoid<Backend, DefaultCamera2D, GraphicElement> renderElement = (Backend bck, DefaultCamera2D camera, GraphicElement element) -> {
		renderElement(bck, camera, element);
	};
	
	/** Render the shadow of the element.
	 * @param bck The rendering back-end.
	 * @param camera The camera.
	 * @param element The element to render. */
	public abstract void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element);
	
	FunctionInVoid<Backend, DefaultCamera2D, GraphicElement> renderShadow = (Backend bck, DefaultCamera2D camera, GraphicElement element) -> {
		renderShadow(bck, camera, element);
	};
	
	/** Called during rendering in place of {@link #renderElement(GraphicsContext, DefaultCamera2D, GraphicElement)}
	 * to signal that the given element is not inside the view. The renderElement() method will be
	 * called as soon as the element becomes visible anew.
	 * @param bck The rendering back-end.
	 * @param camera The camera.
	 * @param element The element to render. */
	public abstract void elementInvisible(Backend bck, DefaultCamera2D camera, GraphicElement element);
	
	/** Called at the end of the rendering pass. 
	 * @param bck The rendering back-end.
	 * @param camera The camera.
	 * @param forShadow true if we are in the shadow rendering pass. */
	public abstract void endRenderingPass(Backend bck, DefaultCamera2D camera, boolean forShadow) ;
}



@FunctionalInterface
interface FunctionInVoid<A,B,C> {
	
	/**
	 * Applies this function to the given arguments.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 * @param c the third function argument
	 * @return the function result
	 */
	void apply(A a, B b, C c);
}
