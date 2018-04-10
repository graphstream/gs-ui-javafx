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

import java.util.logging.Logger;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.AreaSkeleton;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.Decorable;
import org.graphstream.ui.javafx.renderer.shape.Shape;
import org.graphstream.ui.javafx.renderer.shape.javafx.Area;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Arc2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Ellipse2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.FillableMulticolored;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Shadowable;
import org.graphstream.ui.javafx.renderer.shape.javafx.shapePart.Strokable;
import org.graphstream.ui.javafx.util.AttributeUtils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class PieChartShape extends FillableMulticolored implements Shape, AttributeUtils {
	Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA,
	        Color.CYAN, Color.ORANGE, Color.PINK};
	
	Strokable strokabe ;
	Shadowable shadowable ;
	Decorable decorable ;
	Area area ;
	
	Ellipse2D theShape = new Ellipse2D();
	double[] theValues = null ;
	Object valuesRef = null ;
	
	public PieChartShape() {
		strokabe = new Strokable();
		shadowable = new Shadowable();
		decorable = new Decorable();
		area = new Area();
	}
	
	@Override
	public void configureForGroup(Backend backend, Style style, DefaultCamera2D camera) {
		area.configureAreaForGroup(style, camera);
        configureFillableMultiColoredForGroup(style, camera);
        strokabe.configureStrokableForGroup(style, camera);
        shadowable.configureShadowableForGroup(style, camera);
        decorable.configureDecorableForGroup(style, camera);		
	}
	@Override
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel,
			DefaultCamera2D camera) {
		decorable.configureDecorableForElement(bck, camera, element, skel);
		area.configureAreaForElement(bck, camera, (AreaSkeleton)skel, element, decorable.theDecor);		
	}
	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
        theShape.setFrameFromCenter(area.theCenter.x, area.theCenter.y, area.theCenter.x + area.theSize.x / 2, area.theCenter.y + area.theSize.y / 2);		
	}
	
	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		theShape.setFrameFromCenter(area.theCenter.x + shadowable.theShadowOff.x, area.theCenter.y + shadowable.theShadowOff.y,
				area.theCenter.x + (area.theSize.x + shadowable.theShadowWidth.x) / 2, area.theCenter.y + (area.theSize.y + shadowable.theShadowWidth.y) / 2);		
	}
	@Override
	public void render(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skel) {
		GraphicsContext g = bck.graphics2D();
		make(bck, camera);
		checkValues(element);
		fillPies(g, element);
		//fill(g, theSize, theShape)
		strokabe.stroke(g, theShape);
		decorable.decorArea(bck, camera, skel.iconAndText, element, theShape);
	}
	
	private void fillPies(GraphicsContext g, GraphicElement element) {
		if (theValues != null) {
			// we assume the pies values sum up to one. And we wont check it, its a mater of speed ;-).
			Arc2D arc = new Arc2D();
            double beg = 0.0;
            double end = 0.0;
            double col = 0;
            double sum = 0.0;
            
            for( int i = 0 ; i < theValues.length ; i++ ) {
            	double value = theValues[i];
            	end = beg + value;
                arc.setArcByCenter(area.theCenter.x, area.theCenter.y, area.theSize.x / 2, beg * 360, value * 360, ArcType.ROUND);
                
                g.setStroke(fillColors[(int) (col % fillColors.length)]);
                g.setFill(fillColors[(int) (col % fillColors.length)]);

                arc.drawByPoints(g, false);
                beg = end;
                sum += value;
                col += 1;
            }

            if (sum > 1.01f)
                Logger.getLogger(this.getClass().getSimpleName()).warning("[Sprite "+element.getId()+"] The sum of values for ui.pie-value should eval to 1 at max (actually "+sum+").");
        }
		else {
            // Draw a red empty circle to indicate "no value".
            g.setStroke(Color.RED);
            g.setFill(Color.RED);

            theShape.drawByPoints(g, false);
        }
	}

	private void checkValues(GraphicElement element) {
		Object pieValues = element.getAttribute("ui.pie-values");
	
		if (pieValues != null) {
			// Object oldRef = valuesRef;
			valuesRef = pieValues;
			// We use valueRef to avoid
			// recreating the values array for nothing.
			//if ((theValues == null) || (oldRef ne valuesRef)) {	// Cannot do this : the array reference can be the same and the values changed.
			theValues = getDoubles(valuesRef);
			//}
		}
	}

	@Override
	public void renderShadow(Backend bck, DefaultCamera2D camera, GraphicElement element, Skeleton skeleton) {
		makeShadow(bck, camera);
		shadowable.cast(bck.graphics2D(), theShape);		
	}	
}