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
  
package org.graphstream.ui.fx_viewer.util;

import org.graphstream.graph.Graph;
import org.graphstream.ui.fx_viewer.FxDefaultView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Default Application used by Display for visualize the graph. 
 */
public class DefaultApplication extends Application {
	private static Graph graph ;
	private static Stage stage ;
	private static FxDefaultView view ;
	public static boolean isInstance = false;
	public static boolean antiAliasing = false ;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage = primaryStage ;

        Scene scene = new Scene(view, 800, 600, true, SceneAntialiasing.DISABLED);
        primaryStage.setScene(scene);
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        isInstance = true ;
        primaryStage.show();
	}
	
	public static void newDisplay(FxDefaultView view2) {
		Stage newStage = new Stage();
		newStage.setTitle("GraphStream FX");
		
		Scene scene = new Scene(view2, 800, 600, true, SceneAntialiasing.DISABLED);
		newStage.setScene(scene);
        
		newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		newStage.show();
	}
	
	public static void init(FxDefaultView v, Graph g) {
		graph = g ;
		view = v ;
	}
	
	public static void checkTitle() {
		String titleAttr = String.format("ui.%s.title", view.getIdView());
		String title = (String) graph.getLabel(titleAttr);

		if (title == null) {
			title = (String) graph.getLabel("ui.default.title");

			if (title == null)
				title = (String) graph.getLabel("ui.title");
		}

		if (title != null)
			stage.setTitle(title);
		else
			stage.setTitle("GraphStream FX");
		
	}
	

	public static void setAliasing(boolean antialias) {
		if ( antialias != antiAliasing ) {
			antiAliasing = antialias ;
			
			view.getScene().setRoot(new Region());
			Scene newScene ;
			if (antiAliasing)
				newScene = new Scene(view, 800, 600, true, SceneAntialiasing.BALANCED);
			else
				newScene = new Scene(view, 800, 600, true, SceneAntialiasing.DISABLED);
			
			stage.setScene(newScene);
		}
	}

}
