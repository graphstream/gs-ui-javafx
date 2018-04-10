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
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.CubicCurve2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Line2D;

import javafx.scene.canvas.GraphicsContext;

public class LineShape extends LineConnectorShape {
	protected Line2D theShapeL = new Line2D();
	protected CubicCurve2D theShapeC = new CubicCurve2D();
	protected Form theShape = null;
			
	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		Point3 from = skel.from();
		Point3 to = skel.to();
		if( skel.isCurve() ) {
			Point3 ctrl1 = skel.apply(1);
			Point3 ctrl2 = skel.apply(2);
			theShapeC = new CubicCurve2D( from.x, from.y, ctrl1.x, ctrl1.y, ctrl2.x, ctrl2.y, to.x, to.y );
			theShape = theShapeC;
		} else {
			theShapeL = new Line2D( from.x, from.y, to.x, to.y );
			theShape = theShapeL;
		} 
	}

	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		double x0 = skel.from().x + shadowableLine.theShadowOff.x;
		double y0 = skel.from().y + shadowableLine.theShadowOff.y;
		double x1 = skel.to().x + shadowableLine.theShadowOff.x;
		double y1 = skel.to().y + shadowableLine.theShadowOff.y;
		
		if( skel.isCurve() ) {
			double ctrlx0 = skel.apply(1).x + shadowableLine.theShadowOff.x;
			double ctrly0 = skel.apply(1).y + shadowableLine.theShadowOff.y;
			double ctrlx1 = skel.apply(2).x + shadowableLine.theShadowOff.x;
			double ctrly1 = skel.apply(2).y + shadowableLine.theShadowOff.y;
			
			theShapeC = new CubicCurve2D( x0, y0, ctrlx0, ctrly0, ctrlx1, ctrly1, x1, y1 );
			theShape = theShapeC;
		} else {
			theShapeL = new Line2D( x0, y0, x1, y1 );
			theShape = theShapeL;
		}
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