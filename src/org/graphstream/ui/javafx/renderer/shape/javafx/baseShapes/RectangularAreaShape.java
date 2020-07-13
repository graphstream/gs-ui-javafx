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

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Rectangle2D;

public abstract class RectangularAreaShape extends AreaShape {
	private Rectangle2D theShape = new Rectangle2D();
	
	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		double w = area.theSize.x;
		double h = area.theSize.y;
		
		((Form)theShape()).setFrame(area.theCenter.x-w/2, area.theCenter.y-h/2, w, h);	
	}

	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		double x = area.theCenter.x + shadowable.theShadowOff.x;
		double y = area.theCenter.y + shadowable.theShadowOff.y;
		double w = area.theSize.x + shadowable.theShadowWidth.x * 2;
		double h = area.theSize.y + shadowable.theShadowWidth.y * 2;
		
		((Form)theShape()).setFrame(x-w/2, y-h/2, w, h);
	}

	@Override
	public void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skel) {
		make(bck, camera);
 		fillable.fill(bck.graphics2D(), theShape(), camera);
 		strokable.stroke(bck.graphics2D(), theShape());
 		decorArea(bck, camera, skel.iconAndText, element, theShape());
	}

	@Override
	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		makeShadow(bck, camera);
 		shadowable.cast(bck.graphics2D(), theShape());
	}
	
	public Form theShape() {
		return theShape;
	}
}