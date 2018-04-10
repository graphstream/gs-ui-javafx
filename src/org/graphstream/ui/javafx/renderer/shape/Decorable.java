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

import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.StyleGroup;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.IconAndText;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShapeDecor;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form;

import javafx.geometry.Bounds;

/** Trait for shapes that can be decorated by an icon and/or a text. */
public class Decorable extends HasSkel {
	/** The string of text of the contents. */
	public String text = null;
 
	/** The text and icon. */
	public ShapeDecor theDecor = null ;
  
 	/** Paint the decorations (text and icon). */
 	public void decorArea(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, GraphicElement element, Form shape ) {
 	  	boolean visible = true ;
 	  	if( element != null ) visible = camera.isTextVisible( element );
 	  	if( theDecor != null && visible ) {
 	  		Bounds bounds = shape.getBounds();
 	  		theDecor.renderInside(backend, camera, iconAndText, bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY() );
 	  	}
 	}
	
	public void decorConnector(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, GraphicElement element, Form shape ) {
		boolean visible = true ;
 	  	if( element != null ) visible = camera.isTextVisible( element );
 	  	if( theDecor != null && visible ) {
 	  		if ( element instanceof GraphicEdge ) {
 	  			GraphicEdge edge = (GraphicEdge)element;
 	  			if((skel != null) && (skel.isCurve())) {
 	  				theDecor.renderAlong(backend, camera, iconAndText, skel);
 	  			} 
 	  			else {
 	  				theDecor.renderAlong(backend, camera, iconAndText, edge.from.x, edge.from.y, edge.to.x, edge.to.y);
 	  			}
 	  		}
 	  		else {
 	 	  		Bounds bounds = shape.getBounds();
 	  			theDecor.renderAlong(backend, camera, iconAndText, bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY() );
 	  		}
 	  	}
	}
	
	/** Configure all the static parts needed to decor the shape. */
  	public void configureDecorableForGroup( Style style, DefaultCamera2D camera) {
		/*if( theDecor == null )*/ theDecor = ShapeDecor.apply( style );
  	}
  	/** Setup the parts of the decor specific to each element. */
  	public void configureDecorableForElement(Backend backend, DefaultCamera2D camera, GraphicElement element, Skeleton skel) {
  		text = element.label;
  		if( skel != null ) {
  			StyleGroup style = element.getStyle();
  			skel.iconAndText = IconAndText.apply( style, camera, element );
  			if( style.getIcon() != null && style.getIcon().equals( "dynamic" ) && element.hasAttribute( "ui.icon" ) ) {
  				String url = element.getLabel("ui.icon").toString();
  				skel.iconAndText.setIcon(backend, url);
  			}
  			skel.iconAndText.setText(backend, element.label);
  		}
  	}
}