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
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.util.DefaultApplication;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.javafx.util.ImageCache;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javafx.application.Application;

public class TestFreePlane implements ViewerListener {
	public static void main(String[] args) {
		(new TestFreePlane()).run(args);
	}
	
	public static final String URL_IMAGE = ImageCache.class.getClassLoader().getResource("org/graphstream/ui/viewer_fx/test/data/icon.png").toString();
	
	private boolean loop = true;
	
	private void run(String[] args) {
		MultiGraph graph  = new MultiGraph( "g1" );
		FxViewer viewer = new FxViewer( graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
		ViewerPipe pipeIn = viewer.newViewerPipe();
		FxDefaultView view = (FxDefaultView)viewer.addView("view1", new FxGraphRenderer() );

		DefaultApplication.init(view, graph);
	    new Thread(() -> Application.launch(DefaultApplication.class)).start();
	    
		pipeIn.addAttributeSink( graph );
		pipeIn.addViewerListener( this );
		pipeIn.pump();

		graph.setAttribute( "ui.stylesheet", styleSheet );
		graph.setAttribute( "ui.antialias" );
		graph.setAttribute( "ui.quality" );

		Node root = graph.addNode( "root" );
		Node A    = graph.addNode( "A" );
		Node B    = graph.addNode( "B" );
		Node C    = graph.addNode( "C" );
		Node D    = graph.addNode( "D" );
		Node E    = graph.addNode( "E" );
		Node F    = graph.addNode( "F" );
		Node G    = graph.addNode( "G" );
		Node H    = graph.addNode( "H" );

		graph.addEdge( "rA", "root", "A" );
		graph.addEdge( "rB", "root", "B" );
		graph.addEdge( "rC", "root", "C" );
		graph.addEdge( "rD", "root", "D" );
		graph.addEdge( "rE", "root", "E" );
		graph.addEdge( "AF", "A", "F" );
		graph.addEdge( "CG", "C", "G" );
		graph.addEdge( "DH", "D", "H" );

		root.setAttribute("xyz", new double[] { 0, 0, 0});
		A.setAttribute("xyz"   , new double[] {1, 1, 0 });
		B.setAttribute("xyz"   , new double[] { 1, 0, 0 });
		C.setAttribute("xyz"   , new double[] {-1, 1, 0 });
		D.setAttribute("xyz"   , new double[] {-1, 0, 0 });
		E.setAttribute("xyz"   , new double[] {-1,-1, 0 });
		F.setAttribute("xyz"   , new double[] { 2, 1.2, 0 });
		G.setAttribute("xyz"   , new double[] {-2, 1.2, 0 });
		H.setAttribute("xyz"   , new double[] {-2,-.5, 0 });

		root.setAttribute("label", "Idea");
		A.setAttribute("label", "Topic1");
		B.setAttribute("label", "Topic2");
		C.setAttribute("label", "Topic3");
		D.setAttribute("label", "Topic4");
		E.setAttribute("label", "Topic5");
		F.setAttribute("label", "SubTopic1");
		G.setAttribute("label", "SubTopic2");
		H.setAttribute("label", "Very Long Sub Topic ...");

		while( loop ) {
			pipeIn.pump();
			sleep( 10 );
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

	public void viewClosed( String id ) { loop = false ;}

	public void buttonPushed( String id ) {
		System.out.println(id);
		if( id.equals("quit") )
 			loop = false;
 		else if( id.equals("A") )
 			System.out.println( "Button A pushed" );
	}

 	public void buttonReleased( String id ) {}
 	
 // Data
  	private String styleSheet = ""
  			+ "graph {"
  			+ "	canvas-color: white; "
  			+ "	fill-mode: gradient-radial; "
  			+ "	fill-color: white, #EEEEEE; "
  			+ "	padding: 60px; "
  			+ "}"
  			+ ""
  			+ "node {"
  			+ "	shape: freeplane;"
  			+ "	size: 10px;"
  			+ "	size-mode: fit;"
  			+ "	fill-mode: none;"
  			+ "	stroke-mode: plain;"
  			+ "	stroke-color: grey;"
  			+ "	stroke-width: 3px;"
  			+ "	padding: 5px, 1px;"
  			+ "	shadow-mode: none;"
  			+ "	icon-mode: at-left;"
  			+ "	text-style: normal;"
  			+ "	text-font: 'Droid Sans';"
  			+ "	icon: url('"+URL_IMAGE+"');"
  			+ "}"
  			+ ""
  			+ "node:clicked {"
  			+ "	stroke-mode: plain;"
  			+ "	stroke-color: red;"
  			+ "}"
  			+ ""
  			+ "node:selected {"
  			+ "	stroke-mode: plain;"
  			+ "	stroke-color: blue;"
  			+ "}"
  			+ ""
  			+ "edge {"
  			+ "	shape: freeplane;"
  			+ "	size: 3px;"
  			+ "	fill-color: grey;"
  			+ "	fill-mode: plain;"
  			+ "	shadow-mode: none;"
  			+ "	shadow-color: rgba(0,0,0,100);"
  			+ "	shadow-offset: 3px, -3px;"
  			+ "	shadow-width: 0px;"
  			+ "	arrow-shape: arrow;"
  			+ "	arrow-size: 20px, 6px;"
  			+ "}";
  	
  	public void mouseOver(String id){}

	public void mouseLeft(String id){}
}
