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

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicEdge;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.SpriteOrientation;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.ConnectorSkeleton;
import org.graphstream.ui.javafx.renderer.Skeleton;

/** Trait for all shapes that points at a direction. */
public class Orientable {
    /** The shape orientation. */
	StyleConstants.SpriteOrientation orientation = null ;
	
	/** The shape target. */
	public Point3 target = new Point3();
	
	/** Configure the orientation mode for the group according to the style. */
	public void configureOrientableForGroup(Style style, DefaultCamera2D camera) { orientation = style.getSpriteOrientation(); }
	
	/** Compute the orientation vector for the given element according to the orientation mode. */
	public void configureOrientableForElement(DefaultCamera2D camera, GraphicSprite sprite) {
		if ( sprite.getAttachment() instanceof GraphicNode ) {
			switch (sprite.getStyle().getSpriteOrientation()) {
				case NONE: 
					target.set(0, 0); 
					break;
				case FROM: 
					target.set(((GraphicNode)sprite.getAttachment()).getX(), ((GraphicNode)sprite.getAttachment()).getY());
					break;
				case TO:
					target.set(((GraphicNode)sprite.getAttachment()).getX(), ((GraphicNode)sprite.getAttachment()).getY());
					break;
				case PROJECTION: 
					target.set(((GraphicNode)sprite.getAttachment()).getX(), ((GraphicNode)sprite.getAttachment()).getY());
					break;
				default:
					break;
			}
		}
		else if ( sprite.getAttachment() instanceof GraphicEdge ) {
			switch (sprite.getStyle().getSpriteOrientation()) {
				case NONE: 
					target.set(0, 0); 
					break;
				case FROM: 
					target.set(((GraphicEdge)sprite.getAttachment()).from.getX(), ((GraphicEdge)sprite.getAttachment()).from.getY());
					break;
				case TO:
					target.set(((GraphicEdge)sprite.getAttachment()).to.getX(), ((GraphicEdge)sprite.getAttachment()).to.getY());
					break;
				case PROJECTION: 
					ConnectorSkeleton ei = (ConnectorSkeleton)((GraphicEdge)sprite.getAttachment()).getAttribute(Skeleton.attributeName) ;
					if(ei != null)
					     ei.pointOnShape(sprite.getX(), target) ;
					else
						setTargetOnLineEdge(camera, sprite, (GraphicEdge)sprite.getAttachment()) ; 
					break;
				default:
					break;
			}
		}
		else {
			orientation = SpriteOrientation.NONE ;
		}
	}
	
	private void setTargetOnLineEdge(DefaultCamera2D camera, GraphicSprite sprite, GraphicEdge ge) {
		Vector2 dir = new Vector2(ge.to.getX()-ge.from.getX(), ge.to.getY()-ge.from.getY());
		dir.scalarMult(sprite.getX());
		target.set(ge.from.getX() + dir.x(), ge.from.getY() + dir.y());
	}
}