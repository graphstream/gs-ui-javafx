package org.graphstream.ui.javafx.util;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.images.FileSinkImagesFactory;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.util.DefaultApplication;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.Layouts;
import org.graphstream.ui.view.GraphRenderer;
import org.graphstream.ui.view.Viewer;

import javafx.application.Application;

public class Display implements org.graphstream.util.Display, FileSinkImagesFactory {

	@Override
	public Viewer display(Graph graph, boolean autoLayout) {
		FxViewer viewer = new FxViewer(graph,
				FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		
		GraphRenderer renderer = new FxGraphRenderer();
		FxDefaultView view = (FxDefaultView) viewer.addView(FxViewer.DEFAULT_VIEW_ID, renderer);
		
		if(autoLayout) {
			Layout layout = Layouts.newLayoutAlgorithm() ;
			viewer.enableAutoLayout(layout);
		}
		
		DefaultApplication.init(view, graph);
	    new Thread(() -> Application.launch(DefaultApplication.class)).start();
		
		return viewer;
	}

	@Override public FileSinkImages createFileSinkImages() {
		return new FxFileSinkImages();
	}
}
