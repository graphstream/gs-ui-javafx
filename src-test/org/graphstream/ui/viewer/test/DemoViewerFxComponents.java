/*
 * Copyright 2006 - 2016
 *     Stefan Balev     <stefan.balev@graphstream-project.org>
 *     Julien Baudry    <julien.baudry@graphstream-project.org>
 *     Antoine Dutot    <antoine.dutot@graphstream-project.org>
 *     Yoann Pigné      <yoann.pigne@graphstream-project.org>
 *     Guilhelm Savin   <guilhelm.savin@graphstream-project.org>
 * 
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
package org.graphstream.ui.viewer.test;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.fx_viewer.basicRenderer.FxBasicGraphRenderer;
import org.graphstream.ui.fx_viewer.util.DefaultApplication;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javafx.application.Application;

public class DemoViewerFxComponents implements ViewerListener {
	public static void main(String args[]) {
		new DemoViewerFxComponents();
	}
	
	public static final String URL_IMAGE = "file:///home/hicham/Bureau/b.png" ;

	private boolean loop = true;
	private Graph graph = new MultiGraph("main graph");
	private Node A, B, C;
	
	public DemoViewerFxComponents() {

		FxViewer viewer = new FxViewer( graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
		ViewerPipe pipeIn = viewer.newViewerPipe();
		FxDefaultView view = (FxDefaultView)viewer.addView("view1", new FxBasicGraphRenderer() );
		
		DefaultApplication.init(view, graph);
	    new Thread(() -> Application.launch(DefaultApplication.class)).start();
	    
		pipeIn.addAttributeSink(graph);
		pipeIn.addViewerListener( this );
		pipeIn.pump();
		//viewer.addDefaultView(true);
		
		A = graph.addNode("quit");
		B = graph.addNode("B");
		C = graph.addNode("C");

		graph.addEdge("AB", "quit", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "quit");

		A.setAttribute("xyz", 0, 1, 0);
		B.setAttribute("xyz", 1, 0, 0);
		C.setAttribute("xyz", -1, 0, 0);

		A.setAttribute("ui.label", "Quit");
		B.setAttribute("ui.label", "Editable text");
		C.setAttribute("ui.label", "Click to edit");

		graph.setAttribute("ui.stylesheet", styleSheet);

		SpriteManager sman = new SpriteManager(graph);

		Sprite s1 = sman.addSprite("S1");
		Sprite s2 = sman.addSprite("S2");
		Sprite s3 = sman.addSprite("S3");

		s1.attachToNode("B");
		s2.attachToEdge("BC");
		s1.setPosition(StyleConstants.Units.PX, 1, 0, 0);
		s2.setPosition(0.5f);
		s3.setPosition(0, 0.5f, 0);
		s1.setAttribute("ui.label", "Clic");
		s2.setAttribute("ui.label", "2");

		float angle = 0;

		while (loop) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			pipeIn.pump();	

			angle += 0.03;
			if (angle > 360)
				angle = 0;
			
			double r = 60 ;
			
			double x = r*Math.cos(angle);
			double y = r*Math.sin(angle);
			
			s1.setPosition(StyleConstants.Units.PX, x, y, 0);
		}

		System.out.printf("Bye bye ...%n");
		System.exit(0);
	}

	protected static String styleSheet = ""
			+ "graph {" 
			+ "	padding:      60px;"
			+ "	stroke-width: 1px;" 
			+ "	stroke-color: rgb(200,200,200);"
			+ "	stroke-mode:  dots;" 
			+ "	fill-mode:    gradient-diagonal1;"
			+ "	fill-color:   white, rgb(230,230,230);" 
			+ "}"
			+ "node {"
			+ "	shape:        jcomponent;" 
			+ "	jcomponent:   button;"
			+ "	size:         100px, 30px;" 
			+ "	stroke-width: 2px;"
			+ "	stroke-color: rgb(180,180,180);" 
			+ "	fill-mode:    none;"
			+ "	text-font:    arial;"
			+ "	text-size:    11;"
			+ "	text-color:   rgb(30,30,30);"
			+ "	text-style:   bold;" 
			+ "	stroke-mode: plain;"
  			+ "	stroke-color: rgba(100,100,100,255);"
  			+ "	stroke-width: 9px;"
			+ " }"
			+ "node#B {"
			+ "	shape:      jcomponent;"
			+ "	jcomponent: text-field;" 
			+ "	text-color: red;"
			+ "	text-style: italic;"
			+ "}"
			+ "node#C {"
			+ "	size:       200px, 30px;"
			+ "	icon:		url('"+URL_IMAGE+"');"
			+ "	icon-mode:	at-left;"
			+ "}"
			+ "sprite#S3 {"
			+ "	size:       70, 80;"
			+ "	size-mode:	fit;"
			+ "	icon:		url('"+URL_IMAGE+"');"
			+ "	icon-mode:	above;"
			+ "}" 
			+ "node.editable {"
			+ "	shape:      jcomponent;"
			+ "	jcomponent: text-field;"
			+ "}"
			+ "node:selected {"
			+ "	stroke-mode: plain; stroke-width: 5px; stroke-color: red;"
			+ "}"
			+ "sprite {" 
			+ "	shape:      jcomponent;"
			+ "	jcomponent: button;" 
			+ "	size:       51px, 20px;"
			+ "	fill-mode:  none;" 
			+ "}";
	

	@Override
	public void viewClosed(String viewName) {
		loop = false ;
	}

	@Override
	public void buttonPushed(String id) {
		System.out.println("Button "+id+" pushed");
		
		if(id.equals("S1")) {
			C.setAttribute("ui.label", B.getAttribute("ui.label"));
		}
		else if(id.equals("C")) {
			C.setAttribute("ui.label", "Editable");
		}
		else if( id.equals("quit") ) {
			loop = false;
		}
	}

	@Override
	public void buttonReleased(String id) {
		
	}

	@Override
	public void mouseOver(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseLeft(String id) {
		// TODO Auto-generated method stub
		
	}
}