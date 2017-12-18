package org.graphstream.ui.viewer_fx.test;

import java.util.ArrayList;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.util.DefaultApplication;
import org.graphstream.ui.javafx.FxFullGraphRenderer;
import org.graphstream.ui.javafx.util.ImageCache;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javafx.application.Application;

public class TestIcons implements ViewerListener{
	public static void main(String[] args) {
		(new TestIcons()).run(args);
	}
	private boolean loop = true;
	private boolean direction = true ;
	
	public static ArrayList<String> iconAnim = new ArrayList<>();
			
	private void run(String[] args) {
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_1.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_2.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_3.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_4.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_5.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_6.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_7.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_8.png").toString());
		iconAnim.add(ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon_9.png").toString());
		
		MultiGraph graph  = new MultiGraph( "Icons ..." );
		FxViewer viewer = new FxViewer( graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
		ViewerPipe pipeIn = viewer.newViewerPipe();
		FxDefaultView view = (FxDefaultView)viewer.addView("view1", new FxFullGraphRenderer() );

		DefaultApplication.init(view, graph);
	    new Thread(() -> Application.launch(DefaultApplication.class)).start();
	    
		pipeIn.addAttributeSink( graph );
		pipeIn.addViewerListener( this );
		pipeIn.pump();

		graph.setAttribute( "ui.stylesheet", styleSheet );
		graph.setAttribute( "ui.antialias" );
		graph.setAttribute( "ui.quality" );

		Node A    = graph.addNode( "A" );
		Node B    = graph.addNode( "B" );
		Node C    = graph.addNode( "C" );
		Node D    = graph.addNode( "D" );
		
		graph.addEdge( "AB", "A", "B" );
		graph.addEdge( "BC", "B", "C" );
		graph.addEdge( "CD", "C", "D" );
		graph.addEdge( "DA", "D", "A" );

		A.setAttribute("xyz", new double[] { 0.0, 1.0, 0 });
		B.setAttribute("xyz", new double[] { 3.2, 1.5, 0 });
		C.setAttribute("xyz", new double[] { 0.2, 0.0, 0 });
		D.setAttribute("xyz", new double[] { 3.0,-0.5, 0 });

		A.setAttribute("label", "Topic1");
		B.setAttribute("label", "Topic2");
		C.setAttribute("label", "Topic3");
		D.setAttribute("label", "Topic4");

		A.setAttribute("ui.icon", iconAnim.get(0));

		int i=0;

		while( loop ) {
			pipeIn.pump();
			sleep( 60 );
			
			if( i >= 9 )
				i = 0;
			else if (i < 0)
				i = 8 ;
			
			A.setAttribute("ui.icon", iconAnim.get(i));
			
			if (direction)	i++ ;
			else			i--;
			
		}
		System.out.println( "bye bye" );
		System.exit(0);
	}
	
	protected void sleep( long ms ) {
		try {
			Thread.sleep( ms );
		} catch (InterruptedException e) { e.printStackTrace(); }
	}

// Viewer Listener Interface

	public void viewClosed( String id ) { }

	public void buttonPushed( String id ) {
		System.out.println(id);
		if( id.equals("quit") )
 			loop = false;
 		else if( id.equals("A") ) {
 			System.out.println( "Button A pushed" );
 			direction = !direction;
 		}
	}

 	public void buttonReleased( String id ) {}
 	
 // Data
  	private String styleSheet =
  			"graph {"+
  			"canvas-color: white;"+
			"fill-mode: gradient-radial;"+
			"fill-color: white, #EEEEEE;"+
			"padding: 60px;"+
		"}"+
	"node {"+
		"shape: freeplane;"+
		"size: 10px;"+
		"size-mode: fit;"+
		"fill-mode: none;"+
		"stroke-mode: plain;"+
		"stroke-color: grey;"+
		"stroke-width: 3px;"+
		"padding: 5px, 1px;"+
		"shadow-mode: none;"+
		"icon-mode: at-left;"+
		"text-style: normal;"+
		"text-font: 'Droid Sans';"+
		"icon: dyn-icon;"+
	"}"+
	"node:clicked {"+
		"stroke-mode: plain;"+
		"stroke-color: red;"+
	"}"+
	"node:selected {"+
		"stroke-mode: plain;"+
		"stroke-color: blue;"+
	"}"+
	"edge {"+
		"shape: freeplane;"+
		"size: 3px;"+
		"fill-color: grey;"+
		"fill-mode: plain;"+
		"shadow-mode: none;"+
		"shadow-color: rgba(0,0,0,100);"+
		"shadow-offset: 3px, -3px;"+
		"shadow-width: 0px;"+
		"arrow-shape: arrow;"+
		"arrow-size: 20px, 6px;"+
	"}";
  	
  	public void mouseOver(String id){}

	public void mouseLeft(String id){}
}
