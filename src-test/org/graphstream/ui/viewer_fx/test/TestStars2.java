package org.graphstream.ui.viewer_fx.test;

import java.util.Random;

import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;
import org.graphstream.ui.javafx.FxFullGraphRenderer;
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
		FxDefaultView view = (FxDefaultView)viewer.addView("view1", new FxFullGraphRenderer() );
		
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
