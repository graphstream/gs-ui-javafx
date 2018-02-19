package org.graphstream.ui.viewer_fx.test;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.thread.ThreadProxyPipe;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.Viewer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TestTwoViewersInOneFrame extends Application {
	public static void main(String[] args) {
		Application.launch(TestTwoViewersInOneFrame.class, args);
	}

	public void start(Stage primaryStage) throws Exception {
		MultiGraph graph1 = new MultiGraph("g1");
		MultiGraph graph2 = new MultiGraph("g2");
		
		ThreadProxyPipe pipe1 = new ThreadProxyPipe() ;
		pipe1.init(graph1);
		ThreadProxyPipe pipe2 = new ThreadProxyPipe() ;
		pipe2.init(graph2);
		
		Viewer viewer1 = new FxViewer(pipe1);
		Viewer viewer2 = new FxViewer(pipe2);

	    graph1.setAttribute("ui.quality");
	    graph2.setAttribute("ui.quality");
	    graph1.setAttribute("ui.antialias");
	    graph2.setAttribute("ui.antialias");
		graph1.setAttribute("ui.stylesheet", styleSheet1);
		graph2.setAttribute("ui.stylesheet", styleSheet2);

		FxDefaultView view1 = new FxDefaultView(viewer1, "view1", new FxGraphRenderer());
		FxDefaultView view2 = new FxDefaultView(viewer2, "view2", new FxGraphRenderer());
		viewer1.addView(view1);
		viewer2.addView(view2);
		viewer1.enableAutoLayout();
		viewer2.enableAutoLayout();

		DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

		gen.addSink(graph1);
		gen.addSink(graph2);
		gen.begin();
		for(int i = 0 ; i < 100; i++)
			gen.nextEvents();
		gen.end();

		gen.removeSink(graph1);
		gen.removeSink(graph2);
		
		
		GridPane gridpane = new GridPane();
		gridpane.add(view1, 1, 0);
		gridpane.add(view2, 2, 0);
		
		
		Scene scene = new Scene(gridpane, 800, 600, true, SceneAntialiasing.BALANCED);
        primaryStage.setScene(scene);
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        primaryStage.show();
	}
	
	protected String styleSheet1 =
			"graph { padding: 40px; }" +
			"node { fill-color: red; stroke-mode: plain; stroke-color: black; }";
	
	protected String styleSheet2 =
		"graph { padding: 40px; }" +
		"node { fill-color: blue; stroke-mode: plain; stroke-color: black; }";
}
