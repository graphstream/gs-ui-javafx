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

import java.util.Random;

import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TestStars2 extends Application {
	public static void main(String[] args) {
		Application.launch(TestStars2.class, args);
	}

	public void start(Stage primaryStage) throws Exception {
		SingleGraph graph  = new SingleGraph("Stars !");
		double x0     = 0.0;
		double x1     = 0.0;
		double width  = 100.0;
		double height = 20.0;
		int n      = 500;
		Random random = new Random();
		double minDis = 4.0;
		double sizeMx = 10.0;
		        
		graph.setAttribute("ui.stylesheet", styleSheet);
		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");
		
		Viewer viewer = new FxViewer( graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
		ViewerPipe pipeIn = viewer.newViewerPipe();
		FxDefaultView view = (FxDefaultView)viewer.addView("view1", new FxGraphRenderer() );
		
		view.resize(1000, (int)(1200*(height/width)));
		
		Scene scene = new Scene(view, 1000, 1200*(height/width), true, SceneAntialiasing.BALANCED);
        primaryStage.setScene(scene);
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setTitle("Stars 2");
        primaryStage.show();		
		
		
		for (int i = 0 ; i < n ; i++) {
			Node node = graph.addNode(i+"");
			node.setAttribute("xyz", (random.nextDouble()*width), (random.nextDouble()*height), 0);
			node.setAttribute("ui.size", (random.nextDouble()*sizeMx));
		}
		
		graph.nodes().forEach( node -> {
			Point3 pos = new Point3(GraphPosLengthUtils.nodePosition(node));
	        
	        graph.nodes().forEach( otherNode -> {
	            if(otherNode != node) {
	            	Point3 otherPos = new Point3(GraphPosLengthUtils.nodePosition(otherNode));
	                double dist     = otherPos.distance(pos);
	                
	                if(dist < minDis) {
	                    if(! node.hasEdgeBetween(otherNode.getId())) {
	                    	try {
	                    		graph.addEdge(node.getId()+"--"+otherNode.getId(), node.getId(), otherNode.getId());
	                    	}
	                    	catch(IdAlreadyInUseException e) {
	                    		graph.addEdge(node.getId()+"--"+otherNode.getId()+"-2", node.getId(), otherNode.getId());
	                    	}
	                    }
	                }
	            }
		        
			});
		});
	}
	
	private String styleSheet = 
			"graph {"+
			"	canvas-color: black;"+
			"	fill-mode: gradient-vertical;"+
			"	fill-color: black, #004;"+
			"	padding: 20px;"+
			"}"+ 
			"node {"+
			"	shape: circle;"+
			"	size-mode: dyn-size;"+
			"	size: 10px;"+
			"	fill-mode: gradient-radial;"+
			"	fill-color: #FFFC, #FFF0;"+
			"	stroke-mode: none;"+ 
			"	shadow-mode: gradient-radial;"+
			"	shadow-color: #FFF5, #FFF0;"+
			"	shadow-width: 5px;"+
			"	shadow-offset: 0px, 0px;"+
			"}"+
			"node:clicked {"+
			"	fill-color: #F00A, #F000;"+
			"}"+
			"node:selected {"+
			"	fill-color: #00FA, #00F0;"+
			"}"+
			"edge {"+
			"	shape: L-square-line;"+
			"	size: 1px;"+
			"	fill-color: #FFF3;"+
			"	fill-mode: plain;"+
			"	arrow-shape: none;"+
			"}"+
			"sprite {"+
			"	shape: circle;"+
			"	fill-mode: gradient-radial;"+
			"	fill-color: #FFF8, #FFF0;"+
			"}";
}
