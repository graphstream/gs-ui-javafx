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
import org.graphstream.graph.implementations.SingleGraph;

public class TestPolys {
	public static void main(String[] args) {
		(new TestPolys()).run();
	}

	private void run() {
		System.setProperty("org.graphstream.ui", "org.graphstream.ui.javafx.util.Display");
		SingleGraph g = new SingleGraph("Polys");
		
		Node A = g.addNode("A");
		Node B = g.addNode("B");
		Node C = g.addNode("C");
		Node D = g.addNode("D");
		
		A.setAttribute("xyz", new double[]{  1,  1, 0});
		B.setAttribute("xyz", new double[]{  1,  -1, 0});
		C.setAttribute("xyz", new double[]{  -1,  -1, 0});
		D.setAttribute("xyz", new double[]{  -1,  1, 0});
		
		A.setAttribute("ui.label", "A");
		B.setAttribute("ui.label", "B");
		C.setAttribute("ui.label", "C");
		D.setAttribute("ui.label", "D");

		Edge AB = g.addEdge("AB", "A", "B");
		Edge BC = g.addEdge("BC", "B", "C");
		Edge CD = g.addEdge("CD", "C", "D");
		Edge DA = g.addEdge("DA", "D", "A");

	
		AB.setAttribute("ui.points", new double[]{1, 1, 0,
                1.25, 0.5, 0,
                0.75, -0.5, 0,
                1, -1, 0});
		BC.setAttribute("ui.points", new double[]{1, -1, 0,
		                0.5, -0.5, 0,
		                -0.5, -0.25, 0,
		                -1, -1, 0});
		CD.setAttribute("ui.points", new double[]{-1, -1, 0,
		                -0.40, -0.5, 0,
		                -1.70, 0.5, 0,
		                -1, 1, 0});
		//DA.setAttribute("ui.points", new double[]{-1, 1, 0,
		//                -0.5, 0.75, 0,
		//                0.5, 0.25, 0,
		//                1, 1, 0});
		
		g.setAttribute("ui.stylesheet", styleSheet);
		g.setAttribute("ui.antialias");
		g.display(false);
	}
	
	String styleSheet = "edge { shape: cubic-curve; }";
}
