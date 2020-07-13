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

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

public class TestPies {
	public static void main(String[] args) {
		(new TestPies()).run();
	}

	private void run() {
		System.setProperty("org.graphstream.ui", "org.graphstream.ui.javafx.util.Display");
		SingleGraph g = new SingleGraph("test");
		Node A = g.addNode("A");
		Node B = g.addNode("B");
		g.addEdge("AB", "A", "B");

		SpriteManager sm = new SpriteManager(g);
		Sprite pie = sm.addSprite("pie");

		g.setAttribute("ui.antialias");
		pie.setAttribute("ui.style", "shape: pie-chart; fill-color: #F00, #0F0, #00F; size: 30px;");
//		g.addAttribute("ui.stylesheet", "sprite { shape: pie-chart; fill-color: #F00, #0F0, #00F; size: 30px; } node {fill-color: red; }")
		double[] values = new double[3];
		values[0] = 0.3333;
		values[1] = 0.3333;
		values[2] = 0.3333;
		pie.setAttribute("ui.pie-values", values);
		pie.attachToEdge("AB");
		pie.setPosition(0.5);
		
		g.display();

		double[] values2 = new double[3];
		values2[0] = 0.1;
		values2[1] = 0.3;
		values2[2] = 0.6;
		boolean on = true;
		
		while(true) {
		    try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    if(on) {
		        values[0] = 0.1;
		        values[1] = 0.3;
		        values[2] = 0.6;
		        A.setAttribute("ui.pie-values", new double[]{1.0});
		        A.setAttribute("ui.style", "shape:pie-chart; fill-color:red;");
		    } 
		    else {
		        values[0] = 0.3;
		        values[1] = 0.3;
		        values[2] = 0.3;
		        A.setAttribute("ui.pie-values", new double[]{1.0});
		        A.setAttribute("ui.style", "shape:pie-chart; fill-color:blue;");
		    }
		    pie.setAttribute("ui.pie-values", values);
		    
		    //pie.addAttribute("ui.pie-values", if(on) values else values2)
		    on = ! on;
		}
	}
}
