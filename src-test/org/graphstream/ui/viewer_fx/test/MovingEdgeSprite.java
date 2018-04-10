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
  
package org.graphstream.ui.viewer_fx.test;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.spriteManager.Sprite;

public class MovingEdgeSprite extends Sprite {
	public double SPEED = 0.001f;
	public double speed = SPEED;
	public double off = 0f;
	public Units units = Units.PX;

	public void setOffsetPx( float offset ) { off = offset; units = Units.PX; }

	public void move() {
		double p = getX();

		p += speed;

		if( p < 0 || p > 1 ) {
			Edge edge = null ;
			if ( getAttachment() instanceof Edge)
				edge = (Edge)getAttachment();
		
			if( edge != null ) {				
				Node node = edge.getSourceNode();
				if( p > 1 )
					node = edge.getTargetNode() ;
				Edge other = randomOutEdge( node );

				if(other==null) {
				    System.err.println("node "+node.getId()+" out="+node.getOutDegree()+" null !!");
				}

				if( node.getOutDegree() > 1 ) { 
					while( other.equals(edge) )
						other = randomOutEdge( node );
				}

				attachToEdge( other.getId() );
				if( node.equals(other.getSourceNode()) ) {
					setPosition( units, 0, off, 0 );
					speed = SPEED;
				} else {
					setPosition( units, 1, off, 0 );
					speed = -SPEED;
				}
			}
		} 
		else {
			setPosition( units, p, off, 0 );
		}
	}

	public Edge randomOutEdge(Node node) {
		int min = 0 ;
		int max = (int) node.leavingEdges().count();
		
		int rand = (int) (min + (Math.random() * (max - min)));
		
		return node.getLeavingEdge(rand);
	}
}