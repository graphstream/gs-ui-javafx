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

import org.graphstream.graph.implementations.MultiGraph;

public class TestStrokeMode {
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui", "org.graphstream.ui.javafx.util.Display");
		(new TestStrokeMode()).run();
	}

	private void run() {
		MultiGraph graph = new MultiGraph("stroke");

		graph.setAttribute("ui.quality");
	    graph.setAttribute("ui.antialias");
	    graph.setAttribute("ui.stylesheet", styleSheet);
	    graph.display();
	    graph.addNode("A");
	    graph.addNode("B");
	    graph.addNode("C");
	    graph.addNode("D");
	    graph.addEdge("AB", "A", "B");
	    graph.addEdge("BC", "B", "C");
	    graph.addEdge("CA", "C", "A");
	    graph.addEdge("AD", "A", "D");
	    graph.forEach(node -> node.setAttribute("ui.label", node.getId()));
	}
	
	private String styleSheet =
		"node {"+
		"	fill-color: white;"+
		"	fill-mode: plain;"+
		"	stroke-mode: dashes;"+
		"	stroke-width: 1px;"+
		"	stroke-color: red;"+
		"	size: 20px;"+
		"}"+
		"node#A {"+
		"	shape: triangle;"+
		"	stroke-mode: plain;"+
		"}"+
		"node#B {"+
		"	shape: cross;"+
		"	stroke-mode: plain;"+
		"}"+
		"node#C {"+
		"	shape: diamond;"+
		"	stroke-mode: plain;"+
		"}"+
		"node#D {"+
		"	fill-color: gray; "+
		"	stroke-color: blue; "+
		"}"+
		"edge {"+
		//"	fill-mode: none;"+
		"	shape: line;"+
		"	size: 0px;"+
		"	stroke-mode: dashes;"+
		"	stroke-width: 1px;"+
		"	stroke-color: red;"+
		"	fill-color: red;"+
		"}"+
		"edge#BC {"+	
		"	shape: blob; size: 3px; fill-color: #444;"+
		"}"+
		"edge#AD {"+	
		"	stroke-mode: double;"+
		"}";
}
