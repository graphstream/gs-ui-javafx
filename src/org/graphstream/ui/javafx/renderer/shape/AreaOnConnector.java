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
  
package org.graphstream.ui.javafx.renderer.shape;

import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.shape.javafx.Area;

/** Some areas are attached to a connector (sprites). */
public class AreaOnConnector extends Area {
	/** The connector we are attached to. */
	protected Connector theConnector = null;
	
	/** The edge represented by the connector.. */
	protected GraphicEdge theEdge = null;
			
	/** XXX must call this method explicitly in the renderer !!! bad !!! XXX */
	public void theConnectorYoureAttachedTo(Connector connector) { theConnector = connector; }
	
	protected void configureAreaOnConnectorForGroup(Style style, DefaultCamera2D camera) {
		sizeForEdgeArrow(style, camera);
	}
	
	protected void configureAreaOnConnectorForElement(GraphicEdge edge, Style style, DefaultCamera2D camera) {
		connector(edge);
		theCenter.set(edge.to.getX(), edge.to.getY());
	}
	
	private void connector(GraphicEdge edge) { theEdge = edge ; }
 
	private void sizeForEdgeArrow(Style style, DefaultCamera2D camera) {
		double w = camera.getMetrics().lengthToGu(style.getArrowSize(), 0);
		double h = w ;
		if(style.getArrowSize().size() > 1)
			h = camera.getMetrics().lengthToGu(style.getArrowSize(), 1) ;
  
		theSize.set(w, h);
	}
	
}