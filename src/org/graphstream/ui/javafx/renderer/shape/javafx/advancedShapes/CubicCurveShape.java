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
  
package org.graphstream.ui.javafx.renderer.shape.javafx.advancedShapes;

import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.ShowCubics;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.LineConnectorShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Path2D;

import javafx.scene.canvas.GraphicsContext;

public class CubicCurveShape extends LineConnectorShape {
	ShowCubics showCubics ;
	Path2D theShape = new Path2D(0, false);

	public CubicCurveShape() {
		this.showCubics = new ShowCubics() ;
	}

	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		make(camera, 0, 0, 0, 0);
	}

	private void make(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		if (skel.multi() > 1 || skel.isLoop()) // is a loop or a multi edge
            makeMultiOrLoop(camera, sox, soy, swx, swy);
       else if(skel.isPoly() && skel.size() == 4)
            makeFromPoints(camera, sox, soy, swx, swy); // has points positions
       else 
    	   makeSingle(camera, sox, soy, swx, swy); // is a single edge.
	}

	private void makeMultiOrLoop(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		if (skel.isLoop())
			makeLoop(camera, sox, soy, swx, swy);
        else
        	makeMulti(camera, sox, soy, swx, swy);
	}

	private void makeLoop(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		double fromx = skel.apply(0).x + sox;
		double fromy = skel.apply(0).y + soy;
		double tox = skel.apply(3).x + sox;
		double toy = skel.apply(3).y + soy;
		double c1x = skel.apply(1).x + sox;
		double c1y = skel.apply(1).y + soy;
		double c2x = skel.apply(2).x + sox;
		double c2y = skel.apply(2).y + soy;
		
		theShape = new Path2D(5, false);
		theShape.moveTo(fromx, fromy);
		theShape.curveTo(c1x, c1y, c2x, c2y, tox, toy);
	}

	private void makeMulti(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		double fromx = skel.apply(0).x + sox;
		double fromy = skel.apply(0).y + soy;
		double tox = skel.apply(3).x + sox;
		double toy = skel.apply(3).y + soy;
		double c1x = skel.apply(1).x + sox;
		double c1y = skel.apply(1).y + soy;
		double c2x = skel.apply(2).x + sox;
		double c2y = skel.apply(2).y + soy;	

		theShape = new Path2D(5, false);
		theShape.moveTo(fromx, fromy);
		theShape.curveTo(c1x, c1y, c2x, c2y, tox, toy);
	}

	private void makeFromPoints(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		double fromx = skel.from().x + sox;
		double fromy = skel.from().y + soy;
		double tox = skel.to().x + sox;
		double toy = skel.to().y + soy;
		double c1x = skel.apply(1).x + sox;
		double c1y = skel.apply(1).y + soy;
		double c2x = skel.apply(2).x + sox;
		double c2y = skel.apply(2).y + soy;
		
		theShape = new Path2D(5, false);
		theShape.moveTo(fromx, fromy);
		theShape.curveTo(c1x, c1y, c2x, c2y, tox, toy);	

		if (sox == 0 && soy == 0) {	// Inform the system this is a curve, not a polyline.
			skel.setCurve(
					fromx, fromy, 0,
					c1x, c1y, 0,
					c2x, c2y, 0,
					tox, toy, 0);
		}	
	}

	private void makeSingle(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		double fromx = skel.from().x + sox;
		double fromy = skel.from().y + soy;
		double tox = skel.to().x + sox;
		double toy = skel.to().y + soy;
		Vector2 mainDir = new Vector2(skel.from(), skel.to());
        double length = mainDir.length();
        double angle = mainDir.y() / length;
        double c1x = 0.0;
        double c1y = 0.0;
        double c2x = 0.0;
        double c2y = 0.0;

        if (angle > 0.707107f || angle < -0.707107f) {
            // North or south.
            c1x = fromx + mainDir.x() / 2;
            c2x = c1x;
            c1y = fromy;
            c2y = toy;
        } 
        else {
            // East or west.
            c1x = fromx;
            c2x = tox;
            c1y = fromy + mainDir.y() / 2;
            c2y = c1y;
        }

        theShape = new Path2D(5, false);
        theShape.moveTo(fromx, fromy);
        theShape.curveTo(c1x, c1y, c2x, c2y, tox, toy);

        // Set the connector as a curve.

        if (sox == 0 && soy == 0) {
            skel.setCurve(
                fromx, fromy, 0,
                c1x, c1y, 0,
                c2x, c2y, 0,
                tox, toy, 0);
        }
	}

	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		if (skel.isCurve())
            makeMultiOrLoop(camera, shadowableLine.theShadowOff.x, shadowableLine.theShadowOff.y, shadowableLine.theShadowWidth, shadowableLine.theShadowWidth);
		else
			makeSingle(camera, shadowableLine.theShadowOff.x, shadowableLine.theShadowOff.y, shadowableLine.theShadowWidth, shadowableLine.theShadowWidth);
	}

	@Override
	public void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		GraphicsContext g = bck.graphics2D();
		make(bck, camera);
		strokableLine.stroke(g, theShape);
		fillableLine.fill(g, theSize, theShape);
		decorable.decorConnector(bck, camera, skel.iconAndText, element, theShape);
		// 		showControlPolygon = true
		// 		if( showControlPolygon ) {
		//	 		val c = g.getColor();
		//	 		val s = g.getStroke();
		//	 		g.setStroke( new java.awt.BasicStroke( camera.metrics.px1 ) )
		//	 		g.setColor( Color.red );
		//	 		g.draw( theShape );
		//	 		g.setStroke( s );
		//	 		g.setColor( c );
		//	 		showCtrlPoints( g, camera )
		// 		}		
	}

	@Override
	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		makeShadow(bck, camera);
		shadowableLine.cast(bck.graphics2D(), theShape);
	}
}