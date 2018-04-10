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

import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.AreaSkeleton;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.Connector;
import org.graphstream.ui.javafx.util.AttributeUtils.Tuple;

import javafx.geometry.Point2D;

/** Utility methods to deal with cubic Bézier curves. */
public class CubicCurve {
	/**
	 * Evaluate a cubic Bézier curve according to control points `x0`, `x1`,
	 * `x2` and `x3` and return the position at parametric position `t` of the
	 * curve.
	 * 
	 * @return The coordinate at parametric position `t` on the curve.
	 */
	public static double eval(double x0, double x1, double x2, double x3, double t) {
		double tt = (1f - t);

		return x0 * (tt * tt * tt) + 3f * x1 * t * (tt * tt) + 3f * x2
				* (t * t) * tt + x3 * (t * t * t);
	}

	/**
	 * Evaluate a cubic Bézier curve according to control points `p0`, `p1`,
	 * `p2` and `p3` and return the position at parametric position `t` of the
	 * curve.
	 * 
	 * @return The point at parametric position `t` on the curve.
	 */
	public static Point2 eval(Point2 p0, Point2 p1, Point2 p2, Point2 p3,
			double t) {
		return new Point2(eval(p0.x, p1.x, p2.x, p3.x, t), eval(p0.y, p1.y,
				p2.y, p3.y, t));
	}

	/** Evaluate a cubic Bézier curve according to control points `p0`, `p1`, `p2` and `p3` and 
	 * return the position at parametric position `t` of the curve.
	 * @return The point at parametric position `t` on the curve. */
	public static Point3 eval(Point3 p0, Point3 p1, Point3 p2, Point3 p3,
			double t) {
		return new Point3(eval(p0.x, p1.x, p2.x, p3.x, t),
	               eval(p0.y, p1.y, p2.y, p3.y, t),
	               eval(p0.z, p1.z, p2.z, p3.z, t));
	}
	
	/**
	 * Evaluate a cubic Bézier curve according to control points `p0`, `p1`,
	 * `p2` and `p3` and return the position at parametric position `t` of the
	 * curve.
	 * 
	 * @return The point at parametric position `t` on the curve.
	 */
	public static Point2D eval(Point2D p0, Point2D p1,
			Point2D p2, Point2D p3, double t) {
		return new Point2D(eval(p0.getX(), p1.getX(), p2.getX(), p3.getX(), t), eval(p0.getY(),
				p1.getY(), p2.getY(), p3.getY(), t));
	}

	/**
	 * Evaluate a cubic Bézier curve according to control points `p0`, `p1`,
	 * `p2` and `p3` and store the position at parametric position `t` of the
	 * curve in `result`.
	 * 
	 * @return the given reference to `result`.
	 */
	public static Point2 eval(Point2 p0, Point2 p1, Point2 p2, Point2 p3,
			double t, Point2 result) {
		result.set(eval(p0.x, p1.x, p2.x, p3.x, t),
				eval(p0.y, p1.y, p2.y, p3.y, t));
		return result;
	}

	/**
	 * Derivative of a cubic Bézier curve according to control points `x0`,
	 * `x1`, `x2` and `x3` at parametric position `t` of the curve.
	 * 
	 * @return The derivative at parametric position `t` on the curve.
	 */
	public static double derivative(double x0, double x1, double x2, double x3,
			double t) {
		return 3 * (x3 - 3 * x2 + 3 * x1 - x0) * t * t + 2
				* (3 * x2 - 6 * x1 + 3 * x0) * t + (3 * x1 - 3 * x0);
	}

	/**
	 * Derivative point of a cubic Bézier curve according to control points
	 * `x0`, `x1`, `x2` and `x3` at parametric position `t` of the curve.
	 * 
	 * @return The derivative point at parametric position `t` on the curve.
	 */
	public static Point2 derivative(Point2 p0, Point2 p1, Point2 p2, Point3 p3,
			double t) {
		return new Point2(derivative(p0.x, p1.x, p2.x, p3.x, t), derivative(
				p0.y, p1.y, p2.y, p3.y, t));
	}

	/**
	 * Store in `result` the derivative point of a cubic Bézier curve according
	 * to control points `x0`, `x1`, `x2` and `x3` at parametric position `t` of
	 * the curve.
	 * 
	 * @return the given reference to `result`.
	 */
	public static Point2 derivative(Point2 p0, Point2 p1, Point2 p2, Point3 p3,
			double t, Point2 result) {
		result.set(derivative(p0.x, p1.x, p2.x, p3.x, t),
				derivative(p0.y, p1.y, p2.y, p3.y, t));
		return result;
	}

	/**
	 * The perpendicular vector to the curve defined by control points `p0`,
	 * `p1`, `p2` and `p3` at parametric position `t`.
	 * 
	 * @return A vector perpendicular to the curve at position `t`.
	 */
	public static Vector2 perpendicular(Point2 p0, Point2 p1, Point2 p2,
			Point2 p3, double t) {
		return new Vector2(derivative(p0.y, p1.y, p2.y, p3.y, t), -derivative(
				p0.x, p1.x, p2.x, p3.x, t));
	}

	/**
	 * Store in `result` the perpendicular vector to the curve defined by
	 * control points `p0`, `p1`, `p2` and `p3` at parametric position `t`.
	 * 
	 * @return the given reference to `result`.
	 */
	public static Vector2 perpendicular(Point2 p0, Point2 p1, Point2 p2,
			Point2 p3, double t, Vector2 result) {
		result.set(derivative(p0.y, p1.y, p2.y, p3.y, t),
				-derivative(p0.x, p1.x, p2.x, p3.x, t));
		return result;
	}

	/**
	 * The perpendicular vector to the curve defined by control points `p0`,
	 * `p1`, `p2` and `p3` at parametric position `t`.
	 * 
	 * @return A vector perpendicular to the curve at position `t`.
	 */
	public static Point2D perpendicular(Point2D p0,
			Point2D p1, Point2D p2, Point2D p3, double t) {
		return new Point2D(derivative(p0.getY(), p1.getY(), p2.getY(), p3.getY(), t),
				-derivative(p0.getX(), p1.getX(), p2.getX(), p3.getX(), t));
	}
	
	
	/** A quick and dirty hack to evaluate the length of a cubic bezier curve. This method simply compute
	 * the length of the three segments of the enclosing polygon and scale them. This is fast but
	 * inaccurate. */
	public static double approxLengthOfCurveQuickAndDirty( Connector c ) {
		// Computing a curve real length is really heavy.
		// We approximate it using the length of the 3 line segments of the enclosing
		// control points.
		return ( c.fromPos().distance( c.byPos1() )*0.5f + c.byPos1().distance( c.byPos2() )*0.8f + c.byPos2().distance( c.toPos() )*0.5f );
	}
	
	/** Evaluate the length of a Bézier curve by taking four points on the curve and summing the lengths of
	 * the five segments thus defined. */
	public static double approxLengthOfCurveQuick( Connector c ) {
		Point2 ip0 = CubicCurve.eval( c.fromPos(), c.byPos1(), c.byPos2(), c.toPos(), 0.1f );
		Point2 ip1 = CubicCurve.eval( c.fromPos(), c.byPos1(), c.byPos2(), c.toPos(), 0.3f );
		Point2 ip2 = CubicCurve.eval( c.fromPos(), c.byPos1(), c.byPos2(), c.toPos(), 0.7f );
		Point2 ip3 = CubicCurve.eval( c.fromPos(), c.byPos1(), c.byPos2(), c.toPos(), 0.9f );
		
		return ( c.fromPos().distance( ip0 ) + ip0.distance( ip1 ) + ip1.distance( ip2 ) + ip2.distance( ip3 ) + ip3.distance( c.toPos() ) );
	}
	
	/** Evaluate the length of a Bézier curve by taking n points on the curve and summing the lengths of
	 * the n+1 segments thus defined. */
	public static double approxLengthOfCurve( Connector c ) {
		double inc = 0.1;
		double i   = inc;
		double len = 0.0;
		Point2 p0  = c.fromPos();
		
		while( i < 1f ) {
			Point2 p = CubicCurve.eval( c.fromPos(), c.byPos1(), c.byPos2(), c.toPos(), i );
			i += inc;
			len += p0.distance( p );
			p0 = p;
		}
		
		len += p0.distance( c.toPos() );
		
		return len;
	}
	
	/** Return two points, one inside and the second outside of the shape of the destination node
	 * of the given `edge`, the points can be used to deduce a vector along the Bézier curve entering
	 * point in the shape. */
	public static Tuple<Point2, Point2> approxVectorEnteringCurve( GraphicEdge edge, Connector c, DefaultCamera2D camera ) {
		GraphicNode node = edge.to;
		AreaSkeleton info = (AreaSkeleton)node.getAttribute(Skeleton.attributeName);
		double w = 0.0;
		double h = 0.0;
		
		if( info != null ) {
			w = info.theSize.x;
			h = info.theSize.y;
		}
		else {
			w = camera.getMetrics().lengthToGu( node.getStyle().getSize(), 0 );
			h = w ;
			if( node.getStyle().getSize().size() > 1 ) 
				camera.getMetrics().lengthToGu( node.getStyle().getSize(), 1 ) ;
		}
		
		boolean searching = true;
		Point3 p0 = c.fromPos();
		Point3 p1 = c.toPos();
		double inc = 0.1f; 
		double i = inc;
		
		while( searching ) {
			p1 = CubicCurve.eval( c.fromPos(), c.byPos1(), c.byPos2(), c.toPos(), i );
			
			if( ShapeUtil.isPointIn( node, p1, w, h ) ) {
				searching = false;
			} else {
				p0 = p1;
			}
		}
		
		return new Tuple<Point2, Point2>(p0, p1);
	}
	
	/** Use a dichotomy method to evaluate the intersection between the `edge` destination node
	 * shape and the Bézier curve of the connector `c`. The returned values are the point of
	 * intersection as well as the parametric position of this point on the curve (a float).
	 * The maximal recursive depth of the dichotomy is fixed to 7 here.
	 * @return A 2-tuple made of the point of intersection and the associated parametric position.
	 */
	public static Tuple<Point2, Double> approxIntersectionPointOnCurve( GraphicEdge edge, Connector c, DefaultCamera2D camera ) {
		return approxIntersectionPointOnCurve( edge, c, camera, 7 );
	}
		
	/** Use a dichotomy method to evaluate the intersection between the `edge` destination node
	 * shape and the Bézier curve of the connector `c`. The returned values are the point of
	 * intersection as well as the parametric position of this point on the curve (a float).
	 * The dichotomy can recurse at any level to increase precision, often 7 is sufficient, the
	 * `maxDepth` parameter allows to set this depth.
	 * @return A 2-tuple made of the point of intersection and the associated parametric position.
	 */
	public static Tuple<Point2,Double> approxIntersectionPointOnCurve( GraphicEdge edge, Connector c, DefaultCamera2D camera, int maxDepth ) {
		GraphicNode node = edge.to;
		AreaSkeleton info = (AreaSkeleton)node.getAttribute(Skeleton.attributeName);
		double w = 0.0;
		double h = 0.0;
		
		if( info != null ) {
			w = info.theSize.x;
			h = info.theSize.y;
		} 
		else {
			w = camera.getMetrics().lengthToGu( node.getStyle().getSize(), 0 );
			h = w ;
			if( node.getStyle().getSize().size() > 1 ) 
				camera.getMetrics().lengthToGu( node.getStyle().getSize(), 1 );
		}
			
		boolean searching = true;
		Point3 p = c.toPos(); //        = CubicCurve.eval( c.fromPos, c.byPos1, c.byPos2, c.toPos, 0.5f )
		double tbeg = 0.0;
		double tend = 1.0;
		double t = 0.0;
		double depth = 0;
		
		while( depth < maxDepth ) {
			t = tbeg + ( (tend - tbeg ) / 2 );
			p = CubicCurve.eval( c.fromPos(), c.byPos1(), c.byPos2(), c.toPos(), t );
			
			if( ShapeUtil.isPointIn( node, p, w, h ) ) {
				tend = t;
			} 
			else {
				tbeg = t;
			}
			
			depth += 1;
		}
		
		return new Tuple<Point2,Double>(p, t);
	}
	
	
	// =================================================================================================
	// A simple test for the cubic curve eval, derivative and perpendicular methods.	
	// =================================================================================================
	/*public static void main( String[] args ) {
		JFrame frame = new JFrame("Test Beziers");
		MyCanvas canvas = new MyCanvas();
				
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.add( canvas, BorderLayout.CENTER );
		frame.setSize( 400, 420 );
		frame.setVisible( true );
	}*/
}
/*
@SuppressWarnings("serial")
class MyCanvas extends JPanel {
	
	
	@Override
	protected void paintComponent(Graphics gg) {
		Graphics2D g  = (Graphics2D)gg;
		Point2D P0 = new Point2D( 10, 390 );
		Point2D P1 = new Point2D( 50, 10 );
		Point2D P2 = new Point2D( 350, 390 );
		Point2D P3 = new Point2D( 390, 10 );
		
		CubicCurve2D curve = new CubicCurve2D();
		Line2D line  = new Line2D();
		curve.setCurve( P0, P1, P2, P3 );
		
		g.setColor( Color.BLUE );
		g.draw( curve );
		g.setColor( Color.RED );
		
		line.setLine( P0, P1 );
		g.draw( line );
		line.setLine( P1, P2 );
		g.draw( line );
		line.setLine( P2, P3 );
		g.draw( line );
		
		double t = 0.0;
		
		g.setColor( Color.GREEN );
		while( t < 1 ) {
			Point2D P = CubicCurve.eval( P0, P1, P2, P3, t );
			Point2D V = CubicCurve.perpendicular( P0, P1, P2, P3, t );
			Point2D T = new Point2D( P.x+V.x, P.y+V.y );
			
			line.setLine( P, T );
			g.draw( line );
			
			t += 0.01;
		}
	}
}
*/