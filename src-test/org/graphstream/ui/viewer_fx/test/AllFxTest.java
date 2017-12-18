package org.graphstream.ui.viewer_fx.test;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxFullGraphRenderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AllFxTest extends Application {
	public static void main(String[] args) {
		Application.launch(AllFxTest.class, args);
	}
	
	protected String styleSheet = "graph {padding: 60px;}";

	public void start(Stage primaryStage) throws Exception {
		MultiGraph g = new MultiGraph("mg");
		FxViewer v = new FxViewer(g, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();
		
		g.setAttribute("ui.antialias");
		g.setAttribute("ui.quality");
		g.setAttribute("ui.stylesheet", styleSheet);
		
		v.enableAutoLayout();
		FxViewPanel panel = (FxViewPanel)v.addDefaultView(false, new FxFullGraphRenderer());
		
		gen.addSink(g);
		gen.begin();
		for(int i = 0 ; i < 100 ; i++)
			gen.nextEvents();
		gen.end();
		gen.removeSink(g);
		
		Scene scene = new Scene(panel, 800, 600);
  		primaryStage.setScene(scene);
  				
		primaryStage.show();
	}
}