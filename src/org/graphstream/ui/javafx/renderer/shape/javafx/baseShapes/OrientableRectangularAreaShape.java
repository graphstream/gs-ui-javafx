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

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.Orientable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

public class OrientableRectangularAreaShape extends RectangularAreaShape {
	Orientable orientable ;
	
	Point3 p = null;
	double angle = 0.0;
	double w = 0.0;
	double h = 0.0;
	boolean oriented = false;
	
	public OrientableRectangularAreaShape() {
		orientable = new Orientable();
	}
	
	public void configureForGroup(Backend bck, Style style, DefaultCamera2D camera) {
		super.configureForGroup(bck, style, camera);
		orientable.configureOrientableForGroup(style, camera);
		oriented = (style.getSpriteOrientation() != StyleConstants.SpriteOrientation.NONE);
	}
	
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, DefaultCamera2D camera) {
		super.configureForElement(bck, element, skel, camera);
		orientable.configureOrientableForElement(camera, (GraphicSprite) element /* Check This XXX TODO !*/);
	}
	
	public void make(Backend backend, DefaultCamera2D camera) {make(backend, false, camera);}
	
 	public void makeShadow(Backend backend, DefaultCamera2D camera) {make(backend, true, camera);}

	private void make(Backend bck, boolean forShadow, DefaultCamera2D camera) {
		if (oriented) {
			Vector2 theDirection = new Vector2(
					orientable.target.x - area.theCenter.x,
					orientable.target.y - area.theCenter.y );
			
			theDirection.normalize();
		
			double x = area.theCenter.x;
			double y = area.theCenter.y;
		
			if( forShadow ) {
				x += shadowable.theShadowOff.x;
				y += shadowable.theShadowOff.y;
			}
		
			p = camera.transformGuToPx(x, y, 0); // Pass to pixels, the image will be drawn in pixels.
			angle = Math.acos(theDirection.dotProduct( 1, 0 ));
		
			if( theDirection.y() > 0 )			// The angle is always computed for acute angles
				angle = ( Math.PI - angle );
	
			w = camera.getMetrics().lengthToPx(area.theSize.x, Units.GU);
			h = camera.getMetrics().lengthToPx(area.theSize.y, Units.GU);
			((Form) theShape()).setFrame(0, 0, w, h);
		} else {
			if (forShadow)
				super.makeShadow(bck, camera);
			else
				super.make(bck, camera);
		}
	}
 	
	public void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skel) {
		make(bck, false, camera);
		
		GraphicsContext g = bck.graphics2D();
 		
 		if (oriented) {
	 		Affine Tx = g.getTransform();
	 		Affine Tr = new Affine();
	 		Tr.appendTranslation( p.x, p.y );			// 3. Position the image at its position in the graph.
	 		Tr.appendRotation( angle );					// 2. Rotate the image from its center.
	 		Tr.appendTranslation( -w/2, -h/2 );			// 1. Position in center of the image.
	 		g.setTransform( Tr );						// An identity matrix.
	 		strokable.stroke(g, theShape());
	 		fillable.fill(g, theShape(), camera);
	 		g.setTransform( Tx );						// Restore the original transform
	 		((Form) theShape()).setFrame(area.theCenter.x-w/2, area.theCenter.y-h/2, w, h);
	 		decorArea(bck, camera, skel.iconAndText, element, theShape());
 		}
 		else {
 			super.render(bck, camera, element, skel);
 		}
	}
	
	
 	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skel) {
 		make(bck, true, camera);
 		
 		GraphicsContext g = bck.graphics2D();
 		
 		if (oriented) {
	 		Affine Tx = g.getTransform();
	 		Affine Tr = new Affine();
	 		Tr.appendTranslation( p.x, p.y );								// 3. Position the image at its position in the graph.
	 		Tr.appendRotation( angle );										// 2. Rotate the image from its center.
	 		Tr.appendTranslation( -w/2, -h/2 );								// 1. Position in center of the image.
	 		g.setTransform( Tr );									// An identity matrix.
 			shadowable.cast(g, theShape());
	 		g.setTransform( Tx );									// Restore the original transform
 		} else {
 			super.renderShadow(bck, camera, element, skel);
 		}
 	}
}
