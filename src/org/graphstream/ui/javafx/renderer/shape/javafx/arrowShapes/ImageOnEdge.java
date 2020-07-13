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
  
package org.graphstream.ui.javafx.renderer.shape.javafx.arrowShapes;

import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.AreaOnConnectorShape;
import org.graphstream.ui.javafx.util.CubicCurve;
import org.graphstream.ui.javafx.util.ImageCache;
import org.graphstream.ui.javafx.util.ShapeUtil;
import org.graphstream.ui.javafx.util.AttributeUtils.Tuple;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;

public class ImageOnEdge extends AreaOnConnectorShape {
	Image image = null;
	Point3 p = null ;
	double angle = 0.0;
	
	@Override
	public void configureForGroup(Backend bck, Style style, DefaultCamera2D camera) {
		super.configureForGroup(bck, style, camera);
	}
	
	@Override
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, DefaultCamera2D camera) {
		super.configureForElement(bck, element, skel, camera);
		
		String url = element.getStyle().getArrowImage();
				
		if( url.equals( "dynamic" ) ) {
			if( element.hasLabel( "ui.arrow-image" ) )
				url = element.getLabel( "ui.arrow-image" ).toString();
			else
				url = null;
		}
				
		if( url != null ) {
			image = ImageCache.loadImage(url);
			if (image == null) {
				image = ImageCache.dummyImage();
			}
		}
	}
	
	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		make( false, camera );
	}
	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		make( true, camera );
	}
	
	private void make(boolean forShadow, DefaultCamera2D camera) {
		if( theConnector.skel.isCurve() )
			makeOnCurve( forShadow, camera );
		else
			makeOnLine( forShadow, camera );	
	}
	
	private void makeOnCurve(boolean forShadow, DefaultCamera2D camera) {
		Tuple<Point2, Double> tuple = CubicCurve.approxIntersectionPointOnCurve( theEdge, theConnector, camera );
		Point2 p1 = tuple.x ;
		double t = tuple.y ;
		
		Style style  = theEdge.getStyle();
		Point3 p2  = CubicCurve.eval( theConnector.fromPos(), theConnector.byPos1(), theConnector.byPos2(), theConnector.toPos(), t-0.1f );
		Vector2 dir = new Vector2( p1.x - p2.x, p1.y - p2.y );
		
		dir.normalize();
		
		double iw = camera.getMetrics().lengthToGu( image.getWidth(), Units.PX ) / 2;
		double x  = p1.x - ( dir.x() * iw );
		double y  = p1.y - ( dir.y() * iw );
		
		if( forShadow ) {
			x += shadowable.theShadowOff.x;
			y += shadowable.theShadowOff.y;
		}
		
		p = camera.transformGuToPx( x, y, 0 );
		angle = Math.acos( dir.dotProduct( 1, 0 ) );
		
		if( dir.y() > 0 )
			angle = ( Math.PI - angle );
	}
	
	private void makeOnLine(boolean forShadow, DefaultCamera2D camera) {
		double off = ShapeUtil.evalTargetRadius2D( theEdge, camera );
		
		Vector2 theDirection = new Vector2(
				theConnector.toPos().x - theConnector.fromPos().x,
				theConnector.toPos().y - theConnector.fromPos().y );
					
		theDirection.normalize();
				
		double iw = camera.getMetrics().lengthToGu( image.getWidth(), Units.PX ) / 2;
		double x  = theCenter.x - ( theDirection.x() * ( off + iw ) );
		double y  = theCenter.y - ( theDirection.y() * ( off + iw ) );
				
		if( forShadow ) {
			x += shadowable.theShadowOff.x;
			y += shadowable.theShadowOff.y;
		}	
				
		p = camera.transformGuToPx( x, y, 0 );	// Pass to pixels, the image will be drawn in pixels.
		angle = Math.acos( theDirection.dotProduct( 1, 0 ) );
				
		if( theDirection.y() > 0 )			// The angle is always computed for acute angles
			angle = ( Math.PI - angle );
	}

	@Override
	public void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		GraphicsContext g = bck.graphics2D();

 		make( false, camera );
 		// stroke( g, theShape )
 		// fill( g, theShape, camera )
 		
 		if( image != null ) {
 			Affine Tx = g.getTransform();
 			Affine Tr = new Affine();
 			
 			Tr.appendTranslation( p.x, p.y );									// 3. Position the image at its position in the graph.
 			Tr.appendRotation( angle );											// 2. Rotate the image from its center.
 			Tr.appendTranslation( -image.getWidth()/2, -image.getHeight()/2 );	// 1. Position in center of the image.
 			g.setTransform( Tr );										// An identity matrix.
 		
 			g.drawImage(image, 0, 0);								// Paint the image.
 			g.setTransform( Tx );										// Restore the original transform
 		}
	}

	@Override
	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		//make( true, camera );
		//shadowable.cast( g, theShape );
	}	
}