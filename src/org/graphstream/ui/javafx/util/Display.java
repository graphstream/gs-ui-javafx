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
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class Display implements org.graphstream.util.Display, FileSinkImagesFactory {
	public static boolean instanceJavaFX = false ;
	
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
		
		if (!instanceJavaFX) {
			instanceJavaFX = true ;	
			DefaultApplication.init(view, graph);
			new Thread(() -> {
				try {
					Application.launch(DefaultApplication.class);
				}
				catch (Exception e) {
					newDisplay(view);
				}
			}).start();	
			
		}
		else {
			newDisplay(view);
		}
		
	    return viewer;
	}
	
	public void newDisplay(FxDefaultView view) {
		new Thread(() -> { 
			new JFXPanel();
			
			Platform.runLater(() -> {
				DefaultApplication.newDisplay(view)	;
			});
		}).start();
	}

	@Override public FileSinkImages createFileSinkImages() {
		return new FxFileSinkImages();
	}
}
