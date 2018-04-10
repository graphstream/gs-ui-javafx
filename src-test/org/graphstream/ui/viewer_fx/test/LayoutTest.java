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
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class LayoutTest {
	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui", "org.graphstream.ui.javafx.util.Display");
		ATest test = new ATest();
		test.run( args );
	}
}

class ATest implements ViewerListener {
	private boolean loop = true;

	public void run( String[] args ) {
		Graph graph  = new MultiGraph( "g1" );
		Viewer viewer = graph.display( true );
		ViewerPipe pipeIn = viewer.newViewerPipe();
		DorogovtsevMendesGenerator gen    = new DorogovtsevMendesGenerator();

		pipeIn.addAttributeSink( graph );
		pipeIn.addViewerListener( this );
		pipeIn.pump();

		graph.setAttribute( "ui.default.title", "Layout Test Fx" );
		graph.setAttribute( "ui.antialias" );
		graph.setAttribute( "ui.stylesheet", styleSheet );

		gen.addSink( graph );
		gen.setDirectedEdges( true, true );
		gen.begin();
		int i = 0;
		while ( i < 100 ) {
			gen.nextEvents(); 
			i += 1;
		}
		gen.end();

		graph.forEach( n -> n.setAttribute( "ui.label", "truc" ));

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

	public void buttonPushed( String id ) {}

 	public void buttonReleased( String id ) {}

// Data
	private String styleSheet =
			"graph {"+
 				"fill-mode: gradient-radial;"+
 				"fill-color: white, gray;"+
 				"padding: 60px;"+
 			"}"+
			"node {"+
				"shape: circle;"+
				"size: 10px;"+
				"fill-mode: gradient-vertical;"+
				"fill-color: white, rgb(200,200,200);"+
				"stroke-mode: plain;"+
				"stroke-color: rgba(255,255,0,255);"+
				"stroke-width: 2px;"+
				"shadow-mode: plain;"+
				"shadow-width: 0px;"+
				"shadow-offset: 3px, -3px;"+
				"shadow-color: rgba(0,0,0,100);"+
				"text-visibility-mode: zoom-range;"+
				"text-visibility: 0, 0.9;"+
				//icon-mode: at-left;
				//icon: url('file:///home/antoine/GSLogo11d24.png');
			"}"+
			"node:clicked {"+
				"stroke-mode: plain;"+
				"stroke-color: red;"+
			"}"+
			"node:selected {"+
				"stroke-mode: plain;"+
				"stroke-width: 4px;"+
				"stroke-color: blue;"+
			"}"+
			"edge {"+
				"size: 1px;"+
				"shape: cubic-curve;"+
				"fill-color: rgb(128,128,128);"+
				"fill-mode: plain;"+
				"stroke-mode: plain;"+
				"stroke-color: rgb(80,80,80);"+
				"stroke-width: 1px;"+
				"shadow-mode: none;"+
				"shadow-color: rgba(0,0,0,50);"+
				"shadow-offset: 3px, -3px;"+
				"shadow-width: 0px;"+
				"arrow-shape: diamond;"+
				"arrow-size: 14px, 7px;"+
			"}";

	private String oldStyleSheet =
			"graph {"+
 				"fill-mode: gradient-radial;"+
 				"fill-color: white, gray;"+
 				"padding: 60px;"+
 			"}"+
			"node {"+
				"shape: box;"+
				"size: 10px, 10px;"+
				"fill-mode: gradient-vertical;"+
				"fill-color: white, rgb(200,200,200);"+
				"stroke-mode: plain;"+
				"stroke-color: rgba(255,255,0,255);"+
				"stroke-width: 2px;"+
				"shadow-mode: plain;"+
				"shadow-width: 0px;"+
				"shadow-offset: 3px, -3px;"+
				"shadow-color: rgba(0,0,0,100);"+
				"text-visibility-mode: zoom-range;"+
				"text-visibility: 0, 0.9;"+
				//icon-mode: at-left;
				//icon: url('file:///home/antoine/GSLogo11d24.png');
			"}"+
			"node:clicked {"+
				"stroke-mode: plain;"+
				"stroke-color: red;"+
			"}"+
			"node:selected {"+
				"stroke-mode: plain;"+
				"stroke-width: 4px;"+
				"stroke-color: blue;"+
			"}"+
			"edge {"+
				"size: 2px;"+
				"shape: blob;"+
				"fill-color: rgb(128,128,128);"+
				"fill-mode: plain;"+
				"stroke-mode: plain;"+
				"stroke-color: rgb(80,80,80);"+
				"stroke-width: 2px;"+
				"shadow-mode: plain;"+
				"shadow-color: rgba(0,0,0,50);"+
				"shadow-offset: 3px, -3px;"+
				"shadow-width: 0px;"+
				"arrow-shape: arrow;"+
				"arrow-size: 20px, 6px;"+
			"}";

	public void mouseOver(String id){}

	public void mouseLeft(String id){}
}
