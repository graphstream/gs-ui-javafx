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
  
package org.graphstream.ui.javafx.renderer.shape.javafx;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.javafx.renderer.ConnectorSkeleton;
import org.graphstream.ui.view.camera.Camera;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/** Utility trait to display cubics Bézier curves control polygons. */
public class ShowCubics {
	public boolean showControlPolygon = false ;
	
	/** Show the control polygons. */
    public void showCtrlPoints(GraphicsContext g, Camera camera, ConnectorSkeleton skel) {
        if (showControlPolygon && skel.isCurve()) {
            Point3 from = skel.from();
            Point3 ctrl1 = skel.apply(1);
            Point3 ctrl2 = skel.apply(2);
            Point3 to = skel.to();

            Paint colorStroke = g.getStroke();
            Paint colorFill = g.getFill();
            double stroke = g.getLineWidth();
            
            double px6 = camera.getMetrics().px1 * 6;
            double px3 = camera.getMetrics().px1 * 3;

            g.setStroke(Color.RED);
            g.setFill(Color.RED);
            g.fillOval(from.x - px3, from.y - px3, px6, px6);

            if (ctrl1 != null) {
            	g.fillOval(ctrl1.x - px3, ctrl1.y - px3, px6, px6);

            	g.fillOval(ctrl2.x - px3, ctrl2.y - px3, px6, px6);

            	g.setLineWidth(camera.getMetrics().px1);
                g.strokeLine(ctrl1.x, ctrl1.y, ctrl2.x, ctrl2.y);
               
                g.strokeLine(from.x, from.y, ctrl1.x, ctrl1.y);
                
                g.strokeLine(ctrl2.x, ctrl2.y, to.x, to.y);
            }

            g.fillOval(to.x - px3, to.y - px3, px6, px6);

            g.setStroke(colorStroke);
            g.setFill(colorFill);
            g.setLineWidth(stroke);
        }
    }
}