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
  
package org.graphstream.ui.javafx.renderer.shape.javafx.spriteShapes;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.SpriteOrientation;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.ConnectorSkeleton;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.Skeleton.Triplet;
import org.graphstream.ui.javafx.renderer.shape.Decorable;
import org.graphstream.ui.javafx.renderer.shape.Shape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Path2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.FillableLine;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.ShadowableLine;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.StrokableLine;
import org.graphstream.ui.javafx.util.CubicCurve;

import javafx.scene.canvas.GraphicsContext;

public class SpriteFlowShape implements Shape {
	FillableLine fillableLine ;
	StrokableLine strokableLine ;
	ShadowableLine shadowableLine ;
	Decorable decorable ;
	
	double theSize = 0.0;
	double along = 0.0;
	double offset = 0.0;
	ConnectorSkeleton connectorSkel = null ;
	Path2D theShape = new Path2D(0, false) ;
	boolean reverse = false ;
	
	public SpriteFlowShape() {
		this.fillableLine = new FillableLine() ;
		this.strokableLine = new StrokableLine() ;
		this.shadowableLine = new ShadowableLine() ;
		this.decorable = new Decorable() ;
	}
	
	@Override
	public void configureForGroup(Backend bck, Style style, DefaultCamera2D camera) {
		theSize = camera.getMetrics().lengthToGu(style.getSize(), 0);
		reverse = (style.getSpriteOrientation() == SpriteOrientation.FROM);
		
		fillableLine.configureFillableLineForGroup(bck, style, camera, theSize);
		strokableLine.configureStrokableForGroup(style, camera);
		shadowableLine.configureShadowableLineForGroup(style, camera);
		decorable.configureDecorableForGroup(style, camera);
	}

	@Override
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel,	DefaultCamera2D camera) {
		GraphicSprite sprite = (GraphicSprite)element;
			
		if( sprite.isAttachedToEdge() ) {
			GraphicEdge edge = sprite.getEdgeAttachment();
			
			fillableLine.configureFillableLineForElement( element.getStyle(), camera, element );
			decorable.configureDecorableForElement( bck, camera, element, skel );
		
			if( element.hasAttribute( "ui.size" ) )
				theSize = camera.getMetrics().lengthToGu( StyleConstants.convertValue( element.getAttribute( "ui.size" ) ) );
			
			along    = element.getX();
			offset   = camera.getMetrics().lengthToGu( element.getY(), sprite.getUnits() );
			connectorSkel = (ConnectorSkeleton)edge.getAttribute( Skeleton.attributeName );
		} 
		else {
			connectorSkel = null;
		}
	}

	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		make(backend.graphics2D(), camera, 0, 0 );
	}

	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		make(backend.graphics2D(), camera, shadowableLine.theShadowOff.x, shadowableLine.theShadowOff.y );
	}

	private void make(GraphicsContext graphics2d, DefaultCamera2D camera, double shx, double shy) {
		// EdgeInfo contains a way to compute points perpendicular to the shape, however here
		// we only need to compute the perpendicular vector once, hence this code.
		if(connectorSkel != null) {
	        if(connectorSkel.isCurve()) {
	        	Point3 P0 = connectorSkel.apply(0);
	        	if(reverse) 
	        		P0 = connectorSkel.apply(3) ;
	        	Point3 P1 = connectorSkel.apply(1) ;
	        	if(reverse)
	        		P1 = connectorSkel.apply(2) ;
	        	Point3 P2 = connectorSkel.apply(2) ;
	        	if(reverse) 
	        		P2 = connectorSkel.apply(1) ;
	        	Point3 P3 = connectorSkel.apply(3) ;
	        	if(reverse)
	        		P3 = connectorSkel.apply(0) ;
				double inc = 0.01;
				double t   = 0.0;
				Vector2 dir = new Vector2(P3.x-P0.x, P3.y-P0.y);
				Vector2 per = new Vector2(dir.y() + shx, -dir.x() + shy);
				
				per.normalize();
				per.scalarMult(offset);
				theShape = new Path2D((int)(along/inc)+3, false);
				theShape.moveTo(P0.x + per.x(), P0.y + per.y());
				while(t <= along) {
					theShape.lineTo(
						CubicCurve.eval(P0.x + per.x(), P1.x + per.x(), P2.x + per.x(), P3.x + per.x(), t),
						CubicCurve.eval(P0.y + per.y(), P1.y + per.y(), P2.y + per.y(), P3.y + per.y(), t)
					);
					t += inc;
				}
	        } 
	        else if(connectorSkel.isPoly()) {
	            Point3 P0 = connectorSkel.from() ;
	            if(reverse)
	            	P0 = connectorSkel.to() ;
	            Point3 P1 = connectorSkel.to();
	            if(reverse)
	            	P1 = connectorSkel.from() ;
	            double a = along ;
	            if(reverse)
	            	a = 1-along;
	           Triplet<Integer, Double, Double> triplet = connectorSkel.wichSegment(a);
	           int i = triplet.i ;
	           double sum = triplet.sum;
	           double ps = triplet.ps ;
	           
	           Vector2 dir = new Vector2(P1.x-P0.x, P1.y-P0.y);
	           Vector2 per = new Vector2(dir.y() + shx, -dir.x() + shy);
	            
				per.normalize();
				per.scalarMult(offset);
				
				theShape = new Path2D(i+3, false);

				if(reverse) {
				    int n = connectorSkel.size();
	                sum = connectorSkel.length() - sum;
	                ps  = 1-ps;
	                theShape.moveTo(P1.x+per.x(), P1.y+per.y());
	                for(int j = n-2 ; j < i ; j--) {
	                	theShape.lineTo(connectorSkel.apply(j).x + per.x(), connectorSkel.apply(j).y + per.y());
	                }
	                Point3 PX = connectorSkel.pointOnShape(along);
	                theShape.lineTo(PX.x+per.x(), PX.y+per.y());
	            } 
				else {
	                theShape.moveTo(P0.x+per.x(), P0.y+per.y());
	                for(int j = 1 ; j <= i ; j++) {
	                	theShape.lineTo(connectorSkel.apply(j).x + per.x(), connectorSkel.apply(j).y + per.y());
	                }
	                Point3 PX = connectorSkel.pointOnShape(along);
	                theShape.lineTo(PX.x+per.x(), PX.y+per.y());
	            }
	        } 
	        else {
	            Point3 P0 = connectorSkel.from();
	            if(reverse) 
	            	P0 = connectorSkel.to() ;
	            Point3 P1 = connectorSkel.to() ;
	            if(reverse) 
	            	P1 = connectorSkel.from() ;
	            Vector2 dir = new Vector2(P1.x-P0.x, P1.y-P0.y);
	            Vector2 per = new Vector2(dir.y() + shx, -dir.x() + shy);

				per.normalize();
				per.scalarMult(offset);
				dir.scalarMult(along);

				theShape = new Path2D(5, false);
				theShape.moveTo(P0.x + per.x(), P0.y + per.y());
				theShape.lineTo(P0.x + dir.x() + per.x(), P0.y + dir.y() + per.y());
	        }
	    }
	    
//		if( connectorSkel != null ) {
//			var P0  = if( reverse ) connectorSkel.to else connectorSkel.from
//			var P3  = if( reverse ) connectorSkel.from else connectorSkel.to
//			val dir = Vector2( P3.x-P0.x, P3.y-P0.y )
//			val per = Vector2( dir.y + shx, -dir.x + shy )
//			
//			per.normalize
//			per.scalarMult( offset )
//			theShape.reset
//			theShape.moveTo( P0.x + per.x, P0.y + per.y  )
//			
//			if( connectorSkel.isCurve ) {
//				val P1  = if( reverse ) connectorSkel(2) else connectorSkel(1)
//				val P2  = if( reverse ) connectorSkel(1) else connectorSkel(2)
//				val inc = 0.01f
//				var t   = 0f
//				
//				while( t <= along ) {
//					theShape.lineTo(
//						CubicCurve.eval( P0.x + per.x, P1.x + per.x, P2.x + per.x, P3.x + per.x, t ),
//						CubicCurve.eval( P0.y + per.y, P1.y + per.y, P2.y + per.y, P3.y + per.y, t )
//					)
//					
//					t += inc
//				}
//			} else {
//				val dir = Vector2( P3.x-P0.x, P3.y-P0.y )
//				dir.scalarMult( along )
//				theShape.lineTo( P0.x + dir.x + per.x, P0.y + dir.y + per.y )
//			}
//		}
	}

	@Override
	public void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skel) {
		if( connectorSkel != null ) {
 		    GraphicsContext g = bck.graphics2D();
 			make(bck, camera);
 			strokableLine.stroke( g, theShape );
 			fillableLine.fill( g, theSize, theShape );
 			decorable.decorConnector( bck, camera, skel.iconAndText, element, theShape );
 		}
	}

	@Override
	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		if( connectorSkel != null ) {
			makeShadow(bck, camera);
			shadowableLine.cast(bck.graphics2D(), theShape );
		}
	}
	
}
