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
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.Connector;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.AreaOnConnectorShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Ellipse2D;
import org.graphstream.ui.javafx.util.CubicCurve;
import org.graphstream.ui.javafx.util.ShapeUtil;
import org.graphstream.ui.javafx.util.AttributeUtils.Tuple;

import javafx.scene.canvas.GraphicsContext;

public class CircleOnEdge extends AreaOnConnectorShape {
	Ellipse2D theShape = new Ellipse2D();

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
			makeOnLine(  forShadow, camera );
	}
	
	private void makeOnCurve(boolean forShadow, DefaultCamera2D camera) {
		Tuple<Point2, Double> tuple = CubicCurve.approxIntersectionPointOnCurve( theEdge, theConnector, camera );
		Point2 p1 = tuple.x ;
		double t = tuple.y ;
		
		Style style  = theEdge.getStyle();
				
		Point3 p2 = CubicCurve.eval( theConnector.fromPos(), theConnector.byPos1(), theConnector.byPos2(), theConnector.toPos(), t-0.1f );
		Vector2 dir = new Vector2( p1.x - p2.x, p1.y - p2.y );
		dir.normalize();
		dir.scalarMult( theSize.x/2 );

		// Create a polygon.
		theShape.setFrame( (p1.x-dir.x())-(theSize.x/2), (p1.y-dir.y())-(theSize.y/2), theSize.x, theSize.y );			
	}

	private void makeOnLine(boolean forShadow, DefaultCamera2D camera) {
		double off = ShapeUtil.evalTargetRadius2D( theEdge, camera ) + ((theSize.x+theSize.y)/4);
		Vector2 theDirection = new Vector2(
				theConnector.toPos().x - theConnector.fromPos().x,
				theConnector.toPos().y - theConnector.fromPos().y );
		
		theDirection.normalize();
		  
		double x = theCenter.x - ( theDirection.x() * off );
		double y = theCenter.y - ( theDirection.y() * off );
		//val perp = new Vector2( theDirection(1), -theDirection(0) )
				
		//perp.normalize
		theDirection.scalarMult( theSize.x );
		//perp.scalarMult( theSize.y )
		
		if( forShadow ) {
			x += shadowable.theShadowOff.x;
			y += shadowable.theShadowOff.y;
		}
		
		// Set the shape.	
		theShape.setFrame( x-(theSize.x/2), y-(theSize.y/2), theSize.x, theSize.y );
	}

	@Override
	public void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		GraphicsContext g = bck.graphics2D();
		make( false, camera );
		strokable.stroke( g, theShape );
		fillable.fill( g, theShape, camera );
	}
	
	public double lengthOfCurve( Connector c ) {
		// Computing a curve real length is really heavy.
		// We approximate it using the length of the 3 line segments of the enclosing
		// control points.
		return ( c.fromPos().distance( c.byPos1() ) + c.byPos1().distance( c.byPos2() ) + c.byPos2().distance( c.toPos() ) ) * 0.75f;
	}

	@Override
	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		make( true, camera );
		shadowable.cast(bck.graphics2D(), theShape );
	}
	
}