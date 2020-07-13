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
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.util.ImageCache;
import org.graphstream.ui.view.Viewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TutorialDiagrams extends Application {
	public static final String URL_IMAGE = ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon.png").toString();
	
	public static void main(String[] args) {
		Application.launch(TutorialDiagrams.class, args);
	}

	public void start(Stage primaryStage) throws Exception {
		Scene scene1 = diagram1("diagram1", styleSheet1);
		Scene scene2 = diagram1b("diagram1b", styleSheet1);
		Scene scene3 = diagram2("diagram2", styleSheet1);
		Scene scene4 = diagram3("diagram3", styleSheet2);
		
        primaryStage.setScene(scene1);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });		 
        primaryStage.show();
        
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });	
        stage2.show();
        
        Stage stage3 = new Stage();
        stage3.setScene(scene3);
        stage3.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });	
        stage3.show();
        
        Stage stage4 = new Stage();
        stage4.setScene(scene4);
        stage4.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });	
        stage4.show();
	}
	
	public Scene diagram(Graph graph, String style, String title, int width, int height) {
		 graph.setAttribute("ui.quality");
		 graph.setAttribute("ui.antialias");
		 graph.setAttribute("ui.stylesheet", style);
		 
		 graph.setAttribute("ui.screenshot", title+".png");
		 
		 Viewer viewer = new FxViewer( graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
		 FxDefaultView view = (FxDefaultView) viewer.addDefaultView(true);
		 view.resize(width, height);
		 Scene scene = new Scene(view, width, height, true, SceneAntialiasing.DISABLED);
		 
		 return scene;
	}
	
	public Scene diagram1(String title, String styleSheet) {
		MultiGraph graph = new MultiGraph(title);
        Scene s = diagram(graph, styleSheet, title, 500, 250);
        
		Node G = graph.addNode("Graph");
		Node V = graph.addNode("Viewer");
		Edge E = graph.addEdge("G->V", "Graph", "Viewer", true);
		
		G.setAttribute("xyz", new double[] {0, 0, 0});
		V.setAttribute("xyz", new double[] {1, 0, 0});
		G.setAttribute("ui.label", "Graph");
		V.setAttribute("ui.label", "Viewer");
		
		return s ;
	}
	
	public Scene diagram1b(String title, String styleSheet) {
		MultiGraph graph = new MultiGraph(title);
		Scene s = diagram(graph, styleSheet, title, 500, 370);
        
        Node G = graph.addNode("Graph");
        Node V = graph.addNode("Viewer");
        Node B1 = graph.addNode("bidon1");
        Node B2 = graph.addNode("bidon2");
        
        graph.addEdge("G->bidon1", "Graph", "bidon1", true);
        graph.addEdge("bidon1->V", "bidon1", "Viewer", true);
        graph.addEdge("V->bidon2", "Viewer", "bidon2", true);
        graph.addEdge("bidon2->G", "bidon2", "Graph", true);
        
        G.setAttribute("xyz", new double[]{0, 0, 0});
        B1.setAttribute("xyz", new double[]{0, 0.5, 0});
        V.setAttribute("xyz", new double[]{1, 0.5, 0});
        B2.setAttribute("xyz", new double[]{1, 0, 0});
        G.setAttribute("ui.label", "Graph");
        V.setAttribute("ui.label", "Viewer");
        B1.setAttribute("ui.class", "invisible");
        B2.setAttribute("ui.class", "invisible");
        
        return s;
	}
	
	public Scene diagram2(String title, String styleSheet) {
		MultiGraph graph = new MultiGraph(title);
		Scene s = diagram(graph, styleSheet, title, 500, 250);
        
        Node G = graph.addNode("Graph");
        Node P = graph.addNode("Pipe");
        Node V = graph.addNode("Viewer");
                
		graph.addEdge("G->P", "Graph", "Pipe", true);
		graph.addEdge("P->V", "Pipe", "Viewer", true);
		 
		G.setAttribute("xyz", new double[] {0, 0, 0});
		P.setAttribute("xyz", new double[] {1, 0, 0});
		V.setAttribute("xyz", new double[] {2, 0, 0});
		G.setAttribute("ui.label", "Graph");
		P.setAttribute("ui.label", "Pipe");
        V.setAttribute("ui.label", "Viewer");
        
        return s;
	}
	
	public Scene diagram3(String title, String styleSheet) {
		MultiGraph graph = new MultiGraph(title);
		Scene s = diagram(graph, styleSheet, title, 800, 500);
        
        Node G = graph.addNode("Graph");
        Node V = graph.addNode("Viewer");
        Node P1 = graph.addNode("GtoV");
        Node P2 = graph.addNode("VtoG");
        graph.addEdge("G->GtoV", "Graph", "GtoV", true);
        graph.addEdge("GtoV->V", "GtoV", "Viewer", true);
        graph.addEdge("VtoG<-V", "Viewer", "VtoG", true);
        graph.addEdge("G<-VtoG", "VtoG", "Graph", true);
                
        G.setAttribute("ui.label", "Graph");
        P1.setAttribute("ui.label", "Pipe");
        P2.setAttribute("ui.label", "ViewerPipe");
        V.setAttribute("ui.label", "Viewer");
            
        G.setAttribute("xyz", new double[] {-2,  0, 0});
        P1.setAttribute("xyz", new double[] {-1,  1.4, 0});
        P2.setAttribute("xyz", new double[] { 1, -1.4, 0});
        V.setAttribute("xyz", new double[] { 2,  0, 0});
        
        return s;
	}
	
	public String styleSheet1 = 
    		"graph {"+
			"	padding: 90px;"+
			"}"+
			"node {"+
			"	size: 128px;"+
			"	shape: box;"+
			"	fill-mode: image-scaled;"+
			"	fill-image: url('"+URL_IMAGE+"');"+
			"	text-alignment: under;"+
			"	text-color: #DDD;"+
			"	text-background-mode: rounded-box;"+
			"	text-background-color: #333;"+
			"	text-padding: 4px;"+
			"}"+
			"node#Pipe {"+
			"	fill-image: url('"+URL_IMAGE+"');"+
			"}"+
			"node#Viewer {"+
			"	fill-image: url('"+URL_IMAGE+"');"+
			"}"+
			"node.invisible {"+
			"	fill-mode: plain;"+
			"	fill-color: #0000;"+
			"}"+
			"edge {"+
			"	size: 4px;"+
			"	fill-color: #979797;"+
			"	arrow-shape: none;"+
			"}";
	
	public String styleSheet2 = 
		"graph {"+
		"	padding: 90px;"+
		"}"+
		"node {"+
		"	size: 128px;"+
		"	shape: box;"+
		"	fill-mode: image-scaled;"+
		"	fill-image: url('"+URL_IMAGE+"');"+
		"	text-alignment: under;"+
		"	text-color: #DDD;"+
		"	text-background-mode: rounded-box;"+
		"	text-background-color: #333;"+
		"	text-padding: 4px;"+
		"}"+
		"node#Graph {"+
		"	fill-image: url('"+URL_IMAGE+"');"+
		"}"+
		"node#Viewer {"+
		"	fill-image: url('"+URL_IMAGE+"');"+
		"}"+
		"node#VtoG {"+
		"	fill-image: url('"+URL_IMAGE+"');"+
		"}"+
		"edge {"+
		"	size: 4px;"+
		"	fill-color: #979797;"+
		"	shape: L-square-line;"+
		"	arrow-size: 25px, 10px;"+
		"	arrow-shape: none;"+
		"}";
		
	class Size {
		public Size(int width, int height) {
			this.width = width ;
			this.height = height ;
		}
		public int width ;
		public int height ;
	}
}
