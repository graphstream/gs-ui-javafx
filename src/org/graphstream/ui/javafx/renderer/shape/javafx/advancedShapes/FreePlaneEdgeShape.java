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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.LineConnectorShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Path2D;

import javafx.scene.canvas.GraphicsContext;

public class FreePlaneEdgeShape extends LineConnectorShape {
	Path2D theShape = new Path2D(0, false);

	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		make(camera, 0, 0, 0, 0);
	}
	
	private void make(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		if (skel.multi() > 1 || skel.isLoop()) // is a loop or a multi edge
			makeMultiOrLoop(camera, sox, soy, swx, swy);
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

	private void makeSingle(DefaultCamera2D camera, double sox, double soy, double swx, double swy) {
		try {
			double fromx = skel.from().x + sox;
			double fromy = skel.from().y + soy - theSourceSize.y / 2;
			double tox = skel.to().x + sox;
			double toy = skel.to().y + soy - theTargetSize.y / 2;
			double length = Math.abs(skel.to().x - skel.from().x);
			double c1x = 0.0;
			double c1y = 0.0;
			double c2x = 0.0;
			double c2y = 0.0;
			
			if (skel.from().x < skel.to().x) {
			    // At right.
			    fromx += theSourceSize.x / 2;
			    tox -= theTargetSize.x / 2;
			    c1x = fromx + length / 3;
			    c2x = tox - length / 3;
			    c1y = fromy;
			    c2y = toy;
			} else {
			    // At left.
			    fromx -= theSourceSize.x / 2;
			    tox += theTargetSize.x / 2;
			    c1x = fromx - length / 3;
			    c2x = tox + length / 3;
			    c1y = fromy;
			    c2y = toy;
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
		catch(Exception e) {
			Logger.getLogger(this.getClass().getSimpleName()).log(Level.WARNING, "FOUND!", e);
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
	}

	@Override
	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		makeShadow(bck, camera);
		shadowableLine.cast(bck.graphics2D(), theShape);
	}
}
