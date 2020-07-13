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

import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicSprite;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.Skeleton;
import org.graphstream.ui.javafx.renderer.shape.Orientable;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.PolygonalShape;
import org.graphstream.ui.javafx.renderer.shape.javafx.baseShapes.Form.Path2D;

public class SpriteArrowShape extends PolygonalShape {
	Orientable orientable ;
	
	public SpriteArrowShape() {
		this.orientable = new Orientable() ;
	}
	
	@Override
	public void configureForGroup(Backend bck, Style style, DefaultCamera2D camera) {
		super.configureForGroup(bck, style, camera);
		orientable.configureOrientableForGroup(style, camera);
	}
	
	@Override
	public void configureForElement(Backend bck, GraphicElement element, Skeleton skel, DefaultCamera2D camera) {
		super.configureForElement(bck, element, skel, camera);
		orientable.configureOrientableForElement(camera, (GraphicSprite)element);
	}

	@Override
	public void make(Backend backend, DefaultCamera2D camera) {
		double x = area.theCenter.x;
		double y = area.theCenter.y;
		Vector2 dir = new Vector2(  orientable.target.x - x, orientable.target.y - y ); 

		dir.normalize();
		Vector2 per = new Vector2( dir.y(), -dir.x() );
		
		dir.scalarMult( area.theSize.x );
		per.scalarMult( area.theSize.y / 2 );

		theShape = new Path2D(5, true);
		theShape().moveTo( x + per.x(), y + per.y() );
		theShape().lineTo( x + dir.x(), y + dir.y() );
		theShape().lineTo( x - per.x(), y - per.y() );
		theShape.closePath();
	}

	@Override
	public void makeShadow(Backend backend, DefaultCamera2D camera) {
		double x = area.theCenter.x + shadowable.theShadowOff.x;
		double y = area.theCenter.y + shadowable.theShadowOff.y;
		Vector2 dir = new Vector2( orientable.target.x - x, orientable.target.y - y );
		dir.normalize();
		Vector2 per = new Vector2( dir.y(), -dir.x() );
		
		dir.scalarMult( area.theSize.x + shadowable.theShadowWidth.x );
		per.scalarMult( ( area.theSize.y + shadowable.theShadowWidth.y ) / 2 );

		theShape = new Path2D(5, true);
		theShape().moveTo( x + per.x(), y + per.y() );
		theShape().lineTo( x + dir.x(), y + dir.y() );
		theShape().lineTo( x - per.x(), y - per.y() );
		theShape.closePath();
	}
}