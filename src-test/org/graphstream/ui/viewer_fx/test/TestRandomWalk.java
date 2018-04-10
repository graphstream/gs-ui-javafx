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

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.algorithm.randomWalk.RandomWalk;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class TestRandomWalk {
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui", "org.graphstream.ui.javafx.util.Display");
		(new TestRandomWalk()).run();
	}

	private void run() {
		MultiGraph graph = new MultiGraph("random walk");
		DorogovtsevMendesGenerator gen   = new DorogovtsevMendesGenerator();
		RandomWalk rwalk = new RandomWalk();
    	
    	gen.addSink(graph);
    	gen.begin();
    	for(int i = 0 ; i < 400 ; i++) {
    		gen.nextEvents();
    	}
    	gen.end();

    	graph.setAttribute("ui.stylesheet", styleSheet);
    	graph.setAttribute("ui.quality");
    	graph.setAttribute("ui.antialias");
    	graph.display();

    	rwalk.setEntityCount(graph.getNodeCount()*2);
    	rwalk.setEvaporation(0.97);
    	rwalk.setEntityMemory(40);
    	rwalk.init(graph);
    	for(int i = 0 ; i < 3000 ; i++) {
    	    rwalk.compute();
    	    if(i%100==0){
    	        System.err.println("step "+i);
    	    	updateGraph(graph, rwalk);
    	    }
    	//    Thread.sleep(100)
    	}
    	rwalk.terminate();
    	updateGraph(graph, rwalk);
    	graph.setAttribute("ui.screenshot", "randomWalk.png");
    }
    
	public void updateGraph(Graph graph, RandomWalk rwalk) {
		float mine[] = {Float.MAX_VALUE};
        float maxe[] = {Float.MIN_VALUE};
    	
    	graph.edges().forEach ( edge -> {
    		float passes = (float) rwalk.getPasses(edge);
    	    if(passes>maxe[0]) 
    	    	maxe[0] = passes;
    	    if(passes<mine[0]) 
    	    	mine[0] = passes;
    	});
    	
    	graph.edges().forEach ( edge -> {
    		float passes = (float) rwalk.getPasses(edge);
    	    float color  = ((passes-mine[0])/(maxe[0]-mine[0]));
    		edge.setAttribute("ui.color", color);
    	});
	}
	
	public String styleSheet = 
			"edge {"+
			"size: 2px;"+
			"fill-color: red, yellow, green, #444;"+
			"fill-mode: dyn-plain;"+
		"}"+
		"node {"+
			"size: 6px;"+
			"fill-color: #444;"+
		"}";
}
